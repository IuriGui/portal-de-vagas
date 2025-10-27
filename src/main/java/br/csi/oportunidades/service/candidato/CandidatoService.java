package br.csi.oportunidades.service.candidato;

import br.csi.oportunidades.dto.candidato.CandidatoPublicResponseDTO;
import br.csi.oportunidades.dto.candidato.CandidatoResponseDTO;
import br.csi.oportunidades.dto.candidato.CandidatoUpdateRequestDTO;
import br.csi.oportunidades.dto.inscricao.InscricaoResponseDTO;
import br.csi.oportunidades.dto.inscricao.InscricaoResponseParaCandidatoDTO;
import br.csi.oportunidades.model.Endereco;
import br.csi.oportunidades.model.candidato.Habilidade;
import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.inscricao.StatusInscricao;
import br.csi.oportunidades.model.oportunidade.Oportunidade;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.repository.HabilidadeRepository;
import br.csi.oportunidades.repository.OportunidadeRepository;
import br.csi.oportunidades.service.InscricaoService;
import br.csi.oportunidades.service.OportunidadeService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;


@Service
@AllArgsConstructor
public class CandidatoService {


    private final CandidatoRepository candidatoRepository;
    private final InscricaoService inscricaoService;
    private final OportunidadeService oportunidadeService;
    private final HabilidadeRepository habilidadeRepository;

    private ModelMapper modelMapper;

    public Candidato findByIdUsuario(UUID id) { //Usar para recuperar para???
      return candidatoRepository.findByUsuarioId(id);
    }

    public CandidatoResponseDTO create(Candidato candidato) {
        return modelMapper.map(candidatoRepository.save(candidato), CandidatoResponseDTO.class);
    }

    public List<CandidatoPublicResponseDTO> findAll() {
        List<CandidatoPublicResponseDTO> candidatos = new ArrayList<>();

        for(Candidato candidato : candidatoRepository.findAll()) {
            candidatos.add(modelMapper.map(candidato, CandidatoPublicResponseDTO.class));
        }
        return candidatos;
    }


    public List<InscricaoResponseDTO> listarInscricoes(Long id){
        return inscricaoService.listarInscricoes(id);
    }

    public InscricaoResponseParaCandidatoDTO inscreverEmOportunidade(Long idOportunidade){

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


    public void cancelarInscricaoPorId(Long id){
        inscricaoService.cancelarInscricaoPorId(id);
    }


    public InscricaoResponseDTO buscarInscricaoPorId(Long id) throws AccessDeniedException {
        Inscricao d = inscricaoService.recuperarInscricaoPorId(id);
        if(d == null){
            throw new NoSuchElementException("Inscricao nao encontrada");
        }
        if(Objects.equals(d.getCandidato().getId(), UsuarioAutenticado.getUserId())){
            return modelMapper.map(d, InscricaoResponseDTO.class);
        } else {
            throw new AccessDeniedException("Você não tem permissão para acessar este recurso.");
        }
    }



    @Transactional
    public Candidato editarCandidatoPorId(Long id, CandidatoUpdateRequestDTO dto) throws AccessDeniedException {
        Candidato c = candidatoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Oportunidade com id " + id + " nao encontrada"));

        if (!c.getId().equals(UsuarioAutenticado.getUserId())) {
            throw new AccessDeniedException("Voce nao tem permissao para acessar esse recurso");
        }


        if(dto.getNome() != null) c.setNome(dto.getNome());
        if (dto.getTelefone() != null) c.setTelefone(dto.getTelefone());
        if (dto.getCurriculoUrl() != null) c.setCurriculoUrl(dto.getCurriculoUrl());


        if (dto.getEndereco() != null) {
            Endereco end = c.getEndereco();
            if (end == null) {
                end = new Endereco();
                c.setEndereco(end);
            }

            if (dto.getEndereco().getCep() != null) end.setCep(dto.getEndereco().getCep());
            if (dto.getEndereco().getRua() != null) end.setRua(dto.getEndereco().getRua());
            if (dto.getEndereco().getNumero() != null) end.setNumero(dto.getEndereco().getNumero());
            if (dto.getEndereco().getBairro() != null) end.setBairro(dto.getEndereco().getBairro());
            if (dto.getEndereco().getCidade() != null) end.setCidade(dto.getEndereco().getCidade());
            if (dto.getEndereco().getUf() != null) end.setUf(dto.getEndereco().getUf());
            if (dto.getEndereco().getComplemento() != null) end.setComplemento(dto.getEndereco().getComplemento());

        }


        if (dto.getHabilidadesIds() != null) {
            // Busca as *entidades* Habilidade no banco de dados usando os IDs
            Set<Habilidade> novasHabilidades = new HashSet<>(
                    habilidadeRepository.findAllById(dto.getHabilidadesIds())
            );

            if (novasHabilidades.size() != dto.getHabilidadesIds().size()) {
                throw new NoSuchElementException("Um ou mais IDs de habilidade não foram encontrados.");
            }

            c.setHabilidades(novasHabilidades);

        } else {
            c.getHabilidades().clear();
        }


        return c;

    }


}
