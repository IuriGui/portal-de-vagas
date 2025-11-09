package br.csi.oportunidades.dto.candidate;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateSimpleDTO {
    private Long id;
    private String fullName;
    private String resumeUrl;
}
