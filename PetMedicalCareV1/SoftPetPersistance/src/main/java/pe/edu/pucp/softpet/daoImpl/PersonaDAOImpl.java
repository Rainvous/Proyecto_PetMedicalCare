/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImpl;

import java.util.ArrayList;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.model.actoresdto.PersonasDTO;
import pe.edu.pucp.softpet.dao.PersonaDAO;
import pe.edu.pucp.softpet.daoImpl.util.Columna;

/**
 *
 * @author User
 */
public class PersonaDAOImpl extends DAOImplBase implements PersonaDAO {

    private PersonasDTO persona;

    public PersonaDAOImpl(String nombre_tabla) {
        super("PERSONA");
        this.persona=null;
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        
        this.listaColumnas.add(new Columna("PERSONA_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DIRECCION",false,false));
        this.listaColumnas.add(new Columna("CORREO",false,false));
        
        this.listaColumnas.add(new Columna("TELEFONO",false,false));
        
        this.listaColumnas.add(new Columna("SEXO",false,false));
        this.listaColumnas.add(new Columna("TIPO_PERSONA",false,false));
        this.listaColumnas.add(new Columna("TIPO_DOCUMENTO",false,false));
        this.listaColumnas.add(new Columna("NRO_DOCUMENTO",false,false));
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
