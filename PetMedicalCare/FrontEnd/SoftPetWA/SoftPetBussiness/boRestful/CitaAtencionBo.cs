using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;

namespace SoftPetBussiness.Bo
{
    public class CitaAtencionBo : IDisposable
    {
        // URL embebido
        private const string Endpoint = "http://localhost:26092/CitasMedicasWS/resources/CitaAtencion";

        private readonly HttpClient _http;
        private readonly bool _ownsClient;
        private readonly JsonSerializerSettings _json;

        public CitaAtencionBo(HttpClient httpClient = null)
        {
            _ownsClient = httpClient == null;
            _http = httpClient ?? new HttpClient();

            _http.DefaultRequestHeaders.Accept.Clear();
            _http.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            _http.Timeout = TimeSpan.FromSeconds(100);

            // No parsear fechas automáticamente: el backend requiere strings exactos
            _json = new JsonSerializerSettings
            {
                ContractResolver = new CamelCasePropertyNamesContractResolver(),
                NullValueHandling = NullValueHandling.Ignore,
                DateParseHandling = DateParseHandling.None
            };
        }

        // ========= Helpers de formato =========
        // Para java.sql.Date enviado como "2025-11-09Z"
        public static string ToSqlDateZ(DateTime date)
        {
            return date.ToString("yyyy-MM-dd") + "Z";
        }

        // Para Timestamp enviado como "2025-11-10T01:37:12Z[UTC]"
        public static string ToUtcZoned(DateTime dtUtc)
        {
            if (dtUtc.Kind != DateTimeKind.Utc)
                dtUtc = DateTime.SpecifyKind(dtUtc, DateTimeKind.Utc);
            return dtUtc.ToString("yyyy-MM-dd'T'HH:mm:ss") + "Z[UTC]";
        }

        // =============== CRUD (sincrónico) ===============
        public List<CitaAtencionDto> ListarTodos()
        {
            var resp = _http.GetAsync(Endpoint).Result;
            using (resp)
            {
                resp.EnsureSuccessStatusCode();
                var json = resp.Content.ReadAsStringAsync().Result;
                var list = JsonConvert.DeserializeObject<List<CitaAtencionDto>>(json, _json);
                return list ?? new List<CitaAtencionDto>();
            }
        }

        public CitaAtencionDto ObtenerPorId(int id)
        {
            var resp = _http.GetAsync(Endpoint + "/" + id).Result;
            using (resp)
            {
                if (resp.StatusCode == HttpStatusCode.NotFound) return null;
                resp.EnsureSuccessStatusCode();
                var json = resp.Content.ReadAsStringAsync().Result;
                return JsonConvert.DeserializeObject<CitaAtencionDto>(json, _json);
            }
        }

        public CitaAtencionDto Insertar(CitaAtencionDto dto)
        {
            var payload = JsonConvert.SerializeObject(dto, _json);
            Console.WriteLine("Payload de inserción: " + payload);
            using (var content = new StringContent(payload, Encoding.Default, "application/json"))
            {
                var resp = _http.PostAsync(Endpoint, content).Result;
                using (resp)
                {
                    if (resp.StatusCode == HttpStatusCode.BadRequest)
                        throw new InvalidOperationException("Creación rechazada (400). Revisa campos/formatos.");

                    resp.EnsureSuccessStatusCode(); // 201 Created
                    var body = resp.Content.ReadAsStringAsync().Result;
                    var creado = JsonConvert.DeserializeObject<CitaAtencionDto>(body, _json);
                    if (creado == null) throw new InvalidOperationException("Respuesta vacía al crear cita.");
                    return creado;
                }
            }
        }

        public CitaAtencionDto Modificar(CitaAtencionDto dto)
        {
            var payload = JsonConvert.SerializeObject(dto, _json);
            using (var content = new StringContent(payload, Encoding.Default, "application/json"))
            {
                var resp = _http.PutAsync(Endpoint, content).Result;
                using (resp)
                {
                    if (resp.StatusCode == HttpStatusCode.BadRequest)
                        throw new InvalidOperationException("Modificación rechazada (400). Verifica existencia y formatos.");

                    resp.EnsureSuccessStatusCode(); // 200 OK
                    var body = resp.Content.ReadAsStringAsync().Result;
                    var actualizado = JsonConvert.DeserializeObject<CitaAtencionDto>(body, _json);
                    if (actualizado == null) throw new InvalidOperationException("Respuesta vacía al modificar cita.");
                    return actualizado;
                }
            }
        }

        public bool Eliminar(int id)
        {
            var resp = _http.DeleteAsync(Endpoint + "/" + id).Result;
            using (resp)
            {
                if (resp.StatusCode == HttpStatusCode.NoContent) return true;   // 204
                if (resp.StatusCode == HttpStatusCode.NotFound) return false;   // 404
                resp.EnsureSuccessStatusCode();
                return true;
            }
        }

        // Overloads convenientes: reciben DateTime, el BO formatea a los strings esperados por tu API
        public CitaAtencionDto Insertar(
            int veterinarioId,
            int mascotaId,
            DateTime fechaRegistro,           // solo fecha
            DateTime fechaHoraInicioUtc,      // timestamp UTC
            DateTime fechaHoraFinUtc,         // timestamp UTC
            double pesoMascota,
            double monto,
            string estado,
            string observacion,
            bool activo)
        {
            var dto = new CitaAtencionDto
            {
                Veterinario = new VeterinarioRef { VeterinarioId = veterinarioId },
                Mascota = new MascotaRef { MascotaId = mascotaId },
                FechaRegistro = ToSqlDateZ(fechaRegistro),
                FechaHoraInicio = ToUtcZoned(fechaHoraInicioUtc),
                FechaHoraFin = ToUtcZoned(fechaHoraFinUtc),
                PesoMascota = pesoMascota,
                Monto = monto,
                Estado = estado,
                Observacion = observacion,
                Activo = activo
            };
            return Insertar(dto);
        }

        public CitaAtencionDto Modificar(
            int citaId,
            int veterinarioId,
            int mascotaId,
            DateTime fechaRegistro,
            DateTime fechaHoraInicioUtc,
            DateTime fechaHoraFinUtc,
            double pesoMascota,
            double monto,
            string estado,
            string observacion,
            bool activo)
        {
            var dto = new CitaAtencionDto
            {
                CitaId = citaId,
                Veterinario = new VeterinarioRef { VeterinarioId = veterinarioId },
                Mascota = new MascotaRef { MascotaId = mascotaId },
                FechaRegistro = ToSqlDateZ(fechaRegistro),
                FechaHoraInicio = ToUtcZoned(fechaHoraInicioUtc),
                FechaHoraFin = ToUtcZoned(fechaHoraFinUtc),
                PesoMascota = pesoMascota,
                Monto = monto,
                Estado = estado,
                Observacion = observacion,
                Activo = activo
            };
            return Modificar(dto);
        }

        public void Dispose()
        {
            if (_ownsClient && _http != null) _http.Dispose();
        }
    }
}
