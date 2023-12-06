package com.myapps.cabs.model;

import java.sql.Time;

public enum DropOff {

    MDT1(),
    MDT2(),
    MDT3(),
    MDT4(),
    MD5(),
    ADT1(),
    ADT2(),
    ADT3(),
    ADT4(),
    AD5(),
    ED1(),
    ED2(),
    ED3(),
    ED4(),
    ED5();

    private Time t;

    public void setT(Time t) {
        this.t = t;
    }

    public Time getT() {
        return t;
    }
}
