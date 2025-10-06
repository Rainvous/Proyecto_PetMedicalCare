package pe.edu.pucp.softpet.dto.usuarios;

public class RolDto {

    private Integer rolId;
    private String nombre;
    private Boolean activo;

    public RolDto() {
        this.rolId = null;
        this.nombre = null;
        this.activo = null;
    }

    public RolDto(Integer rolId, String nombre, Boolean activo) {
        this.rolId = rolId;
        this.nombre = nombre;
        this.activo = activo;
    }

    /**
     * @return the rolId
     */
    public Integer getRolId() {
        return rolId;
    }

    /**
     * @param rolId the rolId to set
     */
    public void setRolId(Integer rolId) {
        this.rolId = rolId;
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
