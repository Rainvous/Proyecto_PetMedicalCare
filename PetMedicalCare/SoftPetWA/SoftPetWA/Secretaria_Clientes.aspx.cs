// Nombre del archivo: Secretaria_Clientes.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Secretaria_Clientes : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarFiltros();
                CargarClientes();
            }
        }

        private void CargarFiltros()
        {
            // Cargar Tipos de Documento (ejemplo)
            DataTable dtDoc = new DataTable();
            dtDoc.Columns.Add("Tipo", typeof(string));
            dtDoc.Rows.Add("DNI");
            dtDoc.Rows.Add("RUC");
            dtDoc.Rows.Add("CE"); // Carnet de Extranjería

            ddlDocumento.DataSource = dtDoc;
            ddlDocumento.DataTextField = "Tipo";
            ddlDocumento.DataValueField = "Tipo";
            ddlDocumento.DataBind();
        }

        private void CargarClientes()
        {
            DataTable dt = ObtenerClientesEjemplo();
            rptClientes.DataSource = dt;
            rptClientes.DataBind();

            // Actualizar contadores de ejemplo
            litRegistrosTotales.Text = dt.Rows.Count.ToString();
            litRegistrosActuales.Text = $"1-{dt.Rows.Count}";
        }

        private DataTable ObtenerClientesEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("ClienteID", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("Email", typeof(string));
            dt.Columns.Add("Iniciales", typeof(string));
            dt.Columns.Add("AvatarColor", typeof(string));
            dt.Columns.Add("Documento", typeof(string));
            dt.Columns.Add("RUC", typeof(string));
            dt.Columns.Add("Telefono", typeof(string));
            dt.Columns.Add("NumMascotas", typeof(int));
            dt.Columns.Add("TextoMascotas", typeof(string));
            dt.Columns.Add("Estado", typeof(string));
            dt.Columns.Add("CssEstado", typeof(string));

            // Datos de ejemplo basados en el Figma
            dt.Rows.Add(1, "María González Pérez", "maria.gonzalez@email.com", "MG", "#4a6fa5", "DNI: 72458963", "RUC: 12345678901", "987 654 321", 3, "3 mascotas", "Activo", "text-success");
            dt.Rows.Add(2, "Carlos Pérez Torres", "carlos.perez@email.com", "CP", "#f7a36c", "DNI: 45789632", "RUC: -", "956 321 478", 1, "1 mascota", "Activo", "text-success");
            dt.Rows.Add(3, "Alvaro Ferro Domínguez", "alvaro.ferro@email.com", "AA", "#8c8c8c", "DNI: 73024143", "RUC: 09876543210", "988 528 244", 15, "15 mascotas", "Activo", "text-success");
            dt.Rows.Add(4, "Ana Martínez López", "ana.martinez@email.com", "AM", "#904c77", "DNI: 65478932", "RUC: -", "912 345 678", 2, "2 mascotas", "Activo", "text-success");
            dt.Rows.Add(5, "Juan Torres Ramírez", "juan.torres@email.com", "JT", "#e0e0e0", "DNI: 78945632", "RUC: -", "923 456 789", 4, "4 mascotas", "Inactivo", "text-danger");

            return dt;
        }

        // --- Eventos de Botones (Vacíos por ahora) ---

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            // Lógica de búsqueda iría aquí
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            // Lógica para limpiar filtros iría aquí
        }

        protected void btnNuevoCliente_Click(object sender, EventArgs e)
        {
            // Lógica para redirigir a "Nuevo Cliente"
            // Response.Redirect("Secretaria_NuevoCliente.aspx");
        }

        protected void rptClientes_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            string clienteID = e.CommandArgument.ToString();

            if (e.CommandName == "Ver")
            {
                // Response.Redirect($"Secretaria_VerCliente.aspx?id={clienteID}");
            }
            else if (e.CommandName == "Editar")
            {
                // Response.Redirect($"Secretaria_EditarCliente.aspx?id={clienteID}");
            }
            else if (e.CommandName == "Eliminar")
            {
                // Lógica de eliminación...
            }
        }
    }
}