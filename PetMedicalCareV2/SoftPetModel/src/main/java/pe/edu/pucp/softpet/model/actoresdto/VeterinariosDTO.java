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
    private PersonasDTO persona;
    private String estado;
    private Double sueldo;

    public VeterinariosDTO() {
        this.veterinarioId = null;
        this.especializacion = null;
        this.fechaDeContratacion = null;
        this.persona = null; // un toque, veré el code de mlegar , es que amaru no uso id jakjksj muda quedé
        this.estado = null;
        this.sueldo = null;
    }

    public VeterinariosDTO(Integer veterinarioId, String especializacion, Date fechaDeContratacion, PersonasDTO persona, String estado, Double sueldo) {
        this.veterinarioId = veterinarioId;
        this.especializacion = especializacion;
        this.fechaDeContratacion = fechaDeContratacion;
        this.persona = persona;
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
    public PersonasDTO getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PersonasDTO persona) {
        this.persona = persona;
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
