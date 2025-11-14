package pe.edu.pucp.softpet.dto.facturacion;

import java.sql.Date;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoDocumentoDePago;
import pe.edu.pucp.softpet.dto.util.enums.TipoDocumentoDePago;

public class DocumentoPagoDto {

    /**
     * @return the fechaEmisionString
     */
    public String getFechaEmisionString() {
        return fechaEmisionString;
    }

    /**
     * @param fechaEmisionString the fechaEmisionString to set
     */
    public void setFechaEmisionString(String fechaEmisionString) {
        this.fechaEmisionString = fechaEmisionString;
    }

    private Integer documentoPagoId;
    private MetodoDePagoDto metodoDePago;
    private PersonaDto persona;
    private TipoDocumentoDePago tipoDocumento;
    private String serie;
    private String numero;
    private Date fechaEmision;
    private String fechaEmisionString;
    private EstadoDocumentoDePago estado;
    private Double subtotal;
    private Double IGVTotal;
    private Double total;
    private Boolean activo;

    public DocumentoPagoDto() {
        this.documentoPagoId = null;
        this.metodoDePago = null;
        this.persona = null;
        this.tipoDocumento = null;
        this.serie = null;
        this.numero = null;
        this.fechaEmision = null;
        this.estado = null;
        this.subtotal = null;
        this.IGVTotal = null;
        this.total = null;
        this.activo = null;
        this.fechaEmisionString = null;//anotar
    }

    public DocumentoPagoDto(Integer documentoPagoId, MetodoDePagoDto metodoDePago,
            PersonaDto persona, TipoDocumentoDePago tipoDocumento, 
            String serie, String numero, Date fechaEmision, 
            EstadoDocumentoDePago estado, Double subtotal, Double IGVTotal,
            Double total, Boolean activo) {
        this.documentoPagoId = documentoPagoId;
        this.metodoDePago = metodoDePago;
        this.persona = persona;
        this.tipoDocumento = tipoDocumento;
        this.serie = serie;
        this.numero = numero;
        this.fechaEmision = fechaEmision;
        this.estado = estado;
        this.subtotal = subtotal;
        this.IGVTotal = IGVTotal;
        this.total = total;
        this.activo = activo;
        this.fechaEmisionString = fechaEmision.toString(); //anotar
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
     * @return the metodoDePago
     */
    public MetodoDePagoDto getMetodoDePago() {
        return metodoDePago;
    }

    /**
     * @param metodoDePago the metodoDePago to set
     */
    public void setMetodoDePago(MetodoDePagoDto metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    /**
     * @return the persona
     */
    public PersonaDto getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PersonaDto persona) {
        this.persona = persona;
    }

    /**
     * @return the tipoDocumento
     */
    public TipoDocumentoDePago getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(TipoDocumentoDePago tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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
     * @return the fechaEmision
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * @param fechaEmision the fechaEmision to set
     */
    public void setFechaEmision(Date fechaEmision) {
        this.setFechaEmisionString(fechaEmision.toString());
        this.fechaEmision = fechaEmision;
    }

    /**
     * @return the estado
     */
    public EstadoDocumentoDePago getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoDocumentoDePago estado) {
        this.estado = estado;
    }

    /**
     * @return the subtotal
     */
    public Double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
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
     * @param activo the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
