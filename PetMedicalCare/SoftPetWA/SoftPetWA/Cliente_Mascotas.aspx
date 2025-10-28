<%-- Nombre del archivo: Cliente_Mascotas.aspx --%>
<%@ Page Title="Mis Mascotas" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true" 
    CodeBehind="Cliente_Mascotas.aspx.cs" 
    Inherits="SoftPetWA.Cliente_Mascotas" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Mis Mascotas
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/clientemascotas.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3><i class="fas fa-paw me-2"></i>Mis Mascotas</h3>
        <%-- Opcional: Botón para agregar nueva mascota (si el cliente puede hacerlo) --%>
        <%-- 
        <asp:LinkButton ID="btnNuevaMascota" runat="server" CssClass="btn btn-success">
            <i class="fas fa-plus me-2"></i>Registrar Nueva Mascota
        </asp:LinkButton> 
        --%>
    </div>

    <div class="row">
        <asp:Repeater ID="rptMascotasCliente" runat="server" OnItemCommand="rptMascotasCliente_ItemCommand">
            <ItemTemplate>
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="pet-card-cliente">
                        <div class="pet-avatar-lg">
                            <asp:Image ID="imgAvatarPetLg" runat="server" ImageUrl='<%# Eval("AvatarURL") %>' />
                        </div>
                        <h4 class="pet-name-lg"><%# Eval("Nombre") %></h4>
                        <span class="pet-breed-lg d-block"><%# Eval("EspecieRaza") %></span>
                        <div class="pet-info-sm mb-3">
                            <span><i class="fas fa-birthday-cake"></i> <%# Eval("Edad") %></span>
                            <span class="ms-3"><i class="fas fa-venus-mars"></i> <%# Eval("Sexo") %></span>
                        </div>
                        <asp:LinkButton ID="btnVerHistorial" runat="server" 
                            CssClass="btn btn-historial-cliente" 
                            CommandName="VerHistorial" 
                            CommandArgument='<%# Eval("MascotaID") %>'>
                            <i class="fas fa-notes-medical me-2"></i>Ver Historial Médico
                        </asp:LinkButton>
                    </div>
                </div>
            </ItemTemplate>
            <FooterTemplate>
                <%-- Mensaje si no hay mascotas --%>
                <asp:Panel ID="pnlNoMascotas" runat="server" Visible='<%# rptMascotasCliente.Items.Count == 0 %>'>
                    <div class="col-12">
                        <div class="alert alert-info text-center" role="alert">
                           <i class="fas fa-info-circle me-2"></i> Aún no tienes mascotas registradas.
                        </div>
                    </div>
                </asp:Panel>
            </FooterTemplate>
        </asp:Repeater>
    </div>

</asp:Content>