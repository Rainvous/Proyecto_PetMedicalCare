<%@ Page Title="Reservar Cita - Fecha y Hora" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true"
    CodeBehind="Cliente_ReservarCita_Paso3.aspx.cs"
    Inherits="SoftPetWA.Cliente_ReservarCita_Paso3" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Reservar Cita - Fecha y Hora
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/reservarcita.css" rel="stylesheet" />
    <%-- ESTILOS DEL CALENDARIO (INTEGRADOS AQUÍ PARA QUE FUNCIONE SÍ O SÍ) --%>
    <link href="Content/reservarcita-calendario.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <%-- 1. Contenedor del Stepper --%>
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body p-4">
            <div class="stepper-wrapper">
                <div class="step-item active">
                    <div class="step-circle">1</div><span class="step-label">Mascota</span>
                </div>
                <div class="step-item active">
                    <div class="step-circle">2</div><span class="step-label">Servicio</span>
                </div>
                <div class="step-item active">
                    <div class="step-circle">3</div><span class="step-label">Fecha y Hora</span>
                </div>
                <div class="step-item">
                    <div class="step-circle">4</div><span class="step-label">Confirmación</span>
                </div>
            </div>
        </div>
    </div>

    <%-- 2. Contenedor del Contenido Principal --%>
    <div class="card shadow-sm border-0">
        <div class="card-body p-4">
            
            <div class="mb-4">
                <h4><i class="fas fa-calendar-alt me-2 text-primary"></i>Selecciona Fecha y Hora</h4>
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

                            <%-- Contenedor del Calendario --%>
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
                                    <div class="day-header">Dom</div><div class="day-header">Lun</div><div class="day-header">Mar</div>
                                    <div class="day-header">Mié</div><div class="day-header">Jue</div><div class="day-header">Vie</div><div class="day-header">Sáb</div>

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
                    
                    <asp:Label ID="lblError" runat="server" CssClass="text-danger mt-2 fw-bold" Visible="false"></asp:Label>

                </ContentTemplate>
            </asp:UpdatePanel>

            <%-- BOTONES DE NAVEGACIÓN --%>
            <div class="d-flex justify-content-between mt-4">
                <asp:LinkButton ID="btnAnterior" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnAnterior_Click">
                    <i class="fas fa-arrow-left me-2"></i>Anterior
                </asp:LinkButton>
                <asp:LinkButton ID="btnSiguiente" runat="server" CssClass="btn btn-primary" OnClick="btnSiguiente_Click">
                    Siguiente<i class="fas fa-arrow-right ms-2"></i>
                </asp:LinkButton>
            </div>

        </div>
    </div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>