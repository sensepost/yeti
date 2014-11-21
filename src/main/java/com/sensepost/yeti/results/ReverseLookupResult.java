package com.sensepost.yeti.results;

/**
 *
 * @author willemmouton
 */
public class ReverseLookupResult extends Object {

    private String ipAddress = "";
    private String hostName = "";
    private String domainName = "";
    private boolean keep = false;

    public ReverseLookupResult(String ip) {
        ipAddress = ip;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostName() {
        return hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setDomainName(String value) {
        domainName = value;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setKeep(boolean value) {
        keep = value;
    }

    public boolean getKeep() {
        return keep;
    }
}
