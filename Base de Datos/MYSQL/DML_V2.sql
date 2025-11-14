-- ===============================================
-- INSERTS DE DATOS DE PRUEBA
-- ===============================================
use tu_esquema;
-- USUARIOS
INSERT INTO USUARIOS (USERNAME, PASSWORD, CORREO, ACTIVO) VALUES
('admin', 'hash_admin', 'admin@vet.com', 1),
('vet_jose', 'hash_vetjose', 'jose@vet.com', 1),
('vet_ana', 'hash_vetana', 'ana@vet.com', 1),
('recepcion', 'hash_recep', 'recepcion@vet.com', 1);

-- PERSONAS
INSERT INTO PERSONAS (USUARIO_ID, NOMBRE, DIRECCION, TELEFONO, SEXO, NRO_DOCUMENTO, RUC, TIPO_DOCUMENTO, ACTIVO) VALUES
(1, 'Carlos Perez', 'Av. Siempre Viva 123', '999111222', 'M', '12345678', NULL, 'DNI', 1),
(2, 'Jose Ramos', 'Jr. Los Olivos 456', '988222333', 'M', '23456789', NULL, 'DNI', 1),
(3, 'Ana Torres', 'Calle Lima 789', '977333444', 'F', '34567890', NULL, 'DNI', 1),
(4, 'Lucia Romero', 'Av. Grau 101', '966444555', 'F', '45678901', NULL, 'DNI', 1);

-- MASCOTAS
INSERT INTO MASCOTAS (PERSONA_ID, NOMBRE, ESPECIE, SEXO, RAZA, COLOR, ACTIVO) VALUES
(1, 'Firulais', 'Perro', 'M', 'Labrador', 'Marrón', 1),
(1, 'Michi', 'Gato', 'F', 'Siames', 'Blanco', 1),
(3, 'Nina', 'Perro', 'F', 'Poodle', 'Blanco', 1),
(4, 'Rocky', 'Perro', 'M', 'Bulldog', 'Negro', 1);

-- VETERINARIOS
INSERT INTO VETERINARIOS (PERSONA_ID, ESPECIALIDAD_ID, FECHA_DE_CONTRATACION, ESTADO, ESPECIALIDAD, ACTIVO) VALUES
(2, NULL, '2020-01-15', 'ACTIVO', 'Medicina General', 1),
(3, NULL, '2021-05-10', 'ACTIVO', 'Cirugía', 1),
(4, NULL, '2022-02-20', 'ACTIVO', 'Dermatología', 1),
(2, NULL, '2023-03-12', 'ACTIVO', 'Urgencias', 1);

-- CITAS_ATENCION
INSERT INTO CITAS_ATENCION (VETERINARIO_ID, MASCOTA_ID, FECHA_REGISTRO, FECHA_HORA_INICIO, FECHA_HORA_FIN, PESO_MASCOTA, MONTO, ESTADO_CITA, OBSERVACION, ACTIVO) VALUES
(1, 1, '2025-01-01', '2025-01-02 09:00:00', '2025-01-02 09:30:00', 25.5, 80.00, 'ATENDIDA', 'Revisión general', 1),
(2, 2, '2025-01-03', '2025-01-04 10:00:00', '2025-01-04 10:45:00', 4.2, 90.00, 'ATENDIDA', 'Control anual', 1),
(3, 3, '2025-01-05', '2025-01-06 11:00:00', '2025-01-06 11:40:00', 7.5, 100.00, 'PROGRAMADA', 'Chequeo dental', 1),
(1, 4, '2025-01-07', '2025-01-08 12:00:00', '2025-01-08 12:30:00', 30.0, 110.00, 'PROGRAMADA', 'Vacuna antirrábica', 1);

-- METODOS_DE_PAGO
INSERT INTO METODOS_DE_PAGO (METODO_DE_PAGO_ID, NOMBRE, ACTIVO) VALUES
(1, 'Efectivo', 1),
(2, 'Tarjeta Crédito', 1),
(3, 'Tarjeta Débito', 1),
(4, 'Transferencia Bancaria', 1);

