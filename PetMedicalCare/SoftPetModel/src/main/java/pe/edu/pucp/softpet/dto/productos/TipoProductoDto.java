package pe.edu.pucp.softpet.dto.productos;

public class TipoProductoDto {

    private Integer tipoProductoId;
    private String nombre;
    private String descripcion;
    private Boolean activo;

    public TipoProductoDto() {
        this.tipoProductoId = null;
        this.nombre = null;
        this.descripcion = null;
        this.activo = null;
    }
    
    public TipoProductoDto(Integer tipoProductoId, String nombre, 
            String descripcion, Boolean activo) {
        this.tipoProductoId = tipoProductoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    /**
     * @return the tipoProductoId
     */
    public Integer getTipoProductoId() {
        return tipoProductoId;
    }

    /**
     * @param tipoProductoId the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
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
