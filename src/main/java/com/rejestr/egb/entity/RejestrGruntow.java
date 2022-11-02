package com.rejestr.egb.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class RejestrGruntow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nrJednostkiRejestrowej;
    private String nrGrupyRejestrowej;
    private String nrKsiegiWieczystej;
    private boolean zmiany = false;

    private String dataZalozenia;

    @OneToMany
    @JoinColumn(name = "rejestr_Gruntow_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Skany> skany = new LinkedList<>();

    @OneToMany(mappedBy = "rejestrGruntow", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Parcel> dzialki = new LinkedList<>();

    @ManyToMany(mappedBy = "rejestrGruntows",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Person> wlasciciel = new LinkedList<>();

    @ManyToMany(mappedBy = "rejestrGruntows")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Person> OsobaWladajaca = new LinkedList<>();

    @OneToMany
    @JoinColumn(name = "rejestr_Gruntow_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Zmiana> zmianyList = new LinkedList<>();

    @OneToMany
    @JoinColumn(name = "rejestr_Gruntow_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Zmiana> zmianyListDoRejestru = new LinkedList<>();

    public RejestrGruntow() {
    }

    public RejestrGruntow(String nrJednostkiRejestrowej, String nrGrupyRejestrowej, String nrKsiegiWieczystej, boolean zmiany, List<Skany> skany,
                          List<Parcel> dzialki, List<Person> wlasciciel, List<Person> osobaWladajaca) {
        this.nrJednostkiRejestrowej = nrJednostkiRejestrowej;
        this.nrGrupyRejestrowej = nrGrupyRejestrowej;
        this.nrKsiegiWieczystej = nrKsiegiWieczystej;
        this.zmiany = zmiany;
        this.skany = skany;
        this.dzialki = dzialki;
        this.wlasciciel = wlasciciel;
        OsobaWladajaca = osobaWladajaca;
    }

    public RejestrGruntow(String nrJednostkiRejestrowej, String nrGrupyRejestrowej, String nrKsiegiWieczystej, boolean zmiany, List<Skany> skany,
                          List<Parcel> dzialki, List<Person> wlasciciel) {
        this.nrJednostkiRejestrowej = nrJednostkiRejestrowej;
        this.nrGrupyRejestrowej = nrGrupyRejestrowej;
        this.nrKsiegiWieczystej = nrKsiegiWieczystej;
        this.zmiany = zmiany;
        this.skany = skany;
        this.dzialki = dzialki;
        this.wlasciciel = wlasciciel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNrJednostkiRejestrowej() {
        return nrJednostkiRejestrowej;
    }

    public void setNrJednostkiRejestrowej(String nrJednostkiRejestrowej) {
        this.nrJednostkiRejestrowej = nrJednostkiRejestrowej;
    }

    public String getNrGrupyRejestrowej() {
        return nrGrupyRejestrowej;
    }

    public void setNrGrupyRejestrowej(String nrGrupyRejestrowej) {
        this.nrGrupyRejestrowej = nrGrupyRejestrowej;
    }

    public String getNrKsiegiWieczystej() {
        return nrKsiegiWieczystej;
    }

    public void setNrKsiegiWieczystej(String nrKsiegiWieczystej) {
        this.nrKsiegiWieczystej = nrKsiegiWieczystej;
    }

    public List<Parcel> getDzialki() {
        return dzialki;
    }

    public void setDzialki(List<Parcel> dzialki) {
        this.dzialki = dzialki;
    }

    public List<Person> getWlasciciel() {
        return wlasciciel;
    }

    public void setWlasciciel(List<Person> wlasciciel) {
        this.wlasciciel = wlasciciel;
    }

    public List<Person> getOsobaWladajaca() {
        return OsobaWladajaca;
    }

    public void setOsobaWladajaca(List<Person> osobaWladajaca) {
        OsobaWladajaca = osobaWladajaca;
    }

    public boolean isZmiany() {
        return zmiany;
    }

    public void setZmiany(boolean zmiany) {
        this.zmiany = zmiany;
    }

    public List<Skany> getSkany() {
        return skany;
    }

    public void setSkany(List<Skany> skany) {
        this.skany = skany;
    }

    public String getDataZalozenia() {
        return dataZalozenia;
    }

    public void setDataZalozenia(String dataZalozenia) {
        this.dataZalozenia = dataZalozenia;
    }

    public List<Zmiana> getZmianyList() {
        return zmianyList;
    }

    public void setZmianyList(List<Zmiana> zmianyList) {
        this.zmianyList = zmianyList;
        this.zmiany = true;
    }

    public void addZmiana(Zmiana zmiana) {
        this.zmianyList.add(zmiana);
        this.zmiany = true;
    }

    public void removeZmiana(Zmiana zmiana) {
        this.zmianyList.remove(zmiana);
        if(zmianyList.isEmpty()){
            this.zmiany = false;

        }
    }

    public void addZmianaDo(Zmiana zmiana) {
        this.zmianyListDoRejestru.add(zmiana);
    }

    public void removeZmianaDo(Zmiana zmiana) {
        this.zmianyListDoRejestru.remove(zmiana);

    }

    public List<Zmiana> getZmianyListDoRejestru() {
        return zmianyListDoRejestru;
    }

    public void setZmianyListDoRejestru(List<Zmiana> zmianyListDoRejestru) {
        this.zmianyListDoRejestru = zmianyListDoRejestru;
    }

    public void addSkan(Skany skan){
        this.skany.add(skan);
    }

    public void removeSkan(Skany skan){
        this.skany.remove(skan);
    }

    public enum Zmiany {
        Tak, Nie;
    }

    @Override
    public String toString() {
        return  nrJednostkiRejestrowej + "   (" + dataZalozenia + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RejestrGruntow)) return false;
        RejestrGruntow that = (RejestrGruntow) o;
        return Objects.equals(nrJednostkiRejestrowej, that.nrJednostkiRejestrowej) &&
                Objects.equals(dataZalozenia, that.dataZalozenia) &&
                Objects.equals(skany, that.skany) &&
                Objects.equals(dzialki, that.dzialki) &&
                Objects.equals(wlasciciel, that.wlasciciel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nrJednostkiRejestrowej, dataZalozenia, skany, dzialki, wlasciciel);
    }
}
