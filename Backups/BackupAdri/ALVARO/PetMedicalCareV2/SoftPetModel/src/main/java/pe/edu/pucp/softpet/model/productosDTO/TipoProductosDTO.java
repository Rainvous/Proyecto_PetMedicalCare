package pe.edu.pucp.softpet.model.productosDTO;

/**
 *
 * @author User
 */
public class TipoProductosDTO {

    private Integer tipo_producto_id;
    private String nombre;
    private String descripcion;

    public TipoProductosDTO() {
        tipo_producto_id = null;
        nombre = null;
        descripcion = null;
    }

    public TipoProductosDTO(Integer tipo_producto_id,
            String nombre,
            String descripcion) {
        this.tipo_producto_id = tipo_producto_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * @return the tipo_producto_id
     */
    public Integer getTipo_producto_id() {
        return tipo_producto_id;
    }

    /**
     * @param tipo_producto_id the tipo_producto_id to set
     */
    public void setTipo_producto_id(Integer tipo_producto_id) {
        this.tipo_producto_id = tipo_producto_id;
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
