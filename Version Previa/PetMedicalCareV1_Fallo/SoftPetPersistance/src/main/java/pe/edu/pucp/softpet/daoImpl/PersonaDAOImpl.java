
package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.softpet.dao.PersonaDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.TipoSexo;
import pe.edu.pucp.softpet.model.actoresdto.PersonasDTO;

public class PersonaDAOImpl extends DAOImplBase implements PersonaDAO {

    private PersonasDTO persona;

    public PersonaDAOImpl() {
        super("PERSONA"); // nombre de tabla en BD
        this.persona = null;
    }

    public PersonaDAOImpl(String nombre_tabla) {
        super(nombre_tabla);
        this.persona = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        // Definir columnas y metadatos (PK, autogenerado)
        // Orden aquí importa: se ocupará para generar SQL y parámetros.
        this.listaColumnas.add(new Columna("PERSONA_ID", true, true));  // PK, autogenerado
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DIRECCION", false, false));
        this.listaColumnas.add(new Columna("CORREO", false, false));
        this.listaColumnas.add(new Columna("TELEFONO", false, false));
        this.listaColumnas.add(new Columna("SEXO", false, false));
        this.listaColumnas.add(new Columna("TIPO_PERSONA", false, false));
        this.listaColumnas.add(new Columna("TIPO_DOCUMENTO", false, false));
        this.listaColumnas.add(new Columna("NRO_DOCUMENTO", false, false));
    }

    // ===== PersonaDAO =====
    @Override
    public Integer insertar(PersonasDTO persona) {
        this.persona = persona;
        // Si quieres que retorne el ID autogenerado:
        this.retornarLlavePrimaria = true;
        return super.insertar();
    }

    @Override
    public PersonasDTO obtenerPorId(Integer personaId) {
        // Preparar “contexto” para que los hooks sepan qué ID poner en el WHERE
        this.persona = new PersonasDTO();
        this.persona.setPersonaId(personaId);

        super.obtenerPorId(); // DAOImplBase ejecuta SELECT ... WHERE PK=?, y luego llama a instanciarObjetoDelResultSet()

        return this.persona; // queda seteada en instanciarObjetoDelResultSet()
    }

    @Override
    public ArrayList<PersonasDTO> listarTodos() {
        List lista = super.listarTodos(); // DAOImplBase iterará el ResultSet y llamará a agregarObjetoALaLista(lista)
        ArrayList<PersonasDTO> resultado = new ArrayList<>();
        for (Object o : lista) {
            resultado.add((PersonasDTO) o);
        }
        return resultado;
    }

    @Override
    public Integer modificar(PersonasDTO persona) {
        this.persona = persona;
        return super.modificar();
    }

    @Override
    public Integer eliminar(PersonasDTO persona) {
        this.persona = persona;
        return super.eliminar();
    }

    // ===== Hooks llamados por DAOImplBase =====
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // Orden de parámetros = orden de columnas NO autogeneradas:
        // NOMBRE, DIRECCION, CORREO, TELEFONO, SEXO, TIPO_PERSONA, TIPO_DOCUMENTO, NRO_DOCUMENTO
        this.statement.setString(1, this.persona.getNombre());
        this.statement.setString(2, this.persona.getDireccion());
        this.statement.setString(3, this.persona.getCorreo());
        this.statement.setString(4, this.persona.getTelefono());

        // Si tu columna SEXO es VARCHAR:
        System.out.println("hola detalle de sexo: "+this.persona.getSexo().name());
        this.statement.setString(5, this.persona.getSexo() != null ? this.persona.getSexo().name() : null);
        // Si en tu BD SEXO es tinyint/number, cambia a:
        // this.statement.setInt(5, this.persona.getSexo() == TipoSexo.FEMENINO ? 1 : 0);

