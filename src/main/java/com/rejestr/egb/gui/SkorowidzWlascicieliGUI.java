package com.rejestr.egb.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("ZMIANY")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class SkorowidzWlascicieliGUI extends VerticalLayout {
    TextField nrZmiany = new TextField("Nr Zmiany");
    ComboBox nrJednostki = new ComboBox("Nr Jednostki");
    TextField opis = new TextField("Opis");
    TextField uwagi = new TextField("Uwagi");
    ComboBox wlasciciel = new ComboBox("wlasciciel");
    ComboBox dzialki = new ComboBox("dzialki");
    TextField dzialkiTxt = new TextField("dzialki");
    HorizontalLayout horizontalLayout1 = new HorizontalLayout();
    private TextField path;
    private Button loadData;
    private Button choseFolder;

    HorizontalLayout horizontalLayout2 = new HorizontalLayout();


    Button zapisz = new Button("Zapisz");
    Button edytuj = new Button("Edytuj");

    public SkorowidzWlascicieliGUI() {
        choseFolder = new Button("...");

        loadData = new Button("Wczytaj dane:");
        path = new TextField();
        path.setPlaceholder("Dodaj Skan");

        horizontalLayout1.add(path,choseFolder,loadData);

        horizontalLayout2.add(zapisz,edytuj);
        add(nrZmiany,nrJednostki,opis,uwagi,wlasciciel,dzialki,dzialkiTxt,horizontalLayout1,horizontalLayout2);
    }
}
