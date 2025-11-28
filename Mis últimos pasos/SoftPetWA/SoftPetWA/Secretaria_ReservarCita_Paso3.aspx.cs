// Nombre del archivo: Secretaria_ReservarCita_Paso3.aspx.cs (VERSIÓN CORREGIDA 3.2 - Lógica Invertida)
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;

// Imports (sin cambios)
using SoftPetBusiness;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.VeterinarioClient;

// Alias (sin cambios)
using personaDto = SoftPetBussiness.PersonaClient.personaDto;
using veterinarioDto = SoftPetBussiness.VeterinarioClient.veterinarioDto;

namespace SoftPetWA
{
    public partial class Secretaria_ReservarCita_Paso3 : System.Web.UI.Page
    {
        // (Todo el código de BOs, ViewState, Page_Load, CargarNombreCliente, 
        // CargarVeterinarios, y GenerarCalendario se mantiene EXACTAMENTE IGUAL
        // que en la respuesta anterior v3.1)

        private PersonaBO boPersona = new PersonaBO();
        private VeterinarioBO boVeterinario = new VeterinarioBO();
        private CitaAtencionBO boCitaAtencion = new CitaAtencionBO();

        private DateTime currentCalendarMonth
        {
            get
            {
                if (ViewState["CurrentMonth"] == null)
                {
                    ViewState["CurrentMonth"] = new DateTime(DateTime.Today.Year, DateTime.Today.Month, 1);
                }
                return (DateTime)ViewState["CurrentMonth"];
            }
            set
            {
                ViewState["CurrentMonth"] = value;
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (Session["ServiciosID_Reserva"] == null || Session["MascotaID_Reserva"] == null || Session["ClienteID_Reserva"] == null)
                {
                    Response.Redirect("Secretaria_ReservarCita_Paso1.aspx");
                    return;
                }
                Session.Remove("VeterinarioID_Reserva");
                Session.Remove("Fecha_Reserva");
                Session.Remove("Hora_Reserva");
                Session.Remove("FechaHoraInicio_Reserva");
                CargarNombreCliente(Convert.ToInt32(Session["ClienteID_Reserva"]));
                CargarVeterinarios();
                GenerarCalendario(currentCalendarMonth);
                CargarHorariosDisponibles();
            }
        }

        #region "Carga de Datos"

        private void CargarNombreCliente(int clienteId)
        {
            try
            {
                personaDto cliente = boPersona.ObtenerPorId(clienteId);
                if (cliente != null) lblClienteNombre.Text = cliente.nombre;
                else lblClienteNombre.Text = "Cliente no encontrado";
            }
            catch (Exception ex) { lblClienteNombre.Text = "Error al cargar cliente"; }
        }

        private void CargarVeterinarios()
        {
            try
            {
                List<veterinarioDto> vets = boVeterinario.ListarActivos();
                List<object> vetViewModels = new List<object>();
                foreach (veterinarioDto vet in vets)
                {
                    personaDto personaVet = boPersona.ObtenerPorId(vet.persona.personaId);
                    if (personaVet != null)
                    {
                        vetViewModels.Add(new { VeterinarioID = vet.veterinarioId, Nombre = personaVet.nombre });
                    }
                }
                ddlVeterinario.DataSource = vetViewModels;
                ddlVeterinario.DataTextField = "Nombre";
                ddlVeterinario.DataValueField = "VeterinarioID";
                ddlVeterinario.DataBind();
                ddlVeterinario.Items.Insert(0, new ListItem("-- Seleccione un Veterinario --", "0"));
            }
            catch (Exception ex) { ddlVeterinario.Items.Insert(0, new ListItem("Error al cargar veterinarios", "0")); }
        }

        private void GenerarCalendario(DateTime monthToDisplay)
        {
            lblMesAnio.Text = monthToDisplay.ToString("MMMM yyyy", new CultureInfo("es-ES"));
            List<object> days = new List<object>();
            DateTime firstDayOfMonth = new DateTime(monthToDisplay.Year, monthToDisplay.Month, 1);
            int startDayOfWeek = (int)firstDayOfMonth.DayOfWeek;
            DateTime startDate = firstDayOfMonth.AddDays(-startDayOfWeek);
            DateTime today = DateTime.Today;
            DateTime? selectedDay = Session["Fecha_Reserva"] as DateTime?;

            for (int i = 0; i < 42; i++)
            {
                DateTime currentDay = startDate.AddDays(i);
                string css = "calendar-day";
                bool enabled = true;

                if (currentDay.Month != monthToDisplay.Month) { css += " other-month"; enabled = false; }
                else if (currentDay < today) { css += " disabled"; enabled = false; }
                else if (currentDay == today) { css += " today"; }
                if (selectedDay.HasValue && currentDay == selectedDay.Value) { css += " selected"; }

                days.Add(new { Day = currentDay.Day, Date = currentDay.ToString("yyyy-MM-dd"), CssClass = css, Enabled = enabled });
            }
            rptCalendarDays.DataSource = days;
            rptCalendarDays.DataBind();
        }


