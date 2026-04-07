package com.br.dto.request.create;

import com.br.validation.DataDDMMYYYY;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.math.BigInteger;

public record DespesaRequestCreateLoteDTO(
    @NotNull(message = "O Tipo Despesa é obrigatório")
    String tipoDespesa,

    @NotBlank(message = "O CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos numéricos, sem sinais ou espaços")
    String cnpj,

    @NotBlank(message = "A data de pagamento é obrigatória")
    @DataDDMMYYYY(message = "A data de pagamento é inválida")
    String dataPagamento,

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    BigDecimal valor,

    @NotNull(message = "O idFormaPagamento é obrigatório")
    BigInteger idFormaPagamento,
    String observacao
){
}
