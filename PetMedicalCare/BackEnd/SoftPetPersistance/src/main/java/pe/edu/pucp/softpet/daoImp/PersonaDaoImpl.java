package pe.edu.pucp.softpet.daoImp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.dao.PersonaDao;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;
import pe.edu.pucp.softpet.dto.util.enums.Sexo;

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
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DIRECCION", false, false));
        this.listaColumnas.add(new Columna("TELEFONO", false, false));
        this.listaColumnas.add(new Columna("SEXO", false, false));
        this.listaColumnas.add(new Columna("NRO_DOCUMENTO", false, false));
        this.listaColumnas.add(new Columna("RUC", false, false));
        this.listaColumnas.add(new Columna("TIPO_DOCUMENTO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        this.statement.setInt(1, this.persona.getUsuario().getUsuarioId());
        this.statement.setString(2, this.persona.getNombre());
        this.statement.setString(3, this.persona.getDireccion());
        this.statement.setString(4, this.persona.getTelefono());
        this.statement.setString(5, this.persona.getSexo().toString());
        this.statement.setInt(6, this.persona.getNroDocumento());
        this.statement.setInt(7, this.persona.getRuc());
        this.statement.setString(8, this.persona.getTipoDocumento());
        this.statement.setInt(9, this.persona.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.persona.getNombre());
        this.statement.setInt(2, this.persona.getUsuario().getUsuarioId());
        this.statement.setString(3, this.persona.getDireccion());
        this.statement.setString(4, this.persona.getTelefono());
        this.statement.setString(5, this.persona.getSexo().toString());
        this.statement.setInt(6, this.persona.getNroDocumento());
        this.statement.setInt(7, this.persona.getRuc());
        this.statement.setString(8, this.persona.getTipoDocumento());
        this.statement.setInt(9, this.persona.getActivo() ? 1 : 0);

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
        this.persona.setNroDocumento(this.resultSet.getInt("NRO_DOCUMENTO"));
        
        // RUC puede ser nulo en BD, manejar con cuidado
        int rucVal = this.resultSet.getInt("RUC");
        if (this.resultSet.wasNull()) {
            this.persona.setRuc(0); // O null si tu DTO lo soporta
        } else {
            this.persona.setRuc(rucVal);
        }
        
        this.persona.setTipoDocumento(this.resultSet.getString("TIPO_DOCUMENTO"));
        
        try {
            this.persona.setActivo(this.resultSet.getInt("ACTIVO_PERS") == 1);
        } catch (Exception e) { 
             this.persona.setActivo(true); 
        }

        // Sexo Seguro
        String sexoStr = this.resultSet.getString("SEXO");
        if (sexoStr != null) {
            try { this.persona.setSexo(Sexo.valueOf(sexoStr)); } catch (Exception ex) { this.persona.setSexo(Sexo.O); }
        } else {
            this.persona.setSexo(Sexo.O);
        }

        // Usuario
        UsuarioDto u = new UsuarioDto();
        u.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        try {
            
            u.setUsername(this.resultSet.getString("USERNAME"));
            u.setCorreo(this.resultSet.getString("CORREO"));
            
        } catch (SQLException e) { /* Ignorar */ }
        this.persona.setUsuario(u);
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
    
    public Integer insertarPersonaCompleta(
            String username, String password, String correo, boolean activoUsuario,
            String nombre, String direccion, String telefono, String sexo, 
            Integer nroDocumento, Integer ruc, String tipoDocumento) {
        
        Integer resultado = 0;
        try {
            this.iniciarTransaccion();

            // A. Insertar Usuario
            String sqlUsu = "INSERT INTO USUARIOS (USERNAME, PASSWORD, CORREO, ACTIVO, FECHA_CREACION) VALUES (?, ?, ?, ?, NOW())";
            Integer idUsuario = 0;
            try (PreparedStatement psUsu = this.conexion.prepareStatement(sqlUsu, Statement.RETURN_GENERATED_KEYS)) {
                psUsu.setString(1, username);
                psUsu.setString(2, password);
                psUsu.setString(3, correo);
                psUsu.setInt(4, activoUsuario ? 1 : 0);
                psUsu.executeUpdate();
                try (ResultSet rs = psUsu.getGeneratedKeys()) { if (rs.next()) idUsuario = rs.getInt(1); }
            }

            // B. Asignar Rol (ID 4 = Cliente)
            String sqlRol = "INSERT INTO ROLES_USUARIO (ROL_ID, USUARIO_ID, ACTIVO) VALUES (4, ?, 1)";
            try(PreparedStatement psRol = this.conexion.prepareStatement(sqlRol)){
                psRol.setInt(1, idUsuario);
                psRol.executeUpdate();
            }

            // C. Insertar Persona
            String sqlPer = "INSERT INTO PERSONAS (USUARIO_ID, NOMBRE, DIRECCION, TELEFONO, SEXO, NRO_DOCUMENTO, RUC, TIPO_DOCUMENTO, ACTIVO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
            try (PreparedStatement psPer = this.conexion.prepareStatement(sqlPer, Statement.RETURN_GENERATED_KEYS)) {
                psPer.setInt(1, idUsuario);
                psPer.setString(2, nombre);
                psPer.setString(3, direccion);
                psPer.setString(4, telefono);
                psPer.setString(5, sexo);
                psPer.setInt(6, nroDocumento);
                if(ruc != null && ruc != 0) psPer.setInt(7, ruc); else psPer.setNull(7, java.sql.Types.INTEGER);
                psPer.setString(8, tipoDocumento);
                psPer.executeUpdate();
                try (ResultSet rs = psPer.getGeneratedKeys()) { if (rs.next()) resultado = rs.getInt(1); }
            }

            this.comitarTransaccion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try { this.rollbackTransaccion(); } catch (SQLException e) { }
            resultado = 0;
        } finally {
            try { this.cerrarConexion(); } catch (SQLException ex) { }
        }
        return resultado;
    }

    public ArrayList<PersonaDto> ListasBusquedaAvanzada(
            String nombre, String NroDocumento, String Ruc, Integer Activo) {
        
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        parametrosEntrada.put(1, nombre);
        parametrosEntrada.put(2, NroDocumento);
        parametrosEntrada.put(3, Ruc); // Ahora pasamos RUC en vez de teléfono
        
        // Manejo de NULL para Estado (-1 = Todos)
        if (Activo == null || Activo == -1) {
            parametrosEntrada.put(4, null);
        } else {
            parametrosEntrada.put(4, Activo);
        }

        return (ArrayList<PersonaDto>) super.ejecutarProcedimientoLectura("sp_buscar_clientes_avanzada", parametrosEntrada);
    }

    public ArrayList<PersonaDto> ListasBusquedaAvanzadaParaCliente() {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        return (ArrayList<PersonaDto>) super.ejecutarProcedimientoLectura("sp_listar_solo_clientes", parametrosEntrada);
    }

    @Override
    public ArrayList<PersonaDto> listarPersonasActivas() {

        // 1. Obtenemos el SQL base: "SELECT ..., ..., FROM PERSONAS"
        String sql = super.generarSQLParaListarTodos();

        // 2. Añadimos el filtro WHERE
        sql = sql.concat(" WHERE ACTIVO = ?");

        // 3. El parámetro es fijo: 1 (para activo)
        Object parametros = 1;

        // 4. Llamamos al método listarTodos de la clase base
        return (ArrayList<PersonaDto>) super.listarTodos(sql,
                this::incluirValorDeParametrosParaListarActivas,
                parametros);
    }

    private void incluirValorDeParametrosParaListarActivas(Object objetoParametros) {
        // Casteamos el objeto de parámetros a su tipo original
        Integer activoFlag = (Integer) objetoParametros;
        try {
            // Asignamos el '1' al primer '?' en el SQL
            this.statement.setInt(1, activoFlag);
        } catch (SQLException ex) {
            Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int VerificarSiLaPersonaTieneInformacion(int idServicio) {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        Map<Integer, Object> parametrosSalida = new HashMap<>();
        String NombreProcedure = "sp_verificar_relacion_persona";
        parametrosEntrada.put(1, idServicio);
        parametrosSalida.put(2, Types.INTEGER);
        ejecutarProcedimiento(NombreProcedure, parametrosEntrada, parametrosSalida);
        int resultado = (int) parametrosSalida.get(2);
        return resultado;
    }
}
