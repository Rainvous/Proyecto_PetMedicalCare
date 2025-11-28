package pe.edu.pucp.softpet.daoImp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;
import pe.edu.pucp.softpet.dao.UsuarioDAO;

public class UsuarioDaoImpl extends DaoBaseImpl implements UsuarioDAO {

    private UsuarioDto usuario;

    public UsuarioDaoImpl() {
        super("USUARIOS");
        this.usuario = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("USUARIO_ID", true, true));
        this.listaColumnas.add(new Columna("USERNAME", false, false));
        this.listaColumnas.add(new Columna("PASSWORD", false, false));
        this.listaColumnas.add(new Columna("CORREO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // NOTA: la auditoria se maneja con un trigger
        this.statement.setString(1, this.usuario.getUsername());
        this.statement.setString(2, this.usuario.getPassword());
        this.statement.setString(3, this.usuario.getCorreo());
        this.statement.setBoolean(4, this.usuario.getActivo());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.usuario.getUsername());
        this.statement.setString(2, this.usuario.getPassword());
        this.statement.setString(3, this.usuario.getCorreo());
        this.statement.setBoolean(4, this.usuario.getActivo());

        this.statement.setInt(5, this.usuario.getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.usuario.getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.usuario.getUsuarioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.usuario = new UsuarioDto();
        this.usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        this.usuario.setUsername(this.resultSet.getString("USERNAME"));
        this.usuario.setPassword(this.resultSet.getString("PASSWORD"));
        this.usuario.setCorreo(this.resultSet.getString("CORREO"));
        this.usuario.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.usuario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.usuario);
    }

    @Override
    public Integer insertar(UsuarioDto usuario) {
        this.usuario = usuario;
        return super.insertar();
    }

    @Override
    public UsuarioDto obtenerPorId(Integer usuarioId) {
        this.usuario = new UsuarioDto();
        this.usuario.setUsuarioId(usuarioId);
        super.obtenerPorId();

        return this.usuario;
    }

    @Override
    public ArrayList<UsuarioDto> listarTodos() {
        return (ArrayList<UsuarioDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(UsuarioDto usuario) {
        this.usuario = usuario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(UsuarioDto usuario) {
        this.usuario = usuario;
        return super.eliminar();
    }
    
    // Método para insertar dentro de la transacción de otro DAO
    public Integer insertarTransaccional(UsuarioDto usuario, java.sql.Connection con) throws SQLException {
        this.usuario = usuario; // Seteamos el DTO para que el padre lo lea
        return super.insertarEnTransaccion(con);
    }

    // Método para modificar dentro de la transacción de otro DAO
    public Integer modificarTransaccional(UsuarioDto usuario, java.sql.Connection con) throws SQLException {
        this.usuario = usuario;
        return super.modificarEnTransaccion(con);
    }

    /// PROCEDURES Y SELECT'
    /// @return s

    public String generarListarPorCorreoYContra() {
//         String sql = "SELECT * ";
//         sql = sql.concat("FROM ROLES r ");
//         sql = sql.concat("JOIN ROLES_USUARIO ru ON ru.rol_id=r.rol_id ");
//         sql = sql.concat("JOIN USUARIOS usu ON usu.usuario_id=ru.usuario_id ");
//         sql = sql.concat("WHERE usu.usuario_id= ? ");

        String sql = "SELECT * ";
        sql = sql.concat("FROM USUARIOS usu ");
        sql = sql.concat("WHERE usu.correo like ? ");
        sql = sql.concat("AND usu.password like ? ");
        return sql;
    }

    public void incluirValorDeParametrosParaListarPorCorreoYContra(Object objetoParametros) {
        UsuarioDto parametro = (UsuarioDto) objetoParametros;
        String correo = "%";
        String contra = "%";
        correo = correo.concat(parametro.getCorreo());
        correo = correo.concat("%");
        contra = contra.concat(parametro.getPassword());
        contra = contra.concat("%");
        try {
            this.statement.setString(1, correo);
            this.statement.setString(2, contra);
        } catch (SQLException ex) {
            System.err.println("No se pudo incluirValores de parametro den el Statement-> " + this.statement);
            System.getLogger(ProductoDaoImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @Override
    public ArrayList<UsuarioDto> ObtenerPorCorreoYContra(String correo, String contra) {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setCorreo(correo);
        usuario.setPassword(contra);
        String sql = generarListarPorCorreoYContra();
        return (ArrayList<UsuarioDto>) super.listarTodos(sql, this::incluirValorDeParametrosParaListarPorCorreoYContra, usuario);
    }
    
    // ================================================================
    //  NUEVOS MÉTODOS AGREGADOS PARA CAMBIO DE CONTRASEÑA
    // ================================================================

    @Override
    public Integer actualizarPassword(int idUsuario, String nuevaPasswordHash) {
        Integer resultado = 0;
        String sql = "UPDATE USUARIOS SET PASSWORD = ? WHERE USUARIO_ID = ?";
        try {
            this.abrirConexion();
            try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
                ps.setString(1, nuevaPasswordHash);
                ps.setInt(2, idUsuario);
                resultado = ps.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try { this.cerrarConexion(); } catch (Exception e) {}
        }
        return resultado;
    }

    @Override
    public String obtenerPasswordActual(int idUsuario) {
        String passwordHash = null;
        String sql = "SELECT PASSWORD FROM USUARIOS WHERE USUARIO_ID = ?";
        try {
            this.abrirConexion();
            try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        passwordHash = rs.getString("PASSWORD");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try { this.cerrarConexion(); } catch (Exception e) {}
        }
        return passwordHash;
    }
    
    // NUEVO: Buscar ID por Correo (Para recuperación)
    @Override
    public UsuarioDto obtenerPorCorreo(String correo) {
        UsuarioDto user = null;
        String sql = "SELECT USUARIO_ID, USERNAME, ACTIVO FROM USUARIOS WHERE CORREO = ? AND ACTIVO = 1";
        
        try {
            this.abrirConexion();
            try (PreparedStatement ps = this.conexion.prepareStatement(sql)) {
                ps.setString(1, correo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user = new UsuarioDto();
                        user.setUsuarioId(rs.getInt("USUARIO_ID"));
                        user.setUsername(rs.getString("USERNAME"));
                        user.setCorreo(correo);
                        user.setActivo(true);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try { this.cerrarConexion(); } catch (Exception e) {}
        }
        return user;
    }
}
