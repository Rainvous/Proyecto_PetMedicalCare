package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.CitaAtencionDao;
import pe.edu.pucp.softpet.daoImp.CitaAtencionDaoImpl;
import pe.edu.pucp.softpet.daoImp.MascotaDaoImpl;
import pe.edu.pucp.softpet.daoImp.VeterinarioDaoImpl;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoCita;

public class CitaAtencionBo {

    private final CitaAtencionDao citaAtencionDao;

    public CitaAtencionBo() {
        this.citaAtencionDao = new CitaAtencionDaoImpl();
    }

    public Integer insertar(int veterinarioId, int mascotaId, Date fechaRegistro,
            Date fechaHoraInicio, Date fechaHoraFin, double pesoMascota,
            double monto, EstadoCita estado, String observacion, boolean activo) {

        CitaAtencionDto cita = new CitaAtencionDto();

        cita.setVeterinario(new VeterinarioDaoImpl().obtenerPorId(veterinarioId));
        cita.setMascota(new MascotaDaoImpl().obtenerPorId(mascotaId));
        cita.setFechaRegistro(fechaRegistro);
        cita.setFechaHoraInicio(fechaHoraInicio);
        cita.setFechaHoraFin(fechaHoraFin);
        cita.setPesoMascota(pesoMascota);
        cita.setMonto(monto);
        cita.setEstado(estado);
        cita.setObservacion(observacion);
        cita.setActivo(activo);

        return this.citaAtencionDao.insertar(cita);
    }

    public Integer modificar(int citaId, int veterinarioId, int mascotaId,
            Date fechaRegistro, Date fechaHoraInicio, Date fechaHoraFin,
            double pesoMascota, double monto, EstadoCita estado, String observacion,
            boolean activo) {

        CitaAtencionDto cita = new CitaAtencionDto();

        cita.setCitaId(citaId);
        cita.setVeterinario(new VeterinarioDaoImpl().obtenerPorId(veterinarioId));
        cita.setMascota(new MascotaDaoImpl().obtenerPorId(mascotaId));
        cita.setFechaRegistro(fechaRegistro);
        cita.setFechaHoraInicio(fechaHoraInicio);
        cita.setFechaHoraFin(fechaHoraFin);
        cita.setPesoMascota(pesoMascota);
        cita.setMonto(monto);
        cita.setEstado(estado);
        cita.setObservacion(observacion);
        cita.setActivo(activo);

        return this.citaAtencionDao.modificar(cita);
    }

    public Integer eliminar(int citaId) {
        CitaAtencionDto cita = new CitaAtencionDto();
        cita.setCitaId(citaId);
        return this.citaAtencionDao.eliminar(cita);
    }

    public CitaAtencionDto obtenerPorId(int citaId) {
        return this.citaAtencionDao.obtenerPorId(citaId);
    }

    public ArrayList<CitaAtencionDto> listarTodos() {
        return this.citaAtencionDao.listarTodos();
    }
}
