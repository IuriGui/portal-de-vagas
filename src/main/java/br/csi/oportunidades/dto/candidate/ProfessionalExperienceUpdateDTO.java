package br.csi.oportunidades.dto.candidate;


import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfessionalExperienceUpdateDTO {
    private String jobTitle;

    private String companyName;

    @PastOrPresent(message = "A data de início não pode ser no futuro")
    private LocalDate startDate;

    private LocalDate endDate;

}
