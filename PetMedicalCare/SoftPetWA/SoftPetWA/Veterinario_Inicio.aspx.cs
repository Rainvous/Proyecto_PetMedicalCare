using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    // Asegúrate que el nombre de la clase coincida con el Inherits del ASPX
    public partial class Veterinario_Inicio : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarDashboard();
            }
        }

        private void CargarDashboard()
        {
            // Lógica para cargar los datos del dashboard (contadores, repeater)
            litNombreVeterinario.Text = "Rony"; // Podría venir de Sesión
            litNumCitas.Text = "6"; // Calcular desde BD
            litCitasHoy.Text = "6"; // Calcular desde BD
            litCitasPendientes.Text = "3"; // Calcular desde BD
            litConsultasSemana.Text = "28"; // Calcular desde BD
            litVacunasSemana.Text = "15"; // Calcular desde BD
            litCirugiasSemana.Text = "5"; // Calcular desde BD

            // Carga del Repeater de Citas (ejemplo hardcoded)
            DataTable dt = new DataTable();
            dt.Columns.Add("IdCita", typeof(string)); dt.Columns.Add("IdMascota", typeof(string)); dt.Columns.Add("HoraCita", typeof(string)); dt.Columns.Add("EstadoCita", typeof(string)); dt.Columns.Add("EstadoCss", typeof(string)); dt.Columns.Add("NombrePaciente", typeof(string)); dt.Columns.Add("DetallesPaciente", typeof(string)); dt.Columns.Add("NombreCliente", typeof(string)); dt.Columns.Add("TipoCita", typeof(string)); dt.Columns.Add("TipoCitaCss", typeof(string)); dt.Columns.Add("BotonPrincipalTexto", typeof(string)); dt.Columns.Add("BotonSecundarioTexto", typeof(string)); dt.Columns.Add("BotonSecundarioVisible", typeof(bool)); dt.Columns.Add("CardClass", typeof(string));
            dt.Rows.Add("C001", "M001", "08:00 AM", "Completada", "status-completada", "Max", "Golden Retriever • 3 años • 32.5 kg", "María González", "Consulta General", "tag-consulta", "Consulta General", "Ver Historial", false, "");
            dt.Rows.Add("C002", "M002", "10:30 AM", "En Proceso", "status-proceso", "Luna", "Gato Persa • 2 años • 4.2 kg", "Carlos Rodríguez", "Vacunación", "tag-vacunacion", "Continuar Consulta", "Ver Historial", false, "cita-card-proceso");
            dt.Rows.Add("C003", "M003", "12:00 PM", "Pendiente", "status-pendiente", "Rocky", "Bulldog • 5 años • 28 kg", "Ana Martínez", "Control", "tag-control", "Iniciar Consulta", "Ver Historial", true, "");
            rptrCitas.DataSource = dt;
            rptrCitas.DataBind();
        }

        protected void rptrCitas_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // Si se hace clic en "Atender" (o similar), redirige a la PÁGINA de Consulta
            if (e.CommandName == "Atender")
            {
                Response.Redirect($"Veterinario_Consulta.aspx?idCita={e.CommandArgument}");
            }
            // Si se hace clic en "Historial", redirige a la PÁGINA de Historial (a crear)
            if (e.CommandName == "Historial")
            {
                // Response.Redirect($"Veterinario_Historial.aspx?idMascota={e.CommandArgument}");
                // Por ahora, podrías redirigir a consulta también si quieres ver los datos
                Response.Redirect($"Veterinario_Consulta.aspx?idMascota={e.CommandArgument}"); // O usar idCita si lo tienes
            }
        }

        // El botón btnVerAgendaCompleta ahora usa PostBackUrl, no necesita evento aquí
        // protected void btnVerAgendaCompleta_Click(object sender, EventArgs e) { }
    }
}