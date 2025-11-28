using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.PersonaClient;

namespace SoftPetWA
{
    public partial class Secretaria_Mascotas : System.Web.UI.Page
    {
        private MascotaBO boMascota;
        private PersonaBO boPersona;

        [Serializable]
        public class MascotaViewModel
        {
            public int MascotaId { get; set; }
            public string Nombre { get; set; }
            public string Especie { get; set; }
            public string Raza { get; set; }
            public string Sexo { get; set; }
            public string Color { get; set; }
            public bool Activo { get; set; }
            public int PersonaId { get; set; }
            public string NombreDuenio { get; set; }
            public string TelefonoDuenio { get; set; }
        }

        public class ClienteBusquedaVM
        {
            public int PersonaID { get; set; }
            public string Nombre { get; set; }
            public string NumDocumento { get; set; }
            public string Email { get; set; }
        }

        private const int PageSize = 6;
        public int CurrentPage
        {
            get { return (int)(ViewState["CurrentPage"] ?? 1); }
            set { ViewState["CurrentPage"] = value; }
        }

        protected void Page_Init(object sender, EventArgs e)
        {
            boMascota = new MascotaBO();
            boPersona = new PersonaBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CurrentPage = 1;
                CargarFiltrosDropDowns();
                LimpiarGrilla();
            }
        }

        private void LimpiarGrilla()
        {
            rptMascotas.DataSource = null;
            rptMascotas.DataBind();
            litRegistrosTotales.Text = "0";
            litRegistrosActuales.Text = "0-0";
            divListaMascotas.Visible = false;
            pnlSinResultados.Visible = true;
        }

        private void CargarFiltrosDropDowns()
        {
            DataTable dtEspecie = new DataTable();
            dtEspecie.Columns.Add("Nombre", typeof(string));
            dtEspecie.Rows.Add("Todas"); dtEspecie.Rows.Add("Perro"); dtEspecie.Rows.Add("Gato");
            ddlEspecie.DataSource = dtEspecie; ddlEspecie.DataTextField = "Nombre"; ddlEspecie.DataValueField = "Nombre"; ddlEspecie.DataBind();

            var dtModal = dtEspecie.AsEnumerable().Where(r => r.Field<string>("Nombre") != "Todas").CopyToDataTable();
            ddlModalEspecie.DataSource = dtModal; ddlModalEspecie.DataTextField = "Nombre"; ddlModalEspecie.DataValueField = "Nombre"; ddlModalEspecie.DataBind();
        }

