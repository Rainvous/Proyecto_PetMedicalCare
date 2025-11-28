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
import pe.edu.pucp.softpet.dto.usuarios.RolDto;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;
import pe.edu.pucp.softpet.dto.util.enums.Sexo;
import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;

public class PersonaDaoImpl extends DaoBaseImpl implements PersonaDao {

    private PersonaDto persona;
    private int idGuest;
    private RolDto rol;

    public PersonaDaoImpl() {
        super("PERSONAS");
        this.persona = null;
        this.retornarLlavePrimaria = true;
    }

    // -------------------------------------------------------------------------
    // MÉTODOS ABSTRACTOS DE DaoBaseImpl IMPLEMENTADOS
    // -------------------------------------------------------------------------
    @Override
    protected void configurarListaDeColumnas() {
        // EL ORDEN ES IMPORTANTE: Define la estructura para INSERT y UPDATE genéricos
        this.listaColumnas.add(new Columna("PERSONA_ID", true, true)); // PK
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
        // 1. USUARIO_ID
        if (this.persona.getUsuario() != null && this.persona.getUsuario().getUsuarioId() != 0) {
            this.statement.setInt(1, this.persona.getUsuario().getUsuarioId());
        } else {
            this.statement.setNull(1, Types.INTEGER);
        }
        // 2. NOMBRE
        this.statement.setString(2, this.persona.getNombre());
        // 3. DIRECCION
        this.statement.setString(3, this.persona.getDireccion());
        // 4. TELEFONO
        this.statement.setString(4, this.persona.getTelefono());
        // 5. SEXO
        this.statement.setString(5, this.persona.getSexo().toString());
        // 6. NRO_DOCUMENTO
        String nroDoc = String.valueOf(this.persona.getNroDocumento());
        if (this.persona.getNroDocumento() == 0 || this.persona.getNroDocumento() == null) {
            this.statement.setNull(6, java.sql.Types.VARCHAR);
        } else {

            this.statement.setString(6, nroDoc);
        }

        // 7. RUC
        if (this.persona.getRuc() != null && this.persona.getRuc() != 0) {
            this.statement.setInt(7, this.persona.getRuc());
        } else {
            this.statement.setNull(7, Types.INTEGER);
        }
        // 8. TIPO_DOCUMENTO
        this.statement.setString(8, this.persona.getTipoDocumento());
        // 9. ACTIVO
        this.statement.setInt(9, this.persona.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        // CORRECCIÓN CRÍTICA: El orden debe coincidir EXACTAMENTE con configurarListaDeColumnas
        // (saltando la llave primaria inicial, que va al final en el WHERE)

        // 1. USUARIO_ID
        if (this.persona.getUsuario() != null && this.persona.getUsuario().getUsuarioId() != 0) {
            this.statement.setInt(1, this.persona.getUsuario().getUsuarioId());
        } else {
            this.statement.setNull(1, Types.INTEGER);
        }

        // 2. NOMBRE
        this.statement.setString(2, this.persona.getNombre());

        // 3. DIRECCION
        this.statement.setString(3, this.persona.getDireccion());

        // 4. TELEFONO
        this.statement.setString(4, this.persona.getTelefono());

        // 5. SEXO
        this.statement.setString(5, this.persona.getSexo().toString());

        // 6. NRO_DOCUMENTO
        String nroDoc = String.valueOf(this.persona.getNroDocumento());
        if (this.persona.getNroDocumento() == 0 || this.persona.getNroDocumento() == null) {
            this.statement.setNull(6, java.sql.Types.VARCHAR);
        } else {

            this.statement.setString(6, nroDoc);
        }

        // 7. RUC
        if (this.persona.getRuc() != null && this.persona.getRuc() != 0) {
            this.statement.setInt(7, this.persona.getRuc());
        } else {
            this.statement.setNull(7, Types.INTEGER);
        }

        // 8. TIPO_DOCUMENTO
        this.statement.setString(8, this.persona.getTipoDocumento());

        // 9. ACTIVO
        this.statement.setInt(9, this.persona.getActivo() ? 1 : 0);

        // 10. PK (PERSONA_ID) - Para el WHERE
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
        int nroDoc = 0;
        if (this.resultSet.getString("NRO_DOCUMENTO") != null) {
            nroDoc = Integer.parseInt(this.resultSet.getString("NRO_DOCUMENTO"));
        }

        this.persona.setNroDocumento(nroDoc);

        // Manejo seguro de RUC (puede ser NULL en BD, retorna 0 en int)
        int rucVal = this.resultSet.getInt("RUC");
        if (this.resultSet.wasNull()) {
            this.persona.setRuc(0);
        } else {
            this.persona.setRuc(rucVal);
        }

        this.persona.setTipoDocumento(this.resultSet.getString("TIPO_DOCUMENTO"));

        // Manejo flexible de la columna ACTIVO (a veces se llama ACTIVO_PERS en joins)
        try {
            this.persona.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        } catch (Exception e) {
            try {
                this.persona.setActivo(this.resultSet.getInt("ACTIVO_PERS") == 1);
            } catch (Exception ex) {
                this.persona.setActivo(true);
            }
        }

        // Manejo de Enum Sexo
        String sexoStr = this.resultSet.getString("SEXO");
        if (sexoStr != null) {
            try {
                this.persona.setSexo(Sexo.valueOf(sexoStr));
            } catch (Exception ex) {
                this.persona.setSexo(Sexo.O);
            }
        } else {
            this.persona.setSexo(Sexo.O);
        }

        // Manejo de Usuario (JOIN)
        UsuarioDto u = new UsuarioDto();
        u.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));

