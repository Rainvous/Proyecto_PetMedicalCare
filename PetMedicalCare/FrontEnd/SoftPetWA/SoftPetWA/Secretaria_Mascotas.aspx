<%@ Page Title="Gestión de Mascotas" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_Mascotas.aspx.cs"
    Inherits="SoftPetWA.Secretaria_Mascotas" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestión de Mascotas
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/mascotas.css" rel="stylesheet" />
    <style>
        /* Estilos de Tarjeta */
        .pet-avatar { background-color: #20c997;
            color: white; width: 80px; height: 80px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 15px auto;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        
        /* Paginación */
        .custom-pagination .page-item { margin: 0 3px; }
        .custom-pagination .page-link { border: none; border-radius: 50% !important; width: 35px; height: 35px;
            display: flex; align-items: center; justify-content: center; color: #6c757d; background-color: transparent; font-weight: 600; transition: all 0.2s;
        }
        .custom-pagination .page-link:hover { background-color: #e9ecef; color: #0097a7; }
        .custom-pagination .page-item.active .page-link { background-color: #0097a7; color: white;
            box-shadow: 0 2px 5px rgba(0, 151, 167, 0.3); }
        .pagination-container { background-color: #fff;
            border-radius: 0.5rem; box-shadow: 0 .125rem .25rem rgba(0,0,0,.075); padding: 1rem; }
        
        /* Validaciones y Readonly */
        .form-control[readonly], .form-select[disabled] { background-color: #e9ecef;
            opacity: 1; cursor: not-allowed; }
        .val-error { color: #dc3545; font-size: 0.875em; margin-top: 0.25rem;
            display: block; }
        .is-invalid { border-color: #dc3545 !important; }

        /* Modal Backdrop Oscuro */
        .modal-backdrop.show { opacity: 0.7 !important; }
        .z-index-high { z-index: 1060 !important; }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <asp:UpdatePanel ID="updPanelMascotas" runat="server" UpdateMode="Conditional">
        <ContentTemplate>
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3><i class="fas fa-paw me-2"></i>Gestión de Mascotas</h3>
            </div>

            <%-- FILTROS --%>
            <div class="card shadow-sm mb-4 filter-bar">
                <div class="card-body">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-3"><label class="form-label">Nombre de la Mascota</label><asp:TextBox ID="txtNombreMascota" runat="server" CssClass="form-control" placeholder="Buscar..."></asp:TextBox></div>
                        <div class="col-md-3"><label class="form-label">Propietario</label><asp:TextBox ID="txtPropietario" runat="server" CssClass="form-control" placeholder="Nombre del dueño..."></asp:TextBox></div>
                        <div class="col-md-2"><label class="form-label">Especie</label><asp:DropDownList ID="ddlEspecie" runat="server" CssClass="form-select"></asp:DropDownList></div>
                        <div class="col-md-2"><label class="form-label">Estado</label><asp:DropDownList ID="ddlFiltroEstado" runat="server" CssClass="form-select"><asp:ListItem Value="-1" Text="Todos" Selected="True"></asp:ListItem><asp:ListItem Value="1" Text="Activo"></asp:ListItem><asp:ListItem Value="0" Text="Inactivo"></asp:ListItem></asp:DropDownList></div>
                        <div class="col-md-2 d-flex"><asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary me-2" OnClick="btnBuscar_Click"><i class='fas fa-search me-1'></i> Buscar</asp:LinkButton><asp:LinkButton ID="btnLimpiar" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnLimpiar_Click"><i class='fas fa-times'></i> Limpiar</asp:LinkButton></div>
                    </div>
                </div>
            </div>

            <div class="d-flex justify-content-end align-items-center mb-3">
                <asp:LinkButton ID="btnNuevaMascota" runat="server" CssClass="btn btn-success" OnClick="btnNuevaMascota_Click"><i class='fas fa-plus me-2'></i>Nueva Mascota</asp:LinkButton>
            </div>

            <%-- LISTA GRID (OCULTA AL INICIO) --%>
            <div id="divListaMascotas" runat="server" visible="false">
                <asp:Repeater ID="rptMascotas" runat="server" OnItemCommand="rptMascotas_ItemCommand">
                    <HeaderTemplate><div class="row"></HeaderTemplate>
                    <ItemTemplate>
                        <div class="col-lg-4 col-md-6 mb-4">
                            <div class="pet-card">
                                <div class="pet-card-header">
                                    <span class="badge rounded-pill <%# (bool)Eval("Activo") ? "bg-success" : "bg-danger" %> pet-status-badge"><%# (bool)Eval("Activo") ? "Activo" : "Inactivo" %></span>
                                    <div class="pet-avatar"><i class="<%# GetIconoEspecie(Eval("Especie").ToString()) %> fa-3x"></i></div>
                                    <h3 class="pet-name"><%# Eval("Nombre") %></h3>
                                    <span class="pet-breed"><%# Eval("Especie") + " • " + Eval("Raza") %></span>
                                </div>
                                <div class="pet-card-body">
                                    <div class="pet-info-row"><span class="label"><i class="fas fa-venus-mars me-2"></i>Sexo</span><span class="value"><%# Eval("Sexo") %></span></div>
                                    <div class="pet-info-row"><span class="label"><i class="fas fa-palette me-2"></i>Color</span><span class="value"><%# Eval("Color") %></span></div>
                                    <div class="pet-owner-card mt-3 p-2 border rounded bg-light">
                                        <div class="d-flex align-items-center">
                                            <div class="owner-avatar me-2 d-flex align-items-center justify-content-center rounded-circle text-white" style='width: 35px;
                                                height: 35px; background-color: <%# GetAvatarColor(Eval("NombreDuenio").ToString()) %>;'><small><%# GetIniciales(Eval("NombreDuenio").ToString()) %></small></div>
                                            <div class="owner-info lh-1"><div class="fw-bold" style="font-size: 0.9rem;"><%# Eval("NombreDuenio") %></div><small class="text-muted"><%# Eval("TelefonoDuenio") %></small></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="pet-card-footer">
                                    <button type="button" class="pet-action-btn btn-editar" title="Editar"
                                        onclick='abrirModalJS(this, false)'
                                        data-id='<%# Eval("MascotaId") %>'
                                        data-nombre='<%# Eval("Nombre") %>'
                                        data-especie='<%# Eval("Especie") %>'
                                        data-raza='<%# Eval("Raza") %>'
                                        data-sexo='<%# Eval("Sexo") %>'
                                        data-color='<%# Eval("Color") %>'
                                        data-activo='<%# (bool)Eval("Activo") ? "true" : "false" %>'
                                        data-personaid='<%# Eval("PersonaId") %>'
                                        data-nombreduenio='<%# Eval("NombreDuenio") %>'>
                                        <i class="fas fa-pencil-alt"></i> Editar
                                    </button>

                                    <button type="button" class="pet-action-btn btn-eliminar" title="Eliminar"
                                        onclick="abrirModalEliminar('<%# Eval("MascotaId") %>', '<%# Eval("Nombre") %>')">
                                        <i class="fas fa-trash-alt"></i> Eliminar
                                    </button>
                                </div>
                            </div>
                        </div>
                    </ItemTemplate>
                    <FooterTemplate></div></FooterTemplate>
                </asp:Repeater>

                <%-- Paginación --%>
                <div id="pnlPaginacion" runat="server" class="pagination-container d-flex justify-content-between align-items-center mt-3">
                    <div class="text-muted small">Mostrando <asp:Literal ID="litRegistrosActuales" runat="server" Text="0-0"></asp:Literal> de <asp:Literal ID="litRegistrosTotales" runat="server" Text="0"></asp:Literal> mascotas</div>
                    <nav class="custom-pagination">
                        <ul class="pagination mb-0">
                            <li class="page-item"><asp:LinkButton ID="lnkAnterior" runat="server" CssClass="page-link" OnClick="lnkPaginado_Click" CommandName="Anterior" CausesValidation="false"><i class="fas fa-chevron-left"></i></asp:LinkButton></li>
                            <asp:Repeater ID="rptPaginador" runat="server" OnItemCommand="rptPaginador_ItemCommand"><ItemTemplate><li class='<%# (bool)Eval("EsPaginaActual") ? "page-item active" : "page-item" %>'><asp:LinkButton ID="lnkPagina" runat="server" CssClass="page-link" CommandName="IrPagina" CommandArgument='<%# Eval("Pagina") %>' CausesValidation="false"><%# Eval("Pagina") %></asp:LinkButton></li></ItemTemplate></asp:Repeater>
                            <li class="page-item"><asp:LinkButton ID="lnkSiguiente" runat="server" CssClass="page-link" OnClick="lnkPaginado_Click" CommandName="Siguiente" CausesValidation="false"><i class="fas fa-chevron-right"></i></asp:LinkButton></li>
                        </ul>
                    </nav>
                </div>
            </div>

            <%-- PANEL SIN RESULTADOS --%>
            <asp:Panel ID="pnlSinResultados" runat="server" Visible="true" CssClass="text-center py-5">
                <div class="text-muted"><i class="fas fa-paw mb-3" style="font-size: 3rem; color: #dee2e6;"></i><h5 class="fw-bold text-secondary">No se encontraron mascotas</h5><p class="mb-0">Intenta ajustar los filtros de búsqueda.</p></div>
            </asp:Panel>
        </ContentTemplate>
    </asp:UpdatePanel>

    <%-- MODAL CREAR/EDITAR --%>
    <div class="modal fade" id="modalMascota" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header"><h5 class="modal-title" id="modalMascotaLabel">Registrar Nueva Mascota</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
                <asp:UpdatePanel ID="updModalMascota" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body">
                            <asp:Literal ID="litMensaje" runat="server" EnableViewState="false"></asp:Literal>
                            <asp:HiddenField ID="hdMascotaID" runat="server" Value="0" />
                            <asp:HiddenField ID="hdClienteIDSeleccionado" runat="server" Value="0" />

                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label">Nombre de la Mascota *</label>
                                    <%-- NOMBRE: Permite letras y números (Ej: Baki 33) --%>
                                    <asp:TextBox ID="txtModalNombre" runat="server" CssClass="form-control" MaxLength="50"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valNom" runat="server" ControlToValidate="txtModalNombre" ErrorMessage="El nombre es requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="regexNom" runat="server" ControlToValidate="txtModalNombre" ValidationExpression="^[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9\s]+$" ErrorMessage="Solo letras, números y espacios." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RegularExpressionValidator>
                                </div>
                                
                                <div class="col-md-6"><label class="form-label">Propietario *</label><div class="input-group"><asp:TextBox ID="txtModalClienteNombre" runat="server" CssClass="form-control" ReadOnly="true" placeholder="Seleccione un cliente..."></asp:TextBox><asp:LinkButton ID="btnAbrirBuscadorCliente" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnAbrirBuscadorCliente_Click" CausesValidation="false"><i class="fas fa-search"></i></asp:LinkButton></div><asp:RequiredFieldValidator ID="valCli" runat="server" ControlToValidate="txtModalClienteNombre" ErrorMessage="Debe seleccionar un propietario" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RequiredFieldValidator></div>

                                <div class="col-md-6"><label class="form-label">Especie</label><asp:DropDownList ID="ddlModalEspecie" runat="server" CssClass="form-select"></asp:DropDownList></div>
                                
                                <div class="col-md-6">
                                    <label class="form-label">Raza *</label>
                                    <%-- RAZA: SOLO LETRAS Y ESPACIOS (Lógica mejorada) --%>
                                    <asp:TextBox ID="txtModalRaza" runat="server" CssClass="form-control" MaxLength="50"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valRaza" runat="server" ControlToValidate="txtModalRaza" ErrorMessage="La raza es requerida" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="regexRaza" runat="server" ControlToValidate="txtModalRaza" ValidationExpression="^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$" ErrorMessage="Solo se permiten letras y espacios." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RegularExpressionValidator>
                                </div>
                                
                                <div class="col-md-4"><label class="form-label">Sexo</label><asp:DropDownList ID="ddlModalSexo" runat="server" CssClass="form-select"><asp:ListItem Value="M" Text="Macho"></asp:ListItem><asp:ListItem Value="H" Text="Hembra"></asp:ListItem></asp:DropDownList></div>
                                
                                <div class="col-md-4">
                                    <label class="form-label">Color *</label>
                                    <%-- COLOR: SOLO LETRAS Y ESPACIOS (Lógica mejorada) --%>
                                    <asp:TextBox ID="txtModalColor" runat="server" CssClass="form-control" MaxLength="30"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valColor" runat="server" ControlToValidate="txtModalColor" ErrorMessage="El color es requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="regexColor" runat="server" ControlToValidate="txtModalColor" ValidationExpression="^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$" ErrorMessage="Solo se permiten letras y espacios." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarMascota"></asp:RegularExpressionValidator>
                                </div>
                                
                                <div class="col-md-4"><label class="form-label">Estado</label><asp:DropDownList ID="ddlModalEstado" runat="server" CssClass="form-select"><asp:ListItem Value="true" Text="Activo"></asp:ListItem><asp:ListItem Value="false" Text="Inactivo"></asp:ListItem></asp:DropDownList></div>
                            </div>
                        </div>
                        <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button><asp:Button ID="btnGuardarMascota" runat="server" Text="Guardar" CssClass="btn btn-primary" OnClick="btnGuardarMascota_Click" ValidationGroup="GuardarMascota" OnClientClick="if(!Page_ClientValidate('GuardarMascota')){ applyBootstrapValidation(); return false; }" /></div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>

    <%-- MODAL BUSCAR PROPIETARIO --%>
    <div class="modal fade z-index-high" id="modalBuscarPropietario" tabindex="-1" aria-hidden="true" style="background: rgba(0,0,0,0.5);">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header bg-light"><h5 class="modal-title">Buscar Propietario</h5><button type="button" class="btn-close" onclick="$('#modalBuscarPropietario').modal('hide');"></button></div>
                <asp:UpdatePanel ID="updModalBuscarPropietario" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body">
                            <div class="input-group mb-3"><asp:TextBox ID="txtBusqCliente" runat="server" CssClass="form-control" placeholder="Ingrese nombre..."></asp:TextBox><asp:LinkButton ID="btnBusqCliente" runat="server" CssClass="btn btn-primary" OnClick="btnBusqCliente_Click"><i class="fas fa-search"></i> Buscar</asp:LinkButton></div>
                            <div class="table-responsive"><asp:GridView ID="gvBusqClientes" runat="server" AutoGenerateColumns="false" CssClass="table table-hover table-sm" GridLines="None" OnRowCommand="gvBusqClientes_RowCommand"><Columns><asp:BoundField DataField="Nombre" HeaderText="Nombre" /><asp:BoundField DataField="NumDocumento" HeaderText="Documento" /><asp:BoundField DataField="Email" HeaderText="Email" /><asp:TemplateField><ItemTemplate><asp:LinkButton ID="btnSeleccionar" runat="server" CommandName="Seleccionar" CommandArgument='<%# Eval("PersonaID") + "|" + Eval("Nombre") %>' CssClass="btn btn-sm btn-outline-success"><i class="fas fa-check"></i> Seleccionar</asp:LinkButton></ItemTemplate></asp:TemplateField></Columns><EmptyDataTemplate><div class="text-center text-muted py-3">No se encontraron clientes activos.<br /><a href="Secretaria_Clientes.aspx" class="fw-bold text-primary text-decoration-none mt-2 d-inline-block"><i class="fas fa-plus-circle me-1"></i> Registrar Nuevo Cliente</a></div></EmptyDataTemplate></asp:GridView></div>
                        </div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>

    <%-- MODAL ELIMINAR --%>
    <div class="modal fade" id="modalConfirmarEliminar" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header"><h5 class="modal-title">Confirmar Eliminación</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
                <asp:UpdatePanel ID="updModalEliminar" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body"><asp:HiddenField ID="hdMascotaIDEliminar" runat="server" Value="0" />¿Eliminar a "<strong id="lblNombreMascotaEliminar"></strong>"?</div>
                        <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button><asp:Button ID="btnConfirmarEliminar" runat="server" Text="Sí, Eliminar" CssClass="btn btn-danger" OnClick="btnConfirmarEliminar_Click" /></div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>
</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script type="text/javascript">
        // -------------------------------------------------------
        // 1. VALIDACIÓN VISUAL EN TIEMPO REAL (Bordes Rojos)
        // -------------------------------------------------------
        function ValidatorUpdateDisplay(val) {
            val.style.display = val.isvalid ? "none" : "inline";
            var ctrl = document.getElementById(val.controltovalidate);
            if (ctrl) {
                if (!val.isvalid) {
                    ctrl.classList.add("is-invalid");
                } else {
                    var sigueConError = false;
                    if (typeof (Page_Validators) != "undefined") {
                        for (var i = 0; i < Page_Validators.length; i++) {
                            if (Page_Validators[i].controltovalidate == val.controltovalidate && !Page_Validators[i].isvalid) {
                                sigueConError = true;
                                break;
                            }
                        }
                    }
                    if (!sigueConError) {
                        ctrl.classList.remove("is-invalid");
                    }
                }
            }
        }

        function applyBootstrapValidation() {
            if (typeof (Page_Validators) != "undefined") {
                for (var i = 0; i < Page_Validators.length; i++) {
                    ValidatorUpdateDisplay(Page_Validators[i]);
                }
            }
        }
        
        // JS PARA ABRIR MODAL
        function abrirModalJS(btn, esSoloLectura) {
            var id = btn.getAttribute('data-id');
            var nombre = btn.getAttribute('data-nombre');
            var especie = btn.getAttribute('data-especie');
            var raza = btn.getAttribute('data-raza');
            var sexo = btn.getAttribute('data-sexo');
            var color = btn.getAttribute('data-color');
            var activo = btn.getAttribute('data-activo'); 
            var personaId = btn.getAttribute('data-personaid');
            var nombreDuenio = btn.getAttribute('data-nombreduenio');

            $('#<%= hdMascotaID.ClientID %>').val(id);
            $('#<%= hdClienteIDSeleccionado.ClientID %>').val(personaId);
            $('#<%= txtModalNombre.ClientID %>').val(nombre);
            $('#<%= txtModalClienteNombre.ClientID %>').val(nombreDuenio);
            $('#<%= ddlModalEspecie.ClientID %>').val(especie);
            $('#<%= txtModalRaza.ClientID %>').val(raza);
            $('#<%= ddlModalSexo.ClientID %>').val(sexo);
            $('#<%= txtModalColor.ClientID %>').val(color);
            $('#<%= ddlModalEstado.ClientID %>').val(activo.toLowerCase());
            
            // Limpiar errores visuales anteriores
            $('.is-invalid').removeClass('is-invalid');
            if (typeof (Page_Validators) != "undefined") {
                for (var i = 0; i < Page_Validators.length; i++) {
                    Page_Validators[i].isvalid = true;
                    ValidatorUpdateDisplay(Page_Validators[i]);
                }
            }

            // Configurar Lectura
            var inputs = ['txtModalNombre', 'txtModalRaza', 'txtModalColor'];
            inputs.forEach(id => $('input[id$="' + id + '"]').prop('readonly', esSoloLectura));
            var selects = ['ddlModalEspecie', 'ddlModalSexo', 'ddlModalEstado'];
            selects.forEach(id => $('select[id$="' + id + '"]').prop('disabled', esSoloLectura));
            
            if(esSoloLectura) { 
                $('[id$="btnAbrirBuscadorCliente"]').hide();
                $('[id$="btnGuardarMascota"]').hide(); 
            } else { 
                $('[id$="btnAbrirBuscadorCliente"]').show();
                $('[id$="btnGuardarMascota"]').show(); 
            }

            $('#modalMascotaLabel').text(esSoloLectura ? "Ver Detalles" : "Modificar Mascota");
            var modal = new bootstrap.Modal(document.getElementById('modalMascota'));
            modal.show();
        }

        function abrirModalEliminar(id, nombre) {
            $('#<%= hdMascotaIDEliminar.ClientID %>').val(id);
            $('#lblNombreMascotaEliminar').text(nombre);
            var modal = new bootstrap.Modal(document.getElementById('modalConfirmarEliminar'));
            modal.show();
        }
        
        function mostrarExitoLocal(mensaje) {
            $('.modal').modal('hide');
            $('.modal-backdrop').remove();
            if (typeof mostrarExito === 'function') mostrarExito(mensaje, 'Secretaria_Mascotas.aspx');
            else window.location.href = 'Secretaria_Mascotas.aspx';
        }

        $(document).ready(function () {
            var prm = Sys.WebForms.PageRequestManager.getInstance();
            prm.add_endRequest(function (sender, e) {
                applyBootstrapValidation();
                if (sender._postBackSettings.sourceElement) {
                    var id = sender._postBackSettings.sourceElement.id;
                    if (id.includes("btnGuardarMascota") && $('.val-error').is(':visible') == false && $('#<%= litMensaje.ClientID %>').text().trim() == "") {
                        $('#modalMascota').modal('hide'); $('.modal-backdrop').remove();
                    }
                    if (id.includes("btnConfirmarEliminar")) {
                        $('#modalConfirmarEliminar').modal('hide'); $('.modal-backdrop').remove();
                    }
                }
            });
        });
    </script>
</asp:Content>