package br.csi.oportunidades.controller.autenticacao;

import br.csi.oportunidades.infra.TokenServiceJWT;
import br.csi.oportunidades.infra.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/login")
@Tag(name = "Usuários e Autenticação", description = "Faz o login")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenServiceJWT tokenServiceJWT;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenServiceJWT t) {
        this.authenticationManager = authenticationManager;
        this.tokenServiceJWT = t;
    }

    @PostMapping
    @Operation(
            summary = "Realizar login e obter token JWT",
            description = "Autentica um usuário com email e senha. Se as credenciais forem válidas, retorna um token JWT para ser usado no header 'Authorization' (Bearer Token) das próximas requisições."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticação bem-sucedida",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DadosTokenJWT.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais inválidas (senha incorreta)",
                    content = @Content
            )
    })
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO u) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(u.email(), u.senha());
            Authentication authenticated = authenticationManager.authenticate(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authenticated.getPrincipal();

            UUID userId = userDetails.getUserId();
            Long profileId = userDetails.getProfileId();
            Long companyId = userDetails.getCompanyId(); // <-- Agora temos o companyId

            String token = this.tokenServiceJWT.gerarToken(
                    userDetails, // Passa o objeto 'pesado'
                    userId,
                    profileId,
                    companyId
            );

            return ResponseEntity.ok().body(new DadosTokenJWT(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Email ou senha incorretos");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("Usuário ou senha incorretos");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }

    @Schema(description = "Token JWT de autenticação")
    private record DadosTokenJWT(
            @Schema(description = "Token JWT gerado", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
            String token
    ) {}

    @Schema(description = "Credenciais de login do usuário")
    private record UserLoginDTO(
            @Schema(description = "Email do usuário", example = "usuario@email.com")
            String email,
            @Schema(description = "Senha do usuário", example = "senha123")
            String senha
    ) {}
}