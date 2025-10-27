package br.csi.oportunidades.controller;


import br.csi.oportunidades.dto.OportunidadeRequestDTO;
import br.csi.oportunidades.dto.OportunidadeResponseDT0;
import br.csi.oportunidades.service.OportunidadeService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//Possibilita os acessos de recursos usando o usuário autenticado
//Pesquisar sobre mapeamento de nulls para o update
@RestController
@RequestMapping("/me/oportunidades")
@AllArgsConstructor
public class MeEmpresaController {


    private final OportunidadeService oportunidadeService;


    @PostMapping
    public OportunidadeResponseDT0 criarOportunidade(@RequestBody OportunidadeRequestDTO dto){
        return oportunidadeService.inserirOportunidade(dto);
    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<?> deletarOportunidade(@PathVariable Long id){
//        oportunidadeService.deleteOportunidade(id);
//        return ResponseEntity.notFound().build();
//    }



//    @GetMapping
//    public List<OportunidadeResponseDTO> listarOportunidades(){
//        return oportunidadeService.getOportunidades(UsuarioAutenticado.getUserId());
//    }

    @GetMapping("oloko")
    public ResponseEntity<?> ahaha(){

        System.out.println("Tipo de conta: " + UsuarioAutenticado.getTipoConta());
        return ResponseEntity.ok().body(UsuarioAutenticado.getTipoConta());

    }






}
