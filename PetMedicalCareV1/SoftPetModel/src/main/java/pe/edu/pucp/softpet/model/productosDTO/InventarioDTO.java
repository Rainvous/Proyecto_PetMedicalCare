/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.model.productosDTO;

import java.util.Date;

/**
 *
 * @author User
 */
public class InventarioDTO {

    
    private Integer inventario_id;
    private Integer producto_id;
    private Integer cita_id;
    private String tipo_movimiento;
    private Integer cantidad;
    private Date fecha;
    private String motivo;
    
    
    public InventarioDTO(){
        inventario_id=null;
        producto_id=null;
        cita_id=null;
        tipo_movimiento=null;
        cantidad=null;
        fecha=null;
        motivo=null;
    }
    
    public InventarioDTO(Integer inventario_id,Integer producto_id,
    Integer cita_id, String tipo_movimiento, Integer cantidad,Date fecha,
    String motivo){
        this.inventario_id=inventario_id;
        this.producto_id=producto_id;
        this.cita_id=cita_id;
        this.tipo_movimiento=tipo_movimiento;
        this.cantidad=cantidad;
        this.fecha=fecha;
        this.motivo=motivo;
    }
    /**
     * @return the inventario_id
     */
    public Integer getInventario_id() {
        return inventario_id;
    }

    /**
     * @param inventario_id the inventario_id to set
     */
    public void setInventario_id(Integer inventario_id) {
        this.inventario_id = inventario_id;
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
     * @return the cita_id
     */
    public Integer getCita_id() {
        return cita_id;
    }

    /**
     * @param cita_id the cita_id to set
     */
    public void setCita_id(Integer cita_id) {
        this.cita_id = cita_id;
    }

    /**
     * @return the tipo_movimiento
     */
    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    /**
     * @param tipo_movimiento the tipo_movimiento to set
     */
    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
