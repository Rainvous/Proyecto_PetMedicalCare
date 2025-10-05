package pe.edu.pucp.softpet.dto.citas;

import java.sql.Date;
import java.sql.Time;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

public class CitaAtencionDto {
    
    private Integer citaId;
    private String observacion;
    private Time fechaHoraInicio;
    private Date fechaRegistro;
    private Time fechaHoraFin;
    private Double monto;
    private Boolean activo;
    private String peso;
    private VeterinarioDto veterinario;
    private MascotaDto mascota;

    public CitaAtencionDto() {
        this.citaId = null;
        this.observacion = null;
        this.fechaHoraInicio = null;
        this.fechaRegistro = null;
        this.fechaHoraFin = null;
        this.monto = null;
        this.activo = null;
        this.peso = null;
        this.veterinario = null;
        this.mascota = null;
    }
    
    public CitaAtencionDto(Integer citaId, String observacion, 
            Time fechaHoraInicio, Date fechaRegistro, Time fechaHoraFin, 
            Double monto, Boolean activo, String peso, 
            VeterinarioDto veterinario, MascotaDto mascota) {
        this.citaId = citaId;
        this.observacion = observacion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaRegistro = fechaRegistro;
        this.fechaHoraFin = fechaHoraFin;
        this.monto = monto;
        this.activo = activo;
        this.peso = peso;
        this.veterinario = veterinario;
        this.mascota = mascota;
    }

    /**
     * @return the citaId
     */
    public Integer getCitaId() {
        return citaId;
    }

    /**
     * @param citaId the citaId to set
     */
    public void setCitaId(Integer citaId) {
        this.citaId = citaId;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the fechaHoraInicio
     */
    public Time getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    /**
     * @param fechaHoraInicio the fechaHoraInicio to set
     */
    public void setFechaHoraInicio(Time fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the fechaHoraFin
     */
    public Time getFechaHoraFin() {
        return fechaHoraFin;
    }

    /**
     * @param fechaHoraFin the fechaHoraFin to set
     */
    public void setFechaHoraFin(Time fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    /**
     * @return the monto
     */
    public Double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(Double monto) {
        this.monto = monto;
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
     * @return the peso
     */
    public String getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(String peso) {
        this.peso = peso;
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
     * @return the mascota
     */
    public MascotaDto getMascota() {
        return mascota;
    }

    /**
     * @param mascota the mascota to set
     */
    public void setMascota(MascotaDto mascota) {
        this.mascota = mascota;
    }
}
