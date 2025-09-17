package pe.edu.pucp.softpet.model.actoresdto;

/**
 *
 * @author snipe
 */
public class MascotasDTO {

    private Integer mascotaId;
    private PersonasDTO persona;
    private String nombre;
    private String raza;
    private String color;

    public MascotasDTO() {
        this.mascotaId = null;
        this.persona = null;
        this.nombre = null;
        this.raza = null;
        this.color = null;
    }

    public MascotasDTO(Integer mascotaId, PersonasDTO persona, String nombre, String raza, String color) {
        this.mascotaId = mascotaId;
        this.persona = persona;
        this.nombre = nombre;
        this.raza = raza;
        this.color = color;
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
     * @return the personaId
     */
    public PersonasDTO getPersona() {
        return persona;
    }

    /**
     * @param persona the personaId to set
     */
    public void setPersona(PersonasDTO persona) {
        this.persona = persona;
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

}
