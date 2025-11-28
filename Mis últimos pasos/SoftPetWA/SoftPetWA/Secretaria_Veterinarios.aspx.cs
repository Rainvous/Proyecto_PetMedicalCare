using SoftPetBusiness;
using SoftPetBussiness.VeterinarioClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.UsuarioClient;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using usuarioDto = SoftPetBussiness.UsuarioClient.usuarioDto;
using personaDto = SoftPetBussiness.PersonaClient.personaDto;

namespace SoftPetWA
{
    public partial class Secretaria_Veterinarios : System.Web.UI.Page
    {
        private VeterinarioBO veterinarioBo;
        private PersonaBO personaBo;
        private UsuarioBO usuarioBo;
        private HorarioLaboralBO horarioBo;

        private const int PageSize = 7;

        public int CurrentPage
        {
            get { return (int)(ViewState["CurrentPage"] ?? 1); }
            set { ViewState["CurrentPage"] = value; }
        }

        private class VeterinarioViewModel
        {
            public int VeterinarioID { get; set; }
            public int PersonaID { get; set; }
            public int UsuarioID { get; set; }
            public string Nombre { get; set; }
            public string Email { get; set; }
            public string Username { get; set; }
            public string Iniciales { get; set; }
            public string AvatarColor { get; set; }
            public string TipoDocumento { get; set; }
            public string NumDocumento { get; set; }
            public DateTime FechaContratacion { get; set; }
            public string Especialidad { get; set; }
            public string Telefono { get; set; }
            public string Estado { get; set; }
            public string RUC { get; set; }
            public string Direccion { get; set; }
            public string Sexo { get; set; }
            public string EstadoLaboral { get; set; }
        }

        protected void Page_Init(object sender, EventArgs e)
        {
            this.veterinarioBo = new VeterinarioBO();
            this.personaBo = new PersonaBO();
            this.usuarioBo = new UsuarioBO();
            this.horarioBo = new HorarioLaboralBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CurrentPage = 1;
                CargarFiltros();
                CargarHoras();
                LimpiarGrilla();
            }
        }

        private void CargarHoras()
        {
            ddlHoraInicio.Items.Clear();
            ddlHoraFin.Items.Clear();

            for (int h = 8; h <= 16; h++)
            {
                string valor = h.ToString("00") + ":00";
                string texto = (h < 12) ? h + ":00 AM" : (h == 12 ? "12:00 PM" : (h - 12) + ":00 PM");
                ddlHoraInicio.Items.Add(new ListItem(texto, valor));
                ddlHoraFin.Items.Add(new ListItem(texto, valor));
            }

            if (ddlHoraInicio.Items.FindByValue("08:00") != null) ddlHoraInicio.SelectedValue = "08:00";
            if (ddlHoraFin.Items.FindByValue("16:00") != null) ddlHoraFin.SelectedValue = "16:00";
        }

        private void CargarFiltros()
        {
            DataTable dtDoc = new DataTable();
            dtDoc.Columns.Add("Tipo", typeof(string));
            dtDoc.Rows.Add("DNI"); dtDoc.Rows.Add("CE");
            ddlDocumento.DataSource = dtDoc; ddlDocumento.DataTextField = "Tipo"; ddlDocumento.DataValueField = "Tipo"; ddlDocumento.DataBind();
            ddlModalTipoDoc.DataSource = dtDoc; ddlModalTipoDoc.DataTextField = "Tipo"; ddlModalTipoDoc.DataValueField = "Tipo"; ddlModalTipoDoc.DataBind();

            DataTable dtEsp = new DataTable();
            dtEsp.Columns.Add("Nombre", typeof(string));
            dtEsp.Rows.Add("Todos"); dtEsp.Rows.Add("Medicina General"); dtEsp.Rows.Add("Cirugía"); dtEsp.Rows.Add("Dermatología"); dtEsp.Rows.Add("Animales Exóticos");
            ddlEspecialidad.DataSource = dtEsp; ddlEspecialidad.DataTextField = "Nombre"; ddlEspecialidad.DataValueField = "Nombre"; ddlEspecialidad.DataBind();

            var dtModalEsp = dtEsp.AsEnumerable().Where(r => r.Field<string>("Nombre") != "Todos").CopyToDataTable();
            ddlModalEspecialidad.DataSource = dtModalEsp; ddlModalEspecialidad.DataTextField = "Nombre"; ddlModalEspecialidad.DataValueField = "Nombre"; ddlModalEspecialidad.DataBind();
        }

