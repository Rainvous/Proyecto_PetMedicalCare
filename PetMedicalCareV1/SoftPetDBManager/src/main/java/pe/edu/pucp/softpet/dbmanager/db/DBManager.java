package pe.edu.pucp.softpet.dbmanager.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import pe.edu.pucp.softpet.util.Cifrado;

public class DBManager {

    private static final String ARCHIVO_CONFIGURACION = "jdbc.properties";

    private Connection conexion;
    private String driver;
    private String tipo_de_driver;
    private String base_de_datos;
    private String nombre_de_host;
    private String puerto;
    private String usuario;
    private String contraseña;
    private static DBManager dbManager = null;

    private DBManager() {
        //constructor privado para evitar que se creen instancias.
        //Solo se podrá crear una instancia y esta debe hacerse usando el 
        //método getInstance()
    }

    public static DBManager getInstance() {
        if (DBManager.dbManager == null) {
            DBManager.createInstance();
        }
        return DBManager.dbManager;
    }

    private static void createInstance() {
        if (DBManager.dbManager == null) {
            DBManager.dbManager = new DBManager();
            DBManager.dbManager.leer_archivo_de_propiedades();
        }
    }

    public Connection getConnection() {
        // Cifrado.cifrarMD5(this.contraseña); aqui se ha puesto la contraseña cifrada y eso lo
        //guardo en properties
        try {
            Class.forName(this.driver); //aqui hay un polimorfico (Indica que tipo de driverManager usara (MySql, SQLserver, etc)
            this.conexion = DriverManager.getConnection(getURL(), this.usuario, Cifrado.descifrarMD5(this.contraseña));//
            //aqui en cifrado desencripta la contraseña
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error al generar la conexión - " + ex);
        }
        return conexion;
    }

    private String getURL() {
        //Esto incialmente fue hecho por melgar pero he cambiado
        //el codigo para poder conectarme a una bd en la nube
        String url = this.tipo_de_driver.concat("://");
        url = url.concat(this.nombre_de_host);
        url = url.concat(":");
        url = url.concat(this.puerto);
        url = url.concat("/");
        url = url.concat(this.base_de_datos);
        System.out.println(url);
        //Con el codigo de melgar sale: jdbc:mysql://localhost:3306/petmedicalcarev1
        //Con el cambio hecho saldrá: 
        return url;
    }

    /*NOTA: RECUERDA QUE PARA PODER CONECTARTE A AWS al momento de agregar el URL este debe salir de esta manera
    URL="jdbc:mysql://labs-2025-2-prog3.cb00kc8g8po3.us-east-1.rds.amazonaws.com:3306/laboratorio4";
    USUARIO= (indicado en el aws en este caso es admin)
    contraseña = (contra indicada, tmb puedes cifrarla)
    
    PARA ESO HAY QUE MODIFICAR EL arhcivo properties
     */
    private void leer_archivo_de_propiedades() {
        Properties properties = new Properties();
        try {
            //el siguiente código ha sido probado en MAC
            //el archivo de configuración se encuentra en la carpeta src/main/resources/jdbc.properties            
            String nmArchivoConf = "/" + ARCHIVO_CONFIGURACION;

            properties.load(this.getClass().getResourceAsStream(nmArchivoConf));
            this.driver = properties.getProperty("driver");
            this.tipo_de_driver = properties.getProperty("tipo_de_driver");
            this.base_de_datos = properties.getProperty("base_de_datos");
            this.nombre_de_host = properties.getProperty("nombre_de_host");
            this.puerto = properties.getProperty("puerto");
            this.usuario = properties.getProperty("usuario");
            this.contraseña = properties.getProperty("contrasenha");

        } catch (FileNotFoundException ex) {
            System.err.println("Error al leer el archivo de propiedades - " + ex);
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo de propiedades - " + ex);
        }
    }
}
