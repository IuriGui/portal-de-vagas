package br.csi.oportunidades.service;

import br.csi.oportunidades.infra.security.MyUserDetails;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.model.Users;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.repository.UsersRepository;
import br.csi.oportunidades.service.candidato.CandidatoService;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final MultipartConfigElement multipartConfigElement;
    private final UsersService usersService;
    private final CandidatoService candidatoService;
    private final InstituicaoService instituicaoService;

    public AutenticacaoService(UsersRepository usersRepository, MultipartConfigElement multipartConfigElement, UsersService usersService, CandidatoService candidatoService, InstituicaoService instituicaoService) {
        this.usersRepository = usersRepository;
        this.multipartConfigElement = multipartConfigElement;
        this.usersService = usersService;
        this.candidatoService = candidatoService;
        this.instituicaoService = instituicaoService;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        Users usuario = usersRepository.findByEmail(email);
//        if (usuario == null) {
//            throw new UsernameNotFoundException("Usuario ou senha incorretos");
//        } else{
//            UserDetails user =
//                    User.withUsername(usuario.getEmail())
//                            .password(usuario.getSenha())
//                            .authorities(String.valueOf(usuario.getTipoConta()))
//                            .build();
//            return user;
//        }
//
//
//
//    }


    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario ou senha incorretos");
        }
        
        Candidato c = candidatoService.findByIdUsuario(user.getId());
        Instituicao i = instituicaoService.findByUser(user.getId());
        
        Long tipoId = null;
        if (c != null) {
            tipoId = c.getId();
        } else if (i != null) {
            tipoId = i.getId();
        }
        
        return new MyUserDetails(
                user.getId(),
                user.getEmail(),
                user.getSenha(),
                tipoId,
                user.getTipoConta()
        );
    }


}
