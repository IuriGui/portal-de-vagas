package br.csi.oportunidades.controller;

import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.dto.candidato.CandidatoPublicResponseDTO;
import br.csi.oportunidades.dto.candidato.CandidatoResponseDTO;
import br.csi.oportunidades.dto.candidato.CandidatoUpdateRequestDTO;
import br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO;
import br.csi.oportunidades.dto.inscricao.InscricaoResponseParaCandidatoDTO;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.service.candidato.CandidatoService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
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
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/me")
@AllArgsConstructor
@Tag(name = "Candidato logado", description = "Gerencia as inscricoes e perfil do candidato")
public class MeCandidatoController {

    private CandidatoService candidatoService;
    private ModelMapper modelMapper;



    @GetMapping("/detalhes")
    @Operation(
            summary = "Ver meus detalhes completos",
            description = "Retorna os detalhes completos do perfil do candidato autenticado (incluindo dados sensíveis como email e CPF)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalhes do candidato",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Candidato.class))),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado", content = @Content)
    })
    public ResponseEntity<Candidato> meusDetalhes(){
        return ResponseEntity.ok(candidatoService.recuperarPorId(UsuarioAutenticado.getUserId()));
    }

    @PutMapping("/detalhes/editar")
    @JsonView(Views.Publico.class)
    @Operation(
            summary = "Atualizar meu perfil",
            description = "Atualiza as informações do perfil do candidato autenticado com base nos dados fornecidos no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CandidatoPublicResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado", content = @Content)
    })
    public ResponseEntity<CandidatoPublicResponseDTO> editarMeusDetalhes(@Valid @RequestBody CandidatoUpdateRequestDTO dto) throws AccessDeniedException {
        Candidato candidatoAtualizado = candidatoService.editarCandidatoPorId(UsuarioAutenticado.getUserId(), dto);
        return ResponseEntity.ok(modelMapper.map(candidatoAtualizado, CandidatoPublicResponseDTO.class));
    }





    @GetMapping("/inscricoes")
    @JsonView(Views.Detalhado.class)
    @Operation(
            summary = "Listar minhas inscrições",
            description = "Retorna uma lista de todas as inscrições (em oportunidades) realizadas pelo candidato autenticado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de inscrições",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = InscricaoResponseDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado", content = @Content)
    })
    public List<InscricaoResponseDTO> listarMinhasInscricoes(){
        return candidatoService.listarInscricoes(UsuarioAutenticado.getUserId());
    }

    @GetMapping("/inscricoes/{id}")
    @JsonView(Views.Detalhado.class)
    @Operation(
            summary = "Ver detalhes de uma inscrição",
            description = "Retorna os detalhes de uma inscrição específica (com base no ID da inscrição), desde que ela pertença ao candidato autenticado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalhes da inscrição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InscricaoResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado (inscrição não pertence ao usuário)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada", content = @Content)
    })
    public ResponseEntity<InscricaoResponseDTO> recuperarMinhaInscricao(@Parameter(description = "ID da inscrição a ser recuperada") @PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(candidatoService.buscarInscricaoPorId(id));
    }

    @PostMapping("/inscricoes/{idOportunidade}/inscrever")
    @Operation(
            summary = "Inscrever-se em uma oportunidade",
            description = "Cria uma nova inscrição para o candidato autenticado na oportunidade especificada pelo `idOportunidade`."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inscrição realizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InscricaoResponseParaCandidatoDTO.class)),
                    headers = @Header(name = "Location", description = "URL para acessar a inscrição criada")),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Oportunidade ou Candidato não encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Candidato já inscrito nesta oportunidade", content = @Content)
    })
    public ResponseEntity<InscricaoResponseParaCandidatoDTO> inscreverEmOportunidade(@Parameter(description = "ID da oportunidade na qual deseja se inscrever") @PathVariable Long idOportunidade, UriComponentsBuilder uriBuilder){

        InscricaoResponseParaCandidatoDTO resp = candidatoService.inscreverEmOportunidade(idOportunidade);

        URI loc = uriBuilder
                .path("/me/inscricoes/{idInscricao}")
                .buildAndExpand(resp.getId())
                .toUri();
        return ResponseEntity.created(loc).body(resp);
    }



    @DeleteMapping("/inscricoes/{idOportunidade}/cancelar")
    @Operation(
            summary = "Cancelar inscrição em uma oportunidade",
            description = "Remove/cancela a inscrição do candidato autenticado na oportunidade especificada pelo `idOportunidade`."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inscrição cancelada com sucesso", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada (pode ser pela oportunidade ou pelo candidato)", content = @Content)
    })
    public ResponseEntity<?> removerInscricoes(@Parameter(description = "ID da oportunidade da qual deseja cancelar a inscrição") @PathVariable Long idOportunidade){
        candidatoService.cancelarInscricaoPorId(idOportunidade);
        return ResponseEntity.noContent().build();
    }



}
