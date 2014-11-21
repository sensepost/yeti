package com.sensepost.yeti.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sensepost.yeti.persistence.DataStore;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public class NetworkTools {

    public static List<String> getIpFromHost(String hostname) throws TextParseException {
        List<String> result = new ArrayList<>();
        Record[] recs = new Lookup(hostname, Type.A).run();
        if (recs != null) {
            if (recs.length > 0) {
                for (Record rec : recs) {
                    String ipAddress = ((ARecord) rec).getAddress().toString();
                    result.add(ipAddress.replace("/", ""));
                }
            }
        }
        return result;
    }

    public static String getDomainFromHost(String host) {
        String tmpHost = host;
        if (host.isEmpty()) {
            return "";
        }
        while (true) {
            try {
                if (!tmpHost.startsWith("www.")) {
                    Record[] recs = new Lookup(tmpHost, Type.SOA).run();
                    if (recs != null) {
                        return tmpHost;
                    }
                }
                if (tmpHost.contains(".")) {
                    tmpHost = tmpHost.split("\\.", 2)[1];
                } else {
                    break;
                }
            } catch (TextParseException ex) {
                Logger.getLogger("networkTools.getDomainFromHost").log(Level.SEVERE, null, ex);
                break;
            }
        }
        return "";
    }

    public static String getHTMLFromUrl(String url) {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        String response = "";
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                response = method.getResponseBodyAsString();
            }
        } catch (IOException e) {
            Logger.getLogger("networkTools.getHTMLFromUrl").log(Level.SEVERE, null, e);
        } finally {
            method.releaseConnection();
        }
        return response;
    }

    public static String getRootTermFromDomain(String domainName) {
        String tmp = domainName.split("\\.")[0];
        return tmp;
    }

    public static String getRelativeHostNameFromHost(String hostName) {
        String result = hostName.split("\\.")[0];
        return result;
    }

    public static String getHostNameWhoisResult(String term, String tld, boolean useCache) {
        if (tld.charAt(0) != '.') {
            tld = "." + tld;
        }
        String result;
        String domainName = term + tld;

        // Check if cache should be used and if there is a value stored in the cache.
        if (useCache) {
            result = DataStore.getWhoisCache(term + "." + tld);
            if ((result != null) && (!result.isEmpty())) {
                return result;
            }
        }

        try {
            // First try the whois server defined for the tld in the mapping file
            String server = getWhoisServerFromProperties(tld);
            result = getLiveWhoisResult(server, domainName);
            String altServer = getRedirectWhoisServerInResult(result);
            if (altServer != null && !"".equals(altServer)) {
                result = getLiveWhoisResult(altServer, domainName);
            }

            // If no map could be retrieved, try the fallback whois server
            if ("".equals(result)) {
                server = "whois.internic.net";
                result = getLiveWhoisResult(server, domainName);
            }

            if (!"".equals(result)) {
                DataStore.setWhoisCache(term + "." + tld, result);
                return result;
            }

        } catch (IOException ioe) {
            Logger.getLogger("networkTools.getHTMLFromUrl").log(Level.SEVERE, null, ioe);
        }

        return "Could not retrieve Who-Is information";
    }

    private static String getLiveWhoisResult(String serverToQuery, String domainToQuery) throws IOException {
        if (serverToQuery == null || "".equals(serverToQuery)) {
            return "No server defined to request WhoIs information from.";
        }

        StringBuilder result = new StringBuilder();

        try (Socket socket = new Socket(serverToQuery, 43)) {
            PrintWriter out;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                out = new PrintWriter(socket.getOutputStream(), true);
                out.print(domainToQuery + "\r\n");
                out.flush();
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line).append(System.lineSeparator());
                }
            }
            out.close();
        } catch (java.net.ConnectException ce) {
            Logger.getLogger("networkTools.getLiveWhoisResult").log(Level.SEVERE,
                    "Connection Exception  when connecting to: " + serverToQuery
                    + " and to domain to query: " + domainToQuery, ce);
        }
        return result.toString();
    }

    private static String getWhoisServerFromProperties(String tld) throws IOException {
        // Create the mapping from the file defined
        Map<String, String> map = new HashMap<>();

        int numDots = 0;
        for (int i = 0; i < tld.length(); i++) {
            if (tld.charAt(i) == '.') {
                numDots++;
            }
        }

        int pos = 0;
        String server;
        if (numDots > 2) {
            int dotsToIgnore = numDots - 2;
            for (int i = 0; i <= dotsToIgnore; i++) {
                pos = tld.indexOf(".", pos + 1);
            }
            numDots = 2;
        }

        if (numDots == 2) {
            tld = tld.substring(pos);
            server = map.get(tld);
            if (server != null) {
                return server;
            }
            pos = tld.lastIndexOf(".");
        }

        pos = tld.indexOf(".", pos);
        tld = tld.substring(pos);

        String filename = ConfigSettings.getWhoisTldServerFile();
        Path tldWhoisFile = Paths.get(filename);

        List<String> lines = Files.readAllLines(tldWhoisFile, Charset.defaultCharset());
        for (String line : lines) {
            line = line.trim();
            String[] strArr = line.split(",");
            map.put(strArr[0], strArr[1]);
        }

        return map.get(tld);
    }

    private static String getRedirectWhoisServerInResult(String whoisResult) {
        String whoisServer = "";
        Pattern redirectPattern = Pattern.compile("whois server:\\s(.*)\\s");
        Matcher matcher = redirectPattern.matcher(whoisResult.toLowerCase());
        while (matcher.find()) {
            whoisServer = matcher.group(1);
        }
        return whoisServer;
    }
}
