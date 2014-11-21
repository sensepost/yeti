package com.sensepost.yeti.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sensepost.yeti.common.FormatUtil;
import com.sensepost.yeti.common.Globals;
import com.sensepost.yeti.common.Log;
import com.sensepost.yeti.persistence.dao.DomainAttributeDao;
import com.sensepost.yeti.persistence.dao.DomainDao;
import com.sensepost.yeti.persistence.dao.FootprintDao;
import com.sensepost.yeti.persistence.dao.HibernateDaoFactory;
import com.sensepost.yeti.persistence.dao.HostAttributeDao;
import com.sensepost.yeti.persistence.dao.HostDao;
import com.sensepost.yeti.persistence.dao.InitialDataDao;
import com.sensepost.yeti.persistence.dao.IpAddressDao;
import com.sensepost.yeti.persistence.dao.PortDao;
import com.sensepost.yeti.persistence.entities.Domain;
import com.sensepost.yeti.persistence.entities.DomainAttribute;
import com.sensepost.yeti.persistence.entities.Footprint;
import com.sensepost.yeti.persistence.entities.Host;
import com.sensepost.yeti.persistence.entities.HostAttribute;
import com.sensepost.yeti.persistence.entities.InitialData;
import com.sensepost.yeti.persistence.entities.IpAddress;
import com.sensepost.yeti.persistence.entities.Port;
import com.sensepost.yeti.results.CertResult;
import com.sensepost.yeti.results.CollectorResult;
import com.sensepost.yeti.results.DomainEntry;
import com.sensepost.yeti.results.ForwardLookupResult;
import com.sensepost.yeti.results.ReverseLookupResult;
import com.sensepost.yeti.results.DomainResult;
import com.sensepost.yeti.results.FootprintData;
import com.sensepost.yeti.results.HostEntry;
import com.sensepost.yeti.results.KeyValue;
import com.sensepost.yeti.treeview.AttributeValue;
import org.hibernate.Transaction;

/**
 *
 * @author willemmouton
 */
public class DataStore {

    private final static Map<String, String> whoisCache = new HashMap<>();

    public final static String A = "A";
    public final static String MX = "MX";
    public final static String NS = "NS";
    public final static String PTR = "PTR";
    public final static String SOA = "SOA";

    public final static String ASTERIX_RECORD = "* Record";
    public final static String HTTP = "HTTP";
    public final static String HTTPS = "HTTPS";
    public final static String ZONE_TRANSFER = "Zone Transfer Enabled";

    public final static String CONFIG = "Config";
    public final static String DNS_RECORD = "DNS Record";
    public final static String MAIL_SERVER = "Mailserver";
    public final static String NAME_SERVER = "Nameserver";
    public final static String SOA_NS = "SOA Nameserver";
    public final static String SOA_ADMIN = "SOA Admin";
    public final static String INFORMATION = "Information";
    public final static String WEB_SERVER = "Web Server";

    public final static String ROOT_TERM = "ROOT_TERM";
    public final static String IP = "IP";
    public final static String DOMAIN = "DOMAIN";
    public final static String HOST = "HOST";

    public static boolean testConnection() {
        return HibernateUtil.getSession() != null;
    }

