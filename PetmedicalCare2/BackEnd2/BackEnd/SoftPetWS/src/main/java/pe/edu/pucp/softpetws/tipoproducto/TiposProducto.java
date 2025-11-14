package pe.edu.pucp.softpetws.tipoproducto;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;

import pe.edu.pucp.softpet.bo.TipoProductoBo;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;

@WebService(serviceName = "TiposProducto")
public class TiposProducto {

    private final TipoProductoBo tipoProductoBo;

    public TiposProducto() {
        this.tipoProductoBo = new TipoProductoBo();
    }

    @WebMethod(operationName = "insertar_tipoproductos")
    public Integer insertar(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "activo") boolean activo) {

        return this.tipoProductoBo.insertar(nombre, descripcion, activo);
    }

    @WebMethod(operationName = "modificar_tipoproductos")
    public Integer modificar(
            @WebParam(name = "tipoProductoId") int tipoProductoId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "activo") boolean activo) {

        return this.tipoProductoBo.modificar(tipoProductoId, nombre, descripcion, activo);
    }

    @WebMethod(operationName = "eliminar_tipoproductos")
    public int eliminar(@WebParam(name = "tipoProductoId") int tipoProductoId) {
        return this.tipoProductoBo.eliminar(tipoProductoId);
    }

    @WebMethod(operationName = "obtener_por_id")
    public TipoProductoDto obtenerPorId(@WebParam(name = "id") int tipoProductoId) {
        return this.tipoProductoBo.obtenerPorId(tipoProductoId);
    }

    @WebMethod(operationName = "listar_todos")
    public ArrayList<TipoProductoDto> listarTodos() {
        return this.tipoProductoBo.listarTodos();
    }
}
