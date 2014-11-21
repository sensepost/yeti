package com.sensepost.yeti.gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import com.sensepost.yeti.common.UtilFunctions;
import com.sensepost.yeti.models.TldModel;
import com.sensepost.yeti.reports.ResultExport;
import com.sensepost.yeti.persistence.DataStore;

/**
 *
 * @author willemm
 */
public class TldDisplayResults extends javax.swing.JPanel implements DisplayResultIFace {

    public TldDisplayResults() {
        initComponents();
        pnlWhois.setContentType("text/plain");
    }

    @Override
    public DefaultTableModel getModel() {
        return (DefaultTableModel) tblResults.getModel();
    }

    @Override
    public void setModel(DefaultTableModel model) {
        tblResults.setModel((TldModel) model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmTldMenu = new javax.swing.JPopupMenu();
        miWhois = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miSelectAllFromNS = new javax.swing.JMenuItem();
        miSelectAllFromAdmin = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        miUnselectAllFromNS = new javax.swing.JMenuItem();
        miUnselectAllFromAdmin = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnTrimUnchecked = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        tbtnSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        cbxCheckAll = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtFilterItems = new javax.swing.JTextArea();
        btnFilter = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResults = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        pnlWhois = new javax.swing.JEditorPane();

        pmTldMenu.setName("pmTldMenu"); // NOI18N

        miWhois.setText("Whois"); // NOI18N
        miWhois.setName("miWhois"); // NOI18N
        miWhois.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miWhoisActionPerformed(evt);
            }
        });
        pmTldMenu.add(miWhois);

        jSeparator1.setName("jSeparator1"); // NOI18N
        pmTldMenu.add(jSeparator1);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/sensepost/yeti/gui/Bundle"); // NOI18N
        miSelectAllFromNS.setText(bundle.getString("TldDisplayResults.miSelectAllFromNS.text")); // NOI18N
        miSelectAllFromNS.setName("miSelectAllFromNS"); // NOI18N
        miSelectAllFromNS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSelectAllFromNSActionPerformed(evt);
            }
        });
        pmTldMenu.add(miSelectAllFromNS);

        miSelectAllFromAdmin.setText(bundle.getString("TldDisplayResults.miSelectAllFromAdmin.text")); // NOI18N
        miSelectAllFromAdmin.setName("miSelectAllFromAdmin"); // NOI18N
        miSelectAllFromAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSelectAllFromAdminActionPerformed(evt);
            }
        });
        pmTldMenu.add(miSelectAllFromAdmin);

        jSeparator2.setName("jSeparator2"); // NOI18N
        pmTldMenu.add(jSeparator2);

        miUnselectAllFromNS.setText(bundle.getString("TldDisplayResults.miUnselectAllFromNS.text")); // NOI18N
        miUnselectAllFromNS.setName("miUnselectAllFromNS"); // NOI18N
        miUnselectAllFromNS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUnselectAllFromNSActionPerformed(evt);
            }
        });
        pmTldMenu.add(miUnselectAllFromNS);

        miUnselectAllFromAdmin.setText(bundle.getString("TldDisplayResults.miUnselectAllFromAdmin.text")); // NOI18N
        miUnselectAllFromAdmin.setName("miUnselectAllFromAdmin"); // NOI18N
        miUnselectAllFromAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUnselectAllFromAdminActionPerformed(evt);
            }
        });
        pmTldMenu.add(miUnselectAllFromAdmin);

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

        tbtnSave.setText("Save"); // NOI18N
        tbtnSave.setFocusable(false);
        tbtnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbtnSave.setName("tbtnSave"); // NOI18N
        tbtnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbtnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbtnSaveMouseClicked(evt);
            }
        });
        tbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(tbtnSave);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        jPanel1.setMaximumSize(new java.awt.Dimension(232, 32767));
        jPanel1.setMinimumSize(new java.awt.Dimension(232, 0));
        jPanel1.setName("jPanel1"); // NOI18N

        cbxCheckAll.setText("Select all"); // NOI18N
        cbxCheckAll.setName("cbxCheckAll"); // NOI18N
        cbxCheckAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCheckAllItemStateChanged(evt);
            }
        });

        jLabel1.setText("Filter"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        txtFilterItems.setColumns(20);
        txtFilterItems.setRows(5);
        txtFilterItems.setName("txtFilterItems"); // NOI18N
        jScrollPane2.setViewportView(txtFilterItems);

        btnFilter.setText("Filter"); // NOI18N
        btnFilter.setName("btnFilter"); // NOI18N
        btnFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFilterMouseClicked(evt);
            }
        });
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cbxCheckAll))
                .addGap(91, 91, 91))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFilter))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cbxCheckAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFilter))
        );

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setDividerSize(5);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tblResults.setAutoCreateRowSorter(true);
        tblResults.setComponentPopupMenu(pmTldMenu);
        tblResults.setName("tblResults"); // NOI18N
        tblResults.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResultsMouseClicked(evt);
            }
        });
        tblResults.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblResultsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblResults);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        pnlWhois.setName("pnlWhois"); // NOI18N
        jScrollPane3.setViewportView(pnlWhois);

        jSplitPane1.setRightComponent(jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 945, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMouseClicked
        String filename = UtilFunctions.saveFile(".xls");
        if (!filename.isEmpty()) {
            try {
                ResultExport.ExportTLDToXLS(filename, ((TldModel) tblResults.getModel()).getData());
            } catch (IOException ex) {
                Logger.getLogger("tldDisplayResults.btnExportMouseClicked").log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnExportMouseClicked

    private void btnTrimUncheckedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrimUncheckedMouseClicked
        TldModel model = (TldModel) tblResults.getModel();
        for (int idx = model.getRowCount() - 1; idx >= 0; idx--) {
            if (!(Boolean) model.getValueAt(idx, 3)) {
                model.deleteRow(idx);
            }
        }
    }//GEN-LAST:event_btnTrimUncheckedMouseClicked

    public void saveData() {
        TldModel model = (TldModel) tblResults.getModel();
        DataStore.addDomains(model.getData());
    }

    private void tbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbtnSaveMouseClicked
        saveData();
    }//GEN-LAST:event_tbtnSaveMouseClicked

    private void miWhoisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miWhoisActionPerformed
        String domain = ((TldModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 0).toString();
        try {
            String name = domain.split("\\.", 2)[0];
            String tld = domain.split("\\.", 2)[1];
            DoWhois frmWhoisTool = new DoWhois(name, tld);
            frmWhoisTool.setVisible(true);
        } catch (Exception e) {
            DoWhois frmWhoisTool = new DoWhois();
            frmWhoisTool.setVisible(true);
        }
    }//GEN-LAST:event_miWhoisActionPerformed

    private void cbxCheckAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCheckAllItemStateChanged
        TldModel model = (TldModel) tblResults.getModel();
        for (int idx = model.getRowCount() - 1; idx >= 0; idx--) {
            model.setValueAt(cbxCheckAll.isSelected(), idx, 3);
        }
    }//GEN-LAST:event_cbxCheckAllItemStateChanged

    private void btnFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFilterMouseClicked

}//GEN-LAST:event_btnFilterMouseClicked

    private void tblResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResultsMouseClicked
        showWhoisData();
}//GEN-LAST:event_tblResultsMouseClicked

    private void showWhoisData() {
        if (tblResults.getSelectedRow() >= 0) {
            String whois = ((TldModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 4).toString();
            pnlWhois.setText(whois);
        }
    }

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        HashSet<String> filter = new HashSet<>();
        filter.addAll(Arrays.asList(txtFilterItems.getText().split(("\\n"))));
        TldModel model = (TldModel) tblResults.getModel();
        for (int idx = 0; idx < model.getRowCount(); idx++) {
            String whoisData = model.getValueAt(idx, 4).toString();
            for (String fi : filter) {
                if (whoisData.contains(fi)) {
                    model.setValueAt(true, idx, 3);
                    break;
                }
            }
        }
    }//GEN-LAST:event_btnFilterActionPerformed

    private void tblResultsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblResultsKeyReleased
        showWhoisData();
    }//GEN-LAST:event_tblResultsKeyReleased

    private void miSelectAllFromAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSelectAllFromAdminActionPerformed
        String admin = ((TldModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 2).toString();
        toggleBasedOnAdmin(admin, true);
    }//GEN-LAST:event_miSelectAllFromAdminActionPerformed

    private void miSelectAllFromNSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSelectAllFromNSActionPerformed
        if (tblResults.getSelectedRow() >= 0) {
            String ns = ((TldModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 1).toString();
            toggleBasedOnNS(ns, true);
        }
    }//GEN-LAST:event_miSelectAllFromNSActionPerformed

    private void miUnselectAllFromNSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUnselectAllFromNSActionPerformed
        String ns = ((TldModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 1).toString();
        toggleBasedOnNS(ns, false);
    }//GEN-LAST:event_miUnselectAllFromNSActionPerformed

    private void miUnselectAllFromAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUnselectAllFromAdminActionPerformed
        String admin = ((TldModel) tblResults.getModel()).getValueAt(tblResults.getSelectedRow(), 2).toString();
        toggleBasedOnAdmin(admin, false);
    }//GEN-LAST:event_miUnselectAllFromAdminActionPerformed

    private void tbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbtnSaveActionPerformed

    private void toggleBasedOnNS(String ns, boolean value) {
        TldModel model = (TldModel) tblResults.getModel();
        for (int idx = 0; idx < model.getRowCount(); idx++) {
            String cNS = model.getValueAt(idx, 1).toString();
            if (ns.compareTo(cNS) == 0) {
                model.setValueAt(value, idx, 3);
            }
        }
    }

    private void toggleBasedOnAdmin(String admin, boolean value) {
        TldModel model = (TldModel) tblResults.getModel();
        for (int idx = 0; idx < model.getRowCount(); idx++) {
            String cAdmin = model.getValueAt(idx, 2).toString();
            if (admin.compareTo(cAdmin) == 0) {
                model.setValueAt(value, idx, 3);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnTrimUnchecked;
    private javax.swing.JCheckBox cbxCheckAll;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem miSelectAllFromAdmin;
    private javax.swing.JMenuItem miSelectAllFromNS;
    private javax.swing.JMenuItem miUnselectAllFromAdmin;
    private javax.swing.JMenuItem miUnselectAllFromNS;
    private javax.swing.JMenuItem miWhois;
    private javax.swing.JPopupMenu pmTldMenu;
    private javax.swing.JEditorPane pnlWhois;
    public javax.swing.JTable tblResults;
    private javax.swing.JButton tbtnSave;
    private javax.swing.JTextArea txtFilterItems;
    // End of variables declaration//GEN-END:variables

}
