package br.csi.oportunidades.controller.autenticacao;

import br.csi.oportunidades.infra.TokenServiceJWT;
import br.csi.oportunidades.infra.security.MyUserDetails;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.service.InstituicaoService;
import br.csi.oportunidades.service.UsersService;
import br.csi.oportunidades.service.candidato.CandidatoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final UsersService usersService;
    private final CandidatoService candidatoService;
    private final TokenServiceJWT tokenServiceJWT;
    private final InstituicaoService instituicaoService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenServiceJWT t, UsersService usersService, CandidatoService candidatoService, InstituicaoService instituicaoService) {
        this.authenticationManager = authenticationManager;
        this.tokenServiceJWT = t;
        this.usersService = usersService;
        this.candidatoService = candidatoService;
        this.instituicaoService = instituicaoService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserLoginDTO u){
        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken(u.email(), u.senha());
            Authentication authenticated = authenticationManager.authenticate(authentication);
            
            UUID id = usersService.getIdByEmail(u.email);
            if (id == null) {
                return ResponseEntity.badRequest().body("Usuário não encontrado");
            }
            
            Instituicao i = instituicaoService.findByUser(id);
            Candidato c = candidatoService.findByIdUsuario(id);
            Long tipoId = null;

            if (c != null) {
                tipoId = c.getId();
            } else if (i != null) {
                tipoId = i.getId();
            }

            MyUserDetails userDetails = (MyUserDetails) authenticated.getPrincipal();
            String token = this.tokenServiceJWT.gerarToken(userDetails, id, tipoId);

            return ResponseEntity.ok().body(new DadosTokenJWT(token));
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            return ResponseEntity.status(401).body("Email ou senha incorretos");
        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException e) {
            return ResponseEntity.status(401).body("Usuário não encontrado");
        } catch (ClassCastException e) {
            return ResponseEntity.status(500).body("Erro interno do servidor: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }

    private record DadosTokenJWT(String token){}


    private record UserLoginDTO(String email, String senha){}

}
