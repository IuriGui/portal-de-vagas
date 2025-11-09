package br.csi.oportunidades.model.opportunity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "job_area")
@Getter
@Setter
@NoArgsConstructor
public class JobArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",unique = true, nullable = false)
    private String name;


}
