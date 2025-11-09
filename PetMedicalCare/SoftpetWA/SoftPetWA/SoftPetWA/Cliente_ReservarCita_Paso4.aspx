<%-- Nombre del archivo: Cliente_ReservarCita_Paso4.aspx --%>
<%@ Page Title="Reservar Cita - Confirmación" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true" 
    CodeBehind="Cliente_ReservarCita_Paso4.aspx.cs" 
    Inherits="SoftPetWA.Cliente_ReservarCita_Paso4" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Reservar Cita - Confirmación
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Reutilizamos el CSS del Paso 1 y añadimos el de confirmación --%>
    <link href="Content/reservarcita.css" rel="stylesheet" />
    <link href="Content/reservarcita-confirmacion.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <%-- Botón Volver --%>
    <a href="Cliente_ReservarCita_Paso3.aspx" class="btn btn-link mb-3 text-decoration-none text-muted">
        <i class="fas fa-arrow-left me-2"></i>Reservar Nueva Cita
    </a>

    <%-- Stepper de Pasos (ahora en su propio contenedor) --%>
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
                <div class="step-item active">
                    <div class="step-circle">4</div><span class="step-label">Confirmación</span>
                </div>
            </div>
        </div>
    </div>

    <%-- Contenido principal de la confirmación --%>
    <div class="card shadow-sm border-0">
        <div class="card-body p-4">
            <div class="mb-4">
                <h4><i class="fas fa-check-circle me-2 text-primary"></i>Confirmar Reserva</h4>
            </div>

            <div class="row">
                <%-- Columna Izquierda: Resumen (SIN la tarjeta anidada) --%>
                <div class="col-lg-8">
                    <h5 class="mb-3 fw-bold"><i class="fas fa-receipt me-2"></i>Resumen de la Cita</h5>
                    
                    <ul class="confirmation-summary">
                        <li>
                            <span class="label"><i class="fas fa-paw"></i>Mascota:</span>
                            <span class="value"><asp:Label ID="lblMascotaSeleccionada" runat="server">Max (Golden Retriever)</asp:Label></span>
                        </li>
                        <li>
                            <span class="label"><i class="fas fa-user-md"></i>Veterinario:</span>
                            <span class="value"><asp:Label ID="lblVeterinarioSeleccionado" runat="server">Dr. García López</asp:Label></span>
                        </li>
                        <li>
                            <span class="label"><i class="fas fa-calendar-day"></i>Fecha:</span>
                            <span class="value"><asp:Label ID="lblFechaSeleccionada" runat="server">Martes, 15 de Octubre 2024</asp:Label></span>
                        </li>
                        <li>
                            <span class="label"><i class="fas fa-clock"></i>Hora:</span>
                            <span class="value"><asp:Label ID="lblHoraSeleccionada" runat="server">11:00 AM</asp:Label></span>
                        </li>
                        <li>
                            <span class="label align-items-start"><i class="fas fa-briefcase-medical"></i>Servicios:</span>
                            <span class="value">
                                <ul class="service-list">
                                    <asp:Repeater ID="rptServiciosSeleccionados" runat="server">
                                        <ItemTemplate>
                                            <li><%# Eval("NombrePrecio") %></li>
                                        </ItemTemplate>
                                    </asp:Repeater>
                                </ul>
                            </span>
                        </li>
                        <li class="total-row">
                            <span class="label">TOTAL A PAGAR:</span>
                            <span class="value"><asp:Label ID="lblTotalAPagar" runat="server">S/ 80.00</asp:Label></span>
                        </li>
                    </ul>
                </div>

                <%-- Columna Derecha: Notas y Alerta --%>
                <div class="col-lg-4">
                    <div class="notes-section mb-3">
                        <label for="<%= txtNotas.ClientID %>" class="form-label">Notas Adicionales (Opcional)</label>
                        <asp:TextBox ID="txtNotas" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="4" placeholder="Ej: Mi mascota tiene alergia a..."></asp:TextBox>
                        <div class="form-text">Indica aquí cualquier información relevante para la cita.</div>
                    </div>

                    <div class="alert confirmation-alert" role="alert">
                        <i class="fas fa-info-circle me-2"></i>Recibirás un correo de confirmación con los detalles de tu cita. Por favor llega 10 minutos antes de tu cita.
                    </div>
                </div>
            </div>

            <%-- Botones de Navegación --%>
            <div class="d-flex justify-content-between mt-5">
                <asp:LinkButton ID="btnAnterior" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnAnterior_Click">
                    <i class="fas fa-arrow-left me-2"></i>Anterior
                </asp:LinkButton>
                <asp:LinkButton ID="btnConfirmarReserva" runat="server" CssClass="btn btn-success" OnClick="btnConfirmarReserva_Click">
                    <i class="fas fa-check me-2"></i>Confirmar Reserva
                </asp:LinkButton>
            </div>

        </div>
    </div>

</asp:Content>  