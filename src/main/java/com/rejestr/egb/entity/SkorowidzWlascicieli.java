package com.rejestr.egb.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "skorowidzWla")
public class SkorowidzWlascicieli extends AbstractEntity{

    private String rok;

    @OneToMany
    @JoinColumn(name = "SKOROWIDZ_WLA_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Skany> skany = new LinkedList<>();

    @ManyToOne
    private Teryt teryt;

    public SkorowidzWlascicieli() {
    }

    public List<Skany> getSkany() {
        return skany;
    }

    public void setSkany(List<Skany> skany) {
        this.skany = skany;
    }

    public Teryt getTeryt() {
        return teryt;
    }

    public void setTeryt(Teryt teryt) {
        this.teryt = teryt;
    }

    public void addSkan(Skany skan){
        this.skany.add(skan);
    }

    public void removeSkan(Skany skan){
        this.skany.remove(skan);
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }
}
