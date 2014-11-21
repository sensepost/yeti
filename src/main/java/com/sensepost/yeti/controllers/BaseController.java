package com.sensepost.yeti.controllers;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.sensepost.yeti.gui.DisplayResultIFace;

/**
 *
 * @author willemmouton
 */
public abstract class BaseController implements ControllerIFace {

    private RunningJob job;
    private String name = "BaseController";
    private WorkflowController workflowController;
    private DisplayResultIFace displayResults;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private int totalRequests = 0;
    private int requestsCompleted = 0;
    private boolean cancelled;
    private boolean running;

    public BaseController() {
    }

    protected abstract void addResult(Object result);

    protected abstract void addResults(ArrayList<Object> results);

    @Override
    public void pauseJob() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean startJob() {
        running = true;
        return true;
    }

    @Override
    public synchronized void setCancelled(boolean value) {
        cancelled = value;
    }

    public boolean getCancelledValue() {
        return cancelled;
    }

    @Override
    public void unPauseJob() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void finishProgress() {
        workflowController.finishProgress(this);
    }

    protected synchronized void updateProgress(int inc) {
        requestsCompleted += inc;
        workflowController.updateProgress(this);
    }

    protected synchronized void cancelProgress() {
        workflowController.cancellProgress(job);

    }

    @Override
    public void viewJob() {
        //this.workflowController.setJobDetail(this.displayResults);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setJob(RunningJob job) {
        this.job = job;
    }

    public RunningJob getJob() {
        return job;
    }

    public DisplayResultIFace getDisplayResults() {
        return displayResults;
    }

    public void setDisplayResults(DisplayResultIFace displayResults) {
        this.displayResults = displayResults;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkflowController getWorkflowController() {
        return workflowController;
    }

    public void setWorkflowController(WorkflowController parent) {
        this.workflowController = parent;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }

    public int getRequestsCompleted() {
        return requestsCompleted;
    }

    public void setRequestsCompleted(int requestsCompleted) {
        this.requestsCompleted = requestsCompleted;
    }

    public boolean isRunning() {
        return running;
    }

    public void setIsRunning(boolean running) {
        this.running = running;
    }
}
