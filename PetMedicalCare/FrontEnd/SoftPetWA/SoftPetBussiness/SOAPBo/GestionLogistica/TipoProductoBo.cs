
using SoftPetBussiness.TipoProductoClient;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class TipoProductoBO
    {
        private TiposProductoClient clienteSOAP;

        public TipoProductoBO()
        {
            this.clienteSOAP = new TiposProductoClient();
        }

        // Insertar tipo de producto
        public int Insertar(string nombre, string descripcion, bool activo)
        {
            return this.clienteSOAP.insertar_tipoproductos(nombre, descripcion, activo);
        }

        // Modificar tipo de producto
        public int Modificar(int tipoProductoId, string nombre, string descripcion, bool activo)
        {
            return this.clienteSOAP.modificar_tipoproductos(tipoProductoId, nombre, descripcion, activo);
        }

        // Eliminar tipo de producto
        public int Eliminar(int tipoProductoId)
        {
            return this.clienteSOAP.eliminar_tipoproductos(tipoProductoId);
        }

        // Obtener tipo de producto por ID
        public tipoProductoDto ObtenerPorId(int tipoProductoId)
        {
            return this.clienteSOAP.obtener_por_id(tipoProductoId);
        }

        // Listar todos los tipos de productos
        public List<tipoProductoDto> ListarTodos()
        {
            return this.clienteSOAP.listar_todos().ToList<tipoProductoDto>();
        }

        // Insertar tipo de producto usando DTO
        public int Insertar(tipoProductoDto tipoProducto)
        {
            return this.clienteSOAP.insertar_tipoproductos(
                tipoProducto.nombre,
                tipoProducto.descripcion,
                tipoProducto.activo
            );
        }

        // Modificar tipo de producto usando DTO
        public int Modificar(tipoProductoDto tipoProducto)
        {
            return this.clienteSOAP.modificar_tipoproductos(
                tipoProducto.tipoProductoId,
                tipoProducto.nombre,
                tipoProducto.descripcion,
                tipoProducto.activo
            );
        }
    }
}
