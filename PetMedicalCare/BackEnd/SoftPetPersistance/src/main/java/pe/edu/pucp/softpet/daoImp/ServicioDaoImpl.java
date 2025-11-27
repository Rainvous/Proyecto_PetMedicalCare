package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.dao.ServicioDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;
import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;

public class ServicioDaoImpl extends DaoBaseImpl implements ServicioDao {

    private ServicioDto servicio;

    public ServicioDaoImpl() {
        super("SERVICIOS");
        this.retornarLlavePrimaria = true;
        this.servicio = null;
        this.usuario = "user_backend";
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("SERVICIO_ID", true, true));
        this.listaColumnas.add(new Columna("TIPO_SERVICIO_ID", false, false));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("COSTO", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // NOTA: la auditoria se maneja con un trigger
        this.statement.setInt(1, this.servicio.getTipoServicio().getTipoServicioId());
        this.statement.setString(2, this.servicio.getNombre());
        this.statement.setString(3, this.servicio.getDescripcion());
        this.statement.setDouble(4, this.servicio.getCosto());
        this.statement.setString(5, this.servicio.getEstado());
        this.statement.setInt(6, this.servicio.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.servicio.getTipoServicio().getTipoServicioId());
        this.statement.setString(2, this.servicio.getNombre());
        this.statement.setString(3, this.servicio.getDescripcion());
        this.statement.setDouble(4, this.servicio.getCosto());
        this.statement.setString(5, this.servicio.getEstado());
        this.statement.setInt(6, this.servicio.getActivo() ? 1 : 0);

        this.statement.setInt(7, this.servicio.getServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.servicio.getServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.servicio.getServicioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.servicio = new ServicioDto();
        this.servicio.setServicioId(this.resultSet.getInt("SERVICIO_ID"));
        TipoServicioDto tp = new TipoServicioDto();
        tp.setTipoServicioId(this.resultSet.getInt("TIPO_SERVICIO_ID"));
        this.servicio.setTipoServicio(tp);
        this.servicio.setNombre(this.resultSet.getString("NOMBRE"));
        this.servicio.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.servicio.setCosto(this.resultSet.getDouble("COSTO"));
        this.servicio.setEstado(this.resultSet.getString("ESTADO"));
        this.servicio.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.servicio = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.servicio);
    }

    @Override
    public ServicioDto obtenerPorId(Integer idDto) {
        this.servicio = new ServicioDto();
        this.servicio.setServicioId(idDto);
        super.obtenerPorId();
        return this.servicio;
    }

    @Override
    public Integer insertar(ServicioDto entity) {
        this.servicio = entity;
        return super.insertar();
    }

