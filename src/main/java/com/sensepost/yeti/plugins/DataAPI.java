package com.sensepost.yeti.plugins;

import java.util.ArrayList;
import java.util.List;
import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.NetworkTools;
import com.sensepost.yeti.dns.DNS;
import com.sensepost.yeti.persistence.DataStore;
import com.sensepost.yeti.results.ARecordResult;
import com.sensepost.yeti.results.DomainResult;
import com.sensepost.yeti.persistence.entities.Port;
import com.sensepost.yeti.results.SOARecordResult;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.PTRRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public class DataAPI {

    public String getConfigSetting(String configSetting) {
        return ConfigSettings.getConfigValue(configSetting);
    }

    public String testAPI() {
        return "Hello from API";
    }

    public List<String> getHosts() {
        return DataStore.getHosts();
    }

    public List<String> getHosts(int footprintId) {
        return DataStore.getHosts(footprintId);
    }

    public List<String> getDomains() {
        return DataStore.getDomains();
    }

    public List<Integer> getFootprintIds() {
        return DataStore.getFootprintIds();
    }

    public void addHostAttribute(String host, String key, String value) {
        addHostAttribute(host, key, value, "info");
    }

    public void addDomainToCurrentFootprint(Object domain) {
        DomainResult dr = (DomainResult) domain;
        DataStore.addDomainToCurrentFootprint(dr.getDomainName(), dr.getRegistrant());
    }

    public static void addDomainAttribute(String domain, String key, String val) {
        DataStore.addDomainAttribute(domain, key, val);
    }

    public void addHostAttribute(String hostName, String key, String value, String type) {
        DataStore.addHostAttribute(hostName, key, value, type);
    }

    public void addPortToIP(String ipAddress, int port, String state, String service, String version, String extraInfo) {
        DataStore.addPortToIP(ipAddress, port, state, service, version, extraInfo);
    }

    public List<String> getAllIPS() {
        return DataStore.getAllIPSForCurrentFootprint();
    }

    public int getCurrentFootprintId() {
        return DataStore.getCurrentFootprintId();
    }

    public List<String> getDomainAttributes(String domain) {
        return DataStore.getDomainAttributes(domain);
    }

    public List<String> getHostAttributes(String host) {
        return DataStore.getHostAttributes(host);
    }

    public List<String> getHostsPerIp(String ip) {
        return DataStore.getHostsPerIp(ip);
    }

    public List<String> getIpAttributes(String ip) {
        List<String> results = new ArrayList<>();
        List<Port> ports = DataStore.getPortsForIp(ip);
        for (Port port : ports) {
            results.add(port + ":" + port.getPortState() + ":" + port.getPortNumber() + "-" + port.getVersion());
        }
        return results;
    }

    public SOARecordResult getSOARecord(String domainName) throws TextParseException {
        return DNS.getSOARecord(domainName);
    }

    public NSRecord getNSRecord(String domainName) {
        return null;
    }

    public MXRecord getMXRecord(String domainName) {
        return null;
    }

    public List<ARecordResult> getARecord(String hostName) throws TextParseException {
        List<ARecordResult> entries = null;

        Record[] recs = new Lookup(hostName, Type.A).run();
        if (recs != null) {
            if (recs.length > 0) {
                entries = new ArrayList<>();
                for (Record record : recs) {
                    ARecordResult foundSubDomain = new ARecordResult(NetworkTools.getDomainFromHost(hostName));
                    foundSubDomain.setHostName(hostName);
                    String ipAddress = ((ARecord) record).getAddress().getHostAddress();
                    foundSubDomain.setIpAddress(ipAddress);
                    foundSubDomain.setLookupType("A");
                    entries.add(foundSubDomain);
                }
            }
        }

        return entries;
    }

    public PTRRecord getPTRRecord(String ipAddress) {
        return null;
    }

    public CNAMERecord getCNAMERecord(String hostName) {
        return null;
    }

    public TXTRecord getTXTRecord(String hostName) {
        return null;
    }
}