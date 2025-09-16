package pe.edu.pucp.softpet.model.productosDTO;

import java.util.Date;

/**
 *
 * @author User
 */
public class InventarioDTO {

    private Integer inventario_id;
    private ProductosDTO producto;
    private Date fechaultimomov;
    private String lote;
    private Integer cantidad_lote;

    public InventarioDTO() {
        this.inventario_id = null;
        this.producto = null;
        this.fechaultimomov = null;
        this.lote = null;
        this.cantidad_lote = null;
    }

    public InventarioDTO(Integer inventario_id, ProductosDTO producto, Date fechaultimomov, String lote, Integer cantidad_lote) {
        this.inventario_id = inventario_id;
        this.producto = producto;
        this.fechaultimomov = fechaultimomov;
        this.lote = lote;
        this.cantidad_lote = cantidad_lote;
    }

    /**
     * @return the inventario_id
     */
    public Integer getInventario_id() {
        return inventario_id;
    }

    /**
     * @param inventario_id the inventario_id to set
     */
    public void setInventario_id(Integer inventario_id) {
        this.inventario_id = inventario_id;
    }

    /**
     * @return the fechaultimomov
     */
    public Date getFechaultimomov() {
        return fechaultimomov;
    }

    /**
     * @param fechaultimomov the fechaultimomov to set
     */
    public void setFechaultimomov(Date fechaultimomov) {
        this.fechaultimomov = fechaultimomov;
    }

    /**
     * @return the lote
     */
    public String getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * @return the cantidad_lote
     */
    public Integer getCantidad_lote() {
        return cantidad_lote;
    }

    /**
     * @param cantidad_lote the cantidad_lote to set
     */
    public void setCantidad_lote(Integer cantidad_lote) {
        this.cantidad_lote = cantidad_lote;
    }

    /**
     * @return the producto
     */
    public ProductosDTO getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(ProductosDTO producto) {
        this.producto = producto;
    }

}
