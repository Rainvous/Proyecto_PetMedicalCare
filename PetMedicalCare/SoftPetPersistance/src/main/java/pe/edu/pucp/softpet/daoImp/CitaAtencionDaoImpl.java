package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dao.CitaAtencionDao;

/**
 *
 * @author marti
 */
public class CitaAtencionDaoImpl extends DaoBaseImpl implements CitaAtencionDao {

    private CitaAtencionDto citaAtencion;

    public CitaAtencionDaoImpl() {
        super("CITAS_ATENCION");
        this.citaAtencion = null;
        this.retornarLlavePrimaria = true;
        this.usuario = "user_backend";
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("CITA_ID", true, true));
        this.listaColumnas.add(new Columna("OBSERVACION", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_INICIO", false, false));
        this.listaColumnas.add(new Columna("FECHA_REGISTRO", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_FIN", false, false));
        this.listaColumnas.add(new Columna("MONTO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("PESO_MASCOTA", false, false));
        this.listaColumnas.add(new Columna("VETERINARIO_ID", false, false));
        this.listaColumnas.add(new Columna("MASCOTA_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.citaAtencion.getObservacion());
        this.statement.setDate(2, this.citaAtencion.getFechaHoraInicio());
        this.statement.setDate(3, this.citaAtencion.getFechaRegistro());
        this.statement.setDate(4, this.citaAtencion.getFechaHoraFin());
        this.statement.setDouble(5, this.citaAtencion.getMonto());
        this.statement.setInt(6, this.citaAtencion.getActivo() ? 1 : 0);
        this.statement.setString(7, this.citaAtencion.getPeso());
        this.statement.setInt(8, this.citaAtencion.getVeterinario().getVeterinarioId());
        this.statement.setInt(9, this.citaAtencion.getMascota().getMascotaId());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.citaAtencion.getObservacion());
        this.statement.setDate(2, this.citaAtencion.getFechaHoraInicio());
        this.statement.setDate(3, this.citaAtencion.getFechaRegistro());
        this.statement.setDate(4, this.citaAtencion.getFechaHoraFin());
        this.statement.setDouble(5, this.citaAtencion.getMonto());
        this.statement.setInt(6, this.citaAtencion.getActivo() ? 1 : 0);
        this.statement.setString(7, this.citaAtencion.getPeso());
        this.statement.setInt(8, this.citaAtencion.getVeterinario().getVeterinarioId());
        this.statement.setInt(9, this.citaAtencion.getMascota().getMascotaId());
        
        this.statement.setInt(10, this.citaAtencion.getCitaId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.citaAtencion.getCitaId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.citaAtencion.getCitaId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.citaAtencion = new CitaAtencionDto();
        this.citaAtencion.setCitaId(this.resultSet.getInt("CITA_ID"));
        this.citaAtencion.setObservacion(this.resultSet.getString("OBSERVACION"));
        this.citaAtencion.setFechaHoraInicio(this.resultSet.getDate("FECHA_HORA_INICIO"));
        this.citaAtencion.setFechaRegistro(this.resultSet.getDate("FECHA_REGISTRO"));
        this.citaAtencion.setFechaHoraFin(this.resultSet.getDate("FECHA_HORA_FIN"));
        this.citaAtencion.setMonto(this.resultSet.getDouble("MONTO"));
        this.citaAtencion.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        this.citaAtencion.setPeso(this.resultSet.getString("PESO_MASCOTA"));
        this.citaAtencion.setVeterinario(new VeterinarioDaoImpl().
                obtenerPorId(this.resultSet.getInt("VETERINARIO_ID")));
        this.citaAtencion.setMascota(new MascotaDaoImpl().
                obtenerPorId(this.resultSet.getInt("MASCOTA_ID")));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.citaAtencion = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.citaAtencion);
    }

    @Override
    public Integer insertar(CitaAtencionDto citaAtencion) {
        this.citaAtencion = citaAtencion;
        return super.insertar();
    }

    @Override
    public CitaAtencionDto obtenerPorId(Integer citaAtencionId) {
        this.citaAtencion = new CitaAtencionDto();
        this.citaAtencion.setCitaId(citaAtencionId);
        super.obtenerPorId();
        return this.citaAtencion;
    }

    @Override
    public ArrayList<CitaAtencionDto> listarTodos() {
        return (ArrayList<CitaAtencionDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(CitaAtencionDto citaAtencion) {
        this.citaAtencion = citaAtencion;
        return super.modificar();
    }

    @Override
    public Integer eliminar(CitaAtencionDto citaAtencion) {
        this.citaAtencion = citaAtencion;
        return super.eliminar();
    }
}
