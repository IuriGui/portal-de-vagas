package br.csi.oportunidades.dto.candidate;


import br.csi.oportunidades.dto.AddressDTO;
import br.csi.oportunidades.model.Skill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CandidateProfileDTO {

    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private LocalDate birthDate;
    private String resumeUrl;

    private AddressDTO address;
    private Set<AcademicBackgroundDTO> academicHistory;
    private Set<ProfessionalExperienceDTO> experiences;
    private Set<Skill> skills;

}
