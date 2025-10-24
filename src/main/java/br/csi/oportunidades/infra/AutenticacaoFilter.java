package br.csi.oportunidades.infra;

import br.csi.oportunidades.infra.security.MyUserDetails;
import br.csi.oportunidades.model.TipoConta;
import br.csi.oportunidades.service.AutenticacaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
                                    FilterChain filterChain) throws IOException, ServletException {

        String token = recuperarToken(request);
        System.out.println("Token: " + token);

        if (token != null) {
            try {
                String subject = this.tokenService.getSubject(token);
                MyUserDetails userDetails = (MyUserDetails) this.autenticacaoService.loadUserByUsername(subject);

                // Cria Authentication e seta no contexto
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                System.out.println("Usuário autenticado: " + userDetails.getUsername());
                System.out.println("Authorities: " + userDetails.getAuthorities());
            } catch (Exception e) {
                System.err.println("Erro ao processar token: " + e.getMessage());
                e.printStackTrace();
                // Continua sem autenticação se houver erro no token
            }
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
