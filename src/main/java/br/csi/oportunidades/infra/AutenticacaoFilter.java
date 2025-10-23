package br.csi.oportunidades.infra;

import br.csi.oportunidades.service.AutenticacaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AutenticacaoFilter extends OncePerRequestFilter {

    private final TokenServiceJWT tokenService;
    private final AutenticacaoService autenticacaoService;
    public AutenticacaoFilter(TokenServiceJWT tokenService, AutenticacaoService autenticacaoService) {
        this.tokenService = tokenService;
        this.autenticacaoService = autenticacaoService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {
        System.out.println("Filtro Autenciacao e autorizacao");

        String token = recuperarToken(request);
        System.out.println("Token: " + token);

        if(token != null){
            String subject = this.tokenService.getSubject(token);
            System.out.println("Subject: "+subject);

            UserDetails userDetails = this.autenticacaoService.loadUserByUsername(subject);
            UsernamePasswordAuthenticationToken autorizacao =
                    new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(autorizacao);

        }
        filterChain.doFilter(request, response);

    }


    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token != null){
            return token.replace("Bearer ", "").trim();
        }
        return null;
    }

}
