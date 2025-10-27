package br.csi.oportunidades.repository;

import br.csi.oportunidades.model.inscricao.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

//    @Query("SELECT new br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO(" +
//            "i.id, i.candidato.nome, i.oportunidade.titulo, i.data_inscricao, i.status) " +
//            "FROM Inscricao i " +
//            "WHERE i.candidato.id = :candidatoId")
//    List<InscricaoResponseDTO> findInscricoesByCandidato(@Param("candidatoId") Long candidatoId);
//
//
//    @Query("SELECT new br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO(" +
//            "i.id, i.candidato.nome, i.oportunidade.titulo, i.data_inscricao, i.status) " +
//            "FROM Inscricao i " +
//            "WHERE i.oportunidade.id = :oportunidadeId")
//    List<InscricaoResponseDTO> findInscricoesByOportunidade(@Param("oportunidadeId") Long oportunidadeId);

    List<Inscricao> findInscricaosByCandidato_Id(Long candidato_Id);

    Optional<Inscricao> findByOportunidadeIdAndCandidatoId(Long oportunidadeId, Long candidatoId);
}

