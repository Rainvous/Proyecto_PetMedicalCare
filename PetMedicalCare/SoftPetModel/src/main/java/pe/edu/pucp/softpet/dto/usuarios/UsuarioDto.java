package pe.edu.pucp.softpet.dto.usuarios;

import java.sql.Date;

public class UsuarioDto {

    private Integer usuarioId;
    private String username;
    private String password;
    private String correo;
    private Boolean activo;
    private String usuarioCreador;
    private Date fechaCreacion;
    private String usuarioModificador;
    private Date fechaModificacion;

    public UsuarioDto() {
        this.usuarioId = null;
        this.username = null;
        this.password = null;
        this.correo = null;
        this.activo = null;
        this.usuarioCreador = null;
        this.fechaCreacion = null;
        this.usuarioModificador = null;
        this.fechaModificacion = null;
    }

    public UsuarioDto(Integer usuarioId, String username, String password,
            String correo, Boolean activo, String usuarioCreador,
            Date fechaCreacion, String usuarioModificador, Date fechaModificacion) {
        this.usuarioId = usuarioId;
        this.username = username;
        this.password = password;
        this.correo = correo;
        this.activo = activo;
        this.usuarioCreador = usuarioCreador;
        this.fechaCreacion = fechaCreacion;
        this.usuarioModificador = usuarioModificador;
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the usuarioId
     */
    public Integer getUsuarioId() {
        return usuarioId;
    }

    /**
     * @param usuarioId the usuarioId to set
     */
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
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
     * @return the usuarioCreador
     */
    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    /**
     * @param usuarioCreador the usuarioCreador to set
     */
    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the usuarioModificador
     */
    public String getUsuarioModificador() {
        return usuarioModificador;
    }

    /**
     * @param usuarioModificador the usuarioModificador to set
     */
    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }

    /**
     * @return the fechaModificacion
     */
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion the fechaModificacion to set
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
