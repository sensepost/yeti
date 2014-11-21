package com.sensepost.yeti.helpers;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.Log;
import com.sensepost.yeti.common.NetworkTools;
import com.sensepost.yeti.results.ForwardLookupResult;
import org.xbill.DNS.AAAARecord;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Address;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;
import org.xbill.DNS.ZoneTransferException;
import org.xbill.DNS.ZoneTransferIn;

public class ForwardLookupHelper {

    public static List<ForwardLookupResult> getARecord(String hostName, String domainName) throws TextParseException {
        List<ForwardLookupResult> entries = null;
        if (hostName != null && !hostName.isEmpty() && domainName != null && !domainName.isEmpty()) {
            Record[] recs = new Lookup(hostName, Type.A).run();
            if (recs != null) {
                if (recs.length > 0) {
                    entries = new ArrayList<>();
                    for (Record record : recs) {
                        ForwardLookupResult foundSubDomain = new ForwardLookupResult(domainName);
                        foundSubDomain.setHostName(hostName);
                        String ipAddress = ((ARecord) record).getAddress().getHostAddress();
                        foundSubDomain.setIpAddress(ipAddress);
                        foundSubDomain.setLookupType("A");
                        entries.add(foundSubDomain);
                    }
                }
            }
        }
        return entries;
    }

    public static List<ForwardLookupResult> getAAAARecord(String hostName, String domainName) throws TextParseException {
        List<ForwardLookupResult> entries = null;
        if (hostName != null && !hostName.isEmpty() && domainName != null && !domainName.isEmpty()) {
            Record[] recs = new Lookup(hostName, Type.AAAA).run();
            if (recs != null) {
                if (recs.length > 0) {
                    entries = new ArrayList<>();
                    for (Record record : recs) {
                        ForwardLookupResult foundSubDomain = new ForwardLookupResult(domainName);
                        foundSubDomain.setHostName(hostName);
                        String ipAddress = ((AAAARecord) record).getAddress().getHostAddress();
                        foundSubDomain.setIpAddress(ipAddress);
                        foundSubDomain.setLookupType("A");
                        entries.add(foundSubDomain);
                    }
                }
            }
        }
        return entries;
    }

