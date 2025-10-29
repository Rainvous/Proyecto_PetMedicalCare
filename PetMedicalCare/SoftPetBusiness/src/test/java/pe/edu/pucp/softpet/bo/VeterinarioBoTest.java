package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

public class VeterinarioBoTest {

    private final VeterinarioBo veterinarioBo;

    public VeterinarioBoTest() {
        this.veterinarioBo = new VeterinarioBo();
    }

    /**
     * Test of insertar method, of class VeterinarioBo.
     */
    @Test
    @Order(1)
    public void testInsertar() {
        System.out.println("=== Test: Insertar - VETERINARIOS ===");

        // Datos de prueba
        int personaId = 1;
        int especialidadId = 1;
        Date fechaContratacion = new Date(System.currentTimeMillis());
        String estado = "ACTIVO";
        String especialidad = "Cardiología";
        boolean activo = true;
        
        Integer idGenerado = veterinarioBo.insertar(
                personaId, especialidadId, fechaContratacion, estado,
                especialidad, activo
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
        System.out.println("=== Test: Modificar - VETERINARIOS ===");

        // Datos de prueba
        int veterinarioId = 3;
        int personaId = 2;
        int especialidadId = 1;
        Date fechaContratacion = new Date(System.currentTimeMillis());
        String estado = "SUSPENDIDO";
        String especialidad = "Traumatología";
        boolean activo = true;

        Integer resultado = veterinarioBo.modificar(
                veterinarioId, personaId, especialidadId, fechaContratacion,
                estado, especialidad, activo
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
        System.out.println("=== Test: Eliminar - VETERINARIOS ===");

        // Primero insertamos uno para eliminar
        int personaId = 1;
        int especialidadId = 3;
        Date fechaContratacion = new Date(System.currentTimeMillis());
        String estado = "ACTIVO";
        String especialidad = "Cardiología";
        boolean activo = true;

        Integer idGenerado = veterinarioBo.insertar(
                personaId, especialidadId, fechaContratacion, estado,
                especialidad, activo
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
        System.out.println("=== Test: Obtener por ID - VETERINARIOS ===");

        int veterinarioId = 1; // Debe existir
        VeterinarioDto veterinario = veterinarioBo.obtenerPorId(veterinarioId);

        assertNotNull(veterinario, "El veterinario no debe ser null");
        assertEquals(veterinarioId, veterinario.getVeterinarioId(), "El ID no coincide");

        // Mostrar datos
        System.out.println("ID Veterinario: " + veterinario.getVeterinarioId());
        System.out.println("Especialidad ID: " + veterinario.getEspecialidadId());
        System.out.println("Fecha contratación: " + veterinario.getFechaContratacion());
        System.out.println("Estado: " + veterinario.getEstado());
        System.out.println("Especialidad: " + veterinario.getEspecialidad());
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
        System.out.println("=== Test: Listar todos - VETERINARIOS ===");

        ArrayList<VeterinarioDto> lista = veterinarioBo.listarTodos();

        assertNotNull(lista, "La lista no debe ser null");
        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");

        System.out.println("Veterinarios encontrados: " + lista.size());
        for (VeterinarioDto v : lista) {
            System.out.println("-----------------------------------");
            System.out.println("ID: " + v.getVeterinarioId());
            System.out.println("Especialización: " + v.getEspecialidad());
            System.out.println("Estado: " + v.getEstado());
            System.out.println("Activo: " + (v.getActivo() ? "Sí" : "No"));
            if (v.getPersona() != null) {
                System.out.println("Persona: " + v.getPersona().getNombre());
            }
        }
    }
}
