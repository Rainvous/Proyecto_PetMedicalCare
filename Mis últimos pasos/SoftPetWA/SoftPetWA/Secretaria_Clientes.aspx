<%@ Page Title="Gestión de Clientes" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_Clientes.aspx.cs"
    Inherits="SoftPetWA.Secretaria_Clientes" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestión de Clientes
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/clientes.css" rel="stylesheet" />
    <style>
        /* Estilos unificados */
        .form-control[readonly], .form-select[disabled] {
            background-color: #e9ecef;
            cursor: not-allowed; opacity: 1;
        }
        .val-error {
            color: #dc3545;
            font-size: 0.875em; margin-top: 0.25rem; display: block;
        }
        .is-invalid { border-color: #dc3545 !important; }
        
        /* Paginación */
        .custom-pagination .page-item { margin: 0 3px; }
        .custom-pagination .page-link {
            border: none;
            border-radius: 50% !important; width: 35px; height: 35px;
            display: flex; align-items: center; justify-content: center;
            color: #6c757d; background-color: transparent; font-weight: 600;
            transition: all 0.2s;
        }
        .custom-pagination .page-link:hover { background-color: #e9ecef; color: #0097a7; }
        .custom-pagination .page-item.active .page-link {
            background-color: #0097a7;
            color: white; box-shadow: 0 2px 5px rgba(0, 151, 167, 0.3);
        }
        .pagination-container {
            background-color: #fff;
            border-radius: 0.5rem; box-shadow: 0 .125rem .25rem rgba(0,0,0,.075); padding: 1rem;
        }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <asp:UpdatePanel ID="updPanelClientes" runat="server" UpdateMode="Conditional">
        <ContentTemplate>
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3><i class="fas fa-users me-2"></i>Gestión de Clientes</h3>
            </div>

            <%-- FILTROS --%>
            <div class="card shadow-sm mb-4 filter-bar">
                <div class="card-body">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-3">
                            <label class="form-label">Nombre</label>
                            <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" placeholder="Buscar..."></asp:TextBox>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">Documento</label>
                            <div class="input-group">
                                <asp:DropDownList ID="ddlDocumento" runat="server" CssClass="form-select" style="max-width: 80px;"></asp:DropDownList>
                                <asp:TextBox ID="txtDocumento" runat="server" CssClass="form-control" placeholder="Num..."></asp:TextBox>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">RUC</label>
                            <asp:TextBox ID="txtRUC" runat="server" CssClass="form-control" placeholder="RUC..."></asp:TextBox>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">Estado</label>
                            <asp:DropDownList ID="ddlFiltroEstado" runat="server" CssClass="form-select">
                                <asp:ListItem Value="-1" Text="Todos" Selected="True"></asp:ListItem>
                                <asp:ListItem Value="1" Text="Activo"></asp:ListItem>
                                <asp:ListItem Value="0" Text="Inactivo"></asp:ListItem>
                            </asp:DropDownList>
                        </div>
                        <div class="col-md-3 d-flex">
                            <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary me-2" OnClick="btnBuscar_Click">
                                <i class='fas fa-search me-1'></i> Buscar
                            </asp:LinkButton>
                            <asp:LinkButton ID="btnLimpiar" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnLimpiar_Click">
                                <i class='fas fa-times'></i> Limpiar
                            </asp:LinkButton>
                        </div>
                    </div>
                </div>
            </div>

            <div class="d-flex justify-content-end align-items-center mb-3">
                <asp:LinkButton ID="btnNuevoCliente" runat="server" CssClass="btn btn-success" OnClick="btnNuevoCliente_Click">
                    <i class='fas fa-plus me-2'></i>Nuevo Cliente
                </asp:LinkButton>
            </div>

            <%-- LISTA --%>
            <div id="divListaClientes" runat="server" visible="false">
                <div class="client-list-header d-none d-lg-block">
                    <div class="row align-items-center">
                        <div class="col-lg-3"><span class="header-text">Cliente</span></div>
                        <div class="col-lg-2"><span class="header-text">Documento</span></div>
                        <div class="col-lg-2"><span class="header-text">RUC</span></div>
                        <div class="col-lg-1"><span class="header-text">Teléfono</span></div>
                        <div class="col-lg-1"><span class="header-text">Mascotas</span></div>
                        <div class="col-lg-1"><span class="header-text">Estado</span></div>
                        <div class="col-lg-2 text-center"><span class="header-text">Acciones</span></div>
                    </div>
                </div>

                <asp:Repeater ID="rptClientes" runat="server" OnItemCommand="rptClientes_ItemCommand">
                    <ItemTemplate>
                        <div class="client-list-row">
                            <div class="row align-items-center">
                                <div class="col-lg-3 mb-2 mb-lg-0">
                                    <div class="client-info">
                                        <div class="client-avatar" style='background-color: <%# Eval("AvatarColor") %>;'><%# Eval("Iniciales") %></div>
                                        <div><span class="name"><%# Eval("Nombre") %></span><span class="email"><%# Eval("Email") %></span></div>
                                    </div>
                                </div>
                                <div class="col-6 col-lg-2 mb-2 mb-lg-0"><span class="d-lg-none header-text">Documento: </span><span class="row-text"><%# Eval("TipoDocumento") %>: <%# Eval("NumDocumento") %></span></div>
                                <div class="col-6 col-lg-2 mb-2 mb-lg-0"><span class="d-lg-none header-text">RUC: </span><span class="row-text"><%# Eval("RUC") %></span></div>
                                <div class="col-6 col-lg-1 mb-2 mb-lg-0"><span class="d-lg-none header-text">Teléfono: </span><span class="row-text"><%# Eval("Telefono") %></span></div>
                                <div class="col-6 col-lg-1 mb-2 mb-lg-0">
                                    <asp:LinkButton ID="btnVerMascotas" runat="server" CommandName="VerMascotas" CommandArgument='<%# Eval("PersonaID") %>' CausesValidation="false"><%# Eval("TextoMascotas") %></asp:LinkButton>
                                </div>
                                <div class="col-6 col-lg-1 mb-2 mb-lg-0"><span class="badge rounded-pill <%# Eval("Estado").ToString() == "Activo" ? "bg-success-soft text-success" : "bg-danger-soft text-danger" %>"><%# Eval("Estado") %></span></div>
                                
                                <div class="col-6 col-lg-2 text-lg-center">
                                    <%-- BOTÓN VER --%>
                                    <button type="button" class="btn action-btn action-btn-view" onclick='abrirModalJS(this, true)'
                                        data-id='<%# Eval("PersonaID") %>' 
                                        data-usuarioid='<%# Eval("UsuarioID") %>'
                                        data-nombre='<%# Eval("Nombre") %>' 
                                        data-email='<%# Eval("Email") %>' 
                                        data-username='<%# Eval("Username") %>'
                                        data-telefono='<%# Eval("Telefono") %>' 
                                        data-tipodoc='<%# Eval("TipoDocumento") %>'
                                        data-numdoc='<%# Eval("NumDocumento") %>' 
                                        data-ruc='<%# Eval("RUCRaw") %>' 
                                        data-direccion='<%# Eval("Direccion") %>' 
                                        data-sexo='<%# Eval("Sexo") %>'
                                        data-estado='<%# Eval("Estado") %>' 
                                        title="Ver"><i class="fas fa-eye"></i></button>

                                    <%-- BOTÓN EDITAR --%>
                                    <button type="button" class="btn action-btn action-btn-edit ms-1" onclick='abrirModalJS(this, false)'
                                        data-id='<%# Eval("PersonaID") %>' 
                                        data-usuarioid='<%# Eval("UsuarioID") %>'
                                        data-nombre='<%# Eval("Nombre") %>' 
                                        data-email='<%# Eval("Email") %>' 
                                        data-username='<%# Eval("Username") %>'
                                        data-telefono='<%# Eval("Telefono") %>' 
                                        data-tipodoc='<%# Eval("TipoDocumento") %>'
                                        data-numdoc='<%# Eval("NumDocumento") %>' 
                                        data-ruc='<%# Eval("RUCRaw") %>'
                                        data-direccion='<%# Eval("Direccion") %>' 
                                        data-sexo='<%# Eval("Sexo") %>'
                                        data-estado='<%# Eval("Estado") %>' 
                                        title="Editar"><i class="fas fa-pencil-alt"></i></button>

                                    <button type="button" class="btn action-btn action-btn-delete ms-1" 
                                        onclick="abrirModalEliminar('<%# Eval("PersonaID") %>', '<%# Eval("Nombre") %>')" title="Eliminar"><i class="fas fa-trash-alt"></i></button>
                                </div>
                            </div>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>

                <%-- Paginación --%>
                <div class="pagination-container d-flex justify-content-between align-items-center mt-3">
                    <div class="text-muted small">Mostrando <asp:Literal ID="litRegistrosActuales" runat="server" Text="0-0"></asp:Literal> de <asp:Literal ID="litRegistrosTotales" runat="server" Text="0"></asp:Literal> Clientes</div>
                    <nav class="custom-pagination">
                        <ul class="pagination mb-0">
                            <li class="page-item"><asp:LinkButton ID="lnkAnterior" runat="server" CssClass="page-link" OnClick="lnkPaginado_Click" CommandName="Anterior" CausesValidation="false"><i class="fas fa-chevron-left"></i></asp:LinkButton></li>
                            <asp:Repeater ID="rptPaginador" runat="server" OnItemCommand="rptPaginador_ItemCommand"><ItemTemplate><li class='<%# (bool)Eval("EsPaginaActual") ? "page-item active" : "page-item" %>'><asp:LinkButton ID="lnkPagina" runat="server" CssClass="page-link" CommandName="IrPagina" CommandArgument='<%# Eval("Pagina") %>' CausesValidation="false"><%# Eval("Pagina") %></asp:LinkButton></li></ItemTemplate></asp:Repeater>
                            <li class="page-item"><asp:LinkButton ID="lnkSiguiente" runat="server" CssClass="page-link" OnClick="lnkPaginado_Click" CommandName="Siguiente" CausesValidation="false"><i class="fas fa-chevron-right"></i></asp:LinkButton></li>
                        </ul>
                    </nav>
                </div>
            </div>

            <asp:Panel ID="pnlSinResultados" runat="server" Visible="true" CssClass="text-center py-5">
                <div class="text-muted"><i class="fas fa-search mb-3" style="font-size: 3rem; color: #dee2e6;"></i><h5 class="fw-bold text-secondary">No se encontraron resultados</h5><p class="mb-0">Intenta ajustar los filtros de búsqueda.</p></div>
            </asp:Panel>
        </ContentTemplate>
    </asp:UpdatePanel>

    <%-- MODAL CLIENTE --%>
    <div class="modal fade" id="modalCliente" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header"><h5 class="modal-title" id="modalClienteLabel">Registrar Nuevo Cliente</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
                <asp:UpdatePanel ID="updModalCliente" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body">
                            <asp:Literal ID="litModalError" runat="server" EnableViewState="false"></asp:Literal>
                            <asp:HiddenField ID="hdClienteID" runat="server" Value="0" />
                            <asp:HiddenField ID="hdTempUsuarioID" runat="server" Value="0" />
                            
                            <div class="row g-3">
                                <%-- NOMBRE: Regex solo letras --%>
                                <div class="col-md-12">
                                    <label class="form-label">Nombre Completo *</label>
                                    <asp:TextBox ID="txtModalNombre" runat="server" CssClass="form-control" MaxLength="100"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valNombre" runat="server" ControlToValidate="txtModalNombre" ErrorMessage="Requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarCliente"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="valNombreRegex" runat="server" ControlToValidate="txtModalNombre" ErrorMessage="Solo se permiten letras y espacios." CssClass="val-error" Display="Dynamic" ValidationExpression="^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$" ValidationGroup="GuardarCliente"></asp:RegularExpressionValidator>
                                </div>

                                <%-- EMAIL: Mensaje mejorado --%>
                                <div class="col-md-12">
                                    <label class="form-label">Email *</label>
                                    <asp:TextBox ID="txtModalEmail" runat="server" CssClass="form-control" TextMode="Email" MaxLength="100"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valEmailReq" runat="server" ControlToValidate="txtModalEmail" ErrorMessage="Requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarCliente"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="valEmailFmt" runat="server" ControlToValidate="txtModalEmail" ErrorMessage="Formato inválido. Asegúrese de incluir '@' y un dominio (ej: ejemplo@gmail.com)." CssClass="val-error" Display="Dynamic" ValidationExpression="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$" ValidationGroup="GuardarCliente"></asp:RegularExpressionValidator>
                                </div>
                                
                                <%-- USUARIO: Alfanumérico --%>
                                <div class="col-md-6">
                                    <label class="form-label">Usuario *</label>
                                    <asp:TextBox ID="txtModalUsuario" runat="server" CssClass="form-control" MaxLength="20"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valUsuario" runat="server" ControlToValidate="txtModalUsuario" ErrorMessage="Requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarCliente"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="valUsuarioRegex" runat="server" ControlToValidate="txtModalUsuario" ErrorMessage="Debe ser alfanumérico." CssClass="val-error" Display="Dynamic" ValidationExpression="^[a-zA-Z0-9]+$" ValidationGroup="GuardarCliente"></asp:RegularExpressionValidator>
                                </div>

                                
                                <%-- TELÉFONO: Solo 9 dígitos, onkeypress numérico --%>
                                <div class="col-md-12">
                                    <label class="form-label">Teléfono</label>
                                    <asp:TextBox ID="txtModalTelefono" runat="server" CssClass="form-control" MaxLength="9" onkeypress="return (event.charCode >= 48 && event.charCode <= 57)"></asp:TextBox>
                                    <asp:RegularExpressionValidator ID="valTel" runat="server" ControlToValidate="txtModalTelefono" ErrorMessage="El teléfono debe tener exactamente 9 dígitos numéricos." CssClass="val-error" Display="Dynamic" ValidationExpression="^\d{9}$" ValidationGroup="GuardarCliente"></asp:RegularExpressionValidator>
                                </div>
                                
                                <div class="col-md-3">
                                    <label class="form-label">Tipo Doc.</label>
                                    <asp:DropDownList ID="ddlModalTipoDoc" runat="server" CssClass="form-select" onchange="actualizarReglasDoc()"></asp:DropDownList>
                                </div>
                                
                                <%-- DOC: Bloqueo dinámico y mensaje custom --%>
                                <div class="col-md-5">
                                    <label class="form-label">Nro Doc. *</label>
                                    <asp:TextBox ID="txtModalNumDoc" runat="server" CssClass="form-control"
                                        onkeypress="var tipo = document.getElementById(this.id.replace('txtModalNumDoc','ddlModalTipoDoc')); if(tipo && tipo.options[tipo.selectedIndex].text === 'DNI') return (event.charCode >= 48 && event.charCode <= 57);"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valNumDoc" runat="server" ControlToValidate="txtModalNumDoc" ErrorMessage="Requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarCliente"></asp:RequiredFieldValidator>
                                    <asp:CustomValidator ID="valDocLength" runat="server" ControlToValidate="txtModalNumDoc" ClientValidationFunction="validarDocumento" ErrorMessage="Longitud incorrecta: DNI requiere 8 dígitos, CE requiere 12 caracteres." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarCliente"></asp:CustomValidator>
                                </div>

                                <%-- RUC: Solo números, inicio 10/20 --%>
                                <div class="col-md-4">
                                    <label class="form-label">RUC (Opcional)</label>
                                    <asp:TextBox ID="txtModalRUC" runat="server" CssClass="form-control" MaxLength="11" onkeypress="return (event.charCode >= 48 && event.charCode <= 57)"></asp:TextBox>
                                    <asp:RegularExpressionValidator ID="valRuc" runat="server" ControlToValidate="txtModalRUC" ErrorMessage="RUC inválido. Debe tener 11 dígitos y empezar con 10 o 20." CssClass="val-error" Display="Dynamic" ValidationExpression="^(10|20)\d{9}$" ValidationGroup="GuardarCliente"></asp:RegularExpressionValidator>
                                </div>
                                
                                <div class="col-md-12"><label class="form-label">Dirección</label><asp:TextBox ID="txtModalDireccion" runat="server" CssClass="form-control" MaxLength="200"></asp:TextBox></div>
                                <div class="col-md-4"><label class="form-label">Sexo</label><asp:DropDownList ID="ddlModalSexo" runat="server" CssClass="form-select"><asp:ListItem Value="M">Masculino</asp:ListItem><asp:ListItem Value="F">Femenino</asp:ListItem><asp:ListItem Value="O" Selected="True">Otro</asp:ListItem></asp:DropDownList></div>
                                <div class="col-md-4"><label class="form-label">Rol</label><asp:TextBox ID="txtModalRol" runat="server" CssClass="form-control" Text="Cliente" ReadOnly="true"></asp:TextBox></div>
								<div class="col-md-4"><label class="form-label">Estado</label><asp:DropDownList ID="ddlModalEstado" runat="server" CssClass="form-select"><asp:ListItem Value="Activo">Activo</asp:ListItem><asp:ListItem Value="Inactivo">Inactivo</asp:ListItem></asp:DropDownList></div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <asp:Button ID="btnGuardarCliente" runat="server" Text="Guardar" CssClass="btn btn-primary" OnClick="btnGuardarCliente_Click" ValidationGroup="GuardarCliente" OnClientClick="if(!Page_ClientValidate('GuardarCliente')){ applyBootstrapValidation(); return false; }" />
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
                <div class="modal-body"><asp:UpdatePanel ID="updEliminar" runat="server"><ContentTemplate><asp:HiddenField ID="hdClienteIDEliminar" runat="server" Value="0" />¿Eliminar a "<strong id="lblNombreClienteEliminar"></strong>"?<div class="mt-3 text-end"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button><asp:Button ID="btnConfirmarEliminar" runat="server" Text="Sí, Eliminar" CssClass="btn btn-danger" OnClick="btnConfirmarEliminar_Click" UseSubmitBehavior="false" /></div></ContentTemplate></asp:UpdatePanel></div>
            </div>
        </div>
    </div>

    <%-- Modal Mascotas --%>
    <div class="modal fade" id="modalMascotas" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white"><h5 class="modal-title">Mascotas del Cliente</h5><button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div>
                <asp:UpdatePanel ID="updModalMascotas" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body">
                            <div class="mb-3"><h6 class="text-muted">Propietario: <asp:Label ID="lblClienteMascotas" runat="server" CssClass="fw-bold text-dark"></asp:Label></h6></div>
                            <div class="table-responsive"><asp:GridView ID="gvMascotas" runat="server" AutoGenerateColumns="false" CssClass="table table-hover table-bordered align-middle" GridLines="None" ShowHeaderWhenEmpty="true"><HeaderStyle CssClass="table-light" /><Columns><asp:BoundField DataField="nombre" HeaderText="Nombre" /><asp:BoundField DataField="especie" HeaderText="Especie" /><asp:BoundField DataField="raza" HeaderText="Raza" /><asp:TemplateField HeaderText="Sexo" ItemStyle-HorizontalAlign="Center"><ItemTemplate><%# Eval("sexo") != null && Eval("sexo").ToString() == "M" ? "Macho" : "Hembra" %></ItemTemplate></asp:TemplateField><asp:BoundField DataField="color" HeaderText="Color" /><asp:TemplateField HeaderText="Estado" ItemStyle-HorizontalAlign="Center"><ItemTemplate><span class='badge rounded-pill <%# (bool)Eval("activo") ? "bg-success" : "bg-secondary" %>'><%# (bool)Eval("activo") ? "Activo" : "Inactivo" %></span></ItemTemplate></asp:TemplateField></Columns><EmptyDataTemplate><div class="alert alert-info text-center m-0">Este cliente no tiene mascotas.</div></EmptyDataTemplate></asp:GridView></div>
                        </div>
                        <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button></div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>
</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script type="text/javascript">
        // -------------------------------------------------------
        // 1. CONTROL DINÁMICO DE LONGITUD (DNI vs CE)
        // -------------------------------------------------------
        function actualizarReglasDoc() {
            var ddl = document.getElementById('<%= ddlModalTipoDoc.ClientID %>');
            var txt = document.getElementById('<%= txtModalNumDoc.ClientID %>');
            
            if (ddl && txt) {
                var tipo = ddl.options[ddl.selectedIndex].text;
                
                // Limpiar error visual previo
                txt.classList.remove('is-invalid');
                
                if (tipo === "DNI") {
                    // DNI: Max 8 caracteres
                    txt.setAttribute("maxLength", "8");
                    // Recortar si tiene más
                    if (txt.value.length > 8) txt.value = txt.value.substring(0, 8);
                } else {
                    // CE: Max 12 caracteres
                    txt.setAttribute("maxLength", "12");
                    // Recortar si tiene más
                    if (txt.value.length > 12) txt.value = txt.value.substring(0, 12);
                }
            }
        }

        // -------------------------------------------------------
        // 2. VALIDACIÓN VISUAL EN TIEMPO REAL (Bordes Rojos)
        // -------------------------------------------------------
        function ValidatorUpdateDisplay(val) {
            val.style.display = val.isvalid ? "none" : "inline";

            var ctrl = document.getElementById(val.controltovalidate);
            if (ctrl) {
                if (!val.isvalid) {
                    ctrl.classList.add("is-invalid");
                } else {
                    // Revisar si otros validadores fallan en el mismo control
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

        // -------------------------------------------------------
        // 3. VALIDACIÓN LÓGICA (DNI números, CE alfanumérico)
        // -------------------------------------------------------
        function validarDocumento(sender, args) {
            var ddl = document.getElementById('<%= ddlModalTipoDoc.ClientID %>');
            var tipo = ddl.options[ddl.selectedIndex].text;
            var valor = args.Value;
            if (tipo === "DNI") {
                args.IsValid = (valor.length === 8 && /^\d+$/.test(valor));
            } else if (tipo === "CE") {
                args.IsValid = (valor.length === 12 && /^[a-zA-Z0-9]+$/.test(valor));
            } else {
                args.IsValid = true;
            }
        }

        function applyBootstrapValidation() {
            if (typeof (Page_Validators) != "undefined") {
                for (var i = 0; i < Page_Validators.length; i++) {
                    ValidatorUpdateDisplay(Page_Validators[i]);
                }
            }
        }
        
        // --- FUNCIÓN JS PARA LLENAR EL MODAL CON TODOS LOS DATOS ---
        function abrirModalJS(btn, esSoloLectura) {
            var id = btn.getAttribute('data-id');
            var usuarioId = btn.getAttribute('data-usuarioid');
            var nombre = btn.getAttribute('data-nombre');
            var email = btn.getAttribute('data-email');
            var usuario = btn.getAttribute('data-username');
            var telefono = btn.getAttribute('data-telefono');
            var tipoDoc = btn.getAttribute('data-tipodoc');
            var numDoc = btn.getAttribute('data-numdoc');
            var ruc = btn.getAttribute('data-ruc');
            var direccion = btn.getAttribute('data-direccion');
            var sexo = btn.getAttribute('data-sexo');
            var estado = btn.getAttribute('data-estado');

            $('#<%= hdClienteID.ClientID %>').val(id);
            $('#<%= hdTempUsuarioID.ClientID %>').val(usuarioId);
            
            $('#<%= txtModalNombre.ClientID %>').val(nombre);
            $('#<%= txtModalEmail.ClientID %>').val(email);
            $('#<%= txtModalUsuario.ClientID %>').val(usuario);
            $('#<%= txtModalTelefono.ClientID %>').val(telefono);
            $('#<%= ddlModalTipoDoc.ClientID %>').val(tipoDoc);
            $('#<%= txtModalNumDoc.ClientID %>').val(numDoc);
            $('#<%= txtModalRUC.ClientID %>').val(ruc);
            $('#<%= txtModalDireccion.ClientID %>').val(direccion);
            $('#<%= ddlModalSexo.ClientID %>').val(sexo);
            $('#<%= ddlModalEstado.ClientID %>').val(estado);


            configurarModoLectura(esSoloLectura);
            $('#modalClienteLabel').text(esSoloLectura ? "Ver Detalles" : "Modificar Cliente");
            
            // IMPORTANTE: Ajustar MaxLength según el documento cargado
            actualizarReglasDoc();
            
            var modal = new bootstrap.Modal(document.getElementById('modalCliente'));
            modal.show();
        }

        function configurarModoLectura(soloLectura) {
            var inputs = ['txtModalNombre', 'txtModalEmail', 'txtModalUsuario', 'txtModalTelefono', 'txtModalNumDoc', 'txtModalRUC', 'txtModalDireccion'];
            inputs.forEach(id => $('input[id$="' + id + '"]').prop('readonly', soloLectura));
            
            var selects = ['ddlModalTipoDoc', 'ddlModalSexo', 'ddlModalEstado'];
            selects.forEach(id => $('select[id$="' + id + '"]').prop('disabled', soloLectura));
            
            var btn = $('[id$="btnGuardarCliente"]');
            if(soloLectura) btn.hide(); else btn.show();
        }

        function abrirModalEliminar(id, nombre) {
            $('#<%= hdClienteIDEliminar.ClientID %>').val(id);
            $('#lblNombreClienteEliminar').text(nombre);
            var modal = new bootstrap.Modal(document.getElementById('modalConfirmarEliminar'));
            modal.show();
        }

        $(document).ready(function () {
            var prm = Sys.WebForms.PageRequestManager.getInstance();
            prm.add_endRequest(function (sender, e) {
                applyBootstrapValidation();
                if (sender._postBackSettings.sourceElement) {
                    var idBtn = sender._postBackSettings.sourceElement.id;
                    if (idBtn.includes("btnGuardarCliente") && $('.val-error').is(':visible') == false && $('#<%= litModalError.ClientID %>').text().trim() == "") {
                        $('#modalCliente').modal('hide'); $('.modal-backdrop').remove();
                    }
                    if (idBtn.includes("btnConfirmarEliminar")) {
                        $('#modalConfirmarEliminar').modal('hide'); $('.modal-backdrop').remove();
                    }
                }
            });
            // Aplicar reglas al cargar (por si es PostBack)
            actualizarReglasDoc();
        });

        function mostrarExitoLocal(mensaje) {
            $('.modal').modal('hide');
            $('.modal-backdrop').remove();
            if (typeof mostrarExito === 'function') mostrarExito(mensaje, 'Secretaria_Clientes.aspx');
            else window.location.href = 'Secretaria_Clientes.aspx';
        }
    </script>
</asp:Content>