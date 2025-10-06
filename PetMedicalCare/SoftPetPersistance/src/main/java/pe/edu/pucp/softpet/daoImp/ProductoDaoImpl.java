package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.ProductoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;

/**
 *
 * @author User
 */
public class ProductoDaoImpl extends DaoBaseImpl implements ProductoDao {

    private ProductoDto producto;

    public ProductoDaoImpl() {
        super("PRODUCTOS");
        this.retornarLlavePrimaria = true;
        this.producto = null;
        this.usuario = "user_backend";
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PRODUCTO_ID", true, true));  // PK, autogenerado
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("PRESENTACION", false, false));
        this.listaColumnas.add(new Columna("PRECIO_UNITARIO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("TIPO_PRODUCTO_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // NOTA NO ES NECESARIO AGREGAR LA FECHA
        this.statement.setString(1, this.producto.getNombre());
        this.statement.setString(2, this.producto.getPresentacion());
        this.statement.setDouble(3, this.producto.getPrecioUnitario());
        this.statement.setInt(4, this.producto.getActivo() ? 1 : 0);
        this.statement.setInt(5, this.producto.getTipoProducto().getTipoProductoId());
        // System.out.println(statement);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.producto.getNombre());
        this.statement.setString(2, this.producto.getPresentacion());
        this.statement.setDouble(3, this.producto.getPrecioUnitario());
        this.statement.setInt(4, this.producto.getActivo() ? 1 : 0);
        this.statement.setInt(5, this.producto.getTipoProducto().getTipoProductoId());

        this.statement.setInt(6, this.producto.getProductoId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.producto.getProductoId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.producto.getProductoId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.producto = new ProductoDto();
        this.producto.setProductoId(this.resultSet.getInt("PRODUCTO_ID"));
        this.producto.setNombre(this.resultSet.getString("NOMBRE"));
        this.producto.setPresentacion(this.resultSet.getString("PRESENTACION"));
        this.producto.setPrecioUnitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
        this.producto.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        this.producto.setTipoProducto(new TipoProductoDaoImpl().
                obtenerPorId(this.resultSet.getInt("TIPO_PRODUCTO_ID")));
//        TipoProductoDto tipoProducto = new TipoProductoDto();
//        tipoProducto.setTipoProductoId(this.resultSet.getInt("TIPO_PRODUCTO_ID"));
//
//        this.producto.setTipoProducto(tipoProducto);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.producto = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.producto);
    }

    @Override
    public ProductoDto obtenerPorId(Integer idDto) {
        this.producto = new ProductoDto();
        this.producto.setProductoId(idDto);
        super.obtenerPorId();
        return this.producto;
    }

    @Override
    public ArrayList<ProductoDto> listarTodos() {
        return (ArrayList<ProductoDto>) super.listarTodos();
    }

    @Override
    public Integer insertar(ProductoDto entity) {
        this.producto = entity;
        return super.insertar();
    }

    @Override
    public Integer modificar(ProductoDto entity) {
        this.producto = entity;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ProductoDto entity) {
        this.producto = entity;
        return super.eliminar();
    }
}
