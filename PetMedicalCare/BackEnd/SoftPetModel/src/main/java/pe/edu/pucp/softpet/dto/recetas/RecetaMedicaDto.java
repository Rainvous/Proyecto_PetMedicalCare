package pe.edu.pucp.softpet.dto.recetas;

import java.sql.Date;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;

public class RecetaMedicaDto {

    private Integer recetaMedicaId;
    private CitaAtencionDto cita;
    private Date fechaEmision;
    private Date vigenciaHasta;
    private String diagnostico;
    private String observaciones;
    private Boolean activo;

    public RecetaMedicaDto() {
        this.recetaMedicaId = null;
        this.cita = null;
        this.fechaEmision = null;
        this.vigenciaHasta = null;
        this.diagnostico = null;
        this.observaciones = null;
        this.activo = null;
    }

    public RecetaMedicaDto(Integer recetaMedicaId, CitaAtencionDto cita,
            Date fechaEmision, Date vigenciaHasta, String diagnostico,
            String observaciones, Boolean activo) {
        this.recetaMedicaId = recetaMedicaId;
        this.cita = cita;
        this.fechaEmision = fechaEmision;
        this.vigenciaHasta = vigenciaHasta;
        this.diagnostico = diagnostico;
        this.observaciones = observaciones;
        this.activo = activo;
    }

    /**
     * @return the recetaMedicaId
     */
    public Integer getRecetaMedicaId() {
        return recetaMedicaId;
    }

    /**
     * @param recetaMedicaId the recetaMedicaId to set
     */
    public void setRecetaMedicaId(Integer recetaMedicaId) {
        this.recetaMedicaId = recetaMedicaId;
    }

    /**
     * @return the cita
     */
    public CitaAtencionDto getCita() {
        return cita;
    }

    /**
     * @param cita the cita to set
     */
    public void setCita(CitaAtencionDto cita) {
        this.cita = cita;
    }

    /**
     * @return the fechaEmision
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * @param fechaEmision the fechaEmision to set
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * @return the vigenciaHasta
     */
    public Date getVigenciaHasta() {
        return vigenciaHasta;
    }

    /**
     * @param vigenciaHasta the vigenciaHasta to set
     */
    public void setVigenciaHasta(Date vigenciaHasta) {
        this.vigenciaHasta = vigenciaHasta;
    }

    /**
     * @return the diagnostico
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     * @param diagnostico the diagnostico to set
     */
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the activo
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
