package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.candidato.Habilidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabilidadeRepository extends JpaRepository<Habilidade, Long> {
}
