package com.sensepost.yeti.controllers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.NetworkTools;
import com.sensepost.yeti.dns.DNS;
import com.sensepost.yeti.gui.DisplayResultIFace;
import com.sensepost.yeti.gui.ReverseLookupDisplayResults;
import com.sensepost.yeti.gui.ReverseLookupInit;
import com.sensepost.yeti.models.ReverseLookupModel;
import com.sensepost.yeti.results.ReverseLookupResult;
import com.sensepost.yeti.persistence.DataStore;
import java.net.InetAddress;

import org.xbill.DNS.TextParseException;

/**
 *
 * @author willemmouton
 */
public class ReverseLookupController extends BaseController {

    private ArrayList<ReverseLookupWorker> workers = null;
    private final Queue<String> ipAddresses = new LinkedList<>();

    public ReverseLookupController(WorkflowController parent, ReverseLookupModel dataModel) {
        setName("ReverseLookup");
        setWorkflowController(parent);

        DisplayResultIFace displayResults = new ReverseLookupDisplayResults();
        displayResults.setModel((DefaultTableModel) dataModel);
        setDisplayResults(displayResults);

        workers = new ArrayList<>();
    }

    public synchronized void addIPs(ArrayList<String> rootNames) {
        ipAddresses.addAll(rootNames);
    }

    private synchronized String popTarget() {
        return (String) ipAddresses.poll();
    }

    @Override
    public boolean startJob() {
        ReverseLookupInit frmInit = new ReverseLookupInit();
        frmInit.setVisible(true);
        if (!frmInit.cancelled) {
            ipAddresses.addAll(frmInit.getTargets());
            setTotalRequests(ipAddresses.size());
            try {
                for (int idx = 0; idx <= ConfigSettings.getReveseLookupThreadCount(); idx++) {
                    String ip = popTarget();
                    if (ip == null) {
                        break;
                    }
                    ReverseLookupWorker forwardLookupRunner = new ReverseLookupWorker(ip);
                    getExecutor().execute(forwardLookupRunner);
                    workers.add(forwardLookupRunner);
                }
            } catch (Exception ex) {
                Logger.getLogger("reverseLookupController.startRevercingIps").log(Level.SEVERE, null, ex);
            }
        }
        return !frmInit.cancelled;
    }

    private synchronized void workerDone(ReverseLookupWorker worker) {
        int currentWorkerIdx = workers.indexOf(worker);
        workers.remove(workers.get(currentWorkerIdx));
    }

    @Override
    protected void addResult(Object result) {
        ReverseLookupResult rlr = (ReverseLookupResult) result;
        if (rlr.getDomainName() != null && !rlr.getDomainName().isEmpty()) {
            if (DNS.getSOARecord(rlr.getDomainName()) != null) {
                ((ReverseLookupModel) (getDisplayResults().getModel())).addRow(result);
            }
        }
    }

    @Override
    protected void addResults(ArrayList<Object> results) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveData() {
        DataStore.addReverseHosts(((ReverseLookupModel) (getDisplayResults().getModel())).getData());
    }

    class ReverseLookupWorker extends SwingWorker<Object, Object> {

        private String ip = "";

        public ReverseLookupWorker(String ip) {
            this.ip = ip;
        }

        @Override
        protected String doInBackground() throws Exception {
            while (!ip.isEmpty() && (ip != null)) {
                ReverseLookupResult entry = null;
                try {
                    String name = InetAddress.getByName(ip).getHostName();
                    if (ip.compareTo(name) < 0) {
                        entry = new ReverseLookupResult(ip);
                        entry.setHostName(name);
                        entry.setDomainName(NetworkTools.getDomainFromHost(name));
                    }
                } catch (UnknownHostException ex) {
                    Logger.getLogger("reverseLookupController.getReverseName").log(Level.SEVERE, null, ex);
                }

                if (entry != null) {
                    addResult(entry);
                }
                updateProgress(1);
                ip = popTarget();
            }
            return "Done";
        }

        @Override
        protected void done() {
            workerDone(this);
        }
    }
}
