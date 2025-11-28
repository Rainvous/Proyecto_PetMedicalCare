<%@ Page Title="Expedientes Clínicos" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_Expedientes.aspx.cs"
    Inherits="SoftPetWA.Secretaria_Expedientes" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Expedientes Clínicos
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/expedientes.css" rel="stylesheet" />
    <style>
        /* Estilos Base */
        .pet-avatar-small {
            background-color: #20c997; color: white; width: 50px; height: 50px; border-radius: 50%;
            display: flex; align-items: center; justify-content: center; font-size: 1.5rem; margin-right: 15px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .custom-pagination .page-item { margin: 0 3px; }
        .custom-pagination .page-link { border: none; border-radius: 50% !important; width: 35px; height: 35px; display: flex; align-items: center; justify-content: center; color: #6c757d; background-color: transparent; font-weight: 600; transition: all 0.2s; }
        .custom-pagination .page-link:hover { background-color: #e9ecef; color: #0097a7; }
        .custom-pagination .page-item.active .page-link { background-color: #0097a7; color: white; box-shadow: 0 2px 5px rgba(0, 151, 167, 0.3); }
        .pagination-container { background-color: #fff; border-radius: 0.5rem; box-shadow: 0 .125rem .25rem rgba(0,0,0,.075); padding: 1rem; }

        /* Botón Ver Expediente */
        .btn-ver-expediente {
            background-color: #e3f2fd; color: #0d6efd; border: 1px solid transparent; font-weight: 500; transition: all 0.2s;
        }
        .btn-ver-expediente:hover {
            background-color: #0d6efd; color: white; transform: translateY(-1px); box-shadow: 0 4px 6px rgba(13, 110, 253, 0.2);
        }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <asp:UpdatePanel ID="updPanelExpedientes" runat="server" UpdateMode="Conditional">
        <ContentTemplate>

            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3><i class="fas fa-folder-open me-2"></i>Expedientes Clínicos</h3>
            </div>

            <%-- BARRA DE FILTROS (4 Filtros + Botones) --%>
            <div class="card shadow-sm mb-4 filter-bar">
                <div class="card-body">
                    <div class="row g-3 align-items-end">
                        <%-- Nombre Mascota --%>
                        <div class="col-md-3">
                            <label class="form-label">Nombre Mascota</label>
                            <asp:TextBox ID="txtNombreMascota" runat="server" CssClass="form-control" placeholder="Buscar..."></asp:TextBox>
                        </div>
                        
                        <%-- Propietario --%>
                        <div class="col-md-3">
                            <label class="form-label">Propietario</label>
                            <asp:TextBox ID="txtPropietario" runat="server" CssClass="form-control" placeholder="Nombre del dueño..."></asp:TextBox>
                        </div>
                        
                        <%-- Especie --%>
                        <div class="col-md-2">
                            <label class="form-label">Especie</label>
                            <asp:DropDownList ID="ddlTipo" runat="server" CssClass="form-select"></asp:DropDownList>
                        </div>

                        <%-- NUEVO: Estado --%>
                        <div class="col-md-2">
                            <label class="form-label">Estado</label>
                            <asp:DropDownList ID="ddlEstado" runat="server" CssClass="form-select">
                                <asp:ListItem Value="-1" Text="Todos"></asp:ListItem>
                                <asp:ListItem Value="1" Text="Activo" Selected="True"></asp:ListItem>
                                <asp:ListItem Value="0" Text="Inactivo"></asp:ListItem>
                            </asp:DropDownList>
                        </div>
                        
                        <%-- Botones --%>
                        <div class="col-md-2 d-flex">
                            <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary me-2 w-100" OnClick="btnBuscar_Click">
                                <i class='fas fa-search me-1'></i>
                            </asp:LinkButton>
                            <asp:LinkButton ID="btnLimpiar" runat="server" CssClass="btn btn-outline-secondary w-100" OnClick="btnLimpiar_Click">
                                <i class='fas fa-times'></i>
                            </asp:LinkButton>
                        </div>
                    </div>
                </div>
            </div>

            <%-- LISTA EXPEDIENTES --%>
            <div id="divListaExpedientes" runat="server" visible="false">
                
                <%-- Encabezado Tabla (Reemplazo Documento por Estado) --%>
                <div class="client-list-header d-none d-lg-block mb-2 ps-3 pe-3">
                    <div class="row align-items-center text-muted small fw-bold text-uppercase">
                        <div class="col-lg-3">Mascota</div>
                        <div class="col-lg-3">Propietario</div>
                        <div class="col-lg-2">Estado</div> <%-- CAMBIO --%>
                        <div class="col-lg-2">Teléfono</div>
                        <div class="col-lg-2 text-center">Acciones</div>
                    </div>
                </div>

                <asp:Repeater ID="rptExpedientesLista" runat="server">
                    <ItemTemplate>
                        <div class="client-list-row card mb-2 border-0 shadow-sm hover-shadow">
                            <div class="card-body p-3">
                                <div class="row align-items-center">
                                    <%-- Columna Mascota --%>
                                    <div class="col-lg-3 mb-2 mb-lg-0">
                                        <div class="d-flex align-items-center">
                                            <div class="pet-avatar-small">
                                                <i class="<%# Eval("AvatarIcon") %>"></i>
                                            </div>
                                            <div>
                                                <span class="name d-block fw-bold text-dark" style="font-size:1.1rem;"><%# Eval("MascotaNombre") %></span>
                                                <span class="email text-muted small"><%# Eval("EspecieRaza") %></span>
                                            </div>
                                        </div>
                                    </div>

                                    <%-- Columna Propietario --%>
                                    <div class="col-6 col-lg-3 mb-2 mb-lg-0">
                                        <span class="d-lg-none fw-bold text-muted small">Propietario: </span>
                                        <span class="text-dark"><%# Eval("PropietarioNombre") %></span>
                                    </div>

                                    <%-- NUEVO: Columna Estado (Con Badge) --%>
                                    <div class="col-6 col-lg-2 mb-2 mb-lg-0">
                                        <span class="d-lg-none fw-bold text-muted small">Estado: </span>
                                        <span class="badge rounded-pill <%# (bool)Eval("Activo") ? "bg-success" : "bg-danger" %>">
                                            <%# (bool)Eval("Activo") ? "Activo" : "Inactivo" %>
                                        </span>
                                    </div>

                                    <%-- Columna Teléfono --%>
                                    <div class="col-6 col-lg-2 mb-2 mb-lg-0">
                                        <span class="d-lg-none fw-bold text-muted small">Teléfono: </span>
                                        <span class="text-dark"><%# Eval("Telefono") %></span>
                                    </div>

                                    <%-- Botón Acción --%>
                                    <div class="col-12 col-lg-2 text-lg-center mt-2 mt-lg-0">
                                        <asp:HyperLink ID="btnVerExpediente" runat="server" CssClass="btn btn-sm btn-ver-expediente w-100"
                                            NavigateUrl='<%# "Secretaria_ExpedienteDetalle.aspx?id=" + Eval("MascotaID") %>'
                                            onclick="$('#divLoadingGlobal').css('display', 'flex');">
                                            <i class="fas fa-file-medical me-1"></i> Ver Expediente
                                        </asp:HyperLink>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>

                <%-- Paginación --%>
                <div class="pagination-container d-flex justify-content-between align-items-center mt-3">
                    <div class="text-muted small">
                        Mostrando <asp:Literal ID="litRegistrosActuales" runat="server" Text="0-0"></asp:Literal>
                        de <asp:Literal ID="litRegistrosTotales" runat="server" Text="0"></asp:Literal> Expedientes
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
                                        <asp:LinkButton ID="lnkPagina" runat="server" CssClass="page-link" CommandName="IrPagina" CommandArgument='<%# Eval("Pagina") %>' CausesValidation="false">
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

            <%-- PANEL SIN RESULTADOS --%>
            <asp:Panel ID="pnlSinResultados" runat="server" Visible="true" CssClass="text-center py-5">
                <div class="text-muted opacity-50 mb-3">
                    <i class="fas fa-search" style="font-size: 4rem;"></i>
                </div>
                <h5 class="fw-bold text-muted">Búsqueda de Expedientes</h5>
                <asp:Label ID="lblMensajeVacio" runat="server" CssClass="text-muted" Text="Utilice los filtros para buscar."></asp:Label>
            </asp:Panel>

        </ContentTemplate>
    </asp:UpdatePanel>
</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>