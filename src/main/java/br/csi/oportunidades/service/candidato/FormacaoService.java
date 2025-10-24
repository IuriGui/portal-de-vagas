package br.csi.oportunidades.service.candidato;


import br.csi.oportunidades.model.candidato.Candidato;
import br.csi.oportunidades.model.candidato.FormacaoAcademica;
import br.csi.oportunidades.repository.ExperienciaProfissionalRepository;
import br.csi.oportunidades.repository.FormacaoAcademicaRepository;
import br.csi.oportunidades.util.UsuarioAutenticado;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class FormacaoService {

    private final FormacaoAcademicaRepository foa;
    private final CandidatoService candidatoService;



    public FormacaoAcademica addFormacaoAcademica(FormacaoAcademica fa) {
        fa.setCandidato(candidatoService.findById(fa.getCandidato().getId()));
        return foa.save(fa);
    }

    public List<FormacaoAcademica> getFormacoesAcademicas(Long idUCandidato) {

        if (idUCandidato == null) {
            Candidato c = candidatoService.findById(UsuarioAutenticado.getUserId());
            return foa.findByCandidatoId(c.getId());
        } else{
            return foa.findByCandidatoId(idUCandidato);
        }


    }

    public FormacaoAcademica updateFormacaoAcademica(FormacaoAcademica nova, Long id) throws AccessDeniedException {

        Candidato c = candidatoService.findById(UsuarioAutenticado.getUserId());

        FormacaoAcademica formacaoExistente = foa.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Formação não encontrada"));

        formacaoExistente.setCurso(nova.getCurso());
        formacaoExistente.setInstituicao(nova.getInstituicao());
        formacaoExistente.setDataInicio(nova.getDataInicio());
        formacaoExistente.setDataConclusao(nova.getDataConclusao());
        return foa.save(formacaoExistente);

    }


    public void deleteFormacaoAcademica(Long id) throws AccessDeniedException {
        if(!foa.existsById(id)){
            throw new NoSuchElementException("Formacao não encontrada");
        }

        foa.deleteById(id);

    }
}
