package br.csi.oportunidades.model;


import jakarta.persistence.*;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "oportunidade")
public class Oportunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

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

}
