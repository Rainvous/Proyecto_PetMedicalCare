package pe.edu.pucp.softpet.dto.facturacion;

public class MetodoDePagoDto {

    private Integer metodoDePagoId;
    private String nombre;
    private Boolean activo;

    public MetodoDePagoDto() {
        this.metodoDePagoId = null;
        this.nombre = null;
        this.activo = null;
    }

    public MetodoDePagoDto(Integer metodoDePagoId, String nombre, Boolean activo) {
        this.metodoDePagoId = metodoDePagoId;
        this.nombre = nombre;
        this.activo = activo;
    }

    /**
     * @return the metodoDePagoId
     */
    public Integer getMetodoDePagoId() {
        return metodoDePagoId;
    }

    /**
     * @param metodoDePagoId the metodoDePagoId to set
     */
    public void setMetodoDePagoId(Integer metodoDePagoId) {
        this.metodoDePagoId = metodoDePagoId;
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
