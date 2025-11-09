package br.csi.oportunidades.service;


import br.csi.oportunidades.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;


}
