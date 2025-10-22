package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.instituicao.InstituicaoResponseDTO;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.repository.InstituicaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InstituicaoService {

    private InstituicaoRepository instituicaoRepository;

    public Instituicao create(Instituicao instituicao) {
        return instituicaoRepository.save(instituicao);
    }

    public Optional<Instituicao> findById(Long id) {
        return instituicaoRepository.findById(id);
    }

    public InstituicaoResponseDTO getInstituicao(Long id) {
        return instituicaoRepository.findById(id)
                .map(InstituicaoResponseDTO::from)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<InstituicaoResponseDTO> getInstituicoes() {
        return instituicaoRepository.findAll().stream()
                .map(InstituicaoResponseDTO::from)
                .toList();
    }

    public void deleteInstituicao(Long id) {
        if (!instituicaoRepository.existsById(id)) {
            throw new NoSuchElementException("Instituição não encontrada");
        }
        instituicaoRepository.deleteById(id);
    }

}
