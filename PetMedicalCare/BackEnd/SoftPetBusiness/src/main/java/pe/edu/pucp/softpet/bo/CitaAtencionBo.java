package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.CitaAtencionDao;
import pe.edu.pucp.softpet.daoImp.CitaAtencionDaoImpl;
import pe.edu.pucp.softpet.daoImp.MascotaDaoImpl;
import pe.edu.pucp.softpet.daoImp.VeterinarioDaoImpl;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoCita;

public class CitaAtencionBo {

    private final CitaAtencionDao citaDao;

    public CitaAtencionBo() {
        this.citaDao = new CitaAtencionDaoImpl();
    }

    /**
     * Inserta una nueva Cita de Atención.
     *
     * @param citaAtencion Objeto DTO con los datos de la cita a insertar.
     * @return El ID de la cita insertada.
     */
    public Integer insertar(CitaAtencionDto citaAtencion) {
        return this.citaDao.insertar(citaAtencion);
    }

    /**
     * Modifica una Cita de Atención existente.
     *
     * @param citaAtencion Objeto DTO con los datos de la cita a modificar.
     * @return Número de filas afectadas (debería ser 1 si se actualiza
     * correctamente).
     */
    public Integer modificar(CitaAtencionDto citaAtencion) {
        return this.citaDao.modificar(citaAtencion);
    }

    public Integer eliminar(int citaAtencionId) {
        CitaAtencionDto cita = new CitaAtencionDto();
        cita.setCitaId(citaAtencionId);
        return this.citaDao.eliminar(cita);
    }

    public CitaAtencionDto obtenerPorId(int citaAtencionId) {
        return this.citaDao.obtenerPorId(citaAtencionId);
    }

    public ArrayList<CitaAtencionDto> listarTodos() {
        return this.citaDao.listarTodos();
    }

    public Integer insertar(int veterinarioId, int mascotaId, Date fechaRegistro,
            Timestamp fechaHoraInicio, Timestamp fechaHoraFin, double pesoMascota,
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

        return this.citaDao.insertar(cita);
    }

    public Integer modificar(int citaId, int veterinarioId, int mascotaId,
            Date fechaRegistro, Timestamp fechaHoraInicio, Timestamp fechaHoraFin,
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

        return this.citaDao.modificar(cita);
    }
}
