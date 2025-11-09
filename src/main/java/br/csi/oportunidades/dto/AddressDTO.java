package br.csi.oportunidades.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddressDTO {

    @NotBlank(message = "O CEP é obrigatório.")
    private String zipCode;

    @Size(min = 2, max = 2, message = "O estado deve ser a sigla de 2 letras (ex: RS, SP).")
    private String stateCode;

    @NotBlank(message = "A cidade é obrigatória.")
    @Size(min = 2, max = 100, message = "A cidade deve ter entre 2 e 100 caracteres.")
    private String city;

    @NotBlank(message = "O bairro é obrigatório.")
    @Size(min = 2, max = 100, message = "O bairro deve ter entre 2 e 100 caracteres.")
    private String district;

    @NotBlank(message = "A rua é obrigatória.")
    @Size(min = 2, max = 120, message = "A rua deve ter entre 2 e 120 caracteres.")
    private String street;

    @NotBlank(message = "O número é obrigatório.")
    private String number;


    @Size(max = 100, message = "O complemento pode ter no máximo 150 caracteres.")
    private String complement;

    private BigDecimal latitude;

    private BigDecimal longitude;

}
