package com.rejestr.egb.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class RejestrForm extends FormLayout {

    TextField nrJednostkiRejestrowej=new TextField("Nr jednostki rejestrowej");
    TextField nrGrupyRejestrowej=new TextField("Nr Grupy Rejestrowej");
    TextField nrKsiegiWieczystej=new TextField("Nr Ksiegi Wieczystej");
//    ComboBox<RejestrGruntow.Zmiany> zmiany = new ComboBox("Zmiany");


    Button save = new Button("Zapisz");
    Button delete = new Button("Usuń");
    Button clouse = new Button("Zamknij");

//    Binder<RejestrGruntow> binder = new BeanValidationBinder<>(RejestrGruntow.class);

    public RejestrForm() {
        addClassName("rejestr-form");
//        binder.bindInstanceFields(this);

        add(
                nrJednostkiRejestrowej,
                nrGrupyRejestrowej,
                nrKsiegiWieczystej,
//                zmiany,
                createButtonsLayout()
        );
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clouse.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        clouse.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save,delete,clouse);
    }
}
