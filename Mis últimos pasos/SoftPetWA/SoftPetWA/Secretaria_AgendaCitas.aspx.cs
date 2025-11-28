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
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.VeterinarioClient;

using personaDto = SoftPetBussiness.PersonaClient.personaDto;
using mascotaDto = SoftPetBussiness.MascotaClient.mascotaDto;
using veterinarioDto = SoftPetBussiness.VeterinarioClient.veterinarioDto;
using citaAtencionDto = SoftPetBussiness.CitaAtencionClient.citaAtencionDto;


namespace SoftPetWA
{
    public partial class Secretaria_AgendaCitas : System.Web.UI.Page
    {
        private PersonaBO boPersona = new PersonaBO();
        private MascotaBO boMascota = new MascotaBO();
        private VeterinarioBO boVeterinario = new VeterinarioBO();
        private CitaAtencionBO boCitaAtencion = new CitaAtencionBO();

        private DateTime FiltroFecha
        {
            get
            {
                if (Session["AgendaFiltroFecha"] == null)
                {
                    Session["AgendaFiltroFecha"] = DateTime.Today;
                }
                return (DateTime)Session["AgendaFiltroFecha"];
            }
            set
            {
                Session["AgendaFiltroFecha"] = value;
            }
        }
        private int FiltroVetId
        {
            get
            {
                if (Session["AgendaFiltroVetId"] == null)
                {
                    Session["AgendaFiltroVetId"] = 0;
                }
                return (int)Session["AgendaFiltroVetId"];
            }
            set
            {
                Session["AgendaFiltroVetId"] = value;
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                FiltroFecha = DateTime.Today;
                FiltroVetId = 0;

                CargarFiltros();
                CargarResumen();
                CargarAgenda();

                ActualizarControlesDeFiltro();
                ActualizarLabelsFecha();
                calAgenda.SelectedDate = FiltroFecha;
                calAgenda.VisibleDate = FiltroFecha;
            }
        }

        #region "Carga de Datos y Filtros"

        private void CargarResumen()
        {
            try
            {
                string fechaStr = FiltroFecha.ToString("yyyy-MM-dd");
                int vetId = FiltroVetId;

                List<citaAtencionDto> citasFiltradas = boCitaAtencion.ListarBusquedaAvanzada2(fechaStr, vetId);

                lblCitasHoy.Text = citasFiltradas.Count.ToString();
                lblConfirmadas.Text = citasFiltradas.Count(c => c.estado.ToString() == "ATENDIDA").ToString();
                lblPendientes.Text = citasFiltradas.Count(c => c.estado.ToString() == "PROGRAMADA").ToString();
                lblCanceladas.Text = citasFiltradas.Count(c => c.estado.ToString() == "CANCELADA").ToString();
            }
            catch (Exception)
            {
                lblCitasHoy.Text = "E"; lblConfirmadas.Text = "E"; lblPendientes.Text = "E"; lblCanceladas.Text = "E";
            }
        }

        private void CargarFiltros()
        {
            try
            {
                List<veterinarioDto> vets = boVeterinario.ListarBusquedaAvanzada("", "", "", -1);
                var vetViewModels = new List<object>();
                foreach (veterinarioDto vet in vets)
                {
                    vetViewModels.Add(new { VeterinarioID = vet.veterinarioId, Nombre = vet.persona.nombre });
                }
                ddlVeterinario.DataSource = vetViewModels;
                ddlVeterinario.DataTextField = "Nombre";
                ddlVeterinario.DataValueField = "VeterinarioID";
                ddlVeterinario.DataBind();
                ddlVeterinario.Items.Insert(0, new ListItem("Todos los Veterinarios", "0"));
            }
            catch (Exception)
            {
                ddlVeterinario.Items.Insert(0, new ListItem("Error al cargar", "0"));
            }
        }

