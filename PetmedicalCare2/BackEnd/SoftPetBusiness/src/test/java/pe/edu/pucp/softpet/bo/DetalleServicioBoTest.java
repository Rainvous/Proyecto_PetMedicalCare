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
//import pe.edu.pucp.softpet.daoImp.CitaAtencionDaoImpl;
//import pe.edu.pucp.softpet.daoImp.ServicioDaoImpl;
//import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
//import pe.edu.pucp.softpet.dto.servicios.DetalleServicioDto;
//import pe.edu.pucp.softpet.dto.servicios.ServicioDto;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class DetalleServicioBoTest {
//
//    private final DetalleServicioBo bo;
//    private static Integer idCreado; // reutilizado entre pruebas
//    private static Integer citaIdValida; // 2..4 según indicas
//    private static Integer servicioIdValido; // se resuelve 1..20
//
//    public DetalleServicioBoTest() {
//        this.bo = new DetalleServicioBo();
//    }
//
//    // ------- Helpers -------
//    private static int resolverCitaIdValida() {
//        if (citaIdValida != null) return citaIdValida;
//        CitaAtencionDaoImpl citaDao = new CitaAtencionDaoImpl();
//        for (int id = 2; id <= 4; id++) {
//            CitaAtencionDto c = citaDao.obtenerPorId(id);
//            if (c != null && c.getCitaId() != null && c.getCitaId() > 0) {
//                citaIdValida = id;
//                return id;
//            }
//        }
//        fail("No se encontró CITA_ID válida entre 2 y 4. Inserta una cita en ese rango y reintenta.");
//        return -1; // unreachable
//    }
//
//    private static int resolverServicioIdValido() {
//        if (servicioIdValido != null) return servicioIdValido;
//        ServicioDaoImpl servDao = new ServicioDaoImpl();
//        for (int id = 1; id <= 20; id++) {
//            ServicioDto s = servDao.obtenerPorId(id);
//            if (s != null && s.getServicioId() != null && s.getServicioId() > 0) {
//                servicioIdValido = id;
//                return id;
//            }
//        }
//        fail("No se encontró un SERVICIO_ID válido (probé 1..20). Crea al menos un servicio y reintenta.");
//        return -1; // unreachable
//    }
//
//    // ------- Tests -------
//
//    @Test
//    @Order(1)
//    public void testInsertar() {
//        System.out.println("=== Test: Insertar - DETALLES_SERVICIO ===");
//
//        int citaId = resolverCitaIdValida();
//        int servicioId = resolverServicioIdValido();
//
//        String descripcion = "Baño y corte";
//        double costo = 49.90;
//        boolean activo = true;
//
//        Integer idGenerado = bo.insertar(citaId, servicioId, descripcion, costo, activo);
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
//        System.out.println("=== Test: Obtener por ID - DETALLES_SERVICIO ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        DetalleServicioDto dto = bo.obtenerPorId(idCreado);
//
//        assertNotNull(dto, "El DTO no debe ser null");
//        assertEquals(idCreado.intValue(), dto.getDetalleServicioId(), "IDs deben coincidir");
//        assertNotNull(dto.getCita(), "La Cita debe haberse cargado");
//        assertNotNull(dto.getServicio(), "El Servicio debe haberse cargado");
//        assertEquals("Baño y corte", dto.getDescripcion());
//        assertEquals(49.90, dto.getCosto(), 0.0001);
//        assertTrue(dto.getActivo(), "Activo debe ser true tras insertar");
//    }
//
//    @Test
//    @Order(3)
//    public void testListarTodos() {
//        System.out.println("=== Test: Listar todos - DETALLES_SERVICIO ===");
//
//        ArrayList<DetalleServicioDto> lista = bo.listarTodos();
//
//        assertNotNull(lista, "La lista no debe ser null");
//        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");
//
//        boolean contiene = lista.stream().anyMatch(r -> r.getDetalleServicioId() == idCreado);
//        assertTrue(contiene, "La lista debe contener el detalle insertado");
//        System.out.println("Cantidad: " + lista.size());
//    }
//
//    @Test
//    @Order(4)
//    public void testModificar() {
//        System.out.println("=== Test: Modificar - DETALLES_SERVICIO ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        int citaId = resolverCitaIdValida();        // mantenemos una cita válida
//        int servicioId = resolverServicioIdValido(); // mantenemos un servicio válido
//
//        String nuevaDescripcion = "Baño medicado";
//        double nuevoCosto = 59.50;
//        boolean nuevoActivo = false;
//
//        Integer filas = bo.modificar(idCreado, citaId, servicioId, nuevaDescripcion, nuevoCosto, nuevoActivo);
//
//        assertNotNull(filas, "El resultado no debe ser null");
//        assertTrue(filas > 0, "Debe retornar > 0 si se actualizó");
//
//        DetalleServicioDto dto = bo.obtenerPorId(idCreado);
//        assertNotNull(dto);
//        assertEquals(nuevaDescripcion, dto.getDescripcion());
//        assertEquals(nuevoCosto, dto.getCosto(), 0.0001);
//        assertFalse(dto.getActivo(), "Activo debe ser false tras modificar");
//    }
//
//    @Test
//    @Order(5)
//    public void testEliminar() {
//        System.out.println("=== Test: Eliminar - DETALLES_SERVICIO ===");
//        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");
//
//        Integer filas = bo.eliminar(idCreado);
//
//        assertNotNull(filas);
//        assertTrue(filas > 0, "Debe retornar > 0 si se eliminó");
//
//        // Validación robusta post-eliminación
//        DetalleServicioDto dto = bo.obtenerPorId(idCreado);
//        boolean noExistePorObtener =
//            (dto == null) ||
//            (dto.getDetalleServicioId() == null) ||
//            (dto.getDetalleServicioId() == 0);
//
//        if (!noExistePorObtener) {
//            ArrayList<DetalleServicioDto> lista = bo.listarTodos();
//            boolean aunExiste = lista.stream().anyMatch(r -> r.getDetalleServicioId() == idCreado);
//            assertFalse(aunExiste, "El registro eliminado no debe aparecer en listarTodos()");
//        }
//    }
//}
