ALTER TABLE inscricao
    DROP CONSTRAINT IF EXISTS chk_status_inscricao;

ALTER TABLE inscricao
    ADD CONSTRAINT chk_status_inscricao
        CHECK (UPPER(status) IN (
                                 'INSCRICAO_RECEBIDA',
                                 'EM_TRIAGEM',
                                 'ENTREVISTA_AGENDADA',
                                 'REJEITADO',
                                 'CONTRATADO',    -- Estado final de sucesso
                                 'RETIRADO',      -- Estado final por desistência do candidato
                                 'CANCELADO'      -- Estado para anulação administrativa (opcional, mas bom ter)
            ));

ALTER TABLE inscricao
    ALTER COLUMN status SET DEFAULT 'INSCRICAO_RECEBIDA';