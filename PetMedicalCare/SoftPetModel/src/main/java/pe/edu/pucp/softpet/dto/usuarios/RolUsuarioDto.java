package pe.edu.pucp.softpet.dto.usuarios;

public class RolUsuarioDto {

    private Integer rolUsuarioId;
    private UsuarioDto usuario;
    private RolDto rol;
    private Boolean activo;

    public RolUsuarioDto() {
        this.rolUsuarioId = null;
        this.usuario = null;
        this.rol = null;
        this.activo = null;
    }

    public RolUsuarioDto(Integer rolUsuarioId, UsuarioDto usuario,
            RolDto rol, Boolean activo) {
        this.rolUsuarioId = rolUsuarioId;
        this.usuario = usuario;
        this.rol = rol;
        this.activo = activo;
    }

    /**
     * @return the rolUsuarioId
     */
    public Integer getRolUsuarioId() {
        return rolUsuarioId;
    }

    /**
     * @param rolUsuarioId the rolUsuarioId to set
     */
    public void setRolUsuarioId(Integer rolUsuarioId) {
        this.rolUsuarioId = rolUsuarioId;
    }

    /**
     * @return the usuario
     */
    public UsuarioDto getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the rol
     */
    public RolDto getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(RolDto rol) {
        this.rol = rol;
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
