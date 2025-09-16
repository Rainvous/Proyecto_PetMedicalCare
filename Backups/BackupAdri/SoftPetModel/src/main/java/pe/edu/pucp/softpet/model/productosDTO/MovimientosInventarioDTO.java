package pe.edu.pucp.softpet.model.productosDTO;

import java.util.Date;
import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;

/**
 *
 * @author User
 */
public class MovimientosInventarioDTO {

    private Integer movimiento_id;
    private ProductosDTO producto;
    private CitaAtencionDTO cita;
    private String tipo_movimiento;
    private Integer cantidad;
    private Date fecha;
    private String motivo;

    public MovimientosInventarioDTO() {
        this.movimiento_id = null;
        this.producto = null;
        this.cita = null;
        this.tipo_movimiento = null;
        this.cantidad = null;
        this.fecha = null;
        this.motivo = null;
    }

    public MovimientosInventarioDTO(Integer movimiento_id, ProductosDTO producto, CitaAtencionDTO cita, String tipo_movimiento, Integer cantidad, Date fecha, String motivo) {
        this.movimiento_id = movimiento_id;
        this.producto = producto;
        this.cita = cita;
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
     * @return the producto
     */
    public ProductosDTO getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(ProductosDTO producto) {
        this.producto = producto;
    }

    /**
     * @return the cita
     */
    public CitaAtencionDTO getCita() {
        return cita;
    }

    /**
     * @param cita the cita to set
     */
    public void setCita(CitaAtencionDTO cita) {
        this.cita = cita;
    }

    /**
     * @return the movimiento_id
     */
}
