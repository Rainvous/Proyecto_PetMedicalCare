using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.VeterinarioClient;

using mascotaDto = SoftPetBussiness.MascotaClient.mascotaDto;
using citaAtencionDto = SoftPetBussiness.CitaAtencionClient.citaAtencionDto;
using personaDto = SoftPetBussiness.PersonaClient.personaDto;

namespace SoftPetWA
{
    public partial class Cliente_HistorialMedico : System.Web.UI.Page
    {
        private MascotaBO boMascota = new MascotaBO();
        private CitaAtencionBO boCitaAtencion = new CitaAtencionBO();
        private VeterinarioBO boVeterinario = new VeterinarioBO();
        private PersonaBO boPersona = new PersonaBO();

        private int MascotaIdActual
        {
            get { return ViewState["MascotaIdActual"] != null ? (int)ViewState["MascotaIdActual"] : 0; }
            set { ViewState["MascotaIdActual"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                string idStr = Request.QueryString["mascotaId"];
                if (int.TryParse(idStr, out int id) && id > 0)
                {
                    MascotaIdActual = id;
                    CargarDetallesMascota(id);
                    CargarHistorial(id);
                }
                else
                {
                    Response.Redirect("Cliente_Mascotas.aspx");
                }
            }
        }

        private void CargarDetallesMascota(int mascotaId)
        {
            try
            {
                mascotaDto mascota = boMascota.ObtenerPorId(mascotaId);
                if (mascota != null)
                {
                    bool esPerro = (mascota.especie != null && (mascota.especie.ToLower().Contains("perro") || mascota.especie.ToLower().Contains("canino")));
                    imgAvatarMascotaHist.ImageUrl = esPerro ? "~/Images/Avatars/dog-avatar.png" : "~/Images/Avatars/cat-avatar.png";
                    lblNombreMascotaHist.Text = mascota.nombre;
                    lblRazaMascotaHist.Text = $"{mascota.especie} - {mascota.raza}";
                    lblSexoHist.Text = mascota.sexo == "M" ? "Macho" : "Hembra";
                    lblColorHist.Text = mascota.color;
                    lblEdadHist.Text = "-";
                    lblPesoHist.Text = "-";
                    lblVisitasHist.Text = "0";
                }
            }
            catch { }
        }

        private void CargarHistorial(int mascotaId)
        {
            try
            {
                List<citaAtencionDto> historial = boCitaAtencion.ListarCitasPorMascota(mascotaId);
                lblVisitasHist.Text = $"{historial.Count} registros";

                var ultimaAtendida = historial.Where(c => c.estado.ToString() == "ATENDIDA")
                                              .OrderByDescending(c => c.citaId).FirstOrDefault();
                if (ultimaAtendida != null) lblPesoHist.Text = $"{ultimaAtendida.pesoMascota} kg";

                // Tabla para el Repeater
                DataTable dt = new DataTable();
                dt.Columns.Add("FechaHora", typeof(string));
                dt.Columns.Add("Titulo", typeof(string));
                dt.Columns.Add("Doctor", typeof(string));
                dt.Columns.Add("BadgeTexto", typeof(string));
                dt.Columns.Add("ClaseBadge", typeof(string)); // bg-soft-success, etc.
                dt.Columns.Add("ClaseDot", typeof(string));   // dot-atendida, etc.
                dt.Columns.Add("IconoTitulo", typeof(string));// fas fa-stethoscope
                dt.Columns.Add("Diagnostico", typeof(string));
                dt.Columns.Add("Observaciones", typeof(string));
                dt.Columns.Add("Peso", typeof(string));
                dt.Columns.Add("Temperatura", typeof(string));
                dt.Columns.Add("Frecuencia", typeof(string));

                foreach (var cita in historial.OrderByDescending(c => c.citaId))
                {
                    string nombreDoc = "Dr. Asignado";
                    try
                    {
                        var vet = boVeterinario.ObtenerPorId(cita.veterinario.veterinarioId);
                        var personaVet = boPersona.ObtenerPorId(vet.persona.personaId);
                        nombreDoc = "Dr. " + personaVet.nombre;
                    }
                    catch { }

                    string estado = cita.estado.ToString();

                    // --- CONFIGURACIÓN VISUAL DINÁMICA ---
                    string claseBadge = "bg-soft-secondary";
                    string claseDot = "dot-programada";
                    string badgeTexto = estado;
                    string titulo = "Consulta General";
                    string icono = "fas fa-stethoscope";

                    if (estado == "ATENDIDA")
                    {
                        claseBadge = "bg-soft-success"; // Verde
                        claseDot = "dot-atendida";
                        badgeTexto = "COMPLETADA";
                        titulo = "Consulta Médica";
                        icono = "fas fa-check-circle";
                    }
                    else if (estado == "PROGRAMADA")
                    {
                        claseBadge = "bg-soft-primary"; // Azul
                        claseDot = "dot-programada";
                        badgeTexto = "PROGRAMADA";
                        titulo = "Cita Programada";
                        icono = "far fa-calendar-alt";
                    }
                    else if (estado == "CANCELADA")
                    {
                        claseBadge = "bg-soft-danger"; // Rojo
                        claseDot = "dot-cancelada";
                        badgeTexto = "CANCELADA";
                        titulo = "Cita Cancelada";
                        icono = "fas fa-ban";
                    }

                    // --- CORRECCIÓN DE FECHA ---
                    string fechaFormateada = "";
                    try
                    {
                        // 1. Intentamos ToString directo (muchos proxies lo soportan)
                        string raw = cita.fechaHoraInicio.ToString();
                        // Si contiene "timestamp", falló el ToString, vamos al plan B
                        if (!raw.ToLower().Contains("timestamp"))
                        {
                            DateTime dtParse = DateTime.Parse(raw);
                            fechaFormateada = dtParse.ToString("dd MMM yyyy - h:mm tt", new CultureInfo("es-ES"));
                        }
                        else
                        {
                            throw new Exception("Formato timestamp detectado");
                        }
                    }
                    catch
                    {
                        try
                        {
                            // 2. Plan B: Leer milisegundos (propiedad .time) usando dynamic
                            dynamic ts = cita.fechaHoraInicio;
                            long millis = (long)ts.time;
                            DateTime dtMath = new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeKind.Utc).AddMilliseconds(millis).ToLocalTime();
                            fechaFormateada = dtMath.ToString("dd MMM yyyy - h:mm tt", new CultureInfo("es-ES"));
                        }
                        catch
                        {
                            fechaFormateada = DateTime.Now.ToString("dd/MM/yyyy"); // Fallback final
                        }
                    }

                    // --- DATOS ---
                    string obs = string.IsNullOrEmpty(cita.observacion) ? "Sin observaciones registradas." : cita.observacion;
                    string diag = ""; // Por defecto vacío
                    string peso = "-";

                    if (estado == "ATENDIDA")
                    {
                        // Si atendida, usamos la observación como "Diagnóstico/Resumen"
                        diag = obs; // Mostramos esto destacado
                        obs = "Tratamiento y recomendaciones indicadas en consulta.";
                        peso = cita.pesoMascota > 0 ? $"{cita.pesoMascota} kg" : "-";
                    }
                    else if (estado == "PROGRAMADA")
                    {
                        obs = "Cita pendiente de atención.";
                    }

                    dt.Rows.Add(
                        fechaFormateada,
                        titulo,
                        nombreDoc,
                        badgeTexto,
                        claseBadge,
                        claseDot,
                        icono,
                        diag,
                        obs,
                        peso,
                        "-", "-" // Temp y Frec
                    );
                }

                rptHistorialCliente.DataSource = dt;
                rptHistorialCliente.DataBind();
            }
            catch
            {
                rptHistorialCliente.DataSource = null;
                rptHistorialCliente.DataBind();
            }
        }

