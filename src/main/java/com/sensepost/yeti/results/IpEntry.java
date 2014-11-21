package com.sensepost.yeti.results;

/**
 *
 * @author willemmouton
 */
public class IpEntry {

    private int ipId = -1;
    private String ipAddress = "";
    private String country = "";

    public IpEntry(String ip) {
        this.ipAddress = ip;
    }

    public IpEntry(int ipId) {
        this.ipId = ipId;
    }

    public String getIp() {
        return this.ipAddress;
    }

    public void setIp(String value) {
        this.ipAddress = value;
    }

    public int getId() {
        return this.ipId;
    }

    public void setId(int value) {
        this.ipId = value;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    public String getCountry() {
        return this.country;
    }
}
