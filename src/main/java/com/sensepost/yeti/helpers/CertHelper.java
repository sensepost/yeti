package com.sensepost.yeti.helpers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.YetiUtils;
import com.sensepost.yeti.common.Log;

/**
 *
 * @author willemm
 */
public class CertHelper {

    private static Certificate[] getCertFromHost(String hostName) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        Log.debug(String.format("Getting cert: %s", hostName));
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        SocketFactory factory = sc.getSocketFactory();

        SSLSocket httpsCon = (SSLSocket) factory.createSocket();
        httpsCon.setSoTimeout(ConfigSettings.getCertSocketTimeout());
        httpsCon.connect(new InetSocketAddress(hostName, 443), ConfigSettings.getCertSocketTimeout());
        httpsCon.startHandshake();

        Certificate[] resultCert = httpsCon.getSession().getPeerCertificates();

        return resultCert;
    }

    public static List<String[]> checkCert(String hostIP) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        List<String[]> result = new ArrayList();

        Certificate[] certs = CertHelper.getCertFromHost(hostIP);
        Certificate cert1 = certs[0];

        X509Certificate cert = (X509Certificate) cert1;
        Log.debug("SubjectDN: " + cert.getSubjectDN().getName());
        Log.debug("IssuerDN : " + cert.getIssuerDN().getName());

        // Extract the SSL subject alt names
        try {
            Collection<List<?>> altNames = cert.getSubjectAlternativeNames();
            if (altNames != null) {
                for (List<?> list : altNames) {
                    String[] strArr = new String[2];
                    strArr[0] = hostIP;
                    strArr[1] = (String) list.get(1);
                    result.add(strArr);
                }
            }
        } catch (CertificateParsingException e) {
            Logger.getLogger("CertHelper").log(Level.SEVERE, "Certificate parsing failed: ", e);
        }

        // Extract the SSL common name
        Principal subjectPrincipal = cert.getSubjectX500Principal();
        if (subjectPrincipal != null && subjectPrincipal.getName() != null) {
            Map<String, String> certDetail = YetiUtils.hastTableFromString(
                    subjectPrincipal.getName());

            String[] strArr = new String[2];
            strArr[0] = hostIP;
            strArr[1] = certDetail.get("CN");
            result.add(strArr);
        }

        return result;
    }

    private class CertChecker implements X509TrustManager {

        private final X509TrustManager defaultTM;

        public CertChecker() throws GeneralSecurityException {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);
            defaultTM = (X509TrustManager) tmf.getTrustManagers()[0];
        }

        @Override
        public void checkServerTrusted(X509Certificate[] certs, String authType) {
            if (defaultTM != null) {
                try {
                    defaultTM.checkServerTrusted(certs, authType);
                    Log.debug("Certificate valid");
                } catch (CertificateException ex) {
                    Logger.getLogger("CertHelper").log(Level.SEVERE, "Certificate invalid: ", ex);
                }
            }
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
