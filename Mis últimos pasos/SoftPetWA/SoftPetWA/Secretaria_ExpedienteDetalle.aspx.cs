using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.RecetaMedicaClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.VeterinarioClient;
using mascotaDto = SoftPetBussiness.MascotaClient.mascotaDto;
using citaAtencionDto = SoftPetBussiness.CitaAtencionClient.citaAtencionDto;

namespace SoftPetWA
{
    public partial class Secretaria_ExpedienteDetalle : System.Web.UI.Page
    {
        private MascotaBO boMascota = new MascotaBO();
        private PersonaBO boPersona = new PersonaBO();
        private CitaAtencionBO boCita = new CitaAtencionBO();
        private RecetaMedicaBO boReceta = new RecetaMedicaBO();
        private VeterinarioBO boVet = new VeterinarioBO();

        public class HistorialViewModel
        {
            public int CitaId { get; set; }
            public string Fecha { get; set; }
            public string Hora { get; set; }
            public string Estado { get; set; }
            public string ClaseEstado { get; set; }
            public string NombreVeterinario { get; set; }
            public string Observacion { get; set; }
            public double Peso { get; set; }
            public double Monto { get; set; }
        }

        public class RecetaViewModel
        {
            public int RecetaId { get; set; }
            public string Fecha { get; set; }
            public string Diagnostico { get; set; }
            public string Indicaciones { get; set; }
            public string NombreVeterinario { get; set; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (Request.QueryString["id"] == null) { Response.Redirect("Secretaria_Expedientes.aspx"); return; }

                int mascotaID = Convert.ToInt32(Request.QueryString["id"]);
                hdMascotaID.Value = mascotaID.ToString();

                CargarDatosMascota(mascotaID);
                LimpiarGrillas();
            }
        }

        private void LimpiarGrillas()
        {
            txtFiltroFechaCita.Text = "";
            txtFiltroFechaReceta.Text = "";

            // Importante: null aquí para que no salga ni el footer, solo el panel de inicio
            rptHistorialMedico.DataSource = null; rptHistorialMedico.DataBind();
            rptRecetasLista.DataSource = null; rptRecetasLista.DataBind();

            pnlInicioCitas.Visible = true;
            pnlInicioRecetas.Visible = true;
        }

        // --- CARGA DE DATOS (Solo al buscar) ---
        private void CargarCitas()
        {
            if (string.IsNullOrEmpty(txtFiltroFechaCita.Text)) return;

            int idMascota = int.Parse(hdMascotaID.Value);
            DateTime fecha = DateTime.Parse(txtFiltroFechaCita.Text);

            List<citaAtencionDto> lista = boCita.ListarCitasPorMascotaYFecha(idMascota, fecha.ToString("yyyy-MM-dd"));

            pnlInicioCitas.Visible = false;

            // FIX: Si es nula o vacía, asignamos lista vacía para que el FooterTemplate se muestre
            if (lista == null || lista.Count == 0)
            {
                rptHistorialMedico.DataSource = new List<HistorialViewModel>();
                rptHistorialMedico.DataBind();
                return;
            }

            var listaVets = boVet.ListarTodos();
            var vetsDict = listaVets.ToDictionary(v => v.veterinarioId, v => v.persona != null ? v.persona.nombre : "Dr.");

            var viewList = lista.OrderByDescending(c => c.fechaHoraInicioStr).Select(c => {
                DateTime dt = ParseFechaSafe(c.fechaHoraInicioStr);
                string nomVet = (c.veterinario != null && vetsDict.ContainsKey(c.veterinario.veterinarioId)) ? vetsDict[c.veterinario.veterinarioId] : "Dr.";
                string st = c.estado.ToString();
                string css = "badge bg-primary";
                if (st == "ATENDIDA") css = "badge bg-success";
                if (st == "CANCELADA") css = "badge bg-danger";

                return new HistorialViewModel
                {
                    CitaId = c.citaId,
                    Fecha = dt.ToString("dd MMMM yyyy", new CultureInfo("es-ES")),
                    Hora = dt.ToString("hh:mm tt"),
                    Estado = st,
                    ClaseEstado = css,
                    NombreVeterinario = nomVet,
                    Observacion = c.observacion,
                    Peso = c.pesoMascota,
                    Monto = c.monto
                };
            }).ToList();

            rptHistorialMedico.DataSource = viewList; rptHistorialMedico.DataBind();
        }

