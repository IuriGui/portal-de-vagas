package br.csi.oportunidades.controller;


import br.csi.oportunidades.dto.oportunidade.OportunidadeRequestDTO;
import br.csi.oportunidades.dto.oportunidade.OportunidadeResponseDT0;
import br.csi.oportunidades.dto.oportunidade.OportunidadeUpdateDTO;
import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.service.OportunidadeService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import com.fasterxml.jackson.annotation.JsonView;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


//Possibilita os acessos de recursos usando o usuário autenticado
//Pesquisar sobre mapeamento de nulls para o update
@RestController
@RequestMapping("/me/oportunidades")
@AllArgsConstructor
@Tag(name = "Oportunidades logado", description = "Gerencia as oportunidades da empresa autenticada")
public class MeEmpresaController {

    private final OportunidadeService oportunidadeService;

    @PostMapping
    @JsonView(Views.Publico.class)
    @Operation(
            summary = "Criar nova oportunidade",
            description = "Cria uma nova oportunidade associada à empresa autenticada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Oportunidade criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OportunidadeResponseDT0.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content)
    })
    public ResponseEntity<OportunidadeResponseDT0> criarOportunidade(@Valid @RequestBody OportunidadeRequestDTO dto, UriComponentsBuilder uriBuilder){
        System.out.println("[CONTROLLER CRIAR OPORTUNIDADE]");

        OportunidadeResponseDT0 resp = oportunidadeService.inserirOportunidade(dto);


         URI loc = uriBuilder
                .path("/oportunidades/{id}")
                .buildAndExpand(resp.getId())
                .toUri();

        return ResponseEntity.created(loc).body(resp);
    }


    @DeleteMapping("/{idOportunidade}")
    @Operation(
            summary = "Excluir oportunidade",
            description = "Remove uma oportunidade específica criada pela empresa autenticada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Oportunidade removida com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Oportunidade não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content)
    })
    public ResponseEntity<?> deletarOportunidade(
            @Parameter(description = "ID da oportunidade a ser excluída", required = true, example = "1")
            @PathVariable Long idOportunidade){
        oportunidadeService.deletarOportunidade(idOportunidade);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @JsonView(Views.Publico.class)
    @Operation(
            summary = "Listar oportunidades da empresa autenticada",
            description = "Retorna todas as oportunidades criadas pela empresa atualmente autenticada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de oportunidades retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OportunidadeResponseDT0.class)))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content)
    })
    public ResponseEntity<List<OportunidadeResponseDT0>> listarOportunidades(){
        return ResponseEntity.ok(oportunidadeService.listarOportunidades(UsuarioAutenticado.getUserId()));
    }

    @PutMapping("/{idOportunidade}")
    @Operation(
            summary = "Atualizar oportunidade",
            description = "Atualiza os dados de uma oportunidade existente pertencente à empresa autenticada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Oportunidade atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OportunidadeResponseDT0.class))),
            @ApiResponse(responseCode = "404", description = "Oportunidade não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado", content = @Content)
    })
    public ResponseEntity<OportunidadeResponseDT0> atualizarOportunidade(
            @Parameter(description = "ID da oportunidade a ser atualizada", required = true, example = "1")
            @PathVariable Long idOportunidade, @RequestBody @Valid OportunidadeUpdateDTO dto){
        return ResponseEntity.ok(oportunidadeService.atualizarOportunidade(idOportunidade, dto));
    }



}
