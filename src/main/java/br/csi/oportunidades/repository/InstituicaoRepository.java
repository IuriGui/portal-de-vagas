package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    Instituicao findByUsuarioId(UUID usuarioId);
}
