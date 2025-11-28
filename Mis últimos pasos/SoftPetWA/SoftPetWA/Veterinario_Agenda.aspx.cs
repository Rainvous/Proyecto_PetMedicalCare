using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.VeterinarioClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.MascotaClient;

using citaAtencionDto = SoftPetBussiness.CitaAtencionClient.citaAtencionDto;
using veterinarioDto = SoftPetBussiness.VeterinarioClient.veterinarioDto;
using personaDto = SoftPetBussiness.PersonaClient.personaDto;

namespace SoftPetWA
{
    public partial class Veterinario_Agenda : System.Web.UI.Page
    {
        private CitaAtencionBO boCita = new CitaAtencionBO();
        private VeterinarioBO boVet = new VeterinarioBO();
        private MascotaBO boMascota = new MascotaBO();
        private PersonaBO boPersona = new PersonaBO();

        private DateTime FechaSeleccionada
        {
            get { return Session["VetAgendaFecha"] != null ? (DateTime)Session["VetAgendaFecha"] : DateTime.Today; }
            set { Session["VetAgendaFecha"] = value; }
        }

        private int VeterinarioLogueadoId
        {
            get { return Session["VeterinarioIdLogueado"] != null ? (int)Session["VeterinarioIdLogueado"] : 0; }
            set { Session["VeterinarioIdLogueado"] = value; }
        }

