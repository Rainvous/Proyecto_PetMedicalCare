using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.VeterinarioClient;

using personaDto = SoftPetBussiness.PersonaClient.personaDto;
using mascotaDto = SoftPetBussiness.MascotaClient.mascotaDto;
using veterinarioDto = SoftPetBussiness.VeterinarioClient.veterinarioDto;
using citaAtencionDto = SoftPetBussiness.CitaAtencionClient.citaAtencionDto;

namespace SoftPetWA
{
    public partial class Cliente_AgendaCitas : System.Web.UI.Page
    {
        private PersonaBO boPersona = new PersonaBO();
        private MascotaBO boMascota = new MascotaBO();
        private VeterinarioBO boVeterinario = new VeterinarioBO();
        private CitaAtencionBO boCitaAtencion = new CitaAtencionBO();

        // Propiedades de Filtro
        private DateTime FiltroFecha
        {
            get { return Session["AgendaFiltroFecha"] == null ? DateTime.Today : (DateTime)Session["AgendaFiltroFecha"]; }
            set { Session["AgendaFiltroFecha"] = value; }
        }

        // AQUI ESTÁ EL FILTRO NUEVO: ID DE MASCOTA
        private int FiltroMascotaId
        {
            get { return Session["AgendaFiltroMascotaId"] == null ? 0 : (int)Session["AgendaFiltroMascotaId"]; }
            set { Session["AgendaFiltroMascotaId"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["UsuarioId"] == null)
            {
                // Si ni siquiera hay usuario, ahí sí botamos
                Response.Redirect("Login.aspx");
                return;
            }

            // Si hay usuario pero NO hay Persona (porque el login viejo no la guardó), la buscamos
            if (Session["PersonaId"] == null)
            {
                try
                {
                    int uid = Convert.ToInt32(Session["UsuarioId"]);
                    // Buscamos la persona dueña de este usuario
                    var todas = boPersona.ListarBusquedaAvanzada("", "", "", -1); // O ListarSoloClientes() si existe
                    var p = todas.FirstOrDefault(x => x.usuario != null && x.usuario.usuarioId == uid);

                    if (p != null)
                    {
                        Session["PersonaId"] = p.personaId;
                        Session["ClienteID_Reserva"] = p.personaId; // Preparado para la reserva
                    }
                }
                catch { }
            }
            // ==========================================================================

            if (!IsPostBack)
            {
                FiltroFecha = DateTime.Today;
                FiltroMascotaId = 0; // 0 = Todas las mascotas

                CargarFiltroMascotas(); // Carga el DDL
                CargarAgenda();         // Carga la tabla
                ActualizarControlesDeFiltro();
                ActualizarLabelsFecha();
            }
        }

        // --- 1. Cargar el DropDownList con Mascotas ---
        private void CargarFiltroMascotas()
        {
            try
            {
                // Verificamos de nuevo por seguridad
                if (Session["PersonaId"] != null)
                {
                    int clienteId = Convert.ToInt32(Session["PersonaId"]);
                    List<mascotaDto> mascotas = boMascota.ListarPorIdPersona(clienteId);

                    ddlMascota.DataSource = mascotas;
                    ddlMascota.DataTextField = "nombre";
                    ddlMascota.DataValueField = "mascotaId";
                    ddlMascota.DataBind();
                }

                // Opción por defecto
                ddlMascota.Items.Insert(0, new ListItem("Todas mis mascotas", "0"));

                // Recuperar selección previa
                if (ddlMascota.Items.FindByValue(FiltroMascotaId.ToString()) != null)
                {
                    ddlMascota.SelectedValue = FiltroMascotaId.ToString();
                }
            }
            catch
            {
                ddlMascota.Items.Insert(0, new ListItem("Error cargando mascotas", "0"));
            }
        }

        // --- 2. Cargar la Agenda filtrando por Mascota ---
        private void CargarAgenda()
        {
            List<citaAtencionDto> citasDelDia = new List<citaAtencionDto>();
            try
            {
                if (Session["PersonaId"] != null)
                {
                    string fechaStr = FiltroFecha.ToString("yyyy-MM-dd");
                    int idMascota = FiltroMascotaId;
                    int clienteId = Convert.ToInt32(Session["PersonaId"]);

                    if (idMascota == 0)
                    {
                        // Estrategia "Todas": Buscamos las mascotas y pedimos citas de cada una
                        // (Esto asume que tu backend 'ListasCitasPorMascotasYFechas' filtra por mascota específica)
                        List<mascotaDto> misMascotas = boMascota.ListarPorIdPersona(clienteId);
                        foreach (var m in misMascotas)
                        {
                            // LLAMADA AL NUEVO MÉTODO DEL BO
                            var citasM = boCitaAtencion.ListarCitasPorMascotaYFecha(m.mascotaId, fechaStr);
                            if (citasM != null) citasDelDia.AddRange(citasM);
                        }
                    }
                    else
                    {
                        // Estrategia "Específica": Pedimos citas solo de esa mascota
                        var citas = boCitaAtencion.ListarCitasPorMascotaYFecha(idMascota, fechaStr);
                        if (citas != null) citasDelDia = citas;
                    }
                }
            }
            catch { /* Error silencioso para no romper la UI */ }

            // --- Renderizado (Igual que antes) ---
            var citasConFechas = citasDelDia.Select(c =>
            {
                DateTime? dtInicio = null, dtFin = null;
                try
                {
                    // Intento robusto de parseo (por si viene con T o sin ella)
                    dtInicio = DateTime.Parse(c.fechaHoraInicioStr);
                    dtFin = DateTime.Parse(c.fechaHoraFinStr);
                }
                catch { }
                return new { Cita = c, FechaInicio = dtInicio, FechaFin = dtFin };
            }).Where(x => x.FechaInicio.HasValue).ToList();

            var viewModelHoras = new List<object>();
            for (int hora = 8; hora <= 18; hora++)
            {
                string horaLabel = $"{hora:00}:00";
                var citasEnEstaHora = citasConFechas.Where(c => c.FechaInicio.Value.Hour == hora).OrderBy(c => c.FechaInicio.Value).ToList();

                var viewModelCitas = citasEnEstaHora.Select(c =>
                {
                    string clienteN = "Yo";
                    string mascotaN = "N/A";
                    string vetN = "N/A";

                    try
                    {
                        // Obtener nombres bonitos
                        mascotaDto m = boMascota.ObtenerPorId(c.Cita.mascota.mascotaId);
                        if (m != null) mascotaN = m.nombre;

                        veterinarioDto v = boVeterinario.ObtenerPorId(c.Cita.veterinario.veterinarioId);
                        if (v != null)
                        {
                            personaDto pv = boPersona.ObtenerPorId(v.persona.personaId);
                            if (pv != null) vetN = pv.nombre;
                        }
                    }
                    catch { }

                    return new
                    {
                        HoraCita = $"{c.FechaInicio:HH:mm} - {c.FechaFin:HH:mm}",
                        Cliente = clienteN,
                        MascotaNombre = mascotaN,
                        VeterinarioNombre = vetN,
                        Status = c.Cita.estado.ToString(),
                        CssClass = "status-" + c.Cita.estado.ToString().ToLower()
                    };
                }).ToList();

                viewModelHoras.Add(new { HoraLabel = horaLabel, Citas = viewModelCitas });
            }

            rptHoras.DataSource = viewModelHoras;
            rptHoras.DataBind();
        }

        // --- Eventos ---

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            try { FiltroFecha = DateTime.Parse(txtFecha.Text); } catch { }
            FiltroMascotaId = Convert.ToInt32(ddlMascota.SelectedValue); // Guardar selección de mascota
            CargarAgenda();
            ActualizarLabelsFecha();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            FiltroFecha = DateTime.Today;
            FiltroMascotaId = 0;
            CargarAgenda();
            ActualizarControlesDeFiltro();
            ActualizarLabelsFecha();
        }

