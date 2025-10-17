ALTER TABLE usuario
    ALTER COLUMN tipo_conta TYPE VARCHAR(20)
        USING tipo_conta::text;