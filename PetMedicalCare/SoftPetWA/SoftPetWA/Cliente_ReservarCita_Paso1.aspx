<%-- Nombre del archivo: Cliente_ReservarCita_Paso1.aspx --%>
<%@ Page Title="Reservar Cita - Mascota" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true" 
    CodeBehind="Cliente_ReservarCita_Paso1.aspx.cs" 
    Inherits="SoftPetWA.Cliente_ReservarCita_Paso1" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Reservar Cita - Mascota
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/reservarcita.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <%-- Botón Volver --%>
    <asp:LinkButton ID="btnVolver" runat="server" CssClass="btn btn-link mb-3" OnClick="btnCancelar_Click">
        <i class="fas fa-arrow-left me-2"></i>Reservar Nueva Cita
    </asp:LinkButton>

    <div class="card shadow-sm border-0">
        <div class="card-body p-4">

            <%-- 1. Stepper de Pasos --%>
            <div class="stepper-wrapper mb-5">
                <div class="step-item active"> <%-- Marcamos el Paso 1 como activo --%>
                    <div class="step-circle">1</div>
                    <span class="step-label">Mascota</span>
                </div>
                <div class="step-item">
                    <div class="step-circle">2</div>
                    <span class="step-label">Servicio</span>
                </div>
                <div class="step-item">
                    <div class="step-circle">3</div>
                    <span class="step-label">Fecha y Hora</span>
                </div>
                <div class="step-item">
                    <div class="step-circle">4</div>
                    <span class="step-label">Confirmación</span>
                </div>
            </div>

            <%-- 2. Sección de Selección de Mascota --%>
            <div class="mb-4">
                <h4><i class="fas fa-paw me-2 text-primary"></i>Selecciona la Mascota</h4>
                <div class="alert alert-primary" role="alert">
                    <i class="fas fa-info-circle me-2"></i>Selecciona la mascota para la cual deseas agendar la cita
                </div>
            </div>

            <div class="row mb-4">
                <asp:Repeater ID="rptMascotas" runat="server" OnItemCommand="rptMascotas_ItemCommand">
                    <ItemTemplate>
                        <div class="col-md-3">
                            <%-- Usaremos un LinkButton para poder capturar el click en el backend --%>
                            <asp:LinkButton ID="btnSelectPet" runat="server" 
                                CssClass="pet-selection-card" 
                                CommandName="SelectPet" 
                                CommandArgument='<%# Eval("MascotaID") %>'>
                                <div class="pet-selection-avatar">
                                    <asp:Image ID="imgAvatarPet" runat="server" ImageUrl='<%# Eval("AvatarURL") %>' />
                                </div>
                                <h6 class="pet-selection-name"><%# Eval("Nombre") %></h6>
                                <span class="pet-selection-breed d-block"><%# Eval("EspecieRaza") %></span>
                            </asp:LinkButton>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>
            </div>

            <%-- 3. Botones de Navegación --%>
            <div class="d-flex justify-content-between">
                <asp:LinkButton ID="btnCancelar" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnCancelar_Click">
                    <i class="fas fa-times me-2"></i>Cancelar
                </asp:LinkButton>
                <asp:LinkButton ID="btnSiguiente" runat="server" CssClass="btn btn-primary" OnClick="btnSiguiente_Click">
                    Siguiente<i class="fas fa-arrow-right ms-2"></i>
                </asp:LinkButton>
            </div>

        </div>
    </div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <%-- Script para simular la selección visual de la mascota --%>
    <script type="text/javascript">
        $(document).ready(function () {
            // Cuando se hace clic en una tarjeta de mascota
            $('.pet-selection-card').click(function (e) {
                // Quita la clase 'selected' de todas las tarjetas
                $('.pet-selection-card').removeClass('selected');
                // Añade la clase 'selected' solo a la tarjeta clicada
                $(this).addClass('selected');
        // (Opcional) Guardar el ID seleccionado en un campo oculto si no se usa el CommandArgument
                // $('#<%--= hfSelectedPetId.ClientID --%>').val($(this).data('petid')); 
            });
        });
    </script>
</asp:Content>