package br.csi.oportunidades.service;


import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.repository.InstituicaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InstituicaoService {

    private InstituicaoRepository instituicaoRepository;

    public Instituicao create(Instituicao instituicao) {
        return instituicaoRepository.save(instituicao);
    }

    public Instituicao findById(Long id) {
        return instituicaoRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public boolean existsById(Long id){
        return instituicaoRepository.existsById(id);
    }


//    public InstituicaoResponseDTO getInstituicao(Long id) {
//    }

//    public List<InstituicaoResponseDTO> getInstituicoes() {
//        return instituicaoRepository.findAll().stream()
//                .map(InstituicaoResponseDTO::from)
//                .toList();
//    }

    public void deleteInstituicao(Long id) {
        if (!instituicaoRepository.existsById(id)) {
            throw new NoSuchElementException("Instituição não encontrada");
        }
        instituicaoRepository.deleteById(id);
    }

    public Instituicao findByUser(UUID idUsuario) {
        return instituicaoRepository.findByUsuarioId(idUsuario);
    }


}
