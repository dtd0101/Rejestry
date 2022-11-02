package com.rejestr.egb.gui;

import com.rejestr.egb.entity.RejestrGruntow;
import com.rejestr.egb.entity.Skany;
import com.rejestr.egb.service.RejestrGService;
import com.rejestr.egb.service.UIService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceWriter;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Route
@Theme(value = Lumo.class, variant = Lumo.DARK)
//@CssImport("./styles/shered-styles.css")
public class GUI extends HorizontalLayout {

    private RejestrGService gService;


    SplitLayout layout = new SplitLayout();
    Grid<RejestrGruntow> grid = new Grid<>(RejestrGruntow.class);
    TextField filterJednostkaRejestrowa = new TextField();
    TextField filterWlasciciel = new TextField();
    TextField filterRodzice = new TextField();
    TextField filterDzialki = new TextField();
    TextField filterTeryt = new TextField();
    TextField test = new TextField();
    ListBox<Skany> skanyListBox = new ListBox<>();
    SplitLayout layout2 = new SplitLayout();
    FormLayout formLayout = new FormLayout();
    Icon sun = new Icon(VaadinIcon.SUN_O);
    ListDataProvider<RejestrGruntow> dataProvider;



    private final RejestrForm form;

    public GUI(RejestrGService gService, @Autowired UIService uiService) {
        HorizontalLayout header = new HorizontalLayout();
        Anchor logout = new Anchor("/logout", "Log out");

        this.gService = gService;
        addClassNames("list-view");
        setSizeFull();
        configureGrid(uiService);
        configureFiler();
        configureSkanList();
        test.setValue("DRUGI");
        layout.setSizeFull();

        configSun();

        header.add(sun,logout);
        header.expand(logout);

//        layout2.setWidth("10");
        formLayout.setHeight("500");
        formLayout.setWidth("10");


        form = new RejestrForm();
        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        File skan1;


//        layout.setOrientation(SplitLayout.Orientation.VERTICAL);
        layout.addToPrimary(filterJednostkaRejestrowa, filterWlasciciel,filterRodzice,filterTeryt,filterDzialki,header,content);

        layout.addToSecondary(layout2);
//        layout.addToSecondary(image);
        add(layout);

//        add(filterJednostkaRejestrowa,content);
        updateList();
    }

    private void configSun() {
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

    private void configureFiler() {
        filterJednostkaRejestrowa.setPlaceholder("Jednostka...");
        filterJednostkaRejestrowa.setClearButtonVisible(true);
//        filterJednostkaRejestrowa.setValueChangeMode(ValueChangeMode.ON_CHANGE);
        filterJednostkaRejestrowa.setWidthFull();
        filterJednostkaRejestrowa.addValueChangeListener(e -> updateList());

        filterWlasciciel.setPlaceholder("Właściciel...");
        filterWlasciciel.setClearButtonVisible(true);
        filterWlasciciel.setWidthFull();
        filterWlasciciel.addValueChangeListener(e -> updateList());

        filterRodzice.setPlaceholder("Rodzic...");
        filterRodzice.setClearButtonVisible(true);
        filterRodzice.setWidthFull();
        filterRodzice.addValueChangeListener(e -> updateList());

        filterTeryt.setPlaceholder("TERYT...");
        filterTeryt.setClearButtonVisible(true);
        filterTeryt.setWidthFull();
        filterTeryt.addValueChangeListener(e -> updateList());

        filterDzialki.setPlaceholder("Działka...");
        filterDzialki.setClearButtonVisible(true);
        filterDzialki.setWidthFull();
        filterDzialki.addValueChangeListener(e -> updateList());

    }

    private void updateList() {
//        grid.setItems(gService.findBy(filterJednostkaRejestrowa.getValue(), filterWlasciciel.getValue(), filterOjciec.getValue(), filterTeryt.getValue(),filterDzialki.getValue()));
    }

    private void configureSkanList(){
        skanyListBox.addValueChangeListener(event -> {
            File f = event.getValue().getSkan();
            Image imige = getImigeStreamFromFile(f);
//            imige.setHeightFull();
//            imige.setWidthFull();
            layout2.addToSecondary(imige);
        });
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

    private Image getImigeFromStream(FileInputStream f) {
        Image image = new Image(new StreamResource("test", (StreamResourceWriter) f), "Nie udało się wczytać skanu" );
        return image;
    }

    private void configureGrid(UIService uiService) {
        List<RejestrGruntow> list = gService.findLimited(50);
        ListDataProvider<RejestrGruntow> dataProvider = new ListDataProvider<>(list);
        grid.setDataProvider(dataProvider);

        grid.addClassName("Rejestr-Grid");
        grid.setSizeFull();
        grid.setColumns(
//                "nrJednostkiRejestrowej"
//                , "zmiany"
        );

//        Grid.Column<RejestrGruntow> collumnNrJednostkiRejestrowej = rejestrGruntowGrid.getColumnByKey("nrJednostkiRejestrowej")
//
//                .setResizable(true).setFlexGrow(1)
//                .setTextAlign(ColumnTextAlign.CENTER);

        Grid.Column<RejestrGruntow> collumnNrJednostkiRejestrowej = grid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow.getNrJednostkiRejestrowej() + " <br> ";
            String dataZalozenia = rejestrGruntow.getDataZalozenia();
            value = "<div>" + value + "(" + dataZalozenia + ")" + "</div>";
            return new Html(value);
        })).setHeader("Jed. Rejestrowa").setResizable(true).setTextAlign(ColumnTextAlign.CENTER).setFlexGrow(1);

