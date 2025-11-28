using SoftPetBussiness.RolClient;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class RolBO
    {
        private RolesClient clienteSOAP;

        public RolBO()
        {
            this.clienteSOAP = new RolesClient();
        }

        // Insertar rol (parámetros sueltos)
        public int Insertar(string nombre, bool activo)
        {
            return this.clienteSOAP.insertar(nombre, activo);
        }

        // Modificar rol (parámetros sueltos)
        public int Modificar(int rolId, string nombre, bool activo)
        {
            return this.clienteSOAP.modificar(rolId, nombre, activo);
        }

        // Eliminar rol
        public int Eliminar(int rolId)
        {
            return this.clienteSOAP.eliminar(rolId);
        }

        // Obtener rol por ID
        public rolDto ObtenerPorId(int rolId)
        {
            return this.clienteSOAP.obtenerPorId(rolId);
        }

        // Listar todos los roles
        public List<rolDto> ListarTodos()
        {
            return this.clienteSOAP.listarTodos().ToList<rolDto>();
        }

        // Insertar usando DTO
        public int Insertar(rolDto rol)
        {
            return this.clienteSOAP.insertar(
                rol.nombre,
                rol.activo
            );
        }

        // Modificar usando DTO
        public int Modificar(rolDto rol)
        {

            return this.clienteSOAP.modificar(
                rol.rolId,
                rol.nombre,
                rol.activo
            );
        }
        public List<rolDto> ObtenerRolesDelUsuario(int idUser)
        {
            // 1. Llama al servicio SOAP
            var respuestaSOAP = this.clienteSOAP.ObtenerRolesDelUsuario(idUser);

            // 2. Comprueba si la respuesta es nula
            if (respuestaSOAP == null)
            {
                // Si es nula, devuelve una LISTA VACÍA nueva
                return new List<rolDto>();
            }
            else
            {
                // Si no es nula, la convierte a lista
                return respuestaSOAP.ToList<rolDto>();
            }
        }
    }
}
