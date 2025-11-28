<%@ Page Title="Gestión de Servicios" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_Servicios.aspx.cs"
    Inherits="SoftPetWA.Secretaria_Servicios" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestión de Servicios
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/servicios.css" rel="stylesheet" />
    <style>
        .val-error { color: #dc3545; font-size: 0.875em; margin-top: 0.25rem; display: block; }
        .is-invalid { border-color: #dc3545 !important; }
        .form-control[readonly], .form-select[disabled] {
            background-color: #e9ecef; cursor: not-allowed; opacity: 1;
        }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <asp:UpdatePanel ID="updPanelServicios" runat="server" UpdateMode="Conditional">
        <ContentTemplate>

            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3><i class="fas fa-briefcase-medical me-2"></i>Gestión de Servicios</h3>
            </div>

            <%-- BARRA DE FILTROS --%>
            <div class="card shadow-sm mb-4 filter-bar border-0">
                <div class="card-body bg-light rounded">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-4">
                            <label class="form-label fw-bold small">Nombre del Servicio</label>
                            <asp:TextBox ID="txtNombreServicio" runat="server" CssClass="form-control" placeholder="Buscar..."></asp:TextBox>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold small">Rango de Costo</label>
                            <asp:DropDownList ID="ddlRangoCosto" runat="server" CssClass="form-select"></asp:DropDownList>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label fw-bold small">Estado</label>
                            <asp:DropDownList ID="ddlEstado" runat="server" CssClass="form-select"></asp:DropDownList>
                        </div>
                        <div class="col-md-3 d-flex">
                            <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary me-2 w-100" OnClick="btnBuscar_Click">
                                <i class='fas fa-search me-1'></i> Buscar
                            </asp:LinkButton>
                            <asp:LinkButton ID="btnLimpiar" runat="server" CssClass="btn btn-outline-secondary w-100" OnClick="btnLimpiar_Click">
                                <i class='fas fa-times'></i> Limpiar
                            </asp:LinkButton>
                        </div>
                    </div>
                </div>
            </div>

            <div class="d-flex justify-content-end align-items-center mb-3">
                <button type="button" class="btn btn-success" onclick="nuevoServicioJS()">
                    <i class='fas fa-plus me-2'></i>Nuevo Servicio
                </button>
            </div>

            <%-- PANEL DE RESULTADOS --%>
            <asp:Panel ID="pnlResultados" runat="server" Visible="false">
                
                <%-- PESTAÑAS --%>
                <div class="mb-3">
                    <h6 class="form-label text-muted">Filtrar por Tipo</h6>
                    <div class="nav nav-pills filter-tabs">
                        <asp:Repeater ID="rptFiltroTipo" runat="server" OnItemCommand="rptFiltroTipo_ItemCommand">
                            <ItemTemplate>
                                <asp:LinkButton ID="btnTipo" runat="server"
                                    CssClass='<%# (int)Eval("TipoID") == CurrentTipoID ? "nav-link active me-1" : "nav-link me-1" %>'
                                    CommandName="Filtrar" CommandArgument='<%# Eval("TipoID") %>'>
                                    <%# Eval("Tipo") %> <span class="badge bg-secondary ms-1"><%# Eval("Cantidad") %></span>
                                </asp:LinkButton>
                            </ItemTemplate>
                        </asp:Repeater>
                    </div>
                </div>

                <%-- GRID DE TARJETAS --%>
                <asp:Repeater ID="rptServicios" runat="server" OnItemCommand="rptServicios_ItemCommand">
                    <HeaderTemplate><div class="row"></HeaderTemplate>
                    <ItemTemplate>
                        <div class="col-lg-3 col-md-6 mb-4 d-flex">
                            <div class="service-card flex-fill">
                                <div class="service-card-header">
                                    <div class="service-icon <%# GetColorPorTipo(Eval("TipoNombre")) %>">
                                        <i class="<%# GetIconoPorTipo(Eval("TipoNombre")) %>"></i>
                                    </div>
                                    <span class="status-badge <%# (bool)Eval("Activo") ? "status-activo" : "status-inactivo" %>">
                                        <%# (bool)Eval("Activo") ? "Activo" : "Inactivo" %>
                                    </span>
                                </div>
                                <div class="service-card-body">
                                    <h6 class="service-name"><%# Eval("Nombre") %></h6>
                                    <p class="service-desc"><%# Eval("Descripcion") %></p>
                                </div>
                                <div class="service-card-footer">
                                    <span class="service-price-footer"><%# String.Format("S/ {0:N2}", Eval("Costo")) %></span>
                                    
                                    <div class="service-actions">
                                        <button type="button" class="btn btn-sm btn-action-view" title="Ver"
                                            onclick='abrirModalJS(this, true)'
                                            data-id='<%# Eval("ID") %>'
                                            data-nombre='<%# Eval("Nombre") %>'
                                            data-tipo='<%# Eval("TipoID") %>'
                                            data-desc='<%# Eval("Descripcion") %>'
                                            data-costo='<%# ((double)Eval("Costo")).ToString("0.00", System.Globalization.CultureInfo.InvariantCulture) %>'
                                            data-activo='<%# (bool)Eval("Activo") ? "true" : "false" %>'>
                                            <i class="fas fa-eye"></i>
                                        </button>

                                        <button type="button" class="btn btn-sm btn-action-edit" title="Editar"
                                            onclick='abrirModalJS(this, false)'
                                            data-id='<%# Eval("ID") %>'
                                            data-nombre='<%# Eval("Nombre") %>'
                                            data-tipo='<%# Eval("TipoID") %>'
                                            data-desc='<%# Eval("Descripcion") %>'
                                            data-costo='<%# ((double)Eval("Costo")).ToString("0.00", System.Globalization.CultureInfo.InvariantCulture) %>'
                                            data-activo='<%# (bool)Eval("Activo") ? "true" : "false" %>'>
                                            <i class="fas fa-pencil-alt"></i>
                                        </button>

                                        <button type="button" class="btn btn-sm btn-action-delete" title="Eliminar"
                                            onclick="abrirModalEliminar('<%# Eval("ID") %>', '<%# Eval("Nombre") %>')">
                                            <i class="fas fa-trash-alt"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </ItemTemplate>
                    <FooterTemplate></div></FooterTemplate>
                </asp:Repeater>

                <%-- PAGINACIÓN --%>
                <div class="d-flex justify-content-between align-items-center mt-3">
                    <div class="text-muted small">
                        Mostrando <asp:Literal ID="litRegistrosActuales" runat="server" Text="0-0"></asp:Literal>
                        de <asp:Literal ID="litRegistrosTotales" runat="server" Text="0"></asp:Literal> servicios
                    </div>
                    <nav class="custom-pagination">
                        <ul class="pagination mb-0">
                            <li class="page-item">
                                <asp:LinkButton ID="lnkAnterior" runat="server" CssClass="page-link border-0" OnClick="lnkPaginado_Click" CommandName="Anterior">
                                    <i class="fas fa-chevron-left"></i>
                                </asp:LinkButton>
                            </li>
                            <asp:Repeater ID="rptPaginador" runat="server" OnItemCommand="rptPaginador_ItemCommand">
                                <ItemTemplate>
                                    <li class='<%# (bool)Eval("EsPaginaActual") ? "page-item active" : "page-item" %>'>
                                        <asp:LinkButton ID="lnkPagina" runat="server" CssClass="page-link border-0 rounded-circle mx-1" CommandName="IrPagina" CommandArgument='<%# Eval("Pagina") %>'>
                                            <%# Eval("Pagina") %>
                                        </asp:LinkButton>
                                    </li>
                                </ItemTemplate>
                            </asp:Repeater>
                            <li class="page-item">
                                <asp:LinkButton ID="lnkSiguiente" runat="server" CssClass="page-link border-0" OnClick="lnkPaginado_Click" CommandName="Siguiente">
                                    <i class="fas fa-chevron-right"></i>
                                </asp:LinkButton>
                            </li>
                        </ul>
                    </nav>
                </div>
            </asp:Panel>

            <asp:Panel ID="pnlSinResultados" runat="server" CssClass="text-center py-5">
                <div class="text-muted opacity-50 mb-3"><i class="fas fa-briefcase-medical" style="font-size: 4rem;"></i></div>
                <h5 class="fw-bold text-muted">Gestión de Servicios</h5>
                <asp:Label ID="lblMensajeVacio" runat="server" CssClass="text-muted" Text="Utilice los filtros para buscar servicios."></asp:Label>
            </asp:Panel>

        </ContentTemplate>
    </asp:UpdatePanel>

    <%-- MODAL SERVICIO --%>
    <div class="modal fade" id="modalServicio" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header bg-light">
                    <h5 class="modal-title" id="modalServicioLabel">Servicio</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <asp:UpdatePanel ID="updModalServicio" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body">
                            <asp:Literal ID="litModalError" runat="server" EnableViewState="false"></asp:Literal>
                            <asp:HiddenField ID="hdServicioID" runat="server" Value="0" />
                            
                            <div class="row g-3">
                                <div class="col-md-8">
                                    <label class="form-label">Nombre <span class="text-danger">*</span></label>
                                    <%-- NOMBRE: Solo letras, espacios y caracteres básicos --%>
                                    <asp:TextBox ID="txtModalNombre" runat="server" CssClass="form-control" MaxLength="100" onkeyup="validarEnVivo(this);"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valNombre" runat="server" ControlToValidate="txtModalNombre" ErrorMessage="Nombre requerido." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarServicio"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="regexNombre" runat="server" ControlToValidate="txtModalNombre" ValidationExpression="^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s0-9]+$" ErrorMessage="Solo caracteres alfanuméricos." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarServicio"></asp:RegularExpressionValidator>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Tipo <span class="text-danger">*</span></label>
                                    <asp:DropDownList ID="ddlModalTipo" runat="server" CssClass="form-select"></asp:DropDownList>
                                    <asp:RequiredFieldValidator ID="valTipo" runat="server" ControlToValidate="ddlModalTipo" InitialValue="0" ErrorMessage="Seleccione un tipo." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarServicio"></asp:RequiredFieldValidator>
                                </div>
                                <div class="col-md-12">
                                    <label class="form-label">Descripción Breve</label>
                                    <asp:TextBox ID="txtModalDescripcion" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="2" MaxLength="200"></asp:TextBox>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Costo (S/) <span class="text-danger">*</span></label>
                                    <%-- COSTO: Step 0.10 --%>
                                    <asp:TextBox ID="txtModalPrecio" runat="server" CssClass="form-control" TextMode="Number" step="0.10" onkeyup="validarEnVivo(this);"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valPrecio" runat="server" ControlToValidate="txtModalPrecio" ErrorMessage="Costo requerido." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarServicio"></asp:RequiredFieldValidator>
                                    <asp:CompareValidator ID="valPrecioPos" runat="server" ControlToValidate="txtModalPrecio" Operator="GreaterThanEqual" ValueToCompare="0" ErrorMessage="No negativo." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarServicio"></asp:CompareValidator>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Estado</label>
                                    <asp:DropDownList ID="ddlModalEstado" runat="server" CssClass="form-select">
                                        <asp:ListItem Value="true" Text="Activo"></asp:ListItem>
                                        <asp:ListItem Value="false" Text="Inactivo"></asp:ListItem>
                                    </asp:DropDownList>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <asp:Button ID="btnGuardarServicio" runat="server" Text="Guardar" CssClass="btn btn-primary" 
                                OnClick="btnGuardarServicio_Click" ValidationGroup="GuardarServicio"
                                OnClientClick="if(!Page_ClientValidate('GuardarServicio')){ applyBootstrapValidation(); return false; }" />
                        </div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>

    <%-- MODAL ELIMINAR --%>
    <div class="modal fade" id="modalConfirmarEliminar" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header border-0 pb-0">
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <asp:UpdatePanel ID="updModalEliminar" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body text-center">
                             <div class="mb-3 text-warning"><i class="fas fa-exclamation-triangle fa-3x"></i></div>
                            <asp:HiddenField ID="hdServicioIDEliminar" runat="server" Value="0" />
                            <h5 class="mb-3">¿Eliminar Servicio?</h5>
                            <p class="mb-0">Estás a punto de eliminar <strong id="lblNombreServicioEliminar"></strong>.</p>
                            <asp:Label ID="lblErrorEliminar" runat="server" CssClass="text-danger small mt-2 d-block"></asp:Label>
                        </div>
                        <div class="modal-footer justify-content-center border-0 pt-0">
                            <button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">No, cancelar</button>
                            <asp:Button ID="btnConfirmarEliminar" runat="server" Text="Sí, eliminar" CssClass="btn btn-danger px-4" OnClick="btnConfirmarEliminar_Click" />
                        </div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script>
        // -----------------------------------------------------
        // VALIDACIÓN EN VIVO Y VISUAL
        // -----------------------------------------------------
        function validarEnVivo(ctrl) {
            if (typeof (Page_Validators) != "undefined") {
                for (var i = 0; i < Page_Validators.length; i++) {
                    if (Page_Validators[i].controltovalidate == ctrl.id) {
                        ValidatorValidate(Page_Validators[i]);
                    }
                }
            }
            applyBootstrapValidation();
        }

        function applyBootstrapValidation() {
            if (typeof (Page_Validators) != "undefined") {
                for (var i = 0; i < Page_Validators.length; i++) {
                    var val = Page_Validators[i];
                    val.style.display = val.isvalid ? "none" : "inline";
                    var ctrl = document.getElementById(val.controltovalidate);
                    if (ctrl != null) {
                        if (!val.isvalid) {
                            ctrl.classList.add("is-invalid");
                        } else {
                            // Validar si todos los validadores de este control pasan
                            var esValidoTotal = true;
                            for (var j = 0; j < Page_Validators.length; j++) {
                                if (Page_Validators[j].controltovalidate == ctrl.id && !Page_Validators[j].isvalid) {
                                    esValidoTotal = false;
                                    break;
                                }
                            }
                            if (esValidoTotal) {
                                ctrl.classList.remove("is-invalid");
                            }
                        }
                    }
                }
            }
        }

        // -----------------------------------------------------
        // LOGICA CLIENT-SIDE
        // -----------------------------------------------------
        function nuevoServicioJS() {
            limpiarModal();
            resetModalState(false);
            $('#modalServicioLabel').text('Nuevo Servicio');
            $('#<%= hdServicioID.ClientID %>').val('0');
            new bootstrap.Modal(document.getElementById('modalServicio')).show();
        }

        function abrirModalJS(btn, esVer) {
            $('#<%= hdServicioID.ClientID %>').val(btn.dataset.id);
            $('#<%= txtModalNombre.ClientID %>').val(btn.dataset.nombre);
            $('#<%= ddlModalTipo.ClientID %>').val(btn.dataset.tipo);
            $('#<%= txtModalDescripcion.ClientID %>').val(btn.dataset.desc);
            $('#<%= txtModalPrecio.ClientID %>').val(btn.dataset.costo);
            $('#<%= ddlModalEstado.ClientID %>').val(btn.dataset.activo.toLowerCase());

            resetModalState(esVer);
            $('#modalServicioLabel').text(esVer ? "Detalle del Servicio" : "Modificar Servicio");
            
            // Limpiar validaciones visuales al abrir
            $('.is-invalid').removeClass('is-invalid');
            $('.val-error').hide();
            
            new bootstrap.Modal(document.getElementById('modalServicio')).show();
        }

        function resetModalState(esVer) {
            var inputs = ['txtModalNombre', 'txtModalDescripcion', 'txtModalPrecio'];
            inputs.forEach(id => {
                var el = $('input[id$="' + id + '"], textarea[id$="' + id + '"]');
                if (esVer) el.prop('readonly', true); else el.prop('readonly', false);
            });
            var selects = ['ddlModalTipo', 'ddlModalEstado'];
            selects.forEach(id => {
                var el = $('select[id$="' + id + '"]');
                if (esVer) el.prop('disabled', true); else el.prop('disabled', false);
            });
            var btn = $('[id$="btnGuardarServicio"]');
            if (esVer) btn.hide(); else btn.show();

            $('#<%= litModalError.ClientID %>').text('');
        }

        function limpiarModal() {
            $('input[id$="txtModalNombre"]').val('');
            $('textarea[id$="txtModalDescripcion"]').val('');
            $('input[id$="txtModalPrecio"]').val('');
            $('select[id$="ddlModalTipo"]').prop('selectedIndex', 0);
            $('select[id$="ddlModalEstado"]').val('true');
            $('.is-invalid').removeClass('is-invalid');
            $('.val-error').hide();
        }

        function abrirModalEliminar(id, nombre) {
            $('#<%= hdServicioIDEliminar.ClientID %>').val(id);
            $('#lblNombreServicioEliminar').text(nombre);
            $('#<%= lblErrorEliminar.ClientID %>').text('');
            new bootstrap.Modal(document.getElementById('modalConfirmarEliminar')).show();
        }

        function mostrarExitoLocal(msg) {
            var modalProd = bootstrap.Modal.getInstance(document.getElementById('modalServicio'));
            if (modalProd) modalProd.hide();
            var modalEli = bootstrap.Modal.getInstance(document.getElementById('modalConfirmarEliminar'));
            if (modalEli) modalEli.hide();
            $('.modal-backdrop').remove();
            if (typeof mostrarExito === 'function') {
                mostrarExito(msg, 'Secretaria_Servicios.aspx');
            } else {
                alert(msg); window.location.reload();
            }
        }
    </script>
</asp:Content>