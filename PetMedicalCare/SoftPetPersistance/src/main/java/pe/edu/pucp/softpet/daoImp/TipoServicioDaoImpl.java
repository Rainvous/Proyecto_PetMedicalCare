package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dao.TipoServicioDao;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

/**
 *
 * @author marti
 */
public class TipoServicioDaoImpl extends DAOImplBase implements TipoServicioDao {

    private TipoServicioDto tipoServicio;

    public TipoServicioDaoImpl() {
        super("TIPOS_SERVICIO");
        this.tipoServicio = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_SERVICIO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.tipoServicio.getNombre());
        this.statement.setString(2, this.tipoServicio.getDescripcion());
        this.statement.setInt(3, this.tipoServicio.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.tipoServicio.getNombre());
        this.statement.setString(2, this.tipoServicio.getDescripcion());
        this.statement.setInt(3, this.tipoServicio.getActivo() ? 1 : 0);
        this.statement.setInt(4, this.tipoServicio.getTipoServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tipoServicio.getTipoServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoServicio.getTipoServicioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoServicio = new TipoServicioDto();
        this.tipoServicio.setTipoServicioId(this.resultSet.getInt("TIPO_SERVICIO_ID"));
        this.tipoServicio.setNombre(this.resultSet.getString("NOMBRE"));
        this.tipoServicio.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.tipoServicio.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoServicio = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoServicio);
    }

    @Override
    public Integer insertar(TipoServicioDto tipoServicio) {
        this.tipoServicio = tipoServicio;
        return super.insertar();
    }

    @Override
    public TipoServicioDto obtenerPorId(Integer tipoServicioiD) {
        this.tipoServicio = new TipoServicioDto();
        this.tipoServicio.setTipoServicioId(tipoServicioiD);
        super.obtenerPorId();
        return this.tipoServicio;
    }

    @Override
    public ArrayList<TipoServicioDto> listarTodos() {
        return (ArrayList<TipoServicioDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(TipoServicioDto tipoServicio) {
        this.tipoServicio = tipoServicio;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TipoServicioDto rol) {
        this.tipoServicio = tipoServicio;
        return super.eliminar();
    }
}
