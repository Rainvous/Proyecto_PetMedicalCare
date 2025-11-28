<%@ Page Title="Mis Mascotas" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true"
    CodeBehind="Cliente_Mascotas.aspx.cs"
    Inherits="SoftPetWA.Cliente_Mascotas" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Mis Mascotas
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/clientemascotas.css" rel="stylesheet" />
    <style>
        /* Card Styling */
        .pet-card-cliente {
            background: white; border-radius: 15px; box-shadow: 0 4px 20px rgba(0,0,0,0.05);
            transition: transform 0.3s ease, box-shadow 0.3s ease; padding: 2rem 1.5rem;
            text-align: center; border: 1px solid #f0f0f0; height: 100%; position: relative; overflow: hidden;
        }
        .pet-card-cliente:hover { transform: translateY(-5px); box-shadow: 0 10px 25px rgba(0,151,167,0.15); border-color: #b2ebf2; }
        .pet-card-cliente::before { content: ''; position: absolute; top: 0; left: 0; width: 100%; height: 5px; background: linear-gradient(90deg, #00bcd4, #0097a7); }
        
        /* Avatar */
        .pet-avatar-lg { width: 100px; height: 100px; margin: 0 auto 1.5rem; border-radius: 50%; background-color: #f8f9fa; display: flex; align-items: center; justify-content: center; border: 4px solid white; box-shadow: 0 5px 15px rgba(0,0,0,0.1); overflow: hidden; }
        .pet-avatar-lg img { width: 100%; height: 100%; object-fit: cover; }
        
        /* Info */
        .pet-name-lg { font-weight: 800; color: #333; margin-bottom: 0.25rem; font-size: 1.3rem; }
        .pet-breed-lg { color: #777; font-size: 0.95rem; margin-bottom: 1rem; font-style: italic; }
        .pet-badges { display: flex; justify-content: center; gap: 10px; margin-bottom: 1.5rem; }
        .pet-badge { font-size: 0.85rem; padding: 5px 12px; border-radius: 20px; background-color: #e0f7fa; color: #006064; font-weight: 600; display: flex; align-items: center; gap: 5px; }
        
        /* Actions */
        .btn-group-actions { display: flex; gap: 10px; justify-content: center; }
        .btn-historial-cliente { background-color: #e3f2fd; color: #1976d2; border: none; padding: 8px 16px; border-radius: 8px; font-weight: 600; font-size: 0.9rem; transition: all 0.2s; text-decoration: none; }
        .btn-historial-cliente:hover { background-color: #1976d2; color: white; }
        .btn-editar-pet { background-color: #fff3e0; color: #f57c00; border: none; padding: 8px 12px; border-radius: 8px; font-weight: 600; transition: all 0.2s; }
        .btn-editar-pet:hover { background-color: #f57c00; color: white; }

        /* Validation */
        .val-error { color: #dc3545; font-size: 0.85em; margin-top: 4px; display: block; text-align: left; }
        .is-invalid { border-color: #dc3545 !important; }
        .form-select[disabled], .form-control[readonly] { background-color: #e9ecef; cursor: not-allowed; }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <%-- UpdatePanel principal para la lista --%>
    <asp:UpdatePanel ID="updPanelMascotas" runat="server" UpdateMode="Conditional">
        <ContentTemplate>
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h3 class="fw-bold text-dark mb-1"><i class="fas fa-paw me-2 text-primary"></i>Mis Mascotas</h3>
                    <p class="text-muted mb-0">Gestiona el perfil y salud de tus engreídos.</p>
                </div>
                <asp:LinkButton ID="btnNuevaMascota" runat="server" CssClass="btn btn-primary rounded-pill px-4 shadow-sm" OnClick="btnNuevaMascota_Click">
                    <i class="fas fa-plus me-2"></i>Registrar Nueva
                </asp:LinkButton>
            </div>

            <div class="row g-4">
                <asp:Repeater ID="rptMascotasCliente" runat="server" OnItemCommand="rptMascotasCliente_ItemCommand">
                    <ItemTemplate>
                        <div class="col-lg-4 col-md-6">
                            <div class="pet-card-cliente">
                                <div class="pet-avatar-lg">
                                    <asp:Panel ID="pnlImg" runat="server" Visible='<%# !string.IsNullOrEmpty(Eval("AvatarURL").ToString()) %>'>
                                        <asp:Image ID="imgAvatarPetLg" runat="server" ImageUrl='<%# Eval("AvatarURL") %>' />
                                    </asp:Panel>
                                    <asp:Panel ID="pnlIcon" runat="server" Visible='<%# string.IsNullOrEmpty(Eval("AvatarURL").ToString()) %>'>
                                        <i class="fas fa-paw fa-3x text-secondary opacity-50"></i>
                                    </asp:Panel>
                                </div>
                                <h4 class="pet-name-lg"><%# Eval("Nombre") %></h4>
                                <span class="pet-breed-lg d-block"><%# Eval("EspecieRaza") %></span>
                                <div class="pet-badges">
                                    <span class="pet-badge" title="Sexo"><i class="fas <%# Eval("SexoIcon") %>"></i> <%# Eval("SexoTexto") %></span>
                                    <span class="pet-badge" title="Color" style="background-color: #f3e5f5; color: #7b1fa2;"><i class="fas fa-palette"></i> <%# Eval("Color") %></span>
                                </div>
                                <div class="btn-group-actions">
                                    <%-- JS Abre Modal de Edición --%>
                                    <button type="button" class="btn btn-editar-pet" title="Editar Información"
                                        onclick='abrirModalEdicion(this)'
                                        data-id='<%# Eval("MascotaID") %>'
                                        data-nombre='<%# Eval("Nombre") %>'
                                        data-especie='<%# Eval("Especie") %>'
                                        data-raza='<%# Eval("Raza") %>'
                                        data-sexo='<%# Eval("Sexo") %>'
                                        data-color='<%# Eval("Color") %>'>
                                        <i class="fas fa-pencil-alt"></i>
                                    </button>
                                    <asp:LinkButton ID="btnVerHistorial" runat="server" CssClass="btn btn-historial-cliente"
                                        CommandName="VerHistorial" CommandArgument='<%# Eval("MascotaID") %>'>
                                         <i class="fas fa-notes-medical me-1"></i>Historial
                                    </asp:LinkButton>
                                </div>
                            </div>
                        </div>
                    </ItemTemplate>
                    <FooterTemplate>
                        <asp:Panel ID="pnlNoMascotas" runat="server" Visible='<%# rptMascotasCliente.Items.Count == 0 %>'>
                            <div class="col-12 text-center py-5">
                                <img src="Images/empty-pets.svg" alt="No pets" style="width: 150px; opacity: 0.5;" onerror="this.style.display='none'"/>
                                <h5 class="mt-3 text-secondary">No tienes mascotas registradas</h5>
                                <p class="text-muted">¡Registra a tu primer compañero ahora!</p>
                            </div>
                        </asp:Panel>
                    </FooterTemplate>
                </asp:Repeater>
            </div>
        </ContentTemplate>
    </asp:UpdatePanel>

    <%-- MODAL MASCOTA --%>
    <div class="modal fade" id="modalMascota" tabindex="-1" aria-hidden="true" data-bs-backdrop="static">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content border-0 shadow">
                <div class="modal-header bg-light border-bottom-0 pb-0">
                    <h5 class="modal-title fw-bold" id="modalMascotaLabel">Datos de Mascota</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <asp:UpdatePanel ID="updModalMascota" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body p-4">
                            <asp:Literal ID="litMensaje" runat="server"></asp:Literal>
                            <asp:HiddenField ID="hdMascotaID" runat="server" Value="0" />

                            <div class="mb-3">
                                <label class="form-label small fw-bold text-secondary">Nombre *</label>
                                <asp:TextBox ID="txtModalNombre" runat="server" CssClass="form-control"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="valNom" runat="server" ControlToValidate="txtModalNombre" ErrorMessage="Ingrese el nombre." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RequiredFieldValidator>
                                <asp:RegularExpressionValidator ID="regNom" runat="server" ControlToValidate="txtModalNombre" ValidationExpression="^[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9\s]+$" ErrorMessage="Solo letras y números." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RegularExpressionValidator>
                            </div>

                            <div class="row g-3 mb-3">
                                <div class="col-6">
                                    <label class="form-label small fw-bold text-secondary">Especie</label>
                                    <asp:DropDownList ID="ddlModalEspecie" runat="server" CssClass="form-select">
                                        <asp:ListItem Value="Perro">Perro</asp:ListItem>
                                        <asp:ListItem Value="Gato">Gato</asp:ListItem>
                                        <asp:ListItem Value="Otro">Otro</asp:ListItem>
                                    </asp:DropDownList>
                                    <small class="text-muted edicion-hint" style="font-size: 0.75rem; display:none;">(Solo editable al crear)</small>
                                </div>
                                <div class="col-6">
                                    <label class="form-label small fw-bold text-secondary">Sexo</label>
                                    <asp:DropDownList ID="ddlModalSexo" runat="server" CssClass="form-select">
                                        <asp:ListItem Value="M">Macho</asp:ListItem>
                                        <asp:ListItem Value="H">Hembra</asp:ListItem>
                                    </asp:DropDownList>
                                    <small class="text-muted edicion-hint" style="font-size: 0.75rem; display:none;">(Solo editable al crear)</small>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label small fw-bold text-secondary">Raza *</label>
                                <asp:TextBox ID="txtModalRaza" runat="server" CssClass="form-control"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="valRaza" runat="server" ControlToValidate="txtModalRaza" ErrorMessage="Ingrese la raza." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RequiredFieldValidator>
                            </div>

                            <div class="mb-3">
                                <label class="form-label small fw-bold text-secondary">Color *</label>
                                <asp:TextBox ID="txtModalColor" runat="server" CssClass="form-control"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="valColor" runat="server" ControlToValidate="txtModalColor" ErrorMessage="Ingrese el color." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RequiredFieldValidator>
                            </div>
                        </div>
                        <div class="modal-footer border-top-0 pt-0">
                            <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancelar</button>
                            <asp:Button ID="btnGuardarMascota" runat="server" Text="Guardar Cambios" CssClass="btn btn-primary px-4" OnClick="btnGuardarMascota_Click" ValidationGroup="GuardarMascota" OnClientClick="if(!Page_ClientValidate('GuardarMascota')){ applyBootstrapValidation(); return false; }" />
                        </div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script type="text/javascript">

        // 1. FUNCIÓN PARA MODO EDICIÓN
        function abrirModalEdicion(btn) {
            var id = btn.getAttribute('data-id');
            var nombre = btn.getAttribute('data-nombre');
            var especie = btn.getAttribute('data-especie');
            var raza = btn.getAttribute('data-raza');
            var sexo = btn.getAttribute('data-sexo');
            var color = btn.getAttribute('data-color');

            $('#<%= hdMascotaID.ClientID %>').val(id);
            $('#<%= txtModalNombre.ClientID %>').val(nombre);
            $('#<%= ddlModalEspecie.ClientID %>').val(especie);
            $('#<%= txtModalRaza.ClientID %>').val(raza);
            $('#<%= ddlModalSexo.ClientID %>').val(sexo);
            $('#<%= txtModalColor.ClientID %>').val(color);

            // Bloqueo para edición
            $('#<%= ddlModalEspecie.ClientID %>').prop('disabled', true);
            $('#<%= ddlModalSexo.ClientID %>').prop('disabled', true);
            $('.edicion-hint').show(); // Mostrar mensaje "(Solo editable al crear)"

            $('#modalMascotaLabel').text('Editar Mascota');
            new bootstrap.Modal(document.getElementById('modalMascota')).show();
        }

        // 2. FUNCIÓN PARA MODO NUEVO (Limpieza forzada)
        function prepararModalNuevo() {
            // Limpiar valores
            $('#<%= hdMascotaID.ClientID %>').val("0");
            $('#<%= txtModalNombre.ClientID %>').val("");
            $('#<%= txtModalRaza.ClientID %>').val("");
            $('#<%= txtModalColor.ClientID %>').val("");
            $('#<%= ddlModalEspecie.ClientID %>').prop('selectedIndex', 0);
            $('#<%= ddlModalSexo.ClientID %>').prop('selectedIndex', 0);

            // Desbloquear todo
            $('#<%= ddlModalEspecie.ClientID %>').prop('disabled', false);
            $('#<%= ddlModalSexo.ClientID %>').prop('disabled', false);
            $('#<%= txtModalNombre.ClientID %>').prop('readonly', false);
            $('#<%= txtModalRaza.ClientID %>').prop('readonly', false);
            $('#<%= txtModalColor.ClientID %>').prop('readonly', false);

            $('.edicion-hint').hide(); // Ocultar mensaje hint
            $('.is-invalid').removeClass('is-invalid'); // Limpiar rojos
        }

        function applyBootstrapValidation() {
            if (typeof (Page_Validators) != "undefined") {
                for (var i = 0; i < Page_Validators.length; i++) {
                    var val = Page_Validators[i];
                    var ctrl = document.getElementById(val.controltovalidate);
                    if (ctrl) {
                        if (!val.isvalid) ctrl.classList.add("is-invalid");
                        else ctrl.classList.remove("is-invalid");
                    }
                }
            }
        }

        function mostrarExitoLocal(mensaje) {
            $('.modal').modal('hide');
            $('.modal-backdrop').remove();
            Swal.fire({
                icon: 'success',
                title: 'Éxito',
                text: mensaje,
                confirmButtonColor: '#0097a7'
            });
        }

        $(document).ready(function () {
            var prm = Sys.WebForms.PageRequestManager.getInstance();
            prm.add_endRequest(function (sender, e) {
                applyBootstrapValidation();
                var elem = sender._postBackSettings.sourceElement;
                if (elem && elem.id.includes("btnGuardarMascota") && $('.val-error').is(':visible') == false && $('#<%= litMensaje.ClientID %>').text() == "") {
                    $('#modalMascota').modal('hide');
                    $('.modal-backdrop').remove();
                }
            });
        });
    </script>
</asp:Content>