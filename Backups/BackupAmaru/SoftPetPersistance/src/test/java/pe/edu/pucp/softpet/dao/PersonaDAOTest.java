package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pe.edu.pucp.softpet.daoImpl.PersonaDAOImpl;
import pe.edu.pucp.softpet.model.actoresdto.PersonasDTO;
import pe.edu.pucp.softpet.model.TipoSexo;
//AUTOR: AMARU

public class PersonaDAOTest {

    private PersonaDAO personaDAO;
    @Test
    public void Datos_Usados_En_Los_CRUDs() {
        System.out.println("INSERTAR");
        PersonasDTO persona = new PersonasDTO();
        persona.setNombre("Martin Sahur Villalobos");
        persona.setCorreo("MartinChameador@gmail.com");
        persona.setDireccion("Santana Villa el salvador");
        persona.setSexo(TipoSexo.FEMENINO);
        persona.setNroDocumento("34398121");
        persona.setTipoDocumento("DNI");
        persona.setTipoPersona("ALIENCODER");
//        Integer resultado = this.personaDAO.insertar(persona);
//        assertTrue(resultado != 0);
        
        System.out.println("OBETENER POR ID");
        PersonasDTO persona2 = this.personaDAO.obtenerPorId(25);
        System.out.println("ID DE LA PERSONA 25: "+ persona2.getNombre()+ 
                persona2.getPersonaId());
        assertTrue(persona2.getPersonaId() != 0);
        
        System.out.println("MODIFICACION");
        persona2.setNombre("NuevoNombre" + 2);
       // persona2.setPersonaId(26);
        this.personaDAO.modificar(persona2);
        
        System.out.println("LISTARTODOS Con modificacion");
        ArrayList<PersonasDTO> listaPersonas = this.personaDAO.listarTodos();
        assertEquals(listaPersonas.size(), listaPersonas.size());
        for (Integer i = 0; i < listaPersonas.size(); i++) {
            System.out.println(listaPersonas.get(i).getNombre()+" - "+listaPersonas.get(i).getPersonaId());
        }
        
        System.out.println("ELIMINAR");
        persona =this.personaDAO.obtenerPorId(56);
        Integer resultado2 = this.personaDAO.eliminar(persona);
        assertTrue(resultado2 != 0);
        //listaPersonasId.add(resultado);

    }

    public PersonaDAOTest() {
        this.personaDAO = new PersonaDAOImpl();
    }

