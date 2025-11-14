//package pe.edu.pucp.softpet.daoImp;
//
//import java.sql.Date;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
//import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;
//
//public class RecetaMedicaDaoImplTest {
//
////    public RecetaMedicaDaoImplTest() {
////    }
////
////    @Test
////    public void testSomeMethod() {
////        CitaAtencionDto cita = new CitaAtencionDto();
////        cita.setCitaId(1); // Suponiendo que existe la cita con ID 1
////
////        // 🔹 Crear las fechas
////        Date fechaEmision = Date.valueOf("2024-5-17"); // Formato yyyy-MM-dd
////        Date vigenciaHasta = Date.valueOf("2024-11-10");
////
////        // 🔹 Crear la receta médica
////        RecetaMedicaDto receta = new RecetaMedicaDto();
////        receta.setRecetaMedicaId(null); // Se genera automáticamente (auto_increment)
////        receta.setFechaEmision(fechaEmision);
////        receta.setVigenciaHasta(vigenciaHasta);
////        receta.setDiagnostico("Cancer de  cerebro");
////        receta.setObservaciones("Paciente debe evitar exposición al frío. Control en 2 semanas.");
////        receta.setActivo(true);
////        receta.setCita(cita);
////        RecetaMedicaDaoImpl dao = new RecetaMedicaDaoImpl();
//////         int result= dao.insertar(receta);
//////         assertTrue(result>0 );
//////         assertNotNull(result);
////        // ✅ Listo para insertar en la BD
////        // Aquí podrías pasar 'receta' a tu DAO para hacer el INSERT con PreparedStatement
////        System.out.println("Receta creada: " + receta.getDiagnostico());
////        System.out.println("LISTA");
////        for (RecetaMedicaDto rec : dao.listarTodos()) {
////            System.out.println("-> " + rec.getDiagnostico() + " - " + rec.getFechaEmision() + " " + rec.getCita().getCitaId());
////        }
////    }
//        
//        @Test
//    public void testObtenerPorIdCita() {
//        System.out.println("--- Prueba: Obtener Receta por ID de Cita ---");
//        
//        RecetaMedicaDaoImpl recetaDao = new RecetaMedicaDaoImpl();
//        
//        Integer citaIdParaProbar = 1; 
//        
//        System.out.println("Buscando receta para la Cita con ID: " + citaIdParaProbar);
//
//        RecetaMedicaDto receta = recetaDao.obtenerPorIdCita(citaIdParaProbar);
//        
//        
//        if (receta != null) {
//            System.out.println("✅ ÉXITO: Se encontró la receta ID: " + receta.getRecetaMedicaId()); //
//            System.out.println("  -> Diagnóstico: " + receta.getDiagnostico()); //
//            
//            // Verificación clave: que la receta sea de la cita correcta
//            assertNotNull(receta.getCita(), "El objeto Cita dentro de la receta no debe ser nulo.");
//            assertEquals(citaIdParaProbar, receta.getCita().getCitaId(), //
//                "La receta " + receta.getRecetaMedicaId() + " no pertenece a la cita " + citaIdParaProbar);
//        
//        } else {
//            System.out.println("ℹ️ INFO: No se encontró receta para la Cita ID " + citaIdParaProbar);
//            assertNotNull(receta, "No se encontró receta para la Cita ID: " + citaIdParaProbar + ". (Verifica la BD de pruebas)");
//        }
//    }
//}
