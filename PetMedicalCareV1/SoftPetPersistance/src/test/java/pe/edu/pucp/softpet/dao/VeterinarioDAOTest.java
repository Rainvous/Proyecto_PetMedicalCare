/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.daoImpl.VeterinarioDAOImpl;
import pe.edu.pucp.softpet.model.TipoSexo;
import pe.edu.pucp.softpet.model.actoresdto.PersonasDTO;
import pe.edu.pucp.softpet.model.actoresdto.VeterinariosDTO;

/**
 *
 * @author User
 */
public class VeterinarioDAOTest {
    
    private VeterinarioDAO veterinarioDAO;
    
    public VeterinarioDAOTest() {
        this.veterinarioDAO=new VeterinarioDAOImpl();
    }
    
//    @BeforeAll
//    public static void setUpClass() {
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }

    /**
     * Test of insertar method, of class VeterinarioDAO.
     */
    @Test
    public void testInsertar() {
        System.out.println("insertar");
        VeterinariosDTO veterinario = crearVeterinario("MédicoVeterinario","Activo",100026.7);
//        VeterinarioDAO instance = new VeterinarioDAOImpl();
//        Integer expResult = null;
        Integer result = this.veterinarioDAO.insertar(veterinario);
        System.out.println("result"+result);
        assertTrue(result != 0);
        
        VeterinariosDTO encontrado=buscarPorSueldo(veterinario.getSueldo());
        assertNotNull(encontrado);
    }

    /**
     * Test of obtenerPorId method, of class VeterinarioDAO.
     */
    @Test
    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        Integer veterinarioId = null;
//        VeterinarioDAO instance = new VeterinarioDAOImpl();
//        VeterinariosDTO expResult = null;
//        VeterinariosDTO result = instance.obtenerPorId(veterinarioId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of listarTodos method, of class VeterinarioDAO.
     */
    @Test
    public void testListarTodos() {
//        System.out.println("listarTodos");
//        VeterinarioDAO instance = new VeterinarioDAOImpl();
//        ArrayList<VeterinariosDTO> expResult = null;
//        ArrayList<VeterinariosDTO> result = instance.listarTodos();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of modificar method, of class VeterinarioDAO.
     */
    @Test
    public void testModificar() {
//        System.out.println("modificar");
//        VeterinariosDTO veterinario = null;
//        VeterinarioDAO instance = new VeterinarioDAOImpl();
//        Integer expResult = null;
//        Integer result = instance.modificar(veterinario);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminar method, of class VeterinarioDAO.
     */
    @Test
    public void testEliminar() {
//        System.out.println("eliminar");
//        VeterinariosDTO veterinario = null;
//        VeterinarioDAO instance = new VeterinarioDAOImpl();
//        Integer expResult = null;
//        Integer result = instance.eliminar(veterinario);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

//    public class VeterinarioDAOImpl implements VeterinarioDAO {
//
//        public Integer insertar(VeterinariosDTO veterinario) {
//            return null;
//        }
//
//        public VeterinariosDTO obtenerPorId(Integer veterinarioId) {
//            return null;
//        }
//
//        public ArrayList<VeterinariosDTO> listarTodos() {
//            return null;
//        }
//
//        public Integer modificar(VeterinariosDTO veterinario) {
//            return null;
//        }
//
//        public Integer eliminar(VeterinariosDTO veterinario) {
//            return null;
//        }
//    }
    
    private void eliminarTodo() {
        // Elimina todo lo listado para empezar limpio como en el ejemplo de AlmacenDAOTest
        ArrayList<VeterinariosDTO> lista = this.veterinarioDAO.listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            Integer res = this.veterinarioDAO.eliminar(lista.get(i));
            assertNotEquals(0, res);
            VeterinariosDTO p = this.veterinarioDAO.obtenerPorId(lista.get(i).getVeterinarioId());
            assertNull(p);
        }
    }

    private VeterinariosDTO crearVeterinario(String especializacion, String estado, 
             Double sueldo) {
        VeterinariosDTO p = new VeterinariosDTO();
        p.setEspecializacion(especializacion);
        // creo que seria una fecha al azar parap, asi no esté como atributo?
        // como asi? es que en la clase veterinario no hay fecha de contratacion, o esp creo, olviddalo, si hay:(
        // jeje, si, solo estoy buscando como agregar una fecha a al atributo :) sisisi es que pense que no
        //habia fecha contratacion
        
        PersonasDTO persona = new PersonasDTO(3, "DAVID", " AV 123", "aa@", "9999", TipoSexo.MASCULINO, "dibt", 
                "34344", "343433");
        p.setPersona(persona);
        p.setFechaDeContratacion(null);
        // agua no sale, le dejaremos en null de omento
        p.setEstado(estado);
        p.setSueldo(sueldo);
        

        return p;
    }

    private String docUnico(String base) {
        // Genera documentos únicos por ejecución
        return base + "-" + (System.currentTimeMillis() % 100000);
    }

    private VeterinariosDTO buscarPorSueldo(Double sueldo) {
        ArrayList<VeterinariosDTO> lista = this.veterinarioDAO.listarTodos();
        for (VeterinariosDTO p : lista) {
            if (sueldo.equals(p.getSueldo())) {
                return p;
            }
        }
        return null;
    }
    
}
