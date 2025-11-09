package br.csi.oportunidades.controller;

import br.csi.oportunidades.dto.application.ApplicationForRecruiterDTO;
import br.csi.oportunidades.dto.application.ApplicationResponseDTO;
import br.csi.oportunidades.dto.opportunity.OpportunityRequestDTO;
import br.csi.oportunidades.dto.opportunity.OpportunityResponseDTO;
import br.csi.oportunidades.service.ApplicationService;
import br.csi.oportunidades.service.OpportunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/opportunities")
@AllArgsConstructor
@Tag(name = "Oportunidades (Vagas)", description = "Endpoints para criar, listar e gerenciar vagas e inscrições")
public class OpportunityController {

    private final OpportunityService opportunityService;
    private final ApplicationService applicationService;

    // recrutador

    @PostMapping
    @Operation(summary = "Recrutador - Criar nova vaga", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vaga criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OpportunityResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (erro de validação)", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado (não é um RECRUITER)", content = @Content)
    })
    public ResponseEntity<OpportunityResponseDTO> create(
            @Valid @RequestBody OpportunityRequestDTO dto,
            UriComponentsBuilder uriBuilder) {

        OpportunityResponseDTO newOpportunity = opportunityService.createOpportunity(dto);

        URI location = uriBuilder.path("/opportunities/{id}")
                .buildAndExpand(newOpportunity.getId())
                .toUri();

        return ResponseEntity.created(location).body(newOpportunity);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Recrutador - Atualizar uma vaga",
            description = "Atualiza os dados de uma vaga existente. Apenas recrutadores da mesma empresa podem realizar esta operação.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vaga atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = OpportunityResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada", content = @Content)
    })
    public ResponseEntity<OpportunityResponseDTO> update(
            @Parameter(description = "ID da vaga a ser atualizada") @PathVariable Long id,
            @Valid @RequestBody OpportunityRequestDTO dto) {

        OpportunityResponseDTO updatedOpportunity = opportunityService.updateOpportunity(id, dto);
        return ResponseEntity.ok(updatedOpportunity);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Recrutador - Deletar uma vaga",
            description = "Remove permanentemente uma vaga vinculada à empresa do recrutador autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vaga deletada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada", content = @Content)
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID da vaga a ser deletada") @PathVariable Long id) {
        opportunityService.deleteOpportunity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-company")
    @Operation(
            summary = "Recrutador - Listar vagas da minha empresa",
            description = "Lista todas as vagas criadas pela empresa do recrutador autenticado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OpportunityResponseDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    public ResponseEntity<List<OpportunityResponseDTO>> getMyCompanyOpportunities() {
        return ResponseEntity.ok(opportunityService.getMyCompanyOpportunities());
    }

    @GetMapping("/{id}/applications")
    @Operation(
            summary = "Recrutador - Listar inscritos em uma vaga",
            description = "Lista todos os candidatos que se inscreveram em uma vaga específica. Apenas o recrutador dono da vaga pode ver.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de inscrições retornada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApplicationForRecruiterDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada", content = @Content)
    })
    public ResponseEntity<List<ApplicationForRecruiterDTO>> getApplications(
            @Parameter(description = "ID da vaga para ver as inscrições") @PathVariable Long id) {

        List<ApplicationForRecruiterDTO> applications = applicationService.getApplicationsForOpportunity(id);
        return ResponseEntity.ok(applications);
    }

    // Candidato

    @PostMapping("/{id}/apply")
    @Operation(
            summary = "Candidato - Inscrever-se em uma vaga",
            description = "Permite que o candidato autenticado se inscreva em uma vaga específica.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inscrição realizada",
                    content = @Content(schema = @Schema(implementation = ApplicationResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito (Candidato já inscrito nesta vaga)", content = @Content)
    })
    public ResponseEntity<ApplicationResponseDTO> apply(
            @Parameter(description = "ID da vaga na qual se inscrever") @PathVariable Long id,
            UriComponentsBuilder uriBuilder) {

        ApplicationResponseDTO newApplication = applicationService.applyToOpportunity(id);

        URI location = uriBuilder.path("/me/applications/{id}")
                .buildAndExpand(newApplication.getId())
                .toUri();

        return ResponseEntity.created(location).body(newApplication);
    }

    // qualquer um

    @GetMapping
    @Operation(
            summary = "Público - Listar todas as vagas",
            description = "Retorna todas as oportunidades disponíveis publicamente. (Futuramente com filtros)"
    )
    @ApiResponse(responseCode = "200", description = "Lista de vagas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OpportunityResponseDTO.class))))
    public ResponseEntity<List<OpportunityResponseDTO>> getAllPublic() {
        return ResponseEntity.ok(opportunityService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obter detalhes de uma vaga",
            description = "Retorna os detalhes completos de uma vaga disponível publicamente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vaga encontrada",
                    content = @Content(schema = @Schema(implementation = OpportunityResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vaga não encontrada", content = @Content)
    })
    public ResponseEntity<OpportunityResponseDTO> getOpportunityPublic(
            @Parameter(description = "ID da vaga a ser buscada") @PathVariable Long id) {

        return ResponseEntity.ok(opportunityService.getOpportunity(id));
    }
}