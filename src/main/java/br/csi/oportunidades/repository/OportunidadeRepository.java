package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.oportunidade.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> {
}
