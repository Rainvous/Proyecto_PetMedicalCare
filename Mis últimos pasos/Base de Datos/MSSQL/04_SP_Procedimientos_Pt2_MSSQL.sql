USE petmedicalcare;
GO

-- =============================================
-- 11. VERIFICAR HORARIO LABORAL EXISTENTE
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_verificar_horario_laboral_existente
    @p_fecha DATE,
    @p_veterinario_id INT,
    @p_horario_id INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    SET @p_horario_id = 0;

    SELECT TOP 1 @p_horario_id = HORARIO_LABORAL_ID 
    FROM dbo.HORARIOS_LABORALES
    WHERE VETERINARIO_ID = @p_veterinario_id 
      AND FECHA = @p_fecha;
END
GO

-- =============================================
-- 12. GUARDAR HORARIO LABORAL (UPSERT)
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_guardar_horario_laboral
    @p_veterinario_id INT,
    @p_fecha DATE,
    @p_hora_inicio DATETIME,
    @p_hora_fin DATETIME,
    @p_activo BIT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @v_existe INT;

    SELECT @v_existe = COUNT(*) 
    FROM dbo.HORARIOS_LABORALES
    WHERE VETERINARIO_ID = @p_veterinario_id 
      AND FECHA = @p_fecha;

    IF @v_existe > 0
    BEGIN
        UPDATE dbo.HORARIOS_LABORALES
        SET 
            HORA_INICIO = @p_hora_inicio,
            HORA_FIN = @p_hora_fin,
            ESTADO = 'DISPONIBLE',
            ACTIVO = @p_activo
        WHERE VETERINARIO_ID = @p_veterinario_id AND FECHA = @p_fecha;
    END
    ELSE
    BEGIN
        INSERT INTO dbo.HORARIOS_LABORALES 
        (VETERINARIO_ID, FECHA, ESTADO, HORA_INICIO, HORA_FIN, ACTIVO)
        VALUES 
        (@p_veterinario_id, @p_fecha, 'DISPONIBLE', @p_hora_inicio, @p_hora_fin, @p_activo);
    END
END
GO

-- =============================================
-- 13. GENERAR HORARIO DISPONIBLE (Tablas Temporales)
-- =============================================
USE petmedicalcare;
GO

IF OBJECT_ID('sp_generar_horario_disponible', 'P') IS NOT NULL
    DROP PROCEDURE sp_generar_horario_disponible;
GO

CREATE PROCEDURE sp_generar_horario_disponible
    @p_veterinario_id INT,
    @p_fecha DATE
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Declarar variables
    DECLARE @v_hora_inicio_real DATETIME; 
    DECLARE @v_hora_fin_real DATETIME;    
    
    DECLARE @v_slot_actual DATETIME;
    DECLARE @v_fin_dia_laboral DATETIME;
    
    -- Variables auxiliares para conversión segura de texto
    DECLARE @fechaStr VARCHAR(20);
    DECLARE @horaInicioStr VARCHAR(20);
    DECLARE @horaFinStr VARCHAR(20);
    
    -- --- CONFIGURACIÓN DE HORARIO BASE (GRILLA VISUAL) ---
    DECLARE @v_default_start TIME = '08:00:00'; 
    DECLARE @v_default_end TIME = '17:00:00';   
    -- ---------------------------------------------------

    -- 2. Limpieza de Tablas Temporales
    IF OBJECT_ID('tempdb..#TempSlotsGenerados') IS NOT NULL DROP TABLE #TempSlotsGenerados;
    CREATE TABLE #TempSlotsGenerados ( SlotStart DATETIME PRIMARY KEY );

    IF OBJECT_ID('tempdb..#TempSlotsReservados') IS NOT NULL DROP TABLE #TempSlotsReservados;
    CREATE TABLE #TempSlotsReservados ( SlotStart DATETIME PRIMARY KEY );

    -- 3. Generar TODOS los slots del día (CORREGIDO)
    -- Usamos CONVERT con estilo para evitar el error de "Conversion failed"
    -- Estilo 23 = yyyy-mm-dd
    -- Estilo 108 = hh:mm:ss
    SET @fechaStr = CONVERT(VARCHAR(20), @p_fecha, 23); 
    SET @horaInicioStr = CONVERT(VARCHAR(20), @v_default_start, 108);
    SET @horaFinStr = CONVERT(VARCHAR(20), @v_default_end, 108);

    -- Concatenamos de forma segura: '2025-11-03' + ' ' + '08:00:00'
    SET @v_slot_actual = CAST(@fechaStr + ' ' + @horaInicioStr AS DATETIME);
    SET @v_fin_dia_laboral = CAST(@fechaStr + ' ' + @horaFinStr AS DATETIME);

    WHILE @v_slot_actual < @v_fin_dia_laboral
    BEGIN
        INSERT INTO #TempSlotsGenerados (SlotStart) VALUES (@v_slot_actual);
        SET @v_slot_actual = DATEADD(HOUR, 1, @v_slot_actual);
    END

    -- 4. Obtener el horario laboral REAL del veterinario
    SELECT TOP 1 
        @v_hora_inicio_real = HORA_INICIO, 
        @v_hora_fin_real = HORA_FIN
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = @p_veterinario_id
      AND FECHA = @p_fecha
      AND ESTADO = 'DISPONIBLE';

    -- 5. Obtener las citas ya reservadas
    INSERT INTO #TempSlotsReservados (SlotStart)
    SELECT FECHA_HORA_INICIO
    FROM CITAS_ATENCION
    WHERE VETERINARIO_ID = @p_veterinario_id
      AND CAST(FECHA_HORA_INICIO AS DATE) = @p_fecha
      AND ESTADO_CITA != 'CANCELADA';

    -- 6. Resultado Final
    SELECT
        ts.SlotStart AS Hora,
        CASE 
            WHEN 
                @v_hora_inicio_real IS NOT NULL 
                AND ts.SlotStart >= @v_hora_inicio_real 
                AND ts.SlotStart < @v_hora_fin_real
                AND tb.SlotStart IS NULL 
            THEN 1 
            ELSE 0 
        END AS Disponible
    FROM #TempSlotsGenerados AS ts
    LEFT JOIN #TempSlotsReservados AS tb ON ts.SlotStart = tb.SlotStart
    ORDER BY ts.SlotStart;

    -- 7. Limpieza
    DROP TABLE #TempSlotsGenerados;
    DROP TABLE #TempSlotsReservados;
