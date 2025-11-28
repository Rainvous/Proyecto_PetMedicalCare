USE petmedicalcare;
GO

IF OBJECT_ID('sp_verificar_relacion_mascota', 'P') IS NOT NULL
    DROP PROCEDURE sp_verificar_relacion_mascota;
GO

CREATE PROCEDURE sp_verificar_relacion_mascota
    @p_mascota_id INT,
    @p_valorRetornado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    SET @p_valorRetornado = 0;

    SELECT TOP 1 @p_valorRetornado = 1
    FROM CITAS_ATENCION
    WHERE MASCOTA_ID = @p_mascota_id;
END
GO

