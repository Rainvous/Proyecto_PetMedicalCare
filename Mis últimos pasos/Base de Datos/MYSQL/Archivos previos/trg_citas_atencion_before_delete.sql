USE petmedicalcare;

DELIMITER $$

DROP TRIGGER IF EXISTS trg_citas_atencion_before_delete $$

CREATE TRIGGER trg_citas_atencion_before_delete
BEFORE DELETE ON CITAS_ATENCION
FOR EACH ROW
BEGIN
    -- Antes de que la cita desaparezca, eliminamos sus detalles de servicio
    -- Utilizamos OLD.CITA_ID para referirnos al ID de la fila que se está intentando borrar
    DELETE FROM DETALLES_SERVICIO 
    WHERE CITA_ID = OLD.CITA_ID;

END$$

DELIMITER ;

USE petmedicalcare;

-- 1. CAMBIAMOS EL DELIMITADOR
DELIMITER $$

-- 2. BORRAMOS SI YA EXISTE (Para evitar errores de "Trigger already exists")
DROP TRIGGER IF EXISTS trg_citas_atencion_before_delete $$

-- 3. CREAMOS EL TRIGGER
CREATE TRIGGER trg_citas_atencion_before_delete
BEFORE DELETE ON CITAS_ATENCION
FOR EACH ROW
BEGIN
    -- Eliminar los detalles de servicio asociados a la cita
    DELETE FROM DETALLES_SERVICIO 
    WHERE CITA_ID = OLD.CITA_ID;

    -- Eliminar las recetas médicas asociadas (Recomendado para evitar otro error de FK)
    DELETE FROM RECETAS_MEDICAS 
    WHERE CITA_ID = OLD.CITA_ID;
    
END$$

-- 4. VOLVEMOS AL DELIMITADOR NORMAL
DELIMITER ;

-- 1. Insertamos una Cita de prueba (para hoy)
INSERT INTO CITAS_ATENCION (VETERINARIO_ID, MASCOTA_ID, FECHA_REGISTRO, ESTADO_CITA, ACTIVO)
VALUES (1, 1, CURDATE(), 'PROGRAMADA', 1);

SET @id_cita_borrar = LAST_INSERT_ID();

-- 2. Le insertamos un Detalle de Servicio a esa cita
INSERT INTO DETALLES_SERVICIO (CITA_ID, SERVICIO_ID, DESCRIPCION, COSTO, ACTIVO)
VALUES (@id_cita_borrar, 1, 'Servicio de Prueba para Borrar', 50.00, 1);

-- 3. Verificamos que existen
SELECT * FROM DETALLES_SERVICIO WHERE CITA_ID = @id_cita_borrar;

-- 4. ¡LA PRUEBA DE FUEGO! Borramos la cita
-- Sin el trigger, esto daría error de Foreign Key.
-- Con el trigger, debería funcionar y borrar ambas cosas.
DELETE FROM CITAS_ATENCION WHERE CITA_ID = @id_cita_borrar;

-- 5. Verificamos que se borró todo (Ambos deben dar vacío)
SELECT * FROM CITAS_ATENCION WHERE CITA_ID = @id_cita_borrar;
SELECT * FROM DETALLES_SERVICIO WHERE CITA_ID = @id_cita_borrar;




