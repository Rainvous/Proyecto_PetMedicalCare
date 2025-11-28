using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Threading;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.VeterinarioClient;
using SoftPetBussiness.RecetaMedicaClient;
using SoftPetBussiness.DetalleRecetaClient;
using SoftPetBussiness.ServicioClient;
using SoftPetBussiness.DetalleServicioClient;
using SoftPetBussiness.RolUsuarioClient;

using mascotaDto = SoftPetBussiness.MascotaClient.mascotaDto;
using personaDto = SoftPetBussiness.PersonaClient.personaDto;
using citaAtencionDto = SoftPetBussiness.CitaAtencionClient.citaAtencionDto;
using veterinarioDto = SoftPetBussiness.VeterinarioClient.veterinarioDto;
using servicioDto = SoftPetBussiness.ServicioClient.servicioDto;
using detalleServicioDto = SoftPetBussiness.DetalleServicioClient.detalleServicioDto;
using rolUsuarioDto = SoftPetBussiness.RolUsuarioClient.rolUsuarioDto;

namespace SoftPetWA
{
    public partial class Veterinario_Consulta : System.Web.UI.Page
    {
        private MascotaBO boMascota = new MascotaBO();
        private PersonaBO boPersona = new PersonaBO();
        private CitaAtencionBO boCita = new CitaAtencionBO();
        private RecetaMedicaBO boReceta = new RecetaMedicaBO();
        private DetalleRecetaBO boDetalleReceta = new DetalleRecetaBO();
        private ServicioBO boServicio = new ServicioBO();
        private DetalleServicioBO boDetalleServicio = new DetalleServicioBO();
        private RolUsuarioBO boRolUsuario = new RolUsuarioBO();

        private int CitaIdActual
        {
            get { return ViewState["CitaIdActual"] != null ? (int)ViewState["CitaIdActual"] : 0; }
            set { ViewState["CitaIdActual"] = value; }
        }

        [Serializable]
        public class ItemReceta
        {
            public string Descripcion { get; set; }
            public string Dosis { get; set; }
            public string Frecuencia { get; set; }
            public string Duracion { get; set; }
            public string Indicacion { get; set; }
            public string Cantidad { get; set; }
        }
        private List<ItemReceta> ListaMedicamentos
        {
            get { return ViewState["ListaMed"] != null ? (List<ItemReceta>)ViewState["ListaMed"] : new List<ItemReceta>(); }
            set { ViewState["ListaMed"] = value; }
        }

        // ===============================================
        //[cite_start]// CLASE DE SERVICIO CON PROPIEDAD "EsNuevo" [cite: 120]
        // ===============================================
        [Serializable]
        public class ItemServicioSeleccionado
        {
            public int ServicioID { get; set; }
            public string Nombre { get; set; }
            public double Precio { get; set; }
            public string PrecioFormateado { get; set; }
            public bool EsNuevo { get; set; } // Indica si se agregó ahora o viene de BD
        }

