package br.csi.oportunidades.model.candidato;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "formacao_academica")
@Getter
@Setter
public class FormacaoAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    private String instituicao;
    private String curso;
    private Date dataInicio;
    private Date dataConclusao;

}
