/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.TipoDeComprobanteDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.facturaciondto.TipoComprobantesDTO;


/**
 *
 * @author User
 */
public class TipoDeComprobanteDAOImpl extends DAOImplBase implements TipoDeComprobanteDAO  {
    private TipoComprobantesDTO tipo_comprobantes;
    
    public TipoDeComprobanteDAOImpl(){
        super("TIPO_COMPROBANTE");
        this.tipo_comprobantes=null;
        this.retornarLlavePrimaria=true;
    }
    
    public TipoDeComprobanteDAOImpl(String nombre_tabla){
        super(nombre_tabla);
        this.tipo_comprobantes=null;
        this.retornarLlavePrimaria=true;
    }

    @Override
    protected void configurarListaDeColumnas() {
          this.listaColumnas.add(new Columna("TIPO_COMPROBANTE_ID", true, true));
          this.listaColumnas.add(new Columna("NOMBRE:", true, true));

    }
    
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {

        // NO AUTOGENERADAS
        
        this.statement.setString(2, this.tipo_comprobantes.getNombre());
       
    }
    
    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        // NO AUTOGENERADAS
        this.statement.setString(2, this.tipo_comprobantes.getNombre());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {

        this.statement.setInt(1, this.tipo_comprobantes.getTipo_comprobante_id());
    }

    @Override
    public Integer insertar(TipoComprobantesDTO tipo_comprobante) {
        this.tipo_comprobantes=tipo_comprobante;
        return super.insertar();
    }

    @Override
    public TipoComprobantesDTO obtenerPorId(Integer tipo_comprobante_id) {
         this.tipo_comprobantes = new TipoComprobantesDTO();
        this.tipo_comprobantes.setTipo_comprobante_id(tipo_comprobante_id);
        super.obtenerPorId();
        return this.tipo_comprobantes;
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipo_comprobantes.getTipo_comprobante_id());
    }
    
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipo_comprobantes = new TipoComprobantesDTO();
        this.tipo_comprobantes.setTipo_comprobante_id(this.resultSet.getInt("TIPO_COMPROBANTE_ID"));
        this.tipo_comprobantes.setNombre(this.resultSet.getString("NOMBRE"));
    }
    @Override
    public ArrayList<TipoComprobantesDTO> listarTodos() {
        return (ArrayList<TipoComprobantesDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(TipoComprobantesDTO tipo_comprobante) {
        this.tipo_comprobantes=tipo_comprobante;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TipoComprobantesDTO tipo_comprobante) {
        this.tipo_comprobantes=tipo_comprobante;
        return super.eliminar();
    }
    
     @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipo_comprobantes = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipo_comprobantes);
    }
    
    
}
