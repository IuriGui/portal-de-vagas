package br.csi.oportunidades.controller;

import br.csi.oportunidades.service.candidato.CandidatoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/me")
public class MeCandidatoController {

    private CandidatoService candidatoService;


//    @GetMapping("/inscricoes")
//    public List<InscricaoResponseDTO> getMySubscription(){
//        return candidatoService.findAllMySubscriptions();
//    }



}
