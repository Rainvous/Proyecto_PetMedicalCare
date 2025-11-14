
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import pe.edu.pucp.softpet.bo.CitaAtencionBo;
import pe.edu.pucp.softpet.dto.util.enums.EstadoCita;

@WebService(serviceName = "CitaAtencionWS")
public class CitaAtencion {

    private final CitaAtencionBo citaBo = new CitaAtencionBo();

    @WebMethod(operationName = "insertar_cita")
    public Integer insertar(int veterinarioId, int mascotaId, String fechaRegistro,
            String fechaHoraInicio, String fechaHoraFin, double pesoMascota,
            double monto, String estado, String observacion, boolean activo) {

        try {
            // =========================
            // 1. Formatos esperados
            // Fecha:       yyyyMMdd        -> 20251210
            // Fecha/Hora:  yyyyMMddHHmmss  -> 20250911245020
            // =========================
            SimpleDateFormat dfFecha = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dfFechaHora = new SimpleDateFormat("yyyyMMddHHmmss");

            // =========================
            // 2. String -> java.sql.Date
            // =========================
            java.util.Date utilFecha = dfFecha.parse(fechaRegistro);
            Date fechaRegistroSql = new Date(utilFecha.getTime());

            // =========================
            // 3. String -> java.sql.Timestamp
            // =========================
            java.util.Date utilInicio = dfFechaHora.parse(fechaHoraInicio);
            java.util.Date utilFin = dfFechaHora.parse(fechaHoraFin);

            Timestamp fechaHoraInicioTs = new Timestamp(utilInicio.getTime());
            Timestamp fechaHoraFinTs = new Timestamp(utilFin.getTime());

            // =========================
            // 4. String -> Enum EstadoCita
            //    (ej: "PENDIENTE", "ATENDIDA", etc.)
            // =========================
            EstadoCita estadoEnum = EstadoCita.valueOf(estado);

            // =========================
            // 5. Llamada al BO real
            // =========================
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
            // Error al convertir las fechas
            throw new WebServiceException("Error al convertir fechas: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            // Error al convertir el enum EstadoCita
            throw new WebServiceException("Estado de cita inválido(    PROGRAMADA,"
                    + "    ATENDIDA,"
                    + "    CANCELADA) : " + estado, e);
        }
    }
}
