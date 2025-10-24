package br.csi.oportunidades.controller.oportunidade;


import br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO;
import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.service.candidato.CandidatoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;

@RestController
@AllArgsConstructor
@RequestMapping("/oportunidade")
public class OportunidadeCandidatoController {

    private CandidatoService candidatoService;


    @PostMapping("/{id}/candidatar")
    public ResponseEntity<InscricaoResponseDTO> inscricaoResponseDTO(@PathVariable Long id, UriComponentsBuilder uriBuilder){


        InscricaoResponseDTO i = candidatoService.inscreverEmOportunidade(id);

        URI location = uriBuilder.path("/oportunidade/{id}").buildAndExpand(i.getId()).toUri();
        return ResponseEntity.created(location).body(i);
    }

    @DeleteMapping("/{idOportunidade}/retirar")
    public ResponseEntity<?> retirar(@PathVariable Long idOportunidade) throws AccessDeniedException {
        candidatoService.desinscreverEmOportunidade(idOportunidade);
        return ResponseEntity.noContent().build();
    }

}
