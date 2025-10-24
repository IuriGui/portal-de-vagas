package br.csi.oportunidades.dto.inscricao;
import br.csi.oportunidades.model.inscricao.StatusInscricao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class InscricaoResponseDTO {

    private Long id;
    private String nomeCandidato;
    private String tituloOportunidade;
    private Date dataInscricao;
    private StatusInscricao status;


    public InscricaoResponseDTO(Long id, String nomeCandidato, String tituloOportunidade, Date dataInscricao, StatusInscricao status) {
        this.id = id;
        this.nomeCandidato = nomeCandidato;
        this.tituloOportunidade = tituloOportunidade;
        this.dataInscricao = dataInscricao;
        this.status = status;
    }

    public static InscricaoResponseDTO fromEntity(
            br.csi.oportunidades.model.inscricao.Inscricao inscricao,
            String nomeCandidato,
            String tituloOportunidade) {
        return new InscricaoResponseDTO(
                inscricao.getId(),
                nomeCandidato,
                tituloOportunidade,
                inscricao.getData_inscricao(),
                inscricao.getStatus()
        );
    }
}
