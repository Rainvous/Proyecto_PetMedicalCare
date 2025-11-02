//package pe.edu.pucp.softpet.bo;
//
//import java.util.ArrayList;
//import java.sql.Date;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.TestMethodOrder;
//
//import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class RecetaMedicaBoTest {
//
//    private final RecetaMedicaBo recetaBo;
//
//    public RecetaMedicaBoTest() {
//        this.recetaBo = new RecetaMedicaBo();
//    }
//
//    @Test
//    @Order(1)
//    public void testInsertar() {
//        System.out.println("=== Test: Insertar - RECETAS_MEDICAS ===");
//
//        int citaId = 1; // Debe existir en BD
//        String diagnostico = "GASTRITIS";
//        Date fechaEmision = new Date(System.currentTimeMillis());
//        Date vigenciaHasta = new Date(System.currentTimeMillis());
//        String observaciones = "Tomar después de las comidas";
//        boolean activo = true;
//
//        Integer idGenerado = recetaBo.insertar(
//                citaId, diagnostico, fechaEmision, vigenciaHasta, observaciones, activo
//        );
//
//        assertNotNull(idGenerado, "El ID generado no debe ser null");
//        assertTrue(idGenerado > 0, "El ID generado debe ser > 0");
//        System.out.println("Receta insertada con ID: " + idGenerado);
//    }
//
//    @Test
//    @Order(2)
//    public void testListarTodos() {
//        System.out.println("=== Test: Listar todos - RECETAS_MEDICAS ===");
//
//        ArrayList<RecetaMedicaDto> lista = recetaBo.listarTodos();
//
//        assertNotNull(lista, "La lista no debe ser null");
//        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");
//
//        System.out.println("Recetas encontradas: " + lista.size());
//        for (RecetaMedicaDto r : lista) {
//            System.out.println("-----------------------------------");
//            System.out.println("ID: " + r.getRecetaMedicaId());
//            System.out.println("Cita ID: " + (r.getCita() != null ? r.getCita().getCitaId() : null));
//            System.out.println("Diagnóstico: " + r.getDiagnostico());
//            System.out.println("Fecha Emisión: " + r.getFechaEmision());
//            System.out.println("Vigencia Hasta: " + r.getVigenciaHasta());
//            System.out.println("Observaciones: " + r.getObservaciones());
//            System.out.println("Activo: " + r.getActivo());
//        }
//    }
//
//    @Test
//    @Order(3)
//    public void testModificar() {
//        System.out.println("=== Test: Modificar - RECETAS_MEDICAS ===");
//
//        int recetaIdExistente = 1; // asegúrate que exista
//        int citaId = 1;
//        String diagnostico = "RESFRIADO MODIFICADO";
//        Date fechaEmision = new Date(System.currentTimeMillis());
//        Date vigenciaHasta = new Date(System.currentTimeMillis());
//        String observaciones = "Revisar en 3 días";
//        boolean activo = true;
//
//        Integer resultado = recetaBo.modificar(
//                recetaIdExistente,
//                citaId,
//                diagnostico,
//                fechaEmision,
//                vigenciaHasta,
//                observaciones,
//                activo
//        );
//
//        assertNotNull(resultado, "El resultado no debe ser null");
//        assertTrue(resultado > 0, "El método modificar debe retornar > 0 si la actualización fue exitosa");
//        System.out.println("Receta modificada correctamente (ID: " + recetaIdExistente + ")");
//    }
//
//    @Test
//    @Order(4)
//    public void testEliminar() {
//        System.out.println("=== Test: Eliminar - RECETAS_MEDICAS ===");
//
//        // Insertar temporal para eliminarlo
//        int citaId = 1;
//        String diagnostico = "TEMPORAL PARA ELIMINAR";
//        Date fechaEmision = new Date(System.currentTimeMillis());
//        Date vigenciaHasta = new Date(System.currentTimeMillis());
//        String observaciones = "Se eliminará al final del test";
//        boolean activo = true;
//
//        Integer idGenerado = recetaBo.insertar(
//                citaId, diagnostico, fechaEmision, vigenciaHasta, observaciones, activo
//        );
//
//        assertNotNull(idGenerado);
//        assertTrue(idGenerado > 0, "No se pudo insertar receta para eliminarla.");
//
//        Integer resultado = recetaBo.eliminar(idGenerado);
//        assertNotNull(resultado);
//        assertTrue(resultado > 0, "El método eliminar debe retornar > 0 si la eliminación fue exitosa");
//        System.out.println("Receta eliminada correctamente (ID: " + idGenerado + ")");
//    }
//}
