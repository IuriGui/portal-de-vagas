package br.csi.oportunidades.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
