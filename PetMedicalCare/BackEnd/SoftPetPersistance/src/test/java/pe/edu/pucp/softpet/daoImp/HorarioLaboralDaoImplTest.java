//package pe.edu.pucp.softpet.daoImp;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import pe.edu.pucp.softpet.dto.personas.HorarioLaboralDto;
//import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
//
//public class HorarioLaboralDaoImplTest {
//
//    @Test
//    public void testInsertarHorarioLaboral() {
//        System.out.println("Insertar Horario Laboral");
//
//        HorarioLaboralDaoImpl dao = new HorarioLaboralDaoImpl();
//
//        // Crear objeto de prueba
//        VeterinarioDto vet = new VeterinarioDto();
//        vet.setVeterinarioId(1); // Debe existir en BD
//
//        HorarioLaboralDto horario = new HorarioLaboralDto();
//        horario.setVeterinario(vet);
//        horario.setFecha(Date.valueOf("2023-11-27"));
//        horario.setEstado("NO_DISPONIBLE");
//        horario.setHoraInicio(Date.valueOf("2023-11-27 08:00:00"));
//        horario.setHoraFin(Date.valueOf("2023-11-27 16:00:00"));
//        horario.setActivo(true);
//
//        Integer result = dao.insertar(horario);
//        assertNotNull(result, "El ID retornado no debe ser nulo");
//        assertTrue(result > 0, "El registro debe insertarse correctamente");
//    }
//
//    @Test
//    public void testListarTodosHorarios() {
//        System.out.println("Listar Horarios Laborales");
//
//        HorarioLaboralDaoImpl dao = new HorarioLaboralDaoImpl();
//        ArrayList<HorarioLaboralDto> lista = dao.listarTodos();
//
//        assertNotNull(lista, "La lista no debe ser nula");
//        assertTrue(lista.size() >= 0, "Debe devolver una lista válida");
//        lista.forEach(h -> {
//            System.out.println("ID: " + h.getHorarioLaboralId()
//                    + " | Fecha: " + h.getFecha()
//                    + " | Estado: " + h.getEstado());
//        });
//    }
//}