        Grid.Column<RejestrGruntow> collumnWlascicielWladajacy = grid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow.getWlasciciel().stream()
                    .map(w -> w.getName() + " " + w.getLastName()
//                            + (w.getFathersName() == null && w.getMathersName() == null ? "" : (w.getName().endsWith("a") ? " c. " : " s. "))
//                            + (w.getFathersName() == null && w.getMathersName() == null ? "" : " -->")
//                            + (w.getFathersName() == null ? "" : " o. " + w.getFathersName() )
//                            + (w.getMathersName() == null ? "" : " m. " + w.getMathersName())
                    )
                    .collect(Collectors.joining("<br> "));
            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Własciciel/Władający").setResizable(true);

        Grid.Column<RejestrGruntow> collumnRodzice = grid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow.getWlasciciel().stream()
                    .map(w -> (w.getFathersName() == null ? "" : " o. " + w.getFathersName())
                            + (w.getMathersName() == null ? "" : " m. " + w.getMathersName()))
                    .collect(Collectors.joining("<br> "));
            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Rodzice").setResizable(true);

        Grid.Column<RejestrGruntow> collumnTeryt = grid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow
                    .getDzialki().stream()
                    .map(p -> p.getTeryt().getMiejscowosc() + " - " + p.getTeryt().getTerytNumber() + ".")
                    .collect(Collectors.joining("<br> "));
            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("TERYT").setTextAlign(ColumnTextAlign.END).setResizable(true);


