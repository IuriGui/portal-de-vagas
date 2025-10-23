package br.csi.oportunidades.service.candidato;

import br.csi.oportunidades.model.Inscricao;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.model.candidato.FormacaoAcademica;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.repository.ExperienciaProfissionalRepository;
import br.csi.oportunidades.repository.FormacaoAcademicaRepository;
import br.csi.oportunidades.service.InscricaoService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.event.ComponentAdapter;
import java.nio.file.AccessDeniedException;
import java.util.List;
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


    public ResponseEntity<Inscricao> inscrever(Inscricao inscricao) {
        Candidato c = findByIdUsuario(usuarioAutenticado.getUserId());
        if (c == null) {
            throw new RuntimeException("Candidato não encontrado para o usuário autenticado.");
        }
        inscricao.setCandidato_id(c.getId());
        inscricao.setData_inscricao(new java.sql.Timestamp(System.currentTimeMillis()));

        Inscricao salva = inscricaoService.createInscricao(inscricao);

        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }



}
