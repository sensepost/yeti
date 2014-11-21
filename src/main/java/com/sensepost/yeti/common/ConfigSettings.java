package com.sensepost.yeti.common;

import com.sensepost.yeti.persistence.HibernateUtil;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willemm
 */
public class ConfigSettings {

    private static final boolean defaultAlwaysShowConfig = true;
    private static final boolean defaultScriptAlwaysOntopConfig = true;

    private static final int defaultCertThreadCount = 10;
    private static final int defaultCertSocketTimeout = 10000;
    private static final int defalutTldThreadCount = 20;
    private static final int defaultTldRetry = 2;
    private static final int defaultForwardLookupThreadCount = 20;
    private static final int defaultForwardLookupRetryCount = 2;
    private static final int defaultReverseLookupThreadCount = 50;
    private static final String defaultWildcardDomainCheck = "diulukaygeejoekaykseriovs";
    private static final String defaultTLDListLocation = "<Please select location>";
    private static final String defaultForwardLookupLocation = "<Please select location>";
    private static final String defaultGeoIPLocation = "<Please select location>";
    private static final String defaultYetiSpottedLocation = "<Please select location>";
    private static final String defaultFootprintDB = "";

    private static final String CONFIG_FILENAME = "application.properties";
    private static final String SQLITE_FILENAME = "hibernate.sqlite.properties";
    private static final String MYSQL_FILENAME = "hibernate.mysql.properties";

    private static final String DB_DIALECT_PROPERTY = "hibernate.dialect";
    private static final String DB_DRIVER_PROPERTY = "hibernate.connection.driver_class";
    private static final String DB_PASSWORD_PROPERTY = "hibernate.connection.password";
    private static final String DB_CONNECTION_PROPERTY = "hibernate.connection.url";
    private static final String DB_USER_PROPERTY = "hibernate.connection.username";

    private static String workingDir;
    private static String connectionString;

    private static final Properties configProps = new ConfigProperties();
    private static final Properties dbProps = new ConfigProperties();

    public static void initConfig(String currentWorkingDir) throws IOException {
        setCurrentJarDir(currentWorkingDir);
        String confFolder = getConfigFolderLocation();
        try (FileInputStream in = new FileInputStream(confFolder + CONFIG_FILENAME)) {
            configProps.load(in);
        }

        String dbFolder = getDatabaseFolderLocation();
        String sqlPropFilename;
        if (getDBType() == 0) {
            sqlPropFilename = getDatabaseFolderLocation() + SQLITE_FILENAME;
            try (FileInputStream in = new FileInputStream(sqlPropFilename)) {
                dbProps.load(in);
            }
        } else if (getDBType() == 1) {
            sqlPropFilename = getDatabaseFolderLocation() + MYSQL_FILENAME;
            try (FileInputStream in = new FileInputStream(sqlPropFilename)) {
                dbProps.load(in);
            }
        }
    }

    public static void saveChanges() {
        try {
            try (FileOutputStream out = new FileOutputStream(getConfigFolderLocation() + CONFIG_FILENAME)) {
                configProps.store(out, null);
                out.flush();
            }

            String filepath = getDatabaseFolderLocation() + SQLITE_FILENAME;
            if (getDBType() == 0) {
                // Don't change anything
            } else if (getDBType() == 1) {
                filepath = getDatabaseFolderLocation() + MYSQL_FILENAME;
            }

            try (FileOutputStream out = new FileOutputStream(filepath)) {
                dbProps.store(out, null);
                out.flush();
            }

        } catch (IOException ex) {
            Logger.getLogger("configSettings.saveChanges").log(Level.SEVERE, null, ex);
        }
    }

    public static Properties getDatabaseProperties() {
        return dbProps;
    }

    public static String getConfigValue(String key) {
        return configProps.getProperty(key);
    }

    public static String getBruteforceFolderLocation() {
        return getCurrentJarDir() + "/bruteforce/";
    }

