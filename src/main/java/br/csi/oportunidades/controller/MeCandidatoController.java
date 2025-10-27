package br.csi.oportunidades.controller;

import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO;
import br.csi.oportunidades.dto.inscricao.InscricaoResponseParaCandidatoDTO;
import br.csi.oportunidades.service.candidato.CandidatoService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/me/inscricoes")
@AllArgsConstructor
public class MeCandidatoController {

    private CandidatoService candidatoService;


    @GetMapping
    @JsonView(Views.Detalhado.class)
    public List<InscricaoResponseDTO> listarMinhasInscricoes(){
        return candidatoService.listarInscricoes(UsuarioAutenticado.getUserId());
    }

    @GetMapping("/{id}")
    @JsonView(Views.Detalhado.class)
    public ResponseEntity<InscricaoResponseDTO> recuperarMinhaInscricao(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(candidatoService.buscarInscricaoPorId(id));
    }

    @PostMapping("/{idOportunidade}/inscrever")
    @Transactional
    public ResponseEntity<InscricaoResponseParaCandidatoDTO> inscreverEmOportunidade(@PathVariable Long idOportunidade, UriComponentsBuilder uriBuilder){

        InscricaoResponseParaCandidatoDTO resp = candidatoService.inscreverEmOportunidade(idOportunidade);

        URI loc = uriBuilder
                .path("/me/inscricoes/{idInscricao}")
                .buildAndExpand(resp.getId())
                .toUri();
        return ResponseEntity.created(loc).body(resp);
    }

    @DeleteMapping("/{idOportunidade}/cancelar")
    public ResponseEntity<?> removerInscricoes(@PathVariable Long idOportunidade){
        candidatoService.cancelarInscricaoPorId(idOportunidade);
        return ResponseEntity.noContent().build();
    }



}
