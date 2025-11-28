<%@ Page Title="Inventario" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_Inventario.aspx.cs"
    Inherits="SoftPetWA.Secretaria_Inventario" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestión de Inventario
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/inventario.css" rel="stylesheet" />
    <style>
        .val-error { color: #dc3545; font-size: 0.875em; margin-top: 0.25rem; display: block; }
        .is-invalid { border-color: #dc3545 !important; }
        /* Estilo visual para readonly */
        .form-control[readonly], .form-select[disabled] {
            background-color: #e9ecef; cursor: not-allowed; opacity: 1;
        }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <asp:UpdatePanel ID="updPanelInventario" runat="server" UpdateMode="Conditional">
        <ContentTemplate>
            
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3><i class="fas fa-boxes me-2"></i>Gestión de Productos e Inventario</h3>
            </div>

            <%-- BARRA DE FILTROS --%>
            <div class="card shadow-sm mb-4 filter-bar border-0">
                <div class="card-body bg-light rounded">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-4">
                            <label class="form-label fw-bold small">Nombre del Producto</label>
                            <asp:TextBox ID="txtNombreProducto" runat="server" CssClass="form-control" placeholder="Buscar..."></asp:TextBox>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold small">Rango de Precio</label>
                            <asp:DropDownList ID="ddlRangoPrecio" runat="server" CssClass="form-select"></asp:DropDownList>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label fw-bold small">Estado</label>
                            <asp:DropDownList ID="ddlEstadoStock" runat="server" CssClass="form-select"></asp:DropDownList>
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
                <button type="button" class="btn btn-success" onclick="nuevoProductoJS()">
                    <i class='fas fa-plus me-2'></i>Nuevo Producto
                </button>
            </div>

            <%-- PANEL DE RESULTADOS --%>
            <asp:Panel ID="pnlResultados" runat="server" Visible="false">
                
                <div class="mb-3">
                    <h6 class="form-label text-muted">Filtrar por Categoría</h6>
                    <div class="nav nav-pills product-filter-tabs">
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

                <%-- TABLA --%>
                <div class="card shadow-sm border-0">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th style="width: 35%;">Producto</th>
                                    <th style="width: 10%;">Tipo</th>
                                    <th style="width: 15%;">Presentación</th>
                                    <th>Precio Unitario</th>
                                    <th>Stock</th>
                                    <th>Estado</th>
                                    <th class="text-center">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <asp:Repeater ID="rptInventario" runat="server" OnItemCommand="rptInventario_ItemCommand">
                                    <ItemTemplate>
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <div class="product-icon-circle <%# GetColorPorTipo(Eval("TipoNombre").ToString()) %> me-3">
                                                        <i class="<%# GetIconoPorTipo(Eval("TipoNombre").ToString()) %>"></i>
                                                    </div>
                                                    <div class="product-details">
                                                        <div class="product-name fw-bold"><%# Eval("Nombre") %></div>
                                                        <div class="product-description text-muted small"><%# Eval("TipoDesc") %></div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <span class="type-badge <%# GetTipoClasePorTipo(Eval("TipoNombre").ToString()) %>">
                                                    <%# Eval("TipoNombre").ToString().ToUpper() %>
                                                </span>
                                            </td>
                                            <td><%# Eval("Presentacion") %></td>
                                            <td class="fw-bold"><%# String.Format("S/ {0:N2}", Eval("Precio")) %></td>
                                            <td>
                                                <span class="<%# (int)Eval("Stock") <= 10 ? "text-danger fw-bold" : "" %>">
                                                    <%# Eval("Stock") %> unidades
                                                </span>
                                            </td>
                                            <td>
                                                <span class="badge rounded-pill <%# (bool)Eval("Activo") ? "bg-success" : "bg-secondary" %>">
                                                    <%# (bool)Eval("Activo") ? "ACTIVO" : "INACTIVO" %>
                                                </span>
                                            </td>
                                            <td class="text-center">
                                                <div class="action-buttons">
                                                    <button type="button" class="btn btn-action-view me-1" title="Ver Detalle"
                                                        onclick='abrirModalJS(this, true)'
                                                        data-id='<%# Eval("ID") %>' 
                                                        data-nombre='<%# Eval("Nombre") %>' 
                                                        data-tipo='<%# Eval("TipoID") %>' 
                                                        data-pres='<%# Eval("Presentacion") %>' 
                                                        data-precio='<%# ((double)Eval("Precio")).ToString("0.00", System.Globalization.CultureInfo.InvariantCulture) %>' 
                                                        data-stock='<%# Eval("Stock") %>'
                                                        data-activo='<%# (bool)Eval("Activo") ? "true" : "false" %>'>
                                                        <i class="fas fa-eye"></i>
                                                    </button>

                                                    <button type="button" class="btn btn-action-edit me-1" title="Editar"
                                                        onclick='abrirModalJS(this, false)'
                                                        data-id='<%# Eval("ID") %>' 
                                                        data-nombre='<%# Eval("Nombre") %>' 
                                                        data-tipo='<%# Eval("TipoID") %>' 
                                                        data-pres='<%# Eval("Presentacion") %>' 
                                                        data-precio='<%# ((double)Eval("Precio")).ToString("0.00", System.Globalization.CultureInfo.InvariantCulture) %>' 
                                                        data-stock='<%# Eval("Stock") %>'
                                                        data-activo='<%# (bool)Eval("Activo") ? "true" : "false" %>'>
                                                        <i class="fas fa-pencil-alt"></i>
                                                    </button>

                                                    <button type="button" class="btn btn-action-delete" title="Eliminar"
                                                        onclick="abrirModalEliminar('<%# Eval("ID") %>', '<%# Eval("Nombre") %>')">
                                                        <i class="fas fa-trash-alt"></i>
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                    </ItemTemplate>
                                </asp:Repeater>
                            </tbody>
                        </table>
                    </div>
                    
                    <%-- PAGINACIÓN --%>
                    <div class="card-footer d-flex justify-content-between align-items-center bg-white">
                        <div class="text-muted small">
                            Mostrando <asp:Literal ID="litRegistrosActuales" runat="server" Text="0-0"></asp:Literal>
                            de <asp:Literal ID="litRegistrosTotales" runat="server" Text="0"></asp:Literal> productos
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
                </div>
            </asp:Panel>

            <asp:Panel ID="pnlSinResultados" runat="server" CssClass="text-center py-5">
                <div class="text-muted opacity-50 mb-3"><i class="fas fa-box-open" style="font-size: 4rem;"></i></div>
                <h5 class="fw-bold text-muted">Gestión de Inventario</h5>
                <asp:Label ID="lblMensajeVacio" runat="server" CssClass="text-muted" Text="Utilice los filtros para buscar productos."></asp:Label>
            </asp:Panel>
        </ContentTemplate>
    </asp:UpdatePanel>

    <%-- MODAL PRODUCTO --%>
    <div class="modal fade" id="modalProducto" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header bg-light">
                    <h5 class="modal-title" id="modalProductoLabel">Producto</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <asp:UpdatePanel ID="updModalProducto" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body">
                            <asp:Literal ID="litModalError" runat="server" EnableViewState="false"></asp:Literal>
                            <asp:HiddenField ID="hdProductoID" runat="server" Value="0" />
                            
                            <div class="row g-3">
                                <div class="col-md-8">
                                    <label class="form-label">Nombre del Producto <span class="text-danger">*</span></label>
                                    <%-- NOMBRE: Regex para letras, numeros, espacios, puntos y guiones. MaxLength 100 --%>
                                    <asp:TextBox ID="txtModalNombre" runat="server" CssClass="form-control" MaxLength="100" onkeyup="validarEnVivo(this);"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valNombre" runat="server" ControlToValidate="txtModalNombre" ErrorMessage="El nombre es obligatorio." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarProducto"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="regexNombre" runat="server" ControlToValidate="txtModalNombre" ValidationExpression="^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\s\.\-]+$" ErrorMessage="Solo letras, números, puntos y guiones." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarProducto"></asp:RegularExpressionValidator>
                                </div>
                                
                                <div class="col-md-4">
                                    <label class="form-label">Tipo <span class="text-danger">*</span></label>
                                    <asp:DropDownList ID="ddlModalTipo" runat="server" CssClass="form-select"></asp:DropDownList>
                                    <asp:RequiredFieldValidator ID="valTipo" runat="server" ControlToValidate="ddlModalTipo" InitialValue="0" ErrorMessage="Seleccione un tipo." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarProducto"></asp:RequiredFieldValidator>
                                </div>
                                
                                <div class="col-md-4">
                                    <label class="form-label">Presentación</label>
                                    <asp:TextBox ID="txtModalPresentacion" runat="server" CssClass="form-control" placeholder="Ej: Blíster x10" MaxLength="50" onkeyup="validarEnVivo(this);"></asp:TextBox>
                                    <asp:RegularExpressionValidator ID="regexPres" runat="server" ControlToValidate="txtModalPresentacion" ValidationExpression="^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\s\.\-]+$" ErrorMessage="Caracteres inválidos." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarProducto"></asp:RegularExpressionValidator>
                                </div>
                                
                                <div class="col-md-4">
                                    <label class="form-label">Precio Unitario (S/) <span class="text-danger">*</span></label>
                                    <%-- PRECIO: Step 0.10 --%>
                                    <asp:TextBox ID="txtModalPrecio" runat="server" CssClass="form-control" TextMode="Number" step="0.10" onkeyup="validarEnVivo(this);"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valPrecioReq" runat="server" ControlToValidate="txtModalPrecio" ErrorMessage="Requerido." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarProducto"></asp:RequiredFieldValidator>
                                    <asp:CompareValidator ID="valPrecioPositivo" runat="server" ControlToValidate="txtModalPrecio" Operator="GreaterThanEqual" ValueToCompare="0" ErrorMessage="No negativo." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarProducto"></asp:CompareValidator>
                                </div>
                                
                                <div class="col-md-4">
                                    <label class="form-label">Stock <span class="text-danger">*</span></label>
                                    <%-- STOCK: Solo enteros, sin decimales --%>
                                    <asp:TextBox ID="txtModalStock" runat="server" CssClass="form-control" TextMode="Number" step="1" onkeypress="return event.charCode >= 48 && event.charCode <= 57" onkeyup="validarEnVivo(this);"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valStockReq" runat="server" ControlToValidate="txtModalStock" ErrorMessage="Requerido." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarProducto"></asp:RequiredFieldValidator>
                                    <asp:CompareValidator ID="valStockPositivo" runat="server" ControlToValidate="txtModalStock" Operator="GreaterThanEqual" ValueToCompare="0" Type="Integer" ErrorMessage="No negativo." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarProducto"></asp:CompareValidator>
                                </div>
                                
                                <div class="col-md-4">
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
                            <asp:Button ID="btnGuardarProducto" runat="server" Text="Guardar" CssClass="btn btn-primary" 
                                OnClick="btnGuardarProducto_Click" ValidationGroup="GuardarProducto"
                                OnClientClick="if(!Page_ClientValidate('GuardarProducto')){ applyBootstrapValidation(); return false; }" />
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
                            <asp:HiddenField ID="hdProductoIDEliminar" runat="server" Value="0" />
                            <h5 class="mb-3">¿Eliminar Producto?</h5>
                            <p class="mb-0">Estás a punto de eliminar <strong id="lblNombreProductoEliminar"></strong>.</p>
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
    <script type="text/javascript">
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
        function nuevoProductoJS() {
            limpiarModal();
            resetModalState(false);
            $('#modalProductoLabel').text('Nuevo Producto');
            $('#<%= hdProductoID.ClientID %>').val('0');
            new bootstrap.Modal(document.getElementById('modalProducto')).show();
        }

        function abrirModalJS(btn, esVer) {
            $('#<%= hdProductoID.ClientID %>').val(btn.dataset.id);
            $('#<%= txtModalNombre.ClientID %>').val(btn.dataset.nombre);
            $('#<%= ddlModalTipo.ClientID %>').val(btn.dataset.tipo);
            $('#<%= txtModalPresentacion.ClientID %>').val(btn.dataset.pres);
            $('#<%= txtModalPrecio.ClientID %>').val(btn.dataset.precio);
            $('#<%= txtModalStock.ClientID %>').val(btn.dataset.stock);
            $('#<%= ddlModalEstado.ClientID %>').val(btn.dataset.activo.toLowerCase());
            
            resetModalState(esVer);
            $('#modalProductoLabel').text(esVer ? "Detalle del Producto" : "Modificar Producto");
            
            // Limpiar validaciones visuales al abrir
            $('.is-invalid').removeClass('is-invalid');
            $('.val-error').hide();
            
            new bootstrap.Modal(document.getElementById('modalProducto')).show();
        }

        function resetModalState(esVer) {
            var inputs = ['txtModalNombre', 'txtModalPresentacion', 'txtModalPrecio', 'txtModalStock'];
            inputs.forEach(id => {
                var el = $('input[id$="' + id + '"]');
                if (esVer) el.prop('readonly', true); else el.prop('readonly', false);
            });
            var selects = ['ddlModalTipo', 'ddlModalEstado'];
            selects.forEach(id => {
                var el = $('select[id$="' + id + '"]');
                if (esVer) el.prop('disabled', true); else el.prop('disabled', false);
            });
            var btn = $('[id$="btnGuardarProducto"]');
            if (esVer) btn.hide(); else btn.show();

            $('#<%= litModalError.ClientID %>').text('');
        }

        function limpiarModal() {
            $('input[id$="txtModalNombre"]').val('');
            $('input[id$="txtModalPresentacion"]').val('');
            $('input[id$="txtModalPrecio"]').val('');
            $('input[id$="txtModalStock"]').val('');
            $('select[id$="ddlModalTipo"]').prop('selectedIndex', 0);
            $('select[id$="ddlModalEstado"]').val('true');
            $('.is-invalid').removeClass('is-invalid');
            $('.val-error').hide();
        }

        function abrirModalEliminar(id, nombre) {
            $('#<%= hdProductoIDEliminar.ClientID %>').val(id);
            $('#lblNombreProductoEliminar').text(nombre);
            $('#<%= lblErrorEliminar.ClientID %>').text('');
            new bootstrap.Modal(document.getElementById('modalConfirmarEliminar')).show();
        }

        function mostrarExitoLocal(msg) {
            var modalProd = bootstrap.Modal.getInstance(document.getElementById('modalProducto'));
            if (modalProd) modalProd.hide();
            var modalEli = bootstrap.Modal.getInstance(document.getElementById('modalConfirmarEliminar'));
            if (modalEli) modalEli.hide();
            $('.modal-backdrop').remove();

            if (typeof mostrarExito === 'function') {
                mostrarExito(msg, 'Secretaria_Inventario.aspx');
            } else {
                alert(msg);
                window.location.reload();
            }
        }
    </script>
</asp:Content>