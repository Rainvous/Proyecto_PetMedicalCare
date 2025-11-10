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
//import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class TipoProductoBoTest {
//
//    private final TipoProductoBo bo;
//    private static Integer idCreado; // se reutiliza entre pruebas
//
//    public TipoProductoBoTest() {
//        this.bo = new TipoProductoBo();
//    }
//
//    @Test
//    @Order(1)
//    public void testInsertar() {
//        System.out.println("=== Test: Insertar - TIPOS_PRODUCTO ===");
//
//        String nombre = "Alimento";
//        String descripcion = "Productos alimenticios para mascotas";
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
//        System.out.println("=== Test: Obtener por ID - TIPOS_PRODUCTO ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        TipoProductoDto dto = bo.obtenerPorId(idCreado);
//
//        assertNotNull(dto, "El DTO no debe ser null");
//        assertEquals(idCreado.intValue(), dto.getTipoProductoId(), "IDs deben coincidir");
//        assertEquals("Alimento", dto.getNombre());
//        assertEquals("Productos alimenticios para mascotas", dto.getDescripcion());
//        assertTrue(dto.getActivo(), "Activo debe ser true tras insertar");
//    }
//
//    @Test
//    @Order(3)
//    public void testListarTodos() {
//        System.out.println("=== Test: Listar todos - TIPOS_PRODUCTO ===");
//
//        ArrayList<TipoProductoDto> lista = bo.listarTodos();
//
//        assertNotNull(lista, "La lista no debe ser null");
//        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");
//
//        boolean contiene = lista.stream().anyMatch(r -> r.getTipoProductoId() == idCreado);
//        assertTrue(contiene, "La lista debe contener el tipo de producto insertado");
//        System.out.println("Cantidad: " + lista.size());
//    }
//
//    @Test
//    @Order(4)
//    public void testModificar() {
//        System.out.println("=== Test: Modificar - TIPOS_PRODUCTO ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        String nuevoNombre = "Accesorio";
//        String nuevaDescripcion = "Accesorios para mascotas";
//        boolean nuevoActivo = false;
//
//        Integer filas = bo.modificar(idCreado, nuevoNombre, nuevaDescripcion, nuevoActivo);
//
//        assertNotNull(filas, "El resultado no debe ser null");
//        assertTrue(filas > 0, "Debe retornar > 0 si se actualizó");
//
//        TipoProductoDto actualizado = bo.obtenerPorId(idCreado);
//        assertNotNull(actualizado);
//        assertEquals(nuevoNombre, actualizado.getNombre());
//        assertEquals(nuevaDescripcion, actualizado.getDescripcion());
//        assertFalse(actualizado.getActivo(), "Activo debe ser false tras modificar");
//    }
//
//    @Test
//    @Order(5)
//    public void testEliminar() {
//        System.out.println("=== Test: Eliminar - TIPOS_PRODUCTO ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        Integer filas = bo.eliminar(idCreado);
//
//        assertNotNull(filas);
//        assertTrue(filas > 0, "Debe retornar > 0 si se eliminó");
//
//        // Verificación robusta post-eliminación:
//        // Algunos DaoBaseImpl retornan DTO "vacío" al no encontrar; por eso también validamos con listarTodos()
//        TipoProductoDto dto = bo.obtenerPorId(idCreado);
//        boolean noExistePorObtener =
//            (dto == null) ||
//            (dto.getTipoProductoId() == null) ||
//            (dto.getTipoProductoId() == 0);
//
//        if (!noExistePorObtener) {
//            ArrayList<TipoProductoDto> lista = bo.listarTodos();
//            boolean aunExiste = lista.stream().anyMatch(r -> r.getTipoProductoId() == idCreado);
//            assertFalse(aunExiste, "El registro eliminado no debe aparecer en listarTodos()");
//        }
//    }
//}
