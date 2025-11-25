package pe.edu.pucp.softpetws.horariolaboral;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.HorarioLaboralBo;
import pe.edu.pucp.softpet.dto.personas.HorarioLaboralDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoLaboral;

@WebService(serviceName = "Horarios")
public class HorarioLaboral {

    private final HorarioLaboralBo horarioBo = new HorarioLaboralBo();

    // =========================
    //       INSERTAR
    // =========================
    @WebMethod(operationName = "insertar_horario")
    public Integer insertar(
            @WebParam(name = "veterinarioId") int veterinarioId,
            @WebParam(name = "fecha") String fecha,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "horaInicio") String horaInicio,
            @WebParam(name = "horaFin") String horaFin,
            @WebParam(name = "activo") boolean activo) {

        try {
//            SimpleDateFormat dfFecha = new SimpleDateFormat("yyyyMMdd");
//            java.util.Date utilFecha = dfFecha.parse(fechaRegistro);

            // yyyyMMdd
            long millis = System.currentTimeMillis();
            Date fechaSql = new Date(millis);
            Date fechaHoraInicioSql = parseFechaHora(horaInicio); // yyyyMMddHHmmss
            Date fechaHoraFinSql = parseFechaHora(horaFin);       // yyyyMMddHHmmss
            EstadoLaboral estadoEnum = parseEstado(estado);

            return horarioBo.insertar(
                    veterinarioId,
                    fechaSql,
                    estadoEnum,
                    fechaHoraInicioSql,
                    fechaHoraFinSql,
                    activo
            );

        } catch (ParseException e) {
            throw new WebServiceException("Error al convertir fechas: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new WebServiceException(
                    "Estado de cita inválido (PROGRAMADA, ATENDIDA, CANCELADA): " + estado, e);
        }
    }

    // =========================
    //       MODIFICAR
    // =========================
    @WebMethod(operationName = "modificar_horario")
    public Integer modificar(@WebParam(name = "horarioId") int horarioId,
            @WebParam(name = "veterinarioId") int veterinarioId,
            @WebParam(name = "fecha") String fecha,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "horaInicio") String horaInicio,
            @WebParam(name = "horaFin") String horaFin,
            @WebParam(name = "activo") boolean activo) {

        try {
//            
// yyyyMMdd
            long millis = System.currentTimeMillis();
            Date fechaSql = new Date(millis);
            Date fechaHoraInicioSql = parseFechaHora(horaInicio); // yyyyMMddHHmmss
            Date fechaHoraFinSql = parseFechaHora(horaFin);       // yyyyMMddHHmmss
            EstadoLaboral estadoEnum = parseEstado(estado);

            return horarioBo.modificar(horarioId,
                    veterinarioId,
                    fechaSql,
                    estadoEnum,
                    fechaHoraInicioSql,
                    fechaHoraFinSql,
                    activo
            );

        } catch (ParseException e) {
            throw new WebServiceException("Error al convertir fechas: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new WebServiceException(
                    "Estado de cita inválido (PROGRAMADA, ATENDIDA, CANCELADA): " + estado, e);
        }
    }

    // =========================
    //   OBTENER POR ID
    // =========================
    @WebMethod(operationName = "obtener_por_id")
    public HorarioLaboralDto obtenerPorId(
            @WebParam(name = "citaAtencionId") int horarioId) {

        return this.horarioBo.obtenerPorId(horarioId);
    }

    // =========================
    //    LISTAR TODOS
    // =========================
    @WebMethod(operationName = "listar_todos")
    public ArrayList<HorarioLaboralDto> listarTodos() {
        return this.horarioBo.listarTodos();
    }

    // =========================
    //   MÉTODOS PRIVADOS DE APOYO
    // =========================
    private Date parseFecha(String fechaStr) throws ParseException {
        // Formato esperado: yyyyMMdd  -> 20251210
        SimpleDateFormat dfFecha = new SimpleDateFormat("yyyyMMdd");
        java.util.Date utilFecha = dfFecha.parse(fechaStr);
        return new Date(utilFecha.getTime());
    }

    private Date parseFechaHora(String fechaHoraStr) throws ParseException {
        // Formato esperado: yyyyMMddHHmmss -> 20250911245020
        SimpleDateFormat dfFechaHora = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date utilFechaHora = dfFechaHora.parse(fechaHoraStr);
        return new Date(utilFechaHora.getTime());
    }

    private EstadoLaboral parseEstado(String estadoStr) {
        return EstadoLaboral.valueOf(estadoStr);
    }
    
    // ==========================================
    //  NUEVO WEB METHOD: REGISTRAR RANGO
    // ==========================================
    @WebMethod(operationName = "registrar_horario_rango")
    public Integer registrarHorarioRango(
            @WebParam(name = "veterinarioId") int veterinarioId,
            @WebParam(name = "fechaInicio") String fechaInicioStr, // Formato: "yyyy-MM-dd"
            @WebParam(name = "fechaFin") String fechaFinStr,       // Formato: "yyyy-MM-dd"
            @WebParam(name = "horaInicio") String horaInicio,      // Formato: "HH:mm"
            @WebParam(name = "horaFin") String horaFin,            // Formato: "HH:mm"
            @WebParam(name = "activo") boolean activo) {
        
        try {
            // Parseo de fechas
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            // Convertimos de String a java.util.Date y luego a java.sql.Date
            java.util.Date uInicio = sdf.parse(fechaInicioStr);
            java.util.Date uFin = sdf.parse(fechaFinStr);
            
            Date fInicio = new Date(uInicio.getTime());
            Date fFin = new Date(uFin.getTime());

            return horarioBo.registrarHorarioRango(veterinarioId, fInicio, fFin, horaInicio, horaFin, activo);
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    // ==========================================
    //  NUEVO WEB METHOD: LISTAR POR VETERINARIO
    // ==========================================
    @WebMethod(operationName = "listar_por_veterinario")
    public ArrayList<HorarioLaboralDto> listarPorVeterinario(@WebParam(name = "veterinarioId") int veterinarioId) {
        return horarioBo.listarPorVeterinario(veterinarioId);
    }
}
