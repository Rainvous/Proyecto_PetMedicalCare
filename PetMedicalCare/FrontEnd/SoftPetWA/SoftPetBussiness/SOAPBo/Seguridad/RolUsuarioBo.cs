using SoftPetBussiness.RolUsuarioClient;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class RolUsuarioBO
    {
        private RolesUsuarioClient clienteSOAP;

        public RolUsuarioBO()
        {
            this.clienteSOAP = new RolesUsuarioClient();
        }

        // Insertar relación Rol-Usuario
        public int Insertar(int rolId, int usuarioId, bool activo)
        {
            return this.clienteSOAP.insertar(rolId, usuarioId, activo);
        }

        // Modificar relación Rol-Usuario
        public int Modificar(int rolUsuarioId, int rolId, int usuarioId, bool activo)
        {
            return this.clienteSOAP.modificar(rolUsuarioId, rolId, usuarioId, activo);
        }

        // Eliminar relación Rol-Usuario
        public int Eliminar(int rolUsuarioId)
        {
            return this.clienteSOAP.eliminar(rolUsuarioId);
        }

        // Obtener RolUsuario por ID
        public rolUsuarioDto ObtenerPorId(int rolUsuarioId)
        {
            return this.clienteSOAP.obtenerPorId(rolUsuarioId);
        }

        // Listar todas las relaciones Rol-Usuario
        public List<rolUsuarioDto> ListarTodos()
        {
            return this.clienteSOAP.listarTodos().ToList<rolUsuarioDto>();
        }

        // Insertar usando DTO
        public int Insertar(rolUsuarioDto rolUsuario)
        {
            return this.clienteSOAP.insertar(
                rolUsuario.rol.rolId,
                rolUsuario.usuario.usuarioId,
                rolUsuario.activo
            );
        }

        // Modificar usando DTO
        public int Modificar(rolUsuarioDto rolUsuario)
        {
            return this.clienteSOAP.modificar(
                rolUsuario.rolUsuarioId,
                rolUsuario.rol.rolId,
                rolUsuario.usuario.usuarioId,
                rolUsuario.activo
            );
        }
    }
}
