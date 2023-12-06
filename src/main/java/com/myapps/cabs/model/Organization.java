package com.myapps.cabs.model;

public enum Organization {

    GMAIL("@gmail.com");

    private String validStr;

    Organization(String str) {
        this.validStr = str;
    }

    public String getValidStr() {
        return validStr;
    }
}
