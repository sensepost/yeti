package com.sensepost.yeti.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import com.sensepost.yeti.common.UtilFunctions;
import com.sensepost.yeti.controllers.ForwardLookupController;
import com.sensepost.yeti.models.ForwardLookupModel;
import com.sensepost.yeti.reports.ResultExport;
import com.sensepost.yeti.persistence.DataStore;

public class ForwardLookupDisplayResults extends javax.swing.JPanel implements DisplayResultIFace {

    private ForwardLookupController parent = null;

    public ForwardLookupDisplayResults(ForwardLookupController parent) {
        initComponents();
        this.parent = parent;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmForwardLookups = new javax.swing.JPopupMenu();
        miLaunchInBrowser = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        miSelectAll = new javax.swing.JMenuItem();
        btnUnselectAll = new javax.swing.JMenuItem();
        miUnselectNS = new javax.swing.JMenuItem();
        miUnselectMX = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        miUnselectIp = new javax.swing.JMenuItem();
        miSelectIp = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miUnselectHost = new javax.swing.JMenuItem();
        miSelectHost = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnTrimUnchecked = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnPersist = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResults = new javax.swing.JTable();

        pmForwardLookups.setName("pmForwardLookups"); // NOI18N

        miLaunchInBrowser.setText("Open browser"); // NOI18N
        miLaunchInBrowser.setName("miLaunchInBrowser"); // NOI18N
        miLaunchInBrowser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miLaunchInBrowserActionPerformed(evt);
            }
        });
        pmForwardLookups.add(miLaunchInBrowser);

        jSeparator2.setName("jSeparator2"); // NOI18N
        pmForwardLookups.add(jSeparator2);

        miSelectAll.setText("Select all"); // NOI18N
        miSelectAll.setName("miSelectAll"); // NOI18N
        miSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSelectAllActionPerformed(evt);
            }
        });
        pmForwardLookups.add(miSelectAll);

        btnUnselectAll.setText("Unselect all"); // NOI18N
        btnUnselectAll.setName("btnUnselectAll"); // NOI18N
        btnUnselectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnselectAllActionPerformed(evt);
            }
        });
        pmForwardLookups.add(btnUnselectAll);

        miUnselectNS.setText("Unselect NS records"); // NOI18N
        miUnselectNS.setName("miUnselectNS"); // NOI18N
        miUnselectNS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUnselectNSActionPerformed(evt);
            }
        });
        pmForwardLookups.add(miUnselectNS);

        miUnselectMX.setText("Unselect MX record"); // NOI18N
        miUnselectMX.setName("miUnselectMX"); // NOI18N
        miUnselectMX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUnselectMXActionPerformed(evt);
            }
        });
        pmForwardLookups.add(miUnselectMX);

        jSeparator3.setName("jSeparator3"); // NOI18N
        pmForwardLookups.add(jSeparator3);

        miUnselectIp.setText("Unselect all from this IP"); // NOI18N
        miUnselectIp.setName("miUnselectIP"); // NOI18N
        miUnselectIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUnselectIpActionPerformed(evt);
            }
        });
        pmForwardLookups.add(miUnselectIp);

        miSelectIp.setText("Select all from this IP"); // NOI18N
        miSelectIp.setName("miSelectIp"); // NOI18N
        miSelectIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSelectIpActionPerformed(evt);
            }
        });
        pmForwardLookups.add(miSelectIp);

        jSeparator1.setName("jSeparator1"); // NOI18N
        pmForwardLookups.add(jSeparator1);

        miUnselectHost.setText("Unselect all from this Host"); // NOI18N
        miUnselectHost.setName("miUnselectHost"); // NOI18N
        miUnselectHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUnselectHostActionPerformed(evt);
            }
        });
        pmForwardLookups.add(miUnselectHost);

        miSelectHost.setText("Select all from this Host"); // NOI18N
        miSelectHost.setName("miSelectHost"); // NOI18N
        miSelectHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSelectHostActionPerformed(evt);
            }
        });
        pmForwardLookups.add(miSelectHost);

        setName("Form"); // NOI18N

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        btnTrimUnchecked.setText("Clear unselected"); // NOI18N
        btnTrimUnchecked.setFocusable(false);
        btnTrimUnchecked.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrimUnchecked.setName("btnTrimUnchecked"); // NOI18N
        btnTrimUnchecked.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTrimUnchecked.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrimUncheckedMouseClicked(evt);
            }
        });
        jToolBar1.add(btnTrimUnchecked);

        btnExport.setText("Export"); // NOI18N
        btnExport.setFocusable(false);
        btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExport.setName("btnExport"); // NOI18N
        btnExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExportMouseClicked(evt);
            }
        });
        jToolBar1.add(btnExport);

        btnPersist.setText("Save"); // NOI18N
        btnPersist.setFocusable(false);
        btnPersist.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPersist.setName("btnPersist"); // NOI18N
        btnPersist.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPersist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPersistMouseClicked(evt);
            }
        });
        jToolBar1.add(btnPersist);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tblResults.setAutoCreateRowSorter(true);
        tblResults.setComponentPopupMenu(pmForwardLookups);
        tblResults.setName("tblResults"); // NOI18N
        tblResults.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResultsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblResults);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMouseClicked
        String filename = UtilFunctions.saveFile(".xls");
        if (!filename.isEmpty()) {
            try {
                ResultExport.ExportForwardLookupsToXLS(filename, ((ForwardLookupModel) tblResults.getModel()).getData());
            } catch (IOException ex) {
                Logger.getLogger("forwardLookupDisplayResults.btnExportMouseClicked").log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnExportMouseClicked

    public void saveData() {
        ForwardLookupModel model = (ForwardLookupModel) tblResults.getModel();
        DataStore.addForwardLookupHosts(model.getData());
    }

    private void btnPersistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPersistMouseClicked
        saveData();
    }//GEN-LAST:event_btnPersistMouseClicked

    private void btnTrimUncheckedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrimUncheckedMouseClicked
        ForwardLookupModel model = (ForwardLookupModel) tblResults.getModel();
        for (int idx = model.getRowCount() - 1; idx >= 0; idx--) {
            if (!(Boolean) model.getValueAt(idx, 4)) {
                model.deleteRow(idx);
            }
        }
    }//GEN-LAST:event_btnTrimUncheckedMouseClicked

    private void miUnselectIpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUnselectIpActionPerformed
        String domain = ((ForwardLookupModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 3).toString();
        toggleBasedOnIP(domain, false);
    }//GEN-LAST:event_miUnselectIpActionPerformed

    private void miSelectIpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSelectIpActionPerformed
        String domain = ((ForwardLookupModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 3).toString();
        toggleBasedOnIP(domain, true);
    }//GEN-LAST:event_miSelectIpActionPerformed

    private void miUnselectHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUnselectHostActionPerformed
        String domain = ((ForwardLookupModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 1).toString();
        toggleBasedOnHost(domain, false);
    }//GEN-LAST:event_miUnselectHostActionPerformed

    private void miSelectHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSelectHostActionPerformed
        String domain = ((ForwardLookupModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 1).toString();
        toggleBasedOnHost(domain, true);
    }//GEN-LAST:event_miSelectHostActionPerformed

    private void tblResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResultsMouseClicked

}//GEN-LAST:event_tblResultsMouseClicked

    private void miLaunchInBrowserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miLaunchInBrowserActionPerformed
        String siteAddress = "http://" + ((ForwardLookupModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 1).toString();
        UtilFunctions.launchBrowser(siteAddress);
    }//GEN-LAST:event_miLaunchInBrowserActionPerformed

    private void miSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSelectAllActionPerformed
        ForwardLookupModel model = (ForwardLookupModel) tblResults.getModel();
        for (int idx = model.getRowCount() - 1; idx >= 0; idx--) {
            model.setValueAt(true, idx, 4);
        }
    }//GEN-LAST:event_miSelectAllActionPerformed

    private void btnUnselectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnselectAllActionPerformed
        ForwardLookupModel model = (ForwardLookupModel) tblResults.getModel();
        for (int idx = model.getRowCount() - 1; idx >= 0; idx--) {
            model.setValueAt(false, idx, 4);
        }
    }//GEN-LAST:event_btnUnselectAllActionPerformed

    private void miUnselectNSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUnselectNSActionPerformed
        ForwardLookupModel model = (ForwardLookupModel) tblResults.getModel();
        for (int idx = model.getRowCount() - 1; idx >= 0; idx--) {
            if (model.getValueAt(idx, 2).toString().compareTo("NS") == 0) {
                model.setValueAt(false, idx, 4);
            }
        }
    }//GEN-LAST:event_miUnselectNSActionPerformed

    private void miUnselectMXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUnselectMXActionPerformed
        ForwardLookupModel model = (ForwardLookupModel) tblResults.getModel();
        for (int idx = model.getRowCount() - 1; idx >= 0; idx--) {
            if (model.getValueAt(idx, 2).toString().compareTo("MX") == 0) {
                model.setValueAt(false, idx, 4);
            }
        }
    }//GEN-LAST:event_miUnselectMXActionPerformed

    private void toggleBasedOnIP(String ip, boolean value) {
        ForwardLookupModel model = (ForwardLookupModel) tblResults.getModel();
        for (int idx = 0; idx < model.getRowCount(); idx++) {
            String cIp = model.getValueAt(idx, 3).toString();
            if (ip.compareTo(cIp) == 0) {
                model.setValueAt(value, idx, 4);
            }
        }
    }

    private void toggleBasedOnHost(String domain, boolean value) {
        ForwardLookupModel model = (ForwardLookupModel) tblResults.getModel();
        for (int idx = 0; idx < model.getRowCount(); idx++) {
            String cIp = model.getValueAt(idx, 1).toString();
            if (domain.compareTo(cIp) == 0) {
                model.setValueAt(value, idx, 4);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnPersist;
    private javax.swing.JButton btnTrimUnchecked;
    private javax.swing.JMenuItem btnUnselectAll;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem miLaunchInBrowser;
    private javax.swing.JMenuItem miSelectAll;
    private javax.swing.JMenuItem miSelectHost;
    private javax.swing.JMenuItem miSelectIp;
    private javax.swing.JMenuItem miUnselectHost;
    private javax.swing.JMenuItem miUnselectIp;
    private javax.swing.JMenuItem miUnselectMX;
    private javax.swing.JMenuItem miUnselectNS;
    private javax.swing.JPopupMenu pmForwardLookups;
    public javax.swing.JTable tblResults;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setModel(DefaultTableModel model) {
        tblResults.setModel((ForwardLookupModel) model);
    }

    @Override
    public DefaultTableModel getModel() {
        return (DefaultTableModel) tblResults.getModel();
    }

}
