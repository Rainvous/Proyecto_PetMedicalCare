package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.dao.RecetaMedicaDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

public class RecetaMedicaDaoImpl extends DaoBaseImpl implements RecetaMedicaDao {

    private RecetaMedicaDto recetaMedica;

    public RecetaMedicaDaoImpl() {
        super("RECETAS_MEDICAS");
        this.recetaMedica = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("RECETA_MEDICA_ID", true, true));
        this.listaColumnas.add(new Columna("CITA_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_EMISION", false, false));
        this.listaColumnas.add(new Columna("VIGENCIA_HASTA", false, false));
        this.listaColumnas.add(new Columna("DIAGNOSTICO", false, false));
        this.listaColumnas.add(new Columna("OBSERVACIONES", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.recetaMedica.getCita().getCitaId());
        this.statement.setDate(2, this.recetaMedica.getFechaEmision());
        this.statement.setDate(3, this.recetaMedica.getVigenciaHasta());
        this.statement.setString(4, this.recetaMedica.getDiagnostico());
        this.statement.setString(5, this.recetaMedica.getObservaciones());
        this.statement.setInt(6, this.recetaMedica.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.recetaMedica.getCita().getCitaId());
        this.statement.setDate(2, this.recetaMedica.getFechaEmision());
        this.statement.setDate(3, this.recetaMedica.getVigenciaHasta());
        this.statement.setString(4, this.recetaMedica.getDiagnostico());
        this.statement.setString(5, this.recetaMedica.getObservaciones());
        this.statement.setInt(6, this.recetaMedica.getActivo() ? 1 : 0);

        this.statement.setInt(7, this.recetaMedica.getRecetaMedicaId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.recetaMedica.getRecetaMedicaId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.recetaMedica.getRecetaMedicaId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.recetaMedica = new RecetaMedicaDto();
        this.recetaMedica.setRecetaMedicaId(this.resultSet.getInt("RECETA_MEDICA_ID"));
        CitaAtencionDto ci= new CitaAtencionDto();
        ci.setCitaId(this.resultSet.getInt("CITA_ID"));
        this.recetaMedica.setCita(ci);
        this.recetaMedica.setFechaEmision(this.resultSet.getDate("FECHA_EMISION"));
        this.recetaMedica.setDiagnostico(this.resultSet.getString("DIAGNOSTICO"));
        this.recetaMedica.setVigenciaHasta(this.resultSet.getDate("VIGENCIA_HASTA"));
        this.recetaMedica.setObservaciones(this.resultSet.getString("OBSERVACIONES"));
        this.recetaMedica.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.recetaMedica = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.recetaMedica);
    }

    @Override
    public Integer insertar(RecetaMedicaDto recetaMedica) {
        this.recetaMedica = recetaMedica;
        return super.insertar();
    }

    @Override
    public RecetaMedicaDto obtenerPorId(Integer recetaMedicaId) {
        this.recetaMedica = new RecetaMedicaDto();
        this.recetaMedica.setRecetaMedicaId(recetaMedicaId);
        super.obtenerPorId();
        return this.recetaMedica;
    }

    @Override
    public ArrayList<RecetaMedicaDto> listarTodos() {
        return (ArrayList<RecetaMedicaDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(RecetaMedicaDto recetaMedica) {
        this.recetaMedica = recetaMedica;
        return super.modificar();
    }

    @Override
    public Integer eliminar(RecetaMedicaDto recetaMedica) {
        this.recetaMedica = recetaMedica;
        return super.eliminar();
    }
    
    @Override
    public RecetaMedicaDto obtenerPorIdCita(Integer citaId) {
        
        // 1. Obtenemos el SQL (cumpliendo la solicitud de "método aparte")
        String sql = this.generarSQLParaObtenerPorIdCita();
        
        // 2. El ID de la cita es el parámetro
        Object parametros = citaId;
        
        // 3. Llamamos al método listarTodos de la clase base
        ArrayList<RecetaMedicaDto> lista = (ArrayList<RecetaMedicaDto>) super.listarTodos(sql, 
                this::incluirValorDeParametrosParaObtenerPorIdCita, 
                parametros);
        
        // 4. Devolvemos el primer resultado (o null si la lista está vacía)
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0); // Devolvemos el primer y (lógicamente) único elemento
        } else {
            return null; // No se encontró receta para esa cita
        }
    }

    private String generarSQLParaObtenerPorIdCita() {
        // 1. Obtenemos el SQL base: "SELECT ..., ..., FROM RECETAS_MEDICAS"
        String sql = super.generarSQLParaListarTodos();
        
        // 2. Añadimos el filtro WHERE
        sql = sql.concat(" WHERE CITA_ID = ?");
        
        return sql;
    }

    private void incluirValorDeParametrosParaObtenerPorIdCita(Object objetoParametros) {
        // Casteamos el objeto de parámetros a su tipo original
        Integer citaId = (Integer) objetoParametros;
        try {            
            // Asignamos el ID al primer '?' en el SQL
            this.statement.setInt(1, citaId);
        } catch (SQLException ex) {
            Logger.getLogger(RecetaMedicaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
