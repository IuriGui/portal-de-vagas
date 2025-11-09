package br.csi.oportunidades.infra.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

//Usuario do token
@Getter
public class UserPrincipal implements UserDetails {

    private UUID userId;
    private String userRole;
    private Long profileId;
    private Long companyId;
    private String email;

    public UserPrincipal(UUID userId, String userRole, Long profileId, Long companyId, String email) {
        this.userId = userId;
        this.userRole = userRole;
        this.profileId = profileId;
        this.companyId = companyId;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.userRole));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
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
