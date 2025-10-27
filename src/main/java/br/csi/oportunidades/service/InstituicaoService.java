package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.InstituicaoResponseDTO;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.repository.InstituicaoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InstituicaoService {

    private final ModelMapper modelMapper;
    private final InstituicaoRepository instituicaoRepository;

    public Instituicao create(Instituicao instituicao) {
        return instituicaoRepository.save(instituicao);
    }

    public Instituicao findById(Long id) {
        return instituicaoRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public boolean existsById(Long id){
        return instituicaoRepository.existsById(id);
    }


    public InstituicaoResponseDTO getInstituicao(Long id) {
        return modelMapper.map(instituicaoRepository.findById(id).get(), InstituicaoResponseDTO.class);
    }

    public List<InstituicaoResponseDTO> getInstituicoes() {

        List<InstituicaoResponseDTO> l = new ArrayList<>();
        for(Instituicao i :  instituicaoRepository.findAll()) {
            l.add(modelMapper.map(i, InstituicaoResponseDTO.class));
        }

        return l;
    }

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