        private List<ItemServicioSeleccionado> ListaServicios
        {
            get { return ViewState["ListaServ"] != null ? (List<ItemServicioSeleccionado>)ViewState["ListaServ"] : new List<ItemServicioSeleccionado>(); }
            set { ViewState["ListaServ"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                string idStr = Request.QueryString["idCita"];
                if (int.TryParse(idStr, out int id) && id > 0)
                {
                    CitaIdActual = id;
                    CargarDatosPorCita(id);
                    CargarServiciosIniciales(id);
                }
                else
                {
                    Response.Redirect("Veterinario_Agenda.aspx");
                }

                if (Session["UsuarioId"] != null)
                {
                    try
                    {
                        int uid = Convert.ToInt32(Session["UsuarioId"]);
                        var persona = boPersona.ListarPersonasActivas().FirstOrDefault(p => p.usuario != null && p.usuario.usuarioId == uid);
                        if (persona != null) litVetResumen.Text = "Dr. " + persona.nombre;
                        else litVetResumen.Text = Session["UsuarioNombre"] != null ? "Dr. " + Session["UsuarioNombre"] : "Dr. Veterinario";
                    }
                    catch { }
                }
            }
        }

        private void CargarDatosPorCita(int idCita)
        {
            try
            {
                citaAtencionDto cita = boCita.ObtenerPorId(idCita);
                if (cita == null) return;
                int idMascota = cita.mascota.mascotaId;

                mascotaDto mascota = boMascota.ObtenerPorId(idMascota);
                if (mascota != null)
                {
                    litNombrePacienteConsulta.Text = mascota.nombre;
                    litRazaPacienteConsulta.Text = $"{mascota.especie} • {mascota.raza}";

                    string icono = "fas fa-paw";
                    string colorAvatar = "";
                    if (mascota.especie != null)
                    {
                        if (mascota.especie.ToLower().Contains("perro")) { icono = "fas fa-dog"; colorAvatar = "dog"; }
                        else if (mascota.especie.ToLower().Contains("gato")) { icono = "fas fa-cat"; colorAvatar = "cat"; }
                    }
                    litAvatarIconConsulta.Text = $"<i class='{icono}'></i>";
                    if (pacienteAvatarLgDiv != null) pacienteAvatarLgDiv.Attributes["class"] = $"patient-avatar-container {colorAvatar}";

                    litSexoConsulta.Text = (!string.IsNullOrEmpty(mascota.sexo) && mascota.sexo == "M") ? "Macho" : ((mascota.sexo == "H" || mascota.sexo == "F") ? "Hembra" : "-");

                    if (mascota.persona != null)
                    {
                        var duenio = boPersona.ObtenerPorId(mascota.persona.personaId);
                        if (duenio != null) { litPropietarioConsulta.Text = duenio.nombre; litContactoConsulta.Text = duenio.telefono; }
                    }
                }

                // Fecha Formateada
                try
                {
                    DateTime fechaCita;
                    string fechaStr = cita.fechaHoraInicioStr;
                    if (string.IsNullOrEmpty(fechaStr) && cita.fechaHoraInicio != null)
                    {
                        dynamic ts = cita.fechaHoraInicio;
                        fechaCita = new DateTime(1970, 1, 1).AddMilliseconds((long)ts.time).ToLocalTime();
                    }
                    else
                    {
                        DateTime.TryParse(fechaStr, out fechaCita);
                    }

                    if (fechaCita != DateTime.MinValue)
                    {
                        string fechaTexto = fechaCita.ToString("dddd dd 'de' MMMM 'del' yyyy", new CultureInfo("es-ES"));
                        fechaTexto = char.ToUpper(fechaTexto[0]) + fechaTexto.Substring(1);
                        litFechaHoraCitaConsulta.Text = " " + fechaTexto;
                    }
                    else litFechaHoraCitaConsulta.Text = "-";
                }
                catch { litFechaHoraCitaConsulta.Text = "-"; }

                string obsCompleta = cita.observacion;
                if (!string.IsNullOrEmpty(obsCompleta))
                {
                    if (obsCompleta.Contains("[DIAGNÓSTICO]:"))
                    {
                        string[] partes = obsCompleta.Split(new string[] { " | " }, StringSplitOptions.None);
                        foreach (string p in partes)
                        {
                            if (p.StartsWith("[DIAGNÓSTICO]:")) txtDiagnosticoClinico.Text = p.Replace("[DIAGNÓSTICO]:", "").Trim();
                            else if (p.StartsWith("[OBSERVACIONES]:")) txtObservacionesClinicas.Text = p.Replace("[OBSERVACIONES]:", "").Trim();
                            else if (p.StartsWith("[TRATAMIENTO]:")) txtPlanTratamiento.Text = p.Replace("[TRATAMIENTO]:", "").Trim();
                        }
                    }
                    else { txtObservacionesClinicas.Text = obsCompleta; }
                }
            }
            catch { }
        }

        // ================================================
        // CARGA SERVICIOS INICIALES -> EsNuevo = FALSE
        // ================================================
        private void CargarServiciosIniciales(int idCita)
        {
            try
            {
                List<detalleServicioDto> detallesCita = boDetalleServicio.ListarPorIdCita(idCita);
                List<ItemServicioSeleccionado> iniciales = new List<ItemServicioSeleccionado>();
                if (detallesCita != null)
                {
                    foreach (var det in detallesCita)
                    {
                        string nombreServicio = "Servicio";
                        int idServ = 0;
                        if (det.servicio != null) { nombreServicio = det.servicio.nombre; idServ = det.servicio.servicioId; }
                        iniciales.Add(new ItemServicioSeleccionado
                        {
                            ServicioID = idServ,
                            Nombre = nombreServicio,
                            Precio = det.costo,
                            PrecioFormateado = det.costo.ToString("C", CultureInfo.CreateSpecificCulture("es-PE")),
                            EsNuevo = false // Histórico
                        });
                    }
                }
                ListaServicios = iniciales;
                ActualizarResumenServicios();
            }
            catch { ListaServicios = new List<ItemServicioSeleccionado>(); }
        }

        // ================================================
        // ELIMINAR SOLO SI EsNuevo = TRUE
        // ================================================
        protected void gvServiciosSeleccionados_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "EliminarServicio")
            {
                int idServicio = Convert.ToInt32(e.CommandArgument);
                var lista = ListaServicios;

                var itemAEliminar = lista.FirstOrDefault(x => x.ServicioID == idServicio && x.EsNuevo);
                if (itemAEliminar != null)
                {
                    lista.Remove(itemAEliminar);
                    ListaServicios = lista;
                    ActualizarResumenServicios();
                }
            }
        }

