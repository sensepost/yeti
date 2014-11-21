package com.sensepost.yeti.gui;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author willemmouton
 */
public interface DisplayResultIFace {

    DefaultTableModel getModel();

    void setModel(DefaultTableModel model);
}
