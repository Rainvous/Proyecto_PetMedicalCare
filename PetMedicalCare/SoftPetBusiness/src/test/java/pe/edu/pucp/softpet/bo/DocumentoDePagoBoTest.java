package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softpet.daoImp.util.enums.EstadoDocumentoDePago;
import pe.edu.pucp.softpet.daoImp.util.enums.TipoDocumentoDePago;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class DocumentoDePagoBoTest {

    private DocumentoDePagoBo documentoBo;

    public DocumentoDePagoBoTest() {
        this.documentoBo = new DocumentoDePagoBo();
    }

    /**
     * Test de insertar un documento de pago
     */
    @Test
    @Order(1)
    public void testInsertar() {
        System.out.println("=== Test: Insertar - DOCUMENTOS_DE_PAGO ===");

        String serie = "F002";
        String numero = "0003";
        Date fechaEmision = new Date(System.currentTimeMillis());
        String metodoPago = "EFECTIVO";
        double subtotal = 200.0;
        double igvTotal = 36.0;
        double total = 236.0;
        boolean activo = true;
        EstadoDocumentoDePago estado = EstadoDocumentoDePago.EMITIDO;
        TipoDocumentoDePago tipoDocumento = TipoDocumentoDePago.FACTURA; // Debe existir
        int MetodoDePagoId = 1; // Debe existir
        int personaId = 1;       // Debe existir

        Integer idGenerado = documentoBo.insertar(
                serie, numero, fechaEmision, metodoPago,
            estado, subtotal, igvTotal,  tipoDocumento,
            total, activo, MetodoDePagoId, personaId
        );

        assertTrue(idGenerado > 0, "El ID generado debe ser mayor que 0");
    }

    /**
     * Test de modificar un documento existente
     */
    @Test
    @Order(2)
    public void testModificar() {
        System.out.println("=== Test: Modificar - DOCUMENTOS_DE_PAGO ===");

        String serie = "F002";
        String numero = "0003";
        Date fechaEmision = new Date(System.currentTimeMillis());
        String metodoPago = "EFECTIVO";
        double subtotal = 200.0;
        double igvTotal = 36.0;
        double total = 236.0;
        boolean activo = true;
        EstadoDocumentoDePago estado = EstadoDocumentoDePago.EMITIDO;
        TipoDocumentoDePago tipoDocumento = TipoDocumentoDePago.BOLETA; // Debe existir
        int MetodoDePagoId = 1; // Debe existir
        int personaId = 1;       // Debe existir

        Integer resultado = documentoBo.modificar(MetodoDePagoId,
                serie, numero, fechaEmision, metodoPago,
            estado, subtotal, igvTotal,  tipoDocumento,
            total, activo, MetodoDePagoId, personaId
        );

        assertTrue(resultado > 0, "El método modificar debe retornar > 0 si la actualización fue exitosa");
    }

    /**
     * Test de eliminar documento de pago
     */
    @Test
    @Order(3)
    public void testEliminar() {
        System.out.println("=== Test: Eliminar - DOCUMENTOS_DE_PAGO ===");

        String serie = "B002";
        String numero = "0003";
        Date fechaEmision = new Date(System.currentTimeMillis());
        String metodoPago = "EFECTIVO";
        double subtotal = 200.0;
        double igvTotal = 36.0;
        double total = 236.0;
        boolean activo = true;
        EstadoDocumentoDePago estado = EstadoDocumentoDePago.EMITIDO;
        TipoDocumentoDePago tipoDocumento = TipoDocumentoDePago.BOLETA; // Debe existir
        int MetodoDePagoId = 1; // Debe existir
        int personaId = 1;       // Debe existir

        Integer idGenerado = documentoBo.insertar(
                serie, numero, fechaEmision, metodoPago,
            estado, subtotal, igvTotal,  tipoDocumento,
            total, activo, MetodoDePagoId, personaId
        );

        assertTrue(idGenerado > 0, "No se pudo insertar el documento para eliminarlo.");

        Integer resultado = documentoBo.eliminar(idGenerado);
        assertTrue(resultado > 0, "El método eliminar debe retornar > 0 si la eliminación fue exitosa");
    }

    /**
     * Test de obtener documento por ID
     */
    @Test
    @Order(4)
    public void testObtenerPorId() {
        System.out.println("=== Test: Obtener por ID - DOCUMENTOS_DE_PAGO ===");

        int documentoIdExistente = 1; // Asegúrate que exista
        DocumentoPagoDto documento = documentoBo.obtenerPorId(documentoIdExistente);

        assertNotNull(documento, "El documento no debe ser null");
        assertEquals(documentoIdExistente, documento.getDocumentoPagoId(), "El ID obtenido no coincide");

        // Mostrar en consola
        System.out.println("ID Documento: " + documento.getDocumentoPagoId());
        System.out.println("Serie: " + documento.getSerie());
        System.out.println("Numero: " + documento.getNumero());
        System.out.println("Método de Pago: " + documento.getMetodoDePago());
        System.out.println("Total: " + documento.getTotal());
        System.out.println("Estado: " + documento.getEstado());
        System.out.println("Activo: " + (documento.getActivo()));
    }

    /**
     * Test de listar todos los documentos
     */
    @Test
    @Order(5)
    public void testListarTodos() {
        System.out.println("=== Test: Listar todos - DOCUMENTOS_DE_PAGO ===");

        ArrayList<DocumentoPagoDto> lista = documentoBo.listarTodos();

        assertNotNull(lista, "La lista no debe ser null");
        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");

        System.out.println("Documentos encontrados: " + lista.size());
        for (DocumentoPagoDto doc : lista) {
            System.out.println("-----------------------------------");
            System.out.println("ID: " + doc.getDocumentoPagoId());
            System.out.println("Serie: " + doc.getSerie());
            System.out.println("Numero: " + doc.getNumero());
            System.out.println("Método de Pago: " + doc.getMetodoDePago());
            System.out.println("Estado: " + doc.getEstado());
            System.out.println("Total: " + doc.getTotal());
            if (doc.getTipoDocumento() != null) {
                System.out.println("Tipo Documento ID: " + doc.getTipoDocumento());
            }
            if (doc.getPersona() != null) {
                System.out.println("Persona ID: " + doc.getPersona().getPersonaId());
            }
        }
    }
}
