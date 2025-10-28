<%@ Page Title="Inicio Veterinario" Language="C#" MasterPageFile="~/Veterinario.Master" AutoEventWireup="true" CodeBehind="Veterinario_Inicio.aspx.cs" Inherits="SoftPetWA.Veterinario_Inicio" %>

<asp:Content ID="ContentTitulo" ContentPlaceHolderID="cphTitulo" runat="server">
    Inicio Veterinario
</asp:Content>

<asp:Content ID="ContentHead" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Enlaza los estilos específicos de las vistas --%>
    <link href="Content/GestionVeterinarios.css" rel="stylesheet" /> 
</asp:Content>

<asp:Content ID="ContentMain" ContentPlaceHolderID="cphContenido" runat="server">
    <%-- Contenido del Dashboard --%>
    <div class="row mb-4"> 
        <div class="col-md-12">
            <h1>¡Hola, <asp:Literal ID="litNombreVeterinario" runat="server" Text="Rony"></asp:Literal>!</h1>
            <p class="lead text-muted">Tienes <asp:Literal ID="litNumCitas" runat="server" Text="6"></asp:Literal> citas programadas para hoy. ¡Que tengas un excelente día!</p>
        </div>
    </div>
    <div class="row mb-4">
        <div class="col-lg-4 col-md-6 mb-4"><div class="card shadow-sm summary-card border-left-primary h-100"><div class="card-body"><div class="row no-gutters align-items-center"><div class="col mr-2"><div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Citas Hoy</div><div class="h5 mb-0 font-weight-bold text-gray-800"><asp:Literal ID="litCitasHoy" runat="server" Text="6"></asp:Literal></div></div><div class="col-auto"><i class="fas fa-calendar-day fa-2x text-gray-300"></i></div></div></div></div></div>
        <div class="col-lg-4 col-md-6 mb-4"><div class="card shadow-sm summary-card border-left-warning h-100"><div class="card-body"><div class="row no-gutters align-items-center"><div class="col mr-2"><div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Citas Pendientes</div><div class="h5 mb-0 font-weight-bold text-gray-800"><asp:Literal ID="litCitasPendientes" runat="server" Text="3"></asp:Literal></div></div><div class="col-auto"><i class="fas fa-hourglass-half fa-2x text-gray-300"></i></div></div></div></div></div>
    </div>
    <div class="row">
        <div class="col-lg-8">
            <div class="d-flex justify-content-between align-items-center mb-3">
                 <h3 class="mb-0"><i class="far fa-calendar-alt me-2"></i> Citas de Hoy</h3>
                 <asp:LinkButton ID="btnVerAgendaCompleta" runat="server" PostBackUrl="~/Veterinario_Agenda.aspx" CssClass="btn btn-sm btn-outline-primary">Ver agenda completa <i class="fas fa-arrow-right ms-1"></i></asp:LinkButton>
            </div>
            <hr class="mt-0 mb-3"/>
            <asp:Repeater ID="rptrCitas" runat="server" OnItemCommand="rptrCitas_ItemCommand">
                <ItemTemplate><div class='card cita-card mb-3 <%# Eval("CardClass") %>'><div class="cita-header"><span class="cita-time"><i class="far fa-clock me-1"></i> <%# Eval("HoraCita") %></span><span class='cita-status <%# Eval("EstadoCss") %>'><%# Eval("EstadoCita") %></span></div><div class="cita-body"><div class="d-flex justify-content-between align-items-start"><div><h4 class="cita-paciente"><%# Eval("NombrePaciente") %></h4><p class="cita-details mb-1"><%# Eval("DetallesPaciente") %></p><p class="cita-owner"><i class="far fa-user me-1"></i> <%# Eval("NombreCliente") %></p></div><span class='cita-tag <%# Eval("TipoCitaCss") %>'><%# Eval("TipoCita") %></span></div><hr class="my-3"/><asp:Button ID="btnAccionPrincipal" runat="server" Text='<%# Eval("BotonPrincipalTexto") %>' CommandName="Atender" CommandArgument='<%# Eval("IdCita") %>' CssClass="btn btn-primary btn-sm" /><asp:Button ID="btnAccionSecundaria" runat="server" Text='<%# Eval("BotonSecundarioTexto") %>' CommandName="Historial" CommandArgument='<%# Eval("IdMascota") %>' Visible='<%# Eval("BotonSecundarioVisible") %>' CssClass="btn btn-outline-secondary btn-sm ms-2" /></div></div></ItemTemplate> <%-- Added btn-sm and ms-2 --%>
                <FooterTemplate><div class="alert alert-info mt-3"><i class="fas fa-check-circle me-1"></i> No tienes más citas programadas para hoy.</div></FooterTemplate>
            </asp:Repeater>
        </div>
        <div class="col-lg-4">
             <h3 class="mb-0"><i class="fas fa-chart-line me-2"></i> Resumen Semanal</h3><hr class="mt-3 mb-3"/>
             <div class="card shadow-sm semanal-card text-center mb-3"><div class="card-body"><div class="h3 font-weight-bold text-primary"><asp:Literal ID="litConsultasSemana" runat="server" Text="28"></asp:Literal></div><div class="text-muted">Consultas</div></div></div>
             <div class="card shadow-sm semanal-card text-center mb-3"><div class="card-body"><div class="h3 font-weight-bold text-success"><asp:Literal ID="litVacunasSemana" runat="server" Text="15"></asp:Literal></div><div class="text-muted">Vacunaciones</div></div></div>
             <div class="card shadow-sm semanal-card text-center mb-3"><div class="card-body"><div class="h3 font-weight-bold text-danger"><asp:Literal ID="litCirugiasSemana" runat="server" Text="5"></asp:Literal></div><div class="text-muted">Cirugías</div></div></div>
        </div>
    </div>
</asp:Content>

<asp:Content ID="ContentScripts" ContentPlaceHolderID="cphScripts" runat="server">
    <%-- No scripts specific to this page yet --%>
</asp:Content>