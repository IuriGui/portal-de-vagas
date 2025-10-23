package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.usuario.UsersDTO;
import br.csi.oportunidades.model.Users;
import br.csi.oportunidades.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public Users save(Users user) {
        user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
        return usersRepository.save(user);
    }

    public void update(Users user, UUID id) {
        Optional<Users> optionalUser = usersRepository.findById(id);
        if (optionalUser.isPresent()) {
            usersRepository.save(user);
        } else {
            throw new RuntimeException("Usuario não encontrado.");
        }

    }

    public ResponseEntity delete(UUID id) {
        Optional<Users> optionalUser = usersRepository.findById(id);
        if (optionalUser.isPresent()) {
            usersRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public List<UsersDTO> findAll() {
        return usersRepository.findAll().stream()
                .map(u -> new UsersDTO(u.getId(), u.getEmail()))
                .collect(Collectors.toList());
    }


    public UsersDTO findById(UUID id) {
        Optional<Users> optionalUser = usersRepository.findById(id);
        return optionalUser.map(users -> new UsersDTO(users.getId(), users.getEmail())).orElse(null);
    }

    public UUID getIdByEmail(String email) {
        return usersRepository.findByEmail(email).getId();
    }

}
