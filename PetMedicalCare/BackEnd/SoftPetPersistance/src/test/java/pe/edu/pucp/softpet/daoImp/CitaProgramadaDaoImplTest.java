/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.citas.CitaProgramadaDto;

/**
 *
 * @author User
 */
public class CitaProgramadaDaoImplTest {

    public CitaProgramadaDaoImplTest() {
    }

    @Test
    public void testSomeMethod() {
        System.out.println("PROCEDURE DE LISTAR FECHAS EN CITA ATENCION");
        CitaAtencionDaoImpl citadao= new CitaAtencionDaoImpl();

        try {
            String fechastr = "2025-11-03";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechautil = sdf.parse(fechastr);
            ArrayList<CitaProgramadaDto> citas=citadao.ListarProgramadas(1, fechautil);
            for (CitaProgramadaDto c : citas){
                System.out.println("-> "+c.getFecha()+" - "+c.isEstaProgramada());
            }
            
            
        } catch (ParseException ex) {
            Logger.getLogger(CitaProgramadaDaoImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
