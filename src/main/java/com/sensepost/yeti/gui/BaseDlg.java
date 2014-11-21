package com.sensepost.yeti.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

/**
 *
 * @author willemmouton
 */
public abstract class BaseDlg extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    public boolean cancelled = true;

    public BaseDlg() {
        this.setLocationRelativeTo(null);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
