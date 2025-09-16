/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.model.actoresdto;

import java.util.Date;

/**
 *
 * @author snipe
 */
public class VeterinariosDTO {
    
    private Integer veterinarioId;
    private String especializacion;
    private Date fechaDeContratacion;
    private PersonasDTO personaId;
    private String estado;
    private Double sueldo;

    public VeterinariosDTO() {
        this.veterinarioId = null;
        this.especializacion = null;
        this.fechaDeContratacion = null;
        this.personaId = null;
        this.estado = null;
        this.sueldo = null;
    }
    
    public VeterinariosDTO(Integer veterinarioId, String especializacion, Date fechaDeContratacion, PersonasDTO personaId, String estado, Double sueldo) {
        this.veterinarioId = veterinarioId;
        this.especializacion = especializacion;
        this.fechaDeContratacion = fechaDeContratacion;
        this.personaId = personaId;
        this.estado = estado;
        this.sueldo = sueldo;
    }

    /**
     * @return the veterinarioId
     */
    public Integer getVeterinarioId() {
        return veterinarioId;
    }

    /**
     * @param veterinarioId the veterinarioId to set
     */
    public void setVeterinarioId(Integer veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    /**
     * @return the especializacion
     */
    public String getEspecializacion() {
        return especializacion;
    }

    /**
     * @param especializacion the especializacion to set
     */
    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    /**
     * @return the fechaDeContratacion
     */
    public Date getFechaDeContratacion() {
        return fechaDeContratacion;
    }

    /**
     * @param fechaDeContratacion the fechaDeContratacion to set
     */
    public void setFechaDeContratacion(Date fechaDeContratacion) {
        this.fechaDeContratacion = fechaDeContratacion;
    }

    /**
     * @return the personaId
     */
    public PersonasDTO getPersonaId() {
        return personaId;
    }

    /**
     * @param personaId the personaId to set
     */
    public void setPersonaId(PersonasDTO personaId) {
        this.personaId = personaId;
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

    /**
     * @return the sueldo
     */
    public Double getSueldo() {
        return sueldo;
    }

    /**
     * @param sueldo the sueldo to set
     */
    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    
    
}
