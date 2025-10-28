/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import java.sql.Date;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

/**
 *
 * @author User
 */
public class RecetaMedicaDaoImplTest {

    public RecetaMedicaDaoImplTest() {
    }

    @Test
    public void testSomeMethod() {
        CitaAtencionDto cita = new CitaAtencionDto();
        cita.setCitaId(1); // Suponiendo que existe la cita con ID 1

        // 🔹 Crear las fechas
        Date fechaEmision = Date.valueOf("2024-5-17"); // Formato yyyy-MM-dd
        Date vigenciaHasta = Date.valueOf("2024-11-10");

        // 🔹 Crear la receta médica
        RecetaMedicaDto receta = new RecetaMedicaDto();
        receta.setRecetaMedicaId(null); // Se genera automáticamente (auto_increment)
        receta.setFechaEmision(fechaEmision);
        receta.setVigenciaHasta(vigenciaHasta);
        receta.setDiagnostico("Cancer de  cerebro");
        receta.setObservaciones("Paciente debe evitar exposición al frío. Control en 2 semanas.");
        receta.setActivo(true);
        receta.setCita(cita);
         RecetaMedicaDaoImpl dao= new RecetaMedicaDaoImpl();
//         int result= dao.insertar(receta);
//         assertTrue(result>0 );
//         assertNotNull(result);
        // ✅ Listo para insertar en la BD
        // Aquí podrías pasar 'receta' a tu DAO para hacer el INSERT con PreparedStatement
        System.out.println("Receta creada: " + receta.getDiagnostico());
        System.out.println("LISTA");
        for( RecetaMedicaDto rec : dao.listarTodos()){
            System.out.println("-> "+rec.getDiagnostico()+" - "+rec.getFechaEmision()+" "+rec.getCita().getCitaId());
        }
    }

}
