package pe.edu.pucp.softpet.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import pe.edu.pucp.softpet.util.Cifrado;
import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;

public abstract class DBManager {

    private static final String ARCHIVO_CONFIGURACION = "jdbc.properties";

    private Connection conexion;
    private String driver;
    protected String tipo_de_driver;
    protected String base_de_datos;
    protected String nombre_de_host;
    protected String puerto;
    private String usuario;
    private String contraseña;
    private static DBManager dbManager = null;
    private static MotorDeBaseDeDatos motorElegido;
    private static String prefijo;

    protected DBManager() {
        //constructor protegido para evitar que se creen instancias.
    }

    public static DBManager getInstance() {
        if (DBManager.dbManager == null) {
            DBManager.createInstance();
        }
        return DBManager.dbManager;
    }

    public static DBManager getInstance(MotorDeBaseDeDatos tipobase) {
        if (DBManager.dbManager == null || tipobase != motorElegido) {
            DBManager.dbManager = null;
            DBManager.createInstance(tipobase);
        }
        return DBManager.dbManager;
    }

    private static void createInstance(MotorDeBaseDeDatos motorElegido1) {

        //System.out.println("s->>"+motorElegido);
        if (motorElegido1 == MotorDeBaseDeDatos.MYSQL) {
            DBManager.dbManager = new DBManagerMySQL();
            DBManager.motorElegido = MotorDeBaseDeDatos.MYSQL;
            DBManager.prefijo = "_mysql";
            //System.out.println("ENTRO A MYSQL");
        } else if (motorElegido1 == MotorDeBaseDeDatos.MSSQL) {
            DBManager.dbManager = new DBManagerMSSQL();
            motorElegido = MotorDeBaseDeDatos.MSSQL;
            DBManager.prefijo = "_mssql";
            //System.out.println("ENTRO A MSSQL");

        } else {
            System.err.println("No se creo la instancia bien");
        }
        // System.out.println("-> " + DBManager.obtenerMotorDeBaseDeDato().toString());
        DBManager.dbManager.leer_archivo_de_propiedades();

    }

    private static void createInstance() {
        if (DBManager.dbManager == null) {
            if (DBManager.obtenerMotorDeBaseDeDato() == MotorDeBaseDeDatos.MYSQL) {
                DBManager.dbManager = new DBManagerMySQL();
                DBManager.motorElegido = MotorDeBaseDeDatos.MYSQL;
                DBManager.prefijo = "_mysql";
                System.out.println("ENTRO A MYSQL");
            } else {
                DBManager.dbManager = new DBManagerMSSQL();
                motorElegido = MotorDeBaseDeDatos.MSSQL;
                DBManager.prefijo = "_mssql";
                System.out.println("ENTRO A MSSQL");

            }
            // System.out.println("-> " + DBManager.obtenerMotorDeBaseDeDato().toString());
            DBManager.dbManager.leer_archivo_de_propiedades();
        }
    }

    public Connection getConnection() {
        try {
            Class.forName(this.driver);
//             System.out.println("Usuario: " + this.usuario);
//             System.out.println("Contraseña: " + this.contraseña);
            // System.out.println("getURL(): " + getURL());
            // System.out.println(Cifrado.descifrarMD5(this.contraseña));
            this.conexion = DriverManager.getConnection(getURL(), this.usuario, this.contraseña);
           //System.out.println("url: " + getURL());
//            System.out.println("conext: " + this.conexion);
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error al generar la conexión - " + ex);
        }
        return conexion;
    }

    protected abstract String getURL();

    private void leer_archivo_de_propiedades() {
        Properties properties = new Properties();
        try {
            // El archivo de configuración se encuentra en la carpeta src/main/resources/jdbc.properties            
            String nmArchivoConf = "/" + ARCHIVO_CONFIGURACION;
            //System.out.println("ola-> " + prefijo);
            properties.load(this.getClass().getResourceAsStream(nmArchivoConf));
            this.driver = properties.getProperty("driver" + prefijo);
            this.tipo_de_driver = properties.getProperty("tipo_de_driver" + prefijo);
            this.base_de_datos = properties.getProperty("base_de_datos"+prefijo);
            this.nombre_de_host = properties.getProperty("nombre_de_host" + prefijo);
            this.puerto = properties.getProperty("puerto" + prefijo);
            this.usuario = properties.getProperty("usuario");
            this.contraseña = properties.getProperty("contra"+prefijo);
            //System.out.println("contra"+this.contraseña);
            // System.out.println(" " + this.driver + " " + this.nombre_de_host + " " + this.usuario + " " + this.puerto);
        } catch (FileNotFoundException ex) {
            System.err.println("Error al leer el archivo de propiedades - " + ex);
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo de propiedades - " + ex);
        }
    }

    private static MotorDeBaseDeDatos obtenerMotorDeBaseDeDato() {
        Properties properties = new Properties();
        try {
            String nmArchivoConf = "/" + ARCHIVO_CONFIGURACION;

            //al ser un método estático, no se puede invocar al getResoucer así
            //properties.load(this.getClass().getResourceAsStream(nmArchivoConf));            
            properties.load(DBManager.class.getResourceAsStream(nmArchivoConf));
            String tipo_de_driver = properties.getProperty("tipo_de_driver_mysql");
            if (tipo_de_driver.equals("jdbc:mysql")) {
                return MotorDeBaseDeDatos.MYSQL;
            } else {
                return MotorDeBaseDeDatos.MSSQL;
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error al leer el archivo de propiedades - " + ex);
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo de propiedades - " + ex);
        }
        return null;
    }

    public abstract String retornarSQLParaUltimoAutoGenerado();
}
