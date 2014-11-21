package com.sensepost.yeti.controllers;

/**
 *
 * @author Johan Snyman
 */
public class RunningJob {

    private final BaseController baseController;
    private final WorkflowController workflowController;
    private final int id;
    private String progress = "Running (0%)";

    public RunningJob(WorkflowController workflowController, BaseController baseController, int jobId) {
        this.workflowController = workflowController;
        this.baseController = baseController;
        id = jobId;
    }

    public boolean startJob() {
        return baseController.startJob();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        if (baseController.getClass() == TldController.class) {
            return "Domain expand";
        }
        if (baseController.getClass() == ForwardLookupController.class) {
            return "Forward lookup";
        }
        if (baseController.getClass() == CertController.class) {
            return "Certificate ext.";
        }
        if (baseController.getClass() == ReverseLookupController.class) {
            return "Reverse lookup";
        }
        if (baseController.getClass() == YileController.class) {
            return "WebSpider scan";
        }
        if (baseController.getClass() == CollectorController.class) {
            return "CollecterScan";
        }
        return "Unknown";
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String value) {
        progress = value;
    }

    public void viewJob() {
        workflowController.getParent().setJobDetail(this.baseController.getDisplayResults());
    }

    public void cancelJob() {
        baseController.setCancelled(true);
    }

    public void saveJob() {
        baseController.saveData();
    }
}
