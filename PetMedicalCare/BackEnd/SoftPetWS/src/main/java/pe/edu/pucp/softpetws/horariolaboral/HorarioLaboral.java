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
}
