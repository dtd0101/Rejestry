package com.rejestr.egb.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Teryt extends AbstractEntity {

    private String gmina;
    private String miejscowosc;
    private String terytNumber;

    @OneToMany
    @JoinColumn(name = "skorowidz_Dzialek_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SkorowidzDzialek> skorowidzDzialekList;

    @OneToMany
    @JoinColumn(name = "skorowidz_Wlascicieli_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SkorowidzWlascicieli> skorowidzWlascicieliList;

    @OneToMany
    @JoinColumn(name = "parcel_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Parcel> parcelList;

    public Teryt() {
    }

    public Teryt(String gmina, String miejscowosc, String terytNumber) {
        this.gmina = gmina;
        this.miejscowosc = miejscowosc;
        this.terytNumber = terytNumber;
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getTerytId() {
        return terytNumber;
    }

    public void setTerytId(String terytNumber) {
        this.terytNumber = terytNumber;
    }

    public String getTerytNumber() {
        return terytNumber;
    }

    public void setTerytNumber(String terytNumber) {
        this.terytNumber = terytNumber;
    }

    public List<SkorowidzDzialek> getSkorowidzDzialekList() {
        return skorowidzDzialekList;
    }

    public void setSkorowidzDzialekList(List<SkorowidzDzialek> skorowidzDzialekList) {
        this.skorowidzDzialekList = skorowidzDzialekList;
    }

    public List<SkorowidzWlascicieli> getSkorowidzWlascicieliList() {
        return skorowidzWlascicieliList;
    }

    public void setSkorowidzWlascicieliList(List<SkorowidzWlascicieli> skorowidzWlascicieliList) {
        this.skorowidzWlascicieliList = skorowidzWlascicieliList;
    }

    public List<Parcel> getParcelList() {
        return parcelList;
    }

    public void setParcelList(List<Parcel> parcelList) {
        this.parcelList = parcelList;
    }

    public void addParcel(Parcel parcel){
        if(parcelList == null){
            parcelList = new LinkedList<>();
            parcelList.add(parcel);
        }else {
            parcelList.add(parcel);
        }
    }

    public void addSkorowidz(SkorowidzDzialek skorowidzDzialek){
        if(skorowidzDzialekList == null){
           skorowidzDzialekList = new LinkedList<>();
           skorowidzDzialekList.add(skorowidzDzialek);
        }else {
            skorowidzDzialekList.add(skorowidzDzialek);
        }
    }

    public void addSkorowidzWla(SkorowidzWlascicieli skorowidzWlascicieli){
        if(skorowidzWlascicieliList == null){
            skorowidzWlascicieliList = new LinkedList<>();
            skorowidzWlascicieliList.add(skorowidzWlascicieli);
        }else {
            skorowidzWlascicieliList.add(skorowidzWlascicieli);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teryt)) return false;
        if (!super.equals(o)) return false;
        Teryt teryt = (Teryt) o;
        return Objects.equals(gmina, teryt.gmina) &&
                Objects.equals(miejscowosc, teryt.miejscowosc) &&
                Objects.equals(terytNumber, teryt.terytNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gmina, miejscowosc, terytNumber);
    }
}
