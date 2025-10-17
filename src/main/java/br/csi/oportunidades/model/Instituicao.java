package br.csi.oportunidades.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "instituicao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFantasia;
    private String descricao;

    private String telefone;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Users usuario;


}
