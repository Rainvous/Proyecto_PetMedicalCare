<%@ Page Title="Mi Agenda" Language="C#" MasterPageFile="~/Veterinario.Master" AutoEventWireup="true" CodeBehind="Veterinario_Agenda.aspx.cs" Inherits="SoftPetWA.Veterinario_Agenda" %>

<asp:Content ID="ContentTitulo" ContentPlaceHolderID="cphTitulo" runat="server">
    Mi Agenda
</asp:Content>

<asp:Content ID="ContentHead" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Enlaza los estilos específicos de las vistas --%>
    <link href="Content/GestionVeterinarios.css" rel="stylesheet" /> 
</asp:Content>

<asp:Content ID="ContentMain" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="page-header">
        <div class="page-header-title">
            <h1>Mi Agenda de Citas</h1>
            <p class="text-muted">Gestiona tus consultas del día.</p>
        </div>
    </div>
    
    <div class="date-selector-bar">
        <div class="date-selector-nav"><asp:Button ID="btnDiaAnterior" runat="server" Text="<" CssClass="btn btn-light" OnClick="btnDiaAnterior_Click" /></div>
        <div class="text-center date-selector-info">
            <h5 class="text-gray-800"><asp:Literal ID="litFecha" runat="server" Text="Martes, 15 de Septiembre 2025"></asp:Literal></h5>
            <small>Tienes <asp:Literal ID="litConteoCitas" runat="server" Text="7"></asp:Literal> citas programadas hoy</small>
        </div>
        <div class="date-selector-nav d-flex">
            <asp:Button ID="btnDiaSiguiente" runat="server" Text=">" CssClass="btn btn-light" OnClick="btnDiaSiguiente_Click" />
            <asp:LinkButton ID="btnHoy" runat="server" OnClick="btnHoy_Click" CssClass="btn btn-primary btn-hoy" style="margin-left: 15px;">
    <i class='fas fa-calendar-check me-1'></i> Hoy
