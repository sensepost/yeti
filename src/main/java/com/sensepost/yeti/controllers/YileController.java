package com.sensepost.yeti.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.sensepost.yeti.common.ConfigSettings;
import com.sensepost.yeti.common.Log;
import com.sensepost.yeti.common.NetworkTools;
import com.sensepost.yeti.gui.DisplayResultIFace;
import com.sensepost.yeti.gui.YileDisplayResults;
import com.sensepost.yeti.gui.YileInit;
import com.sensepost.yeti.models.YileModel;
import com.sensepost.yeti.results.YileResult;
import com.sensepost.yeti.spider.UrlProcessor;
import com.sensepost.yeti.spider.Spider;
import org.xbill.DNS.TextParseException;

public class YileController extends BaseController {

    private final ArrayList<yileWorker> workers;
    private final Queue<String> hosts = new LinkedList<>();

    public YileController(WorkflowController parent, YileModel dataModel) {
        setName("yileScan");

        setWorkflowController(parent);

        DisplayResultIFace displayResults = new YileDisplayResults();
        displayResults.setModel((DefaultTableModel) dataModel);
        setDisplayResults(displayResults);

        workers = new ArrayList<>();
    }

    public synchronized void addHosts(ArrayList<String> hostNames) {
        this.hosts.addAll(hostNames);
    }

    private synchronized String popTarget() {
        return (String) this.hosts.poll();
    }

    @Override
    public boolean startJob() {
        YileInit frmInit = new YileInit();
        frmInit.setVisible(true);
        if (!frmInit.cancelled) {
            this.hosts.addAll(frmInit.getHosts());
            setTotalRequests(hosts.size());

            try {
                for (int idx = 0; idx <= ConfigSettings.getCertSocketTimeout(); idx++) {
                    yileWorker yileRunner = new yileWorker();
                    getExecutor().execute(yileRunner);
                    this.workers.add(yileRunner);
                }
            } catch (Exception ex) {
                Logger.getLogger("yileController.startYiling").log(Level.SEVERE, null, ex);
            }
        }
        return !frmInit.cancelled;
    }

    @Override
    protected void addResult(Object result) {
        ((YileModel) (getDisplayResults().getModel())).addRow(result);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void addResults(ArrayList<Object> results) {
        Iterator i = results.iterator();
        while (i.hasNext()) {
            ((YileModel) (getDisplayResults().getModel())).addRow(i.hasNext());
        }
    }

    @Override
    public void saveData() {
//        DataStore.addYileHosts(((YileModel) (getDisplayResults().getModel())).getData());
    }

    class yileWorker extends SwingWorker<Object, Object> implements UrlProcessor {

        @Override
        protected Object doInBackground() throws Exception {
            Spider s = new Spider(this);
            while (!hosts.isEmpty()) {
                updateProgress(0);
                String host = popTarget();
                if (!host.isEmpty()) {
                    s.addURL(new URL(host));
                    s.begin();
                    updateProgress(1);
                }
            }
            return null;
        }

        @Override
        public void addHostFromUrl(String url, String baseUrl) {
            try {
                URL aUrl = new URL(url);
                URL sUrl = new URL(baseUrl);
                String domain = NetworkTools.getDomainFromHost(aUrl.getHost());
                YileResult res = new YileResult(domain);
                res.setHostName(aUrl.getHost());
                res.setSource(sUrl.getHost());
                res.setUrl(url);
                try {
                    String name = domain.split("\\.", 2)[0];
                    String tld = domain.split("\\.", 2)[1];
                    res.setRegistrant(NetworkTools.getHostNameWhoisResult(name, tld, true));
                    res.setIPAddress(NetworkTools.getIpFromHost(aUrl.getHost()).get(0));
                } catch (TextParseException tpe) {
                    Logger.getLogger("yileController.yileWorker.addHostFromUrl").log(Level.SEVERE, null, tpe);
                }

                addResult(res);

            } catch (MalformedURLException ex) {
                Logger.getLogger("yileController.yileWorker.addHostFromUrl").log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void handleUrlError(URL url, String baseUrl) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void addEmailAddress(String emailAddress) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void onDebug(String message) {
            Log.debug(message);
        }

        @Override
        public void onError(String errorMessage) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
