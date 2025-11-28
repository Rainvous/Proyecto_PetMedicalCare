<%@ Page Title="Gestión de Veterinarios" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_Veterinarios.aspx.cs"
    Inherits="SoftPetWA.Secretaria_Veterinarios" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestión de Veterinarios
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/clientes.css" rel="stylesheet" />
    <style>
        .form-control[readonly], .form-select[disabled] { background-color: #e9ecef; cursor: not-allowed; opacity: 1; }
        .val-error { color: #dc3545; font-size: 0.875em; margin-top: 0.25rem; display: block; }
        .is-invalid { border-color: #dc3545 !important; }
        /* Paginación */
        .custom-pagination .page-item { margin: 0 3px; }
        .custom-pagination .page-link { border: none; border-radius: 50% !important; width: 35px; height: 35px; display: flex; align-items: center; justify-content: center; color: #6c757d; background-color: transparent; font-weight: 600; transition: all 0.2s; }
        .custom-pagination .page-link:hover { background-color: #e9ecef; color: #0097a7; }
        .custom-pagination .page-item.active .page-link { background-color: #0097a7; color: white; box-shadow: 0 2px 5px rgba(0, 151, 167, 0.3); }
        .pagination-container { background-color: #fff; border-radius: 0.5rem; box-shadow: 0 .125rem .25rem rgba(0,0,0,.075); padding: 1rem; }
        /* Botón calendario */
        .action-btn-calendar { background-color: #e0f2f1; color: #00897b; border: none; }
        .action-btn-calendar:hover { background-color: #00897b; color: white; }
        /* Tabs Horario */
        .nav-tabs .nav-link { color: #6c757d; }
        .nav-tabs .nav-link.active { color: #0097a7; font-weight: bold; border-bottom: 3px solid #0097a7 !important; }
        /* Tunning del Input Date */
        input[type="date"] { position: relative; padding: 10px; border-radius: 8px; border: 1px solid #ced4da; font-family: inherit; }
        input[type="date"]::-webkit-calendar-picker-indicator { color: transparent; background: transparent; z-index: 1; width: 100%; height: 100%; position: absolute; top: 0; left: 0; cursor: pointer; }
        input[type="date"]:after { content: "\f073"; font-family: "Font Awesome 5 Free"; font-weight: 900; color: #0097a7; position: absolute; right: 10px; top: 50%; transform: translateY(-50%); pointer-events: none; }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <asp:UpdatePanel ID="updPanelVeterinarios" runat="server" UpdateMode="Conditional">
        <ContentTemplate>
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3><i class="fas fa-user-md me-2"></i>Gestión de Veterinarios</h3>
            </div>

            <%-- FILTROS --%>
            <div class="card shadow-sm mb-4 filter-bar">
                <div class="card-body">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-3">
                            <label class="form-label">Nombre</label>
                            <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" placeholder="Buscar por nombre..."></asp:TextBox>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Documento</label>
                            <div class="input-group">
                                <asp:DropDownList ID="ddlDocumento" runat="server" CssClass="form-select" Style="max-width: 80px;"></asp:DropDownList>
                                <asp:TextBox ID="txtDocumento" runat="server" CssClass="form-control" placeholder="Número..."></asp:TextBox>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">Especialidad</label>
                            <asp:DropDownList ID="ddlEspecialidad" runat="server" CssClass="form-select"></asp:DropDownList>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label">Estado</label>
                            <asp:DropDownList ID="ddlFiltroEstado" runat="server" CssClass="form-select">
                                <asp:ListItem Value="-1" Text="Todos" Selected="True"></asp:ListItem>
                                <asp:ListItem Value="1" Text="Activo"></asp:ListItem>
                                <asp:ListItem Value="0" Text="Inactivo"></asp:ListItem>
                            </asp:DropDownList>
                        </div>
                        <div class="col-md-2 d-flex">
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
                <asp:LinkButton ID="btnNuevoVeterinario" runat="server" CssClass="btn btn-success" OnClick="btnNuevoVeterinario_Click">
                    <i class='fas fa-plus me-2'></i>Nuevo Veterinario
                </asp:LinkButton>
            </div>

            <%-- LISTA VETERINARIOS --%>
            <div id="divListaVeterinarios" runat="server" visible="false">
                <asp:Repeater ID="rptVeterinarios" runat="server" OnItemCommand="rptVeterinarios_ItemCommand">
                    <ItemTemplate>
                        <div class="client-list-row">
                            <div class="row align-items-center">
                                <div class="col-lg-3 mb-2 mb-lg-0">
                                    <div class="client-info">
                                        <div class="client-avatar" style='background-color: <%# Eval("AvatarColor") %>;'>
                                            <%# Eval("Iniciales") %>
                                        </div>
                                        <div>
                                            <span class="name"><%# Eval("Nombre") %></span>
                                            <span class="email"><%# Eval("Email") %></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6 col-lg-2 mb-2 mb-lg-0">
                                    <span class="d-lg-none header-text">Documento: </span>
                                    <span class="row-text"><%# Eval("TipoDocumento") %>: <%# Eval("NumDocumento") %></span>
                                </div>
                                <div class="col-6 col-lg-1 mb-2 mb-lg-0">
                                    <span class="d-lg-none header-text">Contratación: </span>
                                    <span class="row-text"><%# Eval("FechaContratacion", "{0:dd/MM/yyyy}") %></span>
                                </div>
                                <div class="col-6 col-lg-2 mb-2 mb-lg-0">
                                    <span class="d-lg-none header-text">Especialidad: </span>
                                    <span class="row-text"><%# Eval("Especialidad") %></span>
                                </div>
                                <div class="col-6 col-lg-1 mb-2 mb-lg-0">
                                    <span class="d-lg-none header-text">Teléfono: </span>
                                    <span class="row-text"><%# Eval("Telefono") %></span>
                                </div>
                                <div class="col-6 col-lg-1 mb-2 mb-lg-0">
                                    <span class="badge rounded-pill <%# Eval("Estado").ToString() == "Activo" ? "bg-success-soft text-success" : "bg-danger-soft text-danger" %>">
                                        <%# Eval("Estado") %>
                                    </span>
                                </div>
                                
                                <div class="col-12 col-lg-2 text-lg-center mt-2 mt-lg-0">
                                    <button type="button" class="btn action-btn action-btn-calendar me-1" title="Gestionar Horario"
                                        onclick='abrirModalHorarioJS(this)'
                                        data-id='<%# Eval("VeterinarioID") %>'
                                        data-nombre='<%# Eval("Nombre") %>'
                                        data-estado='<%# Eval("Estado") %>'
                                        data-estadolab='<%# Eval("EstadoLaboral") %>'
                                        data-fechacont='<%# Eval("FechaContratacion", "{0:yyyy-MM-dd}") %>'>
                                        <i class="fas fa-calendar-alt"></i>
                                    </button>

                                    <button type="button" class="btn action-btn action-btn-view" title="Ver"
                                        onclick='abrirModalJS(this, true)'
                                        data-id='<%# Eval("VeterinarioID") %>'
                                        data-personaid='<%# Eval("PersonaID") %>'
                                        data-usuarioid='<%# Eval("UsuarioID") %>' 
                                        data-nombre='<%# Eval("Nombre") %>'
                                        data-email='<%# Eval("Email") %>'
                                        data-username='<%# Eval("Username") %>'
                                        data-telefono='<%# Eval("Telefono") %>'
                                        data-tipodoc='<%# Eval("TipoDocumento") %>'
                                        data-numdoc='<%# Eval("NumDocumento") %>'
                                        data-ruc='<%# Eval("RUC") %>'
                                        data-direccion='<%# Eval("Direccion") %>'
                                        data-sexo='<%# Eval("Sexo") %>'
                                        data-fecha='<%# Eval("FechaContratacion", "{0:yyyy-MM-dd}") %>'
                                        data-especialidad='<%# Eval("Especialidad") %>'
                                        data-estado='<%# Eval("Estado") %>'
                                        data-estadolab='<%# Eval("EstadoLaboral") %>'>
                                        <i class="fas fa-eye"></i>
                                    </button>
                                    <button type="button" class="btn action-btn action-btn-edit ms-1" title="Editar"
                                        onclick='abrirModalJS(this, false)'
                                        data-id='<%# Eval("VeterinarioID") %>'
                                        data-personaid='<%# Eval("PersonaID") %>'
                                        data-usuarioid='<%# Eval("UsuarioID") %>'
                                        data-nombre='<%# Eval("Nombre") %>'
                                        data-email='<%# Eval("Email") %>'
                                        data-username='<%# Eval("Username") %>'
                                        data-telefono='<%# Eval("Telefono") %>'
                                        data-tipodoc='<%# Eval("TipoDocumento") %>'
                                        data-numdoc='<%# Eval("NumDocumento") %>'
                                        data-ruc='<%# Eval("RUC") %>'
                                        data-direccion='<%# Eval("Direccion") %>'
                                        data-sexo='<%# Eval("Sexo") %>'
                                        data-fecha='<%# Eval("FechaContratacion", "{0:yyyy-MM-dd}") %>'
                                        data-especialidad='<%# Eval("Especialidad") %>'
                                        data-estado='<%# Eval("Estado") %>'
                                        data-estadolab='<%# Eval("EstadoLaboral") %>'>
                                        <i class="fas fa-pencil-alt"></i>
                                    </button>
                                    <button type="button" class="btn action-btn action-btn-delete ms-1" title="Eliminar"
                                        onclick="abrirModalEliminar('<%# Eval("VeterinarioID") %>', '<%# Eval("Nombre") %>')">
                                        <i class="fas fa-trash-alt"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>

                <div class="pagination-container d-flex justify-content-between align-items-center mt-3">
                    <div class="text-muted small">
                        Mostrando <asp:Literal ID="litRegistrosActuales" runat="server" Text="0-0"></asp:Literal>
                        de <asp:Literal ID="litRegistrosTotales" runat="server" Text="0"></asp:Literal> Veterinarios
                    </div>
                    <nav class="custom-pagination">
                        <ul class="pagination mb-0">
                            <li class="page-item">
                                <asp:LinkButton ID="lnkAnterior" runat="server" CssClass="page-link" OnClick="lnkPaginado_Click" CommandName="Anterior" CausesValidation="false">
                                    <i class="fas fa-chevron-left"></i>
                                </asp:LinkButton>
                            </li>
                            <asp:Repeater ID="rptPaginador" runat="server" OnItemCommand="rptPaginador_ItemCommand">
                                <ItemTemplate>
                                    <li class='<%# (bool)Eval("EsPaginaActual") ? "page-item active" : "page-item" %>'>
                                        <asp:LinkButton ID="lnkPagina" runat="server" CssClass="page-link" CommandName="IrPagina"
                                            CommandArgument='<%# Eval("Pagina") %>' CausesValidation="false">
                                            <%# Eval("Pagina") %>
                                        </asp:LinkButton>
                                    </li>
                                </ItemTemplate>
                            </asp:Repeater>
                            <li class="page-item">
                                <asp:LinkButton ID="lnkSiguiente" runat="server" CssClass="page-link" OnClick="lnkPaginado_Click" CommandName="Siguiente" CausesValidation="false">
                                    <i class="fas fa-chevron-right"></i>
                                </asp:LinkButton>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>

            <asp:Panel ID="pnlSinResultados" runat="server" Visible="true" CssClass="text-center py-5">
                <div class="text-muted">
                    <i class="fas fa-search mb-3" style="font-size: 3rem; color: #dee2e6;"></i>
                    <h5 class="fw-bold text-secondary">No se encontraron resultados</h5>
                    <p class="mb-0">Intenta ajustar los filtros de búsqueda.</p>
                </div>
            </asp:Panel>
        </ContentTemplate>
    </asp:UpdatePanel>

    <%-- MODAL VETERINARIO (REGISTRO/EDICIÓN) --%>
    <div class="modal fade" id="modalVeterinario" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalVeterinarioLabel">Registrar Nuevo Veterinario</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <asp:UpdatePanel ID="updModalVeterinario" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body">
                            <asp:Literal ID="litModalError" runat="server" EnableViewState="false"></asp:Literal>
                            <asp:HiddenField ID="hdVeterinarioID" runat="server" Value="0" />
                            <asp:HiddenField ID="hdPersonaID" runat="server" Value="0" />
                            <asp:HiddenField ID="hdTempUsuarioID" runat="server" Value="0" />

                            <div class="row g-3">
                                <div class="col-md-12">
                                    <label class="form-label">Nombre Completo *</label>
                                    <asp:TextBox ID="txtModalNombre" runat="server" CssClass="form-control" MaxLength="100"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valNombre" runat="server" ControlToValidate="txtModalNombre" ErrorMessage="El nombre es requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarVeterinario"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="valNombreRegex" runat="server" ControlToValidate="txtModalNombre" ErrorMessage="Solo se permiten letras y espacios." CssClass="val-error" Display="Dynamic" ValidationExpression="^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$" ValidationGroup="GuardarVeterinario"></asp:RegularExpressionValidator>
                                </div>
                                <div class="col-md-12">
                                    <label class="form-label">Email *</label>
                                    <asp:TextBox ID="txtModalEmail" runat="server" CssClass="form-control" TextMode="Email" MaxLength="100"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valEmailReq" runat="server" ControlToValidate="txtModalEmail" ErrorMessage="El email es requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarVeterinario"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="valEmailFmt" runat="server" ControlToValidate="txtModalEmail" ErrorMessage="Formato inválido. (ej: ejemplo@gmail.com)" CssClass="val-error" Display="Dynamic" ValidationExpression="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$" ValidationGroup="GuardarVeterinario"></asp:RegularExpressionValidator>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Usuario *</label>
                                    <asp:TextBox ID="txtModalUsuario" runat="server" CssClass="form-control" MaxLength="20"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valUsuario" runat="server" ControlToValidate="txtModalUsuario" ErrorMessage="El usuario es requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarVeterinario"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="valUsuarioRegex" runat="server" ControlToValidate="txtModalUsuario" ErrorMessage="Solo alfanumérico (5-20 chars)." CssClass="val-error" Display="Dynamic" ValidationExpression="^[a-zA-Z0-9]{5,20}$" ValidationGroup="GuardarVeterinario"></asp:RegularExpressionValidator>
                                </div>
                                <div class="col-md-12">
                                    <label class="form-label">Teléfono</label>
                                    <asp:TextBox ID="txtModalTelefono" runat="server" CssClass="form-control" MaxLength="9" onkeypress="return (event.charCode >= 48 && event.charCode <= 57)"></asp:TextBox>
                                    <asp:RegularExpressionValidator ID="valTel" runat="server" ControlToValidate="txtModalTelefono" ErrorMessage="Debe tener 9 dígitos numéricos." CssClass="val-error" Display="Dynamic" ValidationExpression="^\d{9}$" ValidationGroup="GuardarVeterinario"></asp:RegularExpressionValidator>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Tipo Doc.</label>
                                    <asp:DropDownList ID="ddlModalTipoDoc" runat="server" CssClass="form-select" onchange="actualizarReglasDoc()"></asp:DropDownList>
                                </div>
                                
                                <%-- DOCUMENTO: Validadores restaurados --%>
                                <div class="col-md-5">
                                    <label class="form-label">Nro Doc. *</label>
                                    <%-- onkeypress: Permite solo números si es DNI --%>
                                    <asp:TextBox ID="txtModalNumDoc" runat="server" CssClass="form-control" 
                                        onkeypress="var tipo = document.getElementById(this.id.replace('txtModalNumDoc','ddlModalTipoDoc')); if(tipo && tipo.options[tipo.selectedIndex].text === 'DNI') return (event.charCode >= 48 && event.charCode <= 57);"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="valNumDoc" runat="server" ControlToValidate="txtModalNumDoc" ErrorMessage="Requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarVeterinario"></asp:RequiredFieldValidator>
                                    <asp:CustomValidator ID="valDocLength" runat="server" ControlToValidate="txtModalNumDoc" ClientValidationFunction="validarDocumento" ErrorMessage="Longitud incorrecta: DNI requiere 8 dígitos, CE requiere 12 caracteres." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarVeterinario"></asp:CustomValidator>
                                </div>

                                <%-- RUC: Validadores restaurados --%>
                                <div class="col-md-4">
                                    <label class="form-label">RUC (Opcional)</label>
                                    <asp:TextBox ID="txtModalRUC" runat="server" CssClass="form-control" MaxLength="11" onkeypress="return (event.charCode >= 48 && event.charCode <= 57)"></asp:TextBox>
                                    <asp:RegularExpressionValidator ID="valRuc" runat="server" ControlToValidate="txtModalRUC" ErrorMessage="RUC inválido. Debe tener 11 dígitos y empezar con 10 o 20." CssClass="val-error" Display="Dynamic" ValidationExpression="^(10|20)\d{9}$" ValidationGroup="GuardarVeterinario"></asp:RegularExpressionValidator>
                                </div>

                                <div class="col-md-12">
                                    <label class="form-label">Dirección</label>
                                    <asp:TextBox ID="txtModalDireccion" runat="server" CssClass="form-control" MaxLength="200"></asp:TextBox>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Sexo</label>
                                    <asp:DropDownList ID="ddlModalSexo" runat="server" CssClass="form-select">
                                        <asp:ListItem Value="M">Masculino</asp:ListItem>
                                        <asp:ListItem Value="F">Femenino</asp:ListItem>
                                        <asp:ListItem Value="O" Selected="True">Otro</asp:ListItem>
                                    </asp:DropDownList>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Rol</label>
                                    <asp:TextBox ID="txtModalRol" runat="server" CssClass="form-control" Text="Veterinario" ReadOnly="true"></asp:TextBox>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Estado</label>
                                    <asp:DropDownList ID="ddlModalEstado" runat="server" CssClass="form-select">
                                        <asp:ListItem Value="Activo">Activo</asp:ListItem>
                                        <asp:ListItem Value="Inactivo">Inactivo</asp:ListItem>
                                    </asp:DropDownList>
                                </div>
                                <div class="col-12"><hr class="my-3" /></div>
                                <div class="col-md-4">
                                    <label class="form-label">Contratación</label>
                                    <asp:TextBox ID="txtModalFechaContratacion" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Especialidad</label>
                                    <asp:DropDownList ID="ddlModalEspecialidad" runat="server" CssClass="form-select"></asp:DropDownList>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Laboral</label>
                                    <asp:DropDownList ID="ddlModalEstadoLaboral" runat="server" CssClass="form-select">
                                        <asp:ListItem Value="ACTIVO">Activo</asp:ListItem>
                                        <asp:ListItem Value="SUSPENDIDO">Suspendido</asp:ListItem>
                                        <asp:ListItem Value="INACTIVO">Inactivo</asp:ListItem>
                                    </asp:DropDownList>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <asp:Button ID="btnGuardarVeterinario" runat="server" Text="Guardar" CssClass="btn btn-primary" 
                                OnClick="btnGuardarVeterinario_Click" ValidationGroup="GuardarVeterinario" 
                                OnClientClick="if(!Page_ClientValidate('GuardarVeterinario')){ applyBootstrapValidation(); return false; }" />
                        </div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>

    <%-- MODAL HORARIO --%>
    <div class="modal fade" id="modalHorario" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content border-0 shadow">
                <div class="modal-header bg-white border-bottom pb-3">
                    <div>
                        <h5 class="modal-title fw-bold text-secondary mb-0"><i class="fas fa-clock text-primary me-2"></i>Horario Laboral</h5>
                        <small class="text-muted">Configura la disponibilidad.</small>
                    </div>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <asp:UpdatePanel ID="updModalHorario" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body p-4">
                            <asp:HiddenField ID="hdHorarioVetID" runat="server" />
                            <asp:HiddenField ID="hdModoHorario" runat="server" Value="DIA" />
                            
                            <div class="d-flex align-items-center mb-4 p-3 bg-light rounded">
                                <div class="avatar-small me-3 bg-primary text-white rounded-circle d-flex align-items-center justify-content-center" style="width:40px; height:40px;">
                                    <i class="fas fa-user-md"></i>
                                </div>
                                <div>
                                    <h6 class="mb-0 fw-bold text-dark" id="lblNombreVetHorario"></h6>
                                    <span class="text-muted small">Programación de turnos</span>
                                </div>
                            </div>

                            <ul class="nav nav-tabs mb-4" id="tabHorarios" role="tablist">
                                <li class="nav-item w-50 text-center" role="presentation">
                                    <button class="nav-link active w-100" id="dia-tab" data-bs-toggle="tab" data-bs-target="#pnlDia" type="button" role="tab" onclick="setModo('DIA')">Día Único</button>
                                </li>
                                <li class="nav-item w-50 text-center" role="presentation">
                                    <button class="nav-link w-100" id="rango-tab" data-bs-toggle="tab" data-bs-target="#pnlRango" type="button" role="tab" onclick="setModo('RANGO')">Rango de Fechas</button>
                                </li>
                            </ul>

                            <div class="tab-content">
                                <div class="tab-pane fade show active" id="pnlDia" role="tabpanel">
                                    <div class="mb-3">
                                        <label class="form-label fw-bold small text-muted">FECHA LABORAL</label>
                                        <asp:TextBox ID="txtFechaUnica" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="pnlRango" role="tabpanel">
                                    <div class="row g-2 mb-3">
                                        <div class="col-6">
                                            <label class="form-label fw-bold small text-muted">DESDE</label>
                                            <asp:TextBox ID="txtFechaDesde" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox>
                                        </div>
                                        <div class="col-6">
                                            <label class="form-label fw-bold small text-muted">HASTA</label>
                                            <asp:TextBox ID="txtFechaHasta" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox>
                                        </div>
                                    </div>
                                    <div class="alert alert-info d-flex align-items-center py-2 small" role="alert">
                                        <i class="fas fa-info-circle me-2"></i>
                                        <div>Se aplicará el mismo horario a todos los días.</div>
                                    </div>
                                </div>
                            </div>

                            <hr class="my-4 text-muted opacity-25" />

                            <div class="row g-2">
                                <div class="col-6">
                                    <label class="form-label fw-bold small text-muted">HORA INICIO</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-white border-end-0"><i class="far fa-clock text-muted"></i></span>
                                        <asp:DropDownList ID="ddlHoraInicio" runat="server" CssClass="form-select border-start-0"></asp:DropDownList>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <label class="form-label fw-bold small text-muted">HORA FIN</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-white border-end-0"><i class="far fa-clock text-muted"></i></span>
                                        <asp:DropDownList ID="ddlHoraFin" runat="server" CssClass="form-select border-start-0"></asp:DropDownList>
                                    </div>
                                </div>
                            </div>
                            
                            <asp:Literal ID="litErrorHorario" runat="server"></asp:Literal>
                        </div>
                        <div class="modal-footer bg-light border-top-0">
                            <button type="button" class="btn btn-link text-muted text-decoration-none" data-bs-dismiss="modal">Cancelar</button>
                            <asp:Button ID="btnGuardarHorario" runat="server" Text="Guardar Horario" CssClass="btn btn-primary px-4 rounded-pill" OnClick="btnGuardarHorario_Click" />
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
                <div class="modal-header">
                    <h5 class="modal-title">Confirmar Eliminación</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <asp:UpdatePanel ID="updModalEliminar" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body">
                            <asp:HiddenField ID="hdVeterinarioIDEliminar" runat="server" Value="0" />
                            ¿Eliminar a "<strong id="lblNombreVeterinarioEliminar"></strong>"?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <asp:Button ID="btnConfirmarEliminar" runat="server" Text="Sí, Eliminar" CssClass="btn btn-danger" OnClick="btnConfirmarEliminar_Click" />
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
        // --- 1. REGLAS DOCUMENTO (Longitud y tipo) ---
        function actualizarReglasDoc() {
            var ddl = document.getElementById('<%= ddlModalTipoDoc.ClientID %>');
            var txt = document.getElementById('<%= txtModalNumDoc.ClientID %>');
            if (ddl && txt) {
                var tipo = ddl.options[ddl.selectedIndex].text;
                if (tipo === "DNI") { 
                    txt.setAttribute("maxLength", "8"); 
                    if (txt.value.length > 8) txt.value = txt.value.substring(0, 8); 
                } else { 
                    txt.setAttribute("maxLength", "12"); 
                    if (txt.value.length > 12) txt.value = txt.value.substring(0, 12); 
                }
            }
        }

        // --- 2. VALIDACIÓN DOCUMENTO (Custom Validator) ---
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

        // --- 3. VALIDACIÓN VISUAL (Bordes Rojos) ---
        function applyBootstrapValidation() {
            if (typeof (Page_Validators) != "undefined") {
                for (var i = 0; i < Page_Validators.length; i++) ValidatorUpdateDisplay(Page_Validators[i]);
            }
        }
        function ValidatorUpdateDisplay(val) {
            val.style.display = val.isvalid ? "none" : "inline";
            var ctrl = document.getElementById(val.controltovalidate);
            if (ctrl) {
                if (!val.isvalid) ctrl.classList.add("is-invalid");
                else ctrl.classList.remove("is-invalid");
            }
        }

        // --- MODAL VETERINARIO ---
        function abrirModalJS(btn, esSoloLectura) {
            var id = btn.getAttribute('data-id');
            $('#<%= hdVeterinarioID.ClientID %>').val(id);
            $('#<%= hdPersonaID.ClientID %>').val(btn.getAttribute('data-personaid'));
            $('#<%= hdTempUsuarioID.ClientID %>').val(btn.getAttribute('data-usuarioid'));
            $('#<%= txtModalNombre.ClientID %>').val(btn.getAttribute('data-nombre'));
            $('#<%= txtModalEmail.ClientID %>').val(btn.getAttribute('data-email'));
            $('#<%= txtModalUsuario.ClientID %>').val(btn.getAttribute('data-username'));
            $('#<%= txtModalTelefono.ClientID %>').val(btn.getAttribute('data-telefono'));
            $('#<%= ddlModalTipoDoc.ClientID %>').val(btn.getAttribute('data-tipodoc'));
            $('#<%= txtModalNumDoc.ClientID %>').val(btn.getAttribute('data-numdoc'));
            $('#<%= txtModalRUC.ClientID %>').val(btn.getAttribute('data-ruc'));
            $('#<%= txtModalDireccion.ClientID %>').val(btn.getAttribute('data-direccion'));
            $('#<%= ddlModalSexo.ClientID %>').val(btn.getAttribute('data-sexo'));
            $('#<%= txtModalFechaContratacion.ClientID %>').val(btn.getAttribute('data-fecha'));
            $('#<%= ddlModalEspecialidad.ClientID %>').val(btn.getAttribute('data-especialidad'));
            $('#<%= ddlModalEstado.ClientID %>').val(btn.getAttribute('data-estado'));
            $('#<%= ddlModalEstadoLaboral.ClientID %>').val(btn.getAttribute('data-estadolab'));

            configurarModoLectura(esSoloLectura);
            $('#modalVeterinarioLabel').text(esSoloLectura ? "Ver Detalles" : "Modificar Veterinario");
            actualizarReglasDoc();
            new bootstrap.Modal(document.getElementById('modalVeterinario')).show();
        }

        function configurarModoLectura(soloLectura) {
            var inputs = ['txtModalNombre', 'txtModalEmail', 'txtModalUsuario', 'txtModalTelefono', 'txtModalNumDoc', 'txtModalRUC', 'txtModalDireccion', 'txtModalFechaContratacion'];
            inputs.forEach(id => $('input[id$="' + id + '"]').prop('readonly', soloLectura));
            var selects = ['ddlModalTipoDoc', 'ddlModalSexo', 'ddlModalEspecialidad', 'ddlModalEstado', 'ddlModalEstadoLaboral'];
            selects.forEach(id => $('select[id$="' + id + '"]').prop('disabled', soloLectura));
            var btn = $('[id$="btnGuardarVeterinario"]');
            if (soloLectura) btn.hide(); else btn.show();
        }

        function abrirModalEliminar(id, nombre) {
            $('#<%= hdVeterinarioIDEliminar.ClientID %>').val(id);
            $('#lblNombreVeterinarioEliminar').text(nombre);
            new bootstrap.Modal(document.getElementById('modalConfirmarEliminar')).show();
        }

        // --- MODAL HORARIO ---
        function abrirModalHorarioJS(btn) {
            var estado = btn.getAttribute('data-estado');
            var estadoLab = btn.getAttribute('data-estadolab');
            if (estado !== "Activo" || estadoLab !== "ACTIVO") {
                Swal.fire({
                    icon: 'warning',
                    title: 'Veterinario No Disponible',
                    text: 'Este veterinario se encuentra inactivo o suspendido. Actívelo para gestionar su horario.',
                    confirmButtonColor: '#0097a7'
                });
                return;
            }

            var id = btn.getAttribute('data-id');
            var nombre = btn.getAttribute('data-nombre');
            var fechaCont = btn.getAttribute('data-fechacont'); 

            $('#<%= hdHorarioVetID.ClientID %>').val(id);
            $('#lblNombreVetHorario').text(nombre);
            
            // Fecha Mínima
            var parts = fechaCont.split('-');
            var contrato = new Date(parts[0], parts[1] - 1, parts[2]); 
            var hoy = new Date();
            hoy.setHours(0,0,0,0); 

            var fechaMinima = (contrato > hoy) ? contrato : hoy;
            var y = fechaMinima.getFullYear();
            var m = String(fechaMinima.getMonth() + 1).padStart(2, '0');
            var d = String(fechaMinima.getDate()).padStart(2, '0');
            var minStr = y + '-' + m + '-' + d;

            $('#<%= txtFechaUnica.ClientID %>').attr('min', minStr);
            $('#<%= txtFechaDesde.ClientID %>').attr('min', minStr);
            $('#<%= txtFechaHasta.ClientID %>').attr('min', minStr);

            // Limpieza
            $('#<%= txtFechaUnica.ClientID %>').val('');
            $('#<%= txtFechaDesde.ClientID %>').val('');
            $('#<%= txtFechaHasta.ClientID %>').val('');
            $('#<%= litErrorHorario.ClientID %>').html(''); 
            $('#<%= ddlHoraInicio.ClientID %>').prop('selectedIndex', 0);
            $('#<%= ddlHoraFin.ClientID %>').val('16:00');

            var triggerEl = document.querySelector('#tabHorarios button[id="dia-tab"]');
            bootstrap.Tab.getOrCreateInstance(triggerEl).show();
            $('#<%= hdModoHorario.ClientID %>').val('DIA');

            new bootstrap.Modal(document.getElementById('modalHorario')).show();
        }

        function setModo(modo) {
            $('#<%= hdModoHorario.ClientID %>').val(modo);
        }

        $(document).ready(function () {
            $('#<%= txtFechaDesde.ClientID %>').on('change', function () {
                var desde = $(this).val();
                if (desde) {
                    $('#<%= txtFechaHasta.ClientID %>').attr('min', desde);
                    var hasta = $('#<%= txtFechaHasta.ClientID %>').val();
                    if (hasta && hasta < desde) $('#<%= txtFechaHasta.ClientID %>').val('');
                }
            });

            var prm = Sys.WebForms.PageRequestManager.getInstance();
            prm.add_endRequest(function (sender, e) {
                if (sender._postBackSettings.sourceElement) {
                    var idBtn = sender._postBackSettings.sourceElement.id;
                    if (idBtn.includes("btnGuardarVeterinario") && $('.val-error').is(':visible') == false && $('#<%= litModalError.ClientID %>').text() == "") {
                        $('#modalVeterinario').modal('hide'); $('.modal-backdrop').remove();
                    }
                    if (idBtn.includes("btnConfirmarEliminar")) {
                        $('#modalConfirmarEliminar').modal('hide'); $('.modal-backdrop').remove();
                    }
                }
            });
            // Aplicar reglas al recargar
            actualizarReglasDoc();
        });

        function mostrarExitoLocal(mensaje) {
            $('.modal').modal('hide');
            $('.modal-backdrop').remove();
            if (typeof mostrarExito === 'function') { mostrarExito(mensaje, 'Secretaria_Veterinarios.aspx'); }
            else { alert(mensaje); window.location.href = 'Secretaria_Veterinarios.aspx'; }
        }
    </script>
</asp:Content>