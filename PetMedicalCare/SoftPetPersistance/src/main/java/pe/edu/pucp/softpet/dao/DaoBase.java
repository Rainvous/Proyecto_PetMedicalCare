package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;

/**
 *
 * @author User
 * @param <T> Tipo de Entidad (DTO) que usara el DAO
 */
public interface DaoBase<T> {

    public Integer insertar(T entity);

    // public <T> obtenerPorId(Integer idDto);
    public ArrayList<T> listarTodos();

    public Integer modificar(T entity);

    public Integer eliminar(T entity);
}
