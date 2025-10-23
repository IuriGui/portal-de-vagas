package br.csi.oportunidades.service.candidato;

import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.model.candidato.FormacaoAcademica;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.repository.ExperienciaProfissionalRepository;
import br.csi.oportunidades.repository.FormacaoAcademicaRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@AllArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;

    private final FormacaoAcademicaRepository formacaoAcademica;
    private final UsuarioAutenticado usuarioAutenticado;

    public Candidato create(Candidato candidato) {
        return candidatoRepository.save(candidato);
    }

    public Candidato findByIdUsuario(UUID idUsuario) {
        return candidatoRepository.findByUsuarioId(idUsuario);
    }



}
