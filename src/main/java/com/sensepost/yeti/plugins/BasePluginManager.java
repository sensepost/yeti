package com.sensepost.yeti.plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JMenuItem;
import javax.swing.SwingWorker;

import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.UtilFunctions;
import com.sensepost.yeti.gui.ScriptOutput;

public abstract class BasePluginManager {

    protected String scriptLocation = "";
    protected DataAPI dataApi = new DataAPI();
    protected UtilAPI utilApi = new UtilAPI();
    protected ScriptOutput scriptIO = new ScriptOutput();
    protected ExecutorService executor = Executors.newCachedThreadPool();
    protected ScriptEngineManager factory = new ScriptEngineManager();

    public ArrayList<JMenuItem> getPlugins() {
        ArrayList<JMenuItem> result = new ArrayList<>();

        for (String filename : UtilFunctions.filesFromDir(scriptLocation)) {
            JMenuItem miPlugin = new JMenuItem();
            miPlugin.setText(filename);
            miPlugin.setName(filename);
            miPlugin.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    pluginMenuEvent(evt);
                }
            });
            result.add(miPlugin);
        }
        return result;
    }

    public void setLocation(String value) {
        scriptLocation = ConfigSettings.getPluginDir() + value;
    }

    public synchronized void addOutput(String message) {
        scriptIO.addDebug(message);
    }

    public void executeScript(String scriptName) throws FileNotFoundException, javax.script.ScriptException {
        ScriptWorker scriptRunner = new ScriptWorker(scriptName, this);
        executor.execute(scriptRunner);
    }

    public void scriptDone() {
        scriptIO.setCanClose();
    }

    // Data section, testing this with bsh
    public String dataAPITest() {
        return "test";
    }

    public abstract void pluginMenuEvent(java.awt.event.ActionEvent evt);

    @SuppressWarnings("rawtypes")
    class ScriptWorker extends SwingWorker {

        private final BasePluginManager parent;
        private final BufferedReader input;
        private final ScriptEngine engine;

        public ScriptWorker(String scriptName, BasePluginManager parent) throws FileNotFoundException {
            this.parent = parent;
            File file = new File(scriptLocation + File.separatorChar + scriptName);
            engine = factory.getEngineByExtension(UtilFunctions.getFileExtension(scriptName));
            input = new BufferedReader(new FileReader(file));
            engine.put("controller", parent);
            engine.put("dataApi", dataApi);
            engine.put("utilApi", utilApi);
        }

        @Override
        protected String doInBackground() throws javax.script.ScriptException {
            scriptIO.reset();
            scriptIO.setVisible(true);
            engine.eval(input);
            return "Done";
        }

        @Override
        protected void done() {
            scriptDone();
        }
    }
}
