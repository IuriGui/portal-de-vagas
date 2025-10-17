package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
}
