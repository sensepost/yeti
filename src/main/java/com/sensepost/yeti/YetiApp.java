package com.sensepost.yeti;

import java.awt.EventQueue;
import javax.swing.UnsupportedLookAndFeelException;
import com.sensepost.yeti.common.ConfigSettings;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YetiApp {

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
            //javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ConfigSettings.initConfig(System.getProperty("user.dir"));
                } catch (IOException ex) {
                    Logger.getLogger("FrmMain.FrmMain").log(Level.SEVERE, null, ex);
                }
                
                FrmMain app = new FrmMain();
                app.setSize(1024, 768);
                app.setLocationRelativeTo(null);
                app.setVisible(true);
                app.resetScreens();
            }
        });
    }
}
