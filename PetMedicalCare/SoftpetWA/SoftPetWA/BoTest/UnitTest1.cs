//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using Newtonsoft.Json;
//using SoftPetBussiness.Bo;
//using System;
//using System.Collections.Generic;
//using System.Net;
//using System.Net.Http;
//using System.Text;
//using System.Threading.Tasks;

//namespace SoftPetBussiness.Tests
//{
//    [TestClass]
//    public class RecetaMedicaBoTest
//    {
//        private static readonly string BaseUrl = "http://localhost:26092/CitasMedicasWS/resources/RecetaMedica";
//        private static readonly HttpClient client = new HttpClient();

//        //[TestMethod]
//        //public async Task TestListarTodos()
//        //{
//        //    // Realizar la solicitud GET a /RecetaMedica
//        //    var response = await client.GetAsync(BaseUrl);
//        //    Assert.AreEqual(HttpStatusCode.OK, response.StatusCode);

//        //    // Leer el contenido de la respuesta
//        //    var jsonResponse = await response.Content.ReadAsStringAsync();
//        //    Assert.IsFalse(string.IsNullOrEmpty(jsonResponse));

//        //    // Deserializar la respuesta JSON
//        //    var recetas = JsonConvert.DeserializeObject<List<RecetaMedicaDto>>(jsonResponse);
//        //    Assert.IsNotNull(recetas);
//        //    Assert.IsTrue(recetas.Count > 0);  // Verifica que se devuelvan recetas
//        //}

//        [TestMethod]
//        public async Task TestObtenerPorId()
//        {
//            int recetaId = 2;  // Cambia esto según los datos disponibles en tu base

//            // Realizar la solicitud GET a /RecetaMedica/{id}
//            var response = await client.GetAsync($"{BaseUrl}/{recetaId}");
//            Assert.AreEqual(HttpStatusCode.OK, response.StatusCode);

//            // Leer el contenido de la respuesta
//            var jsonResponse = await response.Content.ReadAsStringAsync();
//            Assert.IsFalse(string.IsNullOrEmpty(jsonResponse));

//            // Deserializar la respuesta JSON
//            var receta = JsonConvert.DeserializeObject<RecetaMedicaDto>(jsonResponse);
//            Console.WriteLine("Respuesta del ob id\n" + jsonResponse);
//            Assert.IsNotNull(receta);
//            Assert.AreEqual(recetaId, receta.RecetaMedicaId);
//        }

//        [TestMethod]
//        public async Task TestInsertar()
//        {
//            // Crear un objeto RecetaMedicaDto para insertar
//            var nuevaReceta = new RecetaMedicaDto
//            {
//                Cita = new CitaDto { CitaId = 2 }, // Asegúrate de que exista un CitaId válido en tu base de datos
//                Diagnostico = "Gastritis leve2 RESTFUL C#",
//                Observaciones = "Reposo y control en 7 días  RESTFUL C#",
//                FechaEmision = DateTime.Parse("2025-10-28"), // Fecha sin hora, pero con "Z" al final
//                VigenciaHasta = DateTime.Parse("2025-11-04"), // Fecha sin hora, pero con "Z" al final
//                Activo = true
//            };

//            // Serializar el objeto RecetaMedicaDto a JSON con el formato correcto de fecha
//            var jsonRequest = JsonConvert.SerializeObject(nuevaReceta, new JsonSerializerSettings
//            {
//                DateFormatString = "yyyy-MM-dd" // Formato de fecha adecuado
//            });

//            // Imprimir el JSON en consola para depuración
//            Console.WriteLine($"JSON Request: {jsonRequest}");

//            // Crear el contenido de la solicitud POST (JSON)
//            var content = new StringContent(jsonRequest, Encoding.Default, "application/json");

//            // Realizar la solicitud POST a /RecetaMedica
//            var response = await client.PostAsync(BaseUrl, content);

//            // Verificar que la respuesta sea 201 (Created)
//            Assert.AreEqual(HttpStatusCode.Created, response.StatusCode);

//            // Leer la respuesta
//            var jsonResponse = await response.Content.ReadAsStringAsync();
//            Assert.IsFalse(string.IsNullOrEmpty(jsonResponse));

//            // Deserializar la respuesta JSON
//            var recetaCreada = JsonConvert.DeserializeObject<RecetaMedicaDto>(jsonResponse);
//            Assert.IsNotNull(recetaCreada);
//            Assert.AreEqual("Gastritis leve2 RESTFUL C#", recetaCreada.Diagnostico);
//            Assert.AreEqual(true, recetaCreada.Activo);
//        }



//        [TestMethod]
//        public async Task TestModificar()
//        {
//            // Crear un objeto RecetaMedicaDto para modificar
//            var recetaModificada = new RecetaMedicaDto
//            {
//                RecetaMedicaId = 8,  // Asegúrate de que este ID exista en la base de datos
//                Cita = new CitaDto { CitaId = 1 },  // Asegúrate de que el CitaId sea válido
//                Diagnostico = "Conjuntivitis mofidicacion restful c#",
//                Observaciones = "Gotas 3x/día",
//                FechaEmision = DateTime.Parse("2025-10-28"), // Fecha sin hora, pero con "Z" al final
//                VigenciaHasta = DateTime.Parse("2025-11-04"), // Fecha sin hora, pero con "Z" al final
//                Activo = true
//            };

//            // Serializar el objeto RecetaMedicaDto a JSON con el formato correcto de fecha
//            var jsonRequest = JsonConvert.SerializeObject(recetaModificada, new JsonSerializerSettings
//            {
//                DateFormatString = "yyyy-MM-dd"  // Configura el formato de fecha a 'yyyy-MM-dd'
//            });

//            // Imprimir el JSON en consola para depuración
//            Console.WriteLine($"JSON Request: {jsonRequest}");

//            // Crear el contenido de la solicitud PUT (JSON)
//            var content = new StringContent(jsonRequest, Encoding.Default, "application/json");

//            // Realizar la solicitud PUT a /RecetaMedica
//            var response = await client.PutAsync(BaseUrl, content);

//            // Verificar q




//        }
//        [TestMethod]
//        public async Task TestEliminar()
//        {
//            int recetaId = 7;  // Cambia esto a un ID válido de tu base

//            // Realizar la solicitud DELETE a /RecetaMedica/{id}
//            var response = await client.DeleteAsync($"{BaseUrl}/{recetaId}");
//            Assert.AreEqual(HttpStatusCode.NoContent, response.StatusCode);  // Verifica que el código de estado sea 204
//        }
//    }

//}
