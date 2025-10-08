package pe.edu.pucp.softpet.bo;

import pe.edu.pucp.softpet.bo.RecetaMedicaBo;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

public class RecetaMedicaBoTest {

    private final RecetaMedicaBo recetaBo = new RecetaMedicaBo();

    /**
     * Test of insertar method, of class RecetaMedicaBo.
     */
    @Test
    public void testInsertar() {
        System.out.println("=== Insertar Receta Médica ===");
        Integer idGenerado = recetaBo.insertar("Gripe grave", true, 1);
        assertNotNull(idGenerado);
        System.out.println("ID generado: " + idGenerado);
    }

    /**
     * Test of modificar method, of class RecetaMedicaBo.
     */
    @Test
    public void testModificar() {
        System.out.println("=== Modificar Receta ===");
        Integer result = recetaBo.modificar(2, "Infección leve modificada", true, 1);
        assertTrue(result > 0);
        System.out.println("Modificación exitosa");
    }

    /**
     * Test of eliminar method, of class RecetaMedicaBo.
     */
    @Test
    public void testEliminar() {
        // Primero insertamos una receta temporal
        Integer idTemp = recetaBo.insertar("Para eliminar", true, 1);
        assertNotNull(idTemp);
        assertTrue(idTemp > 0);

        // Luego la eliminamos
        Integer resultado = recetaBo.eliminar(idTemp);
        assertEquals(1, resultado);
        System.out.println(" Receta eliminada correctamente con ID: " + idTemp);
    }

    /**
     * Test of obtenerPorId method, of class RecetaMedicaBo.
     */
    @Test
    public void testObtenerPorId() {
        System.out.println("=== Obtener Receta por ID ===");
        RecetaMedicaDto receta = recetaBo.obtenerPorId(1);
        assertNotNull(receta);
        System.out.println("Receta: " + receta.getDiagnostico());
    }

    /**
     * Test of listarTodos method, of class RecetaMedicaBo.
     */
    @Test
    public void testListarTodos() {
        System.out.println("=== Listar Recetas ===");
        ArrayList<RecetaMedicaDto> lista = recetaBo.listarTodos();
        assertNotNull(lista);
        System.out.println("Cantidad: " + lista.size());
        for (RecetaMedicaDto r : lista) {
            System.out.println(r.getRecetaMedicaId() + " - " + r.getDiagnostico());
        }
    }

}
