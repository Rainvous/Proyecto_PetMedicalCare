/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

/**
 *
 * @author snipe
 */
public class MascotaDaoImplTest {
    
    @Test
    public void testListasBusquedaAvanzada() {
        MascotaDaoImpl mascotadao= new MascotaDaoImpl();
        MascotaDto mascota= new MascotaDto();
        mascota.setNombre("");
        mascota.setRaza("");
        mascota.setEspecie("");
        PersonaDto persona = new PersonaDto();
        persona.setNombre("");
        mascota.setPersona(persona);
        ArrayList<MascotaDto> personas= mascotadao.ListasBusquedaAvanzada(mascota);
        System.out.println("BUSQUEDA DE Mascota AVANZADO");
        for( MascotaDto p : personas){
            System.out.println("->"+p.getMascotaId());
            System.out.println("->"+p.getPersona().getNombre());
            System.out.println("->"+p.getNombre());
            System.out.println("->"+p.getEspecie());
            System.out.println("->"+p.getSexo());
        }   
    }
}
