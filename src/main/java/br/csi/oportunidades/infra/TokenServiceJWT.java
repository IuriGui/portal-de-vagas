package br.csi.oportunidades.infra;

import br.csi.oportunidades.infra.security.UserDetailsImpl;
import br.csi.oportunidades.infra.security.UserPrincipal;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TokenServiceJWT {

    private String secret;

    private final String ISSUER = "API de Portal De Vagas";
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public TokenServiceJWT(@Value("${jwt.secret.key:POO2}") String secret) {
        this.secret = secret;
        if (secret.equals("POO2")) {
            System.out.println("AVISO: Usando chave JWT insegura!");
        }
        this.algorithm = Algorithm.HMAC256(this.secret);
        this.verifier = JWT.require(this.algorithm)
                .withIssuer(ISSUER)
                .build();
    }

    public String gerarToken(UserDetailsImpl userDetails, UUID userId, Long profileId, Long companyId) {
        try {
            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Usuário sem 'role' definida"))
                    .getAuthority();

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userDetails.getUsername())
                    .withClaim("role", role)
                    .withClaim("uid", userId.toString())
                    .withClaim("pid", profileId)
                    .withClaim("cid", companyId)
                    .withExpiresAt(dataExpiracao())
                    .sign(this.algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public UserPrincipal validateAndParseToken(String token) {
        try {
            DecodedJWT decodedJWT = this.verifier.verify(token);

            String email = decodedJWT.getSubject();
            String role = decodedJWT.getClaim("role").asString();
            UUID userId = UUID.fromString(decodedJWT.getClaim("uid").asString());

            Long profileId = decodedJWT.getClaim("pid").isNull() //id do candidato ou recrutador
                    ? null : decodedJWT.getClaim("pid").asLong();

            Long companyId = decodedJWT.getClaim("cid").isNull() //id da company do recrutador
                    ? null : decodedJWT.getClaim("cid").asLong();


            String cleanRole = role.startsWith("ROLE_") ? role.substring(5) : role;


            return new UserPrincipal(userId, cleanRole, profileId, companyId, email);

        } catch (JWTVerificationException e) {
            throw new AuthorizationDeniedException("Token invalido ou expirado" + e);
        } catch (Exception e) {
            throw new AuthorizationDeniedException("Erro ao processar token" + e);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
