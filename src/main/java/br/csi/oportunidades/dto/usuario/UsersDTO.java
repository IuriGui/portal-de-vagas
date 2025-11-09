package br.csi.oportunidades.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class UsersDTO {
    private UUID id;
    private String email;
}
