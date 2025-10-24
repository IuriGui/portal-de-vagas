package br.csi.oportunidades.service.candidato;

import br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO;
import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.inscricao.StatusInscricao;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.service.InscricaoService;
import br.csi.oportunidades.service.OportunidadeService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;
    private final InscricaoService inscricaoService;
    private final UsuarioAutenticado usuarioAutenticado;
    private final OportunidadeService oportunidadeService;

    public Candidato create(Candidato candidato) {
        return candidatoRepository.save(candidato);
    }

    public Candidato findByIdUsuario(UUID idUsuario) {
        assert candidatoRepository != null;
        return candidatoRepository.findByUsuarioId(idUsuario);
    }

    public Candidato findById(Long id) {

        Optional<Candidato> candidato = candidatoRepository.findById(id);

        if (candidato.isPresent()) {
            return candidato.get();
        } else {
            throw new NoSuchElementException("Candidato não encontrado");
        }
    }


    @Transactional
    public InscricaoResponseDTO inscreverEmOportunidade(Long id) {

        Inscricao inscricao = new Inscricao();
        Long candidatoId = UsuarioAutenticado.getUserId();
        Candidato c = findById(candidatoId);

        Oportunidade o = oportunidadeService.findById(id);
        
        if (c == null) {
            throw new NoSuchElementException("Candidato não encontrado para o usuário autenticado.");
        }

        if(o == null) {
            throw new NoSuchElementException("Oportuniadde não encontrada.");
        }

        inscricao.setCandidato(c);
        inscricao.setData_inscricao(new java.sql.Timestamp(System.currentTimeMillis()));
        inscricao.setOportunidade(o);

        System.out.println(inscricao.getOportunidade().getId() + "----" + inscricao.getCandidato().getId());

        Inscricao a = inscricaoService.findByOportunidadeIdAndCandidatoId(inscricao.getOportunidade().getId(), inscricao.getCandidato().getId());

        if(a != null) {
            throw new DataIntegrityViolationException("Usuario já se candidatou a essa vaga");
        }

        inscricao.setStatus(StatusInscricao.INSCRICAO_RECEBIDA);

        inscricao = inscricaoService.save(inscricao);

        return InscricaoResponseDTO.fromEntity(inscricao, inscricao.getCandidato().getNome(), o.getTitulo());
    }

    public void desinscreverEmOportunidade(Long idOportunidade) throws AccessDeniedException {

        if (UsuarioAutenticado.isUsuarioLogado(UsuarioAutenticado.getUserId())){
            Inscricao i = inscricaoService.findByOportunidadeIdAndCandidatoId(idOportunidade, UsuarioAutenticado.getUserId());
            if (i != null) {
                i.setStatus(StatusInscricao.CANCELADA);
                inscricaoService.save(i);
            } else {
                throw new NoSuchElementException("Inscrição não existe");
            }
        }



    }





}
