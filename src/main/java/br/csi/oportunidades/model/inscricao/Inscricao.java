package br.csi.oportunidades.model.inscricao;

import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Table(name = "inscricao")
@Getter
@Setter
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    @JsonIgnore
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "oportunidade_id")
    @JsonIgnore
    private Oportunidade oportunidade;

    private Date data_inscricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusInscricao status;



}