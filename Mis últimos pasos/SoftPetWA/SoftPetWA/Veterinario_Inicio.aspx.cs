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

// Alias DTO
using citaAtencionDto = SoftPetBussiness.CitaAtencionClient.citaAtencionDto;

namespace SoftPetWA
{
    public partial class Veterinario_Inicio : System.Web.UI.Page
    {
        private PersonaBO boPersona = new PersonaBO();
        private CitaAtencionBO boCita = new CitaAtencionBO();
        private MascotaBO boMascota = new MascotaBO();
        private VeterinarioBO boVet = new VeterinarioBO();

        private int VeterinarioLogueadoId
        {
            get { return Session["VeterinarioIdLogueado"] != null ? (int)Session["VeterinarioIdLogueado"] : 0; }
            set { Session["VeterinarioIdLogueado"] = value; }
        }

        private string FiltroActual
        {
            get { return ViewState["FiltroDashboard"] != null ? (string)ViewState["FiltroDashboard"] : "TODOS"; }
            set { ViewState["FiltroDashboard"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["UsuarioId"] == null)
            {
                Response.Redirect("Login.aspx");
                return;
            }

            if (!IsPostBack)
            {
                IdentificarVeterinario();
                if (VeterinarioLogueadoId > 0)
                {
                    CargarDashboardReal();
                }
            }
        }

        // --- BOTONES DE FILTRO ---
        protected void btnFiltroTodos_Click(object sender, EventArgs e) { FiltroActual = "TODOS"; ActualizarEstilosBotones(); CargarDashboardReal(); }
        protected void btnFiltroPendientes_Click(object sender, EventArgs e) { FiltroActual = "PENDIENTES"; ActualizarEstilosBotones(); CargarDashboardReal(); }
        protected void btnFiltroAtendidas_Click(object sender, EventArgs e) { FiltroActual = "ATENDIDAS"; ActualizarEstilosBotones(); CargarDashboardReal(); }

        private void ActualizarEstilosBotones()
        {
            btnFiltroTodos.CssClass = "btn btn-inactive";
            btnFiltroPendientes.CssClass = "btn btn-inactive";
            btnFiltroAtendidas.CssClass = "btn btn-inactive";

            switch (FiltroActual)
            {
                case "TODOS": btnFiltroTodos.CssClass = "btn btn-active"; break;
                case "PENDIENTES": btnFiltroPendientes.CssClass = "btn btn-active"; break;
                case "ATENDIDAS": btnFiltroAtendidas.CssClass = "btn btn-active"; break;
            }
        }

        private void IdentificarVeterinario()
        {
            try
            {
                if (Session["Master_Nombre"] != null) litNombreVeterinario.Text = Session["Master_Nombre"].ToString();
                else litNombreVeterinario.Text = Session["UsuarioNombre"] != null ? Session["UsuarioNombre"].ToString() : "Doctor";

                if (VeterinarioLogueadoId == 0 && Session["UsuarioId"] != null)
                {
                    int uid = Convert.ToInt32(Session["UsuarioId"]);
                    var persona = boPersona.ListarPersonasActivas().FirstOrDefault(p => p.usuario != null && p.usuario.usuarioId == uid);
                    if (persona != null)
                    {
                        var listaVets = boVet.ListarTodos();
                        var miPerfilVet = listaVets.FirstOrDefault(v => v.persona != null && v.persona.personaId == persona.personaId);
                        if (miPerfilVet != null) VeterinarioLogueadoId = miPerfilVet.veterinarioId;
                    }
                }
            }
            catch { litNombreVeterinario.Text = "Doctor"; }
        }

        // ==========================================
        //  CARGA DASHBOARD DIARIO
        // ==========================================
        private void CargarDashboardReal()
        {
            try
            {
                string fechaHoyStr = DateTime.Now.ToString("yyyy-MM-dd");

                // Usamos la búsqueda optimizada SOLO para el día de hoy (rápido)
                List<citaAtencionDto> citasHoy = boCita.ListarBusquedaAvanzada2(fechaHoyStr, VeterinarioLogueadoId);
                if (citasHoy == null) citasHoy = new List<citaAtencionDto>();

                // Eliminar duplicados por si el SP falla
                var citasUnicasHoy = citasHoy.GroupBy(c => c.citaId).Select(g => g.First()).ToList();

                // KPIs Diarios
                int totalHoy = citasUnicasHoy.Count;
                int pendientesHoy = citasUnicasHoy.Count(c => EsPendiente(c.estado));
                int atendidasHoy = citasUnicasHoy.Count(c => EsAtendida(c.estado));

                litNumCitas.Text = totalHoy.ToString();
                litCitasHoy.Text = totalHoy.ToString();
                litCitasPendientes.Text = pendientesHoy.ToString();
                litCitasAtendidas.Text = atendidasHoy.ToString();

                // Lista Visual
                List<citaAtencionDto> citasFiltradas = new List<citaAtencionDto>();
                if (FiltroActual == "TODOS") citasFiltradas = citasUnicasHoy;
                else if (FiltroActual == "PENDIENTES") citasFiltradas = citasUnicasHoy.Where(c => EsPendiente(c.estado)).ToList();
                else if (FiltroActual == "ATENDIDAS") citasFiltradas = citasUnicasHoy.Where(c => EsAtendida(c.estado)).ToList();

                var listaVisual = new List<object>();
                foreach (var c in citasFiltradas.OrderBy(x => x.fechaHoraInicioStr))
                {
                    DateTime fechaInicio = ParsearFechaSegura(c.fechaHoraInicio, c.fechaHoraInicioStr);
                    string horaMostrar = (fechaInicio != DateTime.MinValue) ? fechaInicio.ToString("hh:mm", CultureInfo.InvariantCulture) : "--:--";
                    string ampm = (fechaInicio != DateTime.MinValue) ? fechaInicio.ToString("tt", CultureInfo.InvariantCulture) : "";

                    string nombreMascota = "Mascota";
                    string detallesMascota = "Sin detalles";
                    string nombreDuenio = "Cliente";
                    string iconoMascota = "fas fa-paw";
                    string claseAvatar = "avatar-default";

                    if (c.mascota != null)
                    {
                        try
                        {
                            if (!string.IsNullOrEmpty(c.mascota.nombre))
                            {
                                nombreMascota = c.mascota.nombre;
                                detallesMascota = c.mascota.raza ?? "";
                                string esp = (c.mascota.especie ?? "").ToLower();
                                if (esp.Contains("perro") || esp.Contains("canino")) { iconoMascota = "fas fa-dog"; claseAvatar = "avatar-dog"; }
                                else if (esp.Contains("gato") || esp.Contains("felino")) { iconoMascota = "fas fa-cat"; claseAvatar = "avatar-cat"; }
                                if (c.mascota.persona != null) nombreDuenio = c.mascota.persona.nombre;
                            }
                            else
                            {
                                var m = boMascota.ObtenerPorId(c.mascota.mascotaId);
                                if (m != null)
                                {
                                    nombreMascota = m.nombre;
                                    detallesMascota = m.raza;
                                    string esp = (m.especie ?? "").ToLower();
                                    if (esp.Contains("perro")) { iconoMascota = "fas fa-dog"; claseAvatar = "avatar-dog"; }
                                    else if (esp.Contains("gato")) { iconoMascota = "fas fa-cat"; claseAvatar = "avatar-cat"; }
                                    if (m.persona != null)
                                    {
                                        var p = boPersona.ObtenerPorId(m.persona.personaId);
                                        if (p != null) nombreDuenio = p.nombre;
                                    }
                                }
                            }
                        }
                        catch { }
                    }

                    string estadoStr = c.estado != null ? c.estado.ToString().ToUpper().Trim() : "DESC";
                    string cssClass = "badge-soft-secondary";
                    string btnTexto = "Ver";

                    if (EsPendiente(estadoStr)) { cssClass = "badge-soft-warning"; btnTexto = "Atender"; }
                    else if (EsAtendida(estadoStr)) { cssClass = "badge-soft-success"; btnTexto = "Ver Consulta"; }
                    else if (EsCancelada(estadoStr)) { cssClass = "badge-soft-danger"; btnTexto = "-"; }

                    listaVisual.Add(new
                    {
                        IdCita = c.citaId,
                        HoraCita = horaMostrar,
                        AmPm = ampm,
                        NombrePaciente = nombreMascota,
                        DetallesPaciente = detallesMascota,
                        NombreCliente = nombreDuenio,
                        EstadoCita = estadoStr,
                        EstadoCss = cssClass,
                        BotonPrincipalTexto = btnTexto,
                        IconoMascota = iconoMascota,
                        ClaseAvatar = claseAvatar
                    });
                }
                rptrCitas.DataSource = listaVisual;
                rptrCitas.DataBind();

                // 2. Llamar al Resumen Semanal (Método Robusto)
                CargarResumenSemanal();
            }
            catch (Exception ex) { System.Diagnostics.Debug.WriteLine(ex.Message); }
        }

        // =========================================================
        //  RESUMEN SEMANAL (MÉTODO ROBUSTO - INFALIBLE)
        // =========================================================
        private void CargarResumenSemanal()
        {
            try
            {
                // 1. Estrategia Segura: Traer todo y filtrar en memoria.
                //    Esto garantiza que no falle por formatos de fecha en BD.
                List<citaAtencionDto> todasCitas = boCita.ListarTodos();
                if (todasCitas == null) return;

                // Filtrar solo las de este veterinario
                var misCitas = todasCitas.Where(x => x.veterinario != null && x.veterinario.veterinarioId == VeterinarioLogueadoId).ToList();

                // 2. Definir la Semana Actual
                DateTime hoy = DateTime.Now;
                int diasAlLunes = (7 + (hoy.DayOfWeek - DayOfWeek.Monday)) % 7;

                // Rango: Lunes 00:00:00 hasta Domingo 23:59:59
                DateTime lunesSemana = hoy.AddDays(-1 * diasAlLunes).Date;
                DateTime domingoSemana = lunesSemana.AddDays(6).Date.AddHours(23).AddMinutes(59).AddSeconds(59);

                // 3. Filtrar y Contar
                int totalSemana = 0;
                int vacunasSemana = 0;
                int cirugiasSemana = 0;

                foreach (var c in misCitas)
                {
                    // Usamos el parseo seguro de fecha
                    DateTime fechaCita = ParsearFechaSegura(c.fechaHoraInicio, c.fechaHoraInicioStr);

                    if (fechaCita != DateTime.MinValue)
                    {
                        // Verificar si está dentro de esta semana
                        if (fechaCita >= lunesSemana && fechaCita <= domingoSemana)
                        {
                            // Solo contamos si no está cancelada
                            if (!EsCancelada(c.estado))
                            {
                                totalSemana++; // Sumar al total general

                                string obs = (c.observacion ?? "").ToUpper();

                                // Detectar Vacunas
                                if (obs.Contains("VACUNA") || obs.Contains("INYECC") || obs.Contains("DOSIS"))
                                {
                                    vacunasSemana++;
                                }
                                // Detectar Cirugías
                                else if (obs.Contains("CIRUG") || obs.Contains("ESTERIL") || obs.Contains("OPERAC") || obs.Contains("CASTR"))
                                {
                                    cirugiasSemana++;
                                }
                            }
                        }
                    }
                }

                // 4. Mostrar resultados
                litConsultasSemana.Text = totalSemana.ToString();
                litVacunasSemana.Text = vacunasSemana.ToString();
                litCirugiasSemana.Text = cirugiasSemana.ToString();
            }
            catch
            {
                litConsultasSemana.Text = "0";
                litVacunasSemana.Text = "0";
                litCirugiasSemana.Text = "0";
            }
        }

        // --- Helpers ---
        private DateTime ParsearFechaSegura(object fechaObj, string fechaStr)
        {
            DateTime fechaResult = DateTime.MinValue;
            if (!string.IsNullOrEmpty(fechaStr) && DateTime.TryParseExact(fechaStr, "yyyyMMddHHmmss", CultureInfo.InvariantCulture, DateTimeStyles.None, out fechaResult)) return fechaResult;
            if (!string.IsNullOrEmpty(fechaStr) && DateTime.TryParse(fechaStr, out fechaResult)) return fechaResult;
            if (fechaObj != null) { try { dynamic ts = fechaObj; return new DateTime(1970, 1, 1).AddMilliseconds((long)ts.time).ToLocalTime(); } catch { } }
            return DateTime.MinValue;
        }

        private bool EsPendiente(object estadoObj)
        {
            if (estadoObj == null) return false;
            string st = estadoObj.ToString().ToUpper().Trim();
            return st.Contains("PROGRAMADA") || st.Contains("PENDIENTE");
        }
        private bool EsAtendida(object estadoObj)
        {
            if (estadoObj == null) return false;
            string st = estadoObj.ToString().ToUpper().Trim();
            return st.Contains("ATENDIDA") || st.Contains("COMPLETADA") || st.Contains("FINALIZADA");
        }
        private bool EsCancelada(object estadoObj)
        {
            if (estadoObj == null) return false;
            string st = estadoObj.ToString().ToUpper().Trim();
            return st.Contains("CANCELADA") || st.Contains("ANULADA");
        }

        protected void rptrCitas_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "Atender")
            {
                Response.Redirect($"Veterinario_Consulta.aspx?idCita={e.CommandArgument}");
            }
        }
    }
}