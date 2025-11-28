USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_buscar_veterinarios_avanzada;

DELIMITER $$
CREATE PROCEDURE sp_buscar_veterinarios_avanzada(
    IN p_especialidad VARCHAR(50),
    IN p_nombre VARCHAR(120),
    IN p_nro_documento VARCHAR(20),
    IN p_activo TINYINT -- Nuevo filtro: 1 (Activo), 0 (Inactivo), NULL (Todos)
)
BEGIN
    SELECT 
        v.VETERINARIO_ID,
        v.FECHA_DE_CONTRATACION,
        v.ESTADO as ESTADO_VET,
        v.ESPECIALIDAD,
        v.ACTIVO as ACTIVO_VET,
        -- Datos Persona
        p.PERSONA_ID,
        p.NOMBRE,
        p.DIRECCION,
        p.TELEFONO,
        p.SEXO,
        p.NRO_DOCUMENTO,
        p.RUC,
        p.TIPO_DOCUMENTO,
        p.ACTIVO as ACTIVO_PERS,
        -- Datos Usuario (IMPORTANTE para optimizar C#)
        u.USUARIO_ID,
        u.USERNAME,
        u.CORREO
    FROM VETERINARIOS v
    JOIN PERSONAS p ON v.PERSONA_ID = p.PERSONA_ID
    JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID
    WHERE 
        (p_especialidad IS NULL OR p_especialidad = '' OR v.ESPECIALIDAD LIKE CONCAT('%', p_especialidad, '%'))
        AND (p_nombre IS NULL OR p_nombre = '' OR p.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_nro_documento IS NULL OR p_nro_documento = '' OR p.NRO_DOCUMENTO LIKE CONCAT('%', p_nro_documento, '%'))
        -- Filtro de Estado
        AND (p_activo IS NULL OR v.ACTIVO = p_activo)
    ORDER BY p.NOMBRE ASC;
END$$
DELIMITER ;