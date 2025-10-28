<%-- Nombre del archivo: Cliente_ReservarCita_Paso2.aspx --%>
<%@ Page Title="Reservar Cita - Servicio" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true" 
    CodeBehind="Cliente_ReservarCita_Paso2.aspx.cs" 
    Inherits="SoftPetWA.Cliente_ReservarCita_Paso2" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Reservar Cita - Servicio
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Reutilizamos el CSS del Paso 1 --%>
    <link href="Content/reservarcita.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <%-- 1. Botón Volver --%>
    <asp:LinkButton ID="btnVolver" runat="server" CssClass="btn btn-link mb-3 text-decoration-none text-muted" OnClick="btnAnterior_Click">
        <i class="fas fa-arrow-left me-2"></i>Reservar Nueva Cita
    </asp:LinkButton>

    <%-- 2. Contenedor del Stepper (En su propia tarjeta) --%>
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body p-4">
            <div class="stepper-wrapper">
                <div class="step-item active">
                    <div class="step-circle">1</div><span class="step-label">Mascota</span>
                </div>
                <div class="step-item active">
                    <div class="step-circle">2</div><span class="step-label">Servicio</span>
                </div>
                <div class="step-item">
                    <div class="step-circle">3</div><span class="step-label">Fecha y Hora</span>
                </div>
                <div class="step-item">
                    <div class="step-circle">4</div><span class="step-label">Confirmación</span>
                </div>
            </div>
        </div>
    </div>

    <%-- 3. Contenedor del Contenido Principal (En su propia tarjeta) --%>
    <div class="card shadow-sm border-0">
        <div class="card-body p-4">
            
            <%-- SECCIÓN DE SELECCIÓN DE SERVICIO --%>
            <div class="mb-4">
                <h4><i class="fas fa-briefcase-medical me-2 text-primary"></i>Selecciona el Servicio</h4>
                <div class="alert alert-primary" role="alert">
                    <i class="fas fa-info-circle me-2"></i>Puedes seleccionar uno o más servicios para tu cita
                </div>
            </div>

            <div class="row mb-4">
                <asp:Repeater ID="rptServicios" runat="server" OnItemCommand="rptServicios_ItemCommand">
                    <ItemTemplate>
                        <div class="col-md-4 mb-3">
                            <div class="service-selection-card">
                                <div class="service-checkbox-container">
                                    <asp:CheckBox ID="chkSelectService" runat="server" CssClass="form-check-input" />
                                </div>
                                <div class="service-info-container">
                                    <label for='<%# ((CheckBox)Container.FindControl("chkSelectService")).ClientID %>'>
                                        <span class="service-name d-block"><%# Eval("Nombre") %></span>
                                        <span class="service-desc d-block"><%# Eval("Descripcion") %></span>
                                        <span class="service-price d-block"><%# Eval("PrecioFormateado") %></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>
            </div>

            <%-- BOTONES DE NAVEGACIÓN --%>
            <div class="d-flex justify-content-between">
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
    <%-- Script para simular la selección visual del servicio --%>
    <script type="text/javascript">
        $(document).ready(function () {
            // Cuando se hace clic en una tarjeta de servicio (o su contenido)
            $('.service-selection-card').click(function (e) {
                // Previene que el clic en el checkbox active esto dos veces
                if (e.target.type !== 'checkbox') {
                    var checkbox = $(this).find('.form-check-input');
                    checkbox.prop('checked', !checkbox.prop('checked'));
                }
                // Añade o quita la clase 'selected' basado en el estado del checkbox
                if ($(this).find('.form-check-input').is(':checked')) {
                    $(this).addClass('selected');
                } else {
                    $(this).removeClass('selected');
                }
            });
            // También maneja el clic directo en el checkbox
            $('.service-selection-card .form-check-input').change(function () {
                 if ($(this).is(':checked')) {
                    $(this).closest('.service-selection-card').addClass('selected');
                } else {
                    $(this).closest('.service-selection-card').removeClass('selected');
                }
            });
        });
    </script>
</asp:Content>