END
GO

-- =============================================
-- 14. LISTAR CITAS POR FECHA
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_listar_citas_por_fecha
    @p_fecha VARCHAR(20) = NULL,
    @p_veterinario_id VARCHAR(20) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @v_fecha_busqueda DATE;
    DECLARE @v_vet_id_str VARCHAR(20);

    SET @v_fecha_busqueda = COALESCE(NULLIF(LTRIM(RTRIM(@p_fecha)), ''), GETDATE());
    SET @v_vet_id_str = NULLIF(LTRIM(RTRIM(@p_veterinario_id)), ''); 
    IF @v_vet_id_str = '0' SET @v_vet_id_str = NULL;       

    SELECT c.*
    FROM dbo.CITAS_ATENCION AS c
    JOIN dbo.VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN dbo.PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID
    JOIN dbo.MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN dbo.PERSONAS AS p_cli ON m.PERSONA_ID = p_cli.PERSONA_ID
    WHERE
        CAST(c.FECHA_HORA_INICIO AS DATE) = @v_fecha_busqueda
        AND (@v_vet_id_str IS NULL OR CAST(c.VETERINARIO_ID AS VARCHAR) = @v_vet_id_str)
    ORDER BY c.FECHA_HORA_INICIO ASC;
END
GO

-- =============================================
-- 15. LISTAR CITAS POR FECHA (Detallada V2)
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_listar_citas_por_fecha_2
    @p_fecha VARCHAR(20) = NULL,
    @p_veterinario_id VARCHAR(20) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @v_fecha_busqueda DATE;
    DECLARE @v_vet_id_str VARCHAR(20);

    SET @v_fecha_busqueda = COALESCE(NULLIF(LTRIM(RTRIM(@p_fecha)), ''), GETDATE());
    SET @v_vet_id_str = NULLIF(LTRIM(RTRIM(@p_veterinario_id)), ''); 
    IF @v_vet_id_str = '0' SET @v_vet_id_str = NULL;        

    SELECT 
        c.CITA_ID, c.FECHA_REGISTRO, c.FECHA_HORA_INICIO, c.FECHA_HORA_FIN, c.ESTADO_CITA, c.PESO_MASCOTA, c.MONTO, c.OBSERVACION, c.ACTIVO AS CITA_ACTIVO,
        v.VETERINARIO_ID, v.ESPECIALIDAD AS VETERINARIO_ESPECIALIDAD, v.ESTADO AS VETERINARIO_ESTADO_LABORAL,
        p_vet.NOMBRE AS VETERINARIO_NOMBRE, p_vet.NRO_DOCUMENTO AS VETERINARIO_DNI, p_vet.TELEFONO AS VETERINARIO_TELEFONO,
        u_vet.CORREO AS VETERINARIO_CORREO,
        m.MASCOTA_ID, m.NOMBRE AS MASCOTA_NOMBRE, m.ESPECIE AS MASCOTA_ESPECIE, m.RAZA AS MASCOTA_RAZA, m.SEXO AS MASCOTA_SEXO, m.COLOR AS MASCOTA_COLOR, m.FECHA_DEFUNCION AS MASCOTA_FECHA_DEFUNCION,
        p_cli.PERSONA_ID AS CLIENTE_PERSONA_ID, p_cli.NOMBRE AS CLIENTE_NOMBRE, p_cli.DIRECCION AS CLIENTE_DIRECCION, p_cli.TELEFONO AS CLIENTE_TELEFONO, p_cli.NRO_DOCUMENTO AS CLIENTE_NRO_DOCUMENTO, p_cli.TIPO_DOCUMENTO AS CLIENTE_TIPO_DOC,
        u_cli.CORREO AS CLIENTE_CORREO, u_cli.USERNAME AS CLIENTE_USERNAME
    FROM dbo.CITAS_ATENCION AS c
    JOIN dbo.VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN dbo.PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID
    LEFT JOIN dbo.USUARIOS AS u_vet ON p_vet.USUARIO_ID = u_vet.USUARIO_ID
    JOIN dbo.MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN dbo.PERSONAS AS p_cli ON m.PERSONA_ID = p_cli.PERSONA_ID
    LEFT JOIN dbo.USUARIOS AS u_cli ON p_cli.USUARIO_ID = u_cli.USUARIO_ID
    WHERE
        CAST(c.FECHA_HORA_INICIO AS DATE) = @v_fecha_busqueda
        AND (@v_vet_id_str IS NULL OR CAST(c.VETERINARIO_ID AS VARCHAR) = @v_vet_id_str)
    ORDER BY c.FECHA_HORA_INICIO ASC;
