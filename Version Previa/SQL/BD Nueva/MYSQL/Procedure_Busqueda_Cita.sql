USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_listar_citas_por_fecha;

DELIMITER $$
CREATE PROCEDURE sp_listar_citas_por_fecha(
    IN p_fecha VARCHAR(20) -- <-- CAMBIO AQUÍ
)
BEGIN
    DECLARE v_fecha_busqueda DATE;

    -- CAMBIO AQUÍ:
    -- Normaliza el string: si es NULL o '' usa la fecha de hoy.
    -- Si no, usa la fecha enviada.
    SET v_fecha_busqueda = COALESCE(NULLIF(TRIM(p_fecha), ''), CURDATE());
	
    SELECT
        -- Info de la Cita
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

    -- Unir con Veterinarios y su info personal
    JOIN VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID

    -- Unir con Mascotas y su dueño
    JOIN MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN PERSONAS AS p_cli ON m.PERSONA_ID = p_cli.PERSONA_ID

    WHERE
        -- Comparamos solo la parte de la FECHA del campo DATETIME
        DATE(c.FECHA_HORA_INICIO) = v_fecha_busqueda

    ORDER BY
        c.FECHA_HORA_INICIO ASC; -- Ordenadas de la más temprana a la más tarde
END$$
DELIMITER ;

CALL sp_listar_citas_por_fecha('2025-11-09');

-- Buscar una fecha específica (usa tus datos de prueba)
CALL sp_listar_citas_por_fecha('2025-11-03');

-- Buscar las citas de HOY (enviando NULL)
CALL sp_listar_citas_por_fecha(NULL);

-- Buscar las citas de HOY (enviando un string vacío)
CALL sp_listar_citas_por_fecha('');
