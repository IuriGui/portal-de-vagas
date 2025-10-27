package br.csi.oportunidades.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {


    @JsonView(Views.Publico.class)
    private Long id;

    @JsonView(Views.Publico.class)
    private String cep;

    @JsonView(Views.Publico.class)
    private String uf;
    @JsonView(Views.Publico.class)
    private String cidade;
    @JsonView(Views.Publico.class)
    private String bairro;
    @JsonView(Views.Publico.class)
    private String rua;
    @JsonView(Views.Publico.class)
    private String numero;
    @JsonView(Views.Publico.class)
    private String complemento;

    @JsonView(Views.Detalhado.class)
    private Double latitude;
    @JsonView(Views.Detalhado.class)
    private Double longitude;
}
