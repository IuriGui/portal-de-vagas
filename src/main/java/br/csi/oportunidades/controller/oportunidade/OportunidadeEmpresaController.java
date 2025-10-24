package br.csi.oportunidades.controller.oportunidade;


import br.csi.oportunidades.dto.oportunidade.OportunidadeRequestDTO;
import br.csi.oportunidades.dto.oportunidade.OportunidadeResponseDTO;
import br.csi.oportunidades.service.OportunidadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario/oportunidade")
@AllArgsConstructor
public class OportunidadeEmpresaController {

    private final OportunidadeService oportunidadeService;


    //acessivel apenas para empresas
    @PostMapping
    public ResponseEntity<OportunidadeResponseDTO> criarOportunidade(@RequestBody OportunidadeRequestDTO dto, UriComponentsBuilder uriBuilder){
        OportunidadeResponseDTO entity = oportunidadeService.createOportunidade(dto);
        URI location = uriBuilder.path("/oportunidade/{id}").buildAndExpand(entity.oportunidade_id()).toUri();
        return ResponseEntity.created(location).body(entity);
    }

    //acessivel apenas para empresas
    @GetMapping
    public ResponseEntity<List<OportunidadeResponseDTO>> getAllOportunidades(){
        return ResponseEntity.ok(oportunidadeService.getOportunidadesLogado());
    }

    //acessivel apenas para empresas
    @DeleteMapping("/oportunidade/{id}")
    public ResponseEntity<?> deleteOportunidade(@PathVariable Long id){
        oportunidadeService.deleteOportunidade(id);
        return ResponseEntity.noContent().build();
    }
}
