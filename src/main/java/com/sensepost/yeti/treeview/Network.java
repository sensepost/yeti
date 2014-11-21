package com.sensepost.yeti.treeview;

/**
 *
 * @author willemmouton
 */
public class Network {

    String value = "";

    public Network(String value) {
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
        return this.value;
    }
}
