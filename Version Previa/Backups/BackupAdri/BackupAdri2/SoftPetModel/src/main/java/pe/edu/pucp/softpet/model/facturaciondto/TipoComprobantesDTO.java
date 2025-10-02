package pe.edu.pucp.softpet.model.facturaciondto;

/**
 *
 * @author ferro
 */
public class TipoComprobantesDTO {
    
    private Integer tipo_comprobante_id;
    private String nombre;

    public TipoComprobantesDTO() {
        this.tipo_comprobante_id = null;
        this.nombre = null;
    }
    
    public TipoComprobantesDTO(Integer tipo_comprobante_id, String nombre) {
        this.tipo_comprobante_id = tipo_comprobante_id;
        this.nombre = nombre;
    }

    /**
     * @return the tipo_comprobante_id
     */
    public Integer getTipo_comprobante_id() {
        return tipo_comprobante_id;
    }

    /**
     * @param tipo_comprobante_id the tipo_comprobante_id to set
     */
    public void setTipo_comprobante_id(Integer tipo_comprobante_id) {
        this.tipo_comprobante_id = tipo_comprobante_id;
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
    
    
}
