/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.dao.HistorialDeAtencionDAO;

/**
 *
 * @author User
 */
public class HistorialDeAtencionDAOImpl extends DAOImplBase implements HistorialDeAtencionDAO{
    private HistorialDeAtencionDAOImpl historialDeAtencion;
    
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
    protected void configurarListaDeColumnas() {

        this.listaColumnas.add(new Columna("HISTORIA_ID", true, true));
        this.listaColumnas.add(new Columna("MASCOTA_ID", false, false));
        this.listaColumnas.add(new Columna("ESTAD0_MASCOTA", false, false));
    }

    @Override
    public Integer insertar(HistorialDeAtencionDAO historialDeAtencion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public HistorialDeAtencionDAO obtenerPorId(Integer historiaAtencion_id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<HistorialDeAtencionDAO> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Integer modificar(HistorialDeAtencionDAO historialDeAtencion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   
    
    
}
