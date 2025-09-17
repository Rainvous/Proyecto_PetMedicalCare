/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.TipoProductoDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.productosDTO.TipoProductosDTO;
/**
 *
 * @author marti
 */
public class TipoProductoDAOImpl extends DAOImplBase implements  TipoProductoDAO{

    private TipoProductosDTO tipoProducto;

    public TipoProductoDAOImpl() {

        super("TIPO_PRODUCTO");
        this.tipoProducto = null;
        this.retornarLlavePrimaria = true;
    }

    public TipoProductoDAOImpl(String nombre_tabla) {

        super(nombre_tabla);
        this.tipoProducto = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {

        this.listaColumnas.add(new Columna("ID_TIPO_PRODUCTO", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setString(1, this.tipoProducto.getNombre());
        this.statement.setString(2, this.tipoProducto.getDescripcion());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setString(1, this.tipoProducto.getNombre());
        this.statement.setString(2, this.tipoProducto.getDescripcion());

        this.statement.setInt(3, this.tipoProducto.getTipo_producto_id());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {

        this.statement.setInt(3, this.tipoProducto.getTipo_producto_id());
    }

    @Override
    public Integer insertar(TipoProductosDTO tipoProducto) {

        this.tipoProducto = tipoProducto;
        return super.insertar();
    }

    @Override
    public TipoProductosDTO obtenerPorId(Integer tipoProductoId) {
        this.tipoProducto = new TipoProductosDTO();
        this.tipoProducto.setTipo_producto_id(tipoProductoId);
        super.obtenerPorId();
        return this.tipoProducto;
    }

    @Override
    public ArrayList<TipoProductosDTO> listarTodos() {
        return (ArrayList<TipoProductosDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(TipoProductosDTO tipoProducto) {
        this.tipoProducto = tipoProducto;
        return super.modificar();
    }


    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(3, this.tipoProducto.getTipo_producto_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoProducto = new TipoProductosDTO();
        this.tipoProducto.setTipo_producto_id(this.resultSet.getInt("ID_TIPO_PRODUCTO"));
        this.tipoProducto.setNombre(this.resultSet.getString("NOMBRE"));
        this.tipoProducto.setDescripcion(this.resultSet.getString("DESCRIPCION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoProducto = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoProducto);
    }
}
