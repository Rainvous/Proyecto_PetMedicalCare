/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.DetalleServicioDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.DetalleDTO.DetalleServiciosDTO;
import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;
import pe.edu.pucp.softpet.model.serviciosdto.ServiciosDTO;

/**
 *
 * @author marti
 */
public class DetalleServicioDAOImpl extends DAOImplBase implements DetalleServicioDAO {

    private DetalleServiciosDTO detalleServicio;

    public DetalleServicioDAOImpl() {

        super("DETALLE_SERVICIO");
        this.detalleServicio = null;
        this.retornarLlavePrimaria = true;
    }

    public DetalleServicioDAOImpl(String nombre_tabla) {

        super(nombre_tabla);
        this.detalleServicio = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {

        this.listaColumnas.add(new Columna("CITAXSERVICIO", true, true));
        this.listaColumnas.add(new Columna("ID_CITA", false, false));
        this.listaColumnas.add(new Columna("ID_SERVICIO", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setObject(1, this.detalleServicio.getCita());
        this.statement.setObject(2, this.detalleServicio.getServicio());
        this.statement.setString(3, this.detalleServicio.getDescripcion());
        this.statement.setDouble(4, this.detalleServicio.getTotal());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        // NO AUTOGENERADAS
         this.statement.setObject(1, this.detalleServicio.getCita());
        this.statement.setObject(2, this.detalleServicio.getServicio());
        this.statement.setString(3, this.detalleServicio.getDescripcion());
        this.statement.setDouble(4, this.detalleServicio.getTotal());

        this.statement.setInt(5, this.detalleServicio.getCitaXServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {

        this.statement.setInt(5, this.detalleServicio.getCitaXServicioId());
    }

    @Override
    public Integer insertar(DetalleServiciosDTO detalleServicio) {

        this.detalleServicio = detalleServicio;
        return super.insertar();
    }

    @Override
    public DetalleServiciosDTO obtenerPorId(Integer detalleServicioId) {
        this.detalleServicio = new DetalleServiciosDTO();
        this.detalleServicio.setCitaXServicioId(detalleServicioId);
        super.obtenerPorId();
        return this.detalleServicio;
    }

    @Override
    public ArrayList<DetalleServiciosDTO> listarTodos() {
        return (ArrayList<DetalleServiciosDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(DetalleServiciosDTO detalleServicio) {
        this.detalleServicio = detalleServicio;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetalleServiciosDTO detalleServicio) {
        this.detalleServicio = detalleServicio;
        return super.eliminar();
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(5, this.detalleServicio.getCitaXServicioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detalleServicio = new DetalleServiciosDTO();
        this.detalleServicio.setCitaXServicioId(this.resultSet.getInt("CITAXSERVICIO_ID"));
        this.detalleServicio.setCita((CitaAtencionDTO) this.resultSet.getObject("ID_CITA"));
        this.detalleServicio.setServicio((ServiciosDTO)this.resultSet.getObject("ID_SERVICIO"));
        this.detalleServicio.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.detalleServicio.setTotal(this.resultSet.getDouble("TOTAL"));
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
}

