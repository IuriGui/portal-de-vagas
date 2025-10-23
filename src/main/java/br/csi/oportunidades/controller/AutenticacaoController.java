package br.csi.oportunidades.controller;

import br.csi.oportunidades.infra.TokenServiceJWT;
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

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenServiceJWT tokenServiceJWT;
    public AutenticacaoController(AuthenticationManager authenticationManager, TokenServiceJWT t) {
        this.authenticationManager = authenticationManager;
        this.tokenServiceJWT = t;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserLoginDTO u){
        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken(u.email(), u.senha());
            Authentication authenticated = authenticationManager.authenticate(authentication);

            User user = (User) authenticated.getPrincipal();
            String token = this.tokenServiceJWT.gerarToken(user);

            return ResponseEntity.ok().body(new DadosTokenJWT(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private record DadosTokenJWT(String token){}


    private record UserLoginDTO(String email, String senha){}

}
