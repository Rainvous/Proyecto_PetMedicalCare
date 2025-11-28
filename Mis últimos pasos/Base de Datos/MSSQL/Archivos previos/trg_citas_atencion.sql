USE petmedicalcare;
GO

IF OBJECT_ID('trg_citas_atencion_cascade_delete', 'TR') IS NOT NULL 
    DROP TRIGGER trg_citas_atencion_cascade_delete;
GO

CREATE TRIGGER trg_citas_atencion_cascade_delete
ON CITAS_ATENCION
INSTEAD OF DELETE -- <-- Clave: "En lugar de borrar, haz esto..."
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Eliminar Detalles de Servicio (Hijos)
    DELETE FROM DETALLES_SERVICIO
    WHERE CITA_ID IN (SELECT CITA_ID FROM deleted);

    -- 2. Eliminar Recetas Médicas (Hijos)
    DELETE FROM RECETAS_MEDICAS
    WHERE CITA_ID IN (SELECT CITA_ID FROM deleted);

    -- 3. ¡MUY IMPORTANTE! Ahora sí borramos la Cita original (Padre)
    -- Como estamos en un trigger INSTEAD OF, si no hacemos esto, la cita nunca se borra.
    DELETE FROM CITAS_ATENCION
    WHERE CITA_ID IN (SELECT CITA_ID FROM deleted);
END
GO