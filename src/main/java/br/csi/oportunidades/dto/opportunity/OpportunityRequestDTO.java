package br.csi.oportunidades.dto.opportunity;


import br.csi.oportunidades.dto.AddressDTO;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OpportunityRequestDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    private String title;

    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    @NotNull(message = "A data de expiração é obrigatória")
    @Future(message = "A data de expiração deve ser no futuro")
    private LocalDateTime expiresAt;

    @NotNull(message = "O campo 'remoto' é obrigatório")
    private Boolean isRemote;

    @NotNull(message = "A carga horária é obrigatória")
    @Positive(message = "A carga horária deve ser maior que zero")
    @Max(value = 60, message = "A carga horária máxima permitida é 60 horas por semana")
    private Integer workloadHours;

    @PositiveOrZero(message = "O salário deve ser um valor positivo")
    private BigDecimal salary;

    private String benefits;

    private String requirements;


    @NotNull(message = "A área de atuação (jobAreaId) é obrigatória")
    private Long jobAreaId;


    private AddressDTO address;

}
