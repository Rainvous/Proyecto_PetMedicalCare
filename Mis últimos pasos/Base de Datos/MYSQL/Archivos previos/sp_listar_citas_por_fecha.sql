USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_listar_citas_por_fecha;

DELIMITER $$
CREATE PROCEDURE sp_listar_citas_por_fecha(
    IN p_fecha VARCHAR(20),
    IN p_veterinario_id VARCHAR(20)
)
BEGIN
    DECLARE v_fecha_busqueda DATE;
    DECLARE v_vet_id_str VARCHAR(20);

    -- Normaliza la fecha (si es NULL o '', usa la fecha de hoy)
    SET v_fecha_busqueda = COALESCE(NULLIF(TRIM(p_fecha), ''), CURDATE());
    
    -- Normaliza el ID de Veterinario (si es NULL, '' o '0', se ignora)
    SET v_vet_id_str = NULLIF(TRIM(p_veterinario_id), ''); 
    SET v_vet_id_str = NULLIF(v_vet_id_str, '0');       

    -- MODIFICACIÓN: Devuelve todas las columnas de CITAS_ATENCION
    SELECT c.*
    FROM CITAS_ATENCION AS c
    
    -- Se mantienen los JOINs para asegurar la integridad de los datos
    JOIN VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID
    JOIN MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN PERSONAS AS p_cli ON m.PERSONA_ID = p_cli.PERSONA_ID
    
    WHERE
        -- Filtro 1: Fecha
        DATE(c.FECHA_HORA_INICIO) = v_fecha_busqueda
        
        -- Filtro 2: Veterinario
        AND (v_vet_id_str IS NULL OR c.VETERINARIO_ID = v_vet_id_str)

    ORDER BY
        c.FECHA_HORA_INICIO ASC;
END$$
DELIMITER ;
SELECT * FROM petmedicalcare.CITAS_ATENCION;
-- Buscar citas de '2025-11-03' SOLO para 'Ana Pérez' (ID '1')
CALL sp_listar_citas_por_fecha('2025-11-03', '1'); 

-- Buscar citas de '2025-11-03' para TODOS los veterinarios
-- (Enviando string vacío '' en lugar de NULL)
CALL sp_listar_citas_por_fecha('2025-11-03', '');

-- Buscar citas de HOY para TODOS los veterinarios
CALL sp_listar_citas_por_fecha('', '');