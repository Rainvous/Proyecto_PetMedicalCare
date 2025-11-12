package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.VeterinarioDao;
import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.daoImp.VeterinarioDaoImpl;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoVeterinario;

public class VeterinarioBo {

    private final VeterinarioDao veterinarioDao;

    public VeterinarioBo() {
        this.veterinarioDao = new VeterinarioDaoImpl();
    }
    
    public Integer insertar(int personaId, String fechaContratacion,
            String estado, String especialidad, boolean activo) {

        VeterinarioDto veterinario = new VeterinarioDto();

        veterinario.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        veterinario.setFechaContratacion(Date.valueOf(fechaContratacion));
        veterinario.setEstado(EstadoVeterinario.valueOf(estado));
        veterinario.setEspecialidad(especialidad);
        veterinario.setActivo(activo);

        return this.veterinarioDao.insertar(veterinario);
    }

    public Integer modificar(int veterinarioId, int personaId, String fechaContratacion,
            String estado, String especialidad, boolean activo) {

        VeterinarioDto veterinario = new VeterinarioDto();

        veterinario.setVeterinarioId(veterinarioId);
        veterinario.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        veterinario.setFechaContratacion(Date.valueOf(fechaContratacion));
        veterinario.setEstado(EstadoVeterinario.valueOf(estado));
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
}