    public static int addFootprint(String footprintName) {
        Footprint footprint = new Footprint();
        footprint.setId(-1);
        footprint.setName(footprintName);

        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            FootprintDao dao = HibernateDaoFactory.getInstance().getFootprintDao();
            dao.saveOrUpdate(footprint);

            tx.commit();
        } catch (RuntimeException re) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.addFootprint").log(Level.SEVERE, null, re);
        }

        return footprint.getId();
    }

    public static int addDomainToCurrentFootprint(String name, String whois) {
        Domain domain = new Domain();
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
            Footprint currentFootprint = fpDao.findById(Globals.getCurrentFootprintId());

            domain.setName(name);
            domain.setWhois(whois);
            domain.setFootprint(currentFootprint);

            DomainDao dao = HibernateDaoFactory.getInstance().getDomainDao();
            dao.saveOrUpdate(domain);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.addDomainToCurrentFootprint").log(Level.SEVERE, null, e);
        }
        return domain.getId();
    }

    public static int addDomains(List<Object> domains) {
        if (domains != null) {
            Log.debug("Adding " + domains.size() + " domains");
            Transaction tx = null;

            for (Object t : domains) {
                try {
                    tx = HibernateUtil.getSession().beginTransaction();
                    DomainResult tldResult = (DomainResult) t;
                    if (tldResult.getKeep()) {
                        FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
                        Footprint currentFootprint = fpDao.findById(Globals.getCurrentFootprintId());

                        Domain domain = new Domain();
                        domain.setName(tldResult.getDomainName());
                        domain.setWhois(tldResult.getRegistrant());
                        domain.setFootprint(currentFootprint);

                        DomainDao dao = HibernateDaoFactory.getInstance().getDomainDao();
                        dao.saveOrUpdate(domain);

                        DomainAttributeDao daDao = HibernateDaoFactory.getInstance().getDomainAttributeDao();

                        if (tldResult.getAdminName() != null && !tldResult.getAdminName().isEmpty()) {
                            DomainAttribute da = new DomainAttribute();
                            da.setKey(SOA_ADMIN);
                            da.setValue(tldResult.getAdminName());
                            da.setDomain(domain);
                            daDao.saveOrUpdate(da);

                            da = new DomainAttribute();
                            da.setKey(DNS_RECORD);
                            da.setValue(SOA);
                            da.setDomain(domain);
                            daDao.saveOrUpdate(da);
                        }

                        if (tldResult.getNameServer() != null && !tldResult.getNameServer().isEmpty()) {
                            DomainAttribute da = new DomainAttribute();
                            da.setKey(SOA_NS);
                            da.setValue(tldResult.getNameServer());
                            da.setDomain(domain);
                            daDao.saveOrUpdate(da);
                        }

                        Map<String, String> otherAttrs = tldResult.getAttributes();
                        for (String key : otherAttrs.keySet()) {
                            DomainAttribute da = new DomainAttribute();
                            da.setKey(key);
                            da.setValue(otherAttrs.get(key));
                            da.setDomain(domain);
                            daDao.saveOrUpdate(da);
                        }
                    }

                    tx.commit();
                } catch (RuntimeException e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    Logger.getLogger("DataStore.addDomainToCurrentFootprint").log(Level.SEVERE, null, e);
                }
            }

            return domains.size();
        }

        Log.debug("No domains added, null list");
        return -1;
    }

    public static void addForwardLookupHosts(List<Object> data) {
        if (data != null) {
            Log.debug("Adding " + data.size() + " forward lookup hosts");
            Transaction tx = null;

            for (Object obj : data) {
                ForwardLookupResult flr = (ForwardLookupResult) obj;
                if (flr.getKeep()) {
                    String domainName = flr.getDomainName();
                    try {
                        tx = HibernateUtil.getSession().beginTransaction();

                        FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
                        Footprint currentFootprint = fpDao.findById(Globals.getCurrentFootprintId());

                        DomainDao dDao = HibernateDaoFactory.getInstance().getDomainDao();
                        Domain domain = dDao.findByName(domainName, currentFootprint.getId());

                        if (domain == null) {
                            domain = new Domain();
                            domain.setName(domainName);
                            domain.setFootprint(currentFootprint);
                            dDao.saveOrUpdate(domain);
                        }

                        DomainAttributeDao daDao = HibernateDaoFactory.getInstance().getDomainAttributeDao();
                        if (null != flr.getLookupType()) switch (flr.getLookupType()) {
                            case NS:{
                                DomainAttribute da = new DomainAttribute();
                                    da.setKey(NAME_SERVER);
                                    da.setValue(flr.getHostName());
                                    da.setDomain(domain);
                                    daDao.saveOrUpdate(da);
                                    break;
                                }
                            case MX:{
                                DomainAttribute da = new DomainAttribute();
                                    da.setKey(MAIL_SERVER);
                                    da.setValue(flr.getHostName());
                                    da.setDomain(domain);
                                    daDao.saveOrUpdate(da);
                                    break;
                                }
                        }
                        
                        if (flr.getAttributes().size() > 0) {
                            for (String key : flr.getAttributes().keySet()) {
                                DomainAttribute da = new DomainAttribute();
                                da.setKey(key);
                                da.setValue(flr.getAttributes().get(key));
                                da.setDomain(domain);
                                daDao.saveOrUpdate(da);
                            }
                        }

                        IpAddress ipAddress = null;
                        if (flr.getIpAddress() != null && !flr.getIpAddress().isEmpty()) {
                            ipAddress = new IpAddress();
                            ipAddress.setAddress(flr.getIpAddress());
                            ipAddress.setFootprint(currentFootprint);

                            IpAddressDao ipDao = HibernateDaoFactory.getInstance().getIpAddressDao();
                            ipAddress = ipDao.saveOrUpdate(ipAddress);
                        }

                        HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();

                        Host host = new Host();
                        host.setName(flr.getHostName());
                        host.setDomain(domain);
                        host.addIpAddress(ipAddress);
                        hDao.saveOrUpdate(host);
                        
                        if (flr.getLookupType() != null && !flr.getLookupType().isEmpty()) {
                            HostAttribute ha = new HostAttribute();
                            ha.setType(DNS_RECORD);
                            ha.setKey(DNS_RECORD);
                            ha.setValue(flr.getLookupType());
                            ha.setHost(host);

                            HostAttributeDao haDao = HibernateDaoFactory.getInstance().getHostAttributeDao();
                            haDao.saveOrUpdate(ha);
                        }

                        tx.commit();
                    } catch (RuntimeException e) {
                        if (tx != null) {
                            tx.rollback();
                        }
                        Logger.getLogger("DataStore.addForwardLookupHosts").log(Level.SEVERE, null, e);
                    }
                }
            }
        }
    }

    public static void addCollectorResults(ArrayList<Object> data) {
        for (Object obj : data) {
            CollectorResult cr = (CollectorResult) obj;
            Transaction tx = null;

            try {
                tx = HibernateUtil.getSession().beginTransaction();

                FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
                Footprint currentFootprint = fpDao.findById(Globals.getCurrentFootprintId());

                DomainDao dDao = HibernateDaoFactory.getInstance().getDomainDao();
                Domain domain = dDao.findByName(cr.getDomainName(), currentFootprint.getId());

                if (domain == null) {
                    domain = new Domain();
                    domain.setName(cr.getDomainName());
                    domain.setFootprint(currentFootprint);
                    dDao.saveOrUpdate(domain);
                }

                IpAddressDao ipDao = HibernateDaoFactory.getInstance().getIpAddressDao();
                IpAddress ipAddress = ipDao.findByAddressAndFootprint(cr.getIpAddress(), currentFootprint.getId());

                if (ipAddress == null && cr.getIpAddress() != null && !cr.getIpAddress().isEmpty()) {
                    ipAddress = new IpAddress();
                    ipAddress.setAddress(cr.getIpAddress());
                    ipAddress.setFootprint(currentFootprint);

                    ipDao.saveOrUpdate(ipAddress);
                }

                HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();
                Host host = hDao.findByNameAndDomain(cr.getHostName(), domain.getId());
                if (host == null) {
                    host = new Host();
                    host.setName(cr.getHostName());
                    host.setDomain(domain);
                    host.addIpAddress(ipAddress);
                }

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null) {
                    tx.rollback();
                }
                Logger.getLogger("DataStore.addCollectorResults").log(Level.SEVERE, null, e);
            }
        }
    }

    public static void addCertHosts(List<Object> data) {
        for (Object obj : data) {
            CertResult cr = (CertResult) obj;
            Transaction tx = null;

            try {
                tx = HibernateUtil.getSession().beginTransaction();

                FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
                Footprint currentFootprint = fpDao.findById(Globals.getCurrentFootprintId());

                DomainDao dDao = HibernateDaoFactory.getInstance().getDomainDao();
                Domain domain = dDao.findByName(cr.getDomainName(), currentFootprint.getId());

                if (domain == null) {
                    domain = new Domain();
                    domain.setName(cr.getDomainName());
                    domain.setFootprint(currentFootprint);
                    dDao.saveOrUpdate(domain);
                }

                IpAddressDao ipDao = HibernateDaoFactory.getInstance().getIpAddressDao();
                IpAddress ipAddress = ipDao.findByAddressAndFootprint(cr.getIpAddress(), currentFootprint.getId());

                if (ipAddress == null && cr.getIpAddress() != null && !cr.getIpAddress().isEmpty()) {
                    ipAddress = new IpAddress();
                    ipAddress.setAddress(cr.getIpAddress());
                    ipAddress.setFootprint(currentFootprint);

                    ipDao.saveOrUpdate(ipAddress);
                }

                HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();
                Host host = hDao.findByNameAndDomain(cr.getHostName(), domain.getId());
                if (host == null) {
                    host = new Host();
                    host.setName(cr.getHostName());
                    host.setDomain(domain);
                    host.addIpAddress(ipAddress);
                }
                hDao.saveOrUpdate(host);

                HostAttributeDao haDao = HibernateDaoFactory.getInstance().getHostAttributeDao();

                HostAttribute ha = new HostAttribute();
                ha.setHost(host);
                ha.setKey(WEB_SERVER);
                ha.setValue(HTTPS);
                ha.setType(INFORMATION);

                haDao.saveOrUpdate(ha);

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null) {
                    tx.rollback();
                }
                Logger.getLogger("DataStore.addCertHosts").log(Level.SEVERE, null, e);
            }
        }
    }

    public static void addReverseHosts(ArrayList<Object> data) {
        for (Object obj : data) {
            ReverseLookupResult rr = (ReverseLookupResult) obj;

            Transaction tx = null;

            try {
                tx = HibernateUtil.getSession().beginTransaction();

                FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
                Footprint currentFootprint = fpDao.findById(Globals.getCurrentFootprintId());

                DomainDao dDao = HibernateDaoFactory.getInstance().getDomainDao();
                Domain domain = dDao.findByName(rr.getDomainName(), currentFootprint.getId());

                if (domain == null) {
                    domain = new Domain();
                    domain.setName(rr.getDomainName());
                    domain.setFootprint(currentFootprint);
                    dDao.saveOrUpdate(domain);
                }

                IpAddressDao ipDao = HibernateDaoFactory.getInstance().getIpAddressDao();
                IpAddress ipAddress = ipDao.findByAddressAndFootprint(rr.getIpAddress(), currentFootprint.getId());

                if (ipAddress == null && rr.getIpAddress() != null && !rr.getIpAddress().isEmpty()) {
                    ipAddress = new IpAddress();
                    ipAddress.setAddress(rr.getIpAddress());
                    ipAddress.setFootprint(currentFootprint);

                    ipDao.saveOrUpdate(ipAddress);
                }

                HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();
                Host host = hDao.findByNameAndDomain(rr.getHostName(), domain.getId());
                if (host == null) {
                    host = new Host();
                    host.setName(rr.getHostName());
                    host.setDomain(domain);
                    host.addIpAddress(ipAddress);
                }

                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null) {
                    tx.rollback();
                }
                Logger.getLogger("DataStore.addReverseHosts").log(Level.SEVERE, null, e);
            }
        }
    }

    public static void deleteDomainFromFootprint(String domainName, int footprintId) {
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            DomainDao dDao = HibernateDaoFactory.getInstance().getDomainDao();
            dDao.deleteDomain(domainName, footprintId);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.deleteDomainFromFootprint").log(Level.SEVERE, null, e);
        }
    }

    public static void deleteDomainFromFootprint(String domainName) {
        deleteDomainFromFootprint(domainName, Globals.getCurrentFootprintId());
    }

    public static void deleteDomainAttribute(String domainName, String key, String value) {
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            DomainAttributeDao daDao = HibernateDaoFactory.getInstance().getDomainAttributeDao();
            daDao.deleteDomainAtrribute(domainName, key, value);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.deleteDomainAttribute").log(Level.SEVERE, null, e);
        }
    }

    public static void deleteHostFromFootprint(String hostName, int footprintId) {
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();
            hDao.deleteHost(hostName, footprintId);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.deleteDomainFromFootprint").log(Level.SEVERE, null, e);
        }
    }

    public static void deleteHostFromFootprint(String domainName) {
        deleteHostFromFootprint(domainName, Globals.getCurrentFootprintId());
    }

    public static void deleteHostAttribute(String hostName, String key, String value, int footprintId) {
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            HostAttributeDao haDao = HibernateDaoFactory.getInstance().getHostAttributeDao();
            haDao.deleteHostAtrribute(hostName, key, value, footprintId);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.deleteHostFromFootprint").log(Level.SEVERE, null, e);
        }
    }

    public static void deleteHostAttribute(String hostName, String key, String value) {
        deleteHostAttribute(hostName, key, value, Globals.getCurrentFootprintId());
    }

    public static void deletePortFromIp(String ipAddress, int port, int footprintId) {
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            PortDao pDao = HibernateDaoFactory.getInstance().getPortDao();
            pDao.deletePortFromIp(ipAddress, port, footprintId);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.deletePortFromIp").log(Level.SEVERE, null, e);
        }
    }

    public static void deletePortFromIp(String ipAddress, int port) {
        deletePortFromIp(ipAddress, port, Globals.getCurrentFootprintId());
    }

    public static void deleteIpFromFootprint(String ipAddress, int footprintId) {
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            IpAddressDao iaDao = HibernateDaoFactory.getInstance().getIpAddressDao();
            iaDao.deleteIpAddressFromFootprint(ipAddress, footprintId);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.deleteIpFromFootprint").log(Level.SEVERE, null, e);
        }
    }

    public static void deleteIpFromFootprint(String ipAddress) {
        deleteIpFromFootprint(ipAddress, Globals.getCurrentFootprintId());
    }

