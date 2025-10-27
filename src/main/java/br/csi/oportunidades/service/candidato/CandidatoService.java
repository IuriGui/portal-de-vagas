package br.csi.oportunidades.service.candidato;

import br.csi.oportunidades.dto.CandidatoResponseDTO;
import br.csi.oportunidades.dto.usuario.InscricaoResponseDTO;
import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.inscricao.StatusInscricao;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.service.InscricaoService;
import br.csi.oportunidades.service.OportunidadeService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class CandidatoService {


    private final CandidatoRepository candidatoRepository;
    private final InscricaoService inscricaoService;
    private final OportunidadeService oportunidadeService;

    @Autowired
    private ModelMapper modelMapper;

    public Candidato findByIdUsuario(UUID id) { //Usar para recuperar para???
      return candidatoRepository.findByUsuarioId(id);
    }

    public CandidatoResponseDTO create(Candidato candidato) {
        return modelMapper.map(candidatoRepository.save(candidato), CandidatoResponseDTO.class);
    }

    public List<InscricaoResponseDTO> listarInscricoes(Long id){
        return inscricaoService.listarInscricoes(id);
    }

    public InscricaoResponseDTO inscreverEmOportunidade(Long idOportunidade){

        Candidato candidato = this.recuperarPorId(UsuarioAutenticado.getUserId());


        if(!oportunidadeService.exists(idOportunidade)){
            throw new NoSuchElementException("Oportunidade nao encontrada");
        }
        Oportunidade o = oportunidadeService.recuperarPorId(idOportunidade);

        Inscricao n =  new Inscricao();
        n.setCandidato(candidato);
        n.setOportunidade(o);

        n.setStatus(StatusInscricao.INSCRICAO_RECEBIDA);
        n.setData_inscricao(new Date());

        return inscricaoService.create(n);
    }


    public Candidato recuperarPorId(Long id){

        Optional<Candidato> c = candidatoRepository.findById(id);

        if(c.isPresent()){
            return c.get();
        } else{
            throw new NoSuchElementException("Candidato nao encontrada");
        }

    }




}
