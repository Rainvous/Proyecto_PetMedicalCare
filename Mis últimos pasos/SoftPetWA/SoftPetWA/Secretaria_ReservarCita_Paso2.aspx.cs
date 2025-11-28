// Nombre del archivo: Secretaria_ReservarCita_Paso2.aspx.cs (CONECTADO AL BO)
using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization; // (NUEVO) Para formatear el precio
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;

// (NUEVO) Imports para los BO y DTOs
using SoftPetBusiness;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.ServicioClient; // El BO de Servicios

namespace SoftPetWA
{
    public partial class Secretaria_ReservarCita_Paso2 : System.Web.UI.Page
    {
        // (NUEVO) Instancias de los Business Objects
        private PersonaBO boPersona = new PersonaBO();
        private ServicioBO boServicio = new ServicioBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // 1. Validar que venimos del paso 1 (se mantiene igual)
                if (Session["MascotaID_Reserva"] == null || Session["ClienteID_Reserva"] == null)
                {
                    Response.Redirect("Secretaria_ReservarCita_Paso1.aspx");
                    return;
                }

                // 2. (ACTUALIZADO) Cargar nombre del cliente desde el BO
                CargarNombreCliente(Convert.ToInt32(Session["ClienteID_Reserva"]));

                // 3. (ACTUALIZADO) Cargar servicios desde el BO
                CargarServicios();

                // 4. Marcar servicios si ya estaban seleccionados (se mantiene igual)
                MarcarServiciosPrevios();
            }
        }

        // *** (ACTUALIZADO) Carga el nombre usando PersonaBO ***
        private void CargarNombreCliente(int clienteId)
        {
            try
            {
                personaDto cliente = boPersona.ObtenerPorId(clienteId);
                if (cliente != null)
                {
                    lblClienteNombre.Text = cliente.nombre;
                }
                else
                {
                    lblClienteNombre.Text = "Cliente no encontrado";
                }
            }
            catch (Exception ex)
            {
                lblClienteNombre.Text = "Error al cargar cliente";
                // (Opcional: registrar ex.Message)
            }
        }

        // *** (ACTUALIZADO) Carga los servicios usando ServicioBO ***
        private void CargarServicios()
        {
            try
            {
                // 1. Obtener DTOs reales desde el BO
                List<servicioDto> listaServicios = boServicio.listarServicioActivos();

                // 2. Proyectar a un objeto anónimo que coincida con el ASPX
                // El ASPX espera: "ServicioID", "Nombre", "Descripcion", "PrecioFormateado"
                var serviciosVM = listaServicios.Select(s => new {
                    ServicioID = s.servicioId,
                    Nombre = s.nombre,
                    Descripcion = s.descripcion,
                    // Formateamos el 'costo' (double) a moneda local (Soles)
                    PrecioFormateado = s.costo.ToString("C", CultureInfo.CreateSpecificCulture("es-PE"))
                }).ToList();

                rptServicios.DataSource = serviciosVM;
                rptServicios.DataBind();
            }
            catch (Exception ex)
            {
                // Manejar error de conexión SOAP
                rptServicios.DataSource = null;
                rptServicios.DataBind();
                // (Opcional: Mostrar error en un label)
            }
        }

        // (Este método se mantiene igual, ya funciona correctamente)
        private void MarcarServiciosPrevios()
        {
            if (Session["ServiciosID_Reserva"] != null)
            {
                var seleccionados = (List<int>)Session["ServiciosID_Reserva"];
                foreach (RepeaterItem item in rptServicios.Items)
                {
                    HiddenField hd = (HiddenField)item.FindControl("hdServicioID");
                    int servicioId = Convert.ToInt32(hd.Value);

                    if (seleccionados.Contains(servicioId))
                    {
                        CheckBox chk = (CheckBox)item.FindControl("chkSelectService");
                        chk.Checked = true;

                        // (NUEVO) Registrar script para que la UI se marque al cargar
                        // (El script de JQuery en el ASPX solo funciona al hacer clic)
                        string script = $"$('#{chk.ClientID}').closest('.service-selection-card').addClass('selected');";
                        ScriptManager.RegisterStartupScript(this, GetType(), "marcar_" + servicioId, script, true);
                    }
                }
            }
        }

        // (Este método se mantiene igual, ya funciona correctamente)
        protected void btnSiguiente_Click(object sender, EventArgs e)
        {
            List<int> serviciosSeleccionados = new List<int>();

            foreach (RepeaterItem item in rptServicios.Items)
            {
                CheckBox chk = (CheckBox)item.FindControl("chkSelectService");

                if (chk != null && chk.Checked)
                {
                    HiddenField hd = (HiddenField)item.FindControl("hdServicioID");
                    int servicioId = Convert.ToInt32(hd.Value);
                    serviciosSeleccionados.Add(servicioId);
                }
            }

            if (serviciosSeleccionados.Count > 0)
            {
                Session["ServiciosID_Reserva"] = serviciosSeleccionados;
                Response.Redirect("Secretaria_ReservarCita_Paso3.aspx");
            }
            else
            {
                // (Opcional: Mostrar un error si no se seleccionó servicio)
            }
        }

        // (Este método se mantiene igual, ya funciona correctamente)
        protected void btnAnterior_Click(object sender, EventArgs e)
        {
            Response.Redirect("Secretaria_ReservarCita_Paso1.aspx");
        }
    }
}