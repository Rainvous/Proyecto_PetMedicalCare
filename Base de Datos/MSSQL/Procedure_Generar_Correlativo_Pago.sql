USE petmedicalcare;
GO

IF OBJECT_ID('sp_generar_correlativo_pago', 'P') IS NOT NULL
    DROP PROCEDURE sp_generar_correlativo_pago;
GO

CREATE PROCEDURE sp_generar_correlativo_pago
    @p_tipo_codigo CHAR(1),        -- 'B' o 'F'
    @p_serie_generada VARCHAR(10) OUTPUT,
    @p_numero_generado VARCHAR(12) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @v_tipo_documento VARCHAR(50);
    DECLARE @v_ultima_serie VARCHAR(10);
    DECLARE @v_ultimo_numero VARCHAR(12);
    DECLARE @v_num_entero BIGINT;
    DECLARE @v_serie_entero INT;

    -- 1. Mapear el código
    IF @p_tipo_codigo = 'B' 
        SET @v_tipo_documento = 'BOLETA';
    ELSE IF @p_tipo_codigo = 'F' 
        SET @v_tipo_documento = 'FACTURA';
    ELSE
    BEGIN
        SET @p_serie_generada = NULL;
        SET @p_numero_generado = NULL;
        RETURN; -- Sale del SP
    END

    -- 2. Obtener el ÚLTIMO documento (Top 1 en lugar de Limit)
    SELECT TOP 1 
        @v_ultima_serie = SERIE, 
        @v_ultimo_numero = NUMERO
    FROM DOCUMENTOS_DE_PAGO
    WHERE TIPO_DOCUMENTO = @v_tipo_documento
      AND SERIE LIKE CONCAT(@p_tipo_codigo, '%') 
    ORDER BY SERIE DESC, NUMERO DESC;

    -- 3. Lógica de Generación
    IF @v_ultima_serie IS NULL
    BEGIN
        -- CASO 1: NO EXISTEN DATOS
        SET @p_serie_generada = CONCAT(@p_tipo_codigo, '001');
        SET @p_numero_generado = '00000001';
    END
    ELSE
    BEGIN
        -- CASO 2: YA EXISTEN
        SET @v_num_entero = CAST(@v_ultimo_numero AS BIGINT) + 1;

        IF @v_num_entero > 99999999
        BEGIN
            -- CASO 3: DESBORDAMIENTO
            SET @v_serie_entero = CAST(SUBSTRING(@v_ultima_serie, 2, LEN(@v_ultima_serie)) AS INT) + 1;
            
            -- Simulación de LPAD en SQL Server: RIGHT('000' + Var, 3)
            SET @p_serie_generada = CONCAT(@p_tipo_codigo, RIGHT('000' + CAST(@v_serie_entero AS VARCHAR(10)), 3));
            SET @p_numero_generado = '00000001';
        END
        ELSE
        BEGIN
            -- CASO NORMAL
            SET @p_serie_generada = @v_ultima_serie;
            -- Simulación de LPAD para 8 dígitos
            SET @p_numero_generado = RIGHT('00000000' + CAST(@v_num_entero AS VARCHAR(12)), 8);
        END
    END
END
GO



DECLARE @serie VARCHAR(10);
DECLARE @numero VARCHAR(12);

EXEC sp_generar_correlativo_pago 'F', @serie OUTPUT, @numero OUTPUT;

SELECT @serie as Serie_Generada, @numero as Numero_Generado;