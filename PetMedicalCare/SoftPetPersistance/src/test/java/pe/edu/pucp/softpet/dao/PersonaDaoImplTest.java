/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;

/**
 *
 * @author User
 */
public class PersonaDaoImplTest {
    
    public PersonaDaoImplTest() {
    }

    /**
     * Test of insertar method, of class PersonaDaoImpl.
     */
    @Test
    public void testInsertar() {
    }

    /**
     * Test of obtenerPorId method, of class PersonaDaoImpl.
     */
    @Test
    public void testObtenerPorId() {
    }

    /**
     * Test of listarTodos method, of class PersonaDaoImpl.
     */
    @Test
    public void testListarTodos() {
    }

    /**
     * Test of modificar method, of class PersonaDaoImpl.
     */
    @Test
    public void testModificar() {
    }

    /**
     * Test of eliminar method, of class PersonaDaoImpl.
     */
    @Test
    public void testEliminar() {
    }

    /**
     * Test of ListasBusquedaAvanzada method, of class PersonaDaoImpl.
     */
    @Test
    public void testListasBusquedaAvanzada() {
        PersonaDaoImpl personadao= new PersonaDaoImpl();
        PersonaDto person= new PersonaDto();
        person.setNombre("");
        person.setNroDocumento(234);
        person.setRuc(0);
        person.setTelefono("");
        personadao.EstablecerMotorBaseDeDatos(MotorDeBaseDeDatos.MYSQL.toString());
        ArrayList<PersonaDto> personas= personadao.ListasBusquedaAvanzada(person);
        System.out.println("BUSQUEDA DE PERSONAS AVANZADO");
        for( PersonaDto p : personas){
            System.out.println("->"+p.getNombre());
        }
        
    }
    
}
