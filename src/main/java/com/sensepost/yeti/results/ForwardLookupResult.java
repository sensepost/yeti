package com.sensepost.yeti.results;

import com.sensepost.yeti.dns.DNS;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author willemm
 */
public class ForwardLookupResult extends Object {

    private final DomainResult domainResult;
    private String lookupType = "";
    private String hostName = "";
    private String ipAddress = "";
    private boolean keep = true;
    private Map<String, String> attributes = new HashMap<>();

    public ForwardLookupResult(String domainName) {
        domainResult = DNS.getSOARecord(domainName);
    }

    public String getDomainName() {
        if (domainResult != null) {
            return domainResult.getDomainName();
        }
        return "";
    }
    

    public String getHostName() {
        return this.hostName;
    }

    public String getLookupType() {
        return this.lookupType;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setLookupType(String lookupType) {
        this.lookupType = lookupType;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress.replace("/", "");
    }

    public void setKeep(boolean value) {
        this.keep = value;
    }

    public boolean getKeep() {
        return this.keep;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
