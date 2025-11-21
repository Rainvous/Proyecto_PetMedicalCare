package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softpet.dao.DetalleRecetaDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.recetas.DetalleRecetaDto;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

public class DetalleRecetaDaoImpl extends DaoBaseImpl implements DetalleRecetaDao {

    private DetalleRecetaDto detalleReceta;

    public DetalleRecetaDaoImpl() {
        super("DETALLES_RECETA");
        this.detalleReceta = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DETALLE_RECETA_ID", true, true));
        this.listaColumnas.add(new Columna("RECETA_MEDICA_ID", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION_MEDICAMENTO", false, false));
        this.listaColumnas.add(new Columna("PRESENTACION", false, false));
        this.listaColumnas.add(new Columna("VIA_ADMINISTRACION", false, false));
        this.listaColumnas.add(new Columna("DOSIS", false, false));
        this.listaColumnas.add(new Columna("FRECUENCIA", false, false));
        this.listaColumnas.add(new Columna("DURACION", false, false));
        this.listaColumnas.add(new Columna("INDICACION", false, false));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.detalleReceta.getReceta().getRecetaMedicaId());
        this.statement.setString(2, this.detalleReceta.getDescripcionMedicamento());
        this.statement.setString(3, this.detalleReceta.getPresentacion());
        this.statement.setString(4, this.detalleReceta.getViaAdministracion());
        this.statement.setString(5, this.detalleReceta.getDosis());
        this.statement.setString(6, this.detalleReceta.getFrecuencia());
        this.statement.setString(7, this.detalleReceta.getDuracion());
        this.statement.setString(8, this.detalleReceta.getIndicacion());
        this.statement.setString(9, this.detalleReceta.getCantidad());
        this.statement.setInt(10, this.detalleReceta.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.detalleReceta.getReceta().getRecetaMedicaId());
        this.statement.setString(2, this.detalleReceta.getDescripcionMedicamento());
        this.statement.setString(3, this.detalleReceta.getPresentacion());
        this.statement.setString(4, this.detalleReceta.getViaAdministracion());
        this.statement.setString(5, this.detalleReceta.getDosis());
        this.statement.setString(6, this.detalleReceta.getFrecuencia());
        this.statement.setString(7, this.detalleReceta.getDuracion());
        this.statement.setString(8, this.detalleReceta.getIndicacion());
        this.statement.setString(9, this.detalleReceta.getCantidad());
        this.statement.setInt(10, this.detalleReceta.getActivo() ? 1 : 0);

        this.statement.setInt(11, this.detalleReceta.getDetalleRecetaId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.detalleReceta.getDetalleRecetaId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.detalleReceta.getDetalleRecetaId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detalleReceta = new DetalleRecetaDto();
        this.detalleReceta.setDetalleRecetaId(this.resultSet.getInt("DETALLE_RECETA_ID"));
        RecetaMedicaDto rec = new RecetaMedicaDto();
        rec.setRecetaMedicaId(this.resultSet.getInt("RECETA_MEDICA_ID"));
        this.detalleReceta.setReceta(rec);
        this.detalleReceta.setDescripcionMedicamento(this.resultSet.getString("DESCRIPCION_MEDICAMENTO"));
        this.detalleReceta.setPresentacion(this.resultSet.getString("PRESENTACION"));
        this.detalleReceta.setViaAdministracion(this.resultSet.getString("VIA_ADMINISTRACION"));
        this.detalleReceta.setDosis(this.resultSet.getString("DOSIS"));
        this.detalleReceta.setFrecuencia(this.resultSet.getString("FRECUENCIA"));
        this.detalleReceta.setDuracion(this.resultSet.getString("DURACION"));
        this.detalleReceta.setIndicacion(this.resultSet.getString("INDICACION"));
        this.detalleReceta.setCantidad(this.resultSet.getString("CANTIDAD"));
        this.detalleReceta.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.detalleReceta = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.detalleReceta);
    }

    @Override
    public Integer insertar(DetalleRecetaDto detalleReceta) {
        this.detalleReceta = detalleReceta;
        return super.insertar();
    }

    @Override
    public DetalleRecetaDto obtenerPorId(Integer detalleRecetaId) {
        this.detalleReceta = new DetalleRecetaDto();
        this.detalleReceta.setDetalleRecetaId(detalleRecetaId);
        super.obtenerPorId();
        return this.detalleReceta;
    }

    @Override
    public ArrayList<DetalleRecetaDto> listarTodos() {
        return (ArrayList<DetalleRecetaDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(DetalleRecetaDto detalleReceta) {
        this.detalleReceta = detalleReceta;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetalleRecetaDto detalleReceta) {
        this.detalleReceta = detalleReceta;
        return super.eliminar();
    }

    @Override
    public ArrayList<DetalleRecetaDto> listarPorIdReceta(Integer recetaMedicaId) {

        // 1. Obtenemos el SQL (cumpliendo la solicitud de "método aparte")
        String sql = this.generarSQLParaListarPorIdReceta();

        // 2. El ID de la receta es el parámetro
        Object parametros = recetaMedicaId;

        // 3. Llamamos al método listarTodos de la clase base
        return (ArrayList<DetalleRecetaDto>) super.listarTodos(sql,
                this::incluirValorDeParametrosParaListarPorIdReceta,
                parametros);
    }

    private String generarSQLParaListarPorIdReceta() {
        // 1. Obtenemos el SQL base: "SELECT ..., ..., FROM DETALLES_RECETA"
        String sql = super.generarSQLParaListarTodos();

        // 2. Añadimos el filtro WHERE
        sql = sql.concat(" WHERE RECETA_MEDICA_ID = ?");

        return sql;
    }

    private void incluirValorDeParametrosParaListarPorIdReceta(Object objetoParametros) {
        // Casteamos el objeto de parámetros a su tipo original
        Integer recetaMedicaId = (Integer) objetoParametros;
        try {
            // Asignamos el ID al primer '?' en el SQL
            this.statement.setInt(1, recetaMedicaId);
        } catch (SQLException ex) {
            Logger.getLogger(DetalleRecetaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
