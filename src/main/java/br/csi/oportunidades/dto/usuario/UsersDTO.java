package br.csi.oportunidades.dto.usuario;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class UsersDTO {
    private UUID id;
    private String email;
}
