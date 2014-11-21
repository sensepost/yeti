package com.sensepost.yeti.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;

import com.sensepost.yeti.FrmMain;
import com.sensepost.yeti.gui.StartFootprintInit;
import com.sensepost.yeti.models.CertModel;
import com.sensepost.yeti.models.CollectorModel;
import com.sensepost.yeti.models.ForwardLookupModel;
import com.sensepost.yeti.models.JobModel;
import com.sensepost.yeti.models.ReverseLookupModel;
import com.sensepost.yeti.models.TldModel;
import com.sensepost.yeti.models.YileModel;

/**
 *
 * @author willemmouton
 */
public class WorkflowController {

    private int jobCounter = 0;
    private final StartFootprintInit frmStart = new StartFootprintInit();

    public enum jobTypes {

        TLDEXPAND, FORWORDLOOKUP, REVERSELOOKUP, CERTEXTRACTION, SPIDER, BINGSITE, BINGIP, SCRIPT,
    };

    private final FrmMain parent;
    private final JobModel model;

    public WorkflowController(FrmMain parent) {
        model = new JobModel();
        this.parent = parent;
    }

    public JobModel getModel() {
        return model;
    }

    protected int getNextJobId() {
        return ++jobCounter;
    }

    public synchronized void startTLDExpand() {
        TldController cTld = new TldController(this, new TldModel());
        RunningJob job = new RunningJob(this, cTld, this.getNextJobId());
        cTld.setJob(job);
        if (job.startJob()) {
            model.addRow(job);
        }
    }

    public void startForwardLookup() {
        ForwardLookupController cFld = new ForwardLookupController(this, new ForwardLookupModel());
        RunningJob job = new RunningJob(this, cFld, this.getNextJobId());
        cFld.setJob(job);
        if (job.startJob()) {
            model.addRow(job);
        }
    }

    public void startCertExtraction() {
        CertController cCert = new CertController(this, new CertModel());
        RunningJob job = new RunningJob(this, cCert, this.getNextJobId());
        cCert.setJob(job);
        if (job.startJob()) {
            model.addRow(job);
        }
    }

    public void startReverseLookup() {
        ReverseLookupController cReverse = new ReverseLookupController(this, new ReverseLookupModel());
        RunningJob job = new RunningJob(this, cReverse, this.getNextJobId());
        cReverse.setJob(job);
        if (job.startJob()) {
            model.addRow(job);
        }
    }

    public void startWebsiteSpider() {
        YileController cSpider = new YileController(this, new YileModel());
        RunningJob job = new RunningJob(this, cSpider, this.getNextJobId());
        cSpider.setJob(job);
        if (job.startJob()) {
            model.addRow(job);
        }
    }

    public void startCollector(String scriptName) {
        CollectorController cCollector = new CollectorController(this, new CollectorModel(), scriptName);
        RunningJob job = new RunningJob(this, cCollector, this.getNextJobId());
        cCollector.setJob(job);
        if (job.startJob()) {
            model.addRow(job);
        }
    }

    public void startFootprint() throws FileNotFoundException, IOException {
        this.frmStart.setVisible(true);
    }

    public synchronized void finishProgress(BaseController baseController) {
        model.setValueAt("Done (100%)", model.getIndexOf(baseController.getJob()), 2);
    }

    public synchronized void updateProgress(BaseController baseController) {
        String value;
        if (baseController.getTotalRequests() > baseController.getRequestsCompleted()) {
            Double t = (double) baseController.getTotalRequests();
            Double d = (double) baseController.getRequestsCompleted();

            double perc = (d / t);
            NumberFormat percentFormatter;

            percentFormatter = NumberFormat.getPercentInstance();

            if (!baseController.getCancelledValue()) {
                value = String.format("Running (%s)", percentFormatter.format(perc));
            } else {
                value = String.format("Cancelling (%s)", percentFormatter.format(perc));
            }

        } else {
            value = "Done (100%)";
        }

        model.setValueAt(value, model.getIndexOf(baseController.getJob()), 2);
    }

    public void cancelJob(int idx) {
        RunningJob j = (RunningJob) model.getObjectAt(idx);
        j.cancelJob();
    }

    public void saveJob(int idx) {
        RunningJob j = (RunningJob) model.getObjectAt(idx);
        j.saveJob();
    }

    public void saveAllJobs() {
        for (Object j : model.getData()) {
            ((RunningJob) j).saveJob();
        }
    }

    public void removeJob(int idx) {
        RunningJob j = (RunningJob) model.getObjectAt(idx);
        j.cancelJob();
        ((JobModel) model).removeItem(j);
    }

    public void removeAllJobs() {
        model.clear();
    }

    public void cancellProgress(Object caller) {
        model.setValueAt("Cancelled", model.getIndexOf(caller), 2);
    }

    protected FrmMain getParent() {
        return parent;
    }
}
