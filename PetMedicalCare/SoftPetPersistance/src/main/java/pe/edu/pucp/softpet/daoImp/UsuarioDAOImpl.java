package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;
import pe.edu.pucp.softpet.dao.UsuariosDao;

public class UsuarioDaoImpl extends DAOImplBase implements UsuariosDao {

    private UsuarioDto usuarios;

    public UsuarioDaoImpl() {
        super("USUARIOS"); // nombre de tabla en BD
        this.usuarios = null;
    }

    public UsuarioDaoImpl(String nombre_tabla) {
        super(nombre_tabla);
        this.usuarios = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        // Definir columnas y metadatos (PK, autogenerado)
        // Orden aquí importa: se ocupará para generar SQL y parámetros.
        this.listaColumnas.add(new Columna("USUARIO_ID", true, true));  // PK, autogenerado
        this.listaColumnas.add(new Columna("USENAME", false, false));
        this.listaColumnas.add(new Columna("PASSWORD", false, false));
        this.listaColumnas.add(new Columna("CORREO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("FECHA_MODIFICACION", false, false));
        this.listaColumnas.add(new Columna("USUARIO_MODIFICADOR", false, false));
        this.listaColumnas.add(new Columna("USUARIO_CREADOR", false, false));
        this.listaColumnas.add(new Columna("FECHA_CREACION", false, false));
    }

    // ===== PersonaDAO =====
    @Override
    public Integer insertar(UsuarioDto usuarios) {
        this.usuarios = usuarios;
        // Si quieres que retorne el ID autogenerado:
        this.retornarLlavePrimaria = true;
        return super.insertar();
    }

    @Override
    public UsuarioDto obtenerPorId(Integer usuarioId) {
        // Preparar “contexto” para que los hooks sepan qué ID poner en el WHERE
        this.usuarios = new UsuarioDto();
        this.usuarios.setUsuarioId(usuarioId);

        super.obtenerPorId(); // DAOImplBase ejecuta SELECT ... WHERE PK=?, y luego llama a instanciarObjetoDelResultSet()

        return this.usuarios; // queda seteada en instanciarObjetoDelResultSet()
    }

    @Override
    public ArrayList<UsuarioDto> listarTodos() {
        List lista = super.listarTodos(); // DAOImplBase iterará el ResultSet y llamará a agregarObjetoALaLista(lista)
        ArrayList<UsuarioDto> resultado = new ArrayList<>();
        for (Object o : lista) {
            resultado.add((UsuarioDto) o);
        }
        return resultado;
    }

    @Override
    public Integer modificar(UsuarioDto usuarios) {
        this.usuarios = usuarios;
        return super.modificar();
    }

    @Override
    public Integer eliminar(UsuarioDto usuarios) {
        this.usuarios = usuarios;
        return super.eliminar();
    }

    // ===== Hooks llamados por DAOImplBase =====
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // Orden de parámetros = orden de columnas NO autogeneradas:
        // NOMBRE, DIRECCION, CORREO, TELEFONO, SEXO, TIPO_PERSONA, TIPO_DOCUMENTO, NRO_DOCUMENTO
        this.statement.setString(1, this.usuarios.getUsername());
        this.statement.setString(2, this.usuarios.getPassword());
        this.statement.setInt(3, this.usuarios.getActivo() ? 1 : 0);
        this.statement.setDate(4, this.usuarios.getFechaModificacion());
        this.statement.setString(5, this.usuarios.getUsuarioModificador());

        this.statement.setString(6, this.usuarios.getUsuarioCreador());
        this.statement.setDate(7, this.usuarios.getFechaCreacion());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        // UPDATE PERSONA SET (todas NO PK) WHERE (PK)
        // Mismo orden que inserción, y al final el PK:
        this.statement.setString(1, this.usuarios.getUsername());
        this.statement.setString(2, this.usuarios.getPassword());
        this.statement.setInt(3, this.usuarios.getActivo() ? 1 : 0);
        this.statement.setDate(4, this.usuarios.getFechaModificacion());
        this.statement.setString(5, this.usuarios.getUsuarioModificador());

        this.statement.setString(6, this.usuarios.getUsuarioCreador());
        this.statement.setDate(7, this.usuarios.getFechaCreacion());

        // WHERE PERSONA_ID = ?
        this.statement.setInt(8, this.usuarios.getUsuarioId());
    }

    //Hay que cambiar la pos de personId, de 1 a 9
    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        // DELETE ... WHERE PERSONA_ID = ?
        this.statement.setInt(1, this.usuarios.getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        // SELECT ... WHERE PERSONA_ID = ?
        this.statement.setInt(1, this.usuarios.getUsuarioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        UsuarioDto p = new UsuarioDto();
        p.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        p.setUsername(this.resultSet.getString("USERNAME"));
        p.setPassword(this.resultSet.getString("PASSWORD"));
        p.setCorreo(this.resultSet.getString("CORREO"));
        p.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        p.setFechaModificacion(this.resultSet.getDate("FECHA_MODIFICACION"));

        p.setUsuarioModificador(this.resultSet.getString("USUARIO_MODIFICADOR"));
        p.setUsuarioCreador(this.resultSet.getString("FECHA_CREACION"));
        p.setFechaCreacion(this.resultSet.getDate("FECHA_CREACION"));
        this.usuarios = p; // importante: `obtenerPorId` devolverá `this.persona`
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        UsuarioDto p = new UsuarioDto();
        p.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        p.setUsername(this.resultSet.getString("USERNAME"));
        p.setPassword(this.resultSet.getString("PASSWORD"));
        p.setCorreo(this.resultSet.getString("CORREO"));
        p.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        p.setFechaModificacion(this.resultSet.getDate("FECHA_MODIFICACION"));

        p.setUsuarioModificador(this.resultSet.getString("USUARIO_MODIFICADOR"));
        p.setUsuarioCreador(this.resultSet.getString("FECHA_CREACION"));
        p.setFechaCreacion(this.resultSet.getDate("FECHA_CREACION"));

        lista.add(p);
    }
}
