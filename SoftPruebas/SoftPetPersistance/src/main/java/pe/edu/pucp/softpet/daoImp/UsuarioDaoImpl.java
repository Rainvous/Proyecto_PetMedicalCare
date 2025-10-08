package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;
import pe.edu.pucp.softpet.dao.UsuarioDAO;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;

/**
 *
 * @author marti
 */
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
        this.listaColumnas.add(new Columna("USENAME", false, false));
        this.listaColumnas.add(new Columna("PASSWORD", false, false));
        this.listaColumnas.add(new Columna("CORREO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
//        this.listaColumnas.add(new Columna("FECHA_MODIFICACION", false, false));
//        this.listaColumnas.add(new Columna("USUARIO_MODIFICADOR", false, false));
//        this.listaColumnas.add(new Columna("USUARIO_CREADOR", false, false));
//        this.listaColumnas.add(new Columna("FECHA_CREACION", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.usuario.getUsername());
        this.statement.setString(2, this.usuario.getPassword());
        this.statement.setString(3, this.usuario.getCorreo());
        this.statement.setBoolean(4, this.usuario.getActivo());
//        this.statement.setDate(4, this.usuario.getFechaModificacion());
//        this.statement.setString(5, this.usuario.getUsuarioModificador());
//        this.statement.setString(6, this.usuario.getUsuarioCreador());
//        this.statement.setDate(7, this.usuario.getFechaCreacion());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.usuario.getUsername());
        this.statement.setString(2, this.usuario.getPassword());
        this.statement.setString(3, this.usuario.getCorreo());
        this.statement.setBoolean(4, this.usuario.getActivo());
//        this.statement.setDate(4, this.usuarios.getFechaModificacion());
//        this.statement.setString(5, this.usuarios.getUsuarioModificador());
//        this.statement.setString(6, this.usuarios.getUsuarioCreador());
//        this.statement.setDate(7, this.usuarios.getFechaCreacion());

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
//        this.usuario.setActivo.setFechaModificacion(this.resultSet.getDate("FECHA_MODIFICACION"));
//      
//        this.usuario.setActivo.setUsuarioModificador(this.resultSet.getString("USUARIO_MODIFICADOR"));
//        this.usuario.setActivo.setUsuarioCreador(this.resultSet.getString("FECHA_CREACION"));
//        this.usuario.setActivo.setFechaCreacion(this.resultSet.getDate("FECHA_CREACION"));
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

    public String generarListarPorCorreoYContra() {
//         String sql = "SELECT * ";
//         sql = sql.concat("FROM ROLES r ");
//         sql = sql.concat("JOIN ROLES_USUARIO ru ON ru.rol_id=r.rol_id ");
//         sql = sql.concat("JOIN USUARIOS usu ON usu.usuario_id=ru.usuario_id ");
//         sql = sql.concat("WHERE usu.usuario_id= ? ");

        String sql="SELECT * ";
        sql= sql.concat("FROM USUARIO usu ");
        sql= sql.concat("WHERE usu.correo like ? ");
        sql= sql.concat("AND usu.password like ? ");
        return sql;
    }

    public void incluirValorDeParametrosParaListarPorCorreoYContra(Object objetoParametros) {
        UsuarioDto parametro = (UsuarioDto) objetoParametros;
        String correo="%";
        String contra="%";
        correo= correo.concat(parametro.getCorreo());
        correo= correo.concat("%");
        contra= contra.concat(parametro.getPassword());
        contra= contra.concat("%");
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
}
