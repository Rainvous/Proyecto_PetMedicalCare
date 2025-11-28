using SoftPetBussiness.ProductoClient;
using SoftPetBussiness.ServicioClient;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class ProductoBO
    {
        private ProductosClient clienteSOAP;
        /*
         *  EJEMPLO DE DTO
         *  productoDto pro= new productoDto();
            pro.nombre = nombre;
            pro.precioUnitario = 12.2;
            pro.activo = true;
            pro.tipoProducto.tipoProductoId = 1;
            pro.tipoProducto.nombre = "a";
            pro.tipoProducto.descripcion = "d";
         * 
         * 
         */
        public ProductoBO()
        {
            this.clienteSOAP = new ProductosClient();
        }

        // Insertar producto
        public int Insertar(int tipoProductoId, string nombre, string presentacion, double precioUnitario, int stock, bool activo)
        {
            return this.clienteSOAP.insertar_productos(tipoProductoId, nombre, presentacion, precioUnitario, stock, activo);
        }

        // Modificar producto
        public int Modificar(int productoId, int tipoProductoId, string nombre, string presentacion, double precioUnitario, int stock, bool activo)
        {
            return this.clienteSOAP.modificar_productos(productoId, tipoProductoId, nombre, presentacion, precioUnitario, stock, activo);
        }

        // Eliminar producto
        public int Eliminar(int productoId)
        {
            return this.clienteSOAP.eliminar_productos(productoId);
        }

        // Obtener producto por ID
        public productoDto ObtenerPorId(int productoId)
        {
            return this.clienteSOAP.obtener_por_id(productoId);
        }

        // Listar todos los productos
        public List<productoDto> ListarTodos()
        {
            return this.clienteSOAP.listar_todos().ToList<productoDto>();
        }

        // Insertar producto usando DTO
        public int Insertar(productoDto producto)
        {
            return this.clienteSOAP.insertar_productos(
                producto.tipoProducto.tipoProductoId,
                producto.nombre,
                producto.presentacion,
                producto.precioUnitario,
                producto.stock,
                producto.activo
            );
        }

        // Modificar producto usando DTO
        public int Modificar(productoDto producto)
        {
            return this.clienteSOAP.modificar_productos(
                producto.productoId,
                producto.tipoProducto.tipoProductoId,
                producto.nombre,
                producto.presentacion,
                producto.precioUnitario,
                producto.stock,
                producto.activo
            );
        }
        public List<productoDto> ListarActivos()
        {
            return this.clienteSOAP.listar_productos_activos().ToList<productoDto>();
        }

        public List<productoDto> ListarPorTipo(String nombreTipo)
        {
            return this.clienteSOAP.listar_productos_por_tipo(nombreTipo).ToList<productoDto>();
        }

        // ==========================================
        // MÉTODOS NUEVOS Y ACTUALIZADOS (INTEGRACIÓN)
        // ==========================================

        /// <summary>
        /// Verifica si el producto tiene registros asociados (Ventas, Citas, etc.)
        /// </summary>
        /// <param name="productoId">ID del producto a verificar</param>
        /// <returns>Mayor a 0 si tiene información, 0 si está limpio.</returns>
        public int VerificarSiElProductoTieneInformacion(int productoId)
        {
            // Este método evita eliminar productos que romperían la integridad referencial
            return this.clienteSOAP.VerificarSiElProductoTieneInformacion(productoId);
        }

        /// <summary>
        /// Búsqueda avanzada optimizada. Ahora filtra por Tipo directamente en Base de Datos.
        /// </summary>
        /// <param name="nombre">Texto a buscar en el nombre</param>
        /// <param name="rango">ID del rango de precio (1, 2, 3)</param>
        /// <param name="activo">Estado (1, 0)</param>
        /// <param name="tipoId">ID del Tipo de Producto (0 para Todos)</param>
        public List<productoDto> ListarBusquedaAvanzada(string nombre, string rango, string activo, int tipoId)
        {
            // 1. Sanitización de nulos para evitar errores en el SOAP Java
            nombre = nombre ?? "";
            rango = rango ?? "";
            activo = activo ?? "";
            // El tipoId (int) no necesita null check, si viene 0 el SP lo maneja como "Todos"


            // 2. Llamada al servicio actualizado con el 4to parámetro
            var respuesta = this.clienteSOAP.ListasBusquedaProductosAvanzada(nombre, rango, activo, tipoId);

            // 3. Validación de respuesta nula
            if (respuesta == null)
            {
                return new List<productoDto>();
            }

            return respuesta.ToList<productoDto>();
        }

        public List<productoDto> ListarBusquedaAvanzadaPaginado(string nombre, string rango, Boolean activo, int pagina)
        {
            // 1. Sanitización de nulos para evitar errores en el SOAP Java
            nombre = nombre ?? "";
            rango = rango ?? "";
            //EJEMPLO DE LO QUE DEVUELVE EL DTO
            //productoDto pro= new productoDto();
            //pro.nombre = nombre;
            //pro.precioUnitario = 12.2;
            //pro.activo = true;
            //pro.tipoProducto.tipoProductoId = 1;
            //pro.tipoProducto.nombre = "a";
            //pro.tipoProducto.descripcion = "d";
            // El tipoId (int) no necesita null check, si viene 0 el SP lo maneja como "Todos"
            var respuesta = this.clienteSOAP.buscar_productos_paginados(nombre, rango, activo, pagina);
            if (respuesta == null)
            {
                return new List<productoDto>();

            }
            else
            {
                return respuesta.ToList<productoDto>();
            }
        }

    }
}
