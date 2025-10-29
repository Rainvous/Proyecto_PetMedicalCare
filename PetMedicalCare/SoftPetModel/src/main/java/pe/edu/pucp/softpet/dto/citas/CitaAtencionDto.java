package pe.edu.pucp.softpet.dto.citas;

import java.sql.Date;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

public class CitaAtencionDto {

    private Integer citaId;
    private VeterinarioDto veterinario;
    private MascotaDto mascota;
    private String observacion;
    private Date fechaHoraInicio;
    private Date fechaRegistro;
    private Date fechaHoraFin;
    private Double monto;
    private Boolean activo;
    private Double pesoMascota;
    private String estado;

    public CitaAtencionDto() {
        this.citaId = null;
        this.veterinario = null;
        this.mascota = null;
        this.observacion = null;
        this.fechaHoraInicio = null;
        this.fechaRegistro = null;
        this.fechaHoraFin = null;
        this.monto = null;
        this.activo = null;
        this.pesoMascota = null;
        this.estado = null;
    }

    public CitaAtencionDto(Integer citaId, VeterinarioDto veterinario,
            MascotaDto mascota, Date fechaHoraInicio, Date fechaRegistro,
            Date fechaHoraFin, Double monto, Boolean activo, Double pesoMascota,
            String observacion, String estado) {
        this.citaId = citaId;
        this.veterinario = veterinario;
        this.mascota = mascota;
        this.observacion = observacion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaRegistro = fechaRegistro;
        this.fechaHoraFin = fechaHoraFin;
        this.monto = monto;
        this.activo = activo;
        this.pesoMascota = pesoMascota;
        this.estado = estado;
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
    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    /**
     * @param fechaHoraInicio the fechaHoraInicio to set
     */
    public void setFechaHoraInicio(Date fechaHoraInicio) {
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
    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    /**
     * @param fechaHoraFin the fechaHoraFin to set
     */
    public void setFechaHoraFin(Date fechaHoraFin) {
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
     * @return the pesoMascota
     */
    public Double getPesoMascota() {
        return pesoMascota;
    }

    /**
     * @param pesoMascota the peso to set
     */
    public void setPesoMascota(Double pesoMascota) {
        this.pesoMascota = pesoMascota;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
