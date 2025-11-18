package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dao.CitaAtencionDao;
import pe.edu.pucp.softpet.dto.citas.CitaProgramadaDto;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoCita;

public class CitaAtencionDaoImpl extends DaoBaseImpl implements CitaAtencionDao {

    private CitaAtencionDto citaAtencion;
    private CitaProgramadaDto citaProg;

    public CitaAtencionDaoImpl() {
        super("CITAS_ATENCION");
        this.citaAtencion = null;
        citaProg = null;
        this.retornarLlavePrimaria = true;
        this.usuario = "user_backend";
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("CITA_ID", true, true));
        this.listaColumnas.add(new Columna("VETERINARIO_ID", false, false));
        this.listaColumnas.add(new Columna("MASCOTA_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_REGISTRO", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_INICIO", false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_FIN", false, false));
        this.listaColumnas.add(new Columna("PESO_MASCOTA", false, false));
        this.listaColumnas.add(new Columna("MONTO", false, false));
        this.listaColumnas.add(new Columna("ESTADO_CITA", false, false));
        this.listaColumnas.add(new Columna("OBSERVACION", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        this.statement.setInt(1, this.citaAtencion.getVeterinario().getVeterinarioId());
        this.statement.setInt(2, this.citaAtencion.getMascota().getMascotaId());
        this.statement.setDate(3, this.citaAtencion.getFechaRegistro());
        this.statement.setTimestamp(4, this.citaAtencion.getFechaHoraInicio());
        this.statement.setTimestamp(5, this.citaAtencion.getFechaHoraFin());
        this.statement.setDouble(6, this.citaAtencion.getPesoMascota());
        this.statement.setDouble(7, this.citaAtencion.getMonto());
        this.statement.setString(8, this.citaAtencion.getEstado().toString());
        this.statement.setString(9, this.citaAtencion.getObservacion());
        this.statement.setInt(10, this.citaAtencion.getActivo() ? 1 : 0);
    }

//    protected String retornaEstadoCita(String estado) {
//        if (this.citaAtencion.getEstado().equals(EstadoCita.ATENDIDA.toString())) {
//            estado = EstadoCita.ATENDIDA.toString();
//        } else if (this.citaAtencion.getEstado().equals(EstadoCita.CANCELADA.toString())) {
//            estado = EstadoCita.CANCELADA.toString();
//        } else {
//            estado = EstadoCita.PROGRAMADA.toString();
//        }
//        return estado;
//    }
    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.citaAtencion.getVeterinario().getVeterinarioId());
        this.statement.setInt(2, this.citaAtencion.getMascota().getMascotaId());
        this.statement.setDate(3, this.citaAtencion.getFechaRegistro());
        this.statement.setTimestamp(4, this.citaAtencion.getFechaHoraInicio());
        this.statement.setTimestamp(5, this.citaAtencion.getFechaHoraFin());
        this.statement.setDouble(6, this.citaAtencion.getPesoMascota());
        this.statement.setDouble(7, this.citaAtencion.getMonto());
        this.statement.setString(8, this.citaAtencion.getEstado().toString());
        this.statement.setString(9, this.citaAtencion.getObservacion());
        this.statement.setInt(10, this.citaAtencion.getActivo() ? 1 : 0);

        this.statement.setInt(11, this.citaAtencion.getCitaId());
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
        VeterinarioDto vet = new VeterinarioDto();
        MascotaDto mas = new MascotaDto();
        vet.setVeterinarioId(this.resultSet.getInt("VETERINARIO_ID"));
        mas.setMascotaId(this.resultSet.getInt("MASCOTA_ID"));
        this.citaAtencion.setCitaId(this.resultSet.getInt("CITA_ID"));
        this.citaAtencion.setVeterinario(vet);
        this.citaAtencion.setMascota(mas);
        this.citaAtencion.setFechaRegistro(this.resultSet.getDate("FECHA_REGISTRO"));
        this.citaAtencion.setFechaHoraInicio(this.resultSet.getTimestamp("FECHA_HORA_INICIO"));
        this.citaAtencion.setFechaHoraFin(this.resultSet.getTimestamp("FECHA_HORA_FIN"));
        this.citaAtencion.setPesoMascota(this.resultSet.getDouble("PESO_MASCOTA"));
        this.citaAtencion.setMonto(this.resultSet.getDouble("MONTO"));
        this.citaAtencion.setEstado(EstadoCita.valueOf(this.resultSet.getString("ESTADO_CITA")));
        this.citaAtencion.setObservacion(this.resultSet.getString("OBSERVACION"));
        this.citaAtencion.setActivo(this.resultSet.getInt("ACTIVO") == 1);
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

