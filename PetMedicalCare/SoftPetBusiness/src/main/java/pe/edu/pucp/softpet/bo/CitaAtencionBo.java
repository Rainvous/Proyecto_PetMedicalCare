package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.softpet.dao.CitaAtencionDao;
import pe.edu.pucp.softpet.daoImp.CitaAtencionDaoImpl;
import pe.edu.pucp.softpet.daoImp.MascotaDaoImpl;
import pe.edu.pucp.softpet.daoImp.VeterinarioDaoImpl;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;

public class CitaAtencionBo {

    private CitaAtencionDao citaAtencionDao;

    public CitaAtencionBo() {
        this.citaAtencionDao = new CitaAtencionDaoImpl();
    }

    // Inserta una nueva cita
    public Integer insertar(String observacion, Date fechaHoraInicio, Date fechaRegistro,
            Date fechaHoraFin, double monto, boolean activo,
            String pesoMascota, int veterinarioId, int mascotaId, 
            String estadoCita) {

        CitaAtencionDto cita = new CitaAtencionDto();

        // ✅ Conversión correcta de java.util.Date a java.sql.Date
        java.sql.Date fechaInicioSQL = new java.sql.Date(fechaHoraInicio.getTime());
        java.sql.Date fechaRegistroSQL = new java.sql.Date(fechaRegistro.getTime());
        java.sql.Date fechaFinSQL = new java.sql.Date(fechaHoraFin.getTime());

        cita.setObservacion(observacion.trim().toUpperCase());
        cita.setFechaHoraInicio(fechaInicioSQL);
        cita.setFechaRegistro(fechaRegistroSQL);
        cita.setFechaHoraFin(fechaFinSQL);
        cita.setMonto(monto);
        cita.setActivo(activo);
        cita.setPeso(pesoMascota.trim());
        cita.setVeterinario(new VeterinarioDaoImpl().obtenerPorId(veterinarioId));
        cita.setMascota(new MascotaDaoImpl().obtenerPorId(mascotaId));
        cita.setEstado(estadoCita);

        return this.citaAtencionDao.insertar(cita);
    }

    // Modifica una cita existente
    public Integer modificar(int citaId, String observacion, Date fechaHoraInicio, Date fechaRegistro,
            Date fechaHoraFin, double monto, boolean activo,
            String pesoMascota, int veterinarioId, int mascotaId,
            String estadoCita) {

        CitaAtencionDto cita = new CitaAtencionDto();

        // ✅ Conversión correcta
        java.sql.Date fechaInicioSQL = new java.sql.Date(fechaHoraInicio.getTime());
        java.sql.Date fechaRegistroSQL = new java.sql.Date(fechaRegistro.getTime());
        java.sql.Date fechaFinSQL = new java.sql.Date(fechaHoraFin.getTime());

        cita.setCitaId(citaId);
        cita.setObservacion(observacion.trim().toUpperCase());
        cita.setFechaHoraInicio(fechaInicioSQL);
        cita.setFechaRegistro(fechaRegistroSQL);
        cita.setFechaHoraFin(fechaFinSQL);
        cita.setMonto(monto);
        cita.setActivo(activo);
        cita.setPeso(pesoMascota.trim());
        cita.setVeterinario(new VeterinarioDaoImpl().obtenerPorId(veterinarioId));
        cita.setMascota(new MascotaDaoImpl().obtenerPorId(mascotaId));
        cita.setEstado(estadoCita);

        return this.citaAtencionDao.modificar(cita);
    }

    // Elimina una cita
    public Integer eliminar(int citaId) {
        CitaAtencionDto cita = new CitaAtencionDto();
        cita.setCitaId(citaId);
        return this.citaAtencionDao.eliminar(cita);
    }

    // Obtiene una cita por su ID
    public CitaAtencionDto obtenerPorId(int citaId) {
        return this.citaAtencionDao.obtenerPorId(citaId);
    }

    // Lista todas las citas
    public ArrayList<CitaAtencionDto> listarTodos() {
        return this.citaAtencionDao.listarTodos();
    }
}
