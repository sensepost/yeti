package com.sensepost.yeti.plugins;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.script.ScriptException;
import javax.swing.JMenuItem;

/**
 *
 * @author willemmouton
 */
public class ExportPluginManager extends BasePluginManager {

    protected String args = null;

    public ExportPluginManager() {
        super();
        this.setLocation("/exportPlugins");
    }

    @Override
    public void pluginMenuEvent(ActionEvent evt) {
        if (evt.getSource().getClass() == JMenuItem.class) {
            try {
                String scriptName = (((JMenuItem) evt.getSource()).getName());
                this.executeScript(scriptName);
            } catch (FileNotFoundException | ScriptException ex) {
                Logger.getLogger(AttributePluginManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setArgs(String value) {
        this.args = value;
    }

    public String getArgs() {
        return this.args;
    }
}
