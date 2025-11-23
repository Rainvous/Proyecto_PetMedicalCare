package pe.edu.pucp.softpet.daoImp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.daoImp.util.Tipo_Operacion;
import pe.edu.pucp.softpet.db.DBManager;
import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;

public abstract class DaoBaseImpl {

    /* ATRIBUTOS DEFAULT */
    protected String nombre_tabla;
    protected ArrayList<Columna> listaColumnas;
    protected Boolean retornarLlavePrimaria;
    protected Connection conexion;
    protected CallableStatement statement;
    protected ResultSet resultSet;

    /* ATRIBUTOS QUE HAN SIDO AGREGADOS AL DAO */
    protected String usuario; //Esto es para el trigger
    protected MotorDeBaseDeDatos tipoMotor = MotorDeBaseDeDatos.MYSQL; // Ayuda a cambiar rapidamente entre motores (El dbManager tmb ha sido modificado)
    protected boolean TieneAuditoria = false; // Tiene auditoria es para activar el trigger de auditoria de algunas tablas (Y AYUDA A SETEAR EL USUARIO que modifico la tabla)

    /* NOTA: La auditoria solo es para tablas que tienen triggers */

 /*
    ------------------------------------------------------------------------
    INICIO DE FUNCIONES PARA AUDITORIA Y MOTOR DE BASE DE DATOS (by AmaruMVP)
    ------------------------------------------------------------------------
     */
    public void EstablecerMotorBaseDeDatos(MotorDeBaseDeDatos input) {
        tipoMotor = input;
    }

    public void EstablecerMotorBaseDeDatos(String input) {
        if (MotorDeBaseDeDatos.MSSQL.toString().equals(input)) {
            tipoMotor = MotorDeBaseDeDatos.MSSQL;
        } else {
            tipoMotor = MotorDeBaseDeDatos.MYSQL;
        }
    }
    // NUEVO CONSTRUCTOR PARA AUDITORIA

    public DaoBaseImpl(String nombre_tabla, boolean TieneAuditoria) {
        this.nombre_tabla = nombre_tabla;
        this.retornarLlavePrimaria = false;
        this.TieneAuditoria = TieneAuditoria;
        this.incluirListaDeColumnas();
    }

    // FUNCION PARA AUDITORIA 
    //NOTA : Agregar un booleano para no usar siempre esta funcion
    private void SetDeUsuario(String usuario) throws SQLException {
        // NOTA IMPORTANTE: ESTE SET SE USA DURANTE LA TRANSACCION
        // NO SE PUEDE HACER APARTE PORQUE SE ABRE Y CIERRA CONEXIONES VARIAS VECES
        if (this.usuario == null) {
            return;
        }
        if (usuario.isEmpty()) {
            return;
        }
        try (PreparedStatement psSet = this.conexion.prepareStatement("SET @app_user := ?")) {
            //System.out.println("------>" + psSet);
            psSet.setString(1, usuario);
            //System.out.println("------>" + psSet);
            psSet.execute();
        }
    }

    public void NombreDelUsuarioQueModifica(String user) {
        this.usuario = user;
    }

    public void EjecutaSetUsuario() throws SQLException {
        ejecutarDMLEnBD();
    }

    /*
    ------------------------------------------------------------------------
    FIN de las funciones de auditoria y motor de base de datos
    ------------------------------------------------------------------------
     */

 /*
    ------------------------------------------------------------------------
    INICIO de las  Funciones del DaoImplBase Deafult
    ------------------------------------------------------------------------
     */
    public DaoBaseImpl(String nombre_tabla) {
        this.nombre_tabla = nombre_tabla;
        this.retornarLlavePrimaria = false;

        this.incluirListaDeColumnas();
    }

    private void incluirListaDeColumnas() {
        this.listaColumnas = new ArrayList<>();
        this.configurarListaDeColumnas();
    }

    protected abstract void configurarListaDeColumnas();

    protected void abrirConexion() {
        this.conexion = DBManager.getInstance(this.tipoMotor).getConnection();
    }

