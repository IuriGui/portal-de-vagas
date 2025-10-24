package br.csi.oportunidades.controller.autenticacao;


import br.csi.oportunidades.dto.usuario.UserCreateRequest;
import br.csi.oportunidades.dto.usuario.UsersDTO;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.model.TipoConta;
import br.csi.oportunidades.model.Users;
import br.csi.oportunidades.service.candidato.CandidatoService;
import br.csi.oportunidades.service.InstituicaoService;
import br.csi.oportunidades.service.UsersService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UsersController {

    private final UsersService usersService;
    private final CandidatoService candidatoService;
    private final InstituicaoService instituicaoService;


    @PostMapping("/createUser")
    @Transactional
    public ResponseEntity<Users> register(@Valid @RequestBody UserCreateRequest request, UriComponentsBuilder uriBuilder) {
        //Cria usuário base
        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setSenha(request.getSenha());
        user.setTipoConta(request.getTipoConta());
        
        Users savedUser = usersService.save(user);

        if (savedUser.getTipoConta() == TipoConta.CANDIDATO) {
            //Cria candidato
            Candidato candidato = new Candidato();
            candidato.setNome(request.getNome());
            candidato.setTelefone(request.getTelefone());
            candidato.setDataNascimento(request.getDataNascimento());
            candidato.setCurriculoUrl(request.getCurriculoUrl());
            candidato.setUsuario(savedUser);
            
            candidatoService.create(candidato);
        } else if (savedUser.getTipoConta() == TipoConta.EMPRESA) {
            //Cria instituição
            Instituicao instituicao = new Instituicao();
            instituicao.setNomeFantasia(request.getNomeFantasia());
            instituicao.setDescricao(request.getDescricao());
            instituicao.setTelefone(request.getTelefone());
            instituicao.setUsuario(savedUser);
            
            instituicaoService.create(instituicao);
        } else {
            return ResponseEntity.badRequest().build();
        }

        URI location = uriBuilder.path("/user/{uuid}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).body(savedUser);
    }


    @PutMapping("/update/{id}")
    public void updateUser(@RequestBody Users user, @PathVariable UUID id) {
        usersService.update(user, id);
    }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
       usersService.delete(id);
       return ResponseEntity.noContent().build();
   }

   @GetMapping
    public List<UsersDTO> getAllUsers() {
        return usersService.findAll();
   }



}
