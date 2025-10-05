/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.DetalleServicioDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.servicios.DetalleServicioDto;

/**
 *
 * @author marti
 */
public class DetalleServicioDaoImpl extends DAOImplBase implements DetalleServicioDao {

    private DetalleServicioDto detalleServicio;

    public DetalleServicioDaoImpl() {
        super("DETALLES_SERVICIO");
        this.detalleServicio = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DETALLE_SERVICIO", true, true));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("COSTO", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("SERVICIO_ID", false, false));
        this.listaColumnas.add(new Columna("CITA_ID", false, false));

    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.detalleServicio.getDescripcion());
        this.statement.setDouble(2, this.detalleServicio.getCosto());
        this.statement.setInt(3, this.detalleServicio.getActivo() ? 1 : 0);
        this.statement.setInt(4, this.detalleServicio.getServicio().getServicioId());
        this.statement.setInt(5, this.detalleServicio.getCita().getCitaId());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.detalleServicio.getDescripcion());
        this.statement.setDouble(2, this.detalleServicio.getCosto());
        this.statement.setInt(3, this.detalleServicio.getActivo() ? 1 : 0);
        this.statement.setInt(4, this.detalleServicio.getServicio().getServicioId());
        this.statement.setInt(5, this.detalleServicio.getCita().getCitaId());

        this.statement.setInt(6, this.detalleServicio.getDetalleServicioId());

    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.detalleServicio.getDetalleServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.detalleServicio.getDetalleServicioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detalleServicio = new DetalleServicioDto();
        this.detalleServicio.setDetalleServicioId(this.resultSet.getInt("DETALLE_SERVICIO_ID"));
        this.detalleServicio.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.detalleServicio.setCosto(this.resultSet.getDouble("COSTO"));
        this.detalleServicio.setActivo(this.resultSet.getInt("ACTIVO")==1);
        this.detalleServicio.setServicio(new ServicioDaoImpl().obtenerPorId(this.resultSet.getInt("SERVICIO_ID")));
        this.detalleServicio.setCita(new CitaAtencionDaoImpl().obtenerPorId(this.resultSet.getInt("CITA_ID")));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.detalleServicio = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.detalleServicio);
    }

    @Override
    public Integer insertar(DetalleServicioDto detalleServicio) {
        this.detalleServicio = detalleServicio;
        return super.insertar();
    }

    @Override
    public DetalleServicioDto obtenerPorId(Integer detalleServicioId) {
        this.detalleServicio = new DetalleServicioDto();
        this.detalleServicio.setDetalleServicioId(detalleServicioId);
        super.obtenerPorId();
        return this.detalleServicio;
    }

    @Override
    public ArrayList<DetalleServicioDto> listarTodos() {
        return (ArrayList<DetalleServicioDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(DetalleServicioDto detalleServicio) {
        this.detalleServicio = detalleServicio;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetalleServicioDto detalleServicio) {
        this.detalleServicio = detalleServicio;
        return super.eliminar();
    }
}
