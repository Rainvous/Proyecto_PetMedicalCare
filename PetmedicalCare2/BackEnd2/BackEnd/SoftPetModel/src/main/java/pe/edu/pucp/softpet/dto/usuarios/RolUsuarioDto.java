package pe.edu.pucp.softpet.dto.usuarios;

public class RolUsuarioDto {

    private Integer rolUsuarioId;
    private RolDto rol;
    private UsuarioDto usuario;
    private Boolean activo;

    public RolUsuarioDto() {
        this.rolUsuarioId = null;
        this.rol = null;
        this.usuario = null;
        this.activo = null;
    }

    public RolUsuarioDto(Integer rolUsuarioId, RolDto rol,
            UsuarioDto usuario, Boolean activo) {
        this.rolUsuarioId = rolUsuarioId;
        this.rol = rol;
        this.usuario = usuario;
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
