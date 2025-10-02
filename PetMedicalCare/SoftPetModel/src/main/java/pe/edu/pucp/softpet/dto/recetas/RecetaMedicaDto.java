package pe.edu.pucp.softpet.dto.recetas;

import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;

public class RecetaMedicaDto {
    
    private Integer recetaMedicaId;
    private String diagnostico;
    private Boolean activo;
    private CitaAtencionDto cita;

    public RecetaMedicaDto() {
        this.recetaMedicaId = null;
        this.diagnostico = null;
        this.activo = null;
        this.cita = null;
    }
    
    public RecetaMedicaDto(Integer recetaMedicaId, String diagnostico, 
            Boolean activo, CitaAtencionDto cita) {
        this.recetaMedicaId = recetaMedicaId;
        this.diagnostico = diagnostico;
        this.activo = activo;
        this.cita = cita;
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
}
