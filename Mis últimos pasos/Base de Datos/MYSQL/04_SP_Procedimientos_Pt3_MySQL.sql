USE petmedicalcare;

-- =============================================
-- 21. VERIFICAR RELACION SERVICIO (Para Borrado)
-- =============================================
DROP PROCEDURE IF EXISTS sp_verificar_relacion_servicio;
DELIMITER $$
CREATE PROCEDURE sp_verificar_relacion_servicio(
    IN p_servicio_id INT,
    OUT p_valorRetornado INT
)
BEGIN
    -- Inicializamos en 0 (Falso/No encontrado)
    SET p_valorRetornado = 0;

    -- 1. Buscar en Detalles de Citas
    SELECT 1 INTO p_valorRetornado
    FROM DETALLES_SERVICIO
    WHERE SERVICIO_ID = p_servicio_id
    LIMIT 1;

    -- 2. Si no se encontró, buscar en Ventas/Pagos
    IF p_valorRetornado = 0 THEN
        SELECT 1 INTO p_valorRetornado
        FROM DETALLES_DOCUMENTO_DE_PAGO
        WHERE SERVICIO_ID = p_servicio_id
        LIMIT 1;
    END IF;
END$$
DELIMITER ;