package com.sensepost.yeti.results;

public class FootprintData {

    private int id = -1;
    private String name = "";
    private String created = "";

    public FootprintData() {
    }

    public FootprintData(String name) {
        this.name = name;
    }

    public FootprintData(int id, String name, String created) {
        this.id = id;
        this.name = name;
        this.created = created;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public String getCreated() {
        return this.created;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setCreated(String value) {
        this.created = value;
    }
}
