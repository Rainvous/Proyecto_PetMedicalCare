using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using ProductoWS = SoftPetBussiness.ProductoClient;
using TipoWS = SoftPetBussiness.TipoProductoClient;

namespace SoftPetWA
{
    public partial class Secretaria_Inventario : System.Web.UI.Page
    {
        private ProductoBO boProducto = new ProductoBO();
        private TipoProductoBO boTipoProducto = new TipoProductoBO();
        private const int PageSize = 7;

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
            // Filtros Estáticos
            DataTable dtR = new DataTable(); dtR.Columns.Add("ID"); dtR.Columns.Add("Txt");
            dtR.Rows.Add("", "Todos"); dtR.Rows.Add("1", "S/ 0 - S/ 50"); dtR.Rows.Add("2", "S/ 51 - S/ 150"); dtR.Rows.Add("3", "S/ 151 a más");
            ddlRangoPrecio.DataSource = dtR; ddlRangoPrecio.DataTextField = "Txt"; ddlRangoPrecio.DataValueField = "ID"; ddlRangoPrecio.DataBind();

            DataTable dtE = new DataTable(); dtE.Columns.Add("Val"); dtE.Columns.Add("Txt");
            dtE.Rows.Add("", "Todos"); dtE.Rows.Add("1", "Activo"); dtE.Rows.Add("0", "Inactivo");
            ddlEstadoStock.DataSource = dtE; ddlEstadoStock.DataTextField = "Txt"; ddlEstadoStock.DataValueField = "Val"; ddlEstadoStock.DataBind();

            // Combo Modal
            var tipos = boTipoProducto.ListarTodos() ?? new List<TipoWS.tipoProductoDto>();
            ddlModalTipo.DataSource = tipos.Where(t => t.activo == true).ToList();
            ddlModalTipo.DataTextField = "nombre"; ddlModalTipo.DataValueField = "tipoProductoId"; ddlModalTipo.DataBind();
            ddlModalTipo.Items.Insert(0, new ListItem("Seleccione...", "0"));
        }

        private void MostrarEstadoSinResultados()
        {
            pnlResultados.Visible = false; pnlSinResultados.Visible = true;
            if (!string.IsNullOrEmpty(txtNombreProducto.Text) || ddlRangoPrecio.SelectedIndex > 0 || ddlEstadoStock.SelectedIndex > 0)
                lblMensajeVacio.Text = "No se encontraron productos con los filtros seleccionados.";
            else
                lblMensajeVacio.Text = "Utilice los filtros y presione 'Buscar' para ver el inventario.";
        }

        private void EjecutarBusqueda()
        {
            List<ProductoWS.productoDto> lista = boProducto.ListarBusquedaAvanzada(
                txtNombreProducto.Text.Trim(), ddlRangoPrecio.SelectedValue, ddlEstadoStock.SelectedValue, 0);

            if (lista != null && lista.Count > 0)
            {
                pnlResultados.Visible = true; pnlSinResultados.Visible = false;
                ConfigurarTabs(lista); EnlazarGrilla(lista);
            }
            else
            {
                MostrarEstadoSinResultados();
            }
        }

        private void EnlazarGrilla(List<ProductoWS.productoDto> lista)
        {
            if (CurrentTipoID > 0)
                lista = lista.Where(p => p.tipoProducto != null && p.tipoProducto.tipoProductoId == CurrentTipoID).ToList();

            int total = lista.Count;
            litRegistrosTotales.Text = total.ToString();
            int ini = (CurrentPage - 1) * PageSize;
            litRegistrosActuales.Text = total == 0 ? "0-0" : $"{ini + 1}-{Math.Min(ini + PageSize, total)}";

            var listaPaginada = lista.Skip(ini).Take(PageSize).Select(p => new {
                ID = p.productoId,
                Nombre = p.nombre,
                TipoNombre = p.tipoProducto != null ? p.tipoProducto.nombre : "Sin Tipo",
                TipoDesc = p.tipoProducto != null ? p.tipoProducto.descripcion : "",
                TipoID = p.tipoProducto != null ? p.tipoProducto.tipoProductoId : 0,
                Presentacion = p.presentacion,
                Precio = p.precioUnitario,
                Stock = p.stock,
                Activo = p.activo
            }).ToList();

            rptInventario.DataSource = listaPaginada; rptInventario.DataBind();
            GenerarPaginado(total);
        }

