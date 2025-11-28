package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

// Importa el DTO correcto para CitaAtencion
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoCita;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CitaAtencionBoTest {

    private final CitaAtencionBo bo;

    public CitaAtencionBoTest() {
        this.bo = new CitaAtencionBo();
    }

    // Helper methods para manejo de fechas y horas
    private static Date hoy() {
        return new Date(System.currentTimeMillis());  // Fecha actual para fechaRegistro
    }

    private static Timestamp masMinutos(int minutos) {
        long tiempoActual = System.currentTimeMillis();
        return new Timestamp(tiempoActual + minutos * 60L * 1000L);  // +30 o +60 minutos
    }

    private static Timestamp obtenerHoraFin(Timestamp horaInicio, int minutos) {
        return new Timestamp(horaInicio.getTime() + minutos * 60L * 1000L);  // +30 o +60 minutos
    }

    // -------- Tests --------

//    @Test
//    @Order(1)
//    public void testInsertar() {
//        System.out.println("=== Test: Insertar - CITA_ATENCION ===");
//
//        // Crear objetos DTO de Veterinario y Mascota
//        VeterinarioDto veterinario = new VeterinarioDto();
//        veterinario.setVeterinarioId(1);  // Asumimos que el ID existe
//
//        MascotaDto mascota = new MascotaDto();
//        mascota.setMascotaId(1);  // Asumimos que el ID existe
//
//        CitaAtencionDto cita = new CitaAtencionDto();
//        cita.setVeterinario(veterinario);  // Asignamos el objeto Veterinario
//        cita.setMascota(mascota);          // Asignamos el objeto Mascota
//        cita.setFechaRegistro(hoy());     // Usamos java.sql.Date para fechaRegistro
//         cita.setPesoMascota(10.0);
//         cita.setMonto(200.3);
//        // Hora inicio y fin con un intervalo de 30 minutos
//        Timestamp horaInicio = masMinutos(0);  // Para este ejemplo usaremos la misma hora para inicio
//        Timestamp horaFin = obtenerHoraFin(horaInicio, 30);  // Intervalo de 30 minutos
//
//        cita.setFechaHoraInicio(horaInicio);
//        cita.setFechaHoraFin(horaFin);
//        cita.setEstado(EstadoCita.PROGRAMADA);  // Usamos el enum para el estado
//        cita.setObservacion("Observación de prueba");
//        cita.setActivo(true);
//
//        Integer idGenerado = bo.insertar(cita);
//        assertNotNull(idGenerado, "El ID generado no debe ser null");
//        assertTrue(idGenerado > 0, "El ID generado debe ser > 0");
//        System.out.println("Insertado con ID: " + idGenerado);
//    }
//
//    @Test
//    @Order(2)
//    public void testObtenerPorId() {
//        System.out.println("=== Test: Obtener por ID - CITA_ATENCION ===");
//        assertNotNull(1, "Primero debe ejecutarse testInsertar");
//
//        CitaAtencionDto dto = bo.obtenerPorId(1);  // Cambiar al id correcto según el entorno
//        assertNotNull(dto, "El DTO no debe ser null");
//      
//    }
//
//    @Test
//    @Order(3)
//    public void testListarTodos() {
//        System.out.println("=== Test: Listar todos - CITA_ATENCION ===");
//
//        ArrayList<CitaAtencionDto> lista = bo.listarTodos();
//        assertNotNull(lista, "La lista no debe ser null");
//        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");
//
//        
//    }

//    @Test
//    @Order(4)
//    public void testModificar() {
//        System.out.println("=== Test: Modificar - CITA_ATENCION ===");
//        assertNotNull(1, "Primero debe ejecutarse testInsertar");
//
//        // Crear objetos DTO de Veterinario y Mascota
//        VeterinarioDto veterinario = new VeterinarioDto();
//        veterinario.setVeterinarioId(1);  // Asumimos que el ID existe
//
//        MascotaDto mascota = new MascotaDto();
//        mascota.setMascotaId(2);  // Asumimos que el ID existe
//
//        CitaAtencionDto cita = new CitaAtencionDto();
//        cita.setCitaId(2);  // Cambiar ID según el contexto
//        cita.setVeterinario(veterinario);  // Asignamos el objeto Veterinario
//        cita.setMascota(mascota);          // Asignamos el objeto Mascota
//        cita.setFechaRegistro(hoy());     // Usamos java.sql.Date para fechaRegistro
//        cita.setPesoMascota(10.0);
//        cita.setMonto(200.3);
//        // Hora inicio y fin con un intervalo de 1 hora
//        Timestamp horaInicio = masMinutos(0);
//        Timestamp horaFin = obtenerHoraFin(horaInicio, 60);  // Intervalo de 60 minutos
//
//        cita.setFechaHoraInicio(horaInicio);
//        cita.setFechaHoraFin(horaFin);
//        cita.setEstado(EstadoCita.ATENDIDA);  // Usamos el enum para el estado
//        cita.setObservacion("Observación modificada 2");
//        cita.setActivo(false);
//
//        Integer filas = bo.modificar(cita.getCitaId(),
//                cita.getVeterinario().getVeterinarioId(),
//                cita.getMascota().getMascotaId(),
//                cita.getFechaRegistro(),
//                cita.getFechaHoraInicio(),
//                cita.getFechaHoraFin(),
//                cita.getPesoMascota(),
//                cita.getMonto(),
//                cita.getEstado(),
//                cita.getObservacion(),
//                cita.getActivo()
//                );
//        assertNotNull(filas, "El resultado no debe ser null");
//        assertTrue(filas > 0, "Debe retornar > 0 si se actualizó");
//
//        CitaAtencionDto dto = bo.obtenerPorId(1); // Cambiar ID según el contexto
//        assertNotNull(dto);
//        assertEquals(EstadoCita.ATENDIDA, dto.getEstado(), "El estado debe ser ATENDIDA");
//   
//    }

//    @Test
//    @Order(5)
//    public void testEliminar() {
//        System.out.println("=== Test: Eliminar - CITA_ATENCION ===");
//        assertNotNull(1, "Primero debe ejecutarse testInsertar");
//
//        Integer filas = bo.eliminar(6); // Cambiar ID según el contexto
//        assertNotNull(filas);
//        assertTrue(filas > 0, "Debe retornar > 0 si se eliminó");
//
//        CitaAtencionDto dto = bo.obtenerPorId(1); // Cambiar ID según el contexto
//        assertNull(dto, "La cita ya no debería existir después de eliminar");
//    }
}
