package pe.edu.pucp.softpet.dto.mascotas;

import java.sql.Date;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

public class MascotaDto {
  
    private Integer mascotaId;
    private String nombre;
    private String especie;
    private String sexo;
    private String raza;
    private String color;
    private Boolean activo;
    private Date fechaDefuncion;
    private PersonaDto persona;

    public MascotaDto() {
        this.mascotaId = null;
        this.nombre = null;
        this.especie = null;
        this.sexo = null;
        this.raza = null;
        this.color = null;
        this.activo = null;
        this.fechaDefuncion = null;
        this.persona = null;
    }
    
    public MascotaDto(Integer mascotaId, String nombre, String especie, 
            String sexo, String raza, String color, Boolean activo, 
            Date fechaDefuncion, PersonaDto persona) {
        this.mascotaId = mascotaId;
        this.nombre = nombre;
        this.especie = especie;
        this.sexo = sexo;
        this.raza = raza;
        this.color = color;
        this.activo = activo;
        this.fechaDefuncion = fechaDefuncion;
        this.persona = persona;
    }

    /**
     * @return the mascotaId
     */
    public Integer getMascotaId() {
        return mascotaId;
    }

    /**
     * @param mascotaId the mascotaId to set
     */
    public void setMascotaId(Integer mascotaId) {
        this.mascotaId = mascotaId;
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
     * @return the especie
     */
    public String getEspecie() {
        return especie;
    }

    /**
     * @param especie the especie to set
     */
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the raza
     */
    public String getRaza() {
        return raza;
    }

    /**
     * @param raza the raza to set
     */
    public void setRaza(String raza) {
        this.raza = raza;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
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
     * @return the fechaDefuncion
     */
    public Date getFechaDefuncion() {
        return fechaDefuncion;
    }

    /**
     * @param fechaDefuncion the fechaDefuncion to set
     */
    public void setFechaDefuncion(Date fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
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
