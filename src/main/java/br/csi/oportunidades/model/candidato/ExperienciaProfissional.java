package br.csi.oportunidades.model.candidato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "experiencia_profissional")
@Getter
@Setter
public class ExperienciaProfissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    @JsonIgnore
    private Candidato candidato;

    private String cargo;
    private String empresa;
    private Date dataInicio;
    private Date dataFim;

}