        private void CargarRecetas()
        {
            if (string.IsNullOrEmpty(txtFiltroFechaReceta.Text)) return;

            int idMascota = int.Parse(hdMascotaID.Value);
            DateTime fecha = DateTime.Parse(txtFiltroFechaReceta.Text);

            List<recetaMedicaDto> lista = boReceta.ListarPorMascotaYFecha(idMascota, fecha);

            pnlInicioRecetas.Visible = false;

            // FIX: Si es nula o vacía, asignamos lista vacía para que el FooterTemplate se muestre
            if (lista == null || lista.Count == 0)
            {
                rptRecetasLista.DataSource = new List<RecetaViewModel>();
                rptRecetasLista.DataBind();
                return;
            }

            var listaVets = boVet.ListarTodos();
            var vetsDict = listaVets.ToDictionary(v => v.veterinarioId, v => v.persona != null ? v.persona.nombre : "Dr.");

            var viewRecetas = lista.OrderByDescending(r => r.fechaEmisionstr).Select(r => {
                string nomVet = "Dr.";
                if (r.cita != null && r.cita.veterinario != null && vetsDict.ContainsKey(r.cita.veterinario.veterinarioId))
                    nomVet = vetsDict[r.cita.veterinario.veterinarioId];

                return new RecetaViewModel
                {
                    RecetaId = r.recetaMedicaId,
                    Fecha = ParseFechaSafe(r.fechaEmisionstr).ToString("dd/MM/yyyy"),
                    Diagnostico = r.diagnostico,
                    Indicaciones = r.observaciones,
                    NombreVeterinario = nomVet
                };
            }).ToList();

            rptRecetasLista.DataSource = viewRecetas; rptRecetasLista.DataBind();
        }

        // --- Eventos ---
        protected void btnBuscarCitaFecha_Click(object sender, EventArgs e) { CargarCitas(); }
        protected void btnLimpiarCita_Click(object sender, EventArgs e) { LimpiarGrillas(); }

        protected void btnBuscarRecetaFecha_Click(object sender, EventArgs e) { CargarRecetas(); }
        protected void btnLimpiarReceta_Click(object sender, EventArgs e) { LimpiarGrillas(); }

        protected void btnNuevaConsulta_Click(object sender, EventArgs e)
        {
            Response.Redirect("Secretaria_AgendaCitas.aspx");
        }

        // --- Helpers ---
        private void CargarDatosMascota(int mascotaID)
        {
            mascotaDto mascota = boMascota.ObtenerPorId(mascotaID);
            if (mascota != null && mascota.mascotaId != 0)
            {
                lblMascotaNombre.Text = mascota.nombre; lblMascotaRaza.Text = $"{mascota.especie} - {mascota.raza}";
                string esp = (mascota.especie ?? "").ToLower();
                if (esp.Contains("perro")) litIconoEspecie.Text = "<i class='fas fa-dog'></i>"; else if (esp.Contains("gato")) litIconoEspecie.Text = "<i class='fas fa-cat'></i>"; else litIconoEspecie.Text = "<i class='fas fa-paw'></i>";
                lblCodigo.Text = $"M-{mascota.mascotaId:D6}"; lblSexo.Text = mascota.sexo == "M" ? "Macho" : "Hembra"; lblColor.Text = mascota.color;
                lblEstado.Text = mascota.activo ? "ACTIVO" : "INACTIVO"; lblEstado.CssClass = mascota.activo ? "badge bg-success" : "badge bg-secondary";
                if (mascota.persona != null)
                {
                    var duenio = boPersona.ObtenerPorId(mascota.persona.personaId);
                    if (duenio != null) { lblPropietarioNombre.Text = duenio.nombre; lblPropietarioDNI.Text = duenio.tipoDocumento + ": " + duenio.nroDocumento; lblPropietarioTelefono.Text = duenio.telefono; }
                }
            }
        }
        private DateTime ParseFechaSafe(string s) { if (DateTime.TryParse(s, out DateTime dt)) return dt; return DateTime.Now; }
        protected void btnTabConsultas_Click(object s, EventArgs e) { btnTabConsultas.CssClass = "nav-link active text-dark fw-bold"; btnTabRecetas.CssClass = "nav-link text-muted"; mvHistorial.ActiveViewIndex = 0; btnNuevaConsulta.Visible = true; }
        protected void btnTabRecetas_Click(object s, EventArgs e) { btnTabConsultas.CssClass = "nav-link text-muted"; btnTabRecetas.CssClass = "nav-link active text-dark fw-bold"; mvHistorial.ActiveViewIndex = 1; btnNuevaConsulta.Visible = false; }
    }
}