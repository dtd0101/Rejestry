package com.rejestr.egb.entity;

import javax.persistence.*;
import java.io.File;
import java.util.Objects;

@Entity
public class Skany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private RejestrGruntow rejestrGruntow;

    @ManyToOne
    private SkorowidzDzialek skorowidzDzialek;

    @ManyToOne
    private SkorowidzWlascicieli skorowidzWlascicieli;

    @ManyToOne
    private Zmiana zmiana;

    @Column(name = "skan", columnDefinition = "blob")
    private File skan;

    public Skany() {
    }

    public Skany(File skan) {
        this.skan = skan;
    }

    public RejestrGruntow getRejestrGruntow() {
        return rejestrGruntow;
    }

    public void setRejestrGruntow(RejestrGruntow rejestrGruntow) {
        this.rejestrGruntow = rejestrGruntow;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public File getSkan() {
        return skan;
    }

    public void setSkan(File skan) {
        this.skan = skan;
    }

    public SkorowidzDzialek getSkorowidzDzialek() {
        return skorowidzDzialek;
    }

    public void setSkorowidzDzialek(SkorowidzDzialek skorowidzDzialek) {
        this.skorowidzDzialek = skorowidzDzialek;
    }

    public SkorowidzWlascicieli getSkorowidzWlascicieli() {
        return skorowidzWlascicieli;
    }

    public void setSkorowidzWlascicieli(SkorowidzWlascicieli skorowidzWlascicieli) {
        this.skorowidzWlascicieli = skorowidzWlascicieli;
    }

    public Zmiana getZmiana() {
        return zmiana;
    }

    public void setZmiana(Zmiana zmiana) {
        this.zmiana = zmiana;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skany)) return false;
        Skany skany = (Skany) o;
        return Objects.equals(skan, skany.skan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skan);
    }

    @Override
    public String toString() {
        return skan.getAbsolutePath();
    }
}