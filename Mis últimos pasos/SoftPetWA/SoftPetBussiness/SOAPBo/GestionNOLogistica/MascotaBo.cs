
using SoftPetBussiness.MascotaClient;
using System;
using System.Collections.Generic;
using System.Linq;


namespace SoftPetBusiness
{
    public class MascotaBO
    {
        private MascotasClient clienteSOAP;

        public MascotaBO()
        {
            this.clienteSOAP = new MascotasClient();
        }

        // Insertar mascota (parámetros sueltos)
        public int Insertar(int personaId, string nombre, string especie, string sexo,
                            string raza, string color, string fechaDefuncion, bool activo)
        {
            // CORRECCIÓN: Si viene vacío, enviamos null al servicio
            if (string.IsNullOrEmpty(fechaDefuncion))
            {
                fechaDefuncion = null;
            }

            return this.clienteSOAP.insertar_mascota(
                personaId, nombre, especie, sexo, raza, color, fechaDefuncion, activo
            );
        }

        // Modificar mascota
        public int Modificar(int mascotaId, int personaId, string nombre, string especie, string sexo,
                             string raza, string color, string fechaDefuncion, bool activo)
        {
            // CORRECCIÓN: Si viene vacío, enviamos null al servicio
            if (string.IsNullOrEmpty(fechaDefuncion))
            {
                fechaDefuncion = null;
            }

            return this.clienteSOAP.modificar_mascota(
                mascotaId, personaId, nombre, especie, sexo, raza, color, fechaDefuncion, activo
            );
        }

        // Eliminar mascota
        public int Eliminar(int mascotaId)
        {
            return this.clienteSOAP.eliminar_mascota(mascotaId);
        }

        // Obtener mascota por ID
        public mascotaDto ObtenerPorId(int mascotaId)
        {
            return this.clienteSOAP.obtenerPorId_mascota(mascotaId);
        }

        // Listar todas las mascotas
        public List<mascotaDto> ListarTodos()
        {
            return this.clienteSOAP.listar_mascotas().ToList<mascotaDto>();
        }

        // Insertar mascota usando DTO
        //public int Insertar(mascotaDto mascota)
        //{
        //    return this.clienteSOAP.insertar_mascota(
        //        mascota.persona.personaId,
        //        mascota.nombre,
        //        mascota.especie,
        //        mascota.sexo,
        //        mascota.raza,
        //        mascota.color,
        //        // Si en el proxy fechaDefuncion es DateTime?, usar mascota.fechaDefuncion?.ToString("yyyy-MM-dd")
        //        mascota.fechaDefuncion.ToString(),
        //        mascota.activo
        //    );
        //}
        public int Insertar(mascotaDto mascota, DateTime fechadefuncion)
        {
            string fechaDefuncionStr;
            if (fechadefuncion == null)
            {
                fechaDefuncionStr = DateTime.MinValue.ToString("yyyy-MM-dd");
            }
            else
            {
                fechaDefuncionStr = fechadefuncion.ToString("yyyy-MM-dd");
            }


            return this.clienteSOAP.insertar_mascota(
                mascota.persona.personaId,
                mascota.nombre,
                mascota.especie,
                mascota.sexo,
                mascota.raza,
                mascota.color,
                // Si en el proxy fechaDefuncion es DateTime?, usar mascota.fechaDefuncion?.ToString("yyyy-MM-dd")
                fechaDefuncionStr,
                mascota.activo
            );
        }
        //public int Insertar(mascotaDto mascota, string fechadefuncion)
        //{
        //    string fechaDefuncionStr;
        //    if (fechadefuncion == null || fechadefuncion == "")
        //    {
        //        fechaDefuncionStr = DateTime.MinValue.ToString("yyyy-MM-dd");
        //    }
        //    fechaDefuncionStr=fechadefuncion;
        //    return this.clienteSOAP.insertar_mascota(
        //        mascota.persona.personaId,
        //        mascota.nombre,
        //        mascota.especie,
        //        mascota.sexo,
        //        mascota.raza,
        //        mascota.color,
        //        // Si en el proxy fechaDefuncion es DateTime?, usar mascota.fechaDefuncion?.ToString("yyyy-MM-dd")
        //        fechaDefuncionStr,
        //        mascota.activo
        //    );
        //}

        // Modificar mascota usando DTO
        public int Modificar(mascotaDto mascota)
        {
            return this.clienteSOAP.modificar_mascota(
                mascota.mascotaId,
                mascota.persona.personaId,
                mascota.nombre,
                mascota.especie,
                mascota.sexo,
                mascota.raza,
                mascota.color,
                mascota.fechaDefuncion.ToString(),
                mascota.activo
            );
        }
        public int Modificar(mascotaDto mascota, DateTime fechadefuncion)
        {
            string fechaDefuncionStr;
            if (fechadefuncion == null)
            {
                fechaDefuncionStr = DateTime.MinValue.ToString("yyyy-MM-dd");
            }
            else
            {
                fechaDefuncionStr = fechadefuncion.ToString("yyyy-MM-dd");
            }
            return this.clienteSOAP.modificar_mascota(
                mascota.mascotaId,
                mascota.persona.personaId,
                mascota.nombre,
                mascota.especie,
                mascota.sexo,
                mascota.raza,
                mascota.color,
                fechaDefuncionStr
                ,
                mascota.activo
            );
        }
        public List<mascotaDto> ListarPorIdPersona(int personaId)
        {
            var resultado = this.clienteSOAP.listar_mascotas_por_persona(personaId);
            return (resultado ?? new mascotaDto[0]).ToList<mascotaDto>();
        }
        public List<mascotaDto> ListarActivos()
        {
            return this.clienteSOAP.listar_mascotas_activas().ToList<mascotaDto>();
        }

        public List<mascotaDto> ListarBusquedaAvanzada(
            string nombreMascota, string especie, string nombrePersona, int estadoActivo)
        {
            var resultado = this.clienteSOAP.ListasBusquedaAvanzada(nombreMascota, especie, nombrePersona, estadoActivo);
            if (resultado == null) return new List<mascotaDto>();
            return resultado.ToList<mascotaDto>();
        }
        public List<mascotaDto> listartodos(
            mascotaDto mascota)
        {
            return this.clienteSOAP.listar_mascotas().ToList<mascotaDto>();
        }
    }
}
