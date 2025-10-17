package br.csi.oportunidades.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue
    private UUID id;
    private String email;
    private String senha;


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta", nullable = false)
    private TipoConta tipoConta = TipoConta.CANDIDATO;

}
