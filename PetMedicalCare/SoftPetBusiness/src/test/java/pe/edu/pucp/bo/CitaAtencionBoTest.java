///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
// */
//package pe.edu.pucp.bo;
//
//import java.util.ArrayList;
//import java.util.Date;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.TestMethodOrder;
//import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
//import org.junit.jupiter.api.Order;
//
///**
// *
// * @author marti
// */
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // ✅ Agregar esta anotación
//
//public class CitaAtencionBoTest {
//
//    private CitaAtencionBo citaBo;
//
//    public CitaAtencionBoTest() {
//        this.citaBo = new CitaAtencionBo();
//    }
//
//    /**
//     * Test of insertar method, of class CitaAtencionBo.
//     */
//    @Test
//    @Order(1)
//    public void testInsertar() {
//        System.out.println("=== Test: insertar cita de atención ===");
//
//        // Fechas dinámicas
//        Date fechaInicio = new Date(System.currentTimeMillis());
//        Date fechaRegistro = new Date(System.currentTimeMillis());
//        Date fechaFin = new Date(System.currentTimeMillis() + (2 * 60 * 60 * 1000)); // +2 horas
//
//        // Datos de prueba
//        String observacion = "Control general del perro";
//        double monto = 120.50;
//        boolean activo = true;
//        String pesoMascota = "12.4";
//
//        int veterinarioId = 3; // Debe existir
//        int mascotaId = 1;     // Debe existir
//
//        Integer idGenerado = citaBo.insertar(
//                observacion,
//                fechaInicio,
//                fechaRegistro,
//                fechaFin,
//                monto,
//                activo,
//                pesoMascota,
//                veterinarioId,
//                mascotaId
//        );
//
//        assertTrue(idGenerado > 0, "El ID generado debe ser mayor que 0");
//    }
//
//    /**
//     * Test of modificar method, of class CitaAtencionBo.
//     */
//    @Test
//    @Order(2)
//    public void testModificar() {
//        System.out.println("=== Test: modificar cita existente ===");
//
//        int idCitaExistente = 2; // Asegúrate de que exista una cita con este ID
//        // Fechas dinámicas
//        Date fechaInicio = new Date(System.currentTimeMillis());
//        Date fechaRegistro = new Date(System.currentTimeMillis());
//        Date fechaFin = new Date(System.currentTimeMillis() + (2 * 60 * 60 * 1000)); // +2 horas
//
//        String nuevaObservacion = "Revisión post vacuna";
//        double nuevoMonto = 150.0;
//        boolean nuevoActivo = true;
//        String nuevoPeso = "13.0";
//
//        int veterinarioId = 3;
//        int mascotaId = 1;
//
//        Integer resultado = citaBo.modificar(
//                idCitaExistente,
//                nuevaObservacion,
//                fechaInicio,
//                fechaRegistro,
//                fechaFin,
//                nuevoMonto,
//                nuevoActivo,
//                nuevoPeso,
//                veterinarioId,
//                mascotaId
//        );
//
//        assertTrue(resultado > 0, "El método modificar debe retornar > 0 si la actualización fue exitosa");
//    }
//
//    /**
//     * Test of eliminar method, of class CitaAtencionBo.
//     */
//    @Test
//    @Order(3)
//    public void testEliminar() {
//        System.out.println("=== Test: eliminar cita ===");
//
//         // Fechas dinámicas
//        Date fechaInicio = new Date(System.currentTimeMillis());
//        Date fechaRegistro = new Date(System.currentTimeMillis());
//        Date fechaFin = new Date(System.currentTimeMillis() + (2 * 60 * 60 * 1000)); // +2 horas
//        
//        String observacion = "Cita temporal para eliminar";
//        double monto = 80.0;
//        boolean activo = true;
//        String peso = "10.5";
//
//        int veterinarioId = 3;
//        int mascotaId = 1;
//
//        Integer idGenerado = citaBo.insertar(
//                observacion,
//                fechaInicio,
//                fechaRegistro,
//                fechaFin,
//                monto,
//                activo,
//                peso,
//                veterinarioId,
//                mascotaId
//        );
//
//        assertTrue(idGenerado > 0, "No se pudo insertar la cita para eliminarla.");
//
//        // Ahora eliminamos la cita
//        Integer resultado = citaBo.eliminar(idGenerado);
//        assertTrue(resultado > 0, "El método eliminar debe retornar > 0 si la eliminación fue exitosa");
//    }
//
//    /**
//     * Test of obtenerPorId method, of class CitaAtencionBo.
//     */
//    @Test
//    @Order(4)
//    public void testObtenerPorId() {
//        System.out.println("=== Test: obtener cita por ID ===");
//
//        int idCitaExistente = 1;
//        CitaAtencionDto cita = citaBo.obtenerPorId(idCitaExistente);
//
//        assertNotNull(cita, "La cita no debe ser null");
//        assertEquals(idCitaExistente, cita.getCitaId(), "El ID obtenido no coincide");
//
//        // Mostrar datos en consola
//        System.out.println("ID Cita: " + cita.getCitaId());
//        System.out.println("Observación: " + cita.getObservacion());
//        System.out.println("Monto: " + cita.getMonto());
//        System.out.println("Activo: " + (cita.getActivo() ? "Sí" : "No"));
//        System.out.println("Peso Mascota: " + cita.getPeso());
//        if (cita.getVeterinario() != null) {
//            System.out.println("Veterinario ID: " + cita.getVeterinario().getVeterinarioId());
//        }
//        if (cita.getMascota() != null) {
//            System.out.println("Mascota ID: " + cita.getMascota().getMascotaId());
//        }
//    }
//
//    /**
//     * Test of listarTodos method, of class CitaAtencionBo.
//     */
//    @Test
//    @Order(5)
//    public void testListarTodos() {
//        System.out.println("=== Test: listar todas las citas ===");
//
//        ArrayList<CitaAtencionDto> lista = citaBo.listarTodos();
//
//        // Validaciones básicas
//        assertNotNull(lista, "La lista no debe ser null");
//        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");
//
//        // Mostrar resultados
//        System.out.println("Citas encontradas: " + lista.size());
//        for (CitaAtencionDto c : lista) {
//            System.out.println("-----------------------------------");
//            System.out.println("ID Cita: " + c.getCitaId());
//            System.out.println("Observación: " + c.getObservacion());
//            System.out.println("Monto: " + c.getMonto());
//            System.out.println("Activo: " + (c.getActivo() ? "Sí" : "No"));
//            System.out.println("Peso: " + c.getPeso());
//            if (c.getVeterinario() != null) {
//                System.out.println("Veterinario ID: " + c.getVeterinario().getVeterinarioId());
//            }
//            if (c.getMascota() != null) {
//                System.out.println("Mascota ID: " + c.getMascota().getMascotaId());
//            }
//        }
//    }
//
//}
