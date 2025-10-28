package pe.edu.pucp.softpet.dto.recetas;

import java.sql.Date;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;

public class RecetaMedicaDto {

    private Integer recetaMedicaId;
    private Date fechaEmision;
    private Date vigenciaHasta;
    private String diagnostico;
    private String observaciones;
    private Boolean activo;

    private CitaAtencionDto cita; // FK a CITA

    // Constructor vacío
    public RecetaMedicaDto() {
        this.recetaMedicaId = null;
        this.fechaEmision = null;
        this.vigenciaHasta = null;
        this.diagnostico = null;
        this.observaciones = null;
        this.activo = null;
        this.cita = null;
    }

    // Constructor con parámetros
    public RecetaMedicaDto(Integer recetaMedicaId, Date fechaEmision, Date vigenciaHasta,
                           String diagnostico, String observaciones, Boolean activo,
                           CitaAtencionDto cita) {
        this.recetaMedicaId = recetaMedicaId;
        this.fechaEmision = fechaEmision;
        this.vigenciaHasta = vigenciaHasta;
        this.diagnostico = diagnostico;
        this.observaciones = observaciones;
        this.activo = activo;
        this.cita = cita;
    }

    public Integer getRecetaMedicaId() {
        return recetaMedicaId;
    }

    public void setRecetaMedicaId(Integer recetaMedicaId) {
        this.recetaMedicaId = recetaMedicaId;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getVigenciaHasta() {
        return vigenciaHasta;
    }

    public void setVigenciaHasta(Date vigenciaHasta) {
        this.vigenciaHasta = vigenciaHasta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public CitaAtencionDto getCita() {
        return cita;
    }

    public void setCita(CitaAtencionDto cita) {
        this.cita = cita;
    }
}
