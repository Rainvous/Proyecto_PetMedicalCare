package pe.edu.pucp.softpet.daoImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.VeterinarioDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.actoresdto.PersonasDTO;
import pe.edu.pucp.softpet.model.actoresdto.VeterinariosDTO;

/**
 *
 * @author ferro
 */
public class VeterinarioDAOImpl extends DAOImplBase implements VeterinarioDAO {

    private VeterinariosDTO veterinario;
    
    public VeterinarioDAOImpl() {

        super("PRODUCTO");
        this.veterinario = null;
        this.retornarLlavePrimaria = true;
    }

    public VeterinarioDAOImpl(String nombre_tabla) {

        super(nombre_tabla);
        this.veterinario = null;
        this.retornarLlavePrimaria = true;
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("VETERINARIO_ID", true, true));
        this.listaColumnas.add(new Columna("PERSONA_ID", false, false));
        this.listaColumnas.add(new Columna("ESPECIALIZACION", false, false));
        this.listaColumnas.add(new Columna("FECHA_DE_CONTRATACION", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("SUELDO", false, false));
    }
    
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        this.statement.setObject(1, this.veterinario.getPersona());        
        this.statement.setString(1, this.veterinario.getEspecializacion());        
        this.statement.setDate(1, (Date) this.veterinario.getFechaDeContratacion());        
        this.statement.setString(1, this.veterinario.getEstado());        
        this.statement.setDouble(1, this.veterinario.getSueldo());        
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        this.statement.setObject(1, this.veterinario.getPersona());        
        this.statement.setString(2, this.veterinario.getEspecializacion());        
        this.statement.setDate(3, (Date) this.veterinario.getFechaDeContratacion());        
        this.statement.setString(4, this.veterinario.getEstado());        
        this.statement.setDouble(5, this.veterinario.getSueldo());   
        
        this.statement.setInt(6, this.veterinario.getVeterinarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {

        this.statement.setInt(1, this.veterinario.getVeterinarioId());
    }

    @Override
    public Integer insertar(VeterinariosDTO veterinario) {
        this.veterinario = veterinario;
        return super.insertar();
    }

    @Override
    public VeterinariosDTO obtenerPorId(Integer veterinarioId) {
        this.veterinario = new VeterinariosDTO();
        this.veterinario.setVeterinarioId(veterinarioId);
        super.obtenerPorId();
        return this.veterinario;
    }

    @Override
    public ArrayList<VeterinariosDTO> listarTodos() {
        return (ArrayList<VeterinariosDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(VeterinariosDTO veterinario) {
        this.veterinario = veterinario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(VeterinariosDTO veterinario) {
        this.veterinario = veterinario;
        return super.eliminar();
    }
    
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.veterinario.getVeterinarioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.veterinario = new VeterinariosDTO();
        this.veterinario.setVeterinarioId(this.resultSet.getInt("VETERINARIO_ID"));
        this.veterinario.setPersona((PersonasDTO) this.resultSet.getObject("PERSONA_ID"));
        this.veterinario.setEspecializacion(this.resultSet.getString("ESPECIALIZACION"));
        this.veterinario.setFechaDeContratacion(this.resultSet.getDate("FECHA_DE_CONTRATACION"));
        this.veterinario.setEstado(this.resultSet.getString("ESTADO"));
            this.veterinario.setSueldo(this.resultSet.getDouble("SUELDO"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.veterinario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.veterinario);
    }
    
}
