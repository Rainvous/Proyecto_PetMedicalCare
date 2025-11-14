package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;

/**
 *
 * @author User
 * @param <T> Tipo de Entidad (DTO) que usara el DAO
 */
public interface DaoBase<T> {

    Integer insertar(T entity);

    T obtenerPorId(Integer id);

    ArrayList<T> listarTodos();

    Integer modificar(T entity);

    Integer eliminar(T entity);
}