    public static String getConfigFolderLocation() {
        return getCurrentJarDir() + "/conf/";
    }

    public static String getDatabaseFolderLocation() {
        return getCurrentJarDir() + "/db/";
    }

    public static String getGeoIpFolderLocation() {
        return getCurrentJarDir() + "/geoipdata/";
    }

    public static String getScriptsFolderLocation() {
        return getCurrentJarDir() + "/scripts/";
    }

    public static String getTldFolderLocation() {
        return getCurrentJarDir() + "/tlddata/";
    }

    public static String getWhoisFolderLocation() {
        return getCurrentJarDir() + "/whois/";
    }

    public static int getCertThreadCount() {
        try {
            return Integer.parseInt(configProps.getProperty("cert.ThreadCount"));
        } catch (NumberFormatException ex) {
            Logger.getLogger("ConfigSettings.getCertThreadCount").log(Level.WARNING, null, ex);
            return defaultCertThreadCount;
        }
    }

    public static void setCertThreadCount(int value) {
        configProps.setProperty("cert.ThreadCount", String.valueOf(value));
    }

    public static int getCertSocketTimeout() {
        try {
            return Integer.parseInt(configProps.getProperty("cert.SocketTimeout"));
        } catch (NumberFormatException ex) {
            Logger.getLogger("ConfigSettings.getCertSocketTimeout").log(Level.WARNING, null, ex);
            return defaultCertSocketTimeout;
        }
    }

    public static void setCertSocketTimeout(int value) {
        configProps.setProperty("cert.SocketTimeout", String.valueOf(value));
    }

