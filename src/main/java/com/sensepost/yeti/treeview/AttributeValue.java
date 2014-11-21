package com.sensepost.yeti.treeview;

public class AttributeValue {

    private String key = "";
    private String value = "";
    private String type = "info";

    public AttributeValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public AttributeValue(String key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = value;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return String.format("%s - (%s)", this.value, this.key);
    }
}
