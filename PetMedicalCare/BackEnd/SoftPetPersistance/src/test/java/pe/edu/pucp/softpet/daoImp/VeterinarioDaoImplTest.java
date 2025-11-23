//package pe.edu.pucp.softpet.daoImp;
//
//import java.util.ArrayList;
//import java.util.Date;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
//import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;
//
//public class VeterinarioDaoImplTest {
////
////    @Test
////    public void testListarVeterinariosActivos() {
////        System.out.println("--- Prueba: Listar Veterinarios Activos ---");
////
////        VeterinarioDaoImpl vetDao = new VeterinarioDaoImpl();
////        vetDao.EstablecerMotorBaseDeDatos(MotorDeBaseDeDatos.MYSQL.toString());
////
////        ArrayList<VeterinarioDto> veterinariosActivos = vetDao.listarVeterinariosActivos();
////
////        assertNotNull(veterinariosActivos, "La lista devuelta no debe ser nula.");
////
////        System.out.println("Se encontraron " + veterinariosActivos.size() + " veterinarios activos.");
////
////        for (VeterinarioDto v : veterinariosActivos) {
////            System.out.println(
////                    "  -> ID: " + v.getVeterinarioId()
////                    + //
////                    ", Especialidad: " + v.getEspecialidad()
////                    + //
////                    ", Activo: " + v.getActivo() //
////            );
////        }
////
////        if (veterinariosActivos.isEmpty()) {
////            System.out.println("INFO: No se encontraron veterinarios activos (la lista está vacía).");
////        }
//    
//        @Test
//        public void VerificarSiExisteHorarioLaboral () {
//    
//            String fecha = "2025-11-03";
//            Integer idVeterinario = 1 ;
//            
//            java.sql.Date FechaDate = java.sql.Date.valueOf(fecha);
//            
//            VeterinarioDaoImpl vetDao = new VeterinarioDaoImpl();
//            
//            Integer Id =vetDao.VerificarSiExisteHorarioLaboral(FechaDate,idVeterinario);
//            
//            System.out.println("TEST DE EXISTENCIA DE HORAIO LABORAL");
//            System.err.println("---ID Horario "+Id);
//            
//        }
//        
//        
//        @Test
//    public void testListarVeterinariosActivos() {
//        System.out.println("--- Prueba: Listar Veterinarios Activos ---");
//
//        VeterinarioDaoImpl vetDao = new VeterinarioDaoImpl();
//        String Especialidad = "";
//        String nombre = "";
//        String Telefono = "";
//        String nroDocumento = "";
//        ArrayList<VeterinarioDto> veterinariosActivos = vetDao.ListasBusquedaAvanzadaVeterinario(Especialidad, nombre, Telefono, nroDocumento);
//        for (VeterinarioDto v :  veterinariosActivos ) {
//            System.out.println(
//                    "  -> ID: " + v.getVeterinarioId()
//                    + //
//                    ", Especialidad: " + v.getEspecialidad()
//                    + //
//                    ", Activo: " + v.getActivo() //
//            );
//        }
//
//    }
//}
//
