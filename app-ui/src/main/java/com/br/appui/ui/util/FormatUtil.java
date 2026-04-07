package com.br.appui.ui.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtil {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    public static String formatMoeda(BigDecimal moeda) {
        return moeda != null ? CURRENCY_FORMAT.format(moeda) : "";
    }

    public static String formatData(LocalDate date) {
        return  date != null ? date.format(DATE_FORMAT) : "";
    }
}
