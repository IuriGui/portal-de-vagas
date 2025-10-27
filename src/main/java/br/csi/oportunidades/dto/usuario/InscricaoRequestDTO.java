package br.csi.oportunidades.dto.usuario;

import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.inscricao.StatusInscricao;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class InscricaoRequestDTO {

    @NotBlank
    private Long oportunidade;

}
