package br.csi.oportunidades.infra.security;

import br.csi.oportunidades.model.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MyUserDetails implements UserDetails {

    private UUID idUsuarioGeral;
    private String username;
    private String password;
    private Long id;
    private TipoConta tipoConta;

    public MyUserDetails(UUID idUsuarioGeral, String username, String password, Long id, TipoConta tipoConta) {
        this.idUsuarioGeral = idUsuarioGeral;
        this.username = username;
        this.password = password;
        this.id = id;
        this.tipoConta = tipoConta;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.tipoConta));
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