END
GO

-- =============================================
-- 16. LISTAR CITAS POR MASCOTA Y FECHA
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_listar_citas_por_mascota_y_fecha
    @p_mascota_id INT,
    @p_fecha VARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @v_fecha_filtro DATE;
    SET @v_fecha_filtro = CAST(@p_fecha AS DATE);

    SELECT 
        c.CITA_ID, c.VETERINARIO_ID, c.MASCOTA_ID, c.FECHA_REGISTRO, c.FECHA_HORA_INICIO, c.FECHA_HORA_FIN, c.PESO_MASCOTA, c.MONTO, c.ESTADO_CITA, c.OBSERVACION, c.ACTIVO
    FROM dbo.CITAS_ATENCION AS c
    JOIN dbo.MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN dbo.VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN dbo.PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID
    WHERE 
        c.MASCOTA_ID = @p_mascota_id
        AND CAST(c.FECHA_HORA_INICIO AS DATE) = @v_fecha_filtro
    ORDER BY c.FECHA_HORA_INICIO ASC;
END
GO

-- =============================================
-- 17. LISTAR RECETAS POR MASCOTA Y FECHA
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_listar_recetas_por_mascota_y_fecha
    @p_mascota_id INT,
    @p_fecha VARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @v_fecha_filtro DATE;
    SET @v_fecha_filtro = CAST(@p_fecha AS DATE);

    SELECT 
        r.RECETA_MEDICA_ID, r.CITA_ID, r.FECHA_EMISION, r.VIGENCIA_HASTA, r.DIAGNOSTICO, r.OBSERVACIONES, r.ACTIVO
    FROM dbo.RECETAS_MEDICAS AS r
    JOIN dbo.CITAS_ATENCION AS c ON r.CITA_ID = c.CITA_ID
    WHERE 
        c.MASCOTA_ID = @p_mascota_id
        AND r.FECHA_EMISION = @v_fecha_filtro
    ORDER BY r.FECHA_EMISION DESC;
END
GO

-- =============================================
-- 18. VERIFICAR RELACION MASCOTA
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_verificar_relacion_mascota
    @p_mascota_id INT,
    @p_valorRetornado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    SET @p_valorRetornado = 0;

    SELECT TOP 1 @p_valorRetornado = 1
    FROM dbo.CITAS_ATENCION
    WHERE MASCOTA_ID = @p_mascota_id;
END
GO

-- =============================================
-- 19. VERIFICAR RELACION PERSONA
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_verificar_relacion_persona
    @p_persona_id INT,
    @p_valorRetornado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    SET @p_valorRetornado = 0;

    SELECT TOP 1 @p_valorRetornado = 1 FROM dbo.MASCOTAS WHERE PERSONA_ID = @p_persona_id;

    IF @p_valorRetornado = 0 
    BEGIN
        SELECT TOP 1 @p_valorRetornado = 1 FROM dbo.VETERINARIOS WHERE PERSONA_ID = @p_persona_id;
    END

    IF @p_valorRetornado = 0 
    BEGIN
        SELECT TOP 1 @p_valorRetornado = 1 FROM dbo.DOCUMENTOS_DE_PAGO WHERE PERSONA_ID = @p_persona_id;
    END
END
GO

-- =============================================
-- 20. VERIFICAR RELACION PRODUCTO
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_verificar_relacion_producto
    @p_producto_id INT,
    @p_valorRetornado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    SET @p_valorRetornado = 0;

    SELECT TOP 1 @p_valorRetornado = 1
    FROM dbo.DETALLES_DOCUMENTO_DE_PAGO
    WHERE PRODUCTO_ID = @p_producto_id;
END
GO