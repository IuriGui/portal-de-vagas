package br.csi.oportunidades.dto.usuario;


import br.csi.oportunidades.model.appUser.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRecruiterRequestDTO {

    @Email(message = "O email deve ser válido")
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String password;

    @NotNull(message = "O tipo de conta é obrigatório (CANDIDATO ou EMPRESA).")
    private UserRoles role;
    private String recruiterFullName;

    @NotBlank(message = "O nome da empresa é obrigatório.")
    private String companyName;

    @NotBlank(message = "O telefone da empresa é obrigatório.")
    @Size(max = 15, message = "Telefone no formato invalido")
    private String companyPhone;


    @Size(max = 1000, message = "A descrição não pode exceder 1000 caracteres")
    private String companyDescription;


}
