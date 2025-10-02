package pe.edu.pucp.softpet.dto.servicios;

import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;

public class DetalleServicioDto {
    
    private Integer detalleServicioId;
    private String descripcion;
    private Double costo;
    private Boolean activo;
    private ServicioDto servicio;
    private CitaAtencionDto cita;

    public DetalleServicioDto() {
        this.detalleServicioId = null;
        this.descripcion = null;
        this.costo = null;
        this.activo = null;
        this.servicio = null;
        this.cita = null;
    }
    
    public DetalleServicioDto(Integer detalleServicioId, String descripcion, 
            Double costo, Boolean activo, ServicioDto servicio, 
            CitaAtencionDto cita) {
        this.detalleServicioId = detalleServicioId;
        this.descripcion = descripcion;
        this.costo = costo;
        this.activo = activo;
        this.servicio = servicio;
        this.cita = cita;
    }

    /**
     * @return the detalleServicioId
     */
    public Integer getDetalleServicioId() {
        return detalleServicioId;
    }

    /**
     * @param detalleServicioId the detalleServicioId to set
     */
    public void setDetalleServicioId(Integer detalleServicioId) {
        this.detalleServicioId = detalleServicioId;
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
     * @return the costo
     */
    public Double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(Double costo) {
        this.costo = costo;
    }

    /**
     * @return the activo
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    /**
     * @return the servicio
     */
    public ServicioDto getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(ServicioDto servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the cita
     */
    public CitaAtencionDto getCita() {
        return cita;
    }

    /**
     * @param cita the cita to set
     */
    public void setCita(CitaAtencionDto cita) {
        this.cita = cita;
    }
}
