USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_verificar_relacion_persona;

DELIMITER $$
CREATE PROCEDURE sp_verificar_relacion_persona(
    IN p_persona_id INT,
    OUT p_valorRetornado INT
)
BEGIN
    -- 1. Establece el valor por defecto (0 = Sin relación)
    SET p_valorRetornado = 0;

    -- 2. Busca en la primera tabla (MASCOTAS)
    SELECT 1 INTO p_valorRetornado
    FROM MASCOTAS
    WHERE PERSONA_ID = p_persona_id
    LIMIT 1;

    -- 3. Si NO lo encontró, busca en la segunda tabla (VETERINARIOS)
    IF p_valorRetornado = 0 THEN
        SELECT 1 INTO p_valorRetornado
        FROM VETERINARIOS
        WHERE PERSONA_ID = p_persona_id
        LIMIT 1;
    END IF;

    -- 4. Si AÚN NO lo encontró, busca en la tercera tabla (DOCUMENTOS_DE_PAGO)
    IF p_valorRetornado = 0 THEN
        SELECT 1 INTO p_valorRetornado
        FROM DOCUMENTOS_DE_PAGO
        WHERE PERSONA_ID = p_persona_id
        LIMIT 1;
    END IF;
    
END$$
DELIMITER ;

-- --- PRUEBA 1: Persona CON relación (Dueña de mascota) ---
-- 'Luisa Romero' SÍ tiene Mascotas y Documentos de Pago
SET @id_persona_cliente = (SELECT PERSONA_ID FROM PERSONAS WHERE NOMBRE = 'Luisa Romero' LIMIT 1);

CALL sp_verificar_relacion_persona(@id_persona_cliente, @resultado);

SELECT @resultado AS 'Persona_Luisa_Romero';
-- Resultado esperado: 1


-- --- PRUEBA 2: Persona CON relación (Es veterinario) ---
-- 'Ana Pérez' SÍ es una Veterinaria
SET @id_persona_vet = (SELECT PERSONA_ID FROM PERSONAS WHERE NOMBRE = 'Ana Pérez' LIMIT 1);

CALL sp_verificar_relacion_persona(@id_persona_vet, @resultado);

SELECT @resultado AS 'Persona_Ana_Perez';
-- Resultado esperado: 1


-- --- PRUEBA 3: Persona SIN relaciones (Recepcionista) ---
-- 'Rosa Díaz' es una persona, pero NO tiene mascotas, 
-- NO es veterinaria, y NO ha comprado nada (no está en Documentos de Pago)
SET @id_persona_sin_rel = (SELECT PERSONA_ID FROM PERSONAS WHERE NOMBRE = 'Rosa Díaz' LIMIT 1);

CALL sp_verificar_relacion_persona(@id_persona_sin_rel, @resultado);

SELECT @resultado AS 'Persona_Rosa_Diaz';
-- Resultado esperado: 0