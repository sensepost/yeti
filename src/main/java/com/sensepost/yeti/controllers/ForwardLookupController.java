package com.sensepost.yeti.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.Log;
import com.sensepost.yeti.gui.DisplayResultIFace;
import com.sensepost.yeti.models.ForwardLookupModel;
import com.sensepost.yeti.results.ForwardLookupResult;
import com.sensepost.yeti.gui.ForwardLookupDisplayResults;
import com.sensepost.yeti.gui.ForwardLookupInit;
import com.sensepost.yeti.helpers.ForwardLookupHelper;
import com.sensepost.yeti.persistence.DataStore;

import org.xbill.DNS.TextParseException;

/**
 *
 * @author willemm
 */
public class ForwardLookupController extends BaseController {

    private List<BruteforceWorker> bfWorkers = new ArrayList<>();
    private List<ForwardLookupWorker> flWorkers = new ArrayList<>();
    private final List<String> bruteforceList = new ArrayList<>();
    private final Queue<String> targetDomains = new LinkedList<>();
    private final Queue<String> foundDomains = new LinkedList<>();

    private boolean checkARecord = true;
    private boolean checkAAAARecord = true;
    private boolean checkCNameRecord = true;
//    private boolean checkTXTRecord = true;

    private int numFLLookups;

    private final ExecutorService flExecutor;

    public ForwardLookupController(WorkflowController parent, ForwardLookupModel dataModel) {
        setName("ForwardLookup");
        setWorkflowController(parent);

        DisplayResultIFace displayResults = new ForwardLookupDisplayResults(this);
        displayResults.setModel((DefaultTableModel) dataModel);
        setDisplayResults(displayResults);

        flExecutor = Executors.newFixedThreadPool(ConfigSettings.getForwardLookupThreadCount());
    }