</asp:LinkButton>
        </div>
    </div>

    <%-- Tarjetas de resumen --%>
    <div class="row mb-4">
        <div class="col-lg-3 col-md-6 mb-4"><div class="card shadow-sm summary-card h-100"><div class="card-body"><div class="row no-gutters align-items-center"><div class="col mr-2"><div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Total de Citas</div><div class="h5 mb-0 font-weight-bold text-gray-800"><asp:Literal ID="litTotalCitas" runat="server" Text="7"></asp:Literal></div></div><div class="col-auto"><div class="icon-circle bg-primary"><i class="fas fa-calendar-day"></i></div></div></div></div></div></div>
        <div class="col-lg-3 col-md-6 mb-4"><div class="card shadow-sm summary-card h-100"><div class="card-body"><div class="row no-gutters align-items-center"><div class="col mr-2"><div class="text-xs font-weight-bold text-success text-uppercase mb-1">Completadas</div><div class="h5 mb-0 font-weight-bold text-gray-800"><asp:Literal ID="litCompletadas" runat="server" Text="3"></asp:Literal></div></div><div class="col-auto"><div class="icon-circle bg-success"><i class="fas fa-check"></i></div></div></div></div></div></div>
        <div class="col-lg-3 col-md-6 mb-4"><div class="card shadow-sm summary-card h-100"><div class="card-body"><div class="row no-gutters align-items-center"><div class="col mr-2"><div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Pendientes</div><div class="h5 mb-0 font-weight-bold text-gray-800"><asp:Literal ID="litPendientes" runat="server" Text="4"></asp:Literal></div></div><div class="col-auto"><div class="icon-circle bg-warning"><i class="fas fa-hourglass-half"></i></div></div></div></div></div></div>
        <div class="col-lg-3 col-md-6 mb-4"><div class="card shadow-sm summary-card h-100"><div class="card-body"><div class="row no-gutters align-items-center"><div class="col mr-2"><div class="text-xs font-weight-bold text-danger text-uppercase mb-1">Canceladas</div><div class="h5 mb-0 font-weight-bold text-gray-800"><asp:Literal ID="litCanceladas" runat="server" Text="0"></asp:Literal></div></div><div class="col-auto"><div class="icon-circle bg-danger"><i class="fas fa-times"></i></div></div></div></div></div></div>
    </div>

    <%-- Cronograma --%>
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3 class="mb-0"><i class="fas fa-stream me-2"></i> Cronograma de Atenciones</h3>
                <div class="agenda-filters btn-group" role="group">
                    <asp:LinkButton ID="lnkFiltroTodas" runat="server" CssClass="btn btn-sm btn-outline-primary active" CommandName="Filtro" CommandArgument="Todas" OnCommand="filtro_Command">Todas</asp:LinkButton>
                    <asp:LinkButton ID="lnkFiltroPendientes" runat="server" CssClass="btn btn-sm btn-outline-primary" CommandName="Filtro" CommandArgument="Pendientes" OnCommand="filtro_Command">Pendientes</asp:LinkButton>
                    <asp:LinkButton ID="lnkFiltroConfirmadas" runat="server" CssClass="btn btn-sm btn-outline-primary" CommandName="Filtro" CommandArgument="Confirmadas" OnCommand="filtro_Command">Confirmadas</asp:LinkButton> <%-- Asumiendo que existe este filtro --%>
                    <asp:LinkButton ID="lnkFiltroCompletadas" runat="server" CssClass="btn btn-sm btn-outline-primary" CommandName="Filtro" CommandArgument="Completadas" OnCommand="filtro_Command">Completadas</asp:LinkButton>
                </div>
            </div>
            <hr class="mt-0 mb-4"/>
            <ul class="agenda-timeline">
                <asp:Repeater ID="rptrAgenda" runat="server" OnItemCommand="rptrAgenda_ItemCommand">
                    <ItemTemplate>
                        <li class="agenda-item">
                            <div class='agenda-icon <%# Eval("IconoEstado") %>'><i class='<%# Eval("IconoClase") %>'></i></div>
                            <div class='card agenda-card <%# Eval("CardClass") %> shadow-sm'> <%-- Added shadow-sm --%>
                                <div class="agenda-card-header">
                                    <span class="agenda-time"><%# Eval("HoraRango") %></span>
                                    <span class='status-tag <%# Eval("EstadoCss") %>'><%# Eval("EstadoCita") %></span>
                                </div>
                                <div class="agenda-card-body">
                                    <div class="d-flex align-items-center mb-3"> <%-- Added mb-3 --%>
                                        <div class='paciente-avatar'><i class='<%# Eval("IconoMascota") %>'></i></div>
                                        <div class="paciente-info">
                                            <div class="nombre"><%# Eval("NombrePaciente") %></div>
                                            <div class="details"><%# Eval("DetallesPaciente") %></div>
                                        </div>
                                    </div>
                                    <ul class="servicios-list">
                                        <li><i class='<%# Eval("IconoServ1") %>'></i> <%# Eval("Servicio1") %></li>
                                        <li><i class='<%# Eval("IconoServ2") %>'></i> <%# Eval("Servicio2") %></li>
                                    </ul>
                                </div>
                                <div class="agenda-card-footer text-end"> <%-- text-end instead of text-right --%>
                                    <asp:Button ID="btnVerExpediente" runat="server" Text="Ver Expediente" CommandName="VerExpediente" CommandArgument='<%# Eval("IdMascota") %>' CssClass="btn btn-outline-primary btn-sm" /> <%-- Added btn-sm --%>
                                </div>
                            </div>
                        </li>
                    </ItemTemplate>
                    <FooterTemplate>
                        <div class="alert alert-light text-center mt-4"> <%-- alert-light for subtle message --%>
                            <i class="fas fa-info-circle me-1"></i> No hay citas que mostrar para este día o filtro.
                        </div>
                    </FooterTemplate>
                </asp:Repeater>
            </ul>
        </div>
    </div>
</asp:Content>

<asp:Content ID="ContentScripts" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>