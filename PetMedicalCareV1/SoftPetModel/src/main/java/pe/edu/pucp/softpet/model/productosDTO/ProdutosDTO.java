/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.model.productosDTO;

/**
 *
 * @author User
 */
public class ProdutosDTO {

    
    private Integer producto_id;
    private Integer tipo_producto_id;
    private String nombre;
    private String presentacion;
    private String precio_unitario_decimal;
    private Integer cantidad_total;
    
    public ProdutosDTO(){
         producto_id=null;
         tipo_producto_id=null;
         nombre=null;
         presentacion=null;
         precio_unitario_decimal=null;
         cantidad_total=null;
    }
    
    public ProdutosDTO(Integer producto_id,Integer tipo_producto_id,
    String nombre,String presentacion,String precio_unitario_decimal,
    Integer cantidad_total){
         this.producto_id=producto_id;
         this.tipo_producto_id=tipo_producto_id;
         this.nombre=nombre;
         this.presentacion=presentacion;
         this.precio_unitario_decimal=precio_unitario_decimal;
         this.cantidad_total=cantidad_total;
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
     * @return the tipo_producto_id
     */
    public Integer getTipo_producto_id() {
        return tipo_producto_id;
    }

    /**
     * @param tipo_producto_id the tipo_producto_id to set
     */
    public void setTipo_producto_id(Integer tipo_producto_id) {
        this.tipo_producto_id = tipo_producto_id;
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
     * @return the precio_unitario_decimal
     */
    public String getPrecio_unitario_decimal() {
        return precio_unitario_decimal;
    }

    /**
     * @param precio_unitario_decimal the precio_unitario_decimal to set
     */
    public void setPrecio_unitario_decimal(String precio_unitario_decimal) {
        this.precio_unitario_decimal = precio_unitario_decimal;
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
}
