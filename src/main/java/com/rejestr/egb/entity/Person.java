package com.rejestr.egb.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "imie")
    private String name;

    @Column(name = "nazwisko")
    private String lastName;

    @Column(name = "imie_ojca")
    private String fathersName;

    @Column(name = "imie_matki")
    private String mathersName;

    @ManyToMany
    private List<RejestrGruntow> rejestrGruntows = new LinkedList<>();

    @OneToMany(mappedBy = "person")
    private List<Adres> adres = new LinkedList<>();

    @ManyToOne
    private Zmiana zmiana;

    public Person() {
    }

    public Person(String name, String lastName, String fathersName, String mathersName, List<Adres> adres) {
        this.name = name;
        this.lastName = lastName;
        this.fathersName = fathersName;
        this.mathersName = mathersName;
        this.adres = adres;
    }

    public Person(String name, String lastName, List<Adres> adres) {
        this.name = name;
        this.lastName = lastName;
        this.adres = adres;
    }

    public Person(String name, String lastName, String fathersName, String mathersName) {
        this.name = name;
        this.lastName = lastName;
        this.fathersName = fathersName;
        this.mathersName = mathersName;
    }

    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMathersName() {
        return mathersName;
    }

    public void setMathersName(String mathersName) {
        this.mathersName = mathersName;
    }

    public List<Adres> getAdres() {
        return adres;
    }

    public void setAdres(List<Adres> adres) {
        this.adres = adres;
    }

    public List<RejestrGruntow> getRejestrGruntows() {
        return rejestrGruntows;
    }

    public void setRejestrGruntows(List<RejestrGruntow> rejestrGruntows) {
        this.rejestrGruntows = rejestrGruntows;
    }

    public void addRejestr(RejestrGruntow rejestrGruntow){
        rejestrGruntows.add(rejestrGruntow);
    }

    public Zmiana getZmiana() {
        return zmiana;
    }

    public void setZmiana(Zmiana zmiana) {
        this.zmiana = zmiana;
    }

    @Override
    public String toString() {
        return name + " " + lastName + "\n o. " + fathersName + " m. " + mathersName ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(fathersName, person.fathersName) &&
                Objects.equals(mathersName, person.mathersName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, fathersName, mathersName);
    }
}
