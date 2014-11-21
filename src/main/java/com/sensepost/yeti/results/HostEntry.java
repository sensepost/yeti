package com.sensepost.yeti.results;

import java.util.ArrayList;

/**
 *
 * @author willemmouton
 */
public class HostEntry {

    private String hostName = "";
    private String domainName = "";
    private int hostId = -1;
    private final ArrayList<String> ips = new ArrayList();

    public HostEntry(String hostName) {
        this.hostName = hostName;
    }

    public HostEntry(int hostId) {
        this.hostId = hostId;
    }

    public void setHostName(String value) {
        hostName = value;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostId(int value) {
        hostId = value;
    }

    public int getHostId() {
        return hostId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String value) {
        domainName = value;
    }

    public void addIps(ArrayList<String> ips) {
        this.ips.addAll(ips);
    }

    @SuppressWarnings("rawtypes")
    public ArrayList getIps() {
        return ips;
    }
}
