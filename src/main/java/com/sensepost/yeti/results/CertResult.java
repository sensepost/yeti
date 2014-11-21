package com.sensepost.yeti.results;

/**
 *
 * @author willemm
 */
public class CertResult extends Object {

    private String ipAddress = null;
    private String hostName = null;
    private String domainName = null;
    private boolean keep = true;

    public CertResult(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress.replace("\\", "");
    }

    public void setHostName(String commonName) {
        this.hostName = commonName.toLowerCase();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getHostName() {
        return hostName;
    }

    public void setKeep(boolean value) {
        keep = value;
    }

    public boolean getKeep() {
        return keep;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName.toLowerCase();
    }
}