    public static String getTldSourceListLocation() {
        try {
            String result = configProps.getProperty("tld.SourceList");
            if (result == null) {
                result = UtilFunctions.fixPath(getTldFolderLocation() + "tld_full.txt");
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger("ConfigSettings.getTldSourceListLocation").log(Level.WARNING, null, ex);
            return defaultTLDListLocation;
        }
    }

    public static void setTldSourceListLocation(String value) {
        configProps.setProperty("tld.SourceList", value);
    }

    public static int getTldThreadCount() {
        try {
            return Integer.parseInt(configProps.getProperty("tld.ThreadCount"));
        } catch (NumberFormatException nfe) {
            Logger.getLogger("ConfigSettings.getTldSourceListLocation").log(Level.WARNING, null, nfe);
            return defalutTldThreadCount;
        }
    }

    public static void setTldThreadCount(int value) {
        configProps.setProperty("tld.ThreadCount", String.valueOf(value));
    }

    public static int getTldRetryCount() {
        try {
            return Integer.parseInt(configProps.getProperty("tld.RetryCount"));
        } catch (NumberFormatException ex) {
            Logger.getLogger("ConfigSettings.getTldRetryCount").log(Level.WARNING, null, ex);
            return defaultTldRetry;
        }
    }

    public static void setTldRetryCount(int value) {
        configProps.setProperty("tld.RetryCount", String.valueOf(value));
    }

    public static String getForwardLookupBruteListLocation() {
        try {
            String result = configProps.getProperty("forwardLookup.BruteLocaction");
            if (result == null) {
                result = getBruteforceFolderLocation();
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger("ConfigSettings.getForwardLookupBruteListLocation").log(Level.WARNING, null, ex);
            return defaultForwardLookupLocation;
        }
    }

    public static String getGeoIPCountryFile() {
        try {
            String result = configProps.getProperty("geoip.country");
            if (result == null) {
                result = getGeoIpFolderLocation() + "GeoIP.dat";
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger("ConfigSettings.getGeoIPCountryFile").log(Level.WARNING, null, ex);
            return defaultGeoIPLocation;
        }
    }

    public static void setgetIPCountryFile(String value) {
        configProps.setProperty("geoip.country", value);
    }

    public static void setForwardLookupBruteListLocation(String value) {
        configProps.setProperty("forwardLookup.BruteLocaction", value);
    }

    public static int getForwardLookupThreadCount() {
        try {
            return Integer.parseInt(configProps.getProperty("forwardLookup.ThreadCount"));
        } catch (NumberFormatException ex) {
            Logger.getLogger("ConfigSettings.getForwardLookupThreadCount").log(Level.WARNING, null, ex);
            return defaultForwardLookupThreadCount;
        }
    }

    public static void setForwardLookupThreadCount(int value) {
        configProps.setProperty("forwardLookup.ThreadCount", String.valueOf(value));
    }

    public static int getForwardLookupRetryCount() {
        try {
            return Integer.parseInt(configProps.getProperty("forwardLookup.RetryCount"));
        } catch (NumberFormatException ex) {
            Logger.getLogger("ConfigSettings.getForwardLookupRetryCount").log(Level.WARNING, null, ex);
            return defaultForwardLookupRetryCount;
        }
    }

    public static void setForwordLookupRetryCount(int value) {
        configProps.setProperty("forwardLookup.RetryCount", String.valueOf(value));
    }

    public static String getWildCardSubdomain() {
        try {
            String result = configProps.getProperty("forwardLookup.wildCardDnsCheckSubdomain");
            if (result == null) {
                result = defaultWildcardDomainCheck;
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger("ConfigSettings.getWildCardSubdomain").log(Level.WARNING, null, ex);
            return defaultWildcardDomainCheck;
        }
    }

    public static void setWildCardSubdomain(String value) {
        configProps.setProperty("forwardLookup.wildCardDnsCheckSubdomain", value);
    }

    public static void setReverseLookupThreadCount(int value) {
        configProps.setProperty("reverseLookup.ThreadCount", String.valueOf(value));
    }

    public static int getReveseLookupThreadCount() {
        try {
            return Integer.parseInt(configProps.getProperty("reverseLookup.ThreadCount"));
        } catch (NumberFormatException ex) {
            Logger.getLogger("ConfigSettings.getReveseLookupThreadCount").log(Level.WARNING, null, ex);
            return defaultReverseLookupThreadCount;
        }
    }

    public static void setYetiFoundHostsFile(String value) {
        configProps.setProperty("yeti.FoundHostFile", value);
    }

    public static String getYetiFoundHostsFile() {
        try {
            String result = configProps.getProperty("yeti.FoundHostFile");
            if (result == null) {
                result = "";
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger("ConfigSettings.getYetiFoundHostsFile").log(Level.WARNING, null, ex);
            return defaultYetiSpottedLocation;
        }
    }

    public static String getGeoIPCityFile() {
        try {
            String result = configProps.getProperty("geoip.ip2city");
            if (result == null) {
                result = "";
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger("ConfigSettings.getGeoIPCityFile").log(Level.WARNING, null, ex);
            return defaultGeoIPLocation;
        }
    }

    public static void setgetIPCiryFile(String value) {
        configProps.setProperty("geoip.ip2city", value);
    }

    public static boolean getAlwaysShowConfigAtStartup() {
        try {
            boolean result = Boolean.parseBoolean(configProps.getProperty("app.showconfigonstartup"));
            return result;
        } catch (Exception ex) {
            Logger.getLogger("ConfigSettings.getAlwaysShowConfigAtStartup").log(Level.WARNING, null, ex);
            return defaultAlwaysShowConfig;
        }
    }

    public static void setAlwaysShowConfigAtStartup(boolean value) {
        configProps.setProperty("app.showconfigonstartup", String.valueOf(value));
    }

    public static boolean getScriptAlwaysOntop() {
        try {
            boolean result = Boolean.parseBoolean(configProps.getProperty("app.scriptAlwaysOntop"));
            return result;
        } catch (Exception ex) {
            Logger.getLogger("ConfigSettings.getScriptAlwaysOntop").log(Level.WARNING, null, ex);
            return defaultScriptAlwaysOntopConfig;
        }
    }

    public static void setScriptAlwaysOntop(boolean value) {
        configProps.setProperty("app.scriptAlwaysOntop", String.valueOf(value));
    }

    public static String getPluginDir() {
        try {
            String result = configProps.getProperty("plugins.location");
            if (result == null) {
                result = getScriptsFolderLocation();
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger("ConfigSettings.getPluginDir").log(Level.WARNING, null, ex);
            return defaultFootprintDB;
        }
    }

    public static void setPluginDir(String value) {
        configProps.setProperty("plugins.location", value);
    }

    public static String getPluginConfig(String key) {
        try {
            return configProps.getProperty(key);
        } catch (Exception ex) {
            Logger.getLogger("ConfigSettings.getPluginConfig").log(Level.WARNING, null, ex);
            return "";
        }
    }

    public static void setPluginConfig(String key, String value) {
        configProps.setProperty(key, value);
        saveChanges();
    }

    public static void setCurrentJarDir(String jarDir) {
        workingDir = jarDir;
    }

    public static String getCurrentJarDir() {
        return workingDir;
    }

    public static String getWhoisTldServerFile() {
        try {
            String result = configProps.getProperty("whois.tldServerFile");
            if (result == null) {
                result = "";
            }
            return result;
        } catch (Exception ex) {
            Logger.getLogger("getWhoisTldServerFile").log(Level.WARNING, null, ex);
            return defaultGeoIPLocation;
        }
    }

    public static void setWhoisTldServerFile(String value) {
        configProps.setProperty("whois.tldServerFile", value);
    }

    // 0 - SQLite
    // 1 - MySQL
    public static int getDBType() {
        try {
            if (configProps.getProperty("database.type") != null) {
                return Integer.parseInt(configProps.getProperty("database.type"));
            }
        } catch (NumberFormatException ex) {
            Logger.getLogger("ConfigSettings.getDBType").log(Level.WARNING, null, ex);
        }
        return 0;
    }

    public static void setDBType(int type) {
        int currentDbType = getDBType();
        if (currentDbType != type) {
            configProps.setProperty("database.type", Integer.toString(type));
            try {
                String sqlPropFilename;
                if (type == 0) {
                    sqlPropFilename = getDatabaseFolderLocation() + SQLITE_FILENAME;
                    try (FileInputStream in = new FileInputStream(sqlPropFilename)) {
                        dbProps.load(in);
                    }
                } else if (type == 1) {
                    sqlPropFilename = getDatabaseFolderLocation() + MYSQL_FILENAME;
                    try (FileInputStream in = new FileInputStream(sqlPropFilename)) {
                        dbProps.load(in);
                    }
                }
                HibernateUtil.resetSession();
            } catch (IOException e) {
                Logger.getLogger("setDBType").log(Level.SEVERE, null, e);
            }
        }
    }

    public static String getDBUrl() {
        String dbConnectionStr = dbProps.getProperty(DB_CONNECTION_PROPERTY);
        String[] strArr = dbConnectionStr.split(":");
        String result = strArr[2];
        if (strArr.length > 3) {
            for (int i = 3; i < strArr.length; i++) {
                result += ":" + strArr[i];
            }
        }
        if (getDBType() == 1) {
            result = result.substring(2);
        }
        return result;
    }

    public static void setDBUrl(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:");
        if (getDBType() == 0) {
            sb.append("sqlite:");
        } else if (getDBType() == 1) {
            sb.append("mysql://");
        }
        sb.append(url);

        dbProps.setProperty(DB_CONNECTION_PROPERTY, sb.toString());
    }

    public static String getDBUser() {
        return dbProps.getProperty(DB_USER_PROPERTY);
    }

    public static void setDBUser(String user) {
        dbProps.setProperty(DB_USER_PROPERTY, user);
    }

    public static String getDBPassword() {
        return dbProps.getProperty(DB_PASSWORD_PROPERTY);
    }

    public static void setDBPassword(String passwd) {
        dbProps.setProperty(DB_PASSWORD_PROPERTY, passwd);
    }
}
