package br.csi.oportunidades.infra;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AutenticacaoFilter autenticacaoFilter;
    public SecurityConfig(AutenticacaoFilter autenticacaoFilter) {
        this.autenticacaoFilter = autenticacaoFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(crsf -> crsf.disable())
                .sessionManagement(sm-> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST ,"/user/createUser").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/candidatos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/candidatos/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/instituicoes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/instituicoes/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/oportunidades").permitAll()
                        .requestMatchers(HttpMethod.GET, "/oportunidades/*").permitAll()

                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.POST, "/me/oportunidades").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.DELETE, "/me/oportunidades/*").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.GET, "/me/oportunidades").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.PUT, "/me/oportunidades/*").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.GET, "/me/detalhes").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.PUT, "/me/detalhes/editar").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.GET, "/me/inscricoes").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.GET, "/me/inscricoes/*").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.DELETE, "/me/inscricoes/*/cancelar").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.POST, "/inscricoes/*/inscrever").hasRole("CANDIDATO")




                        .requestMatchers(HttpMethod.GET, "/me/inscricoes/").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.POST, "me/inscricoes/*/inscrever").hasRole("CANDIDATO")
                        .requestMatchers(HttpMethod.DELETE, "me/inscricoes/*/cancelar").hasRole("CANDIDATO")

                        .anyRequest().authenticated())
                .addFilterBefore(this.autenticacaoFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
