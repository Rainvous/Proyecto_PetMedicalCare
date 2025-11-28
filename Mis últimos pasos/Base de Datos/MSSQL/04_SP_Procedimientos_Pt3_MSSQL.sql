USE petmedicalcare;
GO

-- =============================================
-- 21. VERIFICAR RELACION SERVICIO
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_verificar_relacion_servicio
    @p_servicio_id INT,
    @p_valorRetornado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    -- Inicializamos en 0
    SET @p_valorRetornado = 0;

    -- 1. Buscar en Detalles de Citas
    SELECT TOP 1 @p_valorRetornado = 1
    FROM dbo.DETALLES_SERVICIO
    WHERE SERVICIO_ID = @p_servicio_id;

    -- 2. Si no se encontró, buscar en Ventas/Pagos
    IF @p_valorRetornado = 0
    BEGIN
        SELECT TOP 1 @p_valorRetornado = 1
        FROM dbo.DETALLES_DOCUMENTO_DE_PAGO
        WHERE SERVICIO_ID = @p_servicio_id;
    END
END
GO