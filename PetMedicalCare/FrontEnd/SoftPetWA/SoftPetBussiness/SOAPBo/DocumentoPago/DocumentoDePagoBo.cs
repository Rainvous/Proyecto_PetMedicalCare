using SoftPetBussiness.DetalleDocumentoDePagoClient;
using SoftPetBussiness.DocumentoDePagoClient; // Ajusta al namespace de tu Service Reference
using SoftPetBussiness.PersonaClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;

namespace SoftPetBusiness
{
    public class DocumentoDePagoBO
    {
        private DocumentosDePagoClient clienteSOAP;

        public DocumentoDePagoBO()
        {
            this.clienteSOAP = new DocumentosDePagoClient();



        }
        /*
         *  documentoPagoDto doc = new documentoPagoDto();
            doc.activo = activo;
            doc.estado = estado;
            doc.fechaEmision = fechaEmision;  //recomiendo NO usar este que es formato date 
            
            doc.igvTotal = igvTotal;
            doc.metodoDePagoId = metodoDePagoId;
            doc.numero = numero;
            doc.personaId = personaId;
            doc.serie = serie;
            doc.subtotal = subtotal;
            doc.total = total;
            doc.igvTotal = igvTotal;
            doc.tipoDocumento = tipoDocumento;
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
        //  insertar_Documento_De_Pago(int metodoDePagoId, int personaId,
        //                              String tipoDocumento, String serie,
        //                              String numero, String fechaEmision,
        //                              String estado, double subtotal,
        //                              double igvTotal, double total, boolean activo)
        // ===================================================
        public int Insertar(
            int metodoDePagoId,
            int personaId,
            string tipoDocumento,
            string serie,
            string numero,
            string fechaEmision, // Formato de fecha 2025-11-30 ; yyyy-MM-dd
            string estado,
            double subtotal,
            double igvTotal,
            double total,
            bool activo)
        {
            return this.clienteSOAP.insertar_Documento_De_Pago(
                metodoDePagoId,
                personaId,
                tipoDocumento,
                serie,
                numero,
                fechaEmision,
                estado,
                subtotal,
                igvTotal,
                total,
                activo
            );



        }

        // ===================================================
        //  MODIFICAR (MISMA FIRMA QUE EL SERVICIO EN JAVA)
        //  Java:
        //  modificar_Documento_De_Pago(int documentoPagoId, int metodoDePagoId,
        //                                int personaId, String tipoDocumento, String serie,
        //                                String numero, String fechaEmision, String estado,
        //                                double subtotal, double igvTotal, double total, boolean activo)
        // ===================================================
        public int Modificar(
            int documentoPagoId,
            int metodoDePagoId,
            int personaId,
            string tipoDocumento,
            string serie,
            string numero,
            string fechaEmision,
            string estado,
            double subtotal,
            double igvTotal,
            double total,
            bool activo)
        {
            return this.clienteSOAP.modificar_Documento_De_Pago(
                documentoPagoId,
                metodoDePagoId,
                personaId,
                tipoDocumento,
                serie,
                numero,
                fechaEmision,
                estado,
                subtotal,
                igvTotal,
                total,
                activo
            );
        }

        // =========================
        //        ELIMINAR
        // =========================
        public int Eliminar(int documentoPagoId)
        {
            return this.clienteSOAP.eliminar_Documento_De_Pago(documentoPagoId);
        }

        // =========================
        //      OBTENER POR ID
        // =========================
        public SoftPetBussiness.DocumentoDePagoClient.documentoPagoDto ObtenerPorId(int documentoPagoId)
        {
            return this.clienteSOAP.obtener_por_id(documentoPagoId);
        }

        // =========================
        //       LISTAR TODOS
        // =========================
        public List<SoftPetBussiness.DocumentoDePagoClient.documentoPagoDto> ListarTodos()
        {
            return this.clienteSOAP
                .listar_todos()
                .ToList<SoftPetBussiness.DocumentoDePagoClient.documentoPagoDto>();
        }

        public List<String> GeneracionDeSiguienteBoletaOFactura(string documentoPagoId)
        {
            //F para factura
            //B para boleta
            return this.clienteSOAP.GeneracionDeSiguienteBoletaOFactura(documentoPagoId).ToList<String>();
        }

        public List<SoftPetBussiness.DocumentoDePagoClient.documentoPagoDto> listarporfecha(string fecha)
        {
            var lista = this.clienteSOAP.listar_por_fecha(fecha);
            if (lista != null)
            {
                return lista.ToList<SoftPetBussiness.DocumentoDePagoClient.documentoPagoDto>();
            }
            return null;
        }
        /*
         *  SoftPetBussiness.DocumentoDePagoClient.documentoPagoDto doc = new SoftPetBussiness.DocumentoDePagoClient.documentoPagoDto();
            doc.activo = activo;
            doc.estado = estado;
            doc.fechaEmision = fechaEmision;  //recomiendo NO usar este que es formato date 
            
            doc.igvTotal = igvTotal;
            doc.metodoDePagoId = metodoDePagoId;
            doc.numero = numero;
            doc.persona.nombre
            doc.serie = serie;
            doc.subtotal = subtotal;
            doc.total = total;
            doc.igvTotal = igvTotal;
            doc.metodoDePago.metodoDePagoId
            doc.metodoDePago.nombre;
            doc.persona.ruc = 1232;

            doc.tipoDocumento = SoftPetBussiness.DocumentoDePagoClient.tipoDocumentoDePago.BOLETA;
            doc.persona.personaId = personaId;
            doc.persona.nombre = "";
         * 
         * 
         * 
         * 
         */
        public byte[] muestraReporteDocumentoDePago(String tipoDocumento, int idDocumento)
        {
            byte[] reporte = this.clienteSOAP.generar_comprobante_de_pago_pdf(
                tipoDocumento,
                idDocumento
            );
            return reporte;
        }
        public void abrirPdf(HttpResponse response, byte[] pdfBytes, string nombreArchivo)
        {
            response.Clear();
            response.ContentType = "application/pdf";
            response.AddHeader("Content-Disposition", $"inline; filename={nombreArchivo}");
            response.BinaryWrite(pdfBytes);
            response.End();

        }
        public String EnviarComprobantePorEmail(String email, String tipoDoc, int idcomprobante)
        {
            return this.clienteSOAP.enviar_comprobante_al_correo(email, tipoDoc, idcomprobante);
        }

    }
}
