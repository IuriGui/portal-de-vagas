package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.oportunidade.OportunidadeResponseDTO;
import br.csi.oportunidades.model.Inscricao;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import br.csi.oportunidades.repository.InscricaoRepository;
import br.csi.oportunidades.service.candidato.CandidatoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class InscricaoService {


   private final InscricaoRepository inscricaoRepository;
   private final CandidatoService candidatoService;
   private final OportunidadeService oportunidadeService;

   public Inscricao createInscricao(Inscricao inscricao) {

       Oportunidade op = oportunidadeService.findById(inscricao.getOportunidade_id());
       Candidato c = candidatoService.findById(inscricao.getCandidato_id());


       if(op == null || c == null) {
           throw new EntityNotFoundException("Oportunida ou usuario não encontrados");
       }

       if (!oportunidadeService.isOportunidadeOpen(op)){
           throw new IllegalStateException("Oportunidade já encerrou as inscrições");
       }



       return inscricaoRepository.save(inscricao);
   }







}
