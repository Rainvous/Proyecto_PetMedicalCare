USE petmedicalcare;
GO

IF OBJECT_ID('sp_verificar_relacion_persona', 'P') IS NOT NULL
    DROP PROCEDURE sp_verificar_relacion_persona;
GO

CREATE PROCEDURE sp_verificar_relacion_persona
    @p_persona_id INT,
    @p_valorRetornado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    SET @p_valorRetornado = 0;

    -- 1. Mascotas
    SELECT TOP 1 @p_valorRetornado = 1
    FROM MASCOTAS
    WHERE PERSONA_ID = @p_persona_id;

    -- 2. Veterinarios
    IF @p_valorRetornado = 0
    BEGIN
        SELECT TOP 1 @p_valorRetornado = 1
        FROM VETERINARIOS
        WHERE PERSONA_ID = @p_persona_id;
    END

    -- 3. Documentos de Pago
    IF @p_valorRetornado = 0
    BEGIN
        SELECT TOP 1 @p_valorRetornado = 1
        FROM DOCUMENTOS_DE_PAGO
        WHERE PERSONA_ID = @p_persona_id;
    END
END
GO