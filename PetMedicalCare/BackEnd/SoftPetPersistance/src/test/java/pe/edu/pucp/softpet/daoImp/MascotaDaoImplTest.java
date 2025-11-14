//package pe.edu.pucp.softpet.daoImp;
//
//import java.util.ArrayList;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import org.junit.jupiter.api.Test;
//import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
//import pe.edu.pucp.softpet.dto.personas.PersonaDto;
//import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;
//
//public class MascotaDaoImplTest {
//
//    @Test
//    public void testListasBusquedaAvanzada() {
//        MascotaDaoImpl mascotadao = new MascotaDaoImpl();
//        MascotaDto mascota = new MascotaDto();
//        mascota.setNombre("");
//        mascota.setRaza("");
//        mascota.setEspecie("");
//        PersonaDto persona = new PersonaDto();
//        persona.setNombre("");
//        mascota.setPersona(persona);
//        ArrayList<MascotaDto> personas = mascotadao.ListasBusquedaAvanzada(mascota);
//        System.out.println("TEST: BUSQUEDA DE MASCOTA AVANZADO");
//        for (MascotaDto p : personas) {
//            System.out.println("->" + p.getMascotaId());
//            System.out.println("->" + p.getPersona().getNombre());
//            System.out.println("->" + p.getNombre());
//            System.out.println("->" + p.getEspecie());
//            System.out.println("->" + p.getSexo());
//        }
//        System.out.println("================================================");
//    }
//
//    @Test
//    public void testListarMascotasPorIdPersona() {
//        MascotaDaoImpl mascotaDao = new MascotaDaoImpl();
//        ArrayList<MascotaDto> mascotas = new ArrayList<>();
//        System.out.println("TEST: BUSQUEDA DE MASCOTAS POR ID DE PERSONA");
//        mascotas = mascotaDao.listarPorIdPersona(5); // debe existir
//        for (MascotaDto m : mascotas) {
//            System.out.println("->" + m.getMascotaId());
//            System.out.println("->" + m.getPersona().getNombre());
//            System.out.println("->" + m.getNombre());
//            System.out.println("->" + m.getEspecie());
//            System.out.println("->" + m.getSexo());
//        }
//        System.out.println("================================================");
//    }
//    
//    @Test
//    public void testListarMascotasActivas() {
//        System.out.println("--- Prueba: Listar Mascotas Activas ---");
//        
//        // 1. Instanciamos el DAO
//        MascotaDaoImpl mascotaDao = new MascotaDaoImpl();
//        
//        // 2. Establecemos el motor de BD (siguiendo tu patrón)
//        mascotaDao.EstablecerMotorBaseDeDatos(MotorDeBaseDeDatos.MYSQL.toString());
//        
//        // 3. Llamamos al método
//        ArrayList<MascotaDto> mascotasActivas = mascotaDao.listarMascotasActivas();
//        
//        // 4. Verificamos que la lista no sea nula
//        assertNotNull(mascotasActivas, "La lista devuelta no debe ser nula.");
//        
//        System.out.println("Se encontraron " + mascotasActivas.size() + " mascotas activas.");
//        
//        // 5. Iteramos y verificamos que CADA mascota esté activa
//        for (MascotaDto m : mascotasActivas) {
//            System.out.println(
//                "  -> ID: " + m.getMascotaId() + //
//                ", Nombre: " + m.getNombre() + //
//                ", Activo: " + m.getActivo() //
//            );
//        }
//        
//        if (mascotasActivas.isEmpty()) {
//            System.out.println("INFO: No se encontraron mascotas activas (la lista está vacía).");
//        }
//    }
//}