        Grid.Column<RejestrGruntow> collumnDzialki = grid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow
                    .getDzialki().stream()
                    .map(p -> p.getParcelNumber())
                    .collect(Collectors.joining("<br> "));

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Działki").setTextAlign(ColumnTextAlign.START).setResizable(true);

//        rejestrGruntowGrid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
//            Skany skan = rejestrGruntow.getSkany().get(0);
//
//            File skan1 = skan.getSkan();
//            Image image = new Image(new StreamResource("RG_003_070.jpg", () -> {
//                try {
//                    return new FileInputStream(skan1);
//                } catch (FileNotFoundException e) {
//                    // file not found
//                    e.printStackTrace();
//                }
//                return null;
//            }), "alt text");
//
//            return image;
//        })).setHeader("Skan");

//        rejestrGruntowGrid.addColumn(rejestrGruntow -> {
//            Skany skan = rejestrGruntow.getSkany().get(0);
//
//            return skan == null ? "-" : skan.getImage();
//        }).setHeader("Skany");

//        rejestrGruntowGrid.getColumns().forEach(col -> col.setAutoWidth(true));

//        rejestrGruntowGrid.addSelectionListener(event -> {
//        SingleSelect<Grid<RejestrGruntow>, RejestrGruntow> gridRejestrGruntowSingleSelect = rejestrGruntowGrid.asSingleSelect();
//        if(!gridRejestrGruntowSingleSelect.isEmpty()) {
//            RejestrGruntow rejestrG = gridRejestrGruntowSingleSelect.getValue();
//            List<Skany> skany = rejestrG.getSkany();
//            File skan1 = skany.get(0).getSkan();
//            Image image = new Image(new StreamResource("RG_003_070.jpg", () -> {
//                try {
//                    return new FileInputStream(skan1);
//                } catch (FileNotFoundException e) {
//                    // file not found
//                    e.printStackTrace();
//                }
//                return null;
//            }), "alt text");
//            image.setWidth("30%");
//            image.setHeight("30%");
//            layout.addToSecondary(image);
//
//        }
//    });

//        rejestrGruntowGrid.addSelectionListener(event -> {
//
//            SingleSelect<Grid<RejestrGruntow>, RejestrGruntow> gridRejestrGruntowSingleSelect = rejestrGruntowGrid.asSingleSelect();
//            if (!gridRejestrGruntowSingleSelect.isEmpty()) {
//                RejestrGruntow rejestrG = gridRejestrGruntowSingleSelect.getValue();
//                List<Skany> skany = rejestrG.getSkany();
//                Set<File> files = new LinkedHashSet<>();
//
//                for (Skany skan : skany) {
//                    files.add(skan.getSkan());
//                }
//
//                List<Image> images = new ArrayList<>();
//                for (File f : files) {
//                    Image image = new Image(new StreamResource(f.getName(), () -> {
//                        try {
//                            return new FileInputStream(f);
//                        } catch (FileNotFoundException e) {
//                            // file not found
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }), "alt text");
//                    image.setWidth("50%");
//                    image.setHeight("50%");
//                    images.add(image);
//
//                }
//
//                gridSkany.setItems(images);
//                gridSkany.setColumns();
//
//                for (Image image: images
//                     ) {
//                    gridSkany.addColumn(new ComponentRenderer<>(i -> image)).setHeader("skany").setResizable(true);
//
//                }
//            }
//            layout.addToSecondary(gridSkany);
//            });

