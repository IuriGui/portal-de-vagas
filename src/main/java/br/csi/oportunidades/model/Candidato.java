package br.csi.oportunidades.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Users usuario;

    private Date dataNascimento;

    private String curriculoUrl;

}
