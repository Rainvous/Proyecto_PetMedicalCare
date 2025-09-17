package pe.edu.pucp.softpet.model.facturaciondto;

import java.util.Date;
import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;

/**
 *
 * @author ferro
 */
public class ComprobantesDePagoDTO {
    
    private Integer comprobante_id;
    private TipoComprobantesDTO tipoComprobante;
    private CitaAtencionDTO citaAtencion;
    private Date fecha_emision;
    private Double monto_total;
    private String estado;
    private Double igv;
    private String metodo_de_pago;

    public ComprobantesDePagoDTO() {
        this.comprobante_id = null;
        this.tipoComprobante = null;
        this.citaAtencion = null;
        this.fecha_emision = null;
        this.monto_total = null;
        this.estado = null;
        this.igv = null;
        this.metodo_de_pago = null;
    }
    
    public ComprobantesDePagoDTO(Integer comprobante_id, TipoComprobantesDTO tipoComprobante, 
            CitaAtencionDTO citaAtencion, Date fecha_emision, Double monto_total, 
            String estado, Double igv, String metodo_de_pago) {
        this.comprobante_id = comprobante_id;
        this.tipoComprobante = tipoComprobante;
        this.citaAtencion = citaAtencion;
        this.fecha_emision = fecha_emision;
        this.monto_total = monto_total;
        this.estado = estado;
        this.igv = igv;
        this.metodo_de_pago = metodo_de_pago;
    }

    /**
     * @return the comprobante_id
     */
    public Integer getComprobante_id() {
        return comprobante_id;
    }

    /**
     * @param comprobante_id the comprobante_id to set
     */
    public void setComprobante_id(Integer comprobante_id) {
        this.comprobante_id = comprobante_id;
    }

    /**
     * @return the tipoComprobante
     */
    public TipoComprobantesDTO getTipoComprobante() {
        return tipoComprobante;
    }

    /**
     * @param tipoComprobante the tipoComprobante to set
     */
    public void setTipoComprobante(TipoComprobantesDTO tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    /**
     * @return the citaAtencion
     */
    public CitaAtencionDTO getCitaAtencion() {
        return citaAtencion;
    }

    /**
     * @param citaAtencion the citaAtencion to set
     */
    public void setCitaAtencion(CitaAtencionDTO citaAtencion) {
        this.citaAtencion = citaAtencion;
    }

    /**
     * @return the fecha_emision
     */
    public Date getFecha_emision() {
        return fecha_emision;
    }

    /**
     * @param fecha_emision the fecha_emision to set
     */
    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    /**
     * @return the monto_total
     */
    public Double getMonto_total() {
        return monto_total;
    }

    /**
     * @param monto_total the monto_total to set
     */
    public void setMonto_total(Double monto_total) {
        this.monto_total = monto_total;
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
     * @return the igv
     */
    public Double getIgv() {
        return igv;
    }

    /**
     * @param igv the igv to set
     */
    public void setIgv(Double igv) {
        this.igv = igv;
    }

    /**
     * @return the metodo_de_pago
     */
    public String getMetodo_de_pago() {
        return metodo_de_pago;
    }

    /**
     * @param metodo_de_pago the metodo_de_pago to set
     */
    public void setMetodo_de_pago(String metodo_de_pago) {
        this.metodo_de_pago = metodo_de_pago;
    }
    
    
}
