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

    public Integer getMetodoDePagoId() {
        return metodoDePagoId;
    }

    public void setMetodoDePagoId(Integer metodoDePagoId) {
        this.metodoDePagoId = metodoDePagoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
