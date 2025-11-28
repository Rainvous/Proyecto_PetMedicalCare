using SoftPetBusiness;
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.UsuarioClient;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using usuarioDto = SoftPetBussiness.UsuarioClient.usuarioDto;

namespace SoftPetWA
{
    public partial class Secretaria_Clientes : System.Web.UI.Page
    {
        private PersonaBO personaBo;
        private UsuarioBO usuarioBo;
        private MascotaBO mascotaBo;
        private RolUsuarioBO rolUsuarioBo;

        private const int PageSize = 7;

        public int CurrentPage
        {
            get { return (int)(ViewState["CurrentPage"] ?? 1); }
            set { ViewState["CurrentPage"] = value; }
        }

        // --- VIEWMODEL ACTUALIZADO ---
        private class ClienteViewModel
        {
            public int PersonaID { get; set; }
            public int UsuarioID { get; set; }
            public string Nombre { get; set; }
            public string Email { get; set; }
            public string Username { get; set; }
            public string Iniciales { get; set; }
            public string AvatarColor { get; set; }
            public string TipoDocumento { get; set; }
            public string NumDocumento { get; set; }

            public string RUC { get; set; }
            public string RUCRaw { get; set; }

            public string Telefono { get; set; }
            public string TextoMascotas { get; set; }
            public string Estado { get; set; }
            public string Direccion { get; set; }
            public string Sexo { get; set; }
        }

        protected void Page_Init(object sender, EventArgs e)
        {
            this.personaBo = new PersonaBO();
            this.usuarioBo = new UsuarioBO();
            this.mascotaBo = new MascotaBO();
            this.rolUsuarioBo = new RolUsuarioBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CurrentPage = 1;
                CargarFiltros();
                LimpiarGrilla();
            }
        }

        private void LimpiarGrilla()
        {
            rptClientes.DataSource = null;
            rptClientes.DataBind();
            litRegistrosTotales.Text = "0";
            litRegistrosActuales.Text = "0-0";
            divListaClientes.Visible = false;
            pnlSinResultados.Visible = true;
        }

        private void CargarFiltros()
        {
            DataTable dtDoc = new DataTable();
            dtDoc.Columns.Add("Tipo", typeof(string));
            // Solo DNI y CE
            dtDoc.Rows.Add("DNI");
            dtDoc.Rows.Add("CE");

            ddlDocumento.DataSource = dtDoc; ddlDocumento.DataTextField = "Tipo"; ddlDocumento.DataValueField = "Tipo"; ddlDocumento.DataBind();
            ddlModalTipoDoc.DataSource = dtDoc; ddlModalTipoDoc.DataTextField = "Tipo"; ddlModalTipoDoc.DataValueField = "Tipo"; ddlModalTipoDoc.DataBind();
        }

        private void CargarClientes(string nombre, string numDoc, string ruc, int activo)
        {
            IList<SoftPetBussiness.PersonaClient.personaDto> listaPersonas =
                this.personaBo.ListarBusquedaAvanzada(nombre, numDoc, ruc, activo);

            if (listaPersonas == null || listaPersonas.Count == 0)
            {
                LimpiarGrilla();
                return;
            }

            pnlSinResultados.Visible = false;
            divListaClientes.Visible = true;

            var bindingList = listaPersonas.Select(p =>
            {
                int uid = (p.usuario != null) ? p.usuario.usuarioId : 0;
                string uName = (p.usuario != null) ? p.usuario.username : "";
                string uEmail = (p.usuario != null) ? p.usuario.correo : "";

                return new ClienteViewModel
                {
                    PersonaID = p.personaId,
                    UsuarioID = uid,
                    Nombre = p.nombre,
                    Email = uEmail,
                    Username = uName,
                    Iniciales = ObternerIniciales(p.nombre),
                    AvatarColor = GetAvatarColor(p.personaId),
                    TipoDocumento = p.tipoDocumento,
                    NumDocumento = p.nroDocumento.ToString(),

                    RUC = (p.ruc == 0) ? "RUC: -" : $"RUC: {p.ruc}",
                    RUCRaw = (p.ruc == 0) ? "" : p.ruc.ToString(),

                    Telefono = p.telefono,
                    TextoMascotas = "Ver mascotas",
                    Estado = p.activo ? "Activo" : "Inactivo",
                    Direccion = p.direccion,
                    Sexo = p.sexo.ToString()
                };
            }).ToList();

            int totalRegistros = bindingList.Count;
            int inicio = (CurrentPage - 1) * PageSize + 1;
            int fin = Math.Min(CurrentPage * PageSize, totalRegistros);
            litRegistrosTotales.Text = totalRegistros.ToString();
            litRegistrosActuales.Text = $"{inicio}-{fin}";

            rptClientes.DataSource = bindingList.Skip((CurrentPage - 1) * PageSize).Take(PageSize).ToList();
            rptClientes.DataBind();
            GenerarPaginado(totalRegistros);
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

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            CurrentPage = 1;
            int estado = int.Parse(ddlFiltroEstado.SelectedValue);
            CargarClientes(txtNombre.Text.Trim(), txtDocumento.Text.Trim(), txtRUC.Text.Trim(), estado);
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtNombre.Text = ""; txtDocumento.Text = ""; txtRUC.Text = "";
            ddlDocumento.SelectedIndex = 0; ddlFiltroEstado.SelectedValue = "-1";
            CurrentPage = 1;
            LimpiarGrilla();
        }

