package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.candidato.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
}
