-- Asegúrate de estar en el esquema correcto
-- USE petmedicalcare;

DELIMITER $$

-- Limpieza por si ya existen
DROP TRIGGER IF EXISTS trg_SERVICIOS_bi_audit $$
DROP TRIGGER IF EXISTS trg_SERVICIOS_bu_audit $$

-- ============================================
-- BEFORE INSERT: inicializa creación y (si faltan) modificación
-- ============================================
CREATE TRIGGER trg_SERVICIOS_bi_audit
BEFORE INSERT ON SERVICIOS
FOR EACH ROW
BEGIN
  -- Fechas con hora
  IF NEW.FECHA_CREACION IS NULL THEN
    SET NEW.FECHA_CREACION = NOW();
  END IF;

  IF NEW.FECHA_MODIFICACION IS NULL THEN
    SET NEW.FECHA_MODIFICACION = NOW();
  END IF;

  -- Usuarios
  IF NEW.USUARIO_CREADOR IS NULL OR NEW.USUARIO_CREADOR = '' THEN
    SET NEW.USUARIO_CREADOR = COALESCE(@app_user, SUBSTRING_INDEX(CURRENT_USER(), '@', 1));
  END IF;

  IF NEW.USUARIO_MOFICADOR IS NULL OR NEW.USUARIO_MOFICADOR = '' THEN
    SET NEW.USUARIO_MOFICADOR = COALESCE(@app_user, SUBSTRING_INDEX(CURRENT_USER(), '@', 1));
  END IF;
END $$

-- ============================================
-- BEFORE UPDATE: actualiza siempre modificación y usuario
-- (NO toca creación/creador)
-- ============================================
CREATE TRIGGER trg_SERVICIOS_bu_audit
BEFORE UPDATE ON SERVICIOS
FOR EACH ROW
BEGIN
  SET NEW.FECHA_MODIFICACION = NOW();
  SET NEW.USUARIO_MOFICADOR  = COALESCE(@app_user, SUBSTRING_INDEX(CURRENT_USER(), '@', 1));

  -- (Opcional) blindar campos de creación si tu app los manda por error:
  -- SET NEW.FECHA_CREACION   = OLD.FECHA_CREACION;
  -- SET NEW.USUARIO_CREADOR  = OLD.USUARIO_CREADOR;
END $$

DELIMITER ;
SET @app_user := 'sofia';

-- Insert
INSERT INTO SERVICIOS (NOMBRE, COSTO, ESTADO, DESCRIPCION, ACTIVO, TIPO_SERVICIO_ID)
VALUES ('Consulta básica', 50.00, 'Disponible', 'Consulta general veterinaria', 1, 1);

-- Update
UPDATE SERVICIOS
SET COSTO = 55.00
WHERE SERVICIO_ID = LAST_INSERT_ID();

-- Verificar
SELECT SERVICIO_ID, FECHA_CREACION, USUARIO_CREADOR,
       FECHA_MODIFICACION, USUARIO_MOFICADOR
FROM SERVICIOS
ORDER BY SERVICIO_ID DESC
LIMIT 1;