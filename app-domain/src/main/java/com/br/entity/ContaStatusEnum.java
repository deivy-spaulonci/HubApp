package com.br.entity;

import lombok.Getter;

@Getter
public enum ContaStatusEnum {
    ABERTO("Em Aberto"),
    HOJE("Vencimento Hoje"),
    ATRASADO("Atrasado"),
    PAGO("Pago");

    private final String nome;
    private final String value;

    ContaStatusEnum(String nome) {
        this.nome = nome;
        this.value = this.toString();
    }

    public static ContaStatusEnum forValues(String value) {
        for (ContaStatusEnum contaStatus : ContaStatusEnum.values()) {
            if (contaStatus.value.equals(value)) {
                return contaStatus;
            }
        }
        return null;
    }
}
