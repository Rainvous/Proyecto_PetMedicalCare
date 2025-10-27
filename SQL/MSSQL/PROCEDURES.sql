

USE petmedicalcare;
GO

-- Crea o reemplaza el procedimiento en esta BD
CREATE OR ALTER PROCEDURE dbo.sp_buscar_personas_avanzada
    @p_nombre        NVARCHAR(100) = NULL,
    @p_nro_documento INT           = NULL,
    @p_ruc           INT           = NULL,
    @p_telefono      NVARCHAR(100) = NULL
AS
BEGIN
    SET NOCOUNT ON;

    -- Limpieza básica (TRIM y NULL si vacío)
    SET @p_nombre   = NULLIF(LTRIM(RTRIM(@p_nombre)), N'');
    SET @p_telefono = NULLIF(LTRIM(RTRIM(@p_telefono)), N'');

    DECLARE @v_doc_pat NVARCHAR(32);
    DECLARE @v_ruc_pat NVARCHAR(32);

    IF @p_nro_documento IS NULL OR @p_nro_documento = 0
        SET @v_doc_pat = NULL;
    ELSE
        SET @v_doc_pat = N'%' + CONVERT(NVARCHAR(20), @p_nro_documento) + N'%';

    IF @p_ruc IS NULL OR @p_ruc = 0
        SET @v_ruc_pat = NULL;
    ELSE
        SET @v_ruc_pat = N'%' + CONVERT(NVARCHAR(20), @p_ruc) + N'%';

    SELECT
        PERSONA_ID,
        NOMBRE,
        DIRECCION,
        TELEFONO,
        SEXO,
        ACTIVO,
        TIPO_DOCUMENTO,
        NRO_DOCUMENTO,
        RUC,
        USUARIO_ID
    FROM dbo.PERSONAS
    WHERE
        (@p_nombre IS NULL OR NOMBRE LIKE N'%' + @p_nombre + N'%')
        AND (@v_doc_pat IS NULL OR NRO_DOCUMENTO LIKE @v_doc_pat)
        AND (@v_ruc_pat IS NULL OR RUC LIKE @v_ruc_pat)
        AND (@p_telefono IS NULL OR TELEFONO LIKE N'%' + @p_telefono + N'%')
    ORDER BY NOMBRE ASC, PERSONA_ID ASC;
    --OJO: Resulta que hay errores en las tablas no estan iguales a como lo hicimos en mysql
END
GO


EXEC dbo.sp_buscar_personas_avanzada @p_nombre = N'ana';
EXEC dbo.sp_buscar_personas_avanzada @p_nro_documento = N'00123';
EXEC dbo.sp_buscar_personas_avanzada @p_ruc = N'2012345', @p_telefono = N'987';