    @Override
    public boolean startJob() {
        ForwardLookupInit frmInit = new ForwardLookupInit();
        frmInit.setVisible(true);

        if (!frmInit.cancelled) {
            targetDomains.addAll(frmInit.getDomains());
            numFLLookups = targetDomains.size();

            try {
                bruteforceList.addAll(frmInit.getBruteforceName());
            } catch (IOException ex) {
                Logger.getLogger(ForwardLookupController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            checkARecord = frmInit.getCheckARecord();
            checkAAAARecord = frmInit.getCheckAAAARecord();
            checkCNameRecord = frmInit.getCheckCNameRecord();
//            checkTXTRecord = frmInit.getCheckTXTRecord();

            setTotalRequests(targetDomains.size() + (bruteforceList.size() * targetDomains.size()));

            for (String domainName : targetDomains) {
                ForwardLookupWorker flRunner = new ForwardLookupWorker(domainName);
                flExecutor.execute(flRunner);
                flWorkers.add(flRunner);
            }
        }
        return !frmInit.cancelled;
    }

    private void startBruteForcing() {
        String domain = foundDomains.poll();
        while (domain != null) {
            for (String prefix : bruteforceList) {
                BruteforceWorker bfRunner = new BruteforceWorker(prefix, domain);
                flExecutor.execute(bfRunner);
                bfWorkers.add(bfRunner);
            }
            domain = foundDomains.poll();
        }
    }

    // Called from ForwardLookupWorker.done()
    private synchronized void preworkDone(ForwardLookupWorker worker) {
        if (getCancelledValue()) {
            flExecutor.shutdown();
            flWorkers = null;
            cancelProgress();
            Log.debug("ForwardLookup: job cancelled");
        }

        Log.debug("Done removing worker: waiting for " + String.valueOf(flWorkers.size()) + " more workers.");
        updateProgress(1);
        flWorkers.remove(worker);
        numFLLookups--;

        if (numFLLookups == 0) {
            Log.debug("ForwardLookupController.preworkDone: Prework done, starting brute forcing.");
            startBruteForcing();
        }
    }

    private synchronized void bruteforcerWorkDone(BruteforceWorker worker) {
        if (getCancelledValue()) {
            flExecutor.shutdown();
            bfWorkers = null;
            cancelProgress();
            Log.debug("ForwardLookup: job cancelled");
        }

        Log.debug("forwardLookupController.workDone: Worker done, cleaning up");
        updateProgress(1);
        bfWorkers.remove(worker);

        if (bfWorkers.isEmpty()) {
            Log.debug("forwardLookupController.workDone: Job completed");
            finishProgress();
        }
    }

    @Override
    protected void addResult(Object result) {
        ((ForwardLookupModel) (getDisplayResults().getModel())).addRow(result);
    }

    @Override
    protected void addResults(ArrayList<Object> results) {
        Iterator i = results.iterator();
        while (i.hasNext()) {
            ((ForwardLookupModel) (getDisplayResults().getModel())).addRow(i.hasNext());
        }
    }

    private void addDomainAttribute(String domainName, String attrKey, String attrValue) {
        ArrayList<Object> data = ((ForwardLookupModel) (getDisplayResults().getModel())).getData();
        for (Object obj : data) {
            ForwardLookupResult flr = (ForwardLookupResult) obj;
            if (flr.getDomainName() != null && flr.getDomainName().equals(domainName)) {
                Map<String, String> attrs = flr.getAttributes();
                attrs.put(attrKey, attrValue);
                flr.setAttributes(attrs);
            }
        }
    }

    @Override
    public void saveData() {
        DataStore.addForwardLookupHosts(((ForwardLookupModel) (getDisplayResults().getModel())).getData());
    }

    class ForwardLookupWorker extends SwingWorker<Object, Object> {

        private final String domainName;

        public ForwardLookupWorker(String domainName) {
            this.domainName = domainName;
        }

        @Override
        protected String doInBackground() throws Exception {
            if (getCancelledValue()) {
                Log.debug("ForwardLookup: Job cancelled, quitting");
            } else {
                // Attempt zone transfer
                boolean ztTransferable = false;
                try {
                    List<ForwardLookupResult> nameServers = ForwardLookupHelper.getNSRecord(domainName);
                    if (nameServers != null) {
                        addResults(nameServers);
                    }

                    List<ForwardLookupResult> ztResults = ForwardLookupHelper.attemptZoneTransfer(domainName, nameServers);
                    if (ztResults != null) {
                        addResults(ztResults);
                        ztTransferable = !(ztResults.isEmpty());
                    }
                } catch (TextParseException ex) {
                    Log.debug("Exception EX1: [" + domainName + "] : " + ex.toString());
                }

                // Attempt to get mailservers
                List<ForwardLookupResult> mailServers = ForwardLookupHelper.getMXRecord(domainName);
                if (mailServers != null) {
                    addResults(mailServers);
                }

                if (ForwardLookupHelper.isWildCard(domainName)) {
                    Log.debug("*." + domainName + " Found");
                    addDomainAttribute(domainName, DataStore.CONFIG, DataStore.ASTERIX_RECORD);
                    return "Done";
                }

                // Check A, AAAA and CNAME records
                List<ForwardLookupResult> otherServers = ForwardLookupHelper.checkAllRecords(domainName, domainName);
                if (otherServers != null) {
                    addResults(otherServers);
                }

                if (!ztTransferable) {
                    foundDomains.add(domainName);
                } else {
                    addDomainAttribute(domainName, DataStore.CONFIG, DataStore.ZONE_TRANSFER);
                    updateProgress(bruteforceList.size());
                }
            }

            return "Done";
        }

        @Override
        protected void done() {
            preworkDone(this);
        }

        private void addResults(List<ForwardLookupResult> items) {
            for (ForwardLookupResult flr : items) {
                addResult(flr);
            }
        }
    }

    class BruteforceWorker extends SwingWorker<Object, Object> {

        private final String prefix;
        private final String domain;

        public BruteforceWorker(String prefix, String domain) {
            this.prefix = prefix;
            this.domain = domain;
        }

        @Override
        protected String doInBackground() throws Exception {
            if (getCancelledValue()) {
                Log.debug("ForwardLookup: Job cancelled, quitting");
                return "Done";
            }

            int retryCount = ConfigSettings.getForwardLookupRetryCount();
            List<ForwardLookupResult> entries;
            String hostName = prefix + "." + domain;

            try {
                if (checkARecord) {
                    for (int x = 0; x < retryCount; x++) {
                        entries = ForwardLookupHelper.getARecord(hostName, domain);
                        if (entries != null) {
                            Log.debug("forwardLookupController.getSubdomainExists: Found: " + hostName);
                            addResults(entries);
                            break;
                        }
                    }
                }
                if (checkAAAARecord) {
                    for (int x = 0; x < retryCount; x++) {
                        entries = ForwardLookupHelper.getAAAARecord(hostName, domain);
                        if (entries != null) {
                            Log.debug("forwardLookupController.getSubdomainExists: Found: " + hostName);
                            addResults(entries);
                            break;
                        }
                    }
                }
                if (checkCNameRecord) {
                    for (int x = 0; x < retryCount; x++) {
                        entries = ForwardLookupHelper.getCNameRecord(hostName, domain);
                        if (entries != null) {
                            Log.debug("forwardLookupController.getSubdomainExists: Found: " + hostName);
                            addResults(entries);
                            break;
                        }
                    }
                }
            } catch (TextParseException ex) {
                Logger.getLogger("forwardLookupController.attemptZoneTransfer").log(Level.SEVERE, null, ex);
            }

            return "done";
        }

        @SuppressWarnings("rawtypes")
        private void addResults(List<ForwardLookupResult> items) {
            for (ForwardLookupResult result : items) {
                addResult(result);
            }
        }

        @Override
        protected void done() {
            bruteforcerWorkDone(this);
        }
    }
}
