package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import pe.edu.pucp.softpet.dto.facturacion.MetodoDePagoDto;
import pe.edu.pucp.softpet.dto.util.enums.TipoMetodoPago;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MetodoDePagoBoTest {

    private final MetodoDePagoBo bo;
    private static Integer idCreado; // ID persistente entre tests

    public MetodoDePagoBoTest() {
        this.bo = new MetodoDePagoBo();
    }

    @Test
    @Order(1)
    public void testInsertar() {
        System.out.println("=== Test: Insertar - METODOS_DE_PAGO ===");

        TipoMetodoPago nombre = TipoMetodoPago.EFECTIVO;
        boolean activo = true;

        Integer idGenerado = bo.insertar(nombre, activo);

        assertNotNull(idGenerado, "El ID generado no debe ser null");
        assertTrue(idGenerado > 0, "El ID generado debe ser > 0");

        idCreado = idGenerado;
        System.out.println("Insertado con ID: " + idCreado);
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        System.out.println("=== Test: Obtener por ID - METODOS_DE_PAGO ===");
        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");

        MetodoDePagoDto dto = bo.obtenerPorId(idCreado);

        assertNotNull(dto, "El DTO no debe ser null");
       // assertEquals(idCreado.intValue(), dto.getMetodoDePagoId(), "Los IDs deben coincidir");
     //   assertEquals(TipoMetodoPago.EFECTIVO, dto.getNombre(), "El nombre debe coincidir con lo insertado");
        assertTrue(dto.getActivo(), "Activo debe ser true tras insertar");
    }

    @Test
    @Order(3)
    public void testListarTodos() {
        System.out.println("=== Test: Listar todos - METODOS_DE_PAGO ===");

        ArrayList<MetodoDePagoDto> lista = bo.listarTodos();

        assertNotNull(lista, "La lista no debe ser null");
        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");

        boolean contiene = lista.stream().anyMatch(r -> r.getMetodoDePagoId() == idCreado);
        assertTrue(contiene, "La lista debe contener el método de pago insertado");

        System.out.println("Cantidad de métodos: " + lista.size());
    }

    @Test
    @Order(4)
    public void testModificar() {
        System.out.println("=== Test: Modificar - METODOS_DE_PAGO ===");
        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");

        TipoMetodoPago nuevoNombre = TipoMetodoPago.EFECTIVO;
        boolean nuevoActivo = false;

        Integer filas = bo.modificar(idCreado, nuevoNombre, nuevoActivo);

        assertNotNull(filas, "El resultado no debe ser null");
        assertTrue(filas > 0, "Debe retornar > 0 si se actualizó");

        MetodoDePagoDto actualizado = bo.obtenerPorId(idCreado);
        assertNotNull(actualizado, "El DTO modificado no debe ser null");
        assertEquals(nuevoNombre, actualizado.getNombre(), "El nombre debe haberse actualizado");
        assertFalse(actualizado.getActivo(), "Activo debe ser false tras modificar");
    }

    @Test
    @Order(5)
    public void testEliminar() {
        System.out.println("=== Test: Eliminar - METODOS_DE_PAGO ===");
        assertNotNull(idCreado, "Primero debe ejecutarse testInsertar");

        Integer filas = bo.eliminar(idCreado);

        assertNotNull(filas, "El resultado no debe ser null");
        assertTrue(filas > 0, "Debe retornar > 0 si se eliminó");

        // Validación robusta post-eliminación
        MetodoDePagoDto dto = bo.obtenerPorId(idCreado);
        boolean noExistePorObtener =
            (dto == null) ||
            (dto.getMetodoDePagoId() == null) ||
            (dto.getMetodoDePagoId() == 0);

        if (!noExistePorObtener) {
            ArrayList<MetodoDePagoDto> lista = bo.listarTodos();
            boolean aunExiste = lista.stream().anyMatch(r -> r.getMetodoDePagoId() == idCreado);
            assertFalse(aunExiste, "El registro eliminado no debe aparecer en listarTodos()");
        }
    }
}
