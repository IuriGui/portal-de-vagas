package br.csi.oportunidades.model.candidato;

import br.csi.oportunidades.model.Endereco;
import br.csi.oportunidades.model.Users;
import br.csi.oportunidades.model.inscricao.Inscricao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "candidato")
@Getter
@Setter
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @JsonIgnore
    private Endereco endereco;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonIgnore
    private Users usuario;

    private Date dataNascimento;
    private String curriculoUrl;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienciaProfissional> experiencias;

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormacaoAcademica> formacoesAcademicas;

    @ManyToMany
    @JoinTable(
            name = "candidato_habilidade",
            joinColumns = @JoinColumn(name = "candidato_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidade_id")
    )
    private Set<Habilidade> habilidades = new HashSet<>();

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscricao> inscricoes;


}
