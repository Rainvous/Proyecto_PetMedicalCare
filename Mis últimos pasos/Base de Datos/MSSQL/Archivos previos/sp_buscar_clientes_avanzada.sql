USE petmedicalcare;
GO

IF OBJECT_ID('sp_buscar_clientes_avanzada', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_clientes_avanzada;
GO

CREATE PROCEDURE sp_buscar_clientes_avanzada
    @p_nombre VARCHAR(120),
    @p_nro_documento VARCHAR(20),
    @p_ruc VARCHAR(11),
    @p_activo TINYINT
AS
BEGIN
    SET NOCOUNT ON;

    -- Normalización
    SET @p_nombre = NULLIF(TRIM(@p_nombre), '');
    SET @p_nro_documento = NULLIF(TRIM(@p_nro_documento), '');
    SET @p_ruc = NULLIF(TRIM(@p_ruc), '');

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
        (@p_nombre IS NULL OR p.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_nro_documento IS NULL OR p.NRO_DOCUMENTO LIKE CONCAT('%', @p_nro_documento, '%'))
        AND (@p_ruc IS NULL OR p.RUC LIKE CONCAT('%', @p_ruc, '%'))
        AND (@p_activo IS NULL OR p.ACTIVO = @p_activo)
        
        -- 2. Lógica de "SOLO CLIENTES" (Excluir Staff)
        AND NOT EXISTS (
            SELECT 1
            FROM ROLES_USUARIO AS RU
            JOIN ROLES AS R ON RU.ROL_ID = R.ROL_ID
            WHERE
                RU.USUARIO_ID = p.USUARIO_ID
                AND R.NOMBRE IN ('ADMIN', 'VET', 'RECEPCION')
        )
    ORDER BY p.NOMBRE ASC;
END
GO