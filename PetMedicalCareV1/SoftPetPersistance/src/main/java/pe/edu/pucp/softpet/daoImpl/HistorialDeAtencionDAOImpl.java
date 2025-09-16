/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.dao.HistorialDeAtencionDAO;
import pe.edu.pucp.softpet.model.HistorialDeAtencionesDTO;
import pe.edu.pucp.softpet.model.actoresdto.MascotasDTO;

/**
 *
 * @author User
 */
public class HistorialDeAtencionDAOImpl extends DAOImplBase implements HistorialDeAtencionDAO{
    private HistorialDeAtencionesDTO historialDeAtencion;
    
    public HistorialDeAtencionDAOImpl(){
        super("HISTORIAL_DE_ATENCION");
        this.historialDeAtencion = null;
        this.retornarLlavePrimaria = true;
    }
    
    
    public HistorialDeAtencionDAOImpl(String nombre_tabla){
        super(nombre_tabla);
        this.historialDeAtencion=null;
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.historialDeAtencion.getHistoriaId());
    }
    
    @Override
    protected void configurarListaDeColumnas() {

        this.listaColumnas.add(new Columna("HISTORIAL_ID", true, true));
        this.listaColumnas.add(new Columna("MASCOTA_ID", false, false));
        this.listaColumnas.add(new Columna("ESTAD0_MASCOTA", false, false));
    }
    
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        // NO AUTOGENERADAS mascotaID,ESTADO_MASCOTA
        this.statement.setObject(1, this.historialDeAtencion.getMascota());
        this.statement.setString(2, this.historialDeAtencion.getEstadoMascota());
    }
    
    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setObject(1, this.historialDeAtencion.getMascota());
        this.statement.setString(2, this.historialDeAtencion.getEstadoMascota());
    }
    
    @Override
    public Integer insertar(HistorialDeAtencionesDTO historialDeAtencion) {
        this.historialDeAtencion=historialDeAtencion;
        return super.insertar();
    }

    @Override
    public HistorialDeAtencionesDTO obtenerPorId(Integer historiaAtencion_id) {
        this.historialDeAtencion = new HistorialDeAtencionesDTO();
        this.historialDeAtencion.setHistoriaId(historiaAtencion_id);
        super.obtenerPorId();
        return this.historialDeAtencion;
    }

    @Override
    public ArrayList<HistorialDeAtencionesDTO> listarTodos() {
        return (ArrayList<HistorialDeAtencionesDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(HistorialDeAtencionesDTO historialDeAtencion) {
        this.historialDeAtencion = historialDeAtencion;
        return super.modificar();
    }
    
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.historialDeAtencion = new HistorialDeAtencionesDTO();
        this.historialDeAtencion.setHistoriaId(this.resultSet.getInt("HISTORIAL_ID"));
        this.historialDeAtencion.setMascota((MascotasDTO) this.resultSet.getObject("MASCOTA_ID"));
        this.historialDeAtencion.setEstadoMascota(this.resultSet.getString("ESTADO_MASCOTA"));
       
    }
    
   
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.historialDeAtencion);
    }

    
    
    
    
}
