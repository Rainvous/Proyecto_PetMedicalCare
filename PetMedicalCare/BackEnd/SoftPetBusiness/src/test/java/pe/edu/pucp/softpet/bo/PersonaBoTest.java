/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.bo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.util.enums.TipoRol;

/**
 *
 * @author User
 */
public class PersonaBoTest {

    private PersonaBo bo;

    public PersonaBoTest() {
        this.bo = new PersonaBo();
    }

    @Test
    public void testSomeMethod() {
        System.out.println("=== Test: Insertar Modificar USUARIO DUMMY - PERSONAS ===");
        int val = bo.insertarOModificarUsuarioGest("PEPE", 21099032, 0);
        int valor = TipoRol.GUEST.getValor();
      //  System.out.println("=== Test: Modificarr USUARIO DUMMY - PERSONA ENCONTRADA---> " + valor);
        assertTrue(val > 0, "NO SE INSERTO BIEN EL DUMMY");
    }

}
