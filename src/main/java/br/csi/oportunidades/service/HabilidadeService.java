package br.csi.oportunidades.service;


import br.csi.oportunidades.model.candidato.Habilidade;
import br.csi.oportunidades.repository.HabilidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class HabilidadeService {

    private HabilidadeRepository habilidadeRepository;

    public Habilidade save(Habilidade habilidade) {
        return habilidadeRepository.save(habilidade);
    }

    public List<Habilidade> findAll() {
        return habilidadeRepository.findAll();
    }

    public Habilidade update(Habilidade habilidade, Long id) {

        if(!habilidadeRepository.existsById(id)) {
            throw new NoSuchElementException("Instituição não encontrada");
        }

        habilidade.setId(id);
        return habilidadeRepository.save(habilidade);

    }

    public void delete(Long id) {
        if(!habilidadeRepository.existsById(id)) {
            throw new NoSuchElementException("Instituição não encontrada");
        }
        habilidadeRepository.deleteById(id);
    }

}
