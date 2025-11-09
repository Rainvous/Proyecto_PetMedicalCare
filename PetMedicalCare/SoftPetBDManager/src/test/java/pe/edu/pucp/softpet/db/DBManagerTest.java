package pe.edu.pucp.softpet.db;

import java.sql.Connection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.util.Cifrado;
import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;

/**
 *
 * @author User
 */
public class DBManagerTest {

    public DBManagerTest() {
    }

    /**
     * Test of getInstance method, of class DBManager.
     */
    @org.junit.jupiter.api.Test
    public void testGetInstance() {
        // System.out.println("contra" + Cifrado.cifrarMD5("Fullchow#2025"));
        System.out.println("getInstance");
        DBManager dBManager = DBManager.getInstance();
        assertNotNull(dBManager);
    }

    @org.junit.jupiter.api.Test
    public void testGetConnection() {
        System.out.println("getConnection");
        DBManager dBManager;
        Connection conexion;
        dBManager = DBManager.getInstance(MotorDeBaseDeDatos.MSSQL);
        conexion = dBManager.getConnection();
        assertNotNull(conexion);
        dBManager = DBManager.getInstance(MotorDeBaseDeDatos.MYSQL);
        conexion = dBManager.getConnection();
        assertNotNull(conexion);
    }
}
