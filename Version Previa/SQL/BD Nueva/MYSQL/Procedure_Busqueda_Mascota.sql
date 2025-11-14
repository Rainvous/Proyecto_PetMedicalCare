USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_buscar_mascotas_avanzada;

DELIMITER $$
CREATE PROCEDURE sp_buscar_mascotas_avanzada(
    IN p_nombre_mascota VARCHAR(80),
    IN p_raza VARCHAR(60),
    IN p_especie VARCHAR(40),
    IN p_nombre_propietario VARCHAR(120)
)
BEGIN
    -- 1. Normalizar entradas (convertir '' en NULL)
    SET p_nombre_mascota = NULLIF(TRIM(p_nombre_mascota), '');
    SET p_raza = NULLIF(TRIM(p_raza), '');
    SET p_especie = NULLIF(TRIM(p_especie), '');
    SET p_nombre_propietario = NULLIF(TRIM(p_nombre_propietario), '');

    -- 2. Búsqueda
    SELECT
        m.MASCOTA_ID,
        m.PERSONA_ID,
        m.NOMBRE ,
        m.ESPECIE,
        m.SEXO,
		m.RAZA,
        m.COLOR,
        m.FECHA_DEFUNCION,
        m.ACTIVO
        
    FROM MASCOTAS AS m
    -- Unimos con la tabla PERSONAS para obtener el nombre del dueño
    JOIN PERSONAS AS p ON m.PERSONA_ID = p.PERSONA_ID
    
    WHERE
        -- Filtro 1: Nombre de la Mascota
        (p_nombre_mascota IS NULL OR m.NOMBRE LIKE CONCAT('%', p_nombre_mascota, '%'))
        
        -- Filtro 2: Raza
        AND (p_raza IS NULL OR m.RAZA LIKE CONCAT('%', p_raza, '%'))
        
        -- Filtro 3: Especie
        AND (p_especie IS NULL OR m.ESPECIE LIKE CONCAT('%', p_especie, '%'))
        
        -- Filtro 4: Nombre del Propietario (usa el alias 'p' de la tabla Personas)
        AND (p_nombre_propietario IS NULL OR p.NOMBRE LIKE CONCAT('%', p_nombre_propietario, '%'))
        
    ORDER BY
        p.NOMBRE ASC, m.NOMBRE ASC; -- Ordena por dueño, y luego por mascota
END$$
DELIMITER ;

-- Caso 1: Buscar todo (sin filtros)
CALL sp_buscar_mascotas_avanzada(NULL, NULL, NULL, NULL);

-- Caso 2: Buscar por Especie 'Gato'
CALL sp_buscar_mascotas_avanzada(NULL, NULL, 'Gato', NULL);

-- Caso 3: Buscar por Nombre de Propietario 'Luisa'
CALL sp_buscar_mascotas_avanzada(NULL, NULL, NULL, 'Luisa Romero');

-- Caso 4: Búsqueda combinada (Propietario 'Carlos' Y Especie 'Perro')
CALL sp_buscar_mascotas_avanzada(NULL, NULL, 'Perro', 'Carlos Torres');

-- Caso 5: Búsqueda combinada (Raza 'Mestizo')
CALL sp_buscar_mascotas_avanzada(NULL, 'Mestizo', NULL, NULL);