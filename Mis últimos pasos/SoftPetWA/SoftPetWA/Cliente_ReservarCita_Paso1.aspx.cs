using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.MascotaClient;

namespace SoftPetWA
{
    public partial class Cliente_ReservarCita_Paso1 : System.Web.UI.Page
    {
        private MascotaBO boMascota = new MascotaBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            // Validar si hay sesión de usuario (Persona)
            if (Session["PersonaId"] == null)
            {
                Response.Redirect("Login.aspx");
                return;
            }

            if (!IsPostBack)
            {
                CargarMascotasCliente();
            }
        }

        private void CargarMascotasCliente()
        {
            try
            {
                int clienteId = Convert.ToInt32(Session["PersonaId"]);

                // Llama al servicio SOAP real
                List<mascotaDto> mascotas = boMascota.ListarPorIdPersona(clienteId);

                var mascotasVM = mascotas.Select(m => new
                {
                    MascotaID = m.mascotaId,
                    Nombre = m.nombre,
                    EspecieRaza = $"{m.especie} - {m.raza}",
                    AvatarURL = (m.especie != null && (m.especie.ToLower().Contains("perro") || m.especie.ToLower().Contains("canino")))
                                ? "~/Images/Avatars/dog-avatar.png"
                                : "~/Images/Avatars/cat-avatar.png"
                }).ToList();

                rptMascotas.DataSource = mascotasVM;
                rptMascotas.DataBind();
            }
            catch (Exception ex)
            {
                // Manejo básico de error
                rptMascotas.DataSource = null;
                rptMascotas.DataBind();
            }
        }

        protected void rptMascotas_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "SelectPet")
            {
                // Guardar la selección en Sesión
                Session["MascotaID_Reserva"] = Convert.ToInt32(e.CommandArgument);

                // Ir automáticamente al Paso 2
                Response.Redirect("Cliente_ReservarCita_Paso2.aspx");
            }
        }

        protected void btnSiguiente_Click(object sender, EventArgs e)
        {
            if (Session["MascotaID_Reserva"] != null)
            {
                Response.Redirect("Cliente_ReservarCita_Paso2.aspx");
            }
        }

        protected void btnCancelar_Click(object sender, EventArgs e)
        {
            Response.Redirect("Cliente_AgendaCitas.aspx");
        }
    }
}