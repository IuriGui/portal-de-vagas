package br.csi.oportunidades.controller;


import br.csi.oportunidades.dto.OportunidadeRequestDTO;
import br.csi.oportunidades.dto.OportunidadeResponseDT0;
import br.csi.oportunidades.dto.OportunidadeUpdateDTO;
import br.csi.oportunidades.dto.Views;
import br.csi.oportunidades.service.OportunidadeService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


//Possibilita os acessos de recursos usando o usuário autenticado
//Pesquisar sobre mapeamento de nulls para o update
@RestController
@RequestMapping("/me/oportunidades")
@AllArgsConstructor
public class MeEmpresaController {

    private final OportunidadeService oportunidadeService;

    @PostMapping
    @JsonView(Views.Publico.class)
    public ResponseEntity<OportunidadeResponseDT0> criarOportunidade(@Valid @RequestBody OportunidadeRequestDTO dto, UriComponentsBuilder uriBuilder){
        System.out.println("[CONTROLLER CRIAR OPORTUNIDADE]");

        OportunidadeResponseDT0 resp = oportunidadeService.inserirOportunidade(dto);


         URI loc = uriBuilder
                .path("/oportunidades/{id}")
                .buildAndExpand(resp.getId())
                .toUri();

        return ResponseEntity.created(loc).body(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarOportunidade(@PathVariable Long id){
        oportunidadeService.deletarOportunidade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @JsonView(Views.Publico.class)
    public ResponseEntity<List<OportunidadeResponseDT0>> listarOportunidades(){
        return ResponseEntity.ok(oportunidadeService.listarOportunidades(UsuarioAutenticado.getUserId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OportunidadeResponseDT0> atualizarOportunidade(@PathVariable Long id, @RequestBody @Valid OportunidadeUpdateDTO dto){
        return ResponseEntity.ok(oportunidadeService.atualizarOportunidade(id, dto));
    }



}
