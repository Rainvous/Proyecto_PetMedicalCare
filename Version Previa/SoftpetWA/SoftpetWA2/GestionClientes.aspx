<%@ Page Title="" Language="C#" MasterPageFile="~/Admin.Master" AutoEventWireup="true" CodeBehind="GestionClientes.aspx.cs" Inherits="SoftpetWA2.GestionClientes" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <link href="Content/gestionclientes.css" rel="stylesheet" />
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <div class="gestion-clientes-container">
        <!-- Header -->
        <div class="page-header">
            <div class="d-flex align-items-center">
                <asp:LinkButton ID="btnVolver" runat="server" CssClass="btn-volver me-3" OnClick="btnVolver_Click">
                    <i class="fas fa-arrow-left"></i>
                </asp:LinkButton>
                <h2 class="page-title mb-0">Gestión de Clientes</h2>
            </div>
            <div class="header-actions">
                <button type="button" class="btn btn-icon">
                    <i class="fas fa-bell"></i>
                </button>
                <button type="button" class="btn btn-icon">
                    <i class="fas fa-cog"></i>
                </button>
                <button type="button" class="btn btn-icon">
                    <i class="fas fa-sign-out-alt"></i>
                </button>
            </div>
        </div>

        <!-- Filtros de búsqueda -->
        <div class="filtros-container">
            <div class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">Nombre</label>
                    <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" placeholder="Buscar por nombre..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label class="form-label">Documento</label>
                    <asp:TextBox ID="txtDocumento" runat="server" CssClass="form-control" placeholder="DNI"></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label class="form-label">Teléfono</label>
                    <asp:TextBox ID="txtTelefono" runat="server" CssClass="form-control" placeholder="Número..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label class="form-label">RUC (Opcional)</label>
                    <asp:TextBox ID="txtRUC" runat="server" CssClass="form-control" placeholder="RUC"></asp:TextBox>
                </div>
                <div class="col-md-3 d-flex align-items-end gap-2">
                    <asp:Button ID="btnBuscar" runat="server" Text="Buscar" CssClass="btn btn-buscar" OnClick="btnBuscar_Click" />
                    <asp:Button ID="btnLimpiar" runat="server" Text="×" CssClass="btn btn-limpiar" OnClick="btnLimpiar_Click" ToolTip="Limpiar filtros" />
                </div>
            </div>
        </div>

        <!-- Información de registros y botón nuevo -->
        <div class="info-bar">
            <div class="registros-info">
                Mostrando <strong><asp:Literal ID="litRegistrosActuales" runat="server">1-8</asp:Literal></strong> de <strong><asp:Literal ID="litRegistrosTotales" runat="server">47</asp:Literal></strong> clientes
            </div>
            <asp:Button ID="btnNuevoCliente" runat="server" Text="+ Nuevo Cliente" CssClass="btn btn-nuevo" OnClick="btnNuevoCliente_Click" />
        </div>

        <!-- Tabla de clientes -->
        <div class="tabla-container">
            <asp:GridView ID="gvClientes" runat="server" AutoGenerateColumns="False" 
                CssClass="table-clientes" OnRowCommand="gvClientes_RowCommand"
                OnRowDataBound="gvClientes_RowDataBound" DataKeyNames="ClienteID">
                <Columns>
                    <asp:TemplateField HeaderText="CLIENTE">
                        <ItemTemplate>
                            <div class="cliente-info">
                                <div class="avatar-circle" >
                                    <%# ObtenerInicialesHelper(Eval("Nombre").ToString()) %>
                                </div>
                                <div class="ms-3">
                                    <div class="cliente-nombre"><%# Eval("Nombre") %></div>
                                    <div class="cliente-email"><%# Eval("Email") %></div>
                                </div>
                            </div>
                        </ItemTemplate>
                    </asp:TemplateField>
                    
                    <asp:TemplateField HeaderText="DOCUMENTO">
                        <ItemTemplate>
                            <span class="text-muted"><%# Eval("TipoDocumento") %>: <%# Eval("NumeroDocumento") %></span>
                        </ItemTemplate>
                    </asp:TemplateField>
                    
                    <asp:TemplateField HeaderText="RUC">
                        <ItemTemplate>
                            <span class="text-muted"><%# Eval("RUC") != null && Eval("RUC").ToString() != "" ? "RUC: " + Eval("RUC") : "-" %></span>
                        </ItemTemplate>
                    </asp:TemplateField>
                    
                    <asp:TemplateField HeaderText="TELÉFONO">
                        <ItemTemplate>
                            <span class="text-muted"><%# Eval("Telefono") %></span>
                        </ItemTemplate>
                    </asp:TemplateField>
                    
                    <asp:TemplateField HeaderText="MASCOTAS">
                        <ItemTemplate>
                            <span class="badge-mascotas">
                                <i class="fas fa-paw me-1"></i><%# Eval("NumeroMascotas") %> mascota<%# Convert.ToInt32(Eval("NumeroMascotas")) != 1 ? "s" : "" %>
                            </span>
                        </ItemTemplate>
                    </asp:TemplateField>
                    
                    <asp:TemplateField HeaderText="ESTADO">
                        <ItemTemplate>
                            <span class='<%# "badge-estado " + (Convert.ToBoolean(Eval("Activo")) ? "badge-activo" : "badge-inactivo") %>'>
                                <%# Convert.ToBoolean(Eval("Activo")) ? "Activo" : "Inactivo" %>
                            </span>
                        </ItemTemplate>
                    </asp:TemplateField>
                    
                    <asp:TemplateField HeaderText="ACCIONES">
                        <ItemTemplate>
                            <div class="acciones-grupo">
                                <asp:LinkButton ID="btnVer" runat="server" CommandName="Ver" 
                                    CommandArgument='<%# Eval("ClienteID") %>' CssClass="btn-accion btn-ver" ToolTip="Ver detalles">
                                    <i class="fas fa-eye"></i>
                                </asp:LinkButton>
                                <asp:LinkButton ID="btnEditar" runat="server" CommandName="Editar" 
                                    CommandArgument='<%# Eval("ClienteID") %>' CssClass="btn-accion btn-editar" ToolTip="Editar">
                                    <i class="fas fa-edit"></i>
                                </asp:LinkButton>
                                <asp:LinkButton ID="btnEliminar" runat="server" CommandName="Eliminar" 
                                    CommandArgument='<%# Eval("ClienteID") %>' CssClass="btn-accion btn-eliminar" 
                                    ToolTip="Eliminar" OnClientClick="return confirm('¿Está seguro de eliminar este cliente?');">
                                    <i class="fas fa-trash-alt"></i>
                                </asp:LinkButton>
                            </div>
                        </ItemTemplate>
                    </asp:TemplateField>
                </Columns>
                <EmptyDataTemplate>
                    <div class="text-center py-5">
                        <i class="fas fa-users fa-3x text-muted mb-3"></i>
                        <p class="text-muted">No se encontraron clientes</p>
                    </div>
                </EmptyDataTemplate>
            </asp:GridView>
        </div>
    </div>
</asp:Content>