        private void EnlazarDatos()
        {
            string nombreMascota = txtNombreMascota.Text.Trim();
            string nombreDuenio = txtPropietario.Text.Trim();
            string especie = ddlEspecie.SelectedValue == "Todas" ? "" : ddlEspecie.SelectedValue;
            int estadoFiltro = int.Parse(ddlFiltroEstado.SelectedValue);

            List<mascotaDto> listaResultados = boMascota.ListarBusquedaAvanzada(nombreMascota, especie, nombreDuenio, estadoFiltro);

            if (listaResultados == null || listaResultados.Count == 0)
            {
                LimpiarGrilla();
                return;
            }

            pnlSinResultados.Visible = false;
            divListaMascotas.Visible = true;

            var listaViewModel = listaResultados.Select(m => new MascotaViewModel
            {
                MascotaId = m.mascotaId,
                Nombre = m.nombre,
                Especie = m.especie,
                Raza = m.raza,
                Sexo = m.sexo,
                Color = m.color,
                Activo = m.activo,
                PersonaId = m.persona != null ? m.persona.personaId : 0,
                NombreDuenio = m.persona != null ? m.persona.nombre : "Desconocido",
                TelefonoDuenio = m.persona != null ? m.persona.telefono : "-"
            }).ToList();

            int totalRegistros = listaViewModel.Count;
            litRegistrosTotales.Text = totalRegistros.ToString();
            int inicio = (CurrentPage - 1) * PageSize + 1;
            int fin = Math.Min(CurrentPage * PageSize, totalRegistros);
            litRegistrosActuales.Text = $"{inicio}-{fin}";

            rptMascotas.DataSource = listaViewModel.Skip((CurrentPage - 1) * PageSize).Take(PageSize).ToList();
            rptMascotas.DataBind();
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

        // --- Eventos ---
        protected void btnBuscar_Click(object sender, EventArgs e) { CurrentPage = 1; EnlazarDatos(); }
        protected void btnLimpiar_Click(object sender, EventArgs e) { txtNombreMascota.Text = ""; txtPropietario.Text = ""; ddlEspecie.SelectedIndex = 0; ddlFiltroEstado.SelectedValue = "-1"; CurrentPage = 1; LimpiarGrilla(); }
        protected void lnkPaginado_Click(object sender, EventArgs e) { if (((LinkButton)sender).CommandName == "Anterior") CurrentPage--; else CurrentPage++; EnlazarDatos(); }
        protected void rptPaginador_ItemCommand(object s, RepeaterCommandEventArgs e) { CurrentPage = Convert.ToInt32(e.CommandArgument); EnlazarDatos(); }

        protected void rptMascotas_ItemCommand(object s, RepeaterCommandEventArgs e) { /* Vacío (JS maneja acciones) */ }

        // --- Modal Nuevo ---
        protected void btnNuevaMascota_Click(object sender, EventArgs e)
        {
            hdMascotaID.Value = "0"; hdClienteIDSeleccionado.Value = "0";
            txtModalNombre.Text = ""; txtModalClienteNombre.Text = "";
            ddlModalEspecie.SelectedIndex = 0; txtModalRaza.Text = ""; ddlModalSexo.SelectedValue = "M";
            txtModalColor.Text = ""; ddlModalEstado.SelectedValue = "true"; litMensaje.Text = "";

            // Habilitar botón buscar cliente
            btnAbrirBuscadorCliente.Visible = true;
            btnGuardarMascota.Visible = true;

            updModalMascota.Update();
            ScriptManager.RegisterStartupScript(this, GetType(), "ShowNew", "$('#modalMascotaLabel').text('Registrar Nueva Mascota'); $('#modalMascota').modal('show');", true);
        }

        protected void btnGuardarMascota_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid) return;
            try
            {
                int mascotaID = Convert.ToInt32(hdMascotaID.Value);
                int personaId = Convert.ToInt32(hdClienteIDSeleccionado.Value);
                if (personaId == 0) { litMensaje.Text = "<div class='alert alert-warning'>Debe seleccionar un propietario.</div>"; updModalMascota.Update(); return; }

                string nombre = txtModalNombre.Text; string especie = ddlModalEspecie.SelectedValue;
                string sexo = ddlModalSexo.SelectedValue; string raza = txtModalRaza.Text;
                string color = txtModalColor.Text; bool activo = Convert.ToBoolean(ddlModalEstado.SelectedValue);

                int res;
                if (mascotaID == 0) res = boMascota.Insertar(personaId, nombre, especie, sexo, raza, color, null, activo);
                else res = boMascota.Modificar(mascotaID, personaId, nombre, especie, sexo, raza, color, null, activo);

                if (res > 0) ScriptManager.RegisterStartupScript(this, GetType(), "Exito", "mostrarExitoLocal('Mascota guardada correctamente.');", true);
                else { litMensaje.Text = "<div class='alert alert-danger'>Error al guardar.</div>"; updModalMascota.Update(); }
            }
            catch (Exception ex) { litMensaje.Text = $"<div class='alert alert-danger'>Error: {ex.Message}</div>"; updModalMascota.Update(); }
        }

        // --- Búsqueda Cliente ---
        protected void btnAbrirBuscadorCliente_Click(object s, EventArgs e)
        {
            txtBusqCliente.Text = ""; gvBusqClientes.DataSource = null; gvBusqClientes.DataBind();
            updModalBuscarPropietario.Update();
            ScriptManager.RegisterStartupScript(this, GetType(), "OpenSearch", "$('#modalBuscarPropietario').modal('show');", true);
        }

        protected void btnBusqCliente_Click(object s, EventArgs e)
        {
            var resultados = boPersona.ListarBusquedaAvanzada(txtBusqCliente.Text.Trim(), null, null, 1);
            var listaVM = resultados.Select(p => new ClienteBusquedaVM
            {
                PersonaID = p.personaId,
                Nombre = p.nombre,
                NumDocumento = p.nroDocumento.ToString(),
                Email = p.usuario != null ? p.usuario.correo : "-"
            }).ToList();
            gvBusqClientes.DataSource = listaVM; gvBusqClientes.DataBind();
            updModalBuscarPropietario.Update();
        }

        protected void gvBusqClientes_RowCommand(object s, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "Seleccionar")
            {
                string[] args = e.CommandArgument.ToString().Split('|');
                hdClienteIDSeleccionado.Value = args[0];
                txtModalClienteNombre.Text = args[1];
                updModalMascota.Update();
                ScriptManager.RegisterStartupScript(this, GetType(), "CloseSearch", "$('#modalBuscarPropietario').modal('hide');", true);
            }
        }

        protected void btnConfirmarEliminar_Click(object s, EventArgs e)
        {
            try
            {
                int id = int.Parse(hdMascotaIDEliminar.Value);
                boMascota.Eliminar(id);
                ScriptManager.RegisterStartupScript(this, GetType(), "ExitoDel", "mostrarExitoLocal('Mascota eliminada correctamente.');", true);
            }
            catch { }
        }

        // Helpers
        protected string GetIniciales(string n) { if (string.IsNullOrEmpty(n)) return "??"; var p = n.Split(' '); return p.Length > 1 ? (p[0][0].ToString() + p[1][0].ToString()).ToUpper() : p[0].Substring(0, Math.Min(2, p[0].Length)).ToUpper(); }
        protected string GetAvatarColor(string n) { if (string.IsNullOrEmpty(n)) return "#ccc"; string[] c = { "#4a6fa5", "#f7a36c", "#904c77", "#5b8c5a" }; return c[Math.Abs(n.GetHashCode()) % c.Length]; }
        protected string GetIconoEspecie(string e) { e = e.ToLower(); if (e.Contains("perro")) return "fas fa-dog"; if (e.Contains("gato")) return "fas fa-cat"; return "fas fa-paw"; }
        protected void rptMascotas_ItemDataBound(object s, RepeaterItemEventArgs e) { }
    }
}