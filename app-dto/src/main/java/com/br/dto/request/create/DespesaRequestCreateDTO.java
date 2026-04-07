package com.br.dto.request.create;

import com.br.dto.request.ref.FormaPagamentoRefDTO;
import com.br.dto.request.ref.PessoaRefDTO;
import com.br.dto.request.ref.TipoDespesaRefDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record DespesaRequestCreateDTO(
        @NotNull(message = "O Tipo de Despesa é obrigatório")
        TipoDespesaRefDTO tipoDespesa,
        @NotNull(message = "O Fornecedor é obrigatório")
        PessoaRefDTO fornecedor,
//    @DataDDMMYYYY(message = "A data de pagamento é inválida")
        @NotNull(message = "A data de pagamento é obrigatória")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dataPagamento,
        @NotNull(message = "O valor é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        BigDecimal valor,
        @NotNull(message = "O Forma Pagamento é obrigatório")
        FormaPagamentoRefDTO formaPagamento,
        String observacao
){}