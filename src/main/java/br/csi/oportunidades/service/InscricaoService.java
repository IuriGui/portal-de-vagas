package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO;
import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.model.inscricao.StatusInscricao;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import br.csi.oportunidades.repository.InscricaoRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class InscricaoService {


   private final InscricaoRepository inscricaoRepository;
   private final OportunidadeService oportunidadeService;

   public Inscricao createInscricao(Inscricao inscricao) {

       Oportunidade op = oportunidadeService.findById(inscricao.getOportunidade().getId());


       if(op == null) {
           throw new NoSuchElementException("Oportunida ou usuario não encontrados");
       }

       if (!oportunidadeService.isOportunidadeOpen(op)){
           throw new IllegalStateException("Oportunidade já encerrou as inscrições");
       }

       Optional<Inscricao> e = inscricaoRepository.findByOportunidadeIdAndCandidatoId(op.getId(), inscricao.getCandidato().getId());
       if (e.isEmpty()) {

           inscricao.setStatus(StatusInscricao.INSCRICAO_RECEBIDA);
           return inscricaoRepository.save(inscricao);
       } else {
           throw new IllegalStateException("Usuário já está inscrito nessa oportunidade");
       }



   }

   public Inscricao getInscricaoCandidato(Long id) {
       Optional<Inscricao> inscricao = inscricaoRepository.findById(id);
       if (inscricao.isPresent()) {
           return inscricao.get();
       }
       return null;
   }

   public List<InscricaoResponseDTO> getAllInscricoesEmpresa() {
       return inscricaoRepository.findInscricoesByOportunidade(UsuarioAutenticado.getUserId());
   }

   public Inscricao findByOportunidadeIdAndCandidatoId(Long oportunidadeId, Long candidatoId) {
       Optional<Inscricao> t = inscricaoRepository.findByOportunidadeIdAndCandidatoId(oportunidadeId, candidatoId);
       if (t.isPresent()) {
           return t.get();
       }
       return null;
   }

   public Inscricao save(Inscricao inscricao) {
       return inscricaoRepository.save(inscricao);
   }


}
