using SoftPetBussiness.DetalleDocumentoDePagoClient; // Ajusta al namespace de tu Service Reference
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class DetalleDocumentoDePagoBO
    {
        private DetallesDocumentoDePagoClient clienteSOAP;

        public DetalleDocumentoDePagoBO()
        {
            this.clienteSOAP = new DetallesDocumentoDePagoClient();
        }
        /*
         *  detalleDocumentoPagoDto detalle = new detalleDocumentoPagoDto();
            detalle.activo = activo;
            detalle.cantidad = cantidad;
            detalle.descripcion = descripcion;
            detalle.documentoPago.documentoPagoId = documentoPagoId; //el listar solo me da el documento de pago id
            detalle.nroItem = nroItem;
            detalle.precioUnitario = precioUnitario;
            detalle.producto.productoId = productoId; //el listar solo me da el producto id
            detalle.servicio.servicioId = servicioId; //el listar solo me da el servicio id
            detalle.valorVenta = valorVenta;
         * 
         * 
         * 
         * 
         * 
         * 
         */
        // ===================================================
        //  INSERTAR (MISMA FIRMA QUE EL SERVICIO EN JAVA)
        //  Java:
        //  insertar_Detalle_Documento_De_Pago(int documentoPagoId, int servicioId,
        //                                      int productoId, int nroItem,
        //                                      String descripcion, int cantidad,
        //                                      double precioUnitario, double valorVenta, boolean activo)
        // ===================================================
        public int Insertar(
            int documentoPagoId,
            int servicioId,
            int productoId,
            int nroItem,
            string descripcion,
            int cantidad,
            double precioUnitario,
            double valorVenta,
            bool activo)
        {
            return this.clienteSOAP.insertar_Detalle_Documento_De_Pago(
                documentoPagoId,
                servicioId,
                productoId,
                nroItem,
                descripcion,
                cantidad,
                precioUnitario,
                valorVenta,
                activo
            );



        }

        // ===================================================
        //  MODIFICAR (MISMA FIRMA QUE EL SERVICIO EN JAVA)
        //  Java:
        //  modificar_Detalle_Documento_De_Pago(int ddpId, int documentoPagoId,
        //                                        int servicioId, int productoId,
        //                                        int nroItem, String descripcion,
        //                                        int cantidad, double precioUnitario,
        //                                        double valorVenta, boolean activo)
        // ===================================================
        public int Modificar(
            int ddpId,
            int documentoPagoId,
            int servicioId,
            int productoId,
            int nroItem,
            string descripcion,
            int cantidad,
            double precioUnitario,
            double valorVenta,
            bool activo)
        {
            return this.clienteSOAP.modificar_Detalle_Documento_De_Pago(
                ddpId,
                documentoPagoId,
                servicioId,
                productoId,
                nroItem,
                descripcion,
                cantidad,
                precioUnitario,
                valorVenta,
                activo
            );
        }

        // =========================
        //        ELIMINAR
        // =========================
        public int Eliminar(int ddpId)
        {
            return this.clienteSOAP.eliminar_Detalle_Documento_De_Pago(ddpId);
        }

        // =========================
        //      OBTENER POR ID
        // =========================
        public detalleDocumentoPagoDto ObtenerPorId(int ddpId)
        {
            return this.clienteSOAP.obtener_por_id(ddpId);
        }

        // =========================
        //       LISTAR TODOS
        // =========================
        public List<detalleDocumentoPagoDto> ListarTodos()
        {
            return this.clienteSOAP
                .listar_todos()
                .ToList<detalleDocumentoPagoDto>();
        }
    }
}