    public static List<ForwardLookupResult> getCNameRecord(String hostName, String domain) throws TextParseException {
        List<ForwardLookupResult> entries = null;
        if (hostName != null && !hostName.isEmpty() && domain != null && !domain.isEmpty()) {
            Record[] recs = new Lookup(hostName, Type.CNAME).run();
            if (recs != null) {
                if (recs.length > 0) {
                    entries = new ArrayList<>();
                    for (Record record : recs) {
                        ForwardLookupResult foundSubDomain = new ForwardLookupResult(domain);
                        foundSubDomain.setHostName(hostName);

                        String alias = ((CNAMERecord) record).getAlias().toString();
                        if (alias.endsWith(".")) {
                            alias = alias.substring(0, (alias.length() - 1));
                        }

                        try {
                            String ipAddress = Address.getByName(hostName).getHostAddress();
                            foundSubDomain.setIpAddress(ipAddress);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ForwardLookupHelper.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (!alias.isEmpty()) {
                            if (hostName.compareTo(alias) != 0) {
                                String aliasDomain = NetworkTools.getDomainFromHost(alias);
                                ForwardLookupResult foundAliasDomain;
                                if (aliasDomain.compareTo(domain) != 0) {
                                    foundAliasDomain = new ForwardLookupResult(aliasDomain);
                                } else {
                                    foundAliasDomain = new ForwardLookupResult(domain);
                                }

                                foundAliasDomain.setHostName(alias);
                                try {
                                    String ipAddress = Address.getByName(alias).getHostAddress();
                                    foundAliasDomain.setIpAddress(ipAddress);
                                } catch (UnknownHostException ex) {
                                    Logger.getLogger(ForwardLookupHelper.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                foundAliasDomain.setLookupType("CNAME ALIAS");
                                entries.add(foundAliasDomain);
                            }
                        }

                        foundSubDomain.setLookupType("CNAME");
                        entries.add(foundSubDomain);
                    }
                }
            }
        }
        return entries;
    }

    public static void getTXTRecord() {
    }

    public static void getSRVRecord() {
    }

    public static List<ForwardLookupResult> getNSRecord(String domain) {
        ArrayList<ForwardLookupResult> result = null;
        if (domain != null && !domain.isEmpty()) {
            try {
                Lookup lu = new Lookup(domain, Type.NS);
                Record[] recs = lu.run();
                if (recs != null) {
                    result = new ArrayList<>();
                    for (Record record : recs) {
                        String nsServer = ((NSRecord) record).getAdditionalName().toString();
                        if (nsServer.endsWith(".")) {
                            nsServer = nsServer.substring(0, nsServer.length() - 1);
                        }
                        try {
                            String ipAddress = Address.getByName(nsServer).getHostAddress();
                            String realDomain = NetworkTools.getDomainFromHost(nsServer);
                            ForwardLookupResult fr;
                            if (realDomain.compareTo(domain) != 0) {
                                fr = new ForwardLookupResult(realDomain);
                            } else {
                                fr = new ForwardLookupResult(domain);
                            }

                            fr.setHostName(nsServer);
                            fr.setIpAddress(ipAddress);
                            fr.setLookupType("NS");
                            result.add(fr);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ForwardLookupHelper.class.getName()).log(Level.FINE, null, ex);
                        }
                    }
                }

            } catch (TextParseException ex) {
                Logger.getLogger(ForwardLookupHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public static List<ForwardLookupResult> getMXRecord(String domain) {
        List<ForwardLookupResult> result = null;
        if (domain != null && !domain.isEmpty()) {
            try {
                Lookup lu = new Lookup(domain, Type.MX);
                Record[] recs = lu.run();
                if (recs != null) {
                    result = new ArrayList<>();
                    for (Record record : recs) {
                        String mxServer = ((MXRecord) record).getAdditionalName().toString();
                        if (mxServer.endsWith(".")) {
                            mxServer = mxServer.substring(0, mxServer.length() - 1);
                        }
                        String ipAddress = "";
                        try {
                            ipAddress = Address.getByName(mxServer).getHostAddress();
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(ForwardLookupHelper.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String realDomain = NetworkTools.getDomainFromHost(mxServer);
                        ForwardLookupResult fr;
                        if (realDomain.compareTo(domain) != 0) {
                            fr = new ForwardLookupResult(realDomain);
                        } else {
                            fr = new ForwardLookupResult(domain);
                        }
                        fr.setHostName(mxServer);
                        fr.setIpAddress(ipAddress);
                        fr.setLookupType("MX");
                        result.add(fr);
                    }
                }
            } catch (TextParseException ex) {
                Logger.getLogger(ForwardLookupHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public static List<ForwardLookupResult> checkAllRecords(String hostName, String domain) {
        List<ForwardLookupResult> result = new ArrayList<>();
        if (hostName != null && !hostName.isEmpty() && domain != null && !domain.isEmpty()) {
            try {
                List<ForwardLookupResult> buf = ForwardLookupHelper.getARecord(hostName, domain);
                if (buf != null) {
                    result.addAll(buf);
                }
            } catch (TextParseException ex) {
                Logger.getLogger(ForwardLookupHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                List<ForwardLookupResult> buf = ForwardLookupHelper.getAAAARecord(hostName, domain);
                if (buf != null) {
                    result.addAll(buf);
                }
            } catch (TextParseException ex) {
                Logger.getLogger(ForwardLookupHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                List<ForwardLookupResult> buf = ForwardLookupHelper.getCNameRecord(hostName, domain);
                if (buf != null) {
                    result.addAll(buf);
                }
            } catch (TextParseException ex) {
                Logger.getLogger(ForwardLookupHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public static List<ForwardLookupResult> attemptZoneTransfer(String domain, List<ForwardLookupResult> nameServers) throws TextParseException {
        List<ForwardLookupResult> result = new ArrayList<>();

        ZoneTransferIn xfr;
        Iterator i = nameServers.iterator();
        for (ForwardLookupResult nameServer : nameServers) {
            try {
                xfr = ZoneTransferIn.newAXFR(new Name(domain), nameServer.getIpAddress(), null);
                List records = xfr.run();
                for (Iterator it = records.iterator(); it.hasNext();) {
                    Record r = (Record) it.next();
                    if (r.getType() == 1) {
                        ForwardLookupResult rec = new ForwardLookupResult(domain);
                        String hostName = ((ARecord) r).getName().toString().toLowerCase();

                        if (hostName.endsWith(".")) {
                            hostName = hostName.substring(0, hostName.length() - 1);
                        }

                        rec.setHostName(hostName);
                        rec.setIpAddress(((ARecord) r).getAddress().getHostAddress());
                        rec.setLookupType("A");
                        result.add(rec);
                    }
                }
            } catch (IOException ioex) {
                Logger.getLogger("ForwardLookupHelper.attemptZoneTransfer").log(Level.WARNING, null, ioex);
            } catch (ZoneTransferException zex) {
                Log.debug("ForwardLookupHelper.attemptZoneTransfer: Failed zonetransfer");
            }
        }
        return result;
    }

    public static boolean isWildCard(String domain) {
        if (domain != null && !domain.isEmpty()) {
            try {
                String subDomain = String.format("%s.%s", ConfigSettings.getWildCardSubdomain(), domain);
                if (ForwardLookupHelper.getARecord(subDomain, domain) == null) {
                    return false;
                }
            } catch (TextParseException ex) {
                Log.debug(ex.toString());
            }
        }
        return true;
    }
}
