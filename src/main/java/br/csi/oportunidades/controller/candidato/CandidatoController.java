package br.csi.oportunidades.controller.candidato;


import br.csi.oportunidades.service.candidato.CandidatoService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/candidato")
public class CandidatoController {

    private final CandidatoService candidatoService;
    private final UsuarioAutenticado usuarioAutenticado;


}
