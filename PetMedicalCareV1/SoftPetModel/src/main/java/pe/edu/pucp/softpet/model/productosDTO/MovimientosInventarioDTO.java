/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.model.productosDTO;

import java.util.Date;
import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;

/**
 *
 * @author User
 */
public class MovimientosInventarioDTO {

    
    private Integer movimiento_id;
    private ProductosDTO producto_id;
    private CitaAtencionDTO cita_id;
    private String tipo_movimiento;
    private Integer cantidad;
    private Date fecha;
    private String motivo;

    
    public MovimientosInventarioDTO() {
        this.movimiento_id = null;
        this.producto_id = null;
        this.cita_id = null;
        this.tipo_movimiento = null;
        this.cantidad = null;
        this.fecha = null;
        this.motivo = null;
    }
    public MovimientosInventarioDTO(Integer movimiento_id, ProductosDTO producto_id, CitaAtencionDTO cita_id, String tipo_movimiento, Integer cantidad, Date fecha, String motivo) {
        this.movimiento_id = movimiento_id;
        this.producto_id = producto_id;
        this.cita_id = cita_id;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.motivo = motivo;
    }
    
    
    
    /**
     * @return the movimiento_id
     */
    public Integer getMovimiento_id() {
        return movimiento_id;
    }

    /**
     * @param movimiento_id the movimiento_id to set
     */
    public void setMovimiento_id(Integer movimiento_id) {
        this.movimiento_id = movimiento_id;
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

    /**
     * @return the movimiento_id
     */
    
    
    
}
