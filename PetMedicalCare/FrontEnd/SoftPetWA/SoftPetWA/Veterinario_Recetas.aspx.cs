using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.RecetaMedicaClient;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.VeterinarioClient;
using SoftPetBussiness.DetalleRecetaClient;

namespace SoftPetWA
{
    public partial class Veterinario_Recetas : System.Web.UI.Page
    {
        private RecetaMedicaBO boReceta;
        private CitaAtencionBO boCita;
        private VeterinarioBO boVet;
        private DetalleRecetaBO boDetalle;

        private const int PageSize = 5;

        public int CurrentPage
        {
            get { return (int)(ViewState["CurrentPage"] ?? 1); }
            set { ViewState["CurrentPage"] = value; }
        }

        public int MiVeterinarioID
        {
            get { return (int)(Session["MiVeterinarioID"] ?? 0); }
            set { Session["MiVeterinarioID"] = value; }
        }

        private class RecetaViewModel
        {
            public int RecetaID { get; set; }
            public int CitaID { get; set; }
            public string FechaEmision { get; set; }
            public string VigenciaHasta { get; set; }
            public string FechaEmisionISO { get; set; }
            public string VigenciaISO { get; set; }
            public string MascotaNombre { get; set; }
            public string PropietarioNombre { get; set; }
            public string DiagnosticoResumen { get; set; }
            public string DiagnosticoCompleto { get; set; }
            public string Observaciones { get; set; }
            public bool ActivoBool { get; set; }
            public string EstadoTexto { get; set; }
            public string EstadoClase { get; set; }
        }

        protected void Page_Init(object sender, EventArgs e)
        {
            boReceta = new RecetaMedicaBO();
            boCita = new CitaAtencionBO();
            boVet = new VeterinarioBO();
            boDetalle = new DetalleRecetaBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                if (!IdentificarVeterinario())
                {
                    pnlSinResultados.Visible = true;
                    lblMensajeVacio.Text = "Error: No se pudo identificar al veterinario.";
                    btnNuevaReceta.Visible = false;
                    return;
                }

                CurrentPage = 1;
                // ESTADO INICIAL: VACÍO
                divListaRecetas.Visible = false;
                pnlSinResultados.Visible = true;
                lblMensajeVacio.Text = "Utilice los filtros para buscar recetas.";
                litPaginacion.Text = "0-0 de 0";
            }
        }

        private bool IdentificarVeterinario()
        {
            if (MiVeterinarioID != 0) return true;
            int usuarioId = Convert.ToInt32(Session["UsuarioId"] ?? 0);
            if (usuarioId == 0) return false;

            var listaVets = boVet.ListarBusquedaAvanzada("", "", "", 1);
            if (listaVets != null && listaVets.Count > 0)
            {
                var miVet = listaVets.FirstOrDefault(v => v.persona != null && v.persona.usuario != null && v.persona.usuario.usuarioId == usuarioId);
                if (miVet != null) { MiVeterinarioID = miVet.veterinarioId; return true; }
            }
            return false;
        }

        // --- COMBOS ---
        protected void txtModalFiltroFechaCita_TextChanged(object sender, EventArgs e) { CargarCitasEnCombo(txtModalFiltroFechaCita.Text); }

        private void CargarCitasEnCombo(string fecha)
        {
            ddlModalCita.Items.Clear();
            if (MiVeterinarioID == 0) { ddlModalCita.Items.Add(new ListItem("-", "0")); return; }
            string fechaBusqueda = string.IsNullOrEmpty(fecha) ? DateTime.Today.ToString("yyyy-MM-dd") : fecha;
            var citas = boCita.ListarBusquedaAvanzada2(fechaBusqueda, MiVeterinarioID);

            if (citas != null && citas.Count > 0)
            {
                ddlModalCita.Items.Add(new ListItem("-- Seleccione Cita --", "0"));
                foreach (var c in citas)
                {
                    string hora = (c.fechaHoraInicioStr != null && c.fechaHoraInicioStr.Length > 11) ? c.fechaHoraInicioStr.Substring(11, 5) : "00:00";
                    string nombreMascota = (c.mascota != null) ? c.mascota.nombre : "??";
                    ddlModalCita.Items.Add(new ListItem($"{hora} - {nombreMascota} ({c.estado})", c.citaId.ToString()));
                }
            }
            else ddlModalCita.Items.Add(new ListItem("-- Sin citas --", "0"));
        }

