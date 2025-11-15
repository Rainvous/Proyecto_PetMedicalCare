package pe.edu.pucp.softpetws.producto;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.ProductoBo;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;

@WebService(serviceName = "Productos")
public class Productos {

    private final ProductoBo productoBo;

    public Productos() {
        this.productoBo = new ProductoBo();
    }

    @WebMethod(operationName = "insertar_productos")
    public Integer insertar(
            @WebParam(name = "tipoProductoId") int tipoProductoId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "presentacion") String presentacion,
            @WebParam(name = "precioUnitario") double precioUnitario,
            @WebParam(name = "stock") int stock,
            @WebParam(name = "activo") boolean activo) {

        return this.productoBo.insertar(
                tipoProductoId, nombre, presentacion, precioUnitario, stock, activo
        );
    }
//

    @WebMethod(operationName = "modificar_productos")
    public Integer modificar(
            @WebParam(name = "productoId") int productoId,
            @WebParam(name = "tipoProductoId") int tipoProductoId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "presentacion") String presentacion,
            @WebParam(name = "precioUnitario") double precioUnitario,
            @WebParam(name = "stock") int stock,
            @WebParam(name = "activo") boolean activo) {

        return this.productoBo.modificar(
                productoId, tipoProductoId, nombre, presentacion, precioUnitario, stock, activo
        );
    }

    @WebMethod(operationName = "eliminar_productos")
    public int eliminar(@WebParam(name = "productoId") int productoId) {
        return this.productoBo.eliminar(productoId);
    }

    @WebMethod(operationName = "obtener_por_id")
    public ProductoDto obtenerPorId(@WebParam(name = "id") int productoId) {
        return this.productoBo.obtenerPorId(productoId);
    }

    @WebMethod(operationName = "listar_todos")
    public ArrayList<ProductoDto> listarTodos() {
        return this.productoBo.listarTodos();
    }
    
    @WebMethod(operationName = "listar_productos_activos")
    public ArrayList<ProductoDto> listarProductosActivos() {
        return this.productoBo.listarProductosActivos();
    }
    
    @WebMethod(operationName = "listar_productos_por_tipo")
    public ArrayList<ProductoDto> ListarPorTipo(
            @WebParam(name = "nombreTipo") String nombreTipo) {
        
        return this.productoBo.ListarPorTipo(nombreTipo);
    }
    
    @WebMethod(operationName = "VerificarSiElProductoTieneInformacion")
    public int VerificarSiElProductoTieneInformacion(
            @WebParam(name = "nombreTipo") int idServicio){
        return this.productoBo.VerificarSiElProductoTieneInformacion(idServicio);
    }
}
