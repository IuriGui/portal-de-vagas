package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.candidato.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    Candidato findByUsuarioId(UUID usuarioId);


}