        private void CargarAgenda()
        {
            List<citaAtencionDto> citasDelDia;
            try
            {
                string fechaStr = FiltroFecha.ToString("yyyy-MM-dd");
                int vetId = FiltroVetId;
                citasDelDia = boCitaAtencion.ListarBusquedaAvanzada2(fechaStr, vetId);
            }
            catch (Exception)
            {
                citasDelDia = new List<citaAtencionDto>();
            }

            var citasConFechasParseadas = citasDelDia.Select(c =>
            {
                DateTime? dtInicio = null;
                DateTime? dtFin = null;
                try
                {
                    dtInicio = DateTime.Parse(c.fechaHoraInicioStr, CultureInfo.InvariantCulture);
                    dtFin = DateTime.Parse(c.fechaHoraFinStr, CultureInfo.InvariantCulture);
                }
                catch { }
                return new { Cita = c, FechaInicio = dtInicio, FechaFin = dtFin };
            }).Where(x => x.FechaInicio.HasValue).ToList();


            var viewModelHoras = new List<object>();
            for (int hora = 8; hora <= 18; hora++)
            {
                string horaLabel = $"{hora:00}:00";

                var citasEnEstaHora = citasConFechasParseadas
                    .Where(c => c.FechaInicio.Value.Hour == hora)
                    .OrderBy(c => c.FechaInicio.Value)
                    .ToList();

                var viewModelCitasEnHora = citasEnEstaHora.Select(citaEncontrada =>
                {
                    string clienteNombre = "N/A";
                    string mascotaNombre = "N/A";
                    string veterinarioNombre = "N/A";
                    int personaId = 0;

                    try
                    {
                        if (citaEncontrada.Cita.mascota != null)
                        {
                            mascotaNombre = citaEncontrada.Cita.mascota.nombre;

                            if (citaEncontrada.Cita.mascota.persona != null)
                            {
                                clienteNombre = citaEncontrada.Cita.mascota.persona.nombre;
                                personaId = citaEncontrada.Cita.mascota.persona.personaId;
                            }
                        }

                        if (citaEncontrada.Cita.veterinario != null)
                        {
                            if (citaEncontrada.Cita.veterinario.persona != null)
                                veterinarioNombre = citaEncontrada.Cita.veterinario.persona.nombre;
                        }
                    }
                    catch (Exception) { }

                    string horaCita = $"{citaEncontrada.FechaInicio.Value:HH:mm} - {citaEncontrada.FechaFin.Value:HH:mm}";
                    string statusMayuscula = citaEncontrada.Cita.estado.ToString();
                    string statusCapitalizado = CultureInfo.CurrentCulture.TextInfo.ToTitleCase(statusMayuscula.ToLower());
                    string cssClass = "status-" + statusCapitalizado.ToLower();

                    return new
                    {
                        CitaId = citaEncontrada.Cita.citaId,
                        PersonaId = personaId,
                        HoraCita = horaCita,
                        Cliente = clienteNombre,
                        MascotaNombre = mascotaNombre,
                        VeterinarioNombre = veterinarioNombre,
                        Status = statusCapitalizado,
                        Estado = statusMayuscula,
                        CssClass = cssClass
                    };
                }).ToList();

                viewModelHoras.Add(new
                {
                    HoraLabel = horaLabel,
                    Citas = viewModelCitasEnHora
                });
            }

            rptHoras.DataSource = viewModelHoras;
            rptHoras.DataBind();
        }

        #endregion

        #region "Eventos de Botones"

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            try { FiltroFecha = DateTime.Parse(txtFecha.Text); }
            catch { FiltroFecha = DateTime.Today; }
            FiltroVetId = Convert.ToInt32(ddlVeterinario.SelectedValue);

            CargarAgenda();
            CargarResumen();
            ActualizarLabelsFecha();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            FiltroFecha = DateTime.Today;
            FiltroVetId = 0;
            CargarAgenda();
            CargarResumen();
            ActualizarControlesDeFiltro();
            ActualizarLabelsFecha();
        }

        protected void lnkAnterior_Click(object sender, EventArgs e)
        {
            FiltroFecha = FiltroFecha.AddDays(-1);
            CargarAgenda();
            CargarResumen();
            ActualizarControlesDeFiltro();
            ActualizarLabelsFecha();
        }

        protected void lnkSiguiente_Click(object sender, EventArgs e)
        {
            FiltroFecha = FiltroFecha.AddDays(1);
            CargarAgenda();
            CargarResumen();
            ActualizarControlesDeFiltro();
            ActualizarLabelsFecha();
        }

        protected void btnHoy_Click(object sender, EventArgs e)
        {
            FiltroFecha = DateTime.Today;
            CargarAgenda();
            CargarResumen();
            ActualizarControlesDeFiltro();
            ActualizarLabelsFecha();
        }

        protected void calAgenda_SelectionChanged(object sender, EventArgs e)
        {
            FiltroFecha = calAgenda.SelectedDate;
            CargarAgenda();
            CargarResumen();
            ActualizarControlesDeFiltro();
            ActualizarLabelsFecha();
            pnlVistaLista.Visible = true;
            pnlVistaCalendario.Visible = false;
            btnVistaCalendario.CssClass = "btn btn-outline-secondary me-2";
            btnVistaLista.CssClass = "btn btn-secondary";
        }

        protected void calAgenda_DayRender(object sender, DayRenderEventArgs e)
        {
            try
            {
                int vetId = FiltroVetId;
                string fechaStr = e.Day.Date.ToString("yyyy-MM-dd");
                var citasEnFecha = boCitaAtencion.ListasBusquedaAvanzadaPoridVet(fechaStr, vetId).Count;
                if (citasEnFecha > 0)
                {
                    e.Cell.Controls.Add(new LiteralControl($"<span class='cal-dot'>{citasEnFecha}</span>"));
                }
            }
            catch (Exception) { }
        }

        private void ActualizarLabelsFecha()
        {
            CultureInfo ci = new CultureInfo("es-ES");
            lblFechaActual.Text = FiltroFecha.ToString("dddd, dd 'de' MMMM yyyy", ci);
            calAgenda.SelectedDate = FiltroFecha;
            calAgenda.VisibleDate = FiltroFecha;
        }

