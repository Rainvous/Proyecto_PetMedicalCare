package pe.edu.pucp.softpet.model.DetalleDTO;

import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;
import pe.edu.pucp.softpet.model.serviciosdto.ServiciosDTO;

/**
 *
 * @author snipe
 */
public class DetalleServiciosDTO {

    private Integer citaXServicioId;
    //Falta Colocar la cita Atencion su ID
    private CitaAtencionDTO cita;
    private ServiciosDTO servicio;
    private String descripcion;
    private Double total;

    public DetalleServiciosDTO() {
        this.citaXServicioId = null;
        this.cita = null;
        this.servicio = null;
        this.descripcion = null;
        this.total = null;
    }

    public DetalleServiciosDTO(Integer citaXServicioId, CitaAtencionDTO cita, ServiciosDTO servicio, String descripcion, Double total) {
        this.citaXServicioId = citaXServicioId;
        this.cita = cita;
        this.servicio = servicio;
        this.descripcion = descripcion;
        this.total = total;
    }

    /**
     * @return the citaXServicioId
     */
    public Integer getCitaXServicioId() {
        return citaXServicioId;
    }

    /**
     * @param citaXServicioId the citaXServicioId to set
     */
    public void setCitaXServicioId(Integer citaXServicioId) {
        this.citaXServicioId = citaXServicioId;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * @return the cita
     */
    public CitaAtencionDTO getCita() {
        return cita;
    }

    /**
     * @param cita the cita to set
     */
    public void setCita(CitaAtencionDTO cita) {
        this.cita = cita;
    }

    /**
     * @return the servicio
     */
    public ServiciosDTO getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(ServiciosDTO servicio) {
        this.servicio = servicio;
    }

}
