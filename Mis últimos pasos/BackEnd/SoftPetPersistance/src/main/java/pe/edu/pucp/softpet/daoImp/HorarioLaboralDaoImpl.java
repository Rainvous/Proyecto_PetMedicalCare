package pe.edu.pucp.softpet.daoImp;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.dao.HorarioLaboralDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.personas.HorarioLaboralDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoLaboral;

public class HorarioLaboralDaoImpl extends DaoBaseImpl implements HorarioLaboralDao {

    private HorarioLaboralDto horario;

    public HorarioLaboralDaoImpl() {
        super("HORARIOS_LABORALES");
        this.horario = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("HORARIO_LABORAL_ID", true, true));
        this.listaColumnas.add(new Columna("VETERINARIO_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("HORA_INICIO", false, false));
        this.listaColumnas.add(new Columna("HORA_FIN", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.horario.getVeterinario().getVeterinarioId());
        this.statement.setDate(2, this.horario.getFecha());
        this.statement.setString(3, this.horario.getEstado().toString());
        this.statement.setDate(4, this.horario.getHoraInicio());
        this.statement.setDate(5, this.horario.getHoraFin());
        this.statement.setInt(6, this.horario.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.horario.getVeterinario().getVeterinarioId());
        this.statement.setDate(2, this.horario.getFecha());
        this.statement.setString(3, this.horario.getEstado().toString());
        this.statement.setDate(4, this.horario.getHoraInicio());
        this.statement.setDate(5, this.horario.getHoraFin());
        this.statement.setInt(6, this.horario.getActivo() ? 1 : 0);

        this.statement.setInt(7, this.horario.getHorarioLaboralId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.horario.getHorarioLaboralId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.horario.getHorarioLaboralId());
    }

    // -------------------------------------------------------------------------
    // AQUÍ ESTÁ LA CORRECCIÓN DEL ERROR DE LA IMAGEN
    // -------------------------------------------------------------------------
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.horario = new HorarioLaboralDto();
        this.horario.setHorarioLaboralId(this.resultSet.getInt("HORARIO_LABORAL_ID"));
        
        VeterinarioDto vet = new VeterinarioDto();
        vet.setVeterinarioId(this.resultSet.getInt("VETERINARIO_ID"));
        this.horario.setVeterinario(vet);
        
        this.horario.setFecha(this.resultSet.getDate("FECHA"));
        this.horario.setEstado(EstadoLaboral.valueOf(this.resultSet.getString("ESTADO")));
        
        // --- FIX: Convertir Timestamp (BD) a java.sql.Date (DTO) ---
        Timestamp tsInicio = this.resultSet.getTimestamp("HORA_INICIO");
        if (tsInicio != null) {
            // Creamos un Date usando los milisegundos del Timestamp para no perder la hora
            this.horario.setHoraInicio(new java.sql.Date(tsInicio.getTime()));
        }

        Timestamp tsFin = this.resultSet.getTimestamp("HORA_FIN");
        if (tsFin != null) {
            this.horario.setHoraFin(new java.sql.Date(tsFin.getTime()));
        }
        
        this.horario.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }
    
//    @Override
//    protected void instanciarObjetoDelResultSet() throws SQLException {
//        this.horario = new HorarioLaboralDto();
//        this.horario.setHorarioLaboralId(this.resultSet.getInt("HORARIO_LABORAL_ID"));
//        VeterinarioDto vet = new VeterinarioDto();
//        vet.setVeterinarioId(this.resultSet.getInt("VETERINARIO_ID"));
//        this.horario.setVeterinario(vet);
//        this.horario.setFecha(this.resultSet.getDate("FECHA"));
//        this.horario.setEstado(EstadoLaboral.valueOf(this.resultSet.getString("ESTADO")));
//        // Usamos getTimestamp para obtener la hora precisa
//        this.horario.setHoraInicio(this.resultSet.getTimestamp("HORA_INICIO"));
//        this.horario.setHoraFin(this.resultSet.getTimestamp("HORA_FIN"));
//        this.horario.setActivo(this.resultSet.getInt("ACTIVO") == 1);
//    }

//    protected String sacarEstado(String data) {
//        if (data.equals(EstadoLaboral.DISPONIBLE.toString()) || data.equals("disponible")) {
//            return EstadoLaboral.DISPONIBLE.toString();
//        } else {
//            return EstadoLaboral.NO_DISPONIBLE.toString();
//        }
//    }
    @Override
    protected void limpiarObjetoDelResultSet() {
        this.horario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.horario);
    }

