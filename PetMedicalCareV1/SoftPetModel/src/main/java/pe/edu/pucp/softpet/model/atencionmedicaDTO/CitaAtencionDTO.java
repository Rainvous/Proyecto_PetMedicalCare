package pe.edu.pucp.softpet.model.atencionmedicaDTO;

import java.sql.Time;
import java.util.Date;
import pe.edu.pucp.softpet.model.actoresdto.MascotasDTO;
import pe.edu.pucp.softpet.model.actoresdto.VeterinariosDTO;

/**
 *
 * @author User
 */
public class CitaAtencionDTO {

    private Integer cita_id;
    private MascotasDTO mascota;
    private VeterinariosDTO veterinario;
    private String tratamiento;
    //private Text observacion;
    private Date fecha_registro;
    private Time hora_inicio;
    private Time hora_fin;
    private Double monto;
    private String estado;

    public CitaAtencionDTO() {
        this.cita_id = null;
        this.mascota = null;
        this.veterinario = null;
        this.tratamiento = null;
        this.fecha_registro = null;
        this.hora_inicio = null;
        this.hora_fin = null;
        this.estado = null;
    }

    public CitaAtencionDTO(Integer cita_id, MascotasDTO mascota,
            VeterinariosDTO veterinario, String tratamiento,
            Date fecha_registro, Time hora_inicio, Time hora_fin, String estado) {
        this.cita_id = cita_id;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.tratamiento = tratamiento;
        this.fecha_registro = fecha_registro;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.estado = estado;
    }

    /**
     * @return the cita_id
     */
    public Integer getCita_id() {
        return cita_id;
    }

    /**
     * @param cita_id the cita_id to set
     */
    public void setCita_id(Integer cita_id) {
        this.cita_id = cita_id;
    }

    /**
     * @return the tratamiento
     */
    public String getTratamiento() {
        return tratamiento;
    }

    /**
     * @param tratamiento the tratamiento to set
     */
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    /**
     * @return the fecha_registro
     */
    public Date getFecha_registro() {
        return fecha_registro;
    }

    /**
     * @param fecha_registro the fecha_registro to set
     */
    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    /**
     * @return the hora_inicio
     */
    public Time getHora_inicio() {
        return hora_inicio;
    }

    /**
     * @param hora_inicio the hora_inicio to set
     */
    public void setHora_inicio(Time hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    /**
     * @return the hora_fin
     */
    public Time getHora_fin() {
        return hora_fin;
    }

    /**
     * @param hora_fin the hora_fin to set
     */
    public void setHora_fin(Time hora_fin) {
        this.hora_fin = hora_fin;
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

    /**
     * @return the mascota
     */
    public MascotasDTO getMascota() {
        return mascota;
    }

    /**
     * @param mascota the mascota to set
     */
    public void setMascota(MascotasDTO mascota) {
        this.mascota = mascota;
    }

    /**
     * @return the veterinario
     */
    public VeterinariosDTO getVeterinario() {
        return veterinario;
    }

    /**
     * @param veterinario the veterinario to set
     */
    public void setVeterinario(VeterinariosDTO veterinario) {
        this.veterinario = veterinario;
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

}
