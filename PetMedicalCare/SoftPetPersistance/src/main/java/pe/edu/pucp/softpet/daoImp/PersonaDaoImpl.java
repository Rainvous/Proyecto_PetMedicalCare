package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.dao.PersonaDao;

public class PersonaDaoImpl extends DaoBaseImpl implements PersonaDao {

    private PersonaDto persona;

    public PersonaDaoImpl() {
        super("PERSONAS");
        this.persona = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PERSONA_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DIRECCION", false, false));
        this.listaColumnas.add(new Columna("TELEFONO", false, false));
        this.listaColumnas.add(new Columna("SEXO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("TIPO_DOCUMENTO", false, false));
        this.listaColumnas.add(new Columna("NRO_DOCUMENTO", false, false));
        this.listaColumnas.add(new Columna("RUC", false, false));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.persona.getNombre());
        this.statement.setString(2, this.persona.getDireccion());
        this.statement.setString(3, this.persona.getTelefono());
        this.statement.setString(4, this.persona.getSexo());
        this.statement.setInt(5, this.persona.getActivo() ? 1 : 0);
        this.statement.setString(6, this.persona.getTipoDocumento());
        this.statement.setInt(7, this.persona.getNroDocumento());
        this.statement.setInt(8, this.persona.getRuc());
        this.statement.setInt(9, this.persona.getUsuario().getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.persona.getNombre());
        this.statement.setString(2, this.persona.getDireccion());
        this.statement.setString(3, this.persona.getTelefono());
        this.statement.setString(4, this.persona.getSexo());
        this.statement.setInt(5, this.persona.getActivo() ? 1 : 0);
        this.statement.setString(6, this.persona.getTipoDocumento());
        this.statement.setInt(7, this.persona.getNroDocumento());
        this.statement.setInt(8, this.persona.getRuc());
        this.statement.setInt(9, this.persona.getUsuario().getUsuarioId());

        this.statement.setInt(10, this.persona.getPersonaId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.persona.getPersonaId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.persona.getPersonaId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.persona = new PersonaDto();
        this.persona.setPersonaId(this.resultSet.getInt("PERSONA_ID"));
        this.persona.setNombre(this.resultSet.getString("NOMBRE"));
        this.persona.setDireccion(this.resultSet.getString("DIRECCION"));
        this.persona.setTelefono(this.resultSet.getString("TELEFONO"));
        this.persona.setSexo(this.resultSet.getString("SEXO"));
        this.persona.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        this.persona.setTipoDocumento(this.resultSet.getString("TIPO_DOCUMENTO"));
        this.persona.setNroDocumento(this.resultSet.getInt("NRO_DOCUMENTO"));
        this.persona.setRuc(this.resultSet.getInt("RUC"));
        this.persona.setUsuario(new UsuarioDaoImpl().obtenerPorId(this.resultSet.getInt("USUARIO_ID")));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.persona = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.persona);
    }

    @Override
    public Integer insertar(PersonaDto persona) {
        this.persona = persona;
        return super.insertar();
    }

    @Override
    public PersonaDto obtenerPorId(Integer personaId) {
        this.persona = new PersonaDto();
        this.persona.setPersonaId(personaId);
        super.obtenerPorId();
        return this.persona;
    }

    @Override
    public ArrayList<PersonaDto> listarTodos() {
        return (ArrayList<PersonaDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(PersonaDto persona) {
        this.persona = persona;
        return super.modificar();
    }

    @Override
    public Integer eliminar(PersonaDto persona) {
        this.persona = persona;
        return super.eliminar();
    }
}
