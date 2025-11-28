//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using SoftPetBussiness.UsuarioClient;
//using System;
//using System.Collections.Generic;

//namespace SoftPetBusiness.Tests
//{
//    [TestClass]
//    public class UsuarioBOTest
//    {
//        private UsuarioBO usuarioBO;

//        [TestInitialize]
//        public void Setup()
//        {
//            usuarioBO = new UsuarioBO();
//        }

//        [TestMethod]
//        public void TestListarTodos()
//        {
//            // Listar todos los usuarios
//            List<usuarioDto> usuarios = usuarioBO.ListarTodos();

//            // Validaciones
//            Assert.IsNotNull(usuarios);
//            Assert.IsTrue(usuarios.Count > 0, "La lista de usuarios no debería estar vacía.");
//        }

//        [TestMethod]
//        public void Insertar()
//        {
//            // Crear DTO de usuario
//            var nuevoUsuario = new usuarioDto
//            {
//                username = "usuario.prueba",
//                password = "clave123",
//                correo = "usuario.prueba@example.com",
//                activo = true
//            };

//            // Insertar
//            int resultado = usuarioBO.Insertar(nuevoUsuario);

//            // Validación
//            Console.WriteLine("ID del nuevo usuario: " + resultado);
//            Assert.IsTrue(resultado > 0, "El usuario debería haberse insertado correctamente.");
//        }

//        [TestMethod]
//        public void Modificar()
//        {
//            // Obtener usuario existente (ajusta el ID según tus datos)
//            int usuarioId = 2;
//            usuarioDto usuarioExistente = usuarioBO.ObtenerPorId(usuarioId);

//            Assert.IsNotNull(usuarioExistente, "El usuario no debería ser nulo.");

//            // Cambios
//            usuarioExistente.correo = "usuario.actualizado@example.com";
//            usuarioExistente.password = "nuevaClave456";

//            // Modificar (parámetros sueltos, siguiendo tu plantilla)
//            int resultado = usuarioBO.Modificar(
//                usuarioExistente.usuarioId,
//                usuarioExistente.username,
//                usuarioExistente.password,
//                usuarioExistente.correo,
//                usuarioExistente.activo
//            );

//            Assert.IsTrue(resultado > 0, "El usuario debería haberse modificado correctamente.");
//        }

//        [TestMethod]
//        public void Eliminar()
//        {
//            // ID a eliminar (ajusta según tus datos)
//            int usuarioIdAEliminar = 3;

//            int resultado = usuarioBO.Eliminar(usuarioIdAEliminar);

//            Assert.IsTrue(resultado > 0, "El usuario debería haberse eliminado correctamente.");
//        }

//        [TestMethod]
//        public void ObtenerPorId()
//        {
//            // ID a consultar (ajusta según tus datos)
//            int usuarioId = 2;

//            usuarioDto usuario = usuarioBO.ObtenerPorId(usuarioId);

//            Assert.IsNotNull(usuario, "El usuario no debería ser nulo.");
//            Assert.AreEqual(usuarioId, usuario.usuarioId, "El ID del usuario obtenido debería coincidir.");
//        }
//    }
//}
