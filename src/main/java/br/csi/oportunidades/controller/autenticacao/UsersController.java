package br.csi.oportunidades.controller.autenticacao;

import br.csi.oportunidades.dto.RegistrationResponseDTO;
import br.csi.oportunidades.dto.usuario.UserCandidateRequestDTO;
import br.csi.oportunidades.dto.usuario.UserRecruiterRequestDTO;
import br.csi.oportunidades.dto.usuario.UsersDTO;
import br.csi.oportunidades.model.appUser.AppUser;
import br.csi.oportunidades.repository.UsersRepository;
import br.csi.oportunidades.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Tag(name = "Usuários", description = "Gerencia o cadastro e operações de usuários (candidatos e empresas)")
public class UsersController {

    private final UsersRepository usersRepository;
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;


    @PostMapping("/register/candidate")
    @Transactional
    @Operation(summary = "Registrar novo usuário (Candidato)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content)
    })
    public ResponseEntity<?> registerCandidate(
            @Valid @RequestBody UserCandidateRequestDTO request,
            UriComponentsBuilder uriBuilder) {

        AppUser savedUser;
        savedUser = registrationService.registerCandidate(request);

        RegistrationResponseDTO responseDTO = new RegistrationResponseDTO();
        responseDTO.setUserId(savedUser.getId());
        responseDTO.setEmail(savedUser.getEmail());
        responseDTO.setRole(savedUser.getRole());
        if (savedUser.getCandidateProfile() != null) {
            responseDTO.setProfileId(savedUser.getCandidateProfile().getId());
        }

        URI location = uriBuilder.path("/user/{uuid}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }


    @PostMapping("/register/recruiter")
    @Transactional
    @Operation(summary = "Registrar novo usuário (Recrutador)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistrationResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content)
    })
    public ResponseEntity<?> registerRecruiter(
            @Valid @RequestBody UserRecruiterRequestDTO request,
            UriComponentsBuilder uriBuilder) {

        AppUser savedUser;
        savedUser = registrationService.registerRecruiter(request);

        RegistrationResponseDTO responseDTO = new RegistrationResponseDTO();
        responseDTO.setUserId(savedUser.getId());
        responseDTO.setEmail(savedUser.getEmail());
        responseDTO.setRole(savedUser.getRole());
        if (savedUser.getRecruiterProfile() != null) {
            responseDTO.setProfileId(savedUser.getRecruiterProfile().getId());
        }

        URI location = uriBuilder.path("/user/{uuid}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }


    @GetMapping
    @Operation(summary = "Listar todos os usuários ",
            description = "Retorna uma lista de todos os usuários cadastrados no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsersDTO.class))))
    })
    public List<UsersDTO> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(user -> new UsersDTO(user.getId(), user.getEmail()))
                .toList();
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    @Operation(summary = "Excluir usuário",
            description = "Exclui um usuário e seu perfil associado do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID (UUID) do usuário a ser excluído") @PathVariable UUID id) {
        if (!usersRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usersRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}