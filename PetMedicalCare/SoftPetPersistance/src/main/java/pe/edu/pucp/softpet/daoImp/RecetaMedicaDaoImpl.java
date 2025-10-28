package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.RecetaMedicaDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
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
        this.listaColumnas.add(new Columna("DIAGNOSTICO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("CITA_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_EMISION", false, false));
        this.listaColumnas.add(new Columna("VIGENCIA_HASTA", false, false));
        this.listaColumnas.add(new Columna("OBSERVACIONES", false, false));
        
        
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.recetaMedica.getDiagnostico());
        this.statement.setInt(2, this.recetaMedica.getActivo() ? 1 : 0);
        this.statement.setInt(3, this.recetaMedica.getCita().getCitaId());
        this.statement.setDate(4, this.recetaMedica.getFechaEmision());
        this.statement.setDate(5, this.recetaMedica.getVigenciaHasta());
        this.statement.setString(6, this.recetaMedica.getObservaciones());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.recetaMedica.getDiagnostico());
        this.statement.setInt(2, this.recetaMedica.getActivo() ? 1 : 0);
        this.statement.setInt(3, this.recetaMedica.getCita().getCitaId());
        this.statement.setDate(4, this.recetaMedica.getFechaEmision());
        this.statement.setDate(5, this.recetaMedica.getVigenciaHasta());
        this.statement.setString(6, this.recetaMedica.getObservaciones());

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
        this.recetaMedica.setDiagnostico(this.resultSet.getString("DIAGNOSTICO"));
        this.recetaMedica.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        this.recetaMedica.setCita(new CitaAtencionDaoImpl().obtenerPorId(this.resultSet.getInt("CITA_ID")));
        this.recetaMedica.setFechaEmision(this.resultSet.getDate("FECHA_EMISION"));
        this.recetaMedica.setVigenciaHasta(this.resultSet.getDate("VIGENCIA_HASTA"));
        this.recetaMedica.setObservaciones(this.resultSet.getString("OBSERVACIONES"));
        
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
}
