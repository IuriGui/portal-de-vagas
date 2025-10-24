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
import java.util.Objects;

@Service
@AllArgsConstructor
public class ExperienciaService {

    private final ExperienciaProfissionalRepository experienciaProfissionalRepository;
    private final CandidatoService candidatoService;


    public ExperienciaProfissional addExperienciaProfissional(ExperienciaProfissional ep) {

        Candidato c = candidatoService.findById(UsuarioAutenticado.getUserId());
        ep.setCandidato(c);

        return experienciaProfissionalRepository.save(ep);
    }

    public List<ExperienciaProfissional> getExperienciasProfissionais(Long idUCandidato) {

        if(idUCandidato == null) {
            return experienciaProfissionalRepository.findByCandidatoId(UsuarioAutenticado.getUserId());
        }
        return experienciaProfissionalRepository.findByCandidatoId(idUCandidato);
    }

    public ResponseEntity<?> updateExperienciaProfissional(ExperienciaProfissional nova, Long id) throws AccessDeniedException {

        Long tipo_id = UsuarioAutenticado.getUserId();

        Candidato c = candidatoService.findById(tipo_id);

        ExperienciaProfissional exExistente = experienciaProfissionalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Experiencia não encontrada"));

        if (!Objects.equals(exExistente.getCandidato().getId(), tipo_id)) {
            throw new AccessDeniedException("Você só pode alterar suas próprias experiências profissionais.");
        }

        exExistente.setCandidato(c);
        exExistente.setCargo(nova.getCargo());
        exExistente.setEmpresa(nova.getEmpresa());
        exExistente.setDataInicio(nova.getDataInicio());
        exExistente.setDataFim(nova.getDataFim());

        return ResponseEntity.ok(experienciaProfissionalRepository.save(exExistente));

    }

    public void deleteExperienciaProfissional(Long id) {

        if(!experienciaProfissionalRepository.existsById(id)){
            throw new NoSuchElementException("Experiencia não encontrada");
        }

        experienciaProfissionalRepository.deleteById(id);
    }


}
