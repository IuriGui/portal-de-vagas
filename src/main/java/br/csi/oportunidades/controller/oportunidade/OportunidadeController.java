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
@RequestMapping("/oportunidade")
@AllArgsConstructor
public class OportunidadeController {

    private final OportunidadeService oportunidadeService;


    @PostMapping
    public ResponseEntity<OportunidadeResponseDTO> create(@RequestBody OportunidadeRequestDTO dto, UriComponentsBuilder uriBuilder) {
        OportunidadeResponseDTO entity = oportunidadeService.createOportunidade(dto);
        URI location = uriBuilder.path("/oportunidade/{id}").buildAndExpand(entity.oportunidade_id()).toUri();
        return ResponseEntity.created(location).body(entity);
    }

    @GetMapping
    public ResponseEntity<List<OportunidadeResponseDTO>> getAll() {
        return ResponseEntity.ok(oportunidadeService.getOportunidades());
    }

    @GetMapping("{id}")
    public ResponseEntity<OportunidadeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(oportunidadeService.getOportunidade(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        oportunidadeService.deleteOportunidade(id);
        return ResponseEntity.noContent().build();
    }



}