//        rejestrGruntowGrid.addSelectionListener(event -> {
//
//            SingleSelect<Grid<RejestrGruntow>, RejestrGruntow> gridRejestrGruntowSingleSelect = rejestrGruntowGrid.asSingleSelect();
//            if (!gridRejestrGruntowSingleSelect.isEmpty()) {
//                RejestrGruntow rejestrG = gridRejestrGruntowSingleSelect.getValue();
//                List<Skany> skany = rejestrG.getSkany();
//                Set<File> files = new LinkedHashSet<>();
//
//                for (Skany skan : skany) {
//                    files.add(skan.getSkan());
//                }
////                imageListBox.setItems(skany);
//                List<Image> images = new ArrayList<>();
//                for (File f : files) {
//                    Image image = new Image(new StreamResource(f.getName(), () -> {
//                        try {
//                            return new FileInputStream(f);
//                        } catch (FileNotFoundException e) {
//                            // file not found
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }), "alt text");
//                    image.setWidth("10%");
//                    image.setHeight("10%");
//                    images.add(image);
////                    imageListBox.setRenderer(new ComponentRenderer<>(s -> image));
//
//                }
//
//                imageListBox.setRenderer(new ComponentRenderer<>(i -> {
//                    Div div = new Div();
//                    div.add(images.get(0));
//                    div.add(new Text("1"));
//                    return div;
//                }));
//                imageListBox.setRenderer(new ComponentRenderer<>(i -> {
//                    Div div = new Div();
//                    div.add(images.get(1));
//                    div.add(new Text("2"));
//                    return div;
//                }));
//
//                imageListBox.setItems(images);
//            }
//            layout.addToSecondary(imageListBox);
//        });

        grid.addComponentColumn(item -> createSkanViewingButton(grid, item, uiService))
                .setHeader("Skany");

        grid.addItemDoubleClickListener(event -> {
            SingleSelect<Grid<RejestrGruntow>, RejestrGruntow> gridRejestrGruntowSingleSelect = grid.asSingleSelect();
            if (!gridRejestrGruntowSingleSelect.isEmpty()) {
                RejestrGruntow rejestrG = gridRejestrGruntowSingleSelect.getValue();
                List<Skany> skany = rejestrG.getSkany();
                AtomicInteger imageNumb = new AtomicInteger();

                uiService.setSkanyList(skany);

                skanyListBox.setItems(skany);
//                uiService.getSkanyListBox().setItems(skany);

//                skanyListBox.setValue(skany.get(0));
//                skanyListBox.setMaxHeight("50");
//                skanyListBox.setMaxWidth("50");
                skanyListBox.setRenderer(new ComponentRenderer<>(s ->  {

                    Div text = new Div();
//                    text.setText(s.getId() +"");
                    imageNumb.set(imageNumb.get() + 1);
                    text.setText(imageNumb +"");
                    File f = s.getSkan();

                    Image image = getImigeStreamFromFile(f);
                    image.setWidth("80px");
                    image.setHeight("50px");
//                    image.setWidth("10%");
//                    image.setHeight("10%");
//                    image.setSrc();
                    FlexLayout wrapper = new FlexLayout();
                    text.getStyle().set("margin-right", "0.5em");
                    wrapper.add(text,image);
                    return wrapper;
                }));
//                skanViewing = new SkanViewing(rejestrG);
//                skanViewing.setRejestrGruntow(rejestrG);
            }
//            ScanService.setSkanyListBox(skanyListBox);
            formLayout.add(skanyListBox);
            layout2.addToPrimary(formLayout);


//            skanViewing.setSkanyListBox(skanyListBox);

        });

        configureFilterDataProvider(dataProvider, collumnNrJednostkiRejestrowej, collumnWlascicielWladajacy, collumnRodzice, collumnTeryt, collumnDzialki);

    }

    private RouterLink createSkanViewingButton(Grid<RejestrGruntow> grid, RejestrGruntow item, UIService uiService) {
        RouterLink routerLink = new RouterLink("", SkanViewing.class);

        Icon viewSkanIcon = new Icon(VaadinIcon.EYE);
//        viewSkanIcon.setColor("blue");
        routerLink.getElement().appendChild(viewSkanIcon.getElement());
        viewSkanIcon.addClickListener(event -> {
            grid.select(item);
            SingleSelect<Grid<RejestrGruntow>, RejestrGruntow> gridRejestrGruntowSingleSelect = grid.asSingleSelect();
            if (!gridRejestrGruntowSingleSelect.isEmpty()) {
                RejestrGruntow rejestrG = gridRejestrGruntowSingleSelect.getValue();
                List<Skany> skany = rejestrG.getSkany();
                uiService.setSkanyList(skany);
            }
        });

        return routerLink;
    }

    private void openDocumentDialog(StreamResource resource) {
        Dialog dialog = new Dialog();
        StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
        IFrame content = new IFrame(registration.getResourceUri().toString());
        content.setSizeFull();
        dialog.add(content);
        dialog.setWidth("80%");
        dialog.setHeight("100%");
        dialog.open();

    }

    private void configureFilterDataProvider(ListDataProvider<RejestrGruntow> dataProvider, Grid.Column<RejestrGruntow> collumnNrJednostkiRejestrowej, Grid.Column<RejestrGruntow> collumnWlascicielWladajacy, Grid.Column<RejestrGruntow> collumnRodzice, Grid.Column<RejestrGruntow> collumnTeryt, Grid.Column<RejestrGruntow> collumnDzialki) {
        HeaderRow filterRow = grid.appendHeaderRow();

        RejestrFilter rg = new RejestrFilter("test");
        dataProvider.setFilter(rejestrGruntow -> rg.test(rejestrGruntow));
        filterJednostkaRejestrowa.addValueChangeListener(event -> dataProvider.refreshAll());
        filterWlasciciel.addValueChangeListener(event -> dataProvider.refreshAll());
        filterRodzice.addValueChangeListener(event -> dataProvider.refreshAll());
        filterTeryt.addValueChangeListener(event -> dataProvider.refreshAll());
        filterDzialki.addValueChangeListener(event -> dataProvider.refreshAll());

        filterRow.getCell(collumnNrJednostkiRejestrowej).setComponent(filterJednostkaRejestrowa);
        filterRow.getCell(collumnWlascicielWladajacy).setComponent(filterWlasciciel);
        filterRow.getCell(collumnRodzice).setComponent(filterRodzice);
        filterRow.getCell(collumnTeryt).setComponent(filterTeryt);
        filterRow.getCell(collumnDzialki).setComponent(filterDzialki);
    }

    public ListBox<Skany> getSkanyListBox() {
        return skanyListBox;
    }
}

