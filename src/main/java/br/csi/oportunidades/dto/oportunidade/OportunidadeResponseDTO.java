package br.csi.oportunidades.dto.oportunidade;

import br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO;
import br.csi.oportunidades.model.Endereco;
import br.csi.oportunidades.model.oportunidade.Oportunidade;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public record OportunidadeResponseDTO(
        Long oportunidade_id,
        Long instituicao_id,
        String instituicao,
        Endereco endereco,
        Long area_atuacao_id,
        String titulo,
        String descricao,
        Timestamp dataPublicacao,
        Timestamp dataValidade,
        boolean remoto,
        int cargaHoraria,
        BigDecimal remuneracao,
        String beneficios,
        String requisitos,
        List<InscricaoResponseDTO> inscricoes   // <- adiciona aqui
) {
    public static OportunidadeResponseDTO from(Oportunidade op) {
        List<InscricaoResponseDTO> inscricoesDTO = op.getInscricoes() != null
                ? op.getInscricoes().stream()
                .map(i -> InscricaoResponseDTO.fromEntity(i, i.getCandidato().getNome(), i.getOportunidade().getTitulo()))
                .collect(Collectors.toList())
                : List.of();

        return new OportunidadeResponseDTO(
                op.getId(),
                op.getInstituicao() != null ? op.getInstituicao().getId() : null,
                op.getInstituicao() != null ? op.getInstituicao().getNomeFantasia() : null,
                op.getEndereco(),
                op.getAreaAtuacao() != null ? op.getAreaAtuacao().getId() : null,
                op.getTitulo(),
                op.getDescricao(),
                op.getDataPublicacao(),
                op.getDataValidade(),
                op.isRemoto(),
                op.getCargaHoraria(),
                op.getRemuneracao(),
                op.getBeneficios(),
                op.getRequisitos(),
                inscricoesDTO   // <- passa a lista convertida
        );
    }
}