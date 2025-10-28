<%-- Nombre del archivo: Secretaria_AgendaCitas.aspx --%>
<%@ Page Title="Agenda de Citas" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true" 
    CodeBehind="Secretaria_AgendaCitas.aspx.cs" 
    Inherits="SoftPetWA.Secretaria_AgendaCitas" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Agenda de Citas
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/agendacitas.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
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
                        <span class="label d-block">Confirmadas</span>
                    </div>
                </div>
                <div class="col-md-3 summary-item">
                    <div class="icon-circle-sm bg-success-soft">
                        <i class="fas fa-check-double text-success-custom"></i>
                    </div>
                    <div>
                        <asp:Label ID="lblPendientes" runat="server" Text="0" CssClass="count"></asp:Label>
                        <span class="label d-block">Pendientes</span>
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
                <div class="col-md-2">
                    <label for="<%= txtFecha.ClientID %>" class="form-label">Fecha</label>
                    <asp:TextBox ID="txtFecha" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label for="<%= ddlVeterinario.ClientID %>" class="form-label">Veterinario</label>
                    <asp:DropDownList ID="ddlVeterinario" runat="server" CssClass="form-select"></asp:DropDownList>
                </div>
                <div class="col-md-2">
                    <label for="<%= ddlEstado.ClientID %>" class="form-label">Estado</label>
                    <asp:DropDownList ID="ddlEstado" runat="server" CssClass="form-select"></asp:DropDownList>
                </div>
                <div class="col-md-3">
                    <label for="<%= txtClienteMascota.ClientID %>" class="form-label">Cliente/Mascota</label>
                    <asp:TextBox ID="txtClienteMascota" runat="server" CssClass="form-control" placeholder="Buscar..."></asp:TextBox>
                </div>
                <div class="col-md-3 d-flex">
                    <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary me-2" OnClick="btnBuscar_Click">
                        <i class='fas fa-search me-1'></i> Buscar
                    </asp:LinkButton>
                    <asp:LinkButton ID="btnLimpiar" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnLimpiar_Click">
                        <i class='fas fa-times'></i>
                    </asp:LinkButton>
                </div>
            </div>
        </div>
    </div>

    <%-- 3. Barra de Acciones y Vistas --%>
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div>
            <asp:LinkButton ID="btnVistaCalendario" runat="server" CssClass="btn btn-outline-secondary me-2">
                <i class='fas fa-calendar-week me-2'></i>Vista Calendario
            </asp:LinkButton>
            <asp:LinkButton ID="btnVistaLista" runat="server" CssClass="btn btn-secondary">
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
            <asp:LinkButton ID="lnkAnterior" runat="server" CssClass="btn btn-outline-secondary"><i class="fas fa-chevron-left"></i> Anterior</asp:LinkButton>
            <asp:LinkButton ID="lnkSiguiente" runat="server" CssClass="btn btn-outline-secondary ms-2">Siguiente <i class="fas fa-chevron-right"></i></asp:LinkButton>
        </div>
        <h4 class="mb-0">
            <asp:Label ID="lblFechaActual" runat="server" Text="Lunes, 6 de Octubre 2024"></asp:Label>
        </h4>
       <asp:LinkButton ID="btnHoy" runat="server" CssClass="btn btn-primary">Hoy</asp:LinkButton>
    </div>

    <%-- 5. Timeline de Citas (Repeater) --%>
    <div class="agenda-timeline">
        <asp:Repeater ID="rptAgendaTimeline" runat="server">
            <ItemTemplate>
                <div class="mb-3 timeline-slot">
                    <span class="time-label"><%# Eval("HoraLabel") %></span>
                    
                    <%-- Solo mostramos la tarjeta si hay una cita (HoraCita no está vacío) --%>
                    <asp:Panel ID="pnlCita" runat="server" Visible='<%# !string.IsNullOrEmpty(Eval("HoraCita").ToString()) %>'>
                        <div class="appointment-card <%# Eval("CssClass") %>">
                            <span class="badge-status float-end fw-bold"><%# Eval("Status") %></span>
                            <div class="appointment-time"><%# Eval("HoraCita") %></div>
                            <h6><%# Eval("Cliente") %></h6>
                            <small class="meta-info d-block"><%# Eval("MascotaServicio") %></small>
                        </div>
                    </asp:Panel>
                </div>
            </ItemTemplate>
        </asp:Repeater>
    </div>

</asp:Content>