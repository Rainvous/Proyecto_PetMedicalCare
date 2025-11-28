using SoftPetBussiness.UsuarioClient;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class UsuarioBO
    {
        private UsuariosClient clienteSOAP;

        public UsuarioBO()
        {
            this.clienteSOAP = new UsuariosClient();
        }

        // Insertar usuario
        public int Insertar(string username, string password, string correo, bool activo)
        {
            return this.clienteSOAP.insertar(username, password, correo, activo);
        }

        // Modificar usuario
        public int Modificar(int usuarioId, string username, string password, string correo, bool activo)
        {
            return this.clienteSOAP.modificar(usuarioId, username, password, correo, activo);
        }

        // Eliminar usuario
        public int Eliminar(int usuarioId)
        {
            return this.clienteSOAP.eliminar(usuarioId);
        }

        // Obtener usuario por ID
        public usuarioDto ObtenerPorId(int usuarioId)
        {
            return this.clienteSOAP.obtenerPorId(usuarioId);
        }

        // Listar todos los usuarios
        public List<usuarioDto> ListarTodos()
        {
            return this.clienteSOAP.listarTodos().ToList<usuarioDto>();
        }

        // Insertar usando DTO
        public int Insertar(usuarioDto usuario)
        {
            return this.clienteSOAP.insertar(
                usuario.username,
                usuario.password,
                usuario.correo,
                usuario.activo
            );
        }

        // Modificar usando DTO
        public int Modificar(usuarioDto usuario)
        {
            return this.clienteSOAP.modificar(
                usuario.usuarioId,
                usuario.username,
                usuario.password,
                usuario.correo,
                usuario.activo
            );
        }

        // Login Seguro (Hash)
        public List<usuarioDto> ObtenerPorCorreoYContra(string correo, string contra)
        {
            // 1. Llama al servicio SOAP (que ahora espera la contraseña en texto plano para hashearla)
            // O si tu lógica cambió para hashear aquí, asegúrate de coordinarlo. 
            // Según lo que hicimos en Java, el BO de Java hashea. Así que aquí enviamos plano.
            var respuestaSOAP = this.clienteSOAP.ObtenerPorCorreoYContra(correo, contra);

            // 2. Comprueba si la respuesta es nula
            if (respuestaSOAP == null)
            {
                return new List<usuarioDto>();
            }
            else
            {
                return respuestaSOAP.ToList<usuarioDto>();
            }
        }

        // NUEVO MÉTODO: Cambiar Contraseña
        public int cambiarPassword(int idUsuario, string passwordActual, string passwordNueva)
        {
            // Llama al nuevo método expuesto en el WS Java
            // Si te sale error aquí, haz clic derecho en 'Connected Services' -> 'Update Service Reference'
            return this.clienteSOAP.cambiarPassword(idUsuario, passwordActual, passwordNueva);
        }

        // ========================================================
        // NUEVO MÉTODO: RECUPERAR CONTRASEÑA
        // ========================================================
        public int recuperarPassword(string correo)
        {
            // Llama al servicio Java que se encarga de todo el proceso seguro
            return this.clienteSOAP.recuperarPassword(correo);
        }
    }
}