        this.statement.setString(6, this.persona.getTipoPersona());
        this.statement.setString(7, this.persona.getTipoDocumento());
        this.statement.setString(8, this.persona.getNroDocumento());
        System.out.println("STatement: "+this.statement);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        // UPDATE PERSONA SET (todas NO PK) WHERE (PK)
        // Mismo orden que inserción, y al final el PK:
        this.statement.setString(1, this.persona.getNombre());
        this.statement.setString(2, this.persona.getDireccion());
        this.statement.setString(3, this.persona.getCorreo());
        this.statement.setString(4, this.persona.getTelefono());
        this.statement.setString(5, this.persona.getSexo() != null ? this.persona.getSexo().name() : null);
        this.statement.setString(6, this.persona.getTipoPersona());
        this.statement.setString(7, this.persona.getTipoDocumento());
        this.statement.setString(8, this.persona.getNroDocumento());

        // WHERE PERSONA_ID = ?
        this.statement.setInt(9, this.persona.getPersonaId());
    }

    //Hay que cambiar la pos de personId, de 1 a 9
    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        // DELETE ... WHERE PERSONA_ID = ?
        this.statement.setInt(1, this.persona.getPersonaId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        // SELECT ... WHERE PERSONA_ID = ?
        this.statement.setInt(1, this.persona.getPersonaId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        PersonasDTO p = new PersonasDTO();
        p.setPersonaId(this.resultSet.getInt("PERSONA_ID"));
        p.setNombre(this.resultSet.getString("NOMBRE"));
        p.setDireccion(this.resultSet.getString("DIRECCION"));
        p.setCorreo(this.resultSet.getString("CORREO"));
        p.setTelefono(this.resultSet.getString("TELEFONO"));

        // SEXO: si la BD guarda texto (FEMENINO/MASCULINO):
        String sexoStr = this.resultSet.getString("SEXO");
        if (sexoStr != null) {
            try {
                p.setSexo(TipoSexo.valueOf(sexoStr));
            } catch (IllegalArgumentException ex) {
                // Si guardan “F/M” u otros valores, aquí puedes mapear
                if ("F".equalsIgnoreCase(sexoStr)) {
                    p.setSexo(TipoSexo.FEMENINO);
                } else if ("M".equalsIgnoreCase(sexoStr)) {
                    p.setSexo(TipoSexo.MASCULINO);
                } else {
                    p.setSexo(null);
                }
            }
        }

        p.setTipoPersona(this.resultSet.getString("TIPO_PERSONA"));
        p.setTipoDocumento(this.resultSet.getString("TIPO_DOCUMENTO"));
        p.setNroDocumento(this.resultSet.getString("NRO_DOCUMENTO"));

        this.persona = p; // importante: `obtenerPorId` devolverá `this.persona`
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        PersonasDTO p = new PersonasDTO();
        p.setPersonaId(this.resultSet.getInt("PERSONA_ID"));
        p.setNombre(this.resultSet.getString("NOMBRE"));
        p.setDireccion(this.resultSet.getString("DIRECCION"));
        p.setCorreo(this.resultSet.getString("CORREO"));
        p.setTelefono(this.resultSet.getString("TELEFONO"));

        String sexoStr = this.resultSet.getString("SEXO");
        if (sexoStr != null) {
            try {
                p.setSexo(TipoSexo.valueOf(sexoStr));
            } catch (IllegalArgumentException ex) {
                if ("F".equalsIgnoreCase(sexoStr)) {
                    p.setSexo(TipoSexo.FEMENINO);
                } else if ("M".equalsIgnoreCase(sexoStr)) {
                    p.setSexo(TipoSexo.MASCULINO);
                } else {
                    p.setSexo(null);
                }
            }
        }

        p.setTipoPersona(this.resultSet.getString("TIPO_PERSONA"));
        p.setTipoDocumento(this.resultSet.getString("TIPO_DOCUMENTO"));
        p.setNroDocumento(this.resultSet.getString("NRO_DOCUMENTO"));

        lista.add(p);
    }
}
