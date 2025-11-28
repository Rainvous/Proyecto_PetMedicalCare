// Nombre del archivo: Secretaria_ReservarCita_Paso1.aspx.cs (CORREGIDO)
using System;
using System.Linq;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.MascotaClient;
using System.Collections.Generic;
using System.Web.UI;
using personaDto = SoftPetBussiness.PersonaClient.personaDto; // <-- (AÑADIDO) Necesario para DataBinder

// (He borrado el using duplicado de personaDto)

namespace SoftPetWA
{
    public partial class Secretaria_ReservarCita_Paso1 : System.Web.UI.Page
    {
        private PersonaBO boPersona = new PersonaBO();
        private MascotaBO boMascota = new MascotaBO();

        // *** (MÉTODO MODIFICADO) ***
        protected void Page_Load(object sender, EventArgs e)
        {
            // 1. Validar el ClienteID (se mantiene igual)
            if (Session["ClienteID_Reserva"] == null)
            {
                Response.Redirect("Secretaria_AgendaCitas.aspx");
                return;
            }

            int clienteId = Convert.ToInt32(Session["ClienteID_Reserva"]);

            if (!IsPostBack)
            {
                // 2. (MODIFICADO) Limpiar la selección SÓLO en la carga inicial
                Session.Remove("MascotaID_Reserva");
            }

            // 3. (MOVIDO FUERA) Cargar datos en CADA carga de página (PostBack o no)
            // Esto soluciona el bug de que las mascotas "desaparecen".
            CargarNombreCliente(clienteId);
            CargarMascotas(clienteId);
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
            }
        }

        // *** (ACTUALIZADO) Carga las mascotas usando MascotaBO ***
        private void CargarMascotas(int clienteId)
        {
            try
            {
                List<mascotaDto> mascotasCliente = boMascota.ListarPorIdPersona(clienteId);

                var mascotasVM = mascotasCliente.Select(m => new
                {
                    MascotaID = m.mascotaId,
                    Nombre = m.nombre,
                    EspecieRaza = $"{m.especie} - {m.raza}",

                    // (CORREGIDO) Usando tus rutas de /Images/Avatars/
                    AvatarURL = (m.especie != null && (m.especie.ToLower().Contains("canino") || m.especie.ToLower().Contains("perro")))
                                ? "~/Images/Avatars/dog-avatar.png"
                                : "~/Images/Avatars/cat-avatar.png"
                }).ToList();

                rptMascotas.DataSource = mascotasVM;
                rptMascotas.DataBind();
            }
            catch (Exception ex)
            {
                rptMascotas.DataSource = null;
                rptMascotas.DataBind();
            }
        }

        // *** (MÉTODO MODIFICADO) ***
        protected void rptMascotas_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "SelectPet")
            {
                // 1. Guardar la mascota seleccionada en la Sesión
                Session["MascotaID_Reserva"] = Convert.ToInt32(e.CommandArgument);

                // 2. (NUEVO) Volver a cargar las mascotas INMEDIATAMENTE
                // Esto fuerza al Repeater a re-renderizarse con el nuevo
                // valor de Session, permitiendo que ItemDataBound aplique la clase 'selected'.
                int clienteId = Convert.ToInt32(Session["ClienteID_Reserva"]);
                CargarMascotas(clienteId);
            }
        }

        // *** (NUEVO MÉTODO) ***
        // Este evento se dispara por CADA item en el Repeater durante el DataBind()
        protected void rptMascotas_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            // Solo nos interesan los items de datos
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                // 1. Verificar si tenemos una mascota seleccionada en la sesión
                if (Session["MascotaID_Reserva"] != null)
                {
                    int mascotaSeleccionadaID = Convert.ToInt32(Session["MascotaID_Reserva"]);

                    // 2. Obtener el ID de la mascota de ESTE item
                    // Usamos DataBinder.Eval porque el 'e.Item.DataItem' es un objeto anónimo
                    int mascotaItemID = Convert.ToInt32(DataBinder.Eval(e.Item.DataItem, "MascotaID"));

                    // 3. Comparar
                    if (mascotaItemID == mascotaSeleccionadaID)
                    {
                        // 4. Si coinciden, encontrar el LinkButton y añadirle la clase 'selected'
                        LinkButton btnSelectPet = e.Item.FindControl("btnSelectPet") as LinkButton;
                        if (btnSelectPet != null)
                        {
                            btnSelectPet.CssClass += " selected";
                        }
                    }
                }
            }
        }


        // (Este método no cambia)
        protected void btnSiguiente_Click(object sender, EventArgs e)
        {
            if (Session["MascotaID_Reserva"] != null)
            {
                Response.Redirect("Secretaria_ReservarCita_Paso2.aspx");
            }
            else
            {
                // (Opcional: Mostrar un error si no se seleccionó mascota)
            }
        }

        // (Este método no cambia)
        protected void btnCancelar_Click(object sender, EventArgs e)
        {
            Session.Remove("ClienteID_Reserva");
            Session.Remove("MascotaID_Reserva");
            Response.Redirect("Secretaria_AgendaCitas.aspx");
        }
    }
}