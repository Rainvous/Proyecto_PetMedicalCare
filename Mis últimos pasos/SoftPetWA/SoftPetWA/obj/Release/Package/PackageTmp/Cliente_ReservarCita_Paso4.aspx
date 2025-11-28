<%@ Page Title="Reservar Cita - Confirmación" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true"
    CodeBehind="Cliente_ReservarCita_Paso4.aspx.cs"
    Inherits="SoftPetWA.Cliente_ReservarCita_Paso4" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Reservar Cita - Confirmación
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/reservarcita.css" rel="stylesheet" />
    <link href="Content/reservarcita-confirmacion.css" rel="stylesheet" />

    <%-- ESTILOS DEL CÍRCULO DE CARGA (INTEGRADO AQUÍ) --%>
    <style>
        /* Pantalla completa semitransparente */
        .loading-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(255, 255, 255, 0.85); /* Blanco al 85% de opacidad */
            z-index: 9999; /* Muy alto para estar encima de todo */
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
        /* El círculo giratorio */
        .loading-spinner {
            width: 60px;
            height: 60px;
            border: 6px solid #f3f3f3; /* Gris claro */
            border-top: 6px solid #198754; /* Verde Success de Bootstrap */
            border-radius: 50%;
            animation: spin 1s linear infinite;
            margin-bottom: 15px;
        }
        /* Texto debajo del círculo */
        .loading-text {
            color: #198754;
            font-weight: bold;
            font-size: 1.2rem;
            font-family: sans-serif;
        }
        /* Animación de giro */
        @keyframes spin {
            0% {
                transform: rotate(0deg);
            }

            100% {
                transform: rotate(360deg);
            }
        }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">

    <%-- 1. COMPONENTE DE CARGA (Se muestra automáticamente al procesar) --%>
    <asp:UpdateProgress ID="updProgress" runat="server" AssociatedUpdatePanelID="updConfirmar">
        <ProgressTemplate>
            <div class="loading-overlay">
                <div class="loading-spinner"></div>
                <div class="loading-text">Guardando tu reserva...</div>
            </div>
        </ProgressTemplate>
    </asp:UpdateProgress>

    <%-- Botón Volver --%>
    <asp:LinkButton ID="btnVolver" runat="server" CssClass="btn btn-link mb-3 text-decoration-none text-muted" OnClick="btnAnterior_Click">
        <i class="fas fa-arrow-left me-2"></i>Reservar Nueva Cita
    </asp:LinkButton>

    <%-- Stepper --%>
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body p-4">
            <div class="stepper-wrapper">
                <div class="step-item active">
                    <div class="step-circle">1</div>
                    <span class="step-label">Mascota</span> </div>
                <div class="step-item active">
                    <div class="step-circle">2</div>
                    <span class="step-label">Servicio</span> </div>
                <div class="step-item active">
                    <div class="step-circle">3</div>
                    <span class="step-label">Fecha y Hora</span> </div>
                <div class="step-item active">
                    <div class="step-circle">4</div>
                    <span class="step-label">Confirmación</span> </div>
            </div>
        </div>
    </div>

    <%-- Contenido Principal --%>
    <div class="card shadow-sm border-0">
        <div class="card-body p-4">
            <div class="mb-4">
                <h4><i class="fas fa-check-circle me-2 text-primary"></i>Confirmar Reserva</h4>
            </div>

            <div class="row">
                <%-- Resumen --%>
                <div class="col-lg-8">
                    <h5 class="mb-3 fw-bold"><i class="fas fa-receipt me-2"></i>Resumen de la Cita</h5>
                    <ul class="confirmation-summary">
                        <li><span class="label"><i class="fas fa-paw"></i>Mascota:</span> <span class="value">
                            <asp:Label ID="lblMascotaSeleccionada" runat="server"></asp:Label></span></li>
                        <li><span class="label"><i class="fas fa-user-md"></i>Veterinario:</span> <span class="value">
                            <asp:Label ID="lblVeterinarioSeleccionado" runat="server"></asp:Label></span></li>
                        <li><span class="label"><i class="fas fa-calendar-day"></i>Fecha:</span> <span class="value">
                            <asp:Label ID="lblFechaSeleccionada" runat="server"></asp:Label></span></li>
                        <li><span class="label"><i class="fas fa-clock"></i>Hora:</span> <span class="value">
                            <asp:Label ID="lblHoraSeleccionada" runat="server"></asp:Label></span></li>
                        <li><span class="label align-items-start"><i class="fas fa-briefcase-medical"></i>Servicios:</span>
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
                        <li class="total-row"><span class="label">TOTAL A PAGAR:</span> <span class="value">
                            <asp:Label ID="lblTotalAPagar" runat="server">S/ 0.00</asp:Label></span></li>
                    </ul>
                </div>

                <%-- Notas --%>
                <div class="col-lg-4">
                    <div class="notes-section mb-3">
                        <label for="<%= txtNotas.ClientID %>" class="form-label">Notas Adicionales (Opcional)</label>
                        <asp:TextBox ID="txtNotas" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="4" placeholder="Ej: Mi mascota tiene alergia a..."></asp:TextBox>
                    </div>
                    <div class="alert confirmation-alert" role="alert">
                        <i class="fas fa-info-circle me-2"></i>Recibirás un correo de confirmación. Por favor llega 10 minutos antes.
                    </div>
                </div>
            </div>

            <%-- 2. PANEL DE ACTUALIZACIÓN (Para que funcione el círculo sin recargar toda la página) --%>
            <asp:UpdatePanel ID="updConfirmar" runat="server">
                <ContentTemplate>
                    <div class="d-flex justify-content-between mt-5">
                        <asp:LinkButton ID="btnAnterior" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnAnterior_Click">
                            <i class="fas fa-arrow-left me-2"></i>Anterior
                        </asp:LinkButton>

                        <%-- Al hacer clic aquí, aparece el círculo --%>
                        <asp:LinkButton ID="btnConfirmarReserva" runat="server" CssClass="btn btn-success" OnClick="btnConfirmarReserva_Click">
                            <i class="fas fa-check me-2"></i>Confirmar Reserva
                        </asp:LinkButton>
                    </div>
                    <asp:Label ID="lblError" runat="server" CssClass="text-danger mt-2 d-block fw-bold"></asp:Label>
                </ContentTemplate>
            </asp:UpdatePanel>

        </div>
    </div>

    <%-- 3. MODAL DE ÉXITO (Se activará desde C#) --%>
    <div class="modal fade" id="modalExito" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content text-center p-4">
                <div class="modal-body">
                    <div class="mb-3">
                        <i class="fas fa-check-circle text-success" style="font-size: 4rem;"></i>
                    </div>
                    <h3 class="fw-bold mb-3">¡Guardado Satisfactoriamente!</h3>
                    <p class="text-muted">Tu cita ha sido registrada en el sistema.</p>
                    <div class="mt-4">
                        <%-- Botón para volver a la agenda --%>
                        <a href="Cliente_AgendaCitas.aspx" class="btn btn-success w-100 py-2">Aceptar</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</asp:Content>
