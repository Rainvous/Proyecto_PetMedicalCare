using SoftPetBussiness.DetalleRecetaClient;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class DetalleRecetaBO
    {
        private DetallesRecetaClient clienteSOAP;

        public DetalleRecetaBO()
        {
            this.clienteSOAP = new DetallesRecetaClient();
        }

        public int Insertar(int recetaMedicaId, string descripcion, string presentacion, string viaAdmin,
                            string dosis, string frecuencia, string duracion, string indicacion, string cantidad, bool activo)
        {
            return this.clienteSOAP.insertar_detallereceta(
                recetaMedicaId, descripcion, presentacion, viaAdmin, dosis, frecuencia, duracion, indicacion, cantidad, activo
            );
        }

        public int Modificar(int detalleId, int recetaMedicaId, string descripcion, string presentacion, string viaAdmin,
                             string dosis, string frecuencia, string duracion, string indicacion, string cantidad, bool activo)
        {
            return this.clienteSOAP.modificar_detallereceta(
                detalleId, recetaMedicaId, descripcion, presentacion, viaAdmin, dosis, frecuencia, duracion, indicacion, cantidad, activo
            );
        }

        public int Eliminar(int detalleId)
        {
            return this.clienteSOAP.eliminar_detallereceta(detalleId);
        }

        public detalleRecetaDto ObtenerPorId(int detalleId)
        {
            return this.clienteSOAP.obtener_por_id(detalleId);
        }

        public List<detalleRecetaDto> ListarDetallesPorIdDeReceta(int recetaId)
        {
            var resultado = this.clienteSOAP.listar_detalles_por_receta(recetaId);
            // CORRECCIÓN: Validar null antes de convertir a lista
            if (resultado == null) return new List<detalleRecetaDto>();
            return resultado.ToList<detalleRecetaDto>();
        }
    }
}