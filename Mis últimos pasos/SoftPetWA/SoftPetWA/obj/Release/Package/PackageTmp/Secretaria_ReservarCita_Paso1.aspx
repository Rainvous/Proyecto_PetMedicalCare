<%-- Nombre del archivo: Secretaria_ReservarCita_Paso1.aspx --%>

<%@ Page Title="Reservar Cita - Mascota" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_ReservarCita_Paso1.aspx.cs"
    Inherits="SoftPetWA.Secretaria_ReservarCita_Paso1" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Reservar Cita - Mascota
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/reservarcita.css" rel="stylesheet" />
    <link href="css_validaciones/validation-errors.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">

    <div class="card shadow-sm border-0">
        <div class="card-body p-4">

            <%-- 1. Stepper de Pasos --%>
            <div class="stepper-wrapper mb-5">
                <div class="step-item active">
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

                <%-- CAMBIO AÑADIDO: Mostrar para quién se agenda --%>
                <h5>Cliente:
                    <asp:Label ID="lblClienteNombre" runat="server" CssClass="text-primary fw-bold" Text="[Nombre del Cliente]" /></h5>

                <div class="alert alert-primary" role="alert">
                    <i class="fas fa-info-circle me-2"></i>Selecciona la mascota para la cual deseas agendar la cita
                </div>
            </div>

            <div class="row mb-4">
                <asp:Repeater ID="rptMascotas" runat="server"
                    OnItemCommand="rptMascotas_ItemCommand"
                    OnItemDataBound="rptMascotas_ItemDataBound">
                    <ItemTemplate>

                        <div class="col-md-3">
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
               <asp:LinkButton ID="btnSiguiente" runat="server" 
                    CssClass="btn btn-primary" 
                    OnClick="btnSiguiente_Click" 
                    OnClientClick="return validarSeleccionMascota();"> 
                    Siguiente<i class="fas fa-arrow-right ms-2"></i>
                </asp:LinkButton>
            </div>

        </div>
    </div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script type="text/javascript">
        // 1. Script visual para marcar la selección (Click)
        $(document).ready(function () {
            $('.pet-selection-card').click(function (e) {
                // Quitamos la clase 'selected' a todas
                $('.pet-selection-card').removeClass('selected');
                // Se la ponemos a la que se hizo clic
                $(this).addClass('selected');
            });
        });

        // 2. NUEVA VALIDACIÓN (Llamada por el botón Siguiente)
        // (Ya le quité los comentarios // para que funcione)
        function validarSeleccionMascota() {
            // Buscamos cuántos elementos tienen la clase 'pet-selection-card' Y 'selected' al mismo tiempo
            var seleccionados = $('.pet-selection-card.selected').length;

            if (seleccionados === 0) {
                // Si es 0, significa que el usuario no ha elegido nada
                var errores = ['Debes seleccionar una mascota para continuar.'];

                // Llamamos a la función "Puente" de la MasterPage
                if (typeof mostrarErroresCliente === 'function') {
                    mostrarErroresCliente(errores);
                } else {
                    alert(errores[0]); // Fallback por si acaso
                }

                // Retornamos false para CANCELAR el clic y que no vaya al servidor
                return false;
            }

            // Si hay selección, mostramos el loading y permitimos continuar
            if (typeof mostrarLoading === 'function') {
                mostrarLoading();
            }
            return true;
        }
    </script>
</asp:Content>