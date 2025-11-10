using Microsoft.VisualStudio.TestTools.UnitTesting;
using SoftPetBussiness.Bo;
using System;
using System.Collections.Generic;

namespace SoftPetBussiness.Tests
{
    [TestClass]
    public class CitaAtencionBoTest
    {
        private static CitaAtencionBo citaBo;

        [ClassInitialize]
        public static void ClassInitialize(TestContext _)
        {
            citaBo = new CitaAtencionBo(); // URL está dentro del BO
        }

        //[TestMethod]
        //public void TestListarTodos()
        //{
        //    var citas = citaBo.ListarTodos();
        //    Assert.IsNotNull(citas);
        //    Assert.IsTrue(citas.Count > 0); // Esperamos al menos una cita
        //}

        [TestMethod]
        public void TestInsertar()
        {
            var nueva = NuevaCitaDto(vetId: 2, masId: 2,
                                     fechaReg: new DateTime(2025, 11, 09),
                                     iniUtc: new DateTime(2025, 11, 10, 1, 37, 12, DateTimeKind.Utc),
                                     finUtc: new DateTime(2025, 11, 10, 2, 37, 12, DateTimeKind.Utc),
                                     estado: "ATENDIDA",
                                     obs: "Obs de inserción BO",
                                     activo: true,
                                     peso: 12.5, monto: 50.0);

            var creada = citaBo.Insertar(nueva);

            Assert.IsNotNull(creada);
            Assert.IsTrue(creada.CitaId > 0);
            Assert.AreEqual("ATENDIDA", creada.Estado);
     
        }

        [TestMethod]
        public void TestObtenerPorId()
        {
            // Creamos primero para asegurar que existe
            var baseDto = NuevaCitaDto(2, 2,
                new DateTime(2025, 11, 09),
                new DateTime(2025, 11, 10, 1, 37, 12, DateTimeKind.Utc),
                new DateTime(2025, 11, 10, 2, 37, 12, DateTimeKind.Utc),
                "ATENDIDA", "Obs para obtener", true, 10.0, 40.0);

            var creada = citaBo.Insertar(baseDto);

            var porId = citaBo.ObtenerPorId(creada.CitaId);

            Assert.IsNotNull(porId);
            Assert.AreEqual(creada.CitaId, porId.CitaId);
            Assert.AreEqual("ATENDIDA", porId.Estado);
        }

        [TestMethod]
        public void TestModificar()
        {
            // Insertamos primero
            var original = NuevaCitaDto(2, 2,
                new DateTime(2025, 11, 09),
                new DateTime(2025, 11, 10, 1, 37, 12, DateTimeKind.Utc),
                new DateTime(2025, 11, 10, 2, 37, 12, DateTimeKind.Utc),
                "ATENDIDA", "Obs original", false, 9.9, 200.3);

            var creada = citaBo.Insertar(original);

            // Preparamos dto modificado (mismo citaId)
            var mod = new CitaAtencionDto
            {
                CitaId = creada.CitaId,
                Veterinario = new VeterinarioRef { VeterinarioId = 2 },
                Mascota = new MascotaRef { MascotaId = 2 },
                FechaRegistro = CitaAtencionBo.ToSqlDateZ(new DateTime(2025, 11, 09)),
                FechaHoraInicio = CitaAtencionBo.ToUtcZoned(new DateTime(2025, 11, 10, 1, 37, 12, DateTimeKind.Utc)),
                FechaHoraFin = CitaAtencionBo.ToUtcZoned(new DateTime(2025, 11, 10, 2, 47, 12, DateTimeKind.Utc)), // fin +10 min
                PesoMascota = 9999.0,
                Monto = 210.0,
                Estado = "ATENDIDA",
                Observacion = "Observación restful Modificada",
                Activo = false
            };

            var actualizado = citaBo.Modificar(mod);

            Assert.IsNotNull(actualizado);
            Assert.AreEqual(creada.CitaId, actualizado.CitaId);
        
            Assert.AreEqual(false, actualizado.Activo);
        }

        //[TestMethod]
        //public void TestEliminar()
        //{
        //    // Insertamos y luego eliminamos esa misma cita
        //    var dto = NuevaCitaDto(2, 2,
        //        new DateTime(2025, 11, 09),
        //        new DateTime(2025, 11, 10, 1, 37, 12, DateTimeKind.Utc),
        //        new DateTime(2025, 11, 10, 2, 37, 12, DateTimeKind.Utc),
        //        "ATENDIDA", "Obs a eliminar", true, 11.0, 55.0);

        //    var creada = citaBo.Insertar(dto);

        //    var ok = citaBo.Eliminar(creada.CitaId);
        //    Assert.IsTrue(ok);

        //    var debeSerNull = citaBo.ObtenerPorId(creada.CitaId);
        //    Assert.IsNull(debeSerNull);
        //}

        // =======================
        // Helper para armar DTO
        // =======================
        private static CitaAtencionDto NuevaCitaDto(
            int vetId, int masId,
            DateTime fechaReg, DateTime iniUtc, DateTime finUtc,
            string estado, string obs, bool activo,
            double peso, double monto)
        {
            return new CitaAtencionDto
            {
                Veterinario = new VeterinarioRef { VeterinarioId = vetId },
                Mascota = new MascotaRef { MascotaId = masId },
                FechaRegistro = CitaAtencionBo.ToSqlDateZ(fechaReg),         // "yyyy-MM-ddZ"
                FechaHoraInicio = CitaAtencionBo.ToUtcZoned(iniUtc),         // "yyyy-MM-dd'T'HH:mm:ssZ[UTC]"
                FechaHoraFin = CitaAtencionBo.ToUtcZoned(finUtc),            // idem
                PesoMascota = peso,
                Monto = monto,
                Estado = estado,
                Observacion = obs,
                Activo = activo
            };
        }
    }
}
