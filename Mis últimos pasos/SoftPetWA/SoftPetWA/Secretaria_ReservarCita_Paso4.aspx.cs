using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Threading; // <--- IMPORTANTE: Necesario para el Thread.Sleep
using System.Web.UI;
using System.Web.UI.WebControls;

using SoftPetBusiness;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.DetalleServicioClient;
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.ServicioClient;
using SoftPetBussiness.VeterinarioClient;

using personaDto = SoftPetBussiness.PersonaClient.personaDto;
using mascotaDto = SoftPetBussiness.MascotaClient.mascotaDto;
using veterinarioDto = SoftPetBussiness.VeterinarioClient.veterinarioDto;
using servicioDto = SoftPetBussiness.ServicioClient.servicioDto;

namespace SoftPetWA
{
    public partial class Secretaria_ReservarCita_Paso4 : System.Web.UI.Page
    {
        private PersonaBO boPersona = new PersonaBO();
        private MascotaBO boMascota = new MascotaBO();
        private VeterinarioBO boVeterinario = new VeterinarioBO();
        private ServicioBO boServicio = new ServicioBO();
        private CitaAtencionBO boCitaAtencion = new CitaAtencionBO();
        private DetalleServicioBO boDetalleServicio = new DetalleServicioBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack) // Solo cargar si no es postback, el UpdatePanel maneja el resto
            {
                if (Session["FechaHoraInicio_Reserva"] == null ||
                    Session["VeterinarioID_Reserva"] == null ||
                    Session["ServiciosID_Reserva"] == null ||
                    Session["MascotaID_Reserva"] == null ||
                    Session["ClienteID_Reserva"] == null)
                {
                    Response.Redirect("Secretaria_ReservarCita_Paso1.aspx");
                    return;
                }

                CargarResumenConBOs();
            }
        }

        private void CargarResumenConBOs()
        {
            CultureInfo culture = new CultureInfo("es-PE");

            try
            {
                int clienteId = Convert.ToInt32(Session["ClienteID_Reserva"]);
                int mascotaId = Convert.ToInt32(Session["MascotaID_Reserva"]);
                int vetId = Convert.ToInt32(Session["VeterinarioID_Reserva"]);
                var serviciosIds = (List<int>)Session["ServiciosID_Reserva"];
                DateTime fechaHoraInicio = (DateTime)Session["FechaHoraInicio_Reserva"];

                // 1. Cliente
                personaDto cliente = boPersona.ObtenerPorId(clienteId);
                lblClienteSeleccionado.Text = (cliente != null) ? cliente.nombre : "N/A";

                // 2. Mascota
                mascotaDto mascota = boMascota.ObtenerPorId(mascotaId);
                lblMascotaSeleccionada.Text = (mascota != null) ? mascota.nombre : "N/A";

                // 3. Veterinario
                veterinarioDto vet = boVeterinario.ObtenerPorId(vetId);
                if (vet != null)
                {
                    personaDto personaVet = boPersona.ObtenerPorId(vet.persona.personaId);
                    lblVeterinarioSeleccionado.Text = (personaVet != null) ? personaVet.nombre : "N/A";
                }

                // 4. Fecha y Hora
                lblFechaSeleccionada.Text = fechaHoraInicio.ToString("dddd, dd 'de' MMMM yyyy", culture);
                lblHoraSeleccionada.Text = fechaHoraInicio.ToString("hh:mm tt", CultureInfo.InvariantCulture);

                // 5. Servicios y Total
                double montoTotal = 0.0;
                List<object> serviciosVM = new List<object>();

                foreach (int servicioId in serviciosIds)
                {
                    servicioDto srv = boServicio.ObtenerPorId(servicioId);
                    if (srv != null)
                    {
                        serviciosVM.Add(new
                        {
                            NombrePrecio = $"{srv.nombre} ({srv.costo.ToString("C", culture)})"
                        });
                        montoTotal += srv.costo;
                    }
                }

                rptServiciosSeleccionados.DataSource = serviciosVM;
                rptServiciosSeleccionados.DataBind();

                lblTotalAPagar.Text = montoTotal.ToString("C", culture);
                Session["MontoTotal_Reserva"] = montoTotal;
            }
            catch (Exception)
            {
                Response.Redirect("Secretaria_AgendaCitas.aspx");
            }
        }

        protected void btnConfirmarReserva_Click(object sender, EventArgs e)
        {
            // 1. RETRASO PARA EFECTO VISUAL DEL CARGANDO (Igual que en Cliente)
            Thread.Sleep(1500);

            try
            {
                int vetId = Convert.ToInt32(Session["VeterinarioID_Reserva"]);
                int mascotaId = Convert.ToInt32(Session["MascotaID_Reserva"]);
                var serviciosIds = (List<int>)Session["ServiciosID_Reserva"];
                DateTime fechaHoraInicio = (DateTime)Session["FechaHoraInicio_Reserva"];
                double montoTotal = Convert.ToDouble(Session["MontoTotal_Reserva"]);
                string notas = txtNotas.Text;

                DateTime fechaHoraFin = fechaHoraInicio.AddHours(1);
                string estadoCita = "PROGRAMADA";
                bool activo = true;

                string fechaHoraInicioStr = fechaHoraInicio.ToString("yyyy-MM-dd HH:mm:ss");
                string fechaHoraFinStr = fechaHoraFin.ToString("yyyy-MM-dd HH:mm:ss");

                // Insertar Cita
                int nuevaCitaId = boCitaAtencion.Insertar(
                    vetId, mascotaId, fechaHoraInicioStr, fechaHoraFinStr,
                    0.0, montoTotal, estadoCita, notas, activo
                );

                if (nuevaCitaId > 0)
                {
                    // Insertar Detalles
                    foreach (int servicioId in serviciosIds)
                    {
                        servicioDto srv = boServicio.ObtenerPorId(servicioId);
                        if (srv != null)
                        {
                            boDetalleServicio.Insertar(nuevaCitaId, servicioId, srv.descripcion, srv.costo, activo);
                        }
                    }

                    // Limpiar Sesión
                    Session.Remove("ClienteID_Reserva");
                    Session.Remove("MascotaID_Reserva");
                    Session.Remove("ServiciosID_Reserva");
                    Session.Remove("VeterinarioID_Reserva");
                    Session.Remove("Fecha_Reserva");
                    Session.Remove("Hora_Reserva");
                    Session.Remove("FechaHoraInicio_Reserva");
                    Session.Remove("MontoTotal_Reserva");

                    // MOSTRAR MODAL DE ÉXITO (Dentro del UpdatePanel funciona con ScriptManager)
                    string script = "$('#modalExito').modal('show');";
                    ScriptManager.RegisterStartupScript(this, GetType(), "mostrarModalExito", script, true);
                }
                else
                {
                    lblError.Text = "Error: No se pudo generar la cita.";
                }
            }
            catch (Exception ex)
            {
                lblError.Text = "Error al guardar: " + ex.Message;
            }
        }

        protected void btnAnterior_Click(object sender, EventArgs e)
        {
            Response.Redirect("Secretaria_ReservarCita_Paso3.aspx");
        }
    }
}