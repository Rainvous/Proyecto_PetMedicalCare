using SoftPetBussiness.PersonaClient;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class PersonaBO
    {
        private PersonasClient clienteSOAP;

        personaDto personaDto = new personaDto();
        //personaDto.usuario = new usuarioDto();
        //personaDto.usuario.usuarioId = 1; // Usuario por defecto
        //    personaDto.nombre = "Default";
        //    personaDto.direccion = "Default Address";
        //    personaDto.telefono = "0000000000";
        //    personaDto.sexo = sexo.F; // Asumiendo 'M' para masculino como valor por defecto
        //    personaDto.nroDocumento = 0;
        //    personaDto.ruc = 0;
        //    personaDto.tipoDocumento = "DNI";
        //    personaDto.activo = true;
        //    personaDto.personaId = 0;
        public PersonaBO()
        {
            this.clienteSOAP = new PersonasClient();

        }

        // Insertar persona (parámetros sueltos)
        public int Insertar(int usuarioId, string nombre, string direccion, string telefono,
                            string sexo, int nroDocumento, int ruc, string tipoDocumento, bool activo)
        {
            return this.clienteSOAP.insertar_persona(
                usuarioId, nombre, direccion, telefono, sexo, nroDocumento, ruc, tipoDocumento, activo
            );
        }

        // Modificar persona (parámetros sueltos)
        public int Modificar(int personaId, int usuarioId, string nombre, string direccion, string telefono,
                             string sexo, int nroDocumento, int ruc, string tipoDocumento, bool activo)
        {
            return this.clienteSOAP.modificar_persona(
                personaId, usuarioId, nombre, direccion, telefono, sexo, nroDocumento, ruc, tipoDocumento, activo
            );
        }

        // Eliminar persona
        public int Eliminar(int personaId)
        {
            return this.clienteSOAP.eliminar_persona(personaId);
        }

        // Obtener persona por ID
        public personaDto ObtenerPorId(int personaId)
        {
            return this.clienteSOAP.obtenerPorId_persona(personaId);
        }

        public int ModificarCompleto(
            int idPersona, int idUsuario, string username, string password, string correo, bool activo,
            string nombre, string direccion, string telefono, string sexo, int nroDocumento, int ruc, string tipoDocumento)
        {
            return this.clienteSOAP.modificarPersonaCompleta(idPersona, idUsuario, username, password, correo, activo,
                nombre, direccion, telefono, sexo, nroDocumento, ruc, tipoDocumento);
        }

        public int EliminarCompleto(int idPersona)
        {
            return this.clienteSOAP.eliminarPersonaCompleta(idPersona);
        }

        // Listar todas las personas
        public List<personaDto> ListarTodos()
        {
            return this.clienteSOAP.listar_personas().ToList<personaDto>();
        }

        // Insertar persona usando DTO
        public int Insertar(personaDto persona)
        {
            return this.clienteSOAP.insertar_persona(
                persona.usuario.usuarioId,
                persona.nombre,
                persona.direccion,
                persona.telefono,
                persona.sexo.ToString(),          // si en el proxy es enum, usa persona.sexo.ToString()
                persona.nroDocumento,
                persona.ruc,
                persona.tipoDocumento,
                persona.activo
            );
        }

        // Modificar persona usando DTO
        public int Modificar(personaDto persona)
        {
            return this.clienteSOAP.modificar_persona(
                persona.personaId,
                persona.usuario.usuarioId,
                persona.nombre,
                persona.direccion,
                persona.telefono,
                persona.sexo.ToString(),          // si en el proxy es enum, usa persona.sexo.ToString()
                persona.nroDocumento,
                persona.ruc,
                persona.tipoDocumento,
                persona.activo
            );
        }

        public List<personaDto> ListarPersonasActivas()
        {
            return this.clienteSOAP.listar_personas_activas().ToList<personaDto>();
        }
        public List<personaDto> ListarBusquedaAvanzada(string nombre, string nroDocumento, string telefono, int activo)
        {
            nombre = nombre ?? "";
            nroDocumento = nroDocumento ?? "";
            telefono = telefono ?? "";

            var resultado = this.clienteSOAP.ListasBusquedaAvanzada(nombre, nroDocumento, telefono, activo);
            if (resultado == null) return new List<personaDto>();
            return resultado.ToList<personaDto>();
        }

        public List<personaDto> ListarSoloClientes()
        {
            return this.clienteSOAP.ListasBusquedaAvanzadaParaCliente().ToList<personaDto>();
        }

        public int InsertarCompleto(
                string username, string password, string correo,
                string nombre, string direccion, string telefono, string sexo,
                int nroDocumento, int ruc, string tipoDocumento)
        {
            return this.clienteSOAP.insertarPersonaCompleta(username, password, correo, nombre, direccion, telefono, sexo, nroDocumento, ruc, tipoDocumento);
        }
        public int insertarUsaurioGuest(String nombre, int ruc, int nroDocumento)
        {
            //Esta funcion crea o modifica un usuario guest que existe en la base de datos
            //este usuario guest nos ayuda para el caso que un cliente no registrado quiera emitir un comprobante de pago
            //asi no tengamos que registrar toda la informacion del cliente
            //Llena la informacion basica para que se pueda emitir el comprobante (Boleta o Factura)
            return this.clienteSOAP.insertarOModificarUsuarioGest(nombre, ruc, nroDocumento);
        }
        public int obtenerIDdeUsuarioGuest()
        {
            //Esta funcion retorna el ID del usuario guest
            // Para que puedas usarlo en la emision de comprobantes de pago
            return this.clienteSOAP.obtenerIdDePersonaGuest();

        }
        public personaDto obtenerUsuarGuestCompleto()
        {
            //Te retorna toda la informacion completa del usuario guest 
            //funcion por si acaso
            return this.clienteSOAP.ObtenerDatosPersonaGuest();
        }
        public rolDto obtenerRol_DeCliente(int idpersona)
        {
            return this.clienteSOAP.obtener_rol_de_cliente(idpersona);
        }
        public String obtenerCorreo_DeCliente(int idpersona)
        {
            return this.clienteSOAP.obtener_correo_cliente(idpersona);
        }
    }
}
