using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    // Asegúrate que el nombre coincida con Inherits
    public partial class Veterinario_Agenda : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarAgenda();
            }
        }

        private void CargarAgenda()
        {
            // Lógica para cargar los datos de la agenda
            litFecha.Text = "Martes, 15 de Septiembre 2025"; // Debe ser dinámico
            litConteoCitas.Text = "7"; // Calcular desde BD
            litTotalCitas.Text = "7"; // Calcular desde BD
            litCompletadas.Text = "3"; // Calcular desde BD
            litPendientes.Text = "4"; // Calcular desde BD
            litCanceladas.Text = "0"; // Calcular desde BD

            // Carga del Repeater (ejemplo hardcoded)
            DataTable dt = new DataTable();
            dt.Columns.Add("IdCita", typeof(string)); dt.Columns.Add("IdMascota", typeof(string)); dt.Columns.Add("HoraRango", typeof(string)); dt.Columns.Add("EstadoCita", typeof(string)); dt.Columns.Add("EstadoCss", typeof(string)); dt.Columns.Add("IconoEstado", typeof(string)); dt.Columns.Add("IconoClase", typeof(string)); dt.Columns.Add("CardClass", typeof(string)); dt.Columns.Add("IconoMascota", typeof(string)); dt.Columns.Add("NombrePaciente", typeof(string)); dt.Columns.Add("DetallesPaciente", typeof(string)); dt.Columns.Add("Servicio1", typeof(string)); dt.Columns.Add("IconoServ1", typeof(string)); dt.Columns.Add("Servicio2", typeof(string)); dt.Columns.Add("IconoServ2", typeof(string));
            dt.Rows.Add("C001", "M001", "08:00 - 08:30", "Completada", "tag-completada", "icon-completada", "fas fa-check", "agenda-card-completada", "fas fa-dog", "MAX", "Labrador • Propietario: María González", "Consulta General", "fas fa-stethoscope", "Control de rutina y vacunación antirrábica", "fas fa-syringe");
            dt.Rows.Add("C002", "M002", "09:00 - 09:30", "Completada", "tag-completada", "icon-completada", "fas fa-check", "agenda-card-completada", "fas fa-cat", "LUNA", "Gato Persa • Propietario: Carlos Pérez", "Vacunación", "fas fa-syringe", "Triple felina y desparasitación", "fas fa-pills");
            dt.Rows.Add("C003", "M003", "10:00 - 10:30", "Pendiente", "tag-pendiente", "icon-pendiente", "far fa-clock", "agenda-card-pendiente", "fas fa-dog", "ROCKY", "Bulldog • Propietario: Ana Martínez", "Control Post-operatorio", "fas fa-notes-medical", "Revisión de suturas", "fas fa-band-aid");
            rptrAgenda.DataSource = dt;
            rptrAgenda.DataBind();
        }

        protected void btnDiaAnterior_Click(object sender, EventArgs e) { /* Lógica para cambiar fecha */ }
        protected void btnDiaSiguiente_Click(object sender, EventArgs e) { /* Lógica para cambiar fecha */ }
        protected void btnHoy_Click(object sender, EventArgs e) { /* Lógica para ir a fecha actual */ }

        protected void filtro_Command(object sender, CommandEventArgs e)
        {
            // Lógica para filtrar el repeater y marcar botón activo
            lnkFiltroTodas.CssClass = "btn btn-sm btn-outline-primary";
            lnkFiltroPendientes.CssClass = "btn btn-sm btn-outline-primary";
            lnkFiltroConfirmadas.CssClass = "btn btn-sm btn-outline-primary";
            lnkFiltroCompletadas.CssClass = "btn btn-sm btn-outline-primary";
            (sender as LinkButton).CssClass = "btn btn-sm btn-outline-primary active";
            // TODO: Volver a llamar a CargarAgenda() o método similar con el filtro aplicado
        }

        protected void rptrAgenda_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // Si se hace clic en "Ver Expediente", redirige a la PÁGINA de Consulta (o Historial)
            if (e.CommandName == "VerExpediente")
            {
                // Podrías redirigir a Consulta pasando idMascota o idCita
                Response.Redirect($"Veterinario_Consulta.aspx?idMascota={e.CommandArgument}");
                // O si prefieres una página de historial dedicada:
                // Response.Redirect($"Veterinario_Historial.aspx?idMascota={e.CommandArgument}");
            }
        }
    }
}