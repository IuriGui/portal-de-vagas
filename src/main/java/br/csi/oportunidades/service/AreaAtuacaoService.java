package br.csi.oportunidades.service;


import br.csi.oportunidades.model.oportunidade.AreaAtuacao;
import br.csi.oportunidades.repository.AreaAtuacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AreaAtuacaoService {

    private final AreaAtuacaoRepository areaAtuacaoRepository;


    public AreaAtuacao findByName(String name){
        return areaAtuacaoRepository.findByNome(name);
    }

    public AreaAtuacao findById(Long id){
        return areaAtuacaoRepository.findById(id).get();
    }


}
