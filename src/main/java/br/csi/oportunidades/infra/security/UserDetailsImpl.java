package br.csi.oportunidades.infra.security;

import br.csi.oportunidades.model.appUser.AppUser;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;


@Getter
public class UserDetailsImpl implements UserDetails {

    private final AppUser appUser;

    private final UUID userId;
    private final Long profileId;
    private final Long companyId;

    public UserDetailsImpl(AppUser appUser, UUID userId, Long profileId, Long companyId) {
        this.appUser = appUser;
        this.userId = userId;
        this.profileId = profileId;
        this.companyId = companyId;
    }

    @Override
    public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
        return this.appUser.getAuthorities();
    }
    @Override
    public String getPassword() {
        return this.appUser.getPassword();
    }
    @Override
    public String getUsername() {
        return this.appUser.getUsername();
    }
    @Override
    public boolean isAccountNonExpired() {
        return this.appUser.isAccountNonExpired();
    }
    @Override
    public boolean isAccountNonLocked() {
        return this.appUser.isAccountNonLocked();
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return this.appUser.isCredentialsNonExpired();
    }
    @Override
    public boolean isEnabled() {
        return this.appUser.isEnabled();
    }
}
