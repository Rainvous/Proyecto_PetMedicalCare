using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.VeterinarioClient;

// Alias
using personaDto = SoftPetBussiness.PersonaClient.personaDto;
using veterinarioDto = SoftPetBussiness.VeterinarioClient.veterinarioDto;

namespace SoftPetWA
{
    public partial class Cliente_ReservarCita_Paso3 : System.Web.UI.Page
    {
        private PersonaBO boPersona = new PersonaBO();
        private VeterinarioBO boVeterinario = new VeterinarioBO();
        private CitaAtencionBO boCitaAtencion = new CitaAtencionBO();

        // Propiedad para mantener el mes actual del calendario en ViewState
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
                // 1. Validar que venimos de los pasos anteriores
                if (Session["ServiciosID_Reserva"] == null ||
                    Session["MascotaID_Reserva"] == null ||
                    Session["ClienteID_Reserva"] == null)
                {
                    Response.Redirect("Cliente_ReservarCita_Paso1.aspx");
                    return;
                }

                // 2. Limpiar selección previa de este paso para obligar a elegir
                Session.Remove("VeterinarioID_Reserva");
                Session.Remove("Fecha_Reserva");
                Session.Remove("Hora_Reserva");
                Session.Remove("FechaHoraInicio_Reserva");

                CargarVeterinarios();
                GenerarCalendario(currentCalendarMonth);
                CargarHorariosDisponibles();
            }
        }

        private void CargarVeterinarios()
        {
            try
            {
                ddlVeterinario.Items.Clear();

                // 1. Llamamos al BO para traer SOLO los activos
                List<veterinarioDto> vets = boVeterinario.ListarActivos();

                if (vets == null) vets = new List<veterinarioDto>();

                List<object> vetViewModels = new List<object>();

                foreach (veterinarioDto vet in vets)
                {
                    // 2. DOBLE VERIFICACIÓN DE ESTADO Y NULOS
                    if (vet != null && vet.persona != null && vet.estado.ToString().ToUpper() == "ACTIVO")
                    {
                        string nombreMostrar = "Vet. Sin Nombre";
                        try
                        {
                            if (vet.persona.personaId > 0)
                            {
                                personaDto personaVet = boPersona.ObtenerPorId(vet.persona.personaId);
                                if (personaVet != null)
                                {
                                    nombreMostrar = personaVet.nombre;
                                }
                            }
                        }
                        catch { }

                        // Añadimos a la lista visual
                        vetViewModels.Add(new { VeterinarioID = vet.veterinarioId, Nombre = nombreMostrar });
                    }
                }

                // 3. Enlazar al DropDownList
                ddlVeterinario.DataSource = vetViewModels;
                ddlVeterinario.DataTextField = "Nombre";
                ddlVeterinario.DataValueField = "VeterinarioID";
                ddlVeterinario.DataBind();

                // 4. Opción por defecto
                ddlVeterinario.Items.Insert(0, new ListItem("-- Seleccione un Veterinario --", "0"));
            }
            catch (Exception ex)
            {
                ddlVeterinario.Items.Clear();
                ddlVeterinario.Items.Insert(0, new ListItem("No se pudieron cargar veterinarios", "0"));
            }
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
                // Llamada al BO para ver citas ocupadas
                List<citaProgramadaDto> horarios = boCitaAtencion.ListarCitasProgramadaEseDia(vetId, fechaStr);
                string horaSeleccionada = Session["Hora_Reserva"] as string;

                var horariosVM = horarios.Select(h =>
                {
                    DateTime dt = DateTime.Parse(h.fecha, CultureInfo.InvariantCulture);
                    string horaUI = dt.ToString("hh:mm tt", CultureInfo.InvariantCulture);
                    string horaComando = dt.ToString("HH:mm");
                    string css = "time-slot-btn";
                    if (horaComando == horaSeleccionada) { css += " selected"; }

                    // ================================================================
                    // CORRECCIÓN DE LÓGICA INVERSA
                    // ================================================================
                    // Si estaProgramada es TRUE, significa que el slot ESTÁ DISPONIBLE
                    // Si es FALSE, significa que NO es horario laboral o está ocupado.
                    bool estaDisponible = h.estaProgramada; // <-- Ya no tiene el '!'

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
            catch (Exception)
            {
                lblHorariosDisponibles.Text = "Error al cargar horarios";
            }
        }

        // EVENTOS

        protected void ddlVeterinario_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (ddlVeterinario.SelectedValue == "0") Session.Remove("VeterinarioID_Reserva");
            else Session["VeterinarioID_Reserva"] = Convert.ToInt32(ddlVeterinario.SelectedValue);

            Session.Remove("Hora_Reserva");
            Session.Remove("FechaHoraInicio_Reserva");
            CargarHorariosDisponibles();
            updCalendar.Update();
        }

        protected void rptCalendarDays_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "SelectDay")
            {
                Session["Fecha_Reserva"] = DateTime.Parse(e.CommandArgument.ToString());
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

                // Construir la fecha completa (DATETIME)
                DateTime fecha = (DateTime)Session["Fecha_Reserva"];
                TimeSpan hora = TimeSpan.Parse(horaStr);
                DateTime fechaHoraInicio = fecha.Add(hora);

                // ¡AQUÍ SE GUARDA LA VARIABLE CLAVE QUE BUSCA EL PASO 4!
                Session["FechaHoraInicio_Reserva"] = fechaHoraInicio;

                // Borramos error si había
                lblError.Visible = false;

                CargarHorariosDisponibles(); // Para actualizar estilos (clase 'selected')
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

        protected void btnSiguiente_Click(object sender, EventArgs e)
        {
            // Validar que tengamos todo antes de avanzar
            if (Session["VeterinarioID_Reserva"] != null && Session["FechaHoraInicio_Reserva"] != null)
            {
                Response.Redirect("Cliente_ReservarCita_Paso4.aspx");
            }
            else
            {
                lblError.Text = "Por favor, seleccione un veterinario, una fecha y una hora.";
                lblError.Visible = true;
                updCalendar.Update();
            }
        }

        protected void btnAnterior_Click(object sender, EventArgs e)
        {
            Response.Redirect("Cliente_ReservarCita_Paso2.aspx");
        }
    }
}