package pe.edu.pucp.softpet.dto.recetas;

public class DetalleRecetaDto {

    /**
     * @return the presentacion
     */
    public String getPresentacion() {
        return presentacion;
    }

    /**
     * @param presentacion the presentacion to set
     */
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    /**
     * @return the viaAdministracion
     */
    public String getViaAdministracion() {
        return viaAdministracion;
    }

    /**
     * @param viaAdministracion the viaAdministracion to set
     */
    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    /**
     * @return the dosis
     */
    public String getDosis() {
        return dosis;
    }

    /**
     * @param dosis the dosis to set
     */
    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    /**
     * @return the frecuencia
     */
    public String getFrecuencia() {
        return frecuencia;
    }

    /**
     * @param frecuencia the frecuencia to set
     */
    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    /**
     * @return the duracion
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    private Integer detalleRecetaId;
    private Integer cantidad;
    private String descripcionMedicamento;
    private String indicacion;
    private RecetaMedicaDto receta;
    private String presentacion;
    private String viaAdministracion;
    private String dosis;
    private String frecuencia;
    private String duracion;
 
    
    private Boolean activo;

    public DetalleRecetaDto() {
        this.detalleRecetaId = null;
        this.cantidad = null;
        this.descripcionMedicamento = null;
        this.indicacion = null;
        this.receta = null;
        this.activo = null;
    }

    public DetalleRecetaDto(Integer detalleRecetaId, Integer cantidad,
            String descripcionMedicamento, String indicacion,
            RecetaMedicaDto receta, Boolean activo) {
        this.detalleRecetaId = detalleRecetaId;
        this.cantidad = cantidad;
        this.descripcionMedicamento = descripcionMedicamento;
        this.indicacion = indicacion;
        this.receta = receta;
        this.activo = activo;
    }

    /**
     * @return the detalleRecetaId
     */
    public Integer getDetalleRecetaId() {
        return detalleRecetaId;
    }

    /**
     * @param detalleRecetaId the detalleRecetaId to set
     */
    public void setDetalleRecetaId(Integer detalleRecetaId) {
        this.detalleRecetaId = detalleRecetaId;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the descripcionMedicamento
     */
    public String getDescripcionMedicamento() {
        return descripcionMedicamento;
    }

    /**
     * @param descripcionMedicamento the descripcionMedicamento to set
     */
    public void setDescripcionMedicamento(String descripcionMedicamento) {
        this.descripcionMedicamento = descripcionMedicamento;
    }

    /**
     * @return the indicacion
     */
    public String getIndicacion() {
        return indicacion;
    }

    /**
     * @param indicacion the indicacion to set
     */
    public void setIndicacion(String indicacion) {
        this.indicacion = indicacion;
    }

    /**
     * @return the receta
     */
    public RecetaMedicaDto getReceta() {
        return receta;
    }

    /**
     * @param receta the receta to set
     */
    public void setReceta(RecetaMedicaDto receta) {
        this.receta = receta;
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
