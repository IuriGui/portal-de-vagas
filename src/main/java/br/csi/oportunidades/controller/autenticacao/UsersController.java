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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Usuários", description = "Gerencia o cadastro e operações de usuários (candidatos e empresas)")
public class UsersController {

    private final UsersService usersService;
    private final CandidatoService candidatoService;
    private final InstituicaoService instituicaoService;


    @PostMapping("/createUser")
    @Transactional
    @Operation(summary = "Registrar novo usuário (Candidato ou Empresa)",
            description = "Cria um novo usuário base e, dependendo do 'tipoConta' (CANDIDATO ou EMPRESA), cria o perfil correspondente (Candidato ou Instituicao).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Users.class)),
                    headers = @Header(name = "Location", description = "URL para acessar o usuário criado")),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: email já existe, tipo de conta inválido, campos obrigatórios ausentes)", content = @Content)
    })
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
    @Operation(summary = "Atualizar usuário (Endpoint de Exemplo)",
            description = "Atualiza dados básicos de um usuário (email/senha). **Nota:** Este endpoint atualiza a entidade 'Users' diretamente. Para atualizar perfis (Candidato/Instituicao), endpoints específicos (ex: '/me/detalhes/editar') devem ser usados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado (retorna void)"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public void updateUser(@RequestBody Users user,
                           @Parameter(description = "ID (UUID) do usuário a ser atualizado") @PathVariable UUID id) {
        usersService.update(user, id);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Excluir usuário",
            description = "Exclui um usuário e seu perfil associado (candidato/empresa) do sistema, baseado no ID (UUID) do usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID (UUID) do usuário a ser excluído") @PathVariable UUID id) {
        usersService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários",
            description = "Retorna uma lista de todos os usuários cadastrados no sistema (em formato DTO).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsersDTO.class))))
    })
    public List<UsersDTO> getAllUsers() {
        return usersService.findAll();
    }
}
