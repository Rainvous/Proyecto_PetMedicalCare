package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.DetalleProductoDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.DetalleDTO.DetalleProductosDTO;
import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;
import pe.edu.pucp.softpet.model.productosDTO.ProductosDTO;

/**
 *
 * @author ferro
 */
public class DetalleProductoDAOImpl extends DAOImplBase implements DetalleProductoDAO {
    
    private DetalleProductosDTO detalleProducto;

    public DetalleProductoDAOImpl() {

        super("DETALLE_PRODUCTO");
        this.detalleProducto = null;
        this.retornarLlavePrimaria = true;
    }

    public DetalleProductoDAOImpl(String nombre_tabla) {

        super(nombre_tabla);
        this.detalleProducto = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {

        this.listaColumnas.add(new Columna("CITAXPRODUCTO_ID", true, true));
        this.listaColumnas.add(new Columna("CITA_ID", false, false));
        this.listaColumnas.add(new Columna("PRODUCTO_ID", false, false));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setObject(1, this.detalleProducto.getCitaAtencion());
        this.statement.setObject(2, this.detalleProducto.getProducto());
        this.statement.setInt(3, this.detalleProducto.getCantidad());
        this.statement.setDouble(4, this.detalleProducto.getTotal());
        this.statement.setString(5, this.detalleProducto.getDescripcion());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setObject(1, this.detalleProducto.getCitaAtencion());
        this.statement.setObject(2, this.detalleProducto.getProducto());
        this.statement.setInt(3, this.detalleProducto.getCantidad());
        this.statement.setDouble(4, this.detalleProducto.getTotal());
        this.statement.setString(5, this.detalleProducto.getDescripcion());
        
        this.statement.setInt(6, this.detalleProducto.getCita_producto_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {

        this.statement.setInt(1, this.detalleProducto.getCita_producto_id());
    }

    @Override
    public Integer insertar(DetalleProductosDTO detalleProducto) {

        this.detalleProducto = detalleProducto;
        return super.insertar();
    }

    @Override
    public DetalleProductosDTO obtenerPorId(Integer detalleProductoId) {
        this.detalleProducto = new DetalleProductosDTO();
        this.detalleProducto.setCita_producto_id(detalleProductoId);
        super.obtenerPorId();
        return this.detalleProducto;
    }

    @Override
    public ArrayList<DetalleProductosDTO> listarTodos() {
        return (ArrayList<DetalleProductosDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(DetalleProductosDTO detalleProducto) {
        this.detalleProducto = detalleProducto;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetalleProductosDTO detalleProducto) {
        this.detalleProducto = detalleProducto;
        return super.eliminar();
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.detalleProducto.getCita_producto_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detalleProducto = new DetalleProductosDTO();
        this.detalleProducto.setCita_producto_id(this.resultSet.getInt("CITAXPRODUCTO_ID"));
        this.detalleProducto.setCitaAtencion((CitaAtencionDTO) this.resultSet.getObject("CITA_ID"));
        this.detalleProducto.setProducto((ProductosDTO) this.resultSet.getObject("PRODUCTO_ID"));
        this.detalleProducto.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.detalleProducto.setTotal(this.resultSet.getDouble("TOTAL"));
        this.detalleProducto.setDescripcion(this.resultSet.getString("DESCRIPCION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.detalleProducto = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.detalleProducto);
    }
}
