package br.csi.oportunidades.controller.oportunidade;

import br.csi.oportunidades.dto.oportunidade.OportunidadeResponseDT0;
import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.service.OportunidadeService;
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
@RequestMapping("/oportunidades")
@AllArgsConstructor
@Tag(name = "Oportunidades (Público)", description = "Endpoints públicos para visualização de oportunidades de vaga")
public class OportunidadeController {

    private OportunidadeService oportunidadeService;


    @GetMapping
    @JsonView(Views.Publico.class)
    @Operation(summary = "Listar todas as oportunidades (visão pública)",
            description = "Retorna uma lista de todas as oportunidades de vaga disponíveis com informações públicas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de oportunidades",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OportunidadeResponseDT0.class))))
    })
    public ResponseEntity<List<OportunidadeResponseDT0>> listarMinhasInscricoes(){
        return ResponseEntity.ok(oportunidadeService.listarOportunidades());
    }

    @GetMapping("/{idOportunidade}")
    @JsonView(Views.Detalhado.class)
    @Operation(summary = "Buscar oportunidade por ID (visão detalhada)",
            description = "Retorna informações detalhadas de uma oportunidade de vaga específica pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalhes da oportunidade",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OportunidadeResponseDT0.class))),
            @ApiResponse(responseCode = "404", description = "Oportunidade não encontrada", content = @Content)
    })
    public ResponseEntity<OportunidadeResponseDT0> listarOportunidadeDetalhada(
            @Parameter(description = "ID da oportunidade a ser buscada") @PathVariable Long idOportunidade){
        return ResponseEntity.ok(oportunidadeService.listarOportunidade(idOportunidade));
    }

}
