package pe.edu.pucp.softpet.dto.personas;

import java.sql.Date;
import pe.edu.pucp.softpet.dto.util.enums.EstadoLaboral;

public class HorarioLaboralDto {

    private Integer horarioLaboralId;
    private VeterinarioDto veterinario;
    private Date fecha;
    private EstadoLaboral estado;
    private Date horaInicio;
    private Date horaFin;
    private Boolean activo;

    public HorarioLaboralDto() {
        this.horarioLaboralId = null;
        this.veterinario = null;
        this.fecha = null;
        this.estado = null;
        this.horaInicio = null;
        this.horaFin = null;
        this.activo = null;
    }

    public HorarioLaboralDto(Integer horarioLaboralId, VeterinarioDto veterinario,
            Date fecha, EstadoLaboral estado, Date horaInicio, Date horaFin,
            Boolean activo) {
        this.horarioLaboralId = horarioLaboralId;
        this.veterinario = veterinario;
        this.fecha = fecha;
        this.estado = estado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.activo = activo;
    }

    /**
     * @return the horarioLaboralId
     */
    public Integer getHorarioLaboralId() {
        return horarioLaboralId;
    }

    /**
     * @param horarioLaboralId the horarioLaboralId to set
     */
    public void setHorarioLaboralId(Integer horarioLaboralId) {
        this.horarioLaboralId = horarioLaboralId;
    }

    /**
     * @return the veterinario
     */
    public VeterinarioDto getVeterinario() {
        return veterinario;
    }

    /**
     * @param veterinario the veterinario to set
     */
    public void setVeterinario(VeterinarioDto veterinario) {
        this.veterinario = veterinario;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the estado
     */
    public EstadoLaboral getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoLaboral estado) {
        this.estado = estado;
    }

    /**
     * @return the horaInicio
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFin
     */
    public Date getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
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
