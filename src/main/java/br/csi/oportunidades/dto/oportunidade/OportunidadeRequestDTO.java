package br.csi.oportunidades.dto.oportunidade;

import br.csi.oportunidades.model.Endereco;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record OportunidadeRequestDTO(
        Long instituicao_id,
        Long areaAtuacao_id,
        Endereco endereco,
        String titulo,
        String descricao,
        Timestamp dataPublicacao,
        Timestamp dataValidade,
        boolean remoto,
        int cargaHoraria,
        BigDecimal remuneracao,
        String beneficios,
        String requisitos
) {}

