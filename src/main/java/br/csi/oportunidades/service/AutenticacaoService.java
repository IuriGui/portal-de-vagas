package br.csi.oportunidades.service;

import br.csi.oportunidades.model.Users;
import br.csi.oportunidades.repository.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsersRepository usersRepository;
    public AutenticacaoService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Users usuario = usersRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario ou senha incorretos");
        } else{
            UserDetails user =
                    User.withUsername(usuario.getEmail())
                            .password(usuario.getSenha())
                            .authorities(String.valueOf(usuario.getTipoConta()))
                            .build();
            return user;
        }



    }




}
