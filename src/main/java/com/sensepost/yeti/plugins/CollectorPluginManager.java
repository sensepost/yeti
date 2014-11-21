package com.sensepost.yeti.plugins;

import java.awt.event.ActionEvent;

/**
 *
 * @author willem
 */
public class CollectorPluginManager extends BasePluginManager {

    protected String args = null;

    public CollectorPluginManager() {
        super();
        this.setLocation("/collectorPlugins");
    }

    @Override
    public void pluginMenuEvent(ActionEvent evt) {

    }

    public void setArgs(String value) {
        this.args = value;
    }

    public String getArgs() {
        return this.args;
    }

}
