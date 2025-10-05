/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.TipoProductoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;

/**
 *
 * @author marti
 */
public class TipoProductoDaoImpl extends DAOImplBase implements TipoProductoDao {

    private TipoProductoDto tipoProducto;

    public TipoProductoDaoImpl() {
        super("TIPOS_PRODUCTO");
        this.tipoProducto = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_PRODUCTO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DESCIPCION", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.tipoProducto.getNombre());
        this.statement.setString(2, this.tipoProducto.getDescripcion());
        this.statement.setInt(3, this.tipoProducto.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.tipoProducto.getNombre());
        this.statement.setString(2, this.tipoProducto.getDescripcion());
        this.statement.setInt(3, this.tipoProducto.getActivo() ? 1 : 0);

        this.statement.setInt(4, this.tipoProducto.getTipoProductoId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tipoProducto.getTipoProductoId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoProducto.getTipoProductoId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoProducto = new TipoProductoDto();
        this.tipoProducto.setTipoProductoId(this.resultSet.getInt("TIPO_PRODUCTO_ID"));
        this.tipoProducto.setNombre(this.resultSet.getString("NOMBRE"));
        this.tipoProducto.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.tipoProducto.setActivo(this.resultSet.getInt("ACTIVO") == 1);
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

    @Override
    public Integer insertar(TipoProductoDto tipoProducto) {
        this.tipoProducto = tipoProducto;
        return super.insertar();
    }

    @Override
    public TipoProductoDto obtenerPorId(Integer tipoProductoiD) {
        this.tipoProducto = new TipoProductoDto();
        this.tipoProducto.setTipoProductoId(tipoProductoiD);
        super.obtenerPorId();
        return this.tipoProducto;
    }

    @Override
    public ArrayList<TipoProductoDto> listarTodos() {
        return (ArrayList<TipoProductoDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(TipoProductoDto tipoProducto) {
        this.tipoProducto = tipoProducto;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TipoProductoDto tipoProducto) {
        this.tipoProducto = tipoProducto;
        return super.eliminar();
    }
}
