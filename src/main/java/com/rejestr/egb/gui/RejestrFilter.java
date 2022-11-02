package com.rejestr.egb.gui;

import com.rejestr.egb.entity.RejestrGruntow;

public class RejestrFilter {
    String name = "";

    public RejestrFilter(String name) {
        this.name = name;
    }


    public boolean test(RejestrGruntow rejestrGruntow) {
       return true;
    }
}
