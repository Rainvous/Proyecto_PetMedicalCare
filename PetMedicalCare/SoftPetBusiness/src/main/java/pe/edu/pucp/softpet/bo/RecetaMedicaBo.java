package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import java.sql.Date;

import pe.edu.pucp.softpet.dao.RecetaMedicaDao;
import pe.edu.pucp.softpet.daoImp.RecetaMedicaDaoImpl;
import pe.edu.pucp.softpet.daoImp.CitaAtencionDaoImpl;

import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;

public class RecetaMedicaBo {

    private final RecetaMedicaDao recetaDao;

    public RecetaMedicaBo() {
        this.recetaDao = new RecetaMedicaDaoImpl();
    }

    // INSERTAR (PK autoincremental: no se pasa)
    // Orden de parámetros alineado al DaoImpl/DDL: FK -> propios -> auditoría
    public Integer insertar(int citaId,
                            String diagnostico,
                            Date fechaEmision,
                            Date vigenciaHasta,
                            String observaciones,
                            boolean activo) {

        RecetaMedicaDto dto = new RecetaMedicaDto();

        CitaAtencionDto cita = new CitaAtencionDaoImpl().obtenerPorId(citaId);
        dto.setCita(cita);

        dto.setDiagnostico(diagnostico);
        dto.setFechaEmision(fechaEmision);
        dto.setVigenciaHasta(vigenciaHasta);
        dto.setObservaciones(observaciones);
        dto.setActivo(activo);

        return this.recetaDao.insertar(dto);
    }

    // MODIFICAR
    public Integer modificar(int recetaMedicaId,
                             int citaId,
                             String diagnostico,
                             Date fechaEmision,
                             Date vigenciaHasta,
                             String observaciones,
                             boolean activo) {

        RecetaMedicaDto dto = new RecetaMedicaDto();

        dto.setRecetaMedicaId(recetaMedicaId);

        CitaAtencionDto cita = new CitaAtencionDaoImpl().obtenerPorId(citaId);
        dto.setCita(cita);

        dto.setDiagnostico(diagnostico);
        dto.setFechaEmision(fechaEmision);
        dto.setVigenciaHasta(vigenciaHasta);
        dto.setObservaciones(observaciones);
        dto.setActivo(activo);

        return this.recetaDao.modificar(dto);
    }

    // ELIMINAR
    public Integer eliminar(int recetaMedicaId) {
        RecetaMedicaDto dto = new RecetaMedicaDto();
        dto.setRecetaMedicaId(recetaMedicaId);
        return this.recetaDao.eliminar(dto);
    }

    // OBTENER POR ID
    public RecetaMedicaDto obtenerPorId(int recetaMedicaId) {
        return this.recetaDao.obtenerPorId(recetaMedicaId);
    }

    // LISTAR TODOS
    public ArrayList<RecetaMedicaDto> listarTodos() {
        return this.recetaDao.listarTodos();
    }
}
