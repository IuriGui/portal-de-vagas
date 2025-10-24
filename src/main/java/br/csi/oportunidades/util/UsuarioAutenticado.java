package br.csi.oportunidades.util;


import br.csi.oportunidades.infra.TokenServiceJWT;
import br.csi.oportunidades.infra.security.MyUserDetails;
import br.csi.oportunidades.model.TipoConta;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class UsuarioAutenticado {

    @Autowired
    private TokenServiceJWT tokenServiceJWT;

    public static TipoConta getTipoConta() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("Usuário não autenticado");
            return null;
        }

        var principal = authentication.getPrincipal();
        if (principal instanceof MyUserDetails userDetails) {
            return userDetails.getTipoConta();
        }
        return null;
    }

    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("Usuário não autenticado");
            return null;
        }
        
        var principal = authentication.getPrincipal();
        if (principal instanceof MyUserDetails userDetails) {
            System.out.println("Username: " + userDetails.getUsername());
            System.out.println("Authorities: " + userDetails.getAuthorities());
            System.out.println("ID: " + userDetails.getId());
            return userDetails.getId();
        }
        
        System.out.println("Principal não é MyUserDetails: " + principal.getClass().getSimpleName());
        return null;
    }

    public static boolean isUsuarioLogado(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof MyUserDetails userDetails) {
            return Objects.equals(userDetails.getId(), id);
        }

        return false;
    }


    public boolean isUsuarioLogado(UUID userId) {
        return getUserId().equals(userId);
    }


}
