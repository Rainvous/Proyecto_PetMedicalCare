/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author User
 * @param <T> Tipo de Entidad (DTO) que usara el DAO
 */
public interface DaoBase<T> {
    
    
    
    public Integer insertar(T entity);

    

    public ArrayList<T> listarTodos();

    public Integer modificar(T entity);

    public Integer eliminar(T entity);
    


}
