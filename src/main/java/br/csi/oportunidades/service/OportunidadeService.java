package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.OportunidadeRequestDTO;
import br.csi.oportunidades.dto.OportunidadeResponseDT0;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.model.oportunidade.AreaAtuacao;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import br.csi.oportunidades.repository.AreaAtuacaoRepository;
import br.csi.oportunidades.repository.OportunidadeRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OportunidadeService {


    @Autowired
    private ModelMapper mp;
    private UsuarioAutenticado usuarioAutenticado;
    private InstituicaoService instituicaoService;
    private OportunidadeRepository or;


    public OportunidadeResponseDT0 inserirOportunidade(OportunidadeRequestDTO oportunidade){
        Oportunidade n = mp.map(oportunidade, Oportunidade.class);
        if(!UsuarioAutenticado.isUsuarioLogado(UsuarioAutenticado.getUserId())){
            throw new AuthorizationDeniedException("Voce nao pode modificar este recurso");
        }
        if(instituicaoService.existsById(n.getInstituicao().getId())){
            throw new NoSuchElementException("Not Found");
        }
        Oportunidade savedOP =  or.save(n);
        return mp.map(savedOP, OportunidadeResponseDT0.class);

    }
}
