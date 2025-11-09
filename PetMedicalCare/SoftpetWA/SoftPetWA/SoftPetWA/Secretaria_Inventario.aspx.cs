// Nombre del archivo: Secretaria_Inventario.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Secretaria_Inventario : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarFiltros();
                CargarInventario();
            }
        }

        private void CargarFiltros()
        {
            // Cargar DropDownList de Estado de Stock
            DataTable dtEstado = new DataTable();
            dtEstado.Columns.Add("EstadoID", typeof(string));
            dtEstado.Columns.Add("Nombre", typeof(string));
            dtEstado.Rows.Add("TODOS", "Todos");
            dtEstado.Rows.Add("ACTIVO", "Activo");
            dtEstado.Rows.Add("INACTIVO", "Inactivo");

            ddlEstadoStock.DataSource = dtEstado;
            ddlEstadoStock.DataTextField = "Nombre";
            ddlEstadoStock.DataValueField = "EstadoID";
            ddlEstadoStock.DataBind();

            // Cargar Pestañas de Filtro (ejemplo)
            DataTable dtTipos = new DataTable();
            dtTipos.Columns.Add("Tipo", typeof(string));
            dtTipos.Columns.Add("Cantidad", typeof(int));
            dtTipos.Rows.Add("Todos", 45);
            dtTipos.Rows.Add("Medicamentos", 15);
            dtTipos.Rows.Add("Alimentos", 12);
            dtTipos.Rows.Add("Accesorios", 8);
            dtTipos.Rows.Add("Higiene", 6);
            dtTipos.Rows.Add("Juguetes", 4);

            rptFiltroTipo.DataSource = dtTipos;
            rptFiltroTipo.DataBind();
        }

        private void CargarInventario()
        {
            DataTable dt = ObtenerInventarioEjemplo();
            rptInventario.DataSource = dt;
            rptInventario.DataBind();

            // Actualizar contadores de ejemplo
            litRegistrosTotales.Text = "45"; // Valor fijo de Figma
            litRegistrosActuales.Text = "1-10"; // Valor fijo de Figma
        }

        // En Secretaria_Inventario.aspx.cs
        private DataTable ObtenerInventarioEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("ProductoID", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("Descripcion", typeof(string));
            dt.Columns.Add("Tipo", typeof(string));
            dt.Columns.Add("TipoClaseCss", typeof(string)); // Ej: "medicamento", "alimento" (para badge y color icono)
            dt.Columns.Add("Presentacion", typeof(string));
            dt.Columns.Add("PrecioFormateado", typeof(string));
            dt.Columns.Add("Stock", typeof(int)); // Guardamos el número
            dt.Columns.Add("StockTexto", typeof(string)); // Ej: "85 unidades"
            dt.Columns.Add("StockCss", typeof(string)); // Ej: "stock-warning" o ""
            dt.Columns.Add("Estado", typeof(string)); // Ej: "Activo", "Agotado"
            dt.Columns.Add("EstadoClaseCss", typeof(string)); // Ej: "status-activo", "status-agotado"
            dt.Columns.Add("IconoCss", typeof(string)); // Ej: "fas fa-pills"
            dt.Columns.Add("IconoColorCss", typeof(string)); // NUEVO: Ej: "icon-bg-medicamento"

            // Datos de ejemplo
            int stock;
            string stockCss;
            string estado;
            string estadoClaseCss;

            // Producto 1
            stock = 85;
            stockCss = stock < 10 ? "stock-warning" : "";
            estado = stock > 0 ? "Activo" : "Agotado";
            estadoClaseCss = stock > 0 ? "status-activo" : "status-agotado";
            dt.Rows.Add(1, "AMOXICILINA 500MG", "Antibiótico de amplio espectro", "Medicamento", "type-medicamento", "Caja x 10 tabletas", "S/ 35.00", stock, $"{stock} unidades", stockCss, estado, estadoClaseCss, "fas fa-pills", "icon-bg-medicamento");

            // Producto 2
            stock = 5; // Stock bajo
            stockCss = stock < 10 ? "stock-warning" : "";
            estado = stock > 0 ? "Activo" : "Agotado";
            estadoClaseCss = stock > 0 ? "status-activo" : "status-agotado";
            dt.Rows.Add(2, "VACUNA ANTIRRÁBICA", "Inmunización contra rabia canina", "Medicamento", "type-medicamento", "Dosis única 1ml", "S/ 45.00", stock, $"{stock} unidades", stockCss, estado, estadoClaseCss, "fas fa-syringe", "icon-bg-medicamento");

            // Producto 3
            stock = 32;
            stockCss = stock < 10 ? "stock-warning" : "";
            estado = stock > 0 ? "Activo" : "Agotado";
            estadoClaseCss = stock > 0 ? "status-activo" : "status-agotado";
            dt.Rows.Add(3, "ALIMENTO PREMIUM ADULTO", "Concentrado para perros adultos", "Alimento", "type-alimento", "Bolsa 15Kg", "S/ 180.00", stock, $"{stock} unidades", stockCss, estado, estadoClaseCss, "fas fa-bone", "icon-bg-alimento");

            // Producto 4
            stock = 58;
            stockCss = stock < 10 ? "stock-warning" : "";
            estado = stock > 0 ? "Activo" : "Agotado";
            estadoClaseCss = stock > 0 ? "status-activo" : "status-agotado";
            dt.Rows.Add(4, "ALIMENTO PARA GATOS", "Nutrición balanceada para felinos", "Alimento", "type-alimento", "Bolsa 8Kg", "S/ 120.00", stock, $"{stock} unidades", stockCss, estado, estadoClaseCss, "fas fa-cat", "icon-bg-alimento"); // Cambiado icono a gato

            // Producto 5
            stock = 24;
            stockCss = stock < 10 ? "stock-warning" : "";
            estado = stock > 0 ? "Activo" : "Agotado";
            estadoClaseCss = stock > 0 ? "status-activo" : "status-agotado";
            dt.Rows.Add(5, "COLLAR ANTIPULGAS", "Protección por 8 meses", "Accesorio", "type-accesorio", "Unidad", "S/ 55.00", stock, $"{stock} unidades", stockCss, estado, estadoClaseCss, "fas fa-tag", "icon-bg-accesorio");

            // Producto 6 (Agotado)
            stock = 0;
            stockCss = stock < 10 ? "stock-warning" : "";
            estado = stock > 0 ? "Activo" : "Agotado";
            estadoClaseCss = stock > 0 ? "status-activo" : "status-agotado";
            dt.Rows.Add(6, "SHAMPOO MEDICADO", "Para pieles sensibles", "Higiene", "type-higiene", "Frasco 500ml", "S/ 68.00", stock, $"{stock} unidades", stockCss, estado, estadoClaseCss, "fas fa-pump-soap", "icon-bg-higiene");


            return dt;
        }

        // --- Eventos de Botones (Vacíos por ahora) ---

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            // Lógica de búsqueda iría aquí
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            // Lógica para limpiar filtros iría aquí
        }

        protected void btnNuevoProducto_Click(object sender, EventArgs e)
        {
            // Lógica para redirigir a "Nuevo Producto"
            // Response.Redirect("Secretaria_NuevoProducto.aspx");
        }

        protected void rptInventario_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // Lógica para los botones de la fila (Editar, Stock, Eliminar)
        }

        protected void rptFiltroTipo_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // Lógica para filtrar por tipo al hacer clic en las pestañas
        }
    }
}