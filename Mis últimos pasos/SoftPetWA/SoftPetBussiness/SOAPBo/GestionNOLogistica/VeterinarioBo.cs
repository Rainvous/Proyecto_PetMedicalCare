using SoftPetBussiness.ServicioClient;
using SoftPetBussiness.VeterinarioClient;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class VeterinarioBO
    {
        private VeterinariosClient clienteSOAP;

        public VeterinarioBO()
        {
            this.clienteSOAP = new VeterinariosClient();
        }

        // Insertar veterinario (Parámetros sueltos)
        public int Insertar(int personaId, string fechaContratacion, string estado, string especialidad, bool activo)
        {
            return this.clienteSOAP.insertar_veterinario(personaId, fechaContratacion, estado, especialidad, activo);
        }

        // Modificar veterinario (Parámetros sueltos)
        public int Modificar(int veterinarioId, int personaId, string fechaContratacion, string estado, string especialidad, bool activo)
        {
            return this.clienteSOAP.modificar_veterinario(veterinarioId, personaId, fechaContratacion, estado, especialidad, activo);
        }

        // Eliminar veterinario
        public int Eliminar(int veterinarioId)
        {
            return this.clienteSOAP.eliminar_veterinario(veterinarioId);
        }

        // Obtener veterinario por ID
        public veterinarioDto ObtenerPorId(int veterinarioId)
        {
            return this.clienteSOAP.obtenerPorId_veterinario(veterinarioId);
        }

        // Listar todos los veterinarios
        public List<veterinarioDto> ListarTodos()
        {
            return this.clienteSOAP.listar_veterinarios().ToList<veterinarioDto>();
        }

        public List<veterinarioDto> ListarActivos()
        {
            return this.clienteSOAP.listar_veterinarios_activos().ToList<veterinarioDto>();
        }

        // Insertar veterinario usando DTO (FIX: Formato de fecha)
        public int Insertar(veterinarioDto veterinario)
        {
            return this.clienteSOAP.insertar_veterinario(
                veterinario.persona.personaId,
                veterinario.fechaContratacion.ToString("yyyy-MM-dd"), // FIX AQUÍ
                veterinario.estado.ToString(),
                veterinario.especialidad,
                veterinario.activo
            );
        }

        // Modificar veterinario usando DTO (FIX: Formato de fecha)
        public int Modificar(veterinarioDto veterinario)
        {
            return this.clienteSOAP.modificar_veterinario(
                veterinario.veterinarioId,
                veterinario.persona.personaId,
                veterinario.fechaContratacion.ToString("yyyy-MM-dd"), // FIX AQUÍ
                veterinario.estado.ToString(),
                veterinario.especialidad,
                veterinario.activo
            );
        }

        public List<veterinarioDto> ListarBusquedaAvanzada(string nombre, string documento, string especialidad, int estadoActivo)
        {
            nombre = nombre ?? "";
            documento = documento ?? "";
            especialidad = (especialidad == "Todos") ? "" : (especialidad ?? "");

            // Llamada al WS con el entero
            var resultado = this.clienteSOAP.ListasBusquedaAvanzadaVeterinario(especialidad, nombre, documento, estadoActivo);

            if (resultado == null) return new List<veterinarioDto>();
            return resultado.ToList<veterinarioDto>();
        }

        public int InsertarCompleto(
            string username, string password, string correo,
            string nombre, string direccion, string telefono, string sexo,
            int nroDocumento, int ruc, string tipoDocumento,
            string fechaContratacion, string estado, string especialidad)
        {
            return this.clienteSOAP.insertarVeterinarioCompleto(
                username, password, correo,
                nombre, direccion, telefono, sexo,
                nroDocumento, ruc, tipoDocumento,
                fechaContratacion, estado, especialidad
            );
        }

        public int ModificarCompleto(
            int idVeterinario, int idPersona, int idUsuario,
            string username, string password, string correo, bool activo,
            string nombre, string direccion, string telefono, string sexo,
            int nroDocumento, int ruc, string tipoDocumento,
            string fechaContratacion, string estado, string especialidad)
        {
            // Llamada al método 'modificarVeterinarioCompleto' del WS Java
            return this.clienteSOAP.modificarVeterinarioCompleto(
                idVeterinario, idPersona, idUsuario,
                username, password, correo, activo,
                nombre, direccion, telefono, sexo,
                nroDocumento, ruc, tipoDocumento,
                fechaContratacion, estado, especialidad
            );
        }

        public int EliminarCompleto(int idVeterinario)
        {
            // Llamada al método 'eliminarVeterinarioCompleto' del WS Java
            return this.clienteSOAP.eliminarVeterinarioCompleto(idVeterinario);
        }
    }
}