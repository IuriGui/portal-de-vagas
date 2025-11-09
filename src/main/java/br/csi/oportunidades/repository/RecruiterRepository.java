package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.recruiter.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {
}
