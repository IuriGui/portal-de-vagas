package br.csi.oportunidades.service;

import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.repository.ExperienciaProfissionalRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
public class CandidatoService {

    private CandidatoRepository candidatoRepository;
    private ExperienciaProfissionalRepository experienciaProfissional;
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



}
