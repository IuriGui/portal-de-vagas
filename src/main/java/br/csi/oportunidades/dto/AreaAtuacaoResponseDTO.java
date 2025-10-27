package br.csi.oportunidades.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaAtuacaoResponseDTO {

    @JsonView(Views.Publico.class)
    private Long id;
    @JsonView(Views.Publico.class)
    private String nome;
}
