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
                + "    tp.TIPO_PRODUCTO_ID, "
                + "    tp.NOMBRE AS NOMBRE_TIPO, "
                + "    tp.DESCRIPCION AS DESC_TIPO, "
                + "    tp.ACTIVO AS ACTIVO_TIPO "
                + "FROM PRODUCTOS p "
                + "INNER JOIN TIPOS_PRODUCTO tp ON p.TIPO_PRODUCTO_ID = tp.TIPO_PRODUCTO_ID "
                + "WHERE "
                + "    (? IS NULL OR p.NOMBRE LIKE CONCAT('%', ?, '%')) " // 1, 2: Nombre
                + "    AND (? IS NULL OR p.ACTIVO = ?) " // 3, 4: Activo (Si pasas NULL trae todo, si pasas 1 solo activos)
                + "    AND ( "
                + "        ? IS NULL " // 5: Rango Check Null
                + "        OR (? = '1' AND p.PRECIO_UNITARIO BETWEEN 0 AND 50) " // 6: Rango 1
                + "        OR (? = '2' AND p.PRECIO_UNITARIO BETWEEN 51 AND 150) " // 7: Rango 2
                + "        OR (? = '3' AND p.PRECIO_UNITARIO > 150) " // 8: Rango 3
                + "    ) "
                + "ORDER BY p.NOMBRE ASC ";

        if (this.tipoMotor == MotorDeBaseDeDatos.MYSQL) {
            return sqlBase + "LIMIT ? OFFSET ?;";
        } else if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {
            return sqlBase + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        }
        return sqlBase;
    }

    public void IncluirEnSQLBusquedaAvanzadaProducto(Object parametrosVoid) {
        Map<String, Object> params = (Map<String, Object>) parametrosVoid;

        // Validación y limpieza: Convertir cadenas vacías "" a NULL para que el SQL funcione
        String nombreRaw = (String) params.get("nombre");
        // Nombre vacío funciona con LIKE '%%'

        String rangoRaw = (String) params.get("rango");
        String rango = (rangoRaw != null && !rangoRaw.isEmpty()) ? rangoRaw : null; // Rango vacío DEBE ser NULL

        Integer activoInt = (Integer) params.get("activo"); // Recibimos Integer (1, 0 o NULL)
        Integer pagina = (Integer) params.get("pagina");

        // CAMBIO SOLICITADO: Limite de 9 items por página
        int limit = 9;
        int offset = (pagina - 1) * limit;

        try {
            int i = 1;

            // --- Filtro Nombre ---
            // Si es nulo o vacío, pasamos null al primer '?' para activar el IS NULL
            // O podemos pasar "" al segundo '?' para que el LIKE '%%' funcione.
            // Opción segura: Si hay dato, se usa. Si no, se manda null para activar el IS NULL.
            String nombreParam = (nombreRaw != null && !nombreRaw.isEmpty()) ? nombreRaw : null;

            this.statement.setString(i++, nombreParam); // Para el check IS NULL
            // Si nombreParam es null, pasamos "" al concat para evitar problemas, aunque el IS NULL ya nos salvó.
            this.statement.setString(i++, (nombreParam != null) ? nombreParam : "");

            // --- Filtro Activo ---
            if (activoInt == null) {
                this.statement.setObject(i++, null);
                this.statement.setObject(i++, null);
            } else {
                this.statement.setInt(i++, activoInt);
                this.statement.setInt(i++, activoInt);
            }

            // --- Filtro Rango de Precio ---
            // IMPORTANTE: Aquí usamos la variable 'rango' que ya saneamos a NULL si venía vacía
            this.statement.setString(i++, rango); // Check IS NULL
            this.statement.setString(i++, rango); // Comp 1
            this.statement.setString(i++, rango); // Comp 2
            this.statement.setString(i++, rango); // Comp 3

            // --- Paginación ---
            if (this.tipoMotor == MotorDeBaseDeDatos.MYSQL) {
                this.statement.setInt(i++, limit);
                this.statement.setInt(i++, offset);
            } else if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {
                this.statement.setInt(i++, offset);
                this.statement.setInt(i++, limit);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AgregarProductoLigeroALaLista(Object listaVoid) {
        List<ProductoDto> lista = (List<ProductoDto>) listaVoid;
        try {
            TipoProductoDto tipo = new TipoProductoDto();
            tipo.setTipoProductoId(this.resultSet.getInt("TIPO_PRODUCTO_ID"));
            tipo.setNombre(this.resultSet.getString("NOMBRE_TIPO"));
            tipo.setDescripcion(this.resultSet.getString("DESC_TIPO"));
            tipo.setActivo(this.resultSet.getBoolean("ACTIVO_TIPO"));

            this.producto = new ProductoDto();
            this.producto.setProductoId(this.resultSet.getInt("PRODUCTO_ID"));
            this.producto.setNombre(this.resultSet.getString("NOMBRE"));
            this.producto.setPresentacion(this.resultSet.getString("PRESENTACION"));
            this.producto.setPrecioUnitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
            this.producto.setStock(this.resultSet.getInt("STOCK"));
            this.producto.setActivo(this.resultSet.getBoolean("ACTIVO"));
            this.producto.setTipoProducto(tipo);

            lista.add(this.producto);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // 4. Método Público Orquestador
    public List<ProductoDto> buscarProductosPaginados(String nombre, String rangoId, Boolean activo, int pagina) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", nombre);
        parametros.put("rango", rangoId);

        Integer activoParam = null;
        if (activo != null && activo) {
            activoParam = 1;
        }

        parametros.put("activo", activoParam);
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