        // Intentamos obtener username/correo para visualización en listados
        try {
            u.setUsername(this.resultSet.getString("USERNAME"));
            u.setCorreo(this.resultSet.getString("CORREO"));
        } catch (SQLException e) {
            // Si la consulta no trae estos campos, no rompemos el flujo
            u.setUsername("");
            u.setCorreo("");
        }
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

    // -------------------------------------------------------------------------
    // SOBREESCRITURA DE MÉTODOS CRUD BÁSICOS (Para asignar this.persona)
    // -------------------------------------------------------------------------
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

    // -------------------------------------------------------------------------
    // MÉTODOS TRANSACCIONALES (COMPLEJOS)
    // -------------------------------------------------------------------------
    public Integer insertarPersonaCompleta(
            String username, String password, String correo, boolean activoUsuario,
            String nombre, String direccion, String telefono, String sexo,
            Integer nroDocumento, Integer ruc, String tipoDocumento) {
        return this.insertarPersonaCompleta(username, password, correo, true, nombre, direccion, telefono, sexo, nroDocumento, ruc, tipoDocumento, 4);
    }

    public Integer insertarPersonaCompleta(
            String username, String password, String correo, boolean activoUsuario,
            String nombre, String direccion, String telefono, String sexo,
            Integer nroDocumento, Integer ruc, String tipoDocumento, int idrol) {

        Integer resultado = 0;
        try {
            this.iniciarTransaccion();

            // 1. Insertar Usuario
            String sqlUsu = "INSERT INTO USUARIOS (USERNAME, PASSWORD, CORREO, ACTIVO) VALUES (?, ?, ?, ?)";
            Integer idUsuario = 0;
            try (PreparedStatement psUsu = this.conexion.prepareStatement(sqlUsu, Statement.RETURN_GENERATED_KEYS)) {
                psUsu.setString(1, username);
                psUsu.setString(2, password);
                psUsu.setString(3, correo);
                psUsu.setInt(4, activoUsuario ? 1 : 0);
                psUsu.executeUpdate();
                try (ResultSet rs = psUsu.getGeneratedKeys()) {
                    if (rs.next()) {
                        idUsuario = rs.getInt(1);
                    }
                }
            }

            // 2. Asignar Rol (Asumimos ROL_ID = 4 para Clientes)
            if (idrol == 0) {
                String sqlRol = "INSERT INTO ROLES_USUARIO (ROL_ID, USUARIO_ID, ACTIVO) VALUES (4, ?, 1)";
                try (PreparedStatement psRol = this.conexion.prepareStatement(sqlRol)) {
                    psRol.setInt(1, idUsuario);
                    psRol.executeUpdate();
                }
            } else {
                String sqlRol = "INSERT INTO ROLES_USUARIO (ROL_ID, USUARIO_ID, ACTIVO) VALUES (" + idrol + ", ?, 1)";
                try (PreparedStatement psRol = this.conexion.prepareStatement(sqlRol)) {
                    psRol.setInt(1, idUsuario);
                    psRol.executeUpdate();
                }
            }

            // 3. Insertar Persona
            String sqlPer = "INSERT INTO PERSONAS (USUARIO_ID, NOMBRE, DIRECCION, TELEFONO, SEXO, NRO_DOCUMENTO, RUC, TIPO_DOCUMENTO, ACTIVO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
            try (PreparedStatement psPer = this.conexion.prepareStatement(sqlPer, Statement.RETURN_GENERATED_KEYS)) {
                psPer.setInt(1, idUsuario);
                psPer.setString(2, nombre);
                psPer.setString(3, direccion);
                psPer.setString(4, telefono);
                psPer.setString(5, sexo);
                psPer.setInt(6, nroDocumento);
                if (ruc != null && ruc != 0) {
                    psPer.setInt(7, ruc);
                } else {
                    psPer.setNull(7, java.sql.Types.INTEGER);
                }
                psPer.setString(8, tipoDocumento);
                psPer.executeUpdate();
                try (ResultSet rs = psPer.getGeneratedKeys()) {
                    if (rs.next()) {
                        resultado = rs.getInt(1);
                    }
                }
            }

            this.comitarTransaccion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                this.rollbackTransaccion();
            } catch (SQLException e) {
            }
            resultado = 0;
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
            }
        }
        return resultado;
    }

    public Integer modificarPersonaCompleta(
            Integer idPersona, Integer idUsuario,
            String username, String password, String correo, boolean activo,
            String nombre, String direccion, String telefono, String sexo,
            Integer nroDocumento, Integer ruc, String tipoDocumento) {

        Integer resultado = 0;
        try {
            this.iniciarTransaccion();

            // 1. Actualizar Usuario
            String sqlUsu;
            // Si password tiene contenido, se actualiza; si es null/vacío, se ignora
            if (password != null && !password.isEmpty()) {
                sqlUsu = "UPDATE USUARIOS SET USERNAME=?, CORREO=?, ACTIVO=?, PASSWORD=? WHERE USUARIO_ID=?";
            } else {
                sqlUsu = "UPDATE USUARIOS SET USERNAME=?, CORREO=?, ACTIVO=? WHERE USUARIO_ID=?";
            }

            try (PreparedStatement ps = this.conexion.prepareStatement(sqlUsu)) {
                ps.setString(1, username);
                ps.setString(2, correo);
                ps.setInt(3, activo ? 1 : 0);

                if (password != null && !password.isEmpty()) {
                    ps.setString(4, password);
                    ps.setInt(5, idUsuario);
                } else {
                    ps.setInt(4, idUsuario);
                }
                ps.executeUpdate();
            }

            // 2. Actualizar Persona (Usando la lógica genérica corregida)
            this.persona = new PersonaDto();
            this.persona.setPersonaId(idPersona);

            UsuarioDto u = new UsuarioDto();
            u.setUsuarioId(idUsuario);
            this.persona.setUsuario(u);

            this.persona.setNombre(nombre);
            this.persona.setDireccion(direccion);
            this.persona.setTelefono(telefono);

            try {
                this.persona.setSexo(Sexo.valueOf(sexo));
            } catch (Exception ex) {
                this.persona.setSexo(Sexo.O);
            }

            this.persona.setNroDocumento(nroDocumento);
            if (ruc != null) {
                this.persona.setRuc(ruc);
            } else {
                this.persona.setRuc(0);
            }
            this.persona.setTipoDocumento(tipoDocumento);
            this.persona.setActivo(activo);

            // Llamada al método del padre que usa nuestra conexión abierta
            resultado = super.modificarEnTransaccion(this.conexion);

            // resultado = 1; // Éxito
            this.comitarTransaccion();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                this.rollbackTransaccion();
            } catch (SQLException e) {
            }
            resultado = 0;
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
            }
        }
        return resultado;
    }

    public Integer eliminarPersonaCompleta(Integer idPersona) {
        Integer resultado = 0;
        try {
            this.iniciarTransaccion();

            // Obtener el Usuario ID asociado
            int idUsuario = 0;
            String sqlGet = "SELECT USUARIO_ID FROM PERSONAS WHERE PERSONA_ID = ?";
            try (PreparedStatement ps = this.conexion.prepareStatement(sqlGet)) {
                ps.setInt(1, idPersona);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idUsuario = rs.getInt(1);
                    }
                }
            }

            // Soft Delete Persona
            String sqlPer = "UPDATE PERSONAS SET ACTIVO=0 WHERE PERSONA_ID=?";
            try (PreparedStatement ps = this.conexion.prepareStatement(sqlPer)) {
                ps.setInt(1, idPersona);
                ps.executeUpdate();
            }

            // Soft Delete Usuario
            if (idUsuario > 0) {
                String sqlUsu = "UPDATE USUARIOS SET ACTIVO=0 WHERE USUARIO_ID=?";
                try (PreparedStatement ps = this.conexion.prepareStatement(sqlUsu)) {
                    ps.setInt(1, idUsuario);
                    ps.executeUpdate();
                }
            }

            resultado = 1;
            this.comitarTransaccion();
        } catch (Exception ex) {
            try {
                this.rollbackTransaccion();
            } catch (SQLException e) {
            }
            resultado = 0;
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
            }
        }
        return resultado;
    }

    // Métodos auxiliares para transacciones externas
    public Integer insertarTransaccional(PersonaDto persona, java.sql.Connection con) throws SQLException {
        this.persona = persona;
        return super.insertarEnTransaccion(con);
    }

    public Integer modificarTransaccional(PersonaDto persona, java.sql.Connection con) throws SQLException {
        this.persona = persona;
        return super.modificarEnTransaccion(con);
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE BÚSQUEDA Y CONSULTAS ESPECÍFICAS
    // -------------------------------------------------------------------------
    public ArrayList<PersonaDto> ListasBusquedaAvanzada(
            String nombre, String NroDocumento, String Ruc, Integer Activo) {

        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        parametrosEntrada.put(1, nombre);
        parametrosEntrada.put(2, NroDocumento);
        parametrosEntrada.put(3, Ruc);
        if (Activo == null || Activo == -1) {
            parametrosEntrada.put(4, null);
        } else {
            parametrosEntrada.put(4, Activo);
        }
        // Usa instanciarObjetoDelResultSet internamente, por lo que mapea Username/Correo si el SP los trae
        return (ArrayList<PersonaDto>) super.ejecutarProcedimientoLectura("sp_buscar_clientes_avanzada", parametrosEntrada);
    }

    public ArrayList<PersonaDto> ListasBusquedaAvanzadaParaCliente() {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        return (ArrayList<PersonaDto>) super.ejecutarProcedimientoLectura("sp_listar_solo_clientes", parametrosEntrada);
    }

    @Override
    public ArrayList<PersonaDto> listarPersonasActivas() {
        String sql = super.generarSQLParaListarTodos();
        sql = sql.concat(" WHERE ACTIVO = ?");
        Object parametros = 1;
        return (ArrayList<PersonaDto>) super.listarTodos(sql, this::incluirValorDeParametrosParaListarActivas, parametros);
    }

    private void incluirValorDeParametrosParaListarActivas(Object objetoParametros) {
        Integer activoFlag = (Integer) objetoParametros;
        try {
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
    // -------------------------------------------------------------------------
    // MÉTODO DE APOYO PARA COMPROBANTE (USUARIO DUMMY)
    // -------------------------------------------------------------------------

    public String generarSQLparaPersonaGuestOCero() {

        String sql;

        if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {

            // ---------- SQL SERVER (MSSQL) ----------
            sql
                    = "SELECT "
                    + "CASE "
                    + "    WHEN EXISTS ( "
                    + "        SELECT 1 "
                    + "        FROM ROLES r "
                    + "        JOIN ROLES_USUARIO ru ON r.ROL_ID = ru.ROL_ID "
                    + "        WHERE r.NOMBRE = 'GUEST' "
                    + "    ) THEN ( "
                    + "        SELECT TOP 1 p.PERSONA_ID "
                    + "        FROM PERSONAS p "
                    + "        JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID "
                    + "        JOIN ROLES_USUARIO ru ON u.USUARIO_ID = ru.USUARIO_ID "
                    + "        JOIN ROLES r ON ru.ROL_ID = r.ROL_ID "
                    + "        WHERE r.NOMBRE = 'GUEST' "
                    + "    ) "
                    + "    ELSE 0 "
                    + "END AS RESULTADO";

        } else {

            // ---------- MYSQL ----------
            sql
                    = "SELECT "
                    + "    IF( "
                    + "        EXISTS ( "
                    + "            SELECT 1 "
                    + "            FROM ROLES r "
                    + "            JOIN ROLES_USUARIO ru ON r.ROL_ID = ru.ROL_ID "
                    + "            WHERE r.NOMBRE = 'GUEST' "
                    + "        ), "
                    + "        (SELECT p.PERSONA_ID "
                    + "         FROM PERSONAS p "
                    + "         JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID "
                    + "         JOIN ROLES_USUARIO ru ON u.USUARIO_ID = ru.USUARIO_ID "
                    + "         JOIN ROLES r ON ru.ROL_ID = r.ROL_ID "
                    + "         WHERE r.NOMBRE = 'GUEST' "
                    + "         LIMIT 1), "
                    + "        0 "
                    + "    ) AS RESULTADO";

        }

        return sql;
    }

    private void instanciarParaGuest() throws SQLException {
        this.idGuest = this.resultSet.getInt("RESULTADO");
    }

    public void AgregarMiPropioObjetoALaLista(Object listavoid) {
        List<Integer> lista = (List<Integer>) listavoid;
        try {
            this.instanciarParaGuest();
        } catch (SQLException ex) {
            Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        lista.add(this.idGuest);
    }

    public int obtenerPersonaGuestOCero() {

        String sql = generarSQLparaPersonaGuestOCero();

        // super.listarTodos devolverá una List<Integer>
        List<Integer> lista = (List<Integer>) super.listarTodos(
                sql,
                null, // no hace nada
                null,
                this::AgregarMiPropioObjetoALaLista
        );

        // Como la consulta devuelve solo una fila, tomamos el primero
        if (lista == null || lista.isEmpty()) {
            // en teoría no debería pasar, pero por seguridad:
            return 0;
        }

        return lista.get(0);
    }

    public String generarSQLparaPersonaPorId() {
        String sql
                = "SELECT "
                + " p.PERSONA_ID, "
                + " p.USUARIO_ID, "
                + " p.NOMBRE, "
                + " p.DIRECCION, "
                + " p.TELEFONO, "
                + " p.SEXO, "
                + " p.NRO_DOCUMENTO, "
                + " p.RUC, "
                + " p.TIPO_DOCUMENTO, "
                + " p.ACTIVO AS PERSONA_ACTIVO, "
                + " u.USERNAME, "
                + " u.CORREO, "
                + " u.PASSWORD, "
                + " u.ACTIVO AS USUARIO_ACTIVO "
                + "FROM PERSONAS p "
                + "INNER JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID "
                + "WHERE p.PERSONA_ID = ?;";
        return sql;
    }

    public void IncluirEnSQLPersonaId(Object personaIdVoid) {
        int personaId = (int) personaIdVoid;
        try {
            this.statement.setInt(1, personaId);
        } catch (SQLException ex) {
            Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AgregarMiPropioObjetoALaLista2(Object listaVoid) {
        List<PersonaDto> lista = (List<PersonaDto>) listaVoid;
        try {
            this.instanciarObjetoDelResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        lista.add(this.persona);
    }

    public PersonaDto obtenerPersonaPorIdCompleto(int personaId) {

        String sql = generarSQLparaPersonaPorId();

        List<PersonaDto> lista = (List<PersonaDto>) super.listarTodos(
                sql,
                this::IncluirEnSQLPersonaId,
                personaId,
                this::AgregarMiPropioObjetoALaLista2
        );

        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    // PAGINACION
    public String generarSQLparaBusquedaClientesAvanzada() {
        // Definimos la consulta base común para ambos motores
        String sqlBase = "SELECT "
                + "    p.PERSONA_ID, "
                + "    p.NOMBRE, "
                + "    p.DIRECCION, "
                + "    p.TELEFONO, "
                + "    p.SEXO, "
                + "    p.NRO_DOCUMENTO, "
                + "    p.RUC, "
                + "    p.TIPO_DOCUMENTO, "
                + "    p.ACTIVO, "
                + "    u.USUARIO_ID, "
                + "    u.USERNAME, "
                + "    u.CORREO "
                + "FROM PERSONAS p "
                + "INNER JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID "
                + "WHERE "
                + "    (? IS NULL OR p.NOMBRE LIKE CONCAT('%', ?, '%')) " // 1, 2: Nombre
                + "    AND (? IS NULL OR p.NRO_DOCUMENTO LIKE CONCAT('%', ?, '%')) " // 3, 4: DNI
                + "    AND (? IS NULL OR p.RUC LIKE CONCAT('%', ?, '%')) " // 5, 6: RUC
                + "    AND (? IS NULL OR p.ACTIVO = ?) " // 7, 8: Activo
                // Lógica para EXCLUIR empleados (Admin, Vet, Recepcion) - Solo Clientes
                + "    AND NOT EXISTS ( "
                + "        SELECT 1 "
                + "        FROM ROLES_USUARIO RU "
                + "        JOIN ROLES R ON RU.ROL_ID = R.ROL_ID "
                + "        WHERE RU.USUARIO_ID = p.USUARIO_ID "
                + "        AND R.NOMBRE IN ('ADMIN', 'VET', 'RECEPCION') "
                + "    ) "
                + "ORDER BY p.NOMBRE ASC ";

        // Agregamos la paginación según el motor
        if (this.tipoMotor == MotorDeBaseDeDatos.MYSQL) {
            // MySQL: LIMIT [cantidad] OFFSET [salto]
            return sqlBase + "LIMIT ? OFFSET ?;";

        } else if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {
            // SQL Server: OFFSET [salto] ... FETCH NEXT [cantidad] ...
            return sqlBase + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        }

        return sqlBase;
    }

    // 2. Inyección de Parámetros
    public void IncluirEnSQLBusquedaClientes(Object parametrosVoid) {
        Map<String, Object> params = (Map<String, Object>) parametrosVoid;

        String nombre = (String) params.get("nombre");
        String nroDoc = (String) params.get("nroDoc");
        String ruc = (String) params.get("ruc");
        Boolean activo = (Boolean) params.get("activo");
        Integer pagina = (Integer) params.get("pagina");

        int limit = 8; // Tamaño de página
        int offset = (pagina - 1) * limit;

        try {
            int i = 1;

            // --- Filtro Nombre ---
            this.statement.setString(i++, nombre);
            this.statement.setString(i++, nombre);

            // --- Filtro Nro Documento ---
            this.statement.setString(i++, nroDoc);
            this.statement.setString(i++, nroDoc);

            // --- Filtro RUC ---
            this.statement.setString(i++, ruc);
            this.statement.setString(i++, ruc);

            // --- Filtro Activo ---
            if (activo == null) {
                this.statement.setObject(i++, null);
                this.statement.setObject(i++, null);
            } else {
                int valorBit = activo ? 1 : 0;
                this.statement.setInt(i++, valorBit);
                this.statement.setInt(i++, valorBit);
            }

            // --- Paginación (Adaptada al Motor) ---
            if (this.tipoMotor == MotorDeBaseDeDatos.MYSQL) {
                // MySQL espera: LIMIT (cantidad), OFFSET (salto)
                this.statement.setInt(i++, limit);
                this.statement.setInt(i++, offset);

            } else if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {
                // SQL Server espera: OFFSET (salto) ... FETCH NEXT (cantidad)
                this.statement.setInt(i++, offset); // Primero el salto
                this.statement.setInt(i++, limit);  // Luego la cantidad
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // 3. Método de Mapeo
    public void AgregarClientePaginadoALaLista(Object listaVoid) {
        List<PersonaDto> lista = (List<PersonaDto>) listaVoid;
        try {
            // Reutilizamos tu método robusto que ya maneja NULOS, ENUMS y USUARIOS
            this.instanciarObjetoDelResultSet();
            lista.add(this.persona);
        } catch (SQLException ex) {
            Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // 4. Método Público Orquestador (Este llamarás desde el BO)
    public List<PersonaDto> buscarClientesPaginados(String nombre, String nroDoc, String ruc, Boolean activo, int pagina) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", nombre);
        parametros.put("nroDoc", nroDoc);
        parametros.put("ruc", ruc);
        parametros.put("activo", activo);
        parametros.put("pagina", pagina);

        String sql = generarSQLparaBusquedaClientesAvanzada();

        return (List<PersonaDto>) super.listarTodos(
                sql,
                this::IncluirEnSQLBusquedaClientes,
                parametros,
                this::AgregarClientePaginadoALaLista
        );
    }

    //OBTENER ROL DE PERSONA
    public String generarSQLparaObtenerRolPorPersonaId() {
        String sql = "SELECT ";

        // MSSQL usa TOP al inicio
        if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {
            sql += "TOP 1 ";
        }

        sql += "    r.ROL_ID, "
                + "    r.NOMBRE, "
                + "    r.ACTIVO "
                + "FROM PERSONAS p "
                + "INNER JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID "
                + "INNER JOIN ROLES_USUARIO ru ON u.USUARIO_ID = ru.USUARIO_ID "
                + "INNER JOIN ROLES r ON ru.ROL_ID = r.ROL_ID "
                + "WHERE p.PERSONA_ID = ? ";

        // MySQL usa LIMIT al final
        if (this.tipoMotor == MotorDeBaseDeDatos.MYSQL) {
            sql += "LIMIT 1";
        }

        return sql + ";";
    }

    // 2. Inyección de Parámetros
    public void IncluirEnSQLRolPorPersonaId(Object parametroId) {
        Integer personaId = (Integer) parametroId;
        try {
            // Solo tenemos un parámetro: el ID de la persona
            this.statement.setInt(1, personaId);
        } catch (SQLException ex) {
            Logger.getLogger(RolDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // 3. Mapeo de Resultados (ResultSet -> RolDto)
    public void AgregarRolALaLista(Object listaVoid) {
        List<RolDto> lista = (List<RolDto>) listaVoid;
        try {
            this.rol = new RolDto();

            // Mapeamos los campos directos de la tabla ROLES
            this.rol.setRolId(this.resultSet.getInt("ROL_ID"));
            this.rol.setNombre(this.resultSet.getString("NOMBRE"));

            // Manejo seguro del booleano (tinyint en BD)
            // Asumiendo que en tu BD 1 es true y 0 es false
            this.rol.setActivo(this.resultSet.getInt("ACTIVO") == 1);

            lista.add(this.rol);

        } catch (SQLException ex) {
            Logger.getLogger(RolDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // 4. Método Público Orquestador
    public RolDto obtenerRolPorPersonaId(int personaId) {

        String sql = generarSQLparaObtenerRolPorPersonaId();

        // Usamos listarTodos para aprovechar la infraestructura, aunque solo venga 1
        List<RolDto> lista = (List<RolDto>) super.listarTodos(
                sql,
                this::IncluirEnSQLRolPorPersonaId,
                personaId, // Pasamos el Integer directamente
                this::AgregarRolALaLista
        );

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0); // Devolvemos el primer rol encontrado
        }

        return null; // Si no tiene rol o no existe la persona
    }

    // 1. Generar SQL
    public String generarSQLparaObtenerCorreoPorPersonaId() {
        String sql = "SELECT ";

        if (this.tipoMotor == MotorDeBaseDeDatos.MSSQL) {
            sql += "TOP 1 ";
        }

        sql += "u.CORREO "
                + "FROM PERSONAS p "
                + "INNER JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID "
                + "WHERE p.PERSONA_ID = ? ";

        if (this.tipoMotor == MotorDeBaseDeDatos.MYSQL) {
            sql += "LIMIT 1";
        }
        return sql + ";";
    }

    // 2. Inyección de Parámetros (Podríamos reusar el anterior, pero creamos uno propio por orden)
    public void IncluirEnSQLCorreoPorPersonaId(Object parametroId) {
        Integer personaId = (Integer) parametroId;
        try {
            this.statement.setInt(1, personaId);
        } catch (SQLException ex) {
            Logger.getLogger(RolDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // 3. Mapeo de Resultados (ResultSet -> String)
    public void AgregarCorreoALaLista(Object listaVoid) {
        List<String> lista = (List<String>) listaVoid;
        try {
            // Obtenemos directamente la columna CORREO
            String correo = this.resultSet.getString("CORREO");
            lista.add(correo);
        } catch (SQLException ex) {
            Logger.getLogger(RolDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // 4. Método Público Orquestador
    public String obtenerCorreoPorPersonaId(int personaId) {

        String sql = generarSQLparaObtenerCorreoPorPersonaId();

        // Notar que casteamos a List<String>
        List<String> lista = (List<String>) super.listarTodos(
                sql,
                this::IncluirEnSQLCorreoPorPersonaId,
                personaId,
                this::AgregarCorreoALaLista
        );

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null; // No se encontró correo
    }

}
