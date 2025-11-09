<%-- Nombre del archivo: Secretaria_Dashboard.aspx --%>
<%@ Page Title="Dashboard" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true" 
    CodeBehind="Secretaria_Dashboard.aspx.cs" 
    Inherits="SoftPetWA.Secretaria_Dashboard" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Dashboard
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/dashboard.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="container-fluid">
        <h1 class="mt-4">¡Hola, <asp:Label ID="lblNombreSecretaria" runat="server" Text="..."></asp:Label>!</h1>
        <p class="text-muted mb-4">Gestiona todas las operaciones de la clínica desde un solo lugar</p>

        <%-- Resumen de Tarjetas (Controles Label) --%>
        <div class="row">
            
            <%-- Tarjeta 1 (Citas) - HTML con clases personalizadas --%>
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="card shadow-sm border-0 h-100 py-2">
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <div class="text-xs font-weight-bold text-primary-custom text-uppercase mb-1">Citas Hoy</div>
                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                    <asp:Label ID="lblCitasHoy" runat="server" Text="0"></asp:Label>
                                </div>
                            </div>
                            <div class="col-auto">
                                <div class="icon-circle bg-primary-soft">
                                    <i class="fas fa-calendar-check text-primary-custom"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%-- Tarjeta 2 (Ingresos) - HTML con clases personalizadas --%>
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="card shadow-sm border-0 h-100 py-2">
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <div class="text-xs font-weight-bold text-warning-custom text-uppercase mb-1">Ingresos Hoy</div>
                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                    <asp:Label ID="lblIngresosHoy" runat="server" Text="S/ 0.00"></asp:Label>
                                </div>
                            </div>
                            <div class="col-auto">
                                <div class="icon-circle bg-warning-soft">
                                    <i class="fas fa-dollar-sign text-warning-custom"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%-- Tarjeta 3 (Veterinarios) - HTML con clases personalizadas --%>
            <div class="col-lg-3 col-md-6 mb-4">
                <div class="card shadow-sm border-0 h-100 py-2">
                    <div class="card-body">
                        <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                                <div class="text-xs font-weight-bold text-purple-custom text-uppercase mb-1">Veterinarios Activos</div>
                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                    <asp:Label ID="lblVeterinariosActivos" runat="server" Text="0"></asp:Label>
                                </div>
                            </div>
                            <div class="col-auto">
                                <div class="icon-circle bg-purple-soft">
                                    <i class="fas fa-user-md text-purple-custom"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%-- Resumen Financiero (Control Repeater) --%>
            <div class="col-lg-3 col-md-6 mb-4 d-none d-lg-block">
                <div class="card shadow-sm border-0 h-100 py-2" style="background-color: #f0f2f5;">
                    <div class="card-body">
                         <h6 class="font-weight-bold mb-3"><i class="fas fa-chart-line me-2"></i>Resumen Financiero Hoy</h6>
                        <asp:Repeater ID="rptResumenFinanciero" runat="server">
                            <ItemTemplate>
                                <div class="d-flex justify-content-between">
                                    <span><%# Eval("Concepto") %></span>
                                    <span><%# Eval("MontoFormateado") %></span>
                                 </div>
                            </ItemTemplate>
                        </asp:Repeater>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <%-- Agenda del Día (Control Repeater) --%>
            <div class="col-lg-8 mb-4">
                <div class="card shadow-sm border-0">
                    <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                        <h6 class="m-0 font-weight-bold text-primary"><i class="fas fa-calendar-day me-2"></i>Agenda del Día</h6>
                        <a href="#" class="btn btn-link btn-sm">Ver todo el calendario <i class="fas fa-arrow-right ms-1"></i></a>
                    </div>
                    <div class="card-body">
                        <asp:Repeater ID="rptAgendaDia" runat="server">
                            <ItemTemplate>
                                <div class="d-flex align-items-center mb-3 p-3 bg-light rounded shadow-sm">
                                    <span class="badge bg-primary me-3"><%# Eval("Hora") %></span>
                                    <div>
                                        <h6 class="mb-0"><%# Eval("NombreMascota") %> - <%# Eval("Servicio") %></h6>
                                        <small class="text-muted"><%# Eval("NombreCliente") %> • <%# Eval("NombreVeterinario") %></small>
                                    </div>
                                    <span class="badge <%# Eval("CssEstado") %> ms-auto"><%# Eval("Estado") %></span>
                                </div>
                            </ItemTemplate>
                        </asp:Repeater>
                    </div>
                </div>
            </div>

            <%-- Estado de Veterinarios (Control Repeater) --%>
            <div class="col-lg-4 mb-4">
                <div class="card shadow-sm border-0">
                    <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                        <h6 class="m-0 font-weight-bold text-primary"><i class="fas fa-user-md me-2"></i>Estado de Veterinarios</h6>
                        <a href="#" class="btn btn-link btn-sm">Ver todos</a>
                    </div>
                    <div class="card-body">
                        <asp:Repeater ID="rptEstadoVeterinarios" runat="server">
                            <ItemTemplate>
                                <div class="d-flex align-items-center mb-3">
                                    <div class="user-avatar-list me-3" style='background-color: <%# Eval("ColorAvatar") %>;'><%# Eval("Iniciales") %></div>
                                    <div>
                                        <h6 class="mb-0"><%# Eval("NombreCompleto") %></h6>
                                        <small class="text-muted"><%# Eval("Especialidad") %></small>
                                    </div>
                                    <span class="badge <%# Eval("CssEstado") %> ms-auto"><i class="fas fa-circle me-1"></i><%# Eval("Estado") %></span>
                                </div>
                            </ItemTemplate>
                        </asp:Repeater>
                    </div>
                </div>
            </div>
        </div>
    </div>

</asp:Content>