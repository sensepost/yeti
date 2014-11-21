package com.sensepost.yeti.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.NetworkTools;
import com.sensepost.yeti.common.UtilFunctions;
import com.sensepost.yeti.helpers.ForwardLookupHelper;

/**
 *
 * @author willemmouton
 */
public class UtilAPI {

    public String getRelativeName(String hostName) {
        return NetworkTools.getRelativeHostNameFromHost(hostName);
    }

    public String showInputBox(String text) {
        return JOptionPane.showInputDialog(null, text);
    }

    public String saveFileDialog(String extentions) {
        return UtilFunctions.saveFile(extentions);
    }

    public void launchBrowser(String url) {
        UtilFunctions.launchBrowser(url);
    }

    public void launchBrowser(String scheme, String host, String path, String payload) {
        try {
            URI uri = new URI(scheme, host, path, payload, null);
            this.launchBrowser(uri.toASCIIString());
        } catch (URISyntaxException ex) {
            Logger.getLogger(UtilAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getConfigValue(String key) {
        String value = ConfigSettings.getPluginConfig("plugin." + key);
        if (value == null) {
            return "";
        }
        return value;
    }

    public void setConfigValue(String key, String value) {
        ConfigSettings.setPluginConfig("plugin." + key, value);
    }

    public String getTempDir() {
        return System.getProperty("java.io.tmpdir");
    }

    public String httpGETRequest(String request) {
        return NetworkTools.getHTMLFromUrl(request);
    }

    public String execCmd(String cmd) {
        StringBuilder output = new StringBuilder();

        Process p;
        try {
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        } catch (IOException | InterruptedException e) {
            Logger.getLogger(ForwardLookupHelper.class.getName()).log(Level.SEVERE, null, e);
        }

        return output.toString();
    }

    public String getDomainFromHostname(String hostname) {
        return NetworkTools.getDomainFromHost(hostname);
    }
}
