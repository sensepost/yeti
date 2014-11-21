package com.sensepost.yeti.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Class written purely to overwrite the default properties that Yeti
 * ships with, to have the folder where it is installed in the properties.
 * @author Johan Snyman
 */
public class ConfigProperties extends Properties {

    protected static final String INSTALL_FOLDER = "<INSTALL_FOLDER>";
    
    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        super.load(inStream);
        Set<String> propertyNames = stringPropertyNames();
        for (String propertyName : propertyNames) {
            String value = getProperty(propertyName);
            if (value.contains(INSTALL_FOLDER)) {
                setProperty(propertyName, value.replace(INSTALL_FOLDER, ConfigSettings.getCurrentJarDir()));
            }
        }
    }
}
