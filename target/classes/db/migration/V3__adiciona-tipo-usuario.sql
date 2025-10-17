CREATE TYPE tipo_conta_enum AS ENUM ('EMPRESA', 'CANDIDATO');


ALTER TABLE usuario
ADD COLUMN tipo_conta tipo_conta_enum NOT NULL DEFAULT 'CANDIDATO';