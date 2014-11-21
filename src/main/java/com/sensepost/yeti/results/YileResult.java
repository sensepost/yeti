package com.sensepost.yeti.results;

public class YileResult extends Object {

    private String source = "";
    private String hostName = "";
    private String domain = "";
    private String registrant = "";
    private boolean keep = false;
    private String ipaddress = "";
    private String url = "";

    public YileResult(String domainName) {
        this.domain = domainName;
    }

    public String getDomainName() {
        return this.domain;
    }

    public String getHostName() {
        return this.hostName;
    }

    public String getSource() {
        return this.source;
    }

    public String getRegistrant() {
        return this.registrant;
    }

    public String getIPAddress() {
        return this.ipaddress;
    }

    public void setDomainName(String domainName) {
        this.domain = domainName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public void setKeep(boolean value) {
        this.keep = value;
    }

    public void setIPAddress(String ipAddress) {
        this.ipaddress = ipAddress;
    }

    public boolean getKeep() {
        return this.keep;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    public String getUrl() {
        return this.url;
    }
}
