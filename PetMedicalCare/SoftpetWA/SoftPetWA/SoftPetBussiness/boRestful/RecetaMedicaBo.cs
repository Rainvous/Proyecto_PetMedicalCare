using Newtonsoft.Json;
using SoftPetBussiness.Bo;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Text;

namespace SoftPetBussiness.Bo
{
    public class RecetaMedicaBo
    {
        private readonly HttpClient _httpClient;
        private readonly string _baseUrl = "http://localhost:26092/CitasMedicasWS/resources/RecetaMedica";

        public RecetaMedicaBo()
        {
            _httpClient = new HttpClient();
        }

        // Obtener todos los registros sincrónico
        public List<RecetaMedicaDto> ListarTodos()
        {
            var response = _httpClient.GetAsync(_baseUrl).Result;  // Sincrónico
            if (response.StatusCode == HttpStatusCode.OK)
            {
                var jsonResponse = response.Content.ReadAsStringAsync().Result;  // Sincrónico
                var recetas = JsonConvert.DeserializeObject<List<RecetaMedicaDto>>(jsonResponse);
                return recetas;
            }

            throw new Exception("No se pudo obtener la lista de recetas.");
        }

        // Obtener una receta por ID sincrónico
        public RecetaMedicaDto ObtenerPorId(int recetaId)
        {
            var response = _httpClient.GetAsync($"{_baseUrl}/{recetaId}").Result;  // Sincrónico
            if (response.StatusCode == HttpStatusCode.OK)
            {
                var jsonResponse = response.Content.ReadAsStringAsync().Result;  // Sincrónico
                var receta = JsonConvert.DeserializeObject<RecetaMedicaDto>(jsonResponse);
                return receta;
            }

            throw new Exception($"Receta con ID {recetaId} no encontrada.");
        }

        // Insertar una nueva receta sincrónico
        public RecetaMedicaDto Insertar(RecetaMedicaDto receta)
        {
            var jsonRequest = JsonConvert.SerializeObject(receta, new JsonSerializerSettings
            {
                DateFormatString = "yyyy-MM-dd"  // Formato de fecha adecuado
            });

            var content = new StringContent(jsonRequest, Encoding.Default, "application/json");

            var response = _httpClient.PostAsync(_baseUrl, content).Result;  // Sincrónico
            if (response.StatusCode == HttpStatusCode.Created)
            {
                var jsonResponse = response.Content.ReadAsStringAsync().Result;  // Sincrónico
                var recetaCreada = JsonConvert.DeserializeObject<RecetaMedicaDto>(jsonResponse);
                return recetaCreada;
            }

            throw new Exception("No se pudo crear la receta.");
        }

        // Modificar una receta existente sincrónico
        public RecetaMedicaDto Modificar(RecetaMedicaDto receta)
        {
            var jsonRequest = JsonConvert.SerializeObject(receta, new JsonSerializerSettings
            {
                DateFormatString = "yyyy-MM-dd"  // Formato de fecha adecuado
            });

            var content = new StringContent(jsonRequest, Encoding.Default, "application/json");

            var response = _httpClient.PutAsync(_baseUrl, content).Result;  // Sincrónico
            if (response.StatusCode == HttpStatusCode.OK)
            {
                var jsonResponse = response.Content.ReadAsStringAsync().Result;  // Sincrónico
                var recetaActualizada = JsonConvert.DeserializeObject<RecetaMedicaDto>(jsonResponse);
                return recetaActualizada;
            }

            throw new Exception("No se pudo modificar la receta.");
        }

        // Eliminar una receta sincrónico
        public void Eliminar(int recetaId)
        {
            var response = _httpClient.DeleteAsync($"{_baseUrl}/{recetaId}").Result;  // Sincrónico
            if (response.StatusCode != HttpStatusCode.NoContent)
            {
                throw new Exception($"No se pudo eliminar la receta con ID {recetaId}.");
            }
        }
    }
}
