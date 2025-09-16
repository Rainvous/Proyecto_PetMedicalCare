/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;
import pe.edu.pucp.softpet.model.productosDTO.ProductosDTO;

/**
 *
 * @author User
 */
public class DetalleProductosDTO {

    
    
    private Integer cita_producto_id;
    
    private CitaAtencionDTO cita_id;
    
    
    
    private ProductosDTO producto;
    private Integer cantidad;
    private Double total;
    private String descripcion;
    
    
    public DetalleProductosDTO() {
        this.cita_producto_id = null;
        this.cita_id = null;
        this.producto = null;
        this.cantidad = null;
        this.total = null;
        this.descripcion = null;
    }
    public DetalleProductosDTO(Integer cita_producto_id, CitaAtencionDTO cita_id, ProductosDTO producto_id, Integer cantidad, Double total, String descripcion) {
        this.cita_producto_id = cita_producto_id;
        this.cita_id = cita_id;
        this.producto = producto_id;
        this.cantidad = cantidad;
        this.total = total;
        this.descripcion = descripcion;
    }
    
    /**
     * @return the cita_producto_id
     */
    public Integer getCita_producto_id() {
        return cita_producto_id;
    }

    /**
     * @param cita_producto_id the cita_producto_id to set
     */
    public void setCita_producto_id(Integer cita_producto_id) {
        this.cita_producto_id = cita_producto_id;
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
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
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
}
