package br.csi.oportunidades.controller.oportunidade;


import br.csi.oportunidades.dto.oportunidade.OportunidadeRequestDTO;
import br.csi.oportunidades.dto.oportunidade.OportunidadeResponseDTO;
import br.csi.oportunidades.service.OportunidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario/oportunidade")
@AllArgsConstructor
@Tag(name = "Oportunidades (Empresa)", description = "Gerenciamento de vagas criadas pela empresa autenticada.")
public class OportunidadeEmpresaController {

    private final OportunidadeService oportunidadeService;


    //acessivel apenas para empresas
    @Operation(summary = "Cria uma nova oportunidade/vaga de emprego",
            description = "Permite que a empresa autenticada publique uma nova vaga. Requer ROLE_EMPRESA.")
    @ApiResponse(responseCode = "201", description = "Oportunidade criada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OportunidadeResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos.")
    @ApiResponse(responseCode = "403", description = "Acesso negado (Usuário não é EMPRESA).")
    @PostMapping
    public ResponseEntity<OportunidadeResponseDTO> criarOportunidade(@RequestBody @Valid OportunidadeRequestDTO dto, UriComponentsBuilder uriBuilder){
        OportunidadeResponseDTO entity = oportunidadeService.createOportunidade(dto);
        URI location = uriBuilder.path("/oportunidade/{id}").buildAndExpand(entity.oportunidade_id()).toUri();
        return ResponseEntity.created(location).body(entity);
    }

    @Operation(summary = "Lista todas as oportunidades criadas pela empresa logada",
            description = "Retorna um array com todas as vagas publicadas pela empresa autenticada. Requer ROLE_EMPRESA.")
    @ApiResponse(responseCode = "200", description = "Lista de oportunidades retornada com sucesso.",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = OportunidadeResponseDTO.class))))
    @ApiResponse(responseCode = "403", description = "Acesso negado (Usuário não é EMPRESA).")
    @GetMapping
    public ResponseEntity<List<OportunidadeResponseDTO>> getAllOportunidades(){
        return ResponseEntity.ok(oportunidadeService.getOportunidadesLogado());
    }

    @Operation(summary = "Exclui uma oportunidade/vaga",
            description = "Remove uma vaga específica. Apenas a empresa que criou a vaga pode excluí-la. Requer ROLE_EMPRESA.")
    @ApiResponse(responseCode = "204", description = "Oportunidade excluída com sucesso.")
    @ApiResponse(responseCode = "403", description = "Acesso negado (Usuário não é EMPRESA ou não é o proprietário da vaga).")
    @ApiResponse(responseCode = "404", description = "Oportunidade não encontrada.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOportunidade(@PathVariable Long id){
        oportunidadeService.deleteOportunidade(id);
        return ResponseEntity.noContent().build();
    }
}