        // --- Eventos ---
        protected void rptFiltroTipoHist_ItemCommand(object source, RepeaterCommandEventArgs e) { }
        protected void rptHistorialCliente_ItemCommand(object source, RepeaterCommandEventArgs e) { }
        protected void btnImprimir_Click(object sender, EventArgs e) { }

        protected void btnAgendarCita_Click(object sender, EventArgs e)
        {
            // 1. Guardar ID de la Mascota (Ya lo tenías)
            Session["MascotaID_Reserva"] = MascotaIdActual;

            // 2. ¡CORRECCIÓN! Guardar también el ID del Cliente
            // Como el usuario ya está logueado, tomamos su PersonaId de la sesión principal
            if (Session["PersonaId"] != null)
            {
                Session["ClienteID_Reserva"] = Session["PersonaId"];
            }

            // 3. Limpiar datos de pasos posteriores por si quedaron sucios de una reserva anterior
            Session.Remove("ServiciosID_Reserva");
            Session.Remove("VeterinarioID_Reserva");
            Session.Remove("FechaHoraInicio_Reserva");

            // 4. Redirigir al Paso 2 (Servicios)
            Response.Redirect("Cliente_ReservarCita_Paso2.aspx");
        }

        protected void btnCambiarMascota_Click(object sender, EventArgs e)
        {
            Response.Redirect("Cliente_Mascotas.aspx");
        }
    }
}