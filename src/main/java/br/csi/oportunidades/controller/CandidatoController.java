package br.csi.oportunidades.controller;


import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.service.CandidatoService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/candidato")
public class CandidatoController {

    private final CandidatoService candidatoService;


//    @GetMapping("/experiencia")
//    public Candidato getExperiencia() {
//        UUID userId = usuarioAutenticado.getUserId();
//        return candidatoService.findByIdUsuario(userId);
//    }

    @PostMapping("/experiencia")
    public ResponseEntity<ExperienciaProfissional> createExperiencia(@RequestBody ExperienciaProfissional ex) {
        ExperienciaProfissional experiencia = candidatoService.addExperienciaProfissional(ex);
        return ResponseEntity.status(HttpStatus.CREATED).body(experiencia);
        //Não retorna o location pq aqui não faz sentido acessar apenas uma experiencia. Voce SEMPRE vai querer todas, dps se quiser uma individualment faz a mágica no front
    }


}
