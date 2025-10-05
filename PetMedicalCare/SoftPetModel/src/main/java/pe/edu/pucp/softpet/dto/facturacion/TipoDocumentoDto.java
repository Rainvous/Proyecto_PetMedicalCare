package pe.edu.pucp.softpet.dto.facturacion;

public class TipoDocumentoDto {
    
    private Integer tipoDocumentoId;
    private String nombre;
    private Boolean activo;

    public TipoDocumentoDto() {
        this.tipoDocumentoId = null;
        this.nombre = null;
        this.activo = null;
    }
    
    public TipoDocumentoDto(Integer tipoDocumentoId, String nombre, Boolean activo) {
        this.tipoDocumentoId = tipoDocumentoId;
        this.nombre = nombre;
        this.activo = activo;
    }

    /**
     * @return the tipoDocumentoId
     */
    public Integer getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    /**
     * @param tipoDocumentoId the tipoDocumentoId to set
     */
    public void setTipoDocumentoId(Integer tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
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
