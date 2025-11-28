using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.ServicioClient;

namespace SoftPetWA
{
    public partial class Cliente_ReservarCita_Paso2 : System.Web.UI.Page
    {
        private ServicioBO boServicio = new ServicioBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Validar que venimos del Paso 1 (Mascota seleccionada)
                if (Session["MascotaID_Reserva"] == null)
                {
                    Response.Redirect("Cliente_ReservarCita_Paso1.aspx");
                    return;
                }

                CargarServiciosDisponibles();
            }
        }

        private void CargarServiciosDisponibles()
        {
            try
            {
                List<servicioDto> servicios = boServicio.listarServicioActivos();

                var serviciosVM = servicios.Select(s => new
                {
                    ServicioID = s.servicioId,
                    Nombre = s.nombre,
                    Descripcion = s.descripcion,
                    PrecioFormateado = s.costo.ToString("C", CultureInfo.CreateSpecificCulture("es-PE"))
                }).ToList();

                rptServicios.DataSource = serviciosVM;
                rptServicios.DataBind();
            }
            catch (Exception ex)
            {
                // Manejo de errores
            }
        }

        protected void btnSiguiente_Click(object sender, EventArgs e)
        {
            List<int> seleccionados = new List<int>();

            foreach (RepeaterItem item in rptServicios.Items)
            {
                CheckBox chk = (CheckBox)item.FindControl("chkSelectService");
                HiddenField hd = (HiddenField)item.FindControl("hdServicioID");

                if (chk != null && chk.Checked && hd != null)
                {
                    seleccionados.Add(Convert.ToInt32(hd.Value));
                }
            }

            if (seleccionados.Count > 0)
            {
                // Guardar servicios seleccionados y avanzar
                Session["ServiciosID_Reserva"] = seleccionados;
                Response.Redirect("Cliente_ReservarCita_Paso3.aspx");
            }
            else
            {
                // Podrías mostrar un mensaje de error aquí
            }
        }

        protected void btnAnterior_Click(object sender, EventArgs e)
        {
            Response.Redirect("Cliente_ReservarCita_Paso1.aspx");
        }
    }
}