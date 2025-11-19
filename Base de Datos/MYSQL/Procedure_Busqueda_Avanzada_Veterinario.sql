USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_buscar_veterinarios_avanzada;

DELIMITER $$
CREATE PROCEDURE sp_buscar_veterinarios_avanzada(
    IN p_especialidad VARCHAR(50),
    IN p_nombre VARCHAR(120),
    IN p_telefono VARCHAR(20),
    IN p_nro_documento VARCHAR(20)
)
BEGIN
    -- 1. Normalizar entradas
    SET p_especialidad  = NULLIF(TRIM(p_especialidad), '');
    SET p_nombre        = NULLIF(TRIM(p_nombre), '');
    SET p_telefono      = NULLIF(TRIM(p_telefono), '');
    SET p_nro_documento = NULLIF(TRIM(p_nro_documento), '');

    -- 2. Búsqueda
    SELECT 
        v.VETERINARIO_ID,
        v.PERSONA_ID,
        v.FECHA_DE_CONTRATACION,
        v.ESTADO,
        v.ESPECIALIDAD,
        v.ACTIVO
        
    FROM VETERINARIOS AS v
    JOIN PERSONAS AS p ON v.PERSONA_ID = p.PERSONA_ID
    WHERE 
        (p_especialidad IS NULL OR v.ESPECIALIDAD LIKE CONCAT('%', p_especialidad, '%'))
        AND (p_nombre IS NULL OR p.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_telefono IS NULL OR p.TELEFONO LIKE CONCAT('%', p_telefono, '%'))
        AND (p_nro_documento IS NULL OR p.NRO_DOCUMENTO LIKE CONCAT('%', p_nro_documento, '%'))
        
    ORDER BY p.NOMBRE ASC;
END$$
DELIMITER ;


-- Caso 1: Buscar todo (sin filtros)
CALL sp_buscar_veterinarios_avanzada('', '', '', '');

-- Caso 2: Buscar por Especialidad (ej. 'Cirugía')
CALL sp_buscar_veterinarios_avanzada('Cirugía', NULL, NULL, NULL);

-- Caso 3: Buscar por Nombre (ej. 'Juan')
CALL sp_buscar_veterinarios_avanzada(NULL, 'Juan', NULL, NULL);

-- Caso 4: Buscar por DNI parcial (ej. '456')
CALL sp_buscar_veterinarios_avanzada(NULL, NULL, NULL, '456');

-- Caso 5: Búsqueda combinada (Especialidad 'Dermatología' y Nombre 'García')
CALL sp_buscar_veterinarios_avanzada('Dermatología', 'García', NULL, NULL);


