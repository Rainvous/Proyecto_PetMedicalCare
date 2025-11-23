USE petmedicalcare;
GO

-- ============================================
-- TRIGGER DE AUDITORÍA PARA PRODUCTOS (INSERT)
-- ============================================
IF OBJECT_ID('trg_PRODUCTOS_audit_insert', 'TR') IS NOT NULL
    DROP TRIGGER trg_PRODUCTOS_audit_insert;
GO

CREATE TRIGGER trg_PRODUCTOS_audit_insert
ON PRODUCTOS
AFTER INSERT -- En SQL Server, generalmente usamos AFTER para auditoría (o INSTEAD OF)
AS
BEGIN
    SET NOCOUNT ON;

    -- Actualizamos las filas recién insertadas
    UPDATE P
    SET 
        FECHA_CREACION = COALESCE(i.FECHA_CREACION, GETDATE()),
        FECHA_MODIFICACION = COALESCE(i.FECHA_MODIFICACION, GETDATE()),
        USUARIO_CREADOR = COALESCE(NULLIF(i.USUARIO_CREADOR, ''), SYSTEM_USER),
        USUARIO_MODIFICADOR = COALESCE(NULLIF(i.USUARIO_MODIFICADOR, ''), SYSTEM_USER)
    FROM PRODUCTOS P
    INNER JOIN inserted i ON P.PRODUCTO_ID = i.PRODUCTO_ID;
END
GO

-- ============================================
-- TRIGGER DE AUDITORÍA PARA PRODUCTOS (UPDATE)
-- ============================================
IF OBJECT_ID('trg_PRODUCTOS_audit_update', 'TR') IS NOT NULL
    DROP TRIGGER trg_PRODUCTOS_audit_update;
GO

CREATE TRIGGER trg_PRODUCTOS_audit_update
ON PRODUCTOS
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Actualizamos solo modificación
    UPDATE P
    SET 
        FECHA_MODIFICACION = GETDATE(),
        USUARIO_MODIFICADOR = SYSTEM_USER
    FROM PRODUCTOS P
    INNER JOIN inserted i ON P.PRODUCTO_ID = i.PRODUCTO_ID;
END
GO