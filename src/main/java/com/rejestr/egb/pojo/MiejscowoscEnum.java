package com.rejestr.egb.pojo;

public enum MiejscowoscEnum {
    Bircza(1)
    ;

    int terytId;
    MiejscowoscEnum(int terytId){
        this.terytId = terytId;
    }
    int showTerytId(){
        return terytId;
    }
}
