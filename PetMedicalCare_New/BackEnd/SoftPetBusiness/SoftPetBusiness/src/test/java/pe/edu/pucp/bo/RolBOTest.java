/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.bo;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;

/**
 *
 * @author snipe
 */
public class RolBOTest {
    
    private RolBO rolBo;
    
    public RolBOTest() {
        this.rolBo = new RolBO();
    }
    
    @Test
    public void testInsertar() {
        System.out.println("insertar");
        ArrayList<Integer> listaRolID = new ArrayList<>();
        insertarRol(listaRolID);
        //eliminarTodo();
        
    }
    
    private void insertarRol (ArrayList<Integer> listaRolID){
        Integer resultado = this.rolBo.insertar("administrador",true);
        assertTrue(resultado != 0);
        listaRolID.add(resultado);
    }
    
    @Test
    public void testObtenerPorId() {
        System.out.println("obtenerPorId");
        ArrayList<Integer> listaRolId = new ArrayList<>();
        insertarRol(listaRolId);
        RolDto rolDto = this.rolBo.obtenerPorID(listaRolId.get(0));
        assertEquals(rolDto.getRolId(), listaRolId.get(0));
        //eliminarTodo();
    }
    
    @Test
    public void testListarTodos() {
        System.out.println("listarTodos");
        ArrayList<Integer> listaRolId = new ArrayList<>();
        insertarRol(listaRolId);
        
        ArrayList<RolDto> listaRol = this.rolBo.listarTodos();
        assertTrue(listaRolId.size()>0);
        for (Integer i = 0; i < listaRolId.size(); i++) {
            assertTrue(listaRolId.get(i)>0);
        }
        //eliminarTodo();
    }
    

    
    @Test
    public void testEliminar() {
        System.out.println("eliminar");
        ArrayList<Integer> listaRolId = new ArrayList<>();
        insertarRol(listaRolId);
       // eliminarTodo();
    }
    
@Test
    public void testModificar() {
        System.out.println("=== Test: modificar producto existente ===");

        int idRol = 1; // Asumimos que ya existe
            String nuevoNombre = "ADMINISTRADOR";
        Boolean  activo = true;
        Integer resultado = rolBo.modificar(
                idRol,
                nuevoNombre,
                activo
        );

        assertTrue(resultado > 0, "El método modificar debe retornar true si la actualización fue exitosa");
    }
    
    
    private void eliminarTodo(){                
        ArrayList<RolDto> listarRol = this.rolBo.listarTodos();
        for (Integer i = 0; i < listarRol.size(); i++) {
            Integer resultado = this.rolBo.eliminar(listarRol.get(i).getRolId());
            System.out.println(resultado);
            assertNotEquals(0, resultado);
            RolDto rol = this.rolBo.obtenerPorID(listarRol.get(i).getRolId());
            assertNull(rol);
        }
    }
    
}
