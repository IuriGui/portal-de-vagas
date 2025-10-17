CREATE TYPE turno_enum AS ENUM ('MATUTINO', 'VESPERTINO', 'NOTURNO', 'INTEGRAL');
CREATE TYPE modalidade_enum AS ENUM ('PRESENCIAL', 'REMOTO', 'HIBRIDO');

ALTER TABLE oportunidade
ADD COLUMN turno turno_enum NOT NULL DEFAULT 'INTEGRAL',
ADD COLUMN modalidade modalidade_enum NOT NULL DEFAULT 'PRESENCIAL';

CREATE TYPE status_inscricao_enum AS ENUM ('APLICADO', 'EM_ANALISE', 'ACEITO', 'RECUSADO');
ALTER TABLE inscricao
ALTER COLUMN status TYPE status_inscricao_enum USING status::status_inscricao_enum;

