//package pe.edu.pucp.softpet.bo;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.TestMethodOrder;
//
//import pe.edu.pucp.softpet.daoImp.RecetaMedicaDaoImpl;
//import pe.edu.pucp.softpet.dto.recetas.DetalleRecetaDto;
//import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class DetalleRecetaBoTest {
//
//    private final DetalleRecetaBo bo;
//    private static Integer idCreado;         // persistente entre pruebas
//    private static Integer recetaIdValida;   // FK válida
//
//    public DetalleRecetaBoTest() {
//        this.bo = new DetalleRecetaBo();
//    }
//
//    // ------- Helpers -------
//    private static int resolverRecetaIdValida() {
//        if (recetaIdValida != null) return recetaIdValida;
//        RecetaMedicaDaoImpl recetaDao = new RecetaMedicaDaoImpl();
//        for (int id = 1; id <= 20; id++) {
//            RecetaMedicaDto r = recetaDao.obtenerPorId(id);
//            if (r != null && r.getRecetaMedicaId() != null && r.getRecetaMedicaId() > 0) {
//                recetaIdValida = id;
//                return id;
//            }
//        }
//        fail("No se encontró RECETA_MEDICA_ID válida (probé 1..20). Inserta una receta y reintenta.");
//        return -1; // unreachable
//    }
//
//    // ------- Tests -------
//
//    @Test
//    @Order(1)
//    public void testInsertar() {
//        System.out.println("=== Test: Insertar - DETALLES_RECETA ===");
//
//        int recetaId = resolverRecetaIdValida();
//
//        Integer idGenerado = bo.insertar(
//                recetaId,
//                "Amoxicilina 500mg",
//                "Cápsulas",
//                "Oral",
//                "1 cápsula",
//                "Cada 8 horas",
//                "7 días",
//                "Tomar después de alimentos",
//                "21",
//                true
//        );
//
//        assertNotNull(idGenerado, "El ID generado no debe ser null");
//        assertTrue(idGenerado > 0, "El ID generado debe ser > 0");
//        idCreado = idGenerado;
//
//        System.out.println("Insertado con ID: " + idCreado);
//    }
//
//    @Test
//    @Order(2)
//    public void testObtenerPorId() {
//        System.out.println("=== Test: Obtener por ID - DETALLES_RECETA ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        DetalleRecetaDto dto = bo.obtenerPorId(idCreado);
//
//        assertNotNull(dto, "El DTO no debe ser null");
//        assertEquals(idCreado.intValue(), dto.getDetalleRecetaId(), "IDs deben coincidir");
//        assertNotNull(dto.getReceta(), "La receta debe haberse cargado");
//        assertEquals("Amoxicilina 500mg", dto.getDescripcionMedicamento());
//        assertEquals("Cápsulas", dto.getPresentacion());
//        assertEquals("Oral", dto.getViaAdministracion());
//        assertEquals("1 cápsula", dto.getDosis());
//        assertEquals("Cada 8 horas", dto.getFrecuencia());
//        assertEquals("7 días", dto.getDuracion());
//        assertEquals("Tomar después de alimentos", dto.getIndicacion());
//        assertEquals("21", dto.getCantidad());
//        assertTrue(dto.getActivo(), "Activo debe ser true tras insertar");
//    }
//
//    @Test
//    @Order(3)
//    public void testListarTodos() {
//        System.out.println("=== Test: Listar todos - DETALLES_RECETA ===");
//
//        ArrayList<DetalleRecetaDto> lista = bo.listarTodos();
//
//        assertNotNull(lista, "La lista no debe ser null");
//        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");
//
//        boolean contiene = lista.stream().anyMatch(r -> r.getDetalleRecetaId() == idCreado);
//        assertTrue(contiene, "La lista debe contener el detalle insertado");
//        System.out.println("Cantidad: " + lista.size());
//    }
//
//    @Test
//    @Order(4)
//    public void testModificar() {
//        System.out.println("=== Test: Modificar - DETALLES_RECETA ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        int recetaId = resolverRecetaIdValida();
//
//        Integer filas = bo.modificar(
//                2,
//                recetaId,
//                "Doxiciclina 100mg",
//                "Tabletas MY MY",
//                "Oral",
//                "1 tableta",
//                "Cada 12 horas",
//                "10 días",
//                "Beber con abundante agua",
//                "20",
//                false
//        );
//
//        assertNotNull(filas, "El resultado no debe ser null");
//        assertTrue(filas > 0, "Debe retornar > 0 si se actualizó");
//
//        DetalleRecetaDto dto = bo.obtenerPorId(idCreado);
//        assertNotNull(dto);
//        assertEquals("Doxiciclina 100mg", dto.getDescripcionMedicamento());
//
//        assertEquals("Oral", dto.getViaAdministracion());
//        assertEquals("1 tableta", dto.getDosis());
//        assertEquals("Cada 12 horas", dto.getFrecuencia());
//        assertEquals("10 días", dto.getDuracion());
//        assertEquals("Beber con abundante agua", dto.getIndicacion());
//        assertEquals("20", dto.getCantidad());
//        assertFalse(dto.getActivo(), "Activo debe ser false tras modificar");
//    }
//
//    @Test
//    @Order(5)
//    public void testEliminar() {
//        System.out.println("=== Test: Eliminar - DETALLES_RECETA === Eliminarid( "+idCreado+" )");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        Integer filas = bo.eliminar(idCreado);
//
//        assertNotNull(filas);
//        assertTrue(filas > 0, "Debe retornar > 0 si se eliminó");
//
//        // Verificación robusta post-eliminación
//        DetalleRecetaDto dto = bo.obtenerPorId(idCreado);
//        boolean noExistePorObtener =
//            (dto == null) ||
//            (dto.getDetalleRecetaId() == null) ||
//            (dto.getDetalleRecetaId() == 0);
//
//        if (!noExistePorObtener) {
//            ArrayList<DetalleRecetaDto> lista = bo.listarTodos();
//            boolean aunExiste = lista.stream().anyMatch(r -> r.getDetalleRecetaId() == idCreado);
//            assertFalse(aunExiste, "El registro eliminado no debe aparecer en listarTodos()");
//        }
//    }
//}
