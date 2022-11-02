package com.rejestr.egb.gui;

import com.rejestr.egb.entity.Skany;
import com.rejestr.egb.service.UIServiceSkorDz;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Route("Skorowidze-Dzialek-SKANY")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class SkanViewingSkorowidzeDz extends HorizontalLayout {



////    ScanService scanService;
    FormLayout formLayout = new FormLayout();
    ListBox<Skany> skanyListBox = new ListBox<>();
    Button button;
    FormLayout skanLayout = new FormLayout();
    SplitLayout skanListLayout = new SplitLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    Tabs tabs = new Tabs();

    public SkanViewingSkorowidzeDz(@Autowired UIServiceSkorDz uiService) {

        skanListLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        List<Skany> skanyList = uiService.getSkanyList();
        skanyList.stream().forEach(skany -> System.out.println(skany.getSkan().getName()));
        skanyListBox.setItems(skanyList);
        AtomicInteger imageNumb = new AtomicInteger();

        skanyListBox.setRenderer(new ComponentRenderer<>(s ->  {

            Div text = new Div();
//                    text.setText(s.getId() +"");
            imageNumb.set(imageNumb.get() + 1);
            text.setText(imageNumb +"");
            File f = s.getSkan();

            Image image = getImigeStreamFromFile(f);
            image.setWidth("80px");
            image.setHeight("50px");

            Tab zdj = new Tab();
            zdj.setLabel(imageNumb +"");
            zdj.add(image);

            tabs.add(zdj);

            image.addClickListener(event -> {
                Image imageToDisplay = getImigeStreamFromFile(f);
                imageToDisplay.setHeight("850px");
                imageToDisplay.setWidthFull();
                skanListLayout.addToSecondary(imageToDisplay);
            });

            zdj.addAttachListener(event -> {
                zdj.setSelected(true);
            });

            zdj.addDetachListener(event -> {
                zdj.setSelected(false);
            });

            TextField name = new TextField("Name (Alt+N)");

            FlexLayout wrapper = new FlexLayout();
//            text.getStyle().set("margin-right", "0.5em");
//            wrapper.add(text,image);
//            skanLayout.add(image);
//            formLayout.add(wrapper);
//            formLayout.add(text,image);
//            horizontalLayout.add(text,image);
//            skanListLayout.addToPrimary(wrapper);
            horizontalLayout.add(tabs);
            return wrapper;
        }));

        skanListLayout.addToPrimary(horizontalLayout);

//        skanListLayout.addToPrimary(skanLayout);

        configureSkanList(skanyList);
//        skanListLayout.addToPrimary(skanyListBox);
//        formLayout.add(skanyListBox);
//        formLayout.add(uiService.getSkanyListBox());
        add(skanListLayout);

    }





    public FormLayout getFormLayout() {
        return formLayout;
    }

    public void setFormLayout(FormLayout formLayout) {
        this.formLayout = formLayout;
    }

    public ListBox<Skany> getSkanyListBox() {
        return skanyListBox;
    }

    public void setSkanyListBox(ListBox<Skany> skanyListBox) {
        this.skanyListBox = skanyListBox;
    }

    private Image getImigeStreamFromFile(File f) {
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

    private void configureSkanList(List<Skany> skanyList){
        if(skanyList != null && !skanyList.isEmpty()) {
            skanyListBox.setValue(skanyList.get(0));
            Skany skan = skanyList.get(0);
            Image imigeStreamFromFile = getImigeStreamFromFile(skan.getSkan());
            skanListLayout.addToSecondary(imigeStreamFromFile);
        }
        skanyListBox.addValueChangeListener(event -> {
            File f = event.getValue().getSkan();
            Image image = getImigeStreamFromFile(f);
            image.setHeight("850px");
            image.setWidthFull();

//            skanListLayout.removeAll();
            skanListLayout.addToSecondary(image);
//            skanListLayout.setSizeFull();
        });

    }
}
