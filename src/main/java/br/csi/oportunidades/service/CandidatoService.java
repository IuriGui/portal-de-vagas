package br.csi.oportunidades.service;

import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.candidato.ExperienciaProfissional;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.repository.ExperienciaProfissionalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CandidatoService {

    private CandidatoRepository candidatoRepository;
    private ExperienciaProfissionalRepository experienciaProfissional;

    public Candidato create(Candidato candidato) {
        return candidatoRepository.save(candidato);
    }

    public ExperienciaProfissional addExperienciaProfissional(ExperienciaProfissional ep) {
        return experienciaProfissional.save(ep);
    }



}
