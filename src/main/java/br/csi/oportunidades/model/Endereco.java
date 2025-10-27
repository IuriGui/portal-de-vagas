package br.csi.oportunidades.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "endereco")
@Getter
@Setter
public class Endereco {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;

    @JsonProperty("estado")
    private String uf;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String complemento;


    private Double latitude;
    private Double longitude;

}
