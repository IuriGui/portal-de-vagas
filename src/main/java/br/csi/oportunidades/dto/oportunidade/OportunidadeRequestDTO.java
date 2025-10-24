package br.csi.oportunidades.dto.oportunidade;

import br.csi.oportunidades.model.Endereco;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record OportunidadeRequestDTO(

        @NotNull(message = "O ID da instituição é obrigatório.")
        @Positive(message = "O ID da instituição deve ser positivo.")
        Long instituicao_id,

        @NotNull(message = "O ID da área de atuação é obrigatório.")
        @Positive(message = "O ID da área de atuação deve ser positivo.")
        Long areaAtuacao_id,

        Endereco endereco,

        @NotBlank(message = "O título da vaga é obrigatório.")
        @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
        String titulo,
        @NotBlank(message = "A descrição da vaga é obrigatória.")
        String descricao,

        @NotNull(message = "A data de publicação é obrigatória.")
        @FutureOrPresent(message = "A data de publicação deve ser no presente ou futuro.")
        Timestamp dataPublicacao,

        @NotNull(message = "A data de validade é obrigatória.")
        @Future(message = "A data de validade deve ser uma data futura.")
        Timestamp dataValidade,

        @NotNull
        boolean remoto,

        @Min(value = 1, message = "A carga horária deve ser de pelo menos 1 hora.")
        @Max(value = 80, message = "A carga horária não pode exceder 80 horas (semanal ou quinzenal).")
        int cargaHoraria,

        @DecimalMin(value = "0.00", inclusive = true, message = "A remuneração não pode ser negativa.")
        BigDecimal remuneracao,

        String beneficios,

        String requisitos
) {}

