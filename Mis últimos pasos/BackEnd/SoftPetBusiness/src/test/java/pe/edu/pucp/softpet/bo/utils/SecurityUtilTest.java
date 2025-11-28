/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.bo.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ferro
 */
public class SecurityUtilTest {
    
    public SecurityUtilTest() {
    }

    /**
     * Test of generarPasswordAleatoria method, of class SecurityUtil.
     */
    @Test
    public void testGenerarPasswordAleatoria() {
        System.out.println("generarPasswordAleatoria");
        System.out.println("========================");
        
        // Ejecutamos el método
        String result = SecurityUtil.generarPasswordAleatoria();
        
        // Verificaciones (Asserts)
        assertNotNull(result, "La contraseña no debe ser nula");
        assertEquals(8, result.length(), "La contraseña debe tener 8 caracteres");
        
        // Imprimimos para ver en consola
        System.out.println("Generado: " + result);
        
        String hasheado = SecurityUtil.sha256(result);
        
        assertNotNull(hasheado);
        System.out.println("Hash: " + hasheado);
        
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of sha256 method, of class SecurityUtil.
     */
    @Test
    public void testSha256() {
        System.out.println("sha256");
        System.out.println("========================");
        
        // Caso de prueba: "123456"
        String base = "123456";
        // Hash SHA-256 esperado para "123456"
        String expResult = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
        
        String result = SecurityUtil.sha256(base);
        
        // Verificamos que coincida
        assertEquals(expResult, result, "El hash generado no coincide con el esperado");
        
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
    
    
    @Test
    public void generarSha56() {
        System.out.println("Encriptando");
        System.out.println("========================");
        
        // Caso de prueba: "123456"
        String base = "amaru321";
        System.out.println("Original: " + base);
        // Hash SHA-256 esperado para "123456"
        
        String result = SecurityUtil.sha256(base);
        System.out.println("Generado: " + result);
        
        // Verificamos que coincida
        assertNotNull(result);
        
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
    
}
