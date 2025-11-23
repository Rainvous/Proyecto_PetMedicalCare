USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_buscar_mascotas_avanzada;

DELIMITER $$
CREATE PROCEDURE sp_buscar_mascotas_avanzada(
    IN p_nombre VARCHAR(100),
    IN p_especie VARCHAR(40),
    IN p_nombre_duenio VARCHAR(120),
    IN p_activo TINYINT -- 1: Activo, 0: Inactivo, NULL: Todos
)
BEGIN
    SELECT 
        m.MASCOTA_ID,
        m.NOMBRE,
        m.ESPECIE,
        m.SEXO,
        m.RAZA, -- Se selecciona para mostrarla, pero ya no se filtra por ella
        m.COLOR,
        m.FECHA_DEFUNCION,
        m.ACTIVO,
        -- Datos del Dueño (Persona)
        p.PERSONA_ID,
        p.NOMBRE as NOMBRE_DUENIO,
        p.TELEFONO as TELEFONO_DUENIO
    FROM MASCOTAS m
    INNER JOIN PERSONAS p ON m.PERSONA_ID = p.PERSONA_ID
    WHERE 
        (p_nombre IS NULL OR p_nombre = '' OR m.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_especie IS NULL OR p_especie = '' OR m.ESPECIE = p_especie)
        AND (p_nombre_duenio IS NULL OR p_nombre_duenio = '' OR p.NOMBRE LIKE CONCAT('%', p_nombre_duenio, '%'))
        -- Nuevo Filtro Estado
        AND (p_activo IS NULL OR m.ACTIVO = p_activo)
    ORDER BY m.NOMBRE ASC;
END$$
DELIMITER ;