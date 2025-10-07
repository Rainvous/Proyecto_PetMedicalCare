/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.bo;

import java.sql.Date;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

/**
 *
 * @author marti
 */
public class VeterinarioBoTest {
    
    private VeterinarioBo veterinarioBo;
    
    public VeterinarioBoTest() {
        this.veterinarioBo = new VeterinarioBo();
    }
    
    /**
     * Test of insertar method, of class VeterinarioBo.
     */
    @Test
    @Order(1)
    public void testInsertar() {
        System.out.println("=== Test: insertar veterinario ===");
        
        // Datos de prueba
        String especializacion = "Cardiología";
        Date fechaContratacion = new Date(System.currentTimeMillis());
        String estado = "Activo";
        boolean activo = true;
        int personaId = 1; // Debe existir en la BD
        Date fechaInicioJornada = new Date(System.currentTimeMillis());
        Date fechaFinJornada = new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000)); // +1 año
        
        Integer idGenerado = veterinarioBo.insertar(
                especializacion,
                fechaContratacion,
                estado,
                activo,
                personaId,
                fechaInicioJornada,
                fechaFinJornada
        );
        
        assertTrue(idGenerado > 0, "El ID generado debe ser mayor que 0");
        System.out.println("Veterinario insertado con ID: " + idGenerado);
    }
    
    /**
     * Test of modificar method, of class VeterinarioBo.
     */
    @Test
    @Order(2)
    public void testModificar() {
        System.out.println("=== Test: modificar veterinario ===");
        
        int veterinarioId = 3; // Debe existir
        String nuevaEspecializacion = "Traumatología";
        Date fechaContratacion = new Date(System.currentTimeMillis());
        String nuevoEstado = "Activo";
        boolean activo = true;
        int personaId = 2; // Debe existir
        Date fechaInicioJornada = new Date(System.currentTimeMillis());
        Date fechaFinJornada = new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000));
        
        Integer resultado = veterinarioBo.modificar(
                veterinarioId,
                nuevaEspecializacion,
                fechaContratacion,
                nuevoEstado,
                activo,
                personaId,
                fechaInicioJornada,
                fechaFinJornada
        );
        
        assertTrue(resultado > 0, "La modificación debe retornar > 0");
        System.out.println("Veterinario modificado exitosamente");
    }
    
    /**
     * Test of eliminar method, of class VeterinarioBo.
     */
    @Test
    @Order(3)
    public void testEliminar() {
        System.out.println("=== Test: eliminar veterinario ===");
        
        // Primero insertamos uno para eliminar
        String especializacion = "Temporal";
        Date fechaContratacion = new Date(System.currentTimeMillis());
        String estado = "Activo";
        boolean activo = true;
        int personaId = 1;
        Date fechaInicioJornada = new Date(System.currentTimeMillis());
        Date fechaFinJornada = new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000));
        
        Integer idGenerado = veterinarioBo.insertar(
                especializacion,
                fechaContratacion,
                estado,
                activo,
                personaId,
                fechaInicioJornada,
                fechaFinJornada
        );
        
        assertTrue(idGenerado > 0, "No se pudo insertar el veterinario para eliminar");
        
        // Ahora lo eliminamos
        Integer resultado = veterinarioBo.eliminar(idGenerado);
        assertTrue(resultado > 0, "La eliminación debe retornar > 0");
        System.out.println("Veterinario eliminado exitosamente");
    }
    
    /**
     * Test of obtenerPorId method, of class VeterinarioBo.
     */
    @Test
    @Order(4)
    public void testObtenerPorId() {
        System.out.println("=== Test: obtener veterinario por ID ===");
        
        int veterinarioId = 1; // Debe existir
        VeterinarioDto veterinario = veterinarioBo.obtenerPorId(veterinarioId);
        
        assertNotNull(veterinario, "El veterinario no debe ser null");
        assertEquals(veterinarioId, veterinario.getVeterinarioId(), "El ID no coincide");
        
        // Mostrar datos
        System.out.println("ID Veterinario: " + veterinario.getVeterinarioId());
        System.out.println("Especialización: " + veterinario.getEspecializacion());
        System.out.println("Estado: " + veterinario.getEstado());
        System.out.println("Activo: " + (veterinario.getActivo() ? "Sí" : "No"));
        if (veterinario.getPersona() != null) {
            System.out.println("Persona ID: " + veterinario.getPersona().getPersonaId());
            System.out.println("Nombre: " + veterinario.getPersona().getNombre());
        }
    }
    
    /**
     * Test of listarTodos method, of class VeterinarioBo.
     */
    @Test
    @Order(5)
    public void testListarTodos() {
        System.out.println("=== Test: listar todos los veterinarios ===");
        
        ArrayList<VeterinarioDto> lista = veterinarioBo.listarTodos();
        
        assertNotNull(lista, "La lista no debe ser null");
        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");
        
        System.out.println("Veterinarios encontrados: " + lista.size());
        for (VeterinarioDto v : lista) {
            System.out.println("-----------------------------------");
            System.out.println("ID: " + v.getVeterinarioId());
            System.out.println("Especialización: " + v.getEspecializacion());
            System.out.println("Estado: " + v.getEstado());
            System.out.println("Activo: " + (v.getActivo() ? "Sí" : "No"));
            if (v.getPersona() != null) {
                System.out.println("Persona: " + v.getPersona().getNombre());
            }
        }
    }
}
