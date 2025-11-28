using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.PersonaClient;

// Alias para evitar conflictos
using mascotaDto = SoftPetBussiness.MascotaClient.mascotaDto;
using citaAtencionDto = SoftPetBussiness.CitaAtencionClient.citaAtencionDto;

namespace SoftPetWA
{
    public partial class Cliente_Inicio : System.Web.UI.Page
    {
        private PersonaBO boPersona;
        private MascotaBO boMascota;
        private CitaAtencionBO boCita;

        protected void Page_Init(object sender, EventArgs e)
        {
            boPersona = new PersonaBO();
            boMascota = new MascotaBO();
            boCita = new CitaAtencionBO();
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
                IdentificarCliente();
                CargarDashboard();
            }
        }

        private void IdentificarCliente()
        {
            if (Session["PersonaId"] == null)
            {
                try
                {
                    int uid = Convert.ToInt32(Session["UsuarioId"]);
                    var todas = boPersona.ListarBusquedaAvanzada("", "", "", -1);
                    var p = todas.FirstOrDefault(x => x.usuario != null && x.usuario.usuarioId == uid);
                    if (p != null)
                    {
                        Session["PersonaId"] = p.personaId;
                        lblNombreCliente.Text = p.nombre;
                    }
                    else
                    {
                        lblNombreCliente.Text = "Cliente";
                    }
                }
                catch
                {
                    lblNombreCliente.Text = "Cliente";
                }
            }
            else
            {
                string nombre = this.boPersona.ObtenerPorId(Convert.ToInt32(Session["PersonaId"])).nombre;
                lblNombreCliente.Text = nombre != null ? nombre : "Cliente";
            }
        }

        private void CargarDashboard()
        {
            try
            {
                int clienteId = Session["PersonaId"] != null ? (int)Session["PersonaId"] : 0;
                if (clienteId == 0) return;

                // 1. OBTENER MASCOTAS
                List<mascotaDto> misMascotas = boMascota.ListarPorIdPersona(clienteId);
                if (misMascotas == null) misMascotas = new List<mascotaDto>();

                // KPI 1: Total Mascotas
                litTotalMascotas.Text = misMascotas.Count.ToString();

                // Bindear Tarjetas de Mascotas (Top 3)
                var listaMascotasVisual = misMascotas.Take(3).Select(m => new
                {
                    Nombre = m.nombre,
                    Detalles = $"{m.raza} • {(m.sexo == "M" ? "Macho" : "Hembra")}",
                    IconoClass = ObtenerIconoEspecie(m.especie),
                    AvatarClass = ObtenerClaseAvatar(m.especie),
                    Estado = m.activo ? "Activo" : "Inactivo",
                    EstadoCss = m.activo ? "text-success" : "text-secondary"
                }).ToList();

                rptrMascotas.DataSource = listaMascotasVisual;
                rptrMascotas.DataBind();


                // 2. OBTENER CITAS DE HOY
                List<citaAtencionDto> citasDeHoy = new List<citaAtencionDto>();
                DateTime hoy = DateTime.Today; // Fecha sin hora (00:00:00)

                foreach (var m in misMascotas)
                {
                    // Traemos el historial de la mascota
                    var citasMascota = boCita.ListarCitasPorMascota(m.mascotaId);
                    if (citasMascota != null)
                    {
                        // Filtramos en memoria solo las que coinciden con la fecha de hoy
                        var delDia = citasMascota.Where(c => ParsearFecha(c.fechaHoraInicioStr).Date == hoy).ToList();
                        citasDeHoy.AddRange(delDia);
                    }
                }

                // Ordenar por hora
                citasDeHoy = citasDeHoy.OrderBy(c => c.fechaHoraInicioStr).ToList();

                // KPI 2: Citas de Hoy
                litCitasHoy.Text = citasDeHoy.Count.ToString();

                // Bindear Lista Citas
                var listaCitasVisual = citasDeHoy.Select(c => {
                    DateTime fechaInicio = ParsearFecha(c.fechaHoraInicioStr);

                    string servicio = string.IsNullOrEmpty(c.observacion) ? "Consulta General" : c.observacion;
                    string nombreVet = (c.veterinario != null && c.veterinario.persona != null) ? c.veterinario.persona.nombre : "Por asignar";
                    string nombreMascota = c.mascota != null ? c.mascota.nombre : "Mascota";

                    return new
                    {
                        Hora = fechaInicio.ToString("hh:mm"),
                        AmPm = fechaInicio.ToString("tt", CultureInfo.InvariantCulture),
                        // Como es hoy, mostramos "HOY" explícitamente o la fecha
                        Fecha = "HOY",
                        Servicio = servicio,
                        Doctor = nombreVet,
                        Mascota = nombreMascota,
                        Estado = c.estado.ToString(),
                        EstadoCss = ObtenerCssEstado(c.estado.ToString())
                    };
                }).ToList();

                rptrCitasHoy.DataSource = listaCitasVisual;
                rptrCitasHoy.DataBind();

            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine("Error Dashboard: " + ex.Message);
            }
        }

        // --- HELPERS ---

        private DateTime ParsearFecha(string fechaStr)
        {
            if (DateTime.TryParseExact(fechaStr, "yyyy-MM-dd HH:mm:ss", CultureInfo.InvariantCulture, DateTimeStyles.None, out DateTime dt))
                return dt;
            // Intento alternativo si el formato varía
            if (DateTime.TryParse(fechaStr, out dt)) return dt;
            return DateTime.MinValue;
        }

        private string ObtenerIconoEspecie(string especie)
        {
            if (string.IsNullOrEmpty(especie)) return "fas fa-paw";
            string e = especie.ToLower();
            if (e.Contains("perro") || e.Contains("canino")) return "fas fa-dog";
            if (e.Contains("gato") || e.Contains("felino")) return "fas fa-cat";
            return "fas fa-paw";
        }

        private string ObtenerClaseAvatar(string especie)
        {
            if (string.IsNullOrEmpty(especie)) return "bg-soft-primary text-primary";
            string e = especie.ToLower();
            if (e.Contains("gato")) return "bg-soft-warning text-warning";
            return "bg-soft-primary text-primary";
        }

        private string ObtenerCssEstado(string estado)
        {
            estado = (estado ?? "").ToUpper();
            if (estado.Contains("PROGRAMADA") || estado.Contains("PENDIENTE")) return "badge-soft-warning";
            if (estado.Contains("ATENDIDA") || estado.Contains("CONFIRMADA")) return "badge-soft-success";
            if (estado.Contains("CANCELADA")) return "badge-soft-danger";
            return "badge-soft-secondary";
        }
    }
}