        private string FiltroActual
        {
            get { return Session["VetAgendaFiltro"] != null ? (string)Session["VetAgendaFiltro"] : "Todas"; }
            set { Session["VetAgendaFiltro"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // Seguridad y Autocorrección de Sesión
            if (Session["UsuarioId"] == null) { Response.Redirect("Login.aspx"); return; }

            if (Session["PersonaId"] == null)
            {
                try
                {
                    int uid = Convert.ToInt32(Session["UsuarioId"]);
                    var p = boPersona.ListarPersonasActivas().FirstOrDefault(x => x.usuario != null && x.usuario.usuarioId == uid);
                    if (p != null) Session["PersonaId"] = p.personaId;
                }
                catch { }
            }

            if (VeterinarioLogueadoId == 0 && Session["PersonaId"] != null)
            {
                try
                {
                    int pid = Convert.ToInt32(Session["PersonaId"]);
                    var vet = boVet.ListarTodos().FirstOrDefault(v => v.persona != null && v.persona.personaId == pid);
                    if (vet != null) VeterinarioLogueadoId = vet.veterinarioId;
                }
                catch { }
            }

            if (!IsPostBack)
            {
                if (Session["VetAgendaFecha"] == null) FechaSeleccionada = DateTime.Today;
                if (Session["VetAgendaFiltro"] == null) FiltroActual = "Todas";
                CargarAgenda();
            }
        }

        protected void txtFechaFiltro_TextChanged(object sender, EventArgs e)
        {
            try { FechaSeleccionada = DateTime.Parse(txtFechaFiltro.Text); } catch { }
            CargarAgenda();
        }

        private void CargarAgenda()
        {
            txtFechaFiltro.Text = FechaSeleccionada.ToString("yyyy-MM-dd");
            ActualizarEstilosBotonesFiltro();

            try
            {
                string fechaStr = FechaSeleccionada.ToString("yyyy-MM-dd");
                List<citaAtencionDto> todas = boCita.ListasBusquedaAvanzadaPoridVet(fechaStr, VeterinarioLogueadoId);
                if (todas == null) todas = new List<citaAtencionDto>();

                // Resumen
                litTotalCitas.Text = todas.Count.ToString();
                litCompletadas.Text = todas.Count(c => c.estado.ToString() == "ATENDIDA").ToString();
                litPendientes.Text = todas.Count(c => c.estado.ToString() == "PROGRAMADA").ToString();
                litCanceladas.Text = todas.Count(c => c.estado.ToString() == "CANCELADA").ToString();

                // Filtrado
                List<citaAtencionDto> filtradas = new List<citaAtencionDto>();
                switch (FiltroActual)
                {
                    case "Pendientes":
                    case "Confirmadas":
                        filtradas = todas.Where(c => c.estado.ToString() == "PROGRAMADA").ToList();
                        break;
                    case "Completadas":
                        filtradas = todas.Where(c => c.estado.ToString() == "ATENDIDA").ToList();
                        break;
                    default:
                        filtradas = todas;
                        break;
                }
                litConteoCitas.Text = filtradas.Count.ToString();

                DataTable dt = new DataTable();
                dt.Columns.Add("IdCita", typeof(string)); dt.Columns.Add("IdMascota", typeof(string)); dt.Columns.Add("HoraRango", typeof(string));
                dt.Columns.Add("EstadoCita", typeof(string)); dt.Columns.Add("EstadoCss", typeof(string));
                dt.Columns.Add("IconoEstado", typeof(string)); dt.Columns.Add("IconoClase", typeof(string));
                dt.Columns.Add("CardClass", typeof(string)); dt.Columns.Add("IconoMascota", typeof(string));
                dt.Columns.Add("NombrePaciente", typeof(string)); dt.Columns.Add("DetallesPaciente", typeof(string));
                dt.Columns.Add("Servicio1", typeof(string)); dt.Columns.Add("IconoServ1", typeof(string));
                dt.Columns.Add("Servicio2", typeof(string)); dt.Columns.Add("IconoServ2", typeof(string));

                var citasOrdenadas = filtradas.OrderBy(x => x.fechaHoraInicioStr).ToList();

                foreach (var c in citasOrdenadas)
                {
                    string idCita = c.citaId.ToString();
                    string estadoDB = c.estado.ToString();

                    string horaRango = "--:--";
                    try
                    {
                        if (c.fechaHoraInicioStr.Contains("-"))
                        {
                            DateTime ini = DateTime.Parse(c.fechaHoraInicioStr);
                            horaRango = $"{ini:HH:mm}";
                        }
                        else
                        {
                            DateTime ini = DateTime.ParseExact(c.fechaHoraInicioStr, "yyyyMMddHHmmss", CultureInfo.InvariantCulture);
                            horaRango = $"{ini:HH:mm}";
                        }
                    }
                    catch { }

                    string textoVisible = estadoDB;
                    string estadoCss = "tag-pendiente";
                    string iconoEstado = "icon-pendiente";
                    string iconoClase = "far fa-clock";
                    string cardClass = "agenda-card-pendiente";

                    if (estadoDB == "ATENDIDA")
                    {
                        textoVisible = "COMPLETADA";
                        estadoCss = "tag-completada";
                        iconoEstado = "icon-completada";
                        iconoClase = "fas fa-check";
                        cardClass = "agenda-card-completada";
                    }
                    else if (estadoDB == "CANCELADA")
                    {
                        textoVisible = "CANCELADA";
                        estadoCss = "tag-cancelada";
                        iconoEstado = "icon-cancelada";
                        iconoClase = "fas fa-times";
                        cardClass = "agenda-card-cancelada";
                    }
                    else
                    {
                        textoVisible = "PENDIENTE";
                        estadoCss = "tag-pendiente";
                    }

                    // === CORRECCIÓN CLAVE AQUÍ ===
                    // 1. Asignamos el ID directamente de la cita (siempre existe)
                    string idMascota = c.mascota.mascotaId.ToString();

                    // 2. Datos por defecto
                    string nombreMascota = "Mascota";
                    string detalles = "-";
                    string iconoMascota = "fas fa-paw";

                    // 3. Intentamos hidratar datos extra (Nombre, Raza, Dueño)
                    try
                    {
                        var m = boMascota.ObtenerPorId(c.mascota.mascotaId);
                        if (m != null)
                        {
                            nombreMascota = m.nombre;
                            if (m.especie.ToLower().Contains("perro")) iconoMascota = "fas fa-dog";
                            else if (m.especie.ToLower().Contains("gato")) iconoMascota = "fas fa-cat";

                            var p = boPersona.ObtenerPorId(m.persona.personaId);
                            if (p != null) detalles = $"{m.raza} • {p.nombre}";
                        }
                    }
                    catch
                    {
                        // Si falla la conexión para el nombre, NO importa, 
                        // ya tenemos el idMascota válido para redirigir.
                    }

                    string s1 = "Consulta General";
                    if (!string.IsNullOrEmpty(c.observacion)) s1 = c.observacion;

                    dt.Rows.Add(idCita, idMascota, horaRango, textoVisible, estadoCss, iconoEstado, iconoClase, cardClass, iconoMascota, nombreMascota, detalles, s1, "fas fa-stethoscope", "", "");
                }

                rptrAgenda.DataSource = dt;
                rptrAgenda.DataBind();
            }
            catch
            {
                rptrAgenda.DataSource = null;
                rptrAgenda.DataBind();
            }
        }

        protected void filtro_Command(object sender, CommandEventArgs e)
        {
            FiltroActual = e.CommandArgument.ToString();
            CargarAgenda();
        }

        private void ActualizarEstilosBotonesFiltro()
        {
            lnkFiltroTodas.CssClass = "btn btn-sm btn-outline-primary";
            lnkFiltroPendientes.CssClass = "btn btn-sm btn-outline-primary";
            lnkFiltroConfirmadas.CssClass = "btn btn-sm btn-outline-primary";
            lnkFiltroCompletadas.CssClass = "btn btn-sm btn-outline-primary";

            switch (FiltroActual)
            {
                case "Todas": lnkFiltroTodas.CssClass += " active"; break;
                case "Pendientes": lnkFiltroPendientes.CssClass += " active"; break;
                case "Confirmadas": lnkFiltroConfirmadas.CssClass += " active"; break;
                case "Completadas": lnkFiltroCompletadas.CssClass += " active"; break;
            }
        }

        protected void btnDiaAnterior_Click(object sender, EventArgs e) { FechaSeleccionada = FechaSeleccionada.AddDays(-1); CargarAgenda(); }
        protected void btnDiaSiguiente_Click(object sender, EventArgs e) { FechaSeleccionada = FechaSeleccionada.AddDays(1); CargarAgenda(); }
        protected void btnHoy_Click(object sender, EventArgs e) { FechaSeleccionada = DateTime.Today; CargarAgenda(); }

        // En el método rptrAgenda_ItemCommand
        protected void rptrAgenda_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "VerExpediente")
            {
                // CAMBIO: La URL ahora lleva el parámetro idCita
                Response.Redirect($"Veterinario_Consulta.aspx?idCita={e.CommandArgument}");
            }
        }
    }
}