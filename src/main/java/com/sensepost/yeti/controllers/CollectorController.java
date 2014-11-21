package com.sensepost.yeti.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.UtilFunctions;
import com.sensepost.yeti.gui.DisplayResultIFace;
import com.sensepost.yeti.models.CollectorModel;
import com.sensepost.yeti.gui.CollectorDisplayResults;
import com.sensepost.yeti.gui.CollectorInit;
import com.sensepost.yeti.gui.ScriptOutput;
import com.sensepost.yeti.plugins.DataAPI;
import com.sensepost.yeti.plugins.UtilAPI;

/**
 *
 * @author willem
 */
public class CollectorController extends BaseController {

    protected String scriptLocation = "";
    protected DataAPI dataApi = new DataAPI();
    protected UtilAPI utilApi = new UtilAPI();
    protected ScriptOutput scriptIO = new ScriptOutput();
    protected ExecutorService executor = Executors.newCachedThreadPool();
    protected ScriptEngineManager factory = new ScriptEngineManager();

    private BufferedReader input = null;
    private ScriptEngine engine = null;
    private String scriptName = "";
    private ArrayList<String> targets = null;

    public CollectorController(WorkflowController parent, CollectorModel dataModel, String scriptName) {
        setName("Collector");
        setWorkflowController(parent);

        DisplayResultIFace displayResults = new CollectorDisplayResults(this);
        displayResults.setModel((DefaultTableModel) dataModel);
        setDisplayResults(displayResults);
        this.scriptName = scriptName;
        this.scriptLocation = ConfigSettings.getPluginDir() + "/collectorPlugins";
    }

    public void setScript(String scriptName) {
        this.scriptName = scriptName;
    }

    @Override
    public synchronized void addResult(Object result) {
        ((CollectorModel) (getDisplayResults().getModel())).addRow(result);
    }

    @Override
    protected void addResults(ArrayList<Object> results) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void debug(String s) {
        System.out.println(s);
    }

    private void initScript(String scriptName) {
        File file = new File(scriptLocation + File.separatorChar + scriptName);
    }

    public void scriptDone() {
        scriptIO.setCanClose();
    }

    @Override
    public boolean startJob() {
        try {
            File file = new File(scriptLocation + File.separatorChar + scriptName);
            engine = factory.getEngineByExtension(UtilFunctions.getFileExtension(scriptName));
            input = new BufferedReader(new FileReader(file));

            if (engine != null) {
                engine.put("execute", false);
                String info = (String) engine.eval(input);
                input.close();
                input = new BufferedReader(new FileReader(file));
                CollectorInit frmInit = new CollectorInit(null, true, info);
                frmInit.setVisible(true);
                if (!frmInit.cancelled) {
                    this.targets = frmInit.getInput();
                    setTotalRequests(targets.size());
                    scriptWorker scriptRunner = new scriptWorker(scriptName, input, this);
                    executor.execute(scriptRunner);
                }
                return !frmInit.cancelled;
            }
        } catch (ScriptException | IOException ex) {
            Logger.getLogger(CollectorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    @SuppressWarnings("rawtypes")
    class scriptWorker extends SwingWorker {

        private final CollectorController parent;
        private ScriptEngine engine = null;

        public scriptWorker(String scriptName, BufferedReader script, CollectorController parent) throws FileNotFoundException {
            super();
            this.parent = parent;
            engine = factory.getEngineByExtension(UtilFunctions.getFileExtension(scriptName));

            if (engine != null) {
                engine.put("controller", parent);
                engine.put("dataApi", dataApi);
                engine.put("utilApi", utilApi);
                engine.put("input", targets);
                engine.put("execute", true);
            } else {

            }
        }

        @Override
        protected String doInBackground() throws javax.script.ScriptException {
            scriptIO.reset();
            //scriptIO.setVisible(true);
            engine.eval(input);
            getWorkflowController().finishProgress(this.parent);
            return "Done";
        }

        @Override
        protected void done() {
            scriptDone();
        }
    }
}