//    public static void addYileHosts(ArrayList<Object> data) {
//        // A list of domain names that have been checked if they exist
//        List<String> checked = new ArrayList<>();
//
//        for (Object obj : data) {
//            YileResult yr = (YileResult) obj;
//            try {
//                if (!checked.contains(yr.getDomainName())) {
//                    checked.add(yr.getDomainName());
//                    DomainResult dr = DNS.getSOARecord(yr.getDomainName());
//                    int domainId = DataDiskStoreage.addDomain(dr);
//                }
//
//                int hostId = DataDiskStoreage.addHost(yr.getHostName(), yr.getDomainName());
//                DataDiskStoreage.addHostAttribute(yr.getHostName(), DNS_RECORD, A, INFORMATION);
//                if (!yr.getUrl().isEmpty()) {
//                    if (yr.getUrl().toLowerCase().startsWith("https:")) {
//                        DataDiskStoreage.addHostAttribute(yr.getHostName(), WEB_SERVER, HTTPS, INFORMATION);
//                    } else if (yr.getUrl().toLowerCase().startsWith("http:")) {
//                        DataDiskStoreage.addHostAttribute(yr.getHostName(), WEB_SERVER, HTTP, INFORMATION);
//                    }
//                }
//                int ipId = DataDiskStoreage.addIPAddress(yr.getIPAddress(), hostId);
//            } catch (TextParseException e) {
//                Logger.getLogger("DataStore.addYileHosts").log(Level.SEVERE, null, e);
//            }
//        }
//    }
    public static String getWhoisCache(String domain) {
        String result = whoisCache.get(domain);
        return result;
    }

    public static void setWhoisCache(String domain, String whoisResult) {
        whoisCache.put(domain, whoisResult);
    }

    // START OF API INTERNAL FUNCTIONS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static Integer getFootprintId(String footprintName) {
        Transaction tx = null;
        Integer result = -1;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
            result = fpDao.getFootprintId(footprintName);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getFootprintIds").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<Integer> getFootprintIds() {
        Transaction tx = null;
        List<Integer> result = new ArrayList<>();
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
            List<Footprint> list = fpDao.findAll();

            if (list != null && !list.isEmpty()) {
                for (Footprint fp : list) {
                    result.add(fp.getId());
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getFootprintIds").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<FootprintData> getFootprintData() {
        Transaction tx = null;
        List<FootprintData> result = new ArrayList<>();
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
            List<Footprint> list = fpDao.findAll();

            if (list != null && !list.isEmpty()) {
                for (Footprint fp : list) {
                    FootprintData fpd = new FootprintData(fp.getName());
                    fpd.setId(fp.getId());
                    fpd.setCreated(FormatUtil.formatDate(fp.getCreated()));
                    result.add(fpd);
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getFootprintData").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<String> getHosts(int footprintId) {
        Transaction tx = null;
        List<String> result = new ArrayList<>();

        try {
            tx = HibernateUtil.getSession().beginTransaction();

            HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();
            List<Host> hosts = hDao.findByFootprint(footprintId);

            for (Host host : hosts) {
                result.add(host.getName());
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getHosts").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<String> getHosts() {
        return getHosts(Globals.getCurrentFootprintId());
    }

    public static List<String> getHosts(String domainName, int footprintId) {
        Transaction tx = null;
        List<String> result = new ArrayList<>();

        try {
            tx = HibernateUtil.getSession().beginTransaction();

            HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();
            List<Host> list = hDao.findByDomainAndFootprint(domainName, footprintId);

            if (list != null && !list.isEmpty()) {
                for (Host host : list) {
                    result.add(host.getName());
                }
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getHosts").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<String> getHosts(String domainName) {
        return getHosts(domainName, Globals.getCurrentFootprintId());
    }

    public static List<String> getHostsPerIp(String ip) {
        Transaction tx = null;
        List<String> result = new ArrayList<>();

        try {
            tx = HibernateUtil.getSession().beginTransaction();

            HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();
            List<Host> hosts = hDao.findByIpAddress(ip);

            for (Host host : hosts) {
                result.add(host.getName());
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getHostsPerIp").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<HostEntry> getHostDataForFootprint(int footprintId) {
        Transaction tx = null;
        List<HostEntry> result = new ArrayList<>();

        try {
            tx = HibernateUtil.getSession().beginTransaction();

            HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();
            List<Host> list = hDao.findByFootprint(footprintId);

            if (list != null && !list.isEmpty()) {
                for (Host host : list) {
                    HostEntry hostEntry = new HostEntry(host.getName());
                    if (host.getDomain() != null) {
                        hostEntry.setDomainName(host.getDomain().getName());
                    }
                    result.add(hostEntry);
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getHostDataForFootprint").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public List<HostEntry> getHostDataForFootprint() {
        return getHostDataForFootprint(Globals.getCurrentFootprintId());
    }

    public static List<String> getHostAttributes(String hostName) {
        Transaction tx = null;
        List<String> result = new ArrayList<>();

        try {
            tx = HibernateUtil.getSession().beginTransaction();

            HostAttributeDao haDao = HibernateDaoFactory.getInstance().getHostAttributeDao();
            List<HostAttribute> list = haDao.findByHostName(hostName);

            if (list != null && !list.isEmpty()) {
                for (HostAttribute ha : list) {
                    result.add(ha.getKey() + " - " + ha.getValue());
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getHostAttributes").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<AttributeValue> getHostAttributesKeyValue(String hostName) {
        Transaction tx = null;
        List<AttributeValue> result = new ArrayList<>();

        try {
            tx = HibernateUtil.getSession().beginTransaction();

            HostAttributeDao haDao = HibernateDaoFactory.getInstance().getHostAttributeDao();
            List<HostAttribute> list = haDao.findByHostName(hostName);

            if (list != null && !list.isEmpty()) {
                for (HostAttribute ha : list) {
                    AttributeValue av = new AttributeValue(ha.getKey(), ha.getValue(), ha.getType());
                    result.add(av);
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getHostAttributesKeyValue").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<String> getIpAddressesForHost(String hostName) {
        Transaction tx = null;
        List<String> result = new ArrayList<>();

        try {
            tx = HibernateUtil.getSession().beginTransaction();

            HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();
            Host host = hDao.findByNameAndFootprint(hostName, Globals.getCurrentFootprintId());
            List<IpAddress> ipAddresses = host.getIpAddresses();
            for (IpAddress ipAddress : ipAddresses) {
                result.add(ipAddress.getAddress());
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getIpAddressesForHost").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static void addInitialItems(List<String> rootTerms, List<String> ips,
            List<String> domains, List<String> hosts) {

        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();
            FootprintDao fpDao = HibernateDaoFactory.getInstance().getFootprintDao();
            Footprint currentFootprint = fpDao.findById(Globals.getCurrentFootprintId());

            InitialDataDao idDao = HibernateDaoFactory.getInstance().getInitialDataDao();

            for (String rootTerm : rootTerms) {
                InitialData initialData = new InitialData();
                initialData.setFootprint(currentFootprint);
                initialData.setType(ROOT_TERM);
                initialData.setValue(rootTerm);
                idDao.saveOrUpdate(initialData);
            }

            for (String ip : ips) {
                InitialData initialData = new InitialData();
                initialData.setFootprint(currentFootprint);
                initialData.setType(IP);
                initialData.setValue(ip);
                idDao.saveOrUpdate(initialData);
            }

            for (String domain : domains) {
                InitialData initialData = new InitialData();
                initialData.setFootprint(currentFootprint);
                initialData.setType(DOMAIN);
                initialData.setValue(domain);
                idDao.saveOrUpdate(initialData);
            }

            for (String host : hosts) {
                InitialData initialData = new InitialData();
                initialData.setFootprint(currentFootprint);
                initialData.setType(HOST);
                initialData.setValue(host);
                idDao.saveOrUpdate(initialData);
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.addInitialItems").log(Level.SEVERE, null, e);
        }
    }

    public static List<String> getInitialDataItems(String type, int footprintId) {
        Transaction tx = null;
        List<String> result = new ArrayList<>();
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            InitialDataDao idDao = HibernateDaoFactory.getInstance().getInitialDataDao();
            List<InitialData> list = idDao.findByTypeAndFootprintId(type, footprintId);

            if (list != null && !list.isEmpty()) {
                for (InitialData id : list) {
                    result.add(id.getValue());
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getInitialDataItems").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<String> getInitialDataItems(String type) {
        return getInitialDataItems(type, Globals.getCurrentFootprintId());
    }

    public static List<String> getAllIPSForFootprint(int footprintId) {
        List<String> result = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            IpAddressDao ipDao = HibernateDaoFactory.getInstance().getIpAddressDao();
            List<IpAddress> list = ipDao.findByFootprint(footprintId);

            if (list != null && !list.isEmpty()) {
                for (IpAddress ip : list) {
                    result.add(ip.getAddress());
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getAllIPSForFootprint").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<String> getAllIPSForCurrentFootprint() {
        return getAllIPSForFootprint(Globals.getCurrentFootprintId());
    }

    public static List<String> getDomains(int footprintId) {
        List<String> result = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            DomainDao dao = HibernateDaoFactory.getInstance().getDomainDao();
            List<Domain> list = dao.findByFootprint(footprintId);

            if (list != null && !list.isEmpty()) {
                for (Domain d : list) {
                    result.add(d.getName());
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getDomains").log(Level.SEVERE, null, e);
        }
        return result;
    }

    public static List<String> getDomains() {
        return getDomains(Globals.getCurrentFootprintId());
    }

    public static List<DomainEntry> getDomainData() {
        return getDomainData(Globals.getCurrentFootprintId());
    }

    public static List<DomainEntry> getDomainData(int footprintId) {
        List<DomainEntry> result = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            DomainAttributeDao daDao = HibernateDaoFactory.getInstance().getDomainAttributeDao();
            DomainDao dao = HibernateDaoFactory.getInstance().getDomainDao();
            List<Domain> list = dao.findByFootprint(footprintId);

            if (list != null && !list.isEmpty()) {
                for (Domain d : list) {
                    DomainEntry de = new DomainEntry(d.getName());

                    List<DomainAttribute> daList = daDao.findByDomainNameAndFootprint(d.getName(), footprintId);
                    for (DomainAttribute da : daList) {
                        String key = da.getKey();
                        switch (key) {
                            case SOA_ADMIN:
                                de.setAdmin(da.getValue());
                                break;
                            case SOA_NS:
                                de.setNS(da.getValue());
                                break;
                            case NS:
                                de.addNameServer(da.getValue());
                                break;
                            case MX:
                                de.addMailServer(da.getValue());
                                break;
                        }
                    }
                    result.add(de);
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getDomainData").log(Level.SEVERE, null, e);
        }
        return result;
    }

    public static List<String> getDomainAttributes(String domainName, int footprintId) {
        List<String> result = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            DomainAttributeDao daDao = HibernateDaoFactory.getInstance().getDomainAttributeDao();
            List<DomainAttribute> list = daDao.findByDomainNameAndFootprint(domainName, footprintId);

            if (list != null && !list.isEmpty()) {
                for (DomainAttribute da : list) {
                    result.add(da.getValue());
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getDomainAttributes").log(Level.SEVERE, null, e);
        }
        return result;
    }

    public static List<String> getDomainAttributes(String domainName) {
        return getDomainAttributes(domainName, Globals.getCurrentFootprintId());
    }

    public static List<KeyValue> getDomainAttributesKeyValues(String domainName, int footprintId) {
        List<KeyValue> result = new ArrayList<>();
        Transaction tx = null;

        try {
            tx = HibernateUtil.getSession().beginTransaction();

            DomainAttributeDao daDao = HibernateDaoFactory.getInstance().getDomainAttributeDao();
            List<DomainAttribute> list = daDao.findByDomainNameAndFootprint(domainName, footprintId);

            if (list != null && !list.isEmpty()) {
                for (DomainAttribute da : list) {
                    KeyValue kv = new KeyValue(da.getKey(), da.getValue());
                    result.add(kv);
                }
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getDomainAttributesKeyValues").log(Level.SEVERE, null, e);
        }

        return result;
    }

    public static List<KeyValue> getDomainAttributesKeyValues(String domainName) {
        return getDomainAttributesKeyValues(domainName, Globals.getCurrentFootprintId());
    }

    public static void addDomainAttribute(String domainName, String key, String value) {
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            DomainDao dDao = HibernateDaoFactory.getInstance().getDomainDao();
            Domain domain = dDao.findByName(domainName, Globals.getCurrentFootprintId());

            DomainAttribute domainAttribute = new DomainAttribute();
            domainAttribute.setDomain(domain);
            domainAttribute.setKey(key);
            domainAttribute.setValue(value);

            DomainAttributeDao daDao = HibernateDaoFactory.getInstance().getDomainAttributeDao();
            daDao.saveOrUpdate(domainAttribute);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.addDomainAttribute").log(Level.SEVERE, null, e);
        }
    }

    public static void addHostAttribute(int footprintId, String hostName, String key, String value, String type) {
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            HostDao hDao = HibernateDaoFactory.getInstance().getHostDao();
            Host host = hDao.findByNameAndFootprint(hostName, footprintId);

            HostAttribute hostAttribute = new HostAttribute();
            hostAttribute.setHost(host);
            hostAttribute.setKey(key);
            hostAttribute.setValue(value);
            hostAttribute.setType(type);

            HostAttributeDao haDao = HibernateDaoFactory.getInstance().getHostAttributeDao();
            haDao.saveOrUpdate(hostAttribute);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.addHostAttribute").log(Level.SEVERE, null, e);
        }
    }

    public static void addHostAttribute(String hostName, String key, String value, String type) {
        addHostAttribute(Globals.getCurrentFootprintId(), hostName, key, value, type);
    }

    public static void addPortToIP(String ipAddress, int portNumber, String state,
            String service, String version, String extraInfo, int footprintId) {
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            IpAddressDao ipDao = HibernateDaoFactory.getInstance().getIpAddressDao();
            IpAddress ipa = ipDao.findByAddressAndFootprint(ipAddress, footprintId);

            Port port = new Port();
            port.setIpAddress(ipa);
            port.setPortNumber(portNumber);
            port.setPortState(state);
            port.setServiceName(service);
            port.setVersion(version);
            port.setExtraInfo(extraInfo);

            PortDao pDao = HibernateDaoFactory.getInstance().getPortDao();
            pDao.saveOrUpdate(port);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.addPortToIP").log(Level.SEVERE, null, e);
        }
    }

    public static void addPortToIP(String ipAddress, int port, String state,
            String service, String version, String extraInfo) {
        addPortToIP(ipAddress, port, state, service, version, extraInfo,
                Globals.getCurrentFootprintId());
    }

    public static List<Port> getPortsForIp(String ip, int footprintId) {
        List<Port> ports = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = HibernateUtil.getSession().beginTransaction();

            PortDao pDao = HibernateDaoFactory.getInstance().getPortDao();
            ports = pDao.findPortsByIpAddressAndFootprint(ip, footprintId);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("DataStore.getIpPorts").log(Level.SEVERE, null, e);
        }

        return ports;
    }

    public static List<Port> getPortsForIp(String ip) {
        return DataStore.getPortsForIp(ip, Globals.getCurrentFootprintId());
    }

    public static int getCurrentFootprintId() {
        return Globals.getCurrentFootprintId();
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//    // NOTE: As it stands now, this is an expensive transaction...
//    public static void updateWhoisServerData(List<WhoisServer> servers, List<ServerOption> serverOptions) {
//        Transaction tx = null;
//        try {
//            tx = HibernateUtil.getSession().beginTransaction();
//            
//            ServerOptionDao soDao = HibernateDaoFactory.getInstance().getServerOptionDao();
//            soDao.truncate();
//            
//            WhoisServerDao wsDao = HibernateDaoFactory.getInstance().getWhoisServerDao();
//            wsDao.truncate();
//            
//            for (WhoisServer ws : servers) {
//                wsDao.saveOrUpdate(ws);
//            }
//            
//            for (ServerOption so : serverOptions) {
//                String regex = so.getServer().getRegex();
//                if (regex != null && !regex.isEmpty()) {
//                    WhoisServer ws = wsDao.findByRegex(regex);
//                    if (ws != null) {
//                        so.setServer(ws);
//                        soDao.saveOrUpdate(so);
//                    }
//                }
//            }
//
//            tx.commit();
//        } catch (RuntimeException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            Logger.getLogger("DataStore.setWhoisServer").log(Level.SEVERE, null, e);
//        }
//    }
}
