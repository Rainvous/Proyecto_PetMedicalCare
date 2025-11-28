USE petmedicalcare;
GO

IF OBJECT_ID('sp_verificar_relacion_producto', 'P') IS NOT NULL
    DROP PROCEDURE sp_verificar_relacion_producto;
GO

CREATE PROCEDURE sp_verificar_relacion_producto
    @p_producto_id INT,
    @p_valorRetornado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Establece el valor por defecto
    SET @p_valorRetornado = 0;

    -- 2. Busca si existe al menos 1 registro
    -- Usamos TOP 1 y asignación de variable
    SELECT TOP 1 @p_valorRetornado = 1
    FROM DETALLES_DOCUMENTO_DE_PAGO
    WHERE PRODUCTO_ID = @p_producto_id;
END
GO


DECLARE @resultado INT;
DECLARE @id_prod INT = 1; -- Asumiendo que ID 1 existe y tiene ventas

EXEC sp_verificar_relacion_producto @id_prod, @resultado OUTPUT;
SELECT @resultado as Tiene_Relacion;