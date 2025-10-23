package br.csi.oportunidades.service.candidato;


import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.repository.ExperienciaProfissionalRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ExperienciaService {

    private final ExperienciaProfissionalRepository experienciaProfissionalRepository;
    private final CandidatoService candidatoService;
    private final UsuarioAutenticado usuarioAutenticado;

    public ExperienciaProfissional addExperienciaProfissional(ExperienciaProfissional ep) {

        Candidato c = candidatoService.findByIdUsuario(usuarioAutenticado.getUserId());
        ep.setCandidato(c);

        return experienciaProfissionalRepository.save(ep);
    }

    public List<ExperienciaProfissional> getExperienciasProfissionais(Long idUCandidato) {

        if(idUCandidato == null) {
            Candidato c = candidatoService.findByIdUsuario(usuarioAutenticado.getUserId());
            return experienciaProfissionalRepository.findByCandidatoId(c.getId());
        }
        return experienciaProfissionalRepository.findByCandidatoId(idUCandidato);
    }

    public ResponseEntity<?> updateExperienciaProfissional(ExperienciaProfissional nova, Long id) throws AccessDeniedException {

        Candidato c = candidatoService.findByIdUsuario(usuarioAutenticado.getUserId());
        if(!usuarioAutenticado.isUsuarioLogado(c.getUsuario().getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ExperienciaProfissional exExistente = experienciaProfissionalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Experiencia não encontrada"));

        exExistente.setCargo(nova.getCargo());
        exExistente.setEmpresa(nova.getEmpresa());
        exExistente.setDataInicio(nova.getDataInicio());
        exExistente.setDataFim(nova.getDataFim());
        return ResponseEntity.ok(exExistente);

    }

    public void deleteExperienciaProfissional(Long id) {

        if(!experienciaProfissionalRepository.existsById(id)){
            throw new NoSuchElementException("Experiencia não encontrada");
        }

        experienciaProfissionalRepository.deleteById(id);
    }


}
