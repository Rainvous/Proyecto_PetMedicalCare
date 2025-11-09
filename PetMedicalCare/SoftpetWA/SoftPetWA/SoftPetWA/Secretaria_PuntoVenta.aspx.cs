// Nombre del archivo: Secretaria_PuntoVenta.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Secretaria_PuntoVenta : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarFiltros();
                CargarProductos();
                CargarCarrito();
            }
        }

        private void CargarFiltros()
        {
            // Cargar Pestañas de Filtro (ejemplo)
            DataTable dtTipos = new DataTable();
            dtTipos.Columns.Add("Tipo", typeof(string));
            dtTipos.Columns.Add("IconoCss", typeof(string));
            dtTipos.Rows.Add("Todos", "fas fa-th-large");
            dtTipos.Rows.Add("Medicamentos", "fas fa-pills");
            dtTipos.Rows.Add("Alimentos", "fas fa-bone");
            dtTipos.Rows.Add("Higiene", "fas fa-shower");
            dtTipos.Rows.Add("Accesorios", "fas fa-tag");
            dtTipos.Rows.Add("Servicios", "fas fa-briefcase-medical");

            rptFiltroTipo.DataSource = dtTipos;
            rptFiltroTipo.DataBind();

            // Cargar DropDownList de Método de Pago
            ddlMetodoPago.Items.Add(new ListItem("Efectivo", "1"));
            ddlMetodoPago.Items.Add(new ListItem("Tarjeta de Crédito", "2"));
            ddlMetodoPago.Items.Add(new ListItem("Yape/Plin", "3"));

            // Cargar DropDownList de Tipo de Comprobante
            ddlTipoComprobante.Items.Add(new ListItem("Boleta", "1"));
            ddlTipoComprobante.Items.Add(new ListItem("Factura", "2"));
        }

        private void CargarProductos()
        {
            DataTable dt = ObtenerProductosEjemplo();
            rptProductos.DataSource = dt;
            rptProductos.DataBind();
        }

        private void CargarCarrito()
        {
            // Cargar Info del Cliente (ejemplo)
            lblClienteNombre.Text = "María González Pérez";
            lblClienteInfo.Text = "DNI: 72458963 | Max (Golden)";

            // Cargar Ítems del Carrito (ejemplo)
            DataTable dt = ObtenerCarritoEjemplo();
            rptCarrito.DataSource = dt;
            rptCarrito.DataBind();

            // Cargar Totales (ejemplo)
            lblSubtotal.Text = "S/ 275.42";
            lblIGV.Text = "S/ 49.58";
            lblTotal.Text = "S/ 325.00";
        }

        private DataTable ObtenerProductosEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("ProductoID", typeof(int));
            dt.Columns.Add("IconoCss", typeof(string));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("Descripcion", typeof(string));
            dt.Columns.Add("PrecioFormateado", typeof(string));
            dt.Columns.Add("StockTexto", typeof(string));

            // Datos de ejemplo basados en el Figma
            dt.Rows.Add(1, "fas fa-pills", "Amoxicilina 500mg", "Caja x 10 tab", "S/ 35.00", "Stock: 35 unid");
            dt.Rows.Add(2, "fas fa-pills", "Cetirizina 10mg", "Caja x 10 tab", "S/ 28.50", "Stock: 45 unid");
            dt.Rows.Add(3, "fas fa-syringe", "Vacuna Antirrábica", "Dosis 1ml", "S/ 60.00", "Stock: 12 unid");
            dt.Rows.Add(4, "fas fa-bone", "Dog Chow Adulto", "15 Kg", "S/ 185.00", "Stock: 25 unid");
            dt.Rows.Add(5, "fas fa-bone", "Royal Canin Gato", "7.5 Kg", "S/ 220.00", "Stock: 18 unid");
            dt.Rows.Add(6, "fas fa-bone", "Pro Plan Cachorro", "3 Kg", "S/ 85.00", "Stock: 30 unid");
            dt.Rows.Add(7, "fas fa-shower", "Shampoo Antipulgas", "Fco. 250ml", "S/ 32.00", "Stock: 40 unid");
            dt.Rows.Add(8, "fas fa-tooth", "Pasta Dental Pet", "Tubo 70g", "S/ 18.50", "Stock: 50 unid");
            dt.Rows.Add(9, "fas fa-cut", "Pelota de Goma", "Unidad", "S/ 15.00", "Stock: 60 unid");
            dt.Rows.Add(10, "fas fa-tag", "Collar Antipulgas", "Unidad", "S/ 42.00", "Stock: 28 unid");
            dt.Rows.Add(11, "fas fa-cut", "Peluquería Básica", "Servicio", "S/ 40.00", "Stock: N/A");
            dt.Rows.Add(12, "fas fa-stethoscope", "Consulta General", "Servicio", "S/ 50.00", "Stock: N/A");

            return dt;
        }

        private DataTable ObtenerCarritoEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("ItemID", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("Cantidad", typeof(int));
            dt.Columns.Add("PrecioTotalFormateado", typeof(string));

            // Datos de ejemplo basados en el Figma
            dt.Rows.Add(1, "Amoxicilina 500mg", 2, "S/ 90.00");
            dt.Rows.Add(2, "Dog Chow Adulto 15Kg", 1, "S/ 185.00");
            dt.Rows.Add(3, "Consulta General", 1, "S/ 50.00");

            return dt;
        }

        // --- Eventos de Botones (Vacíos por ahora) ---

        protected void btnBuscar_Click(object sender, EventArgs e) { }
        protected void btnGenerarComprobante_Click(object sender, EventArgs e) { }
        protected void btnCancelar_Click(object sender, EventArgs e) { }
        protected void rptFiltroTipo_ItemCommand(object source, RepeaterCommandEventArgs e) { }
        protected void rptProductos_ItemCommand(object source, RepeaterCommandEventArgs e) { }
        protected void rptCarrito_ItemCommand(object source, RepeaterCommandEventArgs e) { }

        // In Secretaria_PuntoVenta.aspx.cs

        // --- Eventos --- // ADD OR UPDATE THIS SECTION

        // ... (Your existing button click handlers like btnBuscar_Click, etc.) ...

        protected void rptCartItems_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // ... (Your existing code for Increment, Decrement, Remove) ...

            // After handling command, reload cart and recalculate totals
        }

        // *** ADD THIS MISSING METHOD ***
        protected void txtQuantity_TextChanged(object sender, EventArgs e)
        {
            TextBox txtQuantity = (TextBox)sender;
            // Find the RepeaterItem that contains this TextBox
            RepeaterItem item = (RepeaterItem)txtQuantity.NamingContainer;
            // Get the CommandArgument (ItemID) from the TextBox within that item
            int itemId = Convert.ToInt32(txtQuantity.Attributes["CommandArgument"]);
            int newQuantity;

            if (int.TryParse(txtQuantity.Text, out newQuantity) && newQuantity >= 0)
            {
                // --- TODO: Update Quantity in your Data Source ---
                // Get your cart data (e.g., from ViewState or Session)
                DataTable dtCart = (DataTable)ViewState["CarritoDT"]; // Assuming you use ViewState like the example
                if (dtCart != null)
                {
                    // Find the row for the item
                    DataRow rowToUpdate = dtCart.AsEnumerable().FirstOrDefault(r => Convert.ToInt32(r["ItemID"]) == itemId);
                    if (rowToUpdate != null)
                    {
                        // Update the quantity
                        rowToUpdate["Cantidad"] = newQuantity;
                        // Recalculate item total if necessary (or do it in CargarItemsCarritoEjemplo)
                        decimal precioUnitario = Convert.ToDecimal(rowToUpdate["PrecioUnitario"]);
                        rowToUpdate["PrecioTotalFormateado"] = (newQuantity * precioUnitario).ToString("C", new CultureInfo("es-PE"));
                        // Save changes back to ViewState/Session
                        ViewState["CarritoDT"] = dtCart;
                    }
                }
            }
            else
            {

            }
        }

        // ... (Your other methods like btnCheckout_Click, etc.) ...
    }
}