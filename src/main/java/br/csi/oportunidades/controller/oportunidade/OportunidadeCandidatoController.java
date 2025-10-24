package br.csi.oportunidades.controller.oportunidade;


import br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO;
import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.service.candidato.CandidatoService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@AllArgsConstructor
@RequestMapping("/oportunidade")
public class OportunidadeCandidatoController {

    private CandidatoService candidatoService;


    @PostMapping("/{id}/candidatar")
    public ResponseEntity<Inscricao> inscricaoResponseDTO(@PathVariable Long id){
        return candidatoService.inscreverEmOportunidade(id);
    }

    @DeleteMapping("/{idOportunidade}/retirar")
    public ResponseEntity<?> retirar(@PathVariable Long idOportunidade) throws AccessDeniedException {
        candidatoService.desinscreverEmOportunidade(idOportunidade);
        return ResponseEntity.noContent().build();
    }

}
