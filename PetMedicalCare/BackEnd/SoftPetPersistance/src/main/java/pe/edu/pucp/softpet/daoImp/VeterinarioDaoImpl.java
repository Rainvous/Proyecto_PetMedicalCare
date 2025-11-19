package pe.edu.pucp.softpet.daoImp;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.dao.VeterinarioDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoVeterinario;

public class VeterinarioDaoImpl extends DaoBaseImpl implements VeterinarioDao {

    private VeterinarioDto veterinario;

    public VeterinarioDaoImpl() {
        super("VETERINARIOS");
        this.veterinario = null;
        this.retornarLlavePrimaria = true;
        // FIX CRÍTICO: Definir usuario para auditoría (Triggers de BD)
        this.usuario = "user_backend"; 
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("VETERINARIO_ID", true, true));
        this.listaColumnas.add(new Columna("PERSONA_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_DE_CONTRATACION", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("ESPECIALIDAD", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.veterinario.getPersona().getPersonaId());
        
        // Manejo seguro de Fecha
        java.util.Date fechaUtil = this.veterinario.getFechaContratacion();
        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
        this.statement.setDate(2, fechaSql);
        
        this.statement.setString(3, this.veterinario.getEstado().toString());
        this.statement.setString(4, this.veterinario.getEspecialidad());
        this.statement.setInt(5, this.veterinario.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.veterinario.getPersona().getPersonaId());
        
        // Manejo seguro de Fecha
        java.util.Date fechaUtil = this.veterinario.getFechaContratacion();
        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
        this.statement.setDate(2, fechaSql);
        
        this.statement.setString(3, this.veterinario.getEstado().toString());
        this.statement.setString(4, this.veterinario.getEspecialidad());
        this.statement.setInt(5, this.veterinario.getActivo() ? 1 : 0);

        this.statement.setInt(6, this.veterinario.getVeterinarioId());
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
        
        PersonaDto persona = new PersonaDto();
        persona.setPersonaId(this.resultSet.getInt("PERSONA_ID"));
        this.veterinario.setPersona(persona);
        
        this.veterinario.setFechaContratacion(this.resultSet.getDate("FECHA_DE_CONTRATACION"));
        this.veterinario.setEstado(EstadoVeterinario.valueOf(this.resultSet.getString("ESTADO")));
        this.veterinario.setEspecialidad(this.resultSet.getString("ESPECIALIDAD"));
        this.veterinario.setActivo(this.resultSet.getInt("ACTIVO") == 1);
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
    public VeterinarioDto obtenerPorId(Integer veterinarioId) {
        this.veterinario = new VeterinarioDto();
        this.veterinario.setVeterinarioId(veterinarioId);
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
    
    @Override
    public ArrayList<VeterinarioDto> listarVeterinariosActivos() {
        String sql = super.generarSQLParaListarTodos();
        sql = sql.concat(" WHERE ACTIVO = ?");
        Object parametros = 1;
        return (ArrayList<VeterinarioDto>) super.listarTodos(sql, 
                this::incluirValorDeParametrosParaListarActivos, 
                parametros);
    }

    private void incluirValorDeParametrosParaListarActivos(Object objetoParametros) {
        Integer activoFlag = (Integer) objetoParametros;
        try {            
            this.statement.setInt(1, activoFlag);
        } catch (SQLException ex) {
            Logger.getLogger(VeterinarioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int VerificarSiExisteHorarioLaboral(Date fecha,Integer idVeterinario ) {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        Map<Integer, Object> parametrosSalida = new HashMap<>();
        String NombreProcedure = "sp_verificar_horario_laboral_existente";
        parametrosEntrada.put(1, fecha);
        parametrosEntrada.put(2, idVeterinario);
        parametrosSalida.put(3, Types.INTEGER);
        ejecutarProcedimiento(NombreProcedure, parametrosEntrada, parametrosSalida);
        int resultado = (int) parametrosSalida.get(3);
        return resultado;
    }
}