    public ArrayList<CitaAtencionDto> ListasBusquedaAvanzada(String fecha,String idVeterinario) {
        String fechaParaSP;

        // 1. Lógica de Java para decidir la fecha
        if (fecha == null || fecha.trim().isEmpty()) {

            // Si la fecha es "" o null, Java obtiene la fecha de hoy
            // (usando la zona horaria de tu máquina, que es la correcta)
            fechaParaSP = LocalDate.now().toString(); // Ej: "2025-11-10"

        } else {
            // Si la fecha SÍ viene, se usa esa.
            fechaParaSP = fecha;
        }

        // 2. Llama al SP
        Map<Integer, Object> parametrosEntrada = new HashMap<>();

        // Aquí siempre pasas una fecha válida ("2025-11-10")
        // ya sea la que escribió el usuario o la que calculó Java.
        parametrosEntrada.put(1, fechaParaSP);
        parametrosEntrada.put(2, idVeterinario);

        return (ArrayList<CitaAtencionDto>) super.ejecutarProcedimientoLectura("sp_listar_citas_por_fecha", parametrosEntrada);
    }

    @Override
    public ArrayList<CitaAtencionDto> listarPorIdMascota(Integer mascotaId) {

        // 1. Obtenemos el SQL (cumpliendo la solicitud de "método aparte")
        String sql = this.generarSQLParaListarPorIdMascota();

        // 2. El ID de la mascota es el parámetro
        Object parametros = mascotaId;

        // 3. Llamamos al método listarTodos de la clase base
        return (ArrayList<CitaAtencionDto>) super.listarTodos(sql,
                this::incluirValorDeParametrosParaListarPorIdMascota,
                parametros);
    }

    private String generarSQLParaListarPorIdMascota() {
        // 1. Obtenemos el SQL base: "SELECT ..., ..., FROM CITAS_ATENCION"
        String sql = super.generarSQLParaListarTodos();

        // 2. Añadimos el filtro WHERE
        sql = sql.concat(" WHERE MASCOTA_ID = ?");

        return sql;
    }

    private void incluirValorDeParametrosParaListarPorIdMascota(Object objetoParametros) {
        // Casteamos el objeto de parámetros a su tipo original
        Integer mascotaId = (Integer) objetoParametros;
        try {
            // Asignamos el ID al primer '?' en el SQL
            this.statement.setInt(1, mascotaId);
        } catch (SQLException ex) {
            Logger.getLogger(CitaAtencionDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ///FUNCION PARA GESTIONAR CITAS
    /// SE IMPLENTADO UNA FUNCION GENERICA
    ///Ahora un daoImplBase puede recibir cualquier objeto x,y,z... 
    ///Puedes devolver Arraylist de otros objetos!
      private void InstanciarObjetoCitaProgramada() throws SQLException {
          //Paso 1: agregar el objeto que quieres y crea tu InstanciarObjeto a lo melgar
        citaProg = new CitaProgramadaDto();
        citaProg.setFecha(this.resultSet.getString(1)); //en este caso se optó por el numero por si no sabemos 
        //el nombre de cada fila
        citaProg.setEstaProgramada(this.resultSet.getBoolean(2));
    }

    private void AgregarObjetoCitaProgramadaALaLista(Object objetoParametros) {
        //Paso 2: Crea tu puntero a funcion este recibirá una lista
        //No te preocupes la lista a pesar de ser casteada guardará la informacion del objeto
        List<CitaProgramadaDto> lista = (List<CitaProgramadaDto>) objetoParametros;
        try {
            this.InstanciarObjetoCitaProgramada();
            lista.add(this.citaProg);
        } catch (SQLException ex) {
            System.err.println("No se instancio bien el objeto");
        }

    }
    public ArrayList<CitaProgramadaDto> ListarProgramadas(int idVeterinario, Date fechaDeCitas) {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        parametrosEntrada.put(1,idVeterinario );
        parametrosEntrada.put(2, fechaDeCitas);
        String sql = "sp_generar_horario_disponible";
        //pasas de parametro tu Void (*) () -> puntero a funcion y listo puedes instanciar cualquier objeto en un dao (USO SOLO PARA PROCEDURES)
        return (ArrayList<CitaProgramadaDto>) this.ejecutarProcedimientoLectura(sql, parametrosEntrada, this::AgregarObjetoCitaProgramadaALaLista);

    }
    
    public ArrayList<CitaAtencionDto> ListasCitasPorMascotasYFechas(
            Integer idMascota,
            String fecha)
    {
        String fechaParaSP;

        // 1. Lógica de Java para decidir la fecha
        if (fecha == null || fecha.trim().isEmpty()) {

            // Si la fecha es "" o null, Java obtiene la fecha de hoy
            // (usando la zona horaria de tu máquina, que es la correcta)
            fechaParaSP = LocalDate.now().toString(); // Ej: "2025-11-10"

        } else {
            // Si la fecha SÍ viene, se usa esa.
            fechaParaSP = fecha;
        }

        // 2. Llama al SP
        Map<Integer, Object> parametrosEntrada = new HashMap<>();

        // Aquí siempre pasas una fecha válida ("2025-11-10")
        // ya sea la que escribió el usuario o la que calculó Java.
        parametrosEntrada.put(1,idMascota);
        parametrosEntrada.put(2,fechaParaSP );

        return (ArrayList<CitaAtencionDto>) super.ejecutarProcedimientoLectura("sp_listar_citas_por_mascota_y_fecha", parametrosEntrada);
    }
}
