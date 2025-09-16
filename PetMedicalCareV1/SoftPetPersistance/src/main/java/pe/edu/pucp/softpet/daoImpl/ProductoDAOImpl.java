package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.ProductoDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.dbmanager.db.DBManager;
import pe.edu.pucp.softpet.model.productosDTO.ProductosDTO;

/**
 *
 * @author ferro
 */
public class ProductoDAOImpl extends DAOImplBase implements ProductoDAO {

    private ProductosDTO producto;
    
    public ProductoDAOImpl(){
        
        super("PRODUCTO");
        this.producto = null;
        this.retornarLlavePrimaria = true;
    }
    
    public ProductoDAOImpl(String nombre_tabla){
        
        super(nombre_tabla);
        this.producto = null;
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        
        this.listaColumnas.add(new Columna ("PRODUCTO_ID", true, true));
        this.listaColumnas.add(new Columna ("TIPO_PRODUCTO_ID", false, false));
        this.listaColumnas.add(new Columna ("NOMBRE", false, false));
        this.listaColumnas.add(new Columna ("PRESENTACION", false, false));
        this.listaColumnas.add(new Columna ("PRECIO_UNITARIO", false, false));
        this.listaColumnas.add(new Columna ("CANTIDAD_TOTAL", false, false));
    }
    
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        
        // NO AUTOGENERADAS
        this.statement.setObject(1, this.producto.getTipo_producto());
        this.statement.setString(2, this.producto.getNombre());
        this.statement.setString(3, this.producto.getPresentacion());
        this.statement.setDouble(4, this.producto.getPrecio_unitario());
        this.statement.setDouble(5, this.producto.getCantidad_total());
    }
    
    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        
        // NO AUTOGENERADAS
        this.statement.setObject(1, this.producto.getTipo_producto());
        this.statement.setString(2, this.producto.getNombre());
        this.statement.setString(3, this.producto.getPresentacion());
        this.statement.setDouble(4, this.producto.getPrecio_unitario());
        this.statement.setDouble(5, this.producto.getCantidad_total());
        
        this.statement.setInt(6, this.producto.getProducto_id());
    }
    
    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        
        this.statement.setInt(6, this.producto.getProducto_id());
    }

    @Override
    public Integer insertar(ProductosDTO producto) {
        
        this.producto = producto;
        return super.insertar();
    }

    @Override
    public ProductosDTO obtenerPorId(Integer productoId) {
        // ... hoy 16/09 veré mi clase :) para hacer esto
        return producto;
    }

    @Override
    public ArrayList<ProductosDTO> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Integer modificar(ProductosDTO producto) {
        this.producto = producto;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ProductosDTO producto) {
        this.producto = producto;
        return super.eliminar();
    }

    
    
}
