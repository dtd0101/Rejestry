package com.rejestr.egb.service;

import com.rejestr.egb.entity.Skany;
import com.vaadin.flow.component.listbox.ListBox;

//@Service
//@Scope("prototype")
public class ScanService {

    static ListBox<Skany> SKANY_LIST_BOX = new ListBox<>();

    public ScanService() {
    }

    public static ListBox<Skany> getSkanyListBox() {
        return SKANY_LIST_BOX;
    }

    public static void setSkanyListBox(ListBox<Skany> skanyListBox) {
        SKANY_LIST_BOX = skanyListBox;
    }
}
