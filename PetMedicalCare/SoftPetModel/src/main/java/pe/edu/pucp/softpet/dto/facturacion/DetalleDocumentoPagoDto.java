package pe.edu.pucp.softpet.dto.facturacion;

import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

public class DetalleDocumentoPagoDto {

    private Integer dddpId;
    private DocumentoPagoDto documentoPago;
    private ServicioDto servicio;
    private ProductoDto producto;
    private Integer nroItem;
    private String descripcion;
    private Integer cantidad;
    private Double precioUnitario;
    private Double valorVenta;
    private Boolean activo;

    public DetalleDocumentoPagoDto() {
        this.dddpId = null;
        this.documentoPago = null;
        this.servicio = null;
        this.producto = null;
        this.nroItem = null;
        this.descripcion = null;
        this.cantidad = null;
        this.precioUnitario = null;
        this.valorVenta = null;
        this.activo = null;
    }

    public DetalleDocumentoPagoDto(Integer dddpId,
            DocumentoPagoDto documentoPago, ServicioDto servicio,
            ProductoDto producto, Integer nroItem, String descripcion,
            Integer cantidad, Double precioUnitario, Double valorVenta,
            Boolean activo) {
        this.dddpId = dddpId;
        this.nroItem = nroItem;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.valorVenta = valorVenta;
        this.documentoPago = documentoPago;
        this.servicio = servicio;
        this.producto = producto;
        this.activo = activo;
    }

    /**
     * @return the dddpId
     */
    public Integer getDddpId() {
        return dddpId;
    }

    /**
     * @param dddpId the dddpId to set
     */
    public void setDddpId(Integer dddpId) {
        this.dddpId = dddpId;
    }

    /**
     * @return the documentoPago
     */
    public DocumentoPagoDto getDocumentoPago() {
        return documentoPago;
    }

    /**
     * @param documentoPago the documentoPago to set
     */
    public void setDocumentoPago(DocumentoPagoDto documentoPago) {
        this.documentoPago = documentoPago;
    }

    /**
     * @return the servicio
     */
    public ServicioDto getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(ServicioDto servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the producto
     */
    public ProductoDto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(ProductoDto producto) {
        this.producto = producto;
    }

    /**
     * @return the nroItem
     */
    public Integer getNroItem() {
        return nroItem;
    }

    /**
     * @param nroItem the nroItem to set
     */
    public void setNroItem(Integer nroItem) {
        this.nroItem = nroItem;
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
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
     * @return the valorVenta
     */
    public Double getValorVenta() {
        return valorVenta;
    }

    /**
     * @param valorVenta the valorVenta to set
     */
    public void setValorVenta(Double valorVenta) {
        this.valorVenta = valorVenta;
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