        protected void btnNuevoCliente_Click(object sender, EventArgs e)
        {
            hdClienteID.Value = "0";
            hdTempUsuarioID.Value = "0";

            txtModalNombre.Text = ""; txtModalEmail.Text = "";
            txtModalUsuario.Text = "";
            txtModalRol.Text = "Cliente"; txtModalTelefono.Text = ""; ddlModalTipoDoc.SelectedValue = "DNI";
            txtModalNumDoc.Text = ""; txtModalRUC.Text = ""; txtModalDireccion.Text = "";
            ddlModalSexo.SelectedValue = "O"; ddlModalEstado.SelectedValue = "Activo";

            litModalError.Text = "";
            SetModalReadOnly(false);
            updModalCliente.Update();
            ScriptManager.RegisterStartupScript(this, this.GetType(), "ShowNew", "$('#modalClienteLabel').text('Registrar Nuevo Cliente'); $('#modalCliente').modal('show');", true);
        }

        protected void btnGuardarCliente_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid) return;
            try
            {
                int personaID = Convert.ToInt32(hdClienteID.Value);
                int nroDocumento = int.Parse(txtModalNumDoc.Text);

                int ruc = 0;
                if (!string.IsNullOrEmpty(txtModalRUC.Text))
                {
                    int.TryParse(txtModalRUC.Text.Trim(), out ruc);
                }

                string passwordInput = "";
                bool esActivo = ddlModalEstado.SelectedValue == "Activo";

                string username = txtModalUsuario.Text.Trim();
                string telefono = txtModalTelefono.Text.Trim();
                string nombre = txtModalNombre.Text.Trim();
                string email = txtModalEmail.Text.Trim();

                int res = 0;
                if (personaID == 0) // NUEVO
                {
                    res = personaBo.InsertarCompleto(
                        username, passwordInput, email,
                        nombre, txtModalDireccion.Text, telefono,
                        ddlModalSexo.SelectedValue, nroDocumento, ruc, ddlModalTipoDoc.SelectedValue);
                }
                else // MODIFICAR
                {
                    int uid = Convert.ToInt32(hdTempUsuarioID.Value);
                    string passFinal = (passwordInput != "**********") ? passwordInput : null;

                    res = personaBo.ModificarCompleto(
                        personaID, uid, username, passFinal, email, esActivo,
                        nombre, txtModalDireccion.Text, telefono,
                        ddlModalSexo.SelectedValue, nroDocumento, ruc, ddlModalTipoDoc.SelectedValue);
                }

                if (res > 0)
                {
                    ScriptManager.RegisterStartupScript(this, GetType(), "ExitoScript", "mostrarExitoLocal('Operación realizada correctamente.');", true);
                }
                else
                {
                    litModalError.Text = "<div class='alert alert-danger'>Error al guardar en BD.</div>";
                    updModalCliente.Update();
                }
            }
            catch (Exception ex)
            {
                litModalError.Text = $"<div class='alert alert-danger'>Error: {ex.Message}</div>";
                updModalCliente.Update();
            }
        }

        protected void btnConfirmarEliminar_Click(object sender, EventArgs e)
        {
            try
            {
                int res = personaBo.EliminarCompleto(int.Parse(hdClienteIDEliminar.Value));
                if (res > 0) ScriptManager.RegisterStartupScript(this, GetType(), "ExitoDel", "mostrarExitoLocal('Cliente eliminado correctamente.');", true);
                else ScriptManager.RegisterStartupScript(this, GetType(), "ErrDel", "alert('No se pudo eliminar.');", true);
            }
            catch (Exception ex) { ScriptManager.RegisterStartupScript(this, GetType(), "ErrEx", $"alert('{ex.Message}');", true); }
        }

        protected void rptClientes_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "VerMascotas")
            {
                int id = Convert.ToInt32(e.CommandArgument);
                var mascotas = mascotaBo.ListarPorIdPersona(id);
                gvMascotas.DataSource = mascotas; gvMascotas.DataBind();
                updModalMascotas.Update();
                ScriptManager.RegisterStartupScript(this, this.GetType(), "Mascotas", "$('#modalMascotas').modal('show');", true);
            }
        }

        protected void lnkPaginado_Click(object sender, EventArgs e) { if (((LinkButton)sender).CommandName == "Anterior") CurrentPage--; else CurrentPage++; RecargarGrilla(); }
        protected void rptPaginador_ItemCommand(object s, RepeaterCommandEventArgs e) { CurrentPage = Convert.ToInt32(e.CommandArgument); RecargarGrilla(); }
        private void RecargarGrilla() { CargarClientes(txtNombre.Text, txtDocumento.Text, txtRUC.Text, int.Parse(ddlFiltroEstado.SelectedValue)); }
        private string ObternerIniciales(string n) { if (string.IsNullOrEmpty(n)) return "??"; var p = n.Trim().Split(' '); return p.Length > 1 ? (p[0][0] + "" + p[1][0]).ToUpper() : p[0].Substring(0, Math.Min(2, p[0].Length)).ToUpper(); }
        private string GetAvatarColor(int id) { string[] c = { "#007bff", "#28a745", "#ffc107", "#dc3545", "#17a2b8", "#6c757d" }; return c[Math.Abs(id) % c.Length]; }

        private void SetModalReadOnly(bool r)
        {
            txtModalNombre.ReadOnly = r; txtModalEmail.ReadOnly = r; txtModalUsuario.ReadOnly = r;
             txtModalRol.ReadOnly = true; txtModalTelefono.ReadOnly = r;
            ddlModalTipoDoc.Enabled = !r; txtModalNumDoc.ReadOnly = r; txtModalRUC.ReadOnly = r;
            txtModalDireccion.ReadOnly = r; ddlModalSexo.Enabled = !r; ddlModalEstado.Enabled = !r;
            btnGuardarCliente.Visible = !r;
        }
    }
}