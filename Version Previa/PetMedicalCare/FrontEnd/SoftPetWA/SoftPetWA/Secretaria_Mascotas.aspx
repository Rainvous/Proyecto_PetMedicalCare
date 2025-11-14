<%-- Nombre del archivo: Secretaria_Mascotas.aspx --%>
<%@ Page Title="Gestión de Mascotas" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true" 
    CodeBehind="Secretaria_Mascotas.aspx.cs" 
    Inherits="SoftPetWA.Secretaria_Mascotas" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestión de Mascotas
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/mascotas.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3><i class="fas fa-paw me-2"></i>Gestión de Mascotas</h3>
    </div>

    <%-- 1. Barra de Filtros --%>
    <div class="card shadow-sm mb-4 filter-bar">
        <div class="card-body">
            <div class="row g-3 align-items-end">
                <div class="col-md-3">
                    <label for="<%= txtNombreMascota.ClientID %>" class="form-label">Nombre de la Mascota</label>
                    <asp:TextBox ID="txtNombreMascota" runat="server" CssClass="form-control" placeholder="Buscar por nombre..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label for="<%= txtRaza.ClientID %>" class="form-label">Raza</label>
                    <asp:TextBox ID="txtRaza" runat="server" CssClass="form-control" placeholder="Ej: Labrador, Persa..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label for="<%= ddlEspecie.ClientID %>" class="form-label">Especie</label>
                    <asp:DropDownList ID="ddlEspecie" runat="server" CssClass="form-select"></asp:DropDownList>
                </div>
                <div class="col-md-3">
                    <label for="<%= txtPropietario.ClientID %>" class="form-label">Propietario</label>
                    <asp:TextBox ID="txtPropietario" runat="server" CssClass="form-control" placeholder="Nombre del dueño..."></asp:TextBox>
                </div>
                <div class="col-md-2 d-flex">
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
            mascotas
        </div>
        <div>
            <asp:LinkButton ID="btnNuevaMascota" runat="server" CssClass="btn btn-success" OnClick="btnNuevaMascota_Click">
                <i class='fas fa-plus me-2'></i>Nueva Mascota
            </asp:LinkButton>
        </div>
    </div>

    <%-- 3. Lista de Mascotas (Repeater en modo Grid) --%>
    <div class="row">
        <asp:Repeater ID="rptMascotas" runat="server" OnItemCommand="rptMascotas_ItemCommand">
            <ItemTemplate>
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="pet-card">
                        
                        <%-- 1. Header (Azul) --%>
                        <div class="pet-card-header">
                            <span class="badge rounded-pill <%# Eval("CssEstado") %> pet-status-badge"><%# Eval("Estado") %></span>
                            <div class="pet-avatar">
                                <asp:Image ID="imgAvatar" runat="server" ImageUrl='<%# Eval("AvatarURL") %>' alt="Avatar" />
                            </div>
                            <h3 class="pet-name"><%# Eval("Nombre") %></h3>
                            <span class="pet-breed"><%# Eval("EspecieRaza") %></span>
                        </div>

                        <%-- 2. Body (Info) --%>
                        <div class="pet-card-body">
                            <div class="pet-info-row">
                                <span class="label"><i class="fas fa-venus-mars me-2"></i>Sexo</span>
                                <span class="value"><%# Eval("Sexo") %></span>
                            </div>
                            <div class="pet-info-row">
                                <span class="label"><i class="fas fa-palette me-2"></i>Color</span>
                                <span class="value"><%# Eval("Color") %></span>
                            </div>

                            <%-- 3. Sub-tarjeta Propietario --%>
                            <div class="pet-owner-card">
                                <div class="owner-avatar" style='background-color: <%# Eval("PropietarioAvatarColor") %>;'>
                                    <%# Eval("PropietarioIniciales") %>
                                </div>
                                <div class="owner-info">
                                    <span class="name"><%# Eval("PropietarioNombre") %></span>
                                    <span class="phone"><%# Eval("PropietarioTelefono") %></span>
                                </div>
                            </div>
                        </div>

                        <%-- 4. Footer (Acciones) --%>
                        <div class="pet-card-footer">
                            <asp:LinkButton ID="btnHistorial" runat="server" CssClass="pet-action-btn btn-historial" CommandName="Historial" CommandArgument='<%# Eval("MascotaID") %>'>
                                <i class="fas fa-history"></i> Historial
                            </asp:LinkButton>
                            <asp:LinkButton ID="btnEditar" runat="server" CssClass="pet-action-btn btn-editar" CommandName="Editar" CommandArgument='<%# Eval("MascotaID") %>'>
                                <i class="fas fa-pencil-alt"></i> Editar
                            </asp:LinkButton>
                            <asp:LinkButton ID="btnEliminar" runat="server" CssClass="pet-action-btn btn-eliminar" CommandName="Eliminar" CommandArgument='<%# Eval("MascotaID") %>'>
                                <i class="fas fa-trash-alt"></i> Eliminar
                            </asp:LinkButton>
                        </div>
                    </div>
                </div>
            </ItemTemplate>
        </asp:Repeater>
    </div>

</asp:Content>