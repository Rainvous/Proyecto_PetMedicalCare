USE petmedicalcare;
GO

IF OBJECT_ID('sp_verificar_relacion_servicio', 'P') IS NOT NULL
    DROP PROCEDURE sp_verificar_relacion_servicio;
GO

CREATE PROCEDURE sp_verificar_relacion_servicio
    @p_servicio_id INT,
    @p_valorRetornado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Establece el valor por defecto (0 = Sin relación)
    SET @p_valorRetornado = 0;

    -- 2. Busca en la primera tabla (DETALLES_SERVICIO)
    SELECT TOP 1 @p_valorRetornado = 1
    FROM DETALLES_SERVICIO
    WHERE SERVICIO_ID = @p_servicio_id;

    -- 3. Si NO lo encontró, busca en la segunda tabla (DETALLES_DOCUMENTO_DE_PAGO)
    IF @p_valorRetornado = 0
    BEGIN
        SELECT TOP 1 @p_valorRetornado = 1
        FROM DETALLES_DOCUMENTO_DE_PAGO
        WHERE SERVICIO_ID = @p_servicio_id;
    END
END
GO

DECLARE @res INT;
EXEC sp_verificar_relacion_servicio 1, @res OUTPUT; -- ID 1 suele existir
SELECT @res AS Tiene_Relacion_Servicio;