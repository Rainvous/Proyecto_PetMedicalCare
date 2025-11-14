<%-- Nombre del archivo: Secretaria_Clientes.aspx --%>
<%@ Page Title="Gestión de Clientes" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true" 
    CodeBehind="Secretaria_Clientes.aspx.cs" 
    Inherits="SoftPetWA.Secretaria_Clientes" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestión de Clientes
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/clientes.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3><i class="fas fa-users me-2"></i>Gestión de Clientes</h3>
    </div>

    <%-- 1. Barra de Filtros --%>
    <div class="card shadow-sm mb-4 filter-bar">
        <div class="card-body">
            <div class="row g-3 align-items-end">
                <div class="col-md-3">
                    <label for="<%= txtNombre.ClientID %>" class="form-label">Nombre</label>
                    <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" placeholder="Buscar por nombre..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label for="<%= ddlDocumento.ClientID %>" class="form-label">Documento</label>
                    <div class="input-group">
                        <asp:DropDownList ID="ddlDocumento" runat="server" CssClass="form-select" style="max-width: 80px;"></asp:DropDownList>
                        <asp:TextBox ID="txtDocumento" runat="server" CssClass="form-control" placeholder="Número..."></asp:TextBox>
                    </div>
                </div>
                <div class="col-md-2">
                    <label for="<%= txtTelefono.ClientID %>" class="form-label">Teléfono</label>
                    <asp:TextBox ID="txtTelefono" runat="server" CssClass="form-control" placeholder="Número..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label for="<%= txtRUC.ClientID %>" class="form-label">RUC (Opcional)</label>
                    <asp:TextBox ID="txtRUC" runat="server" CssClass="form-control" placeholder="RUC..."></asp:TextBox>
                </div>
                <div class="col-md-3 d-flex">
                    <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary me-2" OnClick="btnBuscar_Click">
                        <i class='fas fa-search me-1'></i> Buscar
                    </asp:LinkButton>
                    <asp:LinkButton ID="btnLimpiar" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnLimpiar_Click">
                        <i class='fas fa-times'></i>
                    </asp:LinkButton>
                </div>
            </div>
        </div>
    </div>

    <%-- 2. Barra de Acciones y Conteo --%>
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="text-muted">
            Mostrando 
            <asp:Literal ID="litRegistrosActuales" runat="server" Text="0-0"></asp:Literal> 
            de 
            <asp:Literal ID="litRegistrosTotales" runat="server" Text="0"></asp:Literal> 
            Clientes
        </div>
        <div>
            <asp:LinkButton ID="btnNuevoCliente" runat="server" CssClass="btn btn-success" OnClick="btnNuevoCliente_Click">
                <i class='fas fa-plus me-2'></i>Nuevo Cliente
            </asp:LinkButton>
        </div>
    </div>

    <%-- 3. Lista de Clientes --%>
    <div>
        <%-- Cabecera de la Lista --%>
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

        <%-- Filas de Clientes (Repeater) --%>
        <asp:Repeater ID="rptClientes" runat="server" OnItemCommand="rptClientes_ItemCommand">
            <ItemTemplate>
                <div class="client-list-row">
                    <div class="row align-items-center">
                        <%-- Columna Cliente --%>
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
                        <%-- Columna Documento --%>
                        <div class="col-6 col-lg-2 mb-2 mb-lg-0">
                            <span class="d-lg-none header-text">Documento: </span>
                            <span class="row-text"><%# Eval("Documento") %></span>
                        </div>
                        <%-- Columna RUC --%>
                        <div class="col-6 col-lg-2 mb-2 mb-lg-0">
                            <span class="d-lg-none header-text">RUC: </span>
                            <span class="row-text"><%# Eval("RUC") %></span>
                        </div>
                        <%-- Columna Teléfono --%>
                        <div class="col-6 col-lg-1 mb-2 mb-lg-0">
                            <span class="d-lg-none header-text">Teléfono: </span>
                            <span class="row-text"><%# Eval("Telefono") %></span>
                        </div>
                        <%-- Columna Mascotas --%>
                        <div class="col-6 col-lg-1 mb-2 mb-lg-0">
                            <a href="#"><%# Eval("TextoMascotas") %></a>
                        </div>
                        <%-- Columna Estado --%>
                        <div class="col-6 col-lg-1 mb-2 mb-lg-0">
                            <span class="badge rounded-pill <%# Eval("CssEstado") == "text-success" ? "bg-success-soft text-success" : "bg-danger-soft text-danger" %>">
                                <%# Eval("Estado") %>
                            </span>
                        </div>
                        <%-- Columna Acciones --%>
                        <div class="col-6 col-lg-2 text-lg-center">
                            <asp:LinkButton ID="btnVer" runat="server" CssClass="btn action-btn action-btn-view" CommandName="Ver" CommandArgument='<%# Eval("ClienteID") %>'>
                                <i class="fas fa-eye"></i>
                            </asp:LinkButton>
                            <asp:LinkButton ID="btnEditar" runat="server" CssClass="btn action-btn action-btn-edit ms-1" CommandName="Editar" CommandArgument='<%# Eval("ClienteID") %>'>
                                <i class="fas fa-pencil-alt"></i>
                            </asp:LinkButton>
                            <asp:LinkButton ID="btnEliminar" runat="server" CssClass="btn action-btn action-btn-delete ms-1" CommandName="Eliminar" CommandArgument='<%# Eval("ClienteID") %>'>
                                <i class="fas fa-trash-alt"></i>
                            </asp:LinkButton>
                        </div>
                    </div>
                </div>
            </ItemTemplate>
        </asp:Repeater>
    </div>

</asp:Content>