        private void LimpiarGrilla()
        {
            rptVeterinarios.DataSource = null;
            rptVeterinarios.DataBind();
            litRegistrosTotales.Text = "0";
            litRegistrosActuales.Text = "0-0";
            divListaVeterinarios.Visible = false;
            pnlSinResultados.Visible = true;
        }

        private void CargarVeterinarios(string nombre, string numDoc, string especialidad, int estadoActivo)
        {
            IList<veterinarioDto> listaVets = veterinarioBo.ListarBusquedaAvanzada(nombre, numDoc, especialidad, estadoActivo);

            if (listaVets == null || listaVets.Count == 0) { LimpiarGrilla(); return; }

            pnlSinResultados.Visible = false;
            divListaVeterinarios.Visible = true;

            var bindingList = listaVets.Select(v =>
            {
                string nombreShow = (v.persona != null) ? v.persona.nombre : "Sin Nombre";
                return new VeterinarioViewModel
                {
                    VeterinarioID = v.veterinarioId,
                    PersonaID = (v.persona != null) ? v.persona.personaId : 0,
                    UsuarioID = (v.persona != null && v.persona.usuario != null) ? v.persona.usuario.usuarioId : 0,
                    Nombre = nombreShow,
                    Email = (v.persona != null && v.persona.usuario != null) ? v.persona.usuario.correo : "",
                    Username = (v.persona != null && v.persona.usuario != null) ? v.persona.usuario.username : "",
                    Iniciales = ObternerIniciales(nombreShow),
                    AvatarColor = GetAvatarColor(v.veterinarioId),
                    TipoDocumento = (v.persona != null) ? v.persona.tipoDocumento : "DNI",
                    NumDocumento = (v.persona != null) ? v.persona.nroDocumento.ToString() : "",
                    FechaContratacion = v.fechaContratacion,
                    Especialidad = v.especialidad,
                    Telefono = (v.persona != null) ? v.persona.telefono : "",
                    Estado = v.activo ? "Activo" : "Inactivo",
                    RUC = (v.persona != null && v.persona.ruc != 0) ? v.persona.ruc.ToString() : "",
                    Direccion = (v.persona != null) ? v.persona.direccion : "",
                    Sexo = (v.persona != null) ? v.persona.sexo.ToString() : "O",
                    EstadoLaboral = (v.estado != null) ? v.estado.ToString() : "ACTIVO"
                };
            }).ToList();

            int totalRegistros = bindingList.Count;
            int inicio = (CurrentPage - 1) * PageSize + 1;
            int fin = Math.Min(CurrentPage * PageSize, totalRegistros);
            litRegistrosActuales.Text = $"{inicio}-{fin}";
            litRegistrosTotales.Text = totalRegistros.ToString();

            rptVeterinarios.DataSource = bindingList.Skip((CurrentPage - 1) * PageSize).Take(PageSize).ToList();
            rptVeterinarios.DataBind();
            GenerarPaginado(totalRegistros);
        }

