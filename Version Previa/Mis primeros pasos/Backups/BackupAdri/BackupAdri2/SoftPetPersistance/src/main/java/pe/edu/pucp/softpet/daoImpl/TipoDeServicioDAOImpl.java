/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.TipoDeServicioDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.serviciosdto.TipoServiciosDTO;

/**
 *
 * @author User
 */
public class TipoDeServicioDAOImpl extends DAOImplBase implements TipoDeServicioDAO {
    private TipoServiciosDTO tipo_servicios;
    
    public TipoDeServicioDAOImpl(){
        super ("TIPO_DE_SERVICIO");
        this.tipo_servicios=null;
        this.retornarLlavePrimaria=true;
    }
    
    public TipoDeServicioDAOImpl(String nombre_tabla){
        super(nombre_tabla);
        this.tipo_servicios=null;
        this.retornarLlavePrimaria=true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_DE_SERVICIO_id", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        // NO AUTOGENERADAS
        
        this.statement.setString(2, this.tipo_servicios.getNombre());
        this.statement.setString(3, this.tipo_servicios.getDescripcion());
        
    }
    
    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setString(2, this.tipo_servicios.getNombre());
        this.statement.setString(3, this.tipo_servicios.getDescripcion());
        
    }
    
    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {

        this.statement.setInt(1, this.tipo_servicios.getTipoServicioId());
    }
    
    @Override
    public Integer insertar(TipoServiciosDTO tipoServicio) {
        this.tipo_servicios=null;
        return super.insertar();
    }

    @Override
    public TipoServiciosDTO obtenerPorId(Integer tipoServicio_id) {
        this.tipo_servicios = new TipoServiciosDTO();
        this.tipo_servicios.setTipoServicioId(tipoServicio_id);
        super.obtenerPorId();
        return this.tipo_servicios;
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipo_servicios.getTipoServicioId());
    }
    
     @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipo_servicios = new TipoServiciosDTO();
        this.tipo_servicios.setTipoServicioId(this.resultSet.getInt("TIPO_SERVICIO_ID"));
        this.tipo_servicios.setNombre(this.resultSet.getString("NOMBRE"));
        this.tipo_servicios.setDescripcion(this.resultSet.getString("DESCRIPCION"));
    }


    @Override
    public ArrayList<TipoServiciosDTO> listarTodos() {
        return (ArrayList<TipoServiciosDTO>) super.listarTodos();
    }
    
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipo_servicios);
    }

    @Override
    public Integer modificar(TipoServiciosDTO tipoServicio) {
        this.tipo_servicios=tipoServicio;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TipoServiciosDTO tipoServicio) {
        this.tipo_servicios=tipoServicio;
        return super.eliminar();
    }
    
    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipo_servicios = null;
    }
}
