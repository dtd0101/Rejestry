package com.rejestr.egb.service;

import com.rejestr.egb.entity.Skany;
import com.rejestr.egb.pojo.Utilities;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@VaadinSessionScope
public class UIServiceZmiany {
    private String uid = UUID.randomUUID().toString();
    private ListBox<Skany> skanyListBox = new ListBox<>();
    private List<Skany> skanyList = new ArrayList<>();

    public String getText() {
        return "ui " + uid;
    }

    public ListBox<Skany> getSkanyListBox() {
        return skanyListBox;
    }

    public void setSkanyListBox(ListBox<Skany> skanyListBox) {
        this.skanyListBox = skanyListBox;
    }

    public void addRenderToAll(){
        AtomicInteger imageNumb = new AtomicInteger();
        this.skanyListBox.setRenderer(new ComponentRenderer<>(s ->  {
            Div text = new Div();
            imageNumb.set(imageNumb.get() + 1);
            text.setText(imageNumb +"");
            File f = s.getSkan();

            Image image = Utilities.getImigeStreamFromFile(f);
            image.setWidth("80px");
            image.setHeight("50px");
            FlexLayout wrapper = new FlexLayout();
            text.getStyle().set("margin-right", "0.5em");
            wrapper.add(text,image);
            return wrapper;
        }));
    }


    public List<Skany> getSkanyList() {
        return skanyList;
    }

    public void setSkanyList(List<Skany> skanyList) {
        this.skanyList = skanyList;
    }
}
