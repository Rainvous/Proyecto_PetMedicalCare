package pe.edu.pucp.softpet.dto.facturacion;

import java.sql.Date;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

public class DocumentoPagoDto {

    private Integer documentoPagoId;
    private String tipoDocumento;      // 
    private String serie;
    private String numero;
    private Date fechaEmision;
    private String estado;
    private Double subtotal;           // antes subtotalSinIGV
    private Double IGVTotal;
    private Double total;
    private Boolean activo;

    private MetodoDePagoDto metodoDePago; // FK a METODOS_DE_PAGO
    private PersonaDto persona;            // FK a PERSONA

    public DocumentoPagoDto() {
        this.documentoPagoId = null;
        this.tipoDocumento = null;
        this.serie = null;
        this.numero = null;
        this.fechaEmision = null;
        this.estado = null;
        this.subtotal = null;
        this.IGVTotal = null;
        this.total = null;
        this.activo = null;
        this.metodoDePago = null;
        this.persona = null;
    }

    public DocumentoPagoDto(Integer documentoPagoId, String tipoDocumento,
            String serie, String numero, Date fechaEmision, String estado,
            Double subtotal, Double IGVTotal, Double total, Boolean activo,
            MetodoDePagoDto metodoDePago, PersonaDto persona) {
        this.documentoPagoId = documentoPagoId;
        this.tipoDocumento = tipoDocumento;
        this.serie = serie;
        this.numero = numero;
        this.fechaEmision = fechaEmision;
        this.estado = estado;
        this.subtotal = subtotal;
        this.IGVTotal = IGVTotal;
        this.total = total;
        this.activo = activo;
        this.metodoDePago = metodoDePago;
        this.persona = persona;
    }

    public Integer getDocumentoPagoId() {
        return documentoPagoId;
    }

    public void setDocumentoPagoId(Integer documentoPagoId) {
        this.documentoPagoId = documentoPagoId;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIGVTotal() {
        return IGVTotal;
    }

    public void setIGVTotal(Double IGVTotal) {
        this.IGVTotal = IGVTotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public MetodoDePagoDto getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(MetodoDePagoDto metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public PersonaDto getPersona() {
        return persona;
    }

    public void setPersona(PersonaDto persona) {
        this.persona = persona;
    }
}
