package br.csi.oportunidades.controller;


import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.model.candidato.FormacaoAcademica;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.service.CandidatoService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/candidato")
public class CandidatoController {

    private final CandidatoService candidatoService;




    @PostMapping("/experiencia")
    public ResponseEntity<ExperienciaProfissional> createExperiencia(@RequestBody ExperienciaProfissional ex) {
        ExperienciaProfissional experiencia = candidatoService.addExperienciaProfissional(ex);
        return ResponseEntity.status(HttpStatus.CREATED).body(experiencia);
        //Não retorna o location pq aqui não faz sentido acessar apenas uma experiencia. Voce SEMPRE vai querer todas, dps se quiser uma individualment faz a mágica no front
    }

    @GetMapping("/{id}/experiencia")
    public List<ExperienciaProfissional> getExperiencia(@PathVariable Long id) {
        return candidatoService.getExperienciasProfissionais(id);
    }

    @PostMapping("/formacao")
    public ResponseEntity<FormacaoAcademica> createFormacaoAcademica(@RequestBody FormacaoAcademica fa) {

        FormacaoAcademica formacao = candidatoService.addFormacaoAcademica(fa);
        return ResponseEntity.status(HttpStatus.CREATED).body(formacao);
        //Não tem location pelo mesmo motivo do de cima

    }
    @GetMapping("/{id}/formacao")
    public List<FormacaoAcademica> getFormacao(@PathVariable Long id) {
        return candidatoService.getFormacoesAcademicas(id);
    }


}
