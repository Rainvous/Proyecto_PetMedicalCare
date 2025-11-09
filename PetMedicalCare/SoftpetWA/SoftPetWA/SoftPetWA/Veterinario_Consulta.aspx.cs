using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls; // Necesario para HtmlGenericControl
using System.Web.UI.WebControls;


namespace SoftPetWA
{
    // Asegúrate que el nombre coincida con Inherits
    public partial class Veterinario_Consulta : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Leemos el idCita o idMascota de la URL
                string idCita = Request.QueryString["idCita"];
                string idMascota = Request.QueryString["idMascota"]; // Podría venir de Agenda

                // Priorizamos idCita, si no, intentamos con idMascota (ajustar lógica según necesidad)
                string idParaCargar = !string.IsNullOrEmpty(idCita) ? idCita : idMascota;

                CargarConsulta(idParaCargar);
            }
        }

        private void CargarConsulta(string idParaCargar)
        {
            // --- VALORES POR DEFECTO ---
            string nombrePaciente = "No Seleccionado";
            string raza = "-";
            string propietario = "-";
            string edad = "-";
            string sexo = "-";
            string pesoAnt = "-";
            string contacto = "-";
            string fechaHoraCita = "-";
            string tipoCita = "-";
            string iconoAvatar = "fas fa-question-circle";
            string avatarCss = "avatar-lg-gray";

            // --- CARGAMOS DATOS SEGÚN idParaCargar ---
            // (Simulación - Aquí iría tu lógica de BD)
            if (idParaCargar == "C001" || idParaCargar == "M001") // MAX
            {
                nombrePaciente = "MAX"; raza = "Perro • Labrador Retriever"; propietario = "María González Pérez"; edad = "3 años, 5 meses"; sexo = "Macho"; pesoAnt = "28.5 kg"; contacto = "987 654 321"; fechaHoraCita = "Hoy, 15/09/2025 a las 08:00 AM"; tipoCita = "Consulta General"; iconoAvatar = "fas fa-dog"; avatarCss = "avatar-lg-blue";
            }
            else if (idParaCargar == "C002" || idParaCargar == "M002") // LUNA
            {
                nombrePaciente = "LUNA"; raza = "Gato • Persa"; propietario = "Carlos Rodríguez"; edad = "2 años • 4.2 kg"; sexo = "Hembra"; pesoAnt = "4.1 kg"; contacto = "123 456 789"; fechaHoraCita = "Hoy, 15/09/2025 a las 10:30 AM"; tipoCita = "Vacunación"; iconoAvatar = "fas fa-cat"; avatarCss = "avatar-lg-orange";
            }
            else if (idParaCargar == "C003" || idParaCargar == "M003") // ROCKY
            {
                nombrePaciente = "ROCKY"; raza = "Perro • Bulldog"; propietario = "Ana Martínez"; edad = "5 años • 28 kg"; sexo = "Macho"; pesoAnt = "27.8 kg"; contacto = "111 222 333"; fechaHoraCita = "Hoy, 15/09/2025 a las 12:00 PM"; tipoCita = "Control"; iconoAvatar = "fas fa-dog"; avatarCss = "avatar-lg-green";
            }

            // Asignamos los valores
            litNombrePacienteConsulta.Text = nombrePaciente;
            litRazaPacienteConsulta.Text = raza;
            litPropietarioConsulta.Text = propietario;
            litEdadConsulta.Text = edad;
            litSexoConsulta.Text = sexo;
            litPesoAntConsulta.Text = pesoAnt;
            litContactoConsulta.Text = contacto;
            litFechaHoraCitaConsulta.Text = fechaHoraCita;
            litTipoCitaConsulta.Text = tipoCita;
            litVetResumen.Text = "Dr. Rony"; // Debería venir de Sesión
            litFechaResumen.Text = DateTime.Now.ToString("dd/MM/yyyy hh:mm tt");
            litMontoServicios.Text = "S/ 0.00"; // Se calculará después
            litAvatarIconConsulta.Text = $"<i class='{iconoAvatar}'></i>";

            // Asigna la clase CSS al Div del avatar
            if (pacienteAvatarLgDiv != null)
            { // Acceso directo ya que está en esta página
                pacienteAvatarLgDiv.Attributes["class"] = $"paciente-avatar-lg {avatarCss}";
            }
        }

        // --- Eventos de Botones ---
        protected void btnVolverAgenda_Click(object sender, EventArgs e) { Response.Redirect("~/Veterinario_Agenda.aspx"); }
        protected void btnVerHistorialCompleto_Click(object sender, EventArgs e) { /* Redirigir a Historial */ }
        protected void btnGenerarReceta_Click(object sender, EventArgs e) { /* Lógica Receta */ }
        protected void btnCancelarConsulta_Click(object sender, EventArgs e) { Response.Redirect("~/Veterinario_Agenda.aspx"); }
        protected void btnGuardarBorrador_Click(object sender, EventArgs e) { /* Guardar Borrador */ }
        protected void btnCompletarConsulta_Click(object sender, EventArgs e) { /* Completar Consulta y redirigir */ Response.Redirect("~/Veterinario_Agenda.aspx"); }

    }
}