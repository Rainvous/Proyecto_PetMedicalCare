package pe.edu.pucp.softpet.dto.facturacion;

import pe.edu.pucp.softpet.dto.util.enums.TipoMetodoPago;

public class MetodoDePagoDto {

    /**
     * @return the nombreStr
     */
    public String getNombreStr() {
        return nombreStr;
    }

    /**
     * @param nombreStr the nombreStr to set
     */
    public void setNombreStr(String nombreStr) {
        this.nombre.valueOf(nombreStr);
        this.nombreStr = nombreStr;
    }

    private Integer metodoDePagoId;
    private TipoMetodoPago nombre;
    private String nombreStr;
    private Boolean activo;

    public MetodoDePagoDto() {
        this.metodoDePagoId = null;
        this.nombre = null;
        this.activo = null;
    }

    public MetodoDePagoDto(Integer metodoDePagoId, TipoMetodoPago nombre, 
            Boolean activo) {
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
    public TipoMetodoPago getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(TipoMetodoPago nombre) {
        this.setNombreStr(nombre.toString());
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
