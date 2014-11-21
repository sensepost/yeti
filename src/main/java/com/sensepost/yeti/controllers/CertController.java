package com.sensepost.yeti.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import com.sensepost.yeti.models.CertModel;
import com.sensepost.yeti.results.CertResult;
import com.sensepost.yeti.helpers.CertHelper;
import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.Log;
import com.sensepost.yeti.common.NetworkTools;
import com.sensepost.yeti.gui.CertDisplayResults;
import com.sensepost.yeti.gui.CertInit;
import com.sensepost.yeti.gui.DisplayResultIFace;
import com.sensepost.yeti.persistence.DataStore;

/**
 *
 * @author willemm
 */
public class CertController extends BaseController {

    private ArrayList<certWorker> workers = null;
    private final Queue<String> targets = new LinkedList<>();

    public CertController(WorkflowController parent, CertModel dataModel) {
        setName("CertScraping");
        setWorkflowController(parent);

        DisplayResultIFace displayResults = new CertDisplayResults(this);
        displayResults.setModel((DefaultTableModel) dataModel);
        setDisplayResults(displayResults);

        workers = new ArrayList<>();
    }

    public synchronized void addTargets(ArrayList<String> targets) {
        targets.addAll(targets);
    }

    private synchronized void addResult(CertResult result) {
        ((CertModel) (getDisplayResults().getModel())).addRow(result);
    }

    private synchronized String popTarget() {
        return (String) targets.poll();
    }

    @Override
    public boolean startJob() {
        CertInit frmInit = new CertInit();
        frmInit.setVisible(true);
        if (!frmInit.cancelled) {
            targets.addAll(frmInit.getTargets());
            setTotalRequests(targets.size());

            for (int idx = 0; idx <= ConfigSettings.getCertThreadCount(); idx++) {
                String target = this.popTarget();
                if (target == null) {
                    break;
                }
                certWorker certRunner = new certWorker(target);
                getExecutor().execute(certRunner);
                workers.add(certRunner);
            }
        }
        return !frmInit.cancelled;
    }

    private synchronized void workerDone(certWorker worker) {
        int currentWorkerIdx = workers.indexOf(worker);
        workers.remove(currentWorkerIdx);
        Log.debug("certExtraction: Done removing worker");
    }

    @Override
    protected void addResult(Object result) {
        ((CertModel) (getDisplayResults().getModel())).addRow(result);
    }

    @Override
    protected void addResults(ArrayList<Object> results) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveData() {
        DataStore.addCertHosts(((CertModel) (getDisplayResults().getModel())).getData());
    }

    class certWorker extends SwingWorker<Object, Object> {

        private String targets = "";

        public certWorker(String target) {
            super();
            targets = target;
        }

        @Override
        protected String doInBackground() throws Exception {
            while (!targets.isEmpty() && (targets != null)) {
                if (getCancelledValue()) {
                    Log.debug("certExtraction: Job cancelled, stopping");
                    break;
                }
                try {
                    List<String[]> results = CertHelper.checkCert(targets);
                    for (String[] strArr : results) {
                        CertResult cResult = new CertResult(strArr[0]);
                        cResult.setHostName(strArr[1]);
                        cResult.setDomainName(NetworkTools.getDomainFromHost(cResult.getHostName()));
                        addResult(cResult);
                        Log.debug("Found: " + cResult.getHostName() + " on: " + cResult.getIpAddress());
                    }
                } catch (IOException ex) {
                    Log.debug("Exception: [" + this.targets + "] : " + ex.toString());
                }
                updateProgress(1);
                targets = popTarget();
            }
            return "Done";
        }

        @Override
        protected void done() {
            workerDone(this);
        }
    }
}
