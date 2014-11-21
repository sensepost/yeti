package com.sensepost.yeti.plugins;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import javax.swing.JMenuItem;

public class ReportPluginManager extends BasePluginManager {

    public ReportPluginManager() {
        super();
        this.setLocation("/reportPlugins");
    }

    @Override
    public void pluginMenuEvent(ActionEvent evt) {
        if (evt.getSource().getClass() == JMenuItem.class) {
            try {
                String scriptName = (((JMenuItem) evt.getSource()).getName());
                this.executeScript(scriptName);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReportPluginManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ScriptException ex) {
                Logger.getLogger(ReportPluginManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
