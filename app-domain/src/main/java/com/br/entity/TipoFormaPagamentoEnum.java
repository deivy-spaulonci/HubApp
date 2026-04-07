package com.br.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoFormaPagamentoEnum {
    CHEQUE("Cheque"),
    DINHEIRO("Dinheiro"),
    PIX("Pix"),
    VALE_ALIMENTACAO("Vale Alimentação"),
    VALE_REFEICAO("Vale Refeição"),
    DEBT_NETBANKING("Débito - Net Banking"),
    DEBT_AUTOMATICO("Débito Automático"),
    CART_DEBITO("Cartão de Débito"),
    DEBT_CONTA("Debito em Conta");

    private final String descricao;
    private final String value; // Este campo deve conter "CART_DEBITO"

    TipoFormaPagamentoEnum(String descricao) {
        this.descricao = descricao;
        this.value = this.name(); // Agora o 'value' será "CART_DEBITO"
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoFormaPagamentoEnum getByDescricao(String descricao) {
        for (TipoFormaPagamentoEnum tipo : TipoFormaPagamentoEnum.values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nenhum tipo de forma de pagamento encontrado para a descrição: " + descricao);
    }
}