-- DOCUMENTOS_DE_PAGO
INSERT INTO DOCUMENTOS_DE_PAGO (METODO_DE_PAGO_ID, PERSONA_ID, TIPO_DOCUMENTO, SERIE, NUMERO, FECHA_EMISION, ESTADO, SUBTOTAL, IGV_TOTAL, TOTAL, ACTIVO) VALUES
(1, 1, 'Boleta', 'B001', '0001', '2025-01-02', 'PAGADO', 80.00, 14.40, 94.40, 1),
(2, 2, 'Factura', 'F001', '0002', '2025-01-04', 'PAGADO', 90.00, 16.20, 106.20, 1),
(3, 3, 'Boleta', 'B001', '0003', '2025-01-06', 'EMITIDO', 100.00, 18.00, 118.00, 1),
(4, 4, 'Factura', 'F001', '0004', '2025-01-08', 'PAGADO', 110.00, 19.80, 129.80, 1);

-- TIPOS_PRODUCTO
INSERT INTO TIPOS_PRODUCTO (NOMBRE, DESCRIPCION, ACTIVO) VALUES
('Medicamento', 'Fármacos para tratamiento veterinario', 1),
('Vacuna', 'Inmunizaciones para mascotas', 1),
('Accesorio', 'Correas, collares, etc.', 1),
('Alimento', 'Comida balanceada para mascotas', 1);

-- PRODUCTOS
INSERT INTO PRODUCTOS (TIPO_PRODUCTO_ID, NOMBRE, PRESENTACION, PRECIO_UNITARIO, STOCK, ACTIVO) VALUES
(1, 'Antibiótico Canino', 'Cápsulas 250mg x10', 15.50, 50, 1),
(2, 'Vacuna Rabia', 'Dosis única 5ml', 25.00, 100, 1),
(3, 'Collar Antipulgas', 'Talla M', 30.00, 20, 1),
(4, 'Croquetas Premium', 'Bolsa 10kg', 120.00, 10, 1);

-- TIPOS_SERVICIO
INSERT INTO TIPOS_SERVICIO (NOMBRE, DESCRIPCION, ACTIVO) VALUES
('Consulta General', 'Revisión completa del animal', 1),
('Cirugía', 'Procedimientos quirúrgicos', 1),
('Vacunación', 'Aplicación de vacunas', 1),
('Desparasitación', 'Eliminación de parásitos', 1);

-- SERVICIOS
INSERT INTO SERVICIOS (TIPO_SERVICIO_ID, NOMBRE, DESCRIPCION, COSTO, ESTADO, ACTIVO) VALUES
(1, 'Consulta Básica', 'Evaluación general del paciente', 80.00, 'ACTIVO', 1),
(2, 'Cirugía Menor', 'Extracción de cuerpo extraño', 250.00, 'ACTIVO', 1),
(3, 'Vacuna Antirrábica', 'Aplicación de vacuna anual', 110.00, 'ACTIVO', 1),
(4, 'Desparasitación Interna', 'Tratamiento antiparasitario', 90.00, 'ACTIVO', 1);

-- DETALLES_DOCUMENTO_DE_PAGO
INSERT INTO DETALLES_DOCUMENTO_DE_PAGO (DOCUMENTO_DE_PAGO_ID, SERVICIO_ID, PRODUCTO_ID, NRO_ITEM, DESCRIPCION, CANTIDAD, PRECIO_UNITARIO, VALOR_VENTA, ACTIVO) VALUES
(1, 1, NULL, 1, 'Consulta Básica', 1, 80.00, 80.00, 1),
(2, 3, NULL, 1, 'Vacuna Antirrábica', 1, 90.00, 90.00, 1);

INSERT INTO HORARIOS_LABORALES(VETERINARIO_ID, FECHA, ESTADO, HORA_INICIO, HORA_FIN, ACTIVO) VALUES (1, '2025-10-27', 'DISPONIBLE', '2025-10-27 08:00:00', '2025-10-27 16:00:00', 1);