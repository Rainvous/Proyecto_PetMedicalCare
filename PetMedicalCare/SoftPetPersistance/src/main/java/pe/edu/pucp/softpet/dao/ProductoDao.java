package pe.edu.pucp.softpet.dao;

import pe.edu.pucp.softpet.dto.productos.ProductoDto;

/**
 *
 * @author User
 */
public interface ProductoDao extends DaoBase<ProductoDto> {

    public ProductoDto obtenerPorId(Integer idDto);
}
