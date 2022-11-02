package com.rejestr.egb.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rejestr_gruntow_id")
    private RejestrGruntow rejestrGruntow;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Teryt teryt;

    private String parcelNumber;


    @ManyToOne
    private Zmiana zmiana;

    private String gmina;

    public Parcel() {}

    public Parcel(String parcelNumber) {
        this.parcelNumber = parcelNumber;
    }

    public Parcel(RejestrGruntow rejestrGruntow, String parcelNumber) {
        this.rejestrGruntow = rejestrGruntow;
        this.parcelNumber = parcelNumber;
    }

    public Parcel(RejestrGruntow rejestrGruntow, String parcelNumber, String registrationUnit) {
        this.rejestrGruntow = rejestrGruntow;
        this.parcelNumber = parcelNumber;
//        this.registrationUnit = registrationUnit;
    }

    public Parcel(String parcelNumber, String registrationUnit) {
        this.parcelNumber = parcelNumber;
//        this.registrationUnit = registrationUnit;
//        generateValueMiejscowosc(registrationUnit);
    }

    public RejestrGruntow getRejestrGruntow() {
        return rejestrGruntow;
    }

    public void setRejestrGruntow(RejestrGruntow rejestrGruntow) {
        this.rejestrGruntow = rejestrGruntow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParcelNumber() {
        return parcelNumber;
    }

    public void setParcelNumber(String parcelNumber) {
        this.parcelNumber = parcelNumber;
    }

//    public String getRegistrationUnit() {
//        return registrationUnit;
//    }

//    public void setRegistrationUnit(String registrationUnit) {
//        this.registrationUnit = registrationUnit;
//    }

//    public String getMiejscowosc() {
//        return miejscowosc;
//    }

//    public void setMiejscowosc(String miejscowosc) {
//        this.miejscowosc = miejscowosc;
//    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public Teryt getTeryt() {
        return teryt;
    }

    public void setTeryt(Teryt teryt) {
        this.teryt = teryt;
    }

    public Zmiana getZmiana() {
        return zmiana;
    }

    public void setZmiana(Zmiana zmiana) {
        this.zmiana = zmiana;
    }

//    @Override
//    public String toString() {
//        return parcelNumber + "\n";
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Parcel)) return false;
//        Parcel parcel = (Parcel) o;
//        return Objects.equals(parcelNumber, parcel.parcelNumber) &&
//                Objects.equals(registrationUnit, parcel.registrationUnit);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(parcelNumber, registrationUnit);
//    }


    @Override
    public String toString() {
        return parcelNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parcel)) return false;
        Parcel parcel = (Parcel) o;
        return Objects.equals(teryt, parcel.teryt) &&
                Objects.equals(parcelNumber, parcel.parcelNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teryt, parcelNumber);
    }
}