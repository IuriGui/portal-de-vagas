package br.csi.oportunidades.model.candidate;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "academic_background")
@Getter
@Setter
@NoArgsConstructor
public class AcademicBackground {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Candidate candidate;

    @Column(name="institution_name", nullable = false)
    private String institutionName;

    @Column(name="course_name", nullable = false)
    private String courseName;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;


}
