package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;
import pe.edu.pucp.softpet.daoImp.CitaAtencionDaoImpl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecetaMedicaBoTest {

    private static final RecetaMedicaBo bo = new RecetaMedicaBo();

    private static Integer recetaIdCreada;   // se llena en testInsertar
    private static Integer citaIdValida;     // se resuelve una vez

    // -------- Helpers --------

    private static Date hoy() {
        return new Date(System.currentTimeMillis());
    }

    private static Date masDias(int dias) {
        return new Date(hoy().getTime() + dias * 24L * 60L * 60L * 1000L);
    }

    /**
     * Busca una CITA existente en la BD probando IDs 1..10.
     * Si no encuentra, falla con un mensaje claro para preparar el dato.
     */
    private static int resolverCitaIdValida() {
        if (citaIdValida != null) return citaIdValida;

        CitaAtencionDaoImpl citaDao = new CitaAtencionDaoImpl();
        for (int id = 1; id <= 10; id++) {
            // No importamos el DTO de Cita para evitar dependencia: nos basta saber si existe (no-null)
            Object dto = citaDao.obtenerPorId(id);
            if (dto != null) {
                citaIdValida = id;
                return id;
            }
        }
        fail("No se encontró una CITA válida (probé IDs 1..10). " +
             "Inserta al menos una fila en CITAS_ATENCION y vuelve a ejecutar los tests.");
        return -1; // unreachable
    }

    // -------- Tests --------

    @Test
    @Order(1)
    public void testInsertar() {
        System.out.println("=== Test: Insertar - RECETAS_MEDICAS ===");

        int citaId = resolverCitaIdValida();
        Date fechaEmision = hoy();
        // Para evitar NPEs en DaoImpl si no maneja setNull en fechas, usa una vigencia no-nula
        Date vigenciaHasta = masDias(7);

        Integer idGenerado = bo.insertar(
                citaId,
                fechaEmision,
                vigenciaHasta,
                "DX PRUEBA",
                "OBS PRUEBA",
                true
        );

        assertNotNull(idGenerado, "El ID generado no debe ser null");
        assertTrue(idGenerado > 0, "El ID generado debe ser > 0");
        recetaIdCreada = idGenerado;
        System.out.println("Insertado con ID: " + recetaIdCreada);
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        System.out.println("=== Test: Obtener por ID - RECETAS_MEDICAS ===");
        assertNotNull(recetaIdCreada, "Primero debe ejecutarse testInsertar");

        RecetaMedicaDto dto = bo.obtenerPorId(recetaIdCreada);
        assertNotNull(dto, "El DTO no debe ser null");
        assertEquals(recetaIdCreada.intValue(), dto.getRecetaMedicaId(), "IDs deben coincidir");
        assertNotNull(dto.getFechaEmision(), "La fecha de emisión debe estar seteada");
        assertNotNull(dto.getCita(), "La CITA debe haberse cargado");
        assertTrue(dto.getActivo(), "Activo debe ser true tras insertar");
    }

    @Test
    @Order(3)
    public void testListarTodos() {
        System.out.println("=== Test: Listar todos - RECETAS_MEDICAS ===");

        ArrayList<RecetaMedicaDto> lista = bo.listarTodos();
        assertNotNull(lista, "La lista no debe ser null");
        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");

        boolean contiene = lista.stream().anyMatch(r -> r.getRecetaMedicaId() == recetaIdCreada);
        assertTrue(contiene, "La lista debe contener la receta insertada");
        System.out.println("Cantidad: " + lista.size());
    }

    @Test
    @Order(4)
    public void testModificar() {
        System.out.println("=== Test: Modificar - RECETAS_MEDICAS ===");
        assertNotNull(recetaIdCreada, "Primero debe ejecutarse testInsertar");

        int citaId = resolverCitaIdValida();
        Date nuevaVigencia = masDias(14);

        Integer filas = bo.modificar(
                recetaIdCreada,
                citaId,
                hoy(),          // puedes dejar la misma
                nuevaVigencia,
                "DX EDITADO",
                "OBS EDITADA",
                false
        );

        assertNotNull(filas, "El resultado no debe ser null");
        assertTrue(filas > 0, "Debe retornar > 0 si se actualizó");

        RecetaMedicaDto dto = bo.obtenerPorId(recetaIdCreada);
        assertNotNull(dto);
        assertEquals("DX EDITADO", dto.getDiagnostico());
        assertEquals("OBS EDITADA", dto.getObservaciones());
        assertFalse(dto.getActivo(), "Activo debe ser false tras modificar");
    }

    @Test
    @Order(5)
    public void testEliminar() {
        System.out.println("=== Test: Eliminar - RECETAS_MEDICAS ===");
        assertNotNull(recetaIdCreada, "Primero debe ejecutarse testInsertar");

        Integer filas = bo.eliminar(recetaIdCreada);
        assertNotNull(filas);
        assertTrue(filas > 0, "Debe retornar > 0 si se eliminó");

        RecetaMedicaDto dto = bo.obtenerPorId(recetaIdCreada);

        // Si tu Dao retorna null cuando no encuentra, esta aserción pasa.
        // Si retorna un DTO "vacío" con id=0, cambia a: assertEquals(0, dto.getRecetaMedicaId());
        assertNull(dto, "La receta ya no debería existir después de eliminar");
    }
}
