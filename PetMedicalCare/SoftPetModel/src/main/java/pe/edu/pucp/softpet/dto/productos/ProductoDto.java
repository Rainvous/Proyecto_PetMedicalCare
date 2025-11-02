package pe.edu.pucp.softpet.dto.productos;

import java.sql.Date;

public class ProductoDto {

    private Integer productoId;
    private TipoProductoDto tipoProducto;
    private String nombre;
    private String presentacion;
    private Double precioUnitario;
    private Integer stock;
    private Boolean activo;
    private String usuarioCreador;
    private Date fechaCreacion;
    private String usuarioModificador;
    private Date fechaModificacion;

    public ProductoDto() {
        this.productoId = null;
        this.tipoProducto = null;
        this.nombre = null;
        this.presentacion = null;
        this.precioUnitario = null;
        this.stock = null;
        this.activo = null;
        this.usuarioCreador = null;
        this.fechaCreacion = null;
        this.usuarioModificador = null;
        this.fechaModificacion = null;
    }

    public ProductoDto(Integer productoId, TipoProductoDto tipoProducto,
            String nombre, String presentacion, Double precioUnitario,
            Integer stock, Boolean activo, String usuarioCreador,
            Date fechaCreacion, String usuarioModificador, Date fechaModificacion) {
        this.productoId = productoId;
        this.tipoProducto = tipoProducto;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
        this.activo = activo;
        this.usuarioCreador = usuarioCreador;
        this.fechaCreacion = fechaCreacion;
        this.usuarioModificador = usuarioModificador;
        this.fechaModificacion = fechaModificacion;
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
     * @return the stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Integer stock) {
        this.stock = stock;
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
