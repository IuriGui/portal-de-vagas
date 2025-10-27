package br.csi.oportunidades.dto.oportunidade;

import br.csi.oportunidades.model.Endereco;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class OportunidadeRequestDTO {


    @NotNull(message = "O ID da instituição é obrigatório.")
    @Positive(message = "O ID da instituição deve ser positivo.")
    private Long instituicao_id;

    @NotNull(message = "O ID da área de atuação é obrigatório.")
    @Positive(message = "O ID da área de atuação deve ser positivo.")
    private Long areaAtuacao_id;

    private Endereco endereco;

    @NotBlank(message = "O título da vaga é obrigatório.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    @NotBlank(message = "A descrição da vaga é obrigatória.")
    private String descricao;

    @NotNull(message = "A data de publicação é obrigatória.")
    @FutureOrPresent(message = "A data de publicação deve ser no presente ou futuro.")
    private Timestamp dataPublicacao;

    @NotNull(message = "A data de validade é obrigatória.")
    @Future(message = "A data de validade deve ser uma data futura.")
    private Timestamp dataValidade;

    @NotNull(message = "O campo remoto é obrigatório")
    private boolean remoto;

    @Min(value = 1, message = "A carga horária deve ser de pelo menos 1 hora.")
    @Max(value = 80, message = "A carga horária não pode exceder 80 horas (semanal ou quinzenal).")
    private int cargaHoraria;

    @DecimalMin(value = "0.00", inclusive = true, message = "A remuneração não pode ser negativa.")
    private BigDecimal remuneracao;

    private String beneficios;

    private String requisitos;

}
