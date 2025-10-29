package pe.edu.pucp.softpet.dto.personas;

import java.sql.Date;

public class VeterinarioDto {

    private Integer veterinarioId;
    private PersonaDto persona;
    private Integer especialidadId;
    private Date fechaContratacion;
    private String estado;
    private String especialidad;
    private Boolean activo;

    public VeterinarioDto() {
        this.veterinarioId = null;
        this.persona = null;
        this.especialidadId = null;
        this.fechaContratacion = null;
        this.estado = null;
        this.especialidad = null;
        this.activo = null;
    }

    public VeterinarioDto(Integer veterinarioId, PersonaDto persona,
            Integer especialidadId, Date fechaContratacion, String estado,
            String especialidad, Boolean activo) {
        this.veterinarioId = veterinarioId;
        this.persona = persona;
        this.especialidadId = especialidadId;
        this.fechaContratacion = fechaContratacion;
        this.estado = estado;
        this.especialidad = especialidad;
        this.activo = activo;
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

    /**
     * @return the especialidadId
     */
    public Integer getEspecialidadId() {
        return especialidadId;
    }

    /**
     * @param especialidadId the especialidadId to set
     */
    public void setEspecialidadId(Integer especialidadId) {
        this.especialidadId = especialidadId;
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
     * @return the especialidad
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * @param especialidad the especialidad to set
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
}
