package pe.edu.pucp.softpet.dto.personas;

import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

public class PersonaDto {

    private Integer personaId;
    private UsuarioDto usuario;
    private String nombre;
    private String direccion;
    private String telefono;
    private String sexo;
    private Integer nroDocumento;
    private Integer ruc;
    private String tipoDocumento;
    private Boolean activo;

    public PersonaDto() {
        this.personaId = null;
        this.usuario = null;
        this.nombre = null;
        this.direccion = null;
        this.telefono = null;
        this.sexo = null;
        this.nroDocumento = null;
        this.ruc = null;
        this.tipoDocumento = null;
        this.activo = null;
    }

    public PersonaDto(Integer personaId, UsuarioDto usuario, String nombre,
            String direccion, String telefono, String sexo, Integer nroDocumento,
            Integer ruc, String tipoDocumento, Boolean activo) {
        this.personaId = personaId;
        this.usuario = usuario;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.sexo = sexo;
        this.nroDocumento = nroDocumento;
        this.ruc = ruc;
        this.tipoDocumento = tipoDocumento;
        this.activo = activo;
    }

    /**
     * @return the personaId
     */
    public Integer getPersonaId() {
        return personaId;
    }

    /**
     * @param personaId the personaId to set
     */
    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    /**
     * @return the usuario
     */
    public UsuarioDto getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }

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
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the nroDocumento
     */
    public Integer getNroDocumento() {
        return nroDocumento;
    }

    /**
     * @param nroDocumento the nroDocumento to set
     */
    public void setNroDocumento(Integer nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    /**
     * @return the ruc
     */
    public Integer getRuc() {
        return ruc;
    }

    /**
     * @param ruc the ruc to set
     */
    public void setRuc(Integer ruc) {
        this.ruc = ruc;
    }

    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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
