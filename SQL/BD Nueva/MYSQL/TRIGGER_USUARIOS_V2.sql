DELIMITER $$

-- Limpieza si ya existen
DROP TRIGGER IF EXISTS trg_USUARIOS_bi_audit $$
DROP TRIGGER IF EXISTS trg_USUARIOS_bu_audit $$

-- ============================================
-- BEFORE INSERT: setea creación y (si faltan) modificación
-- ============================================
CREATE TRIGGER trg_USUARIOS_bi_audit
BEFORE INSERT ON USUARIOS
FOR EACH ROW
BEGIN
  -- Fechas con hora
  IF NEW.FECHA_CREACION IS NULL THEN
    SET NEW.FECHA_CREACION = NOW();
  END IF;

  IF NEW.FECHA_MODIFICACION IS NULL THEN
    SET NEW.FECHA_MODIFICACION = NOW();
  END IF;

  -- Usuarios (app o usuario MySQL)
  IF NEW.USUARIO_CREADOR IS NULL OR NEW.USUARIO_CREADOR = '' THEN
    SET NEW.USUARIO_CREADOR = COALESCE(@app_user, SUBSTRING_INDEX(CURRENT_USER(), '@', 1));
  END IF;

  IF NEW.USUARIO_MODIFICADOR IS NULL OR NEW.USUARIO_MODIFICADOR = '' THEN
    SET NEW.USUARIO_MODIFICADOR = COALESCE(@app_user, SUBSTRING_INDEX(CURRENT_USER(), '@', 1));
  END IF;
END $$

-- ============================================
-- BEFORE UPDATE: siempre actualiza modif y usuario
-- (no toca creación/creador)
-- ============================================
CREATE TRIGGER trg_USUARIOS_bu_audit
BEFORE UPDATE ON USUARIOS
FOR EACH ROW
BEGIN
  SET NEW.FECHA_MODIFICACION = NOW();
  SET NEW.USUARIO_MODIFICADOR  = COALESCE(@app_user, SUBSTRING_INDEX(CURRENT_USER(), '@', 1));

  -- (Opcional) blindaje por si tu app manda estos campos por error:
  -- SET NEW.FECHA_CREACION  = OLD.FECHA_CREACION;
  -- SET NEW.USUARIO_CREADOR = OLD.USUARIO_CREADOR;
END $$

DELIMITER ;

-- =======================
-- Prueba rápida
-- =======================
SET @app_user := 'Alcachofa de Kaori';  -- opcional

INSERT INTO USUARIOS (USERNAME, PASSWORD, CORREO, ACTIVO)
VALUES ('carlos_jose', 'secreto2', 'carlos@demo.com', 1);

UPDATE USUARIOS
SET ACTIVO = 0
WHERE USUARIO_ID = LAST_INSERT_ID();

SELECT USUARIO_ID, USERNAME, FECHA_CREACION, USUARIO_CREADOR,
       FECHA_MODIFICACION, USUARIO_MODIFICADOR, ACTIVO
FROM USUARIOS
ORDER BY USUARIO_ID DESC
LIMIT 1;
