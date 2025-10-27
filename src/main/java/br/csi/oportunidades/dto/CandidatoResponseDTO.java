package br.csi.oportunidades.dto;

import br.csi.oportunidades.model.Endereco;
import br.csi.oportunidades.model.Users;
import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.model.candidato.FormacaoAcademica;
import br.csi.oportunidades.model.candidato.Habilidade;
import br.csi.oportunidades.model.inscricao.Inscricao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CandidatoResponseDTO {

    private Long id;

    private String nome;
    private String telefone;
    private Endereco endereco;
    //private Users usuario;

    private Date dataNascimento;
    private String curriculoUrl;


    private List<ExperienciaProfissional> experiencias;


    private List<FormacaoAcademica> formacoesAcademicas;

    private Set<Habilidade> habilidades = new HashSet<>();

    private List<Inscricao> inscricoes;
}
