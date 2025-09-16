/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.model.productosDTO;

/**
 *
 * @author User
 */
public class ProductosDTO {


    
    private Integer producto_id;
    private TipoProductosDTO tipo_producto;
    private String nombre;
    private String presentacion;
    private Double precio_unitario;
    private Integer cantidad_total;
    
    public ProductosDTO() {
        this.producto_id = null;
        this.tipo_producto = null;
        this.nombre = null;
        this.presentacion = null;
        this.precio_unitario = null;
        this.cantidad_total = null;
    }

    public ProductosDTO(Integer producto_id, TipoProductosDTO tipo_producto, String nombre, String presentacion, Double precio_unitario, Integer cantidad_total) {
        this.producto_id = producto_id;
        this.tipo_producto = tipo_producto;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.precio_unitario = precio_unitario;
        this.cantidad_total = cantidad_total;
    }
    
    
    
    /**
     * @return the producto_id
     */
    public Integer getProducto_id() {
        return producto_id;
    }

    /**
     * @param producto_id the producto_id to set
     */
    public void setProducto_id(Integer producto_id) {
        this.producto_id = producto_id;
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
     * @return the precio_unitario
     */
    public Double getPrecio_unitario() {
        return precio_unitario;
    }

    /**
     * @param precio_unitario the precio_unitario to set
     */
    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    /**
     * @return the cantidad_total
     */
    public Integer getCantidad_total() {
        return cantidad_total;
    }

    /**
     * @param cantidad_total the cantidad_total to set
     */
    public void setCantidad_total(Integer cantidad_total) {
        this.cantidad_total = cantidad_total;
    }

    /**
     * @return the tipo_producto
     */
    public TipoProductosDTO getTipo_producto() {
        return tipo_producto;
    }

    /**
     * @param tipo_producto the tipo_producto to set
     */
    public void setTipo_producto(TipoProductosDTO tipo_producto) {
        this.tipo_producto = tipo_producto;
    }
    
}
