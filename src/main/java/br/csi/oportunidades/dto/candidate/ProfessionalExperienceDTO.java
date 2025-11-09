package br.csi.oportunidades.dto.candidate;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfessionalExperienceDTO {

    private Long id;

    @NotBlank(message = "O cargo é obrigatório")
    private String jobTitle;

    @NotBlank(message = "O nome da empresa é obrigatório")
    private String companyName;

    @NotNull(message = "A data de início é obrigatória")
    @PastOrPresent(message = "A data de início não pode ser no futuro")
    private LocalDate startDate;

    @PastOrPresent(message = "A data de término não pode ser no futuro")
    private LocalDate endDate;
}