    @Override
    public Integer insertar(HorarioLaboralDto entity) {
        this.horario = entity;
        return super.insertar();
    }

    @Override
    public HorarioLaboralDto obtenerPorId(Integer id) {
        this.horario = new HorarioLaboralDto();
        this.horario.setHorarioLaboralId(id);
        super.obtenerPorId();
        return this.horario;
    }

    @Override
    public ArrayList<HorarioLaboralDto> listarTodos() {
        return (ArrayList<HorarioLaboralDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(HorarioLaboralDto entity) {
        this.horario = entity;
        return super.modificar();
    }

    @Override
    public Integer eliminar(HorarioLaboralDto entity) {
        this.horario = entity;
        return super.eliminar();
    }
    
    // =====================================================================
    //  1. GUARDAR HORARIO (USANDO EL PADRE DaoBaseImpl)
    // =====================================================================
    // Este método es llamado por el BO dentro del bucle del rango.
    // Utiliza el método 'ejecutarProcedimientoAlmacenado' del padre.
    @Override
    public int guardarHorario(HorarioLaboralDto horarioDto) {
        
        // 1. Definimos el SQL del Procedure
        String sql = "{call sp_guardar_horario_laboral(?,?,?,?,?)}";
        
        // 2. Llamamos al método del padre pasando:
        //    - SQL
        //    - El método privado que setea los parámetros (Consumer)
        //    - El objeto con los datos (DTO)
        //    - false (porque no necesitamos abrir una nueva transacción compleja aquí, el SP es atómico)
        super.ejecutarProcedimientoAlmacenado(
                sql, 
                this::incluirParametrosParaGuardarHorario, 
                horarioDto, 
                false
        );
        
        return 1; // Retornamos éxito
    }

    // Este es el "Consumer" que usa el padre para llenar los ? del PreparedStatement
    private void incluirParametrosParaGuardarHorario(Object obj) {
        HorarioLaboralDto h = (HorarioLaboralDto) obj;
        try {
            // Accedemos a 'this.statement' que es protegido en DaoBaseImpl
            this.statement.setInt(1, h.getVeterinario().getVeterinarioId());
            this.statement.setDate(2, h.getFecha());
            
            // CLAVE: Usamos setTimestamp para mandar la fecha CON HORA (HH:mm:ss)
            // Convertimos de java.util.Date a java.sql.Timestamp
            this.statement.setTimestamp(3, new Timestamp(h.getHoraInicio().getTime()));
            this.statement.setTimestamp(4, new Timestamp(h.getHoraFin().getTime()));
            
            this.statement.setInt(5, h.getActivo() ? 1 : 0);
            
        } catch (SQLException ex) {
            Logger.getLogger(HorarioLaboralDaoImpl.class.getName()).log(Level.SEVERE, "Error al setear parámetros de horario", ex);
        }
    }

    // =====================================================================
    //  2. LISTAR POR VETERINARIO (USANDO EL PADRE DaoBaseImpl)
    // =====================================================================
    @Override
    public ArrayList<HorarioLaboralDto> listarPorVeterinario(Integer veterinarioId) {
        // 1. Reutilizamos la lógica del padre para generar el SELECT base
        String sql = super.generarSQLParaListarTodos();
        
        // 2. Concatenamos el filtro específico
        sql = sql.concat(" WHERE VETERINARIO_ID = ? AND ACTIVO = 1");

        // 3. Usamos el listarTodos genérico del padre, pasando nuestro Consumer para el ID
        return (ArrayList<HorarioLaboralDto>) super.listarTodos(
                sql,
                this::incluirParametroVeterinarioId, // Referencia al método privado de abajo
                veterinarioId
        );
    }

    private void incluirParametroVeterinarioId(Object objetoParametros) {
        Integer vetId = (Integer) objetoParametros;
        try {
            this.statement.setInt(1, vetId);
        } catch (SQLException ex) {
            Logger.getLogger(HorarioLaboralDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
