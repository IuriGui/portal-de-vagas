package br.csi.oportunidades.controller.candidate;


import br.csi.oportunidades.dto.application.ApplicationResponseDTO;
import br.csi.oportunidades.dto.candidate.AcademicBackgroundDTO;
import br.csi.oportunidades.dto.candidate.AcademicBackgroundUpdateDTO;
import br.csi.oportunidades.dto.candidate.ProfessionalExperienceDTO;
import br.csi.oportunidades.dto.candidate.ProfessionalExperienceUpdateDTO;
import br.csi.oportunidades.dto.candidate.CandidateProfileDTO;
import br.csi.oportunidades.dto.candidate.CandidateUpdateDTO;
import br.csi.oportunidades.model.candidate.AcademicBackground;
import br.csi.oportunidades.model.candidate.ProfessionalExperience;
import br.csi.oportunidades.service.ApplicationService;
import br.csi.oportunidades.service.CandidateService;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/me")
@AllArgsConstructor
@Tag(name = "Candidato logado", description = "Endpoints para o candidato gerir o seu perfil e inscrições")
@SecurityRequirement(name = "bearerAuth")
public class MeCandidateController {


    private final CandidateService candidateService;
    private final ModelMapper modelMapper;
    private final ApplicationService applicationService;


    @GetMapping("/profile")
    @Operation(summary = "Recuperar meu perfil", description = "Retorna todos os dados do perfil (pessoal, acadêmico e profissional) do candidato logado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil recuperado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CandidateProfileDTO.class))),
            @ApiResponse(responseCode = "404", description = "Perfil do candidato não encontrado", content = @Content)
    })
    public ResponseEntity<CandidateProfileDTO> getMeuProfile() {
        CandidateProfileDTO profile = candidateService.getMyProfile();
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    @Operation(summary = "Atualizar meu perfil (dados principais)",
            description = "Atualiza os dados principais do perfil (nome, telefone, data de nasc., currículo, endereço e habilidades). Campos não incluídos no JSON não são alterados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CandidateProfileDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil do candidato não encontrado", content = @Content)
    })
    public ResponseEntity<CandidateProfileDTO> updateMyProfile(@Valid @RequestBody CandidateUpdateDTO dto) {
        CandidateProfileDTO updatedProfile = candidateService.updateMyProfile(dto);
        return ResponseEntity.ok(updatedProfile);
    }

    @PostMapping("/profile/experience")
    @Operation(summary = "Adicionar nova experiência profissional")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Experiência adicionada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfessionalExperienceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<ProfessionalExperienceDTO> addExperience(@Valid @RequestBody ProfessionalExperienceDTO dto, UriComponentsBuilder uriBuilder) {

        ProfessionalExperience newExp = candidateService.addExperienceToMe(dto);

        URI location = uriBuilder.path("/me/profile/experience/{id}")
                .buildAndExpand(newExp.getId())
                .toUri();

        ProfessionalExperienceDTO experienceDTO = modelMapper.map(newExp, ProfessionalExperienceDTO.class);

        return ResponseEntity.created(location).body(experienceDTO);
    }

    @PutMapping("/profile/experience/{exp_id}")
    @Operation(summary = "Atualizar uma experiência profissional")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Experiência atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfessionalExperienceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Experiência não encontrada", content = @Content)
    })
    public ResponseEntity<ProfessionalExperienceDTO> updateMyExperience(@Valid @RequestBody ProfessionalExperienceUpdateDTO dto, @PathVariable Long exp_id) {
        return ResponseEntity.ok(candidateService.updateMyExperience(dto, exp_id));
    }

    @DeleteMapping("/profile/experience/{exp_id}")
    @Operation(summary = "Remover uma experiência profissional")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Experiência removida com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Experiência não encontrada", content = @Content)
    })
    public ResponseEntity<?> deleteMyExperience(@PathVariable Long exp_id) {
        candidateService.deleteMyExperience(exp_id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/profile/academic")
    @Operation(summary = "Adicionar nova formação acadêmica")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Formação adicionada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AcademicBackgroundDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<AcademicBackgroundDTO> addAcademicBackground(@Valid @RequestBody AcademicBackgroundDTO dto, UriComponentsBuilder uriBuilder) {

        AcademicBackground newAcaBck = candidateService.addAcademicBackground(dto);

        URI location = uriBuilder.path("/me/profile/experience/{id}")
                .buildAndExpand(newAcaBck.getId())
                .toUri();



        return ResponseEntity.created(location).body(modelMapper.map(newAcaBck, AcademicBackgroundDTO.class));
    }


    @PutMapping("/profile/academic/{ac_bk}")
    @Operation(summary = "Atualizar uma formação acadêmica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Formação atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AcademicBackgroundDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Formação não encontrada", content = @Content)
    })
    public ResponseEntity<AcademicBackgroundDTO> updateMyAcademicBackground(@RequestBody @Valid AcademicBackgroundUpdateDTO dto, @PathVariable Long ac_bk) {
        AcademicBackgroundDTO updatedAcademic = candidateService.updateMyAcademicBackground(dto, ac_bk);
        return ResponseEntity.ok(updatedAcademic);
    }

    @DeleteMapping("/profile/academic/{ac_bk}")
    @Operation(summary = "Remover uma formação acadêmica")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Formação removida com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Formação não encontrada", content = @Content)
    })
    public ResponseEntity<AcademicBackgroundDTO> updateMyAcademicBackground(@PathVariable Long ac_bk) {
        candidateService.deleteMyAcademicBackground(ac_bk);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/applications")
    @Operation(summary = "Listar minhas inscrições",
            description = "Retorna uma lista de todas as vagas às quais o candidato logado se inscreveu.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de inscrições",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ApplicationResponseDTO.class))))
    })
    public ResponseEntity<List<ApplicationResponseDTO>> getMyApplications() {

        List<ApplicationResponseDTO> applications = applicationService.getMyApplications();
        return ResponseEntity.ok(applications);
    }

    @DeleteMapping("/applications/{id}")
    @Operation(summary = "Retirar (cancelar) minha inscrição",
            description = "Muda o status de uma inscrição para 'WITHDRAWN'. O candidato não pode deletar a inscrição, apenas retirá-la.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inscrição retirada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada ou não pertence ao usuário", content = @Content)
    })
    public ResponseEntity<Void> withdrawApplication(
            @Parameter(description = "ID da inscrição a ser retirada")
            @PathVariable Long id) {
        applicationService.withdrawApplication(id);
        return ResponseEntity.noContent().build();
    }




}
