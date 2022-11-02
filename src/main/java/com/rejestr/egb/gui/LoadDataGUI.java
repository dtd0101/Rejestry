package com.rejestr.egb.gui;

import com.rejestr.egb.service.LoadData;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Route(value = "Import-danych")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class LoadDataGUI extends HorizontalLayout {

    private TextField path;
    private Button loadData;
    private Button choseFolder;

    @Autowired
    LoadData loadDataClass;

    public LoadDataGUI() {
        choseFolder = new Button("...");



        loadData = new Button("Wczytaj dane:");
        path = new TextField();
        path.setPlaceholder("Wskaż folder");
//        path.setWidthFull();
        path.setWidth("1000px");

       loadData.addClickListener(l -> {
           loadDataClass.setPath(path.getValue());
           System.out.println(loadDataClass.getPath());
           try {
               loadDataClass.readFile();
           } catch (IOException e) {
               e.printStackTrace();
           }
       });

        add(path,choseFolder,loadData);
    }
}
