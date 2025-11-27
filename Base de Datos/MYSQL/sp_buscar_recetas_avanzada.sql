USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_buscar_recetas_avanzada;

DELIMITER $$
CREATE PROCEDURE sp_buscar_recetas_avanzada(
    IN p_nombre_mascota VARCHAR(100),
    IN p_nombre_duenio VARCHAR(100),
    IN p_fecha DATE,        -- NULL para ignorar
    IN p_activo VARCHAR(10) -- '1', '0' o NULL
)
BEGIN
    SET p_nombre_mascota = NULLIF(TRIM(p_nombre_mascota), '');
    SET p_nombre_duenio = NULLIF(TRIM(p_nombre_duenio), '');
    SET p_activo = NULLIF(TRIM(p_activo), '');

    SELECT 
        r.RECETA_MEDICA_ID,
        r.CITA_ID,
        r.FECHA_EMISION,
        r.VIGENCIA_HASTA,
        r.DIAGNOSTICO,
        r.OBSERVACIONES,
        r.ACTIVO,
        -- Datos Informativos
        m.NOMBRE AS MASCOTA_NOMBRE,
        CONCAT(m.ESPECIE, ' - ', m.RAZA) AS MASCOTA_DETALLE,
        p.NOMBRE AS DUENIO_NOMBRE,
        c.FECHA_HORA_INICIO AS FECHA_CITA
    FROM RECETAS_MEDICAS r
    JOIN CITAS_ATENCION c ON r.CITA_ID = c.CITA_ID
    JOIN MASCOTAS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN PERSONAS p ON m.PERSONA_ID = p.PERSONA_ID
    WHERE 
        (p_nombre_mascota IS NULL OR m.NOMBRE LIKE CONCAT('%', p_nombre_mascota, '%'))
        AND (p_nombre_duenio IS NULL OR p.NOMBRE LIKE CONCAT('%', p_nombre_duenio, '%'))
        AND (p_fecha IS NULL OR r.FECHA_EMISION = p_fecha)
        AND (p_activo IS NULL OR r.ACTIVO = p_activo)
    ORDER BY r.FECHA_EMISION DESC;
END$$
DELIMITER ;