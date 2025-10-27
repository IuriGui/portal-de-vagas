package br.csi.oportunidades.dto;

import br.csi.oportunidades.model.Endereco;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.model.oportunidade.AreaAtuacao;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Getter
@Setter
public class OportunidadeResponseDT0{

    @JsonView(Views.Publico.class)
    private Long id;

    @JsonView(Views.Publico.class)
    private InstituicaoResponseDTO instituicao;

    @JsonView(Views.Publico.class)
    private AreaAtuacaoResponseDTO areaAtuacao;

    @JsonView(Views.Publico.class)
    private EnderecoDTO endereco;

    @JsonView(Views.Publico.class)
    private String titulo;

    @JsonView(Views.Publico.class)
    private String descricao;

    @JsonView(Views.Publico.class)
    private Timestamp dataPublicacao;

    @JsonView(Views.Publico.class)
    private Timestamp dataValidade;

    @JsonView(Views.Publico.class)
    private boolean remoto;

    @JsonView(Views.Publico.class)
    private int cargaHoraria;

    @JsonView(Views.Publico.class)
    private BigDecimal remuneracao;

    @JsonView(Views.Publico.class)
    private String beneficios;

    @JsonView(Views.Publico.class)
    private String requisitos;

    @JsonView(Views.Detalhado.class)
    private List<Inscricao> inscricoes;


}
