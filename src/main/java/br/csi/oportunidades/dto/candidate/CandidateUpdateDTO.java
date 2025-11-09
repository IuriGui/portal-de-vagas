package br.csi.oportunidades.dto.candidate;

import br.csi.oportunidades.dto.AddressDTO;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
public class CandidateUpdateDTO {


    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    private String fullName;

    @Size(min = 10, max = 15, message = "O telefone deve ter um formato válido.")
    private String phone;

    @Past(message = "A data de nascimento deve ser no passado.")
    private LocalDate birthDate;

    @URL(message = "A URL do currículo deve ser válida.")
    private String resumeUrl;

    private AddressDTO address;

    private Set<Long> skillIds;

}
