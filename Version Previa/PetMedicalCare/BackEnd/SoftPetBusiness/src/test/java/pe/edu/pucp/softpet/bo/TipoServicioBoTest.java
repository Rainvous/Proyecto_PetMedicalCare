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
//import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class TipoServicioBoTest {
//
//    private final TipoServicioBo bo;
//    private static Integer idCreado; // lo reutilizamos entre pruebas
//
//    public TipoServicioBoTest() {
//        this.bo = new TipoServicioBo();
//    }
//
//    @Test
//    @Order(1)
//    public void testInsertar() {
//        System.out.println("=== Test: Insertar - TIPOS_SERVICIO ===");
//
//        String nombre = "Consulta General";
//        String descripcion = "Atención veterinaria general";
//        boolean activo = true;
//
//        Integer idGenerado = bo.insertar(nombre, descripcion, activo);
//
//        assertNotNull(idGenerado, "El ID generado no debe ser null");
//        assertTrue(idGenerado > 0, "El ID generado debe ser > 0");
//
//        idCreado = idGenerado;
//        System.out.println("Insertado con ID: " + idCreado);
//    }
//
//    @Test
//    @Order(2)
//    public void testObtenerPorId() {
//        System.out.println("=== Test: Obtener por ID - TIPOS_SERVICIO ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        TipoServicioDto dto = bo.obtenerPorId(idCreado);
//
//        assertNotNull(dto, "El DTO no debe ser null");
//        assertEquals(idCreado.intValue(), dto.getTipoServicioId(), "IDs deben coincidir");
//        assertEquals("Consulta General", dto.getNombre());
//        assertEquals("Atención veterinaria general", dto.getDescripcion());
//        assertTrue(dto.getActivo(), "Activo debe ser true tras insertar");
//    }
//
//    @Test
//    @Order(3)
//    public void testListarTodos() {
//        System.out.println("=== Test: Listar todos - TIPOS_SERVICIO ===");
//
//        ArrayList<TipoServicioDto> lista = bo.listarTodos();
//
//        assertNotNull(lista, "La lista no debe ser null");
//        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");
//
//        boolean contiene = lista.stream().anyMatch(r -> r.getTipoServicioId() == idCreado);
//        assertTrue(contiene, "La lista debe contener el tipo de servicio insertado");
//        System.out.println("Cantidad: " + lista.size());
//    }
//
//    @Test
//    @Order(4)
//    public void testModificar() {
//        System.out.println("=== Test: Modificar - TIPOS_SERVICIO ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        String nuevoNombre = "Consulta Especializada";
//        String nuevaDescripcion = "Atención por especialidad";
//        boolean nuevoActivo = false;
//
//        Integer filas = bo.modificar(idCreado, nuevoNombre, nuevaDescripcion, nuevoActivo);
//
//        assertNotNull(filas, "El resultado no debe ser null");
//        assertTrue(filas > 0, "Debe retornar > 0 si se actualizó");
//
//        TipoServicioDto actualizado = bo.obtenerPorId(idCreado);
//        assertNotNull(actualizado);
//        assertEquals(nuevoNombre, actualizado.getNombre());
//        assertEquals(nuevaDescripcion, actualizado.getDescripcion());
//        assertFalse(actualizado.getActivo(), "Activo debe ser false tras modificar");
//    }
//
//    @Test
//    @Order(5)
//    public void testEliminar() {
//        System.out.println("=== Test: Eliminar - TIPOS_SERVICIO ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        Integer filas = bo.eliminar(idCreado);
//
//        assertNotNull(filas);
//        assertTrue(filas > 0, "Debe retornar > 0 si se eliminó");
//
//        // Verificación robusta post-eliminación
//        TipoServicioDto dto = bo.obtenerPorId(idCreado);
//        boolean noExistePorObtener =
//            (dto == null) ||
//            (dto.getTipoServicioId() == null) ||
//            (dto.getTipoServicioId() == 0);
//
//        if (!noExistePorObtener) {
//            // Si tu DaoBaseImpl retorna un DTO “vacío”, validamos también contra el listado.
//            ArrayList<TipoServicioDto> lista = bo.listarTodos();
//            boolean aunExiste = lista.stream().anyMatch(r -> r.getTipoServicioId() == idCreado);
//            assertFalse(aunExiste, "El registro eliminado no debe aparecer en listarTodos()");
//        }
//    }
//}