        protected void btnGestionarServicios_Click(object sender, EventArgs e)
        {
            txtBuscarServicio.Text = "";
            rptServiciosModal.DataSource = null;
            rptServiciosModal.DataBind();
            pnlMensajeInicial.Visible = true;
            ScriptManager.RegisterStartupScript(this, GetType(), "ShowModalServicios", "$('#modalServicios').modal('show');", true);
        }

        protected void txtBuscarServicio_TextChanged(object sender, EventArgs e)
        {
            string filtro = txtBuscarServicio.Text.Trim();
            if (!string.IsNullOrEmpty(filtro)) { pnlMensajeInicial.Visible = false; CargarServiciosEnModal(filtro); }
            else { rptServiciosModal.DataSource = null; rptServiciosModal.DataBind(); pnlMensajeInicial.Visible = true; updModalServicios.Update(); }
        }

        private void CargarServiciosEnModal(string filtro)
        {
            try
            {
                List<servicioDto> todos = boServicio.listarServicioActivos();
                if (todos != null)
                {
                    var filtrados = todos.Where(s => s.nombre.IndexOf(filtro, StringComparison.OrdinalIgnoreCase) >= 0).ToList();
                    var listaModal = filtrados.Select(s => new {
                        ServicioID = s.servicioId,
                        Nombre = s.nombre,
                        Descripcion = s.descripcion,
                        Precio = s.costo,
                        PrecioFormateado = s.costo.ToString("C", CultureInfo.CreateSpecificCulture("es-PE")),
                        Seleccionado = ListaServicios.Any(x => x.ServicioID == s.servicioId)
                    }).ToList();
                    rptServiciosModal.DataSource = listaModal;
                    rptServiciosModal.DataBind();
                }
                updModalServicios.Update();
            }
            catch { }
        }

        // ================================================
        // AGREGAR NUEVOS -> EsNuevo = TRUE
        // ================================================
        protected void btnGuardarServiciosModal_Click(object sender, EventArgs e)
        {
            List<ItemServicioSeleccionado> nuevaLista = new List<ItemServicioSeleccionado>(ListaServicios);
            foreach (RepeaterItem item in rptServiciosModal.Items)
            {
                CheckBox chk = (CheckBox)item.FindControl("chkServicio");
                HiddenField hdId = (HiddenField)item.FindControl("hdServicioID");
                if (chk != null && hdId != null)
                {
                    int id = Convert.ToInt32(hdId.Value);
                    bool yaExiste = nuevaLista.Any(x => x.ServicioID == id);

                    if (chk.Checked && !yaExiste)
                    {
                        servicioDto s = boServicio.ObtenerPorId(id);
                        if (s != null)
                        {
                            nuevaLista.Add(new ItemServicioSeleccionado
                            {
                                ServicioID = s.servicioId,
                                Nombre = s.nombre,
                                Precio = s.costo,
                                PrecioFormateado = s.costo.ToString("C", CultureInfo.CreateSpecificCulture("es-PE")),
                                EsNuevo = true // Nuevo agregado
                            });
                        }
                    }
                    else if (!chk.Checked && yaExiste)
                    {
                        var itemRemover = nuevaLista.FirstOrDefault(x => x.ServicioID == id && x.EsNuevo);
                        if (itemRemover != null) nuevaLista.Remove(itemRemover);
                    }
                }
            }
            ListaServicios = nuevaLista;
            ActualizarResumenServicios();
            ScriptManager.RegisterStartupScript(this, GetType(), "HideModalServicios", "$('#modalServicios').modal('hide');", true);
        }

