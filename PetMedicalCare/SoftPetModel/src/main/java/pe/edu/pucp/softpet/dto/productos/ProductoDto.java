package pe.edu.pucp.softpet.dto.productos;

import java.sql.Date;

public class ProductoDto {

    private Integer productoId;
    private String nombre;
    private String presentacion;
    private Double precioUnitario;
    private Boolean activo;
    private TipoProductoDto tipoProducto;
    private Date fechaModificacion;
    private String usuarioModificador;
    private String usuarioCreador;
    private Date fechaCreacion;

    public ProductoDto() {
        this.productoId = null;
        this.nombre = null;
        this.presentacion = null;
        this.precioUnitario = null;
        this.activo = null;
        this.tipoProducto = null;
        this.fechaModificacion = null;
        this.usuarioModificador = null;
        this.usuarioCreador = null;
        this.fechaCreacion = null;
    }

    public ProductoDto(Integer productoId, String nombre, String presentacion,
            Double precioUnitario, Boolean activo, TipoProductoDto tipoProducto,
            Date fechaModificacion, String usuarioModificador,
            String usuarioCreador, Date fechaCreacion) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.precioUnitario = precioUnitario;
        this.activo = activo;
        this.tipoProducto = tipoProducto;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificador = usuarioModificador;
        this.usuarioCreador = usuarioCreador;
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the productoId
     */
    public Integer getProductoId() {
        return productoId;
    }

    /**
     * @param productoId the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
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
     * @return the presentacion
     */
    public String getPresentacion() {
        return presentacion;
    }

    /**
     * @param presentacion the presentacion to set
     */
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * @return the precioUnitario
     */
    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * @param precioUnitario the precioUnitario to set
     */
    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
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
     * @return the tipoProducto
     */
    public TipoProductoDto getTipoProducto() {
        return tipoProducto;
    }

    /**
     * @param tipoProducto the tipoProducto to set
     */
    public void setTipoProducto(TipoProductoDto tipoProducto) {
        this.tipoProducto = tipoProducto;
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
}
