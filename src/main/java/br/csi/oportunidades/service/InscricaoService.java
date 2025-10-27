package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO;
import br.csi.oportunidades.dto.inscricao.InscricaoResponseParaCandidatoDTO;
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
import java.util.NoSuchElementException;


@AllArgsConstructor
@Transactional
@Service
public class InscricaoService {


    private final InscricaoRepository inscricaoRepository;

    private ModelMapper modelMapper;

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

    public InscricaoResponseParaCandidatoDTO create(Inscricao i){
        Inscricao in = inscricaoRepository.save(i);
        return modelMapper.map(in, InscricaoResponseParaCandidatoDTO.class);
    }


    public void cancelarInscricaoPorId(Long id){
        if(!inscricaoRepository.existsById(id)){
            throw new NoSuchElementException("Inscricao nao encontrada");
        }
        inscricaoRepository.deleteById(id);
    }

    public Inscricao recuperarInscricaoPorId(Long id){
        return  inscricaoRepository.findById(id).orElse(null);
    }


}
