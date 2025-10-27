package br.csi.oportunidades.controller;


import br.csi.oportunidades.model.candidato.Habilidade;
import br.csi.oportunidades.service.HabilidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/habilidade")
@AllArgsConstructor
@Tag(name = "Habilidades", description = "Gerencia o cadastro de habilidades (para candidatos)")
public class HabilidadeController {

    private final HabilidadeService habilidadeService;

    @PostMapping
    @Operation(summary = "Criar nova habilidade", description = "Cadastra uma nova habilidade no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Habilidade criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Habilidade.class)),
                    headers = @Header(name = "Location", description = "URL para acessar a habilidade criada")),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<Habilidade> createHabilidade(@RequestBody Habilidade habilidade, UriComponentsBuilder uriBuilder) {
        Habilidade h = habilidadeService.save(habilidade);
        URI location = uriBuilder.path("/habilidade/{id}").buildAndExpand(h.getId()).toUri();
        return ResponseEntity.created(location).body(h);
    }

    @GetMapping
    @Operation(summary = "Listar todas as habilidades", description = "Retorna uma lista com todas as habilidades cadastradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de habilidades",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Habilidade.class))))
    })
    public ResponseEntity<List<Habilidade>> getAllHabilidades() {
        return ResponseEntity.ok(habilidadeService.findAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma habilidade", description = "Atualiza os dados de uma habilidade existente com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habilidade atualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Habilidade.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Habilidade não encontrada", content = @Content)
    })
    public ResponseEntity<Habilidade> updateHabilidade(
            @RequestBody Habilidade habilidade,
            @Parameter(description = "ID da habilidade a ser atualizada") @PathVariable Long id) {
        return ResponseEntity.ok(habilidadeService.update(habilidade, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma habilidade", description = "Remove uma habilidade do sistema com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Habilidade excluída com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Habilidade não encontrada", content = @Content)
    })
    public ResponseEntity<Void> deleteHabilidade(
            @Parameter(description = "ID da habilidade a ser excluída") @PathVariable Long id) {
        habilidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
