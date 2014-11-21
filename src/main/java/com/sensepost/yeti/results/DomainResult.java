package com.sensepost.yeti.results;

import java.util.Map;

/**
 *
 * @author willemm
 */
public class DomainResult {

    private String domainName = "";
    private String adminName = "";
    private String nameServer = "";
    private String registrant = "";
    private boolean keep = false;
    private Map<String, String> attributes;

    public DomainResult(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getNameServer() {
        return nameServer;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public void setKeep(boolean value) {
        keep = value;
    }

    public boolean getKeep() {
        return keep;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
