package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.usuario.InscricaoResponseDTO;
import br.csi.oportunidades.model.TipoConta;
import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.repository.InscricaoRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Transactional
@Service
public class InscricaoService {


    private final InscricaoRepository inscricaoRepository;

    private ModelMapper modelMapper;

    private final OportunidadeService oportunidadeService;

    public List<InscricaoResponseDTO> listarInscricoes(Long id){
        if(UsuarioAutenticado.getTipoConta() == TipoConta.CANDIDATO){
            List<InscricaoResponseDTO> l = new ArrayList<>();
            for (Inscricao qw : inscricaoRepository.findInscricaosByCandidato_Id(id)){
                l.add(modelMapper.map(qw, InscricaoResponseDTO.class));
            }
            return l;
        }
     return null;
    }

    public InscricaoResponseDTO create(Inscricao i){
        Inscricao in = inscricaoRepository.save(i);
        return modelMapper.map(in, InscricaoResponseDTO.class);
    }




}
