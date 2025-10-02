package pe.edu.pucp.softpet.model;

import pe.edu.pucp.softpet.model.actoresdto.MascotasDTO;


public class HistoriasAtencion {

    private String historiaId;
    private MascotasDTO mascotaId;
    private String estadoMascota;

    public HistoriasAtencion() {
        this.historiaId = null;
        this.mascotaId = null;
        this.estadoMascota = null;
    }

    public HistoriasAtencion(String historiaId, MascotasDTO mascotaId, String estadoMascota) {
        this.historiaId = historiaId;
        this.mascotaId = mascotaId;
        this.estadoMascota = estadoMascota;
    }

    /**
     * @return the historiaId
     */
    public String getHistoriaId() {
        return historiaId;
    }

    /**
     * @param historiaId the historiaId to set
     */
    public void setHistoriaId(String historiaId) {
        this.historiaId = historiaId;
    }

    /**
     * @return the mascotaId
     */
    public MascotasDTO getMascotaId() {
        return mascotaId;
    }

    /**
     * @param mascotaId the mascotaId to set
     */
    public void setMascotaId(MascotasDTO mascotaId) {
        this.mascotaId = mascotaId;
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
