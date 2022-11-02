package com.rejestr.egb.entity;

import javax.persistence.*;

@Entity
public class Adres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String miejscowosc;
    @Column
    private String numer;
    @Column
    private String gmina;
    @Column
    private String kodPocztowy;

    @ManyToOne
    private Person person;

    public Adres() {
    }

    public Adres(String miejscowosc, String numer, String gmina, String kodPocztowy) {
        this.miejscowosc = miejscowosc;
        this.numer = numer;
        this.gmina = gmina;
        this.kodPocztowy = kodPocztowy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
