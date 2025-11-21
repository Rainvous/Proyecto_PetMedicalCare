package pe.edu.pucp.softpet.daoImp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.db.DBManager;
import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;

public class DaoImplBaseProcedures {

    protected Connection conexion;
    protected CallableStatement statement;
    protected ResultSet resultSet;
    protected MotorDeBaseDeDatos tipoMotor = MotorDeBaseDeDatos.MYSQL; // Ayuda a cambiar rapidamente entre motores (El dbManager tmb ha sido modificado)

    /*
    ------------------------------------------------------------------------
    INICIO DE FUNCIONES de MOTOR DE BASE DE DATOS (by AmaruMVP)
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

    /*
    ------------------------------------------------------------------------
   FIN DE FUNCIONES MOTOR DE BASE DE DATOS (by AmaruMVP)
    ------------------------------------------------------------------------
     */
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

    protected Integer ejecutarProcedureConParametroEntradaSalida() throws SQLException {
        return this.statement.executeUpdate();
    }

    protected void ejecutaProcedureDeLectura() throws SQLException {
        this.resultSet = this.statement.executeQuery();
    }

    ///FUNCIONES PARA INSTANCIAR OBJETO
    protected void instanciarObjetoDelResultSet() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void limpiarObjetoDelResultSet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void agregarObjetoALaLista(List lista) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
    ------------------------------------------------------------------------
    INICIO de las funcion DaoImplBase para procedures creado By AmaruMVP
    ------------------------------------------------------------------------
     */
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
}
