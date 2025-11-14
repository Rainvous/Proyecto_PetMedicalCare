//package pe.edu.pucp.softpet.daoImp;
//
//import java.util.ArrayList;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.softpet.dto.recetas.DetalleRecetaDto;
//
//public class DetalleRecetaDaoImplTest {
//    
//    @Test
//    public void testListarPorIdReceta() {
//        System.out.println("--- Prueba: Listar Detalle de Receta por ID Receta ---");
//        
//        DetalleRecetaDaoImpl detalleDao = new DetalleRecetaDaoImpl();
//        
//        Integer recetaIdParaProbar = 1; 
//        
//        System.out.println("Buscando detalles para la Receta ID: " + recetaIdParaProbar);
//
//        // 4. Llamamos al método
//        ArrayList<DetalleRecetaDto> detalles = detalleDao.listarPorIdReceta(recetaIdParaProbar);
//        
//        // --- ASERCIONES (Assertions) ---
//        
//        // 5. Verificamos que la lista no sea nula
//        assertNotNull(detalles, "La lista de detalles no debe ser nula.");
//        
//        System.out.println("Se encontraron " + detalles.size() + " detalles (medicamentos) para esta receta.");
//        
//        // 6. Iteramos y verificamos que CADA detalle sea de la receta correcta
//        for (DetalleRecetaDto d : detalles) {
//            System.out.println(
//                "  -> Detalle ID: " + d.getDetalleRecetaId() + //
//                ", Medicamento: " + d.getDescripcionMedicamento() + //
//                ", Receta ID: " + d.getReceta().getRecetaMedicaId() //
//            );
//            
//            // Verificamos que los objetos no sean nulos
//            assertNotNull(d, "El objeto DetalleRecetaDto no debe ser nulo.");
//            assertNotNull(d.getReceta(), "El objeto Receta dentro del detalle no debe ser nulo.");
//            
//            // Verificación clave: que el detalle sea de la receta correcta
//            assertEquals(recetaIdParaProbar, d.getReceta().getRecetaMedicaId(),
//                "El detalle " + d.getDetalleRecetaId() + " no pertenece a la receta " + recetaIdParaProbar);
//        }
//        
//        if (detalles.isEmpty()) {
//            System.out.println("INFO: No se encontraron detalles para esta receta (la lista está vacía).");
//        }
//    }
//}
