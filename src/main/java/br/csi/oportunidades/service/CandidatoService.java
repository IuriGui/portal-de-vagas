package br.csi.oportunidades.service;

import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.model.candidato.FormacaoAcademica;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.repository.ExperienciaProfissionalRepository;
import br.csi.oportunidades.repository.FormacaoAcademicaRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;
    private final ExperienciaProfissionalRepository experienciaProfissional;
    private final FormacaoAcademicaRepository formacaoAcademica;
    private final UsuarioAutenticado usuarioAutenticado;

    public Candidato create(Candidato candidato) {
        return candidatoRepository.save(candidato);
    }

    public Candidato findByIdUsuario(UUID idUsuario) {
        return candidatoRepository.findByUsuarioId(idUsuario);
    }

    public ExperienciaProfissional addExperienciaProfissional(ExperienciaProfissional ep) {

        ep.setCandidato(candidatoRepository.findByUsuarioId(usuarioAutenticado.getUserId()));

        return experienciaProfissional.save(ep);
    }

    public List<ExperienciaProfissional> getExperienciasProfissionais(Long idUCandidato) {
        return experienciaProfissional.findByCandidatoId(idUCandidato);
    }

    public FormacaoAcademica addFormacaoAcademica(FormacaoAcademica fa) {
        fa.setCandidato(candidatoRepository.findByUsuarioId(usuarioAutenticado.getUserId()));
        return formacaoAcademica.save(fa);
    }

    public List<FormacaoAcademica> getFormacoesAcademicas(Long idUCandidato) {
        return formacaoAcademica.findByCandidatoId(idUCandidato);
    }




}
