package br.csi.oportunidades.dto.inscricao;


import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.dto.oportunidade.OportunidadeSimplesDTO;
import br.csi.oportunidades.model.inscricao.StatusInscricao;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InscricaoResponseParaCandidatoDTO {

    @JsonView(Views.Publico.class)
    private Long id;
    @JsonView(Views.Publico.class)
    private Date data_inscricao;
    @JsonView(Views.Publico.class)
    private StatusInscricao status;
    @JsonView(Views.Publico.class)
    private OportunidadeSimplesDTO oportunidade;

}
