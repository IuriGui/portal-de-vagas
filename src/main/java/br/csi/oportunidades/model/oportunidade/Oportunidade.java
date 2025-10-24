package br.csi.oportunidades.model.oportunidade;


import br.csi.oportunidades.model.Endereco;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.model.inscricao.Inscricao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "oportunidade")
@Getter
@Setter
public class Oportunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    @ManyToOne
    @JoinColumn(name = "area_atuacao_id")
    private AreaAtuacao areaAtuacao;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private String titulo;

    private String descricao;

    private Timestamp dataPublicacao;

    private Timestamp dataValidade;

    private boolean remoto;

    private int cargaHoraria;

    @Column(precision = 12, scale = 2)
    private BigDecimal remuneracao;

    private String beneficios;

    private String requisitos;

    @OneToMany(mappedBy = "oportunidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscricao> inscricoes;

}
