package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softpet.dto.facturacion.DetalleDocumentoPagoDto;

/**
 *
 * @author marti
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // ✅ Agregar esta anotación

public class DetalleDocumentoDePagoBoTest {

    private DetalleDocumentoDePagoBo detalleBo;

    public DetalleDocumentoDePagoBoTest() {
        this.detalleBo = new DetalleDocumentoDePagoBo();

    }

    /**
     * Test of insertar method, of class DetalleDocumentoDePagoBo.
     */
    @Test
    @Order(1)
    public void testInsertar() {
        System.out.println("=== Test: Insertar - DETALLES_DOCUMENTOS_DE_PAGO ===");

        int nroItem = 1;
        String descripcion = "Consulta Veterinaria";
        int cantidad = 2;
        double precioUnitarioSinIGV = 50.0;
        double valorVenta = 100.0;
        double igvItem = 18.0;
        double importeTotal = 118.0;
        int documentoPagoId = 1; // Debe existir
        int servicioId = 1;      // Debe existir
        int productoId = 1;      // Debe existir

        Integer idGenerado = detalleBo.insertar(
                nroItem, descripcion, cantidad, precioUnitarioSinIGV,
                valorVenta, igvItem, importeTotal,
                documentoPagoId, servicioId, productoId
        );

        assertTrue(idGenerado > 0, "El ID generado debe ser mayor que 0");
        System.out.println("Detalle insertado con ID: " + idGenerado);
    }

    /**
     * Test of modificar method, of class DetalleDocumentoDePagoBo.
     */
    @Test
    @Order(2)
    public void testModificar() {
        System.out.println("=== Test: Modificar - DETALLES_DOCUMENTOS_DE_PAGO ===");

        int detalleIdExistente = 8;
        int nroItem = 2;
        String descripcion = "Vacuna Antirrábica";
        int cantidad = 3;
        double precioUnitarioSinIGV = 40.0;
        double valorVenta = 120.0;
        double igvItem = 21.6;
        double importeTotal = 141.6;
        int documentoPagoId = 1;
        int servicioId = 1;
        int productoId = 1;

        Integer resultado = detalleBo.modificar(
                detalleIdExistente, nroItem, descripcion, cantidad,
                precioUnitarioSinIGV, valorVenta, igvItem, importeTotal,
                documentoPagoId, servicioId, productoId
        );

        assertTrue(resultado > 0, "El método modificar debe retornar > 0 si la actualización fue exitosa");
        System.out.println("Detalle modificado correctamente (ID: " + detalleIdExistente + ")");
    }

    /**
     * Test of eliminar method, of class DetalleDocumentoDePagoBo.
     */
    @Test
    @Order(3)
    public void testEliminar() {
        System.out.println("=== Test: eliminar detalle de documento de pago ===");

        // Insertamos uno temporal para eliminarlo
        int nroItem = 99;
        String descripcion = "Temporal para eliminar";
        int cantidad = 1;
        double precioUnitarioSinIGV = 10.0;
        double valorVenta = 10.0;
        double igvItem = 1.8;
        double importeTotal = 11.8;
        int documentoPagoId = 1;
        int servicioId = 1;
        int productoId = 1;

        Integer idGenerado = detalleBo.insertar(
                nroItem, descripcion, cantidad, precioUnitarioSinIGV,
                valorVenta, igvItem, importeTotal,
                documentoPagoId, servicioId, productoId
        );

        assertTrue(idGenerado > 0, "No se pudo insertar el detalle para eliminarlo.");

        Integer resultado = detalleBo.eliminar(idGenerado);
        assertTrue(resultado > 0, "El método eliminar debe retornar > 0 si la eliminación fue exitosa");
        System.out.println("Detalle eliminado correctamente (ID: " + idGenerado + ")");
    }

    /**
     * Test of obtenerPorId method, of class DetalleDocumentoDePagoBo.
     */
    @Test
    @Order(4)
    public void testObtenerPorId() {
        System.out.println("=== Test: Obtener por ID - DETALLES_DOCUMENTOS_DE_PAGO ===");

        int detalleIdExistente = 1; // Asegurarse que exista
        DetalleDocumentoPagoDto detalle = detalleBo.obtenerPorId(detalleIdExistente);

        assertNotNull(detalle, "El detalle no debe ser null");
        assertEquals(detalleIdExistente, detalle.getDddpId(), "El ID obtenido no coincide");

        System.out.println("ID Detalle: " + detalle.getDddpId());
        System.out.println("Nro Item: " + detalle.getNroItem());
        System.out.println("Descripción: " + detalle.getDescripcion());
        System.out.println("Cantidad: " + detalle.getCantidad());
        System.out.println("Precio Unitario sin IGV: " + detalle.getPrecioUnitarioSinIGV());
        System.out.println("Valor Venta: " + detalle.getValorVenta());
        System.out.println("IGV Item: " + detalle.getIGVItem());
        System.out.println("Importe Total: " + detalle.getImporteTotal());

        if (detalle.getDocumentoPago() != null) {
            System.out.println("Documento Pago ID: " + detalle.getDocumentoPago().getDocumentoPagoId());
        }
        if (detalle.getServicio() != null) {
            System.out.println("Servicio ID: " + detalle.getServicio().getServicioId());
        }
        if (detalle.getProducto() != null) {
            System.out.println("Producto ID: " + detalle.getProducto().getProductoId());
        }
    }

    /**
     * Test of listarTodos method, of class DetalleDocumentoDePagoBo.
     */
    @Test
    @Order(5)
    public void testListarTodos() {
        System.out.println("=== Test: Listar todos - DETALLES_DOCUMENTOS_DE_PAGO ===");

        ArrayList<DetalleDocumentoPagoDto> lista = detalleBo.listarTodos();

        assertNotNull(lista, "La lista no debe ser null");
        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");

        System.out.println("Detalles encontrados: " + lista.size());
        for (DetalleDocumentoPagoDto det : lista) {
            System.out.println("-----------------------------------");
            System.out.println("ID: " + det.getDddpId());
            System.out.println("Nro Item: " + det.getNroItem());
            System.out.println("Descripción: " + det.getDescripcion());
            System.out.println("Cantidad: " + det.getCantidad());
            System.out.println("Precio Unitario sin IGV: " + det.getPrecioUnitarioSinIGV());
            System.out.println("Valor Venta: " + det.getValorVenta());
            System.out.println("IGV Item: " + det.getIGVItem());
            System.out.println("Importe Total: " + det.getImporteTotal());

            if (det.getDocumentoPago() != null) {
                System.out.println("Documento Pago ID: " + det.getDocumentoPago().getDocumentoPagoId());
            }
            if (det.getServicio() != null) {
                System.out.println("Servicio ID: " + det.getServicio().getServicioId());
            }
            if (det.getProducto() != null) {
                System.out.println("Producto ID: " + det.getProducto().getProductoId());
            }
        }
    }
}
