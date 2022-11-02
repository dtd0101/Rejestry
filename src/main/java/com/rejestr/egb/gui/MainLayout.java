package com.rejestr.egb.gui;

import com.rejestr.egb.entity.*;
import com.rejestr.egb.pojo.Utilities;
import com.rejestr.egb.repository.ParcelRepo;
import com.rejestr.egb.repository.PersonRepo;
import com.rejestr.egb.repository.SkanyRepo;
import com.rejestr.egb.repository.ZmianaRepo;
import com.rejestr.egb.service.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//@Push(value = PushMode.MANUAL, transport = Transport.WEBSOCKET_XHR)
@Route("REJESTRY")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PWA(name = "REJESTRY", shortName = "RG")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainLayout extends VerticalLayout {
//    File baner = new File("src/main/resources/baners/Powiat.png");
//    File baneryHome = new File("src/main/resources/baners/BaneryHome.PNG");
    File baner = new File("K:\\GEOMIAR\\APLIKACJA\\banery\\Powiat.png");
    File baneryHome = new File("K:\\GEOMIAR\\APLIKACJA\\banery\\BaneryHome.PNG");
    Tab tabHome = new Tab("Home");
    Tab tabRejestry =  new Tab("Rejestry Gruntów");
    Tab tabZmiany = new Tab("Zmiany");
    Tab tabSkorDzialek = new Tab("Skorowidze Działek");
    Tab tabSkorWlascicieli = new Tab("Skorowidze Właścicieli");
    Tab tabWyloguj = new Tab();
    Tab tabMotyw = new Tab();


    VerticalLayout verticalLayout = new VerticalLayout();
    FormLayout formLayout = new FormLayout();
    SplitLayout splitLayout = new SplitLayout();

    Icon sun = new Icon(VaadinIcon.SUN_O);


    //Services
//    private UI ui;
    private RejestrGService gService;
    private SkorowidzDzService skorowidzDzService;
    private ZmianyService zmianyService;
    private SkorowidzWlaService skorowidzWlaService;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private ParcelRepo parcelRepo;

    @Autowired
    private SkanyRepo skanyRepo;

    @Autowired
    private ZmianaRepo zmianaRepo;

    @Autowired
    UIService uiService;

    @Autowired
    UIServiceZmiany uiServiceZmiany;

    @Autowired
    UIServiceSkorDz uiServiceSkorDz;

    @Autowired
    UIServiceSkorWla uiServiceSkorWla;


    StringBuilder opisProjektu = new StringBuilder();
    Label labelOpisProjektu = new Label();
    Span span = new Span();

    TextField filterJednostkaRejestrowa = new TextField();
    TextField filterWlasciciel = new TextField();
    TextField filterWlascicielNazwisko = new TextField();
    TextField filterOjciec = new TextField();
    TextField filterMatka = new TextField();
    TextField filterDzialki = new TextField();
    TextField filterTerytRejestrGruntow = new TextField();
    TextField filterMiejscowoscRejestrGruntow = new TextField();
    TextField filterGminaRejestrGruntow  = new TextField();

    Grid<RejestrGruntow> rejestrGruntowGrid = new Grid<>(RejestrGruntow.class);

    Grid<SkorowidzDzialek> skorowidzDzialekGrid = new Grid<>(SkorowidzDzialek.class);
    TextField filterRok = new TextField();
    TextField filterGmina = new TextField();
    TextField filterMiejscowosc = new TextField();
    TextField filterTerytSkorDzialek = new TextField();

    Grid<Zmiana> zmianyGrid = new Grid<>(Zmiana.class);
    TextField filterNrZmiany = new TextField();
    TextField filterWlascielZmiany = new TextField();
    TextField filterDzialkiZmiany = new TextField();
    TextField filterJedRejestrowaZmiany = new TextField();
    TextField filterGminaZmiany = new TextField();
    TextField filterMiejscowoscZmiany = new TextField();

    Grid<SkorowidzWlascicieli> skorowidzWlaGrid = new Grid<>(SkorowidzWlascicieli.class);
    TextField filterRokWla = new TextField();
    TextField filterGminaWla = new TextField();
    TextField filterMiejscowoscWla = new TextField();
    TextField filterTerytSkorDzialekWla = new TextField();

    Image imgBaneryHome;
    private Button clearCriteriaButton = new Button();

    public MainLayout(RejestrGService gService, SkorowidzDzService skorowidzDzService, ZmianyService zmianyService, SkorowidzWlaService skorowidzWlaService) {
        addClassNames("main-view");
        setSizeFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        imgBaneryHome = Utilities.getImigeStreamFromFile(baneryHome);

        verticalLayout.setSizeFull();
//        verticalLayout.add(imgBaneryHome,span);
//        verticalLayout.setAlignItems(Alignment.CENTER);


        this.gService = gService;
        this.skorowidzDzService = skorowidzDzService;
        this.zmianyService = zmianyService;
        this.skorowidzWlaService = skorowidzWlaService;

        Utilities.configSun(sun);
        Anchor anchorLogout = new Anchor("/logout", "Log out");
        tabWyloguj.add(anchorLogout);
        tabMotyw.add(sun);



        Image img = Utilities.getImigeStreamFromFile(baner);
        Tabs tabs = new Tabs(tabHome, tabRejestry, tabZmiany , tabSkorDzialek, tabSkorWlascicieli, tabWyloguj,tabMotyw);
        tabs.setSelectedTab(tabRejestry);

        configureRejestryGrid();
        configureRejestryFiler();
        verticalLayout.add(rejestrGruntowGrid);
        img.setHeight("66px");

        horizontalLayout.add(img,tabs);

//        configureRejestryGrid();
//        configureRejestryFiler();
        configureSkorowidzDzialekGrid();
        configureSkorowidzDzialekFiler();
        configureZmianyGrid();
        configureZmianyFiler();
        configureSkorowidzWlaGrid();
        configureSkorowidzWla();


        configOpis();

        Div content = new Div(rejestrGruntowGrid);
        content.addClassName("content");
//        content.setSizeFull();


        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSource().getSelectedTab();

            if(selectedTab == tabHome){
                verticalLayout.add(imgBaneryHome,span);
                verticalLayout.setAlignItems(Alignment.CENTER);
            }else {
                verticalLayout.removeAll();
            }
            if(selectedTab == tabRejestry){
//                UI.getCurrent().getPage().reload();
//                configureRejestryGrid();
//                configureRejestryFiler();
                if(uiService != null && uiService.getRejestrGruntow() != null){
                    filterJednostkaRejestrowa.setValue(uiService.getRejestrGruntow().getNrJednostkiRejestrowej());
                    rejestrGruntowGrid.getSelectionModel().select(uiService.getRejestrGruntow());
                }
                verticalLayout.add(rejestrGruntowGrid);
            }else {
                verticalLayout.remove(rejestrGruntowGrid);
            }
            if(selectedTab == tabZmiany){
//                 configureZmianyGrid();
//                 configureZmianyFiler();
                verticalLayout.add(zmianyGrid);
            }else {
                verticalLayout.remove(zmianyGrid);
            }
            if(selectedTab == tabSkorDzialek){
                verticalLayout.add(skorowidzDzialekGrid);
            }else {
                verticalLayout.remove(skorowidzDzialekGrid);
            }
            if(selectedTab == tabSkorWlascicieli){

                verticalLayout.add(skorowidzWlaGrid);
            }else {
                verticalLayout.remove(skorowidzWlaGrid);
            }
        });
        add(horizontalLayout,verticalLayout);
        verticalLayout.setSizeFull();
        skorowidzDzialekGrid.setSizeFull();

        verticalLayout.add(rejestrGruntowGrid);
