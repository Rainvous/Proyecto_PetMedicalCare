// Nombre del archivo: Veterinario_Recetas.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Veterinario_Recetas : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {

                CargarRecetas();
            }
        }

        private void CargarRecetas()
        {
            DataTable dt = ObtenerRecetasEjemplo();
            rptRecetas.DataSource = dt;
            rptRecetas.DataBind();

            // Actualizar contadores de ejemplo
            litRegistrosTotales.Text = dt.Rows.Count.ToString();
            litRegistrosActuales.Text = $"1-{dt.Rows.Count}";
        }

        private DataTable ObtenerRecetasEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("RecetaID", typeof(int));
            dt.Columns.Add("IconoCss", typeof(string)); // fas fa-prescription
            dt.Columns.Add("Fecha", typeof(string)); // Ej: 06 Octubre, 2024
            dt.Columns.Add("MascotaNombre", typeof(string));
            dt.Columns.Add("PropietarioNombre", typeof(string));
            dt.Columns.Add("EstadoTexto", typeof(string)); // Vigente, Expirada
            dt.Columns.Add("EstadoCss", typeof(string)); // status-vigente, status-expirada

            // Datos de ejemplo
            dt.Rows.Add(1, "fas fa-prescription", "06 Octubre, 2024", "Max", "María González Pérez", "Vigente", "status-vigente");
            dt.Rows.Add(2, "fas fa-prescription", "15 Septiembre, 2024", "Luna", "Carlos Pérez Torres", "Vigente", "status-vigente");
            dt.Rows.Add(3, "fas fa-prescription", "01 Agosto, 2024", "Rocky", "Ana Martínez López", "Expirada", "status-expirada");
            dt.Rows.Add(4, "fas fa-prescription", "10 Julio, 2024", "Max", "María González Pérez", "Expirada", "status-expirada");

            return dt;
        }

        // --- Eventos (Vacíos por ahora) ---
        protected void btnBuscar_Click(object sender, EventArgs e) { }
        protected void btnLimpiar_Click(object sender, EventArgs e) { }
        protected void btnNuevaReceta_Click(object sender, EventArgs e)
        {
            // Response.Redirect("Veterinario_NuevaReceta.aspx");
        }
        protected void rptRecetas_ItemCommand(object source, RepeaterCommandEventArgs e) { }
    }
}