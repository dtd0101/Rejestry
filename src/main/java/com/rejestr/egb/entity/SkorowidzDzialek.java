package com.rejestr.egb.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "skorowidz")
public class SkorowidzDzialek extends AbstractEntity{

    private String rok;

    @OneToMany
    @JoinColumn(name = "skorowidz_dzialek_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Skany> skany = new LinkedList<>();

    @ManyToOne
    private Teryt teryt;

    public SkorowidzDzialek() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkorowidzDzialek)) return false;
        if (!super.equals(o)) return false;
        SkorowidzDzialek that = (SkorowidzDzialek) o;
        return Objects.equals(rok, that.rok) &&
                Objects.equals(skany, that.skany) &&
                Objects.equals(teryt, that.teryt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rok, skany, teryt);
    }
}
