/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.db;

import java.sql.Connection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.util.Cifrado;

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
       // System.out.println("contra"+Cifrado.cifrarMD5("Fullchow#2025"));
        System.out.println("getInstance");                
        DBManager dBManager = DBManager.getInstance();
        //DBManagerMySQL dbmanager;
        assertNotNull(dBManager);
    }

    @org.junit.jupiter.api.Test
    public void testGetConnection() {
        System.out.println("getConnection");                
        DBManager dBManager = DBManager.getInstance();
        Connection conexion = dBManager.getConnection();
        assertNotNull(conexion);
    }
    
}
