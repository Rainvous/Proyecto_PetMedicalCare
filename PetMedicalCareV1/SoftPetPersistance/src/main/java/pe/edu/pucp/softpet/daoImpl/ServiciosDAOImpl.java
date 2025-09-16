/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.ServiciosDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.dbmanager.db.DBManager;
import pe.edu.pucp.softpet.model.serviciosdto.ServiciosDTO;
import pe.edu.pucp.softpet.model.serviciosdto.TipoServiciosDTO;

/**
 *
 * @author ferro
 */
public class ServiciosDAOImpl extends DAOImplBase implements  ServiciosDAO{

    private ServiciosDTO servicio;

    public ServiciosDAOImpl() {

        super("SERVICIO");
        this.servicio = null;
        this.retornarLlavePrimaria = true;
    }

    public ServiciosDAOImpl(String nombre_tabla) {

        super(nombre_tabla);
        this.servicio = null;
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
        this.statement.setObject(1, this.servicio.getTipoServicioDTO());
        this.statement.setString(2, this.servicio.getNombre());
        this.statement.setDouble(3, this.servicio.getCosto());
        this.statement.setString(4, this.servicio.getEstado());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setObject(1, this.servicio.getTipoServicioDTO());
        this.statement.setString(2, this.servicio.getNombre());
        this.statement.setDouble(3, this.servicio.getCosto());
        this.statement.setString(4, this.servicio.getEstado());

        this.statement.setInt(5, this.servicio.getServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {

        this.statement.setInt(5, this.servicio.getServicioId());
    }

    @Override
    public Integer insertar(ServiciosDTO servicio) {

        this.servicio = servicio;
        return super.insertar();
    }

    @Override
    public ServiciosDTO obtenerPorId(Integer servicioId) {
        this.servicio = new ServiciosDTO();
        this.servicio.setServicioId(servicioId);
        super.obtenerPorId();
        return this.servicio;
    }

    @Override
    public ArrayList<ServiciosDTO> listarTodos() {
        return (ArrayList<ServiciosDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(ServiciosDTO servicio) {
        this.servicio = servicio;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ServiciosDTO servicio) {
        this.servicio = servicio;
        return super.eliminar();
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.servicio.getServicioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.servicio = new ServiciosDTO();
        this.servicio.setServicioId(this.resultSet.getInt("SERVICIO_ID"));
        this.servicio.setTipoServicioDTO((TipoServiciosDTO) this.resultSet.getObject("TIPO_SERVICIO_ID"));
        this.servicio.setNombre(this.resultSet.getString("NOMBRE"));
        this.servicio.setCosto(this.resultSet.getDouble("COSTO"));
        this.servicio.setEstado(this.resultSet.getString("ESTADO"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.servicio = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.servicio);
    }
}
