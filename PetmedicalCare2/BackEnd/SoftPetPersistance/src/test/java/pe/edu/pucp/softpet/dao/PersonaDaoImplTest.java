//package pe.edu.pucp.softpet.dao;
//
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;

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
        String nombre = "";
        String NroDocumento = "";
        String Ruc = "";
        String Telefono = "";
        personadao.EstablecerMotorBaseDeDatos(MotorDeBaseDeDatos.MYSQL.toString());
        ArrayList<PersonaDto> personas= personadao.ListasBusquedaAvanzada(
            nombre,
            NroDocumento,
            Ruc,
            Telefono);
        System.out.println("BUSQUEDA DE PERSONAS AVANZADO");
        for( PersonaDto p : personas){
            System.out.println("->"+p.getNombre());
        }   
    }
    
    
    @Test
    public void testListasBusquedaAvanzadaParaCliente() {
        PersonaDaoImpl personadao= new PersonaDaoImpl();
        personadao.EstablecerMotorBaseDeDatos(MotorDeBaseDeDatos.MYSQL.toString());
        ArrayList<PersonaDto> personas= personadao.ListasBusquedaAvanzadaParaCliente();
        System.out.println("BUSQUEDA DE PERSONAS CLIENTE");
        for( PersonaDto p : personas){
            System.out.println("->"+p.getNombre());
        }   
    }
    
}
