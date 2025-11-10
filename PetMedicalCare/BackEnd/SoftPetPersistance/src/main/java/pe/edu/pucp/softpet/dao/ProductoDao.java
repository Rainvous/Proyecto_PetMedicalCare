package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;

public interface ProductoDao extends DaoBase<ProductoDto> {

    ArrayList<ProductoDto> ListarPorTipo(String NombreTipo);

    ArrayList<ProductoDto> ListarPorNombre(String Nombre);
}
