package br.csi.oportunidades.dto.opportunity;

import br.csi.oportunidades.dto.AddressDTO;
import br.csi.oportunidades.dto.AreaAtuacaoResponseDTO;
import br.csi.oportunidades.dto.recruiter.CompanySimpleDTO;
import br.csi.oportunidades.dto.recruiter.RecruiterSimpleDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
public class OpportunityResponseDTO {


    private Long id;
    private String title;
    private String description;
    private LocalDateTime publishedAt;
    private LocalDateTime expiresAt;
    private boolean isRemote;
    private Integer workloadHours;
    private BigDecimal salary;
    private String benefits;
    private String requirements;


    private AddressDTO address;
    private AreaAtuacaoResponseDTO jobArea;
    private CompanySimpleDTO company;
    private RecruiterSimpleDTO recruiter;
}
