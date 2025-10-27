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
public class ExperienciaController {

    private final ExperienciaService experienciaService;


}
