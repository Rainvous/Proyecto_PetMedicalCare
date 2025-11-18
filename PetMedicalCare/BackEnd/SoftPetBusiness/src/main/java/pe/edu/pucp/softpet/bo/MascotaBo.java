package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.softpet.daoImp.MascotaDaoImpl;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

public class MascotaBo {

    private final MascotaDaoImpl mascotaDao;

    public MascotaBo() {
        this.mascotaDao = new MascotaDaoImpl();
    }

    public Integer insertar(int personaId, String nombre, String especie,
            String sexo, String raza, String color, String fechaDefuncion,
            boolean activo) {

        MascotaDto mascota = new MascotaDto();

        PersonaDto persona = new PersonaDto();
        persona.setPersonaId(personaId);

        mascota.setPersona(persona);
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setSexo(sexo);
        mascota.setRaza(raza);
        mascota.setColor(color);
        if (fechaDefuncion != null) {
            mascota.setFechaDefuncion(Date.valueOf(fechaDefuncion));
        }
        mascota.setActivo(activo);

        return this.mascotaDao.insertar(mascota);
    }

    public Integer modificar(int mascotaId, int personaId, String nombre, String especie,
            String sexo, String raza, String color, String fechaDefuncion,
            boolean activo) {

        MascotaDto mascota = new MascotaDto();

        PersonaDto persona = new PersonaDto();
        persona.setPersonaId(personaId);

        mascota.setMascotaId(mascotaId);
        mascota.setPersona(persona);
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setSexo(sexo);
        mascota.setRaza(raza);
        mascota.setColor(color);
        if (fechaDefuncion != null) {
            mascota.setFechaDefuncion(Date.valueOf(fechaDefuncion));
        }
        mascota.setActivo(activo);

        return this.mascotaDao.modificar(mascota);
    }

    public Integer eliminar(int mascotaId) {
        MascotaDto mascota = new MascotaDto();
        mascota.setMascotaId(mascotaId);
        return this.mascotaDao.eliminar(mascota);
    }

    public MascotaDto obtenerPorId(int mascotaId) {
        return this.mascotaDao.obtenerPorId(mascotaId);
    }

    public ArrayList<MascotaDto> listarTodos() {
        return this.mascotaDao.listarTodos();
    }

    // --- CORRECCIÓN IMPORTANTE AQUÍ ---
    public ArrayList<MascotaDto> ListasBusquedaAvanzada(String nombreMascota,
            String raza, String especie, String nombreDeLaPersona) {
        MascotaDto mascota = new MascotaDto();
        PersonaDto persona = new PersonaDto();
        
        // FIX: Asignar cada variable a su campo correspondiente
        mascota.setNombre(nombreMascota == null ? "" : nombreMascota);
        mascota.setRaza(raza == null ? "" : raza); // Antes decía nombreMascota
        mascota.setEspecie(especie == null ? "" : especie); // Antes decía nombreMascota
        
        persona.setNombre(nombreDeLaPersona == null ? "" : nombreDeLaPersona); // Antes decía nombreMascota
        mascota.setPersona(persona);

        return (ArrayList<MascotaDto>) mascotaDao.ListasBusquedaAvanzada(mascota);
    }
    // ----------------------------------

    public ArrayList<MascotaDto> listarPorIdPersona(int personaId) {
        return this.mascotaDao.listarPorIdPersona(personaId);
    }

    public ArrayList<MascotaDto> listarMascotasActivas() {
        return this.mascotaDao.listarMascotasActivas();
    }
    
    public int VerificarSiLaMascotaTieneInformacion(int idServicio){
        return this.mascotaDao.VerificarSiLaMascotaTieneInformacion(idServicio);
    }
}
