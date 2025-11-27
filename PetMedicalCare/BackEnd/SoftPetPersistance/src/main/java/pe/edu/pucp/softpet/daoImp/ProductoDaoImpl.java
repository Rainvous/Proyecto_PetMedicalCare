package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.dao.ProductoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;
import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;

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

        // MAPEO DEL TIPO (JOIN)
        TipoProductoDto tp = new TipoProductoDto();
        tp.setTipoProductoId(this.resultSet.getInt("TIPO_PRODUCTO_ID"));
        try {
            // Intentamos leer el nombre del tipo si vino en el SP
            tp.setNombre(this.resultSet.getString("NOMBRE_TIPO"));
            tp.setDescripcion(this.resultSet.getString("DESC_TIPO"));
        } catch (SQLException e) {
            // Si es un select simple sin join, ignoramos
        }
        this.producto.setTipoProducto(tp);

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

    @Override
    public ArrayList<ProductoDto> listarProductosActivos() {

        // 1. Obtenemos el SQL base: "SELECT ..., ..., FROM PRODUCTOS"
        String sql = super.generarSQLParaListarTodos();

        // 2. Añadimos el filtro WHERE
        sql = sql.concat(" WHERE ACTIVO = ?");

        // 3. El parámetro es fijo: 1 (para activo)
        Object parametros = 1;

        // 4. Llamamos al método listarTodos de la clase base
        return (ArrayList<ProductoDto>) super.listarTodos(sql,
                this::incluirValorDeParametrosParaListarActivos,
                parametros);
    }

    private void incluirValorDeParametrosParaListarActivos(Object objetoParametros) {
        // Casteamos el objeto de parámetros a su tipo original
        Integer activoFlag = (Integer) objetoParametros;
        try {
            // Asignamos el '1' al primer '?' en el SQL
            this.statement.setInt(1, activoFlag);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int VerificarSiElProductoTieneInformacion(int idServicio) {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        Map<Integer, Object> parametrosSalida = new HashMap<>();
        String NombreProcedure = "sp_verificar_relacion_producto";
        parametrosEntrada.put(1, idServicio);
        parametrosSalida.put(2, Types.INTEGER);
        ejecutarProcedimiento(NombreProcedure, parametrosEntrada, parametrosSalida);
        int resultado = (int) parametrosSalida.get(2);
        return resultado;
    }

    // Agrega el parámetro Integer tipoId a la firma
    @Override
    public ArrayList<ProductoDto> ListasBusquedaProductosAvanzada(ProductoDto producto, String rango, String activo, Integer tipoId) {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();

        parametrosEntrada.put(1, producto.getNombre());

        if (rango == null || rango.equals("Todos") || rango.isEmpty()) {
            parametrosEntrada.put(2, null);
        } else {
            parametrosEntrada.put(2, rango);
        }

        if (activo == null || activo.equals("Todos") || activo.isEmpty()) {
            parametrosEntrada.put(3, null);
        } else {
            parametrosEntrada.put(3, activo);
        }

        // Nuevo parámetro: Tipo ID (Si es null o 0, el SP lo maneja)
        parametrosEntrada.put(4, tipoId);

        return (ArrayList<ProductoDto>) super.ejecutarProcedimientoLectura("sp_buscar_productos_avanzada", parametrosEntrada);
    }

    public String generarSQLparaBusquedaAvanzadaProducto() {
        // Definimos la consulta base común para ambos motores
        String sqlBase = "SELECT "
                + "    p.PRODUCTO_ID, "
                + "    p.NOMBRE, "
                + "    p.PRESENTACION, "
                + "    p.PRECIO_UNITARIO, "
                + "    p.STOCK, "
                + "    p.ACTIVO, "
                // Datos del Tipo de Producto (Anidado)
                + "    tp.TIPO_PRODUCTO_ID, "
                + "    tp.NOMBRE AS NOMBRE_TIPO, "
                + "    tp.DESCRIPCION AS DESC_TIPO, "
                + "    tp.ACTIVO AS ACTIVO_TIPO "
                + "FROM PRODUCTOS p "
                + "INNER JOIN TIPOS_PRODUCTO tp ON p.TIPO_PRODUCTO_ID = tp.TIPO_PRODUCTO_ID "
                + "WHERE "
                + "    (? IS NULL OR p.NOMBRE LIKE CONCAT('%', ?, '%')) " // 1, 2: Nombre
                + "    AND (? IS NULL OR p.ACTIVO = ?) " // 3, 4: Activo
                + "    AND ( "
                + "        ? IS NULL " // 5: Rango Check Null
                + "        OR (? = '1' AND p.PRECIO_UNITARIO BETWEEN 0 AND 50) " // 6: Rango 1
                + "        OR (? = '2' AND p.PRECIO_UNITARIO BETWEEN 51 AND 150) " // 7: Rango 2
                + "        OR (? = '3' AND p.PRECIO_UNITARIO > 150) " // 8: Rango 3
                + "    ) "
                + "ORDER BY p.NOMBRE ASC ";

        // Agregamos la paginación según el motor
        if (this.tipoMotor == MotorDeBaseDeDatos.MYSQL) {
            // MySQL: LIMIT [cantidad] OFFSET [salto]
            return sqlBase + "LIMIT ? OFFSET ?;";

        } else if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {
            // SQL Server: OFFSET [salto] ... FETCH NEXT [cantidad] ...
            return sqlBase + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        }

        return sqlBase;
    }

    public void IncluirEnSQLBusquedaAvanzadaProducto(Object parametrosVoid) {
        Map<String, Object> params = (Map<String, Object>) parametrosVoid;

        String nombre = (String) params.get("nombre");
        String rango = (String) params.get("rango");
        Boolean activo = (Boolean) params.get("activo");
        Integer pagina = (Integer) params.get("pagina");

        int limit = 8; // Tamaño de página fijo
        int offset = (pagina - 1) * limit;

        try {
            int i = 1;

            // --- Filtro Nombre ---
            this.statement.setString(i++, nombre);
            this.statement.setString(i++, nombre);

            // --- Filtro Activo ---
            if (activo == null) {
                this.statement.setObject(i++, null);
                this.statement.setObject(i++, null);
            } else {
                int valorBit = activo ? 1 : 0;
                this.statement.setInt(i++, valorBit);
                this.statement.setInt(i++, valorBit);
            }

            // --- Filtro Rango de Precio ---
            this.statement.setString(i++, rango);
            this.statement.setString(i++, rango);
            this.statement.setString(i++, rango);
            this.statement.setString(i++, rango);

            // --- Paginación (Adaptada al Motor) ---
            if (this.tipoMotor == MotorDeBaseDeDatos.MYSQL) {
                // MySQL espera: LIMIT (cantidad), OFFSET (salto)
                this.statement.setInt(i++, limit);
                this.statement.setInt(i++, offset);

            } else if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {
                // SQL Server espera: OFFSET (salto) ... FETCH NEXT (cantidad)
                this.statement.setInt(i++, offset); // Primero el salto
                this.statement.setInt(i++, limit);  // Luego la cantidad
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // 3. Mapeo de Resultados (ResultSet -> ProductoDto)

    public void AgregarProductoLigeroALaLista(Object listaVoid) {
        List<ProductoDto> lista = (List<ProductoDto>) listaVoid;
        try {
            // A. Instanciar TipoProductoDto
            TipoProductoDto tipo = new TipoProductoDto();
            tipo.setTipoProductoId(this.resultSet.getInt("TIPO_PRODUCTO_ID"));
            tipo.setNombre(this.resultSet.getString("NOMBRE_TIPO"));
            tipo.setDescripcion(this.resultSet.getString("DESC_TIPO"));
            tipo.setActivo(this.resultSet.getBoolean("ACTIVO_TIPO"));

            // B. Instanciar ProductoDto
            this.producto = new ProductoDto();
            producto.setProductoId(this.resultSet.getInt("PRODUCTO_ID"));
            producto.setNombre(this.resultSet.getString("NOMBRE"));
            producto.setPresentacion(this.resultSet.getString("PRESENTACION"));
            producto.setPrecioUnitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
            producto.setStock(this.resultSet.getInt("STOCK"));
            producto.setActivo(this.resultSet.getBoolean("ACTIVO"));

            // Asignar el objeto anidado
            producto.setTipoProducto(tipo);

            lista.add(producto);

        } catch (SQLException ex) {
            Logger.getLogger(ProductoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// 4. Método Público Orquestador
    public List<ProductoDto> buscarProductosPaginados(String nombre, String rangoId, Boolean activo, int pagina) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", nombre);
        parametros.put("rango", rangoId);
        parametros.put("activo", activo);
        parametros.put("pagina", pagina);

        String sql = generarSQLparaBusquedaAvanzadaProducto();

        return (List<ProductoDto>) super.listarTodos(
                sql,
                this::IncluirEnSQLBusquedaAvanzadaProducto,
                parametros,
                this::AgregarProductoLigeroALaLista
        );
    }

}
