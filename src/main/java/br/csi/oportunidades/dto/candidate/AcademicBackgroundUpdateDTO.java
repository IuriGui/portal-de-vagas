package br.csi.oportunidades.dto.candidate;

import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class AcademicBackgroundUpdateDTO {

    private String institutionName;

    private String courseName;

    @PastOrPresent(message = "A data de início não pode ser no futuro")
    private LocalDate startDate;

    @PastOrPresent(message = "A data de término não pode ser no futuro")
    private LocalDate endDate;
}
