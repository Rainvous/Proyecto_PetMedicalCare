package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import pe.edu.pucp.softpet.daoImp.CitaAtencionDaoImpl;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoCita;

public class CitaAtencionBo {

    private final CitaAtencionDaoImpl citaDao;

    public CitaAtencionBo() {
        this.citaDao = new CitaAtencionDaoImpl();
    }

    public Integer insertar(CitaAtencionDto citaAtencion) {
        return this.citaDao.insertar(citaAtencion);
    }

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
        
        VeterinarioDto vet = new VeterinarioDto();
        MascotaDto mas = new MascotaDto();
        
        mas.setMascotaId(mascotaId);
        vet.setVeterinarioId(veterinarioId);
        
        cita.setVeterinario(vet);
        cita.setMascota(mas);
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
        
        VeterinarioDto vet = new VeterinarioDto();
        MascotaDto mas = new MascotaDto();
        
        mas.setMascotaId(mascotaId);
        vet.setVeterinarioId(veterinarioId);
        
        cita.setCitaId(citaId);
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

    public ArrayList<CitaAtencionDto> ListasBusquedaAvanzada(String fecha) {
        return (ArrayList<CitaAtencionDto>) citaDao.ListasBusquedaAvanzada(fecha == null ? "" : fecha);
    }
    
    public ArrayList<CitaAtencionDto> listarPorIdMascota(int mascotaId) {
        return this.citaDao.listarPorIdMascota(mascotaId);
    }
}
