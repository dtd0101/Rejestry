//package com.rejestr.egb.service;
//
//import com.rejestr.egb.entity.*;
//import com.rejestr.egb.repository.*;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.filefilter.DirectoryFileFilter;
//import org.apache.commons.io.filefilter.RegexFileFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.util.*;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//
//@Service
//public class LoadData_kopia {
//
//    private static final Logger LOGGER = Logger.getLogger(RejestrGService.class.getName());
//    @Autowired
//    private RejestrGruntowRepo rejestrGruntowRepo;
//    @Autowired
//    private AdresRepo adresRepo;
//    @Autowired
//    private ParcelRepo parcelRepo;
//    @Autowired
//    private SkanyRepo skanyRepo;
//    @Autowired
//    private PersonRepo personRepo;
//    @Autowired
//    private TerytRepo terytRepo;
//
//    @Autowired
//    SkorowidzDzialekRepo skorowidzDzialekRepo;
//
//    @Autowired
//    SkorowidzWlaRepo skorowidzWlaRepo;
//
//    private String path;
//    private List<Tmp> tmpList = new ArrayList<>();
//    private List<File> fileList = new ArrayList<>();
//    private List<String> errorList = new ArrayList<>();
//    private Set<Teryt> allTerytSet = new HashSet<>();
//    private Set<Parcel> allParcelSet = new HashSet<>();
//
//
//    Set<File> allFilesList = new HashSet<>();
//
//
//    public void readFile() throws IOException {
//        if(path == null || path.isEmpty()){
//            path = "e:\\GEOMIAR\\APLIKACJA_EGBV\\opisane próbne\\STARA_BIRCZA.txt";
//        }
//        if(path != null && !path.isEmpty()) {
//
//            allFilesList = new HashSet<>();
//
//            fileList = new ArrayList<>();
//
//            Collection files = FileUtils.listFiles(
//                    new File(path),
//                    new RegexFileFilter("^(.*?)"),
//                    DirectoryFileFilter.DIRECTORY
//            );
//
////            files.forEach(System.out::println);
//
//            files.stream().forEach(o -> {
//                if (o.toString().endsWith(".txt") && !o.toString().contains("Nowy dokument tekstowy.txt") && !o.toString().contains("Zakończenie.txt")) {
//                    File file = new File(o.toString());
//                    allFilesList.add(file);
//                }
//            });
//
//            allFilesList.forEach(System.out::println);
//
////            IMPORT_REJESTRÓW
//
//            for (File f : allFilesList) {
//                try {
//                    if (f.getName().endsWith(".txt") && !f.getName().contains("SKOROWIDZ") && !f.getName().contains("Nowy dokument tekstowy.txt") && !f.getName().contains("Zakończenie.txt")) {
//                        System.out.println("CZYTAM: " + f.getName());
//
//                        tmpList = new ArrayList<>();
//                        FileInputStream fis = new FileInputStream(f);
//                        InputStreamReader isr = new InputStreamReader(fis, "Windows-1250");
////                    InputStreamReader isr = new InputStreamReader(fis);
//                        BufferedReader br = new BufferedReader(isr);
//                        br.readLine(); //skip 1 line
//                        String line = null;
//                        int index = 0;
//
//
//                        while ((line = br.readLine()) != null) {
//                            String[] split = line.split("\t");
////                        System.out.println(line);
////                        Arrays.stream(split).forEach(System.out::println);
//                            if (split.length == 14) {
//                                if (tmpList.isEmpty()) {
//                                    Tmp tmp = new Tmp(split[0], split[1], split[2], split[3], split[4], split[5], split[6], split[7], split[8], split[9], split[10], split[11], split[12], split[13]);
//                                    tmpList.add(tmp);
//                                    tmp.nrDzialek.add(tmp.nrDzialki);
//                                    listFilesForFolder(new File(f.getParent() + "\\"), tmp.fileList);
//                                } else if (!tmpList.isEmpty()) {
//                                    Tmp lastTmp = tmpList.get(index);
//                                    if (lastTmp.name.equals(split[0]) && lastTmp.lastName.equals(split[1])
//                                            && lastTmp.fathersName.equals(split[2]) && lastTmp.mathersName.equals(split[3])
//                                            && lastTmp.nameWla.equals(split[4]) && lastTmp.lastNameWla.equals(split[5])
//                                            && lastTmp.fathersNameWla.equals(split[6]) && lastTmp.mathersNameWla.equals(split[7])
//                                            && !lastTmp.nrDzialki.equals(split[8]) && lastTmp.nrJednostkiRej.equals(split[9])) {
//                                        lastTmp.nrDzialek.add(split[8]);
//                                    } else {
//                                        Tmp tmp = new Tmp(split[0], split[1], split[2], split[3], split[4], split[5], split[6], split[7], split[8], split[9], split[10], split[11], split[12], split[13]);
//
//                                        listFilesForFolder(new File(f.getParent() + "\\"), tmp.fileList);
//
//                                        tmpList.add(tmp);
//                                        tmp.nrDzialek.add(tmp.nrDzialki);
//                                        index++;
//                                    }
//                                }
//                            }
//
//                        }
//                        br.close();
//
//                    }
//                    populateData();
//
//                } catch (Exception ex) {
//                    String message = ex.getMessage();
//                    StackTraceElement[] stackTrace = ex.getStackTrace();
//                    errorList.add(f.getName());
//                    errorList.add(message);
//                }
//            }
//
//            System.out.println("!!! IMPORT REJESTRÓW ZAKONCZONY !!!!");
//
//
//            for (File f : allFilesList) {
//                if (f.getName().endsWith(".txt") && f.getName().contains("SKOROWIDZ_DZIALEK.txt")) {
//                    System.out.println("CZYTAM: " + f.getName());
//
//                    FileInputStream fis = new FileInputStream(f);
////                InputStreamReader isr = new InputStreamReader(fis, "Windows-1250");
//                    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
//
//                    BufferedReader br = new BufferedReader(isr);
//                    br.readLine(); //skip 1 line
//                    String line = null;
//                    Teryt teryt = null;
//                    String[] split = null;
//                    int index = 0;
//                    while ((line = br.readLine()) != null) {
//                        split = line.split("\t");
////                    System.out.println(line);
////                    Arrays.stream(split).forEach(System.out::println);
//                        if (split.length >= 4) {
//                            teryt = new Teryt(split[1], split[2], split[0]);
//                        }
//                    }
//                    br.close();
//                    if (teryt != null && split != null) {
//                        SkorowidzDzialek skorowidzDzialek = new SkorowidzDzialek();
//                        skorowidzDzialekRepo.save(skorowidzDzialek);
//
//                        teryt.addSkorowidz(skorowidzDzialek);
//                        terytRepo.save(teryt);
//
//                        List<Skany> skanyList = new ArrayList<>();
//                        Set<Skany> skanySet = new HashSet<>();
//
//
////        System.out.println(newPath);
//                        List<File> fileListSkorowidze = new ArrayList<>();
//                        listFilesForFolder(new File(f.getParent() + "\\"), fileListSkorowidze);
//
//                        for (File k : fileListSkorowidze) {
//                            Skany skan = new Skany();
//                            skan.setSkan(k);
//                            skan.setSkorowidzDzialek(skorowidzDzialek);
//                            skanySet.add(skan);
//                            skanyRepo.save(skan);
//                        }
//
//                        skanyList.addAll(skanySet);
////                        skanyRepo.saveAll(skanyList);
//
//                        String rok = "";
//
//                        for (String s: split) {
//                            if(s.startsWith("19")){
//                                rok = s;
//                            }
//                        }
//
//                        skorowidzDzialek.setSkany(skanyList);
//                        skorowidzDzialek.setTeryt(teryt);
//                        skorowidzDzialek.setRok(rok);
//                        skorowidzDzialekRepo.save(skorowidzDzialek);
//
//                    }
//
//                }
//
//            }
//            System.out.println("!!! IMPORT SKOROWIDZY DZIALEK ZAKONCZONY !!!!");
//
//            for (File f : allFilesList) {
//                if (f.getName().endsWith(".txt") && f.getName().contains("SKOROWIDZ_WLASCICIELI.txt")) {
//                    System.out.println("CZYTAM: " + f.getName());
//
//                    FileInputStream fis = new FileInputStream(f);
////                InputStreamReader isr = new InputStreamReader(fis, "Windows-1250");
//                    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
//
//                    BufferedReader br = new BufferedReader(isr);
//                    br.readLine(); //skip 1 line
//                    String line = null;
//                    Teryt teryt = null;
//                    String[] split = null;
//                    int index = 0;
//                    while ((line = br.readLine()) != null) {
//                        split = line.split("\t");
////                    System.out.println(line);
////                    Arrays.stream(split).forEach(System.out::println);
//                        if (split.length >= 4) {
//                            teryt = new Teryt(split[1], split[2], split[0]);
//                        }
//                    }
//                    br.close();
//                    if (teryt != null && split != null) {
//                        SkorowidzWlascicieli skorowidzWlascicieli = new SkorowidzWlascicieli();
//                        skorowidzWlaRepo.save(skorowidzWlascicieli);
//
//                        teryt.addSkorowidzWla(skorowidzWlascicieli);
//                        terytRepo.save(teryt);
//
//                        List<Skany> skanyList = new ArrayList<>();
//                        Set<Skany> skanySet = new HashSet<>();
//
//
////        System.out.println(newPath);
//                        List<File> fileListSkorowidze = new ArrayList<>();
//                        listFilesForFolder(new File(f.getParent() + "\\"), fileListSkorowidze);
//
//                        for (File k : fileListSkorowidze) {
//                            Skany skan = new Skany();
//                            skan.setSkan(k);
//                            skan.setSkorowidzWlascicieli(skorowidzWlascicieli);
//                            skanySet.add(skan);
//                        }
//
//                        skanyList.addAll(skanySet);
//                        skanyRepo.saveAll(skanyList);
//
//                        String rok = "";
//
//                        for (String s: split) {
//                            if(s.startsWith("19")){
//                                rok = s;
//                            }
//                        }
//
//                        skorowidzWlascicieli.setSkany(skanyList);
//                        skorowidzWlascicieli.setTeryt(teryt);
//                        skorowidzWlascicieli.setRok(rok);
//                        skorowidzWlaRepo.save(skorowidzWlascicieli);
//
//                    }
//
//                }
//
//            }
//
//            System.out.println("!!! IMPORT SKOROWIDZY WLASCICIELI ZAKONCZONY !!!!");
//
//            System.out.println("!!! IMPORT ZAKONCZONY !!!!");
//            try {
//                PrintWriter writer = new PrintWriter(path + "log.txt", "UTF-8");
//                errorList.stream().forEach(s -> writer.println(s));
//                writer.close();
//            } catch (Exception e) {
//
//            }
//        }
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void listFilesForFolder(final File folder) {
//
//        for (final File fileEntry : folder.listFiles()) {
//            if (fileEntry.isDirectory()) {
//                listFilesForFolder(fileEntry);
//              } else {
////                System.out.println(fileEntry.getName());
//                if(fileEntry.getName().endsWith(".jpg")){
//                    fileList.add(fileEntry);
////                    System.out.println(fileEntry.getAbsolutePath());
//                }
//            }
//        }
//    }
//
//    public void listFilesForFolder(final File folder, Set<File> fileList) {
//
//        for (final File fileEntry : folder.listFiles()) {
//            if (fileEntry.isDirectory()) {
//                listFilesForFolder(fileEntry);
//            } else {
////                System.out.println(fileEntry.getName());
//                if(fileEntry.getName().endsWith(".jpg")){
//                    fileList.add(fileEntry);
////                    System.out.println(fileEntry.getAbsolutePath());
//                }
//            }
//        }
//    }
//
//    public void listFilesForFolder(final File folder, List<File> fileList) {
//
//        for (final File fileEntry : folder.listFiles()) {
//            if (fileEntry.isDirectory()) {
//                listFilesForFolder(fileEntry);
//            } else {
////                System.out.println(fileEntry.getName());
//                if(fileEntry.getName().endsWith(".jpg")){
//                    fileList.add(fileEntry);
////                    System.out.println(fileEntry.getAbsolutePath());
//                }
//            }
//        }
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public void populateData(){
//        List<String> nrJednostek = tmpList.stream().map(tmp -> tmp.nrJednostkiRej).distinct().collect(Collectors.toList());
//
//
//        for (String nrJ:nrJednostek) {
//            List<Tmp> tmps = new ArrayList<>();
//
//            for (Tmp t:tmpList) {
//                if(t.nrJednostkiRej.equals(nrJ)){
//                    tmps.add(t);
//                }
//            }
//
//            Set<Person> personList = new HashSet<>();
//            Set<Parcel> parcels = new HashSet<>();
//
//            List<Skany> skanyList = new ArrayList<>();
//            Set<Skany> skanySet = new HashSet<>();
//            Teryt teryt = new Teryt();
//
//            for (Tmp t : tmps) {
//                teryt.setGmina(t.gmina);
//                teryt.setTerytNumber(t.teryt);
//                teryt.setMiejscowosc(t.miejscowosc);
//            }
//
//            if(!allTerytSet.isEmpty() && !allTerytSet.contains(teryt)){
//                terytRepo.save(teryt);
//            }
//
//            if(allTerytSet.contains(teryt)){
//                for (Teryt t: allTerytSet) {
//                    if(teryt.equals(t)){
//                        teryt = t;
//                    }
//                }
//            }else {
//                allTerytSet.add(teryt);
//            }
//
//
//
//
//            for (Tmp t : tmps) {
//                String s = t.nrJednostkiRej;
//
//                for (File f : t.fileList)
//                {
//                    String fName = f.getName();
//                    String replace = fName.replace(".jpg", "");
//                    String[] splitFileName = replace.split("_");
//
//                    if(splitFileName.length == 3){
//                        if (splitFileName[2].equals(s)){
//                            skanySet.add(new Skany(f));
//                        }
//
//                    }else if(splitFileName.length == 4){
//                        if (splitFileName[2].equals(s)){
//                            skanySet.add(new Skany(f));
//                        }
//                    }
//
//                }
//                skanyList.addAll(skanySet);
//                skanyRepo.saveAll(skanyList);
//            }
//
//            String nrJedn = "";
//            RejestrGruntow rejestrGruntow  = new RejestrGruntow();
//            for (Tmp t : tmps) {
//                if(!t.name.isEmpty()) {
//                    Person person = new Person(t.name, t.lastName, t.fathersName, t.mathersName);
//
//                    personList.add(person);
//                }
//                if(!t.nameWla.isEmpty()) {
//                    Person personWla = new Person(t.nameWla, t.lastNameWla, t.fathersNameWla, t.mathersNameWla);
//                    personList.add(personWla);
//                }
//                for (String d:t.nrDzialek) {
//                    Parcel parcel = new Parcel(d);
//                    parcel.setTeryt(teryt);
//                    teryt.addParcel(parcel);
//                    parcels.add(parcel);
//                }
//
//                rejestrGruntow.setNrJednostkiRejestrowej(t.nrJednostkiRej);
//                rejestrGruntow.setDataZalozenia(t.data);
//
//            }
//            rejestrGruntow.setSkany(skanyList);
//            rejestrGruntow.setDzialki(parcels.stream().collect(Collectors.toList()));
//            rejestrGruntow.setWlasciciel(personList.stream().collect(Collectors.toList()));
//            rejestrGruntow.setNrGrupyRejestrowej("");
//            rejestrGruntow.setNrKsiegiWieczystej("");
//
//            rejestrGruntowRepo.save(rejestrGruntow);
//
//            personList.stream().forEach(person -> person.addRejestr(rejestrGruntow));
//            parcels.stream().forEach(parcel -> parcel.setRejestrGruntow(rejestrGruntow));
//
//            personRepo.saveAll(personList);
//
//            parcelRepo.saveAll(parcels);
//        }
//
//    }
//
//    class Tmp {
//        String name;
//        String lastName;
//        String fathersName;
//        String mathersName;
//        String nameWla;
//        String lastNameWla;
//        String fathersNameWla;
//        String mathersNameWla;
//        String nrDzialki;
//        Set<String> nrDzialek = new HashSet<>();
//        String nrJednostkiRej;
//        String teryt;
//        String gmina;
//        String miejscowosc;
//        String data;
//        Set<File> fileList = new HashSet<>();
//
//
//        public Tmp(String name, String lastName, String fathersName, String mathersName, String nameWla, String lastNameWla, String fathersNameWla, String mathersNameWla, String nrDzialki, String nrJednostkiRej, String teryt, String gmina, String miejscowosc, String data) {
//            this.name = name;
//            this.lastName = lastName;
//            this.fathersName = fathersName;
//            this.mathersName = mathersName;
//            this.nameWla = nameWla;
//            this.lastNameWla = lastNameWla;
//            this.fathersNameWla = fathersNameWla;
//            this.mathersNameWla = mathersNameWla;
//            this.nrDzialki = nrDzialki;
//            this.nrJednostkiRej = nrJednostkiRej;
//            this.teryt = teryt;
//            this.gmina = gmina;
//            this.miejscowosc = miejscowosc;
//            this.data = data;
//        }
//
//        @Override
//        public String toString() {
//            return "Tmp{" +
//                    "name='" + name + '\'' +
//                    ", lastName='" + lastName + '\'' +
//                    ", fathersName='" + fathersName + '\'' +
//                    ", mathersName='" + mathersName + '\'' +
//                    ", nameWla='" + nameWla + '\'' +
//                    ", lastNameWla='" + lastNameWla + '\'' +
//                    ", fathersNameWla='" + fathersNameWla + '\'' +
//                    ", mathersNameWla='" + mathersNameWla + '\'' +
//                    ", nrDzialki='" + nrDzialki + '\'' +
//                    ", nrDzialek=" + nrDzialek +
//                    ", nrJednostkiRej='" + nrJednostkiRej + '\'' +
//                    ", teryt='" + teryt + '\'' +
//                    ", gmina='" + gmina + '\'' +
//                    ", miejscowosc='" + miejscowosc + '\'' +
//                    ", data='" + data + '\'' +
//                    '}';
//        }
//    }
//}
