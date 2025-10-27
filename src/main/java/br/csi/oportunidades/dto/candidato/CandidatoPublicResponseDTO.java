package br.csi.oportunidades.dto.candidato;


import br.csi.oportunidades.dto.EnderecoDTO;
import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.model.candidato.FormacaoAcademica;
import br.csi.oportunidades.model.candidato.Habilidade;
import br.csi.oportunidades.model.inscricao.Inscricao;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CandidatoPublicResponseDTO {

    @JsonView(Views.Publico.class)
    private Long id;

    @JsonView(Views.Publico.class)
    private String nome;
    @JsonView(Views.Publico.class)
    private String telefone;
    @JsonView(Views.Publico.class)
    private EnderecoDTO endereco;

    @JsonView(Views.Publico.class)
    private Date dataNascimento;
    @JsonView(Views.Publico.class)
    private String curriculoUrl;

    @JsonView(Views.Publico.class)
    private List<ExperienciaProfissional> experiencias;

    @JsonView(Views.Publico.class)
    private List<FormacaoAcademica> formacoesAcademicas;

    @JsonView(Views.Publico.class)
    private Set<Habilidade> habilidades;

    @JsonView(Views.Detalhado.class)
    private List<Inscricao> inscricoes;
}
