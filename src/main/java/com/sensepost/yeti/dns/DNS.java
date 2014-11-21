package com.sensepost.yeti.dns;

import java.util.ArrayList;
import java.util.List;
import com.sensepost.yeti.common.NetworkTools;
import com.sensepost.yeti.results.ARecordResult;
import com.sensepost.yeti.results.SOARecordResult;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.PTRRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.SOARecord;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

/**
 *
 * @author willem
 */
public class DNS {

    public static SOARecordResult getSOARecord(String domainName) {
        SOARecordResult newDomain = null;
        try {
            Record[] recs = new Lookup(domainName, Type.SOA).run();
            if (recs != null) {
                domainName = ((SOARecord) recs[0]).getName().canonicalize().toString(true);
                newDomain = new SOARecordResult(domainName);
                newDomain.setNameServer(((SOARecord) recs[0]).getHost().toString(true));
                newDomain.setAdminName(((SOARecord) recs[0]).getAdmin().toString(true));
            }
        } catch (TextParseException tpe) {
            Logger.getLogger(DNS.class.getName()).log(Level.SEVERE, null, tpe);
        }
        return newDomain;
    }

    public static NSRecord getNSRecord(String domainName) {
        return null;
    }

    public static MXRecord getMXRecord(String domainName) {
        return null;
    }

    public static List<ARecordResult> getARecord(String hostName) throws TextParseException {
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

    public static PTRRecord getPTRRecord(String ipAddress) {
        return null;
    }

    public static CNAMERecord getCNAMERecord(String hostName) {
        return null;
    }

    public static TXTRecord getTXTRecord(String hostName) {
        return null;
    }
}
