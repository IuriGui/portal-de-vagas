package br.csi.oportunidades.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InscricaoRequestDTO {

    @NotBlank
    private Long oportunidade;

}
