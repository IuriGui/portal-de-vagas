package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.candidate.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {


    Candidate findByUserId(UUID userId);


    @Query("SELECT c FROM Candidate c " +
            "LEFT JOIN FETCH c.user " +
            "LEFT JOIN FETCH c.address " +
            "LEFT JOIN FETCH c.academicHistory " +
            "LEFT JOIN FETCH c.experiences " +
            "LEFT JOIN FETCH c.skills " +
            "WHERE c.id = :profileId")
    Optional<Candidate> findProfileById(@Param("profileId") Long profileId);


}
