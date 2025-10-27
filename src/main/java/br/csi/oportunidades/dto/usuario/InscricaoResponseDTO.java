package br.csi.oportunidades.dto.usuario;

import br.csi.oportunidades.dto.CandidatoResponseDTO;
import br.csi.oportunidades.dto.OportunidadeResponseDT0;
import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.model.inscricao.StatusInscricao;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class InscricaoResponseDTO {

    @JsonView(Views.Publico.class)
    private Long id;

    @JsonView(Views.Publico.class)
    private CandidatoResponseDTO candidato;

    @JsonView(Views.Publico.class)
    private OportunidadeResponseDT0 oportunidade;

    @JsonView(Views.Publico.class)
    private Date data_inscricao;

    @JsonView(Views.Publico.class)
    private StatusInscricao status;

}
