package com.br.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public enum TipoDespesaEnum implements Serializable {
    ALIMENTACAO("Alimentação"),
    BALSA("Balsa"),
    CINEMA("Cinema"),
    COMBUSTIVEL("Combustível"),
    DPVAT("DPVAT"),
    DIVERSOS("Diversos"),
    ESTACIONAMENTO("Estacionamento"),
    FARMACIA("Fármacia"),
    HARDWARE("HardWare"),
    HOSPEDAGEM("Hospedagem"),
    IPVA("IPVA"),
    LAZER("Lazer"),
    LICENCIAMENTO("Licenciamento"),
    MANUTENCAO_AUTOMOVEL("Manutenção Automovel"),
    MEDICO_CONSULTA("Medico/Consulta"),
    MOTO("Moto"),
    PASSAGEM("Passagem"),
    PEDAGIO("Pedágio"),
    PESCARIA("Pescaria"),
    PRESENTE("Presente"),
    RECARGA_TIM("Recarga TIM"),
    SUPERMERCADO("Supermercado"),
    TRANSPORTE("Transporte"),
    VESTUARIO("Vestuário");

    private final String nome;
    private final String value;

    TipoDespesaEnum(String nome) {
        this.nome = nome;
        this.value = this.name();
    }

    @JsonCreator
    public static TipoDespesaEnum fromJson(@JsonProperty("value") String value) {
        return forValues(value);
    }

    public static TipoDespesaEnum forValues(String value) {
        for (TipoDespesaEnum tipoDespesaEnum : TipoDespesaEnum.values()) {
            if (tipoDespesaEnum.value.equals(value)) {
                return tipoDespesaEnum;
            }
        }
        throw new IllegalArgumentException("Nenhum tipo de despoesa encontrado: " + value);
    }
}
