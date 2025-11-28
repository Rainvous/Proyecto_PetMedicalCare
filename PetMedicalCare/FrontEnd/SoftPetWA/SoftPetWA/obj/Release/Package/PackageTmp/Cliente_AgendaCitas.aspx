<%@ Page Title="Agenda de Citas" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true"
    CodeBehind="Cliente_AgendaCitas.aspx.cs"
    Inherits="SoftPetWA.Cliente_AgendaCitas" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Agenda de Citas
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/agendacitas.css" rel="stylesheet" />
    <link href="Content/cargando.css" rel="stylesheet" />
    <style>
        .filter-bar .form-label {
            font-size: 0.85rem;
            font-weight: 600;
            color: #495057;
        }

        .card {
            border: none;
            border-radius: 12px;
        }

        .agenda-calendario {
            border: none;
        }

        .cal-day-header {
            background-color: #f8f9fa;
            border: none;
            padding: 10px;
        }

        .cal-title {
            background-color: white;
            color: #333;
            font-weight: bold;
            border: none;
            padding: 15px;
        }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">

    <asp:UpdateProgress ID="updProgAgenda" runat="server" AssociatedUpdatePanelID="updAgenda">
        <ProgressTemplate>
            <div class="loading-overlay">
                <div class="loading-spinner"></div>
            </div>
        </ProgressTemplate>
    </asp:UpdateProgress>

    <asp:UpdatePanel ID="updAgenda" runat="server">
        <ContentTemplate>

            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3><i class="fas fa-calendar-alt me-2 text-primary"></i>Agenda de Citas</h3>
            </div>

            <%-- 1. Barra de Filtros --%>
            <div class="card shadow-sm mb-4 filter-bar">
                <div class="card-body">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-4">
                            <label for="<%= txtFecha.ClientID %>" class="form-label">Fecha</label>
                            <asp:TextBox ID="txtFecha" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox>
                        </div>

                        <%-- AQUI ESTÁ EL CAMBIO: Filtro por MASCOTA --%>
                        <div class="col-md-4">
                            <label for="<%= ddlMascota.ClientID %>" class="form-label">Mascota</label>
                            <asp:DropDownList ID="ddlMascota" runat="server" CssClass="form-select"></asp:DropDownList>
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

            <%-- 2. Barra de Acciones --%>
            <div class="d-flex justify-content-between align-items-center mb-3">
                <div>
                    <asp:LinkButton ID="btnVistaCalendario" runat="server" CssClass="btn btn-outline-secondary me-2" OnClientClick="mostrarVista('calendario'); return false;">
                        <i class='fas fa-calendar-week me-2'></i>Vista Calendario
                    </asp:LinkButton>
                    <asp:LinkButton ID="btnVistaLista" runat="server" CssClass="btn btn-secondary" OnClientClick="mostrarVista('lista'); return false;">
                        <i class='fas fa-list me-2'></i>Vista Lista
                    </asp:LinkButton>
                </div>
                <div>
                    <asp:LinkButton ID="btnNuevaCita" runat="server" CssClass="btn btn-success" OnClick="btnNuevaCita_Click">
                        <i class='fas fa-plus me-2'></i>Nueva Cita
                    </asp:LinkButton>
                </div>
            </div>

            <%-- 3. Cabecera y Navegación --%>
            <div class="d-flex justify-content-between align-items-center mb-4 bg-white p-3 rounded shadow-sm border">
                <div>
                    <asp:LinkButton ID="lnkAnterior" runat="server" CssClass="btn btn-outline-secondary btn-sm" OnClick="lnkAnterior_Click"><i class="fas fa-chevron-left"></i> Anterior</asp:LinkButton>
                    <asp:LinkButton ID="lnkSiguiente" runat="server" CssClass="btn btn-outline-secondary btn-sm ms-1" OnClick="lnkSiguiente_Click">Siguiente <i class="fas fa-chevron-right"></i></asp:LinkButton>
                </div>
                <h5 class="mb-0 fw-bold text-secondary">
                    <asp:Label ID="lblFechaActual" runat="server" Text="Fecha Actual"></asp:Label>
                </h5>
                <asp:LinkButton ID="btnHoy" runat="server" CssClass="btn btn-primary btn-sm" OnClick="btnHoy_Click">Hoy</asp:LinkButton>
            </div>

            <%-- VISTA 1: LISTA (Timeline) --%>
            <asp:Panel ID="pnlVistaLista" runat="server">
                <div class="agenda-timeline">
                    <asp:Repeater ID="rptHoras" runat="server">
                        <ItemTemplate>
                            <div class="mb-3 timeline-slot">
                                <span class="time-label"><%# Eval("HoraLabel") %></span>
                                <asp:Repeater ID="rptCitasEnHora" runat="server" DataSource='<%# Eval("Citas") %>'>
                                    <ItemTemplate>
                                        <div class="appointment-card <%# Eval("CssClass") %>">
                                            <span class="badge-status float-end fw-bold"><%# Eval("Status") %></span>
                                            <div class="appointment-time"><i class="far fa-clock me-1"></i><%# Eval("HoraCita") %></div>

                                            <%-- Mostramos 'Yo' porque es la agenda del cliente --%>
                                            <h6><%# Eval("Cliente") %></h6>

                                            <div class="mt-2">
                                                <small class="meta-info d-block"><i class="fas fa-paw me-2"></i><%# Eval("MascotaNombre") %></small>
                                                <small class="meta-info d-block"><i class="fas fa-user-md me-2"></i><%# Eval("VeterinarioNombre") %></small>
                                            </div>
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
                        <asp:Calendar ID="calAgenda" runat="server" CssClass="agenda-calendario" DayNameFormat="Short" Width="100%"
                            OnDayRender="calAgenda_DayRender" OnSelectionChanged="calAgenda_SelectionChanged">
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

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script type="text/javascript">
        $(document).ready(function () {
            window.mostrarVista = function (vista) {
                if (vista === 'calendario') {
                    $('#<%= pnlVistaLista.ClientID %>').hide();
                    $('#<%= pnlVistaCalendario.ClientID %>').show();
                    $('#<%= btnVistaCalendario.ClientID %>').removeClass('btn-outline-secondary').addClass('btn-secondary');
                    $('#<%= btnVistaLista.ClientID %>').removeClass('btn-secondary').addClass('btn-outline-secondary');
                } else {
                    $('#<%= pnlVistaLista.ClientID %>').show();
                    $('#<%= pnlVistaCalendario.ClientID %>').hide();
                    $('#<%= btnVistaCalendario.ClientID %>').removeClass('btn-secondary').addClass('btn-outline-secondary');
                    $('#<%= btnVistaLista.ClientID %>').removeClass('btn-outline-secondary').addClass('btn-secondary');
                }
            }
        });
    </script>
</asp:Content>
