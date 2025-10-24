package br.csi.oportunidades.repository;

import br.csi.oportunidades.dto.oportunidade.OportunidadeResponseDTO;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> {

    List<Oportunidade> findByInstituicaoId(Long id);

}
