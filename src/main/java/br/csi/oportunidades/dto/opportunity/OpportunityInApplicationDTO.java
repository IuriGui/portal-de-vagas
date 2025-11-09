package br.csi.oportunidades.dto.opportunity;


import br.csi.oportunidades.dto.recruiter.CompanySimpleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpportunityInApplicationDTO {
    private Long id;
    private String title;
    private boolean isRemote;
    private CompanySimpleDTO company;
}
