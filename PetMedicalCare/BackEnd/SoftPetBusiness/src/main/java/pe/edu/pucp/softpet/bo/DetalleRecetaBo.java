package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;

import pe.edu.pucp.softpet.dao.DetalleRecetaDao;
import pe.edu.pucp.softpet.daoImp.DetalleRecetaDaoImpl;
import pe.edu.pucp.softpet.dto.recetas.DetalleRecetaDto;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

public class DetalleRecetaBo {

    private final DetalleRecetaDao dao;

    public DetalleRecetaBo() {
        this.dao = new DetalleRecetaDaoImpl();
    }

    public Integer insertar(
            int recetaMedicaId,
            String descripcionMedicamento,
            String presentacion,
            String viaAdministracion,
            String dosis,
            String frecuencia,
            String duracion,
            String indicacion,
            String cantidad,
            boolean activo) {

        DetalleRecetaDto dto = new DetalleRecetaDto();
        
        RecetaMedicaDto receta = new RecetaMedicaDto();
        receta.setRecetaMedicaId(recetaMedicaId);
        
        dto.setReceta(receta);
        dto.setDescripcionMedicamento(descripcionMedicamento);
        dto.setPresentacion(presentacion);
        dto.setViaAdministracion(viaAdministracion);
        dto.setDosis(dosis);
        dto.setFrecuencia(frecuencia);
        dto.setDuracion(duracion);
        dto.setIndicacion(indicacion);
        dto.setCantidad(cantidad);
        dto.setActivo(activo);

        return this.dao.insertar(dto);
    }

    public Integer insertar(
            DetalleRecetaDto dto) {

        return this.dao.insertar(dto);
    }

    public Integer modificar(
            int detalleRecetaId,
            int recetaMedicaId,
            String descripcionMedicamento,
            String presentacion,
            String viaAdministracion,
            String dosis,
            String frecuencia,
            String duracion,
            String indicacion,
            String cantidad,
            boolean activo) {

        DetalleRecetaDto dto = new DetalleRecetaDto();
        
        RecetaMedicaDto receta = new RecetaMedicaDto();
        receta.setRecetaMedicaId(recetaMedicaId);

        dto.setDetalleRecetaId(detalleRecetaId);
        dto.setReceta(receta);
        dto.setDescripcionMedicamento(descripcionMedicamento);
        dto.setPresentacion(presentacion);
        dto.setViaAdministracion(viaAdministracion);
        dto.setDosis(dosis);
        dto.setFrecuencia(frecuencia);
        dto.setDuracion(duracion);
        dto.setIndicacion(indicacion);
        dto.setCantidad(cantidad);
        dto.setActivo(activo);
        
        return this.dao.modificar(dto);
    }

    public Integer modificar(
            DetalleRecetaDto dto) {

        return this.dao.modificar(dto);
    }

    public Integer eliminar(int detalleRecetaId) {
        DetalleRecetaDto dto = new DetalleRecetaDto();
        dto.setDetalleRecetaId(detalleRecetaId);
        return this.dao.eliminar(dto);
    }

    public DetalleRecetaDto obtenerPorId(int detalleRecetaId) {
        return this.dao.obtenerPorId(detalleRecetaId);
    }

    public ArrayList<DetalleRecetaDto> listarTodos() {
        return this.dao.listarTodos();
    }

    public ArrayList<DetalleRecetaDto> listarPorIdReceta(int recetaMedicaId) {
        return this.dao.listarPorIdReceta(recetaMedicaId);
    }
}
