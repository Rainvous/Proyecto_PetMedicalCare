// Nombre del archivo: Cliente_ReservarCita_Paso2.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    // Nombre de la clase actualizado
    public partial class Cliente_ReservarCita_Paso2 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarServiciosDisponibles();
            }
        }

        private void CargarServiciosDisponibles()
        {
            DataTable dt = ObtenerServiciosEjemplo();
            rptServicios.DataSource = dt;
            rptServicios.DataBind();
        }

        private DataTable ObtenerServiciosEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("ServicioID", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("Descripcion", typeof(string));
            dt.Columns.Add("PrecioFormateado", typeof(string));

            // Datos de ejemplo basados en Figma
            dt.Rows.Add(1, "Consulta General", "Examen físico completo y diagnóstico", "S/ 80.00");
            dt.Rows.Add(2, "Vacunación", "Aplicación de vacunas", "S/ 50.00");
            dt.Rows.Add(3, "Desparasitación", "Interna y externa", "S/ 35.00");
            dt.Rows.Add(4, "Análisis de Sangre", "Hemograma completo", "S/ 180.00");
            dt.Rows.Add(5, "Limpieza Dental", "Profilaxis y pulido", "S/ 250.00");
            dt.Rows.Add(6, "Radiografía", "Estudio radiológico", "S/ 120.00");

            return dt;
        }

        protected void rptServicios_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // Lógica para manejar la selección de servicio(s) iría aquí
        }

        protected void btnSiguiente_Click(object sender, EventArgs e)
        {
            // Redirigir al siguiente paso
            Response.Redirect("Cliente_ReservarCita_Paso3.aspx");
        }

        protected void btnAnterior_Click(object sender, EventArgs e)
        {
            // Volver al paso anterior
            Response.Redirect("Cliente_ReservarCita_Paso1.aspx");
        }
    }
}