package com.sensepost.yeti.results;

/**
 *
 * @author willem
 */
public class CollectorResult {

    private String domainName = "";
    private String hostName = "";
    private String ipAddress = "";
    private String extraData = "";
    private String type = "";
    private boolean keep = true;

    public CollectorResult() {

    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public String getHostName() {
        return this.hostName;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public String getExtraData() {
        return this.extraData;
    }

    public boolean getKeep() {
        return this.keep;
    }

    public String getType() {
        return this.type;
    }
}
