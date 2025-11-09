package br.csi.oportunidades.dto.application;


import br.csi.oportunidades.model.application.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationStatusUpdateDTO {

    @NotNull(message = "O Status é obrigatório.")
    private ApplicationStatus status;
}
