package com.rejestr.egb.gui;

import com.rejestr.egb.entity.RejestrGruntow;
import com.rejestr.egb.entity.Skany;
import com.rejestr.egb.entity.Zmiana;
import com.rejestr.egb.repository.SkanyRepo;
import com.rejestr.egb.service.RejestrGService;
import com.rejestr.egb.service.SkorowidzDzService;
import com.rejestr.egb.service.SkorowidzWlaService;
import com.rejestr.egb.service.ZmianyService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Route(value = "Edytor")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class Edytor extends VerticalLayout {

    @Autowired
    private RejestrGService gService;
    @Autowired
    private SkorowidzDzService skorowidzDzService;
    @Autowired
    private ZmianyService zmianyService;
    @Autowired
    private SkorowidzWlaService skorowidzWlaService;
    @Autowired
    private SkanyRepo skanyRepo;


    Button btZastosuj = new Button("Zastosuj");
    Button btSzukaj = new Button("Szukaj");
    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    HorizontalLayout horizontalLayout2 = new HorizontalLayout();

    TextField filterJednostkaRejestrowa = new TextField();
    TextField filternrZmiany = new TextField();
    TextField filterRok = new TextField();
    TextField filterWlasciciel = new TextField();
    TextField filterWlascicielNazwisko = new TextField();
    TextField filterOjciec = new TextField();
    TextField filterMatka = new TextField();
    TextField filterDzialki = new TextField();
    TextField filterTerytRejestrGruntow = new TextField();
    TextField filterMiejscowoscRejestrGruntow = new TextField();
    TextField filterGminaRejestrGruntow  = new TextField();

    TextField path = new TextField();
    ListBox<RejestrGruntow> listBoxRejestry = new ListBox<>();
    ListBox<Skany> listBoxSkany = new ListBox<>();

    ListBox<Zmiana> listBoxZmiana = new ListBox<>();

    public Edytor() {
        configRadioGroup();
        configBtZastosuj();
        configFilters();
        configListBox();
        configListBoxZmiany();
        filterJednostkaRejestrowa.setValue("70");
        filterGminaRejestrGruntow.setValue("Bir%");
        filterMiejscowoscRejestrGruntow.setValue("Korz%");
        add(radioGroup,horizontalLayout,btSzukaj,btZastosuj,path);
    }

    private void configListBox() {
        listBoxZmiana.addValueChangeListener(event -> {
            Zmiana zmiana = event.getValue();
            List<Skany> skanyList = zmiana.getSkany().stream().collect(Collectors.toList());
            listBoxSkany.setItems(skanyList);
            horizontalLayout2.add(listBoxSkany);
            listBoxSkany.addValueChangeListener(event1 -> {
                Skany skan = event1.getValue();
                removeSkan(skan);
            });

        });
    }

    private void configListBoxZmiany() {
        listBoxRejestry.addValueChangeListener(event -> {
            RejestrGruntow rejestrGruntow = event.getValue();
            List<Skany> skanyList = rejestrGruntow.getSkany().stream().collect(Collectors.toList());
            listBoxSkany.setItems(skanyList);
            horizontalLayout2.add(listBoxSkany);
            storeSkan(rejestrGruntow);
            listBoxSkany.addValueChangeListener(event1 -> {
                Skany skan = event1.getValue();
                removeSkan(skan);
            });

        });
    }

    private void removeSkan(Skany skan) {
        Div div = new Div();
        Icon viewSkanIcon2 = new Icon(VaadinIcon.FILE_REMOVE);
        div.add(viewSkanIcon2);
        skanyRepo.delete(skan);
        horizontalLayout2.add(div);
    }

    private void configFilters() {
        filterJednostkaRejestrowa.setPlaceholder("Jednostka...");
        filterJednostkaRejestrowa.setClearButtonVisible(true);
        filternrZmiany.setPlaceholder("Nr Zmiany...");
        filternrZmiany.setClearButtonVisible(true);
        filterJednostkaRejestrowa.setClearButtonVisible(true);
        filterRok.setPlaceholder("Rok...");
        filterRok.setClearButtonVisible(true);
        filterWlasciciel.setPlaceholder("Imię/nazwa...");
        filterWlasciciel.setClearButtonVisible(true);
        filterWlascicielNazwisko.setPlaceholder("Nazwisko...");
        filterWlascicielNazwisko.setClearButtonVisible(true);
        filterOjciec.setPlaceholder("Ojciec...");
        filterOjciec.setClearButtonVisible(true);
        filterMatka.setPlaceholder("Matka...");
        filterMatka.setClearButtonVisible(true);
        filterGminaRejestrGruntow.setPlaceholder("Gmina...");
        filterGminaRejestrGruntow.setClearButtonVisible(true);
        filterMiejscowoscRejestrGruntow.setPlaceholder("Miejscowość...");
        filterMiejscowoscRejestrGruntow.setClearButtonVisible(true);
        filterTerytRejestrGruntow.setPlaceholder("TERYT...");
        filterTerytRejestrGruntow.setClearButtonVisible(true);
        filterDzialki.setPlaceholder("Działka...");
        filterDzialki.setClearButtonVisible(true);
    }

    private void configRadioGroup() {
        radioGroup.setLabel("Edytuj produkt");
        radioGroup.setItems("Rejestry","Zmiany","Skorowidz Działek", "Skorowidz Właścicieli");
        radioGroup.addValueChangeListener(event -> {
            horizontalLayout.removeAll();
            if(event.getValue().equals("Rejestry")){
                horizontalLayout.add(filterJednostkaRejestrowa, filterRok, filterWlasciciel, filterWlascicielNazwisko, filterOjciec, filterMatka,filterDzialki,
                        filterTerytRejestrGruntow, filterMiejscowoscRejestrGruntow,filterGminaRejestrGruntow);
                btSzukaj.addClickListener(event1 -> {
                    listBoxRejestry.removeAll();
                    List<RejestrGruntow> rejestrGruntowList = gService.findBy(filterJednostkaRejestrowa.getValue(), filterWlasciciel.getValue().toUpperCase(), filterWlascicielNazwisko.getValue().toUpperCase(), filterOjciec.getValue().toUpperCase(), filterMatka.getValue().toUpperCase(), filterGminaRejestrGruntow.getValue().toUpperCase(), filterMiejscowoscRejestrGruntow.getValue().toUpperCase(), filterTerytRejestrGruntow.getValue(), filterDzialki.getValue());
                    listBoxRejestry.setItems(rejestrGruntowList);
                    horizontalLayout2.add(listBoxRejestry);
                    add(horizontalLayout2);

                });
            }
            if(event.getValue().equals("Zmiany")){
                horizontalLayout.add(filternrZmiany, filterJednostkaRejestrowa, filterGminaRejestrGruntow, filterMiejscowoscRejestrGruntow);
                btSzukaj.addClickListener(event1 -> {
                    listBoxZmiana.removeAll();
                    TextField filterWlascielZmiany = new TextField();
                    TextField filterDzialkiZmiany = new TextField();

                    List<Zmiana> zmianaList = zmianyService.findBy(filternrZmiany.getValue(), filterJednostkaRejestrowa.getValue(), filterWlascielZmiany.getValue(), filterDzialkiZmiany.getValue(), filterGminaRejestrGruntow.getValue(), filterMiejscowoscRejestrGruntow.getValue());
                listBoxZmiana.setItems(zmianaList);
                horizontalLayout2.removeAll();
                horizontalLayout2.add(listBoxZmiana);
                add(horizontalLayout2);
                });
            }
        });

    }

    private void storeSkan(RejestrGruntow item){
        Div div = new Div();
        Icon viewSkanIcon2 = new Icon(VaadinIcon.FILE_ADD);
        div.add(viewSkanIcon2);
        viewSkanIcon2.addClickListener(event -> {

            MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
            Upload upload = new Upload(buffer);

            NativeButton uploadButton = new NativeButton("Wskaż plik");
            upload.setUploadButton(uploadButton);

            Span dropLabel = new Span("upuść");
            upload.setDropLabel(dropLabel);

            upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif", "image/jpg");
            Div output = new Div();

            Set<File> fileSet = new HashSet<>();


            upload.addSucceededListener(e -> {
                Component component = createComponent(e.getMIMEType(),
                        e.getFileName(),
                        buffer.getInputStream(e.getFileName()), fileSet, path.getValue());

                List<Skany> skanyList = new LinkedList<>();
                fileSet.stream().forEach(file -> {
                    Skany skan = new Skany();
                    skan.setRejestrGruntow(item);
                    skan.setSkan(file);
                    skanyRepo.save(skan);

                    skanyList.add(skan);
                });
                item.setSkany(skanyList);
                gService.save(item);

            });

            div.add(upload);
        });
        horizontalLayout2.add(div);
    }

    private Component createComponent(String mimeType, String fileName,
                                      InputStream stream, Set<File> fileSet, String zmianyPath) {
        String BASE_PATH=zmianyPath;
        File file;



        if (mimeType.startsWith("image")) {
            Image image = new Image();
            try {
                file = new File(BASE_PATH + fileName);
                fileSet.add(file);
                FileOutputStream fos = new FileOutputStream(file);

                byte[] bytes = IOUtils.toByteArray(stream);
                fos.write(bytes);
                fos.flush();
                fos.close();

                image.getElement().setAttribute("src", new StreamResource(
                        fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(
                        new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO
                            .getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            image.setWidth(reader.getWidth(0) + "px");
                            image.setHeight(reader.getHeight(0) + "px");
                        } finally {
                            reader.dispose();
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            image.setSizeFull();
            return image;
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType, MessageDigestUtil.sha256(stream.toString()));
        content.setText(text);
        return content;

    }

    private void configBtZastosuj() {
        btZastosuj.addClickListener(event -> {
            System.out.println("Zastosuj");
        });
    }
}
