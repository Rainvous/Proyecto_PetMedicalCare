/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.bo;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

/**
 *
 * @author marti
 */
public class ServicioBoTest {

    private static ServicioBo servicioBo;
    private static Integer idGenerado; // Guardamos el ID para usarlo en otros tests

    public ServicioBoTest() {
        servicioBo = new ServicioBo();
    }

    /**
     * Test de insertar servicio
     */
    @Test
    
    public void testInsertar() {
        System.out.println("=== Test: insertar servicio ===");

        String nombre = "  baño y corte  ";
        double costo = 45.50;
        String estado = "DISPONIBLE";
        String descripcion = "  servicio completo de baño y corte de pelo  ";
        boolean activo = true;
        int tipoServicioId = 1; // ⚠️ Asegúrate de que exista en tu BD

        idGenerado = servicioBo.insertar(nombre, costo, estado, descripcion, activo, tipoServicioId);

        assertTrue(idGenerado > 0, "El ID generado debe ser mayor que 0");
        System.out.println("Servicio insertado con ID: " + idGenerado);
        
        System.out.println("Resultado de inserción: " + idGenerado);

    }

    /**
     * Test de modificar servicio (usa el que se insertó antes)
     */
    @Test
    public void testModificar() {
        System.out.println("=== Test: modificar servicio ===");

        String nuevoNombre = "  baño especial para perros grandes  ";
        double nuevoCosto = 60.00;
        String nuevoEstado = "NO DISPONIBLE";
        String nuevaDescripcion = "  incluye shampoo medicado y secado profesional  ";
        boolean nuevoActivo = true;
        int tipoServicioId = 1;

        Integer resultado = servicioBo.modificar(23, nuevoNombre, nuevoCosto, nuevoEstado, nuevaDescripcion, nuevoActivo, tipoServicioId);
        assertTrue(resultado > 0, "El resultado de la modificación debe ser mayor que 0");

        System.out.println("Servicio modificado correctamente. ID: " + resultado);
    }

    /**
     * Test de obtener por ID (usa el mismo servicio insertado/modificado)
     */
    @Test
    public void testObtenerPorId() {
        System.out.println("=== Test: obtener servicio por ID ===");

        ServicioDto servicio = servicioBo.obtenerPorId(23);
        assertNotNull(servicio, "El servicio obtenido no debe ser null");
        assertEquals(23, servicio.getServicioId(), "El ID obtenido debe coincidir con el insertado");

        System.out.println("Servicio obtenido:");
        System.out.println("ID: " + servicio.getServicioId());
        System.out.println("Nombre: " + servicio.getNombre());
        System.out.println("Costo: " + servicio.getCosto());
        System.out.println("Estado: " + servicio.getEstado());
        System.out.println("Descripción: " + servicio.getDescripcion());
        System.out.println("Activo: " + servicio.getActivo());
    }

    /**
     * Test de listar todos los servicios
     */
    @Test
    public void testListarTodos() {
        System.out.println("=== Test: listar todos los servicios ===");

        ArrayList<ServicioDto> lista = servicioBo.listarTodos();

        assertNotNull(lista, "La lista no debe ser null");
        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");

        System.out.println("Servicios encontrados: " + lista.size());
    }

    /**
     * Test de eliminar servicio (crea y elimina uno nuevo temporalmente)
     */
    @Test
    public void testEliminar() {
        System.out.println("=== Test: eliminar servicio ===");

        // Insertamos un servicio temporal para eliminar
        String nombre = "servicio temporal";
        double costo = 20.0;
        String estado = "DISPONIBLE";
        String descripcion = "para eliminar";
        boolean activo = true;
        int tipoServicioId = 1;

        Integer idTemp = servicioBo.insertar(nombre, costo, estado, descripcion, activo, tipoServicioId);
        assertTrue(idTemp > 0, "No se pudo insertar el servicio para eliminarlo");

        // Eliminamos
        Integer resultado = servicioBo.eliminar(idTemp);
        assertTrue(resultado > 0, "El resultado de la eliminación debe ser mayor que 0");

        System.out.println("Servicio temporal eliminado correctamente. ID: " + idTemp);
    }
}
