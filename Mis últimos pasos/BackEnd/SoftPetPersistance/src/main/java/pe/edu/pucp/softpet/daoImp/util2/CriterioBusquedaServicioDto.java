package pe.edu.pucp.softpet.daoImp.util2;

public class CriterioBusquedaServicioDto {

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the rangoPrecioId
     */
    public String getRangoPrecioId() {
        return rangoPrecioId;
    }

    /**
     * @param rangoPrecioId the rangoPrecioId to set
     */
    public void setRangoPrecioId(String rangoPrecioId) {
        this.rangoPrecioId = rangoPrecioId;
    }

    /**
     * @return the activo
     */
    public String getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(String activo) {
        this.activo = activo;
    }

    /**
     * @return the numeroPagina
     */
    public int getNumeroPagina() {
        return numeroPagina;
    }

    /**
     * @param numeroPagina the numeroPagina to set
     */
    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }
    private String nombre;
    private String rangoPrecioId; // "1", "2", "3" o null
    private String activo;        // "1", "0" o null
    private int numeroPagina;     // 1, 2, 3...

    // Constructor, Getters y Setters
    public CriterioBusquedaServicioDto(String nombre, String rangoPrecioId, String activo, int numeroPagina) {
        this.nombre = nombre;
        this.rangoPrecioId = rangoPrecioId;
        this.activo = activo;
        this.numeroPagina = numeroPagina;
    }
    
    // ... getters y setters ...
}