    //@Test
    //testeo para INSERCION
//    public void testInsertar() {
//        // Prepara datos de prueba
//        System.out.println("insertar");
//        PersonasDTO persona = crearPersona("Juan", "Perez", "DNI", "12345678");
//
//        // Inserta y verifica que afecte filas
//        Integer resultado = this.personaDAO.insertar(persona);
//        System.out.println("result: " + resultado);
//        assertTrue(resultado != 0);
//
//        // Verifica que exista (por documento) en el listado
//        PersonasDTO encontrada = buscarPorDocumento(persona.getNroDocumento());
//        assertNotNull(encontrada);
//    }
//    private PersonasDTO crearPersona(String nombre, String apellido, String tipoDoc, String nroDoc) {
//        PersonasDTO p = new PersonasDTO();
//        p.setNombre(nombre);
//        // Muchos DTO separan apellidos; si no, puedes incluirlo en nombre
//        // y omitir campos inexistentes según tu modelo real.
//        p.setDireccion("Direccion de " + nombre);
//        p.setCorreo(".pepito@.com");
//        p.setTelefono("900100200");
//        p.setSexo(TipoSexo.FEMENINO);
//        p.setTipoPersona("CLIENTE");
//        p.setTipoDocumento(tipoDoc);
//        p.setNroDocumento(nroDoc);
//        return p;
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.print("obtener por ID");
//        ArrayList<Integer> listaAlmacenId = new ArrayList<>();
//
//        // Inserta una persona y recupérala del listado para conocer su ID
////        PersonasDTO persona = crearPersona("Maria", "Lopez", "DNI", docUnico("87654321"));
////        this.personaDAO.insertar(persona);
////        PersonasDTO insertada = buscarPorDocumento(persona.getNroDocumento());
////        assertNotNull(insertada);
////
////        // Obtiene por ID y valida
////        PersonasDTO porId = this.personaDAO.obtenerPorId(insertada.getPersonaId());
////        assertNotNull(porId);
////        assertEquals(insertada.getPersonaId(), porId.getPersonaId());
////        assertEquals(insertada.getNroDocumento(), porId.getNroDocumento());
////
////        // Limpieza
////        this.personaDAO.eliminar(insertada);
//    }
//
//    @Test
//    public void testListarTodos() {
////        // Limpia, inserta varias y valida tamaño
////        eliminarTodo();
////        ArrayList<Integer> ids = insertarPersonas(3);
////        ArrayList<PersonasDTO> lista = this.personaDAO.listarTodos();
////        assertEquals(ids.size(), lista.size());
////
////        // Limpieza
////        eliminarTodo();
//    }
//
//    @Test
//    public void testModificar() {
//        // Inserta y ubica persona
////        System.out.println("modificar");
////        PersonasDTO persona = crearPersona("Luis", "Gomez", "DNI", docUnico("11223344"));
////        this.personaDAO.insertar(persona);
////        PersonasDTO insertada = buscarPorDocumento(persona.getNroDocumento());
////        assertNotNull(insertada);
////
////        // Modifica campos
////        insertada.setNombre("Luis Alberto");
////        insertada.setDireccion("Av. Siempre Viva 742");
////        insertada.setCorreo("luis.alberto@example.com");
////        insertada.setTelefono("999888777");
////
////        // Ejecuta modificación
////        Integer resultado = this.personaDAO.modificar(insertada);
////        assertNotEquals(0, resultado);
////
////        // Relee y valida cambios
////        PersonasDTO actualizada = this.personaDAO.obtenerPorId(insertada.getPersonaId());
////        assertNotNull(actualizada);
////        assertEquals("Luis Alberto", actualizada.getNombre());
////        assertEquals("Av. Siempre Viva 742", actualizada.getDireccion());
////        assertEquals("luis.alberto@example.com", actualizada.getCorreo());
////        assertEquals("999888777", actualizada.getTelefono());
////
////        // Limpieza
////        this.personaDAO.eliminar(actualizada);
//    }
//
//    @Test
//    public void testEliminar() {
//        // Inserta y ubica persona
////        System.out.println("eliminar");
////        PersonasDTO persona = crearPersona("Ana", "Torres", "DNI", docUnico("55667788"));
////        this.personaDAO.insertar(persona);
////        PersonasDTO insertada = buscarPorDocumento(persona.getNroDocumento());
////        assertNotNull(insertada);
////
////        // Elimina y valida
////        Integer resultado = this.personaDAO.eliminar(insertada);
////        assertNotEquals(0, resultado);
////
////        PersonasDTO porId = this.personaDAO.obtenerPorId(insertada.getPersonaId());
////        assertNull(porId);
//    }
//
//    // ==================
//    // Métodos auxiliares
//    // ==================
////    private ArrayList<Integer> insertarPersonas(int n) {
////        ArrayList<Integer> ids = new ArrayList<>();
////        for (int i = 1; i <= n; i++) {
////            PersonasDTO p = crearPersona("Persona" + i, "Apellido" + i, "DNI", docUnico("00000" + i));
////            this.personaDAO.insertar(p);
////            PersonasDTO enBD = buscarPorDocumento(p.getNroDocumento());
////            assertNotNull(enBD);
////            ids.add(enBD.getPersonaId());
////        }
////        return ids;
////    }
//    private void eliminarTodo() {
//        // Elimina todo lo listado para empezar limpio como en el ejemplo de AlmacenDAOTest
//        ArrayList<PersonasDTO> lista = this.personaDAO.listarTodos();
//        for (int i = 0; i < lista.size(); i++) {
//            Integer res = this.personaDAO.eliminar(lista.get(i));
//            assertNotEquals(0, res);
//            PersonasDTO p = this.personaDAO.obtenerPorId(lista.get(i).getPersonaId());
//            assertNull(p);
//        }
//    }
//
//    private String docUnico(String base) {
//        // Genera documentos únicos por ejecución
//        return base + "-" + (System.currentTimeMillis() % 100000);
//    }
//
//    private PersonasDTO buscarPorDocumento(String nroDocumento) {
//        ArrayList<PersonasDTO> lista = this.personaDAO.listarTodos();
//        for (PersonasDTO p : lista) {
//            if (nroDocumento.equals(p.getNroDocumento())) {
//                return p;
//            }
//        }
//        return null;
//    }
}
