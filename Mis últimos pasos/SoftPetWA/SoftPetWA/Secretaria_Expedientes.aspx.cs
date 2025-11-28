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
    public partial class Secretaria_Expedientes : System.Web.UI.Page
    {
        private MascotaBO boMascota = new MascotaBO();
        private const int PageSize = 7;

        public int CurrentPage
        {
            get { return (int)(ViewState["CurrentPage"] ?? 1); }
            set { ViewState["CurrentPage"] = value; }
        }

        // ViewModel actualizado (Estado en lugar de Documento)
        public class ExpedienteViewModel
        {
            public int MascotaID { get; set; }
            public string MascotaNombre { get; set; }
            public string EspecieRaza { get; set; }
            public string AvatarIcon { get; set; }
            public string PropietarioNombre { get; set; }
            public string Telefono { get; set; }
            public bool Activo { get; set; } // Nuevo campo para el Badge
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CurrentPage = 1;
                CargarFiltros();
                MostrarEstadoSinResultados();
            }
        }

        private void CargarFiltros()
        {
            DataTable dtTipo = new DataTable();
            dtTipo.Columns.Add("Nombre", typeof(string));
            dtTipo.Rows.Add("Todos");
            dtTipo.Rows.Add("Perro");
            dtTipo.Rows.Add("Gato");

            ddlTipo.DataSource = dtTipo;
            ddlTipo.DataTextField = "Nombre";
            ddlTipo.DataValueField = "Nombre";
            ddlTipo.DataBind();
        }

        private void MostrarEstadoSinResultados()
        {
            rptExpedientesLista.DataSource = null;
            rptExpedientesLista.DataBind();

            divListaExpedientes.Visible = false;
            pnlSinResultados.Visible = true;

            if (!string.IsNullOrEmpty(txtNombreMascota.Text) || !string.IsNullOrEmpty(txtPropietario.Text))
                lblMensajeVacio.Text = "No se encontraron expedientes con los filtros seleccionados.";
            else
                lblMensajeVacio.Text = "Utilice los filtros para buscar expedientes clínicos.";
        }

        private void CargarExpedientes()
        {
            string nombreMascota = txtNombreMascota.Text.Trim();
            string nombreDuenio = txtPropietario.Text.Trim();
            string especie = ddlTipo.SelectedValue == "Todos" ? "" : ddlTipo.SelectedValue;

            // Filtro Estado desde el DropDownList
            int estadoFiltro = int.Parse(ddlEstado.SelectedValue); // -1=Todos, 1=Activo, 0=Inactivo

            // Llamada al BO con los 4 parámetros
            List<mascotaDto> listaMascotas = boMascota.ListarBusquedaAvanzada(nombreMascota, especie, nombreDuenio, estadoFiltro);

            if (listaMascotas == null || listaMascotas.Count == 0)
            {
                MostrarEstadoSinResultados();
                return;
            }

            pnlSinResultados.Visible = false;
            divListaExpedientes.Visible = true;

            var listaView = listaMascotas.Select(m => new ExpedienteViewModel
            {
                MascotaID = m.mascotaId,
                MascotaNombre = m.nombre,
                EspecieRaza = $"{m.especie} - {m.raza}",
                AvatarIcon = GetIconoEspecie(m.especie),
                PropietarioNombre = (m.persona != null) ? m.persona.nombre : "Desconocido",
                Telefono = (m.persona != null) ? m.persona.telefono : "-",
                Activo = m.activo // Mapeamos el estado real
            }).ToList();

            int totalRegistros = listaView.Count;
            int inicio = (CurrentPage - 1) * PageSize;
            int fin = Math.Min(inicio + PageSize, totalRegistros);

            litRegistrosTotales.Text = totalRegistros.ToString();
            litRegistrosActuales.Text = totalRegistros > 0 ? $"{inicio + 1}-{fin}" : "0-0";

            rptExpedientesLista.DataSource = listaView.Skip(inicio).Take(PageSize).ToList();
            rptExpedientesLista.DataBind();

            GenerarPaginado(totalRegistros);
        }

        private void GenerarPaginado(int totalRegistros)
        {
            int totalPages = (int)Math.Ceiling((double)totalRegistros / PageSize);
            lnkAnterior.Enabled = (CurrentPage > 1);
            lnkSiguiente.Enabled = (CurrentPage < totalPages);

            var paginas = new List<object>();
            for (int i = 1; i <= totalPages; i++)
                paginas.Add(new { Pagina = i, EsPaginaActual = (i == CurrentPage) });

            rptPaginador.DataSource = paginas;
            rptPaginador.DataBind();
        }

        protected void btnBuscar_Click(object sender, EventArgs e) { CurrentPage = 1; CargarExpedientes(); }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtNombreMascota.Text = ""; txtPropietario.Text = "";
            ddlTipo.SelectedIndex = 0; ddlEstado.SelectedValue = "1"; // Reset a Activos por defecto (o -1 si prefieres)
            CurrentPage = 1;
            MostrarEstadoSinResultados();
        }

        protected void lnkPaginado_Click(object sender, EventArgs e)
        {
            if (((LinkButton)sender).CommandName == "Anterior") CurrentPage--; else CurrentPage++;
            CargarExpedientes();
        }
        protected void rptPaginador_ItemCommand(object s, RepeaterCommandEventArgs e)
        {
            CurrentPage = Convert.ToInt32(e.CommandArgument);
            CargarExpedientes();
        }

        protected string GetIconoEspecie(string especie)
        {
            if (string.IsNullOrEmpty(especie)) return "fas fa-paw";
            string e = especie.Trim().ToLower();
            if (e.Contains("perro")) return "fas fa-dog";
            if (e.Contains("gato")) return "fas fa-cat";
            return "fas fa-paw";
        }
    }
}