package br.csi.oportunidades.controller;

import br.csi.oportunidades.dto.application.ApplicationResponseDTO;
import br.csi.oportunidades.dto.application.ApplicationStatusUpdateDTO;
import br.csi.oportunidades.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@AllArgsConstructor
@Tag(name = "Inscrições (Applications)", description = "Endpoints para gerenciar inscrições")
@SecurityRequirement(name = "bearerAuth")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PutMapping("/{id}/status")
    @Operation(
            summary = "Recrutador - Atualizar status de uma inscrição",
            description = "Permite ao recrutador atualizar o status de uma inscrição existente com base no ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da inscrição atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: status inexistente ou formato incorreto)",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content)
    })
    public ResponseEntity<ApplicationResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationStatusUpdateDTO dto) {

        ApplicationResponseDTO updatedApp = applicationService.updateApplicationStatus(id, dto);
        return ResponseEntity.ok(updatedApp);
    }
}
