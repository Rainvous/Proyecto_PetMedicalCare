using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.PersonaClient;
using System.Data;

namespace SoftPetWA
{
    public partial class Cliente_Mascotas : System.Web.UI.Page
    {
        private MascotaBO boMascota;
        private PersonaBO boPersona;

        protected void Page_Init(object sender, EventArgs e)
        {
            boMascota = new MascotaBO();
            boPersona = new PersonaBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["UsuarioId"] == null)
            {
                Response.Redirect("Login.aspx");
                return;
            }

            if (Session["PersonaId"] == null)
            {
                try
                {
                    int uid = Convert.ToInt32(Session["UsuarioId"]);
                    var todas = boPersona.ListarBusquedaAvanzada("", "", "", -1);
                    var p = todas.FirstOrDefault(x => x.usuario != null && x.usuario.usuarioId == uid);
                    if (p != null) Session["PersonaId"] = p.personaId;
                }
                catch { }
            }

            if (!IsPostBack)
            {
                CargarCombos();
                CargarMascotasCliente();
            }
        }

        private void CargarCombos()
        {
            ddlModalEspecie.Items.Clear();
            ddlModalEspecie.Items.Add(new ListItem("Perro", "Perro"));
            ddlModalEspecie.Items.Add(new ListItem("Gato", "Gato"));
            ddlModalEspecie.Items.Add(new ListItem("Otro", "Otro"));
        }

        private void CargarMascotasCliente()
        {
            try
            {
                if (Session["PersonaId"] != null)
                {
                    int clienteId = Convert.ToInt32(Session["PersonaId"]);
                    var mascotas = boMascota.ListarPorIdPersona(clienteId);

                    var mascotasVM = mascotas.Select(m => new
                    {
                        MascotaID = m.mascotaId,
                        Nombre = m.nombre,
                        Especie = m.especie,
                        Raza = m.raza,
                        EspecieRaza = $"{m.especie} - {m.raza}",
                        Color = m.color,
                        Sexo = m.sexo,
                        SexoIcon = m.sexo == "M" ? "fa-mars" : "fa-venus",
                        SexoTexto = m.sexo == "M" ? "Macho" : "Hembra",
                        AvatarURL = ObtenerAvatar(m.especie)
                    }).ToList();

                    rptMascotasCliente.DataSource = mascotasVM;
                    rptMascotasCliente.DataBind();
                }
            }
            catch (Exception)
            {
                rptMascotasCliente.DataSource = null;
                rptMascotasCliente.DataBind();
            }
        }

        private string ObtenerAvatar(string especie)
        {
            if (string.IsNullOrEmpty(especie)) return "";
            especie = especie.ToLower();
            if (especie.Contains("perro") || especie.Contains("canino")) return "~/Images/Avatars/dog-avatar.png";
            if (especie.Contains("gato") || especie.Contains("felino")) return "~/Images/Avatars/cat-avatar.png";
            return "";
        }

        // --- NUEVA MASCOTA ---
        protected void btnNuevaMascota_Click(object sender, EventArgs e)
        {
            int clienteId = Convert.ToInt32(Session["PersonaId"] ?? 0);
            if (clienteId == 0) return;

            // 1. Validar Límite
            var misMascotas = boMascota.ListarPorIdPersona(clienteId);
            int cantidadActivas = misMascotas.Count(m => m.activo);

            if (cantidadActivas >= 10)
            {
                ScriptManager.RegisterStartupScript(this, GetType(), "AlertLimit",
                    "Swal.fire({icon: 'info', title: 'Límite Alcanzado', text: 'Ya tienes 10 mascotas registradas. Acércate a la veterinaria para más registros.', confirmButtonColor: '#0097a7'});", true);
                return;
            }

            // 2. Limpieza Server-Side
            hdMascotaID.Value = "0";
            txtModalNombre.Text = "";
            ddlModalEspecie.SelectedIndex = 0;
            txtModalRaza.Text = "";
            ddlModalSexo.SelectedValue = "M";
            txtModalColor.Text = "";

            // 3. Desbloqueo Server-Side
            ddlModalEspecie.Enabled = true;
            ddlModalSexo.Enabled = true;
            txtModalNombre.ReadOnly = false;
            txtModalRaza.ReadOnly = false;
            txtModalColor.ReadOnly = false;

            // 4. Actualizar Panel del Modal
            updModalMascota.Update();

            // 5. Llamar script que FUERZA la limpieza visual y abre el modal
            ScriptManager.RegisterStartupScript(this, GetType(), "OpenModalNew",
                "prepararModalNuevo(); $('#modalMascotaLabel').text('Registrar Nueva Mascota'); new bootstrap.Modal(document.getElementById('modalMascota')).show();", true);
        }

        // --- GUARDAR ---
        protected void btnGuardarMascota_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid) return;

            try
            {
                int clienteId = Convert.ToInt32(Session["PersonaId"] ?? 0);
                int mascotaId = Convert.ToInt32(hdMascotaID.Value);

                string nombre = txtModalNombre.Text;
                string raza = txtModalRaza.Text;
                string color = txtModalColor.Text;

                string especie = ddlModalEspecie.SelectedValue;
                string sexo = ddlModalSexo.SelectedValue;

                int res = 0;
                if (mascotaId == 0)
                {
                    // Insertar
                    res = boMascota.Insertar(clienteId, nombre, especie, sexo, raza, color, null, true);
                }
                else
                {
                    // Modificar: Recuperar original para no perder datos bloqueados si fuera necesario
                    var original = boMascota.ObtenerPorId(mascotaId);
                    if (original != null)
                    {
                        res = boMascota.Modificar(mascotaId, clienteId, nombre, original.especie, original.sexo, raza, color, null, true);
                    }
                }

                if (res > 0)
                {
                    // IMPORTANTE: Recargar la lista y actualizar SU panel
                    CargarMascotasCliente();
                    updPanelMascotas.Update(); // <-- ESTO ACTUALIZA LA LISTA EN PANTALLA

                    ScriptManager.RegisterStartupScript(this, GetType(), "Exito", "mostrarExitoLocal('Mascota guardada exitosamente.');", true);
                }
                else
                {
                    litMensaje.Text = "<div class='alert alert-danger'>No se pudo guardar.</div>";
                    updModalMascota.Update();
                }
            }
            catch (Exception ex)
            {
                litMensaje.Text = $"<div class='alert alert-danger'>Error: {ex.Message}</div>";
                updModalMascota.Update();
            }
        }

        protected void rptMascotasCliente_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            string arg = e.CommandArgument.ToString();
            if (e.CommandName == "VerHistorial")
            {
                Response.Redirect($"Cliente_HistorialMedico.aspx?mascotaId={arg}");
            }
        }
    }
}