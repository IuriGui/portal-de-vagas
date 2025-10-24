package br.csi.oportunidades.service.candidato;

import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.inscricao.StatusInscricao;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.service.InscricaoService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Inscricao> inscreverEmOportunidade(Long id) {

        Inscricao inscricao = new Inscricao();
        Long candidatoId = UsuarioAutenticado.getUserId();
        
        if (candidatoId == null) {
            throw new NoSuchElementException("Usuário não autenticado ou ID não encontrado.");
        }
        
        Candidato c = findById(candidatoId);
        System.out.println("Candidato encontrado: " + (c != null ? c.getId() : "null"));

        Oportunidade o = new Oportunidade();
        o.setId(id);
        
        if (c == null) {
            throw new NoSuchElementException("Candidato não encontrado para o usuário autenticado.");
        }


        inscricao.setCandidato(c);
        inscricao.setData_inscricao(new java.sql.Timestamp(System.currentTimeMillis()));
        inscricao.setOportunidade(o);

        Inscricao salva = inscricaoService.createInscricao(inscricao);

        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    public void desinscreverEmOportunidade(Long idOportunidade) throws AccessDeniedException {

        if (UsuarioAutenticado.isUsuarioLogado(UsuarioAutenticado.getUserId())){
            Inscricao i = inscricaoService.findByOportunidadeIdAndCandidatoId(idOportunidade, UsuarioAutenticado.getUserId());
            if (i != null) {
                i.setStatus(StatusInscricao.CANCELADA);
                inscricaoService.save(i);
            } else {
                throw new AccessDeniedException("Acesso ao recurso negado");
            }
        }



    }





}
