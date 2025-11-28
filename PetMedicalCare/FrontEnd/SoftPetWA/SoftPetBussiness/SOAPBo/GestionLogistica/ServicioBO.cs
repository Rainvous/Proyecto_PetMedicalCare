
using SoftPetBussiness.ServicioClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;

namespace SoftPetBusiness
{
    public class ServicioBO
    {
        private ServiciosClient clienteSOAP;

        public ServicioBO()
        {
            this.clienteSOAP = new ServiciosClient();
        }

        // Insertar servicio
        public int Insertar(int tipoServicioId, string nombre, string descripcion, double costo, string estado, bool activo)
        {
            return this.clienteSOAP.insertar_servicios(tipoServicioId, nombre, descripcion, costo, estado, activo);
        }

        // Modificar servicio
        public int Modificar(int servicioId, int tipoServicioId, string nombre, string descripcion, double costo, string estado, bool activo)
        {
            return this.clienteSOAP.modificar_servicios(servicioId, tipoServicioId, nombre, descripcion, costo, estado, activo);
        }

        // Eliminar servicio
        public int Eliminar(int servicioId)
        {
            return this.clienteSOAP.eliminar_servicios(servicioId);
        }

        // Obtener servicio por ID
        public servicioDto ObtenerPorId(int servicioId)
        {
            return this.clienteSOAP.obtener_por_id(servicioId);
        }

        // Listar todos los servicios
        public List<servicioDto> ListarTodos()
        {
            return this.clienteSOAP.listar_todos().ToList<servicioDto>();
        }
        public int Insertar(servicioDto servicio)
        {
            return this.clienteSOAP.insertar_servicios(
                servicio.tipoServicio.tipoServicioId,
                servicio.nombre,
                servicio.descripcion,
                servicio.costo,
                servicio.estado,
                servicio.activo
            );
        }

        // Modificar servicio (recibe un DTO completo)
        public int Modificar(servicioDto servicio)
        {
            return this.clienteSOAP.modificar_servicios(
                servicio.servicioId,
                servicio.tipoServicio.tipoServicioId,
                servicio.nombre,
                servicio.descripcion,
                servicio.costo,
                servicio.estado,
                servicio.activo
            );
        }
        public List<servicioDto> listarServicioActivos()
        {
            return this.clienteSOAP.listar_servicios_activos().ToList<servicioDto>();

        }
        public List<servicioDto> listarServicioPorTipo(string tipoServicio)
        {
            return this.clienteSOAP.listar_servicios_por_tipo(tipoServicio).ToList<servicioDto>();
        }

        public int VerificarSiElServicioTieneInformacion(int productoId)
        {
            // Este método evita eliminar productos que romperían la integridad referencial
            return this.clienteSOAP.VerificarSiElServicioTieneInformacion(productoId);
        }
        /*
         *------------------------------
         * BUSQUEDA AVANZADA DE SERVICIOS
         * Por nombre, rango de costo y activo
         * --------------------------------
         */
        // Agrega este método actualizado en tu ServicioBO.cs
        public List<servicioDto> listarPorNombreRangoActivo(string nombre, string rango, string activo, int tipoId)
        {
            nombre = nombre ?? "";
            rango = rango ?? "";
            activo = activo ?? "";

            // Llamada al WS actualizado (Asegúrate de haber actualizado la referencia)
            var respuesta = this.clienteSOAP.ListasBusquedaAvanzada2(nombre, rango, activo, tipoId);

            if (respuesta == null) return new List<servicioDto>();

            return respuesta.ToList<servicioDto>();
        }
        public List<servicioDto> ListarBusquedaAvanzadaPaginado(String nombre, String rango, Boolean activo, int paginado)
        {
            var respuesta = this.clienteSOAP.buscar_servicios_paginados(nombre, rango, activo, paginado);
            if (respuesta == null) return new List<servicioDto>();
            return respuesta.ToList<servicioDto>();
        }

    }
}
