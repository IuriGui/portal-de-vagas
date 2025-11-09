package br.csi.oportunidades.dto.application;


import br.csi.oportunidades.dto.opportunity.OpportunityInApplicationDTO;
import br.csi.oportunidades.model.application.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationResponseDTO {
    private Long id;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;

    private OpportunityInApplicationDTO opportunity;

}
