package br.csi.oportunidades.controller.candidato;

import br.csi.oportunidades.model.candidato.FormacaoAcademica;
import br.csi.oportunidades.service.candidato.CandidatoService;
import br.csi.oportunidades.service.candidato.FormacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@AllArgsConstructor
public class FormacaoController {

    private FormacaoService formacaoService;

    //FORMACAO
    @PostMapping("/me/formacao")
    public ResponseEntity<FormacaoAcademica> createFormacaoAcademica(@RequestBody FormacaoAcademica fa) {

        FormacaoAcademica formacao = formacaoService.addFormacaoAcademica(fa);
        return ResponseEntity.status(HttpStatus.CREATED).body(formacao);
        //Não tem location pelo mesmo motivo do de cima

    }

    @GetMapping("candidato/{id}/formacoes")
    public List<FormacaoAcademica> getFormacao(@PathVariable Long id) {
        return formacaoService.getFormacoesAcademicas(id);
    }

    @GetMapping("/me/formacoes")
    public List<FormacaoAcademica> getFormacao() {
        return formacaoService.getFormacoesAcademicas(null);
    }

    @PutMapping("/me/formacao/{id}")
    public ResponseEntity<FormacaoAcademica> updateFormacaoAcademica(@PathVariable Long id, @RequestBody FormacaoAcademica fa) throws AccessDeniedException {


        FormacaoAcademica f = formacaoService.updateFormacaoAcademica(fa, id);

        return ResponseEntity.ok().body(f);
    }

    @DeleteMapping("/me/formacao/{id}")
    private ResponseEntity deleteFormacao(@PathVariable Long id) throws AccessDeniedException {
        formacaoService.deleteFormacaoAcademica(id);
        return ResponseEntity.noContent().build();
    }

}
