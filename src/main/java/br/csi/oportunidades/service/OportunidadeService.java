package br.csi.oportunidades.service;


import br.csi.oportunidades.dto.oportunidade.OportunidadeRequestDTO;
import br.csi.oportunidades.dto.oportunidade.OportunidadeResponseDTO;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.model.oportunidade.AreaAtuacao;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import br.csi.oportunidades.repository.AreaAtuacaoRepository;
import br.csi.oportunidades.repository.OportunidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OportunidadeService {

    private final OportunidadeRepository oportunidadeRepository;
    private final AreaAtuacaoRepository areaAtuacaoRepository;
    private final InstituicaoService instituicaoService;

    public List<OportunidadeResponseDTO> getOportunidades(){
        List<OportunidadeResponseDTO> op = new java.util.ArrayList<>(List.of());
        for(Oportunidade o : this.oportunidadeRepository.findAll()){
            op.add(OportunidadeResponseDTO.from(o));
        }
        return op;
    }

    public OportunidadeResponseDTO createOportunidade(OportunidadeRequestDTO dto){
        Oportunidade op = new Oportunidade();

        Optional<Instituicao> i = instituicaoService.findById(dto.instituicao_id());
        Optional<AreaAtuacao> a = areaAtuacaoRepository.findById(dto.areaAtuacao_id());

        if(i.isPresent() && a.isPresent()){
            op.setInstituicao(i.get());
            op.setAreaAtuacao(a.get());
            op.setTitulo(dto.titulo());
            op.setDescricao(dto.descricao());
            op.setDataPublicacao(dto.dataPublicacao());
            op.setDataValidade(dto.dataValidade());
            op.setRemoto(dto.remoto());
            op.setCargaHoraria(dto.cargaHoraria());
            op.setRemuneracao(dto.remuneracao());
            op.setBeneficios(dto.beneficios());
            op.setRequisitos(dto.requisitos());
            return OportunidadeResponseDTO.from(oportunidadeRepository.save(op));

        }



        return null;

    }

    public OportunidadeResponseDTO getOportunidade(Long id) {
        return oportunidadeRepository.findById(id)
                .map(OportunidadeResponseDTO::from)
                .orElseThrow(NoSuchElementException::new);
    }

    public Oportunidade findById(Long id) {
        return oportunidadeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Oportunidade não encontrada com id: " + id));
    }


    public void deleteOportunidade(Long id) {
        if (!oportunidadeRepository.existsById(id)) {
            throw new NoSuchElementException("Oportunidade não encontrada");
        }
        oportunidadeRepository.deleteById(id);
    }

    public boolean isOportunidadeOpen(Oportunidade op) {
        return op.getDataValidade().getTime() > System.currentTimeMillis();
    }




}
