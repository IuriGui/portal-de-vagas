package br.csi.oportunidades.controller.candidato;

import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.service.candidato.CandidatoService;
import br.csi.oportunidades.service.candidato.ExperienciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/candidato")
public class ExperienciaController {

    private final ExperienciaService experienciaService;

    @PostMapping("/me/experiencia")
    public ResponseEntity<ExperienciaProfissional> createExperiencia(@RequestBody ExperienciaProfissional ex) {
        ExperienciaProfissional experiencia = experienciaService.addExperienciaProfissional(ex);
        return ResponseEntity.status(HttpStatus.CREATED).body(experiencia);
        //Não retorna o location pq aqui não faz sentido acessar apenas uma experiencia. Voce SEMPRE vai querer todas, dps se quiser uma individualment faz a mágica no front
    }

    @GetMapping("/{id}/experiencia")
    public List<ExperienciaProfissional> getExperiencias(@PathVariable Long id) {
        return experienciaService.getExperienciasProfissionais(id);
    }

    @GetMapping("/me/experiencia")
    public List<ExperienciaProfissional> getExperiencias() {
        return experienciaService.getExperienciasProfissionais(null);
    }


    @PutMapping("/me/experiencia/{id}")
    public ResponseEntity updateExperiencia(@PathVariable Long id, @RequestBody ExperienciaProfissional ex) throws AccessDeniedException {
        return experienciaService.updateExperienciaProfissional(ex, id);
    }

    @DeleteMapping("/me/experiencia/{id}")
    public ResponseEntity deleteExperiencia(@PathVariable Long id) throws AccessDeniedException {
        experienciaService.deleteExperienciaProfissional(id);
        return ResponseEntity.noContent().build();
    }


}
