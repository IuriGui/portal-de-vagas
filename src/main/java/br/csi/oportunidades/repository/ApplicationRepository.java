package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a " +
            "JOIN FETCH a.opportunity o " +
            "JOIN FETCH o.company c " +
            "WHERE a.id = :applicationId")
    Optional<Application> findByIdWithOpportunityAndCompany(@Param("applicationId") Long applicationId);


    @Query("SELECT a FROM Application a " +
            "JOIN FETCH a.opportunity o " +
            "JOIN FETCH o.company c " +
            "WHERE a.candidate.id = :candidateId " +
            "ORDER BY a.appliedAt DESC")
    List<Application> findAllByCandidateIdWithDetails(@Param("candidateId") Long candidateId);

    Optional<Application> findByIdAndCandidateId(Long applicationId, Long candidateId);

}
