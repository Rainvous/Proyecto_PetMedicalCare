using SoftPetBusiness;
using SoftPetBussiness.DocumentoDePagoClient;
using SoftPetBussiness.PersonaClient; // Necesario para roles y datos de persona
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Secretaria_Reportes : System.Web.UI.Page
    {
        // ViewModel
        public class ReporteComprobanteVM
        {
            public int DocumentoID { get; set; }
            public int PersonaID { get; set; } // Agregado para lógica de correo
            public string TipoDoc { get; set; }
            public string SerieNumero { get; set; }
            public string FechaEmision { get; set; }
            public string ClienteNombre { get; set; }
            public string Total { get; set; }
            public string Icono { get; set; }
            public string MetodoPago { get; set; }
        }

        private DocumentoDePagoBO documentoBO;
        private PersonaBO personaBO;

        public Secretaria_Reportes()
        {
            this.documentoBO = new DocumentoDePagoBO();
            this.personaBO = new PersonaBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                txtFechaBusqueda.Text = DateTime.Now.ToString("yyyy-MM-dd");
                CargarReporteComprobantes();
            }
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            CargarReporteComprobantes();
        }

        private void CargarReporteComprobantes()
        {
            string fechaString = txtFechaBusqueda.Text;

            if (string.IsNullOrEmpty(fechaString))
            {
                fechaString = DateTime.Now.ToString("yyyy-MM-dd");
            }

            List<documentoPagoDto> resultados = documentoBO.listarporfecha(fechaString);
            List<ReporteComprobanteVM> listaVM = new List<ReporteComprobanteVM>();

            if (resultados != null && resultados.Count > 0)
            {
                foreach (var doc in resultados)
                {
                    ReporteComprobanteVM vm = new ReporteComprobanteVM();
                    vm.DocumentoID = doc.documentoPagoId;

                    // IMPORTANTE: Capturar el ID de la persona para usarlo luego en el envío de correo
                    vm.PersonaID = doc.persona != null ? doc.persona.personaId : 0;

                    vm.SerieNumero = $"{doc.serie}-{doc.numero}";
                    vm.Total = $"S/ {doc.total.ToString("N2")}";

                    if (DateTime.TryParse(doc.fechaEmisionString, out DateTime fechaDt))
                        vm.FechaEmision = fechaDt.ToString("dd/MM/yyyy");
                    else
                        vm.FechaEmision = doc.fechaEmisionString;

                    string tipoDocStr = doc.tipoDocumento.ToString().ToUpper();
                    bool esFactura = tipoDocStr.Contains("FACTURA");

                    vm.TipoDoc = esFactura ? "FACTURA" : "BOLETA";
                    vm.Icono = esFactura ? "fas fa-file-invoice-dollar" : "fas fa-receipt";

                    string nombrePersona = doc.persona != null ? doc.persona.nombre : "Cliente General";
                    if (esFactura && doc.persona != null)
                        vm.ClienteNombre = $"{nombrePersona} (RUC: {doc.persona.ruc})";
                    else
                        vm.ClienteNombre = nombrePersona;

                    string metodoPagoNombre = "Efectivo";
                    string met = doc.metodoDePago != null ? doc.metodoDePago.nombreStr : "";

                    if (!string.IsNullOrEmpty(met)) metodoPagoNombre = met;

                    vm.ClienteNombre += $" <span class='text-muted ms-2' style='font-size:0.85em'>| {metodoPagoNombre}</span>";

                    listaVM.Add(vm);
                }
                rptComprobantes.DataSource = listaVM;
                rptComprobantes.DataBind();
                divNoResultados.Visible = false;
                pnlComprobantes.Visible = true;
            }
            else
            {
                rptComprobantes.DataSource = null;
                rptComprobantes.DataBind();
                divNoResultados.Visible = true;
            }

            // Ocultar carga al terminar de buscar
            ScriptManager.RegisterStartupScript(this, GetType(), "hideLoading", "ocultarCarga();", true);
        }

        protected void rptComprobantes_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                LinkButton btn = (LinkButton)e.Item.FindControl("btnPdf");
                ScriptManager scriptManager = ScriptManager.GetCurrent(this);
                if (scriptManager != null && btn != null)
                {
                    // Necesario para descarga de archivos
                    scriptManager.RegisterPostBackControl(btn);
                }
            }
        }

        protected void rptComprobantes_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "VerPDF")
            {
                try
                {
                    string[] argumentos = e.CommandArgument.ToString().Split('|');
                    if (argumentos.Length >= 2)
                    {
                        int idDoc = int.Parse(argumentos[0]);
                        string tipoDocVisual = argumentos[1];

                        byte[] pdfBytes = documentoBO.muestraReporteDocumentoDePago(tipoDocVisual, idDoc);

                        if (pdfBytes != null && pdfBytes.Length > 0)
                        {
                            string nombreArchivo = $"Comprobante_{tipoDocVisual}_{idDoc}.pdf";
                            documentoBO.abrirPdf(Response, pdfBytes, nombreArchivo);
                        }
                        else
                        {
                            mostrarAlerta("No se pudo generar el reporte PDF.");
                        }
                    }
                }
                catch (System.Threading.ThreadAbortException) { }
                catch (Exception ex)
                {
                    string safeMsg = ex.Message.Replace("'", "");
                    // Usamos mostrarMensajeError en lugar de alert
                    ScriptManager.RegisterStartupScript(this, GetType(), "ErrorPdf", $"mostrarMensajeError('Error PDF: {safeMsg}');", true);
                }
                // No llamamos a ocultarCarga aquí porque el ThreadAbortException detiene el script
                // pero hemos puesto un setTimeout en el JS del botón.
            }
            else if (e.CommandName == "PreparaEmail")
            {
                // Argumentos: ID_DOC | TIPO_DOC | ID_PERSONA
                string[] args = e.CommandArgument.ToString().Split('|');
                if (args.Length == 3)
                {
                    int idDoc = int.Parse(args[0]);
                    string tipoDoc = args[1];
                    int idPersona = int.Parse(args[2]);

                    // Guardamos en hidden fields para usar al enviar
                    hfIdDocModal.Value = idDoc.ToString();
                    hfTipoDocModal.Value = tipoDoc;

                    // Lógica CLIENTE vs GUEST
                    string emailSugerido = "";

                    // Obtenemos el Rol
                    // Nota: Asumo que rolDto tiene una propiedad para identificar si es Guest o Admin o Cliente.
                    // Si no tienes acceso directo al nombre del rol, verifica cómo te devuelve el objeto.
                    // Aquí asumo lógica defensiva.
                    try
                    {
                        var rol = personaBO.obtenerRol_DeCliente(idPersona);

                        // Si es guest, el email va vacío. Si es Cliente, buscamos el email.
                        // Ajusta "Guest" según como se llame exactamente en tu BD
                        bool esGuest = false;
                        if (rol != null && (rol.nombre ?? "").ToUpper().Contains("GUEST"))
                        {
                            esGuest = true;
                        }

                        if (!esGuest)
                        {
                            // Es cliente regular, obtenemos su correo
                            emailSugerido = personaBO.obtenerCorreo_DeCliente(idPersona);
                        }
                    }
                    catch
                    {
                        // Si falla algo, asumimos vacío
                        emailSugerido = "";
                    }

                    txtEmailDestino.Text = emailSugerido;

                    // Abrir el modal via JS
                    ScriptManager.RegisterStartupScript(this, GetType(), "AbrirModal", "ocultarCarga(); abrirModalEmail();", true);
                }
            }
        }

        protected void btnEnviarCorreoConfirmado_Click(object sender, EventArgs e)
        {
            try
            {
                int idDoc = int.Parse(hfIdDocModal.Value);
                string tipoDoc = hfTipoDocModal.Value;
                string email = txtEmailDestino.Text;

                // Llamada al BO
                string resultado = documentoBO.EnviarComprobantePorEmail(email, tipoDoc, idDoc);

                // Lógica de respuesta
                if (string.IsNullOrEmpty(resultado) || resultado.ToLower().Contains("exito") || resultado.ToLower().Contains("ok"))
                {
                    // ÉXITO: Llamamos a la función JS bonita
                    // Nota: Si 'resultado' viene vacío, ponemos un mensaje default.
                    string msg = string.IsNullOrEmpty(resultado) ? "El comprobante ha sido enviado correctamente." : resultado;

                    ScriptManager.RegisterStartupScript(this, GetType(), "ExitoJs", $"mostrarMensajeExito('{msg}');", true);
                }
                else
                {
                    // ERROR: Si el servicio devuelve un texto de error
                    ScriptManager.RegisterStartupScript(this, GetType(), "ErrorJs", $"mostrarMensajeError('{resultado}');", true);
                }
            }
            catch (Exception ex)
            {
                // ERROR: Excepción de código
                // Importante: Escapamos las comillas simples del mensaje de error para no romper el JS
                string safeMsg = ex.Message.Replace("'", "").Replace("\n", " ");
                ScriptManager.RegisterStartupScript(this, GetType(), "ErrorEx", $"mostrarMensajeError('Error técnico: {safeMsg}');", true);
            }
        }

        private void mostrarAlerta(string mensaje)
        {
            string script = $"alert('{mensaje}');";
            ScriptManager.RegisterStartupScript(this, GetType(), "AlertaInfo", script, true);
        }
    }
}