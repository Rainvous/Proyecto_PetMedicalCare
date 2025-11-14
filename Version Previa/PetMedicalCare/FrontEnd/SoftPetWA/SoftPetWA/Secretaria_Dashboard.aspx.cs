// Nombre del archivo: Secretaria_Dashboard.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data, igual que tu ejemplo
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    // Nombre de la clase actualizado
    public partial class Secretaria_Dashboard : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Mismo patrón de tu ejemplo: Cargar todo en el primer Load
                CargarDatosGenerales();
                CargarResumenFinanciero();
                CargarAgenda();
                CargarVeterinarios();
            }
        }

        private void CargarDatosGenerales()
        {
            // Carga los datos en los <asp:Label>
            lblNombreSecretaria.Text = "Anita";
            lblCitasHoy.Text = "24";
            lblIngresosHoy.Text = (8450.00m).ToString("C", CultureInfo.GetCultureInfo("es-PE"));
            lblVeterinariosActivos.Text = "5";
        }

        // --- Carga de Repeaters (Igual que en tu guía) ---

        private void CargarResumenFinanciero()
        {
            // 1. Obtener datos de ejemplo
            DataTable dt = ObtenerResumenEjemplo();

            // 2. Enlazar al control Repeater
            rptResumenFinanciero.DataSource = dt;
            rptResumenFinanciero.DataBind();
        }

        private void CargarAgenda()
        {
            DataTable dt = ObtenerAgendaEjemplo();
            rptAgendaDia.DataSource = dt;
            rptAgendaDia.DataBind();
        }

        private void CargarVeterinarios()
        {
            DataTable dt = ObtenerVeterinariosEjemplo();
            rptEstadoVeterinarios.DataSource = dt;
            rptEstadoVeterinarios.DataBind();
        }

        // --- Métodos de Datos de Ejemplo (Igual que en tu guía) ---

        private DataTable ObtenerResumenEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("Concepto", typeof(string));
            dt.Columns.Add("MontoFormateado", typeof(string)); // Usamos string para el formato

            dt.Rows.Add("Ingresos Totales", (8450.00m).ToString("C", CultureInfo.GetCultureInfo("es-PE")));
            dt.Rows.Add("Consultas", (3200.00m).ToString("C", CultureInfo.GetCultureInfo("es-PE")));
            dt.Rows.Add("Vacunaciones", (2100.00m).ToString("C", CultureInfo.GetCultureInfo("es-PE")));
            dt.Rows.Add("Cirugías", (2500.00m).ToString("C", CultureInfo.GetCultureInfo("es-PE")));
            dt.Rows.Add("Otros Servicios", (650.00m).ToString("C", CultureInfo.GetCultureInfo("es-PE")));

            return dt;
        }

        private DataTable ObtenerAgendaEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("Hora", typeof(string));
            dt.Columns.Add("NombreMascota", typeof(string));
            dt.Columns.Add("Servicio", typeof(string));
            dt.Columns.Add("NombreCliente", typeof(string));
            dt.Columns.Add("NombreVeterinario", typeof(string));
            dt.Columns.Add("Estado", typeof(string));
            dt.Columns.Add("CssEstado", typeof(string)); // Columna para el estilo del badge

            dt.Rows.Add("08:00 AM", "Max", "Consulta General", "María González", "Dr. García López", "Confirmada", "bg-success");
            dt.Rows.Add("09:30 AM", "Luna", "Vacunación", "Carlos Rodríguez", "Dra. Martínez Ruiz", "Confirmada", "bg-success");
            dt.Rows.Add("11:00 AM", "Rocky", "Control", "Ana Martínez", "Dr. García López", "Pendiente", "bg-warning");
            dt.Rows.Add("02:00 PM", "Bella", "Cirugía Menor", "Pedro Sánchez", "Dr. García López", "Confirmada", "bg-success");
            dt.Rows.Add("04:30 PM", "Thor", "Revisión", "Laura Fernández", "Dra. Martínez Ruiz", "Pendiente", "bg-warning");

            return dt;
        }

        private DataTable ObtenerVeterinariosEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("Iniciales", typeof(string));
            dt.Columns.Add("ColorAvatar", typeof(string));
            dt.Columns.Add("NombreCompleto", typeof(string));
            dt.Columns.Add("Especialidad", typeof(string));
            dt.Columns.Add("Estado", typeof(string));
            dt.Columns.Add("CssEstado", typeof(string)); // Columna para el estilo del badge

            dt.Rows.Add("GL", "#8c8c8c", "Dr. García López", "Medicina General", "Ocupado", "bg-danger");
            dt.Rows.Add("MR", "#e0e0e0", "Dra. Martínez Ruiz", "Cirugía", "Disponible", "bg-success");
            dt.Rows.Add("JS", "#4a6fa5", "Dr. Jiménez Silva", "Dermatología", "Disponible", "bg-success");
            dt.Rows.Add("LT", "#f7a36c", "Dra. López Torres", "Oftalmología", "En consulta", "bg-warning");
            dt.Rows.Add("RP", "#904c77", "Dr. Ramírez Pérez", "Cardiología", "No disponible", "bg-secondary");

            return dt;
        }
    }
}