package br.csi.oportunidades.controller.candidato;


import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.service.candidato.CandidatoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/candidato")
public class CandidatoController {

    private final CandidatoService candidatoService;



//    @PostMapping("/me/inscrever/{id}")
//    public ResponseEntity<Inscricao> inscrever(@PathVariable Long id) {
//        return candidatoService.inscreverEmOportunidade(id);
//    }


}
