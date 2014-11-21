package com.sensepost.yeti.treeview;

public class Ip {

    private String value = "";

    public Ip(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