        // El botón "Nueva Cita" funciona porque Page_Load ya recuperó el PersonaId
        protected void btnNuevaCita_Click(object sender, EventArgs e)
        {
            // Limpieza
            Session.Remove("MascotaID_Reserva");
            Session.Remove("ServiciosID_Reserva");
            Session.Remove("VeterinarioID_Reserva");
            Session.Remove("FechaHoraInicio_Reserva");
            Session.Remove("ClienteID_Reserva");

            if (Session["PersonaId"] != null)
            {
                Session["ClienteID_Reserva"] = Session["PersonaId"];
                Response.Redirect("Cliente_ReservarCita_Paso1.aspx");
            }
            else
            {
                Response.Redirect("Login.aspx");
            }
        }

        protected void lnkAnterior_Click(object sender, EventArgs e) { FiltroFecha = FiltroFecha.AddDays(-1); CargarAgenda(); ActualizarControlesDeFiltro(); ActualizarLabelsFecha(); }
        protected void lnkSiguiente_Click(object sender, EventArgs e) { FiltroFecha = FiltroFecha.AddDays(1); CargarAgenda(); ActualizarControlesDeFiltro(); ActualizarLabelsFecha(); }
        protected void btnHoy_Click(object sender, EventArgs e) { FiltroFecha = DateTime.Today; CargarAgenda(); ActualizarControlesDeFiltro(); ActualizarLabelsFecha(); }

        protected void calAgenda_SelectionChanged(object sender, EventArgs e)
        {
            FiltroFecha = calAgenda.SelectedDate;
            CargarAgenda();
            ActualizarControlesDeFiltro();
            ActualizarLabelsFecha();
            ScriptManager.RegisterStartupScript(this, GetType(), "CambiarVista", "mostrarVista('lista');", true);
        }

        protected void calAgenda_DayRender(object sender, DayRenderEventArgs e) { /* Opcional: lógica de puntitos */ }

        private void ActualizarLabelsFecha()
        {
            lblFechaActual.Text = FiltroFecha.ToString("dddd, dd 'de' MMMM yyyy", new CultureInfo("es-ES"));
            calAgenda.SelectedDate = FiltroFecha;
            calAgenda.VisibleDate = FiltroFecha;
        }

        private void ActualizarControlesDeFiltro()
        {
            txtFecha.Text = FiltroFecha.ToString("yyyy-MM-dd");
            ddlMascota.SelectedValue = FiltroMascotaId.ToString();
        }
    }
}