package br.csi.oportunidades.controller;


import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.service.CandidatoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/candidato")
public class CandidatoController {

    private final CandidatoService candidatoService;

    @PostMapping()
    public ExperienciaProfissional addExperienciaProfissional(@RequestBody ExperienciaProfissional ex) {
        return candidatoService.addExperienciaProfissional(ex);
    }


}
