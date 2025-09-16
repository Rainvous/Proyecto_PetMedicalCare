/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.PersonasDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.model.actoresdto.PersonasDTO;

/**
 *
 * @author User
 */
public class PersonasDAOImpl extends DAOImplBase implements PersonasDAO {

    private PersonasDTO persona;

    public PersonasDAOImpl(String nombre_tabla) {
        super("nombre");
        this.persona=null;
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        
        
    }

    @Override
    public Integer insertar(PersonasDTO persona) {
        
    }

    @Override
    public PersonasDTO obtenerPorId(Integer personaId) {
        
    }

    @Override
    public ArrayList<PersonasDTO> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Integer modificar(PersonasDTO persona) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Integer eliminar(PersonasDTO persona) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
