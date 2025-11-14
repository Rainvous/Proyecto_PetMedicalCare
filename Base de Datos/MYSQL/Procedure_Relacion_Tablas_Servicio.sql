USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_verificar_relacion_servicio;

DELIMITER $$
CREATE PROCEDURE sp_verificar_relacion_servicio(
    IN p_servicio_id INT,
    OUT p_valorRetornado INT
)
BEGIN
    -- 1. Establece el valor por defecto (0 = Sin relación)
    SET p_valorRetornado = 0;

    -- 2. Busca en la primera tabla relacionada (DETALLES_SERVICIO)
    -- Si encuentra uno, pone 1 y termina la búsqueda.
    SELECT 1 INTO p_valorRetornado
    FROM DETALLES_SERVICIO
    WHERE SERVICIO_ID = p_servicio_id
    LIMIT 1;

    -- 3. Si NO lo encontró (p_valorRetornado sigue en 0), 
    --    busca en la segunda tabla (DETALLES_DOCUMENTO_DE_PAGO)
    IF p_valorRetornado = 0 THEN
        SELECT 1 INTO p_valorRetornado
        FROM DETALLES_DOCUMENTO_DE_PAGO
        WHERE SERVICIO_ID = p_servicio_id
        LIMIT 1;
    END IF;
    
END$$
DELIMITER ;

-- --- PRUEBA 1: Servicio CON relaciones ---
-- 'Consulta general' SÍ está en DETALLES_SERVICIO y DETALLES_DOCUMENTO_DE_PAGO
SET @id_servicio_usado = (SELECT SERVICIO_ID FROM SERVICIOS WHERE NOMBRE = 'Consulta general' LIMIT 1);

CALL sp_verificar_relacion_servicio(@id_servicio_usado, @resultado);

SELECT @resultado AS 'Servicio_Consulta_General';
-- Resultado esperado: 1


-- --- PRUEBA 2: Servicio SIN relaciones ---
-- 'Esterilización felina' se insertó en SERVICIOS, 
-- pero NO se usó en ninguna cita o documento de pago
SET @id_servicio_no_usado = (SELECT SERVICIO_ID FROM SERVICIOS WHERE NOMBRE = 'Esterilización felina' LIMIT 1);

CALL sp_verificar_relacion_servicio(@id_servicio_no_usado, @resultado);

SELECT @resultado AS 'Servicio_Esterilizacion';
-- Resultado esperado: 0