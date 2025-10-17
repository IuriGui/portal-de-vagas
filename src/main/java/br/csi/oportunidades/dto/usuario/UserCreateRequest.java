package br.csi.oportunidades.dto.usuario;

import br.csi.oportunidades.model.TipoConta;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserCreateRequest {

    @Email
    private String email;


    private String senha;

    private TipoConta tipoConta;
    
    // Campos específicos do candidato
    private String nome;


    private String telefone;


    private Date dataNascimento;
    private String curriculoUrl;
    
    // Campos específicos da instituição
    private String nomeFantasia;
    private String descricao;
}
