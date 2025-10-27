package br.csi.oportunidades.infra;

import br.csi.oportunidades.infra.security.MyUserDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TokenServiceJWT {

    public String gerarToken(MyUserDetails userDetails, UUID id, Long tipoId){
        try {
            Algorithm algorithm = Algorithm.HMAC256("POO2");
            return JWT.create()
                    .withIssuer("API de Portal De Vagas")
                    .withSubject(userDetails.getUsername())
                    .withClaim("ROLE", userDetails.getAuthorities().stream().toList().get(0).toString())
                    .withClaim("idUsuario", id.toString())
                    .withClaim("tipoId", tipoId != null ? tipoId.toString() : null)
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token JWT",e);
        }
    }
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256("POO2");
            return JWT.require(algorithm)
                    .withIssuer("API de Portal De Vagas")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            throw new AuthorizationDeniedException("Token invalido ou expirado");
        }
    }

    public String getClaim(String token, String claimName) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("POO2");
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("API de Portal De Vagas")
                    .build()
                    .verify(token);
            return decodedJWT.getClaim(claimName).asString();
        } catch (JWTVerificationException e) {
            throw new AuthorizationDeniedException("Token invalido ou expirado");
        }
    }




}
