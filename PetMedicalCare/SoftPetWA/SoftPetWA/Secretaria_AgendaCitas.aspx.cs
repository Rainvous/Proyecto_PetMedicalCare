// Nombre del archivo: Secretaria_AgendaCitas.aspx.cs
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
    public partial class Secretaria_AgendaCitas : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarResumen();
                CargarFiltros();
                CargarAgenda();

                // Valor de ejemplo para el label de la fecha
                lblFechaActual.Text = "Lunes, 6 de Octubre 2024";
            }
        }

        private void CargarResumen()
        {
            // Aquí iría la lógica para obtener estos números
            lblCitasHoy.Text = "12";
            lblConfirmadas.Text = "8";
            lblPendientes.Text = "3";
            lblCanceladas.Text = "1";
        }

        private void CargarFiltros()
        {
            // Cargar Veterinarios (ejemplo)
            DataTable dtVets = new DataTable();
            dtVets.Columns.Add("VeterinarioID", typeof(int));
            dtVets.Columns.Add("Nombre", typeof(string));
            dtVets.Rows.Add(0, "Todos");
            dtVets.Rows.Add(1, "Dr. García López");
            dtVets.Rows.Add(2, "Dra. Martínez Ruiz");

            ddlVeterinario.DataSource = dtVets;
            ddlVeterinario.DataTextField = "Nombre";
            ddlVeterinario.DataValueField = "VeterinarioID";
            ddlVeterinario.DataBind();

            // Cargar Estados (ejemplo)
            DataTable dtEstados = new DataTable();
            dtEstados.Columns.Add("EstadoID", typeof(string));
            dtEstados.Columns.Add("Nombre", typeof(string));
            dtEstados.Rows.Add("TODOS", "Todos");
            dtEstados.Rows.Add("CONFIRMADA", "Confirmada");
            dtEstados.Rows.Add("PENDIENTE", "Pendiente");
            dtEstados.Rows.Add("CANCELADA", "Cancelada");

            ddlEstado.DataSource = dtEstados;
            ddlEstado.DataTextField = "Nombre";
            ddlEstado.DataValueField = "EstadoID";
            ddlEstado.DataBind();
        }

        private void CargarAgenda()
        {
            DataTable dt = ObtenerAgendaEjemplo();
            rptAgendaTimeline.DataSource = dt;
            rptAgendaTimeline.DataBind();
        }

        private DataTable ObtenerAgendaEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("HoraLabel", typeof(string)); // La etiqueta de hora (08:00, 09:00)
            dt.Columns.Add("HoraCita", typeof(string)); // El rango (08:00 - 08:30)
            dt.Columns.Add("Cliente", typeof(string));
            dt.Columns.Add("MascotaServicio", typeof(string)); // Ej: "Max (Golden Retriever) | Consulta General"
            dt.Columns.Add("Status", typeof(string)); // Ej: "Confirmada"
            dt.Columns.Add("CssClass", typeof(string)); // Ej: "status-confirmada"

            // Datos de ejemplo basados en el Figma
            dt.Rows.Add("08:00", "08:00 - 08:30", "María González Pérez", "Max (Golden Retriever) | Consulta General", "Confirmada", "status-confirmada");
            dt.Rows.Add("09:00", "09:00 - 09:30", "Carlos Pérez Torres", "Luna (Siames) | Vacunación", "Confirmada", "status-confirmada");
            dt.Rows.Add("10:00", "10:00 - 10:30", "Ana Martínez López", "Rocky (Labrador) | Limpieza de Dientes", "Pendiente", "status-pendiente");
            dt.Rows.Add("11:00", "", "", "", "", ""); // Hora vacía
            dt.Rows.Add("12:00", "12:00 - 12:30", "Juan Torres Ramírez", "Toby (Beagle) | Control Postoperatorio", "Confirmada", "status-confirmada");
            dt.Rows.Add("13:00", "", "", "", "", ""); // Hora vacía
            dt.Rows.Add("14:00", "14:00 - 14:30", "Laura Sánchez Díaz", "Mia (Danes) | Herida Leve", "Cancelada", "status-cancelada");

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

        protected void btnNuevaCita_Click(object sender, EventArgs e)
        {
            // Lógica para redirigir a "Nueva Cita"
            // Response.Redirect("Secretaria_NuevaCita.aspx");
        }
    }
}