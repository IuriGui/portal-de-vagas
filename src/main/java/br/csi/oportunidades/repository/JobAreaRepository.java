package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.opportunity.JobArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobAreaRepository extends JpaRepository<JobArea, Long> {
}
