USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_buscar_servicios_avanzada;

DELIMITER $$
CREATE PROCEDURE sp_buscar_servicios_avanzada(
    IN p_nombre VARCHAR(100),
    IN p_rango_precio_id VARCHAR(10), -- 1=(0-50), 2=(51-150), 3=(151+), NULL/''=Sin filtro
    IN p_activo VARCHAR(10)          -- '1'=Activo, '0'=Inactivo, NULL/''=Ambos
)
BEGIN
    -- 1. Normalizar entradas (convertir '' en NULL para todos los strings)
    SET p_nombre = NULLIF(TRIM(p_nombre), '');
    SET p_rango_precio_id = NULLIF(TRIM(p_rango_precio_id), '');
    SET p_activo = NULLIF(TRIM(p_activo), '');

    -- 2. Búsqueda
    SELECT
		s.SERVICIO_ID,
        s.TIPO_SERVICIO_ID,
        s.NOMBRE,
        s.DESCRIPCION,
        s.COSTO,
        s.ESTADO,
        s.ACTIVO,
        s.USUARIO_CREADOR,
        s.FECHA_CREACION,
        s.USUARIO_MODIFICADOR,
        s.FECHA_MODIFICACION
    FROM SERVICIOS AS s
    JOIN TIPOS_SERVICIO AS ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID
    WHERE
        -- Filtro 1: Nombre (ignora si es NULL)
        (p_nombre IS NULL OR s.NOMBRE LIKE CONCAT('%', p_nombre, '%'))

        -- Filtro 2: Activo (ignora si es NULL)
        -- MySQL convierte automáticamente s.ACTIVO (TINYINT)
        -- para compararlo con el string p_activo ('1' o '0')
        AND (p_activo IS NULL OR s.ACTIVO = p_activo)

        -- Filtro 3: Rango de Precio (ignora si es NULL)
        -- Comparamos con strings '1', '2', '3'
        AND (
            p_rango_precio_id IS NULL
            OR (p_rango_precio_id = '1' AND s.COSTO BETWEEN 0 AND 50)
            OR (p_rango_precio_id = '2' AND s.COSTO BETWEEN 51 AND 150)
            OR (p_rango_precio_id = '3' AND s.COSTO >= 151)
        )
    ORDER BY
        s.COSTO ASC, s.NOMBRE ASC;
END$$
DELIMITER ;

-- Caso 1: Buscar todo (sin filtros)
CALL sp_buscar_servicios_avanzada(NULL, NULL, NULL);
-- O TAMBIÉN:
CALL sp_buscar_servicios_avanzada('', '', '');

-- Caso 2: Buscar solo por Rango 1 (0 - 50)
CALL sp_buscar_servicios_avanzada(NULL, '1', NULL);

-- Caso 3: Buscar solo por Rango 2 (51 - 150)
CALL sp_buscar_servicios_avanzada(NULL, '2', NULL);

-- Caso 4: Buscar solo por Rango 3 (151 a más)
CALL sp_buscar_servicios_avanzada(NULL, '3', NULL);

-- Caso 5: Búsqueda combinada (Nombre 'Consulta' y Rango 2)
CALL sp_buscar_servicios_avanzada('Consulta', '2', NULL);

-- Caso 6: Búsqueda solo Activos (p_activo = '1')
CALL sp_buscar_servicios_avanzada(NULL, NULL, '1');