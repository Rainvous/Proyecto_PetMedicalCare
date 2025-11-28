<%@ Page Title="Reservar Cita - Confirmación" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_ReservarCita_Paso4.aspx.cs"
    Inherits="SoftPetWA.Secretaria_ReservarCita_Paso4" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Reservar Cita - Confirmación
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/reservarcita.css" rel="stylesheet" />
    <link href="Content/reservarcita-confirmacion.css" rel="stylesheet" />

    <%-- ESTILOS COMPLETOS: LOADING Y FIX DEL BOTÓN --%>
    <style>
        /* --- 1. Estilos del Loading --- */
        .loading-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(255, 255, 255, 0.85);
            z-index: 9999;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
        .loading-spinner {
            width: 60px;
            height: 60px;
            border: 6px solid #f3f3f3;
            border-top: 6px solid #198754; /* Verde */
            border-radius: 50%;
            animation: spin 1s linear infinite;
            margin-bottom: 15px;
        }
        .loading-text {
            color: #198754;
            font-weight: bold;
            font-size: 1.2rem;
            font-family: sans-serif;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

        /* --- 2. FIX PARA EL BOTÓN BLOQUEADO --- */
        /* Esto hace que la huella gigante sea "fantasma" y los clics pasen a través de ella */
        .modal-content::before {
            pointer-events: none; 
        }

        /* Esto asegura que el botón "Aceptar" esté físicamente encima de cualquier decoración */
        .modal-footer {
            position: relative;
            z-index: 10; 
        }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">

    <%-- 1. COMPONENTE DE CARGA (Se muestra al hacer PostBack en el UpdatePanel) --%>
    <asp:UpdateProgress ID="updProgress" runat="server" AssociatedUpdatePanelID="updConfirmar">
        <ProgressTemplate>
            <div class="loading-overlay">
                <div class="loading-spinner"></div>
                <div class="loading-text">Procesando reserva...</div>
            </div>
        </ProgressTemplate>
    </asp:UpdateProgress>

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
                <div class="step-item active">
                    <div class="step-circle">4</div>
                    <span class="step-label">Confirmación</span>
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
                <div class="col-lg-8">
                    <h5 class="mb-3 fw-bold"><i class="fas fa-receipt me-2"></i>Resumen de la Cita</h5>

                    <ul class="confirmation-summary">
                        <li>
                            <span class="label"><i class="fas fa-user"></i>Cliente:</span>
                            <span class="value"><asp:Label ID="lblClienteSeleccionado" runat="server">[Nombre del Cliente]</asp:Label></span>
                        </li>
                        <li>
                            <span class="label"><i class="fas fa-paw"></i>Mascota:</span>
                            <span class="value"><asp:Label ID="lblMascotaSeleccionada" runat="server"></asp:Label></span>
                        </li>
                        <li>
                            <span class="label"><i class="fas fa-user-md"></i>Veterinario:</span>
                            <span class="value"><asp:Label ID="lblVeterinarioSeleccionado" runat="server"></asp:Label></span>
                        </li>
                        <li>
                            <span class="label"><i class="fas fa-calendar-day"></i>Fecha:</span>
                            <span class="value"><asp:Label ID="lblFechaSeleccionada" runat="server"></asp:Label></span>
                        </li>
                        <li>
                            <span class="label"><i class="fas fa-clock"></i>Hora:</span>
                            <span class="value"><asp:Label ID="lblHoraSeleccionada" runat="server"></asp:Label></span>
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
                            <span class="value"><asp:Label ID="lblTotalAPagar" runat="server">S/ 0.00</asp:Label></span>
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

            <%-- 2. PANEL DE ACTUALIZACIÓN (Envuelve los botones para AJAX) --%>
            <asp:UpdatePanel ID="updConfirmar" runat="server">
                <ContentTemplate>
                    
                    <%-- Botones de Navegación --%>
                    <div class="d-flex justify-content-between mt-5">
                        <asp:LinkButton ID="btnAnterior" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnAnterior_Click">
                            <i class="fas fa-arrow-left me-2"></i>Anterior
                        </asp:LinkButton>
                        
                        <%-- Botón Confirmar (Activa el UpdateProgress) --%>
                        <asp:LinkButton ID="btnConfirmarReserva" runat="server" CssClass="btn btn-success" OnClick="btnConfirmarReserva_Click">
                            <i class="fas fa-check me-2"></i>Confirmar Reserva
                        </asp:LinkButton>
                    </div>
                    
                    <%-- Etiqueta de Error --%>
                    <asp:Label ID="lblError" runat="server" CssClass="text-danger mt-2 fw-bold d-block text-end"></asp:Label>

                </ContentTemplate>
            </asp:UpdatePanel>

        </div>
    </div>

    <%-- MODAL DE ÉXITO --%>
    <div class="modal fade" id="modalExito" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="modalExitoLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalExitoLabel">
                        <i class="fas fa-check-circle text-success me-2"></i>Cita Registrada
                    </h5>
                </div>
                <div class="modal-body">
                    ¡La cita se ha registrado exitosamente!
                </div>
                <div class="modal-footer">
                    <a id="btnOkExito" href="Secretaria_AgendaCitas.aspx" class="btn btn-primary">Aceptar</a>
                </div>
            </div>
        </div>
    </div>

</asp:Content>
