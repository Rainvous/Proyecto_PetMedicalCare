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

    public PersonaDAOTest() {
        this.personaDAO = new PersonaDAOImpl();
    }

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
        System.out.println("ID DE LA PERSONA 25: " + persona2.getNombre()
                + persona2.getPersonaId());
        assertTrue(persona2.getPersonaId() != 0);

        System.out.println("MODIFICACION");
        persona2.setNombre("NuevoNombre" + 2);
        // persona2.setPersonaId(26);
        this.personaDAO.modificar(persona2);

        System.out.println("LISTARTODOS Con modificacion");
        ArrayList<PersonasDTO> listaPersonas = this.personaDAO.listarTodos();
        assertEquals(listaPersonas.size(), listaPersonas.size());
        for (Integer i = 0; i < listaPersonas.size(); i++) {
            System.out.println(listaPersonas.get(i).getNombre() + " - " + listaPersonas.get(i).getPersonaId());
        }

        System.out.println("ELIMINAR");
        persona = this.personaDAO.obtenerPorId(80);
        Integer resultado2 = this.personaDAO.eliminar(persona);
        assertTrue(resultado2 != 0);
        //listaPersonasId.add(resultado);

    }

}
