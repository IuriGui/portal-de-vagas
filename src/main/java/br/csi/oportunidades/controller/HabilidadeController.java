package br.csi.oportunidades.controller;


import br.csi.oportunidades.model.candidato.Habilidade;
import br.csi.oportunidades.service.HabilidadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/habilidade")
@AllArgsConstructor
public class HabilidadeController {

    private final HabilidadeService habilidadeService;

    @PostMapping
    public ResponseEntity<Habilidade> createHabilidade(@RequestBody Habilidade habilidade, UriComponentsBuilder uriBuilder) {
        Habilidade h = habilidadeService.save(habilidade);
        URI location = uriBuilder.path("/habilidade/{id}").buildAndExpand(h.getId()).toUri();
        return ResponseEntity.created(location).body(h);
    }

    @GetMapping
    public ResponseEntity<List<Habilidade>> getAllHabilidades() {
        return ResponseEntity.ok(habilidadeService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habilidade> updateHabilidade(@RequestBody Habilidade habilidade, @PathVariable Long id) {
        return ResponseEntity.ok(habilidadeService.update(habilidade, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabilidade(@PathVariable Long id) {
        habilidadeService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
