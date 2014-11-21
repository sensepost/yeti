package com.sensepost.yeti.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.sensepost.yeti.models.TldModel;
import com.sensepost.yeti.results.DomainResult;
import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.Log;
import com.sensepost.yeti.common.NetworkTools;
import com.sensepost.yeti.gui.DisplayResultIFace;
import com.sensepost.yeti.gui.TLDInit;
import com.sensepost.yeti.gui.TldDisplayResults;
import com.sensepost.yeti.helpers.TldExpandHelper;
import com.sensepost.yeti.persistence.DataStore;
import org.xbill.DNS.CNAMERecord;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SOARecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

/**
 *
 * @author willemm
 */
public class TldController extends BaseController {

    private List<String> tlds;
    private final List<TldWorker> workers;
    private final Queue<String> urls = new LinkedList<>();
    private final int retryCount = ConfigSettings.getTldRetryCount();

    private final ExecutorService tldExecutor;

    public TldController(WorkflowController parent, TldModel dataModel) {
        setName("TldExpand");
        setWorkflowController(parent);

        DisplayResultIFace displayResults = new TldDisplayResults();
        displayResults.setModel((DefaultTableModel) dataModel);
        setDisplayResults(displayResults);

        workers = new ArrayList<>();
        tldExecutor = Executors.newFixedThreadPool(ConfigSettings.getTldThreadCount());
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this
     * queue is empty.
     *
     * @return Head of the domains queue
     */
    private synchronized String popTarget() {
        return (String) urls.poll();
    }

    @Override
    public boolean startJob() {
        TLDInit frmInit = new TLDInit();
        frmInit.setVisible(true);

        if (!frmInit.cancelled) {
            try {
                List<String> rootNameList = frmInit.getRootTerms();
                String tldFileLocation = frmInit.getTLDLocation();            // the tld list file's text
                tlds = TldExpandHelper.getTldList(tldFileLocation);
                // Get all the URLs to be tested 
                for (String rootName : rootNameList) {
                    for (String tld : tlds) {
                        urls.add(rootName + tld);
                    }
                }

                setRequestsCompleted(0);
                setTotalRequests(urls.size());

                String domain = popTarget();
                while (domain != null && !domain.isEmpty()) {
                    if (getCancelledValue()) {
                        break;
                    }

                    TldWorker tldExpandRunner = new TldWorker(domain);
                    tldExecutor.execute(tldExpandRunner);
                    workers.add(tldExpandRunner);

                    domain = popTarget();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TldController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TldController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return !frmInit.cancelled;
    }

    private synchronized void workerDone(TldWorker worker) {
        workers.remove(worker);
        updateProgress(1);
        if (workers.isEmpty()) {
            if (getCancelledValue()) {
                cancelProgress();
            }
            finishProgress();
        }
    }

    @Override
    protected void addResult(Object result) {
        ((TldModel) (getDisplayResults().getModel())).addRow(result);
    }

    @Override
    protected void addResults(ArrayList<Object> results) {
        for (Object obj : results) {
            ((TldModel) (getDisplayResults().getModel())).addRow(obj);
        }
    }

    @Override
    public void saveData() {
        DataStore.addDomains(((TldModel) (getDisplayResults().getModel())).getData());
    }

    class TldWorker extends SwingWorker<Object, Object> {

        private final String domain;

        public TldWorker(String domain) {
            this.domain = domain;
        }

        @Override
        protected String doInBackground() {
            try {
                expandDomain(domain);
            } catch (TextParseException ex) {
                Logger.getLogger(TldController.class.getName() + ":TldWorker -> doInBackground").log(Level.SEVERE, null, ex);
            }

            return "Done";
        }

        public void expandDomain(String domainToCheck) throws TextParseException {
            String domainName = null;

            // Check for domain name alias - CNAME
            Record[] recs = new Lookup(domainToCheck, Type.CNAME).run();
            if (recs != null && recs.length != 0) {
                domainName = ((CNAMERecord) recs[0]).getName().canonicalize().toString(true);
                Log.debug("Found: " + domainName + "CNAME rec: " + domainName);
            }

            // Now get the SOA record that would signify a domain exists
            recs = new Lookup(domainToCheck, Type.SOA).run();
            for (int idx = 0; idx < retryCount; idx++) {
                if (recs != null) {
                    if (domainName == null) {
                        domainName = ((SOARecord) recs[0]).getName().canonicalize().toString(true);
                        Log.debug("Found: " + domainName + " SOA rec: " + domainName);
                    }

                    DomainResult newDomain = new DomainResult(domainName);
                    newDomain.setNameServer(((SOARecord) recs[0]).getHost().toString(true));
                    newDomain.setAdminName(((SOARecord) recs[0]).getAdmin().toString(true));
                    String name = domainToCheck.split("\\.", 2)[0];
                    String tld = domainToCheck.split("\\.", 2)[1];
                    newDomain.setRegistrant(NetworkTools.getHostNameWhoisResult(name, tld, true));
                    Map<String, String> attrs = new HashMap<>();
                    attrs.put(DataStore.DNS_RECORD, DataStore.SOA);
                    newDomain.setAttributes(attrs);
                    addResult(newDomain);
                    break;
                }
            }
        }

        @Override
        protected void done() {
            Log.debug("Done with domain expand for: " + domain);
            workerDone(this);
        }
    }
}
