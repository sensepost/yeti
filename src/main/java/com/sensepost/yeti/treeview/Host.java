package com.sensepost.yeti.treeview;

/**
 *
 * @author willemmouton
 */
public class Host {

    private String value = "";

    public Host(String value) {
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
