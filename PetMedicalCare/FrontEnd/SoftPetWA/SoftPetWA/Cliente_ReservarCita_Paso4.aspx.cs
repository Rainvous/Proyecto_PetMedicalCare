using System;
using System.Collections.Generic;
using System.Globalization;
using System.Threading; // <--- IMPORTANTE para el tiempo de espera
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.DetalleServicioClient;
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.ServicioClient;
using SoftPetBussiness.VeterinarioClient;

// Alias para DTOs
using personaDto = SoftPetBussiness.PersonaClient.personaDto;
using mascotaDto = SoftPetBussiness.MascotaClient.mascotaDto;
using veterinarioDto = SoftPetBussiness.VeterinarioClient.veterinarioDto;
using servicioDto = SoftPetBussiness.ServicioClient.servicioDto;

namespace SoftPetWA
{
    public partial class Cliente_ReservarCita_Paso4 : System.Web.UI.Page
    {
        private PersonaBO boPersona = new PersonaBO();
        private MascotaBO boMascota = new MascotaBO();
        private VeterinarioBO boVeterinario = new VeterinarioBO();
        private ServicioBO boServicio = new ServicioBO();
        private CitaAtencionBO boCitaAtencion = new CitaAtencionBO();
        private DetalleServicioBO boDetalleServicio = new DetalleServicioBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Validar sesión completa
                if (Session["FechaHoraInicio_Reserva"] == null ||
                    Session["VeterinarioID_Reserva"] == null ||
                    Session["ServiciosID_Reserva"] == null ||
                    Session["MascotaID_Reserva"] == null ||
                    Session["ClienteID_Reserva"] == null)
                {
                    Response.Redirect("Cliente_ReservarCita_Paso1.aspx");
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

                mascotaDto mascota = boMascota.ObtenerPorId(mascotaId);
                lblMascotaSeleccionada.Text = (mascota != null) ? mascota.nombre : "N/A";

                veterinarioDto vet = boVeterinario.ObtenerPorId(vetId);
                if (vet != null)
                {
                    personaDto personaVet = boPersona.ObtenerPorId(vet.persona.personaId);
                    lblVeterinarioSeleccionado.Text = (personaVet != null) ? personaVet.nombre : "N/A";
                }

                lblFechaSeleccionada.Text = fechaHoraInicio.ToString("dddd, dd 'de' MMMM yyyy", culture);
                lblHoraSeleccionada.Text = fechaHoraInicio.ToString("hh:mm tt", CultureInfo.InvariantCulture);

                double montoTotal = 0.0;
                List<object> serviciosVM = new List<object>();
                foreach (int servicioId in serviciosIds)
                {
                    servicioDto srv = boServicio.ObtenerPorId(servicioId);
                    if (srv != null)
                    {
                        serviciosVM.Add(new { NombrePrecio = $"{srv.nombre} ({srv.costo.ToString("C", culture)})" });
                        montoTotal += srv.costo;
                    }
                }
                rptServiciosSeleccionados.DataSource = serviciosVM;
                rptServiciosSeleccionados.DataBind();
                lblTotalAPagar.Text = montoTotal.ToString("C", culture);

                // Guardar total para el evento clic
                Session["MontoTotal_Reserva"] = montoTotal;
            }
            catch (Exception) { Response.Redirect("Cliente_AgendaCitas.aspx"); }
        }

        protected void btnConfirmarReserva_Click(object sender, EventArgs e)
        {
            // 1. RETRASO ARTIFICIAL: Para efecto visual del spinner
            Thread.Sleep(1500);

            try
            {
                // Recuperar datos
                int vetId = Convert.ToInt32(Session["VeterinarioID_Reserva"]);
                int mascotaId = Convert.ToInt32(Session["MascotaID_Reserva"]);
                var serviciosIds = (List<int>)Session["ServiciosID_Reserva"];
                DateTime fechaHoraInicio = (DateTime)Session["FechaHoraInicio_Reserva"];
                double montoTotal = Convert.ToDouble(Session["MontoTotal_Reserva"]);
                string notas = txtNotas.Text;

                // Calcular fin (1 hora de duración por defecto)
                DateTime fechaHoraFin = fechaHoraInicio.AddHours(1);

                // ============================================================
                // CORRECCIÓN AQUÍ: Usar formato estándar con guiones y dos puntos
                // ============================================================
                string fechaInicioStr = fechaHoraInicio.ToString("yyyy-MM-dd HH:mm:ss");
                string fechaFinStr = fechaHoraFin.ToString("yyyy-MM-dd HH:mm:ss");

                // Guardar Cita Principal
                int nuevaCitaId = boCitaAtencion.Insertar(
                    vetId, mascotaId, fechaInicioStr, fechaFinStr,
                    0.0, montoTotal, "PROGRAMADA", notas, true
                );

                if (nuevaCitaId > 0)
                {
                    // Guardar Detalles de Servicios
                    foreach (int servicioId in serviciosIds)
                    {
                        servicioDto srv = boServicio.ObtenerPorId(servicioId);
                        if (srv != null)
                        {
                            boDetalleServicio.Insertar(nuevaCitaId, servicioId, srv.descripcion, srv.costo, true);
                        }
                    }

                    // Limpiar la sesión
                    LimpiarSesionReserva();

                    // 2. MOSTRAR MODAL DE ÉXITO
                    string script = "$('#modalExito').modal('show');";
                    ScriptManager.RegisterStartupScript(this, GetType(), "ShowSuccessModal", script, true);
                }
                else
                {
                    lblError.Text = "No se pudo guardar la cita. Inténtelo de nuevo.";
                }
            }
            catch (Exception ex)
            {
                // Muestra el error real si vuelve a fallar
                lblError.Text = "Error técnico: " + ex.Message;
            }
        }

        private void LimpiarSesionReserva()
        {
            Session.Remove("ClienteID_Reserva");
            Session.Remove("MascotaID_Reserva");
            Session.Remove("ServiciosID_Reserva");
            Session.Remove("VeterinarioID_Reserva");
            Session.Remove("Fecha_Reserva");
            Session.Remove("Hora_Reserva");
            Session.Remove("FechaHoraInicio_Reserva");
            Session.Remove("MontoTotal_Reserva");
        }

        protected void btnAnterior_Click(object sender, EventArgs e)
        {
            Response.Redirect("Cliente_ReservarCita_Paso3.aspx");
        }
    }
}