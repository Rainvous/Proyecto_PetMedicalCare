using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.ProductoClient;
using SoftPetBussiness.ServicioClient;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.DetalleServicioClient;

namespace SoftPetWA
{
    public partial class Secretaria_PuntoVenta : System.Web.UI.Page
    {
        // BOs
        private PersonaBO boPersona = new PersonaBO();
        private ProductoBO boProducto = new ProductoBO();
        private ServicioBO boServicio = new ServicioBO();
        private MetodoDePagoBO boMetodoPago = new MetodoDePagoBO();
        private DocumentoDePagoBO boDocumento = new DocumentoDePagoBO();
        private DetalleDocumentoDePagoBO boDetalleDoc = new DetalleDocumentoDePagoBO();
        private DetalleServicioBO boDetalleServicio = new DetalleServicioBO();
        private CitaAtencionBO boCita = new CitaAtencionBO();

        // Propiedades en ViewState para Estado de UI
        private DataTable CarritoDT
        {
            get { return ViewState["CarritoDT"] as DataTable; }
            set { ViewState["CarritoDT"] = value; }
        }

        public string FiltroTipoActual
        {
            get { return ViewState["FiltroTipoActual"] as string ?? "PRODUCTOS"; }
            set { ViewState["FiltroTipoActual"] = value; }
        }

        public int PaginaActual
        {
            get { return ViewState["PaginaActual"] != null ? (int)ViewState["PaginaActual"] : 1; }
            set { ViewState["PaginaActual"] = value; }
        }

        public bool EsClienteGuest
        {
            get { return ViewState["EsClienteGuest"] != null && (bool)ViewState["EsClienteGuest"]; }
            set { ViewState["EsClienteGuest"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Configuración Inicial
                InicializarInterfaz();

                // Lógica de Redirección desde Agenda
                if (Session["Comprobante_CitaId"] != null)
                {
                    int citaId = Convert.ToInt32(Session["Comprobante_CitaId"]);
                    int personaId = Session["Comprobante_PersonaId"] != null ? Convert.ToInt32(Session["Comprobante_PersonaId"]) : 0;
                    CargarDatosDeCita(citaId, personaId);
                }
                else
                {
                    // Venta Normal
                    CargarDatosClienteDefault();
                    InicializarCarritoVacio();
                }
            }
        }

        private void InicializarInterfaz()
        {
            CargarMetodosDePago();
            ConfigurarTiposComprobante(false);

            // Inicia mostrando productos en página 1
            FiltroTipoActual = "PRODUCTOS";
            PaginaActual = 1;
            ActualizarEstilosTabs();
            CargarCatalogoPaginado();
        }

        #region "Lógica de Catálogo y Paginación"

        // Eventos de Tabs
        protected void btnTabProductos_Click(object sender, EventArgs e)
        {
            FiltroTipoActual = "PRODUCTOS";
            PaginaActual = 1;
            txtBusquedaProducto.Text = "";
            ActualizarEstilosTabs();
            CargarCatalogoPaginado();
        }

        protected void btnTabServicios_Click(object sender, EventArgs e)
        {
            FiltroTipoActual = "SERVICIOS";
            PaginaActual = 1;
            txtBusquedaProducto.Text = "";
            ActualizarEstilosTabs();
            CargarCatalogoPaginado();
        }

        private void ActualizarEstilosTabs()
        {
            if (FiltroTipoActual == "PRODUCTOS")
            {
                btnTabProductos.CssClass = "nav-link active";
                btnTabServicios.CssClass = "nav-link";
            }
            else
            {
                btnTabProductos.CssClass = "nav-link";
                btnTabServicios.CssClass = "nav-link active";
            }
        }

        // Búsqueda
        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            PaginaActual = 1; // Reseteamos a página 1 al buscar
            CargarCatalogoPaginado();
        }

        // Paginación
        protected void btnPaginaAnterior_Click(object sender, EventArgs e)
        {
            if (PaginaActual > 1)
            {
                PaginaActual--;
                CargarCatalogoPaginado();
            }
        }

        protected void btnPaginaSiguiente_Click(object sender, EventArgs e)
        {
            PaginaActual++;
            CargarCatalogoPaginado();
        }

