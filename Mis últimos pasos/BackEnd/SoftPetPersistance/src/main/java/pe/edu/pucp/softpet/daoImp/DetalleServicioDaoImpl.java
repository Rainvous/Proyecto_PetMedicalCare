package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.dao.DetalleServicioDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.servicios.DetalleServicioDto;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

public class DetalleServicioDaoImpl extends DaoBaseImpl implements DetalleServicioDao {

    private DetalleServicioDto detalleServicio;

    public DetalleServicioDaoImpl() {
        super("DETALLES_SERVICIO");
        this.detalleServicio = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DETALLE_SERVICIO_ID", true, true));
        this.listaColumnas.add(new Columna("CITA_ID", false, false));
        this.listaColumnas.add(new Columna("SERVICIO_ID", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("COSTO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.detalleServicio.getCita().getCitaId());
        this.statement.setInt(2, this.detalleServicio.getServicio().getServicioId());
        this.statement.setString(3, this.detalleServicio.getDescripcion());
        this.statement.setDouble(4, this.detalleServicio.getCosto());
        this.statement.setInt(5, this.detalleServicio.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.detalleServicio.getCita().getCitaId());
        this.statement.setInt(2, this.detalleServicio.getServicio().getServicioId());
        this.statement.setString(3, this.detalleServicio.getDescripcion());
        this.statement.setDouble(4, this.detalleServicio.getCosto());
        this.statement.setInt(5, this.detalleServicio.getActivo() ? 1 : 0);

        this.statement.setInt(6, this.detalleServicio.getDetalleServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.detalleServicio.getDetalleServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.detalleServicio.getDetalleServicioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detalleServicio = new DetalleServicioDto();
        this.detalleServicio.setDetalleServicioId(this.resultSet.getInt("DETALLE_SERVICIO_ID"));
        this.detalleServicio.setCita(new CitaAtencionDaoImpl().
                obtenerPorId(this.resultSet.getInt("CITA_ID")));
        this.detalleServicio.setServicio(new ServicioDaoImpl().
                obtenerPorId(this.resultSet.getInt("SERVICIO_ID")));
        this.detalleServicio.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.detalleServicio.setCosto(this.resultSet.getDouble("COSTO"));
        this.detalleServicio.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.detalleServicio = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.detalleServicio);
    }

    @Override
    public Integer insertar(DetalleServicioDto detalleServicio) {
        this.detalleServicio = detalleServicio;
        return super.insertar();
    }

    @Override
    public DetalleServicioDto obtenerPorId(Integer detalleServicioId) {
        this.detalleServicio = new DetalleServicioDto();
        this.detalleServicio.setDetalleServicioId(detalleServicioId);
        super.obtenerPorId();
        return this.detalleServicio;
    }

    @Override
    public ArrayList<DetalleServicioDto> listarTodos() {
        return (ArrayList<DetalleServicioDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(DetalleServicioDto detalleServicio) {
        this.detalleServicio = detalleServicio;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetalleServicioDto detalleServicio) {
        this.detalleServicio = detalleServicio;
        return super.eliminar();
    }

    public ArrayList<DetalleServicioDto> listarPorIdCita(int idcita) {
        String sql = generarSQLparaDetalleServicioPorIdCita();
        return (ArrayList<DetalleServicioDto>) super.listarTodos(sql, this::IncluirEnSQLIdCita, idcita, this::AgregarMiPropioObjetoALaLista2);

    }

    private void instanciarMiPropioObjeto() throws SQLException {
        this.detalleServicio = new DetalleServicioDto();

        // --- Datos del detalle de servicio ---
        this.detalleServicio.setDetalleServicioId(
                this.resultSet.getInt("DETALLE_SERVICIO_ID")
        );
        
        // Cita: mantenemos tu lógica de obtener la cita desde su DAO
        CitaAtencionDto cita= new CitaAtencionDto();
        cita.setCitaId(this.resultSet.getInt("CITA_ID"));
        this.detalleServicio.setCita(
                cita
        );

        // --- Servicio (anidado) ---
        ServicioDto servicio = new ServicioDto();
        servicio.setServicioId(
                this.resultSet.getInt("SERVICIO_ID")
        );

        TipoServicioDto tp = new TipoServicioDto();
        tp.setTipoServicioId(
                this.resultSet.getInt("TIPO_SERVICIO_ID")
        );
        servicio.setTipoServicio(tp);

        servicio.setNombre(
                this.resultSet.getString("SERVICIO_NOMBRE")
        );
        servicio.setDescripcion(
                this.resultSet.getString("SERVICIO_DESCRIPCION")
        );
        servicio.setCosto(
                this.resultSet.getDouble("SERVICIO_COSTO")
        );
        servicio.setEstado(
                this.resultSet.getString("SERVICIO_ESTADO")
        );
        servicio.setActivo(
                this.resultSet.getInt("SERVICIO_ACTIVO") == 1
        );

        this.detalleServicio.setServicio(servicio);

        // --- Datos propios del detalle ---
        this.detalleServicio.setDescripcion(
                this.resultSet.getString("DETALLE_DESCRIPCION")
        );
        this.detalleServicio.setCosto(
                this.resultSet.getDouble("DETALLE_COSTO")
        );
        this.detalleServicio.setActivo(
                this.resultSet.getInt("DETALLE_ACTIVO") == 1
        );
    }

    public void IncluirEnSQLIdCita(Object citaIdVoid) {
        int citaId = (int) citaIdVoid;
        try {
            this.statement.setInt(1, citaId);
            System.out.println(this.statement);
        } catch (SQLException ex) {
            Logger.getLogger(DetalleServicioDaoImpl.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    public void AgregarMiPropioObjetoALaLista2(Object listavoid) {
    List<DetalleServicioDto> lista = (List<DetalleServicioDto>) listavoid;
    try {
        this.instanciarMiPropioObjeto();
    } catch (SQLException ex) {
        Logger.getLogger(DetalleServicioDaoImpl.class.getName())
              .log(Level.SEVERE, null, ex);
    }
    lista.add(this.detalleServicio);
}

    public String generarSQLparaDetalleServicioPorIdCita() {
        String sql
                = "SELECT "
                + "  ds.DETALLE_SERVICIO_ID, "
                + "  ds.CITA_ID, "
                + "  ds.SERVICIO_ID, "
                + "  ds.DESCRIPCION AS DETALLE_DESCRIPCION, "
                + "  ds.COSTO AS DETALLE_COSTO, "
                + "  ds.ACTIVO AS DETALLE_ACTIVO, "
                + "  s.TIPO_SERVICIO_ID, "
                + "  s.NOMBRE AS SERVICIO_NOMBRE, "
                + "  s.DESCRIPCION AS SERVICIO_DESCRIPCION, "
                + "  s.COSTO AS SERVICIO_COSTO, "
                + "  s.ESTADO AS SERVICIO_ESTADO, "
                + "  s.ACTIVO AS SERVICIO_ACTIVO "
                + "FROM DETALLES_SERVICIO ds "
                + "INNER JOIN SERVICIOS s ON ds.SERVICIO_ID = s.SERVICIO_ID "
                + "WHERE ds.CITA_ID = ? "
                + "AND ds.ACTIVO = 1";
        return sql;
    }

}
