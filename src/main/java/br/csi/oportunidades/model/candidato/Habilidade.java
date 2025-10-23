package br.csi.oportunidades.model.candidato;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "habilidade")
@Getter
@Setter
public class Habilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "habilidades")
    @JsonIgnore
    private Set<Candidato> candidatos = new HashSet<>();


}
