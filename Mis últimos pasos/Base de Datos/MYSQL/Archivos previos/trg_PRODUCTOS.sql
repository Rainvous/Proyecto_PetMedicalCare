use petmedicalcare;
DELIMITER $$

-- Limpieza (por si existen)
DROP TRIGGER IF EXISTS trg_PRODUCTOS_bi_audit $$
DROP TRIGGER IF EXISTS trg_PRODUCTOS_bu_audit $$

-- ============================================
-- BEFORE INSERT: completa creación y (si faltan) modificación
-- ============================================
CREATE TRIGGER trg_PRODUCTOS_bi_audit
BEFORE INSERT ON PRODUCTOS
FOR EACH ROW
BEGIN
  -- Fechas
  IF NEW.FECHA_CREACION IS NULL THEN
    SET NEW.FECHA_CREACION = NOW();
  END IF;

  IF NEW.FECHA_MODIFICACION IS NULL THEN
    SET NEW.FECHA_MODIFICACION = NOW();
  END IF;

  -- Usuarios (toma @app_user si la defines; si no, el usuario de MySQL)
  IF NEW.USUARIO_CREADOR IS NULL OR NEW.USUARIO_CREADOR = '' THEN
    SET NEW.USUARIO_CREADOR = COALESCE(@app_user, SUBSTRING_INDEX(CURRENT_USER(), '@', 1));
  END IF;

  IF NEW.USUARIO_MODIFICADOR IS NULL OR NEW.USUARIO_MODIFICADOR = '' THEN
    SET NEW.USUARIO_MODIFICADOR = COALESCE(@app_user, SUBSTRING_INDEX(CURRENT_USER(), '@', 1));
  END IF;
END $$

-- ============================================
-- BEFORE UPDATE: siempre actualiza modificación y usuario
-- (NO toca creación/creador)
-- ============================================
CREATE TRIGGER trg_PRODUCTOS_bu_audit
BEFORE UPDATE ON PRODUCTOS
FOR EACH ROW
BEGIN
  SET NEW.FECHA_MODIFICACION = NOW();
  SET NEW.USUARIO_MODIFICADOR = COALESCE(@app_user, SUBSTRING_INDEX(CURRENT_USER(), '@', 1));

  -- Opcional (blindaje):
  -- SET NEW.FECHA_CREACION  = OLD.FECHA_CREACION;
  -- SET NEW.USUARIO_CREADOR = OLD.USUARIO_CREADOR;
END $$

DELIMITER ;
-- (Solo si necesitas un tipo de producto de prueba)
-- INSERT INTO TIPOS_PRODUCTO (NOMBRE, DESCRIPCION, ACTIVO) VALUES ('Medicamento', 'Fármacos', 1);

-- Usuario de app (opcional)
SET @app_user := 'admin';

-- Insert
INSERT INTO PRODUCTOS (TIPO_PRODUCTO_ID, NOMBRE, PRESENTACION, PRECIO_UNITARIO, STOCK, ACTIVO)
VALUES (1, 'Antipulgas Z', 'Talla M', 32.30, 10, 1);

-- Update
UPDATE PRODUCTOS
SET PRECIO_UNITARIO = 33.90
WHERE PRODUCTO_ID = LAST_INSERT_ID();

-- Verificar
SELECT PRODUCTO_ID, NOMBRE, FECHA_CREACION, USUARIO_CREADOR,
       FECHA_MODIFICACION, USUARIO_MODIFICADOR
FROM PRODUCTOS
ORDER BY PRODUCTO_ID DESC
LIMIT 1;
