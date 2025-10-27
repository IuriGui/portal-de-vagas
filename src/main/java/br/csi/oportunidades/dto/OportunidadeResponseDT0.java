package br.csi.oportunidades.dto;

import br.csi.oportunidades.model.Endereco;
import br.csi.oportunidades.model.Instituicao;
import br.csi.oportunidades.model.inscricao.Inscricao;
import br.csi.oportunidades.model.oportunidade.AreaAtuacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Getter
@Setter
public class OportunidadeResponseDT0{

    private Long id;

    private Instituicao instituicao;

    private AreaAtuacao areaAtuacao;

    private Endereco endereco;

    private String titulo;

    private String descricao;

    private Timestamp dataPublicacao;

    private Timestamp dataValidade;

    private boolean remoto;

    private int cargaHoraria;

    private BigDecimal remuneracao;

    private String beneficios;

    private String requisitos;

    private List<Inscricao> inscricoes;


}
