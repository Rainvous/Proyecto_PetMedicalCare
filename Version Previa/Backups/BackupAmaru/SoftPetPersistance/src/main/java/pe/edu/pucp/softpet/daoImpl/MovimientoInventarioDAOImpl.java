package pe.edu.pucp.softpet.daoImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.MovimientoInventarioDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;
import pe.edu.pucp.softpet.model.productosDTO.MovimientosInventarioDTO;
import pe.edu.pucp.softpet.model.productosDTO.ProductosDTO;

/**
 *
 * @author ferro
 */
public class MovimientoInventarioDAOImpl extends DAOImplBase implements MovimientoInventarioDAO {
    
    private MovimientosInventarioDTO movInventario;
    
    public MovimientoInventarioDAOImpl() {

        super("MOVIMIENTO_INVENTARIO");
        this.movInventario = null;
        this.retornarLlavePrimaria = true;
    }

    public MovimientoInventarioDAOImpl(String nombre_tabla) {

        super(nombre_tabla);
        this.movInventario = null;
        this.retornarLlavePrimaria = true;
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("MOVIMIENTO_ID", true, true));
        this.listaColumnas.add(new Columna("PRODUCTO_ID", false, false));
        this.listaColumnas.add(new Columna("CITA_ID", false, false));
        this.listaColumnas.add(new Columna("TIPO_MOVIMIENTO", false, false));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("FECHA", false, false));
        this.listaColumnas.add(new Columna("MOTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setObject(1, this.movInventario.getProducto());
        this.statement.setObject(2, this.movInventario.getCita());
        this.statement.setString(3, this.movInventario.getTipo_movimiento());
        this.statement.setInt(4, this.movInventario.getCantidad());
        this.statement.setDate(5, (Date) this.movInventario.getFecha());
        this.statement.setString(6, this.movInventario.getMotivo());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        this.statement.setObject(1, this.movInventario.getProducto());
        this.statement.setObject(2, this.movInventario.getCita());
        this.statement.setString(3, this.movInventario.getTipo_movimiento());
        this.statement.setInt(4, this.movInventario.getCantidad());
        this.statement.setDate(5, (Date) this.movInventario.getFecha());
        this.statement.setString(6, this.movInventario.getMotivo());
        
        this.statement.setInt(7, this.movInventario.getMovimiento_id());
    }

    @Override
    public Integer insertar(MovimientosInventarioDTO movInventario) {

        this.movInventario = movInventario;
        return super.insertar();
    }

    @Override
    public MovimientosInventarioDTO obtenerPorId(Integer movInventarioId) {
        this.movInventario = new MovimientosInventarioDTO();
        this.movInventario.setMovimiento_id(movInventarioId);
        super.obtenerPorId();
        return this.movInventario;
    }

    @Override
    public ArrayList<MovimientosInventarioDTO> listarTodos() {
        return (ArrayList<MovimientosInventarioDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(MovimientosInventarioDTO movInventario) {
        this.movInventario = movInventario;
        return super.modificar();
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.movInventario.getMovimiento_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.movInventario = new MovimientosInventarioDTO();
        this.movInventario.setMovimiento_id(this.resultSet.getInt("MOVIMIENTO_ID"));
        this.movInventario.setProducto((ProductosDTO) this.resultSet.getObject("PRODUCTO_ID"));
        this.movInventario.setCita((CitaAtencionDTO) this.resultSet.getObject("CITA_ID"));
        this.movInventario.setTipo_movimiento(this.resultSet.getString("TIPO_MOVIMIENTO"));
        this.movInventario.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.movInventario.setFecha(this.resultSet.getDate("FECHA"));
        this.movInventario.setMotivo(this.resultSet.getString("MOTIVO"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.movInventario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.movInventario);
    }
}
