package com.br.appuifx.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class FormatUtil {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static LocalDate stringParaData(String dataTexto) {
        try {
            return LocalDate.parse(dataTexto, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            // Retorna null ou lança uma exceção personalizada se a data estiver errada
            return null;
        }
    }

    public static BigDecimal parseMoedaBR(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }

        String normalizado = valor
                // remove tudo que não for dígito, vírgula ou ponto
                .replaceAll("[^\\d,\\.]", "")
                // remove separador de milhar (ponto antes de grupo de 3 dígitos)
                .replaceAll("\\.(?=\\d{3}(\\D|$))", "")
                // troca vírgula decimal por ponto
                .replace(",", ".");

        return new BigDecimal(normalizado);
    }
}
