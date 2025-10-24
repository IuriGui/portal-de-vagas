package br.csi.oportunidades.dto.usuario;

import br.csi.oportunidades.model.TipoConta;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

@Getter
@Setter
public class UserCreateRequest {

    @Email
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String senha;


    @NotNull(message = "O tipo de conta é obrigatório (CANDIDATO ou EMPRESA).")
    private TipoConta tipoConta;
    
    // Campos específicos do candidato
    private String nome;


    private String telefone;

    @Past(message = "A data de nascimento deve ser uma data no passado.")
    private Date dataNascimento;

    private String curriculoUrl;
    
    // Campos específicos da instituição
    private String nomeFantasia;

    @Size(max = 300, message = "O limite de caracteres é 300")
    private String descricao;
}