        // *** (MÉTODO CORREGIDO) ***
        private void CargarHorariosDisponibles()
        {
            rptHorarios.DataSource = null;
            rptHorarios.DataBind();
            if (Session["VeterinarioID_Reserva"] == null || Session["Fecha_Reserva"] == null)
            {
                lblHorariosDisponibles.Text = "Seleccione veterinario y día";
                return;
            }

            int vetId = Convert.ToInt32(Session["VeterinarioID_Reserva"]);
            DateTime fechaSeleccionada = (DateTime)Session["Fecha_Reserva"];
            lblHorariosDisponibles.Text = $"Horarios para {fechaSeleccionada:dddd, dd MMMM}";

            try
            {
                string fechaStr = fechaSeleccionada.ToString("yyyy-MM-dd");
                List<citaProgramadaDto> horarios = boCitaAtencion.ListarCitasProgramadaEseDia(vetId, fechaStr);
                string horaSeleccionada = Session["Hora_Reserva"] as string;

                var horariosVM = horarios.Select(h =>
                {
                    DateTime dt = DateTime.Parse(h.fecha, CultureInfo.InvariantCulture);
                    string horaUI = dt.ToString("hh:mm tt", CultureInfo.InvariantCulture);
                    string horaComando = dt.ToString("HH:mm");
                    string css = "time-slot-btn";
                    if (horaComando == horaSeleccionada) { css += " selected"; }

                    // *** ¡LA CORRECCIÓN ESTÁ AQUÍ! ***
                    // Si 'estaProgramada' es true (DB=1), la cita SÍ está programada (OCUPADA).
                    // Por lo tanto, 'estaDisponible' debe ser lo INVERSO.
                    bool estaDisponible = h.estaProgramada; // <-- ¡Lógica corregida! (Se añadió el '!')

                    return new
                    {
                        HoraUI = horaUI,
                        HoraComando = horaComando,
                        Disponible = estaDisponible,
                        CssClass = css
                    };
                }).ToList();

                rptHorarios.DataSource = horariosVM;
                rptHorarios.DataBind();
            }
            catch (Exception ex) { lblHorariosDisponibles.Text = "Error al cargar horarios"; }
        }

        #endregion

        #region "Eventos de Clic y Selección (AJAX)"

        // (Todo el código de eventos de clic se mantiene EXACTAMENTE IGUAL
        // que en la respuesta anterior v3.1, con 'updCalendar.Update()' en todos)

        protected void ddlVeterinario_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (ddlVeterinario.SelectedValue == "0") { Session.Remove("VeterinarioID_Reserva"); }
            else { Session["VeterinarioID_Reserva"] = Convert.ToInt32(ddlVeterinario.SelectedValue); }
            Session.Remove("Hora_Reserva");
            Session.Remove("FechaHoraInicio_Reserva");
            CargarHorariosDisponibles();
            updCalendar.Update();
        }

        protected void rptCalendarDays_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "SelectDay")
            {
                string fechaStr = e.CommandArgument.ToString();
                Session["Fecha_Reserva"] = DateTime.Parse(fechaStr);
                Session.Remove("Hora_Reserva");
                Session.Remove("FechaHoraInicio_Reserva");
                GenerarCalendario(currentCalendarMonth);
                CargarHorariosDisponibles();
                updCalendar.Update();
            }
        }

        protected void rptHorarios_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "SelectTime")
            {
                string horaStr = e.CommandArgument.ToString();
                Session["Hora_Reserva"] = horaStr;
                DateTime fecha = (DateTime)Session["Fecha_Reserva"];
                TimeSpan hora = TimeSpan.Parse(horaStr);
                DateTime fechaHoraInicio = fecha.Add(hora);
                Session["FechaHoraInicio_Reserva"] = fechaHoraInicio;
                CargarHorariosDisponibles();
                updCalendar.Update();
            }
        }

        protected void btnPrevMonth_Click(object sender, EventArgs e)
        {
            currentCalendarMonth = currentCalendarMonth.AddMonths(-1);
            GenerarCalendario(currentCalendarMonth);
            updCalendar.Update();
        }

        protected void btnNextMonth_Click(object sender, EventArgs e)
        {
            currentCalendarMonth = currentCalendarMonth.AddMonths(1);
            GenerarCalendario(currentCalendarMonth);
            updCalendar.Update();
        }

        #endregion

        #region "Botones de Navegación de Pasos"

        // (Sin cambios)
        protected void btnAnterior_Click(object sender, EventArgs e)
        {
            Response.Redirect("Secretaria_ReservarCita_Paso2.aspx");
        }

        // (Sin cambios)
        protected void btnSiguiente_Click(object sender, EventArgs e)
        {
            if (Session["VeterinarioID_Reserva"] != null &&
                Session["FechaHoraInicio_Reserva"] != null)
            {
                Response.Redirect("Secretaria_ReservarCita_Paso4.aspx");
            }
            else
            {
                // (Opcional: Mostrar error)
            }
        }

        #endregion
    }
}