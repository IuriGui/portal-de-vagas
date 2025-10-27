package br.csi.oportunidades.controller;

import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.dto.candidato.CandidatoPublicResponseDTO;
import br.csi.oportunidades.dto.candidato.CandidatoResponseDTO;
import br.csi.oportunidades.dto.candidato.CandidatoUpdateRequestDTO;
import br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO;
import br.csi.oportunidades.dto.inscricao.InscricaoResponseParaCandidatoDTO;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.service.candidato.CandidatoService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/me")
@AllArgsConstructor
public class MeCandidatoController {

    private CandidatoService candidatoService;
    private ModelMapper modelMapper;



    @GetMapping("/detalhes")
    public ResponseEntity<Candidato> meusDetalhes(){
        return ResponseEntity.ok(candidatoService.recuperarPorId(UsuarioAutenticado.getUserId()));
    }

    @PutMapping("/detalhes/editar")
    @JsonView(Views.Publico.class)
    public ResponseEntity<CandidatoPublicResponseDTO> editarMeusDetalhes(@Valid @RequestBody CandidatoUpdateRequestDTO dto) throws AccessDeniedException {
        Candidato candidatoAtualizado = candidatoService.editarCandidatoPorId(UsuarioAutenticado.getUserId(), dto);
        return ResponseEntity.ok(modelMapper.map(candidatoAtualizado, CandidatoPublicResponseDTO.class));
    }





    @GetMapping
    @JsonView(Views.Detalhado.class)
    public List<InscricaoResponseDTO> listarMinhasInscricoes(){
        return candidatoService.listarInscricoes(UsuarioAutenticado.getUserId());
    }

    @GetMapping("/inscricoes/{id}")
    @JsonView(Views.Detalhado.class)
    public ResponseEntity<InscricaoResponseDTO> recuperarMinhaInscricao(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(candidatoService.buscarInscricaoPorId(id));
    }

    @PostMapping("/inscricoes/{idOportunidade}/inscrever")
    @Transactional
    public ResponseEntity<InscricaoResponseParaCandidatoDTO> inscreverEmOportunidade(@PathVariable Long idOportunidade, UriComponentsBuilder uriBuilder){

        InscricaoResponseParaCandidatoDTO resp = candidatoService.inscreverEmOportunidade(idOportunidade);

        URI loc = uriBuilder
                .path("/me/inscricoes/{idInscricao}")
                .buildAndExpand(resp.getId())
                .toUri();
        return ResponseEntity.created(loc).body(resp);
    }

    @DeleteMapping("/inscricoes/{idOportunidade}/cancelar")
    public ResponseEntity<?> removerInscricoes(@PathVariable Long idOportunidade){
        candidatoService.cancelarInscricaoPorId(idOportunidade);
        return ResponseEntity.noContent().build();
    }



}
