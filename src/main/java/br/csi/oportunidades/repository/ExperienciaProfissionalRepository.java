package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienciaProfissionalRepository extends JpaRepository<ExperienciaProfissional, Long> {

    List<ExperienciaProfissional> findByCandidatoId(Long candidatoId);


}
