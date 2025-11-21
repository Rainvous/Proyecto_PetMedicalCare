package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.HorarioLaboralDao;
import pe.edu.pucp.softpet.daoImp.HorarioLaboralDaoImpl;
import pe.edu.pucp.softpet.dto.personas.HorarioLaboralDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoLaboral;

public class HorarioLaboralBo {

    private final HorarioLaboralDao horarioDao;

    public HorarioLaboralBo() {
        this.horarioDao = new HorarioLaboralDaoImpl();
    }

    public Integer insertar(int veterinarioId, Date fecha, EstadoLaboral estado,
            Date horaInicio, Date horaFin, boolean activo) {

        HorarioLaboralDto horarioDto = new HorarioLaboralDto();

        VeterinarioDto veterinario = new VeterinarioDto();
        veterinario.setVeterinarioId(veterinarioId);

        horarioDto.setVeterinario(veterinario);
        horarioDto.setFecha(fecha);
        horarioDto.setEstado(estado);
        horarioDto.setHoraInicio(horaInicio);
        horarioDto.setHoraFin(horaFin);
        horarioDto.setActivo(activo);

        return this.horarioDao.insertar(horarioDto);
    }

    public Integer modificar(int hotarioId, int veterinarioId, Date fecha, EstadoLaboral estado,
            Date horaInicio, Date horaFin, boolean activo) {

        HorarioLaboralDto horarioDto = new HorarioLaboralDto();

        VeterinarioDto veterinario = new VeterinarioDto();
        veterinario.setVeterinarioId(veterinarioId);

        horarioDto.setHorarioLaboralId(hotarioId);
        horarioDto.setVeterinario(veterinario);
        horarioDto.setFecha(fecha);
        horarioDto.setEstado(estado);
        horarioDto.setHoraInicio(horaInicio);
        horarioDto.setHoraFin(horaFin);
        horarioDto.setActivo(activo);

        return this.horarioDao.modificar(horarioDto);
    }

    public Integer eliminar(Integer horarioId) {
        HorarioLaboralDto horarioDto = new HorarioLaboralDto();
        horarioDto.setHorarioLaboralId(horarioId);
        return this.horarioDao.eliminar(horarioDto);
    }

    public HorarioLaboralDto obtenerPorId(Integer horarioId) {
        HorarioLaboralDto horarioDto = new HorarioLaboralDto();
        horarioDto.setHorarioLaboralId(horarioId);
        return this.horarioDao.obtenerPorId(horarioId);
    }

    public ArrayList<HorarioLaboralDto> listarTodos() {
        return this.horarioDao.listarTodos();
    }
}
