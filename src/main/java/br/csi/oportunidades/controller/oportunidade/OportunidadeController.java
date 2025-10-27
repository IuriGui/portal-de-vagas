package br.csi.oportunidades.controller.oportunidade;

import br.csi.oportunidades.dto.oportunidade.OportunidadeResponseDT0;
import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.service.OportunidadeService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oportunidades")
@AllArgsConstructor
public class OportunidadeController {

    private OportunidadeService oportunidadeService;


    @GetMapping
    @JsonView(Views.Publico.class)
    public ResponseEntity<List<OportunidadeResponseDT0>> listarMinhasInscricoes(){
        return ResponseEntity.ok(oportunidadeService.listarOportunidades());
    }

    @GetMapping("/{idOportunidade}")
    @JsonView(Views.Detalhado.class)
    public ResponseEntity<OportunidadeResponseDT0> listarOportunidadeDetalhada(@PathVariable Long idOportunidade){
        return ResponseEntity.ok(oportunidadeService.listarOportunidade(idOportunidade));
    }

}
