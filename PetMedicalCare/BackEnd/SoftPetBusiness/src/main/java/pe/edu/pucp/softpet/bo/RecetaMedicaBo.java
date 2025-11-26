package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import java.sql.Date;
import pe.edu.pucp.softpet.dao.RecetaMedicaDao;
import pe.edu.pucp.softpet.daoImp.RecetaMedicaDaoImpl;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

public class RecetaMedicaBo {

    private final RecetaMedicaDao recetaDao;

    public RecetaMedicaBo() {
        this.recetaDao = new RecetaMedicaDaoImpl();
    }

    public Integer insertar(int citaId, String fechaEmision, String vigenciaHasta,
            String diagnostico, String observaciones, boolean activo) {

        RecetaMedicaDto receta = new RecetaMedicaDto();

        CitaAtencionDto cita = new CitaAtencionDto();
        cita.setCitaId(citaId);

        receta.setCita(cita);
        receta.setFechaEmision(Date.valueOf(fechaEmision));
        receta.setVigenciaHasta(Date.valueOf(vigenciaHasta));
        receta.setDiagnostico(diagnostico);
        receta.setObservaciones(observaciones);
        receta.setActivo(activo);

        return this.recetaDao.insertar(receta);
    }

    public Integer modificar(int recetaId, int citaId, String fechaEmision,
            String vigenciaHasta, String diagnostico, String observaciones,
            boolean activo) {

        RecetaMedicaDto receta = new RecetaMedicaDto();

        CitaAtencionDto cita = new CitaAtencionDto();
        cita.setCitaId(citaId);

        receta.setRecetaMedicaId(recetaId);
        receta.setCita(cita);
        receta.setFechaEmision(Date.valueOf(fechaEmision));
        receta.setVigenciaHasta(Date.valueOf(vigenciaHasta));
        receta.setDiagnostico(diagnostico);
        receta.setObservaciones(observaciones);
        receta.setActivo(activo);

        return this.recetaDao.modificar(receta);
    }

    public Integer eliminar(int recetaMedicaId) {
        RecetaMedicaDto dto = new RecetaMedicaDto();
        dto.setRecetaMedicaId(recetaMedicaId);
        return this.recetaDao.eliminar(dto);
    }

    public RecetaMedicaDto obtenerPorId(int recetaMedicaId) {
        return this.recetaDao.obtenerPorId(recetaMedicaId);
    }

    public ArrayList<RecetaMedicaDto> listarTodos() {
        return this.recetaDao.listarTodos();
    }

    public RecetaMedicaDto obtenerPorIdCita(int citaId) {
        return this.recetaDao.obtenerPorIdCita(citaId);
    }
    
    // ... otros métodos ...

    public ArrayList<RecetaMedicaDto> listarPorMascotaYFecha(int mascotaId, String fecha) {
        return this.recetaDao.listarPorMascotaYFecha(mascotaId, fecha);
    }
    
    public ArrayList<RecetaMedicaDto> listarBusquedaAvanzada(String mascota, String duenio, String fechaStr, String activo) {
        java.sql.Date fechaSql = null;
        if (fechaStr != null && !fechaStr.isEmpty()) {
            try {
                fechaSql = java.sql.Date.valueOf(fechaStr);
            } catch (Exception e) {}
        }
        return this.recetaDao.listarBusquedaAvanzada(mascota, duenio, fechaSql, activo);
    }
}
