<%-- Nombre del archivo: Secretaria_AgendaCitas.aspx (CON BOTONES DE ACCIÓN) --%>

<%@ Page Title="Agenda de Citas" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_AgendaCitas.aspx.cs"
    Inherits="SoftPetWA.Secretaria_AgendaCitas" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Agenda de Citas
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/agendacitas.css" rel="stylesheet" />
    <%-- Referencia al CSS de Carga --%>
    <link href="Content/cargando.css" rel="stylesheet" />

    <%-- ESTILOS FORZADOS PARA EL MODAL (Solución al color Cyan) --%>
    <style>
        /* 1. Estado Normal: BLANCO (Usamos !important para sobreescribir cualquier tema) */
        #modalBuscarPaciente .list-group-item {
            background-color: #ffffff !important;
            color: #333333 !important;
            border: 1px solid #e9ecef !important;
            transition: all 0.2s ease;
        }

        /* 2. Estado Hover: CYAN al pasar el mouse */
        #modalBuscarPaciente .list-group-item:hover {
            background-color: #17a2b8 !important; /* Color Cyan */
            color: #ffffff !important;            /* Texto Blanco */
            border-color: #17a2b8 !important;
            cursor: pointer;
        }

        /* 3. Corrección de texto pequeño en hover */
        #modalBuscarPaciente .list-group-item:hover small,
        #modalBuscarPaciente .list-group-item:hover .text-muted {
            color: #e3f2fd !important; /* Blanco muy claro */
        }
        
        /* 4. Ajuste del botón 'Agendar Cita' en hover */
        #modalBuscarPaciente .list-group-item:hover .btn-success {
            border: 1px solid white !important;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
        }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">

    <%-- Indicador de Carga (Loading) --%>
    <asp:UpdateProgress ID="updProgAgenda" runat="server" AssociatedUpdatePanelID="updAgenda">
        <ProgressTemplate>
            <div class="loading-overlay">
                <div class="loading-spinner"></div>
            </div>
        </ProgressTemplate>
    </asp:UpdateProgress>

    <asp:UpdatePanel ID="updAgenda" runat="server">
        <ContentTemplate>

            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3><i class="fas fa-calendar-alt me-2"></i>Agenda de Citas</h3>
            </div>

            <%-- 1. Resumen Superior --%>
            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-3 summary-item">
                            <asp:Label ID="lblCitasHoy" runat="server" Text="0" CssClass="count text-primary-custom"></asp:Label>
                            <span class="label">Citas de Hoy</span>
                        </div>
                        <div class="col-md-3 summary-item">
                            <div class="icon-circle-sm bg-primary-soft">
                                <i class="fas fa-check text-primary-custom"></i>
                            </div>
                            <div>
                                <asp:Label ID="lblConfirmadas" runat="server" Text="0" CssClass="count"></asp:Label>
                                <span class="label d-block">Atendidas</span>
                            </div>
                        </div>
                        <div class="col-md-3 summary-item">
                            <div class="icon-circle-sm bg-success-soft">
                                <i class="fas fa-check-double text-success-custom"></i>
                            </div>
                            <div>
                                <asp:Label ID="lblPendientes" runat="server" Text="0" CssClass="count"></asp:Label>
                                <span class="label d-block">Programadas</span>
                            </div>
                        </div>
                        <div class="col-md-3 summary-item">
                            <div class="icon-circle-sm bg-danger-soft">
                                <i class="fas fa-times text-danger-custom"></i>
                            </div>
                            <div>
                                <asp:Label ID="lblCanceladas" runat="server" Text="0" CssClass="count"></asp:Label>
                                <span class="label d-block">Canceladas</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%-- 2. Barra de Filtros --%>
            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-4">
                            <label for="<%= txtFecha.ClientID %>" class="form-label">Fecha</label>
                            <asp:TextBox ID="txtFecha" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox>
                        </div>
                        <div class="col-md-4">
                            <label for="<%= ddlVeterinario.ClientID %>" class="form-label">Veterinario</label>
                            <asp:DropDownList ID="ddlVeterinario" runat="server" CssClass="form-select"></asp:DropDownList>
                        </div>

                        <div class="col-md-4 d-flex">
                            <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary me-2" OnClick="btnBuscar_Click">
                                <i class='fas fa-search me-1'></i> Buscar
                            </asp:LinkButton>
                            <asp:LinkButton ID="btnLimpiar" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnLimpiar_Click">
                                <i class='fas fa-times'></i> Limpiar
                            </asp:LinkButton>
                        </div>
                    </div>
                </div>
            </div>

            <%-- 3. Barra de Acciones y Vistas (MODIFICADO) --%>
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <asp:LinkButton ID="btnVistaCalendario" runat="server"
                        CssClass="btn btn-outline-secondary me-2"
                        OnClientClick="mostrarVista('calendario'); return false;">
                        <i class='fas fa-calendar-week me-2'></i>Vista Calendario
                    </asp:LinkButton>
                    <asp:LinkButton ID="btnVistaLista" runat="server"
                        CssClass="btn btn-secondary"
                        OnClientClick="mostrarVista('lista'); return false;">
                        <i class='fas fa-list me-2'></i>Vista Lista
                    </asp:LinkButton>
                </div>
                <div>
                    <asp:LinkButton ID="btnNuevaCita" runat="server" CssClass="btn btn-success" OnClick="btnNuevaCita_Click">
                        <i class='fas fa-plus me-2'></i>Nueva Cita
                    </asp:LinkButton>
                </div>
            </div>

            <%-- 4. Cabecera de la Agenda --%>
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <asp:LinkButton ID="lnkAnterior" runat="server" CssClass="btn btn-outline-secondary" OnClick="lnkAnterior_Click"><i class="fas fa-chevron-left"></i> Anterior</asp:LinkButton>
                    <asp:LinkButton ID="lnkSiguiente" runat="server" CssClass="btn btn-outline-secondary ms-2" OnClick="lnkSiguiente_Click">Siguiente <i class="fas fa-chevron-right"></i></asp:LinkButton>
                </div>
                <h4 class="mb-0">
                    <asp:Label ID="lblFechaActual" runat="server" Text="Lunes, 6 de Octubre 2024"></asp:Label>
                </h4>
                <asp:LinkButton ID="btnHoy" runat="server" CssClass="btn btn-primary" OnClick="btnHoy_Click">Hoy</asp:LinkButton>
            </div>

            <%-- VISTA 1: LISTA (Timeline) --%>
            <asp:Panel ID="pnlVistaLista" runat="server">
                <div class="agenda-timeline">

                    <%-- Repeater Exterior: Itera las Horas (8 AM - 6 PM) --%>
                    <asp:Repeater ID="rptHoras" runat="server" OnItemDataBound="rptHoras_ItemDataBound">
                        <ItemTemplate>
                            <div class="mb-3 timeline-slot">
                                <span class="time-label"><%# Eval("HoraLabel") %></span>

                                <%-- Repeater Interior: Itera las Citas dentro de esa Hora --%>
                                <asp:Repeater ID="rptCitasEnHora" runat="server" DataSource='<%# Eval("Citas") %>' OnItemCommand="rptCitasEnHora_ItemCommand">
                                    <ItemTemplate>
                                        <div class="appointment-card <%# Eval("CssClass") %>">
                                            <span class="badge-status float-end fw-bold"><%# Eval("Status") %></span>
                                            <div class="appointment-time"><%# Eval("HoraCita") %></div>
                                            <h6><%# Eval("Cliente") %></h6>
                                            <small class="meta-info d-block"><i class="fas fa-paw me-2"></i><%# Eval("MascotaNombre") %></small>
                                            <small class="meta-info d-block"><i class="fas fa-user-md me-2"></i><%# Eval("VeterinarioNombre") %></small>

                                            <%-- *** (NUEVA SECCIÓN DE BOTONES) *** --%>
                                            <div class="appointment-actions">
                                                <asp:LinkButton ID="btnModificarEstado" runat="server"
                                                    CssClass="btn btn-sm btn-outline-primary"
                                                    CommandName="ModificarEstado"
                                                    CommandArgument='<%# Eval("CitaId") %>'>
                                                    <i class="fas fa-edit me-1"></i>Cambiar Estado
                                                </asp:LinkButton>

                                                <asp:LinkButton ID="btnEmitirComprobante" runat="server"
                                                    CssClass="btn btn-sm btn-success"
                                                    CommandName="EmitirComprobante"
                                                    CommandArgument='<%# Eval("CitaId") + "," + Eval("PersonaId") %>'
                                                    Visible='<%# Eval("Estado").ToString() == "ATENDIDA" %>'>
                                                    <i class="fas fa-receipt me-1"></i>Emitir Comprobante
                                                </asp:LinkButton>
                                            </div>
                                            <%-- *** (FIN DE BOTONES) *** --%>
                                        </div>
                                    </ItemTemplate>
                                </asp:Repeater>
                            </div>
                        </ItemTemplate>
                    </asp:Repeater>
                </div>
            </asp:Panel>

            <%-- VISTA 2: CALENDARIO --%>
            <asp:Panel ID="pnlVistaCalendario" runat="server" Visible="false">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <asp:Calendar ID="calAgenda" runat="server"
                            CssClass="agenda-calendario"
                            DayNameFormat="Short"
                            Width="100%"
                            OnDayRender="calAgenda_DayRender"
                            OnSelectionChanged="calAgenda_SelectionChanged">
                            <TitleStyle CssClass="cal-title" />
                            <DayHeaderStyle CssClass="cal-day-header" />
                            <DayStyle CssClass="cal-day" />
                            <SelectedDayStyle CssClass="cal-day-selected" />
                            <TodayDayStyle CssClass="cal-day-today" />
                            <OtherMonthDayStyle CssClass="cal-day-other" />
                        </asp:Calendar>
                    </div>
                </div>
            </asp:Panel>

        </ContentTemplate>
    </asp:UpdatePanel>

    <%-- ================================================ --%>
    <%--     NUEVO MODAL: BUSCAR PACIENTE (PASO 1)        --%>
    <%-- ================================================ --%>
    <div class="modal fade" id="modalBuscarPaciente" tabindex="-1" aria-labelledby="modalBuscarPacienteLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalBuscarPacienteLabel">Nueva Cita: Buscar Paciente</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <asp:UpdatePanel ID="updModalBusqueda" runat="server" UpdateMode="Conditional">
                        <ContentTemplate>
                            <div class="row g-3 mb-3">
                                <div class="col-md-8">
                                    <label for="<%= txtBuscarTermino.ClientID %>" class="form-label">Término de Búsqueda</label>
                                    <asp:TextBox ID="txtBuscarTermino" runat="server" CssClass="form-control" placeholder="Buscar por nombre del cliente"></asp:TextBox>
                                </div>
                                <div class="col-md-4 d-flex align-items-end">
                                    <asp:LinkButton ID="btnBuscarPaciente" runat="server" CssClass="btn btn-primary w-100" OnClick="btnBuscarPaciente_Click">
                                        <i class="fas fa-search me-1"></i> Buscar
                                    </asp:LinkButton>
                                </div>
                            </div>
                            <hr />
                            <h6>Resultados de Búsqueda</h6>
                            <div class="list-group" style="max-height: 300px; overflow-y: auto;">
                                <asp:Repeater ID="rptResultadosPacientes" runat="server">
                                    <ItemTemplate>
                                        <div class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                            <div>
                                                <h6 class="mb-0"><%# Eval("nombre") %></h6>
                                                <small class="text-muted">Documento: <%# Eval("nroDocumento") %> | Teléfono: <%# Eval("telefono") %></small>
                                            </div>
                                            <asp:LinkButton ID="btnSeleccionarPaciente" runat="server" CssClass="btn btn-success btn-sm"
                                                Text="Agendar Cita"
                                                OnClick="btnSeleccionarPaciente_Click"
                                                CommandArgument='<%# Eval("personaId") %>' />
                                        </div>
                                    </ItemTemplate>
                                    <FooterTemplate>
                                        <asp:Panel ID="pnlNoResultados" runat="server" CssClass="list-group-item text-center text-muted"
                                            Visible="<%# rptResultadosPacientes.Items.Count == 0 %>">
                                            <asp:Label ID="lblNoResultados" runat="server" Text="No se encontraron pacientes. Realice una búsqueda."></asp:Label>
                                        </asp:Panel>
                                    </FooterTemplate>
                                </asp:Repeater>
                            </div>
                        </ContentTemplate>
                    </asp:UpdatePanel>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>


    <%-- MODAL EXISTENTE: REGISTRAR CITA (PASO 2) --%>
    <div class="modal fade" id="modalNuevaCita" tabindex="-1" aria-labelledby="modalNuevaCitaLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalNuevaCitaLabel">Registrar Nueva Cita</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <asp:UpdatePanel ID="updModalCita" runat="server" UpdateMode="Conditional">
                        <ContentTemplate>
                            <asp:HiddenField ID="hdCitaID" runat="server" Value="0" />
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="ddlModalCliente" class="form-label">Cliente</label>
                                    <asp:DropDownList ID="ddlModalCliente" runat="server" CssClass="form-select"></asp:DropDownList>
                                </div>
                                <div class="col-md-6">
                                    <label for="ddlModalMascota" class="form-label">Mascota</label>
                                    <asp:DropDownList ID="ddlModalMascota" runat="server" CssClass="form-select"></asp:DropDownList>
                                </div>
                                <div class="col-md-6">
                                    <label for="ddlModalVeterinario" class="form-label">Veterinario</label>
                                    <asp:DropDownList ID="ddlModalVeterinario" runat="server" CssClass="form-select"></asp:DropDownList>
                                </div>
                                <div class="col-md-6">
                                    <label for="ddlModalServicio" class="form-label">Servicio</label>
                                    <asp:DropDownList ID="ddlModalServicio" runat="server" CssClass="form-select"></asp:DropDownList>
                                </div>
                                <div class="col-md-4">
                                    <label for="txtModalFecha" class="form-label">Fecha</label>
                                    <asp:TextBox ID="txtModalFecha" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox>
                                </div>
                                <div class="col-md-4">
                                    <label for="txtModalHora" class="form-label">Hora</label>
                                    <asp:TextBox ID="txtModalHora" runat="server" CssClass="form-control" TextMode="Time"></asp:TextBox>
                                </div>
                                <div class="col-md-4">
                                    <label for="ddlModalEstado" class="form-label">Estado</label>
                                    <asp:DropDownList ID="ddlModalEstado" runat="server" CssClass="form-select">
                                        <asp:ListItem Value="Pendiente" Text="Pendiente" Selected="True"></asp:ListItem>
                                        <asp:ListItem Value="Confirmada" Text="Confirmada"></asp:ListItem>
                                        <asp:ListItem Value="Cancelada" Text="Cancelada"></asp:ListItem>
                                    </asp:DropDownList>
                                </div>
                                <div class="col-md-12">
                                    <label for="txtModalNotas" class="form-label">Notas Adicionales (Opcional)</label>
                                    <asp:TextBox ID="txtModalNotas" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="2"></asp:TextBox>
                                </div>
                            </div>
                        </ContentTemplate>
                    </asp:UpdatePanel>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" id="btnCerrarModal">Cerrar</button>
                    <asp:Button ID="btnGuardarCita" runat="server" Text="Guardar Cita" CssClass="btn btn-primary" OnClick="btnGuardarCita_Click" />
                </div>
            </div>
        </div>
    </div>


    <%-- *** (NUEVO MODAL PARA CAMBIAR ESTADO) *** --%>
    <div class="modal fade" id="modalModificarEstado" tabindex="-1" aria-labelledby="modalModificarEstadoLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalModificarEstadoLabel">Modificar Estado de la Cita</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <asp:UpdatePanel ID="updModalEstado" runat="server" UpdateMode="Conditional">
                        <ContentTemplate>
                            <asp:HiddenField ID="hdCitaIdModificar" runat="server" Value="0" />
                            <div class="mb-3">
                                <label for="<%= ddlNuevoEstado.ClientID %>" class="form-label">Nuevo Estado</label>
                                <asp:DropDownList ID="ddlNuevoEstado" runat="server" CssClass="form-select">
                                    <asp:ListItem Value="PROGRAMADA" Text="Programada"></asp:ListItem>
                                    <asp:ListItem Value="ATENDIDA" Text="Atendida"></asp:ListItem>
                                    <asp:ListItem Value="CANCELADA" Text="Cancelada"></asp:ListItem>
                                </asp:DropDownList>
                            </div>
                        </ContentTemplate>
                    </asp:UpdatePanel>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <asp:LinkButton ID="btnGuardarEstado" runat="server" CssClass="btn btn-primary" OnClick="btnGuardarEstado_Click">Guardar Estado</asp:LinkButton>
                </div>
            </div>
        </div>
    </div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <%-- Scripts de JQuery --%>
    <script type="text/javascript">
        $(document).ready(function () {

            var prm = Sys.WebForms.PageRequestManager.getInstance();
            if (prm != null) {
                prm.add_endRequest(function (sender, e) {

                    // Ocultar modal de "Nueva Cita"
                    if (sender._postBackSettings.sourceElement.id.endsWith("btnGuardarCita")) {
                        $('#modalNuevaCita').modal('hide');
                    }

                    // (NUEVO) Ocultar modal de "Modificar Estado"
                    if (sender._postBackSettings.sourceElement.id.endsWith("btnGuardarEstado")) {
                        $('#modalModificarEstado').modal('hide');
                    }
                });
            }

            // Esta función se llamará desde los botones OnClientClick
            window.mostrarVista = function (vista) {
                if (vista === 'calendario') {
                    // Oculta la lista, muestra el calendario
                    $('#<%= pnlVistaLista.ClientID %>').hide();
                    $('#<%= pnlVistaCalendario.ClientID %>').show();

                    // Actualiza el estilo de los botones
                    $('#<%= btnVistaCalendario.ClientID %>').removeClass('btn-outline-secondary').addClass('btn-secondary');
                    $('#<%= btnVistaLista.ClientID %>').removeClass('btn-secondary').addClass('btn-outline-secondary');
                } else {
                    // Oculta el calendario, muestra la lista
                    $('#<%= pnlVistaLista.ClientID %>').show();
                    $('#<%= pnlVistaCalendario.ClientID %>').hide();

                    // Actualiza el estilo de los botones
                    $('#<%= btnVistaCalendario.ClientID %>').removeClass('btn-secondary').addClass('btn-outline-secondary');
                    $('#<%= btnVistaLista.ClientID %>').removeClass('btn-outline-secondary').addClass('btn-secondary');
                }
            }

        });
    </script>
</asp:Content>