        // --- MÉTODO PRINCIPAL DE CARGA ---
        private void CargarCatalogoPaginado()
        {
            string terminoBusqueda = txtBusquedaProducto.Text.Trim();

            // Estructura DataTable para el Repeater
            DataTable dtCatalogo = new DataTable();
            dtCatalogo.Columns.Add("ID", typeof(int));
            dtCatalogo.Columns.Add("Tipo", typeof(string));
            dtCatalogo.Columns.Add("IconoCss", typeof(string));
            dtCatalogo.Columns.Add("Nombre", typeof(string));
            dtCatalogo.Columns.Add("Descripcion", typeof(string));
            dtCatalogo.Columns.Add("PrecioFormateado", typeof(string));
            dtCatalogo.Columns.Add("StockTexto", typeof(string));

            CultureInfo culture = CultureInfo.CreateSpecificCulture("es-PE");
            bool hayResultados = false;

            try
            {
                if (FiltroTipoActual == "PRODUCTOS")
                {
                    // Llamada al BO Paginado de Productos
                    // Parametros: nombre, rango(null), activo(true), pagina
                    var lista = boProducto.ListarBusquedaAvanzadaPaginado("", "", true, PaginaActual);

                    if (lista != null && lista.Count > 0)
                    {
                        hayResultados = true;
                        foreach (var p in lista)
                        {
                            dtCatalogo.Rows.Add(
                                p.productoId,
                                "P",
                                "fas fa-box",
                                p.nombre,
                                p.presentacion,
                                ((decimal)p.precioUnitario).ToString("C", culture),
                                $"Stock: {p.stock}"
                            );
                        }
                    }
                }
                else // SERVICIOS
                {
                    // Llamada al BO Paginado de Servicios
                    // Parametros: nombre, rango(null), activo(true), pagina
                    var lista = boServicio.ListarBusquedaAvanzadaPaginado(terminoBusqueda, "", true, PaginaActual);

                    if (lista != null && lista.Count > 0)
                    {
                        hayResultados = true;
                        foreach (var s in lista)
                        {
                            dtCatalogo.Rows.Add(
                                s.servicioId,
                                "S",
                                "fas fa-stethoscope",
                                s.nombre,
                                s.descripcion,
                                ((decimal)s.costo).ToString("C", culture),
                                "Servicio"
                            );
                        }
                    }
                }
            }
            catch (Exception)
            {
                hayResultados = false;
            }

            // Bindear Grid
            rptProductos.DataSource = dtCatalogo;
            rptProductos.DataBind();

            // Gestionar botones de paginación UI
            lblPaginaActual.Text = PaginaActual.ToString();
            btnPaginaAnterior.Enabled = (PaginaActual > 1);

            // Si no hay resultados y estamos en página > 1, podríamos retroceder, 
            // pero por UX simple deshabilitamos "Siguiente" si la lista vino vacía.
            // OJO: Si tu paginado devuelve lista vacía cuando no hay más, esto funciona.
            btnPaginaSiguiente.Enabled = hayResultados;

            // Si estamos en pag > 1 y no trajo nada, es que llegamos al final, deshabilitamos el btn
            if (!hayResultados && PaginaActual > 1)
            {
                // Opcional: Mostrar alerta o simplemente deshabilitar
                btnPaginaSiguiente.Enabled = false;
            }

            updCatalogo.Update();
        }

