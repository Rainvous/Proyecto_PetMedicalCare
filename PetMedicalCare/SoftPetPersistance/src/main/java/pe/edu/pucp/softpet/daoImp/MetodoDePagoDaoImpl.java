package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.MetodoDePagoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.facturacion.MetodoDePagoDto;

public class MetodoDePagoDaoImpl extends DaoBaseImpl implements MetodoDePagoDao {

    private MetodoDePagoDto metodo;

    public MetodoDePagoDaoImpl() {
        super("METODOS_DE_PAGO");
        this.metodo = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("METODO_DE_PAGO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.metodo.getNombre());
        this.statement.setInt(2, this.metodo.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.metodo.getNombre());
        this.statement.setInt(2, this.metodo.getActivo() ? 1 : 0);

        this.statement.setInt(3, this.metodo.getMetodoDePagoId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.metodo.getMetodoDePagoId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.metodo.getMetodoDePagoId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.metodo = new MetodoDePagoDto();
        this.metodo.setMetodoDePagoId(this.resultSet.getInt("METODO_DE_PAGO_ID"));
        this.metodo.setNombre(this.resultSet.getString("NOMBRE"));
        this.metodo.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.metodo = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.metodo);
    }

    @Override
    public Integer insertar(MetodoDePagoDto entity) {
        this.metodo = entity;
        return super.insertar();
    }

    @Override
    public MetodoDePagoDto obtenerPorId(Integer id) {
        this.metodo = new MetodoDePagoDto();
        this.metodo.setMetodoDePagoId(id);
        super.obtenerPorId();
        return this.metodo;
    }

    @Override
    public ArrayList<MetodoDePagoDto> listarTodos() {
        return (ArrayList<MetodoDePagoDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(MetodoDePagoDto entity) {
        this.metodo = entity;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MetodoDePagoDto entity) {
        this.metodo = entity;
        return super.eliminar();
    }
}
