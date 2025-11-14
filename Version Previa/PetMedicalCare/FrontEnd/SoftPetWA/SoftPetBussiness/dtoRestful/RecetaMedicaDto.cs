using System;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
//informacion de receta medica, cita asociada
namespace SoftPetBussiness.Bo
{
    public class RecetaMedicaDto
    {
        public RecetaMedicaDto()
        {
            Cita = new CitaDto();
        }

        [JsonProperty("recetaMedicaId")]
        public int RecetaMedicaId { get; set; }

        // Ajusta el nombre de la propiedad según tu JSON real
        [JsonProperty("cita")]
        public CitaDto Cita { get; set; }

        // Si tu backend usa java.sql.Date (yyyy-MM-dd), Newtonsoft lo parsea a 00:00:00
        //
        [JsonProperty("fechaEmision")]
        public DateTime? FechaEmision { get; set; }

        [JsonProperty("vigenciaHasta")]
        public DateTime? VigenciaHasta { get; set; }

        [JsonProperty("diagnostico")]
        public string Diagnostico { get; set; }

        [JsonProperty("observaciones")]
        public string Observaciones { get; set; }

        [JsonProperty("activo")]
        public bool Activo { get; set; }
    }

    public class CitaDto
    {
        // Si el JSON es "citaAtencionId", cambia este nombre:
        [JsonProperty("citaId")]
        public int CitaId { get; set; }
    }
}
