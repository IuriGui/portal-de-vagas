package br.csi.oportunidades.dto.candidato;


import br.csi.oportunidades.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CandidatoUpdateRequestDTO {

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O telefone não pode estar em branco.")
    @Size(min = 10, max = 15, message = "O telefone deve ter um formato válido (ex: (xx) xxxxx-xxxx).")
    private String telefone;

    private Endereco endereco;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser no passado.")
    private LocalDate dataNascimento;

    @URL(message = "A URL do currículo deve ser válida.")
    private String curriculoUrl;

    private Set<Long> habilidadesIds = new HashSet<>();

}
