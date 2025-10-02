package pe.edu.pucp.softpet.dto.personas;

import java.sql.Date;

public class VeterinarioDto {
   
    private Integer veterinarioId;
    private String especializacion;
    private Date fechaContratacion;
    private String estado;
    private Boolean activo;
    private PersonaDto persona;

    public VeterinarioDto() {
        this.veterinarioId = null;
        this.especializacion = null;
        this.fechaContratacion = null;
        this.estado = null;
        this.activo = null;
        this.persona = null;
    }
    
    public VeterinarioDto(Integer veterinarioId, String especializacion, 
            Date fechaContratacion, String estado, Boolean activo, 
            PersonaDto persona) {
        this.veterinarioId = veterinarioId;
        this.especializacion = especializacion;
        this.fechaContratacion = fechaContratacion;
        this.estado = estado;
        this.activo = activo;
        this.persona = persona;
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
     * @return the fechaContratacion
     */
    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    /**
     * @param fechaContratacion the fechaContratacion to set
     */
    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
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
     * @return the persona
     */
    public PersonaDto getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PersonaDto persona) {
        this.persona = persona;
    }
}
