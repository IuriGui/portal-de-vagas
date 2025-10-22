package br.csi.oportunidades.dto.instituicao;

import br.csi.oportunidades.model.Endereco;
import br.csi.oportunidades.model.Users;

public record InstituicaoResponseDTO(
        Long id,
        String nomeFantasia,
        String descricao,
        String telefone,
        Endereco endereco,
        String email
) {

    public static InstituicaoResponseDTO from(br.csi.oportunidades.model.Instituicao instituicao) {
        return new InstituicaoResponseDTO(
                instituicao.getId(),
                instituicao.getNomeFantasia(),
                instituicao.getDescricao(),
                instituicao.getTelefone(),
                instituicao.getEndereco(),
                instituicao.getUsuario().getEmail()
        );
    }
}
