<%@ Page Title="Reservar Cita - Fecha y Hora" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_ReservarCita_Paso3.aspx.cs"
    Inherits="SoftPetWA.Secretaria_ReservarCita_Paso3" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Reservar Cita - Fecha y Hora
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/reservarcita.css" rel="stylesheet" />
    <%-- AQUÍ AGREGAMOS TU NUEVO CSS DEL CALENDARIO --%>
    <link href="Content/reservarcita-calendario.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">

    <%-- Stepper de Pasos --%>
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
                <div class="step-item active">
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

    <%-- Contenido Principal --%>
    <div class="card shadow-sm border-0">
        <div class="card-body p-4">

            <div class="mb-4">
                <h4><i class="fas fa-calendar-alt me-2 text-primary"></i>Selecciona Fecha y Hora</h4>
                <h5>Cliente: <asp:Label ID="lblClienteNombre" runat="server" CssClass="text-primary fw-bold" /></h5>
            </div>

            <asp:UpdatePanel ID="updCalendar" runat="server" UpdateMode="Conditional">
                <ContentTemplate>
                    <div class="row">
                        
                        <%-- Columna Izquierda: Calendario --%>
                        <div class="col-md-7">
                            <div class="mb-3">
                                <label for="<%= ddlVeterinario.ClientID %>" class="form-label fw-bold">Veterinario</label>
                                <asp:DropDownList ID="ddlVeterinario" runat="server" CssClass="form-select" AutoPostBack="true" OnSelectedIndexChanged="ddlVeterinario_SelectedIndexChanged"></asp:DropDownList>
                            </div>

                            <%-- Contenedor del Calendario (Usa estilos de reservarcita-calendario.css) --%>
                            <div class="calendar-container">
                                <div class="calendar-header">
                                    <div class="month-year">
                                        <asp:Label ID="lblMesAnio" runat="server"></asp:Label>
                                    </div>
                                    <div class="calendar-nav">
                                        <asp:LinkButton ID="btnPrevMonth" runat="server" CssClass="btn-nav-cal me-1" OnClick="btnPrevMonth_Click"><i class="fas fa-chevron-left"></i></asp:LinkButton>
                                        <asp:LinkButton ID="btnNextMonth" runat="server" CssClass="btn-nav-cal" OnClick="btnNextMonth_Click"><i class="fas fa-chevron-right"></i></asp:LinkButton>
                                    </div>
                                </div>

                                <div class="calendar-grid">
                                    <div class="day-header">Dom</div>
                                    <div class="day-header">Lun</div>
                                    <div class="day-header">Mar</div>
                                    <div class="day-header">Mié</div>
                                    <div class="day-header">Jue</div>
                                    <div class="day-header">Vie</div>
                                    <div class="day-header">Sáb</div>

                                    <asp:Repeater ID="rptCalendarDays" runat="server" OnItemCommand="rptCalendarDays_ItemCommand">
                                        <ItemTemplate>
                                            <asp:LinkButton ID="btnDay" runat="server"
                                                CssClass='<%# Eval("CssClass") %>' 
                                                CommandName="SelectDay"
                                                CommandArgument='<%# Eval("Date") %>'
                                                Enabled='<%# Eval("Enabled") %>'>
                                                <%# Eval("Day") %>
                                            </asp:LinkButton>
                                        </ItemTemplate>
                                    </asp:Repeater>
                                </div>
                            </div>
                        </div>

                        <%-- Columna Derecha: Horarios Disponibles --%>
                        <div class="col-md-5">
                            <div class="time-slots-container">
                                <h6 class="time-slots-header">
                                    <asp:Label ID="lblHorariosDisponibles" runat="server"></asp:Label>
                                </h6>
                                <div>
                                    <asp:Repeater ID="rptHorarios" runat="server" OnItemCommand="rptHorarios_ItemCommand">
                                        <ItemTemplate>
                                            <asp:LinkButton ID="btnHora" runat="server"
                                                CssClass='<%# Convert.ToBoolean(Eval("Disponible")) ? (Eval("CssClass").ToString()) : "time-slot-btn disabled" %>'
                                                CommandName="SelectTime"
                                                CommandArgument='<%# Eval("HoraComando") %>'
                                                Enabled='<%# Convert.ToBoolean(Eval("Disponible")) %>'>
                                                <%# Eval("HoraUI") %>
                                            </asp:LinkButton>
                                        </ItemTemplate>
                                    </asp:Repeater>
                                </div>
                                <div class="alert alert-primary mt-3" role="alert" style="font-size: 0.85rem;">
                                    <i class="fas fa-info-circle me-2"></i>Horarios disponibles según el veterinario.
                                </div>
                            </div>
                        </div>
                    </div>
                </ContentTemplate>
            </asp:UpdatePanel>

            <%-- Botones de Navegación --%>
            <div class="d-flex justify-content-between mt-4">
                <asp:LinkButton ID="btnAnterior" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnAnterior_Click">
                    <i class="fas fa-arrow-left me-2"></i>Anterior
                </asp:LinkButton>
                <asp:LinkButton ID="btnSiguiente" runat="server" 
                    CssClass="btn btn-primary" 
                    OnClick="btnSiguiente_Click" 
                    OnClientClick="return validarPaso3();">
                    Siguiente<i class="fas fa-arrow-right ms-2"></i>
                </asp:LinkButton>
            </div>

        </div>
    </div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script type="text/javascript">
        
        // --- VALIDACIÓN 1: AL DAR CLIC EN UN DÍA DEL CALENDARIO ---
        // Usamos delegación de eventos ($(document).on) porque el calendario 
        // está dentro de un UpdatePanel y se regenera dinámicamente.
        $(document).on('click', '.calendar-day', function (e) {
            
            // 1. Verificar si el día está deshabilitado (si es gris, no hacemos nada)
            if ($(this).hasClass('disabled') || $(this).hasClass('other-month')) {
                return false;
            }

            // 2. Obtener el valor del DropDownList de Veterinario
            // Usamos el ClientID de ASP.NET para ser precisos
            var vetId = $('#<%= ddlVeterinario.ClientID %>').val();

            // 3. Validar
            if (vetId === "0" || vetId === "") {
                // Detenemos el PostBack (no va al servidor)
                e.preventDefault(); 
                
                // Mostramos el error
                var errores = ['Debe seleccionar un médico veterinario antes de elegir la fecha.'];
                if (typeof mostrarErroresCliente === 'function') {
                    mostrarErroresCliente(errores);
                } else {
                    alert(errores[0]);
                }
                return false;
            }

            // Si hay veterinario, dejamos que el LinkButton haga su trabajo (PostBack)
            mostrarLoading();
            return true;
        });


        // --- VALIDACIÓN 2: AL DAR CLIC EN "SIGUIENTE" ---
        function validarPaso3() {
            var errores = [];

            // A. Validar Veterinario (por seguridad)
            var vetId = $('#<%= ddlVeterinario.ClientID %>').val();
            if (vetId === "0") {
                errores.push("Debe seleccionar un médico veterinario.");
            }

            // B. Validar Fecha (Buscamos si hay un día con la clase 'selected')
            var fechaSeleccionada = $('.calendar-day.selected').length > 0;
            if (!fechaSeleccionada && errores.length === 0) { 
                // Solo mostramos este error si ya seleccionó vet, para no saturar
                errores.push("Debe seleccionar una fecha en el calendario.");
            }

            // C. Validar Hora (Buscamos si hay un botón de hora con la clase 'selected')
            var horaSeleccionada = $('.time-slot-btn.selected').length > 0;
            
            // Solo validamos la hora si ya pasó las validaciones anteriores
            if (errores.length === 0 && !horaSeleccionada) {
                errores.push("Debe elegir una hora en la cual se le atenderá.");
            }

            // D. Resultado Final
            if (errores.length > 0) {
                if (typeof mostrarErroresCliente === 'function') {
                    mostrarErroresCliente(errores);
                } else {
                    alert(errores[0]);
                }
                return false; // Cancela el envío
            }

            mostrarLoading();
            return true; // Permite el envío
        }

        // --- EXTRAS VISUALES ---
        // Como usas UpdatePanel, necesitamos reactivar efectos visuales (si tuvieras) 
        // después de cada carga parcial (AJAX).
        var prm = Sys.WebForms.PageRequestManager.getInstance();
        prm.add_endRequest(function () {
            // Si necesitas reinicializar algún plugin o efecto visual del calendario, va aquí.
            // Por ahora, la validación funciona sin esto gracias a la delegación de eventos.
        });

    </script>
</asp:Content>