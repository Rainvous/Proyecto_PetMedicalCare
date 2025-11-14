/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

/**
 *
 * @author snipe
 */
public class ServicioDaoImplTest {
    
    public ServicioDaoImplTest(){
        
    }
    
    @Test
    public void testListasBusquedaAvanzada() {
        ServicioDaoImpl servicioDao = new ServicioDaoImpl();
        ServicioDto servicio= new ServicioDto();
        servicio.setNombre("");
        String rango = "";
        String activo= "";
         
        ArrayList<ServicioDto> lista= servicioDao.ListasBusquedaAvanzada(servicio,rango,activo);
        System.out.println("BUSQUEDA DE PERSONAS AVANZADO");
        for( ServicioDto p : lista){
            System.out.println("->"+p.getNombre());
        }
        
    }
    
}
