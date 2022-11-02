package com.rejestr.egb.entity;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Zmiana extends AbstractEntity{

    private String nrZmiany;

    private String doRejestru = "";

    private String opisZmiany = "";

    private String uwagi = "";

    @OneToMany
    @JoinColumn(name = "zmiana_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Skany> skany = new LinkedList<>();

    @ManyToOne
    private RejestrGruntow rejestrGruntow;

    @ManyToOne
    private RejestrGruntow doRejestrGruntow;

    @OneToMany
    @JoinColumn(name = "zmiana_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Person> personList = new LinkedList<>();

    @OneToMany
    @JoinColumn(name = "zmiana_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Parcel> parcelList = new LinkedList<>();

    @Transient
    private CheckboxGroup<Person> personCheckboxGroup = new CheckboxGroup<>();

    @Transient
    private CheckboxGroup<Parcel> parcelCheckboxGroup = new CheckboxGroup<>();


    public Zmiana() {
    }

    public String getNrZmiany() {
        return nrZmiany;
    }

    public void setNrZmiany(String nrZmiany) {
        this.nrZmiany = nrZmiany;
    }

    public List<Skany> getSkany() {
        return skany;
    }

    public void setSkany(List<Skany> skany) {
        this.skany = skany;
    }

    public void addSkan(Skany skan){
        this.skany.add(skan);
    }

    public void removeSkan(Skany skan){
        this.skany.remove(skan);
    }

    public String getOpisZmiany() {
        return opisZmiany;
    }

    public void setOpisZmiany(String opisZmiany) {
        this.opisZmiany = opisZmiany;
    }

    public RejestrGruntow getRejestrGruntow() {
        return rejestrGruntow;
    }

    public void setRejestrGruntow(RejestrGruntow rejestrGruntow) {
        this.rejestrGruntow = rejestrGruntow;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void addPerson(Person person){
        this.personList.add(person);
    }

    public void removePerson(Person person){
        this.personList.remove(person);
    }

    public List<Parcel> getParcelList() {
        return parcelList;
    }

    public void setParcelList(List<Parcel> parcelList) {
        this.parcelList = parcelList;
    }

    public void addParcel(Parcel parcel){
        this.parcelList.add(parcel);
    }

    public void removeParcel(Parcel parcel){
        this.parcelList.remove(parcel);
    }

    public RejestrGruntow getDoRejestrGruntow() {
        return doRejestrGruntow;
    }

    public void setDoRejestrGruntow(RejestrGruntow doRejestrGruntow) {
        this.doRejestrGruntow = doRejestrGruntow;
    }

    public CheckboxGroup<Person> getPersonCheckboxGroup() {
        return personCheckboxGroup;
    }

    public void setPersonCheckboxGroup(CheckboxGroup<Person> personCheckboxGroup) {
        this.personCheckboxGroup = personCheckboxGroup;
    }

    public CheckboxGroup<Parcel> getParcelCheckboxGroup() {
        return parcelCheckboxGroup;
    }

    public void setParcelCheckboxGroup(CheckboxGroup<Parcel> parcelCheckboxGroup) {
        this.parcelCheckboxGroup = parcelCheckboxGroup;
    }

    public String getDoRejestru() {
        return doRejestru;
    }

    public void setDoRejestru(String doRejestru) {
        this.doRejestru = doRejestru;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zmiana)) return false;
        if (!super.equals(o)) return false;
        Zmiana zmiana = (Zmiana) o;
        return Objects.equals(nrZmiany, zmiana.nrZmiany) &&
                Objects.equals(doRejestru, zmiana.doRejestru) &&
                Objects.equals(opisZmiany, zmiana.opisZmiany) &&
                Objects.equals(uwagi, zmiana.uwagi) &&
                Objects.equals(skany, zmiana.skany) &&
                Objects.equals(rejestrGruntow, zmiana.rejestrGruntow) &&
                Objects.equals(doRejestrGruntow, zmiana.doRejestrGruntow) &&
                Objects.equals(personList, zmiana.personList) &&
                Objects.equals(parcelList, zmiana.parcelList) &&
                Objects.equals(personCheckboxGroup, zmiana.personCheckboxGroup) &&
                Objects.equals(parcelCheckboxGroup, zmiana.parcelCheckboxGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nrZmiany, doRejestru, opisZmiany, uwagi, skany, rejestrGruntow, doRejestrGruntow, personList, parcelList, personCheckboxGroup, parcelCheckboxGroup);
    }

    @Override
    public String toString() {
        return "Zmiana{" +
                "nrZmiany='" + nrZmiany + '\'' +
                ", doRejestru='" + doRejestru + '\'' +
                ", opisZmiany='" + opisZmiany + '\'' +
                ", uwagi='" + uwagi + '\'' +
                '}';
    }
}
