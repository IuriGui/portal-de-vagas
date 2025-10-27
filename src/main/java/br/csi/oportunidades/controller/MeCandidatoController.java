package br.csi.oportunidades.controller;

import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.dto.usuario.InscricaoResponseDTO;
import br.csi.oportunidades.service.candidato.CandidatoService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/me/inscricoes")
@AllArgsConstructor
public class MeCandidatoController {

    private CandidatoService candidatoService;


    @GetMapping
    public List<InscricaoResponseDTO> listarMinhasInscricoes(){
        return candidatoService.listarInscricoes(UsuarioAutenticado.getUserId());
    }

    @PostMapping("/{id}/inscrever")
    @JsonView(Views.Publico.class)
    public InscricaoResponseDTO inscreverEmOportunidade(@PathVariable Long id){
        return candidatoService.inscreverEmOportunidade(id);
    }



}
