package br.csi.oportunidades.dto.oportunidade;

import br.csi.oportunidades.model.Endereco;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Getter
@Setter
public class OportunidadeUpdateDTO {

    // Para update, nenhum campo é obrigatório
    @Positive(message = "O ID da área de atuação deve ser positivo.")
    private Long areaAtuacao_id;

    private Endereco endereco;

    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    private String descricao;

    @FutureOrPresent(message = "A data de publicação deve ser no presente ou futuro.")
    private Timestamp dataPublicacao;

    @Future(message = "A data de validade deve ser uma data futura.")
    private Timestamp dataValidade;

    private Boolean remoto; // Boolean wrapper permite null para não atualizar

    @Min(value = 1, message = "A carga horária deve ser de pelo menos 1 hora.")
    @Max(value = 80, message = "A carga horária não pode exceder 80 horas (semanal ou quinzenal).")
    private Integer cargaHoraria; // Integer wrapper para null

    @DecimalMin(value = "0.00",  message = "A remuneração não pode ser negativa.")
    private BigDecimal remuneracao;

    private String beneficios;

    private String requisitos;
}
