// Nombre del archivo: Cliente_Inicio.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Cliente_Inicio : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                lblNombreCliente.Text = "Miguel"; // Ejemplo de saludo
                CargarMascotas();
                CargarCitas();
                CargarNotificaciones();
            }
        }

        private void CargarMascotas()
        {
            DataTable dt = ObtenerMascotasEjemplo();
            rptMascotas.DataSource = dt;
            rptMascotas.DataBind();
        }

        private void CargarCitas()
        {
            DataTable dt = ObtenerCitasEjemplo();
            rptProximasCitas.DataSource = dt;
            rptProximasCitas.DataBind();
        }

        private void CargarNotificaciones()
        {
            DataTable dt = ObtenerNotificacionesEjemplo();
            rptNotificaciones.DataSource = dt;
            rptNotificaciones.DataBind();
        }

        // --- Métodos de Datos de Ejemplo ---

        private DataTable ObtenerMascotasEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("MascotaID", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("EspecieRaza", typeof(string));
            dt.Columns.Add("AvatarURL", typeof(string));
            dt.Columns.Add("Estado", typeof(string));
            dt.Columns.Add("EstadoCss", typeof(string));

            // Datos de ejemplo
            dt.Rows.Add(1, "Max", "Golden Retriever", "Images/Avatars/dog-avatar.png", "Saludable", "bg-success");
            dt.Rows.Add(2, "Luna", "Persa", "Images/Avatars/cat-avatar.png", "Saludable", "bg-success");
            dt.Rows.Add(3, "Rocky", "Labrador", "Images/Avatars/dog-avatar.png", "Saludable", "bg-success");

            return dt;
        }

        private DataTable ObtenerCitasEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("CitaID", typeof(int));
            dt.Columns.Add("Dia", typeof(string));
            dt.Columns.Add("Mes", typeof(string));
            dt.Columns.Add("HoraDoctor", typeof(string));
            dt.Columns.Add("MascotaServicio", typeof(string));
            dt.Columns.Add("Estado", typeof(string));
            dt.Columns.Add("CssEstado", typeof(string)); // Clase para el borde (status-confirmada, etc.)

            // Datos de ejemplo
            dt.Rows.Add(1, "15", "OCT", "10:00 AM - Dr. García López", "Max - Consulta General", "Confirmada", "status-confirmada");
            dt.Rows.Add(2, "22", "OCT", "03:00 PM - Dra. Martínez", "Luna - Vacunación", "Pendiente", "status-pendiente");

            return dt;
        }

        private DataTable ObtenerNotificacionesEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("NotifID", typeof(int));
            dt.Columns.Add("IconoCss", typeof(string));
            dt.Columns.Add("IconoColorCss", typeof(string));
            dt.Columns.Add("Titulo", typeof(string));
            dt.Columns.Add("Mensaje", typeof(string));
            dt.Columns.Add("Tiempo", typeof(string));

            // Datos de ejemplo
            dt.Rows.Add(1, "fas fa-calendar-check", "icon-blue", "Recordatorio de Cita", "Tienes una cita mañana a las 10:00 AM para Max", "Hace 2 horas");
            dt.Rows.Add(2, "fas fa-syringe", "icon-green", "Vacuna Próxima", "Luna necesita su refuerzo de vacuna antirrábica", "Hace 1 día");
            dt.Rows.Add(3, "fas fa-bug", "icon-orange", "Desparasitación Pendiente", "Rocky debe desparasitarse este mes", "Hace 3 días");

            return dt;
        }
    }
}