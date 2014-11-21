package com.sensepost.yeti.gui;

import java.util.Arrays;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;
import com.sensepost.yeti.models.YileModel;
import com.sensepost.yeti.persistence.DataStore;

/**
 *
 * @author willem
 */
public class YileDisplayResults extends javax.swing.JPanel implements DisplayResultIFace {

    public YileDisplayResults() {
        initComponents();
        this.pnlWhois.setContentType("text/html");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnTrimUnchecked.setText("Clear unselected"); // NOI18N
        btnTrimUnchecked.setFocusable(false);
        btnTrimUnchecked.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
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
        tbtnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbtnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbtnSaveMouseClicked(evt);
            }
        });
        jToolBar1.add(tbtnSave);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        jPanel1.setMaximumSize(new java.awt.Dimension(232, 32767));
        jPanel1.setMinimumSize(new java.awt.Dimension(232, 0));

        cbxCheckAll.setText("Select all"); // NOI18N
        cbxCheckAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCheckAllItemStateChanged(evt);
            }
        });

        jLabel1.setText("Filter"); // NOI18N

        txtFilterItems.setColumns(20);
        txtFilterItems.setRows(5);
        jScrollPane2.setViewportView(txtFilterItems);

        btnFilter.setText("Filter"); // NOI18N
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFilter))
        );

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

        jScrollPane3.setViewportView(pnlWhois);

        jSplitPane1.setRightComponent(jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTrimUncheckedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrimUncheckedMouseClicked
        YileModel model = (YileModel) this.tblResults.getModel();
        for (int idx = model.getRowCount() - 1; idx >= 0; idx--) {
            if (!(Boolean) model.getValueAt(idx, 3)) {
                model.deleteRow(idx);
            }
        }
    }//GEN-LAST:event_btnTrimUncheckedMouseClicked

    private void btnExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportMouseClicked

    }//GEN-LAST:event_btnExportMouseClicked

    public void saveData() {
        YileModel model = (YileModel) this.tblResults.getModel();
//        DataStore.addYileHosts(model.getData());
    }

    private void tbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbtnSaveMouseClicked
        this.saveData();
    }//GEN-LAST:event_tbtnSaveMouseClicked

    private void cbxCheckAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCheckAllItemStateChanged
        YileModel model = (YileModel) this.tblResults.getModel();
        for (int idx = model.getRowCount() - 1; idx >= 0; idx--) {
            model.setValueAt(cbxCheckAll.isSelected(), idx, 3);
        }
    }//GEN-LAST:event_cbxCheckAllItemStateChanged

    private void btnFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFilterMouseClicked

    }//GEN-LAST:event_btnFilterMouseClicked

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        HashSet<String> filter = new HashSet<>();
        filter.addAll(Arrays.asList(txtFilterItems.getText().split(("\\n"))));
        YileModel model = (YileModel) this.tblResults.getModel();
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

    public void showWhoisData() {
        String whois = ((YileModel) this.tblResults.getModel()).getValueAt(this.tblResults.getSelectedRow(), 4).toString();
        this.pnlWhois.setText(whois);
    }

    private void tblResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResultsMouseClicked
        showWhoisData();
    }//GEN-LAST:event_tblResultsMouseClicked

    private void tblResultsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblResultsKeyReleased
        showWhoisData();
    }//GEN-LAST:event_tblResultsKeyReleased

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
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JEditorPane pnlWhois;
    public javax.swing.JTable tblResults;
    private javax.swing.JButton tbtnSave;
    private javax.swing.JTextArea txtFilterItems;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setModel(DefaultTableModel model) {
        this.tblResults.setModel((YileModel) model);
    }

    @Override
    public DefaultTableModel getModel() {
        return (DefaultTableModel) tblResults.getModel();
    }
}
