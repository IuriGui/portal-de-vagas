package br.csi.oportunidades.service.candidato;

import br.csi.oportunidades.dto.CandidatoResponseDTO;
import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.inscricao.StatusInscricao;
import br.csi.oportunidades.repository.CandidatoRepository;
import br.csi.oportunidades.service.InscricaoService;
import br.csi.oportunidades.service.OportunidadeService;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class CandidatoService {


    private  final CandidatoRepository candidatoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Candidato findByIdUsuario(UUID id) { //Usar para recuperar para???
      return candidatoRepository.findByUsuarioId(id);
    }

    public CandidatoResponseDTO create(Candidato candidato) {
        return modelMapper.map(candidatoRepository.save(candidato), CandidatoResponseDTO.class);
    }



}
