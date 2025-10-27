package br.csi.oportunidades.controller.candidato;


import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.dto.candidato.CandidatoPublicResponseDTO;
import br.csi.oportunidades.service.candidato.CandidatoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/candidatos")
public class CandidatoController {

    private final CandidatoService candidatoService;
    private ModelMapper modelMapper;

    @GetMapping
    @JsonView(Views.Publico.class)
    public List<CandidatoPublicResponseDTO> recuperarTodos() {
        return candidatoService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Publico.class)
    public CandidatoPublicResponseDTO recuperarCandidatoPorId(@PathVariable Long id) {
        return modelMapper.map(candidatoService.recuperarPorId(id), CandidatoPublicResponseDTO.class);
    }

}
