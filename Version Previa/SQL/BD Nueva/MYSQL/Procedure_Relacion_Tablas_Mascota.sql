USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_verificar_relacion_mascota;

DELIMITER $$
CREATE PROCEDURE sp_verificar_relacion_mascota(
    IN p_mascota_id INT,
    OUT p_valorRetornado INT
)
BEGIN
    -- 1. Establece el valor por defecto (0 = Sin relación)
    SET p_valorRetornado = 0;

    -- 2. Busca si existe al menos 1 registro en la tabla relacionada (CITAS_ATENCION)
    -- Si encuentra uno, sobrescribe el valor de p_valorRetornado a 1.
    -- Si no encuentra nada, el valor se mantiene en 0.
    SELECT 1 INTO p_valorRetornado
    FROM CITAS_ATENCION
    WHERE MASCOTA_ID = p_mascota_id
    LIMIT 1; -- Esta es la forma más rápida de verificar existencia
END$$
DELIMITER ;

-- --- PRUEBA 1: Mascota CON relaciones ---
-- 'Firulais' SÍ tiene citas registradas
SET @id_mascota_con_citas = (SELECT MASCOTA_ID FROM MASCOTAS WHERE NOMBRE = 'Firulais' LIMIT 1);

CALL sp_verificar_relacion_mascota(@id_mascota_con_citas, @resultado);

SELECT @resultado AS 'Mascota_Firulais';
-- Resultado esperado: 1


-- --- PRUEBA 2: Mascota SIN relaciones ---
-- Insertamos una mascota nueva para la prueba (no tiene citas)
INSERT INTO MASCOTAS (PERSONA_ID, NOMBRE, ESPECIE, SEXO, ACTIVO)
VALUES ((SELECT PERSONA_ID FROM PERSONAS WHERE NOMBRE='Luisa Romero' LIMIT 1), 'MascotaDePrueba', 'Hamster', 'M', 1);

SET @id_mascota_sin_citas = LAST_INSERT_ID();

CALL sp_verificar_relacion_mascota(@id_mascota_sin_citas, @resultado);

SELECT @resultado AS 'Mascota_De_Prueba';
-- Resultado esperado: 0

-- Limpieza
DELETE FROM MASCOTAS WHERE MASCOTA_ID = @id_mascota_sin_citas;
