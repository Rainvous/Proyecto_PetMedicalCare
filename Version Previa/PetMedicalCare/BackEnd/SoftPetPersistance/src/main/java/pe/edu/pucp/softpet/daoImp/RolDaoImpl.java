package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;
import pe.edu.pucp.softpet.dao.RolDao;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

public class RolDaoImpl extends DaoBaseImpl implements RolDao {

    private RolDto rol;

    public RolDaoImpl() {
        super("ROLES");
        this.rol = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ROL_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.rol.getNombre());
        this.statement.setInt(2, this.rol.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.rol.getNombre());
        this.statement.setInt(2, this.rol.getActivo() ? 1 : 0);

        this.statement.setInt(3, this.rol.getRolId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.rol.getRolId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.rol.getRolId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.rol = new RolDto();
        this.rol.setRolId(this.resultSet.getInt("ROL_ID"));
        this.rol.setNombre(this.resultSet.getString("NOMBRE"));
        this.rol.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.rol = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.rol);
    }

    @Override
    public Integer insertar(RolDto rol) {
        this.rol = rol;
        return super.insertar();
    }

    @Override
    public RolDto obtenerPorId(Integer rolId) {
        this.rol = new RolDto();
        this.rol.setRolId(rolId);
        super.obtenerPorId();
        return this.rol;
    }

    @Override
    public ArrayList<RolDto> listarTodos() {
        return (ArrayList<RolDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(RolDto rol) {
        this.rol = rol;
        return super.modificar();
    }

    @Override
    public Integer eliminar(RolDto rol) {
        this.rol = rol;
        return super.eliminar();
    }

    @Override
    public ArrayList<RolDto> ObtenerRolesDelUsuario(Integer idUser) {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setUsuarioId(idUser);
        String sql = GenerarSQLListarRolesDeUsuario();
        return (ArrayList<RolDto>) super.listarTodos(sql, this::incluirValorDeParametrosParaListarPorRolesUsuario, usuario);
    }

    private String GenerarSQLListarRolesDeUsuario() {

        String sql = "SELECT * ";
        sql = sql.concat("FROM ROLES r ");
        sql = sql.concat("JOIN ROLES_USUARIO ru ON ru.rol_id=r.rol_id ");
        sql = sql.concat("JOIN USUARIOS usu ON usu.usuario_id=ru.usuario_id ");
        sql = sql.concat("WHERE usu.usuario_id= ? ");
        return sql;
    }

    public void incluirValorDeParametrosParaListarPorRolesUsuario(Object objetoParametros) {
        UsuarioDto parametros = (UsuarioDto) objetoParametros;

        try {
            this.statement.setInt(1, parametros.getUsuarioId());
        } catch (SQLException ex) {
            System.err.println("No se pudo incluirValores de parametro den el Statement-> " + this.statement);
            System.getLogger(ProductoDaoImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
