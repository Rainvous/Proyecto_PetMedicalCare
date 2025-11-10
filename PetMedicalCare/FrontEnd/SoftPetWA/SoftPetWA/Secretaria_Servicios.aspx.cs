// Nombre del archivo: Secretaria_Servicios.aspx.cs
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
    public partial class Secretaria_Servicios : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarFiltros();
                CargarServicios();
            }
        }

        private void CargarFiltros()
        {
            // Cargar Pestañas de Filtro (ejemplo)
            DataTable dtTipos = new DataTable();
            dtTipos.Columns.Add("Tipo", typeof(string));
            dtTipos.Columns.Add("Cantidad", typeof(int));
            dtTipos.Rows.Add("Todos", 18);
            dtTipos.Rows.Add("Servicios Médicos", 12);
            dtTipos.Rows.Add("Servicios No Médicos", 6);

            rptFiltroTipo.DataSource = dtTipos;
            rptFiltroTipo.DataBind();

            // Cargar DropDownList de Rango de Costo
            DataTable dtRango = new DataTable();
            dtRango.Columns.Add("RangoID", typeof(string));
            dtRango.Columns.Add("Nombre", typeof(string));
            dtRango.Rows.Add("TODOS", "Todos");
            dtRango.Rows.Add("0-50", "S/ 0 - S/ 50");
            dtRango.Rows.Add("51-150", "S/ 51 - S/ 150");
            dtRango.Rows.Add("151-MAS", "S/ 151 a más");

            ddlRangoCosto.DataSource = dtRango;
            ddlRangoCosto.DataTextField = "Nombre";
            ddlRangoCosto.DataValueField = "RangoID";
            ddlRangoCosto.DataBind();

            // Cargar DropDownList de Estado
            DataTable dtEstado = new DataTable();
            dtEstado.Columns.Add("EstadoID", typeof(string));
            dtEstado.Columns.Add("Nombre", typeof(string));
            dtEstado.Rows.Add("TODOS", "Todos");
            dtEstado.Rows.Add("ACTIVO", "Activo");
            dtEstado.Rows.Add("INACTIVO", "Inactivo");

            ddlEstado.DataSource = dtEstado;
            ddlEstado.DataTextField = "Nombre";
            ddlEstado.DataValueField = "EstadoID";
            ddlEstado.DataBind();
        }

        private void CargarServicios()
        {
            DataTable dt = ObtenerServiciosEjemplo();
            rptServicios.DataSource = dt;
            rptServicios.DataBind();

            // Actualizar contadores de ejemplo
            litRegistrosTotales.Text = "18"; // Valor fijo de Figma
            litRegistrosActuales.Text = "1-9"; // Valor fijo de Figma
        }

        // En Secretaria_Servicios.aspx.cs
        // En Secretaria_Servicios.aspx.cs
private DataTable ObtenerServiciosEjemplo()
{
    DataTable dt = new DataTable();
    dt.Columns.Add("ServicioID", typeof(int));
    dt.Columns.Add("IconoCss", typeof(string)); 
    dt.Columns.Add("IconoColorCss", typeof(string)); // *** NUEVA COLUMNA ***
    dt.Columns.Add("Nombre", typeof(string));
    dt.Columns.Add("Descripcion", typeof(string));
    dt.Columns.Add("PrecioFormateado", typeof(string));
    dt.Columns.Add("Estado", typeof(string)); 
    dt.Columns.Add("EstadoClaseCss", typeof(string)); 

    // Datos de ejemplo con IconoColorCss
    dt.Rows.Add(1, "fas fa-stethoscope", "icon-bg-consulta", "CONSULTA GENERAL", "Examen clínico completo...", "S/ 50.00", "Activo", "status-activo");
    dt.Rows.Add(2, "fas fa-x-ray", "icon-bg-radiografia", "RADIOGRAFÍA", "Estudio radiológico...", "S/ 120.00", "Activo", "status-activo");
    dt.Rows.Add(3, "fas fa-flask", "icon-bg-analisis", "ANÁLISIS DE SANGRE", "Hemograma completo...", "S/ 180.00", "Activo", "status-activo");
    dt.Rows.Add(4, "fas fa-tooth", "icon-bg-limpieza", "LIMPIEZA DENTAL", "Profilaxis dental completa...", "S/ 250.00", "Activo", "status-activo");
    dt.Rows.Add(5, "fas fa-cut", "icon-bg-cirugia", "CIRUGÍA MENOR", "Procedimientos quirúrgicos...", "S/ 350.00", "Activo", "status-activo");
    dt.Rows.Add(6, "fas fa-syringe", "icon-bg-vacunacion", "VACUNACIÓN", "Aplicación de vacunas...", "S/ 80.00", "Activo", "status-activo");
    dt.Rows.Add(7, "fas fa-bath", "icon-bg-bano", "BAÑO MEDICADO", "Baño terapéutico...", "S/ 60.00", "Inactivo", "status-inactivo"); 
            
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

        protected void btnNuevoServicio_Click(object sender, EventArgs e)
        {
            // Lógica para redirigir a "Nuevo Servicio"
            // Response.Redirect("Secretaria_NuevoServicio.aspx");
        }

        protected void rptServicios_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // Lógica para los botones de la tarjeta (Ver, Editar, Eliminar)
        }

        protected void rptFiltroTipo_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // Lógica para filtrar por tipo al hacer clic en las pestañas
        }
    }
}