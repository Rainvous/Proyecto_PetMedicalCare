<%-- Nombre del archivo: Secretaria_ReservarCita_Paso2.aspx (Limpio y Corregido) --%>

<%@ Page Title="Reservar Cita - Servicio" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_ReservarCita_Paso2.aspx.cs"
    Inherits="SoftPetWA.Secretaria_ReservarCita_Paso2" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Reservar Cita - Servicio
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/reservarcita.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">

    <%-- 1. Contenedor del Stepper --%>
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body p-4">
            <div class="stepper-wrapper">
                <div class="step-item active">
                    <div class="step-circle">1</div>
                    <span class="step-label">Mascota</span>
                </div>
                <div class="step-item active">
                    <div class="step-circle">2</div>
                    <span class="step-label">Servicio</span>
                </div>
                <div class="step-item">
                    <div class="step-circle">3</div>
                    <span class="step-label">Fecha y Hora</span>
                </div>
                <div class="step-item">
                    <div class="step-circle">4</div>
                    <span class="step-label">Confirmación</span>
                </div>
            </div>
        </div>
    </div>

    <%-- 2. Contenedor del Contenido Principal --%>
    <div class="card shadow-sm border-0">
        <div class="card-body p-4">

            <div class="mb-4">
                <h4><i class="fas fa-briefcase-medical me-2 text-primary"></i>Selecciona el Servicio</h4>

                <h5>Cliente:
                    <asp:Label ID="lblClienteNombre" runat="server" CssClass="text-primary fw-bold" Text="[Nombre del Cliente]" /></h5>

                <div class="alert alert-primary" role="alert">
                    <i class="fas fa-info-circle me-2"></i>Puedes seleccionar uno o más servicios para tu cita
                </div>
            </div>

            <div class="row mb-4">
                <asp:Repeater ID="rptServicios" runat="server">
                    <ItemTemplate>
                        <div class="col-md-4 mb-3">
                            <div class="service-selection-card">
                                <div class="service-checkbox-container">

                                    <asp:HiddenField ID="hdServicioID" runat="server" Value='<%# Eval("ServicioID") %>' />

                                    <asp:CheckBox ID="chkSelectService" runat="server" CssClass="form-check-input" />
                                </div>
                                <div class="service-info-container">
                                    <label for='<%# ((CheckBox)Container.FindControl("chkSelectService")).ClientID %>'>
                                        <span class="service-name d-block"><%# Eval("Nombre") %></span>
                                        <span class="service-desc d-block"><%# Eval("Descripcion") %></span>
                                        <span class="service-price d-block"><%# Eval("PrecioFormateado") %></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>
            </div>

            <%-- BOTONES DE NAVEGACIÓN --%>
            <div class="d-flex justify-content-between">
                <asp:LinkButton ID="btnAnterior" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnAnterior_Click">
                    <i class="fas fa-arrow-left me-2"></i>Anterior
                </asp:LinkButton>
                <asp:LinkButton ID="btnSiguiente" runat="server" 
                    CssClass="btn btn-primary" 
                    OnClick="btnSiguiente_Click" 
                    OnClientClick="return validarSeleccionServicio();">
                    Siguiente<i class="fas fa-arrow-right ms-2"></i>
                </asp:LinkButton>
            </div>

        </div>
    </div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script type="text/javascript">
        $(document).ready(function () {
            // --- 1. LÓGICA VISUAL CORREGIDA ---
            $('.service-selection-card').click(function (e) {
                // Si el clic NO fue directamente en el checkbox ni en el label
                if (e.target.type !== 'checkbox' && e.target.tagName !== 'LABEL') {
                    // CORRECCIÓN: Buscamos el input directamente, no la clase
                    var checkbox = $(this).find('input[type="checkbox"]');

                    // Invertimos el valor actual
                    checkbox.prop('checked', !checkbox.prop('checked'));

                    // Disparamos el evento change para actualizar el color azul
                    checkbox.change();
                }
            });

            // Actualizar estilo visual cuando cambia el checkbox
            // CORRECCIÓN: Usamos el selector input[type="checkbox"] para mayor seguridad
            $('.service-selection-card input[type="checkbox"]').change(function () {
                if ($(this).is(':checked')) {
                    $(this).closest('.service-selection-card').addClass('selected');
                } else {
                    $(this).closest('.service-selection-card').removeClass('selected');
                }
            });

            // Al cargar la página, verificar cuáles ya venían marcados
            $('.service-selection-card input[type="checkbox"]').each(function () {
                if ($(this).is(':checked')) {
                    $(this).closest('.service-selection-card').addClass('selected');
                }
            });
        });

        // --- 2. VALIDACIÓN CORREGIDA ---
        function validarSeleccionServicio() {
            // CORRECCIÓN CLAVE: 
            // Buscamos "input[type='checkbox']:checked" dentro de la tarjeta.
            // Esto funciona sin importar si ASP.NET usó spans o no.
            var serviciosMarcados = $('.service-selection-card input[type="checkbox"]:checked').length;

            if (serviciosMarcados === 0) {
                var errores = ['Debes seleccionar al menos un servicio (ej. Consulta General) para continuar.'];

                if (typeof mostrarErroresCliente === 'function') {
                    mostrarErroresCliente(errores);
                } else {
                    alert(errores[0]);
                }
                return false;
            }

            if (typeof mostrarLoading === 'function') {
                mostrarLoading();
            }
            return true;
        }
    </script>
</asp:Content>
