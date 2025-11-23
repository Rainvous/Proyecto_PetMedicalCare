package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.softpet.daoImp.VeterinarioDaoImpl;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoVeterinario;

public class VeterinarioBo {

    private final VeterinarioDaoImpl veterinarioDao;

    public VeterinarioBo() {
        this.veterinarioDao = new VeterinarioDaoImpl();
    }

    public Integer insertar(int personaId, String fechaContratacion,
            String estado, String especialidad, boolean activo) {

        VeterinarioDto veterinario = new VeterinarioDto();

        PersonaDto persona = new PersonaDto();
        persona.setPersonaId(personaId);

        veterinario.setPersona(persona);
        veterinario.setFechaContratacion(Date.valueOf(fechaContratacion));
        veterinario.setEstado(EstadoVeterinario.valueOf(estado.toUpperCase()));
        veterinario.setEspecialidad(especialidad);
        veterinario.setActivo(activo);

        return this.veterinarioDao.insertar(veterinario);
    }

    public Integer modificar(int veterinarioId, int personaId, String fechaContratacion,
            String estado, String especialidad, boolean activo) {

        VeterinarioDto veterinario = new VeterinarioDto();

        PersonaDto persona = new PersonaDto();
        persona.setPersonaId(personaId);

        veterinario.setVeterinarioId(veterinarioId);
        veterinario.setPersona(persona);
        veterinario.setFechaContratacion(Date.valueOf(fechaContratacion));
        veterinario.setEstado(EstadoVeterinario.valueOf(estado.toUpperCase()));
        veterinario.setEspecialidad(especialidad);
        veterinario.setActivo(activo);

        return this.veterinarioDao.modificar(veterinario);
    }

    public Integer eliminar(int veterinarioId) {
        VeterinarioDto veterinario = new VeterinarioDto();
        veterinario.setVeterinarioId(veterinarioId);
        return this.veterinarioDao.eliminar(veterinario);
    }

    public VeterinarioDto obtenerPorId(int veterinarioId) {
        return this.veterinarioDao.obtenerPorId(veterinarioId);
    }

    public ArrayList<VeterinarioDto> listarTodos() {
        return this.veterinarioDao.listarTodos();
    }

    public ArrayList<VeterinarioDto> listarVeterinariosActivos() {
        return this.veterinarioDao.listarVeterinariosActivos();
    }

    public int VerificarSiExisteHorarioLaboral(String fecha, Integer idVeterinario) {

        java.sql.Date FechaDate = java.sql.Date.valueOf(fecha);
        Integer resultado = veterinarioDao.VerificarSiExisteHorarioLaboral(FechaDate, idVeterinario);
        return resultado;

    }

    public ArrayList<VeterinarioDto> ListasBusquedaAvanzadaVeterinario(
            String Especialidad,
            String nombre,
            String nroDocumento,
            Integer estadoActivo) { // Parametro INT
        return (ArrayList<VeterinarioDto>) this.veterinarioDao.ListasBusquedaAvanzadaVeterinario(
                Especialidad, nombre, nroDocumento, estadoActivo);
    }

    public Integer insertarVeterinarioCompleto(
            String username, String password, String correo, boolean activoUsuario,
            String nombre, String direccion, String telefono, String sexo,
            Integer nroDocumento, Integer ruc, String tipoDocumento,
            String fechaContratacion, String estado, String especialidad) {
        return this.veterinarioDao.insertarVeterinarioCompleto(
                username, password, correo, activoUsuario,
                nombre, direccion, telefono, sexo,
                nroDocumento, ruc, tipoDocumento,
                fechaContratacion, estado, especialidad);
    }
}
