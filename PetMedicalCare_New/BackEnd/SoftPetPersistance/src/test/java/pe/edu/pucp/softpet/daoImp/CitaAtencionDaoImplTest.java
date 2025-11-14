package pe.edu.pucp.softpet.daoImp;

import java.util.ArrayList;
import java.util.Date;
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
        ArrayList<CitaAtencionDto> lista= citadao.ListasBusquedaAvanzada(Fecha);
        System.out.println("BUSQUEDA DE CITAS AVANZADO");
        for( CitaAtencionDto p : lista){
            System.out.println("->"+p.getCitaId());
        }
        
    }
    
}
