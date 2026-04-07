package com.br.dto.request.create;

import com.br.dto.request.ref.FormaPagamentoRefDTO;
import com.br.dto.request.ref.TipoContaRefDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaRequestCreateDTO(
        @NotBlank(message = "Código Barras da conta inválida!")
        String codigoBarras,
        @NotNull(message = "Tipo da conta inválido!")
        TipoContaRefDTO tipoConta,
        @NotNull(message = "Emissão conta inválida!")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate emissao,
        @NotNull(message = "Vencimento conta inválida!")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate vencimento,
        Integer parcela,
        Integer totalParcela,
        @NotNull(message = "O valor é obrigatório.")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
        BigDecimal valor,
        FormaPagamentoRefDTO formaPagamento,
        LocalDate dataPagamento,
        BigDecimal valorPago,
        String observacao,
        String titulo,
        String comprovante
) {
}