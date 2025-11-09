package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<AppUser, UUID> {

    @Query("SELECT u FROM AppUser u " +
            "LEFT JOIN FETCH u.candidateProfile c " +
            "LEFT JOIN FETCH u.recruiterProfile r " +
            "LEFT JOIN FETCH r.company " +
            "WHERE u.email = :email")
    Optional<AppUser> findByEmailWithProfiles(@Param("email") String email);

    Optional<AppUser> findByEmail(String email);
}