    @Override
    public ArrayList<ServicioDto> listarTodos() {
        return (ArrayList<ServicioDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(ServicioDto entity) {
        this.servicio = entity;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ServicioDto entity) {
        this.servicio = entity;
        return super.eliminar();
    }

    /// PROCEDURES Y SELECT'
    /// @param NombreTipo
    /// @return s
    
    @Override
    public ArrayList<ServicioDto> ListarPorTipoServicio(String NombreTipo) {
        ServicioDto productoAux = new ServicioDto();
        TipoServicioDto tipo = new TipoServicioDto();
        tipo.setNombre(NombreTipo);
        productoAux.setTipoServicio(tipo);
        String sql = GenerarSQLSelectPorTipoServicio();
        return (ArrayList<ServicioDto>) super.listarTodos(sql, this::incluirValorDeParametrosParaListarPorTipo, NombreTipo);
    }

    private void incluirValorDeParametrosParaListarPorTipo(Object objetoParametro) {
        ServicioDto parametro = (ServicioDto) objetoParametro;
        String nombre = "%";
        nombre = nombre.concat(parametro.getTipoServicio().getNombre());
        nombre = nombre.concat("%");

        try {
            this.statement.setString(1, nombre);
        } catch (SQLException ex) {
            System.err.println("No se pudo incluirValores de parametro den el Statement-> " + this.statement);
            System.getLogger(ProductoDaoImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private String GenerarSQLSelectPorTipoServicio() {
        String sql = "SELECT * ";
        sql = sql.concat("FROM SERVICIOS s ");
        sql = sql.concat("JOIN TIPOS_SERVICIO ts ON ts.tipo_servicio_id=s.tipo_servicio_id ");
        sql = sql.concat("WHERE ts.nombre LIKE ?");
        return sql;
    }

    private String GenerarSQLSelectPorNombre() {
        String sql = "SELECT * ";
        sql = sql.concat("FROM PRODUCTOS p ");
        sql = sql.concat("WHERE p.nombre LIKE ?");
        return sql;
    }

    private void incluirValorDeParametrosPorNombre(Object objetoParametro) {
        ServicioDto parametro = (ServicioDto) objetoParametro;
        String nombre = "%";
        nombre = nombre.concat(parametro.getTipoServicio().getNombre());
        nombre = nombre.concat("%");

        try {
            this.statement.setString(1, nombre);
        } catch (SQLException ex) {
            System.err.println("No se pudo incluirValores de parametro den el Statement-> " + this.statement);
            System.getLogger(ProductoDaoImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @Override
    public ArrayList<ServicioDto> ListarPorNombre(String Nombre) {
        ServicioDto Aux = new ServicioDto();
        Aux.setNombre(Nombre);

        String sql = GenerarSQLSelectPorNombre();
        return (ArrayList<ServicioDto>) super.listarTodos(sql, this::incluirValorDeParametrosPorNombre, Aux);
    }

    public ArrayList<ServicioDto> ListasBusquedaAvanzada(ServicioDto servicio, String rango, String activo) {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        parametrosEntrada.put(1, servicio.getNombre());
        parametrosEntrada.put(2, rango);
        parametrosEntrada.put(3, activo);

        return (ArrayList<ServicioDto>) super.ejecutarProcedimientoLectura("sp_buscar_servicios_avanzada", parametrosEntrada);
    }

    public int VerificarSiElServicioTieneInformacion(int idServicio) {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        Map<Integer, Object> parametrosSalida = new HashMap<>();
        String NombreProcedure = "sp_verificar_relacion_servicio";
        parametrosEntrada.put(1, idServicio);
        parametrosSalida.put(2, Types.INTEGER);
        ejecutarProcedimiento(NombreProcedure, parametrosEntrada, parametrosSalida);
        int resultado = (int) parametrosSalida.get(2);
        return resultado;
    }

    @Override
    public ArrayList<ServicioDto> listarServiciosActivos() {

        // 1. Obtenemos el SQL base: "SELECT ..., ..., FROM SERVICIOS"
        String sql = super.generarSQLParaListarTodos();

        // 2. Añadimos el filtro WHERE
        sql = sql.concat(" WHERE ACTIVO = ?");

        // 3. El parámetro es fijo: 1 (para activo)
        Object parametros = 1;

        // 4. Llamamos al método listarTodos de la clase base
        return (ArrayList<ServicioDto>) super.listarTodos(sql,
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
            Logger.getLogger(ServicioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void InstanciarObjetoCitaProgramada() throws SQLException {
        //Paso 1: agregar el objeto que quieres y crea tu InstanciarObjeto a lo melgar
        servicio = new ServicioDto();
        this.servicio.setServicioId(this.resultSet.getInt("SERVICIO_ID"));
        TipoServicioDto tp = new TipoServicioDto();
        tp.setTipoServicioId(this.resultSet.getInt("TIPO_SERVICIO_ID"));
        tp.setNombre(this.resultSet.getString("NOMBRE_TIPO_SERVICIO"));
        tp.setDescripcion(this.resultSet.getString("DESCRIPCION_TIPO_SERVICIO"));
        tp.setActivo(this.resultSet.getInt("ACTIVO_TIPO_SERVICIO") == 1);
        this.servicio.setTipoServicio(tp);
        this.servicio.setNombre(this.resultSet.getString("NOMBRE"));
        this.servicio.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.servicio.setCosto(this.resultSet.getDouble("COSTO"));
        this.servicio.setEstado(this.resultSet.getString("ESTADO"));
        this.servicio.setActivo(this.resultSet.getInt("ACTIVO") == 1);

    }

    private void AgregarObjetoCitaProgramadaALaLista(Object objetoParametros) {
        //Paso 2: Crea tu puntero a funcion este recibirá una lista
        //No te preocupes la lista a pesar de ser casteada guardará la informacion del objeto
        List<ServicioDto> lista = (List<ServicioDto>) objetoParametros;
        try {
            this.InstanciarObjetoCitaProgramada();
            lista.add(this.servicio);
        } catch (SQLException ex) {
            System.err.println("No se instancio bien el objeto");
        }

    }

    // Reemplaza o actualiza tu método de búsqueda avanzada
    public ArrayList<ServicioDto> ListasBusquedaAvanzada2(ServicioDto servicio, String rango, String activo, Integer tipoId) {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        parametrosEntrada.put(1, servicio.getNombre());
        parametrosEntrada.put(2, rango);
        parametrosEntrada.put(3, activo);
        parametrosEntrada.put(4, tipoId); // Nuevo

        // Usamos el SP v3
        String sql = "sp_buscar_servicios_avanzada_v3";

        // Reutilizamos tu lógica de mapeo que ya tienes (AgregarObjetoCitaProgramadaALaLista)
        // PD: Sugiero renombrar ese método a "AgregarServicioConTipoALaLista" para que tenga sentido semántico
        return (ArrayList<ServicioDto>) this.ejecutarProcedimientoLectura(sql, parametrosEntrada, this::AgregarObjetoCitaProgramadaALaLista);
    }

    public String generarSQLparaBusquedaAvanzada() {
        // 1. Definimos la consulta base (común para ambos motores)
        // Nota: SQL Server soporta CONCAT desde la versión 2012, así que es compatible.
        String sqlBase = "SELECT "
                + "    s.SERVICIO_ID, "
                + "    s.NOMBRE, "
                + "    s.DESCRIPCION, "
                + "    s.COSTO, "
                + "    s.ESTADO, "
                + "    s.ACTIVO, "
                + "    ts.TIPO_SERVICIO_ID, "
                + "    ts.NOMBRE AS NOMBRE_TIPO, "
                + "    ts.DESCRIPCION AS DESC_TIPO, "
                + "    ts.ACTIVO AS ACTIVO_TIPO "
                + "FROM SERVICIOS s "
                + "INNER JOIN TIPOS_SERVICIO ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID "
                + "WHERE "
                + "    (? IS NULL OR s.NOMBRE LIKE CONCAT('%', ?, '%')) "
                + "    AND (? IS NULL OR s.ACTIVO = ?) "
                + "    AND ( "
                + "        ? IS NULL "
                + "        OR (? = '1' AND s.COSTO BETWEEN 0 AND 50) "
                + "        OR (? = '2' AND s.COSTO BETWEEN 51 AND 150) "
                + "        OR (? = '3' AND s.COSTO >= 151) "
                + "    ) "
                + "ORDER BY s.COSTO ASC, s.NOMBRE ASC ";

        // 2. Agregamos la paginación según el motor
        if (this.tipoMotor == MotorDeBaseDeDatos.MYSQL) {
            // MySQL: LIMIT (cantidad), OFFSET (salto)
            return sqlBase + "LIMIT ? OFFSET ?;";

        } else if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {
            // MSSQL: OFFSET (salto) ROWS FETCH NEXT (cantidad) ROWS ONLY
            return sqlBase + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        }

        return sqlBase;
    }

    // Este método reemplaza la lógica de "instanciarObjetoDelResultSet" para esta consulta específica
    public void AgregarServicioLigeroALaLista2(Object listaVoid) {
        List<ServicioDto> lista = (List<ServicioDto>) listaVoid;
        try {
            // 1. Llenar el TipoServicioDto (Anidado)
            TipoServicioDto tipo = new TipoServicioDto();
            tipo.setTipoServicioId(this.resultSet.getInt("TIPO_SERVICIO_ID"));
            tipo.setNombre(this.resultSet.getString("NOMBRE_TIPO")); // Ojo al alias del SQL
            tipo.setDescripcion(this.resultSet.getString("DESC_TIPO")); // Ojo al alias
            tipo.setActivo(this.resultSet.getBoolean("ACTIVO_TIPO")); // Ojo al alias

            // 2. Llenar el ServicioDto Principal
            servicio = new ServicioDto();
            servicio.setServicioId(this.resultSet.getInt("SERVICIO_ID"));
            servicio.setNombre(this.resultSet.getString("NOMBRE"));
            servicio.setDescripcion(this.resultSet.getString("DESCRIPCION"));
            servicio.setCosto(this.resultSet.getDouble("COSTO"));
            servicio.setEstado(this.resultSet.getString("ESTADO"));
            servicio.setActivo(this.resultSet.getBoolean("ACTIVO"));

            // Inyectamos el objeto anidado
            servicio.setTipoServicio(tipo);

            // NOTA: Los campos de auditoría (usuarioCreador, etc.) se quedan en null por defecto.
            lista.add(servicio);

        } catch (SQLException ex) {
            Logger.getLogger(ServicioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void IncluirEnSQLBusquedaAvanzada(Object parametrosVoid) {
        // Desempaquetamos el Map
        Map<String, Object> params = (Map<String, Object>) parametrosVoid;

        // Extraemos los datos individuales
        String nombre = (String) params.get("nombre");
        String rango = (String) params.get("rango"); // "1", "2", "3", ""
        Boolean activo = (Boolean) params.get("activo");
        Integer pagina = (Integer) params.get("pagina");

        // Calculamos paginación
        int limit = 8;
        int offset = (pagina - 1) * limit;

        try {
            int i = 1;

            // ... [Tus seteos de NOMBRE, ACTIVO y RANGO se mantienen igual] ...
            // (Solo copio el final para ahorrar espacio)
            // --- FILTRO NOMBRE ---
            this.statement.setString(i++, nombre);
            this.statement.setString(i++, nombre);

            // --- FILTRO ACTIVO ---
            if (activo == null) {
                this.statement.setObject(i++, null);
                this.statement.setObject(i++, null);
            } else {
                int activoInt = activo ? 1 : 0;
                this.statement.setInt(i++, activoInt);
                this.statement.setInt(i++, activoInt);
            }

            // --- FILTRO RANGO DE PRECIO ---
            this.statement.setString(i++, rango);
            this.statement.setString(i++, rango);
            this.statement.setString(i++, rango);
            this.statement.setString(i++, rango);

            // --- PAGINACIÓN (AQUI ESTA EL CAMBIO CLAVE) ---
            if (this.tipoMotor == MotorDeBaseDeDatos.MYSQL) {
                // MySQL espera: LIMIT ?, OFFSET ?
                this.statement.setInt(i++, limit);  // Cantidad a mostrar
                this.statement.setInt(i++, offset); // Cantidad a saltar

            } else if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {
                // SQL Server espera: OFFSET ? ... FETCH NEXT ? ...
                // El orden se invierte respecto a MySQL
                this.statement.setInt(i++, offset); // Cantidad a saltar va PRIMERO
                this.statement.setInt(i++, limit);  // Cantidad a mostrar va SEGUNDO
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServicioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ServicioDto> buscarServiciosPaginados(String nombre, String rangoId, Boolean activo, int pagina) {

        // Usamos un Map para no crear una clase DTO nueva solo para la búsqueda
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", nombre);
        parametros.put("rango", rangoId);
        parametros.put("activo", activo);
        parametros.put("pagina", pagina);

        String sql = generarSQLparaBusquedaAvanzada();

        // Llamamos al método padre pasándole el Map
        List<ServicioDto> lista = (List<ServicioDto>) super.listarTodos(
                sql,
                this::IncluirEnSQLBusquedaAvanzada,
                parametros, // Pasamos el Map aquí
                this::AgregarServicioLigeroALaLista2
        );

        return lista;
    }

}
