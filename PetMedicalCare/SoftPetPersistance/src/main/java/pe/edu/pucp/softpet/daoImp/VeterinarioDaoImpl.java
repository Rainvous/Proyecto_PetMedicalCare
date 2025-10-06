package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.VeterinarioDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

/**
 *
 * @author marti
 */
public class VeterinarioDaoImpl extends DaoBaseImpl implements VeterinarioDao {

    private VeterinarioDto veterinario;

    public VeterinarioDaoImpl() {
        super("VETERINARIOS");
        this.veterinario = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("VETERINARIO_ID", true, true));
        this.listaColumnas.add(new Columna("ESPECIALIZACION", false, false));
        this.listaColumnas.add(new Columna("FECHA_DE_CONTRATACION", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("PERSONA_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_INICIO_JORNADA", false, false));
        this.listaColumnas.add(new Columna("FECHA_FIN_JORNADA", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.veterinario.getEspecializacion());
        this.statement.setDate(2, this.veterinario.getFechaContratacion());
        this.statement.setString(3, this.veterinario.getEstado());
        this.statement.setInt(4, this.veterinario.getActivo() ? 1 : 0);
        this.statement.setInt(5, this.veterinario.getPersona().getPersonaId());
        this.statement.setDate(6, this.veterinario.getFechaInicioJornada());
        this.statement.setDate(7, this.veterinario.getFechaFinJornada());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.veterinario.getEspecializacion());
        this.statement.setDate(2, this.veterinario.getFechaContratacion());
        this.statement.setString(3, this.veterinario.getEstado());
        this.statement.setInt(4, this.veterinario.getActivo() ? 1 : 0);
        this.statement.setInt(5, this.veterinario.getPersona().getPersonaId());
        this.statement.setDate(6, this.veterinario.getFechaInicioJornada());
        this.statement.setDate(7, this.veterinario.getFechaFinJornada());

        this.statement.setInt(8, this.veterinario.getVeterinarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.veterinario.getVeterinarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.veterinario.getVeterinarioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.veterinario = new VeterinarioDto();
        this.veterinario.setVeterinarioId(this.resultSet.getInt("VETERINARIO_ID"));
        this.veterinario.setEspecializacion(this.resultSet.getString("ESPECIALIZACION"));
        this.veterinario.setFechaContratacion(this.resultSet.getDate("FECHA_DE_CONTRATACION"));
        this.veterinario.setEstado(this.resultSet.getString("ESTADO"));
        this.veterinario.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        this.veterinario.setPersona(new PersonaDaoImpl().obtenerPorId(this.resultSet.getInt("PERSONA_ID")));
        this.veterinario.setFechaInicioJornada(this.resultSet.getDate("FECHA_INICIO_JORNADA"));
        this.veterinario.setFechaFinJornada(this.resultSet.getDate("FECHA_FIN_JORNADA"));
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

    @Override
    public Integer insertar(VeterinarioDto veterinario) {
        this.veterinario = veterinario;
        return super.insertar();
    }

    @Override
    public VeterinarioDto obtenerPorId(Integer personaId) {
        this.veterinario = new VeterinarioDto();
        this.veterinario.setVeterinarioId(personaId);
        super.obtenerPorId();
        return this.veterinario;
    }

    @Override
    public ArrayList<VeterinarioDto> listarTodos() {
        return (ArrayList<VeterinarioDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(VeterinarioDto veterinario) {
        this.veterinario = veterinario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(VeterinarioDto veterinario) {
        this.veterinario = veterinario;
        return super.eliminar();
    }
}
