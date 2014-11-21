package com.sensepost.yeti.controllers;

public interface ControllerIFace {

    void pauseJob();

    void viewJob();

    boolean startJob();

    void unPauseJob();

    void saveData();

    void setCancelled(boolean value);
}
