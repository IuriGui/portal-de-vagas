package br.csi.oportunidades.dto.usuario;


import br.csi.oportunidades.model.appUser.UserRoles;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Getter
@Setter
public class UserCandidateRequestDTO {

    @Email(message = "O email deve ser válido")
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String password;

    @NotNull(message = "O tipo de conta é obrigatório (CANDIDATO ou EMPRESA).")
    private UserRoles role;
    @NotBlank(message = "O nome eh obrigatorio")
    private String candidateFullName;

    @NotBlank(message = "O telefone eh obrigatorio")
    private String CandidatePhone;

    @Past(message = "A data de nascimento deve ser uma data no passado.")
    private LocalDate birthDate;

    @URL(message = "A URL do currículo deve ser válida (ex: http://...)")
    private String resumeUrl;
}
