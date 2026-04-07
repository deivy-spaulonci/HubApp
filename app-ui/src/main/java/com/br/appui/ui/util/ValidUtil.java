package com.br.appui.ui.util;

import com.br.appui.ui.shared.AutoCompleteFornec;
import com.br.appui.ui.shared.InputDate;
import com.br.appui.ui.shared.InputMoney;
import com.br.appui.ui.shared.SelectUI;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidUtil {
    public static boolean dataInvalida(String dataBR)
    {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false); // aqui o pulo do gato
            df.parse(dataBR);
            return false;
        } catch (ParseException ex) {
            return true;
        }
    }

    public static boolean validRequiredField(Component[] components, JComponent jComponent)
    {
        for(Component component : components){
            if(component instanceof InputDate){
                if(((InputDate) component).getDateTime() == null ||
                        dataInvalida(((InputDate) component).getText())){
                    Dialogs.alertErro(jComponent, "Data de Inválida");
                    return false;
                }
            }else if(component instanceof InputMoney){
                if(((InputMoney) component).getBigDecimal() == null ||
                        ((InputMoney) component).getBigDecimal().compareTo(BigDecimal.ZERO) <= 0){
                    Dialogs.alertErro(jComponent, "Valor Inválido");
                    return false;
                }
            }else if(component instanceof SelectUI){
                if(((SelectUI) component).getSelectedItemTyped() == null){
                    Dialogs.alertErro(jComponent, "Valor Inválido");
                    return false;
                }
            }else if(component instanceof AutoCompleteFornec){
                if(((AutoCompleteFornec) component).getSelectedItemTyped() == null){
                    Dialogs.alertErro(jComponent, "Fornecedor Inválido");
                    return false;
                }
            }
        }
        return true;
    }
}