    protected void cerrarConexion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.close();
        }
    }

    protected void iniciarTransaccion() throws SQLException {
        this.abrirConexion();
        this.conexion.setAutoCommit(false);
    }

    protected void comitarTransaccion() throws SQLException {
        this.conexion.commit();
    }

    protected void rollbackTransaccion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.rollback();
        }
    }

    protected void colocarSQLEnStatement(String sql) throws SQLException {
        System.out.println("Colocar SQL: " + sql);
        this.statement = this.conexion.prepareCall(sql);
    }

    protected Integer ejecutarDMLEnBD() throws SQLException {
        return this.statement.executeUpdate();
    }

    protected Integer ejecutarProcedureConParametroEntradaSalida() throws SQLException {
        return this.statement.executeUpdate();
    }

    protected void ejecutarSelectEnDB() throws SQLException {
        this.resultSet = this.statement.executeQuery();
    }

    protected Integer insertar() {
        return this.ejecuta_DML(Tipo_Operacion.INSERTAR);
    }

    protected Integer modificar() {
        return this.ejecuta_DML(Tipo_Operacion.MODIFICAR);
    }

    protected Integer eliminar() {
        return this.ejecuta_DML(Tipo_Operacion.ELIMINAR);
    }

    private Integer ejecuta_DML(Tipo_Operacion tipo_operacion) {
        Integer resultado = 0;

        try {
            this.iniciarTransaccion();
            // NOTA: FALTA IMPLEMENTAR PARA MSSQL
            if (this.TieneAuditoria) {
                this.SetDeUsuario(usuario);
            }
            String sql = null;
            switch (tipo_operacion) {
                case Tipo_Operacion.INSERTAR:
                    sql = this.generarSQLParaInsercion();
                    break;
                case Tipo_Operacion.MODIFICAR:
                    sql = this.generarSQLParaModificacion();
                    break;
                case Tipo_Operacion.ELIMINAR:
                    sql = this.generarSQLParaEliminacion();
                    break;
            }
            this.colocarSQLEnStatement(sql);
            //System.out.println("CRUD: " + sql);
            switch (tipo_operacion) {
                case Tipo_Operacion.INSERTAR:
                    this.incluirValorDeParametrosParaInsercion();
                    break;
                case Tipo_Operacion.MODIFICAR:
                    this.incluirValorDeParametrosParaModificacion();
                    break;
                case Tipo_Operacion.ELIMINAR:
                    this.incluirValorDeParametrosParaEliminacion();
                    break;
            }
            //System.out.println("CRUD de statement: " + statement);
            resultado = this.ejecutarDMLEnBD();
            if (this.retornarLlavePrimaria && tipo_operacion == Tipo_Operacion.INSERTAR) {
                resultado = this.retornarUltimoAutoGenerado();
            }
            this.comitarTransaccion();
        } catch (SQLException ex) {
            System.err.println("Error al intentar insertar - " + ex);
            try {
                this.rollbackTransaccion();
            } catch (SQLException ex1) {
                System.err.println("Error al hacer rollback - " + ex1);
            }
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return resultado;
    }

    protected String generarSQLParaInsercion() {
        // La sentencia que se generará es similiar a
        // INSERT INTO INV_ALMACENES (NOMBRE, ALMACEN_CENTRAL) VALUES (?,?)
        String sql = "INSERT INTO ";
        sql = sql.concat(this.nombre_tabla);
        sql = sql.concat("(");
        String sql_columnas = "";
        String sql_parametros = "";
        for (Columna columna : this.listaColumnas) {
            if (!columna.getEsAutoGenerado()) {
                if (!sql_columnas.isBlank()) {
                    sql_columnas = sql_columnas.concat(", ");
                    sql_parametros = sql_parametros.concat(", ");
                }
                sql_columnas = sql_columnas.concat(columna.getNombre());
                sql_parametros = sql_parametros.concat("?");
            }
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(") VALUES (");
        sql = sql.concat(sql_parametros);
        sql = sql.concat(")");
        return sql;
    }

    protected String generarSQLParaModificacion() {
        // Sentencia SQL a generar es similar a 
        // UPDATE INV_ALMACENES SET NOMBRE=?, ALMACEN_CENTRAL=? WHERE ALMACEN_ID=?
        String sql = "UPDATE ";
        sql = sql.concat(this.nombre_tabla);
        sql = sql.concat(" SET ");
        String sql_columnas = "";
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    //no está probado
                    sql_predicado = sql_predicado.concat(" AND ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            } else {
                if (!sql_columnas.isBlank()) {
                    sql_columnas = sql_columnas.concat(", ");
                }
                sql_columnas = sql_columnas.concat(columna.getNombre());
                sql_columnas = sql_columnas.concat("=?");
            }
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" WHERE ");
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaEliminacion() {
        // Sentencia SQL a generar es similar a 
        // DELETE FROM INV_ALMACENES WHERE ALMACEN_ID=?
        String sql = "DELETE FROM ";
        sql = sql.concat(this.nombre_tabla);
        sql = sql.concat(" WHERE ");
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            }
        }
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaObtenerPorId() {
        // Sentencia SQL a generar es similar a 
        // SELECT ALMACEN_ID, NOMBRE, ALMACEN_CENTRAL FROM INV_ALMACENES WHERE ALMACEN_ID = ?
        String sql = "SELECT ";
        String sql_columnas = "";
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            }
            if (!sql_columnas.isBlank()) {
                sql_columnas = sql_columnas.concat(", ");
            }
            sql_columnas = sql_columnas.concat(columna.getNombre());
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" FROM ");
        sql = sql.concat(this.nombre_tabla);
        sql = sql.concat(" WHERE ");
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaListarTodos() {
        // Sentencia SQL a generar es similar a 
        // SELECT ALMACEN_ID, NOMBRE, ALMACEN_CENTRAL FROM INV_ALMACENES
        String sql = "SELECT ";
        String sql_columnas = "";
        for (Columna columna : this.listaColumnas) {
            if (!sql_columnas.isBlank()) {
                sql_columnas = sql_columnas.concat(", ");
            }
            sql_columnas = sql_columnas.concat(columna.getNombre());
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" FROM ");
        sql = sql.concat(this.nombre_tabla);
        return sql;
    }

    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Integer retornarUltimoAutoGenerado() {
        Integer resultado = null;
        try {
            String sql = DBManager.getInstance().retornarSQLParaUltimoAutoGenerado();
            this.statement = this.conexion.prepareCall(sql);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.next()) {
                resultado = this.resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar retornarUltimoAutoGenerado - " + ex);
        }
        return resultado;
    }

    public void obtenerPorId() {
        try {
            this.abrirConexion();
            String sql = this.generarSQLParaObtenerPorId();
            this.colocarSQLEnStatement(sql);
            this.incluirValorDeParametrosParaObtenerPorId();
            this.ejecutarSelectEnDB();
            if (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();
            } else {
                this.limpiarObjetoDelResultSet();
            }
            //System.out.println("SELECT  -> " + statement);
        } catch (SQLException ex) {
            System.err.println("Error al intentar obtenerPorId - " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
    }

    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        throw new UnsupportedOperationException("El método no ha sido sobreescrito.");
    }

    protected void instanciarObjetoDelResultSet() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void limpiarObjetoDelResultSet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List listarTodos() {
        String sql = null;
        Consumer incluirValorDeParametros = null;
        Object parametros = null;
        return this.listarTodos(sql, incluirValorDeParametros, parametros, null);
    }

    public List listarTodos(String sql,
            Consumer incluirValorDeParametros,
            Object parametros) {
        return this.listarTodos(sql, incluirValorDeParametros, parametros, null);
    }

    public List listarTodos(String sql,
            Consumer incluirValorDeParametros,
            Object parametros, Consumer agregarMiPropioObjetoALaLista) {
        List lista = new ArrayList<>();
        try {
            this.abrirConexion();
            if (sql == null) {
                sql = this.generarSQLParaListarTodos();
            }
            this.colocarSQLEnStatement(sql);
            if (incluirValorDeParametros != null) {
                incluirValorDeParametros.accept(parametros);
            }
            this.ejecutarSelectEnDB();
            while (this.resultSet.next()) {
                if (agregarMiPropioObjetoALaLista == null) {
                    agregarObjetoALaLista(lista);
                } else {
                    agregarMiPropioObjetoALaLista.accept(lista);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar listarTodos - " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return lista;
    }

    protected void agregarObjetoALaLista(List lista) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void ejecutarProcedimientoAlmacenado(String sql,
            Boolean conTransaccion) {
        Consumer incluirValorDeParametros = null;
        Object parametros = null;
        this.ejecutarProcedimientoAlmacenado(sql, incluirValorDeParametros, parametros, conTransaccion);
    }

    public void ejecutarProcedimientoAlmacenado(String sql,
            Consumer incluirValorDeParametros,
            Object parametros,
            Boolean conTransaccion) {
        try {
            if (conTransaccion) {
                this.iniciarTransaccion();
            } else {
                this.abrirConexion();
            }
            this.colocarSQLEnStatement(sql);
            if (incluirValorDeParametros != null) {
                incluirValorDeParametros.accept(parametros);
            }
            this.ejecutarDMLEnBD();
            if (conTransaccion) {
                this.comitarTransaccion();
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar ejecutar procedimiento almacenado: " + ex);
            try {
                if (conTransaccion) {
                    this.rollbackTransaccion();
                }
            } catch (SQLException ex1) {
                System.err.println("Error al hacer rollback - " + ex);
            }
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
    }

    /*
    ------------------------------------------------------------------------
    FIN de las  Funciones del DaoImplBase Default 
    ------------------------------------------------------------------------
     */
    
    // ==================================================================================
    // NUEVOS MÉTODOS PARA TRANSACCIONES COMPARTIDAS (REFACTORIZACIÓN)
    // ==================================================================================

    /**
     * Ejecuta la inserción usando una conexión externa (parte de una transacción padre).
     * No abre ni cierra la conexión, solo ejecuta el PreparedStatement.
     */
    public Integer insertarEnTransaccion(Connection conexionExterna) throws SQLException {
        this.conexion = conexionExterna; // "Tomamos prestada" la conexión
        String sql = this.generarSQLParaInsercion();
        
        // Preparamos el statement usando la conexión externa
        // Nota: Usamos prepareCall para mantener compatibilidad con tu arquitectura, 
        // pero para inserts simples prepareStatement es suficiente.
        this.statement = this.conexion.prepareCall(sql);
        
        this.incluirValorDeParametrosParaInsercion();
        this.ejecutarDMLEnBD(); // Ejecuta
        
        Integer resultado = 0;
        if (this.retornarLlavePrimaria) {
            resultado = this.retornarUltimoAutoGenerado();
        } else {
            resultado = 1;
        }
        return resultado;
    }

    /**
     * Ejecuta la modificación usando una conexión externa.
     */
    public Integer modificarEnTransaccion(Connection conexionExterna) throws SQLException {
        this.conexion = conexionExterna;
        String sql = this.generarSQLParaModificacion();
        this.statement = this.conexion.prepareCall(sql);
        
        this.incluirValorDeParametrosParaModificacion();
        return this.ejecutarDMLEnBD();
    }

 /*
    ------------------------------------------------------------------------
    INICIO de las funcion DaoImplBase para procedures creado By AmaruMVP
    ------------------------------------------------------------------------
     */
    //FUNCION PROCEDURE PARA MANEJAR SELECTS que traen informacion de una entidad
    public List ejecutarProcedimientoLectura(String nombreProcedimiento, Map<Integer, Object> parametrosEntrada) {
        //esto es para procedures que tienen SELECTS
        List lista = new ArrayList<>();
        try {
            abrirConexion();

            String ProcedureSQL = formarLlamadaProcedimiento(nombreProcedimiento, parametrosEntrada, null);
            //call Nombre_procedure (?, ?,?,?)
            colocarSQLEnStatement(ProcedureSQL);
            if (parametrosEntrada != null) {

                registrarParametrosEntrada(parametrosEntrada);
                //{ call Nombre_procedure ('pepe', '99','77','66') }

            }
            System.out.println("->" + this.statement);
            ejecutarSelectEnDB();
            while (this.resultSet.next()) {
                agregarObjetoALaLista(lista);
            }
        } catch (SQLException ex) {
            System.out.println("Error ejecutando procedimiento almacenado de lectura: " + ex.getMessage());
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }

        return lista;
    }

    /*FUNCION PARA PODER CAPTURAR PARAMETROS DE ENTRADA Y DE SALIDA */
    public int ejecutarProcedimiento(String nombreProcedimiento, Map<Integer, Object> parametrosEntrada, Map<Integer, Object> parametrosSalida) {
        int resultado = 0;
        try {
            abrirConexion();
            String ProcedureSQL = formarLlamadaProcedimiento(nombreProcedimiento, parametrosEntrada, parametrosSalida);
            colocarSQLEnStatement(ProcedureSQL);
            //CallableStatement cst 
            if (parametrosEntrada != null) {
                registrarParametrosEntrada(this.statement, parametrosEntrada);
            }
            if (parametrosSalida != null) {
                registrarParametrosSalida(this.statement, parametrosSalida);
            }

            ejecutarProcedureConParametroEntradaSalida();
            if (parametrosSalida != null) {
                obtenerValoresSalida(this.statement, parametrosSalida);
            }
        } catch (SQLException ex) {
            System.out.println("Error ejecutando procedimiento almacenado: " + ex.getMessage());
        } finally {
            try {
                cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(DaoBaseImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    /* FUNCIONES DE APOYO PARA LO QUE ES LA LLAMADA A PROCEDIMIENTOS */
    //FUNCION 2
    public String formarLlamadaProcedimiento(String nombreProcedimiento, Map<Integer, Object> parametrosEntrada, Map<Integer, Object> parametrosSalida) throws SQLException {

        StringBuilder call = new StringBuilder("{call " + nombreProcedimiento + "(");
        int cantParametrosEntrada = 0;
        int cantParametrosSalida = 0;
        if (parametrosEntrada != null) {
            cantParametrosEntrada = parametrosEntrada.size();
        }
        if (parametrosSalida != null) {
            cantParametrosSalida = parametrosSalida.size();
        }
        int numParams = cantParametrosEntrada + cantParametrosSalida;
        for (int i = 0; i < numParams; i++) {
            call.append("?");
            if (i < numParams - 1) {
                call.append(",");
            }
        }
        call.append(")}");
        return call.toString();
        //return con.prepareCall(call.toString());

    }

    //FUNCION 3    
    private void registrarParametrosEntrada(Map<Integer, Object> parametros) throws SQLException {

        for (Map.Entry<Integer, Object> entry : parametros.entrySet()) {
            Integer key = entry.getKey();
            Object value = entry.getValue();
            // 1. SI EL VALOR ES NULL, LO MANDAMOS DIRECTAMENTE COMO NULL A LA BD
            if (value == null) {
                this.statement.setObject(key, null);
                continue; // Pasamos al siguiente parámetro sin entrar al switch
            }
            switch (value) {
                case Integer entero ->
                    this.statement.setInt(key, entero);
                case String cadena ->
                    this.statement.setString(key, cadena);
                case Double decimal ->
                    this.statement.setDouble(key, decimal);
                case Boolean booleano ->
                    this.statement.setBoolean(key, booleano);
                case java.sql.Date fechaSQL ->
                    this.statement.setDate(key, fechaSQL); //POR SI ACASO PONGO ESTE
                case java.util.Date fecha ->
                    this.statement.setDate(key, new java.sql.Date(fecha.getTime()));

                case Character caracter ->
                    this.statement.setString(key, String.valueOf(caracter));
                case byte[] archivo ->
                    this.statement.setBytes(key, archivo);

                default -> {
                }
                // Agregar más tipos según sea necesario
            }
        }
    }

    //FUNCION 4
    //FALTA IMPLEMENTAR
    private void registrarParametrosSalida(CallableStatement cst, Map<Integer, Object> params) throws SQLException {
        for (Map.Entry<Integer, Object> entry : params.entrySet()) {
            Integer posicion = entry.getKey();
            int sqlType = (int) entry.getValue();
            cst.registerOutParameter(posicion, sqlType);
        }
    }

    //FUNCION 5
    //Falta implementar
    private void obtenerValoresSalida(CallableStatement cst, Map<Integer, Object> parametrosSalida) throws SQLException {
        for (Map.Entry<Integer, Object> entry : parametrosSalida.entrySet()) {
            Integer posicion = entry.getKey();
            int sqlType = (int) entry.getValue();
            Object value = null;
            switch (sqlType) {
                case Types.INTEGER ->
                    value = cst.getInt(posicion);
                case Types.VARCHAR ->
                    value = cst.getString(posicion);
                case Types.DOUBLE ->
                    value = cst.getDouble(posicion);
                case Types.BOOLEAN ->
                    value = cst.getBoolean(posicion);
                case Types.DATE ->
                    value = cst.getDate(posicion);
                case Types.BLOB ->
                    value = cst.getBytes(posicion);
                // Agregar más tipos según sea necesario
            }
            parametrosSalida.put(posicion, value);
        }
    }

    //FUNCION 6 
    //PARA PODER el procedure que tiene entrada y salida
    private void registrarParametrosEntrada(CallableStatement cs, Map<Integer, Object> parametros) throws SQLException {
        for (Map.Entry<Integer, Object> entry : parametros.entrySet()) {
            Integer key = entry.getKey();
            Object value = entry.getValue();
            switch (value) {
                case Integer entero ->
                    cs.setInt(key, entero);
                case String cadena ->
                    cs.setString(key, cadena);
                case Double decimal ->
                    cs.setDouble(key, decimal);
                case Boolean booleano ->
                    cs.setBoolean(key, booleano);
                case java.util.Date fecha ->
                    cs.setDate(key, new java.sql.Date(fecha.getTime()));
                case Character caracter ->
                    cs.setString(key, String.valueOf(caracter));
                case byte[] archivo ->
                    cs.setBytes(key, archivo);
                default -> {
                }
                // Agregar más tipos según sea necesario
            }
        }
    }

    //FUNCION MAS GENERICA DEL PROCEDURE DE LECUTRA
    /*
    ------------------------------------------------------------------------
    INICIO de las funcion DaoImplBase para procedures creado By AmaruMVP
    ------------------------------------------------------------------------
     */
    protected void ejecutaProcedureDeLectura() throws SQLException {
        this.resultSet = this.statement.executeQuery();
    }

    //FUNCION PROCEDURE PARA MANEJAR SELECTS que traen informacion de una entidad
    public List ejecutarProcedimientoLectura(String nombreProcedimiento, Map<Integer, Object> parametrosEntrada,
            Consumer AgregarMiPropioObjetoALaLista) {
        //esto es para procedures que tienen SELECTS
        List lista = new ArrayList<>();
        try {
            abrirConexion();

            String ProcedureSQL = formarLlamadaProcedimiento(nombreProcedimiento, parametrosEntrada, null);
            //call Nombre_procedure (?, ?,?,?)
            colocarSQLEnStatement(ProcedureSQL);
            if (parametrosEntrada != null) {

                registrarParametrosEntrada(parametrosEntrada);
                //{ call Nombre_procedure ('pepe', '99','77','66') }

            }
            System.out.println("->" + this.statement);
            ejecutaProcedureDeLectura();
            while (this.resultSet.next()) {
                if (AgregarMiPropioObjetoALaLista == null) {
                    agregarObjetoALaLista(lista);
                } else {
                    AgregarMiPropioObjetoALaLista.accept(lista);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error ejecutando procedimiento almacenado de lectura: " + ex.getMessage());
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }

        return lista;
    }
    /*
 
    
    
    FIN de las funcion DaoImplBase para procedures creado By AmaruMVP
    ------------------------------------------------------------------------
     */
}
/*
FUNCIONES QUE QUEDARON OLVIDADAS
//FUNCION DE PAZ 1
  public CallableStatement formarLlamadaProcedimiento(String nombreProcedimiento, Map<Integer, Object> parametrosEntrada, Map<Integer, Object> parametrosSalida) throws SQLException {

        StringBuilder call = new StringBuilder("{call " + nombreProcedimiento + "(");
        int cantParametrosEntrada = 0;
        int cantParametrosSalida = 0;
        if (parametrosEntrada != null) {
            cantParametrosEntrada = parametrosEntrada.size();
        }
        if (parametrosSalida != null) {
            cantParametrosSalida = parametrosSalida.size();
        }
        int numParams = cantParametrosEntrada + cantParametrosSalida;
        for (int i = 0; i < numParams; i++) {
            call.append("?");
            if (i < numParams - 1) {
                call.append(",");
            }
        }
        call.append(")}");
        colocarSQLEnStatement(call.toString());
        // conexion.prepareCall(usuario);
        return this.statement;
        //return con.prepareCall(call.toString());
    }
//FUNCION DE PAZ 2
    private void registrarParametrosEntrada(CallableStatement cs, Map<Integer, Object> parametros) throws SQLException {
        for (Map.Entry<Integer, Object> entry : parametros.entrySet()) {
            Integer key = entry.getKey();
            Object value = entry.getValue();
            switch (value) {
                case Integer entero ->
                    cs.setInt(key, entero);
                case String cadena ->
                    cs.setString(key, cadena);
                case Double decimal ->
                    cs.setDouble(key, decimal);
                case Boolean booleano ->
                    cs.setBoolean(key, booleano);
                case java.util.Date fecha ->
                    cs.setDate(key, new java.sql.Date(fecha.getTime()));
                case Character caracter ->
                    cs.setString(key, String.valueOf(caracter));
                case byte[] archivo ->
                    cs.setBytes(key, archivo);
                default -> {
                }
                // Agregar más tipos según sea necesario
            }
        }
    }
 */
//-------------------------------------------------------------------------------------------------------
///////////INTENTO DE CREACION DE PROCEDURES USANDO CODIGO DE PA
    //-------------------------------------------------------------------------------------------------------
        //Métodos para llamadas a Procedimientos Almacenados
    //FUNCION 1
//    public int ejecutarProcedimiento(String nombreProcedimiento, Map<Integer, Object> parametrosEntrada, Map<Integer, Object> parametrosSalida)  {
//        //ESTO PARA PROCEDURE CON ENTRADA Y SALIDA DE DATOS
//        //UTILES PARA MODIFICAR, INSERTAR
//        int resultado = 0;
//        try{
//            abrirConexion();
//            this.statement = formarLlamadaProcedimiento(nombreProcedimiento, parametrosEntrada, parametrosSalida);
//            if(parametrosEntrada != null)
//                registrarParametrosEntrada(this.statement, parametrosEntrada);
//            if(parametrosSalida != null)
//                registrarParametrosSalida(this.statement, parametrosSalida);
//        
//            resultado = this.statement.executeUpdate();
//        
//            if(parametrosSalida != null)
//                obtenerValoresSalida(this.statement, parametrosSalida);
//        }catch(SQLException ex){
//            System.out.println("Error ejecutando procedimiento almacenado: " + ex.getMessage());
//        }finally{
//            try {
//                cerrarConexion();
//            } catch (SQLException ex) {
//                Logger.getLogger(DaoBaseImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return resultado;
//    }

    /*
    EJEMPLO USANDO LA FUNCION
        @Override
    public int insertar(Cliente cliente) {
        Map<Integer,Object> parametrosSalida = new HashMap<>();   
        Map<Integer,Object> parametrosEntrada = new HashMap<>();
        parametrosSalida.put(1, Types.INTEGER);
        parametrosEntrada.put(2, cliente.getDni());
        parametrosEntrada.put(3, cliente.getNombre());
        parametrosEntrada.put(4, cliente.getApellidoPaterno());
        parametrosEntrada.put(5, cliente.getSexo());
        parametrosEntrada.put(6, cliente.getFechaNacimiento());
        parametrosEntrada.put(7, cliente.getLineaCredito());
        parametrosEntrada.put(8, cliente.getCategoria().toString());
        DBManager.getInstance().ejecutarProcedimiento("INSERTAR_CLIENTE", parametrosEntrada, parametrosSalida);
        cliente.setIdPersona((int) parametrosSalida.get(1));
        System.out.println("Se ha realizado el registro del cliente");
        return cliente.getIdPersona();
    }
    +/
    */
