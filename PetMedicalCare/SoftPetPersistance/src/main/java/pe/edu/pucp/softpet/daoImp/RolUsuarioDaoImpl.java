package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.RolUsuarioDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.usuarios.RolUsuarioDto;

public class RolUsuarioDaoImpl extends DaoBaseImpl implements RolUsuarioDao {

    private RolUsuarioDto rolUsuario;

    public RolUsuarioDaoImpl() {
        super("ROLES_USUARIO");
        this.rolUsuario = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ROL_USUARIO_ID", true, true));
        this.listaColumnas.add(new Columna("ROL_ID", false, false));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.rolUsuario.getRol().getRolId());
        this.statement.setInt(2, this.rolUsuario.getUsuario().getUsuarioId());
        this.statement.setInt(3, this.rolUsuario.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.rolUsuario.getRol().getRolId());
        this.statement.setInt(2, this.rolUsuario.getUsuario().getUsuarioId());
        this.statement.setInt(3, this.rolUsuario.getActivo() ? 1 : 0);

        this.statement.setInt(4, this.rolUsuario.getRolUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.rolUsuario.getRolUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.rolUsuario.getRolUsuarioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.rolUsuario = new RolUsuarioDto();
        this.rolUsuario.setRolUsuarioId(this.resultSet.getInt("ROL_USUARIO_ID"));
        this.rolUsuario.setRol(new RolDaoImpl().obtenerPorId(this.resultSet.getInt("ROL_ID")));
        this.rolUsuario.setUsuario(new UsuarioDaoImpl().obtenerPorId(this.resultSet.getInt("USUARIO_ID")));
        this.rolUsuario.setActivo(this.resultSet.getInt("ACTIVO") == 1);

    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.rolUsuario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.rolUsuario);
    }

    @Override
    public Integer insertar(RolUsuarioDto rolUsuario) {
        this.rolUsuario = rolUsuario;
        return super.insertar();
    }

    @Override
    public RolUsuarioDto obtenerPorId(Integer rolUsuarioId) {
        this.rolUsuario = new RolUsuarioDto();
        this.rolUsuario.setRolUsuarioId(rolUsuarioId);
        super.obtenerPorId();
        return this.rolUsuario;
    }

    @Override
    public ArrayList<RolUsuarioDto> listarTodos() {
        return (ArrayList<RolUsuarioDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(RolUsuarioDto rolUsuario) {
        this.rolUsuario = rolUsuario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(RolUsuarioDto rolUsuario) {
        this.rolUsuario = rolUsuario;
        return super.eliminar();
    }
}
