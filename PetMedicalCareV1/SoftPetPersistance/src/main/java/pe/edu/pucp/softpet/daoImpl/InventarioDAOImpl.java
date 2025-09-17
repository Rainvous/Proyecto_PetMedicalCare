/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.InventarioDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.productosDTO.InventarioDTO;
import pe.edu.pucp.softpet.model.productosDTO.ProductosDTO;

/**
 *
 * @author marti
 */
public class InventarioDAOImpl extends DAOImplBase implements  InventarioDAO{

    private InventarioDTO inventario;

    public InventarioDAOImpl() {

        super("INVENTARIO");
        this.inventario = null;
        this.retornarLlavePrimaria = true;
    }

    public InventarioDAOImpl(String nombre_tabla) {

        super(nombre_tabla);
        this.inventario = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {

        this.listaColumnas.add(new Columna("SERVICIO_ID", true, true));
        this.listaColumnas.add(new Columna("TIPO_SERVICIO_ID", false, false));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("COSTO", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setObject(1, this.inventario.getProducto());
        this.statement.setDate(2, (Date) this.inventario.getFechaultimomov());
        this.statement.setString(3, this.inventario.getLote());
        this.statement.setInt(4, this.inventario.getCantidad_lote());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setObject(1, this.inventario.getProducto());
        this.statement.setDate(2, (Date) this.inventario.getFechaultimomov());
        this.statement.setString(3, this.inventario.getLote());
        this.statement.setInt(4, this.inventario.getCantidad_lote());

        this.statement.setInt(5, this.inventario.getInventario_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {

        this.statement.setInt(5, this.inventario.getInventario_id());
    }

    @Override
    public Integer insertar(InventarioDTO servicio) {

        this.inventario = servicio;
        return super.insertar();
    }

    @Override
    public InventarioDTO obtenerPorId(Integer inventarioId) {
        this.inventario = new InventarioDTO();
        this.inventario.setInventario_id(inventarioId);
        super.obtenerPorId();
        return this.inventario;
    }

    @Override
    public ArrayList<InventarioDTO> listarTodos() {
        return (ArrayList<InventarioDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(InventarioDTO inventario) {
        this.inventario = inventario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(InventarioDTO inventario) {
        this.inventario = inventario;
        return super.eliminar();
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(5, this.inventario.getInventario_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.inventario = new InventarioDTO();
        this.inventario.setInventario_id(this.resultSet.getInt("INVENTARIO_ID"));
        this.inventario.setProducto((ProductosDTO) this.resultSet.getObject("PRODUCTO_ID"));
        this.inventario.setFechaultimomov(this.resultSet.getDate("FECHAULTIMOMOV"));
        this.inventario.setLote(this.resultSet.getString("LOTE"));
        this.inventario.setCantidad_lote(this.resultSet.getInt("CANTIDAD_LOTE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.inventario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.inventario);
    }
}
