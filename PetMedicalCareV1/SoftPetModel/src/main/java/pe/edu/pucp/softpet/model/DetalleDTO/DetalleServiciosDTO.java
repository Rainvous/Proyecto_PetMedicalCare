/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    private CitaAtencionDTO cita_id;
    private ServiciosDTO servicioId;
    private String descripcion;
    private Double total;

    
    public DetalleServiciosDTO() {
        citaXServicioId = null;
        cita_id = null;
        servicioId = null;
        descripcion = null;
        total = null;
    }
    
    public DetalleServiciosDTO(Integer citaXServicioId, CitaAtencionDTO cita_id, ServiciosDTO servicioId, String descripcion, Double total) {
        this.citaXServicioId = citaXServicioId;
        this.cita_id = cita_id;
        this.servicioId = servicioId;
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
    
   
}
