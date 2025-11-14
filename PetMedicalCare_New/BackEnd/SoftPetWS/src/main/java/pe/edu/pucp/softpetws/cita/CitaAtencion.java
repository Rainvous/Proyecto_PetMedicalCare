package pe.edu.pucp.softpetws.cita;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.CitaAtencionBo;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoCita;

@WebService(serviceName = "CitasAtencion")
public class CitaAtencion {

    private final CitaAtencionBo citaBo = new CitaAtencionBo();

    // =========================
    //       INSERTAR
    // =========================
    @WebMethod(operationName = "insertar_cita")
    public Integer insertar_cita(
            @WebParam(name = "veterinarioId") int veterinarioId,
            @WebParam(name = "mascotaId") int mascotaId,
            @WebParam(name = "fechaHoraInicio") String fechaHoraInicio,
            @WebParam(name = "fechaHoraFin") String fechaHoraFin,
            @WebParam(name = "pesoMascota") double pesoMascota,
            @WebParam(name = "monto") double monto,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "activo") boolean activo) {

        try {
//            SimpleDateFormat dfFecha = new SimpleDateFormat("yyyyMMdd");
//            java.util.Date utilFecha = dfFecha.parse(fechaRegistro);

            // yyyyMMdd
            long millis = System.currentTimeMillis();
            Date fechaRegistroSql = new Date(millis);
            Timestamp fechaHoraInicioTs = parseFechaHora(fechaHoraInicio); // yyyyMMddHHmmss
            Timestamp fechaHoraFinTs = parseFechaHora(fechaHoraFin);       // yyyyMMddHHmmss
            EstadoCita estadoEnum = parseEstado(estado);

            return citaBo.insertar(
                    veterinarioId,
                    mascotaId,
                    fechaRegistroSql,
                    fechaHoraInicioTs,
                    fechaHoraFinTs,
                    pesoMascota,
                    monto,
                    estadoEnum,
                    observacion,
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
    @WebMethod(operationName = "modificar_cita")
    public Integer modificar_cita(
            @WebParam(name = "citaId") int citaId,
            @WebParam(name = "veterinarioId") int veterinarioId,
            @WebParam(name = "mascotaId") int mascotaId,
            @WebParam(name = "fechaHoraInicio") String fechaHoraInicio,
            @WebParam(name = "fechaHoraFin") String fechaHoraFin,
            @WebParam(name = "pesoMascota") double pesoMascota,
            @WebParam(name = "monto") double monto,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "observacion") String observacion,
            @WebParam(name = "activo") boolean activo) {

        try {
            long millis = System.currentTimeMillis();
            Date fechaRegistroSql = new Date(millis);
            //java.util.Date utilFecha = dfFecha.parse(fechaRegistro);
            // Date fechaRegistroSql = new Date(utilFecha.getTime());        // yyyyMMdd
            Timestamp fechaHoraInicioTs = parseFechaHora(fechaHoraInicio);  // yyyyMMddHHmmss
            Timestamp fechaHoraFinTs = parseFechaHora(fechaHoraFin);        // yyyyMMddHHmmss
            EstadoCita estadoEnum = parseEstado(estado);

            return citaBo.modificar(
                    citaId,
                    veterinarioId,
                    mascotaId,
                    fechaRegistroSql,
                    fechaHoraInicioTs,
                    fechaHoraFinTs,
                    pesoMascota,
                    monto,
                    estadoEnum,
                    observacion,
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
    //  BÚSQUEDA AVANZADA
    // =========================
    @WebMethod(operationName = "listas_busqueda_avanzada")
    public ArrayList<CitaAtencionDto> listasBusquedaAvanzada(
            @WebParam(name = "fecha") String fecha) {

        // aquí tu BO ya sabe qué hacer con la fecha (string)
        return (ArrayList<CitaAtencionDto>) citaBo.ListasBusquedaAvanzada(
                fecha == null ? "" : fecha
        );
    }

    // =========================
    //   OBTENER POR ID
    // =========================
    @WebMethod(operationName = "obtener_por_id")
    public CitaAtencionDto obtenerPorId(
            @WebParam(name = "citaAtencionId") int citaAtencionId) {

        return this.citaBo.obtenerPorId(citaAtencionId);
    }

    // =========================
    //    LISTAR TODOS
    // =========================
    @WebMethod(operationName = "listar_todos")
    public ArrayList<CitaAtencionDto> listarTodos() {
        return this.citaBo.listarTodos();
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

    private Timestamp parseFechaHora(String fechaHoraStr) throws ParseException {
        // Formato esperado: yyyyMMddHHmmss -> 20250911245020
        SimpleDateFormat dfFechaHora = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date utilFechaHora = dfFechaHora.parse(fechaHoraStr);
        return new Timestamp(utilFechaHora.getTime());
    }

    private EstadoCita parseEstado(String estadoStr) {
        // Espera exactamente los nombres del enum: PROGRAMADA, ATENDIDA, CANCELADA...
        return EstadoCita.valueOf(estadoStr);
    }
}