        private void ActualizarControlesDeFiltro()
        {
            txtFecha.Text = FiltroFecha.ToString("yyyy-MM-dd");
            ddlVeterinario.SelectedValue = FiltroVetId.ToString();
        }

        #endregion

        #region "Eventos de Modals (Flujo Nueva Cita)"

        protected void btnNuevaCita_Click(object sender, EventArgs e)
        {
            txtBuscarTermino.Text = "";
            rptResultadosPacientes.DataSource = new List<personaDto>();
            rptResultadosPacientes.DataBind();
            ActualizarLabelFooter("Ingrese el nombre de la persona a buscar.");
            updModalBusqueda.Update();
            ScriptManager.RegisterStartupScript(this, this.GetType(), "showModalBuscarPaciente", "$('#modalBuscarPaciente').modal('show');", true);
        }

        protected void btnBuscarPaciente_Click(object sender, EventArgs e)
        {
            string termino = txtBuscarTermino.Text.Trim();
            List<personaDto> resultados;
            string errorMsg = null;
            try
            {
                resultados = boPersona.ListarBusquedaAvanzada(termino, "", "", -1);
            }
            catch (Exception ex) { errorMsg = "Error: " + ex.Message; resultados = new List<personaDto>(); }

            rptResultadosPacientes.DataSource = resultados;
            rptResultadosPacientes.DataBind();
            if (resultados.Count == 0) { ActualizarLabelFooter(errorMsg ?? "No se encontraron coincidencias."); }

            updModalBusqueda.Update();
        }

        protected void btnSeleccionarPaciente_Click(object sender, EventArgs e)
        {
            LinkButton btn = (LinkButton)sender;
            int clienteID = Convert.ToInt32(btn.CommandArgument);
            Session["ClienteID_Reserva"] = clienteID;
            Response.Redirect("Secretaria_ReservarCita_Paso1.aspx");
        }

        protected void btnGuardarCita_Click(object sender, EventArgs e)
        {
        }

        private void ActualizarLabelFooter(string mensaje)
        {
            if (rptResultadosPacientes.Items.Count == 0)
            {
                foreach (Control control in rptResultadosPacientes.Controls)
                {
                    if (control is RepeaterItem item && item.ItemType == ListItemType.Footer)
                    {
                        Label lbl = item.FindControl("lblNoResultados") as Label;
                        if (lbl != null)
                        {
                            lbl.Text = mensaje;
                            break;
                        }
                    }
                }
            }
        }

        #endregion

        #region "Eventos de Cita (Modificar Estado / Emitir Comprobante)"

        protected void rptHoras_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                Repeater rptCitasEnHora = e.Item.FindControl("rptCitasEnHora") as Repeater;
                if (rptCitasEnHora != null)
                {
                    rptCitasEnHora.ItemCommand += rptCitasEnHora_ItemCommand;
                }
            }
        }

        protected void rptCitasEnHora_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // CAMBIO: Lógica para redirigir al punto de venta
            if (e.CommandName == "EmitirComprobante")
            {
                string[] args = e.CommandArgument.ToString().Split(',');

                // Estos argumentos vienen del CommandArgument en el ASPX: Eval("CitaId") + "," + Eval("PersonaId")
                int citaId = Convert.ToInt32(args[0]);
                int personaId = Convert.ToInt32(args[1]);

                // Guardamos en sesión para que la pantalla de Punto de Venta sepa qué cargar
                Session["Comprobante_CitaId"] = citaId;
                Session["Comprobante_PersonaId"] = personaId;

                Response.Redirect("Secretaria_PuntoVenta.aspx");
            }

            if (e.CommandName == "ModificarEstado")
            {
                int citaId = Convert.ToInt32(e.CommandArgument);
                try
                {
                    citaAtencionDto cita = boCitaAtencion.ObtenerPorId(citaId);
                    if (cita != null)
                    {
                        hdCitaIdModificar.Value = cita.citaId.ToString();
                        ddlNuevoEstado.SelectedValue = cita.estado.ToString();
                        updModalEstado.Update();

                        ScriptManager.RegisterStartupScript(this, this.GetType(), "showModalModificarEstado",
                            "$('#modalModificarEstado').modal('show');", true);
                    }
                }
                catch (Exception) { }
            }
        }

        protected void btnGuardarEstado_Click(object sender, EventArgs e)
        {
            try
            {
                int citaId = Convert.ToInt32(hdCitaIdModificar.Value);
                string nuevoEstado = ddlNuevoEstado.SelectedValue;

                citaAtencionDto cita = boCitaAtencion.ObtenerPorId(citaId);

                if (cita != null)
                {
                    boCitaAtencion.Modificar(cita.citaId,
                                            cita.veterinario.veterinarioId,
                                            cita.mascota.mascotaId,
                                            cita.fechaHoraInicioStr,
                                            cita.fechaHoraFinStr,
                                            cita.pesoMascota,
                                            cita.monto,
                                            nuevoEstado,
                                            cita.observacion,
                                            cita.activo);

                    CargarAgenda();
                    CargarResumen();
                }
            }
            catch (Exception) { }
        }

        #endregion
    }
}