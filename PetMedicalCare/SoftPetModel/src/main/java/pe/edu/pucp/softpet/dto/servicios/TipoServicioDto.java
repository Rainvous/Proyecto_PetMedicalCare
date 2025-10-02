package pe.edu.pucp.softpet.dto.servicios;

public class TipoServicioDto {
    
    private Integer tipoServicioId;
    private String nombre;
    private String descripcion;
    private Boolean activo;

    public TipoServicioDto() {
        this.tipoServicioId = null;
        this.nombre = null;
        this.descripcion = null;
        this.activo = null;
    }
    
    public TipoServicioDto(Integer tipoServicioId, String nombre, 
            String descripcion, Boolean activo) {
        this.tipoServicioId = tipoServicioId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    /**
     * @return the tipoServicioId
     */
    public Integer getTipoServicioId() {
        return tipoServicioId;
    }

    /**
     * @param tipoServicioId the tipoServicioId to set
     */
    public void setTipoServicioId(Integer tipoServicioId) {
        this.tipoServicioId = tipoServicioId;
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