//        add(horizontalLayout,splitLayout);

        updateListRejestry();
        updateListSkorowidzeDzialek();
    }

    private void configOpis() {
        span.getElement().setProperty("innerHTML", "<br><br><H3><b> Cyfryzacja materiałów źródłowych powiatowego zasobu geodezyjnego i kartograficznego </b><br>" +
                "<b> i zasilenia programu funkcyjnego w PODGiK w Przemyślu </b></H3><br><br>" +
                "<b> Skanowanie wykonano w rozdzielczości 400 dpi. </b>");
    }

    private void configureZmianyFiler() {
        filterJedRejestrowaZmiany.setPlaceholder("Jednostka...");
        filterJedRejestrowaZmiany.setClearButtonVisible(true);
        filterJedRejestrowaZmiany.setWidthFull();
        filterJedRejestrowaZmiany.addValueChangeListener(e -> updateListZmiany());

        filterWlascielZmiany.setPlaceholder("Właściciel...");
        filterWlascielZmiany.setClearButtonVisible(true);
        filterWlascielZmiany.setWidthFull();
        filterWlascielZmiany.addValueChangeListener(e -> updateListZmiany());

        filterNrZmiany.setPlaceholder("Nr zmiany...");
        filterNrZmiany.setClearButtonVisible(true);
        filterNrZmiany.setWidthFull();
        filterNrZmiany.addValueChangeListener(e -> updateListZmiany());

        filterDzialkiZmiany.setPlaceholder("Działka...");
        filterDzialkiZmiany.setClearButtonVisible(true);
        filterDzialkiZmiany.setWidthFull();
        filterDzialkiZmiany.addValueChangeListener(e -> updateListZmiany());

        filterGminaZmiany.setPlaceholder("Gmina...");
        filterGminaZmiany.setClearButtonVisible(true);
        filterGminaZmiany.setWidthFull();
        filterGminaZmiany.addValueChangeListener(e -> updateListZmiany());

        filterMiejscowoscZmiany.setPlaceholder("Miejscowość...");
        filterMiejscowoscZmiany.setClearButtonVisible(true);
        filterMiejscowoscZmiany.setWidthFull();
        filterMiejscowoscZmiany.addValueChangeListener(e -> updateListZmiany());

    }

    private void updateListZmiany() {
        zmianyGrid.setItems(zmianyService.findBy(filterNrZmiany.getValue(), filterJedRejestrowaZmiany.getValue(),filterWlascielZmiany.getValue(),filterDzialkiZmiany.getValue(),filterGminaZmiany.getValue(),filterMiejscowoscZmiany.getValue()));
    }

    private void configureZmianyGrid() {
            List<Zmiana> list = zmianyService.findAll();
            ListDataProvider<Zmiana> dataProvider = new ListDataProvider<>(list);
            zmianyGrid.setDataProvider(dataProvider);


        zmianyGrid.addClassName("zmiany-grid");
        zmianyGrid.setSizeFull();

        zmianyGrid.setColumns();

            Grid.Column<Zmiana> columnNrZmiany = zmianyGrid.addColumn(new ComponentRenderer<>(zmiana -> {
                String value = zmiana.getNrZmiany();

                value = "<div>" + value + "</div>";
                return new Html(value);
            })).setHeader("Nr zmiany").setResizable(true).setTextAlign(ColumnTextAlign.CENTER).setFlexGrow(1);;

        Grid.Column<Zmiana> columnNrRejestru = zmianyGrid.addColumn(new ComponentRenderer<>(zmiana -> {
            String value = zmiana.getRejestrGruntow().getNrJednostkiRejestrowej();
            String dataZalozenia = zmiana.getRejestrGruntow().getDataZalozenia();

            value = "<div>" + value + " <br>(" + dataZalozenia + ")" + "</div>";
            return new Html(value);
        })).setHeader("Jed. Rej.").setResizable(true).setTextAlign(ColumnTextAlign.CENTER).setFlexGrow(1);
//    })).setHeader("Z Jed. Rej.").setResizable(true).setTextAlign(ColumnTextAlign.CENTER).setFlexGrow(1);


//    Grid.Column<Zmiana> columnDoRejestru = zmianyGrid.addColumn(new ComponentRenderer<>(zmiana -> {
//            String value = zmiana.getDoRejestrGruntow().getNrJednostkiRejestrowej();
//            String dataZalozenia = zmiana.getDoRejestrGruntow().getDataZalozenia();
//
//            value = "<div>" + value + " <br>(" + dataZalozenia + ")" + "</div>";
//            return new Html(value);
//        })).setHeader("Do Jed. Rej.").setResizable(true).setTextAlign(ColumnTextAlign.CENTER).setFlexGrow(1);

        Grid.Column<Zmiana> columnDoRejestru = zmianyGrid.addColumn(new ComponentRenderer<>(zmiana -> {
            String value = zmiana.getDoRejestru();

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Do Rejestru").setResizable(true);

        Grid.Column<Zmiana> collumnGmina = zmianyGrid.addColumn(new ComponentRenderer<>(zmiana -> {
            String value = zmiana.getRejestrGruntow()
                    .getDzialki().stream()
                    .map(p -> p.getTeryt().getGmina())
                    .collect(Collectors.joining("<br> "));

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Gmina").setTextAlign(ColumnTextAlign.CENTER);

        Grid.Column<Zmiana> collumnMiejsc = zmianyGrid.addColumn(new ComponentRenderer<>(zmiana -> {
            String value = zmiana.getRejestrGruntow()
                    .getDzialki().stream()
                    .map(p -> p.getTeryt().getMiejscowosc())
                    .collect(Collectors.joining("<br> "));

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Miejscowość").setTextAlign(ColumnTextAlign.START);

        Grid.Column<Zmiana> columnOpis = zmianyGrid.addColumn(new ComponentRenderer<>(zmiana -> {
                String value = zmiana.getOpisZmiany();

                value = "<div>" + value + "</div>";
                return new Html(value);
            })).setHeader("Opis").setResizable(true);

        Grid.Column<Zmiana> columnUwagi = zmianyGrid.addColumn(new ComponentRenderer<>(zmiana -> {
            String value = zmiana.getUwagi();

                value = "<div>" + value + "</div>";
                return new Html(value);
            })).setHeader("Uwagi").setResizable(true);

//        Grid.Column<Zmiana> columnWlasciciel = zmianyGrid.addColumn(new ComponentRenderer<>(zmiana -> {
//
//            CheckboxGroup<Person> checkbox = zmiana.getPersonCheckboxGroup();
//            checkbox.setItems(zmiana.getRejestrGruntow().getWlasciciel());
//            checkbox.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
//            if(!zmiana.getPersonList().isEmpty()){
//                checkbox.setValue(zmiana.getPersonList().stream().collect(Collectors.toSet()));
//            }
//            return checkbox;
//        })).setHeader("Własciciel/Władający").setResizable(true);
//
//        Grid.Column<Zmiana> columnDzialki = zmianyGrid.addColumn(new ComponentRenderer<>(zmiana -> {
//
//            CheckboxGroup<Parcel> parcelCheckboxGroup = zmiana.getParcelCheckboxGroup();
//            parcelCheckboxGroup.setItems(zmiana.getRejestrGruntow().getDzialki());
//            parcelCheckboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
//            if(!zmiana.getParcelList().isEmpty()){
//                parcelCheckboxGroup.setValue(zmiana.getParcelList().stream().collect(Collectors.toSet()));
//            }
//            return parcelCheckboxGroup;
//        })).setHeader("Działki").setTextAlign(ColumnTextAlign.CENTER).setFlexGrow(1);


        Grid.Column<Zmiana> clolumnZmiany = zmianyGrid.addComponentColumn(item -> createZmianyViewingButtonZmiany(zmianyGrid, item, uiServiceZmiany))
                .setHeader("Zmiany");


//        configureFilterDataProviderZmiany(dataProvider,columnNrZmiany,collumnGmina,collumnMiejsc,columnNrRejestru,columnWlasciciel,columnDzialki);
        configureFilterDataProviderZmiany(dataProvider,columnNrZmiany,collumnGmina,collumnMiejsc,columnNrRejestru, null, null);

        List<Zmiana> z = new LinkedList<>();
        List<Zmiana> all = zmianyService.findAll();
        z.addAll(all);

        zmianyGrid.addItemClickListener(event -> {
            Zmiana item = event.getItem();
            z.clear();
            z.add(item);
        });


        Binder<Zmiana> binder = new Binder<>(Zmiana.class);
        Editor<Zmiana> editor = zmianyGrid.getEditor();
        editor.setBinder(binder);
////        editor.setBuffered(true);
        TextField numerZmiany = new TextField();
        numerZmiany.getElement().addEventListener("keydown", event -> zmianyGrid.getEditor().cancel())
                .setFilter("event.key === 'Tab' && event.shiftKey");
        binder.bind(numerZmiany, "nrZmiany");
        columnNrZmiany.setEditorComponent(numerZmiany);

        TextField doRejestruTxt = new TextField();
        doRejestruTxt.getElement().addEventListener("keydown", event -> zmianyGrid.getEditor().cancel())
                .setFilter("event.key === 'Tab' && event.shiftKey");
        binder.bind(doRejestruTxt, "doRejestru");
        columnDoRejestru.setEditorComponent(doRejestruTxt);

        TextField opis = new TextField();
        TextField uwagi = new TextField();

        binder.bind(opis, "opisZmiany");
        opis.getElement().addEventListener("keydown", event -> zmianyGrid.getEditor().cancel())
                .setFilter("event.key === 'Tab' && event.shiftKey");
        binder.bind(uwagi, "uwagi");
        columnOpis.setEditorComponent(opis);

        uwagi.getElement().addEventListener("keydown", event -> zmianyGrid.getEditor().cancel())
                .setFilter("event.key === 'Tab' && event.shiftKey");
        columnUwagi.setEditorComponent(uwagi);

//        Select<RejestrGruntow> doRejestruSelect = new Select<>();
//        doRejestruSelect.setItems(gService.findAll());
//        doRejestruSelect.setItemLabelGenerator(RejestrGruntow::getNrJednostkiRejestrowej);
//        binder.bind(doRejestruSelect, "doRejestrGruntow");
//        columnDoRejestru.setEditorComponent(doRejestruSelect);
//        doRejestruSelect.getElement().addEventListener("keydown", event -> {
//                zmianyGrid.getEditor().cancel();
//        }).setFilter("event.key === 'Tab' && !event.shiftKey");

        zmianyGrid.addItemDoubleClickListener(event -> {
//            ui = UI.getCurrent();

//            Select<RejestrGruntow> doRejestruSelect = new Select<>();
//            ui.access(() -> {doRejestruSelect.setItems(gService.findBy("","Jan","","","","","",""));
////        doRejestruSelect.setItems(gService.findAll());
//                doRejestruSelect.setItemLabelGenerator(RejestrGruntow::getNrJednostkiRejestrowej);
//                binder.bind(doRejestruSelect, "doRejestrGruntow");
//                columnDoRejestru.setEditorComponent(doRejestruSelect);
//                doRejestruSelect.getElement().addEventListener("keydown", event1 ->  {
//                    zmianyGrid.getEditor().cancel();
//                }).setFilter("event.key === 'Tab' && !event.shiftKey");
//            });
//        ui.push();

            Zmiana item = event.getItem();
            CheckboxGroup<Person> checkbox = item.getPersonCheckboxGroup();
            checkbox.addSelectionListener(e -> {
                Set<Person> allSelectedItems = e.getAllSelectedItems();
                allSelectedItems.stream().forEach(person -> {person.setZmiana(item);});
                personRepo.saveAll(allSelectedItems);
//                item.getPersonList().clear();
                item.setPersonList(new LinkedList<>(allSelectedItems));
//                zmiana.addPerson(allSelectedItems.stream().forEach().;);
//                item.setPersonList(allSelectedItems.stream().collect(Collectors.toList()));
                zmianyService.saveZmiana(item);
                item.getPersonCheckboxGroup().setValue(allSelectedItems);
            });
//
            CheckboxGroup<Parcel> parcelCheckboxGroup = item.getParcelCheckboxGroup();
            parcelCheckboxGroup.addSelectionListener(e -> {
                Set<Parcel> allSelectedItems = e.getAllSelectedItems();
                allSelectedItems.stream().forEach(parcel -> {parcel.setZmiana(item);});
                parcelRepo.saveAll(allSelectedItems);
                item.setParcelList(new LinkedList<>(allSelectedItems));
                zmianyService.saveZmiana(item);
                item.getParcelCheckboxGroup().setValue(allSelectedItems);
            });


//            event.getItem().getPersonCheckboxGroup().setEnabled(true);
            zmianyGrid.getEditor().editItem(event.getItem());
            numerZmiany.focus();
        });



        binder.addValueChangeListener(event -> {

            zmianyGrid.getEditor().refresh();
            zmianyService.saveZmiana(binder.getBean());
            updateListZmiany();
        });
    }



    private void configureFilterDataProviderZmiany(ListDataProvider<Zmiana> dataProvider, Grid.Column<Zmiana> columnNrZmiany, Grid.Column<Zmiana> columnNrRejestru, Grid.Column<Zmiana> columnGmina, Grid.Column<Zmiana> columnMiejsc, Grid.Column<Zmiana> columnWlasciciel, Grid.Column<Zmiana> columnDzialki) {
        HeaderRow filterRow = zmianyGrid.appendHeaderRow();

        dataProvider.setFilter(zmiana -> true);
        filterNrZmiany.addValueChangeListener(event -> dataProvider.refreshAll());
        filterJedRejestrowaZmiany.addValueChangeListener(event -> dataProvider.refreshAll());
        filterGminaZmiany.addValueChangeListener(event -> dataProvider.refreshAll());
        filterMiejscowoscZmiany.addValueChangeListener(event -> dataProvider.refreshAll());
//        filterWlascielZmiany.addValueChangeListener(event -> dataProvider.refreshAll());
//        filterDzialkiZmiany.addValueChangeListener(event -> dataProvider.refreshAll());


//        filterRow.getCell(columnNrZmiany).setComponent(filterNrZmiany);
//        filterRow.getCell(columnNrRejestru).setComponent(filterJedRejestrowaZmiany);
//        filterRow.getCell(columnGmina).setComponent(filterGminaZmiany);
//        filterRow.getCell(columnMiejsc).setComponent(filterMiejscowoscZmiany);
//        filterRow.getCell(columnWlasciciel).setComponent(filterWlascielZmiany);
//        filterRow.getCell(columnDzialki).setComponent(filterDzialkiZmiany);

        filterRow.getCell(columnNrZmiany).setComponent(filterNrZmiany);
        filterRow.getCell(columnNrRejestru).setComponent(filterGminaZmiany);
        filterRow.getCell(columnGmina).setComponent(filterMiejscowoscZmiany);
        filterRow.getCell(columnMiejsc).setComponent(filterJedRejestrowaZmiany);
//        filterRow.getCell(columnWlasciciel).setComponent(filterWlascielZmiany);
//        filterRow.getCell(columnDzialki).setComponent(filterDzialkiZmiany);

    }


    private void configureSkorowidzDzialekFiler() {
        filterRok.setPlaceholder("Rok...");
        filterRok.setClearButtonVisible(true);
        filterRok.setWidthFull();
//        filterRok.setValue("1974");
        filterRok.addValueChangeListener(e -> updateListSkorowidzeDzialek());

        filterGmina.setPlaceholder("Gmina...");
        filterGmina.setClearButtonVisible(true);
        filterGmina.setWidthFull();
        filterGmina.addValueChangeListener(e -> updateListSkorowidzeDzialek());

        filterMiejscowosc.setPlaceholder("Miejscowowść...");
        filterMiejscowosc.setClearButtonVisible(true);
        filterMiejscowosc.setWidthFull();
        filterMiejscowosc.addValueChangeListener(e -> updateListSkorowidzeDzialek());

        filterTerytSkorDzialek.setPlaceholder("Teryt...");
        filterTerytSkorDzialek.setClearButtonVisible(true);
        filterTerytSkorDzialek.setWidthFull();
        filterTerytSkorDzialek.addValueChangeListener(e -> updateListSkorowidzeDzialek());
    }

    private void configureSkorowidzWla() {
        filterRokWla.setPlaceholder("Rok...");
//        filterRokWla.setValue("1974");
        filterRokWla.setClearButtonVisible(true);
        filterRokWla.setWidthFull();
        filterRokWla.addValueChangeListener(e -> updateListSkorowidzeWla());

        filterGminaWla.setPlaceholder("Gmina...");
        filterGminaWla.setClearButtonVisible(true);
        filterGminaWla.setWidthFull();
        filterGminaWla.addValueChangeListener(e -> updateListSkorowidzeWla());

        filterMiejscowoscWla.setPlaceholder("Miejscowowść...");
        filterMiejscowoscWla.setClearButtonVisible(true);
        filterMiejscowoscWla.setWidthFull();
        filterMiejscowoscWla.addValueChangeListener(e -> updateListSkorowidzeWla());

        filterTerytSkorDzialekWla.setPlaceholder("Teryt...");
        filterTerytSkorDzialekWla.setClearButtonVisible(true);
        filterTerytSkorDzialekWla.setWidthFull();
        filterTerytSkorDzialekWla.addValueChangeListener(e -> updateListSkorowidzeWla());
    }

    private void updateListSkorowidzeDzialek() {
        skorowidzDzialekGrid.setItems(skorowidzDzService.findBy(filterRok.getValue().toUpperCase(),filterGmina.getValue().toUpperCase(), filterMiejscowosc.getValue().toUpperCase(),filterTerytSkorDzialek.getValue()));
    }

    private void updateListSkorowidzeWla() {
        skorowidzWlaGrid.setItems(skorowidzWlaService.findBy(filterRokWla.getValue(),filterGminaWla.getValue().toUpperCase(), filterMiejscowoscWla.getValue().toUpperCase(),filterTerytSkorDzialekWla.getValue().toUpperCase()));
    }


    private void configureSkorowidzDzialekGrid() {
//        List<SkorowidzDzialek> list = skorowidzDzService.findAll();
        List<SkorowidzDzialek> list = skorowidzDzService.findLimited();
        ListDataProvider<SkorowidzDzialek> dataProvider = new ListDataProvider<>(list);
        skorowidzDzialekGrid.setDataProvider(dataProvider);


        skorowidzDzialekGrid.addClassName("Skorowidz-Grid");
        skorowidzDzialekGrid.setSizeFull();

        skorowidzDzialekGrid.setColumns();

        Grid.Column<SkorowidzDzialek> columnRok = skorowidzDzialekGrid.addColumn(new ComponentRenderer<>(skorowidzDzialek -> {
            String value = skorowidzDzialek.getRok();

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("ROK").setResizable(true).setTextAlign(ColumnTextAlign.CENTER);

        Grid.Column<SkorowidzDzialek> columnGmina = skorowidzDzialekGrid.addColumn(new ComponentRenderer<>(skorowidzDzialek -> {
            String value = skorowidzDzialek.getTeryt().getGmina();

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("GMINA").setResizable(true);

        Grid.Column<SkorowidzDzialek> columnMiejscowosc = skorowidzDzialekGrid.addColumn(new ComponentRenderer<>(skorowidzDzialek -> {
            String value = skorowidzDzialek.getTeryt().getMiejscowosc();

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("MIEJSCOWOŚĆ").setResizable(true);

        Grid.Column<SkorowidzDzialek> columnTeryt = skorowidzDzialekGrid.addColumn(new ComponentRenderer<>(skorowidzDzialek -> {
            String value = skorowidzDzialek.getTeryt().getTerytNumber();

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("TERYT").setResizable(true).setTextAlign(ColumnTextAlign.CENTER);

        skorowidzDzialekGrid.addComponentColumn(item -> createSkanViewingButtonSkorowidzeDz(skorowidzDzialekGrid, item))
                .setHeader("Skany");

        configureFilterDataProviderSkorowidzDz(dataProvider,columnRok,columnGmina,columnMiejscowosc,columnTeryt);
    }


    private void configureSkorowidzWlaGrid() {
//        List<SkorowidzWlascicieli> list = skorowidzWlaService.findAll();
        List<SkorowidzWlascicieli> list = skorowidzWlaService.findLimited();
        ListDataProvider<SkorowidzWlascicieli> dataProvider = new ListDataProvider<>(list);
        skorowidzWlaGrid.setDataProvider(dataProvider);


        skorowidzWlaGrid.addClassName("SkorowidzWla-Grid");
        skorowidzWlaGrid.setSizeFull();

        skorowidzWlaGrid.setColumns();

        Grid.Column<SkorowidzWlascicieli> columnRok = skorowidzWlaGrid.addColumn(new ComponentRenderer<>(skorowidzWlascicieli -> {
            String value = skorowidzWlascicieli.getRok();

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("ROK").setResizable(true).setTextAlign(ColumnTextAlign.CENTER);

        Grid.Column<SkorowidzWlascicieli> columnGmina = skorowidzWlaGrid.addColumn(new ComponentRenderer<>(skorowidzWlascicieli -> {
            String value = skorowidzWlascicieli.getTeryt().getGmina();

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("GMINA").setResizable(true);

        Grid.Column<SkorowidzWlascicieli> columnMiejscowosc = skorowidzWlaGrid.addColumn(new ComponentRenderer<>(skorowidzWlascicieli -> {
            String value = skorowidzWlascicieli.getTeryt().getMiejscowosc();

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("MIEJSCOWOŚĆ").setResizable(true);

        Grid.Column<SkorowidzWlascicieli> columnTeryt = skorowidzWlaGrid.addColumn(new ComponentRenderer<>(skorowidzDzialek -> {
            String value = skorowidzDzialek.getTeryt().getTerytNumber();

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("TERYT").setResizable(true).setTextAlign(ColumnTextAlign.CENTER);

        skorowidzWlaGrid.addComponentColumn(item -> createSkanViewingButtonSkorowidzeWla(skorowidzWlaGrid, item))
                .setHeader("Skany");

        configureFilterDataProviderSkorowidzWla(dataProvider,columnRok,columnGmina,columnMiejscowosc,columnTeryt);
    }

    private Anchor createSkanViewingButtonSkorowidzeDz(Grid<SkorowidzDzialek> grid, SkorowidzDzialek item) {
//        RouterLink routerLink = new RouterLink("", SkanViewingSkorowidzeDz.class);
        Anchor a = new Anchor();
        a.setHref("/Skorowidze-Dzialek-SKANY");
        a.setTarget("_blank");
        Icon viewSkanIcon = new Icon(VaadinIcon.EYE);
        a.getElement().appendChild(viewSkanIcon.getElement());

        viewSkanIcon.addClickListener(event -> {
            grid.getSelectionModel().select(item);
            SingleSelect<Grid<SkorowidzDzialek>, SkorowidzDzialek> gridRejestrGruntowSingleSelect = grid.asSingleSelect();
            if (!gridRejestrGruntowSingleSelect.isEmpty()) {
                SkorowidzDzialek value = gridRejestrGruntowSingleSelect.getValue();
                List<Skany> skany = value.getSkany();
                uiServiceSkorDz.setSkanyList(skany);
            }
        });

        return a;
    }

    private Anchor createSkanViewingButtonSkorowidzeWla(Grid<SkorowidzWlascicieli> grid, SkorowidzWlascicieli item) {
//        RouterLink routerLink = new RouterLink("", SkanViewingSkorowidzeWla.class);
        Anchor a = new Anchor();
        a.setHref("/Skorowidze-Wlascicieli-SKANY");
        a.setTarget("_blank");

        Icon viewSkanIcon = new Icon(VaadinIcon.EYE);
        a.getElement().appendChild(viewSkanIcon.getElement());

        viewSkanIcon.addClickListener(event -> {
            grid.getSelectionModel().select(item);
            SingleSelect<Grid<SkorowidzWlascicieli>, SkorowidzWlascicieli> gridRejestrGruntowSingleSelect = grid.asSingleSelect();
            if (!gridRejestrGruntowSingleSelect.isEmpty()) {
                SkorowidzWlascicieli value = gridRejestrGruntowSingleSelect.getValue();
                List<Skany> skany = value.getSkany();
                uiServiceSkorWla.setSkanyList(skany);
            }
        });

        return a;
    }

    private void configureFilterDataProviderSkorowidzDz(ListDataProvider<SkorowidzDzialek> dataProvider,Grid.Column<SkorowidzDzialek> columnRok, Grid.Column<SkorowidzDzialek> columnGmina, Grid.Column<SkorowidzDzialek> columnMiejscowosc, Grid.Column<SkorowidzDzialek> columnTeryt) {
        HeaderRow filterRow = skorowidzDzialekGrid.appendHeaderRow();

        dataProvider.setFilter(skorowidzDzialek -> true);
        filterRok.addValueChangeListener(event -> dataProvider.refreshAll());
        filterGmina.addValueChangeListener(event -> dataProvider.refreshAll());
        filterMiejscowosc.addValueChangeListener(event -> dataProvider.refreshAll());
        filterTerytSkorDzialek.addValueChangeListener(event -> dataProvider.refreshAll());

        filterRow.getCell(columnRok).setComponent(filterRok);
        filterRow.getCell(columnGmina).setComponent(filterGmina);
        filterRow.getCell(columnMiejscowosc).setComponent(filterMiejscowosc);
        filterRow.getCell(columnTeryt).setComponent(filterTerytSkorDzialek);

    }

    private void configureFilterDataProviderSkorowidzWla(ListDataProvider<SkorowidzWlascicieli> dataProvider,Grid.Column<SkorowidzWlascicieli> columnRok, Grid.Column<SkorowidzWlascicieli> columnGmina, Grid.Column<SkorowidzWlascicieli> columnMiejscowosc, Grid.Column<SkorowidzWlascicieli> columnTeryt) {
        HeaderRow filterRow = skorowidzWlaGrid.appendHeaderRow();

        dataProvider.setFilter(skorowidzWlascicieli -> true);
        filterRokWla.addValueChangeListener(event -> dataProvider.refreshAll());
        filterGminaWla.addValueChangeListener(event -> dataProvider.refreshAll());
        filterMiejscowoscWla.addValueChangeListener(event -> dataProvider.refreshAll());
        filterTerytSkorDzialekWla.addValueChangeListener(event -> dataProvider.refreshAll());

        filterRow.getCell(columnRok).setComponent(filterRokWla);
        filterRow.getCell(columnGmina).setComponent(filterGminaWla);
        filterRow.getCell(columnMiejscowosc).setComponent(filterMiejscowoscWla);
        filterRow.getCell(columnTeryt).setComponent(filterTerytSkorDzialekWla);
    }

    private void configureRejestryGrid() {
//        List<RejestrGruntow> list = gService.findAll();
        List<RejestrGruntow> list = gService.findLimited(20);
        ListDataProvider<RejestrGruntow> listDataProvider = new ListDataProvider<>(list);
        rejestrGruntowGrid.setDataProvider(listDataProvider);

        rejestrGruntowGrid.addClassName("Rejestr-Grid");
        rejestrGruntowGrid.setColumns();

        Grid.Column<RejestrGruntow> collumnNrJednostkiRejestrowej = rejestrGruntowGrid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow.getNrJednostkiRejestrowej() + " <br> ";
            String dataZalozenia = rejestrGruntow.getDataZalozenia();
            value = "<div>" + value + "(" + dataZalozenia + ")" + "</div>";
            return new Html(value);
        })).setHeader("Jed. Rejestrowa").setResizable(true).setTextAlign(ColumnTextAlign.CENTER).setFlexGrow(1);

        Grid.Column<RejestrGruntow> collumnGmina = rejestrGruntowGrid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow
                    .getDzialki().stream()
                    .map(p -> p.getTeryt().getGmina())
                    .collect(Collectors.joining("<br> "));

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Gmina").setTextAlign(ColumnTextAlign.CENTER);

        Grid.Column<RejestrGruntow> collumnMiejsc = rejestrGruntowGrid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow
                    .getDzialki().stream()
                    .map(p -> p.getTeryt().getMiejscowosc())
                    .collect(Collectors.joining("<br> "));

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Miejscowość").setTextAlign(ColumnTextAlign.START);

        Grid.Column<RejestrGruntow> collumnWlascicielWladajacy = rejestrGruntowGrid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value =
                    rejestrGruntow.getWlasciciel().stream()
                    .map(w -> Arrays.stream(w.getName().split("\\s+")).map(s -> s.toString()).collect(Collectors.joining("<br>")))
                    .collect(Collectors.joining("<br> "));
            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader(new Html("<div>" + "Własciciel/Władający <br> Imie/Nazwa" + "</div>")).setResizable(true);

        Grid.Column<RejestrGruntow> collumnWlascicielWladajacyNazwisko = rejestrGruntowGrid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow.getWlasciciel().stream()
                    .map(w -> w.getLastName())
                    .collect(Collectors.joining("<br> "));
            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader(new Html("<div>" + "Własciciel/Władający <br> Nazwisko" + "</div>")).setResizable(true);

        Grid.Column<RejestrGruntow> collumnOjciec = rejestrGruntowGrid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow.getWlasciciel().stream()
                    .map(w -> w.getFathersName())
                    .collect(Collectors.joining("<br> "));
            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Ojciec").setResizable(true).setFlexGrow(1);

        Grid.Column<RejestrGruntow> collumnMatka = rejestrGruntowGrid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow.getWlasciciel().stream()
                    .map(w -> w.getMathersName())
                    .collect(Collectors.joining("<br> "));
            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Matka").setResizable(true).setFlexGrow(1);

        Grid.Column<RejestrGruntow> collumnTeryt = rejestrGruntowGrid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow
                    .getDzialki().stream()
                    .map(p -> p.getTeryt().getTerytNumber() + ".")
                    .collect(Collectors.joining("<br> "));

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("TERYT").setTextAlign(ColumnTextAlign.END).setResizable(true);

        Grid.Column<RejestrGruntow> collumnDzialki = rejestrGruntowGrid.addColumn(new ComponentRenderer<>(rejestrGruntow -> {
            String value = rejestrGruntow
                    .getDzialki().stream()
                    .map(p -> p.getParcelNumber())
                    .collect(Collectors.joining("<br> "));

            value = "<div>" + value + "</div>";
            return new Html(value);
        })).setHeader("Działki").setTextAlign(ColumnTextAlign.START).setFlexGrow(1);


        Grid.Column<RejestrGruntow> columnSkany = rejestrGruntowGrid.addComponentColumn(item -> createSkanViewingButtonRejestrGruntow(rejestrGruntowGrid, item, uiService))
                .setHeader("Skany").setFlexGrow(0);

//        rejestrGruntowGrid.addComponentColumn(item -> createZmianyViewingButtonRejestrGruntow(rejestrGruntowGrid, item, uiServiceZmiany))
//                .setHeader("Zmiany");

        rejestrGruntowGrid.addComponentColumn(item -> moveToZmianyIfAdded(rejestrGruntowGrid, item))
                .setHeader("Zmiany");

//        rejestrGruntowGrid.setPageSize(50);
//        rejestrGruntowGrid.setPaginatorSize(10);

        configureFilterDataProvider(listDataProvider, collumnNrJednostkiRejestrowej, collumnWlascicielWladajacy, collumnWlascicielWladajacyNazwisko,collumnOjciec, collumnMatka, collumnGmina, collumnMiejsc, collumnTeryt, collumnDzialki, columnSkany);

    }

    private void configureFilterDataProvider(ListDataProvider<RejestrGruntow> dataProvider, Grid.Column<RejestrGruntow> collumnNrJednostkiRejestrowej,
                                             Grid.Column<RejestrGruntow> collumnWlascicielWladajacy, Grid.Column<RejestrGruntow> collumnWlascicielWladajacyNazwisko,
                                             Grid.Column<RejestrGruntow> collumnOjeciec, Grid.Column<RejestrGruntow> collumnMatka, Grid.Column<RejestrGruntow> collumnGmina, Grid.Column<RejestrGruntow> collumnMiejscowosc,
                                             Grid.Column<RejestrGruntow> collumnTeryt, Grid.Column<RejestrGruntow> collumnDzialki, Grid.Column<RejestrGruntow> columnSkany) {
        HeaderRow filterRow = rejestrGruntowGrid.appendHeaderRow();

        RejestrFilter rg = new RejestrFilter("test");
        dataProvider.setFilter(rejestrGruntow -> true);
        filterJednostkaRejestrowa.addValueChangeListener(event -> dataProvider.refreshAll());
        filterWlasciciel.addValueChangeListener(event -> dataProvider.refreshAll());
        filterWlascicielNazwisko.addValueChangeListener(event -> dataProvider.refreshAll());
        filterOjciec.addValueChangeListener(event -> dataProvider.refreshAll());
        filterMatka.addValueChangeListener(event -> dataProvider.refreshAll());
        filterGminaRejestrGruntow.addValueChangeListener(event -> dataProvider.refreshAll());
        filterMiejscowoscRejestrGruntow.addValueChangeListener(event -> dataProvider.refreshAll());
        filterTerytRejestrGruntow.addValueChangeListener(event -> dataProvider.refreshAll());
        filterDzialki.addValueChangeListener(event -> dataProvider.refreshAll());

        filterRow.getCell(collumnNrJednostkiRejestrowej).setComponent(filterJednostkaRejestrowa);
        filterRow.getCell(collumnWlascicielWladajacy).setComponent(filterWlasciciel);
        filterRow.getCell(collumnWlascicielWladajacyNazwisko).setComponent(filterWlascicielNazwisko);
        filterRow.getCell(collumnOjeciec).setComponent(filterOjciec);
        filterRow.getCell(collumnMatka).setComponent(filterMatka);
        filterRow.getCell(collumnGmina).setComponent(filterGminaRejestrGruntow);
        filterRow.getCell(collumnMiejscowosc).setComponent(filterMiejscowoscRejestrGruntow);
        filterRow.getCell(collumnTeryt).setComponent(filterTerytRejestrGruntow);
        filterRow.getCell(collumnDzialki).setComponent(filterDzialki);
        filterRow.getCell(columnSkany).setComponent(clearCriteriaButton);
    }

    private Anchor  createSkanViewingButtonRejestrGruntow(Grid<RejestrGruntow> grid, RejestrGruntow item, UIService uiService) {
//        RouterLink routerLink = new RouterLink(null, SkanViewing.class);

        Anchor a = new Anchor();
        a.setHref("/Przegladanie-Skanow");
        a.setTarget("_blank");

            Icon viewSkanIcon = new Icon(VaadinIcon.FILE_PICTURE);
//        viewSkanIcon.setColor("blue");


        a.getElement().appendChild(viewSkanIcon.getElement());
//            routerLink.getElement().appendChild(viewSkanIcon.getElement());

            viewSkanIcon.addClickListener(event -> {
//                grid.SELECT(item);
                grid.getSelectionModel().select(item);
                SingleSelect<Grid<RejestrGruntow>, RejestrGruntow> gridRejestrGruntowSingleSelect = grid.asSingleSelect();
                if (!gridRejestrGruntowSingleSelect.isEmpty()) {
                    RejestrGruntow rejestrG = gridRejestrGruntowSingleSelect.getValue();
                    List<Skany> skany = rejestrG.getSkany();
                    uiService.setSkanyList(skany);
                    uiService.setRejestrGruntow(item);
                }
//                filterJednostkaRejestrowa.setValue(item.getNrJednostkiRejestrowej());
            });

//        return routerLink;
        return a;
    }

    private RouterLink createZmianyViewingButtonRejestrGruntow(Grid<RejestrGruntow> grid, RejestrGruntow item, UIServiceZmiany uiService){
        RouterLink routerLink = new RouterLink("", SkanViewingZmiany.class);

        Anchor anchor = new Anchor( routerLink.getHref() , "Open a PDF document" ) ;

        if(!item.getZmianyList().isEmpty()) {
            Icon viewSkanIcon = new Icon(VaadinIcon.FILE_ADD);
            viewSkanIcon.setColor("red");
            routerLink.getElement().appendChild(viewSkanIcon.getElement());
            viewSkanIcon.addClickListener(event -> {

                grid.select(item);
                grid.getSelectionModel().select(item);
                SingleSelect<Grid<RejestrGruntow>, RejestrGruntow> gridRejestrGruntowSingleSelect = grid.asSingleSelect();
                if (!gridRejestrGruntowSingleSelect.isEmpty()) {
                    RejestrGruntow rejestrG = gridRejestrGruntowSingleSelect.getValue();
                    if (rejestrG.isZmiany()) {
                        List<Skany> skany = rejestrG.getZmianyList().get(0).getSkany();
                        uiService.setSkanyList(skany);
                    }
                }
            });
        }
        return routerLink;
    }

    private Div moveToZmianyIfAdded(Grid<RejestrGruntow> grid, RejestrGruntow item){
        Div icons = new Div();
        Icon zmianyIconAdd = new Icon(VaadinIcon.FILE_ADD);
        zmianyIconAdd.getStyle().set("cursor", "pointer");
        icons.add(zmianyIconAdd);


        AtomicInteger counter = new AtomicInteger();
        zmianyIconAdd.addClickListener(event -> {
            counter.getAndIncrement();
            if ((counter.get() % 2) == 0) {
                Zmiana zmiana = new Zmiana();
                zmiana.setRejestrGruntow(item);
                zmiana.setDoRejestrGruntow(item);
                zmiana.setNrZmiany("" + item.getZmianyList().size() + 1);
                zmiana.setParcelList(item.getDzialki());
                zmiana.setPersonList(item.getWlasciciel());
                zmianyService.saveZmiana(zmiana);
                item.addZmiana(zmiana);
                gService.save(item);

                grid.getSelectionModel().select(item);

                tabRejestry.setSelected(false);
                tabZmiany.setSelected(true);
                verticalLayout.remove(rejestrGruntowGrid);
                verticalLayout.add(zmianyGrid);

                filterJedRejestrowaZmiany.setValue(item.getNrJednostkiRejestrowej());
                updateListZmiany();
                zmianyGrid.getSelectionModel().select(zmiana);
            }
        });

        if(!item.getZmianyList().isEmpty()) {
            Icon viewZmiany = new Icon(VaadinIcon.FILE_SEARCH);
            viewZmiany.getStyle().set("cursor", "pointer");
            viewZmiany.setColor("red");
            viewZmiany.addClickListener(event -> {

                tabRejestry.setSelected(false);
                tabZmiany.setSelected(true);
                verticalLayout.remove(rejestrGruntowGrid);
                verticalLayout.add(zmianyGrid);

                grid.getSelectionModel().select(item);
                String nrJednostkiRejestrowej = item.getNrJednostkiRejestrowej();
                if(nrJednostkiRejestrowej != null ) {
                    filterJedRejestrowaZmiany.setValue(item.getNrJednostkiRejestrowej());
                    updateListZmiany();
                }else {
                    filterJedRejestrowaZmiany.setValue("");
                }

            });
            icons.add(viewZmiany);
        }

        return icons;
    }

    private Div createZmianyViewingButtonZmiany(Grid<Zmiana> grid, Zmiana item, UIServiceZmiany uiService){
        Div div = new Div();
        if(!item.getSkany().isEmpty()) {
//            RouterLink routerLink = new RouterLink("", SkanViewingZmiany.class);
            Anchor a = new Anchor();
            a.setHref("/Przeglad-skanow-zmian");
            a.setTarget("_blank");
            Icon viewSkanIcon = new Icon(VaadinIcon.FILE_PICTURE);
            viewSkanIcon.setColor("red");
            a.getElement().appendChild(viewSkanIcon.getElement());
            viewSkanIcon.addClickListener(event -> {

                grid.select(item);
                SingleSelect<Grid<Zmiana>, Zmiana> gridRejestrGruntowSingleSelect = grid.asSingleSelect();
                if (!gridRejestrGruntowSingleSelect.isEmpty()) {
                    Zmiana rejestrG = gridRejestrGruntowSingleSelect.getValue();
                    List<Skany> skany = rejestrG.getSkany();
                    uiService.setSkanyList(skany);
                }
            });
            div.add(a);
        }
//        }else {
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

                Path path;
                if(item.getRejestrGruntow().getSkany().size() > 0) {

                    path = Paths.get(item.getRejestrGruntow().getSkany().get(0).getSkan().getPath());
                }else {
                                    String test = "k:\\GEOMIAR\\Zmiany\\Zmiany\\";
                    path = Paths.get(test);
                }

                String directory = path.getParent().toString();
//                String test = "E:\\GEOMIAR\\APLIKACJA_EGBV\\opisane próbne\\TEST\\";

                upload.addSucceededListener(e -> {
                    Component component = createComponent(e.getMIMEType(),
                            e.getFileName(),
                            buffer.getInputStream(e.getFileName()), fileSet, directory);


                    System.out.println(directory);
//
                    List<Skany> skanyList = new LinkedList<>();
                    fileSet.stream().forEach(file -> {
                        Skany skan = new Skany();
                        skan.setZmiana(item);
                        skan.setSkan(file);
                        skanyRepo.save(skan);

                        skanyList.add(skan);
                    });
                    item.setSkany(skanyList);
                    zmianaRepo.save(item);

                });


//                Binder<Zmiana> binder = new Binder<>(Zmiana.class);
//                Edytor<Zmiana> editor = grid.getEditor();
//                editor.setBinder(binder);
//                editor.setBuffered(true);
//                TextField numerZmiany = new TextField();
//                binder.bind(numerZmiany, "nrZmiany");
//                Grid.Column<Zmiana> nr_zmiany = grid.getColumnByKey("Nr zmiany");
//                nr_zmiany.setEditorComponent(numerZmiany);
//                div.removeAll();


                div.add(upload);
            });
//        }
//        updateListZmiany();
        return div;
    }

    private void updateListRejestry() {
        rejestrGruntowGrid.setItems(gService.findBy(filterJednostkaRejestrowa.getValue(), filterWlasciciel.getValue().toUpperCase(), filterWlascicielNazwisko.getValue().toUpperCase(), filterOjciec.getValue().toUpperCase(), filterMatka.getValue().toUpperCase(),filterGminaRejestrGruntow.getValue().toUpperCase(), filterMiejscowoscRejestrGruntow.getValue().toUpperCase(), filterTerytRejestrGruntow.getValue(),filterDzialki.getValue()));
    }

    private void configureRejestryFiler() {
        filterJednostkaRejestrowa.setPlaceholder("Jednostka...");
        filterJednostkaRejestrowa.setClearButtonVisible(true);
//        filterJednostkaRejestrowa.setValue("150");
//        filterJednostkaRejestrowa.setValueChangeMode(ValueChangeMode.ON_CHANGE);
        filterJednostkaRejestrowa.setWidthFull();
        filterJednostkaRejestrowa.addValueChangeListener(e -> {
            updateListRejestry();
            if(!filterJednostkaRejestrowa.isEmpty()) {
                clearCriteriaButton.setEnabled(true);
            }
        });

        filterWlasciciel.setPlaceholder("Imię/nazwa...");
        filterWlasciciel.setClearButtonVisible(true);
        filterWlasciciel.setWidthFull();
        filterWlasciciel.addValueChangeListener(e -> {
            updateListRejestry();
            if(!filterWlasciciel.isEmpty()) {
                clearCriteriaButton.setEnabled(true);
            }
        });

        filterWlascicielNazwisko.setPlaceholder("Nazwisko...");
        filterWlascicielNazwisko.setClearButtonVisible(true);
        filterWlascicielNazwisko.setWidthFull();
        filterWlascicielNazwisko.addValueChangeListener(e -> {
            updateListRejestry();
            if(!filterWlascicielNazwisko.isEmpty()) {
                clearCriteriaButton.setEnabled(true);
            }
        });

        filterOjciec.setPlaceholder("Ojciec...");
        filterOjciec.setClearButtonVisible(true);
        filterOjciec.setWidthFull();
        filterOjciec.addValueChangeListener(e -> {
            updateListRejestry();
            if(!filterOjciec.isEmpty()) {
                clearCriteriaButton.setEnabled(true);
            }
        });

        filterMatka.setPlaceholder("Matka...");
        filterMatka.setClearButtonVisible(true);
        filterMatka.setWidthFull();
        filterMatka.addValueChangeListener(e -> {
            updateListRejestry();
            if(!filterMatka.isEmpty()) {
                clearCriteriaButton.setEnabled(true);
            }
        });

        filterGminaRejestrGruntow.setPlaceholder("Gmina...");
        filterGminaRejestrGruntow.setClearButtonVisible(true);
        filterGminaRejestrGruntow.setWidthFull();
        filterGminaRejestrGruntow.addValueChangeListener(e -> {
            updateListRejestry();
            if(!filterGminaRejestrGruntow.isEmpty()) {
                clearCriteriaButton.setEnabled(true);
            }
        });

        filterMiejscowoscRejestrGruntow.setPlaceholder("Miejscowość...");
        filterMiejscowoscRejestrGruntow.setClearButtonVisible(true);
        filterMiejscowoscRejestrGruntow.setWidthFull();
        filterMiejscowoscRejestrGruntow.addValueChangeListener(e -> {
            updateListRejestry();
            if(!filterMiejscowoscRejestrGruntow.isEmpty()) {
                clearCriteriaButton.setEnabled(true);
            }
        });

        filterTerytRejestrGruntow.setPlaceholder("TERYT...");
        filterTerytRejestrGruntow.setClearButtonVisible(true);
        filterTerytRejestrGruntow.setWidthFull();
        filterTerytRejestrGruntow.addValueChangeListener(e -> {
            updateListRejestry();
            if(!filterTerytRejestrGruntow.isEmpty()) {
                clearCriteriaButton.setEnabled(true);
            }
        });

        filterDzialki.setPlaceholder("Działka...");
        filterDzialki.setClearButtonVisible(true);
        filterDzialki.setWidthFull();
        filterDzialki.addValueChangeListener(e -> {
            updateListRejestry();
            if(!filterDzialki.isEmpty()) {
                clearCriteriaButton.setEnabled(true);
            }
        });

        clearCriteriaButton.setEnabled(false);

        Icon clerIcon = new Icon(VaadinIcon.CLOSE);
        clerIcon.setColor("rgb(255,255,255)");
        clearCriteriaButton.setIcon(clerIcon);
        clearCriteriaButton.getElement().setProperty("title", "Czyść kryteria wyszukiwania");
        clearCriteriaButton.addClickListener(event -> {
           if(!filterJednostkaRejestrowa.getValue().isEmpty() ||  !filterWlasciciel.getValue().isEmpty() ||  !filterWlascicielNazwisko.getValue().isEmpty() || !filterOjciec.getValue().isEmpty() ||
                   !filterMatka.getValue().isEmpty() || !filterGminaRejestrGruntow.getValue().isEmpty() || !filterMiejscowoscRejestrGruntow.getValue().isEmpty() ||
                   !filterTerytRejestrGruntow.getValue().isEmpty() || !filterDzialki.getValue().isEmpty() ){
               filterJednostkaRejestrowa.clear();
               filterWlasciciel.clear();
               filterWlascicielNazwisko.clear();
               filterOjciec.clear();
               filterMatka.clear();
               filterGminaRejestrGruntow.clear();
               filterMiejscowoscRejestrGruntow.clear();
               filterTerytRejestrGruntow.clear();
               filterDzialki.clear();
           }
            clearCriteriaButton.setEnabled(false);
        });

    }

    private Component createComponent(String mimeType, String fileName,
                                      InputStream stream, Set<File> fileSet, String zmianyPath) {
        String BASE_PATH=zmianyPath;
        File file;



       if (mimeType.startsWith("image")) {
            Image image = new Image();
            try {
                file = new File(BASE_PATH + "\\ZMIANA_NR_" + fileName);
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

//            try{
//                ImageInputStream imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(IOUtils.toByteArray(stream)));
//
//                File outputfile = new File("image.jpg");
//           ImageIO.write(imageInputStream, "jpg", outputfile);
//            }catch (IOException e) {
//                e.printStackTrace();
//            }

            return image;
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType, MessageDigestUtil.sha256(stream.toString()));
        content.setText(text);
        return content;

    }



    private void showOutput(String text, Component content,
                            HasComponents outputContainer) {
        HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        outputContainer.add(p);
        outputContainer.add(content);
    }
}
