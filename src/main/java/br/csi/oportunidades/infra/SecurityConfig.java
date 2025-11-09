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
                        .requestMatchers(HttpMethod.POST ,"/user/register/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/swagger-ui.html", "/api-docs/swagger-config", "/api-docs").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.GET, "/opportunities").permitAll()
                        .requestMatchers(HttpMethod.GET, "/opportunities/*").permitAll()

                        .requestMatchers(HttpMethod.GET, "/me/profile").hasRole("CANDIDATE")
                        .requestMatchers(HttpMethod.PUT, "/me/profile").hasRole("CANDIDATE")

                        .requestMatchers(HttpMethod.POST, "/me/profile/experience").hasRole("CANDIDATE")
                        .requestMatchers(HttpMethod.PUT, "/me/profile/experience/**").hasRole("CANDIDATE")
                        .requestMatchers(HttpMethod.POST, "/me/profile/academic").hasRole("CANDIDATE")
                        .requestMatchers(HttpMethod.PUT, "/me/profile/academic/**").hasRole("CANDIDATE")
                        .requestMatchers(HttpMethod.GET, "/me/applications").hasRole("CANDIDATE")
                        .requestMatchers(HttpMethod.DELETE, "/me/applications/**").hasRole("CANDIDATE")

                        .requestMatchers(HttpMethod.POST, "/opportunities/*/apply").hasRole("CANDIDATE")



                        .requestMatchers(HttpMethod.GET, "/opportunities/*/applications").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.PUT, "/applications/*/status").hasRole("RECRUITER")



                        .requestMatchers(HttpMethod.POST, "/opportunities").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.PUT, "/opportunities/**").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.DELETE, "/opportunities/**").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/opportunities/my-company").hasRole("RECRUITER")





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
