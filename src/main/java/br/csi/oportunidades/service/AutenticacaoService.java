package br.csi.oportunidades.service;

import br.csi.oportunidades.infra.security.UserDetailsImpl;
import br.csi.oportunidades.model.appUser.AppUser;
import br.csi.oportunidades.model.appUser.UserRoles;
import br.csi.oportunidades.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public AutenticacaoService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUser user = usersRepository.findByEmailWithProfiles(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha incorretos"));

        UUID userId = user.getId();
        Long profileId = null;
        Long companyId = null;

        if (user.getRole() == UserRoles.CANDIDATE && user.getCandidateProfile() != null) {
            profileId = user.getCandidateProfile().getId();
        } else if (user.getRole() == UserRoles.RECRUITER && user.getRecruiterProfile() != null) {
            profileId = user.getRecruiterProfile().getId();
            companyId = user.getRecruiterProfile().getCompany().getId();
        }

        return new UserDetailsImpl(user, userId, profileId, companyId);
    }

}
