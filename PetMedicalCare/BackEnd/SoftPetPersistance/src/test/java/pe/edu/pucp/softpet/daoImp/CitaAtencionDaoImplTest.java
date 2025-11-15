package pe.edu.pucp.softpet.daoImp;

import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
//import pe.edu.pucp.softpet.daoImp.util.enums.EstadoCita;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
//import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
//import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

public class CitaAtencionDaoImplTest {
    
    public CitaAtencionDaoImplTest() {
    }

//    @Test
//    public void testSomeMethod() {
//        
//        CitaAtencionDto cita= new CitaAtencionDto();
//        VeterinarioDto vet= new VeterinarioDto();
//        MascotaDto mas= new MascotaDto();
//        mas.setMascotaId(1);
//        vet.setVeterinarioId(1);
//        
//        cita.setVeterinario(vet);
//        cita.setMascota(mas);
//        Date date1= new Date(2025-1900,9,27,9,0 );
//        cita.setFechaHoraInicio(new java.sql.Date(date1.getTime()));
//        cita.setFechaHoraFin(new java.sql.Date(date1.getTime()));
//        cita.setFechaRegistro(new java.sql.Date(date1.getTime()));
//        cita.setMonto(80.30);
//        cita.setPeso("34");
//        cita.setEstado(EstadoCita.PROGRAMADA.toString());
//        cita.setActivo(true);
//        
//        CitaAtencionDaoImpl citadao= new CitaAtencionDaoImpl();
//        int result= citadao.insertar(cita);
//         assertTrue(result > 0);
//        
//        for (CitaAtencionDto cita2 : citadao.listarTodos()){
//            System.out.println("->"+cita2.getCitaId()+" "+cita2.getFechaHoraFin());
//        }
//        
//    }
    @Test
    public void testListasBusquedaAvanzada() {
        String Fecha = "";
        CitaAtencionDaoImpl citadao = new CitaAtencionDaoImpl();
        ArrayList<CitaAtencionDto> lista= citadao.ListasBusquedaAvanzada("2025-11-03","1");
        System.out.println("BUSQUEDA DE CITAS AVANZADO");
        for( CitaAtencionDto p : lista){
            System.out.println("->"+p.getCitaId());
        } 
    }
    @Test
    public void testModificar(){
        CitaAtencionDaoImpl citadao= new CitaAtencionDaoImpl();
        CitaAtencionDto cita= citadao.obtenerPorId(2);
        cita.setObservacion("Probando desde el persistance");
        int result=citadao.modificar(cita);
        assertTrue(result>0,"No se modifico en citas");
        
        
    }
//    @Test
//    public void testListarPorIdMascota() {
//        System.out.println("--- Prueba: Listar Citas por ID de Mascota ---");
//        
//        // 1. Instanciamos el DAO
//        CitaAtencionDaoImpl citaDao = new CitaAtencionDaoImpl();
//        
//        Integer mascotaIdParaProbar = 1; 
//        
//        System.out.println("Buscando citas para la mascota con ID: " + mascotaIdParaProbar);
//
//        // 4. Llamamos al método
//        ArrayList<CitaAtencionDto> citas = citaDao.listarPorIdMascota(mascotaIdParaProbar);
//        
//        // --- ASERCIONES (Assertions) ---
//        
//        // 5. Verificamos que la lista no sea nula
//        assertNotNull(citas, "La lista de citas no debe ser nula.");
//        
//        System.out.println("Se encontraron " + citas.size() + " citas para esta mascota.");
//        
//        // 6. Iteramos y verificamos que CADA cita sea de la mascota correcta
//        for (CitaAtencionDto c : citas) {
//            System.out.println(
//                "  -> Cita ID: " + c.getCitaId() + //
//                ", Mascota ID: " + c.getMascota().getMascotaId() + //
//                ", Estado: " + c.getEstado() //
//            );
//            
//            // Verificamos que los objetos no sean nulos
//            assertNotNull(c, "El objeto CitaAtencionDto no debe ser nulo.");
//            assertNotNull(c.getMascota(), "El objeto Mascota dentro de la cita no debe ser nulo.");
//            
//            // Verificación clave: que la cita sea de la mascota correcta
//            assertEquals(mascotaIdParaProbar, c.getMascota().getMascotaId(),
//                "La cita " + c.getCitaId() + " no pertenece a la mascota " + mascotaIdParaProbar);
//        }
//        
//        if (citas.isEmpty()) {
//            System.out.println("INFO: No se encontraron citas para esta mascota (la lista está vacía).");
//        }
//    }
}