        // --- BÚSQUEDA ---
        private void CargarRecetas()
        {
            if (MiVeterinarioID == 0) return;

            string mascota = txtFiltroMascota.Text.Trim();
            string duenio = txtFiltroDuenio.Text.Trim();
            string fecha = txtFiltroFecha.Text;
            string activo = ddlFiltroEstado.SelectedValue == "-1" ? null : ddlFiltroEstado.SelectedValue;

            var listaRaw = boReceta.ListarBusquedaAvanzada(mascota, duenio, fecha, activo);

            if (listaRaw == null || listaRaw.Count == 0)
            {
                divListaRecetas.Visible = false;
                pnlSinResultados.Visible = true;
                lblMensajeVacio.Text = "No se encontraron recetas con los filtros aplicados.";
                litPaginacion.Text = "0-0 de 0";
                return;
            }

            pnlSinResultados.Visible = false;
            divListaRecetas.Visible = true;

            var viewList = listaRaw.Select(r => new RecetaViewModel
            {
                RecetaID = r.recetaMedicaId,
                CitaID = r.cita != null ? r.cita.citaId : 0,
                FechaEmision = r.fechaEmision.ToString("dd/MM/yyyy"),
                VigenciaHasta = r.vigenciaHasta != null ? r.vigenciaHasta.ToString("dd/MM/yyyy") : "-",
                FechaEmisionISO = r.fechaEmision.ToString("yyyy-MM-dd"),
                VigenciaISO = r.vigenciaHasta != null ? r.vigenciaHasta.ToString("yyyy-MM-dd") : "",
                MascotaNombre = (r.cita?.mascota?.nombre) ?? "Desconocido",
                PropietarioNombre = (r.cita?.mascota?.persona?.nombre) ?? "Desconocido",
                DiagnosticoCompleto = r.diagnostico,
                DiagnosticoResumen = (r.diagnostico != null && r.diagnostico.Length > 50) ? r.diagnostico.Substring(0, 47) + "..." : r.diagnostico,
                Observaciones = r.observaciones,
                ActivoBool = r.activo,
                EstadoTexto = r.activo ? "Vigente" : "Anulada",
                EstadoClase = r.activo ? "bg-success-soft text-success" : "bg-danger-soft text-danger"
            }).ToList();

            int totalRegistros = viewList.Count;
            int totalPages = (int)Math.Ceiling((double)totalRegistros / PageSize);
            if (CurrentPage > totalPages && totalPages > 0) CurrentPage = totalPages;
            if (CurrentPage < 1) CurrentPage = 1;

            int skip = (CurrentPage - 1) * PageSize;
            rptRecetas.DataSource = viewList.Skip(skip).Take(PageSize).ToList();
            rptRecetas.DataBind();

            int mostrarInicio = skip + 1;
            int mostrarFin = Math.Min(skip + PageSize, totalRegistros);
            litPaginacion.Text = $"{mostrarInicio}-{mostrarFin} de {totalRegistros}";
            GenerarPaginacion(totalPages);
        }

        private void GenerarPaginacion(int totalPages)
        {
            lnkAnterior.Enabled = CurrentPage > 1;
            lnkSiguiente.Enabled = CurrentPage < totalPages;
            var paginas = new List<object>();
            for (int i = 1; i <= totalPages; i++) paginas.Add(new { Pagina = i, EsPaginaActual = (i == CurrentPage) });
            rptPaginador.DataSource = paginas; rptPaginador.DataBind();
        }

