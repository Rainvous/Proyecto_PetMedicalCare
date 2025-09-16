/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.model.serviciosdto;

/**
 *
 * @author snipe
 */
public class ServiciosDTO {
    private Integer servicioId;
    private TipoServiciosDTO tipoServicioDTO;
    private String nombre;
    private Double costo;
    private String estado;

    public ServiciosDTO() {
        this.servicioId = null;
        this.tipoServicioDTO = null;
        this.nombre = null;
        this.costo = null;
        this.estado = null;
    }

    public ServiciosDTO(Integer servicioId, TipoServiciosDTO tipoServicioDTO, String nombre, Double costo, String estado) {
        this.servicioId = servicioId;
        this.tipoServicioDTO = tipoServicioDTO;
        this.nombre = nombre;
        this.costo = costo;
        this.estado = estado;
    }

    /**
     * @return the servicioId
     */
    public Integer getServicioId() {
        return servicioId;
    }

    /**
     * @param servicioId the servicioId to set
     */
    public void setServicioId(Integer servicioId) {
        this.servicioId = servicioId;
    }

    /**
     * @return the tipoServicioDTO
     */
    public TipoServiciosDTO getTipoServicioDTO() {
        return tipoServicioDTO;
    }

    /**
     * @param tipoServicioDTO the tipoServicioDTO to set
     */
    public void setTipoServicioDTO(TipoServiciosDTO tipoServicioDTO) {
        this.tipoServicioDTO = tipoServicioDTO;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
