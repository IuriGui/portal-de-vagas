package br.csi.oportunidades.util;


import br.csi.oportunidades.infra.TokenServiceJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsuarioAutenticado {

    @Autowired
    private TokenServiceJWT tokenServiceJWT;


    public UUID getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getCredentials() == null) {
            throw new RuntimeException("Usuário não autenticado ou token não encontrado");
        }

        try {
            String token = auth.getCredentials().toString();
            String idStr = tokenServiceJWT.getClaim(token, "id");
            
            if (idStr == null || idStr.isEmpty()) {
                throw new RuntimeException("ID do usuário não encontrado no token");
            }
            
            return UUID.fromString(idStr);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter ID do usuário: " + e.getMessage(), e);
        }
    }


    public boolean isUsuarioLogado(UUID userId) {
        return getUserId().equals(userId);
    }


}
