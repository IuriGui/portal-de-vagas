package br.csi.oportunidades.controller;

import br.csi.oportunidades.infra.TokenServiceJWT;
import br.csi.oportunidades.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
    private final TokenServiceJWT tokenServiceJWT;
    public AutenticacaoController(AuthenticationManager authenticationManager, TokenServiceJWT t, UsersService usersService) {
        this.authenticationManager = authenticationManager;
        this.tokenServiceJWT = t;
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserLoginDTO u){
        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken(u.email(), u.senha());
            Authentication authenticated = authenticationManager.authenticate(authentication);

            UUID id = usersService.getIdByEmail(u.email);

            User user = (User) authenticated.getPrincipal();
            String token = this.tokenServiceJWT.gerarToken(user, id);

            return ResponseEntity.ok().body(new DadosTokenJWT(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private record DadosTokenJWT(String token){}


    private record UserLoginDTO(String email, String senha){}

}
