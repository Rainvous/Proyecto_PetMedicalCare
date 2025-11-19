USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_listar_citas_por_mascota_y_fecha;

DELIMITER $$
CREATE PROCEDURE sp_listar_citas_por_mascota_y_fecha(
    IN p_mascota_id INT,
    IN p_fecha VARCHAR(20)
)
BEGIN
    -- Convertimos el texto a fecha para asegurar la comparación
    -- Si p_fecha es NULL o inválida, esto podría no devolver nada, 
    -- lo cual es el comportamiento seguro para "fecha en específico".
    DECLARE v_fecha_filtro DATE;
    SET v_fecha_filtro = DATE(p_fecha);

    SELECT 
        c.CITA_ID,
        c.VETERINARIO_ID,
        c.MASCOTA_ID,
        c.FECHA_REGISTRO,
        c.FECHA_HORA_INICIO,
        c.FECHA_HORA_FIN,
        c.PESO_MASCOTA,
        c.MONTO,
        c.ESTADO_CITA,
        c.OBSERVACION,
        c.ACTIVO

    FROM CITAS_ATENCION AS c
    
    -- JOIN con Mascotas
    JOIN MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    
    -- JOIN con Veterinarios y Personas (para el nombre del Dr.)
    JOIN VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID
    
    WHERE 
        c.MASCOTA_ID = p_mascota_id
        AND DATE(c.FECHA_HORA_INICIO) = v_fecha_filtro
    
    ORDER BY c.FECHA_HORA_INICIO ASC;

END$$
DELIMITER ;

-- Caso 1: Mascota que SÍ tiene cita en esa fecha (Firulais tiene una cita el 2025-10-28).
-- Reemplaza '1' con el ID real de Firulais
CALL sp_listar_citas_por_mascota_y_fecha(1, '2025-10-28');

-- Caso 2: Mascota que NO tiene cita en esa fecha (Firulais no tiene citas el 2025-01-01).
CALL sp_listar_citas_por_mascota_y_fecha(1, '2025-01-01');

-- Caso 3: Mascota incorrecta (ID que no existe)
CALL sp_listar_citas_por_mascota_y_fecha(999, '2025-10-28');