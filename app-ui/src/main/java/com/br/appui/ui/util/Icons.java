package com.br.appui.ui.util;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import java.awt.*;

public class Icons {
    public static FontIcon getIconList(){
        return FontIcon.of(FontAwesomeSolid.LIST, 16);
    }
    public static FontIcon getIconCad(){
        return FontIcon.of(FontAwesomeSolid.PLUS_SQUARE, 16);
    }

    public static FontIcon getIconSearch(){
        return FontIcon.of(FontAwesomeSolid.SEARCH, 16);
    }

    public static FontIcon getIconClean() {
        return FontIcon.of(FontAwesomeSolid.BROOM, 16);
    }

    public static FontIcon getIconCancel(){
        return FontIcon.of(FontAwesomeSolid.TIMES_CIRCLE, 16);
    }

    public static FontIcon getIconSave(){
        return FontIcon.of(FontAwesomeSolid.SAVE, 16);
    }

    public static FontIcon getIconDelete(){
        return FontIcon.of(FontAwesomeSolid.TRASH, 16);
    }
    public static FontIcon getIconCheck(){
        return FontIcon.of(FontAwesomeSolid.CHECK, 16);
    }

    public static FontIcon getIconEdit(){
        return FontIcon.of(FontAwesomeSolid.PENCIL_ALT, 16);
    }
    public static FontIcon getIconError(){
        FontIcon fontIcon = FontIcon.of(FontAwesomeSolid.EXCLAMATION_TRIANGLE, 16);
        fontIcon.setIconColor(AppColors.CRIMSON);
        return fontIcon;
    }
    public static FontIcon getIconClock(){
        FontIcon fontIcon = FontIcon.of(FontAwesomeSolid.CLOCK, 16);
        fontIcon.setIconColor(AppColors.ORANGE);
        return  fontIcon;
    }
    public static FontIcon getIconFoldeOpen(){
        FontIcon fontIcon = FontIcon.of(FontAwesomeSolid.FOLDER_OPEN, 16);
        fontIcon.setIconColor(AppColors.SEA_GREEN);
        return fontIcon;
    }
    public static FontIcon getIconCalendarCheck(){
        FontIcon fontIcon = FontIcon.of(FontAwesomeSolid.CALENDAR_CHECK, 16);
        fontIcon.setIconColor(AppColors.CORNFLOWER_BLUE);
        return fontIcon;
    }

}
