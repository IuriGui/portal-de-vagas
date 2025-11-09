package br.csi.oportunidades.dto.usuario;

import br.csi.oportunidades.model.appUser.UserRoles;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreateRequest {

    //Comum
    @Email(message = "O email deve ser válido")
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String password;

    @NotNull(message = "O tipo de conta é obrigatório (CANDIDATO ou EMPRESA).")
    private UserRoles role;

    //Candidate
    private String candidateFullName;
    private String CandidatePhone;

    @Past(message = "A data de nascimento deve ser uma data no passado.")
    private LocalDate birthDate;

    @URL(message = "A URL do currículo deve ser válida (ex: http://...)")
    private String resumeUrl;

    // Recruiter
    private String recruiterFullName;

    private String companyName;
    private String companyPhone;


    @Size(max = 1000, message = "A descrição não pode exceder 1000 caracteres")
    private String companyDescription;
}