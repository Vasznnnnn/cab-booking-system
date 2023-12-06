package com.myapps.cabs.model;


import java.sql.Time;

public enum Pickup {

    MPT1(),
    MPT2(),
    MPT3(),
    MPT4(),
    MP5(),
    APT1(),
    APT2(),
    APT3(),
    APT4(),
    AP5(),
    EP1(),
    EP2(),
    EP3(),
    EP4(),
    EP5();

    private Time t;

    public void setT(Time t) {
        this.t = t;
    }

    public Time getT() {
        return t;
    }
}