        protected void rptProductos_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "Agregar")
            {
                string[] args = e.CommandArgument.ToString().Split('|');
                string tipo = args[0];
                int id = Convert.ToInt32(args[1]);

                if (tipo == "P")
                {
                    var p = boProducto.ObtenerPorId(id);
                    if (p != null) AgregarItemAlCarrito(p.productoId, 0, p.nombre, (decimal)p.precioUnitario);
                }
                else if (tipo == "S")
                {
                    var s = boServicio.ObtenerPorId(id);
                    if (s != null) AgregarItemAlCarrito(0, s.servicioId, s.nombre, (decimal)s.costo);
                }
            }
        }

        #endregion

        #region "Lógica de Integración con Cita (Simplificada)"

        private void CargarDatosDeCita(int citaId, int personaIdSession)
        {
            InicializarCarritoVacio();
            EsClienteGuest = false;

            try
            {
                // 1. Cargar Cliente y Mascota desde la Cita
                var cita = boCita.ObtenerPorId(citaId);

                if (cita != null && cita.mascota != null && cita.mascota.persona != null)
                {
                    var p = cita.mascota.persona;
                    Session["Comprobante_PersonaId"] = p.personaId;

                    lblClienteNombre.Text = p.nombre;

                    bool tieneRuc = (p.ruc != null && p.ruc.ToString().Length == 11);
                    string info = tieneRuc ? $"RUC: {p.ruc}" : $"Doc: {p.nroDocumento}";

                    lblClienteInfo.Text = $"{info} | Mascota: {cita.mascota.nombre}";
                    txtRucCliente.Text = tieneRuc ? p.ruc.ToString() : "";
                    ConfigurarTiposComprobante(tieneRuc);
                }
                else
                {
                    // Fallback si falla la carga profunda del objeto cita
                    var p = boPersona.ObtenerPorId(personaIdSession);
                    if (p != null)
                    {
                        lblClienteNombre.Text = p.nombre;
                        ConfigurarTiposComprobante(false);
                    }
                }

                // 2. Cargar Servicios de la Cita al Carrito
                // Esto NO afecta al catálogo paginado, solo inserta en el DataTable del carrito
                List<detalleServicioDto> detalles = boDetalleServicio.ListarPorIdCita(citaId);
                if (detalles != null && detalles.Count > 0)
                {
                    foreach (var det in detalles)
                    {
                        string nombre = det.descripcion;
                        int servId = det.servicio != null ? det.servicio.servicioId : 0;

                        if (servId > 0 && !string.IsNullOrEmpty(det.servicio.nombre))
                            nombre = det.servicio.nombre;

                        AgregarItemAlCarrito(0, servId, nombre, (decimal)det.costo);
                    }
                }
            }
            catch (Exception)
            {
                // Log o manejo silencioso para no romper la pantalla
            }
        }

        #endregion

        #region "Gestión de Carrito y Cliente (Mismo que antes)"

        private void CargarDatosClienteDefault()
        {
            lblClienteNombre.Text = "Cliente General";
            lblClienteInfo.Text = "";
            ConfigurarTiposComprobante(false);
            EsClienteGuest = false;
        }

        private void InicializarCarritoVacio()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("ItemID", typeof(string));
            dt.Columns.Add("ProductoID", typeof(int));
            dt.Columns.Add("ServicioID", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("Cantidad", typeof(int));
            dt.Columns.Add("PrecioUnitario", typeof(decimal));
            dt.Columns.Add("PrecioTotalFormateado", typeof(string));
            CarritoDT = dt;
            ActualizarVistaCarrito();
        }

        private void AgregarItemAlCarrito(int prodId, int servId, string nombre, decimal precio)
        {
            string itemId = prodId > 0 ? $"P-{prodId}" : $"S-{servId}";
            if (CarritoDT == null) InicializarCarritoVacio();

            DataRow row = CarritoDT.AsEnumerable().FirstOrDefault(r => r["ItemID"].ToString() == itemId);
            if (row != null)
            {
                row["Cantidad"] = Convert.ToInt32(row["Cantidad"]) + 1;
                row["PrecioTotalFormateado"] = ((Convert.ToInt32(row["Cantidad"])) * precio).ToString("C", CultureInfo.CreateSpecificCulture("es-PE"));
            }
            else
            {
                DataRow newRow = CarritoDT.NewRow();
                newRow["ItemID"] = itemId;
                newRow["ProductoID"] = prodId;
                newRow["ServicioID"] = servId;
                newRow["Nombre"] = nombre;
                newRow["Cantidad"] = 1;
                newRow["PrecioUnitario"] = precio;
                newRow["PrecioTotalFormateado"] = precio.ToString("C", CultureInfo.CreateSpecificCulture("es-PE"));
                CarritoDT.Rows.Add(newRow);
            }
            ActualizarVistaCarrito();
        }

        protected void rptCarrito_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            string id = e.CommandArgument.ToString();
            DataRow row = CarritoDT.AsEnumerable().FirstOrDefault(r => r["ItemID"].ToString() == id);

            if (row != null)
            {
                int cant = Convert.ToInt32(row["Cantidad"]);
                decimal precio = Convert.ToDecimal(row["PrecioUnitario"]);

                if (e.CommandName == "Sumar") cant++;
                else if (e.CommandName == "Restar") { if (cant > 1) cant--; }
                else if (e.CommandName == "Quitar") CarritoDT.Rows.Remove(row);

                if (e.CommandName != "Quitar")
                {
                    row["Cantidad"] = cant;
                    row["PrecioTotalFormateado"] = (cant * precio).ToString("C", CultureInfo.CreateSpecificCulture("es-PE"));
                }
                ActualizarVistaCarrito();
            }
        }

        private void ActualizarVistaCarrito()
        {
            rptCarrito.DataSource = CarritoDT;
            rptCarrito.DataBind();
            CalcularTotales();
            updCarrito.Update();
        }

        private void CalcularTotales()
        {
            decimal total = 0;
            if (CarritoDT != null)
            {
                foreach (DataRow row in CarritoDT.Rows)
                    total += (Convert.ToDecimal(row["PrecioUnitario"]) * Convert.ToInt32(row["Cantidad"]));
            }
            decimal subtotal = total / 1.18m;
            decimal igv = total - subtotal;
            CultureInfo culture = CultureInfo.CreateSpecificCulture("es-PE");
            lblSubtotal.Text = subtotal.ToString("C", culture);
            lblIGV.Text = igv.ToString("C", culture);
            lblTotal.Text = total.ToString("C", culture);
        }

        #endregion

        #region "Generación de Comprobante (Igual que antes)"

        private void CargarMetodosDePago()
        {
            try
            {
                ddlMetodoPago.DataSource = boMetodoPago.ListarTodos();
                ddlMetodoPago.DataTextField = "nombre";
                ddlMetodoPago.DataValueField = "metodoDePagoId";
                ddlMetodoPago.DataBind();
            }
            catch { ddlMetodoPago.Items.Add(new ListItem("Efectivo", "1")); }
        }

        private void ConfigurarTiposComprobante(bool tieneRuc)
        {
            ddlTipoComprobante.Items.Clear();
            ddlTipoComprobante.Items.Add(new ListItem("Boleta de Venta", "BOLETA"));
            if (tieneRuc) ddlTipoComprobante.Items.Add(new ListItem("Factura", "FACTURA"));
            ddlTipoComprobante.SelectedIndex = 0;
            pnlDatosFactura.Visible = false;
        }

        protected void ddlTipoComprobante_SelectedIndexChanged(object sender, EventArgs e)
        {
            pnlDatosFactura.Visible = (ddlTipoComprobante.SelectedValue == "FACTURA");
            updCarrito.Update();
        }

        protected void btnGenerarComprobante_Click(object sender, EventArgs e)
        {
            try
            {
                // 1. VALIDACIÓN DE CARRITO VACÍO CON DISEÑO
                if (CarritoDT == null || CarritoDT.Rows.Count == 0)
                {
                    // Llamamos a la función de SweetAlert que creamos en el ASPX
                    ScriptManager.RegisterStartupScript(this, GetType(), "AlertaCarrito", "mostrarAlertaCarritoVacio();", true);
                    return; // Detenemos la ejecución aquí
                }

                int personaId = Session["Comprobante_PersonaId"] != null ? Convert.ToInt32(Session["Comprobante_PersonaId"]) : 0;

                // Validación de Cliente
                if (personaId == 0)
                {
                    // Podemos usar también SweetAlert aquí si quisieras, pero mantengo tu alert simple o lo mejoramos:
                    ScriptManager.RegisterStartupScript(this, GetType(), "AlertCliente", "Swal.fire('Atención', 'Debes seleccionar un cliente para continuar.', 'info');", true);
                    return;
                }

                // --- RESTO DE TU LÓGICA ORIGINAL ---
                int metodoPagoId = Convert.ToInt32(ddlMetodoPago.SelectedValue);
                string tipoDoc = ddlTipoComprobante.SelectedValue.ToUpper();

                // ... (Todo el código de inserción y generación de documento sigue igual) ...
                List<string> datos = boDocumento.GeneracionDeSiguienteBoletaOFactura(tipoDoc == "FACTURA" ? "F" : "B");
                string serie = datos[0];
                string numero = datos[1];
                string fecha = DateTime.Now.ToString("yyyy-MM-dd");

                string totalStr = lblTotal.Text.Replace("S/", "").Trim();
                double total = Convert.ToDouble(totalStr, CultureInfo.CreateSpecificCulture("es-PE"));
                double subtotal = total / 1.18;
                double igv = total - subtotal;

                int docId = boDocumento.Insertar(metodoPagoId, personaId, tipoDoc, serie, numero, fecha, "PAGADO", subtotal, igv, total, true);

                if (docId > 0)
                {
                    int nroItem = 1;
                    foreach (DataRow row in CarritoDT.Rows)
                    {
                        int pId = (row["ProductoID"] != DBNull.Value) ? Convert.ToInt32(row["ProductoID"]) : 0;
                        int sId = (row["ServicioID"] != DBNull.Value) ? Convert.ToInt32(row["ServicioID"]) : 0;
                        string desc = row["Nombre"].ToString();
                        int cant = Convert.ToInt32(row["Cantidad"]);
                        double pu = Convert.ToDouble(row["PrecioUnitario"]);

                        boDetalleDoc.Insertar(docId, sId, pId, nroItem, desc, cant, pu, pu * cant, true);
                        nroItem++;
                    }

                    var docGenerado = boDocumento.ObtenerPorId(docId);
                    lblModalTipoDoc.Text = docGenerado.tipoDocumento.ToString().ToUpper();
                    lblModalSerie.Text = docGenerado.serie;
                    lblModalNumero.Text = docGenerado.numero;

                    updModalExito.Update();
                    ScriptManager.RegisterStartupScript(this, GetType(), "ShowSuccess", "$('#modalExitoVenta').modal('show');", true);

                    Session.Remove("Comprobante_CitaId");
                    InicializarCarritoVacio();
                }
            }
            catch (Exception ex)
            {
                // Mejora visual del error técnico también
                string mensajeSeguro = ex.Message.Replace("'", "").Replace("\n", " ");
                ScriptManager.RegisterStartupScript(this, GetType(), "Err", $"Swal.fire('Error Técnico', '{mensajeSeguro}', 'error');", true);
            }
        }

        protected void btnCancelar_Click(object sender, EventArgs e)
        {
            Session.Remove("Comprobante_CitaId");
            Session.Remove("Comprobante_PersonaId");
            Response.Redirect("Secretaria_AgendaCitas.aspx");
        }

        protected void btnIrReportes_Click(object sender, EventArgs e)
        {
            Response.Redirect("Secretaria_Reportes.aspx");
        }

        // --- LÓGICA DE MODALES DE CLIENTE (Mantener igual) ---
        protected void btnCambiarCliente_Click(object sender, EventArgs e) { ScriptManager.RegisterStartupScript(this, GetType(), "m1", "$('#modalBuscarCliente').modal('show');", true); }
        protected void btnClienteGuest_Click(object sender, EventArgs e) { ScriptManager.RegisterStartupScript(this, GetType(), "m2", "$('#modalGuest').modal('show');", true); }

        protected void btnBuscarClienteModal_Click(object sender, EventArgs e)
        {
            rptResultadosCliente.DataSource = boPersona.ListarBusquedaAvanzada(txtBuscarNombreCliente.Text, "", "", 1);
            rptResultadosCliente.DataBind();
            updModalCliente.Update();
        }
        protected void rptResultadosCliente_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "Seleccionar")
            {
                string[] args = e.CommandArgument.ToString().Split('|');
                Session["Comprobante_PersonaId"] = Convert.ToInt32(args[0]);
                lblClienteNombre.Text = args[2];
                string ruc = args[1];
                bool tieneRuc = (ruc != "0" && !string.IsNullOrEmpty(ruc));
                lblClienteInfo.Text = tieneRuc ? $"RUC: {ruc}" : "Sin RUC";
                txtRucCliente.Text = tieneRuc ? ruc : "";
                ConfigurarTiposComprobante(tieneRuc);
                updCarrito.Update();
            }
        }
        protected void btnGuardarGuest_Click(object sender, EventArgs e)
        {
            int dni = 0; int.TryParse(txtGuestDNI.Text, out dni);
            int ruc = 0; int.TryParse(txtGuestRUC.Text, out ruc);
            int id = boPersona.insertarUsaurioGuest(txtGuestNombre.Text, ruc, dni);
            if (id > 0)
            {
                Session["Comprobante_PersonaId"] = id;
                lblClienteNombre.Text = txtGuestNombre.Text + " (INVITADO)";
                lblClienteInfo.Text = $"DNI: {dni}";
                txtRucCliente.Text = ruc > 0 ? ruc.ToString() : "";
                ConfigurarTiposComprobante(ruc > 0);
                updCarrito.Update();
            }
        }

        #endregion
    }
}