package pe.edu.pucp.softpet.dto.citas;

import java.sql.Date;
import java.sql.Timestamp;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoCita;

public class CitaAtencionDto {

    private Integer citaId;
    private VeterinarioDto veterinario;
    private MascotaDto mascota;
    private Date fechaRegistro;
    private Timestamp fechaHoraInicio;
    private Timestamp fechaHoraFin;
    private Double pesoMascota;
    private Double monto;
    private EstadoCita estado;
    private String observacion;
    private Boolean activo;

    public CitaAtencionDto() {
        this.citaId = null;
        this.veterinario = null;
        this.mascota = null;
        this.fechaRegistro = null;
        this.fechaHoraInicio = null;
        this.fechaHoraFin = null;
        this.pesoMascota = null;
        this.monto = null;
        this.estado = null;
        this.observacion = null;
        this.activo = null;
    }

    public CitaAtencionDto(Integer citaId, VeterinarioDto veterinario,
            MascotaDto mascota, Date fechaRegistro, Timestamp fechaHoraInicio,
            Timestamp fechaHoraFin, Double pesoMascota, Double monto,
            EstadoCita estado, String observacion, Boolean activo) {
        this.citaId = citaId;
        this.veterinario = veterinario;
        this.mascota = mascota;
        this.fechaRegistro = fechaRegistro;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.pesoMascota = pesoMascota;
        this.monto = monto;
        this.estado = estado;
        this.observacion = observacion;
        this.activo = activo;
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
     * @return the fechaHoraInicio
     */
    public Timestamp getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    /**
     * @param fechaHoraInicio the fechaHoraInicio to set
     */
    public void setFechaHoraInicio(Timestamp fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    /**
     * @return the fechaHoraFin
     */
    public Timestamp getFechaHoraFin() {
        return fechaHoraFin;
    }

    /**
     * @param fechaHoraFin the fechaHoraFin to set
     */
    public void setFechaHoraFin(Timestamp fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    /**
     * @return the pesoMascota
     */
    public Double getPesoMascota() {
        return pesoMascota;
    }

    /**
     * @param pesoMascota the pesoMascota to set
     */
    public void setPesoMascota(Double pesoMascota) {
        this.pesoMascota = pesoMascota;
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
     * @return the estado
     */
    public EstadoCita getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoCita estado) {
        this.estado = estado;
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
