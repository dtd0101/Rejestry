package com.rejestr.egb.pojo;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceWriter;
import com.vaadin.flow.theme.lumo.Lumo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Utilities {


    public static Image getImigeFromStream(FileInputStream f, String message) {
        Image image = new Image(new StreamResource("test", (StreamResourceWriter) f), message );
        return image;
    }

    public static Image getImigeStreamFromFile(File f) {
        Image image = new Image(new StreamResource(f.getName(), () -> {
            try {
                return new FileInputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }), "Nie udało się wczytać skanu z lokalizacji: " + f.getAbsolutePath());
        return image;
    }

    public static void configSun(Icon sun) {
        sun.getStyle().set("cursor", "pointer");
        sun.setColor("orange");
        sun.addClickListener(click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
            if(themeList.contains(Lumo.DARK)) {
                themeList.remove(Lumo.DARK);
                sun.setColor("black");
            }else {
                themeList.add(Lumo.DARK);
                sun.setColor("orange");
            }
        });
    }
}
