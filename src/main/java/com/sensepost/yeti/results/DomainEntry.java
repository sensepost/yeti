package com.sensepost.yeti.results;

import java.util.ArrayList;
import java.util.List;

public class DomainEntry {

    private String domainName = "";
    private final ArrayList<String> nameServers = new ArrayList<>();
    private final ArrayList<String> mailServers = new ArrayList<>();
    private final ArrayList<KeyValue> attributes = new ArrayList<>();
    private String whoisResult = null;
    private String nameServer = null;
    private String admin = null;

    public DomainEntry(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setNameServers(List<String> nameServers) {
        if (nameServers != null) {
            nameServers.addAll(nameServers);
        }
    }

    public void addNameServer(String nameServer) {
        if (nameServer != null) {
            nameServers.add(nameServer);
        }
    }

    public String getFirstNameServer() {
        if (nameServers.size() == 1) {
            return nameServers.get(0);
        } else if (nameServers.size() > 1) {
            String result = String.format("%s, (%d) more", nameServers.get(0), nameServers.size() - 1);
            return result;
        }
        return "";
    }

    public String getFirstMailSever() {
        if (mailServers.size() == 1) {
            return mailServers.get(0);
        }
        if (mailServers.size() > 1) {
            String result = String.format("%s, (%d) more", mailServers.get(0), mailServers.size() - 1);
            return result;
        }
        return "";
    }

    public String getNS() {
        return nameServer;
    }

    public String getAdmin() {
        return admin;
    }

    public void setNS(String ns) {
        nameServer = ns;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setMailServers(List<String> ms) {
        if (ms != null) {
            mailServers.addAll(ms);
        }
    }

    public void addMailServer(String mailServer) {
        mailServers.add(mailServer);
    }

    public void addAttributes(List<KeyValue> attributes) {
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public int getAttributeCount() {
        int count = attributes.size() + mailServers.size() + nameServers.size();
        if (admin != null && ! admin.isEmpty()) {
            count++;
        }
        if (nameServer != null && !nameServer.isEmpty()) {
            count++;
        }
        return count;
    }

    public String getWhoisResult() {
        return whoisResult;
    }

    public void setWhoisResult(String value) {
        whoisResult = value;
    }
}
