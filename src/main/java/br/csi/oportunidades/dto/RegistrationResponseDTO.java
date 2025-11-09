package br.csi.oportunidades.dto;

import br.csi.oportunidades.model.appUser.UserRoles;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class RegistrationResponseDTO {

    private UUID userId;
    private Long profileId;
    private String email;
    private UserRoles role;


}
