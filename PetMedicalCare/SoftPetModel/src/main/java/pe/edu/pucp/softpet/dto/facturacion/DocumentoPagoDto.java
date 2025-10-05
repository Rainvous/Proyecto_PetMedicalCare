package pe.edu.pucp.softpet.dto.facturacion;

import java.sql.Date;

public class DocumentoPagoDto {

    private Integer documentoPagoId;
    private String serie;
    private String numero;
    private Double tasaIGV;
    private Date fechaEmision;
    private String metodoPago;
    private String estado;
    private Double subtotalSinIGV;
    private Double IGVTotal;
    private Double total;
    private Boolean activo;
    public DocumentoPagoDto() {
        this.documentoPagoId = null;
        this.serie = null;
        this.numero = null;
        this.tasaIGV = null;
        this.fechaEmision = null;
        this.metodoPago = null;
        this.estado = null;
        this.subtotalSinIGV = null;
        this.IGVTotal = null;
        this.total = null;
        this.activo =null;
    }
    
    public DocumentoPagoDto(Integer documentoPagoId, String serie, 
            String numero, Double tasaIGV, Date fechaEmision, String metodoPago, 
            String estado, Double subtotalSinIGV, Double IGVTotal, Double total,
            Boolean activo) {
        this.documentoPagoId = documentoPagoId;
        this.serie = serie;
        this.numero = numero;
        this.tasaIGV = tasaIGV;
        this.fechaEmision = fechaEmision;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.subtotalSinIGV = subtotalSinIGV;
        this.IGVTotal = IGVTotal;
        this.total = total;
        this.activo = activo;
    }

    /**
     * @return the documentoPagoId
     */
    public Integer getDocumentoPagoId() {
        return documentoPagoId;
    }

    /**
     * @param documentoPagoId the documentoPagoId to set
     */
    public void setDocumentoPagoId(Integer documentoPagoId) {
        this.documentoPagoId = documentoPagoId;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the tasaIGV
     */
    public Double getTasaIGV() {
        return tasaIGV;
    }

    /**
     * @param tasaIGV the tasaIGV to set
     */
    public void setTasaIGV(Double tasaIGV) {
        this.tasaIGV = tasaIGV;
    }

    /**
     * @return the fechaEmision
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * @param fechaEmision the fechaEmision to set
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * @return the metodoPago
     */
    public String getMetodoPago() {
        return metodoPago;
    }

    /**
     * @param metodoPago the metodoPago to set
     */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
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
     * @return the subtotalSinIGV
     */
    public Double getSubtotalSinIGV() {
        return subtotalSinIGV;
    }

    /**
     * @param subtotalSinIGV the subtotalSinIGV to set
     */
    public void setSubtotalSinIGV(Double subtotalSinIGV) {
        this.subtotalSinIGV = subtotalSinIGV;
    }

    /**
     * @return the IGVTotal
     */
    public Double getIGVTotal() {
        return IGVTotal;
    }

    /**
     * @param IGVTotal the IGVTotal to set
     */
    public void setIGVTotal(Double IGVTotal) {
        this.IGVTotal = IGVTotal;
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
     * @return the activo
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * @param activo the total to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
