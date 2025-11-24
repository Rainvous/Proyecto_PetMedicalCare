USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_buscar_servicios_avanzada_v3;

DELIMITER $$
CREATE PROCEDURE sp_buscar_servicios_avanzada_v3(
    IN p_nombre VARCHAR(100),
    IN p_rango_precio_id VARCHAR(10), 
    IN p_activo VARCHAR(10),
    IN p_tipo_servicio_id INT -- Nuevo parámetro para las pestañas
)
BEGIN
    SET p_nombre = NULLIF(TRIM(p_nombre), '');
    SET p_rango_precio_id = NULLIF(TRIM(p_rango_precio_id), '');
    SET p_activo = NULLIF(TRIM(p_activo), '');
    IF p_tipo_servicio_id = 0 THEN SET p_tipo_servicio_id = NULL; END IF;

    SELECT
        s.SERVICIO_ID,
        s.TIPO_SERVICIO_ID,
        s.NOMBRE,
        s.DESCRIPCION,
        s.COSTO,
        s.ESTADO,
        s.ACTIVO,
        -- Datos del Tipo para el DTO
        ts.NOMBRE as NOMBRE_TIPO_SERVICIO,
        ts.DESCRIPCION as DESCRIPCION_TIPO_SERVICIO,
        ts.ACTIVO as ACTIVO_TIPO_SERVICIO
    FROM SERVICIOS AS s
    JOIN TIPOS_SERVICIO AS ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID
    WHERE
        (p_nombre IS NULL OR s.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_activo IS NULL OR s.ACTIVO = p_activo)
        -- Filtro de Pestaña
        AND (p_tipo_servicio_id IS NULL OR s.TIPO_SERVICIO_ID = p_tipo_servicio_id)
        AND (
            p_rango_precio_id IS NULL
            OR (p_rango_precio_id = '1' AND s.COSTO BETWEEN 0 AND 50)
            OR (p_rango_precio_id = '2' AND s.COSTO BETWEEN 51 AND 150)
            OR (p_rango_precio_id = '3' AND s.COSTO >= 151)
        )
    ORDER BY s.NOMBRE ASC;
END$$
DELIMITER ;