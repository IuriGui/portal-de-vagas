package br.csi.oportunidades.controller.empresa;

import br.csi.oportunidades.dto.InstituicaoResponseDTO;
import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.service.InstituicaoService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/instituicoes")
@Tag(name = "Instituições (Público)", description = "Endpoints públicos para visualização de instituições")
public class InstituicaoController {


    private InstituicaoService instituicaoService;


    @GetMapping
    @JsonView(Views.Publico.class)
    @Operation(summary = "Listar todas as instituições (visão pública)",
            description = "Retorna uma lista de todas as instituições com informações públicas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de instituições",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = InstituicaoResponseDTO.class))))
    })
    public ResponseEntity<List<InstituicaoResponseDTO>> getAll() {
        return ResponseEntity.ok(instituicaoService.getInstituicoes());
    }


    @GetMapping("/{id}")
    @JsonView(Views.Publico.class)
    @Operation(summary = "Buscar instituição por ID",
            description = "Retorna informações de uma instituição específica pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "instituição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InstituicaoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Instituição não encontrada", content = @Content)
    })
    public InstituicaoResponseDTO getInstituicao(
            @Parameter(description = "ID da instituição a ser buscada") @PathVariable Long id) {
        return instituicaoService.getInstituicao(id);
    }



}
