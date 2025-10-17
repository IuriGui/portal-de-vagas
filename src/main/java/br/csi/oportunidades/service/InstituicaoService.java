package br.csi.oportunidades.service;


import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.repository.InstituicaoRepository;
import org.springframework.stereotype.Service;

@Service
public class InstituicaoService {

    private InstituicaoRepository instituicaoRepository;

    public Instituicao create(Instituicao instituicao) {
        return instituicaoRepository.save(instituicao);
    }

}
