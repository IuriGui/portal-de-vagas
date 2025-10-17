package br.csi.oportunidades.service;

import br.csi.oportunidades.model.Candidato;
import br.csi.oportunidades.repository.CandidatoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CandidatoService {

    private CandidatoRepository candidatoRepository;

    public Candidato create(Candidato candidato) {
        return candidatoRepository.save(candidato);
    }

    public void subcribe(Candidato candidato, Long idInscricao) {

    }



}
