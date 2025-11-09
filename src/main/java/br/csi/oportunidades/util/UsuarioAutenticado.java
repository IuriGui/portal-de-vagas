package br.csi.oportunidades.util;

import br.csi.oportunidades.infra.security.UserPrincipal; // IMPORTANTE: Mudar para UserPrincipal
import br.csi.oportunidades.model.appUser.UserRoles; // Importe seu Enum de Roles
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class UsuarioAutenticado {

    private static UserPrincipal getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserPrincipal) {
            return (UserPrincipal) principal;
        }
        return null;
    }


    public static UserRoles getRole() {
        UserPrincipal principal = getPrincipal();
        if (principal != null) {
            try {
                return UserRoles.valueOf(principal.getUserRole());
            } catch (IllegalArgumentException e) {
                System.err.println("Role no token não é um UserRoles válido: " + principal.getUserRole());
                return null;
            }
        }
        return null;
    }

    public static Long getProfileId() {
        UserPrincipal principal = getPrincipal();
        if (principal != null) {
            return principal.getProfileId();
        }
        return null;
    }

    public static Long getCompanyId() {
        UserPrincipal principal = getPrincipal();
        if (principal != null) {
            return principal.getCompanyId();
        }
        return null;
    }


    public static UUID getAppUserId() {
        UserPrincipal principal = getPrincipal();
        if (principal != null) {
            return principal.getUserId();
        }
        return null;
    }

    public static boolean isUsuarioLogado(Long profileId) {
        UserPrincipal principal = getPrincipal();
        if (principal != null) {
            return Objects.equals(principal.getProfileId(), profileId);
        }
        return false;
    }
    public static boolean isUsuarioLogado(UUID appUserId) {
        UserPrincipal principal = getPrincipal();
        if (principal != null) {
            return Objects.equals(principal.getUserId(), appUserId);
        }
        return false;
    }
}