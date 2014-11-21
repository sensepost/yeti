package com.sensepost.yeti.results;

public class Port {

    private int portNumber = -1;
    private String state = "unknown";
    private String name = "";
    private String version = "";
    private String extraInfo = "";

    public Port(int portNumber) {
        this.portNumber = portNumber;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public String toString() {
        String buff = String.format("%d : %s %s %s %s", portNumber, state, name, version, extraInfo).trim();
        return buff;
    }
}
