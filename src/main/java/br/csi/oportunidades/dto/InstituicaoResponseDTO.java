package br.csi.oportunidades.dto;

import br.csi.oportunidades.model.Users;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InstituicaoResponseDTO {

    @JsonView(Views.Publico.class)
    private Long id;

    @JsonView(Views.Publico.class)
    private String nomeFantasia;
    @JsonView(Views.Detalhado.class)
    private String descricao;
    @JsonView(Views.Publico.class)
    private String telefone;
    @JsonView(Views.Detalhado.class)
    private EnderecoDTO endereco;

    @JsonView(Views.Detalhado.class)
    private Users usuario;
}