        // --- EVENTOS BOTONES ---
        protected void btnBuscar_Click(object sender, EventArgs e) { CurrentPage = 1; CargarRecetas(); }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtFiltroMascota.Text = ""; txtFiltroDuenio.Text = ""; txtFiltroFecha.Text = ""; ddlFiltroEstado.SelectedValue = "-1";
            CurrentPage = 1;
            // Resetear a estado vacío
            divListaRecetas.Visible = false; pnlSinResultados.Visible = true; lblMensajeVacio.Text = "Utilice los filtros para buscar.";
        }

        protected void lnkPaginado_Click(object sender, EventArgs e) { LinkButton btn = (LinkButton)sender; if (btn.CommandName == "Anterior") CurrentPage--; else CurrentPage++; CargarRecetas(); }
        protected void rptPaginador_ItemCommand(object s, RepeaterCommandEventArgs e) { CurrentPage = Convert.ToInt32(e.CommandArgument); CargarRecetas(); }

        // --- CRUD RECETA ---
        protected void btnNuevaReceta_Click(object sender, EventArgs e)
        {
            hdRecetaID.Value = "0";
            string hoy = DateTime.Today.ToString("yyyy-MM-dd");
            txtModalFechaEmision.Text = hoy; txtModalVigencia.Text = DateTime.Today.AddDays(15).ToString("yyyy-MM-dd");
            txtModalFiltroFechaCita.Text = hoy; CargarCitasEnCombo(hoy);
            txtModalDiagnostico.Text = ""; txtModalObservaciones.Text = ""; ddlModalEstado.SelectedValue = "true";

            SetModalReadOnly(false);
            pnlBtnDetalles.Attributes["style"] = "display:none;"; // Oculto hasta guardar

            ScriptManager.RegisterStartupScript(this, GetType(), "NewModal", "$('#modalRecetaLabel').text('Nueva Receta'); new bootstrap.Modal(document.getElementById('modalReceta')).show();", true);
        }

        protected void btnGuardarReceta_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid) return;
            try
            {
                int id = Convert.ToInt32(hdRecetaID.Value);
                int citaId = Convert.ToInt32(ddlModalCita.SelectedValue);
                if (citaId == 0) { litModalError.Text = "<div class='alert alert-danger py-1'>Seleccione una cita.</div>"; return; }
                string diag = txtModalDiagnostico.Text; string obs = txtModalObservaciones.Text; string fEmi = txtModalFechaEmision.Text; string fVig = txtModalVigencia.Text;
                bool activo = ddlModalEstado.SelectedValue == "true";

                int result = (id == 0)
                    ? boReceta.Insertar(citaId, fEmi, fVig, diag, obs, activo)
                    : boReceta.Modificar(id, citaId, fEmi, fVig, diag, obs, activo);

                if (result > 0)
                {
                    int idFinal = (id == 0) ? result : id;
                    hdRecetaID.Value = idFinal.ToString();

                    // Habilitar botón de detalles (Server Side)
                    pnlBtnDetalles.Attributes["style"] = "display:block;";

                    // NO recargamos la lista aún. Mandamos script para el SweetAlert con opciones.
                    ScriptManager.RegisterStartupScript(this, GetType(), "ExitoOpciones",
                        $"mostrarExitoConOpciones('La receta se guardó correctamente.', {idFinal});", true);
                }
                else litModalError.Text = "<div class='alert alert-danger'>Error en BD.</div>";
            }
            catch (Exception ex) { litModalError.Text = $"<div class='alert alert-danger'>Error: {ex.Message}</div>"; }
        }

        protected void btnConfirmarEliminar_Click(object sender, EventArgs e)
        {
            try
            {
                boReceta.Eliminar(int.Parse(hdRecetaIDEliminar.Value));
                // Para eliminar, sí queremos recargar todo.
                ScriptManager.RegisterStartupScript(this, GetType(), "Del", "mostrarExitoRedirect('Receta eliminada correctamente.');", true);
            }
            catch { }
        }

        private void SetModalReadOnly(bool readOnly)
        {
            bool esNuevo = hdRecetaID.Value == "0";
            txtModalFiltroFechaCita.Enabled = !readOnly && esNuevo; ddlModalCita.Enabled = !readOnly && esNuevo;
            txtModalFechaEmision.ReadOnly = readOnly; txtModalVigencia.ReadOnly = readOnly;
            txtModalDiagnostico.ReadOnly = readOnly; txtModalObservaciones.ReadOnly = readOnly;
            ddlModalEstado.Enabled = !readOnly; btnGuardarReceta.Visible = !readOnly;
        }

        protected void rptRecetas_ItemCommand(object source, RepeaterCommandEventArgs e) { }

        // =========================================================================
        // DETALLES / MEDICAMENTOS
        // =========================================================================

        protected void btnGestionarDetalles_Click(object sender, EventArgs e)
        {
            int idReceta = Convert.ToInt32(hdRecetaID.Value);
            CargarGrillaDetalles(idReceta);

            bool esEdicion = btnGuardarReceta.Visible;
            divAgregarMedicamento.Visible = esEdicion;
            if (gvDetalles.Columns.Count > 0) gvDetalles.Columns[gvDetalles.Columns.Count - 1].Visible = esEdicion;

            txtDetMedicina.Text = ""; txtDetCantidad.Text = ""; txtDetIndicacion.Text = ""; txtDetDosis.Text = "";

            updModalDetalles.Update();
            ScriptManager.RegisterStartupScript(this, GetType(), "OpenDet", "abrirModalDetalles();", true);
        }

        private void CargarGrillaDetalles(int idReceta)
        {
            var lista = boDetalle.ListarDetallesPorIdDeReceta(idReceta);
            gvDetalles.DataSource = lista;
            gvDetalles.DataBind();
        }

        protected void btnAgregarDetalle_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid) return;
            try
            {
                int idReceta = Convert.ToInt32(hdRecetaID.Value);
                string med = txtDetMedicina.Text;
                string cant = txtDetCantidad.Text;
                string ind = txtDetIndicacion.Text;
                string dosis = txtDetDosis.Text;

                int res = boDetalle.Insertar(idReceta, med, "", "", dosis, "", "", ind, cant, true);

                if (res > 0)
                {
                    txtDetMedicina.Text = ""; txtDetCantidad.Text = ""; txtDetIndicacion.Text = ""; txtDetDosis.Text = "";
                    CargarGrillaDetalles(idReceta);
                }
            }
            catch (Exception ex)
            {
                ScriptManager.RegisterStartupScript(this, GetType(), "ErrDet", $"alert('Error al agregar: {ex.Message}');", true);
            }
        }

        protected void gvDetalles_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "EliminarDetalle")
            {
                int idDetalle = Convert.ToInt32(e.CommandArgument);
                boDetalle.Eliminar(idDetalle);
                CargarGrillaDetalles(Convert.ToInt32(hdRecetaID.Value));
            }
        }
    }
}