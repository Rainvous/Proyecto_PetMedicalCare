package pe.edu.pucp.softpet.model.serviciosdto;

/**
 *
 * @author snipe
 */
public class TipoServiciosDTO {

    private Integer tipoServicioId;
    private String nombre;
    private String descripcion;

    public TipoServiciosDTO() {
        this.tipoServicioId = null;
        this.nombre = null;
        this.descripcion = null;
    }

    public TipoServiciosDTO(Integer tipoServicioId, String nombre, String descripcion) {
        this.tipoServicioId = tipoServicioId;
        this.nombre = nombre;
        this.descripcion = descripcion;
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
}
