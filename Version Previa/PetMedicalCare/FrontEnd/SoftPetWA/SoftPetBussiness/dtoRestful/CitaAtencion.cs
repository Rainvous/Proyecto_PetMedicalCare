using Newtonsoft.Json;
//informacion de cita, veterinario y mascota
namespace SoftPetBussiness.Bo
{
    public class CitaAtencionDto
    {
        [JsonProperty("citaId")]
        public int CitaId { get; set; }

        [JsonProperty("veterinario")]
        public VeterinarioRef Veterinario { get; set; }

        [JsonProperty("mascota")]
        public MascotaRef Mascota { get; set; }

        // IMPORTANTE: strings para respetar exactamente el formato del backend
        // "2025-11-09Z"
        [JsonProperty("fechaRegistro")]
        public string FechaRegistro { get; set; }

        // "2025-11-10T01:37:12Z[UTC]"
        [JsonProperty("fechaHoraInicio")]
        public string FechaHoraInicio { get; set; }

        // "2025-11-10T02:37:12Z[UTC]"
        [JsonProperty("fechaHoraFin")]
        public string FechaHoraFin { get; set; }

        [JsonProperty("pesoMascota")]
        public double PesoMascota { get; set; }

        [JsonProperty("monto")]
        public double Monto { get; set; }

        // Puedes tiparlo como enum si quieres, pero en JSON va como texto
        [JsonProperty("estado")]
        public string Estado { get; set; }

        [JsonProperty("observacion")]
        public string Observacion { get; set; }

        [JsonProperty("activo")]
        public bool Activo { get; set; }
    }

    public class VeterinarioRef
    {
        [JsonProperty("veterinarioId")]
        public int VeterinarioId { get; set; }
    }

    public class MascotaRef
    {
        [JsonProperty("mascotaId")]
        public int MascotaId { get; set; }
    }
}