        private void ActualizarResumenServicios()
        {
            gvServiciosSeleccionados.DataSource = ListaServicios;
            gvServiciosSeleccionados.DataBind();
            double total = ListaServicios.Sum(x => x.Precio);
            hfMontoTotal.Value = total.ToString("0.00");
            string totalFmt = total.ToString("C", CultureInfo.CreateSpecificCulture("es-PE"));
            ScriptManager.RegisterStartupScript(this, GetType(), "UpdateTotal", $"$('#spanMontoTotal').text('{totalFmt}');", true);
            updBotones.Update();
        }

        protected void btnAgregarMedicamento_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrWhiteSpace(txtRecDesc.Text)) return;
            var lista = ListaMedicamentos;
            lista.Add(new ItemReceta { Descripcion = txtRecDesc.Text, Dosis = txtRecDosis.Text, Frecuencia = txtRecFrec.Text, Duracion = txtRecDuracion.Text, Indicacion = txtRecIndicacion.Text, Cantidad = txtRecCant.Text });
            ListaMedicamentos = lista;
            txtRecDesc.Text = ""; txtRecDosis.Text = ""; txtRecFrec.Text = ""; txtRecDuracion.Text = ""; txtRecIndicacion.Text = ""; txtRecCant.Text = "";
            gvReceta.DataSource = lista; gvReceta.DataBind();
        }

        protected void gvReceta_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            var lista = ListaMedicamentos;
            if (e.RowIndex >= 0 && e.RowIndex < lista.Count) { lista.RemoveAt(e.RowIndex); ListaMedicamentos = lista; gvReceta.DataSource = lista; gvReceta.DataBind(); }
        }

        // ================================================
        // FINALIZAR CONSULTA: INSERTAR SOLO LOS NUEVOS
        // ================================================
        protected void btnCompletarConsulta_Click(object sender, EventArgs e)
        {
            Thread.Sleep(1500);
            try
            {
                if (CitaIdActual == 0) return;
                citaAtencionDto cita = boCita.ObtenerPorId(CitaIdActual);

                string obsFinal = $"[DIAGNÓSTICO]: {txtDiagnosticoClinico.Text} | [OBSERVACIONES]: {txtObservacionesClinicas.Text} | [TRATAMIENTO]: {txtPlanTratamiento.Text}";
                if (obsFinal.Length > 250) obsFinal = obsFinal.Substring(0, 250);

                double peso = 0;
                double monto = 0; double.TryParse(hfMontoTotal.Value, out monto);

                string fi = cita.fechaHoraInicioStr;
                string ff = cita.fechaHoraFinStr;
                if (string.IsNullOrEmpty(fi)) fi = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
                if (string.IsNullOrEmpty(ff)) ff = DateTime.Now.AddMinutes(30).ToString("yyyy-MM-dd HH:mm:ss");

                boCita.Modificar(cita.citaId, cita.veterinario.veterinarioId, cita.mascota.mascotaId, fi, ff, peso, monto, "ATENDIDA", obsFinal, true);

                // Guardar Servicios (Solo los marcados como nuevos)
                foreach (var serv in ListaServicios)
                {
                    if (serv.EsNuevo)
                    {
                        boDetalleServicio.Insertar(cita.citaId, serv.ServicioID, serv.Nombre, serv.Precio, true);
                    }
                }

                if (ListaMedicamentos.Count > 0 || !string.IsNullOrEmpty(txtIndicacionesGenerales.Text))
                {
                    string hoy = DateTime.Now.ToString("yyyy-MM-dd");
                    string fin = DateTime.Now.AddDays(7).ToString("yyyy-MM-dd");
                    int rId = boReceta.Insertar(cita.citaId, hoy, fin, txtDiagnosticoClinico.Text, txtIndicacionesGenerales.Text, true);
                    if (rId > 0)
                    {
                        foreach (var m in ListaMedicamentos) boDetalleReceta.Insertar(rId, m.Descripcion, "-", "Oral", m.Dosis, m.Frecuencia, m.Duracion, m.Indicacion, m.Cantidad, true);
                    }
                }
                string script = "$('#modalExito').modal('show');";
                ScriptManager.RegisterStartupScript(this, GetType(), "ShowSuccess", script, true);
            }
            catch (Exception ex) { lblError.Text = "Error: " + ex.Message; lblError.Visible = true; }
        }

        protected void btnVolverAgenda_Click(object sender, EventArgs e) { Response.Redirect("~/Veterinario_Agenda.aspx"); }
        protected void btnCancelarConsulta_Click(object sender, EventArgs e) { Response.Redirect("~/Veterinario_Agenda.aspx"); }
    }
}