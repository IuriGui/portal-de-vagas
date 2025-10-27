package br.csi.oportunidades.model.candidato;


import br.csi.oportunidades.dto.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(Views.Publico.class)
    private Long id;

    @JsonView(Views.Publico.class)
    private String nome;

    @ManyToMany(mappedBy = "habilidades")
    @JsonIgnore
    private Set<Candidato> candidatos = new HashSet<>();


}
