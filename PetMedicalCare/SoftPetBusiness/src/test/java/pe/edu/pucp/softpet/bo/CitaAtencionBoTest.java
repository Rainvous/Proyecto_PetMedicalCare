package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class CitaAtencionBoTest {

    private final CitaAtencionBo citaBo;

    public CitaAtencionBoTest() {
        this.citaBo = new CitaAtencionBo();
    }

    /**
     * Test of insertar method, of class CitaAtencionBo.
     */
    @Test
    @Order(1)
    public void testInsertar() {
        System.out.println("=== Test: Insertar - CITAS_ATENCION ===");

        // Fechas dinámicas
        Date fechaInicio = new Date(System.currentTimeMillis());
        Date fechaRegistro = new Date(System.currentTimeMillis());
        Date fechaFin = new Date(System.currentTimeMillis() + (2 * 60 * 60 * 1000)); // + 2 horas

        // Datos de prueba
        String observacion = "Control general del perro";
        double monto = 120.50;
        boolean activo = true;
        double pesoMascota = 12.4;
        String estadoCita = "Confirmado";

        int veterinarioId = 3; // Debe existir
        int mascotaId = 1;     // Debe existir

        Integer idGenerado = citaBo.insertar(
                observacion, fechaInicio, fechaRegistro, fechaFin, monto,
                activo, pesoMascota, veterinarioId, mascotaId, estadoCita
        );

        assertTrue(idGenerado > 0, "El ID generado debe ser mayor que 0");
    }

    /**
     * Test of modificar method, of class CitaAtencionBo.
     */
    @Test
    @Order(2)
    public void testModificar() {
        System.out.println("=== Test: Modificar - CITAS_ATENCION ===");

        int idCitaExistente = 2; // Debe existir
        // Fechas dinámicas
        Date fechaInicio = new Date(System.currentTimeMillis());
        Date fechaRegistro = new Date(System.currentTimeMillis());
        Date fechaFin = new Date(System.currentTimeMillis() + (2 * 60 * 60 * 1000)); // +2 horas

        String nuevaObservacion = "Revisión post vacuna";
        double nuevoMonto = 150.0;
        boolean nuevoActivo = true;
        double nuevoPeso = 13.0;
        String estadoCita = "Terminado";

        int veterinarioId = 3;
        int mascotaId = 1;

        Integer resultado = citaBo.modificar(
                idCitaExistente, nuevaObservacion, fechaInicio,
                fechaRegistro, fechaFin, nuevoMonto, nuevoActivo,
                nuevoPeso, veterinarioId, mascotaId, estadoCita
        );

        assertTrue(resultado > 0, "El método modificar debe retornar > 0 si la actualización fue exitosa");
    }

    /**
     * Test of eliminar method, of class CitaAtencionBo.
     */
    @Test
    @Order(3)
    public void testEliminar() {
        System.out.println("=== Test: Eliminar - CITAS_ATENCION ===");

        // Fechas dinámicas
        Date fechaInicio = new Date(System.currentTimeMillis());
        Date fechaRegistro = new Date(System.currentTimeMillis());
        Date fechaFin = new Date(System.currentTimeMillis() + (2 * 60 * 60 * 1000)); // +2 horas

        String observacion = "Cita temporal para eliminar";
        double monto = 80.0;
        boolean activo = true;
        double peso = 10.5;
        String estadoCita = "Pendiente";

        int veterinarioId = 3;
        int mascotaId = 1;

        Integer idGenerado = citaBo.insertar(
                observacion, fechaInicio, fechaRegistro, fechaFin,
                monto, activo, peso, veterinarioId, mascotaId, estadoCita
        );

        assertTrue(idGenerado > 0, "No se pudo insertar la cita para eliminarla.");

        // Ahora eliminamos la cita
        Integer resultado = citaBo.eliminar(idGenerado);
        assertTrue(resultado > 0, "El método eliminar debe retornar > 0 si la eliminación fue exitosa");
    }

    /**
     * Test of obtenerPorId method, of class CitaAtencionBo.
     */
    @Test
    @Order(4)
    public void testObtenerPorId() {
        System.out.println("=== Test: Obtener por ID - CITAS_ATENCION ===");

        int idCitaExistente = 1;
        CitaAtencionDto cita = citaBo.obtenerPorId(idCitaExistente);

        assertNotNull(cita, "La cita no debe ser null");
        assertEquals(idCitaExistente, cita.getCitaId(), "El ID obtenido no coincide");

        // Mostrar datos en consola
        System.out.println("ID Cita: " + cita.getCitaId());
        System.out.println("Observación: " + cita.getObservacion());
        System.out.println("Monto: " + cita.getMonto());
        System.out.println("Activo: " + (cita.getActivo() ? "Sí" : "No"));
        System.out.println("Peso Mascota: " + cita.getPesoMascota());
        System.out.println("Estado Cita: " + cita.getEstado());
        if (cita.getVeterinario() != null) {
            System.out.println("Veterinario ID: " + cita.getVeterinario().getVeterinarioId());
        }
        if (cita.getMascota() != null) {
            System.out.println("Mascota ID: " + cita.getMascota().getMascotaId());
        }
    }

    /**
     * Test of listarTodos method, of class CitaAtencionBo.
     */
    @Test
    @Order(5)
    public void testListarTodos() {
        System.out.println("=== Test: Listar todo - CITAS_ATENCION ===");

        ArrayList<CitaAtencionDto> lista = citaBo.listarTodos();

        // Validaciones básicas
        assertNotNull(lista, "La lista no debe ser null");
        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");

        // Mostrar resultados
        System.out.println("Citas encontradas: " + lista.size());
        for (CitaAtencionDto c : lista) {
            System.out.println("-----------------------------------");
            System.out.println("ID Cita: " + c.getCitaId());
            System.out.println("Observación: " + c.getObservacion());
            System.out.println("Monto: " + c.getMonto());
            System.out.println("Activo: " + (c.getActivo() ? "Sí" : "No"));
            System.out.println("Peso: " + c.getPesoMascota());
            System.out.println("Estado Cita: " + c.getEstado());
            if (c.getVeterinario() != null) {
                System.out.println("Veterinario ID: " + c.getVeterinario().getVeterinarioId());
            }
            if (c.getMascota() != null) {
                System.out.println("Mascota ID: " + c.getMascota().getMascotaId());
            }
        }
    }
}
