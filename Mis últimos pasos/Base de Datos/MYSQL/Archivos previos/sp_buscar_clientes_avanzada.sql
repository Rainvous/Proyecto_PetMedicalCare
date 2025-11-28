USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_buscar_clientes_avanzada;

DELIMITER $$
CREATE PROCEDURE sp_buscar_clientes_avanzada(
    IN p_nombre VARCHAR(120),
    IN p_nro_documento VARCHAR(20),
    IN p_ruc VARCHAR(11),       -- Buscamos por RUC en lugar de Teléfono
    IN p_activo TINYINT         -- Filtro de Estado (1, 0, NULL)
)
BEGIN
    SELECT 
        p.PERSONA_ID,
        p.NOMBRE,
        p.DIRECCION,
        p.TELEFONO,
        p.SEXO,
        p.NRO_DOCUMENTO,
        p.RUC,
        p.TIPO_DOCUMENTO,
        p.ACTIVO as ACTIVO_PERS,
        -- Datos Usuario
        u.USUARIO_ID,
        u.USERNAME,
        u.CORREO
    FROM PERSONAS p
    INNER JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID
    WHERE 
        -- 1. Filtros de Búsqueda
        (p_nombre IS NULL OR p_nombre = '' OR p.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_nro_documento IS NULL OR p_nro_documento = '' OR p.NRO_DOCUMENTO LIKE CONCAT('%', p_nro_documento, '%'))
        AND (p_ruc IS NULL OR p_ruc = '' OR p.RUC LIKE CONCAT('%', p_ruc, '%'))
        AND (p_activo IS NULL OR p.ACTIVO = p_activo)
        
        [cite_start]-- 2. Lógica de "SOLO CLIENTES" (Excluir Staff) [cite: 118]
        AND NOT EXISTS (
            SELECT 1
            FROM ROLES_USUARIO AS RU
            JOIN ROLES AS R ON RU.ROL_ID = R.ROL_ID
            WHERE
                RU.USUARIO_ID = p.USUARIO_ID
                AND R.NOMBRE IN ('ADMIN', 'VET', 'RECEPCION')
        )
    ORDER BY p.NOMBRE ASC;
END$$
DELIMITER ;