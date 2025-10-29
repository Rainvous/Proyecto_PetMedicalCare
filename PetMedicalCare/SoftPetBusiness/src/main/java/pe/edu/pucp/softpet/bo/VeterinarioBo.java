package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.VeterinarioDao;
import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.daoImp.VeterinarioDaoImpl;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

public class VeterinarioBo {

    private final VeterinarioDao veterinarioDao;

    public VeterinarioBo() {
        this.veterinarioDao = new VeterinarioDaoImpl();
    }

    // Inserta un nuevo veterinario
    public Integer insertar(int personaId, int especialidadId,
            Date fechaContratacion, String estado, String especialidad,
            boolean activo) {

        VeterinarioDto veterinario = new VeterinarioDto();

        veterinario.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        veterinario.setEspecialidadId(especialidadId);
        veterinario.setFechaContratacion(fechaContratacion);
        veterinario.setEstado(estado);
        veterinario.setEspecialidad(especialidad);
        veterinario.setActivo(activo);

        return this.veterinarioDao.insertar(veterinario);
    }

    // Modifica un veterinario existente
    public Integer modificar(int veterinarioId, int personaId, int especialidadId,
            Date fechaContratacion, String estado, String especialidad,
            boolean activo) {

        VeterinarioDto veterinario = new VeterinarioDto();

        veterinario.setVeterinarioId(veterinarioId);
        veterinario.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        veterinario.setEspecialidadId(especialidadId);
        veterinario.setFechaContratacion(fechaContratacion);
        veterinario.setEstado(estado);
        veterinario.setEspecialidad(especialidad);
        veterinario.setActivo(activo);

        return this.veterinarioDao.modificar(veterinario);
    }

    // Elimina un veterinario
    public Integer eliminar(int veterinarioId) {
        VeterinarioDto veterinario = new VeterinarioDto();
        veterinario.setVeterinarioId(veterinarioId);
        return this.veterinarioDao.eliminar(veterinario);
    }

    // Obtiene un veterinario por su ID
    public VeterinarioDto obtenerPorId(int veterinarioId) {
        return this.veterinarioDao.obtenerPorId(veterinarioId);
    }

    // Lista todos los veterinarios
    public ArrayList<VeterinarioDto> listarTodos() {
        return this.veterinarioDao.listarTodos();
    }
}
