using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SvcWS = SoftPetBussiness.ServicioClient;
using TipoWS = SoftPetBussiness.TipoServicioClient;

namespace SoftPetWA
{
    public partial class Secretaria_Servicios : System.Web.UI.Page
    {
        private ServicioBO boServicio = new ServicioBO();
        private TipoServicioBO boTipoServicio = new TipoServicioBO();
        private const int PageSize = 8;

        public int CurrentPage { get { return (int)(ViewState["Page"] ?? 1); } set { ViewState["Page"] = value; } }
        public int CurrentTipoID { get { return (int)(ViewState["TipoID"] ?? 0); } set { ViewState["TipoID"] = value; } }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CurrentPage = 1; CurrentTipoID = 0;
                CargarCombos();
                MostrarEstadoSinResultados();
            }
        }

        private void CargarCombos()
        {
            DataTable dtRango = new DataTable(); dtRango.Columns.Add("ID"); dtRango.Columns.Add("Txt");
            dtRango.Rows.Add("", "Todos");
            dtRango.Rows.Add("1", "S/ 0 - S/ 50");
            dtRango.Rows.Add("2", "S/ 51 - S/ 150");
            dtRango.Rows.Add("3", "S/ 151 a más");
            ddlRangoCosto.DataSource = dtRango; ddlRangoCosto.DataTextField = "Txt"; ddlRangoCosto.DataValueField = "ID"; ddlRangoCosto.DataBind();

            DataTable dtEstado = new DataTable(); dtEstado.Columns.Add("ID"); dtEstado.Columns.Add("Txt");
            dtEstado.Rows.Add("", "Todos"); dtEstado.Rows.Add("1", "Activo"); dtEstado.Rows.Add("0", "Inactivo");
            ddlEstado.DataSource = dtEstado; ddlEstado.DataTextField = "Txt"; ddlEstado.DataValueField = "ID"; ddlEstado.DataBind();

            var tipos = boTipoServicio.ListarTodos() ?? new List<TipoWS.tipoServicioDto>();
            ddlModalTipo.DataSource = tipos.Where(t => t.activo == true).ToList();
            ddlModalTipo.DataTextField = "nombre"; ddlModalTipo.DataValueField = "tipoServicioId"; ddlModalTipo.DataBind();
            ddlModalTipo.Items.Insert(0, new ListItem("Seleccione...", "0"));
        }

        private void MostrarEstadoSinResultados()
        {
            pnlResultados.Visible = false; pnlSinResultados.Visible = true;
            if (!string.IsNullOrEmpty(txtNombreServicio.Text) || ddlRangoCosto.SelectedIndex > 0 || ddlEstado.SelectedIndex > 0)
                lblMensajeVacio.Text = "No se encontraron servicios con los filtros seleccionados.";
            else
                lblMensajeVacio.Text = "Utilice los filtros y presione 'Buscar' para ver los servicios.";
        }

        private void EjecutarBusqueda()
        {
            List<SvcWS.servicioDto> lista = boServicio.listarPorNombreRangoActivo(
                txtNombreServicio.Text.Trim(), ddlRangoCosto.SelectedValue, ddlEstado.SelectedValue, 0);

            if (lista != null && lista.Count > 0)
            {
                pnlResultados.Visible = true; pnlSinResultados.Visible = false;
                ConfigurarTabs(lista); EnlazarDatos(lista);
            }
            else
            {
                MostrarEstadoSinResultados();
            }
        }

        private void EnlazarDatos(List<SvcWS.servicioDto> lista)
        {
            if (CurrentTipoID > 0)
                lista = lista.Where(s => s.tipoServicio != null && s.tipoServicio.tipoServicioId == CurrentTipoID).ToList();

            int total = lista.Count;
            litRegistrosTotales.Text = total.ToString();
            int ini = (CurrentPage - 1) * PageSize;
            litRegistrosActuales.Text = total == 0 ? "0-0" : $"{ini + 1}-{Math.Min(ini + PageSize, total)}";

            var listaPaginada = lista.Skip(ini).Take(PageSize).Select(s => new {
                ID = s.servicioId,
                Nombre = s.nombre,
                TipoNombre = s.tipoServicio != null ? s.tipoServicio.nombre : "Sin Tipo",
                TipoID = s.tipoServicio != null ? s.tipoServicio.tipoServicioId : 0,
                Descripcion = s.descripcion,
                Costo = s.costo,
                Activo = s.activo
            }).ToList();

            rptServicios.DataSource = listaPaginada; rptServicios.DataBind();
            GenerarPaginado(total);
        }

        private void ConfigurarTabs(List<SvcWS.servicioDto> lista)
        {
            var grupos = lista.GroupBy(s => new {
                ID = s.tipoServicio != null ? s.tipoServicio.tipoServicioId : 0,
                Nom = s.tipoServicio != null ? s.tipoServicio.nombre : "Otros"
            }).Select(g => new { TipoID = g.Key.ID, Tipo = g.Key.Nom, Cantidad = g.Count() }).OrderBy(x => x.Tipo).ToList();

            DataTable dt = new DataTable();
            dt.Columns.Add("TipoID", typeof(int)); dt.Columns.Add("Tipo", typeof(string)); dt.Columns.Add("Cantidad", typeof(int));
            dt.Rows.Add(0, "Todos", lista.Count);
            foreach (var g in grupos) dt.Rows.Add(g.TipoID, g.Tipo, g.Cantidad);

            rptFiltroTipo.DataSource = dt; rptFiltroTipo.DataBind();
        }

        private void GenerarPaginado(int total)
        {
            int pages = (int)Math.Ceiling((double)total / PageSize);
            lnkAnterior.Enabled = CurrentPage > 1; lnkSiguiente.Enabled = CurrentPage < pages;
            var l = new List<object>(); for (int i = 1; i <= pages; i++) l.Add(new { Pagina = i, EsPaginaActual = i == CurrentPage });
            rptPaginador.DataSource = l; rptPaginador.DataBind();
        }

        protected void btnBuscar_Click(object s, EventArgs e) { CurrentPage = 1; EjecutarBusqueda(); }
        protected void btnLimpiar_Click(object s, EventArgs e)
        {
            txtNombreServicio.Text = ""; ddlRangoCosto.SelectedIndex = 0; ddlEstado.SelectedIndex = 0;
            CurrentTipoID = 0; CurrentPage = 1; MostrarEstadoSinResultados();
        }
        protected void rptFiltroTipo_ItemCommand(object s, RepeaterCommandEventArgs e)
        {
            CurrentTipoID = Convert.ToInt32(e.CommandArgument); CurrentPage = 1; EjecutarBusqueda();
        }
        protected void lnkPaginado_Click(object s, EventArgs e)
        {
            if (((LinkButton)s).CommandName == "Anterior") CurrentPage--; else CurrentPage++; EjecutarBusqueda();
        }
        protected void rptPaginador_ItemCommand(object s, RepeaterCommandEventArgs e)
        {
            CurrentPage = Convert.ToInt32(e.CommandArgument); EjecutarBusqueda();
        }

        // CRUD
        protected void btnGuardarServicio_Click(object s, EventArgs e)
        {
            if (!Page.IsValid) return;
            try
            {
                int id = int.Parse(hdServicioID.Value);
                int tipoId = int.Parse(ddlModalTipo.SelectedValue);
                if (tipoId == 0) { litModalError.Text = "<div class='alert alert-danger'>Seleccione un tipo.</div>"; updModalServicio.Update(); return; }

                double costo = double.Parse(txtModalPrecio.Text, CultureInfo.InvariantCulture);
                bool act = bool.Parse(ddlModalEstado.SelectedValue);
                string estadoStr = act ? "Activo" : "Inactivo";

                int res = (id == 0) ? boServicio.Insertar(tipoId, txtModalNombre.Text, txtModalDescripcion.Text, costo, estadoStr, act)
                                    : boServicio.Modificar(id, tipoId, txtModalNombre.Text, txtModalDescripcion.Text, costo, estadoStr, act);

                if (res > 0)
                {
                    string msg = id == 0 ? "Servicio creado correctamente." : "Servicio actualizado correctamente.";
                    // ERROR CORREGIDO AQUI: Faltaba cerrar paréntesis y argumentos
                    ScriptManager.RegisterStartupScript(this, GetType(), "Exito", $"mostrarExitoLocal('{msg}');", true);
                }
                else
                {
                    litModalError.Text = "<div class='alert alert-danger'>Error al guardar.</div>";
                    updModalServicio.Update();
                }
            }
            catch (Exception ex) { litModalError.Text = $"<div class='alert alert-danger'>Error: {ex.Message}</div>"; updModalServicio.Update(); }
        }

        protected void btnConfirmarEliminar_Click(object s, EventArgs e)
        {
            try
            {
                int id = int.Parse(hdServicioIDEliminar.Value);
                if (boServicio.VerificarSiElServicioTieneInformacion(id) > 0)
                {
                    lblErrorEliminar.Text = "No se puede eliminar: Tiene historial asociado.";
                    updModalEliminar.Update(); return;
                }
                if (boServicio.Eliminar(id) > 0)
                {
                    ScriptManager.RegisterStartupScript(this, GetType(), "ExitoDel", "mostrarExitoLocal('Servicio eliminado correctamente.');", true);
                }
                else
                {
                    lblErrorEliminar.Text = "Error al eliminar."; updModalEliminar.Update();
                }
            }
            catch (Exception ex) { lblErrorEliminar.Text = "Error: " + ex.Message; updModalEliminar.Update(); }
        }

        protected void rptServicios_ItemCommand(object s, RepeaterCommandEventArgs e) { /* Solo compatibilidad */ }

        // =================================================================
        // MÉTODOS HELPER VISUALES (Públicos para ser vistos por ASPX)
        // =================================================================

        public string GetIconoPorTipo(object tipoObj)
        {
            string tipo = Convert.ToString(tipoObj);
            if (string.IsNullOrEmpty(tipo)) return "fas fa-briefcase-medical";

            tipo = tipo.ToLower();

            if (tipo.Contains("consulta") || tipo.Contains("evaluacion") ||
                tipo.Contains("evaluación") || tipo.Contains("check") || tipo.Contains("control"))
                return "fas fa-stethoscope";

            if (tipo.Contains("baño") || tipo.Contains("bano") ||
                tipo.Contains("estetica") || tipo.Contains("estética") ||
                tipo.Contains("grooming") || tipo.Contains("corte"))
                return "fas fa-bath";

            if (tipo.Contains("cirugia") || tipo.Contains("cirugía") ||
                tipo.Contains("operacion") || tipo.Contains("operación") ||
                tipo.Contains("esteril") || tipo.Contains("castra"))
                return "fas fa-user-md";

            if (tipo.Contains("vacuna") || tipo.Contains("inyecc") ||
                tipo.Contains("despara") ||
                tipo.Contains("tratamiento"))
                return "fas fa-syringe";

            if (tipo.Contains("analisis") || tipo.Contains("análisis") ||
                tipo.Contains("lab") || tipo.Contains("sangre") ||
                tipo.Contains("hemograma"))
                return "fas fa-microscope";

            return "fas fa-briefcase-medical";
        }

        public string GetColorPorTipo(object tipoObj)
        {
            string tipo = Convert.ToString(tipoObj);
            if (string.IsNullOrEmpty(tipo)) return "bg-primary text-white";

            tipo = tipo.ToLower();

            if (tipo.Contains("consulta") || tipo.Contains("eval"))
                return "bg-info text-white";

            if (tipo.Contains("baño") || tipo.Contains("bano") || tipo.Contains("estet") || tipo.Contains("groom"))
                return "bg-success text-white";

            if (tipo.Contains("cirugia") || tipo.Contains("cirugía") || tipo.Contains("oper") || tipo.Contains("esteril"))
                return "bg-danger text-white";

            if (tipo.Contains("vacuna") || tipo.Contains("despara") || tipo.Contains("inyecc"))
                return "bg-warning text-dark";

            if (tipo.Contains("analisis") || tipo.Contains("lab"))
                return "bg-secondary text-white";

            return "bg-primary text-white";
        }
    }
}