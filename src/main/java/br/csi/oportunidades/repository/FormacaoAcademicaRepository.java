package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.model.candidato.FormacaoAcademica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormacaoAcademicaRepository extends JpaRepository<FormacaoAcademica, Long> {

    List<FormacaoAcademica> findByCandidatoId(Long candidatoId);

}
