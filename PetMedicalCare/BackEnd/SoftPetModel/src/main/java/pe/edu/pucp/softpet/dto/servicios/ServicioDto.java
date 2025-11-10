package pe.edu.pucp.softpet.dto.servicios;

import java.sql.Date;

public class ServicioDto {

    private Integer servicioId;
    private TipoServicioDto tipoServicio;
    private String nombre;
    private String descripcion;
    private Double costo;
    private String estado;
    private Boolean activo;
    private String usuarioCreador;
    private Date fechaCreacion;
    private String usuarioModificador;
    private Date fechaModificacion;

    public ServicioDto() {
        this.servicioId = null;
        this.tipoServicio = null;
        this.nombre = null;
        this.descripcion = null;
        this.costo = null;
        this.estado = null;
        this.activo = null;
        this.usuarioCreador = null;
        this.fechaCreacion = null;
        this.usuarioModificador = null;
        this.fechaModificacion = null;
    }

    public ServicioDto(Integer servicioId, TipoServicioDto tipoServicio,
            String nombre, String descripcion, Double costo, String estado,
            Boolean activo, String usuarioCreador, Date fechaCreacion,
            String usuarioModificador, Date fechaModificacion) {
        this.servicioId = servicioId;
        this.tipoServicio = tipoServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.estado = estado;
        this.activo = activo;
        this.usuarioCreador = usuarioCreador;
        this.fechaCreacion = fechaCreacion;
        this.usuarioModificador = usuarioModificador;
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the servicioId
     */
    public Integer getServicioId() {
        return servicioId;
    }

    /**
     * @param servicioId the servicioId to set
     */
    public void setServicioId(Integer servicioId) {
        this.servicioId = servicioId;
    }

    /**
     * @return the tipoServicio
     */
    public TipoServicioDto getTipoServicio() {
        return tipoServicio;
    }

    /**
     * @param tipoServicio the tipoServicio to set
     */
    public void setTipoServicio(TipoServicioDto tipoServicio) {
        this.tipoServicio = tipoServicio;
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the costo
     */
    public Double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(Double costo) {
        this.costo = costo;
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
