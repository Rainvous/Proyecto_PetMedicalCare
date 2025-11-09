USE petmedicalcare;
DELIMITER $$

-- Limpieza (usa el esquema explícito)
DROP TRIGGER IF EXISTS petmedicalcare.trg_USUARIOS_bi_audit $$
DROP TRIGGER IF EXISTS petmedicalcare.trg_USUARIOS_bu_audit $$

-- ============================================
-- BEFORE INSERT
-- ============================================
CREATE TRIGGER trg_USUARIOS_bi_audit
BEFORE INSERT ON USUARIOS
FOR EACH ROW
BEGIN
  DECLARE v_user VARCHAR(50);
  -- Si @app_user no está seteado o está vacío, usa el usuario de la sesión MySQL
  SET v_user = COALESCE(NULLIF(@app_user, ''), SUBSTRING_INDEX(USER(), '@', 1));

  IF NEW.FECHA_CREACION IS NULL THEN
    SET NEW.FECHA_CREACION = NOW();
  END IF;

  IF NEW.FECHA_MODIFICACION IS NULL THEN
    SET NEW.FECHA_MODIFICACION = NOW();
  END IF;

  IF NEW.USUARIO_CREADOR IS NULL OR NEW.USUARIO_CREADOR = '' THEN
    SET NEW.USUARIO_CREADOR = v_user;
  END IF;

  IF NEW.USUARIO_MODIFICADOR IS NULL OR NEW.USUARIO_MODIFICADOR = '' THEN
    SET NEW.USUARIO_MODIFICADOR = v_user;
  END IF;
END $$
  
-- ============================================
-- BEFORE UPDATE
-- ============================================
CREATE TRIGGER trg_USUARIOS_bu_audit
BEFORE UPDATE ON USUARIOS
FOR EACH ROW
BEGIN
  DECLARE v_user VARCHAR(50);
  SET v_user = COALESCE(NULLIF(@app_user, ''), SUBSTRING_INDEX(USER(), '@', 1));

  -- Siempre refresca la modificación
  SET NEW.FECHA_MODIFICACION = NOW();
  SET NEW.USUARIO_MODIFICADOR = v_user;

  -- Blindaje opcional: descomenta si quieres impedir que te cambien creación/creador
  -- SET NEW.FECHA_CREACION  = OLD.FECHA_CREACION;
  -- SET NEW.USUARIO_CREADOR = OLD.USUARIO_CREADOR;
END $$

DELIMITER ;

-- =======================
-- Prueba rápida
-- =======================
-- Opcional: propagar usuario de la app
SET @app_user := 'Alcachofa de Kaori';

INSERT INTO USUARIOS (USERNAME, PASSWORD, CORREO, ACTIVO)
VALUES ('Jose nefi castillo', 'secreto2', 'jose@gmail', 1);

UPDATE USUARIOS
SET ACTIVO = 0
WHERE USERNAME = 'Jose nefi castillo';

SELECT USUARIO_ID, USERNAME, FECHA_CREACION, USUARIO_CREADOR,
       FECHA_MODIFICACION, USUARIO_MODIFICADOR, ACTIVO
FROM USUARIOS
WHERE USERNAME = 'Jose nefi castillo';
