USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_listar_citas_por_fecha_2;

DELIMITER $$
CREATE PROCEDURE sp_listar_citas_por_fecha_2(
    IN p_fecha VARCHAR(20),
    IN p_veterinario_id VARCHAR(20)
)
BEGIN
    DECLARE v_fecha_busqueda DATE;
    DECLARE v_vet_id_str VARCHAR(20);

    -- 1. Normalización de parámetros
    SET v_fecha_busqueda = COALESCE(NULLIF(TRIM(p_fecha), ''), CURDATE());
    SET v_vet_id_str = NULLIF(TRIM(p_veterinario_id), ''); 
    SET v_vet_id_str = NULLIF(v_vet_id_str, '0');        

    -- 2. Consulta Completa
    SELECT 
        -- === DATOS DE LA CITA ===
        c.CITA_ID,
        c.FECHA_REGISTRO,
        c.FECHA_HORA_INICIO,
        c.FECHA_HORA_FIN,
        c.ESTADO_CITA,
        c.PESO_MASCOTA,
        c.MONTO,
        c.OBSERVACION,
        c.ACTIVO AS CITA_ACTIVO,

        -- === DATOS DEL VETERINARIO ===
        v.VETERINARIO_ID,
        v.ESPECIALIDAD      AS VETERINARIO_ESPECIALIDAD,
        v.ESTADO            AS VETERINARIO_ESTADO_LABORAL,
        p_vet.NOMBRE        AS VETERINARIO_NOMBRE,
        p_vet.NRO_DOCUMENTO AS VETERINARIO_DNI,
        p_vet.TELEFONO      AS VETERINARIO_TELEFONO,
        u_vet.CORREO        AS VETERINARIO_CORREO,   -- Dato de tabla USUARIOS

        -- === DATOS DE LA MASCOTA ===
        m.MASCOTA_ID,
        m.NOMBRE            AS MASCOTA_NOMBRE,
        m.ESPECIE           AS MASCOTA_ESPECIE,
        m.RAZA              AS MASCOTA_RAZA,
        m.SEXO              AS MASCOTA_SEXO,
        m.COLOR             AS MASCOTA_COLOR,
        m.FECHA_DEFUNCION   AS MASCOTA_FECHA_DEFUNCION,

        -- === DATOS DEL CLIENTE (DUEÑO) ===
        p_cli.PERSONA_ID    AS CLIENTE_PERSONA_ID,
        p_cli.NOMBRE        AS CLIENTE_NOMBRE,
        p_cli.DIRECCION     AS CLIENTE_DIRECCION,
        p_cli.TELEFONO      AS CLIENTE_TELEFONO,
        p_cli.NRO_DOCUMENTO AS CLIENTE_NRO_DOCUMENTO,
        p_cli.TIPO_DOCUMENTO AS CLIENTE_TIPO_DOC,
        u_cli.CORREO        AS CLIENTE_CORREO,       -- Dato de tabla USUARIOS
        u_cli.USERNAME      AS CLIENTE_USERNAME

    FROM CITAS_ATENCION AS c

    -- 1. Relación con VETERINARIO -> PERSONA -> USUARIO
    JOIN VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID
    LEFT JOIN USUARIOS AS u_vet ON p_vet.USUARIO_ID = u_vet.USUARIO_ID

    -- 2. Relación con MASCOTA -> PERSONA (Cliente) -> USUARIO
    JOIN MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN PERSONAS AS p_cli ON m.PERSONA_ID = p_cli.PERSONA_ID
    LEFT JOIN USUARIOS AS u_cli ON p_cli.USUARIO_ID = u_cli.USUARIO_ID

    WHERE
        -- Filtro de Fecha
        DATE(c.FECHA_HORA_INICIO) = v_fecha_busqueda
        
        -- Filtro de Veterinario (Opcional)
        AND (v_vet_id_str IS NULL OR c.VETERINARIO_ID = v_vet_id_str)

    ORDER BY
        c.FECHA_HORA_INICIO ASC;
END$$
DELIMITER ;

-- Buscar citas de '2025-11-03' SOLO para 'Ana Pérez' (ID '1')
CALL sp_listar_citas_por_fecha_2('2025-11-03', '1'); 

-- Buscar citas de '2025-11-03' para TODOS los veterinarios
-- (Enviando string vacío '' en lugar de NULL)
CALL sp_listar_citas_por_fecha_2('2025-11-03', '');

-- Buscar citas de HOY para TODOS los veterinarios
CALL sp_listar_citas_por_fecha_2('', '');