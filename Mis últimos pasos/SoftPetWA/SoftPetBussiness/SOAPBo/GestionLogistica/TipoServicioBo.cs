using SoftPetBussiness.ServicioClient;
using SoftPetBussiness.TipoServicioClient;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class TipoServicioBO
    {
        private TiposServicioClient clienteSOAP;

        //MUCHO CUIDADO CON TIPO DE SERVICIO
        //como tenemos 2 servicios diferentes, uno es ServicioClient y otro TipoServicioClient
        //en este BO estamos usando el TipoServicioClient que es el que maneja los tipos de servicios
        public TipoServicioBO()
        {
            this.clienteSOAP = new TiposServicioClient();
        }

        // Insertar tipo de servicio
        public int Insertar(string nombre, string descripcion, bool activo)
        {
            return this.clienteSOAP.insertar_tiposervicios(nombre, descripcion, activo);
        }

        // Modificar tipo de servicio
        public int Modificar(int tipoServicioId, string nombre, string descripcion, bool activo)
        {
            return this.clienteSOAP.modificar_tiposervicios(tipoServicioId, nombre, descripcion, activo);
        }

        // Eliminar tipo de servicio
        public int Eliminar(int tipoServicioId)
        {
            return this.clienteSOAP.eliminar_tiposervicios(tipoServicioId);
        }

        // Obtener tipo de servicio por ID
        public SoftPetBussiness.TipoServicioClient.tipoServicioDto ObtenerPorId(int tipoServicioId)
        {
            return this.clienteSOAP.obtener_por_id(tipoServicioId);
        }

        // Listar todos los tipos de servicio
        public List<SoftPetBussiness.TipoServicioClient.tipoServicioDto> ListarTodos()
        {
            return this.clienteSOAP.listar_todos().ToList<SoftPetBussiness.TipoServicioClient.tipoServicioDto>();
        }

        // Insertar tipo de servicio usando DTO
        public int Insertar(SoftPetBussiness.TipoServicioClient.tipoServicioDto tipoServicio)
        {
            return this.clienteSOAP.insertar_tiposervicios(
                tipoServicio.nombre,
                tipoServicio.descripcion,
                tipoServicio.activo
            );
        }

        // Modificar tipo de servicio usando DTO
        public int Modificar(SoftPetBussiness.TipoServicioClient.tipoServicioDto tipoServicio)
        {
            return this.clienteSOAP.modificar_tiposervicios(
                tipoServicio.tipoServicioId,
                tipoServicio.nombre,
                tipoServicio.descripcion,
                tipoServicio.activo
            );
        }
    }
}
