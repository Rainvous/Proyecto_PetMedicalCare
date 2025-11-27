package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;

public interface ProductoDao extends DaoBase<ProductoDto> {

    // Consultas específicas existentes
    ArrayList<ProductoDto> ListarPorTipo(String NombreTipo);

    ArrayList<ProductoDto> ListarPorNombre(String Nombre);
    
    ArrayList<ProductoDto> listarProductosActivos();
    
    // Búsqueda avanzada antigua (Por Procedure)
    ArrayList<ProductoDto> ListasBusquedaProductosAvanzada(ProductoDto producto, String rango, String activo, Integer tipoId);

    // -------------------------------------------------------------------------
    // NUEVOS MÉTODOS AGREGADOS 
    // -------------------------------------------------------------------------

    // Búsqueda Paginada (MySQL / SQL Server)
    List<ProductoDto> buscarProductosPaginados(String nombre, String rangoId, Boolean activo, int pagina);

    // Verificación de integridad (SELECT count/flag)
    int VerificarSiElProductoTieneInformacion(int idServicio);
}