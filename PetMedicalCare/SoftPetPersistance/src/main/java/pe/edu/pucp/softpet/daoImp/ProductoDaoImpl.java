package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.ProductoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;

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
        this.listaColumnas.add(new Columna("PRODUCTO_ID", true, true));
        this.listaColumnas.add(new Columna("TIPO_PRODUCTO_ID", false, false));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("PRESENTACION", false, false));
        this.listaColumnas.add(new Columna("PRECIO_UNITARIO", false, false));
        this.listaColumnas.add(new Columna("STOCK", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // NOTA: la auditoria se maneja con un trigger
        this.statement.setInt(1, this.producto.getTipoProducto().getTipoProductoId());
        this.statement.setString(2, this.producto.getNombre());
        this.statement.setString(3, this.producto.getPresentacion());
        this.statement.setDouble(4, this.producto.getPrecioUnitario());
        this.statement.setInt(5, this.producto.getStock());
        this.statement.setInt(6, this.producto.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.producto.getTipoProducto().getTipoProductoId());
        this.statement.setString(2, this.producto.getNombre());
        this.statement.setString(3, this.producto.getPresentacion());
        this.statement.setDouble(4, this.producto.getPrecioUnitario());
        this.statement.setInt(5, this.producto.getStock());
        this.statement.setInt(6, this.producto.getActivo() ? 1 : 0);

        this.statement.setInt(7, this.producto.getProductoId());
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
        this.producto.setTipoProducto(new TipoProductoDaoImpl().
                obtenerPorId(this.resultSet.getInt("TIPO_PRODUCTO_ID")));
        this.producto.setNombre(this.resultSet.getString("NOMBRE"));
        this.producto.setPresentacion(this.resultSet.getString("PRESENTACION"));
        this.producto.setPrecioUnitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
        this.producto.setStock(this.resultSet.getInt("STOCK"));
        this.producto.setActivo(this.resultSet.getInt("ACTIVO") == 1);
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

    /// PROCEDURES Y SELECT's
    
    /// @param NombreTipo
    /// @return T
    
    @Override
    public ArrayList<ProductoDto> ListarPorTipo(String NombreTipo) {
        ProductoDto productoAux = new ProductoDto();
        TipoProductoDto tipo = new TipoProductoDto();
        tipo.setNombre(NombreTipo);
        productoAux.setTipoProducto(tipo);
        String sql = GenerarSQLSelectPorTipo();
        return (ArrayList<ProductoDto>) super.listarTodos(sql, this::incluirValorDeParametrosParaListarPorTipo, productoAux);
    }

    private String GenerarSQLSelectPorTipo() {
        String sql = "SELECT * ";
        sql = sql.concat("FROM PRODUCTOS p ");
        sql = sql.concat("JOIN TIPOS_PRODUCTO tp ON tp.tipo_producto_id=p.tipo_producto_id ");
        sql = sql.concat("WHERE tp.nombre LIKE ?");
        return sql;
    }

    private String GenerarSQLSelectPorNombre() {
        String sql = "SELECT * ";
        sql = sql.concat("FROM PRODUCTOS p ");
        sql = sql.concat("WHERE p.nombre LIKE ?");
        return sql;
    }

    private void incluirValorDeParametrosPorNombre(Object objetoParametro) {
        ProductoDto parametro = (ProductoDto) objetoParametro;
        String nombre = "%";
        nombre = nombre.concat(parametro.getNombre());
        nombre = nombre.concat("%");

        try {
            this.statement.setString(1, nombre);

        } catch (SQLException ex) {
            System.err.println("No se pudo incluirValores de parametro den el Statement-> " + this.statement);
            System.getLogger(ProductoDaoImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void incluirValorDeParametrosParaListarPorTipo(Object objetoParametro) {
        ProductoDto parametro = (ProductoDto) objetoParametro;
        String tipo = "%";
        tipo = tipo.concat(parametro.getTipoProducto().getNombre());
        tipo = tipo.concat("%");

        try {
            this.statement.setString(1, tipo);

        } catch (SQLException ex) {
            System.err.println("No se pudo incluirValores de parametro den el Statement-> " + this.statement);
            System.getLogger(ProductoDaoImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @Override
    public ArrayList<ProductoDto> ListarPorNombre(String Nombre) {
        ProductoDto productoAux = new ProductoDto();
        productoAux.setNombre(Nombre);

        String sql = GenerarSQLSelectPorNombre();
        return (ArrayList<ProductoDto>) super.listarTodos(sql, this::incluirValorDeParametrosPorNombre, productoAux);
    }
}
