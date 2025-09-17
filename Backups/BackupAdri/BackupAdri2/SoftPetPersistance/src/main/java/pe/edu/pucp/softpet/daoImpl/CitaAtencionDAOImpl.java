package pe.edu.pucp.softpet.daoImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.CitaAtencionDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.actoresdto.MascotasDTO;
import pe.edu.pucp.softpet.model.actoresdto.VeterinariosDTO;
import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;

/**
 *
 * @author ferro
 */
public class CitaAtencionDAOImpl extends DAOImplBase implements CitaAtencionDAO {

    private CitaAtencionDTO citaAtencion;
    
    public CitaAtencionDAOImpl() {

        super("CITA_ATENCION");
        this.citaAtencion = null;
        this.retornarLlavePrimaria = true;
    }

    public CitaAtencionDAOImpl(String nombre_tabla) {

        super(nombre_tabla);
        this.citaAtencion = null;
        this.retornarLlavePrimaria = true;
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("CITA_ID", true, true));
        this.listaColumnas.add(new Columna("MASCOTA_ID", false, false));
        this.listaColumnas.add(new Columna("VETERINARIO_ID", false, false));
        this.listaColumnas.add(new Columna("TRATAMIENTO", false, false));
        this.listaColumnas.add(new Columna("OBSERVACION", false, false));
        this.listaColumnas.add(new Columna("FECHA_REGISTRO", false, false));
        this.listaColumnas.add(new Columna("HORA_INICIO", false, false));
        this.listaColumnas.add(new Columna("HORA_FIN", false, false));
        this.listaColumnas.add(new Columna("MONTO", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setObject(1, this.citaAtencion.getMascota());
        this.statement.setObject(2, this.citaAtencion.getVeterinario());
        this.statement.setString(3, this.citaAtencion.getTratamiento());
        //this.statement.setString(4, this.citaAtencion.getObservacion());
        this.statement.setDate(5, (Date) this.citaAtencion.getFecha_registro());
        this.statement.setTime(6, this.citaAtencion.getHora_inicio());
        this.statement.setTime(7, this.citaAtencion.getHora_fin());
        this.statement.setDouble(8, this.citaAtencion.getMonto());
        this.statement.setString(9, this.citaAtencion.getEstado());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        this.statement.setObject(1, this.citaAtencion.getMascota());
        this.statement.setObject(2, this.citaAtencion.getVeterinario());
        this.statement.setString(3, this.citaAtencion.getTratamiento());
        //this.statement.setString(4, this.citaAtencion.getObservacion());
        this.statement.setDate(5, (Date) this.citaAtencion.getFecha_registro());
        this.statement.setTime(6, this.citaAtencion.getHora_inicio());
        this.statement.setTime(7, this.citaAtencion.getHora_fin());
        this.statement.setDouble(8, this.citaAtencion.getMonto());
        this.statement.setString(9, this.citaAtencion.getEstado());
        
        this.statement.setInt(10, this.citaAtencion.getCita_id());
    }

    @Override
    public Integer insertar(CitaAtencionDTO citaAtencion) {

        this.citaAtencion = citaAtencion;
        return super.insertar();
    }

    @Override
    public CitaAtencionDTO obtenerPorId(Integer citaAtencionId) {
        this.citaAtencion = new CitaAtencionDTO();
        this.citaAtencion.setCita_id(citaAtencionId);
        super.obtenerPorId();
        return this.citaAtencion;
    }

    @Override
    public ArrayList<CitaAtencionDTO> listarTodos() {
        return (ArrayList<CitaAtencionDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(CitaAtencionDTO citaAtencion) {
        this.citaAtencion = citaAtencion;
        return super.modificar();
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.citaAtencion.getCita_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.citaAtencion = new CitaAtencionDTO();
        this.citaAtencion.setCita_id(this.resultSet.getInt("CITA_ID"));
        this.citaAtencion.setMascota((MascotasDTO) this.resultSet.getObject("MASCOTA_ID"));
        this.citaAtencion.setVeterinario((VeterinariosDTO) this.resultSet.getObject("VETERINARIO_ID"));
        this.citaAtencion.setTratamiento(this.resultSet.getString("TRATAMIENTO"));
        //this.citaAtencion.setObservacion(this.resultSet.getString("OBSERVACION"));
        this.citaAtencion.setFecha_registro(this.resultSet.getDate("FECHA_REGISTRO"));
        this.citaAtencion.setHora_inicio(this.resultSet.getTime("HORA_INICIO"));
        this.citaAtencion.setHora_fin(this.resultSet.getTime("HORA_FIN"));
        this.citaAtencion.setMonto(this.resultSet.getDouble("MONTO"));
        this.citaAtencion.setEstado(this.resultSet.getString("ESTADO"));
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
    
}
