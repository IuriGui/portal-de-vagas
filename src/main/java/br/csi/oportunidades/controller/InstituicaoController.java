package br.csi.oportunidades.controller;

import br.csi.oportunidades.dto.instituicao.InstituicaoResponseDTO;

import br.csi.oportunidades.service.InstituicaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/instituicao")
public class InstituicaoController {


    private InstituicaoService instituicaoService;


    @GetMapping
    public ResponseEntity<List<InstituicaoResponseDTO>> getAll() {
        return ResponseEntity.ok(instituicaoService.getInstituicoes());
    }


    @GetMapping("{id}")
    public InstituicaoResponseDTO getInstituicao(@PathVariable Long id) {
        return instituicaoService.getInstituicao(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteInstituicao(@RequestParam Long id) {
        instituicaoService.deleteInstituicao(id);
        return ResponseEntity.ok().build();
    }



}
