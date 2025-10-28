package pe.edu.pucp.softpet.dto.personas;

import java.sql.Date;
import java.sql.Timestamp;

public class HorarioLaboralDto {

    private Integer horarioLaboralId;
    private Date fecha;
    private String estado;
    private Timestamp horaInicio;
    private Timestamp horaFin;
    private Boolean activo;

    private VeterinarioDto veterinario; // FK a VETERINARIO

    // Constructor vacío
    public HorarioLaboralDto() {
        this.horarioLaboralId = null;
        this.fecha = null;
        this.estado = null;
        this.horaInicio = null;
        this.horaFin = null;
        this.activo = null;
        this.veterinario = null;
    }

    // Constructor con parámetros
    public HorarioLaboralDto(Integer horarioLaboralId, Date fecha, String estado,
                             Timestamp horaInicio, Timestamp horaFin, Boolean activo,
                             VeterinarioDto veterinario) {
        this.horarioLaboralId = horarioLaboralId;
        this.fecha = fecha;
        this.estado = estado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.activo = activo;
        this.veterinario = veterinario;
    }

    public Integer getHorarioLaboralId() {
        return horarioLaboralId;
    }

    public void setHorarioLaboralId(Integer horarioLaboralId) {
        this.horarioLaboralId = horarioLaboralId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Timestamp getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Timestamp horaFin) {
        this.horaFin = horaFin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public VeterinarioDto getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(VeterinarioDto veterinario) {
        this.veterinario = veterinario;
    }
}
