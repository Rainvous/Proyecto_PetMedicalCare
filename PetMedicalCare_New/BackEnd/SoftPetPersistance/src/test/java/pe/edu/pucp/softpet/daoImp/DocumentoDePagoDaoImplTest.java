package pe.edu.pucp.softpet.daoImp;

//package pe.edu.pucp.softpet.daoImp;
//
//import java.sql.Date;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.softpet.daoImp.util.enums.EstadoDocumentoDePago;
//import pe.edu.pucp.softpet.daoImp.util.enums.TipoDocumentoDePago;
//import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;
//import pe.edu.pucp.softpet.dto.facturacion.MetodoDePagoDto;
//import pe.edu.pucp.softpet.dto.personas.PersonaDto;
//
//public class DocumentoDePagoDaoImplTest {
//
//    public DocumentoDePagoDaoImplTest() {
//    }
//
//    @Test
//    public void testSomeMethod() {
//        System.out.println("DOCUMENTO DE PAGO");
//        DocumentoPagoDto doc = new DocumentoPagoDto();
//        doc.setTipoDocumento("Boleta");
//        doc.setSerie("B007");
//        doc.setNumero("000443");
//        doc.setFechaEmision(new Date(System.currentTimeMillis())); // ✅ java.sql.Date
//        doc.setEstado(EstadoDocumentoDePago.EMITIDO.toString());
//        doc.setSubtotal(100.00);
//        doc.setIGVTotal(18.00);
//        doc.setTotal(118.00);
//        doc.setActivo(true);
//
//         FK: Metodo de pago
//        MetodoDePagoDto metodo = new MetodoDePagoDto();
//        metodo.setMetodoDePagoId(1); // Asumiendo que existe el ID 1
//        doc.setMetodoDePago(metodo);
//
//         FK: Persona
//        PersonaDto persona = new PersonaDto();
//        persona.setPersonaId(2); // Asumiendo que existe el ID 2
//        doc.setPersona(persona);
//
//        DocumentoDePagoDaoImpl dao = new DocumentoDePagoDaoImpl();
//        int result = dao.insertar(doc);
//        assertTrue(result>0);
//
//        for (DocumentoPagoDto det : dao.listarTodos()) {
//            System.out.println("->" + det.getDocumentoPagoId() + " - " + det.getTotal()
//                    + " - " + det.getEstado());
//        }
//    }
//}
