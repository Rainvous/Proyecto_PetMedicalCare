package pe.edu.pucp.softpet.model;

import pe.edu.pucp.softpet.model.actoresdto.MascotasDTO;


public class HistorialDeAtencionesDTO {

    private Integer historiaId;
    private MascotasDTO mascota;
    private String estadoMascota;

    public HistorialDeAtencionesDTO() {
        this.historiaId = null;
        this.mascota = null;
        this.estadoMascota = null;
    }

    public HistorialDeAtencionesDTO(Integer historiaId, MascotasDTO mascota, String estadoMascota) {
        this.historiaId = historiaId;
        this.mascota = mascota;
        this.estadoMascota = estadoMascota;
    }

    /**
     * @return the historiaId
     */
    public Integer getHistoriaId() {
        return historiaId;
    }

    /**
     * @param historiaId the historiaId to set
     */
    public void setHistoriaId(Integer historiaId) {
        this.historiaId = historiaId;
    }

    /**
     * @return the mascota
     */
    public MascotasDTO getMascota() {
        return mascota;
    }

    /**
     * @param mascota the mascotaId to set
     */
    public void setMascota(MascotasDTO mascota) {
        this.mascota = mascota;
    }

    /**
     * @return the estadoMascota
     */
    public String getEstadoMascota() {
        return estadoMascota;
    }

    /**
     * @param estadoMascota the estadoMascota to set
     */
    public void setEstadoMascota(String estadoMascota) {
        this.estadoMascota = estadoMascota;
    }

}