        protected void btnBuscar_Click(object sender, EventArgs e) { CurrentPage = 1; RecargarGrilla(); }
        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtNombre.Text = ""; txtDocumento.Text = ""; ddlEspecialidad.SelectedIndex = 0; ddlFiltroEstado.SelectedValue = "-1";
            CurrentPage = 1; LimpiarGrilla();
        }
        protected void lnkPaginado_Click(object sender, EventArgs e)
        {
            if (((LinkButton)sender).CommandName == "Anterior") CurrentPage--; else CurrentPage++; RecargarGrilla();
        }
        protected void rptPaginador_ItemCommand(object s, RepeaterCommandEventArgs e)
        {
            CurrentPage = Convert.ToInt32(e.CommandArgument); RecargarGrilla();
        }
        private void RecargarGrilla()
        {
            CargarVeterinarios(txtNombre.Text.Trim(), txtDocumento.Text.Trim(), ddlEspecialidad.SelectedValue, int.Parse(ddlFiltroEstado.SelectedValue));
        }
        private void GenerarPaginado(int totalRegistros)
        {
            int totalPages = (int)Math.Ceiling((double)totalRegistros / PageSize);
            lnkAnterior.Enabled = (CurrentPage > 1);
            lnkSiguiente.Enabled = (CurrentPage < totalPages);
            var paginas = new List<object>();
            for (int i = 1; i <= totalPages; i++) paginas.Add(new { Pagina = i, EsPaginaActual = (i == CurrentPage) });
            rptPaginador.DataSource = paginas; rptPaginador.DataBind();
        }

        // --- CRUD ---
        protected void btnNuevoVeterinario_Click(object sender, EventArgs e)
        {
            hdVeterinarioID.Value = "0"; hdPersonaID.Value = "0"; hdTempUsuarioID.Value = "0";
            txtModalNombre.Text = ""; txtModalEmail.Text = ""; txtModalUsuario.Text = ""; txtModalTelefono.Text = "";
            ddlModalTipoDoc.SelectedValue = "DNI"; txtModalNumDoc.Text = ""; txtModalRUC.Text = ""; txtModalDireccion.Text = "";
            ddlModalSexo.SelectedValue = "O"; txtModalRol.Text = "Veterinario";
            txtModalFechaContratacion.Text = DateTime.Today.ToString("yyyy-MM-dd");
            ddlModalEspecialidad.SelectedIndex = 0; ddlModalEstado.SelectedValue = "Activo"; ddlModalEstadoLaboral.SelectedValue = "ACTIVO";
            SetModalReadOnly(false);
            updModalVeterinario.Update();
            ScriptManager.RegisterStartupScript(this, GetType(), "ShowModal", "$('#modalVeterinarioLabel').text('Registrar Nuevo Veterinario'); $('#modalVeterinario').modal('show');", true);
        }

        protected void btnGuardarVeterinario_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid) return;
            try
            {
                int vetID = Convert.ToInt32(hdVeterinarioID.Value);
                int perID = Convert.ToInt32(hdPersonaID.Value);
                string nombre = txtModalNombre.Text; string email = txtModalEmail.Text; string usuario = txtModalUsuario.Text;
                string tel = txtModalTelefono.Text; string doc = ddlModalTipoDoc.SelectedValue; string dir = txtModalDireccion.Text;
                string sexo = ddlModalSexo.SelectedValue;
                bool activo = ddlModalEstado.SelectedValue == "Activo";
                string fecha = Convert.ToDateTime(txtModalFechaContratacion.Text).ToString("yyyy-MM-dd");
                string esp = ddlModalEspecialidad.SelectedValue; string estadoLab = ddlModalEstadoLaboral.SelectedValue;
                int numDoc = int.Parse(txtModalNumDoc.Text); int ruc = 0; int.TryParse(txtModalRUC.Text, out ruc);

                string pass = "";
                int res = 0;
                if (vetID == 0) res = veterinarioBo.InsertarCompleto(usuario, pass, email, nombre, dir, tel, sexo, numDoc, ruc, doc, fecha, estadoLab, esp);
                else
                {
                    int usuID = Convert.ToInt32(hdTempUsuarioID.Value);
                    string passFinal = (pass != "**********") ? pass : null;
                    res = veterinarioBo.ModificarCompleto(vetID, perID, usuID, usuario, passFinal, email, activo, nombre, dir, tel, sexo, numDoc, ruc, doc, fecha, estadoLab, esp);
                }

                if (res > 0) ScriptManager.RegisterStartupScript(this, GetType(), "ExitoScript", "mostrarExitoLocal('Operación realizada correctamente.');", true);
                else { litModalError.Text = "<div class='alert alert-danger'>Error al guardar en BD.</div>"; updModalVeterinario.Update(); }
            }
            catch (Exception ex) { litModalError.Text = $"<div class='alert alert-danger'>Error: {ex.Message}</div>"; updModalVeterinario.Update(); }
        }

        protected void btnConfirmarEliminar_Click(object sender, EventArgs e)
        {
            try
            {
                int id = Convert.ToInt32(hdVeterinarioIDEliminar.Value);
                int res = veterinarioBo.EliminarCompleto(id);
                if (res > 0) ScriptManager.RegisterStartupScript(this, GetType(), "DelScript", "mostrarExitoLocal('Veterinario eliminado correctamente.');", true);
                else ScriptManager.RegisterStartupScript(this, GetType(), "ErrDel", "alert('No se pudo eliminar.');", true);
            }
            catch (Exception ex) { ScriptManager.RegisterStartupScript(this, GetType(), "Error", $"alert('{ex.Message}');", true); }
        }

        // --- HORARIOS ---
        protected void btnGuardarHorario_Click(object sender, EventArgs e)
        {
            try
            {
                int vetId = int.Parse(hdHorarioVetID.Value);
                string modo = hdModoHorario.Value;
                string horaIni = ddlHoraInicio.SelectedValue;
                string horaFin = ddlHoraFin.SelectedValue;

                TimeSpan tIni = TimeSpan.Parse(horaIni);
                TimeSpan tFin = TimeSpan.Parse(horaFin);
                if (tFin <= tIni) { litErrorHorario.Text = "<div class='alert alert-danger mt-2 py-1 small'>La hora de fin debe ser posterior a la de inicio.</div>"; return; }

                int res = 0;
                if (modo == "DIA")
                {
                    if (string.IsNullOrEmpty(txtFechaUnica.Text)) { litErrorHorario.Text = "<div class='alert alert-danger mt-2 py-1 small'>Seleccione una fecha.</div>"; return; }
                    DateTime fecha = DateTime.Parse(txtFechaUnica.Text);
                    res = horarioBo.RegistrarHorarioRango(vetId, fecha, fecha, horaIni, horaFin, true);
                }
                else
                {
                    if (string.IsNullOrEmpty(txtFechaDesde.Text) || string.IsNullOrEmpty(txtFechaHasta.Text)) { litErrorHorario.Text = "<div class='alert alert-danger mt-2 py-1 small'>Seleccione el rango.</div>"; return; }
                    DateTime desde = DateTime.Parse(txtFechaDesde.Text);
                    DateTime hasta = DateTime.Parse(txtFechaHasta.Text);
                    if (hasta < desde) { litErrorHorario.Text = "<div class='alert alert-danger mt-2 py-1 small'>La fecha 'Hasta' debe ser posterior a 'Desde'.</div>"; return; }
                    res = horarioBo.RegistrarHorarioRango(vetId, desde, hasta, horaIni, horaFin, true);
                }

                if (res > 0) ScriptManager.RegisterStartupScript(this, GetType(), "HideHorario",
                    "$('#modalHorario').modal('hide'); $('.modal-backdrop').remove(); mostrarExitoLocal('Horario asignado correctamente (' + " + res + " + ' días).');", true);
                else litErrorHorario.Text = "<div class='alert alert-danger mt-2 py-1 small'>Error al guardar.</div>";
            }
            catch (Exception ex) { litErrorHorario.Text = $"<div class='alert alert-danger mt-2 py-1 small'>Error: {ex.Message}</div>"; }
        }

        protected void rptVeterinarios_ItemCommand(object source, RepeaterCommandEventArgs e) { /* Vacío */ }
        private string ObternerIniciales(string n) { if (string.IsNullOrEmpty(n)) return "??"; var p = n.Trim().Split(' '); return p.Length > 1 ? (p[0][0] + "" + p[1][0]).ToUpper() : p[0].Substring(0, Math.Min(2, p[0].Length)).ToUpper(); }
        private string GetAvatarColor(int id) { string[] c = { "#007bff", "#28a745", "#ffc107", "#dc3545", "#17a2b8", "#6c757d" }; return c[Math.Abs(id) % c.Length]; }
        private void SetModalReadOnly(bool r)
        {
            txtModalNombre.ReadOnly = r; txtModalEmail.ReadOnly = r; txtModalUsuario.ReadOnly = r; txtModalTelefono.ReadOnly = r; ddlModalTipoDoc.Enabled = !r; txtModalNumDoc.ReadOnly = r; txtModalRUC.ReadOnly = r; txtModalDireccion.ReadOnly = r; ddlModalSexo.Enabled = !r; txtModalRol.ReadOnly = true; txtModalFechaContratacion.ReadOnly = r; ddlModalEspecialidad.Enabled = !r; ddlModalEstado.Enabled = !r; ddlModalEstadoLaboral.Enabled = !r; btnGuardarVeterinario.Visible = !r;
        }
    }
}