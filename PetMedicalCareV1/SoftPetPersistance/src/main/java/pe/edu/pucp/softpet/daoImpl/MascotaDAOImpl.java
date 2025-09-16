/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.softpet.dao.MascotaDAO;
import pe.edu.pucp.softpet.model.actoresdto.MascotasDTO;

import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.dbmanager.db.DBManager;


public class MascotaDAOImpl extends DAOImplBase implements MascotaDAO{

    private MascotasDTO mascota;

    public MascotaDAOImpl() {
        super("MASCOTA"); // nombre de tabla en BD
        this.mascota = null;
    }

    public MascotaDAOImpl(String nombre_tabla) {
        super(nombre_tabla);
        this.mascota = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        // Definir columnas y metadatos (PK, autogenerado)
        // Orden aquí importa: se ocupará para generar SQL y parámetros.
        this.listaColumnas.add(new Columna("MASCOTA_ID", true, true));  // PK, autogenerado
        this.listaColumnas.add(new Columna("PERSONA_ID", false, false));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("RAZA", false, false));
        this.listaColumnas.add(new Columna("COLOR", false, false));
    }

    // ===== PersonaDAO =====
    @Override
    public Integer insertar(MascotasDTO mascota) {
        this.mascota = mascota;
        // Si quieres que retorne el ID autogenerado:
        this.retornarLlavePrimaria = true;
        return super.insertar();
    }

    @Override
    public MascotasDTO obtenerPorId(Integer mascotaId) {
        // Preparar “contexto” para que los hooks sepan qué ID poner en el WHERE
        this.mascota = new MascotasDTO();
        this.mascota.setMascotaId(mascotaId);

        super.obtenerPorId(); // DAOImplBase ejecuta SELECT ... WHERE PK=?, y luego llama a instanciarObjetoDelResultSet()

        return this.mascota; // queda seteada en instanciarObjetoDelResultSet()
    }

    @Override
    public ArrayList<MascotasDTO> listarTodos() {
        List lista = super.listarTodos(); // DAOImplBase iterará el ResultSet y llamará a agregarObjetoALaLista(lista)
        ArrayList<MascotasDTO> resultado = new ArrayList<>();
        for (Object o : lista) {
            resultado.add((MascotasDTO) o);
        }
        return resultado;
    }

    @Override
    public Integer modificar(MascotasDTO mascota) {
        this.mascota = mascota;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MascotasDTO mascota) {
        this.mascota = mascota;
        return super.eliminar();
    }

    // ===== Hooks llamados por DAOImplBase =====
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // Orden de parámetros = orden de columnas NO autogeneradas:
        // NOMBRE, DIRECCION, CORREO, TELEFONO, SEXO, TIPO_PERSONA, TIPO_DOCUMENTO, NRO_DOCUMENTO
        this.statement.setObject(1, this.mascota.getPersona());
        this.statement.setString(2, this.mascota.getNombre());
        this.statement.setString(3, this.mascota.getRaza());
        this.statement.setString(4, this.mascota.getColor());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        // UPDATE PERSONA SET (todas NO PK) WHERE (PK)
        // Mismo orden que inserción, y al final el PK:
        this.statement.setObject(1, this.mascota.getPersona());
        this.statement.setString(2, this.mascota.getNombre());
        this.statement.setString(3, this.mascota.getRaza());
        this.statement.setString(4, this.mascota.getColor());
        // WHERE PERSONA_ID = ?
        this.statement.setInt(5, this.mascota.getMascotaId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        // DELETE ... WHERE PERSONA_ID = ?
        this.statement.setInt(5, this.mascota.getMascotaId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        // SELECT ... WHERE PERSONA_ID = ?
        this.statement.setInt(5, this.mascota.getMascotaId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        MascotasDTO m = new MascotasDTO();
        m.setMascotaId(this.resultSet.getInt("MASCOTA_ID"));
        m.setPersona(this.resultSet.getObject("PERSONA_ID"));
        m.setNombre(this.resultSet.getString("NOMBRE"));
        m.setRaza(this.resultSet.getString("RAZA"));
        m.setColor(this.resultSet.getString("COLOR"));
        this.mascota = m; // importante: `obtenerPorId` devolverá `this.persona`
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        PersonasDTO p = new PersonasDTO();
        p.setPersonaId(this.resultSet.getInt("PERSONA_ID"));
        p.setNombre(this.resultSet.getString("NOMBRE"));
        p.setDireccion(this.resultSet.getString("DIRECCION"));
        p.setCorreo(this.resultSet.getString("CORREO"));
        p.setTelefono(this.resultSet.getString("TELEFONO"));

        p.setTipoPersona(this.resultSet.getString("TIPO_PERSONA"));
        p.setTipoDocumento(this.resultSet.getString("TIPO_DOCUMENTO"));
        p.setNroDocumento(this.resultSet.getString("NRO_DOCUMENTO"));

        lista.add(p);
    }

    @Override
    public Integer eliminar(MascotaDAO mascota) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