        private void ConfigurarTabs(List<ProductoWS.productoDto> lista)
        {
            var grupos = lista.GroupBy(p => new {
                ID = p.tipoProducto != null ? p.tipoProducto.tipoProductoId : 0,
                Nom = p.tipoProducto != null ? p.tipoProducto.nombre : "Otros"
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
            txtNombreProducto.Text = ""; ddlRangoPrecio.SelectedIndex = 0; ddlEstadoStock.SelectedIndex = 0;
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

        // --- LÓGICA DE TRANSACCIONES (SERVER) ---

        protected void btnGuardarProducto_Click(object s, EventArgs e)
        {
            if (!Page.IsValid) return;
            try
            {
                int id = int.Parse(hdProductoID.Value);
                int tipo = int.Parse(ddlModalTipo.SelectedValue);
                if (tipo == 0)
                {
                    litModalError.Text = "<div class='alert alert-danger'>Seleccione un tipo de producto.</div>";
                    updModalProducto.Update(); return;
                }

                double precio = double.Parse(txtModalPrecio.Text, CultureInfo.InvariantCulture);
                int stock = int.Parse(txtModalStock.Text);
                bool act = bool.Parse(ddlModalEstado.SelectedValue);

                int res = (id == 0) ? boProducto.Insertar(tipo, txtModalNombre.Text, txtModalPresentacion.Text, precio, stock, act)
                                    : boProducto.Modificar(id, tipo, txtModalNombre.Text, txtModalPresentacion.Text, precio, stock, act);

                if (res > 0)
                {
                    // Mensaje de éxito sin recargar grilla automáticamente (lo hace el redirect del master)
                    string msg = (id == 0) ? "Producto registrado exitosamente." : "Producto actualizado exitosamente.";
                    ScriptManager.RegisterStartupScript(this, GetType(), "Exito", $"mostrarExitoLocal('{msg}');", true);
                }
                else
                {
                    litModalError.Text = "<div class='alert alert-danger'>Error al guardar en base de datos.</div>";
                    updModalProducto.Update();
                }
            }
            catch (Exception ex)
            {
                litModalError.Text = $"<div class='alert alert-danger'>Error: {ex.Message}</div>";
                updModalProducto.Update();
            }
        }

        protected void btnConfirmarEliminar_Click(object s, EventArgs e)
        {
            try
            {
                int id = int.Parse(hdProductoIDEliminar.Value);
                if (boProducto.VerificarSiElProductoTieneInformacion(id) > 0)
                {
                    lblErrorEliminar.Text = "No se puede eliminar: Tiene historial asociado.";
                    updModalEliminar.Update(); return;
                }

                if (boProducto.Eliminar(id) > 0)
                {
                    ScriptManager.RegisterStartupScript(this, GetType(), "ExitoDel", "mostrarExitoLocal('Producto eliminado correctamente.');", true);
                }
                else
                {
                    lblErrorEliminar.Text = "Error al eliminar.";
                    updModalEliminar.Update();
                }
            }
            catch (Exception ex)
            {
                lblErrorEliminar.Text = "Error: " + ex.Message;
                updModalEliminar.Update();
            }
        }

        // Helpers de Estilo
        public string GetColorPorTipo(string t) { if (string.IsNullOrEmpty(t)) return "icon-bg-default"; t = t.ToLower(); return t.Contains("medicamento") ? "icon-bg-medicamento" : t.Contains("alimento") ? "icon-bg-alimento" : t.Contains("accesorio") ? "icon-bg-accesorio" : "icon-bg-default"; }
        public string GetIconoPorTipo(string t) { if (string.IsNullOrEmpty(t)) return "fas fa-box"; t = t.ToLower(); return t.Contains("medicamento") ? "fas fa-pills" : t.Contains("alimento") ? "fas fa-bone" : t.Contains("accesorio") ? "fas fa-tag" : "fas fa-box"; }
        public string GetTipoClasePorTipo(string t) { if (string.IsNullOrEmpty(t)) return "type-default"; t = t.ToLower(); return t.Contains("medicamento") ? "type-medicamento" : t.Contains("alimento") ? "type-alimento" : t.Contains("accesorio") ? "type-accesorio" : "type-default"; }
        protected void rptInventario_ItemCommand(object s, RepeaterCommandEventArgs e) { /* Solo para compatibilidad */ }
    }
}