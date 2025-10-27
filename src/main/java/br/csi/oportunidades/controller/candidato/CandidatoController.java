package br.csi.oportunidades.controller.candidato;


import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.dto.candidato.CandidatoPublicResponseDTO;
import br.csi.oportunidades.service.candidato.CandidatoService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/candidatos")
@Tag(name = "Candidatos (Público)", description = "Endpoints públicos para visualização de candidatos")
public class CandidatoController {

    private final CandidatoService candidatoService;
    private ModelMapper modelMapper;

    @GetMapping
    @JsonView(Views.Publico.class)
    @Operation(summary = "Listar todos os candidatos (visão pública)",
            description = "Retorna uma lista de todos os candidatos com informações públicas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de candidatos",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CandidatoPublicResponseDTO.class))))
    })
    public List<CandidatoPublicResponseDTO> recuperarTodos() {
        return candidatoService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Publico.class)
    @Operation(summary = "Buscar candidato por ID (visão pública)",
            description = "Retorna informações públicas de um candidato específico pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalhes públicos do candidato",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CandidatoPublicResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado", content = @Content)
    })
    public CandidatoPublicResponseDTO recuperarCandidatoPorId(
            @Parameter(description = "ID do candidato a ser buscado") @PathVariable Long id) {
        return modelMapper.map(candidatoService.recuperarPorId(id), CandidatoPublicResponseDTO.class);
    }

}
